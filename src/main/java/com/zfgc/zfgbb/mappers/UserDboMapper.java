package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346747233-04:00", comments="Source Table: zfgbb.user")
    long countByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346798012-04:00", comments="Source Table: zfgbb.user")
    int deleteByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346824211-04:00", comments="Source Table: zfgbb.user")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34684673-04:00", comments="Source Table: zfgbb.user")
    int insert(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346868129-04:00", comments="Source Table: zfgbb.user")
    int insertSelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346912468-04:00", comments="Source Table: zfgbb.user")
    List<UserDbo> selectByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346941757-04:00", comments="Source Table: zfgbb.user")
    UserDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346968316-04:00", comments="Source Table: zfgbb.user")
    int updateByExampleSelective(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346998605-04:00", comments="Source Table: zfgbb.user")
    int updateByExample(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.347048403-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKeySelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.347089362-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKey(UserDbo row);
}