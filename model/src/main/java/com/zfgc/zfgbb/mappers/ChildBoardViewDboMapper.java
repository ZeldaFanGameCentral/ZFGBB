package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ChildBoardViewDbo;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChildBoardViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.471131502-04:00", comments="Source Table: zfgbb.child_board_view")
    long countByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.471146071-04:00", comments="Source Table: zfgbb.child_board_view")
    int deleteByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.471158061-04:00", comments="Source Table: zfgbb.child_board_view")
    int insert(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.471169231-04:00", comments="Source Table: zfgbb.child_board_view")
    int insertSelective(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47118492-04:00", comments="Source Table: zfgbb.child_board_view")
    List<ChildBoardViewDbo> selectByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.471201389-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExampleSelective(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.471226259-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExample(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    List<ChildBoardViewDbo> selectByExampleWithLimits(ChildBoardViewDboExample example);
}