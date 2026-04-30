package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserContactInfoDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352683298-04:00", comments="Source Table: zfgbb.user_contact_info")
    long countByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352723207-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352744616-04:00", comments="Source Table: zfgbb.user_contact_info")
    int deleteByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352763546-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insert(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352781035-04:00", comments="Source Table: zfgbb.user_contact_info")
    int insertSelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352801594-04:00", comments="Source Table: zfgbb.user_contact_info")
    List<UserContactInfoDbo> selectByExample(UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352822034-04:00", comments="Source Table: zfgbb.user_contact_info")
    UserContactInfoDbo selectByPrimaryKey(Integer userId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352840643-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExampleSelective(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352861762-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByExample(@Param("row") UserContactInfoDbo row, @Param("example") UserContactInfoDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352906021-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKeySelective(UserContactInfoDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352967629-04:00", comments="Source Table: zfgbb.user_contact_info")
    int updateByPrimaryKey(UserContactInfoDbo row);
}