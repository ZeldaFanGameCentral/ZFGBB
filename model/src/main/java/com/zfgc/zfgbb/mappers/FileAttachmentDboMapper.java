package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileAttachmentDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688757086-04:00", comments="Source Table: zfgbb.file_attachments")
    long countByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688776815-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688792114-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688808474-04:00", comments="Source Table: zfgbb.file_attachments")
    int insert(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688823053-04:00", comments="Source Table: zfgbb.file_attachments")
    int insertSelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688840273-04:00", comments="Source Table: zfgbb.file_attachments")
    List<FileAttachmentDbo> selectByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688859152-04:00", comments="Source Table: zfgbb.file_attachments")
    FileAttachmentDbo selectByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68893517-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExampleSelective(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688994458-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExample(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.689043457-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKeySelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.689076536-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKey(FileAttachmentDbo row);

    List<FileAttachmentDbo> selectByExampleWithLimits(FileAttachmentDboExample example);
}