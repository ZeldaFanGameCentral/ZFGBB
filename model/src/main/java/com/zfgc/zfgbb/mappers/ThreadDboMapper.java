package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThreadDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684402601-04:00", comments="Source Table: zfgbb.thread")
    long countByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68444768-04:00", comments="Source Table: zfgbb.thread")
    int deleteByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684467719-04:00", comments="Source Table: zfgbb.thread")
    int deleteByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684483818-04:00", comments="Source Table: zfgbb.thread")
    int insert(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684501988-04:00", comments="Source Table: zfgbb.thread")
    int insertSelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684524037-04:00", comments="Source Table: zfgbb.thread")
    List<ThreadDbo> selectByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684543187-04:00", comments="Source Table: zfgbb.thread")
    ThreadDbo selectByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684561476-04:00", comments="Source Table: zfgbb.thread")
    int updateByExampleSelective(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684580635-04:00", comments="Source Table: zfgbb.thread")
    int updateByExample(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684606335-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKeySelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.684630914-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKey(ThreadDbo row);

    List<ThreadDbo> selectByExampleWithLimits(ThreadDboExample example);
}