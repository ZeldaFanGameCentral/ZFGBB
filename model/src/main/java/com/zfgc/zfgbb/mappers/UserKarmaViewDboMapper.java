package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserKarmaViewDbo;
import com.zfgc.zfgbb.dbo.UserKarmaViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserKarmaViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.730825784-04:00", comments="Source Table: zfgbb.user_karma_view")
    long countByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.731821209-04:00", comments="Source Table: zfgbb.user_karma_view")
    int deleteByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.732178337-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insert(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.73239124-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insertSelective(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.732781606-04:00", comments="Source Table: zfgbb.user_karma_view")
    List<UserKarmaViewDbo> selectByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.733151863-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExampleSelective(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T04:18:33.733483541-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExample(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    List<UserKarmaViewDbo> selectByExampleWithRange(UserKarmaViewDboExample example);
}