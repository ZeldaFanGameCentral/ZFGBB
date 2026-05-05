package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThreadDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234158609-04:00", comments="Source Table: zfgbb.thread")
    long countByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234205707-04:00", comments="Source Table: zfgbb.thread")
    int deleteByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234240896-04:00", comments="Source Table: zfgbb.thread")
    int deleteByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234257596-04:00", comments="Source Table: zfgbb.thread")
    int insert(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234273915-04:00", comments="Source Table: zfgbb.thread")
    int insertSelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234293555-04:00", comments="Source Table: zfgbb.thread")
    List<ThreadDbo> selectByExample(ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234313314-04:00", comments="Source Table: zfgbb.thread")
    ThreadDbo selectByPrimaryKey(Integer threadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234333693-04:00", comments="Source Table: zfgbb.thread")
    int updateByExampleSelective(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234354703-04:00", comments="Source Table: zfgbb.thread")
    int updateByExample(@Param("row") ThreadDbo row, @Param("example") ThreadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234381092-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKeySelective(ThreadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.234409081-04:00", comments="Source Table: zfgbb.thread")
    int updateByPrimaryKey(ThreadDbo row);

    List<ThreadDbo> selectByExampleWithLimits(ThreadDboExample example);
}