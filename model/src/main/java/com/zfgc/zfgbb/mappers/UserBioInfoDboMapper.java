package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBioInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220610579-04:00", comments="Source Table: zfgbb.user_bio_info")
    long countByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220658887-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220686776-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220706596-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insert(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220722675-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insertSelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220756634-04:00", comments="Source Table: zfgbb.user_bio_info")
    List<UserBioInfoDbo> selectByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220779503-04:00", comments="Source Table: zfgbb.user_bio_info")
    UserBioInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220801793-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExampleSelective(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220822712-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExample(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220864251-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKeySelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22090285-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKey(UserBioInfoDbo row);

    List<UserBioInfoDbo> selectByExampleWithLimits(UserBioInfoDboExample example);
}