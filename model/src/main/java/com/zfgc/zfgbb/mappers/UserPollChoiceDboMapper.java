package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244347126-04:00", comments="Source Table: zfgbb.user_poll_choice")
    long countByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244378265-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244397424-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244453102-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insert(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244495061-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insertSelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24453019-04:00", comments="Source Table: zfgbb.user_poll_choice")
    List<UserPollChoiceDbo> selectByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244554829-04:00", comments="Source Table: zfgbb.user_poll_choice")
    UserPollChoiceDbo selectByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244578088-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExampleSelective(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244610837-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExample(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244643556-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKeySelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.244671985-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKey(UserPollChoiceDbo row);

    List<UserPollChoiceDbo> selectByExampleWithLimits(UserPollChoiceDboExample example);
}