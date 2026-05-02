package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.CurrentMessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurrentMessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528596989-04:00", comments="Source Table: zfgbb.current_message_view")
    long countByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528613769-04:00", comments="Source Table: zfgbb.current_message_view")
    int deleteByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528628258-04:00", comments="Source Table: zfgbb.current_message_view")
    int insert(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528641818-04:00", comments="Source Table: zfgbb.current_message_view")
    int insertSelective(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528655708-04:00", comments="Source Table: zfgbb.current_message_view")
    List<CurrentMessageDbo> selectByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528672677-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExampleSelective(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528687397-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExample(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    List<CurrentMessageDbo> selectByExampleWithLimits(CurrentMessageDboExample example);
}