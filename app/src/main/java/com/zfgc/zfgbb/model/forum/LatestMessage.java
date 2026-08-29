package com.zfgc.zfgbb.model.forum;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatestMessage {
	private Integer threadId;
    private String threadName;
    private OffsetDateTime lastPostTs;
    private Integer ownerId;
    private String ownerName;
}
