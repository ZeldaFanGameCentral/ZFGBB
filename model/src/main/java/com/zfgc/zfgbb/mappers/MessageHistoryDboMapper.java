package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageHistoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68733119-04:00", comments="Source Table: zfgbb.message_history")
    long countByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687367449-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687384808-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687400668-04:00", comments="Source Table: zfgbb.message_history")
    int insert(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687416207-04:00", comments="Source Table: zfgbb.message_history")
    int insertSelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687434367-04:00", comments="Source Table: zfgbb.message_history")
    List<MessageHistoryDbo> selectByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687454576-04:00", comments="Source Table: zfgbb.message_history")
    MessageHistoryDbo selectByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687474715-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExampleSelective(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687500175-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExample(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687532444-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKeySelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.687559053-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKey(MessageHistoryDbo row);

    List<MessageHistoryDbo> selectByExampleWithLimits(MessageHistoryDboExample example);
}