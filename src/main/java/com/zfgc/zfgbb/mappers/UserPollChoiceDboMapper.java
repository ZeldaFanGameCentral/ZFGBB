package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36814709-04:00", comments="Source Table: zfgbb.user_poll_choice")
    long countByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36816811-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368185779-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int deleteByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368204268-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insert(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368220028-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int insertSelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368242887-04:00", comments="Source Table: zfgbb.user_poll_choice")
    List<UserPollChoiceDbo> selectByExample(UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368264406-04:00", comments="Source Table: zfgbb.user_poll_choice")
    UserPollChoiceDbo selectByPrimaryKey(Integer userPollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368286396-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExampleSelective(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368307845-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByExample(@Param("row") UserPollChoiceDbo row, @Param("example") UserPollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368333944-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKeySelective(UserPollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368358833-04:00", comments="Source Table: zfgbb.user_poll_choice")
    int updateByPrimaryKey(UserPollChoiceDbo row);
}