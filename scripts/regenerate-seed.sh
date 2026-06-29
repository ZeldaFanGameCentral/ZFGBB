#!/bin/bash

set -euo pipefail

REPO_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
SEED_FILE="$REPO_ROOT/app/src/main/resources/db/packs/zfgc/V1__core_dev_data.sql"
CONTENT_TEMPLATE_SEED="$REPO_ROOT/app/src/main/resources/db/migration/functions/R__05_content_templates.sql"
HOME_WIKI_SEED="$REPO_ROOT/app/src/main/resources/db/migration/functions/R__06_home_wiki_page.sql"
RECYCLE_BIN_SEED="$REPO_ROOT/app/src/main/resources/db/packs/zfgc/V3__recycle_bin.sql"
DUMP_FILE="$(mktemp)"
LEGACY_ARCHIVE="${LEGACY_ARCHIVE:-$REPO_ROOT/app/src/test/resources/legacy-assets.tar.gz}"
LEGACY_DIR="$(mktemp -d)"
trap 'rm -f "$DUMP_FILE"; rm -rf "$LEGACY_DIR"' EXIT
tar -C "$LEGACY_DIR" -xzf "$LEGACY_ARCHIVE"

USER="${SEED_USER:-gm112}"
PASS="${SEED_PASS:-derpderp}"
API="${API_BASE_URL:-http://localhost:8080/zfgbb}"
SMF_HOST="${SMF_HOST:-localhost}"
SMF_PORT="${SMF_PORT:-3308}"
SMF_DB="${SMF_DB:-smf}"
SMF_USER="${SMF_USER:-smf}"
SMF_PASS="${SMF_PASS:-smfpw}"
SMF_PREFIX="${SMF_PREFIX:-smf_1}"
SMF_LEGACY_HOST="${SMF_LEGACY_HOST:-localhost:8090}"
APP_BASE_URL="${APP_BASE_URL:-http://localhost:5173}"
ATTACH_SOURCE="${ATTACH_SOURCE:-$LEGACY_DIR/smf/attachments}"
AVATARS_SOURCE="${AVATARS_SOURCE:-$LEGACY_DIR/smf/avatars}"
ATTACH_TARGET="${ATTACH_TARGET:-$REPO_ROOT/backend-assets}"
CMS_FILES="${CMS_FILES:-$LEGACY_DIR/cms/uploads}"
WIKI_IMAGES="${WIKI_IMAGES:-$LEGACY_DIR/wiki/images}"
PG_CONTAINER="${PG_CONTAINER:-zfgbb-postgresql-1}"
PG_USER="${PG_USER:-zfgbb_user}"
PG_DB="${PG_DB:-zfgc_dev}"

echo "==> Truncating migrator-touched tables (preserving install admin user_id=1)"
docker exec -i "$PG_CONTAINER" psql -U "$PG_USER" -d "$PG_DB" >/dev/null <<'SQL'
truncate zfgbb.content_resource restart identity cascade;
truncate
  zfgbb.message_history,
  zfgbb.message,
  zfgbb.thread,
  zfgbb.file_attachments,
  zfgbb.user_poll_choice,
  zfgbb.poll_choice,
  zfgbb.poll,
  zfgbb.reaction,
  zfgbb.user_contact_info,
  zfgbb.user_bio_info,
  zfgbb.email_address,
  zfgbb.br_board_permission,
  zfgbb.br_user_permission,
  zfgbb.board,
  zfgbb.category,
  zfgbb.ip_address,
  zfgbb.avatar,
  zfgbb.migrator_id_map,
  zfgbb.migrator_attachment_ref_rewrites,
  zfgbb.wiki_page_revision,
  zfgbb.wiki_page_category,
  zfgbb.wiki_page,
  zfgbb.project_screenshot,
  zfgbb.project_download,
  zfgbb.project_news,
  zfgbb.project_tag,
  zfgbb.project,
  zfgbb.resource,
  zfgbb.content_entity,
  zfgbb.tag,
  zfgbb.team_member,
  zfgbb.team,
  zfgbb.content_collection_item,
  zfgbb.content_collection,
  zfgbb.personal_message_recipient,
  zfgbb.personal_message,
  zfgbb.personal_message_conversation,
  zfgbb.notification_subscription,
  zfgbb.user_warning,
  zfgbb.moderation_log,
  zfgbb.user_award,
  zfgbb.user_permission_group_assoc,
  zfgbb.permission_group_assoc,
  zfgbb.permission_group,
  zfgbb.migration_conflict
