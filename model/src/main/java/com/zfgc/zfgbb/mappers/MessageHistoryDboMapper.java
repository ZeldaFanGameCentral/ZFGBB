package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageHistoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.362883663-04:00", comments="Source Table: zfgbb.message_history")
    long countByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.362929182-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.362944941-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.362957031-04:00", comments="Source Table: zfgbb.message_history")
    int insert(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36296856-04:00", comments="Source Table: zfgbb.message_history")
    int insertSelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36298623-04:00", comments="Source Table: zfgbb.message_history")
    List<MessageHistoryDbo> selectByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.363000929-04:00", comments="Source Table: zfgbb.message_history")
    MessageHistoryDbo selectByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.363014119-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExampleSelective(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.363057928-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExample(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.363085107-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKeySelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.363114036-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKey(MessageHistoryDbo row);
}