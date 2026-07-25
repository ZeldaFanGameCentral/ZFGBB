package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFAttachmentsDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.678064232-04:00", comments="Source Table: smf_1attachments")
    int deleteByPrimaryKey(Integer idAttach);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.678080382-04:00", comments="Source Table: smf_1attachments")
    int insert(SMFAttachmentsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.678098371-04:00", comments="Source Table: smf_1attachments")
    int insertSelective(SMFAttachmentsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.678117131-04:00", comments="Source Table: smf_1attachments")
    List<SMFAttachmentsDb> selectByExample(SMFAttachmentsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.67813615-04:00", comments="Source Table: smf_1attachments")
    SMFAttachmentsDb selectByPrimaryKey(Integer idAttach);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.678245617-04:00", comments="Source Table: smf_1attachments")
    int updateByPrimaryKeySelective(SMFAttachmentsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.678290536-04:00", comments="Source Table: smf_1attachments")
    int updateByPrimaryKey(SMFAttachmentsDb row);
}