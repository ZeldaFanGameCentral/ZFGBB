package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRefreshTokenDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.500899045-04:00", comments="Source Table: zfgbb.user_refresh_token")
    long countByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.500924154-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.500950733-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int deleteByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.500971092-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insert(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.500988452-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int insertSelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.501007451-04:00", comments="Source Table: zfgbb.user_refresh_token")
    List<UserRefreshTokenDbo> selectByExample(UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50103464-04:00", comments="Source Table: zfgbb.user_refresh_token")
    UserRefreshTokenDbo selectByPrimaryKey(Integer userRefreshTokenId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.501069099-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExampleSelective(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.501092488-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByExample(@Param("row") UserRefreshTokenDbo row, @Param("example") UserRefreshTokenDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.501137027-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKeySelective(UserRefreshTokenDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.501169956-04:00", comments="Source Table: zfgbb.user_refresh_token")
    int updateByPrimaryKey(UserRefreshTokenDbo row);

    List<UserRefreshTokenDbo> selectByExampleWithLimits(UserRefreshTokenDboExample example);
}