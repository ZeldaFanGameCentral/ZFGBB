package com.zfgc.zfgbb.operations.maintenance;

/**
 * Coordinates application-controlled background writers with a consistent
 * backup snapshot. Implementations block new writers while maintenance owns the
 * exclusive lease.
 */
@FunctionalInterface
public interface MutationLeaseProvider {
	Lease acquireMutationLease() throws Exception;

	@FunctionalInterface
	interface Lease extends AutoCloseable {
		@Override
		void close() throws Exception;
	}
}
