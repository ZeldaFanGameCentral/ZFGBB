package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526920695-04:00", comments="Source Table: zfgbb.board_permission_view")
    long countByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526934944-04:00", comments="Source Table: zfgbb.board_permission_view")
    int deleteByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526951434-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insert(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526964093-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insertSelective(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526977563-04:00", comments="Source Table: zfgbb.board_permission_view")
    List<BoardPermissionViewDbo> selectByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526994452-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExampleSelective(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.527009632-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExample(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    List<BoardPermissionViewDbo> selectByExampleWithLimits(BoardPermissionViewDboExample example);
}