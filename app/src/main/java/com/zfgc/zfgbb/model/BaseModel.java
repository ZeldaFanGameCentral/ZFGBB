package com.zfgc.zfgbb.model;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseModel {
	private OffsetDateTime updatedTs;
	private OffsetDateTime createdTs;

	public abstract Integer getId();
	public abstract void setId(Integer id);

	public OffsetDateTime getUpdatedTs() {
		return updatedTs;
	}

	public OffsetDateTime getCreatedTs() {
		return createdTs;
	}

	public void setUpdatedTs(OffsetDateTime updatedTs) {
		this.updatedTs = updatedTs;
	}

	public void setCreatedTs(OffsetDateTime createdTs) {
		this.createdTs = createdTs;
	}

}