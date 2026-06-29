package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WikiPageRevisionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713117957-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    long countByExample(WikiPageRevisionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713135767-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int deleteByExample(WikiPageRevisionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713153906-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int deleteByPrimaryKey(Integer wikiPageRevisionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713180815-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int insert(WikiPageRevisionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713195035-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int insertSelective(WikiPageRevisionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713210494-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    List<WikiPageRevisionDbo> selectByExample(WikiPageRevisionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713226434-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    WikiPageRevisionDbo selectByPrimaryKey(Integer wikiPageRevisionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713246223-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int updateByExampleSelective(@Param("row") WikiPageRevisionDbo row, @Param("example") WikiPageRevisionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713263913-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int updateByExample(@Param("row") WikiPageRevisionDbo row, @Param("example") WikiPageRevisionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713284892-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int updateByPrimaryKeySelective(WikiPageRevisionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713332171-04:00", comments="Source Table: zfgbb.wiki_page_revision")
    int updateByPrimaryKey(WikiPageRevisionDbo row);

    List<WikiPageRevisionDbo> selectByExampleWithLimits(WikiPageRevisionDboExample example);
}