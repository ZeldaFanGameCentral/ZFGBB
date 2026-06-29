package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680758354-04:00", comments="Source Table: zfgbb.category")
    long countByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680783064-04:00", comments="Source Table: zfgbb.category")
    int deleteByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680803003-04:00", comments="Source Table: zfgbb.category")
    int deleteByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680820282-04:00", comments="Source Table: zfgbb.category")
    int insert(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680837092-04:00", comments="Source Table: zfgbb.category")
    int insertSelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680858381-04:00", comments="Source Table: zfgbb.category")
    List<CategoryDbo> selectByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680878771-04:00", comments="Source Table: zfgbb.category")
    CategoryDbo selectByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68089965-04:00", comments="Source Table: zfgbb.category")
    int updateByExampleSelective(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680922259-04:00", comments="Source Table: zfgbb.category")
    int updateByExample(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680950918-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKeySelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.680978568-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKey(CategoryDbo row);

    List<CategoryDbo> selectByExampleWithLimits(CategoryDboExample example);
}