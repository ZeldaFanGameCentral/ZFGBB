package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423016028-04:00", comments="Source Table: zfgbb.user")
    long countByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423048357-04:00", comments="Source Table: zfgbb.user")
    int deleteByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423074286-04:00", comments="Source Table: zfgbb.user")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423096856-04:00", comments="Source Table: zfgbb.user")
    int insert(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423120075-04:00", comments="Source Table: zfgbb.user")
    int insertSelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423146394-04:00", comments="Source Table: zfgbb.user")
    List<UserDbo> selectByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423172463-04:00", comments="Source Table: zfgbb.user")
    UserDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423199682-04:00", comments="Source Table: zfgbb.user")
    int updateByExampleSelective(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423229231-04:00", comments="Source Table: zfgbb.user")
    int updateByExample(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42327501-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKeySelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.423324508-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKey(UserDbo row);

    List<UserDbo> selectByExampleWithLimits(UserDboExample example);
}