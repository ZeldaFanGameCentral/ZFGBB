package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463241464-04:00", comments="Source Table: zfgbb.board_permission_view")
    long countByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463254733-04:00", comments="Source Table: zfgbb.board_permission_view")
    int deleteByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463267783-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insert(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463279263-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insertSelective(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463291982-04:00", comments="Source Table: zfgbb.board_permission_view")
    List<BoardPermissionViewDbo> selectByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463307792-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExampleSelective(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463324451-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExample(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    List<BoardPermissionViewDbo> selectByExampleWithLimits(BoardPermissionViewDboExample example);
}