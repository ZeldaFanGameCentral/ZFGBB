package com.zfgc.zfgbb.migrator.converters;

import com.zfgc.zfgbb.migrator.jobs.JobType;

public abstract class AbstractConverter<T> {

	public abstract JobType getType();

	public abstract T convertToZfgbb() throws Exception;
}
