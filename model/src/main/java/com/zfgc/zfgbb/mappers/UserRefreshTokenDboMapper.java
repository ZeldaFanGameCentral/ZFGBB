package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRefreshTokenDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434218151-04:00", comments="Source Table: zfgbb.user_refresh_token")
    long countByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43424483-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434287748-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434307638-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insert(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434324887-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insertSelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434344567-04:00", comments="Source Table: zfgbb.user_refresh_token")
    List<UserRefreshTokenDbo> selectByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434367826-04:00", comments="Source Table: zfgbb.user_refresh_token")
    UserRefreshTokenDbo selectByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434387595-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExampleSelective(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434421074-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExample(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434459443-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKeySelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434491622-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKey(UserRefreshTokenDbo row);

    List<UserRefreshTokenDbo> selectByExampleWithLimits(UserRefreshTokenDboExample example);
}