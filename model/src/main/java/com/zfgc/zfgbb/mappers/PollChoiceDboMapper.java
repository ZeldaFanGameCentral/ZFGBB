package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PollChoiceDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.513982052-04:00", comments="Source Table: zfgbb.poll_choice")
    long countByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514002212-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514018661-04:00", comments="Source Table: zfgbb.poll_choice")
    int deleteByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514033681-04:00", comments="Source Table: zfgbb.poll_choice")
    int insert(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51404604-04:00", comments="Source Table: zfgbb.poll_choice")
    int insertSelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51406451-04:00", comments="Source Table: zfgbb.poll_choice")
    List<PollChoiceDbo> selectByExample(PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514082969-04:00", comments="Source Table: zfgbb.poll_choice")
    PollChoiceDbo selectByPrimaryKey(Integer pollChoiceId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514099329-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExampleSelective(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514120378-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByExample(@Param("row") PollChoiceDbo row, @Param("example") PollChoiceDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514145457-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKeySelective(PollChoiceDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.514169586-04:00", comments="Source Table: zfgbb.poll_choice")
    int updateByPrimaryKey(PollChoiceDbo row);

    List<PollChoiceDbo> selectByExampleWithLimits(PollChoiceDboExample example);
}