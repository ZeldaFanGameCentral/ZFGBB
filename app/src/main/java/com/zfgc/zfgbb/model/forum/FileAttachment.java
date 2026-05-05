package com.zfgc.zfgbb.model.forum;

import com.zfgc.zfgbb.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileAttachment extends BaseModel {
	private Integer fileAttachmentId;
	private Integer contentResourceId;
	private String filename;
	private String mimeType;
	private Long fileSize;
	private Integer downloads;

	@Override
	public Integer getId() {
		return fileAttachmentId;
	}

	@Override
	public void setId(Integer id) {
		this.fileAttachmentId = id;
	}
}
