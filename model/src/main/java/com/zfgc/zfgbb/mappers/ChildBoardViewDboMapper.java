package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ChildBoardViewDbo;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChildBoardViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.2653102-04:00", comments="Source Table: zfgbb.child_board_view")
    long countByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.26532723-04:00", comments="Source Table: zfgbb.child_board_view")
    int deleteByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.265342559-04:00", comments="Source Table: zfgbb.child_board_view")
    int insert(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.265367278-04:00", comments="Source Table: zfgbb.child_board_view")
    int insertSelective(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.265407977-04:00", comments="Source Table: zfgbb.child_board_view")
    List<ChildBoardViewDbo> selectByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.265455726-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExampleSelective(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.265491804-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExample(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    List<ChildBoardViewDbo> selectByExampleWithLimits(ChildBoardViewDboExample example);
}