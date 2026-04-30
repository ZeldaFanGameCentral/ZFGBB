package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThreadDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.745984395-04:00", comments="Source Table: zfgbb.thread")
    long countByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746058722-04:00", comments="Source Table: zfgbb.thread")
    int deleteByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746112451-04:00", comments="Source Table: zfgbb.thread")
    int deleteByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746165069-04:00", comments="Source Table: zfgbb.thread")
    int insert(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746200448-04:00", comments="Source Table: zfgbb.thread")
    int insertSelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746254016-04:00", comments="Source Table: zfgbb.thread")
    List<ThreadDbo> selectByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746317093-04:00", comments="Source Table: zfgbb.thread")
    ThreadDbo selectByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746365282-04:00", comments="Source Table: zfgbb.thread")
    int updateByExampleSelective(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.74642086-04:00", comments="Source Table: zfgbb.thread")
    int updateByExample(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746565125-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKeySelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.746645822-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKey(ThreadDbo row);

    List<ThreadDbo> selectByExampleWithRange(ThreadDboExample example);
}