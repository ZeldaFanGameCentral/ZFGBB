#!/bin/bash
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
OUT="$REPO_ROOT/app/src/test/resources/smf-fixtures/2.0.15/smf-data.sql"

ENGINE="${ENGINE:-podman}"
SMF_CONTAINER="${SMF_CONTAINER:-zfgbb-smf_mysql_fixture-1}"
SMF_DB="${SMF_DB:-smf}"
SMF_USER="${SMF_USER:-smf}"
SMF_PASS="${SMF_PASS:-smfpw}"

TABLES=(
  smf_1admin_info_files
  smf_1attachments
  smf_1board_permissions
  smf_1boards
  smf_1calendar_holidays
  smf_1categories
  smf_1log_actions
  smf_1log_comments
  smf_1log_karma
  smf_1log_notify
  smf_1log_polls
  smf_1membergroups
  smf_1members
  smf_1message_icons
  smf_1messages
  smf_1messages_history
  smf_1package_servers
  smf_1permission_profiles
  smf_1permissions
  smf_1personal_messages
  smf_1pm_recipients
  smf_1poll_choices
  smf_1polls
  smf_1scheduled_tasks
  smf_1settings
  smf_1smileys
  smf_1spiders
  smf_1themes
  smf_1topics
)

echo "==> Dumping ${#TABLES[@]} SMF tables from $SMF_CONTAINER -> smf-data.sql"
"$ENGINE" exec "$SMF_CONTAINER" mysqldump -u"$SMF_USER" -p"$SMF_PASS" \
  --no-create-info --complete-insert --skip-extended-insert --no-tablespaces \
  --skip-add-locks --default-character-set=utf8 \
  "$SMF_DB" "${TABLES[@]}" > "$OUT"

echo "==> Wrote $(grep -c '^INSERT INTO' "$OUT") INSERTs ($(wc -l < "$OUT") lines)"
