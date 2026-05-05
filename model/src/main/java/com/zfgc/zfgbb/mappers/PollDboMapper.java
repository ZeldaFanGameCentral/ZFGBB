package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240810208-04:00", comments="Source Table: zfgbb.poll")
    long countByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240829527-04:00", comments="Source Table: zfgbb.poll")
    int deleteByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240845247-04:00", comments="Source Table: zfgbb.poll")
    int deleteByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240858706-04:00", comments="Source Table: zfgbb.poll")
    int insert(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240872666-04:00", comments="Source Table: zfgbb.poll")
    int insertSelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240890015-04:00", comments="Source Table: zfgbb.poll")
    List<PollDbo> selectByExample(PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240906315-04:00", comments="Source Table: zfgbb.poll")
    PollDbo selectByPrimaryKey(Integer pollId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240923494-04:00", comments="Source Table: zfgbb.poll")
    int updateByExampleSelective(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240941594-04:00", comments="Source Table: zfgbb.poll")
    int updateByExample(@Param("row") PollDbo row, @Param("example") PollDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240982982-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKeySelective(PollDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.241012261-04:00", comments="Source Table: zfgbb.poll")
    int updateByPrimaryKey(PollDbo row);

    List<PollDbo> selectByExampleWithLimits(PollDboExample example);
}