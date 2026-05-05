package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeModeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255145003-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    long countByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255159722-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255172592-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255182832-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insert(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255193721-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insertSelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255208981-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    List<BBCodeAttributeModeDbo> selectByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.2552244-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    BBCodeAttributeModeDbo selectByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25523822-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExampleSelective(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255254129-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExample(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255303408-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKeySelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255331637-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKey(BBCodeAttributeModeDbo row);

    List<BBCodeAttributeModeDbo> selectByExampleWithLimits(BBCodeAttributeModeDboExample example);
}