package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449491933-04:00", comments="Source Table: zfgbb.poll_choice")
    long countByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449519852-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449541861-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44956806-04:00", comments="Source Table: zfgbb.poll_choice")
    int insert(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449591429-04:00", comments="Source Table: zfgbb.poll_choice")
    int insertSelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449623758-04:00", comments="Source Table: zfgbb.poll_choice")
    List<PollChoiceDbo> selectByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449653098-04:00", comments="Source Table: zfgbb.poll_choice")
    PollChoiceDbo selectByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449682757-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExampleSelective(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449715326-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExample(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449764374-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKeySelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.449804383-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKey(PollChoiceDbo row);

    List<PollChoiceDbo> selectByExampleWithLimits(PollChoiceDboExample example);
}