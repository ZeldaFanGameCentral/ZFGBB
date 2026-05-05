package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThreadDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439327857-04:00", comments="Source Table: zfgbb.thread")
    long countByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439374796-04:00", comments="Source Table: zfgbb.thread")
    int deleteByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439418664-04:00", comments="Source Table: zfgbb.thread")
    int deleteByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439439024-04:00", comments="Source Table: zfgbb.thread")
    int insert(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439453283-04:00", comments="Source Table: zfgbb.thread")
    int insertSelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439476823-04:00", comments="Source Table: zfgbb.thread")
    List<ThreadDbo> selectByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439498872-04:00", comments="Source Table: zfgbb.thread")
    ThreadDbo selectByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439516971-04:00", comments="Source Table: zfgbb.thread")
    int updateByExampleSelective(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439538861-04:00", comments="Source Table: zfgbb.thread")
    int updateByExample(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43956589-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKeySelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.439590779-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKey(ThreadDbo row);

    List<ThreadDbo> selectByExampleWithLimits(ThreadDboExample example);
}