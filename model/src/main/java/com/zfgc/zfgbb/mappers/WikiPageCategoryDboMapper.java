package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WikiPageCategoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724112135-04:00", comments="Source Table: zfgbb.wiki_page_category")
    long countByExample(WikiPageCategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724126635-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int deleteByExample(WikiPageCategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724138284-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int deleteByPrimaryKey(Integer wikiPageCategoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724147784-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int insert(WikiPageCategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724158274-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int insertSelective(WikiPageCategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724174043-04:00", comments="Source Table: zfgbb.wiki_page_category")
    List<WikiPageCategoryDbo> selectByExample(WikiPageCategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724187823-04:00", comments="Source Table: zfgbb.wiki_page_category")
    WikiPageCategoryDbo selectByPrimaryKey(Integer wikiPageCategoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724202662-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int updateByExampleSelective(@Param("row") WikiPageCategoryDbo row, @Param("example") WikiPageCategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724218182-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int updateByExample(@Param("row") WikiPageCategoryDbo row, @Param("example") WikiPageCategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724235881-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int updateByPrimaryKeySelective(WikiPageCategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.724255461-04:00", comments="Source Table: zfgbb.wiki_page_category")
    int updateByPrimaryKey(WikiPageCategoryDbo row);

    List<WikiPageCategoryDbo> selectByExampleWithLimits(WikiPageCategoryDboExample example);
}