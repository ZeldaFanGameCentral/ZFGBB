package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447319302-04:00", comments="Source Table: zfgbb.poll")
    long countByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447347421-04:00", comments="Source Table: zfgbb.poll")
    int deleteByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44737022-04:00", comments="Source Table: zfgbb.poll")
    int deleteByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44739583-04:00", comments="Source Table: zfgbb.poll")
    int insert(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447417549-04:00", comments="Source Table: zfgbb.poll")
    int insertSelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447447338-04:00", comments="Source Table: zfgbb.poll")
    List<PollDbo> selectByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447475047-04:00", comments="Source Table: zfgbb.poll")
    PollDbo selectByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447501506-04:00", comments="Source Table: zfgbb.poll")
    int updateByExampleSelective(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447535235-04:00", comments="Source Table: zfgbb.poll")
    int updateByExample(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447577054-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKeySelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447619143-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKey(PollDbo row);

    List<PollDbo> selectByExampleWithLimits(PollDboExample example);
}