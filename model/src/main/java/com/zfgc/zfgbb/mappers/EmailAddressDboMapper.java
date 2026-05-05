package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmailAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430754551-04:00", comments="Source Table: zfgbb.email_address")
    long countByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43078784-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430810789-04:00", comments="Source Table: zfgbb.email_address")
    int deleteByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430834329-04:00", comments="Source Table: zfgbb.email_address")
    int insert(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430857338-04:00", comments="Source Table: zfgbb.email_address")
    int insertSelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430879477-04:00", comments="Source Table: zfgbb.email_address")
    List<EmailAddressDbo> selectByExample(EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430904486-04:00", comments="Source Table: zfgbb.email_address")
    EmailAddressDbo selectByPrimaryKey(Integer emailAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430937845-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExampleSelective(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430987634-04:00", comments="Source Table: zfgbb.email_address")
    int updateByExample(@Param("row") EmailAddressDbo row, @Param("example") EmailAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.431027232-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKeySelective(EmailAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.431059091-04:00", comments="Source Table: zfgbb.email_address")
    int updateByPrimaryKey(EmailAddressDbo row);

    List<EmailAddressDbo> selectByExampleWithLimits(EmailAddressDboExample example);
}