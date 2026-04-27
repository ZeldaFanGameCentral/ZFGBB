package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.UserKarmaViewDbo;
import com.zfgc.zfgbb.dbo.UserKarmaViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserKarmaViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.5235867-04:00", comments="Source Table: zfgbb.user_karma_view")
    long countByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.52453428-04:00", comments="Source Table: zfgbb.user_karma_view")
    int deleteByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.524896818-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insert(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.525087632-04:00", comments="Source Table: zfgbb.user_karma_view")
    int insertSelective(UserKarmaViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.525447471-04:00", comments="Source Table: zfgbb.user_karma_view")
    List<UserKarmaViewDbo> selectByExample(UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.525817689-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExampleSelective(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.526140838-04:00", comments="Source Table: zfgbb.user_karma_view")
    int updateByExample(@Param("row") UserKarmaViewDbo row, @Param("example") UserKarmaViewDboExample example);
}