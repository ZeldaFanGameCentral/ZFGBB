CREATE OR REPLACE VIEW zfgbb.recent_activity_view AS
SELECT v.thread_id,
       v.thread_name,
       v.board_id,
       b.board_name,
       u.display_name AS last_poster,
       m.owner_id AS last_poster_id,
       v.last_post_ts
FROM zfgbb.latest_message_in_thread_view v
JOIN zfgbb.board b ON b.board_id = v.board_id
JOIN LATERAL (
    SELECT m2.owner_id
    FROM zfgbb.message m2
    WHERE m2.thread_id = v.thread_id
      AND m2.created_ts = v.last_post_ts
    ORDER BY m2.message_id DESC
    LIMIT 1
) m ON true
LEFT JOIN zfgbb."user" u ON u.user_id = m.owner_id;
