package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235777468-04:00", comments="Source Table: zfgbb.message")
    long countByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235798247-04:00", comments="Source Table: zfgbb.message")
    int deleteByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235813696-04:00", comments="Source Table: zfgbb.message")
    int deleteByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235829056-04:00", comments="Source Table: zfgbb.message")
    int insert(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235842196-04:00", comments="Source Table: zfgbb.message")
    int insertSelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235876974-04:00", comments="Source Table: zfgbb.message")
    List<MessageDbo> selectByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235908223-04:00", comments="Source Table: zfgbb.message")
    MessageDbo selectByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235927253-04:00", comments="Source Table: zfgbb.message")
    int updateByExampleSelective(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.235953242-04:00", comments="Source Table: zfgbb.message")
    int updateByExample(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.23602919-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKeySelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.236063638-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKey(MessageDbo row);

    List<MessageDbo> selectByExampleWithLimits(MessageDboExample example);
}