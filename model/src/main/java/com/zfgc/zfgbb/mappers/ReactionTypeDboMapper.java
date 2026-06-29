package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ReactionTypeDbo;
import com.zfgc.zfgbb.dbo.ReactionTypeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReactionTypeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.699993696-04:00", comments="Source Table: zfgbb.reaction_type")
    long countByExample(ReactionTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700018905-04:00", comments="Source Table: zfgbb.reaction_type")
    int deleteByExample(ReactionTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700043814-04:00", comments="Source Table: zfgbb.reaction_type")
    int deleteByPrimaryKey(Integer reactionTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700061914-04:00", comments="Source Table: zfgbb.reaction_type")
    int insert(ReactionTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700082133-04:00", comments="Source Table: zfgbb.reaction_type")
    int insertSelective(ReactionTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700107142-04:00", comments="Source Table: zfgbb.reaction_type")
    List<ReactionTypeDbo> selectByExample(ReactionTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70017644-04:00", comments="Source Table: zfgbb.reaction_type")
    ReactionTypeDbo selectByPrimaryKey(Integer reactionTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700206749-04:00", comments="Source Table: zfgbb.reaction_type")
    int updateByExampleSelective(@Param("row") ReactionTypeDbo row, @Param("example") ReactionTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700230698-04:00", comments="Source Table: zfgbb.reaction_type")
    int updateByExample(@Param("row") ReactionTypeDbo row, @Param("example") ReactionTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700257628-04:00", comments="Source Table: zfgbb.reaction_type")
    int updateByPrimaryKeySelective(ReactionTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700283787-04:00", comments="Source Table: zfgbb.reaction_type")
    int updateByPrimaryKey(ReactionTypeDbo row);

    List<ReactionTypeDbo> selectByExampleWithLimits(ReactionTypeDboExample example);
}