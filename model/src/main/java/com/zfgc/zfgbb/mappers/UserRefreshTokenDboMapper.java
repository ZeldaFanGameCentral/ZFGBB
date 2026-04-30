package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRefreshTokenDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357262688-04:00", comments="Source Table: zfgbb.user_refresh_token")
    long countByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357279387-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357292247-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357304837-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insert(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357316556-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insertSelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357331536-04:00", comments="Source Table: zfgbb.user_refresh_token")
    List<UserRefreshTokenDbo> selectByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357346805-04:00", comments="Source Table: zfgbb.user_refresh_token")
    UserRefreshTokenDbo selectByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357360885-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExampleSelective(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357376824-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExample(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357396424-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKeySelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357416083-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKey(UserRefreshTokenDbo row);
}