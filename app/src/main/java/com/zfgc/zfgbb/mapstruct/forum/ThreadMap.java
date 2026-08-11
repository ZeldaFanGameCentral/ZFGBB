package com.zfgc.zfgbb.mapstruct.forum;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.LatestMessageInThreadViewDbo;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.forum.LatestMessage;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface ThreadMap {
	@Mapping(target="allowedActions", ignore=true)
	@Mapping(target="id", ignore=true)
	@Mapping(target="boardName", ignore=true)
	@Mapping(target="createdUser", ignore=true)
	@Mapping(target="postCount", ignore=true)
	@Mapping(target="pageCount", ignore=true)
	@Mapping(target="boardPermissions", ignore=true)
	@Mapping(target="messages", ignore=true)
	@Mapping(target="latestMessage", ignore=true)
	@Mapping(target="pollInfo", ignore=true)
	@Mapping(target="permissions", ignore=true)
	@Mapping(target="recycleBinEnabled", ignore=true)
	Thread toModel(ThreadDbo dbo);

	@Mapping(target="allowedActions", ignore=true)
	@Mapping(target="id", ignore=true)
	@Mapping(target="boardName", ignore=true)
	@Mapping(target="createdUser", ignore=true)
	@Mapping(target="postCount", ignore=true)
	@Mapping(target="pageCount", ignore=true)
	@Mapping(target="boardPermissions", ignore=true)
	@Mapping(target="messages", ignore=true)
	@Mapping(target="latestMessage", ignore=true)
	@Mapping(target="pollInfo", ignore=true)
	@Mapping(target="permissions", ignore=true)
	@Mapping(target="recycledFromBoardId", ignore=true)
	@Mapping(target="recycledFromThreadId", ignore=true)
	@Mapping(target="recycleBinEnabled", ignore=true)
	Thread toThread(LatestMessageInThreadViewDbo dbo);

	@Mapping(target="ownerId", ignore=true)
	@Mapping(target="ownerName", ignore=true)
	LatestMessage toLatestMessage(LatestMessageInThreadViewDbo dbo);

	@Mapping(target="migrationHash", ignore=true)
	ThreadDbo toDbo(Thread model);
}
