package com.zfgc.zfgbb.dataprovider.forum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.bbcode.BBCodeConfigDao;
import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import com.zfgc.zfgbb.dbo.AttributeValueMappingDbo;
import com.zfgc.zfgbb.dbo.AttributeValueMappingDboExample;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader;
import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import com.zfgc.zfgbb.dbo.ListStyleTypeDbo;
import com.zfgc.zfgbb.dbo.ListStyleTypeDboExample;
import com.zfgc.zfgbb.mappers.AttributeDataTypeDboMapper;
import com.zfgc.zfgbb.mappers.AttributeValueMappingDboMapper;
import com.zfgc.zfgbb.mappers.BBCodeAttributeDboMapper;
import com.zfgc.zfgbb.mappers.BBCodeAttributeModeDboMapper;
import com.zfgc.zfgbb.mappers.ListStyleTypeDboMapper;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeAttributeMap;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeAttributeModeMap;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeConfigMap;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.AttributeValuePolicy;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BBCodeDataProvider {

	private final BBCodeConfigDao bbCodeConfigDao;

	private final BBCodeAttributeModeDboMapper bbCodeAttributeModeDboMapper;

	private final BBCodeAttributeDboMapper bbCodeAttributeDboMapper;

	private final AttributeDataTypeDboMapper attributeDataTypeDboMapper;

	private final AttributeValueMappingDboMapper attributeValueMappingDboMapper;

	private final ListStyleTypeDboMapper listStyleTypeDboMapper;

	private static final Logger LOGGER = LogManager.getLogger(BBCodeDataProvider.class);

	private final BBCodeConfigMap bbCodeConfigMap;

	private final BBCodeAttributeModeMap bbCodeAttributeModeMap;

	private final BBCodeAttributeMap bbCodeAttributeMap;

	public List<BBCodeConfig> getValidBBCodes() {
		BBCodeConfigDboExample ex = new BBCodeConfigDboExample();
		ex.createCriteria().andEnabledFlagEqualTo(true);
		List<BBCodeConfigDbo> results = bbCodeConfigDao.get(ex);

		return results.stream().map(bbCodeConfigMap::toModel).toList();
	}

	public record BBCodeToggle(String code, boolean enabled, boolean honouredInForum, boolean honouredInWiki,
			boolean honouredInProject, boolean honouredInResource, boolean honouredInSignature,
			boolean scopable) {
	}

	public List<BBCodeToggle> getBBCodeToggles() {
		BBCodeConfigDboExample ex = new BBCodeConfigDboExample();
		ex.setOrderByClause("code");
		Set<String> tooStructuralToScope = BBCodeGrammarLoader.codesTooStructuralToScope(getBBCodeConfig());
		return bbCodeConfigDao.get(ex).stream()
				.map(dbo -> theToggleFor(dbo, tooStructuralToScope))
				.toList();
	}

	private static BBCodeToggle theToggleFor(BBCodeConfigDbo dbo, Set<String> tooStructuralToScope) {
		return new BBCodeToggle(dbo.getCode(), Boolean.TRUE.equals(dbo.getEnabledFlag()),
				!Boolean.FALSE.equals(dbo.getHonouredInForumFlag()),
				!Boolean.FALSE.equals(dbo.getHonouredInWikiFlag()),
				!Boolean.FALSE.equals(dbo.getHonouredInProjectFlag()),
				!Boolean.FALSE.equals(dbo.getHonouredInResourceFlag()),
				!Boolean.FALSE.equals(dbo.getHonouredInSignatureFlag()),
				!tooStructuralToScope.contains(dbo.getCode().toUpperCase(Locale.ROOT)));
	}

	public Optional<BBCodeToggle> setBBCodeEnabled(String code, boolean enabled) {
		BBCodeConfigDboExample ex = new BBCodeConfigDboExample();
		ex.createCriteria().andCodeEqualTo(code);
		Set<String> tooStructuralToScope = BBCodeGrammarLoader.codesTooStructuralToScope(getBBCodeConfig());
		return bbCodeConfigDao.get(ex).stream().findFirst().map(dbo -> {
			dbo.setEnabledFlag(enabled);
			bbCodeConfigDao.save(dbo);
			return theToggleFor(dbo, tooStructuralToScope);
		});
	}

	public Optional<BBCodeToggle> setTheSurfacesThatHonour(String code, ContentScope surface, boolean honoured) {
		BBCodeConfigDboExample ex = new BBCodeConfigDboExample();
		ex.createCriteria().andCodeEqualTo(code);
		Set<String> tooStructuralToScope = BBCodeGrammarLoader.codesTooStructuralToScope(getBBCodeConfig());
		return bbCodeConfigDao.get(ex).stream().findFirst().map(dbo -> {
			if (tooStructuralToScope.contains(dbo.getCode().toUpperCase(Locale.ROOT)))
				throw new IllegalArgumentException("code " + dbo.getCode() + " is honoured on every surface: "
						+ "its absence changes how neighbouring content parses");
			String named = surface == null ? "" : surface.name();
			if ("FORUM".equals(named)) dbo.setHonouredInForumFlag(honoured);
			else if ("WIKI".equals(named)) dbo.setHonouredInWikiFlag(honoured);
			else if ("PROJECT".equals(named)) dbo.setHonouredInProjectFlag(honoured);
			else if ("RESOURCE".equals(named)) dbo.setHonouredInResourceFlag(honoured);
			else if ("SIGNATURE".equals(named)) dbo.setHonouredInSignatureFlag(honoured);
			else throw new IllegalArgumentException("not a surface content is read on: " + named);
			bbCodeConfigDao.save(dbo);
			return theToggleFor(dbo, tooStructuralToScope);
		});
	}

	public List<BBCodeAttributeMode> getAttributeModesByBBCode(Integer bbCodeId) {
		BBCodeAttributeModeDboExample ex = new BBCodeAttributeModeDboExample();
		ex.createCriteria().andBbCodeConfigIdEqualTo(bbCodeId);
		List<BBCodeAttributeModeDbo> results = bbCodeAttributeModeDboMapper.selectByExample(ex);

		return results.stream().map(bbCodeAttributeModeMap::toModel).toList();
	}

	public List<BBCodeAttribute> getAttributesByMode(Integer modeId){
		BBCodeAttributeDboExample ex = new BBCodeAttributeDboExample();
		ex.createCriteria().andBbCodeAttributeModeIdEqualTo(modeId);
		List<BBCodeAttributeDbo> results = bbCodeAttributeDboMapper.selectByExample(ex);

		return results.stream().map(bbCodeAttributeMap::toModel).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public Map<AttributeDataType, AttributeValuePolicy> compileTheDeclaredValuePolicies() {
		Map<AttributeDataType, Map<String, String>> mappings = theDeclaredValueMappings();
		Map<AttributeDataType, AttributeValuePolicy> compiled = new HashMap<>();
		for (AttributeDataTypeDbo declared : attributeDataTypeDboMapper
				.selectByExample(new AttributeDataTypeDboExample())) {
			Optional<AttributeDataType> type = AttributeDataType.forCode(declared.getCode());
			if (type.isEmpty()) {
				LOGGER.error("attribute_data_type row '{}' names no known data type; known codes are {}",
						declared.getCode(), AttributeDataType.knownCodes());
				continue;
			}
			compiled.put(type.get(), new AttributeValuePolicy(compilePattern(declared),
					declared.getFallbackValue() == null ? "" : declared.getFallbackValue(),
					Boolean.TRUE.equals(declared.getValueAdmitsWhitespace()),
					Boolean.TRUE.equals(declared.getLowercasesValue()),
					Optional.ofNullable(declared.getBareIntegerUnit()).filter(unit -> !unit.isBlank()),
					theDeclaredAllowedValues(declared.getAllowedValues()),
					mappings.getOrDefault(type.get(), Map.of())));
		}
		return Map.copyOf(compiled);
	}

	private static Optional<Pattern> compilePattern(AttributeDataTypeDbo declared) {
		if (declared.getValidationPattern() == null || declared.getValidationPattern().isBlank())
			return Optional.empty();
		try {
			return Optional.of(Pattern.compile(declared.getValidationPattern()));
		} catch (PatternSyntaxException uncompilable) {
			LOGGER.error("attribute_data_type {} declares a validation_pattern that will not compile: {}",
					declared.getCode(), uncompilable.getMessage());
			return Optional.empty();
		}
	}

	private static Set<String> theDeclaredAllowedValues(String allowedValues) {
		if (allowedValues == null || allowedValues.isBlank())
			return Set.of();
		Set<String> declared = new LinkedHashSet<>();
		for (String allowed : allowedValues.split(","))
			if (!allowed.isBlank())
				declared.add(allowed.trim());
		return Set.copyOf(declared);
	}

	private Map<AttributeDataType, Map<String, String>> theDeclaredValueMappings() {
		Map<AttributeDataType, Map<String, String>> mappings = new HashMap<>();
		for (AttributeValueMappingDbo declared : attributeValueMappingDboMapper
				.selectByExample(new AttributeValueMappingDboExample()))
			AttributeDataType.forCode(declared.getAttributeDataType())
					.ifPresent(type -> mappings.computeIfAbsent(type, key -> new HashMap<>())
							.put(declared.getFromValue(), declared.getToValue()));
		return mappings;
	}

	public Map<String, Boolean> theDeclaredListStyleTypes() {
		Map<String, Boolean> numbersItemsByCode = new LinkedHashMap<>();
		for (ListStyleTypeDbo declared : listStyleTypeDboMapper.selectByExample(new ListStyleTypeDboExample()))
			numbersItemsByCode.put(declared.getCode(), Boolean.TRUE.equals(declared.getNumbersItems()));
		return Map.copyOf(numbersItemsByCode);
	}

	public Map<String,BBCodeConfig> getBBCodeConfig(){
		Map<String,BBCodeConfig> result = new HashMap<>();
		Map<AttributeDataType, AttributeValuePolicy> valuePolicies = compileTheDeclaredValuePolicies();

		List<BBCodeConfig> bbCodes = getValidBBCodes();
		for(BBCodeConfig bbCode : bbCodes){
			normalizeSourceReference(bbCode);
			List<BBCodeAttributeMode> modesDb = getAttributeModesByBBCode(bbCode.getBbCodeConfigId());

			Map<Integer, List<BBCodeAttribute>> attrsByMode = new HashMap<>();
			Set<String> orderedNames = new LinkedHashSet<>();
			Map<String, AttributeValuePolicy> valuePolicyByAttributeName = new HashMap<>();
			for (BBCodeAttributeMode mode : modesDb) {
				List<BBCodeAttribute> attrs = getAttributesByMode(mode.getBbCodeAttributeModeId());
				for (BBCodeAttribute attribute : attrs) {
					AttributeDataType dataType = declaredDataType(attribute);
					attribute.setDataType(dataType);
					attribute.setValuePolicy(valuePolicies.getOrDefault(dataType,
							AttributeValuePolicy.rejectingEveryValue("")));
					attribute.setAttributeIndex("{{" + Integer.parseInt(attribute.getAttributeIndex()) + "}}");
					attribute.setName(attribute.getName().equals("NAMELESS") ? "=" : attribute.getName() + "=");
					orderedNames.add(attribute.getName());
					valuePolicyByAttributeName.putIfAbsent(attribute.getName(), attribute.getValuePolicy());
				}
				attrsByMode.put(mode.getBbCodeAttributeModeId(), attrs);
			}

			bbCode.setAllAttributeNamesAsString(String.join(",", orderedNames));
			bbCode.setValuePolicyByAttributeName(Map.copyOf(valuePolicyByAttributeName));

			for (BBCodeAttributeMode mode : modesDb) {
				List<BBCodeAttribute> attrs = attrsByMode.get(mode.getBbCodeAttributeModeId());
				Set<String> namesInMode = attrs.stream()
						.map(BBCodeAttribute::getName)
						.collect(Collectors.toSet());

				StringBuilder modeKey = new StringBuilder();
				for (String name : orderedNames) {
					if (namesInMode.contains(name)) {
						modeKey.append(name);
					}
				}

				attrs.sort(Comparator.comparingInt(a ->
						Integer.parseInt(a.getAttributeIndex().replaceAll("[^0-9]", ""))));

				mode.setAttributes(attrs);
				bbCode.getAttributeConfig().put(modeKey.toString(), mode);
			}

			result.put(bbCode.getCode().toUpperCase(), bbCode);
		}

		return result;
	}

	private void normalizeSourceReference(BBCodeConfig bbCode) {
		if (bbCode.getSourceReferenceAttribute() == null && bbCode.getSourceReferenceResolver() == null) {
			return;
		}
		if (bbCode.getSourceReferenceAttribute() == null || bbCode.getSourceReferenceResolver() == null) {
			throw InvalidBBCodeGrammarException.sourceReferenceDeclaredWithoutItsPair(bbCode);
		}
		bbCode.setSourceReferenceAttribute(bbCode.getSourceReferenceAttribute() + "=");
	}

	private static AttributeDataType declaredDataType(BBCodeAttribute attribute) {
		return AttributeDataType.forCode(attribute.getAttributeDataType())
				.orElseThrow(() -> InvalidBBCodeGrammarException.unknownAttributeDataType(attribute));
	}
}