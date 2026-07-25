package com.zfgc.zfgbb.operations.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.zfgc.zfgbb.operations.archive.InvalidBackupException;

class PostgresDumpTocValidatorTest {
	private final PostgresDumpTocValidator validator = new PostgresDumpTocValidator();

	@Test
	void acceptsOnlyKnownObjectsInTheApplicationSchema() throws Exception {
		String toc = """
				;
				7; 2615 18596 SCHEMA - zfgbb zfgbb_user
				10; 1255 18607 FUNCTION zfgbb sample(integer, text) zfgbb_user
				11; 1259 18620 TABLE zfgbb sample zfgbb_user
				12; 0 18620 TABLE DATA zfgbb sample zfgbb_user
				13; 2606 18621 FK CONSTRAINT zfgbb sample sample_parent_fkey zfgbb_user
				""";

		assertEquals(5, validator.validate(toc, 10));
	}

	@Test
	void rejectsSchemaNamesHiddenByObjectNamesOrOwners() {
		assertThrows(InvalidBackupException.class, () -> validator.validate(
				"11; 1259 18620 TABLE public zfgbb_payload zfgbb\n", 10));
		assertThrows(InvalidBackupException.class, () -> validator.validate(
				"11; 1259 18620 TABLE public payload zfgbb\n", 10));
	}

	@Test
	void rejectsSecuritySensitiveAndUnknownObjectKinds() {
		assertThrows(InvalidBackupException.class, () -> validator.validate(
				"11; 0 0 ACL zfgbb TABLE sample zfgbb_user\n", 10));
		assertThrows(InvalidBackupException.class, () -> validator.validate(
				"12; 0 0 EVENT TRIGGER - payload zfgbb_user\n", 10));
		assertThrows(InvalidBackupException.class, () -> validator.validate(
				"13; 0 0 FOREIGN TABLE zfgbb payload zfgbb_user\n", 10));
	}

	@Test
	void acceptsOrdinaryObjectKindsAnUpcomingMigrationCanIntroduce() throws Exception {
		String toc = """
				3535; 0 0 COMMENT zfgbb TABLE sample zfgbb_user
				895; 1247 32772 DOMAIN zfgbb sample_domain zfgbb_user
				254; 1255 32770 PROCEDURE zfgbb sample_proc() zfgbb_user
				222; 1259 32763 MATERIALIZED VIEW zfgbb sample_mv zfgbb_user
				3528; 0 32763 MATERIALIZED VIEW DATA zfgbb sample_mv zfgbb_user
				310; 1255 32780 AGGREGATE zfgbb sample_agg(integer) zfgbb_user
				""";

		assertEquals(6, validator.validate(toc, 10),
				"an allowlist over pg_dump's open-ended vocabulary breaks backup, restore "
						+ "and archive install the first time a migration adds a comment");
	}

	@Test
	void stillConfinesThoseKindsToTheApplicationSchema() {
		assertThrows(InvalidBackupException.class, () -> validator.validate(
				"3535; 0 0 COMMENT public TABLE sample zfgbb_user\n", 10));
		assertThrows(InvalidBackupException.class, () -> validator.validate(
				"222; 1259 32763 MATERIALIZED VIEW public sample_mv zfgbb_user\n", 10));
	}

	@Test
	void enforcesTheTocCountLimit() {
		String toc = """
				7; 2615 18596 SCHEMA - zfgbb zfgbb_user
				11; 1259 18620 TABLE zfgbb sample zfgbb_user
				""";
		assertThrows(InvalidBackupException.class, () -> validator.validate(toc, 1));
	}
}
