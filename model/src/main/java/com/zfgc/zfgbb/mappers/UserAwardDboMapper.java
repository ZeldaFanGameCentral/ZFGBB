package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserAwardDbo;
import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAwardDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704203335-04:00", comments="Source Table: zfgbb.user_award")
    long countByExample(UserAwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704221824-04:00", comments="Source Table: zfgbb.user_award")
    int deleteByExample(UserAwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704233254-04:00", comments="Source Table: zfgbb.user_award")
    int deleteByPrimaryKey(Integer userAwardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704244714-04:00", comments="Source Table: zfgbb.user_award")
    int insert(UserAwardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704255933-04:00", comments="Source Table: zfgbb.user_award")
    int insertSelective(UserAwardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704269743-04:00", comments="Source Table: zfgbb.user_award")
    List<UserAwardDbo> selectByExample(UserAwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704284132-04:00", comments="Source Table: zfgbb.user_award")
    UserAwardDbo selectByPrimaryKey(Integer userAwardId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704313141-04:00", comments="Source Table: zfgbb.user_award")
    int updateByExampleSelective(@Param("row") UserAwardDbo row, @Param("example") UserAwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70434286-04:00", comments="Source Table: zfgbb.user_award")
    int updateByExample(@Param("row") UserAwardDbo row, @Param("example") UserAwardDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70436529-04:00", comments="Source Table: zfgbb.user_award")
    int updateByPrimaryKeySelective(UserAwardDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704384989-04:00", comments="Source Table: zfgbb.user_award")
    int updateByPrimaryKey(UserAwardDbo row);

    List<UserAwardDbo> selectByExampleWithLimits(UserAwardDboExample example);
}