package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IpAddressDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37058763-04:00", comments="Source Table: zfgbb.ip_address")
    long countByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370610419-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370650518-04:00", comments="Source Table: zfgbb.ip_address")
    int deleteByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370741855-04:00", comments="Source Table: zfgbb.ip_address")
    int insert(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370764994-04:00", comments="Source Table: zfgbb.ip_address")
    int insertSelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370780474-04:00", comments="Source Table: zfgbb.ip_address")
    List<IpAddressDbo> selectByExample(IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370794133-04:00", comments="Source Table: zfgbb.ip_address")
    IpAddressDbo selectByPrimaryKey(Integer ipAddressId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370806393-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExampleSelective(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370867831-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByExample(@Param("row") IpAddressDbo row, @Param("example") IpAddressDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37089109-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKeySelective(IpAddressDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37090981-04:00", comments="Source Table: zfgbb.ip_address")
    int updateByPrimaryKey(IpAddressDbo row);
}