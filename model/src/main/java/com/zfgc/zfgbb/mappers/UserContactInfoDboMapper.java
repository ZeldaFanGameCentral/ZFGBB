package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserContactInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.495956418-04:00", comments="Source Table: zfgbb.user_contact_info")
    long countByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.495977787-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496000677-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496017536-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insert(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496033036-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insertSelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496052745-04:00", comments="Source Table: zfgbb.user_contact_info")
    List<UserContactInfoDbo> selectByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496071734-04:00", comments="Source Table: zfgbb.user_contact_info")
    UserContactInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496091434-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExampleSelective(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496112383-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExample(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496140162-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKeySelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.496171761-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKey(UserContactInfoDbo row);

    List<UserContactInfoDbo> selectByExampleWithLimits(UserContactInfoDboExample example);
}