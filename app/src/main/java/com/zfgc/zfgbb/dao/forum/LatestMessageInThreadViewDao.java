package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDbo;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDboExample;
import com.zfgc.zfgbb.mappers.LatestMessageInThreadViewDboMapper;

@Repository
public class LatestMessageInThreadViewDao extends ReadDao<LatestMessageInThreadViewDbo, LatestMessageInThreadViewDboExample> {

	public LatestMessageInThreadViewDao(LatestMessageInThreadViewDboMapper mapper) {
		super(mapper);
	}
}