restart identity cascade;
delete from zfgbb."user" where user_id <> 1;
insert into zfgbb.br_user_permission (user_id, user_permission_id) values (1, 1), (1, 10) on conflict do nothing;
select setval(pg_get_serial_sequence('zfgbb."user"', 'user_id'), 1, true);
select setval(pg_get_serial_sequence('zfgbb.email_address', 'email_address_id'), 1, true);
SQL

echo "==> Restoring Flyway-seeded system content_template rows"
docker exec -i "$PG_CONTAINER" psql -U "$PG_USER" -d "$PG_DB" -v ON_ERROR_STOP=1 >/dev/null < "$CONTENT_TEMPLATE_SEED"

echo "==> Logging in as $USER"
TOKEN=$(curl -fsS -X POST "$API/users/auth/login" \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"$USER\",\"password\":\"$PASS\",\"useTokens\":true}" \
  | grep -oP '"accessToken":"[^"]+' | cut -d'"' -f4)

submit_and_wait() {
  local TYPE="$1" EXTRA="${2:-}" SUBMIT JID STATE
  echo "==> Submitting $TYPE"
  SUBMIT=$(curl -fsS -X POST "$API/system/migrate/jobs" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $TOKEN" \
    -d "{
      \"type\":\"$TYPE\",
      \"smfHost\":\"$SMF_HOST\", \"smfPort\":$SMF_PORT,
      \"smfDatabase\":\"$SMF_DB\", \"smfUser\":\"$SMF_USER\", \"smfPassword\":\"$SMF_PASS\",
      \"smfTablePrefix\":\"$SMF_PREFIX\",
      \"smfLegacyHost\":\"$SMF_LEGACY_HOST\",
      \"attachmentsSourcePath\":\"$ATTACH_SOURCE\", \"attachmentsTargetPath\":\"$ATTACH_TARGET\",
      \"appBaseUrl\":\"$APP_BASE_URL\"$EXTRA,
      \"force\":true
    }")
  JID=$(echo "$SUBMIT" | grep -oP '"id":"[^"]+' | tail -1 | cut -d'"' -f4)
  echo "==> Polling $TYPE (last job id: $JID)"
  for i in $(seq 1 90); do
    sleep 2
    STATE=$(curl -fsS "$API/system/migrate/jobs/$JID" -H "Authorization: Bearer $TOKEN" \
      | grep -oP '"state":"[^"]+' | cut -d'"' -f4)
    echo "  [$i] $JID → $STATE"
    case "$STATE" in
      COMPLETED) return 0 ;;
      FAILED|CANCELLED) echo "$TYPE did not complete cleanly"; exit 1 ;;
    esac
  done
  echo "$TYPE timed out"; exit 1
}

submit_and_wait "MIGRATE_SMF_INSTALLATION" ", \"avatarsSourcePath\":\"$AVATARS_SOURCE\", \"groupPermissionMap\":{\"9\":[\"ZFGC_WIKI_MODERATOR\"]}"
submit_and_wait "MIGRATE_CMS_INSTALLATION" ", \"cmsFilesSourcePath\":\"$CMS_FILES\", \"wikiImagesSourcePath\":\"$WIKI_IMAGES\", \"createMemberWikiPages\":true, \"discussionBoardId\":4, \"resourcesBoardId\":5, \"talkBoardIds\":{\"User\":1}, \"wikiNamespaceIds\":{\"4\":\"ZFGCpedia\",\"5\":\"ZFGCpediaTalk\",\"100\":\"KOT\",\"101\":\"KOTTalk\"}"

echo "==> Dumping zfgbb data"
docker exec "$PG_CONTAINER" pg_dump -U "$PG_USER" -d "$PG_DB" -n zfgbb \
  --data-only --column-inserts --no-owner \
  --table=zfgbb.category \
  --table=zfgbb.board \
  --table=zfgbb.br_board_permission \
  --table='zfgbb."user"' \
  --table=zfgbb.email_address \
  --table=zfgbb.user_contact_info \
  --table=zfgbb.avatar \
  --table=zfgbb.user_bio_info \
  --table=zfgbb.br_user_permission \
  --table=zfgbb.ip_address \
  --table=zfgbb.thread \
  --table=zfgbb.message \
  --table=zfgbb.message_history \
  --table=zfgbb.content_resource \
  --table=zfgbb.file_attachments \
  --table=zfgbb.poll \
  --table=zfgbb.poll_choice \
  --table=zfgbb.user_poll_choice \
  --table=zfgbb.reaction \
  --table=zfgbb.wiki_page \
  --table=zfgbb.wiki_page_revision \
  --table=zfgbb.wiki_page_category \
  --table=zfgbb.content_entity \
  --table=zfgbb.project \
  --table=zfgbb.project_screenshot \
  --table=zfgbb.project_download \
  --table=zfgbb.project_news \
  --table=zfgbb.tag \
  --table=zfgbb.project_tag \
  --table=zfgbb.team \
  --table=zfgbb.team_member \
  --table=zfgbb.resource \
  --table=zfgbb.content_collection \
  --table=zfgbb.content_collection_item \
  --table=zfgbb.permission_group \
  --table=zfgbb.permission_group_assoc \
  --table=zfgbb.user_permission_group_assoc \
  --table=zfgbb.personal_message_conversation \
  --table=zfgbb.personal_message \
  --table=zfgbb.personal_message_recipient \
  --table=zfgbb.notification_subscription \
  --table=zfgbb.user_warning \
  --table=zfgbb.moderation_log \
  > "$DUMP_FILE" 2>/dev/null

