package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDbo;
import com.zfgc.zfgbb.dbo.AllMessagesInThreadViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AllMessagesInThreadViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.262942685-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    long countByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.262961975-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int deleteByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.262978684-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insert(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.262990894-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int insertSelective(AllMessagesInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.263004223-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    List<AllMessagesInThreadViewDbo> selectByExample(AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.263021653-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExampleSelective(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.263037052-04:00", comments="Source Table: zfgbb.all_messages_in_thread_view")
    int updateByExample(@Param("row") AllMessagesInThreadViewDbo row, @Param("example") AllMessagesInThreadViewDboExample example);

    List<AllMessagesInThreadViewDbo> selectByExampleWithLimits(AllMessagesInThreadViewDboExample example);
}