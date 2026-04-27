package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358212077-04:00", comments="Source Table: zfgbb.category")
    long countByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358228396-04:00", comments="Source Table: zfgbb.category")
    int deleteByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358240936-04:00", comments="Source Table: zfgbb.category")
    int deleteByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358252825-04:00", comments="Source Table: zfgbb.category")
    int insert(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358264085-04:00", comments="Source Table: zfgbb.category")
    int insertSelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358278065-04:00", comments="Source Table: zfgbb.category")
    List<CategoryDbo> selectByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358292264-04:00", comments="Source Table: zfgbb.category")
    CategoryDbo selectByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358306084-04:00", comments="Source Table: zfgbb.category")
    int updateByExampleSelective(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358321193-04:00", comments="Source Table: zfgbb.category")
    int updateByExample(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358345462-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKeySelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.358365002-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKey(CategoryDbo row);
}