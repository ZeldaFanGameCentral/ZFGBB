package com.zfgc.zfgbb.mappers.custom;

import com.zfgc.zfgbb.dbo.UserAggregateDbo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserProfileHydrationMapper {
    List<UserAggregateDbo> hydrateUsers(@Param("userIds") List<Integer> userIds);
}
