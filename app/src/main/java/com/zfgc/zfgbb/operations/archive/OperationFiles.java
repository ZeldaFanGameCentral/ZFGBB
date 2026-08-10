package com.zfgc.zfgbb.operations.archive;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.UUID;

public final class OperationFiles {

	private OperationFiles() {}

	public static boolean canonicalId(String id) {
		try {
			return UUID.fromString(id).toString().equals(id);
		} catch (IllegalArgumentException | NullPointerException invalid) {
			return false;
		}
	}

	public static void deleteTree(Path root) throws IOException {
		if (!Files.exists(root, LinkOption.NOFOLLOW_LINKS))
			return;
		try (var paths = Files.walk(root)) {
			for (Path path : paths.sorted(Comparator.reverseOrder()).toList())
				Files.deleteIfExists(path);
		} catch (UncheckedIOException unwalkable) {
			throw unwalkable.getCause();
		}
	}
}
