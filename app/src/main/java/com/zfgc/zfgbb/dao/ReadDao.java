package com.zfgc.zfgbb.dao;

import java.util.List;

import com.zfgc.zfgbb.persistence.ReadMapper;

public abstract class ReadDao<Dbo, Example> {

	private final ReadMapper<Dbo, Example> readMapper;

	protected ReadDao(ReadMapper<Dbo, Example> readMapper) {
		this.readMapper = readMapper;
	}

	public List<Dbo> get(Example example) {
		return readMapper.selectByExampleWithLimits(example);
	}

	public long count(Example example) {
		return readMapper.countByExample(example);
	}
}
