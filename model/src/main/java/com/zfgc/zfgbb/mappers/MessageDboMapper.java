package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.440954255-04:00", comments="Source Table: zfgbb.message")
    long countByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.440974675-04:00", comments="Source Table: zfgbb.message")
    int deleteByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.440993634-04:00", comments="Source Table: zfgbb.message")
    int deleteByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441009264-04:00", comments="Source Table: zfgbb.message")
    int insert(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441024953-04:00", comments="Source Table: zfgbb.message")
    int insertSelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441051752-04:00", comments="Source Table: zfgbb.message")
    List<MessageDbo> selectByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441077991-04:00", comments="Source Table: zfgbb.message")
    MessageDbo selectByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44111042-04:00", comments="Source Table: zfgbb.message")
    int updateByExampleSelective(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441161189-04:00", comments="Source Table: zfgbb.message")
    int updateByExample(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441194438-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKeySelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441220587-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKey(MessageDbo row);

    List<MessageDbo> selectByExampleWithLimits(MessageDboExample example);
}