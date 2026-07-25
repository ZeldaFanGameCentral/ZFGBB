package com.zfgc.zfgbb.operations.maintenance;

import java.util.List;
import java.util.UUID;

public interface DistributedJobRepository {
    int claimRun(UUID runId, String claimedState, UUID owner, List<String> expectedStates);
    int heartbeat(UUID runId, String status, UUID owner);
    Integer countOwnedLease(UUID runId, String status, UUID owner);
    int finishRun(UUID runId, String finishedState, String expectedState, UUID owner);
    Integer countRun(UUID runId);
}
