package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IpAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248573891-04:00", comments="Source Table: zfgbb.ip_address")
    long countByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248593251-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24860642-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24862062-04:00", comments="Source Table: zfgbb.ip_address")
    int insert(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24863426-04:00", comments="Source Table: zfgbb.ip_address")
    int insertSelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248650129-04:00", comments="Source Table: zfgbb.ip_address")
    List<IpAddressDbo> selectByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248665519-04:00", comments="Source Table: zfgbb.ip_address")
    IpAddressDbo selectByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248681798-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExampleSelective(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248698498-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExample(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248720177-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKeySelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.248757396-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKey(IpAddressDbo row);

    List<IpAddressDbo> selectByExampleWithLimits(IpAddressDboExample example);
}