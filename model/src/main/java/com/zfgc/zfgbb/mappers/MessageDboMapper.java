package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.507951962-04:00", comments="Source Table: zfgbb.message")
    long countByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.507970591-04:00", comments="Source Table: zfgbb.message")
    int deleteByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.507985201-04:00", comments="Source Table: zfgbb.message")
    int deleteByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50800161-04:00", comments="Source Table: zfgbb.message")
    int insert(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.50801416-04:00", comments="Source Table: zfgbb.message")
    int insertSelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.508050358-04:00", comments="Source Table: zfgbb.message")
    List<MessageDbo> selectByExample(MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.508083487-04:00", comments="Source Table: zfgbb.message")
    MessageDbo selectByPrimaryKey(Integer messageId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.508100627-04:00", comments="Source Table: zfgbb.message")
    int updateByExampleSelective(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.508147475-04:00", comments="Source Table: zfgbb.message")
    int updateByExample(@Param("row") MessageDbo row, @Param("example") MessageDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.508194564-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKeySelective(MessageDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.508242992-04:00", comments="Source Table: zfgbb.message")
    int updateByPrimaryKey(MessageDbo row);

    List<MessageDbo> selectByExampleWithLimits(MessageDboExample example);
}