package com.zfgc.zfgbb.services.conversion;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class DistributedLeaseManager {

    private final PlatformTransactionManager transactionManager;

    @Autowired
    public DistributedLeaseManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public interface LeaseContext {
        boolean heartbeat();
        void requireLease();
        <TransactionResult> TransactionResult inNewTransaction(java.util.function.Supplier<TransactionResult> work);
        UUID getOwner();
        String getStatus();
    }

    public static class LeaseLostException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    public <JobResult> JobResult executeWithLease(UUID runId, List<String> expectedStates, String claimedState, DistributedJobRepository repository, Function<LeaseContext, JobResult> action, java.util.function.Supplier<JobResult> fallback) {
        UUID owner = UUID.randomUUID();
        int updated = repository.claimRun(runId, claimedState, owner, expectedStates);
        if (updated != 1) {
            return fallback.get();
        }

        LeaseContext context = new LeaseContext() {
            @Override
            public boolean heartbeat() {
                return inNewTransaction(() -> repository.heartbeat(runId, claimedState, owner) == 1);
            }

            @Override
            public void requireLease() {
                Integer count = repository.countOwnedLease(runId, claimedState, owner);
                if (count == null || count != 1) {
                    throw new LeaseLostException();
                }
            }

            @Override
            public <TransactionResult> TransactionResult inNewTransaction(java.util.function.Supplier<TransactionResult> work) {
                TransactionTemplate template = new TransactionTemplate(transactionManager);
                template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                return template.execute(status -> work.get());
            }

            @Override
            public UUID getOwner() {
                return owner;
            }

            @Override
            public String getStatus() {
                return claimedState;
            }
        };

        return action.apply(context);
    }
}
