package com.zfgc.zfgbb.operations.archive;

public final class InvalidBackupException extends Exception {
	public InvalidBackupException(String message) {
		super(message);
	}

	public InvalidBackupException(String message, Throwable cause) {
		super(message, cause);
	}
}
