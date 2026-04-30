insert into smf_1members
    (id_member, member_name, real_name, email_address, passwd, is_activated,
     date_registered, gender, signature, personal_text, usertitle, avatar,
     birthdate, hide_email, show_online, karma_good, karma_bad, is_spammer)
values
    (1, 'alice', 'Alice Example', 'alice@example.com', 'pw_alice', 1,
     1700000000, 1, 'alice sig', 'about alice', 'alice title', '',
     '1990-01-15', 0, 1, 5, 1, 0),
    (2, 'bob', 'Bob Example', 'bob@example.com', 'pw_bob', 1,
     1700000100, 2, 'bob sig', 'about bob', 'bob title', '',
     '1985-06-20', 0, 1, 3, 0, 0);

insert into smf_1categories
    (id_cat, cat_order, name, can_collapse)
values
    (1, 0, 'General', 1),
    (2, 1, 'Off-Topic', 1);

insert into smf_1boards
    (id_board, id_cat, child_level, id_parent, board_order, name, description,
     num_topics, num_posts)
values
    (1, 1, 0, 0, 0, 'Announcements', 'Site announcements', 1, 3),
    (2, 1, 1, 1, 1, 'Sub-Board', 'Child of Announcements', 0, 0),
    (3, 2, 0, 0, 2, 'Random Chat', 'Talk about anything', 1, 2);

insert into smf_1topics
    (id_topic, is_sticky, id_board, id_first_msg, id_last_msg,
     id_member_started, id_member_updated, id_poll, num_replies, num_views,
     locked, approved)
values
    (1, 1, 1, 1, 3, 1, 1, 1, 2, 42, 0, 1),
    (2, 0, 3, 4, 5, 2, 1, 0, 1, 17, 0, 1);

insert into smf_1messages
    (id_msg, id_topic, id_board, poster_time, id_member, id_msg_modified,
     subject, poster_name, poster_email, poster_ip, body, icon, approved,
     modified_time, modified_name)
values
    (1, 1, 1, 1700100000, 1, 1700100500, 'Welcome',
     'alice', 'alice@example.com', '10.0.0.1',
     'Welcome to the forum!', 'xx', 1, 1700100500, 'alice'),
    (2, 1, 1, 1700100200, 2, 0, 'Re: Welcome',
     'bob', 'bob@example.com', '10.0.0.2',
     'Thanks alice!', 'xx', 1, 0, ''),
    (3, 1, 1, 1700100400, 1, 0, 'Re: Welcome',
     'alice', 'alice@example.com', '10.0.0.1',
     'Posting an attachment.', 'xx', 1, 0, ''),
    (4, 2, 3, 1700200000, 2, 1700200600, 'Random thought',
     'bob', 'bob@example.com', '10.0.0.2',
     'Something on my mind.', 'xx', 1, 1700200600, 'bob'),
    (5, 2, 3, 1700200300, 1, 0, 'Re: Random thought',
     'alice', 'alice@example.com', '10.0.0.1',
     'Image attached.', 'xx', 1, 0, '');

insert into smf_1messages_history
    (id_edit, id_msg, modified_name, modified_time, body)
values
    (1, 1, 'alice', 1700100500, 'Welcome to the forum!! (edited)'),
    (2, 4, 'bob', 1700200600, 'Something on my mind, more thoughts.');

insert into smf_1polls
    (id_poll, question, voting_locked, max_votes, expire_time, hide_results,
     change_vote, guest_vote, num_guest_voters, reset_poll, id_member,
     poster_name, ID_TOPIC)
values
    (1, 'Favorite color?', 0, 1, 0, 0, 1, 0, 0, 0, 1, 'alice', 1);

insert into smf_1poll_choices
    (id_poll, id_choice, label, votes)
values
    (1, 0, 'Red', 1),
    (1, 1, 'Blue', 1);

insert into smf_1log_polls
    (id_poll, id_member, id_choice)
values
    (1, 1, 0),
    (1, 2, 1);

insert into smf_1log_karma
    (id_target, id_executor, log_time, action, is_read, description, link)
values
    (2, 1, 1700101000, 1, 0, 'great post!', 'http://example.test/forum/?topic=1.msg2#msg2'),
    (1, 2, 1700200800, 1, 0, 'thanks for sharing', 'http://example.test/forum/?topic=2.msg5#msg5');

insert into smf_1attachments
    (id_attach, id_thumb, id_msg, id_member, id_folder, attachment_type,
     filename, file_hash, fileext, size, downloads, width, height, mime_type,
     approved)
values
    (1, 0, 3, 0, 1, 0,
     'hello.txt', 'abc123def456', 'txt', 5, 0, 0, 0, 'text/plain', 1),
    (2, 0, 5, 0, 1, 0,
     'sunset.png', 'fedcba654321', 'png', 12, 0, 320, 240, 'image/png', 1);
