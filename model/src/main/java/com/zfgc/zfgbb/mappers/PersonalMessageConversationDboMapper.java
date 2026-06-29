package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PersonalMessageConversationDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageConversationDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonalMessageConversationDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734269839-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    long countByExample(PersonalMessageConversationDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734284519-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int deleteByExample(PersonalMessageConversationDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734306588-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int deleteByPrimaryKey(Integer personalMessageConversationId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734318038-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int insert(PersonalMessageConversationDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734328197-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int insertSelective(PersonalMessageConversationDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734343137-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    List<PersonalMessageConversationDbo> selectByExample(PersonalMessageConversationDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734358396-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    PersonalMessageConversationDbo selectByPrimaryKey(Integer personalMessageConversationId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734372556-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int updateByExampleSelective(@Param("row") PersonalMessageConversationDbo row, @Param("example") PersonalMessageConversationDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734388415-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int updateByExample(@Param("row") PersonalMessageConversationDbo row, @Param("example") PersonalMessageConversationDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734405295-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int updateByPrimaryKeySelective(PersonalMessageConversationDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.734421724-04:00", comments="Source Table: zfgbb.personal_message_conversation")
    int updateByPrimaryKey(PersonalMessageConversationDbo row);

    List<PersonalMessageConversationDbo> selectByExampleWithLimits(PersonalMessageConversationDboExample example);
}