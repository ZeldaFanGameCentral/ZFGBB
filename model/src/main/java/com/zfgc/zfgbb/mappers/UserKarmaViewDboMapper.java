package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserKarmaViewDbo;
import com.zfgc.zfgbb.dbo.UserKarmaViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserKarmaViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.466956786-04:00", comments="Source Table: zfgbb.user_karma_view")
    long countByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.467931604-04:00", comments="Source Table: zfgbb.user_karma_view")
    int deleteByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.468316931-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insert(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.468541254-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insertSelective(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.468898392-04:00", comments="Source Table: zfgbb.user_karma_view")
    List<UserKarmaViewDbo> selectByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.46928169-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExampleSelective(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.469620348-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExample(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    List<UserKarmaViewDbo> selectByExampleWithLimits(UserKarmaViewDboExample example);
}