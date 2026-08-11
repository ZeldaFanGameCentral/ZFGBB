package com.zfgc.zfgbb.content;

public enum ContentScope {
	ALL,
	WIKI,
	FORUM,
	PROJECT,
	RESOURCE,
	SIGNATURE;

	public boolean isAConcreteSurface() {
		return this != ALL;
	}
}
