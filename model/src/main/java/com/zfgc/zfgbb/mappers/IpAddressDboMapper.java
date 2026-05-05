package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IpAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455107913-04:00", comments="Source Table: zfgbb.ip_address")
    long countByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455125083-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455139932-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455153932-04:00", comments="Source Table: zfgbb.ip_address")
    int insert(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455165862-04:00", comments="Source Table: zfgbb.ip_address")
    int insertSelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455181851-04:00", comments="Source Table: zfgbb.ip_address")
    List<IpAddressDbo> selectByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4552014-04:00", comments="Source Table: zfgbb.ip_address")
    IpAddressDbo selectByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45521725-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExampleSelective(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455235829-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExample(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455258079-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKeySelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455292318-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKey(IpAddressDbo row);

    List<IpAddressDbo> selectByExampleWithLimits(IpAddressDboExample example);
}