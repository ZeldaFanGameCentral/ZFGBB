package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692706383-04:00", comments="Source Table: zfgbb.poll_choice")
    long countByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692725352-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692741152-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692754541-04:00", comments="Source Table: zfgbb.poll_choice")
    int insert(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692768631-04:00", comments="Source Table: zfgbb.poll_choice")
    int insertSelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69278524-04:00", comments="Source Table: zfgbb.poll_choice")
    List<PollChoiceDbo> selectByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69280212-04:00", comments="Source Table: zfgbb.poll_choice")
    PollChoiceDbo selectByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692820839-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExampleSelective(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692840958-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExample(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692862888-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKeySelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692886257-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKey(PollChoiceDbo row);

    List<PollChoiceDbo> selectByExampleWithLimits(PollChoiceDboExample example);
}