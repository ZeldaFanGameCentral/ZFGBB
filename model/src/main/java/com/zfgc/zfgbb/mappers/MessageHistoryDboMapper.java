package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageHistoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509380605-04:00", comments="Source Table: zfgbb.message_history")
    long countByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509400094-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509415093-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509427883-04:00", comments="Source Table: zfgbb.message_history")
    int insert(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509443273-04:00", comments="Source Table: zfgbb.message_history")
    int insertSelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509459552-04:00", comments="Source Table: zfgbb.message_history")
    List<MessageHistoryDbo> selectByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509477881-04:00", comments="Source Table: zfgbb.message_history")
    MessageHistoryDbo selectByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509493771-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExampleSelective(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50951243-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExample(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509536279-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKeySelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.509560569-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKey(MessageHistoryDbo row);

    List<MessageHistoryDbo> selectByExampleWithLimits(MessageHistoryDboExample example);
}