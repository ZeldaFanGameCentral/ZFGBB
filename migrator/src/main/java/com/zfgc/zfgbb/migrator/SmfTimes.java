package com.zfgc.zfgbb.migrator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class SmfTimes {

	public static LocalDateTime fromEpochSeconds(Integer seconds) {
		if (seconds == null || seconds == 0) {
			return null;
		}
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.UTC);
	}

	private SmfTimes() {}
}
