package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ChildBoardViewDbo;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChildBoardViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.533462379-04:00", comments="Source Table: zfgbb.child_board_view")
    long countByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.533479668-04:00", comments="Source Table: zfgbb.child_board_view")
    int deleteByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.533492608-04:00", comments="Source Table: zfgbb.child_board_view")
    int insert(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.533504597-04:00", comments="Source Table: zfgbb.child_board_view")
    int insertSelective(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.533520477-04:00", comments="Source Table: zfgbb.child_board_view")
    List<ChildBoardViewDbo> selectByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.533539006-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExampleSelective(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.533565735-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExample(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    List<ChildBoardViewDbo> selectByExampleWithLimits(ChildBoardViewDboExample example);
}