package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.WikiRevisionRefDbo;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WikiRevisionRefDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751955179-04:00", comments="Source Table: zfgbb.wiki_revision_ref")
    long countByExample(WikiRevisionRefDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751968588-04:00", comments="Source Table: zfgbb.wiki_revision_ref")
    int deleteByExample(WikiRevisionRefDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751980978-04:00", comments="Source Table: zfgbb.wiki_revision_ref")
    int insert(WikiRevisionRefDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751991978-04:00", comments="Source Table: zfgbb.wiki_revision_ref")
    int insertSelective(WikiRevisionRefDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.752004327-04:00", comments="Source Table: zfgbb.wiki_revision_ref")
    List<WikiRevisionRefDbo> selectByExample(WikiRevisionRefDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.752019817-04:00", comments="Source Table: zfgbb.wiki_revision_ref")
    int updateByExampleSelective(@Param("row") WikiRevisionRefDbo row, @Param("example") WikiRevisionRefDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.752036766-04:00", comments="Source Table: zfgbb.wiki_revision_ref")
    int updateByExample(@Param("row") WikiRevisionRefDbo row, @Param("example") WikiRevisionRefDboExample example);

    List<WikiRevisionRefDbo> selectByExampleWithLimits(WikiRevisionRefDboExample example);
}