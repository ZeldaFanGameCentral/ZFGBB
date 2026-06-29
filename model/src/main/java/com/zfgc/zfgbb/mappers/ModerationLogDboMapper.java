package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dbo.ModerationLogDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ModerationLogDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73968531-04:00", comments="Source Table: zfgbb.moderation_log")
    long countByExample(ModerationLogDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73971691-04:00", comments="Source Table: zfgbb.moderation_log")
    int deleteByExample(ModerationLogDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739737549-04:00", comments="Source Table: zfgbb.moderation_log")
    int deleteByPrimaryKey(Integer moderationLogId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739752468-04:00", comments="Source Table: zfgbb.moderation_log")
    int insert(ModerationLogDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739767708-04:00", comments="Source Table: zfgbb.moderation_log")
    int insertSelective(ModerationLogDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739792377-04:00", comments="Source Table: zfgbb.moderation_log")
    List<ModerationLogDbo> selectByExample(ModerationLogDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739814846-04:00", comments="Source Table: zfgbb.moderation_log")
    ModerationLogDbo selectByPrimaryKey(Integer moderationLogId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739837046-04:00", comments="Source Table: zfgbb.moderation_log")
    int updateByExampleSelective(@Param("row") ModerationLogDbo row, @Param("example") ModerationLogDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739860135-04:00", comments="Source Table: zfgbb.moderation_log")
    int updateByExample(@Param("row") ModerationLogDbo row, @Param("example") ModerationLogDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739885324-04:00", comments="Source Table: zfgbb.moderation_log")
    int updateByPrimaryKeySelective(ModerationLogDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739910274-04:00", comments="Source Table: zfgbb.moderation_log")
    int updateByPrimaryKey(ModerationLogDbo row);

    List<ModerationLogDbo> selectByExampleWithLimits(ModerationLogDboExample example);
}