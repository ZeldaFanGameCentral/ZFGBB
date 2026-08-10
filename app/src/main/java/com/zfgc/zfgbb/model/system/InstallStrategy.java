package com.zfgc.zfgbb.model.system;

import java.util.Optional;

public enum InstallStrategy {

	NONE("an installation without sample data"),
	ARCHIVE("the sample data archive");

	private final String description;

	InstallStrategy(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}

	public static Optional<InstallStrategy> of(String persistedName) {
		if (persistedName == null)
			return Optional.empty();
		for (InstallStrategy strategy : values())
			if (strategy.name().equals(persistedName))
				return Optional.of(strategy);
		return Optional.empty();
	}
}
