package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523774149-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    long countByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523804528-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523820127-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523832837-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insert(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523842387-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insertSelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523857226-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    List<BBCodeAttributeDbo> selectByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523871546-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    BBCodeAttributeDbo selectByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523886895-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExampleSelective(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523900695-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExample(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523941583-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKeySelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523968442-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKey(BBCodeAttributeDbo row);

    List<BBCodeAttributeDbo> selectByExampleWithLimits(BBCodeAttributeDboExample example);
}