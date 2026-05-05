package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDbo;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LatestMessageInThreadViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.2605967-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    long countByExample(LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.260611949-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int deleteByExample(LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.260628609-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int insert(LatestMessageInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.260640668-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int insertSelective(LatestMessageInThreadViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.260656378-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    List<LatestMessageInThreadViewDbo> selectByExample(LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.260673367-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int updateByExampleSelective(@Param("row") LatestMessageInThreadViewDbo row, @Param("example") LatestMessageInThreadViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.260689707-04:00", comments="Source Table: zfgbb.latest_message_in_thread_view")
    int updateByExample(@Param("row") LatestMessageInThreadViewDbo row, @Param("example") LatestMessageInThreadViewDboExample example);

    List<LatestMessageInThreadViewDbo> selectByExampleWithLimits(LatestMessageInThreadViewDboExample example);
}