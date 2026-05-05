package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileAttachmentDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444101045-04:00", comments="Source Table: zfgbb.file_attachments")
    long countByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444121854-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444157313-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444175563-04:00", comments="Source Table: zfgbb.file_attachments")
    int insert(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444189372-04:00", comments="Source Table: zfgbb.file_attachments")
    int insertSelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444218231-04:00", comments="Source Table: zfgbb.file_attachments")
    List<FileAttachmentDbo> selectByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44423957-04:00", comments="Source Table: zfgbb.file_attachments")
    FileAttachmentDbo selectByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44425932-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExampleSelective(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444299009-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExample(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444344757-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKeySelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444392096-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKey(FileAttachmentDbo row);

    List<FileAttachmentDbo> selectByExampleWithLimits(FileAttachmentDboExample example);
}