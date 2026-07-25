package com.zfgc.zfgbb.migrator.wiki.mappers;

import com.zfgc.zfgbb.migrator.wiki.dbo.MwTextDb;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwTextDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface MwTextDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729382713-04:00", comments="Source Table: zfgc_wikitext")
    int deleteByPrimaryKey(Integer oldId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729394793-04:00", comments="Source Table: zfgc_wikitext")
    int insert(MwTextDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729407923-04:00", comments="Source Table: zfgc_wikitext")
    int insertSelective(MwTextDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729422202-04:00", comments="Source Table: zfgc_wikitext")
    List<MwTextDb> selectByExampleWithBLOBs(MwTextDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729437802-04:00", comments="Source Table: zfgc_wikitext")
    List<MwTextDb> selectByExample(MwTextDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729461251-04:00", comments="Source Table: zfgc_wikitext")
    MwTextDb selectByPrimaryKey(Integer oldId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729542958-04:00", comments="Source Table: zfgc_wikitext")
    int updateByPrimaryKeySelective(MwTextDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729605956-04:00", comments="Source Table: zfgc_wikitext")
    int updateByPrimaryKeyWithBLOBs(MwTextDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729632665-04:00", comments="Source Table: zfgc_wikitext")
    int updateByPrimaryKey(MwTextDb row);
}