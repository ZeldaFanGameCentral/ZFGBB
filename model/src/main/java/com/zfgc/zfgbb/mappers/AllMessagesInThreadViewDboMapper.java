package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDbo;
import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AllMessagesInThreadViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.53131193-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    long countByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531329169-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int deleteByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531343029-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insert(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531355768-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insertSelective(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531369528-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    List<AllMessagesInThreadViewDbo> selectByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531399527-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExampleSelective(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531416276-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExample(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);

    List<AllMessagesInThreadViewDbo> selectByExampleWithLimits(AllMessagesInThreadViewDboExample example);
}