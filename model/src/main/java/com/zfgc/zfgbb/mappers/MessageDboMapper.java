package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685834367-04:00", comments="Source Table: zfgbb.message")
    long countByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685853166-04:00", comments="Source Table: zfgbb.message")
    int deleteByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685872845-04:00", comments="Source Table: zfgbb.message")
    int deleteByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685887205-04:00", comments="Source Table: zfgbb.message")
    int insert(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685902114-04:00", comments="Source Table: zfgbb.message")
    int insertSelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685920534-04:00", comments="Source Table: zfgbb.message")
    List<MessageDbo> selectByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685939563-04:00", comments="Source Table: zfgbb.message")
    MessageDbo selectByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685959153-04:00", comments="Source Table: zfgbb.message")
    int updateByExampleSelective(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.685980952-04:00", comments="Source Table: zfgbb.message")
    int updateByExample(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.686005021-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKeySelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68604265-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKey(MessageDbo row);

    List<MessageDbo> selectByExampleWithLimits(MessageDboExample example);
}