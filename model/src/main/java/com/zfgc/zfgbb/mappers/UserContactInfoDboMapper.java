package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserContactInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222727412-04:00", comments="Source Table: zfgbb.user_contact_info")
    long countByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22277616-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222879447-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222915666-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insert(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222944885-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insertSelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222966804-04:00", comments="Source Table: zfgbb.user_contact_info")
    List<UserContactInfoDbo> selectByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222986974-04:00", comments="Source Table: zfgbb.user_contact_info")
    UserContactInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.223007253-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExampleSelective(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.223028092-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExample(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.223059201-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKeySelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22309627-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKey(UserContactInfoDbo row);

    List<UserContactInfoDbo> selectByExampleWithLimits(UserContactInfoDboExample example);
}