echo "==> Writing $SEED_FILE"
{
  echo "-- Auto-generated by scripts/regenerate-seed.sh"
  echo "-- Source: SMF 2.0.15 fixture migrated via MIGRATE_SMF_INSTALLATION + MIGRATE_CMS_INSTALLATION."
  echo "-- Edit the SMF/CMS fixtures and re-run that script instead of editing this file by hand."
  echo

  awk -v q="'" '
    !collecting && /^INSERT INTO zfgbb\./ {
      collecting = 1
      stmt = ""
      quotes = 0
      first = $0
    }
    collecting {
      stmt = stmt $0 "\n"
      quotes += split($0, _, q) - 1
      if (quotes % 2 == 0 && $0 ~ /\);$/) {
        collecting = 0
        if (first !~ /INTO zfgbb\."user".*VALUES \(1,/ && first !~ /INTO zfgbb\.br_user_permission.*VALUES \([0-9]+, [0-9]+, 1\)/) {
          printf "%s", stmt
        }
      }
    }
  ' "$DUMP_FILE"

  echo
  echo "-- ===== wiki-migrated content templates (system rows are Flyway-owned via R__05) ====="
  docker exec "$PG_CONTAINER" psql -U "$PG_USER" -d "$PG_DB" -t -A -c \
    "select format('INSERT INTO zfgbb.content_template (content_template_id, code, content_format, scope, source, body, wiki_page_id, created_ts, updated_ts) VALUES (%s, %L, %L, %L, %L, %L, %s, %L, %L) ON CONFLICT DO NOTHING;', content_template_id, code, content_format, scope, source, body, wiki_page_id, created_ts, updated_ts) from zfgbb.content_template where wiki_page_id is not null order by content_template_id;"

  echo
  echo "-- ===== sequence resets ====="
  cat <<'SQL'
select setval(pg_get_serial_sequence('zfgbb.category', 'category_id'), coalesce((select max(category_id) from zfgbb.category), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.board', 'board_id'), coalesce((select max(board_id) from zfgbb.board), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.thread', 'thread_id'), coalesce((select max(thread_id) from zfgbb.thread), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.message', 'message_id'), coalesce((select max(message_id) from zfgbb.message), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.message_history', 'message_history_id'), coalesce((select max(message_history_id) from zfgbb.message_history), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb."user"', 'user_id'), coalesce((select max(user_id) from zfgbb."user"), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.email_address', 'email_address_id'), coalesce((select max(email_address_id) from zfgbb.email_address), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.ip_address', 'ip_address_id'), coalesce((select max(ip_address_id) from zfgbb.ip_address), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.file_attachments', 'file_attachment_id'), coalesce((select max(file_attachment_id) from zfgbb.file_attachments), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.avatar', 'avatar_id'), coalesce((select max(avatar_id) from zfgbb.avatar), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.content_resource', 'content_resource_id'), coalesce((select max(content_resource_id) from zfgbb.content_resource), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.poll', 'poll_id'), coalesce((select max(poll_id) from zfgbb.poll), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.poll_choice', 'poll_choice_id'), coalesce((select max(poll_choice_id) from zfgbb.poll_choice), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.br_board_permission', 'br_board_permission_id'), coalesce((select max(br_board_permission_id) from zfgbb.br_board_permission), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.br_user_permission', 'br_user_permission_id'), coalesce((select max(br_user_permission_id) from zfgbb.br_user_permission), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.wiki_page', 'wiki_page_id'), coalesce((select max(wiki_page_id) from zfgbb.wiki_page), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.wiki_page_revision', 'wiki_page_revision_id'), coalesce((select max(wiki_page_revision_id) from zfgbb.wiki_page_revision), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.wiki_page_category', 'wiki_page_category_id'), coalesce((select max(wiki_page_category_id) from zfgbb.wiki_page_category), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.content_entity', 'content_entity_id'), coalesce((select max(content_entity_id) from zfgbb.content_entity), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.project_screenshot', 'project_screenshot_id'), coalesce((select max(project_screenshot_id) from zfgbb.project_screenshot), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.project_download', 'project_download_id'), coalesce((select max(project_download_id) from zfgbb.project_download), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.project_news', 'project_news_id'), coalesce((select max(project_news_id) from zfgbb.project_news), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.tag', 'tag_id'), coalesce((select max(tag_id) from zfgbb.tag), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.project_tag', 'project_tag_id'), coalesce((select max(project_tag_id) from zfgbb.project_tag), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.team', 'team_id'), coalesce((select max(team_id) from zfgbb.team), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.team_member', 'team_member_id'), coalesce((select max(team_member_id) from zfgbb.team_member), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.content_collection', 'content_collection_id'), coalesce((select max(content_collection_id) from zfgbb.content_collection), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.content_collection_item', 'content_collection_item_id'), coalesce((select max(content_collection_item_id) from zfgbb.content_collection_item), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.content_template', 'content_template_id'), coalesce((select max(content_template_id) from zfgbb.content_template), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.personal_message_conversation', 'personal_message_conversation_id'), coalesce((select max(personal_message_conversation_id) from zfgbb.personal_message_conversation), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.personal_message', 'personal_message_id'), coalesce((select max(personal_message_id) from zfgbb.personal_message), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.personal_message_recipient', 'personal_message_recipient_id'), coalesce((select max(personal_message_recipient_id) from zfgbb.personal_message_recipient), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.notification_subscription', 'notification_subscription_id'), coalesce((select max(notification_subscription_id) from zfgbb.notification_subscription), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.user_warning', 'user_warning_id'), coalesce((select max(user_warning_id) from zfgbb.user_warning), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.moderation_log', 'moderation_log_id'), coalesce((select max(moderation_log_id) from zfgbb.moderation_log), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.permission_group', 'permission_group_id'), coalesce((select max(permission_group_id) from zfgbb.permission_group), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.permission_group_assoc', 'permission_group_assoc_id'), coalesce((select max(permission_group_assoc_id) from zfgbb.permission_group_assoc), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.user_permission_group_assoc', 'user_permission_group_assoc_id'), coalesce((select max(user_permission_group_assoc_id) from zfgbb.user_permission_group_assoc), 0) + 1, false);
select setval(pg_get_serial_sequence('zfgbb.reaction', 'reaction_id'), coalesce((select max(reaction_id) from zfgbb.reaction), 0) + 1, false);
insert into zfgbb.user_award (user_award_id, award_id, user_id, granted_by_user_id, reason) values (1, 1, 3, 1, 'ALTTP tileset project spotlight');
insert into zfgbb.user_award (user_award_id, award_id, user_id, granted_by_user_id, reason) values (2, 3, 5, 1, 'Always answering newbie questions');
select setval(pg_get_serial_sequence('zfgbb.user_award', 'user_award_id'), coalesce((select max(user_award_id) from zfgbb.user_award), 0) + 1, false);
SQL
} > "$SEED_FILE"

# The truncate above wipes engine/pack-owned rows that the migrator does NOT reproduce, and
# neither self-heals on a re-seed: R__06 (Site:Home) is a repeatable that only re-runs when its
# checksum changes, and V3 (recycle bin) lives in the zfgc pack, which zfgc_dev has no
# flyway_pack_history for. Both reference migrated rows (the wiki pages / the 'Deleted Posts'
# board), so they must run AFTER migration. Replaying them here (post-dump) heals the live DB
# without polluting the generated seed, which stays pure migrator output — the pack's V3 and the
# engine's R__06 own these rows again on a fresh flyway migrate. Both statements are idempotent.
echo "==> Restoring engine/pack-owned rows the migrator does not produce (Site:Home, recycle bin)"
docker exec -i "$PG_CONTAINER" psql -U "$PG_USER" -d "$PG_DB" -v ON_ERROR_STOP=1 >/dev/null < "$HOME_WIKI_SEED"
docker exec -i "$PG_CONTAINER" psql -U "$PG_USER" -d "$PG_DB" -v ON_ERROR_STOP=1 >/dev/null < "$RECYCLE_BIN_SEED"

echo "==> Done. $SEED_FILE written ($(wc -l < "$SEED_FILE") lines)"
