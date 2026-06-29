package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDbo;
import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AllMessagesInThreadViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748415089-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    long countByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748519286-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int deleteByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748549305-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insert(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748570444-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insertSelective(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748595133-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    List<AllMessagesInThreadViewDbo> selectByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748622892-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExampleSelective(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74870283-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExample(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);

    List<AllMessagesInThreadViewDbo> selectByExampleWithLimits(AllMessagesInThreadViewDboExample example);
}