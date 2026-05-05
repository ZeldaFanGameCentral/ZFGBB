package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBioInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426683421-04:00", comments="Source Table: zfgbb.user_bio_info")
    long countByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42671645-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426751409-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426775808-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insert(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426804367-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insertSelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426828646-04:00", comments="Source Table: zfgbb.user_bio_info")
    List<UserBioInfoDbo> selectByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426851376-04:00", comments="Source Table: zfgbb.user_bio_info")
    UserBioInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426874725-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExampleSelective(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426900104-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExample(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426986411-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKeySelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.427050929-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKey(UserBioInfoDbo row);

    List<UserBioInfoDbo> selectByExampleWithLimits(UserBioInfoDboExample example);
}