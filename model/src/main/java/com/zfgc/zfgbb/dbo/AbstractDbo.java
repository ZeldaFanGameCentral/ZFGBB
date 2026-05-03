package com.zfgc.zfgbb.dbo;

import java.time.LocalDateTime;

public abstract class AbstractDbo {

	public abstract Integer getPkId();

	public abstract LocalDateTime getCreatedTime();

	public abstract LocalDateTime getUpdatedTime();

	public LocalDateTime getCreatedTs() {
		return null;
	}

	public void setCreatedTs(LocalDateTime createdTs) {
		// no-op fallback; subclass overrides when a created_ts column exists
	}

	public LocalDateTime getUpdatedTs() {
		return null;
	}

	public void setUpdatedTs(LocalDateTime updatedTs) {
		// no-op fallback; subclass overrides when an updated_ts column exists
	}
}
