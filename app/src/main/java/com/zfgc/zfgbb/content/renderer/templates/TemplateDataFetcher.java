package com.zfgc.zfgbb.content.renderer.templates;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Semaphore;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.zfgc.zfgbb.authorization.RequestingUser;
import com.zfgc.zfgbb.model.User;

import tools.jackson.databind.ObjectMapper;

@Component
public class TemplateDataFetcher {

	private record Endpoint(Object bean, Method method, String variable, String pattern) {
	}

	private record Registry(Map<String, Endpoint> exact, Map<String, Endpoint> trailing) {
	}

	private final Logger logger = LoggerFactory.getLogger(TemplateDataFetcher.class);

	private final ThreadLocal<Boolean> inFetch = ThreadLocal.withInitial(() -> Boolean.FALSE);

	private volatile Registry registry;

	private static final int SECONDS_A_FETCH_WAITS_FOR_ITS_CONNECTION = 2;

	private final ObjectProvider<TemplateDataService> dataServices;
	private final ObjectMapper json;
	private final TransactionTemplate fetchTransaction;
	private final Semaphore secondConnections;

	public TemplateDataFetcher(ObjectProvider<TemplateDataService> dataServices, ObjectMapper json,
			PlatformTransactionManager transactionManager,
			@Value("${zfgbb.templates.concurrent-fetches:4}") int concurrentFetches) {
		this.dataServices = dataServices;
		this.json = json;
		this.fetchTransaction = new TransactionTemplate(transactionManager);
		fetchTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		fetchTransaction.setReadOnly(true);
		fetchTransaction.setTimeout(5);
		this.secondConnections = new Semaphore(concurrentFetches);
	}

	private Object theEndpointFetchedWithinTheConnectionBudget(Endpoint endpoint, Map<String, String> params)
			throws InterruptedException {
		if (!secondConnections.tryAcquire(SECONDS_A_FETCH_WAITS_FOR_ITS_CONNECTION, TimeUnit.SECONDS)) {
			logger.warn("template data fetch gave up waiting for a connection slot; the widget renders blank");
			return null;
		}
		try {
			return fetchTransaction.execute(status -> invoke(endpoint, params));
		} finally {
			secondConnections.release();
		}
	}

	public Object fetch(String path) {
		if (path == null || !path.startsWith("/") || Boolean.TRUE.equals(inFetch.get())) {
			return null;
		}
		inFetch.set(Boolean.TRUE);
		try {
			String pathPart = pathPortion(path);
			Map<String, String> params = pathPart.length() == path.length() ? new HashMap<>()
					: parseQuery(path.substring(pathPart.length() + 1));
			Endpoint endpoint = resolve(pathPart, params);
			if (endpoint == null) {
				return null;
			}
			Object result = theEndpointFetchedWithinTheConnectionBudget(endpoint, params);
			return result == null ? null : json.convertValue(result, Object.class);
		} catch (Exception e) {
			logger.warn("template data fetch failed for '{}': {}", path, e.toString());
			return null;
		} finally {
			inFetch.remove();
		}
	}

	public boolean canResolve(String source) {
		if (source == null || !source.startsWith("/"))
			return false;
		return resolve(pathPortion(source), new HashMap<>()) != null;
	}

	public String pathPortion(String source) {
		int queryIdx = source.indexOf('?');
		return queryIdx < 0 ? source : source.substring(0, queryIdx);
	}

	private Endpoint resolve(String path, Map<String, String> params) {
		Registry sources = registry();
		Endpoint exact = sources.exact().get(path);
		if (exact != null) {
			return exact;
		}
		int lastSlash = path.lastIndexOf('/');
		if (lastSlash <= 0) {
			return null;
		}
		Endpoint trailing = sources.trailing().get(path.substring(0, lastSlash));
		String segment = path.substring(lastSlash + 1);
		if (trailing == null || segment.isEmpty()) {
			return null;
		}
		params.put(trailing.variable(), decode(segment));
		return trailing;
	}

