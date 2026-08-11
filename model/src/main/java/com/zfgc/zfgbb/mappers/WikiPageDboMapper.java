package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.persistence.VersionedIdentityCrudMapper;

public interface WikiPageDboMapper extends VersionedIdentityCrudMapper<WikiPageDbo, WikiPageDboExample, Integer> {
}