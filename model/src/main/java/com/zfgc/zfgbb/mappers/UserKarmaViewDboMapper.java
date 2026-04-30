package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserKarmaViewDbo;
import com.zfgc.zfgbb.dbo.UserKarmaViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserKarmaViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.472486822-04:00", comments="Source Table: zfgbb.user_karma_view")
    long countByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.47349836-04:00", comments="Source Table: zfgbb.user_karma_view")
    int deleteByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.473856999-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insert(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.474061232-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insertSelective(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.47442666-04:00", comments="Source Table: zfgbb.user_karma_view")
    List<UserKarmaViewDbo> selectByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.474811698-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExampleSelective(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.475132858-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExample(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);
}