package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365480398-04:00", comments="Source Table: zfgbb.poll")
    long countByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365497577-04:00", comments="Source Table: zfgbb.poll")
    int deleteByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365513687-04:00", comments="Source Table: zfgbb.poll")
    int deleteByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365532706-04:00", comments="Source Table: zfgbb.poll")
    int insert(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365569325-04:00", comments="Source Table: zfgbb.poll")
    int insertSelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365592324-04:00", comments="Source Table: zfgbb.poll")
    List<PollDbo> selectByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365614523-04:00", comments="Source Table: zfgbb.poll")
    PollDbo selectByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365628973-04:00", comments="Source Table: zfgbb.poll")
    int updateByExampleSelective(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365644802-04:00", comments="Source Table: zfgbb.poll")
    int updateByExample(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365664482-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKeySelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.365682761-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKey(PollDbo row);
}