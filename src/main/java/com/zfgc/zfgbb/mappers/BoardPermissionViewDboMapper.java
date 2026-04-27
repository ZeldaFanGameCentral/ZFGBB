package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.376327132-04:00", comments="Source Table: zfgbb.board_permission_view")
    long countByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.376360601-04:00", comments="Source Table: zfgbb.board_permission_view")
    int deleteByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37637329-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insert(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37638249-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insertSelective(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.376392979-04:00", comments="Source Table: zfgbb.board_permission_view")
    List<BoardPermissionViewDbo> selectByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.376406159-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExampleSelective(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.376417469-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExample(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);
}