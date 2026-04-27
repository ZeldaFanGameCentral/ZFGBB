package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AvatarDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356195053-04:00", comments="Source Table: zfgbb.avatar")
    long countByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356212472-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356236442-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356250091-04:00", comments="Source Table: zfgbb.avatar")
    int insert(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356262551-04:00", comments="Source Table: zfgbb.avatar")
    int insertSelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.3562788-04:00", comments="Source Table: zfgbb.avatar")
    List<AvatarDbo> selectByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35629632-04:00", comments="Source Table: zfgbb.avatar")
    AvatarDbo selectByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356311239-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExampleSelective(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356326729-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExample(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356346648-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKeySelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356366137-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKey(AvatarDbo row);
}