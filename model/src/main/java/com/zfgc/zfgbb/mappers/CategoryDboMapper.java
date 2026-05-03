package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502511311-04:00", comments="Source Table: zfgbb.category")
    long countByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50255011-04:00", comments="Source Table: zfgbb.category")
    int deleteByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50256797-04:00", comments="Source Table: zfgbb.category")
    int deleteByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502582459-04:00", comments="Source Table: zfgbb.category")
    int insert(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502597229-04:00", comments="Source Table: zfgbb.category")
    int insertSelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502614818-04:00", comments="Source Table: zfgbb.category")
    List<CategoryDbo> selectByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502634857-04:00", comments="Source Table: zfgbb.category")
    CategoryDbo selectByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502654637-04:00", comments="Source Table: zfgbb.category")
    int updateByExampleSelective(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502674636-04:00", comments="Source Table: zfgbb.category")
    int updateByExample(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502711445-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKeySelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.502745124-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKey(CategoryDbo row);

    List<CategoryDbo> selectByExampleWithLimits(CategoryDboExample example);
}