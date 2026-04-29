package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBioInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350273307-04:00", comments="Source Table: zfgbb.user_bio_info")
    long countByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350339295-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350372644-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350406913-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insert(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350438732-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insertSelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350463681-04:00", comments="Source Table: zfgbb.user_bio_info")
    List<UserBioInfoDbo> selectByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35050972-04:00", comments="Source Table: zfgbb.user_bio_info")
    UserBioInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350538079-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExampleSelective(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350571428-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExample(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350609016-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKeySelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350697353-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKey(UserBioInfoDbo row);
}