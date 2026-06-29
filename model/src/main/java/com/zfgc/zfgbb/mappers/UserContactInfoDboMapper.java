package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserContactInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672882519-04:00", comments="Source Table: zfgbb.user_contact_info")
    long countByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672911999-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672938778-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672956417-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insert(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672973257-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insertSelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672993266-04:00", comments="Source Table: zfgbb.user_contact_info")
    List<UserContactInfoDbo> selectByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.673014885-04:00", comments="Source Table: zfgbb.user_contact_info")
    UserContactInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.673034735-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExampleSelective(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.673072064-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExample(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.673104073-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKeySelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.673135552-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKey(UserContactInfoDbo row);

    List<UserContactInfoDbo> selectByExampleWithLimits(UserContactInfoDboExample example);
}