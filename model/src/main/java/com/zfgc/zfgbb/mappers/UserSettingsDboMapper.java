package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserSettingsDbo;
import com.zfgc.zfgbb.dbo.UserSettingsDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserSettingsDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033082391-04:00", comments="Source Table: zfgbb.user_settings")
    long countByExample(UserSettingsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.03310039-04:00", comments="Source Table: zfgbb.user_settings")
    int deleteByExample(UserSettingsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.03311471-04:00", comments="Source Table: zfgbb.user_settings")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.03312615-04:00", comments="Source Table: zfgbb.user_settings")
    int insert(UserSettingsDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033138159-04:00", comments="Source Table: zfgbb.user_settings")
    int insertSelective(UserSettingsDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033152849-04:00", comments="Source Table: zfgbb.user_settings")
    List<UserSettingsDbo> selectByExample(UserSettingsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033170069-04:00", comments="Source Table: zfgbb.user_settings")
    UserSettingsDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033184888-04:00", comments="Source Table: zfgbb.user_settings")
    int updateByExampleSelective(@Param("row") UserSettingsDbo row, @Param("example") UserSettingsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033201858-04:00", comments="Source Table: zfgbb.user_settings")
    int updateByExample(@Param("row") UserSettingsDbo row, @Param("example") UserSettingsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033221247-04:00", comments="Source Table: zfgbb.user_settings")
    int updateByPrimaryKeySelective(UserSettingsDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033241567-04:00", comments="Source Table: zfgbb.user_settings")
    int updateByPrimaryKey(UserSettingsDbo row);

    List<UserSettingsDbo> selectByExampleWithLimits(UserSettingsDboExample example);
}