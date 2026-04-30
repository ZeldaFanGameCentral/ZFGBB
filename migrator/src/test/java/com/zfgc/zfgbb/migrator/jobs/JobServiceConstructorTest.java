package com.zfgc.zfgbb.migrator.jobs;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.zfgc.zfgbb.migrator.converters.AbstractConverter;

class JobServiceConstructorTest {

	@Test
	void rejects_when_a_jobtype_has_no_converter() {
		List<AbstractConverter<?>> partial = Stream.of(JobType.values())
				.filter(t -> t != JobType.MIGRATE_SMF_INSTALLATION)
				.filter(t -> t != JobType.CATEGORIES)
				.map(StubConverter::new)
				.collect(Collectors.toList());

		IllegalStateException ex = assertThrows(IllegalStateException.class,
				() -> new JobService(partial));
		assertTrue(ex.getMessage().contains("CATEGORIES"),
				"missing-converter list should call out CATEGORIES; was: " + ex.getMessage());
	}

	@Test
	void accepts_full_set_of_converters() {
		List<AbstractConverter<?>> full = Stream.of(JobType.values())
				.filter(t -> t != JobType.MIGRATE_SMF_INSTALLATION)
				.map(StubConverter::new)
				.collect(Collectors.toList());

		new JobService(full);
	}

	private static final class StubConverter extends AbstractConverter<Void> {
		private final JobType type;

		StubConverter(JobType type) {
			this.type = type;
		}

		@Override
		public JobType getType() {
			return type;
		}

		@Override
		public Void convertToZfgbb() {
			return null;
		}
	}
}
