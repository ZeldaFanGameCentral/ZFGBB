package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeModeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524886272-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    long countByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524901672-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524913331-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524925141-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insert(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.52493882-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insertSelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.52495226-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    List<BBCodeAttributeModeDbo> selectByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524966829-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    BBCodeAttributeModeDbo selectByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524983249-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExampleSelective(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524998528-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExample(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.525041067-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKeySelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.525073326-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKey(BBCodeAttributeModeDbo row);

    List<BBCodeAttributeModeDbo> selectByExampleWithLimits(BBCodeAttributeModeDboExample example);
}