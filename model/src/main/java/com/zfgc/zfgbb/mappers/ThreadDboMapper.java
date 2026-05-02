package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThreadDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506437722-04:00", comments="Source Table: zfgbb.thread")
    long countByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50648461-04:00", comments="Source Table: zfgbb.thread")
    int deleteByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50650286-04:00", comments="Source Table: zfgbb.thread")
    int deleteByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506516939-04:00", comments="Source Table: zfgbb.thread")
    int insert(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506531079-04:00", comments="Source Table: zfgbb.thread")
    int insertSelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506547528-04:00", comments="Source Table: zfgbb.thread")
    List<ThreadDbo> selectByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506564398-04:00", comments="Source Table: zfgbb.thread")
    ThreadDbo selectByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506583197-04:00", comments="Source Table: zfgbb.thread")
    int updateByExampleSelective(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506602156-04:00", comments="Source Table: zfgbb.thread")
    int updateByExample(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506625236-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKeySelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.506651245-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKey(ThreadDbo row);

    List<ThreadDbo> selectByExampleWithLimits(ThreadDboExample example);
}