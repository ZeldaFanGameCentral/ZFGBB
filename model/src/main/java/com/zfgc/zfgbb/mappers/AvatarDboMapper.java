package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AvatarDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676645892-04:00", comments="Source Table: zfgbb.avatar")
    long countByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676690811-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.67671741-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676740139-04:00", comments="Source Table: zfgbb.avatar")
    int insert(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676760139-04:00", comments="Source Table: zfgbb.avatar")
    int insertSelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676784738-04:00", comments="Source Table: zfgbb.avatar")
    List<AvatarDbo> selectByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676809297-04:00", comments="Source Table: zfgbb.avatar")
    AvatarDbo selectByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676834387-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExampleSelective(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676858956-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExample(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676898665-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKeySelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676935613-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKey(AvatarDbo row);

    List<AvatarDbo> selectByExampleWithLimits(AvatarDboExample example);
}