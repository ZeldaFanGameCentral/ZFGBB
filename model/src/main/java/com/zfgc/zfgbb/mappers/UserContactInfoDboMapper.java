package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserContactInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428675307-04:00", comments="Source Table: zfgbb.user_contact_info")
    long countByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428694697-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428726766-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428794424-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insert(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428839382-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insertSelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428867671-04:00", comments="Source Table: zfgbb.user_contact_info")
    List<UserContactInfoDbo> selectByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42889602-04:00", comments="Source Table: zfgbb.user_contact_info")
    UserContactInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42892119-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExampleSelective(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428948929-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExample(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428998317-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKeySelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.429040436-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKey(UserContactInfoDbo row);

    List<UserContactInfoDbo> selectByExampleWithLimits(UserContactInfoDboExample example);
}