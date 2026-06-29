package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmailAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674404542-04:00", comments="Source Table: zfgbb.email_address")
    long countByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674437921-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.67446741-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674499559-04:00", comments="Source Table: zfgbb.email_address")
    int insert(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674527138-04:00", comments="Source Table: zfgbb.email_address")
    int insertSelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674557867-04:00", comments="Source Table: zfgbb.email_address")
    List<EmailAddressDbo> selectByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674596736-04:00", comments="Source Table: zfgbb.email_address")
    EmailAddressDbo selectByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674632205-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExampleSelective(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674677394-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExample(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.674733192-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKeySelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.67478551-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKey(EmailAddressDbo row);

    List<EmailAddressDbo> selectByExampleWithLimits(EmailAddressDboExample example);
}