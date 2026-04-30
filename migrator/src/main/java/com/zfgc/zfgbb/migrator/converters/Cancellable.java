package com.zfgc.zfgbb.migrator.converters;

public final class Cancellable {

	private Cancellable() {
	}

	public static void check() {
		if (Thread.currentThread().isInterrupted()) {
			throw new RuntimeException(new InterruptedException("Migrator job cancelled"));
		}
	}
}
