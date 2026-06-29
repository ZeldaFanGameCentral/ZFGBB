package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ContentCollectionItemDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentCollectionItemDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731002451-04:00", comments="Source Table: zfgbb.content_collection_item")
    long countByExample(ContentCollectionItemDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73101705-04:00", comments="Source Table: zfgbb.content_collection_item")
    int deleteByExample(ContentCollectionItemDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73102888-04:00", comments="Source Table: zfgbb.content_collection_item")
    int deleteByPrimaryKey(Integer contentCollectionItemId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73103791-04:00", comments="Source Table: zfgbb.content_collection_item")
    int insert(ContentCollectionItemDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731047889-04:00", comments="Source Table: zfgbb.content_collection_item")
    int insertSelective(ContentCollectionItemDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731060349-04:00", comments="Source Table: zfgbb.content_collection_item")
    List<ContentCollectionItemDbo> selectByExample(ContentCollectionItemDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731074588-04:00", comments="Source Table: zfgbb.content_collection_item")
    ContentCollectionItemDbo selectByPrimaryKey(Integer contentCollectionItemId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731087348-04:00", comments="Source Table: zfgbb.content_collection_item")
    int updateByExampleSelective(@Param("row") ContentCollectionItemDbo row, @Param("example") ContentCollectionItemDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731102248-04:00", comments="Source Table: zfgbb.content_collection_item")
    int updateByExample(@Param("row") ContentCollectionItemDbo row, @Param("example") ContentCollectionItemDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731123027-04:00", comments="Source Table: zfgbb.content_collection_item")
    int updateByPrimaryKeySelective(ContentCollectionItemDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731138996-04:00", comments="Source Table: zfgbb.content_collection_item")
    int updateByPrimaryKey(ContentCollectionItemDbo row);

    List<ContentCollectionItemDbo> selectByExampleWithLimits(ContentCollectionItemDboExample example);
}