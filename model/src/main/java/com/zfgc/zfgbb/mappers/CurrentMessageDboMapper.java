package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.CurrentMessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurrentMessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46491706-04:00", comments="Source Table: zfgbb.current_message_view")
    long countByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4649309-04:00", comments="Source Table: zfgbb.current_message_view")
    int deleteByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464944399-04:00", comments="Source Table: zfgbb.current_message_view")
    int insert(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464960299-04:00", comments="Source Table: zfgbb.current_message_view")
    int insertSelective(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464973318-04:00", comments="Source Table: zfgbb.current_message_view")
    List<CurrentMessageDbo> selectByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.464989998-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExampleSelective(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.465004137-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExample(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    List<CurrentMessageDbo> selectByExampleWithLimits(CurrentMessageDboExample example);
}