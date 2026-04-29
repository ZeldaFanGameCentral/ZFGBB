package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileAttachmentDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364041335-04:00", comments="Source Table: zfgbb.file_attachments")
    long countByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364059885-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364071804-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364082754-04:00", comments="Source Table: zfgbb.file_attachments")
    int insert(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364092733-04:00", comments="Source Table: zfgbb.file_attachments")
    int insertSelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364113913-04:00", comments="Source Table: zfgbb.file_attachments")
    List<FileAttachmentDbo> selectByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364134802-04:00", comments="Source Table: zfgbb.file_attachments")
    FileAttachmentDbo selectByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364147672-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExampleSelective(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.364167101-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExample(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.3641885-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKeySelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36420701-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKey(FileAttachmentDbo row);
}