package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoardPermissionViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.257308704-04:00", comments="Source Table: zfgbb.board_permission_view")
    long countByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.257333543-04:00", comments="Source Table: zfgbb.board_permission_view")
    int deleteByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.257356023-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insert(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.257372382-04:00", comments="Source Table: zfgbb.board_permission_view")
    int insertSelective(BoardPermissionViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.257387922-04:00", comments="Source Table: zfgbb.board_permission_view")
    List<BoardPermissionViewDbo> selectByExample(BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.257418181-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExampleSelective(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.2574477-04:00", comments="Source Table: zfgbb.board_permission_view")
    int updateByExample(@Param("row") BoardPermissionViewDbo row, @Param("example") BoardPermissionViewDboExample example);

    List<BoardPermissionViewDbo> selectByExampleWithLimits(BoardPermissionViewDboExample example);
}