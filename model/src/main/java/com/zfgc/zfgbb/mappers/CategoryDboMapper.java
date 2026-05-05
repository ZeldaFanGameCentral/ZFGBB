package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230234103-04:00", comments="Source Table: zfgbb.category")
    long countByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230293782-04:00", comments="Source Table: zfgbb.category")
    int deleteByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230316461-04:00", comments="Source Table: zfgbb.category")
    int deleteByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.23033332-04:00", comments="Source Table: zfgbb.category")
    int insert(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.23035056-04:00", comments="Source Table: zfgbb.category")
    int insertSelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230370399-04:00", comments="Source Table: zfgbb.category")
    List<CategoryDbo> selectByExample(CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230394528-04:00", comments="Source Table: zfgbb.category")
    CategoryDbo selectByPrimaryKey(Integer categoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230417258-04:00", comments="Source Table: zfgbb.category")
    int updateByExampleSelective(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230439217-04:00", comments="Source Table: zfgbb.category")
    int updateByExample(@Param("row") CategoryDbo row, @Param("example") CategoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230487695-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKeySelective(CategoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.230541554-04:00", comments="Source Table: zfgbb.category")
    int updateByPrimaryKey(CategoryDbo row);

    List<CategoryDbo> selectByExampleWithLimits(CategoryDboExample example);
}