	private Registry registry() {
		Registry sources = registry;
		if (sources == null) {
			sources = buildRegistry();
			registry = sources;
		}
		return sources;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initializeSources() {
		logger.info("template data sources: {}", describeSources());
	}

	public Map<String, List<String>> describeSources() {
		Registry sources = registry();
		Map<String, List<String>> description = new TreeMap<>();
		Stream.concat(sources.exact().values().stream(), sources.trailing().values().stream())
				.forEach(endpoint -> description.put(endpoint.pattern(), paramNames(endpoint.method())));
		return description;
	}

	private static List<String> paramNames(Method method) {
		return Stream.of(method.getParameters())
				.filter(parameter -> parameter.getType() != User.class)
				.map(Parameter::getName)
				.toList();
	}

	private Registry buildRegistry() {
		Map<String, Endpoint> exact = new HashMap<>();
		Map<String, Endpoint> trailing = new HashMap<>();
		dataServices.stream().forEach(service -> {
			Class<?> targetClass = AopProxyUtils.ultimateTargetClass(service);
			for (Method method : targetClass.getMethods()) {
				TemplateSource source = AnnotationUtils.findAnnotation(method, TemplateSource.class);
				if (source == null) {
					continue;
				}
				register(exact, trailing, source.value(), new Endpoint(service, method, null, source.value()));
			}
		});
		return new Registry(Map.copyOf(exact), Map.copyOf(trailing));
	}

	private void register(Map<String, Endpoint> exact, Map<String, Endpoint> trailing, String pattern,
			Endpoint endpoint) {
		for (Parameter parameter : endpoint.method().getParameters()) {
			if (parameter.getType() == User.class) {
				continue;
			}
			require(parameter.getType() == Integer.class || parameter.getType() == String.class, pattern,
					"unsupported parameter type " + parameter.getType().getName());
			require(!parameter.getName().matches("arg\\d+"), pattern,
					"parameter names unavailable (compile with -parameters)");
		}
		int braceIdx = pattern.indexOf('{');
		if (braceIdx < 0) {
			require(!exact.containsKey(pattern), pattern, "duplicate path");
			exact.put(pattern, endpoint);
			return;
		}
		require(braceIdx > 0 && pattern.endsWith("}") && pattern.charAt(braceIdx - 1) == '/'
				&& pattern.indexOf('{', braceIdx + 1) < 0, pattern, "only a single trailing {variable} is supported");
		String prefix = pattern.substring(0, braceIdx - 1);
		String variable = pattern.substring(braceIdx + 1, pattern.length() - 1);
		require(!prefix.isEmpty() && !variable.isEmpty(), pattern, "invalid pattern");
		require(!trailing.containsKey(prefix), pattern, "duplicate path");
		trailing.put(prefix, new Endpoint(endpoint.bean(), endpoint.method(), variable, pattern));
	}

	private static void require(boolean condition, String pattern, String message) {
		if (!condition) {
			throw new IllegalStateException("@TemplateSource '" + pattern + "': " + message);
		}
	}

	private Object invoke(Endpoint endpoint, Map<String, String> params) {
		Parameter[] parameters = endpoint.method().getParameters();
		Object[] args = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			args[i] = bind(parameters[i], params);
		}
		try {
			return endpoint.method().invoke(endpoint.bean(), args);
		} catch (ReflectiveOperationException e) {
			throw e.getCause() instanceof RuntimeException cause ? cause : new IllegalStateException(e);
		}
	}

	private static Object bind(Parameter parameter, Map<String, String> params) {
		if (parameter.getType() == User.class) {
			return RequestingUser.onThisRequest();
		}
		String value = params.get(parameter.getName());
		if (value == null) {
			return null;
		}
		if (parameter.getType() == Integer.class) {
			return value.isBlank() ? null : Integer.valueOf(value);
		}
		return value;
	}

	private static Map<String, String> parseQuery(String query) {
		Map<String, String> params = new HashMap<>();
		for (String pair : query.split("&")) {
			int eqIdx = pair.indexOf('=');
			if (eqIdx > 0) {
				params.put(decode(pair.substring(0, eqIdx)), decode(pair.substring(eqIdx + 1)));
			}
		}
		return params;
	}

	private static String decode(String value) {
		return URLDecoder.decode(value, StandardCharsets.UTF_8);
	}
}
