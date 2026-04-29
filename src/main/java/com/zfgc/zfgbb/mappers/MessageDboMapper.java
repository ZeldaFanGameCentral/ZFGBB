package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361348944-04:00", comments="Source Table: zfgbb.message")
    long countByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361366103-04:00", comments="Source Table: zfgbb.message")
    int deleteByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361378873-04:00", comments="Source Table: zfgbb.message")
    int deleteByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361392302-04:00", comments="Source Table: zfgbb.message")
    int insert(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361403872-04:00", comments="Source Table: zfgbb.message")
    int insertSelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361417701-04:00", comments="Source Table: zfgbb.message")
    List<MessageDbo> selectByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361501799-04:00", comments="Source Table: zfgbb.message")
    MessageDbo selectByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361524348-04:00", comments="Source Table: zfgbb.message")
    int updateByExampleSelective(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361542287-04:00", comments="Source Table: zfgbb.message")
    int updateByExample(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361572686-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKeySelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.361597736-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKey(MessageDbo row);
}