package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254010619-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    long countByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254026308-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254038978-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254049098-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insert(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254060297-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insertSelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254074717-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    List<BBCodeAttributeDbo> selectByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254089526-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    BBCodeAttributeDbo selectByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254103246-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExampleSelective(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254120555-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExample(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254140005-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKeySelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254192503-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKey(BBCodeAttributeDbo row);

    List<BBCodeAttributeDbo> selectByExampleWithLimits(BBCodeAttributeDboExample example);
}