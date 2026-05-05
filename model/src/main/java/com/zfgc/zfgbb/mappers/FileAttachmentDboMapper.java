package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileAttachmentDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238593958-04:00", comments="Source Table: zfgbb.file_attachments")
    long countByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238623177-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238647796-04:00", comments="Source Table: zfgbb.file_attachments")
    int deleteByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238670036-04:00", comments="Source Table: zfgbb.file_attachments")
    int insert(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238692105-04:00", comments="Source Table: zfgbb.file_attachments")
    int insertSelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238741234-04:00", comments="Source Table: zfgbb.file_attachments")
    List<FileAttachmentDbo> selectByExample(FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238772423-04:00", comments="Source Table: zfgbb.file_attachments")
    FileAttachmentDbo selectByPrimaryKey(Integer fileAttachmentId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238798682-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExampleSelective(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238826231-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByExample(@Param("row") FileAttachmentDbo row, @Param("example") FileAttachmentDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238870959-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKeySelective(FileAttachmentDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238915038-04:00", comments="Source Table: zfgbb.file_attachments")
    int updateByPrimaryKey(FileAttachmentDbo row);

    List<FileAttachmentDbo> selectByExampleWithLimits(FileAttachmentDboExample example);
}