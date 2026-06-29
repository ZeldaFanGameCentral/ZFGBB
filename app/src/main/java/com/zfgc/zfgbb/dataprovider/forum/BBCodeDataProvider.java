package com.zfgc.zfgbb.dataprovider.forum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.bbcode.BBCodeAttributeDao;
import com.zfgc.zfgbb.dao.bbcode.BBCodeAttributeModeDao;
import com.zfgc.zfgbb.dao.bbcode.BBCodeConfigDao;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeAttributeMap;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeAttributeModeMap;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeConfigMap;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

@Repository
public class BBCodeDataProvider {
	
	@Autowired
	private BBCodeConfigDao bbCodeConfigDao;
	
	@Autowired
	private BBCodeAttributeModeDao bbCodeAttributeModeDao;
	
	@Autowired
	private BBCodeAttributeDao bbCodeAttributeDao;

	@Autowired
	private BBCodeConfigMap bbCodeConfigMap;

	@Autowired
	private BBCodeAttributeModeMap bbCodeAttributeModeMap;

	@Autowired
	private BBCodeAttributeMap bbCodeAttributeMap;

	public List<BBCodeConfig> getValidBbCodes() {
		BBCodeConfigDboExample ex = new BBCodeConfigDboExample();
		ex.createCriteria().andEnabledFlagEqualTo(true);
		List<BBCodeConfigDbo> results = bbCodeConfigDao.get(ex);

		return results.stream().map(bbCodeConfigMap::toModel).toList();
	}

	public record BbCodeToggle(String code, boolean enabled) {
	}

	public List<BbCodeToggle> getBbCodeToggles() {
		BBCodeConfigDboExample ex = new BBCodeConfigDboExample();
		ex.setOrderByClause("code");
		return bbCodeConfigDao.get(ex).stream()
				.map(dbo -> new BbCodeToggle(dbo.getCode(), Boolean.TRUE.equals(dbo.getEnabledFlag())))
				.toList();
	}

	public Optional<BbCodeToggle> setBbCodeEnabled(String code, boolean enabled) {
		BBCodeConfigDboExample ex = new BBCodeConfigDboExample();
		ex.createCriteria().andCodeEqualTo(code);
		return bbCodeConfigDao.get(ex).stream().findFirst().map(dbo -> {
			dbo.setEnabledFlag(enabled);
			bbCodeConfigDao.save(dbo);
			return new BbCodeToggle(dbo.getCode(), enabled);
		});
	}

	public List<BBCodeAttributeMode> getAttributeModesByBbCode(Integer bbCodeId) {
		BBCodeAttributeModeDboExample ex = new BBCodeAttributeModeDboExample();
		ex.createCriteria().andBbCodeConfigIdEqualTo(bbCodeId);
		List<BBCodeAttributeModeDbo> results = bbCodeAttributeModeDao.get(ex);

		return results.stream().map(bbCodeAttributeModeMap::toModel).toList();
	}

	public List<BBCodeAttribute> getAttributesByMode(Integer modeId){
		BBCodeAttributeDboExample ex = new BBCodeAttributeDboExample();
		ex.createCriteria().andBbCodeAttributeModeIdEqualTo(modeId);
		List<BBCodeAttributeDbo> results = bbCodeAttributeDao.get(ex);

		return results.stream().map(bbCodeAttributeMap::toModel).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public Map<String,BBCodeConfig> getBbCodeConfig(){
		Map<String,BBCodeConfig> result = new HashMap<>();

		List<BBCodeConfig> bbCodes = getValidBbCodes();
		for(BBCodeConfig bbCode : bbCodes){
			List<BBCodeAttributeMode> modesDb = getAttributeModesByBbCode(bbCode.getBbCodeConfigId());

			Map<Integer, List<BBCodeAttribute>> attrsByMode = new HashMap<>();
			Set<String> orderedNames = new LinkedHashSet<>();
			for (BBCodeAttributeMode mode : modesDb) {
				List<BBCodeAttribute> attrs = getAttributesByMode(mode.getBbCodeAttributeModeId());
				for (BBCodeAttribute attribute : attrs) {
					attribute.setDataType(AttributeDataType.values()[attribute.getAttributeDataType()]);
					attribute.setAttributeIndex("{{" + Integer.parseInt(attribute.getAttributeIndex()) + "}}");
					attribute.setName(attribute.getName().equals("NAMELESS") ? "=" : attribute.getName() + "=");
					orderedNames.add(attribute.getName());
				}
				attrsByMode.put(mode.getBbCodeAttributeModeId(), attrs);
			}

			bbCode.setAllAttributeNamesAsString(String.join(",", orderedNames));

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
}