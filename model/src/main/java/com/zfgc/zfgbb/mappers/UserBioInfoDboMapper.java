package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBioInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669955631-04:00", comments="Source Table: zfgbb.user_bio_info")
    long countByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66998454-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670022689-04:00", comments="Source Table: zfgbb.user_bio_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670052048-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insert(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670076787-04:00", comments="Source Table: zfgbb.user_bio_info")
    int insertSelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670183164-04:00", comments="Source Table: zfgbb.user_bio_info")
    List<UserBioInfoDbo> selectByExample(UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670216093-04:00", comments="Source Table: zfgbb.user_bio_info")
    UserBioInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670276161-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExampleSelective(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670324499-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByExample(@Param("row") UserBioInfoDbo row, @Param("example") UserBioInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670402937-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKeySelective(UserBioInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.670456725-04:00", comments="Source Table: zfgbb.user_bio_info")
    int updateByPrimaryKey(UserBioInfoDbo row);

    List<UserBioInfoDbo> selectByExampleWithLimits(UserBioInfoDboExample example);
}