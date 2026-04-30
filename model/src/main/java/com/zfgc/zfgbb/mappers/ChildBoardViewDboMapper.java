package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ChildBoardViewDbo;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChildBoardViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381990266-04:00", comments="Source Table: zfgbb.child_board_view")
    long countByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.382003125-04:00", comments="Source Table: zfgbb.child_board_view")
    int deleteByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.382013425-04:00", comments="Source Table: zfgbb.child_board_view")
    int insert(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.382047874-04:00", comments="Source Table: zfgbb.child_board_view")
    int insertSelective(ChildBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.382061523-04:00", comments="Source Table: zfgbb.child_board_view")
    List<ChildBoardViewDbo> selectByExample(ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.382079683-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExampleSelective(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.382095772-04:00", comments="Source Table: zfgbb.child_board_view")
    int updateByExample(@Param("row") ChildBoardViewDbo row, @Param("example") ChildBoardViewDboExample example);
}