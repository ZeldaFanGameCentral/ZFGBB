package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512646957-04:00", comments="Source Table: zfgbb.poll")
    long countByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512664946-04:00", comments="Source Table: zfgbb.poll")
    int deleteByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512680725-04:00", comments="Source Table: zfgbb.poll")
    int deleteByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512699025-04:00", comments="Source Table: zfgbb.poll")
    int insert(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512711854-04:00", comments="Source Table: zfgbb.poll")
    int insertSelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512727404-04:00", comments="Source Table: zfgbb.poll")
    List<PollDbo> selectByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512745663-04:00", comments="Source Table: zfgbb.poll")
    PollDbo selectByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512762153-04:00", comments="Source Table: zfgbb.poll")
    int updateByExampleSelective(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512780572-04:00", comments="Source Table: zfgbb.poll")
    int updateByExample(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512804281-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKeySelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512830211-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKey(PollDbo row);

    List<PollDbo> selectByExampleWithLimits(PollDboExample example);
}