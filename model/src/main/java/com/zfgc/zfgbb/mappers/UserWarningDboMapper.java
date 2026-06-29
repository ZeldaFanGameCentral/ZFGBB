package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserWarningDbo;
import com.zfgc.zfgbb.dbo.UserWarningDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserWarningDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738360772-04:00", comments="Source Table: zfgbb.user_warning")
    long countByExample(UserWarningDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738376521-04:00", comments="Source Table: zfgbb.user_warning")
    int deleteByExample(UserWarningDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738390561-04:00", comments="Source Table: zfgbb.user_warning")
    int deleteByPrimaryKey(Integer userWarningId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738399221-04:00", comments="Source Table: zfgbb.user_warning")
    int insert(UserWarningDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73840894-04:00", comments="Source Table: zfgbb.user_warning")
    int insertSelective(UserWarningDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73842141-04:00", comments="Source Table: zfgbb.user_warning")
    List<UserWarningDbo> selectByExample(UserWarningDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738435369-04:00", comments="Source Table: zfgbb.user_warning")
    UserWarningDbo selectByPrimaryKey(Integer userWarningId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738448469-04:00", comments="Source Table: zfgbb.user_warning")
    int updateByExampleSelective(@Param("row") UserWarningDbo row, @Param("example") UserWarningDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738468148-04:00", comments="Source Table: zfgbb.user_warning")
    int updateByExample(@Param("row") UserWarningDbo row, @Param("example") UserWarningDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738489958-04:00", comments="Source Table: zfgbb.user_warning")
    int updateByPrimaryKeySelective(UserWarningDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738507807-04:00", comments="Source Table: zfgbb.user_warning")
    int updateByPrimaryKey(UserWarningDbo row);

    List<UserWarningDbo> selectByExampleWithLimits(UserWarningDboExample example);
}