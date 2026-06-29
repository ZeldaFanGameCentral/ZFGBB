package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceCommentDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceCommentDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceCommentDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFResourceCommentDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033803298-04:00", comments="Source Table: smf_1resource_comments")
    long countByExample(SMFResourceCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033831627-04:00", comments="Source Table: smf_1resource_comments")
    int deleteByExample(SMFResourceCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033853756-04:00", comments="Source Table: smf_1resource_comments")
    int deleteByPrimaryKey(Integer idComment);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033879155-04:00", comments="Source Table: smf_1resource_comments")
    int insert(SMFResourceCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033903375-04:00", comments="Source Table: smf_1resource_comments")
    int insertSelective(SMFResourceCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033930704-04:00", comments="Source Table: smf_1resource_comments")
    List<SMFResourceCommentDbWithBLOBs> selectByExampleWithBLOBs(SMFResourceCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033960433-04:00", comments="Source Table: smf_1resource_comments")
    List<SMFResourceCommentDb> selectByExample(SMFResourceCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033991712-04:00", comments="Source Table: smf_1resource_comments")
    SMFResourceCommentDbWithBLOBs selectByPrimaryKey(Integer idComment);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034013631-04:00", comments="Source Table: smf_1resource_comments")
    int updateByExampleSelective(@Param("row") SMFResourceCommentDbWithBLOBs row, @Param("example") SMFResourceCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034031381-04:00", comments="Source Table: smf_1resource_comments")
    int updateByExampleWithBLOBs(@Param("row") SMFResourceCommentDbWithBLOBs row, @Param("example") SMFResourceCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03405135-04:00", comments="Source Table: smf_1resource_comments")
    int updateByExample(@Param("row") SMFResourceCommentDb row, @Param("example") SMFResourceCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034088379-04:00", comments="Source Table: smf_1resource_comments")
    int updateByPrimaryKeySelective(SMFResourceCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034109318-04:00", comments="Source Table: smf_1resource_comments")
    int updateByPrimaryKeyWithBLOBs(SMFResourceCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034125747-04:00", comments="Source Table: smf_1resource_comments")
    int updateByPrimaryKey(SMFResourceCommentDb row);
}