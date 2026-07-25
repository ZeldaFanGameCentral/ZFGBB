package com.zfgc.zfgbb.services.users.deletion;

import java.util.List;
import java.util.Optional;

public interface UserDataHandler {

    int PURGE_CHUNK_SIZE = 500;

    List<String> purgeData(Integer userId, Optional<Integer> accountDeletionRequestId);

    List<String> anonymizeData(Integer userId, Optional<Integer> accountDeletionRequestId);
}
