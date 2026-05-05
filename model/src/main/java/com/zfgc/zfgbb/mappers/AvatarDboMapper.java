package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AvatarDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43234641-04:00", comments="Source Table: zfgbb.avatar")
    long countByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432369769-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432387209-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432404938-04:00", comments="Source Table: zfgbb.avatar")
    int insert(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432425258-04:00", comments="Source Table: zfgbb.avatar")
    int insertSelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432445017-04:00", comments="Source Table: zfgbb.avatar")
    List<AvatarDbo> selectByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432465576-04:00", comments="Source Table: zfgbb.avatar")
    AvatarDbo selectByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432489016-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExampleSelective(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432513605-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExample(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432561463-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKeySelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.432631571-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKey(AvatarDbo row);

    List<AvatarDbo> selectByExampleWithLimits(AvatarDboExample example);
}