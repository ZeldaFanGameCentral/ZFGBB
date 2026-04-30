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
  need anyway). The integration test re-extracts the same content from the
  zip at runtime via `SmfTestFixture#schemaSql`; this checked-in copy exists
  so the docker-compose fixture (`docker-compose.service.smf-fixture.yml`)
  can mount it directly without a build-time prep step.
- `smf-zfgc-deltas.sql` — schema changes ZFGC's production SMF instance had
  on top of vanilla SMF 2.0 (extra columns on `boards`/`members`/`polls`/
  `log_karma`/`messages`, plus the custom `messages_history` table).
- `smf-data.sql` — hand-authored fixture rows exercising every table the
  migrator reads.
- `smf-attachments/` — small binary files named `<id_attach>_<file_hash>`
  matching `smf-data.sql`'s `smf_1attachments` rows; consumed by the
  `ATTACHMENT_FILES` migrator step.

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

## License

SMF 2.0 is distributed under the SMF License (BSD-style with naming clause).
The full text is in `smf-license.txt` next to the archive.
