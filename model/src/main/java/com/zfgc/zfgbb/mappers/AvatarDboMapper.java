package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AvatarDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.498943029-04:00", comments="Source Table: zfgbb.avatar")
    long countByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.498962269-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.498980068-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.498995878-04:00", comments="Source Table: zfgbb.avatar")
    int insert(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.499009407-04:00", comments="Source Table: zfgbb.avatar")
    int insertSelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.499026717-04:00", comments="Source Table: zfgbb.avatar")
    List<AvatarDbo> selectByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.499046836-04:00", comments="Source Table: zfgbb.avatar")
    AvatarDbo selectByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.499066365-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExampleSelective(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.499086945-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExample(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.499125633-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKeySelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.499172582-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKey(AvatarDbo row);

    List<AvatarDbo> selectByExampleWithLimits(AvatarDboExample example);
}