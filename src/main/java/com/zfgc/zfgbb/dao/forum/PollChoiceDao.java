package com.zfgc.zfgbb.dao.forum;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.AbstractDao;
import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import com.zfgc.zfgbb.mappers.PollChoiceDboMapper;

@Repository
public class PollChoiceDao extends AbstractDao<PollChoiceDboExample, PollChoiceDboMapper, PollChoiceDbo> {

	public PollChoiceDao(PollChoiceDboMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<PollChoiceDbo> get(Integer id) {
		return Optional.ofNullable(mapper.selectByPrimaryKey(id));
	}

	@Override
	public List<PollChoiceDbo> get(PollChoiceDboExample ex) {
		return mapper.selectByExample(ex);
	}

	@Override
	protected void update(PollChoiceDbo toSave) {
		mapper.updateByPrimaryKey(toSave);
	}

	@Override
	protected void create(PollChoiceDbo toCreate) {
		mapper.insert(toCreate);
	}

}
