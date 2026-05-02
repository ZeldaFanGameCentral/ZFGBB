package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBioInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.493709212-04:00", comments="Source Table: zfgbb.user_bio_info")
    long countByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.493849408-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.493917755-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.493941375-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insert(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.493974494-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insertSelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.494001383-04:00", comments="Source Table: zfgbb.user_bio_info")
    List<UserBioInfoDbo> selectByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.494034462-04:00", comments="Source Table: zfgbb.user_bio_info")
    UserBioInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.494055771-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExampleSelective(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.49408034-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExample(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.494131398-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKeySelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.494174137-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKey(UserBioInfoDbo row);

    List<UserBioInfoDbo> selectByExampleWithLimits(UserBioInfoDboExample example);
}