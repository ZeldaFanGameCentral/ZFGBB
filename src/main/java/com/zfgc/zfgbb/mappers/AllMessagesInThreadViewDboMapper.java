package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDbo;
import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AllMessagesInThreadViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379460729-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    long countByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379474468-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int deleteByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379485868-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insert(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379494368-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insertSelective(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379504357-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    List<AllMessagesInThreadViewDbo> selectByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379518577-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExampleSelective(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379530656-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExample(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);
}