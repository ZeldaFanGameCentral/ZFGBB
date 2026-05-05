package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageHistoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442589703-04:00", comments="Source Table: zfgbb.message_history")
    long countByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442611242-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442627122-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442642601-04:00", comments="Source Table: zfgbb.message_history")
    int insert(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442658501-04:00", comments="Source Table: zfgbb.message_history")
    int insertSelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44267598-04:00", comments="Source Table: zfgbb.message_history")
    List<MessageHistoryDbo> selectByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44269527-04:00", comments="Source Table: zfgbb.message_history")
    MessageHistoryDbo selectByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442714919-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExampleSelective(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442745378-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExample(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442772497-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKeySelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442800116-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKey(MessageHistoryDbo row);

    List<MessageHistoryDbo> selectByExampleWithLimits(MessageHistoryDboExample example);
}