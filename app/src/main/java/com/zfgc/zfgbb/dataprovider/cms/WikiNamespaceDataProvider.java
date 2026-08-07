package com.zfgc.zfgbb.dataprovider.cms;

import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

import com.zfgc.zfgbb.dbo.WikiNamespaceDbo;
import com.zfgc.zfgbb.dbo.WikiNamespaceDboExample;
import com.zfgc.zfgbb.dao.cms.WikiNamespaceDao;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;
import com.zfgc.zfgbb.wiki.WikiTitle;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WikiNamespaceDataProvider {

	private static final long ROLE_CACHE_TTL_NANOS = TimeUnit.SECONDS.toNanos(10);

	private volatile RoleCache roleCache;

	private final AtomicBoolean warnedMissingTemplateRole =
			new AtomicBoolean();

	private final WikiNamespaceDao wikiNamespaceDao;

	public WikiNamespaceRole roleOf(String namespace) {
		if (namespace == null || namespace.isBlank())
			return null;
		return roleByNamespace().get(namespace.toLowerCase(Locale.ROOT));
	}

	public boolean hasRole(String namespace, WikiNamespaceRole role) {
		return role != null && role == roleOf(namespace);
	}

	public String nameForRole(WikiNamespaceRole role) {
		if (role == null)
			return null;
		return nameByRole().get(role);
	}

	private Map<String, WikiNamespaceRole> roleByNamespace() {
		return roleCache().byNamespace();
	}

	private Map<WikiNamespaceRole, String> nameByRole() {
		return roleCache().byRole();
	}

	private record RoleCache(Map<String, WikiNamespaceRole> byNamespace, Map<WikiNamespaceRole, String> byRole,
			long loadedAtNanos) {
	}

	private RoleCache roleCache() {
		RoleCache cached = roleCache;
		if (cached != null && System.nanoTime() - cached.loadedAtNanos() < ROLE_CACHE_TTL_NANOS)
			return cached;
		Map<String, WikiNamespaceRole> byNamespace = new HashMap<>();
		Map<WikiNamespaceRole, String> byRole = new EnumMap<>(WikiNamespaceRole.class);
		WikiNamespaceDboExample withRole = new WikiNamespaceDboExample();
		withRole.createCriteria().andEngineRoleIsNotNull();
		for (WikiNamespaceDbo row : wikiNamespaceDao.get(withRole)) {
			WikiNamespaceRole role = WikiNamespaceRole.parse(row.getEngineRole());
			if (role == null || row.getName() == null)
				continue;
			byNamespace.put(row.getName().toLowerCase(Locale.ROOT), role);
			byRole.put(role, row.getName());
		}
		RoleCache loaded = new RoleCache(Map.copyOf(byNamespace), Map.copyOf(byRole), System.nanoTime());
		roleCache = loaded;
		return loaded;
	}

	private void invalidateRoleCache() {
		roleCache = null;
	}

	public int assignEngineRole(String name, WikiNamespaceRole role) {
		int updated = wikiNamespaceDao.assignEngineRole(name, role.name());
		invalidateRoleCache();
		return updated;
	}

	public String templateNamespace() {
		String name = nameForRole(WikiNamespaceRole.TEMPLATE);
		if (name != null)
			return name;
		if (warnedMissingTemplateRole.compareAndSet(false, true))
			log.warn("no namespace holds the TEMPLATE engine role; using 'Template'");
		return "Template";
	}

	public String templateCode(String title) {
		return WikiTitle.normalizeTitle(CmsSupport.wikiTitleDisplay(title), caseMode(templateNamespace()));
	}

	public WikiTitle resolve(String path) {
		int colon = path == null ? -1 : path.indexOf(':');
		if (colon > 0 && colon < path.length() - 1) {
			String prefix = path.substring(0, colon).trim();
			List<WikiNamespaceCustomMapper.NamespaceRecord> namespaces = wikiNamespaceDao.resolveNamespace(prefix);
			if (namespaces.size() > 1)
				throw new IllegalStateException("Ambiguous registered wiki namespace '" + prefix + "'");
			if (namespaces.size() == 1) {
				WikiNamespaceCustomMapper.NamespaceRecord ns = namespaces.get(0);
				return WikiTitle.of(ns.name(), path.substring(colon + 1), WikiTitle.CaseMode.valueOf(ns.caseMode()));
			}
		}
		if (colon < 0) {
			List<String> modes = wikiNamespaceDao.findMainCaseMode();
			if (modes.size() == 1)
				return WikiTitle.of("MAIN", path, WikiTitle.CaseMode.valueOf(modes.get(0)));
		}
		return WikiTitle.parse(path);
	}

	public WikiTitle.CaseMode caseMode(String namespace) {
		List<String> modes = wikiNamespaceDao.findCaseModeByName(namespace);
		return modes.size() == 1 ? WikiTitle.CaseMode.valueOf(modes.get(0)) : WikiTitle.CaseMode.FIRST_LETTER;
	}

	public Optional<WikiNamespaceCustomMapper.EditPolicyRecord> editPolicy(String namespace) {
		if (namespace == null || namespace.isBlank())
			return Optional.empty();
		return wikiNamespaceDao.findEditPolicyByName(namespace).stream().findFirst();
	}
}
