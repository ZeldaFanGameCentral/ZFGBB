package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRefreshTokenDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228050603-04:00", comments="Source Table: zfgbb.user_refresh_token")
    long countByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228084102-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22813399-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228163669-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insert(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228194528-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insertSelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228228397-04:00", comments="Source Table: zfgbb.user_refresh_token")
    List<UserRefreshTokenDbo> selectByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228279626-04:00", comments="Source Table: zfgbb.user_refresh_token")
    UserRefreshTokenDbo selectByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228316834-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExampleSelective(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228356493-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExample(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.228399242-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKeySelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22844834-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKey(UserRefreshTokenDbo row);

    List<UserRefreshTokenDbo> selectByExampleWithLimits(UserRefreshTokenDboExample example);
}