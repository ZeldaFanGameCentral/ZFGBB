package com.zfgc.zfgbb.dao.system;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.AbstractDao;
import com.zfgc.zfgbb.dbo.SystemConfigDbo;
import com.zfgc.zfgbb.dbo.SystemConfigDboExample;
import com.zfgc.zfgbb.mappers.SystemConfigDboMapper;

@Repository
public class SystemConfigDao extends AbstractDao<SystemConfigDboExample, SystemConfigDboMapper, SystemConfigDbo> {

	@Autowired
	public SystemConfigDao(SystemConfigDboMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<SystemConfigDbo> get(Integer id) {
		throw new UnsupportedOperationException("system_config has a text PK; use SystemConfigService.get(key)");
	}

	@Override
	public List<SystemConfigDbo> get(SystemConfigDboExample ex) {
		return mapper.selectByExample(ex);
	}

	@Override
	protected void update(SystemConfigDbo toSave) {
		mapper.updateByPrimaryKey(toSave);
	}

	@Override
	protected void create(SystemConfigDbo toCreate) {
		mapper.insert(toCreate);
	}
}
