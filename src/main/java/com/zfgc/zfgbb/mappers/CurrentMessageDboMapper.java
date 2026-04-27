package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.CurrentMessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurrentMessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377780314-04:00", comments="Source Table: zfgbb.current_message_view")
    long countByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377793823-04:00", comments="Source Table: zfgbb.current_message_view")
    int deleteByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377813963-04:00", comments="Source Table: zfgbb.current_message_view")
    int insert(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377824372-04:00", comments="Source Table: zfgbb.current_message_view")
    int insertSelective(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377835022-04:00", comments="Source Table: zfgbb.current_message_view")
    List<CurrentMessageDbo> selectByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377848142-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExampleSelective(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377863201-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExample(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);
}