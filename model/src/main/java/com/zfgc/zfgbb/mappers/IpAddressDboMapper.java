package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IpAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69858076-04:00", comments="Source Table: zfgbb.ip_address")
    long countByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698605529-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698625148-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698667417-04:00", comments="Source Table: zfgbb.ip_address")
    int insert(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698696896-04:00", comments="Source Table: zfgbb.ip_address")
    int insertSelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698721335-04:00", comments="Source Table: zfgbb.ip_address")
    List<IpAddressDbo> selectByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698746615-04:00", comments="Source Table: zfgbb.ip_address")
    IpAddressDbo selectByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698769384-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExampleSelective(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698795883-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExample(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698825082-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKeySelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698909639-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKey(IpAddressDbo row);

    List<IpAddressDbo> selectByExampleWithLimits(IpAddressDboExample example);
}