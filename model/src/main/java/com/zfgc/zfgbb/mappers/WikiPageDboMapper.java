package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WikiPageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711246536-04:00", comments="Source Table: zfgbb.wiki_page")
    long countByExample(WikiPageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711263675-04:00", comments="Source Table: zfgbb.wiki_page")
    int deleteByExample(WikiPageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711276315-04:00", comments="Source Table: zfgbb.wiki_page")
    int deleteByPrimaryKey(Integer wikiPageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711286894-04:00", comments="Source Table: zfgbb.wiki_page")
    int insert(WikiPageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711307634-04:00", comments="Source Table: zfgbb.wiki_page")
    int insertSelective(WikiPageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711334653-04:00", comments="Source Table: zfgbb.wiki_page")
    List<WikiPageDbo> selectByExample(WikiPageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711350252-04:00", comments="Source Table: zfgbb.wiki_page")
    WikiPageDbo selectByPrimaryKey(Integer wikiPageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711366932-04:00", comments="Source Table: zfgbb.wiki_page")
    int updateByExampleSelective(@Param("row") WikiPageDbo row, @Param("example") WikiPageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711384631-04:00", comments="Source Table: zfgbb.wiki_page")
    int updateByExample(@Param("row") WikiPageDbo row, @Param("example") WikiPageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711406271-04:00", comments="Source Table: zfgbb.wiki_page")
    int updateByPrimaryKeySelective(WikiPageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7114248-04:00", comments="Source Table: zfgbb.wiki_page")
    int updateByPrimaryKey(WikiPageDbo row);

    List<WikiPageDbo> selectByExampleWithLimits(WikiPageDboExample example);
}