package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.435898447-04:00", comments="Source Table: zfgbb.category")
    long countByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.435942105-04:00", comments="Source Table: zfgbb.category")
    int deleteByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.435960395-04:00", comments="Source Table: zfgbb.category")
    int deleteByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.435977794-04:00", comments="Source Table: zfgbb.category")
    int insert(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.435992624-04:00", comments="Source Table: zfgbb.category")
    int insertSelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.436011403-04:00", comments="Source Table: zfgbb.category")
    List<CategoryDbo> selectByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.436075161-04:00", comments="Source Table: zfgbb.category")
    CategoryDbo selectByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.436094681-04:00", comments="Source Table: zfgbb.category")
    int updateByExampleSelective(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43611664-04:00", comments="Source Table: zfgbb.category")
    int updateByExample(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.436160278-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKeySelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.436196967-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKey(CategoryDbo row);

    List<CategoryDbo> selectByExampleWithLimits(CategoryDboExample example);
}