package com.zfgc.zfgbb.services.conversion;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.mappers.custom.QuoteStripConversionMapper;

@Component
public class QuoteStripJobRepositoryWrapper implements DistributedJobRepository {

    private final QuoteStripConversionMapper mapper;

    @Autowired
    public QuoteStripJobRepositoryWrapper(QuoteStripConversionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int claimRun(UUID runId, String claimedState, UUID owner, List<String> expectedStates) {
        return mapper.claimRun(runId, claimedState, owner, expectedStates);
    }

    @Override
    public int heartbeat(UUID runId, String status, UUID owner) {
        return mapper.heartbeat(runId, status, owner);
    }

    @Override
    public Integer countOwnedLease(UUID runId, String status, UUID owner) {
        return mapper.countOwnedLease(runId, status, owner);
    }

    @Override
    public int finishRun(UUID runId, String finishedState, String expectedState, UUID owner) {
        return mapper.finishRun(runId, finishedState, expectedState, owner);
    }

    @Override
    public Integer countRun(UUID runId) {
        return mapper.countRun(runId);
    }
}
