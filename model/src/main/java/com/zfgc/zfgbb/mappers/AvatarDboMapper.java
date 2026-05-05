package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AvatarDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226343737-04:00", comments="Source Table: zfgbb.avatar")
    long countByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226364626-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226389295-04:00", comments="Source Table: zfgbb.avatar")
    int deleteByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226406795-04:00", comments="Source Table: zfgbb.avatar")
    int insert(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226421084-04:00", comments="Source Table: zfgbb.avatar")
    int insertSelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226439694-04:00", comments="Source Table: zfgbb.avatar")
    List<AvatarDbo> selectByExample(AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226465323-04:00", comments="Source Table: zfgbb.avatar")
    AvatarDbo selectByPrimaryKey(Integer avatarId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226485702-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExampleSelective(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226508362-04:00", comments="Source Table: zfgbb.avatar")
    int updateByExample(@Param("row") AvatarDbo row, @Param("example") AvatarDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226539711-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKeySelective(AvatarDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226597899-04:00", comments="Source Table: zfgbb.avatar")
    int updateByPrimaryKey(AvatarDbo row);

    List<AvatarDbo> selectByExampleWithLimits(AvatarDboExample example);
}