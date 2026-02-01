#!/usr/bin/env bash
set -euo pipefail

if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
cat <<EOF
Usage: $0 [POSTGRES_USER] [POSTGRES_PASSWORD] [ZFGBB_DATABASE] [ZFGBB_USER] [ZFGBB_USER_PASSWORD]

Example:
  $0 postgres 123456 zfgc_dev zfgbb_user 123456

If no arguments are provided, the following defaults are used:

POSTGRES_USER: postgres
POSTGRES_PASSWORD: 123456
ZFGBB_DATABASE: zfgc_dev
ZFGBB_USER: zfgbb_user
ZFGBB_USER_PASSWORD: 123456

This script:
- Creates the application role if missing
- Creates the database if missing
- Ensures schema ZFGBB exists and is OWNED by the application user
- Runs provisioning SQL with correct ownership so Flyway can manage views
EOF
  exit 0
fi

POSTGRES_USER="${POSTGRES_USER:-${1:-postgres}}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD:-${2:-123456}}"
ZFGBB_DATABASE="${ZFGBB_DATABASE:-${3:-zfgc_dev}}"
ZFGBB_USER="${ZFGBB_USER:-${4:-zfgbb_user}}"
ZFGBB_USER_PASSWORD="${ZFGBB_USER_PASSWORD:-${5:-123456}}"

echo "------------------------------------------------------------"
echo "Initializing ZFGBB database"
echo "  POSTGRES_USER      = ${POSTGRES_USER}"
echo "  ZFGBB_DATABASE     = ${ZFGBB_DATABASE}"
echo "  ZFGBB_USER         = ${ZFGBB_USER}"
echo "------------------------------------------------------------"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  DO \$\$
  BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = '${ZFGBB_USER}') THEN
      CREATE ROLE ${ZFGBB_USER}
        LOGIN
        PASSWORD '${ZFGBB_USER_PASSWORD}'
        NOSUPERUSER
        NOCREATEDB
        NOCREATEROLE
        INHERIT;
      COMMENT ON ROLE ${ZFGBB_USER} IS 'ZFGBB application database user';
    END IF;
  END
  \$\$;

  SELECT 'CREATE DATABASE ${ZFGBB_DATABASE} OWNER ${ZFGBB_USER}'
  WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '${ZFGBB_DATABASE}')\gexec
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$ZFGBB_DATABASE" <<-EOSQL
  DO \$\$
  BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'zfgcadmin') THEN
      CREATE ROLE zfgcadmin NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT;
      COMMENT ON ROLE zfgcadmin IS 'ZFGC application database admin';
    END IF;
  END
  \$\$;

  -- Ensure schema exists and is OWNED BY FLYWAY USER
  CREATE SCHEMA IF NOT EXISTS zfgbb AUTHORIZATION ${ZFGBB_USER};
  ALTER SCHEMA zfgbb OWNER TO ${ZFGBB_USER};

  GRANT USAGE, CREATE ON SCHEMA zfgbb TO ${ZFGBB_USER};
  GRANT ALL PRIVILEGES ON ALL TABLES    IN SCHEMA zfgbb TO ${ZFGBB_USER};
  GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA zfgbb TO ${ZFGBB_USER};
  GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA zfgbb TO ${ZFGBB_USER};

  -- Admin role access
  GRANT USAGE ON SCHEMA zfgbb TO zfgcadmin;
  GRANT ALL PRIVILEGES ON ALL TABLES    IN SCHEMA zfgbb TO zfgcadmin;
  GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA zfgbb TO zfgcadmin;
  GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA zfgbb TO zfgcadmin;

  GRANT zfgcadmin TO ${ZFGBB_USER};

  -- Default privileges FOR FLYWAY USER (this is the missing piece)
  ALTER DEFAULT PRIVILEGES FOR ROLE ${ZFGBB_USER} IN SCHEMA zfgbb
    GRANT ALL ON TABLES TO ${ZFGBB_USER};

  ALTER DEFAULT PRIVILEGES FOR ROLE ${ZFGBB_USER} IN SCHEMA zfgbb
    GRANT ALL ON SEQUENCES TO ${ZFGBB_USER};

  ALTER DEFAULT PRIVILEGES FOR ROLE ${ZFGBB_USER} IN SCHEMA zfgbb
    GRANT ALL ON FUNCTIONS TO ${ZFGBB_USER};
	
	ALTER SCHEMA zfgbb OWNER TO zfgcadmin;
	ALTER TABLE ALL IN SCHEMA zfgbb OWNER TO zfgcadmin;

EOSQL

echo "ZFGBB database provisioning complete"
