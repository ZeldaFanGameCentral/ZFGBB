package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRefreshTokenDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.678842484-04:00", comments="Source Table: zfgbb.user_refresh_token")
    long countByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.678870733-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.678904482-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.678928241-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insert(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.678947021-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insertSelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.67896834-04:00", comments="Source Table: zfgbb.user_refresh_token")
    List<UserRefreshTokenDbo> selectByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.678994129-04:00", comments="Source Table: zfgbb.user_refresh_token")
    UserRefreshTokenDbo selectByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.679026528-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExampleSelective(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.679052447-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExample(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.679085366-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKeySelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.679117145-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKey(UserRefreshTokenDbo row);

    List<UserRefreshTokenDbo> selectByExampleWithLimits(UserRefreshTokenDboExample example);
}