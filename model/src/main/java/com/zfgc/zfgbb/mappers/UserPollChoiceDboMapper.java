package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451545287-04:00", comments="Source Table: zfgbb.user_poll_choice")
    long countByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451582116-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451615615-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451646074-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insert(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451672443-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insertSelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451695362-04:00", comments="Source Table: zfgbb.user_poll_choice")
    List<UserPollChoiceDbo> selectByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451717872-04:00", comments="Source Table: zfgbb.user_poll_choice")
    UserPollChoiceDbo selectByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4517571-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExampleSelective(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451806189-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExample(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451839948-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKeySelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.451865497-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKey(UserPollChoiceDbo row);

    List<UserPollChoiceDbo> selectByExampleWithLimits(UserPollChoiceDboExample example);
}