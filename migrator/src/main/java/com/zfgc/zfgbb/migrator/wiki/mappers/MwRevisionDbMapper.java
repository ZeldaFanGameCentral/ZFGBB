package com.zfgc.zfgbb.migrator.wiki.mappers;

import com.zfgc.zfgbb.migrator.wiki.dbo.MwRevisionDb;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwRevisionDbExample;
import com.zfgc.zfgbb.migrator.wiki.dbo.MwRevisionDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MwRevisionDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728253459-04:00", comments="Source Table: zfgc_wikirevision")
    long countByExample(MwRevisionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728268859-04:00", comments="Source Table: zfgc_wikirevision")
    int deleteByExample(MwRevisionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728288148-04:00", comments="Source Table: zfgc_wikirevision")
    int deleteByPrimaryKey(Integer revId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728310137-04:00", comments="Source Table: zfgc_wikirevision")
    int insert(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728328097-04:00", comments="Source Table: zfgc_wikirevision")
    int insertSelective(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728346086-04:00", comments="Source Table: zfgc_wikirevision")
    List<MwRevisionDbWithBLOBs> selectByExampleWithBLOBs(MwRevisionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728362176-04:00", comments="Source Table: zfgc_wikirevision")
    List<MwRevisionDb> selectByExample(MwRevisionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728387955-04:00", comments="Source Table: zfgc_wikirevision")
    MwRevisionDbWithBLOBs selectByPrimaryKey(Integer revId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728409504-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByExampleSelective(@Param("row") MwRevisionDbWithBLOBs row, @Param("example") MwRevisionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728448263-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByExampleWithBLOBs(@Param("row") MwRevisionDbWithBLOBs row, @Param("example") MwRevisionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728469252-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByExample(@Param("row") MwRevisionDb row, @Param("example") MwRevisionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728501191-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByPrimaryKeySelective(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728521031-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByPrimaryKeyWithBLOBs(MwRevisionDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72853794-04:00", comments="Source Table: zfgc_wikirevision")
    int updateByPrimaryKey(MwRevisionDb row);
}