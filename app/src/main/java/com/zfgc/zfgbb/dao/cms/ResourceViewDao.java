package com.zfgc.zfgbb.dao.cms;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.ResourceViewDbo;
import com.zfgc.zfgbb.dbo.ResourceViewDboExample;
import com.zfgc.zfgbb.mappers.ResourceViewDboMapper;
import com.zfgc.zfgbb.mappers.custom.CmsFacetMapper;

@Repository
public class ResourceViewDao extends ReadDao<ResourceViewDbo, ResourceViewDboExample> {

	private final CmsFacetMapper cmsFacetMapper;

	public ResourceViewDao(ResourceViewDboMapper mapper, CmsFacetMapper cmsFacetMapper) {
		super(mapper);
		this.cmsFacetMapper = cmsFacetMapper;
	}

	public List<CmsFacetMapper.FacetCount> countResourceTypes() {
		return cmsFacetMapper.countResourceTypes();
	}
}
