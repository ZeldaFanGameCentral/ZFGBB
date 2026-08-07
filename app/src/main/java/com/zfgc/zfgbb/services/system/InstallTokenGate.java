package com.zfgc.zfgbb.services.system;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InstallTokenGate {

	public static final int FAILURES_TOLERATED = 10;

	public static final Duration COOLDOWN = Duration.ofMinutes(1);

	private final Clock clock;

	private int consecutiveFailures;

	private Instant lockedUntil = Instant.EPOCH;

	public InstallTokenGate() {
		this(Clock.systemUTC());
	}

	public InstallTokenGate(Clock clock) {
		this.clock = clock;
	}

	public synchronized boolean isLocked() {
		return clock.instant().isBefore(lockedUntil);
	}

	public synchronized void recordFailure() {
		consecutiveFailures++;
		log.warn("install token rejected ({} of {})", consecutiveFailures, FAILURES_TOLERATED);
		if (consecutiveFailures >= FAILURES_TOLERATED) {
			lockedUntil = clock.instant().plus(COOLDOWN);
			consecutiveFailures = 0;
			log.warn("install endpoint locked until {}", lockedUntil);
		}
	}

	public synchronized void recordSuccess() {
		consecutiveFailures = 0;
	}
}
