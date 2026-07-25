package com.zfgc.zfgbb.services.users.deletion;

import java.util.List;

public record PurgeBatchOutcome(int processedCount, List<String> releasedBlobPaths) {}
