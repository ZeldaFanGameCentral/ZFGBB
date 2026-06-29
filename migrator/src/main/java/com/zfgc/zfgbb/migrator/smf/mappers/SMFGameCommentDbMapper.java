package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameCommentDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameCommentDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameCommentDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFGameCommentDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032435703-04:00", comments="Source Table: smf_1game_comments")
    long countByExample(SMFGameCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032454412-04:00", comments="Source Table: smf_1game_comments")
    int deleteByExample(SMFGameCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032470202-04:00", comments="Source Table: smf_1game_comments")
    int deleteByPrimaryKey(Integer idComment);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032483001-04:00", comments="Source Table: smf_1game_comments")
    int insert(SMFGameCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032496571-04:00", comments="Source Table: smf_1game_comments")
    int insertSelective(SMFGameCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03251222-04:00", comments="Source Table: smf_1game_comments")
    List<SMFGameCommentDbWithBLOBs> selectByExampleWithBLOBs(SMFGameCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.0325286-04:00", comments="Source Table: smf_1game_comments")
    List<SMFGameCommentDb> selectByExample(SMFGameCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032549439-04:00", comments="Source Table: smf_1game_comments")
    SMFGameCommentDbWithBLOBs selectByPrimaryKey(Integer idComment);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032565879-04:00", comments="Source Table: smf_1game_comments")
    int updateByExampleSelective(@Param("row") SMFGameCommentDbWithBLOBs row, @Param("example") SMFGameCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032583158-04:00", comments="Source Table: smf_1game_comments")
    int updateByExampleWithBLOBs(@Param("row") SMFGameCommentDbWithBLOBs row, @Param("example") SMFGameCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032600157-04:00", comments="Source Table: smf_1game_comments")
    int updateByExample(@Param("row") SMFGameCommentDb row, @Param("example") SMFGameCommentDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032635926-04:00", comments="Source Table: smf_1game_comments")
    int updateByPrimaryKeySelective(SMFGameCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032658186-04:00", comments="Source Table: smf_1game_comments")
    int updateByPrimaryKeyWithBLOBs(SMFGameCommentDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032674675-04:00", comments="Source Table: smf_1game_comments")
    int updateByPrimaryKey(SMFGameCommentDb row);
}