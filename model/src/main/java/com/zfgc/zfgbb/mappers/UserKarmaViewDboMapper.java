package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserKarmaViewDbo;
import com.zfgc.zfgbb.dbo.UserKarmaViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserKarmaViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.19034126-04:00", comments="Source Table: zfgbb.user_karma_view")
    long countByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.191855071-04:00", comments="Source Table: zfgbb.user_karma_view")
    int deleteByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.192318617-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insert(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.19254707-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insertSelective(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.192944167-04:00", comments="Source Table: zfgbb.user_karma_view")
    List<UserKarmaViewDbo> selectByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.193300666-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExampleSelective(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.193618756-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExample(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    List<UserKarmaViewDbo> selectByExampleWithLimits(UserKarmaViewDboExample example);
}