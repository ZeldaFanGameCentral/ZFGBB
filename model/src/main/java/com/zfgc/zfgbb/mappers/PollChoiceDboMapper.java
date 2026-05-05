package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242525993-04:00", comments="Source Table: zfgbb.poll_choice")
    long countByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242547703-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242573612-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242587422-04:00", comments="Source Table: zfgbb.poll_choice")
    int insert(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242601621-04:00", comments="Source Table: zfgbb.poll_choice")
    int insertSelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242617801-04:00", comments="Source Table: zfgbb.poll_choice")
    List<PollChoiceDbo> selectByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242634-04:00", comments="Source Table: zfgbb.poll_choice")
    PollChoiceDbo selectByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242654619-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExampleSelective(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242673219-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExample(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242694948-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKeySelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242717137-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKey(PollChoiceDbo row);

    List<PollChoiceDbo> selectByExampleWithLimits(PollChoiceDboExample example);
}