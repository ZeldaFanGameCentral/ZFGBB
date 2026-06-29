package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.NotificationSubscriptionDbo;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NotificationSubscriptionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737361923-04:00", comments="Source Table: zfgbb.notification_subscription")
    long countByExample(NotificationSubscriptionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737378092-04:00", comments="Source Table: zfgbb.notification_subscription")
    int deleteByExample(NotificationSubscriptionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737388442-04:00", comments="Source Table: zfgbb.notification_subscription")
    int deleteByPrimaryKey(Integer notificationSubscriptionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737398722-04:00", comments="Source Table: zfgbb.notification_subscription")
    int insert(NotificationSubscriptionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737408201-04:00", comments="Source Table: zfgbb.notification_subscription")
    int insertSelective(NotificationSubscriptionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737420011-04:00", comments="Source Table: zfgbb.notification_subscription")
    List<NotificationSubscriptionDbo> selectByExample(NotificationSubscriptionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737432431-04:00", comments="Source Table: zfgbb.notification_subscription")
    NotificationSubscriptionDbo selectByPrimaryKey(Integer notificationSubscriptionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73744594-04:00", comments="Source Table: zfgbb.notification_subscription")
    int updateByExampleSelective(@Param("row") NotificationSubscriptionDbo row, @Param("example") NotificationSubscriptionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73746026-04:00", comments="Source Table: zfgbb.notification_subscription")
    int updateByExample(@Param("row") NotificationSubscriptionDbo row, @Param("example") NotificationSubscriptionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737476309-04:00", comments="Source Table: zfgbb.notification_subscription")
    int updateByPrimaryKeySelective(NotificationSubscriptionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737491179-04:00", comments="Source Table: zfgbb.notification_subscription")
    int updateByPrimaryKey(NotificationSubscriptionDbo row);

    List<NotificationSubscriptionDbo> selectByExampleWithLimits(NotificationSubscriptionDboExample example);
}