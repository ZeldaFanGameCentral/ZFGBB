package com.zfgc.zfgbb.operations.contentstore;

import java.nio.file.Path;

@FunctionalInterface
public interface ContentRootProvider {
	Path activeContentRoot();
}
