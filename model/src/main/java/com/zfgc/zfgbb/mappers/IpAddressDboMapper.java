package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IpAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.518875161-04:00", comments="Source Table: zfgbb.ip_address")
    long countByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51889292-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51890848-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.518921819-04:00", comments="Source Table: zfgbb.ip_address")
    int insert(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.518935409-04:00", comments="Source Table: zfgbb.ip_address")
    int insertSelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.518952058-04:00", comments="Source Table: zfgbb.ip_address")
    List<IpAddressDbo> selectByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.518968478-04:00", comments="Source Table: zfgbb.ip_address")
    IpAddressDbo selectByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.518986037-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExampleSelective(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.519004276-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExample(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.519026206-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKeySelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.519056395-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKey(IpAddressDbo row);

    List<IpAddressDbo> selectByExampleWithLimits(IpAddressDboExample example);
}