package com.zfgc.zfgbb.dao.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.AbstractDao;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import com.zfgc.zfgbb.mappers.BrUserPermissionDboMapper;

@Repository
public class BrUserPermissionDao extends AbstractDao<BrUserPermissionDboExample, BrUserPermissionDboMapper, BrUserPermissionDbo> {

	@Autowired
	public BrUserPermissionDao(BrUserPermissionDboMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<BrUserPermissionDbo> get(Integer id) {
		return Optional.ofNullable(mapper.selectByPrimaryKey(id));
	}

	@Override
	public List<BrUserPermissionDbo> get(BrUserPermissionDboExample ex) {
		return mapper.selectByExample(ex);
	}

	@Override
	protected void update(BrUserPermissionDbo toSave) {
		mapper.updateByPrimaryKey(toSave);
	}

	@Override
	protected void create(BrUserPermissionDbo toCreate) {
		mapper.insert(toCreate);
	}
}
