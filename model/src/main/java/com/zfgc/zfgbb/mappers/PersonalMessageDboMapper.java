package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PersonalMessageDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonalMessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735336876-04:00", comments="Source Table: zfgbb.personal_message")
    long countByExample(PersonalMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735354845-04:00", comments="Source Table: zfgbb.personal_message")
    int deleteByExample(PersonalMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735371605-04:00", comments="Source Table: zfgbb.personal_message")
    int deleteByPrimaryKey(Integer personalMessageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735383064-04:00", comments="Source Table: zfgbb.personal_message")
    int insert(PersonalMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735394504-04:00", comments="Source Table: zfgbb.personal_message")
    int insertSelective(PersonalMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735407204-04:00", comments="Source Table: zfgbb.personal_message")
    List<PersonalMessageDbo> selectByExample(PersonalMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735424103-04:00", comments="Source Table: zfgbb.personal_message")
    PersonalMessageDbo selectByPrimaryKey(Integer personalMessageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735436923-04:00", comments="Source Table: zfgbb.personal_message")
    int updateByExampleSelective(@Param("row") PersonalMessageDbo row, @Param("example") PersonalMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735451942-04:00", comments="Source Table: zfgbb.personal_message")
    int updateByExample(@Param("row") PersonalMessageDbo row, @Param("example") PersonalMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735470232-04:00", comments="Source Table: zfgbb.personal_message")
    int updateByPrimaryKeySelective(PersonalMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.735486341-04:00", comments="Source Table: zfgbb.personal_message")
    int updateByPrimaryKey(PersonalMessageDbo row);

    List<PersonalMessageDbo> selectByExampleWithLimits(PersonalMessageDboExample example);
}