package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AccountDeletionAuditDbo;
import com.zfgc.zfgbb.dbo.AccountDeletionAuditDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountDeletionAuditDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.465954147-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    long countByExample(AccountDeletionAuditDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.465974577-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int deleteByExample(AccountDeletionAuditDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.465989116-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int deleteByPrimaryKey(Integer deletionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466004746-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int insert(AccountDeletionAuditDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466022325-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int insertSelective(AccountDeletionAuditDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466047524-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    List<AccountDeletionAuditDbo> selectByExample(AccountDeletionAuditDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466075664-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    AccountDeletionAuditDbo selectByPrimaryKey(Integer deletionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466096433-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int updateByExampleSelective(@Param("row") AccountDeletionAuditDbo row, @Param("example") AccountDeletionAuditDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466117922-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int updateByExample(@Param("row") AccountDeletionAuditDbo row, @Param("example") AccountDeletionAuditDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466144421-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int updateByPrimaryKeySelective(AccountDeletionAuditDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.466211449-04:00", comments="Source Table: zfgbb.account_deletion_audit")
    int updateByPrimaryKey(AccountDeletionAuditDbo row);

    List<AccountDeletionAuditDbo> selectByExampleWithLimits(AccountDeletionAuditDboExample example);
}