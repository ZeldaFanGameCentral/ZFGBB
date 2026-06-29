package com.zfgc.zfgbb.model.forum;

import java.time.OffsetDateTime;

public class LatestMessage {
	private Integer threadId;
    private String threadName;
    private OffsetDateTime lastPostTs;
    private Integer ownerId;
    private String ownerName;

	public OffsetDateTime getLastPostTs() {
		return lastPostTs;
	}
	public void setLastPostTs(OffsetDateTime lastPostTs) {
		this.lastPostTs = lastPostTs;
	}
	public Integer getThreadId() {
		return threadId;
	}
	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}