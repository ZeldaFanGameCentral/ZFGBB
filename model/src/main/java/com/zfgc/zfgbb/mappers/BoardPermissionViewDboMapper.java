package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744120303-04:00", comments="Source Table: zfgbb.board_permission_view")
    long countByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744135452-04:00", comments="Source Table: zfgbb.board_permission_view")
    int deleteByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744148362-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insert(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744162801-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insertSelective(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744176031-04:00", comments="Source Table: zfgbb.board_permission_view")
    List<BoardPermissionViewDbo> selectByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.74419357-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExampleSelective(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7442095-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExample(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    List<BoardPermissionViewDbo> selectByExampleWithLimits(BoardPermissionViewDboExample example);
}