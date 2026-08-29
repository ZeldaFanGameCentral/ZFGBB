CREATE OR REPLACE VIEW zfgbb.attachment_board_view AS
SELECT t.board_id, fa.content_resource_id
FROM zfgbb.file_attachments fa
JOIN zfgbb.message m ON m.message_id = fa.message_id
JOIN zfgbb.thread t ON t.thread_id = m.thread_id;
