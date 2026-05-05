package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageHistoryDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237209102-04:00", comments="Source Table: zfgbb.message_history")
    long countByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237232111-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237248601-04:00", comments="Source Table: zfgbb.message_history")
    int deleteByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.23726417-04:00", comments="Source Table: zfgbb.message_history")
    int insert(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.23727739-04:00", comments="Source Table: zfgbb.message_history")
    int insertSelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237293709-04:00", comments="Source Table: zfgbb.message_history")
    List<MessageHistoryDbo> selectByExample(MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237311949-04:00", comments="Source Table: zfgbb.message_history")
    MessageHistoryDbo selectByPrimaryKey(Integer messageHistoryId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237329398-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExampleSelective(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237350628-04:00", comments="Source Table: zfgbb.message_history")
    int updateByExample(@Param("row") MessageHistoryDbo row, @Param("example") MessageHistoryDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237375407-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKeySelective(MessageHistoryDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.237398286-04:00", comments="Source Table: zfgbb.message_history")
    int updateByPrimaryKey(MessageHistoryDbo row);

    List<MessageHistoryDbo> selectByExampleWithLimits(MessageHistoryDboExample example);
}