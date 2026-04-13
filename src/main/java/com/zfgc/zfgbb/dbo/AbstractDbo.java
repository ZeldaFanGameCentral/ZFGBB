package com.zfgc.zfgbb.dbo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDbo {
	
	public abstract Integer getPkId();
	public abstract LocalDateTime getUpdatedTime();
	public abstract LocalDateTime getCreatedTime();
	private LocalDateTime updatedTs;
	private LocalDateTime createdTs;
	
	
	
}