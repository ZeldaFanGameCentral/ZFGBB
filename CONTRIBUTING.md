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
    - [IDEs](#ides)
      - [Visual Studio Code](#visual-studio-code)
      - [Eclipse](#eclipse)
    - [Developing Standalone](#developing-standalone)
    - [Building](#building)
    - [Running Tests](#running-tests)
      - [Test lanes](#test-lanes)
      - [Setting up the PostgreSQL database](#setting-up-the-postgresql-database)
        - [First-run installation: `/system/install`](#first-run-installation-systeminstall)
        - [Regenerating the sample data archive](#regenerating-the-sample-data-archive)
    - [Running MyBatis Generator](#running-mybatis-generator)
    - [Docker](#docker)
      - [Utilizing pgadmin](#utilizing-pgadmin)
      - [Viewing Logs for Docker](#viewing-logs-for-docker)
    - [Tearing down Docker](#tearing-down-docker)
    - [Migrating from SMF2](#migrating-from-smf2)
      - [Enabling the migrator](#enabling-the-migrator)
        - [Bringing up a local SMF fixture](#bringing-up-a-local-smf-fixture)
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

> [!IMPORTANT]
> We have a [Nix flake](https://nixos.wiki/wiki/Flakes) that provides a reproducible development environment. Use the following commands to set up Nix.

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

> [!IMPORTANT]
> We have a [Nix flake](https://nixos.wiki/wiki/Flakes) that provides a reproducible development environment. Use the following commands to set up Nix.

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

Anytime you want to start a shell with the environment, run `nix develop`. \o/

</details>

Now that you have everything installed, let's clone the repo and get started!

```bash
git clone https://github.com/ZFGC/ZFGCBB.git
cd ZFGCBB
```

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
mvn -pl app -am compile spring-boot:run
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

```bash
mvn -pl app test -DexcludedGroups=integration   # same as -Pfast-tests
mvn -pl app test -Dgroups=integration           # same as -Pintegration-tests
```

Tag a new test class with `@Tag("integration")` only if it does not already
extend `ZfgbbIntegrationTest`; anything extending that base is tagged already.

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

##### First-run installation: `/system/install`

> [!NOTE]
> Remember to run ZFGBB first! If you haven't already, look at the [IDEs](#ides) section for
> how to set up an IDE to run ZFGBB.

The schema migrations leave the app in an `installed = false` state. To finish
setup, call the install endpoint once. It creates the site administrator, or
restores the sample data archive and reconciles the
administrator onto it, applies the recycle-bin choice and site name, and returns
only after the application is installed. No application restart is part of
installation.

The endpoint is gated by an install token read from the `ZFGBB_INSTALL_TOKEN` env var. If the var is unset, the endpoint always returns 404. For dev, [.env.local](./.env.local) ships with `ZFGBB_INSTALL_TOKEN=dev-install-token`.

Check status (no token needed):

```bash
curl -s http://localhost:8080/zfgbb/system/install/status
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
    "installSampleData": true,
    "provisionRecycleBin": true
  }'
```

##### Regenerating the sample data archive

Every installation that asks for sample data restores
`app/src/main/resources/sample-data/backup.tar.gz`; a deployment that ships no
archive refuses the request. The archive records the database schema version it
was cut at, so it must be regenerated after every new migration or the restore
drift guard refuses the installation.

```bash
bash ./mvnw -o -pl app test -Dtest=FixtureProvenanceTest \
  -Dzfgbb.regenerate.preview.archive=true
```

`FixtureProvenanceTest` is also the guard: without the regeneration flag it fails
the build when the shipped archive was cut at anything other than the highest
committed migration version.

For production deployments, generate a strong random token
(`openssl rand -hex 32`) and set `ZFGBB_INSTALL_TOKEN` at deploy time. Once
installed, the endpoint is unavailable even while the token remains configured;
remove the token during the next normal deployment.

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
```

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
