package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691284657-04:00", comments="Source Table: zfgbb.poll")
    long countByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691315566-04:00", comments="Source Table: zfgbb.poll")
    int deleteByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691332595-04:00", comments="Source Table: zfgbb.poll")
    int deleteByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691346875-04:00", comments="Source Table: zfgbb.poll")
    int insert(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691361354-04:00", comments="Source Table: zfgbb.poll")
    int insertSelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691378354-04:00", comments="Source Table: zfgbb.poll")
    List<PollDbo> selectByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691397263-04:00", comments="Source Table: zfgbb.poll")
    PollDbo selectByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691417803-04:00", comments="Source Table: zfgbb.poll")
    int updateByExampleSelective(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691436642-04:00", comments="Source Table: zfgbb.poll")
    int updateByExample(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691461631-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKeySelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691486511-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKey(PollDbo row);

    List<PollDbo> selectByExampleWithLimits(PollDboExample example);
}