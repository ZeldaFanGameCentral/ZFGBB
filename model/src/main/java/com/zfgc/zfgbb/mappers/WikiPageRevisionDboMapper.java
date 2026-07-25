package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.persistence.VersionedIdentityCrudMapper;

public interface WikiPageRevisionDboMapper extends VersionedIdentityCrudMapper<WikiPageRevisionDbo, WikiPageRevisionDboExample, Integer> {
}