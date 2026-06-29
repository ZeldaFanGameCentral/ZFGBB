package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.CurrentMessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurrentMessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.745878048-04:00", comments="Source Table: zfgbb.current_message_view")
    long countByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.745899247-04:00", comments="Source Table: zfgbb.current_message_view")
    int deleteByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.745919296-04:00", comments="Source Table: zfgbb.current_message_view")
    int insert(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.745937066-04:00", comments="Source Table: zfgbb.current_message_view")
    int insertSelective(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.745960315-04:00", comments="Source Table: zfgbb.current_message_view")
    List<CurrentMessageDbo> selectByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.745987104-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExampleSelective(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.746009624-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExample(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    List<CurrentMessageDbo> selectByExampleWithLimits(CurrentMessageDboExample example);
}