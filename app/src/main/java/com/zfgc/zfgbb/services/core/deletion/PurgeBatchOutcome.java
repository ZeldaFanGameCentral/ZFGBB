package com.zfgc.zfgbb.services.core.deletion;

import java.util.List;

public record PurgeBatchOutcome(int processedCount, List<String> releasedBlobPaths) {}
