package com.zfgc.zfgbb.model.forum;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FileAttachment extends BaseModel {
	private Integer fileAttachmentId;
	private Integer contentResourceId;
	@Builder.Default
	private Optional<String> filename = Optional.empty();
	@Builder.Default
	private Optional<String> mimeType = Optional.empty();
	@Builder.Default
	private Optional<Long> fileSize = Optional.empty();
	private int downloads;

	@Override
	public Integer getId() {
		return fileAttachmentId;
	}

	@Override
	public void setId(Integer id) {
		this.fileAttachmentId = id;
	}
}
