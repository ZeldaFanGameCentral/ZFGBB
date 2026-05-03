# SMF 2.0.15 install archive

## Provenance

`smf_2-0-15_install.zip` is the upstream SMF 2.0.15 release archive obtained from
the SimpleMachines downloads area:

<https://download.simplemachines.org/index.php?archive;b=4;v=99>

## Files in this directory

- `smf_2-0-15_install.zip` — upstream archive, source of truth.
- `smf-license.txt` — SMF copyright notice (extracted from the archive).
- `smf-schema.sql` — `install_2-0_mysql.sql` extracted from the zip, with the
  `{$db_prefix}` placeholder replaced by `smf_1` and the SMF seed-data
  `INSERT` blocks stripped (they contain unsubstituted placeholders we don't
  need anyway). `SmfFixtureSchemaDriftTest` re-extracts the same content
  from the zip at runtime via `SmfTestFixture#schemaSql` and asserts the
  committed copy matches; the committed copy is what the `fixture` profile
  in `docker-compose.service.smf.yml` mounts as a docker-entrypoint init
  script.
- `smf-zfgc-deltas.sql` — schema changes ZFGC's production SMF instance had
  on top of vanilla SMF 2.0 (extra columns on `boards`/`members`/`polls`/
  `log_karma`/`messages`, plus the custom `messages_history` table).
- `smf-data.sql` — `mysqldump` of a live SMF 2.0.15 instance (the one
  spun up by the `live` profile in `docker-compose.service.smf.yml`)
  restricted to the 12
  tables the migrator reads: `smf_1{members,categories,boards,topics,
  messages,messages_history,polls,poll_choices,log_polls,log_karma,
  attachments,settings}`. The dump is data-only (`--no-create-info`) and
  uses `--skip-extended-insert` so diffs stay readable.
- `smf-attachments/` — binary files named `<id_attach>_<file_hash>`
  matching `smf-data.sql`'s `smf_1attachments` rows; consumed by the
  `ATTACHMENT_FILES` migrator step. The test fixture loader enumerates
  this directory at runtime, so adding/removing files needs no code
  change.

## Regenerating `smf-schema.sql`

If the upstream zip is updated, regenerate the pre-extracted SQL with:

```bash
mvn -pl app -am test-compile
java -cp "app/target/test-classes:$(mvn -pl app -am dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout | tail -1):app/target/classes:model/target/classes:migrator/target/classes" \
  com.zfgc.zfgbb.SmfTestFixture \
  app/src/test/resources/smf-fixtures/2.0.15/smf-schema.sql
```

`SmfFixtureSchemaDriftTest` will fail if the committed `smf-schema.sql` and
the runtime-extracted version diverge.

## Regenerating `smf-data.sql` and `smf-attachments/`

The data fixture is a `mysqldump` of the live SMF docker instance. To
extend coverage (add karma, more users, more bbcode samples, etc.):

```bash
# 1. Spin up the live SMF instance and apply the ZFGC delta columns
docker compose -f docker-compose.service.smf.yml --profile live up -d --build
docker compose -f docker-compose.service.smf.yml exec -T smf_mysql \
  mysql -u root -prootpw smf \
  < app/src/test/resources/smf-fixtures/2.0.15/smf-zfgc-deltas.sql

# 2. Open http://localhost:8090/install.php and complete the SMF installer
#    (use db prefix `smf_1` to match SmfVersion.SUPPORTED_TABLE_PREFIX).
#    Then post topics/messages/attachments via the SMF admin UI.

# 3. Re-dump the 12 migrator-relevant tables:
TABLES="smf_1attachments smf_1boards smf_1categories smf_1log_karma \
  smf_1log_polls smf_1members smf_1messages smf_1messages_history \
  smf_1poll_choices smf_1polls smf_1settings smf_1topics"
docker compose -f docker-compose.service.smf.yml exec -T smf_mysql \
  mysqldump -u root -prootpw \
  --no-create-info --skip-extended-insert --skip-add-locks \
  --skip-set-charset --no-tablespaces --hex-blob \
  --complete-insert --order-by-primary smf $TABLES \
  | sed '/^-- Dump completed on /d' \
  > app/src/test/resources/smf-fixtures/2.0.15/smf-data.sql

# 4. Refresh attachment binaries from the container:
rm -f app/src/test/resources/smf-fixtures/2.0.15/smf-attachments/*
docker compose -f docker-compose.service.smf.yml cp \
  smf_web:/var/www/html/attachments/. \
  app/src/test/resources/smf-fixtures/2.0.15/smf-attachments/
rm -f app/src/test/resources/smf-fixtures/2.0.15/smf-attachments/.htaccess \
      app/src/test/resources/smf-fixtures/2.0.15/smf-attachments/index.php
```

`MigrateSmfInstallationE2ETest` is intentionally written in terms of
data-shape invariants (count parity between SMF and ZFGBB, no orphan
BBCode references) rather than hardcoded row counts or message bodies,
so it should keep passing as the fixture grows. URL-rewrite specifics
are covered by `LegacyUrlRewriterTest` with synthetic inputs.

## License

SMF 2.0 is distributed under the SMF License (BSD-style with naming clause).
The full text is in `smf-license.txt` next to the archive.
