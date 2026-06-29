package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.RecentActivityViewDbo;
import com.zfgc.zfgbb.dbo.RecentActivityViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecentActivityViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196551206-04:00", comments="Source Table: zfgbb.recent_activity_view")
    long countByExample(RecentActivityViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196562065-04:00", comments="Source Table: zfgbb.recent_activity_view")
    int deleteByExample(RecentActivityViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196570965-04:00", comments="Source Table: zfgbb.recent_activity_view")
    int insert(RecentActivityViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196590434-04:00", comments="Source Table: zfgbb.recent_activity_view")
    int insertSelective(RecentActivityViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196604244-04:00", comments="Source Table: zfgbb.recent_activity_view")
    List<RecentActivityViewDbo> selectByExample(RecentActivityViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196615674-04:00", comments="Source Table: zfgbb.recent_activity_view")
    int updateByExampleSelective(@Param("row") RecentActivityViewDbo row, @Param("example") RecentActivityViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196625763-04:00", comments="Source Table: zfgbb.recent_activity_view")
    int updateByExample(@Param("row") RecentActivityViewDbo row, @Param("example") RecentActivityViewDboExample example);

    List<RecentActivityViewDbo> selectByExampleWithLimits(RecentActivityViewDboExample example);
}