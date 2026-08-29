package com.zfgc.zfgbb.model.cms;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResult<T> {
	private List<T> items;
	private long total;
	private int page;
	private int pageSize;
}
