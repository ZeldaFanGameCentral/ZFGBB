package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogCommentDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogCommentDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFLogCommentDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.040349133-04:00", comments="Source Table: smf_1log_comments")
    int deleteByPrimaryKey(Integer idComment);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.040369472-04:00", comments="Source Table: smf_1log_comments")
    int insert(SMFLogCommentDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.040385542-04:00", comments="Source Table: smf_1log_comments")
    int insertSelective(SMFLogCommentDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.040409491-04:00", comments="Source Table: smf_1log_comments")
    List<SMFLogCommentDb> selectByExampleWithBLOBs(SMFLogCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.0404299-04:00", comments="Source Table: smf_1log_comments")
    List<SMFLogCommentDb> selectByExample(SMFLogCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.04044722-04:00", comments="Source Table: smf_1log_comments")
    SMFLogCommentDb selectByPrimaryKey(Integer idComment);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.040523117-04:00", comments="Source Table: smf_1log_comments")
    int updateByPrimaryKeySelective(SMFLogCommentDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.040561496-04:00", comments="Source Table: smf_1log_comments")
    int updateByPrimaryKeyWithBLOBs(SMFLogCommentDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.040592555-04:00", comments="Source Table: smf_1log_comments")
    int updateByPrimaryKey(SMFLogCommentDb row);
}