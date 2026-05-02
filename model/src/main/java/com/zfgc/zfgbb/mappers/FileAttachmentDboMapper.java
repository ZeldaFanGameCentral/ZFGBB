package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileAttachmentDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510763589-04:00", comments="Source Table: zfgbb.file_attachments")
    long countByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510784828-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510851386-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510875735-04:00", comments="Source Table: zfgbb.file_attachments")
    int insert(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510894584-04:00", comments="Source Table: zfgbb.file_attachments")
    int insertSelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510917424-04:00", comments="Source Table: zfgbb.file_attachments")
    List<FileAttachmentDbo> selectByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510938283-04:00", comments="Source Table: zfgbb.file_attachments")
    FileAttachmentDbo selectByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510956042-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExampleSelective(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510976142-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExample(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.511004501-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKeySelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51103074-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKey(FileAttachmentDbo row);

    List<FileAttachmentDbo> selectByExampleWithLimits(FileAttachmentDboExample example);
}