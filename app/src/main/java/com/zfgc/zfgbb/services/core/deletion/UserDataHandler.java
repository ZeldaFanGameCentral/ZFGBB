package com.zfgc.zfgbb.services.core.deletion;

import java.util.List;

public interface UserDataHandler {
    void hardDeleteData(Integer userId);
    void anonymizeData(Integer userId);
    default List<String> backgroundPurge(Integer accountDeletionRequestId, Integer userId) {
        hardDeleteData(userId);
        return List.of();
    }
    default void backgroundAnonymize(Integer userId) {
        anonymizeData(userId);
    }
}
