package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFCategoryDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFCategoryDbExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFCategoryDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687362944-04:00", comments="Source Table: smf_1categories")
    long countByExample(SMFCategoryDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687385924-04:00", comments="Source Table: smf_1categories")
    int deleteByExample(SMFCategoryDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687401363-04:00", comments="Source Table: smf_1categories")
    int deleteByPrimaryKey(Integer idCat);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687414713-04:00", comments="Source Table: smf_1categories")
    int insert(SMFCategoryDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687426132-04:00", comments="Source Table: smf_1categories")
    int insertSelective(SMFCategoryDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687481661-04:00", comments="Source Table: smf_1categories")
    List<SMFCategoryDb> selectByExample(SMFCategoryDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68750569-04:00", comments="Source Table: smf_1categories")
    SMFCategoryDb selectByPrimaryKey(Integer idCat);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687656466-04:00", comments="Source Table: smf_1categories")
    int updateByExampleSelective(@Param("row") SMFCategoryDb row, @Param("example") SMFCategoryDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687679015-04:00", comments="Source Table: smf_1categories")
    int updateByExample(@Param("row") SMFCategoryDb row, @Param("example") SMFCategoryDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687715694-04:00", comments="Source Table: smf_1categories")
    int updateByPrimaryKeySelective(SMFCategoryDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687750563-04:00", comments="Source Table: smf_1categories")
    int updateByPrimaryKey(SMFCategoryDb row);
}