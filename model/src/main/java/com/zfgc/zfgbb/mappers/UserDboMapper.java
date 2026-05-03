package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490518538-04:00", comments="Source Table: zfgbb.user")
    long countByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490546977-04:00", comments="Source Table: zfgbb.user")
    int deleteByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490569206-04:00", comments="Source Table: zfgbb.user")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490590745-04:00", comments="Source Table: zfgbb.user")
    int insert(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490610435-04:00", comments="Source Table: zfgbb.user")
    int insertSelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490636164-04:00", comments="Source Table: zfgbb.user")
    List<UserDbo> selectByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490660063-04:00", comments="Source Table: zfgbb.user")
    UserDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490695952-04:00", comments="Source Table: zfgbb.user")
    int updateByExampleSelective(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490723391-04:00", comments="Source Table: zfgbb.user")
    int updateByExample(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.49075992-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKeySelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.490811618-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKey(UserDbo row);

    List<UserDbo> selectByExampleWithLimits(UserDboExample example);
}