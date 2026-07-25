package com.zfgc.zfgbb.migrator.wiki.mappers;

import com.zfgc.zfgbb.migrator.wiki.dbo.MwRevisionDb;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwRevisionDbWithBLOBs;
import jakarta.annotation.Generated;

public interface MwRevisionDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728288148-04:00", comments="Source Table: zfgc_wikirevision")
    int deleteByPrimaryKey(Integer revId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728310137-04:00", comments="Source Table: zfgc_wikirevision")
    int insert(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728328097-04:00", comments="Source Table: zfgc_wikirevision")
    int insertSelective(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728387955-04:00", comments="Source Table: zfgc_wikirevision")
    MwRevisionDbWithBLOBs selectByPrimaryKey(Integer revId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728501191-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByPrimaryKeySelective(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728521031-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByPrimaryKeyWithBLOBs(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72853794-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByPrimaryKey(MwRevisionDb row);
}