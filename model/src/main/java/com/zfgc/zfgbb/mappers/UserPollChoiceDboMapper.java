package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694213846-04:00", comments="Source Table: zfgbb.user_poll_choice")
    long countByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694235675-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694250384-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694317232-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insert(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69439773-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insertSelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694422309-04:00", comments="Source Table: zfgbb.user_poll_choice")
    List<UserPollChoiceDbo> selectByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694442448-04:00", comments="Source Table: zfgbb.user_poll_choice")
    UserPollChoiceDbo selectByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694462618-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExampleSelective(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694527316-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExample(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694556325-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKeySelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694579114-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKey(UserPollChoiceDbo row);

    List<UserPollChoiceDbo> selectByExampleWithLimits(UserPollChoiceDboExample example);
}