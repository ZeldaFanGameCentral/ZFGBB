package com.zfgc.zfgbb.model.forum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LatestMessage {
	private Integer threadId;
    private String threadName;
    @JsonIgnore
    private LocalDateTime lastPostTs;
    private Integer ownerId;
    private String ownerName;

	public LocalDateTime getLastPostTs() {
		return lastPostTs;
	}
	public void setLastPostTs(LocalDateTime lastPostTs) {
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
	public String getLastPostTsAsString() {
		if(lastPostTs != null) {
			return lastPostTs.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		}
		return "";
	}
}