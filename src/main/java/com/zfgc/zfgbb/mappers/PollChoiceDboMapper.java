package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.366988038-04:00", comments="Source Table: zfgbb.poll_choice")
    long countByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367003768-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367016168-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367027317-04:00", comments="Source Table: zfgbb.poll_choice")
    int insert(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367037077-04:00", comments="Source Table: zfgbb.poll_choice")
    int insertSelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367050016-04:00", comments="Source Table: zfgbb.poll_choice")
    List<PollChoiceDbo> selectByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367062456-04:00", comments="Source Table: zfgbb.poll_choice")
    PollChoiceDbo selectByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367075446-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExampleSelective(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367088845-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExample(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367105495-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKeySelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367133444-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKey(PollChoiceDbo row);
}