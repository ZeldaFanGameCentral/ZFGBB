package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonalMessageRecipientDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736416372-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    long countByExample(PersonalMessageRecipientDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736432832-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int deleteByExample(PersonalMessageRecipientDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736442811-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int deleteByPrimaryKey(Integer personalMessageRecipientId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736452971-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int insert(PersonalMessageRecipientDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736462741-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int insertSelective(PersonalMessageRecipientDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73647538-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    List<PersonalMessageRecipientDbo> selectByExample(PersonalMessageRecipientDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73648844-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    PersonalMessageRecipientDbo selectByPrimaryKey(Integer personalMessageRecipientId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73650244-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int updateByExampleSelective(@Param("row") PersonalMessageRecipientDbo row, @Param("example") PersonalMessageRecipientDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736519579-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int updateByExample(@Param("row") PersonalMessageRecipientDbo row, @Param("example") PersonalMessageRecipientDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736567008-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int updateByPrimaryKeySelective(PersonalMessageRecipientDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.736584147-04:00", comments="Source Table: zfgbb.personal_message_recipient")
    int updateByPrimaryKey(PersonalMessageRecipientDbo row);

    List<PersonalMessageRecipientDbo> selectByExampleWithLimits(PersonalMessageRecipientDboExample example);
}