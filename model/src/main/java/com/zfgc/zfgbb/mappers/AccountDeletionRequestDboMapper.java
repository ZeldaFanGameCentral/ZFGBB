package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AccountDeletionRequestDbo;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountDeletionRequestDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464039819-04:00", comments="Source Table: zfgbb.account_deletion_request")
    long countByExample(AccountDeletionRequestDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464062168-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int deleteByExample(AccountDeletionRequestDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464121036-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int deleteByPrimaryKey(Integer accountDeletionRequestId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464138116-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int insert(AccountDeletionRequestDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464154135-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int insertSelective(AccountDeletionRequestDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464173665-04:00", comments="Source Table: zfgbb.account_deletion_request")
    List<AccountDeletionRequestDbo> selectByExample(AccountDeletionRequestDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464193764-04:00", comments="Source Table: zfgbb.account_deletion_request")
    AccountDeletionRequestDbo selectByPrimaryKey(Integer accountDeletionRequestId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464215713-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int updateByExampleSelective(@Param("row") AccountDeletionRequestDbo row, @Param("example") AccountDeletionRequestDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464238203-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int updateByExample(@Param("row") AccountDeletionRequestDbo row, @Param("example") AccountDeletionRequestDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464273702-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int updateByPrimaryKeySelective(AccountDeletionRequestDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.464303411-04:00", comments="Source Table: zfgbb.account_deletion_request")
    int updateByPrimaryKey(AccountDeletionRequestDbo row);

    List<AccountDeletionRequestDbo> selectByExampleWithLimits(AccountDeletionRequestDboExample example);
}