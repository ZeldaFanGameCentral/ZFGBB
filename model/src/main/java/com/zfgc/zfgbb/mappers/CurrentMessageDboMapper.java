package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.CurrentMessageDbo;
import com.zfgc.zfgbb.dbo.CurrentMessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurrentMessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.259420887-04:00", comments="Source Table: zfgbb.current_message_view")
    long countByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.259435327-04:00", comments="Source Table: zfgbb.current_message_view")
    int deleteByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.259449206-04:00", comments="Source Table: zfgbb.current_message_view")
    int insert(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.259460766-04:00", comments="Source Table: zfgbb.current_message_view")
    int insertSelective(CurrentMessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.259473935-04:00", comments="Source Table: zfgbb.current_message_view")
    List<CurrentMessageDbo> selectByExample(CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.259490445-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExampleSelective(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.259505194-04:00", comments="Source Table: zfgbb.current_message_view")
    int updateByExample(@Param("row") CurrentMessageDbo row, @Param("example") CurrentMessageDboExample example);

    List<CurrentMessageDbo> selectByExampleWithLimits(CurrentMessageDboExample example);
}