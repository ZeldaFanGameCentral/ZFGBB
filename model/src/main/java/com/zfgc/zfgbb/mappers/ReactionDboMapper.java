package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReactionDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701521368-04:00", comments="Source Table: zfgbb.reaction")
    long countByExample(ReactionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701541348-04:00", comments="Source Table: zfgbb.reaction")
    int deleteByExample(ReactionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701556187-04:00", comments="Source Table: zfgbb.reaction")
    int deleteByPrimaryKey(Integer reactionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701568277-04:00", comments="Source Table: zfgbb.reaction")
    int insert(ReactionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701580876-04:00", comments="Source Table: zfgbb.reaction")
    int insertSelective(ReactionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701598956-04:00", comments="Source Table: zfgbb.reaction")
    List<ReactionDbo> selectByExample(ReactionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701615895-04:00", comments="Source Table: zfgbb.reaction")
    ReactionDbo selectByPrimaryKey(Integer reactionId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701733642-04:00", comments="Source Table: zfgbb.reaction")
    int updateByExampleSelective(@Param("row") ReactionDbo row, @Param("example") ReactionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701754811-04:00", comments="Source Table: zfgbb.reaction")
    int updateByExample(@Param("row") ReactionDbo row, @Param("example") ReactionDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70179032-04:00", comments="Source Table: zfgbb.reaction")
    int updateByPrimaryKeySelective(ReactionDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701821439-04:00", comments="Source Table: zfgbb.reaction")
    int updateByPrimaryKey(ReactionDbo row);

    List<ReactionDbo> selectByExampleWithLimits(ReactionDboExample example);
}