# Contributing

When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change.

Please note we have a code of conduct, please follow it in all your interactions with the project.

TBD. We could use some help writing this out.

## Table of Contents

- [Contributing](#contributing)
  - [Table of Contents](#table-of-contents)
  - [Development](#development)
    - [Prerequisites](#prerequisites)
      - [Setting up the PostgreSQL database](#setting-up-the-postgresql-database)
        - [First-run installation: `/system/install`](#first-run-installation-systeminstall)
    - [IDEs](#ides)
      - [Visual Studio Code](#visual-studio-code)
      - [Eclipse](#eclipse)
    - [Developing Standalone](#developing-standalone)
    - [Building](#building)
    - [Running Tests](#running-tests)
      - [Test lanes](#test-lanes)
    - [Running MyBatis Generator](#running-mybatis-generator)
    - [Docker](#docker)
      - [Utilizing pgadmin](#utilizing-pgadmin)
      - [Viewing Logs for Docker](#viewing-logs-for-docker)
    - [Tearing down Docker](#tearing-down-docker)
    - [Migrating from SMF2](#migrating-from-smf2)
      - [Enabling the migrator](#enabling-the-migrator)
      - [Submitting and tracking jobs](#submitting-and-tracking-jobs)
      - [Production note](#production-note)
    - [Workflow - Typical Development Workflow](#workflow---typical-development-workflow)

## Development

### Prerequisites

Clone the repository.

- [ ] Install [Git](https://git-scm.com/downloads)

- [ ] Java 21
- [ ] Maven
- [ ] Docker

The project ships a [flake.nix](./flake.nix) that provisions all the above (plus `git`, `jq`, `curl`, `postgresql` client, `pgcli`). On any OS that has nix installed you can skip the per-tool installation entirely and run `nix develop` from the repo root to drop into a shell with everything set up.

For OS specific instructions, see the following below.

<details>

<summary>Setting up for Windows</summary>

> [!IMPORTANT]
> It's required to install [WinGet](https://apps.microsoft.com/detail/9nblggh4nns1?hl=en-US&gl=US) if you want to follow these steps.

```bash
winget install Git.Git
winget install Microsoft.OpenJDK.21
winget install Apache.Maven
winget install Docker.DockerDesktop
```

After installing Docker Desktop, launch it once so the daemon starts. You may need to enable WSL2 integration the first time you launch it.

</details>

<details>

<summary>Setting up for Mac OSX</summary>

First, install [Homebrew](https://brew.sh/).

Then, we will install git, openjdk, maven, and docker.

```bash
brew install git openjdk@21 maven
brew install --cask docker
```

Now we're ready to install nix!

```bash
sh <(curl -L https://nixos.org/nix/install)
source /nix/var/nix/profiles/default/etc/profile.d/nix-daemon.sh
mkdir -p ~/.config/nix/
echo "experimental-features = nix-command flakes" >> ~/.config/nix/nix.conf
```

Then from the repo root:

```bash
nix develop
```

After installing Docker Desktop, launch it once so the daemon starts. Now you're
ready to go!

</details>

<details>

<summary>Setting up for Linux</summary>

First we have to setup docker on your system. This setup procedure varies depending on the
distro you're using, so if your distro is not listed below, please consult the
[Docker documentation](https://docs.docker.com/engine/install/).

```bash
# Debian/Ubuntu
sudo apt install docker.io docker-compose-v2

# Arch
sudo pacman -S docker docker-compose

# Fedora/RHEL
sudo dnf install docker docker-compose

sudo systemctl enable --now docker
sudo usermod -aG docker $USER
# log out and back in for the group change to take effect
```

Now you're ready to setup nix!

```bash
sh <(curl -L https://nixos.org/nix/install) --daemon
source /nix/var/nix/profiles/default/etc/profile.d/nix-daemon.sh
mkdir -p ~/.config/nix/
echo "experimental-features = nix-command flakes" >> ~/.config/nix/nix.conf
```

Then from the repo root:

```bash
nix develop
```

</details>

Now that you have everything installed, let's clone the repo and get started!

```bash
git clone https://github.com/ZFGC/ZFGCBB.git
cd ZFGCBB
```

#### Setting up the PostgreSQL database

You will need a PostgreSQL 18 database and the version-18 client tools. The
default Compose project provisions the application role:

```bash
docker compose up -d --wait postgresql
```

To run pgAdmin too:

```bash
docker compose -f docker-compose.yml -f docker-compose.service.pgadmin.yml up -d --wait
```

You can access pgAdmin at `http://localhost:5050`.

On the first volume start, the database image creates a local bootstrap role.
`postgresql-init-application-role.sh` then creates the `zfgbb_user` application
role from `.env.local`. Flyway applies
[V0__zfgbb-setup.sql](./app/src/main/resources/db/setup/V0__zfgbb-setup.sql)
and the remaining schema migrations when the application starts.

##### First-run installation: `/system/install`

> [!NOTE]
> Remember to run ZFGBB first! If you haven't already, look at the [IDEs](#ides) section for
> how to set up an IDE to run ZFGBB.

The schema migrations leave the app in an `installed = false` state. To finish
setup, call the install endpoint once. It creates the site administrator, or
restores the requested content pack's installation archive and reconciles the
administrator onto it, applies the recycle-bin choice and site name, and returns
only after the application is installed. No application restart is part of
installation.

The endpoint is gated by an install token read from the `ZFGBB_INSTALL_TOKEN` env var. If the var is unset, the endpoint always returns 404. For dev, [.env.local](./.env.local) ships with `ZFGBB_INSTALL_TOKEN=dev-install-token`.

Check status (no token needed):

```bash
curl -s http://localhost:8080/zfgbb/system/install/status
# {"installed":false,"siteName":null}
```

Run the install:

```bash
curl -sX POST http://localhost:8080/zfgbb/system/install \
  -H 'Content-Type: application/json' \
  -H 'X-Install-Token: dev-install-token' \
  -d '{
    "adminUserName": "admin",
    "adminDisplayName": "Site Admin",
    "adminEmail": "admin@example.dev",
    "adminPassword": "adminpass123",
    "siteName": "ZFGBB Dev",
    "contentPack": "zfgc",
    "provisionRecycleBin": true
  }'
# {"installed":true,"adminUserId":1,"siteName":"ZFGBB Dev","contentPack":"zfgc"}
```

An interrupted installation remains resumable by retrying the exact same
request; completion still returns HTTP 200. A different request while an
installation is in progress is rejected, and ordinary subsequent calls after
installation are unavailable. The `installed: true` flag stays in
`system_config`. To re-test the install flow, use the **Reset DB** VS Code task
(or `docker compose exec -T postgresql psql -U zfgbb_user -d zfgc_dev -c
'drop schema if exists zfgbb cascade' && mvn -pl app -am flyway:migrate`) to
wipe and re-migrate, then re-run the install.

An `install_run` row a *previous* build left part-way through the pipeline can be
unresumable on this build, and the endpoint says so instead of completing an
installation whose content was never applied:

- A row written before the `V20260731.2` migration carries
  `install_strategy IS NULL`, so nothing records which sources the interrupted
  run started from. Any retry is refused with HTTP 409 asking you to reset the
  row.
- A row a build older than the archive installer left at a mid-pipeline state
  with `install_strategy='SEED'` **and** `content_pack='zfgc'` describes an
  installation this build can no longer perform. Retrying **with** the pack is
  refused as a strategy switch (409), and retrying **without** it is refused as a
  different request (409), because the pack name is part of the request
  fingerprint.
- A row parked at `PACK_READY` or `ASSETS_READY` names a phase this build never
  records at all, and is refused the same way.

Reset such a row by hand and install again from the top:

```bash
docker compose exec -T postgresql psql -U zfgbb_user -d zfgc_dev -c "
  update zfgbb.install_run
     set state='READY', last_completed_state='READY', request_fingerprint=null,
         content_pack=null, provision_recycle_bin=null, site_name=null,
         install_strategy=null, admin_user_id=null, last_error=null
   where install_id=1"
```

Whatever the interrupted run already wrote to the database stays there, so reset
the schema too (see the **Reset DB** task above) whenever you want a genuinely
clean installation.

Omit `contentPack` for an empty-site installation. The `zfgc` option restores a
versioned developer-preview backup archive that ZFGBB produced itself from the
committed test fixture. The restore replaces the schema contents wholesale, and
the installer then reconciles the submitted administrator onto the archive's
anchor administrator (user ID 1), replacing that account's identity, contact
information, and authentication material with the submitted ones. Other preview
users have no usable password credentials. Generation-time sessions,
account-deletion state, backup and install bookkeeping, and the migrator's
legacy-identifier mappings are not carried into the installation, so a later
`MIGRATE_SMF_INSTALLATION` against your own legacy database still starts from an
empty mapping table. The request fails closed if the archive is not
installer-compatible, does not name a single reconcilable administrator, or was
cut at a database schema version other than the one this build expects.

The installer records only phase and request-fingerprint metadata in the
database, so retrying the same request after an interruption resumes safely.
Every archive entry is digest-checked before anything is written.

> [!IMPORTANT]
> A content pack now ships nothing but `backup.tar.gz`; the SQL seed
> (`V1__preview_seed.sql`), its `manifest.tsv` and its loose `assets/` tree are
> gone. Maven never deletes resources that disappeared from the source tree, so
> `app/target/classes/content-packs/zfgc/v1/` still holds those files in any
> working copy built before the change, and an incremental `package` would ship
> them. Run `bash ./mvnw clean` (or `mvn clean`) once after pulling this change.

##### Regenerating the `zfgc` installation archive

Every installation that requests the `zfgc` pack restores
`app/src/main/resources/content-packs/zfgc/v1/backup.tar.gz`; a content pack
that ships no archive cannot be installed at all. The archive records the
database schema version it was cut at, so it must be regenerated after every new
migration or the restore drift guard refuses the installation.

The generator is an opt-in test that runs the real pipeline — fresh install, SMF
migration, CMS/wiki migration, reviewed overlays — against the committed test
fixture and then takes an ordinary administrator backup of the result:

```bash
bash ./mvnw -o -pl app test -Dtest=FixtureProvenanceTest \
  -Dzfgbb.regenerate.preview.archive=true
```

Without `-Dzfgbb.regenerate.preview.archive=true` the generator is skipped, so a
normal test run never writes into the source tree. The generator refuses to cut
an archive from a drifted corpus: it first asserts the migrated fixture against
the approved semantic inventory, exactly as the ordinary provenance test does.
Verify the regenerated archive with:

```bash
bash ./mvnw -o -pl app test \
  -Dtest=CanonicalContentPackInventoryTest,SystemInstallContentPackTest
```

The archive is a `pg_dump` of the whole `zfgbb` schema minus the data of the
operational and migration-bookkeeping tables (`backup_job`, `install_run`,
`migrator_id_map`, `migration_conflict`, `migrator_attachment_ref_rewrites`,
`quote_strip_audit`, `quote_strip_run`). Carrying the identifier mappings would
make a later migration of somebody else's legacy database resolve their rows
onto preview rows and silently overwrite them. `flyway_schema_history` is kept
because a full restore has to land at a known schema version, and `system_config`
is kept because the installer reconciles the installation keys itself.

##### Regenerating the approved fixture inventory

`app/src/test/resources/fixture-semantic-inventory.tsv` is the approved semantic
inventory of the migrated corpus: a normalized digest per row of every
corpus-influenced table plus a digest of every content asset. Never edit its rows
by hand. When a deliberate corpus or inventory-coverage change lands, rewrite it
from the live migrated fixture:

```bash
bash ./mvnw -o -pl app test -Dtest=FixtureProvenanceTest \
  -Dzfgbb.regenerate.fixture.inventory=true
```

Then re-run `FixtureProvenanceTest` without the flag, review the diff, and
regenerate the installation archive so the shipped artifact and the approved
inventory stay in step.

Backup archives are staged outside the served content directory. Compose uses
the private `operations` volume at `/var/lib/zfgbb/operations`; local runs
default to `${java.io.tmpdir}/zfgbb-operations` and can override it with
`ZFGBB_OPERATIONS_WORK_PATH`. Completed, failed, and expired backup job metadata
is retained for seven days by default; configure
`ZFGBB_OPERATIONS_METADATA_RETENTION` to change that window.

For production deployments, generate a strong random token
(`openssl rand -hex 32`) and set `ZFGBB_INSTALL_TOKEN` at deploy time. Once
installed, the endpoint is unavailable even while the token remains configured;
remove the token during the next normal deployment.

### IDEs

The project is configured to work with the following IDEs.

- [Eclipse](https://www.eclipse.org/)
- [VSCode](https://code.visualstudio.com/)

#### Visual Studio Code

The [vscode settings](./.vscode/settings.json) provides a basic setup for developing with VS Code.

The following build actions are available:

- `Debug Backend`: Runs the backend in debug mode with [.env.local](./.env.local).
- `Debug Backend (Docker postgres)`: Starts PostgreSQL with Compose, then runs
  the backend on the host with `.env.local`. `.env.docker` is reserved for
  processes running inside the Compose network.

#### Eclipse

Eclipse will respect the applications.properties file, so you can use that to configure the application.

**Environment configuration (`.env.local`):** The project uses a `.env.local` file at the repository root for local dev configuration (database URL, secrets, etc.). Spring Boot loads it automatically via `spring.config.import=optional:file:.env.local[.properties]` when the `local` profile is active. To set this up in Eclipse:

1. In your Run Configuration → **Environment** tab, add `SPRING_PROFILES_ACTIVE=local`.
2. In Run Configuration → **Arguments** or **Working Directory**, ensure the working directory is the **repository root** (not `app/`), so `file:.env.local` resolves correctly.

If you prefer not to use `.env.local`, you can set the required environment variables (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, etc.) directly in Eclipse's Run Configuration environment tab.

This project uses [Lombok](https://projectlombok.org/) for generated getters, setters, builders, and logging. Out of the box, Eclipse's compiler doesn't understand `@Data`, `@Getter`, `@Builder`, etc., so the project will look like it's full of "method not found" errors until the Lombok agent is installed:

1. Download the Lombok JAR matching the version pinned in the [parent pom](./pom.xml) (`lombok.version`):

   ```bash
   mvn dependency:get -Dartifact=org.projectlombok:lombok:$(mvn -q -Dexec.executable=echo -Dexec.args='${lombok.version}' --non-recursive exec:exec)
   ```

   Or grab it from <https://projectlombok.org/download>.

2. Run the JAR with `java -jar lombok-<version>.jar`. The installer GUI auto-detects Eclipse installs on your machine — point it at the one you use, hit **Install/Update**, and quit.

3. Restart Eclipse. The Lombok agent line should now appear at the bottom of the Eclipse splash screen ("Lombok vX.Y.Z by ...") confirming the agent is loaded.

4. If errors persist after install, do **Project → Clean → Clean all projects** to force a full rebuild against the Lombok-aware compiler.

### Developing Standalone

From the repository root, run the application in development mode with:

```bash
mvn -pl app spring-boot:run
```

The Maven run configuration explicitly activates the `local` profile and starts
the forked JVM in the repository root. Spring Boot then imports `.env.local`
itself, so the datasource, install token, and content path all come from the
same file rather than Maven attempting to interpolate them before launch. You
can access the application at `http://localhost:8080`.

### Building

To build the application, run the following command:

```bash
mvn package
```

This will create a `.war` file in the `target` directory.

### Running Tests

To run the tests for the backend application, run the following command:

```bash
mvn -pl app -am test
```

This will run all the tests in [app/src/test](app/src/test).

To run the full reactor (all modules), use `mvn test` from the repo root.

#### Test lanes

The suite is split into two lanes by the JUnit tag `integration`, which
[`ZfgbbIntegrationTest`](app/src/test/java/com/zfgc/zfgbb/testsupport/ZfgbbIntegrationTest.java)
carries and every container-backed test inherits:

| Lane | Command | Contents |
| --- | --- | --- |
| **Everything (default)** | `mvn -pl app test` | Both lanes. Boots Testcontainers. |
| **Fast** | `mvn -pl app -Pfast-tests test` | Container-free unit tests only. No Docker needed. |
| **Integration** | `mvn -pl app -Pintegration-tests test` | Only the container-backed tests. |

> [!IMPORTANT]
> **The default is deliberately the full suite.** Running `mvn test` with no
> profile runs the integration tests too, so CI and a bare local `mvn test` keep
> their full coverage. `-Pfast-tests` is an explicit opt-out for tight local
> iteration — never assume a green fast lane means a green build.

The profiles are declared in the root [pom.xml](./pom.xml) and just set
Surefire's `groups` / `excludedGroups` parameters, so the equivalent raw
invocations still work if you prefer them:

```bash
mvn -pl app test -DexcludedGroups=integration   # same as -Pfast-tests
mvn -pl app test -Dgroups=integration           # same as -Pintegration-tests
```

Tag a new test class with `@Tag("integration")` only if it does not already
extend `ZfgbbIntegrationTest`; anything extending that base is tagged already.

### Running MyBatis Generator

The MyBatis Generator config and the generated DBOs/mappers live in the [model](./model) module. The generator introspects a **live Postgres**, so bring one up with the migrations applied first, then run with the working directory set to `model` — the generator resolves `targetProject` against the shell's working directory, not the module basedir, so `-pl model` and `-f model/pom.xml` bind to the aggregator and silently write nothing.

```bash
mvn -pl mbg-plugin install -DskipTests
(cd model && mvn mybatis-generator:generate \
  -DSPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/zfgc_dev \
  -DSPRING_DATASOURCE_USERNAME=zfgbb_user \
  -DSPRING_DATASOURCE_PASSWORD=<password>)
```

The custom plugin that makes each DBO extend `AbstractDbo` and emits its overrides lives in the [mbg-plugin](./mbg-plugin) module, so install it before regenerating.

Generation is idempotent: `suppressDate` is on, so regenerating without a schema change produces a byte-identical tree and `git status` stays clean. Any diff after a regeneration is a real schema difference — review it, never blanket-revert it.

The [migrator](./migrator) module has its own MBG config ([generatorConfig-smf.xml](./migrator/src/main/resources/generatorConfig-smf.xml)) for the SMF source-side DBOs and mappers. The generator introspects against a live MySQL with the SMF schema, so bring up the fixture first and then run MBG against it. The `SmfTablePrefixPlugin` in [mbg-plugin](./mbg-plugin) rewrites the introspected `smf_1` prefix into a `${smfTablePrefix}` placeholder bound from `JobContextHolder.getTablePrefix()` at SQL execution time, so the resulting mappers work against any SMF table prefix.

```bash
docker compose -f docker-compose.service.smf.yml --profile fixture up -d --wait
mvn -pl mbg-plugin install -DskipTests
(cd migrator && mvn initialize mybatis-generator:generate \
  -DZFGBB_MIGRATOR_SMF_GENERATOR_URL="jdbc:mysql://localhost:3307/smf?nullDatabaseMeansCurrent=true&allowPublicKeyRetrieval=true&useSSL=false" \
  -DZFGBB_MIGRATOR_SMF_GENERATOR_USERNAME=smf \
  -DZFGBB_MIGRATOR_SMF_GENERATOR_PASSWORD=smfpw)
docker compose -f docker-compose.service.smf.yml --profile fixture down -v
```

### Docker

You can use Docker to run the application locally, and to standup a postgres instance in a container. This repository implements a [docker-compose.yml](./docker-compose.yml) file to stand up the database and the application, with the following services:

- `postgresql`: Stands up a PostgreSQL database.
- `pgadmin`: Stands up a pgadmin instance to manage the database.
- `api`: Stands up the application.

To stand up the database and the application, run the following command:

```bash
docker compose up -d
```

You can access the application at `http://0.0.0.0:8080`.

#### Utilizing pgadmin

To stand up pgadmin, run the following command:

```bash
docker compose -f ./docker-compose.yml -f ./docker-compose.service.pgadmin.yml up -d pgadmi
n
```

The docker compose yml files are split up to allow for easier customization of a local docker compose workspace.

You can access pgadmin at `http://0.0.0.0:5050`.

#### Viewing Logs for Docker

View the logs using `docker compose logs -f`, or for a specific service (i.e. postgresql) `docker compose logs -f zfgbb_postgresql`.

### Tearing down Docker

To stop the application, run the following command:

```bash
docker compose down -vvv
```

To tear down pgadmin, run the following command:

```bash
docker compose -f ./docker-compose.yml -f ./docker-compose.service.pgadmin.yml down -vvv
```

We pass the `-vvv` flag to the `down` command to remove the volumes.

### Migrating from SMF2

ZFGBB ships with an opt-in migrator that pulls forum data out of an SMF2 (Simple Machines Forum) MySQL database into the live ZFGBB Postgres database. The migrator is a separate library module ([migrator](./migrator)) that auto-configures into the running ZFGBB app when enabled.

#### Enabling the migrator

Off by default. To turn it on, set the following environment variables (or properties) before booting the app:

```bash
ZFGBB_MIGRATOR_ENABLED=true
ZFGBB_MIGRATOR_SMF_JDBC_URL=jdbc:mysql://your-smf-host:3306/your_smf_db
ZFGBB_MIGRATOR_SMF_USERNAME=smf_reader
ZFGBB_MIGRATOR_SMF_PASSWORD=...
```

If you plan to run the `ATTACHMENT_FILES` job (which copies SMF's hash-named attachment files back to their original filenames), also set:

```bash
ZFGBB_MIGRATOR_ATTACHMENTS_SOURCE_PATH=/path/to/smf/attachments
ZFGBB_MIGRATOR_ATTACHMENTS_TARGET_PATH=/path/to/zfgbb/content/attachments
```

When enabled, ZFGBB exposes operator-only endpoints under `/system/migrate/*`. They require the `ZFGC_SITE_ADMIN` role — log in as the site admin created during `/system/install` to obtain a token.

##### Bringing up a local SMF fixture

For development, the repo ships one complete legacy database dump at
[`app/src/test/resources/legacy-database.sql`](./app/src/test/resources/legacy-database.sql)
and one file archive at
[`app/src/test/resources/legacy-assets.tar.gz`](./app/src/test/resources/legacy-assets.tar.gz).
[docker-compose.service.smf.yml](./docker-compose.service.smf.yml) ships two Compose profiles:
`fixture` brings up MySQL preloaded from the complete dump, while `live` brings
up an empty MySQL instance for migration development. No runnable legacy
PHP/SMF application is shipped.

Stand the fixture up so you can point the migrator at it without a real SMF install:

```bash
docker compose -f docker-compose.yml -f docker-compose.service.smf.yml --profile fixture up -d
```

Then point the migrator at it:

```bash
ZFGBB_MIGRATOR_ENABLED=true
ZFGBB_MIGRATOR_SMF_JDBC_URL=jdbc:mysql://localhost:3307/smf
ZFGBB_MIGRATOR_SMF_USERNAME=smf
ZFGBB_MIGRATOR_SMF_PASSWORD=smfpw
mkdir -p /tmp/zfgbb-legacy
tar -xzf app/src/test/resources/legacy-assets.tar.gz -C /tmp/zfgbb-legacy
ZFGBB_MIGRATOR_ATTACHMENTS_SOURCE_PATH=/tmp/zfgbb-legacy/smf/attachments
ZFGBB_MIGRATOR_ATTACHMENTS_TARGET_PATH=/tmp/zfgbb-attachments
```

The fixture's port (default `3307`), credentials, and database name are
configurable via `SMF_FIXTURE_MYSQL_PORT`, `SMF_USER`, `SMF_PASSWORD`, and
`SMF_DATABASE`. The source SQL and assets are immutable provenance inputs.
The actual migration API, reviewed fixture overlays, and independent semantic
inventory reproduce and verify the shipped developer-preview installation
archive.

#### Submitting and tracking jobs

Jobs run one at a time on a single-threaded executor, in submit order. The submit endpoint returns immediately with a list of job ids you poll for status.

The simplest path — run the whole SMF migration in the canonical order:

```bash
curl -sX POST http://localhost:8080/zfgbb/system/migrate/jobs \
  -H 'Content-Type: application/json' \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -d '{"type": "MIGRATE_SMF_INSTALLATION"}'
# 202 Accepted
# [{"id":"...","type":"USERS","state":"QUEUED",...},
#  {"id":"...","type":"CATEGORIES","state":"QUEUED",...},
#  ... 13 more ...]
```

Or submit a single converter when you need to re-run one step:

```bash
curl -sX POST http://localhost:8080/zfgbb/system/migrate/jobs \
  -H 'Content-Type: application/json' \
  -H "Authorization: Bearer $ACCESS_TOKEN" \
  -d '{"type": "USERS"}'
# 202 Accepted
# [{"id":"e2c1...","type":"USERS","state":"QUEUED",...}]
```

List all jobs (handy for watching pipeline progress):

```bash
curl -s -H "Authorization: Bearer $ACCESS_TOKEN" \
  http://localhost:8080/zfgbb/system/migrate/jobs
```

Poll one job:

```bash
curl -s -H "Authorization: Bearer $ACCESS_TOKEN" \
  http://localhost:8080/zfgbb/system/migrate/jobs/e2c1...
```

Cancel a running or queued job:

```bash
curl -sX DELETE -H "Authorization: Bearer $ACCESS_TOKEN" \
  http://localhost:8080/zfgbb/system/migrate/jobs/e2c1...
# 204 No Content
```

Available individual job types: `USERS`, `CATEGORIES`, `BOARDS`, `THREADS`, `MESSAGES`, `IPS`, `MESSAGE_HISTORY`, `USER_BIO_INFO`, `ATTACHMENTS`, `ATTACHMENT_FILES`, `USER_CONTACT_INFO`, `POLLS`, `POLL_CHOICES`, `USER_POLL_CHOICES`, `KARMA`. `ATTACHMENTS` migrates the file-attachment metadata rows; `ATTACHMENT_FILES` is a separate step that copies SMF's hash-named files on disk back to their original filenames. Failures don't halt the pipeline — subsequent jobs keep running, so a failed step shows up as one `FAILED` entry in the list while the rest complete.

#### Production note

Leave `ZFGBB_MIGRATOR_ENABLED=false` for normal production deployments. Only flip it on for the duration of a one-shot migration, then disable and restart.

### Workflow - Typical Development Workflow

1. Read the [Code of Conduct](CODE_OF_CONDUCT.md).
2. If you are not part of the [ZeldaFanGameCentral](https://github.com/ZeldaFanGameCentral/) organization, you will need to fork this repository.
3. Make sure you are on the `development` branch. `git switch development && git pull`.
4. Make a new branch for your changes. `git switch -c my-new-branch`.
   1. How do I name my branch? See the next section, we have some recommendations, but we don't have any official rules so you can use whatever naming convention you prefer for your branch.
   2. Brach Naming Conventions (General Recommendations)
      1. If you are working on a new feature, you can name your branch `feature/my-new-feature`.
      2. If you are working on a bug fix, you can name your branch `bugfix/my-bug-fix` or `fix/my-bug-fix`.
      3. If you are working on a documentation change, you can name your branch `docs/my-docs-change`.
      4. If you are working on a refactor, you can name your branch `refactor/my-refactor`.
      5. If you are working on a test, you can name your branch `test/my-test`.
      6. You are ready to start working on your branch!
5. Working on your changes: Use your IDE of choice to edit files and save changes.
   1. TBD add instructions.
   2. Stage and commit your changes.
   3. Push your changes to your branch on GitHub.
6. [Create a new pull request](https://github.com/ZeldaFanGameCentral/ZFGCBB-React/compare) and request a review from one of the maintainers.
   1. Add a bullet point list of changes you made.
   2. Mention the issue number you are working on.
      1. If there is no issue, you can create one.
   3. Title the pull request using conventional commits, with `closes #issue-number` included, if applicable.
      1. Example: `feat: add new feature`
      2. See: <https://www.conventionalcommits.org/en/v1.0.0/>
   4. For the duration of your pull request, please keep your branch up to date with the `development` branch.
   5. Your PR must pass all checks before it can be merged or requested for review.
7. As Sonic the Hedgehog says, "Gotta go fast!". And you went fast! Congratulations on making a contribution to the project!
