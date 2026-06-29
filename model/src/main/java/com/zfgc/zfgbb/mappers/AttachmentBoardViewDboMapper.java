package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AttachmentBoardViewDbo;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttachmentBoardViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.195994824-04:00", comments="Source Table: zfgbb.attachment_board_view")
    long countByExample(AttachmentBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196011583-04:00", comments="Source Table: zfgbb.attachment_board_view")
    int deleteByExample(AttachmentBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196022973-04:00", comments="Source Table: zfgbb.attachment_board_view")
    int insert(AttachmentBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196031282-04:00", comments="Source Table: zfgbb.attachment_board_view")
    int insertSelective(AttachmentBoardViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196040702-04:00", comments="Source Table: zfgbb.attachment_board_view")
    List<AttachmentBoardViewDbo> selectByExample(AttachmentBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196051782-04:00", comments="Source Table: zfgbb.attachment_board_view")
    int updateByExampleSelective(@Param("row") AttachmentBoardViewDbo row, @Param("example") AttachmentBoardViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196062591-04:00", comments="Source Table: zfgbb.attachment_board_view")
    int updateByExample(@Param("row") AttachmentBoardViewDbo row, @Param("example") AttachmentBoardViewDboExample example);

    List<AttachmentBoardViewDbo> selectByExampleWithLimits(AttachmentBoardViewDboExample example);
}