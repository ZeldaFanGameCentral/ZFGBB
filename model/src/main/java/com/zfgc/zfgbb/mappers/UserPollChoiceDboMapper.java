package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51526321-04:00", comments="Source Table: zfgbb.user_poll_choice")
    long countByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515281859-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515296849-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515318238-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insert(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515340108-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insertSelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515359887-04:00", comments="Source Table: zfgbb.user_poll_choice")
    List<UserPollChoiceDbo> selectByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515392656-04:00", comments="Source Table: zfgbb.user_poll_choice")
    UserPollChoiceDbo selectByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515421885-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExampleSelective(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515482833-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExample(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515510262-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKeySelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515533421-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKey(UserPollChoiceDbo row);

    List<UserPollChoiceDbo> selectByExampleWithLimits(UserPollChoiceDboExample example);
}