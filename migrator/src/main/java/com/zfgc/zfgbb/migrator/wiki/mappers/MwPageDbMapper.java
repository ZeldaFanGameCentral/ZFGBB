package com.zfgc.zfgbb.migrator.wiki.mappers;

import com.zfgc.zfgbb.migrator.wiki.dbo.MwPageDb;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwPageDbExample;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwPageDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;

public interface MwPageDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726831124-04:00", comments="Source Table: zfgc_wikipage")
    int deleteByPrimaryKey(Integer pageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726846673-04:00", comments="Source Table: zfgc_wikipage")
    int insert(MwPageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726861923-04:00", comments="Source Table: zfgc_wikipage")
    int insertSelective(MwPageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726877593-04:00", comments="Source Table: zfgc_wikipage")
    List<MwPageDbWithBLOBs> selectByExampleWithBLOBs(MwPageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726892572-04:00", comments="Source Table: zfgc_wikipage")
    List<MwPageDb> selectByExample(MwPageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726909751-04:00", comments="Source Table: zfgc_wikipage")
    MwPageDbWithBLOBs selectByPrimaryKey(Integer pageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726986469-04:00", comments="Source Table: zfgc_wikipage")
    int updateByPrimaryKeySelective(MwPageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727005618-04:00", comments="Source Table: zfgc_wikipage")
    int updateByPrimaryKeyWithBLOBs(MwPageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727032348-04:00", comments="Source Table: zfgc_wikipage")
    int updateByPrimaryKey(MwPageDb row);
}