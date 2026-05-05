package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216046224-04:00", comments="Source Table: zfgbb.user")
    long countByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216109352-04:00", comments="Source Table: zfgbb.user")
    int deleteByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21615377-04:00", comments="Source Table: zfgbb.user")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21618116-04:00", comments="Source Table: zfgbb.user")
    int insert(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216202789-04:00", comments="Source Table: zfgbb.user")
    int insertSelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216231348-04:00", comments="Source Table: zfgbb.user")
    List<UserDbo> selectByExample(UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216257127-04:00", comments="Source Table: zfgbb.user")
    UserDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216304996-04:00", comments="Source Table: zfgbb.user")
    int updateByExampleSelective(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216357354-04:00", comments="Source Table: zfgbb.user")
    int updateByExample(@Param("row") UserDbo row, @Param("example") UserDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216424552-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKeySelective(UserDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.216515539-04:00", comments="Source Table: zfgbb.user")
    int updateByPrimaryKey(UserDbo row);

    List<UserDbo> selectByExampleWithLimits(UserDboExample example);
}