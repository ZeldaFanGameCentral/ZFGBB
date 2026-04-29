package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmailAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354524348-04:00", comments="Source Table: zfgbb.email_address")
    long countByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354553157-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354610705-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354647044-04:00", comments="Source Table: zfgbb.email_address")
    int insert(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354670613-04:00", comments="Source Table: zfgbb.email_address")
    int insertSelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354697082-04:00", comments="Source Table: zfgbb.email_address")
    List<EmailAddressDbo> selectByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354721811-04:00", comments="Source Table: zfgbb.email_address")
    EmailAddressDbo selectByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35475766-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExampleSelective(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354788079-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExample(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354819338-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKeySelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354854307-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKey(EmailAddressDbo row);
}