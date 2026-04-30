-- gm112 note: this is seed data only for local development!

insert into zfgbb.category (category_id, category_name, description) values
  (1000, 'General',   'Forum-wide discussion'),
  (1001, 'Off-Topic', 'Anything goes');

-- ===== Boards =====
insert into zfgbb.board (board_id, board_name, description, category_id, seqno) values
  (1000, 'Announcements',  'News and announcements', 1000, 1),
  (1001, 'Help & Support', 'Get help with the forum', 1000, 2),
  (1002, 'Random',         'Off-topic chat',         1001, 1);

insert into zfgbb.br_board_permission (board_id, permission_id) values
  (1000, 1), (1000, 2),  -- Announcements:  ZFGC_USER + ZFGC_GUEST
  (1001, 1), (1001, 2),  -- Help & Support: ZFGC_USER + ZFGC_GUEST
  (1002, 1), (1002, 2);  -- Random:         ZFGC_USER + ZFGC_GUEST

insert into zfgbb.user (
    user_id, sso_key, user_name, display_name, active_flag,
    password_hash, password_algo, password_changed_ts, failed_login_count
) values
  (1001, 'alice', 'alice', 'Alice', true,
   '$2a$10$dHeRQSq0n/laGOnlshHiT.BgOTE9VJbaNuQvpX3Wilvmp2KFw5T0S', 'BCRYPT', current_timestamp, 0),
  (1002, 'bob',   'bob',   'Bob',   true,
   '$2a$10$dHeRQSq0n/laGOnlshHiT.BgOTE9VJbaNuQvpX3Wilvmp2KFw5T0S', 'BCRYPT', current_timestamp, 0),
  (1003, 'carol', 'carol', 'Carol', true,
   '$2a$10$dHeRQSq0n/laGOnlshHiT.BgOTE9VJbaNuQvpX3Wilvmp2KFw5T0S', 'BCRYPT', current_timestamp, 0);

insert into zfgbb.email_address (email_address_id, email_address, spammer_flag) values
  (1001, 'alice@example.dev', false),
  (1002, 'bob@example.dev',   false),
  (1003, 'carol@example.dev', false);

insert into zfgbb.user_contact_info (user_id, email_address_id, allow_email_flag, allow_pm_flag) values
  (1001, 1001, true, true),
  (1002, 1002, true, true),
  (1003, 1003, true, true);

insert into zfgbb.user_bio_info (user_id, karma_good, karma_bad, hide_email_flag, hide_online_status) values
  (1001, 0, 0, false, false),
  (1002, 0, 0, false, false),
  (1003, 0, 0, false, false);

insert into zfgbb.br_user_permission (user_id, user_permission_id) values
  (1001, 1),
  (1002, 1), 
  (1003, 1);  

insert into zfgbb.ip_address (ip_address_id, ip, ip_v6_flag, is_spammer_flag) values
  (1000, '127.0.0.1', false, false);

insert into zfgbb.thread (thread_id, thread_name, locked_flag, pinned_flag, board_id, created_user_id, view_count) values
  (1000, 'Welcome to ZFGBB',           false, true,  1000, 1001, 0),
  (1001, 'Forum rules and guidelines', false, true,  1000, 1001, 0),
  (1002, 'Say hi here!',               false, false, 1002, 1001, 0);

insert into zfgbb.message (message_id, owner_id, thread_id, post_in_thread) values
  (1000, 1001, 1000, 1),
  (1001, 1001, 1001, 1),
  (1002, 1001, 1002, 1),
  (1003, 1002, 1002, 2),
  (1004, 1003, 1002, 3);

insert into zfgbb.message_history (message_history_id, message_id, message_text, current_flag, ip_address_id) values
  (1000, 1000, 'Welcome to ZFGBB! Make yourself at home.',                 true, 1000),
  (1001, 1001, 'Be excellent to each other. Detailed rules will follow.', true, 1000),
  (1002, 1002, 'Hi everyone, this is Alice. Looking forward to chatting!', true, 1000),
  (1003, 1003, 'Hey Alice, Bob here. Welcome.',                            true, 1000),
  (1004, 1004, 'Carol checking in. Hi all.',                               true, 1000);

select setval(pg_get_serial_sequence('zfgbb.category',           'category_id'),           2000, false);
select setval(pg_get_serial_sequence('zfgbb.board',              'board_id'),              2000, false);
select setval(pg_get_serial_sequence('zfgbb.user',               'user_id'),               2000, false);
select setval(pg_get_serial_sequence('zfgbb.email_address',      'email_address_id'),      2000, false);
select setval(pg_get_serial_sequence('zfgbb.ip_address',         'ip_address_id'),         2000, false);
select setval(pg_get_serial_sequence('zfgbb.thread',             'thread_id'),             2000, false);
select setval(pg_get_serial_sequence('zfgbb.message',            'message_id'),            2000, false);
select setval(pg_get_serial_sequence('zfgbb.message_history',    'message_history_id'),    2000, false);
