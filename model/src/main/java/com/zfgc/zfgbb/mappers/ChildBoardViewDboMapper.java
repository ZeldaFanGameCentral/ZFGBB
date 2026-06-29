package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ChildBoardViewDbo;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChildBoardViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751118305-04:00", comments="Source Table: zfgbb.child_board_view")
    long countByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751131594-04:00", comments="Source Table: zfgbb.child_board_view")
    int deleteByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751145294-04:00", comments="Source Table: zfgbb.child_board_view")
    int insert(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751153544-04:00", comments="Source Table: zfgbb.child_board_view")
    int insertSelective(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751166453-04:00", comments="Source Table: zfgbb.child_board_view")
    List<ChildBoardViewDbo> selectByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751179673-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExampleSelective(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751193412-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExample(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    List<ChildBoardViewDbo> selectByExampleWithLimits(ChildBoardViewDboExample example);
}