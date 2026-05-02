package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmailAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497547656-04:00", comments="Source Table: zfgbb.email_address")
    long countByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497578055-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497595814-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497613153-04:00", comments="Source Table: zfgbb.email_address")
    int insert(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497629763-04:00", comments="Source Table: zfgbb.email_address")
    int insertSelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497648522-04:00", comments="Source Table: zfgbb.email_address")
    List<EmailAddressDbo> selectByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497668892-04:00", comments="Source Table: zfgbb.email_address")
    EmailAddressDbo selectByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.49770131-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExampleSelective(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497754479-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExample(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497788988-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKeySelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497818917-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKey(EmailAddressDbo row);

    List<EmailAddressDbo> selectByExampleWithLimits(EmailAddressDboExample example);
}