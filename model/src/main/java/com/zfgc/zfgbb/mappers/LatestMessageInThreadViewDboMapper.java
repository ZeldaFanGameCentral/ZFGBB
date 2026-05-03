package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDbo;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LatestMessageInThreadViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.529749182-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    long countByExample(LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.529762741-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int deleteByExample(LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.529776401-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int insert(LatestMessageInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.529788-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int insertSelective(LatestMessageInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.52980127-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    List<LatestMessageInThreadViewDbo> selectByExample(LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.529821059-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int updateByExampleSelective(@Param("row") LatestMessageInThreadViewDbo row, @Param("example") LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.529840149-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int updateByExample(@Param("row") LatestMessageInThreadViewDbo row, @Param("example") LatestMessageInThreadViewDboExample example);

    List<LatestMessageInThreadViewDbo> selectByExampleWithLimits(LatestMessageInThreadViewDboExample example);
}