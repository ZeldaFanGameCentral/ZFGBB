package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmailAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.224881663-04:00", comments="Source Table: zfgbb.email_address")
    long countByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.224910762-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.224927382-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.224943941-04:00", comments="Source Table: zfgbb.email_address")
    int insert(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.224959121-04:00", comments="Source Table: zfgbb.email_address")
    int insertSelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22497772-04:00", comments="Source Table: zfgbb.email_address")
    List<EmailAddressDbo> selectByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22499692-04:00", comments="Source Table: zfgbb.email_address")
    EmailAddressDbo selectByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.225028749-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExampleSelective(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.225057268-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExample(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.225083987-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKeySelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.225108746-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKey(EmailAddressDbo row);

    List<EmailAddressDbo> selectByExampleWithLimits(EmailAddressDboExample example);
}