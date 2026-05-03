#### ATTENTION: You do not need to run or use this file!  The install.php script does everything for you!
#### Install script for MySQL 4.0.18+

#
# Table structure for table `admin_info_files`
#

CREATE TABLE smf_1admin_info_files (
  id_file tinyint(4) unsigned NOT NULL auto_increment,
  filename varchar(255) NOT NULL default '',
  path varchar(255) NOT NULL default '',
  parameters varchar(255) NOT NULL default '',
  data text NOT NULL,
  filetype varchar(255) NOT NULL default '',
  PRIMARY KEY (id_file),
  KEY filename (filename(30))
) ENGINE=MyISAM;

#
# Dumping data for table `admin_info_files`
#

# --------------------------------------------------------

#
# Table structure for table `approval_queue`
#

CREATE TABLE smf_1approval_queue (
  id_msg int(10) unsigned NOT NULL default '0',
  id_attach int(10) unsigned NOT NULL default '0',
  id_event smallint(5) unsigned NOT NULL default '0'
) ENGINE=MyISAM;

#
# Table structure for table `attachments`
#

CREATE TABLE smf_1attachments (
  id_attach int(10) unsigned NOT NULL auto_increment,
  id_thumb int(10) unsigned NOT NULL default '0',
  id_msg int(10) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_folder tinyint(3) NOT NULL default '1',
  attachment_type tinyint(3) unsigned NOT NULL default '0',
  filename varchar(255) NOT NULL default '',
  file_hash varchar(40) NOT NULL default '',
  fileext varchar(8) NOT NULL default '',
  size int(10) unsigned NOT NULL default '0',
  downloads mediumint(8) unsigned NOT NULL default '0',
  width mediumint(8) unsigned NOT NULL default '0',
  height mediumint(8) unsigned NOT NULL default '0',
  mime_type varchar(20) NOT NULL default '',
  approved tinyint(3) NOT NULL default '1',
  PRIMARY KEY (id_attach),
  UNIQUE id_member (id_member, id_attach),
  KEY id_msg (id_msg),
  KEY attachment_type (attachment_type)
) ENGINE=MyISAM;

#
# Table structure for table `ban_groups`
#

CREATE TABLE smf_1ban_groups (
  id_ban_group mediumint(8) unsigned NOT NULL auto_increment,
  name varchar(20) NOT NULL default '',
  ban_time int(10) unsigned NOT NULL default '0',
  expire_time int(10) unsigned,
  cannot_access tinyint(3) unsigned NOT NULL default '0',
  cannot_register tinyint(3) unsigned NOT NULL default '0',
  cannot_post tinyint(3) unsigned NOT NULL default '0',
  cannot_login tinyint(3) unsigned NOT NULL default '0',
  reason varchar(255) NOT NULL default '',
  notes text NOT NULL,
  PRIMARY KEY (id_ban_group)
) ENGINE=MyISAM;

#
# Table structure for table `ban_items`
#

CREATE TABLE smf_1ban_items (
  id_ban mediumint(8) unsigned NOT NULL auto_increment,
  id_ban_group smallint(5) unsigned NOT NULL default '0',
  ip_low1 tinyint(3) unsigned NOT NULL default '0',
  ip_high1 tinyint(3) unsigned NOT NULL default '0',
  ip_low2 tinyint(3) unsigned NOT NULL default '0',
  ip_high2 tinyint(3) unsigned NOT NULL default '0',
  ip_low3 tinyint(3) unsigned NOT NULL default '0',
  ip_high3 tinyint(3) unsigned NOT NULL default '0',
  ip_low4 tinyint(3) unsigned NOT NULL default '0',
  ip_high4 tinyint(3) unsigned NOT NULL default '0',
  hostname varchar(255) NOT NULL default '',
  email_address varchar(255) NOT NULL default '',
  id_member mediumint(8) unsigned NOT NULL default '0',
  hits mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (id_ban),
  KEY id_ban_group (id_ban_group)
) ENGINE=MyISAM;

#
# Table structure for table `board_permissions`
#

CREATE TABLE smf_1board_permissions (
  id_group smallint(5) NOT NULL default '0',
  id_profile smallint(5) unsigned NOT NULL default '0',
  permission varchar(30) NOT NULL default '',
  add_deny tinyint(4) NOT NULL default '1',
  PRIMARY KEY (id_group, id_profile, permission)
) ENGINE=MyISAM;

#
# Dumping data for table `board_permissions`
#

# --------------------------------------------------------

#
# Table structure for table `boards`
#

CREATE TABLE smf_1boards (
  id_board smallint(5) unsigned NOT NULL auto_increment,
  id_cat tinyint(4) unsigned NOT NULL default '0',
  child_level tinyint(4) unsigned NOT NULL default '0',
  id_parent smallint(5) unsigned NOT NULL default '0',
  board_order smallint(5) NOT NULL default '0',
  id_last_msg int(10) unsigned NOT NULL default '0',
  id_msg_updated int(10) unsigned NOT NULL default '0',
  member_groups varchar(255) NOT NULL default '-1,0',
  id_profile smallint(5) unsigned NOT NULL default '1',
  name varchar(255) NOT NULL default '',
  description text NOT NULL,
  num_topics mediumint(8) unsigned NOT NULL default '0',
  num_posts mediumint(8) unsigned NOT NULL default '0',
  count_posts tinyint(4) NOT NULL default '0',
  id_theme tinyint(4) unsigned NOT NULL default '0',
  override_theme tinyint(4) unsigned NOT NULL default '0',
  unapproved_posts smallint(5) NOT NULL default '0',
  unapproved_topics smallint(5) NOT NULL default '0',
  redirect varchar(255) NOT NULL default '',
  PRIMARY KEY (id_board),
  UNIQUE categories (id_cat, id_board),
  KEY id_parent (id_parent),
  KEY id_msg_updated (id_msg_updated),
  KEY member_groups (member_groups(48))
) ENGINE=MyISAM;

#
# Dumping data for table `boards`
#

# --------------------------------------------------------

#
# Table structure for table `calendar`
#

CREATE TABLE smf_1calendar (
  id_event smallint(5) unsigned NOT NULL auto_increment,
  start_date date NOT NULL default '0001-01-01',
  end_date date NOT NULL default '0001-01-01',
  id_board smallint(5) unsigned NOT NULL default '0',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  title varchar(255) NOT NULL default '',
  id_member mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (id_event),
  KEY start_date (start_date),
  KEY end_date (end_date),
  KEY topic (id_topic, id_member)
) ENGINE=MyISAM;

#
# Table structure for table `calendar_holidays`
#

CREATE TABLE smf_1calendar_holidays (
  id_holiday smallint(5) unsigned NOT NULL auto_increment,
  event_date date NOT NULL default '0001-01-01',
  title varchar(255) NOT NULL default '',
  PRIMARY KEY (id_holiday),
  KEY event_date (event_date)
) ENGINE=MyISAM;

#
# Dumping data for table `calendar_holidays`
#


# --------------------------------------------------------

#
# Table structure for table `categories`
#

CREATE TABLE smf_1categories (
  id_cat tinyint(4) unsigned NOT NULL auto_increment,
  cat_order tinyint(4) NOT NULL default '0',
  name varchar(255) NOT NULL default '',
  can_collapse tinyint(1) NOT NULL default '1',
  PRIMARY KEY (id_cat)
) ENGINE=MyISAM;

#
# Dumping data for table `categories`
#

# --------------------------------------------------------

#
# Table structure for table `collapsed_categories`
#

CREATE TABLE smf_1collapsed_categories (
  id_cat tinyint(4) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (id_cat, id_member)
) ENGINE=MyISAM;

#
# Table structure for table `custom_fields`
#

CREATE TABLE smf_1custom_fields (
  id_field smallint(5) NOT NULL auto_increment,
  col_name varchar(12) NOT NULL default '',
  field_name varchar(40) NOT NULL default '',
  field_desc varchar(255) NOT NULL default '',
  field_type varchar(8) NOT NULL default 'text',
  field_length smallint(5) NOT NULL default '255',
  field_options text NOT NULL,
  mask varchar(255) NOT NULL default '',
  show_reg tinyint(3) NOT NULL default '0',
  show_display tinyint(3) NOT NULL default '0',
  show_profile varchar(20) NOT NULL default 'forumprofile',
  private tinyint(3) NOT NULL default '0',
  active tinyint(3) NOT NULL default '1',
  bbc tinyint(3) NOT NULL default '0',
  can_search tinyint(3) NOT NULL default '0',
  default_value varchar(255) NOT NULL default '',
  enclose text NOT NULL,
  placement tinyint(3) NOT NULL default '0',
  PRIMARY KEY (id_field),
  UNIQUE col_name (col_name)
) ENGINE=MyISAM;

#
# Table structure for table `group_moderators`
#

CREATE TABLE smf_1group_moderators (
  id_group smallint(5) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (id_group, id_member)
) ENGINE=MyISAM;

#
# Table structure for table `log_actions`
#

CREATE TABLE smf_1log_actions (
  id_action int(10) unsigned NOT NULL auto_increment,
  id_log tinyint(3) unsigned NOT NULL default '1',
  log_time int(10) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  ip char(16) NOT NULL default '                ',
  action varchar(30) NOT NULL default '',
  id_board smallint(5) unsigned NOT NULL default '0',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  id_msg int(10) unsigned NOT NULL default '0',
  extra text NOT NULL,
  PRIMARY KEY (id_action),
  KEY id_log (id_log),
  KEY log_time (log_time),
  KEY id_member (id_member),
  KEY id_board (id_board),
  KEY id_msg (id_msg)
) ENGINE=MyISAM;

#
# Table structure for table `log_activity`
#

CREATE TABLE smf_1log_activity (
  date date NOT NULL default '0001-01-01',
  hits mediumint(8) unsigned NOT NULL default '0',
  topics smallint(5) unsigned NOT NULL default '0',
  posts smallint(5) unsigned NOT NULL default '0',
  registers smallint(5) unsigned NOT NULL default '0',
  most_on smallint(5) unsigned NOT NULL default '0',
  PRIMARY KEY (date),
  KEY most_on (most_on)
) ENGINE=MyISAM;

#
# Table structure for table `log_banned`
#

CREATE TABLE smf_1log_banned (
  id_ban_log mediumint(8) unsigned NOT NULL auto_increment,
  id_member mediumint(8) unsigned NOT NULL default '0',
  ip char(16) NOT NULL default '                ',
  email varchar(255) NOT NULL default '',
  log_time int(10) unsigned NOT NULL default '0',
  PRIMARY KEY (id_ban_log),
  KEY log_time (log_time)
) ENGINE=MyISAM;

#
# Table structure for table `log_boards`
#

CREATE TABLE smf_1log_boards (
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_board smallint(5) unsigned NOT NULL default '0',
  id_msg int(10) unsigned NOT NULL default '0',
  PRIMARY KEY (id_member, id_board)
) ENGINE=MyISAM;

#
# Table structure for table `log_comments`
#

CREATE TABLE smf_1log_comments (
  id_comment mediumint(8) unsigned NOT NULL auto_increment,
  id_member mediumint(8) unsigned NOT NULL default '0',
  member_name varchar(80) NOT NULL default '',
  comment_type varchar(8) NOT NULL default 'warning',
  id_recipient mediumint(8) unsigned NOT NULL default '0',
  recipient_name varchar(255) NOT NULL default '',
  log_time int(10) NOT NULL default '0',
  id_notice mediumint(8) unsigned NOT NULL default '0',
  counter tinyint(3) NOT NULL default '0',
  body text NOT NULL,
  PRIMARY KEY (id_comment),
  KEY id_recipient (id_recipient),
  KEY log_time (log_time),
  KEY comment_type (comment_type(8))
) ENGINE=MyISAM;

#
# Table structure for table `log_digest`
#

CREATE TABLE smf_1log_digest (
  id_topic mediumint(8) unsigned NOT NULL,
  id_msg int(10) unsigned NOT NULL,
  note_type varchar(10) NOT NULL default 'post',
  daily tinyint(3) unsigned NOT NULL default '0',
  exclude mediumint(8) unsigned NOT NULL default '0'
) ENGINE=MyISAM;

#
# Table structure for table `log_errors`
#

CREATE TABLE smf_1log_errors (
  id_error mediumint(8) unsigned NOT NULL auto_increment,
  log_time int(10) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  ip char(16) NOT NULL default '                ',
  url text NOT NULL,
  message text NOT NULL,
  session char(32) NOT NULL default '                                ',
  error_type char(15) NOT NULL default 'general',
  file varchar(255) NOT NULL default '',
  line mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (id_error),
  KEY log_time (log_time),
  KEY id_member (id_member),
  KEY ip (ip(16))
) ENGINE=MyISAM;

#
# Table structure for table `log_floodcontrol`
#

CREATE TABLE smf_1log_floodcontrol (
  ip char(16) NOT NULL default '                ',
  log_time int(10) unsigned NOT NULL default '0',
  log_type varchar(8) NOT NULL default 'post',
  PRIMARY KEY (ip(16), log_type(8))
) ENGINE=MyISAM;

#
# Table structure for table `log_group_requests`
#

CREATE TABLE smf_1log_group_requests (
  id_request mediumint(8) unsigned NOT NULL auto_increment,
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_group smallint(5) unsigned NOT NULL default '0',
  time_applied int(10) unsigned NOT NULL default '0',
  reason text NOT NULL,
  PRIMARY KEY (id_request),
  UNIQUE id_member (id_member, id_group)
) ENGINE=MyISAM;

#
# Table structure for table `log_karma`
#

CREATE TABLE smf_1log_karma (
  id_target mediumint(8) unsigned NOT NULL default '0',
  id_executor mediumint(8) unsigned NOT NULL default '0',
  log_time int(10) unsigned NOT NULL default '0',
  action tinyint(4) NOT NULL default '0',
  PRIMARY KEY (id_target, id_executor),
  KEY log_time (log_time)
) ENGINE=MyISAM;

#
# Table structure for table `log_mark_read`
#

CREATE TABLE smf_1log_mark_read (
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_board smallint(5) unsigned NOT NULL default '0',
  id_msg int(10) unsigned NOT NULL default '0',
  PRIMARY KEY (id_member, id_board)
) ENGINE=MyISAM;

#
# Table structure for table `log_member_notices`
#

CREATE TABLE smf_1log_member_notices (
  id_notice mediumint(8) unsigned NOT NULL auto_increment,
  subject varchar(255) NOT NULL default '',
  body text NOT NULL,
  PRIMARY KEY (id_notice)
) ENGINE=MyISAM;

#
# Table structure for table `log_notify`
#

CREATE TABLE smf_1log_notify (
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  id_board smallint(5) unsigned NOT NULL default '0',
  sent tinyint(1) unsigned NOT NULL default '0',
  PRIMARY KEY (id_member, id_topic, id_board),
  KEY id_topic (id_topic, id_member)
) ENGINE=MyISAM;

#
# Table structure for table `log_online`
#

CREATE TABLE smf_1log_online (
  session varchar(32) NOT NULL default '',
  log_time int(10) NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_spider smallint(5) unsigned NOT NULL default '0',
  ip int(10) unsigned NOT NULL default '0',
  url text NOT NULL,
  PRIMARY KEY (session),
  KEY log_time (log_time),
  KEY id_member (id_member)
) ENGINE=MyISAM;

#
# Table structure for table `log_packages`
#

CREATE TABLE smf_1log_packages (
  id_install int(10) NOT NULL auto_increment,
  filename varchar(255) NOT NULL default '',
  package_id varchar(255) NOT NULL default '',
  name varchar(255) NOT NULL default '',
  version varchar(255) NOT NULL default '',
  id_member_installed mediumint(8) NOT NULL default '0',
  member_installed varchar(255) NOT NULL default '',
  time_installed int(10) NOT NULL default '0',
  id_member_removed mediumint(8) NOT NULL default '0',
  member_removed varchar(255) NOT NULL default '',
  time_removed int(10) NOT NULL default '0',
  install_state tinyint(3) NOT NULL default '1',
  failed_steps text NOT NULL,
  themes_installed varchar(255) NOT NULL default '',
  db_changes text NOT NULL,
  PRIMARY KEY (id_install),
  KEY filename (filename(15))
) ENGINE=MyISAM;

#
# Table structure for table `log_polls`
#

CREATE TABLE smf_1log_polls (
  id_poll mediumint(8) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_choice tinyint(3) unsigned NOT NULL default '0',
  KEY id_poll (id_poll, id_member, id_choice)
) ENGINE=MyISAM;

#
# Table structure for table `log_reported`
#

CREATE TABLE smf_1log_reported (
  id_report mediumint(8) unsigned NOT NULL auto_increment,
  id_msg int(10) unsigned NOT NULL default '0',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  id_board smallint(5) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  membername varchar(255) NOT NULL default '',
  subject varchar(255) NOT NULL default '',
  body text NOT NULL,
  time_started int(10) NOT NULL default '0',
  time_updated int(10) NOT NULL default '0',
  num_reports mediumint(6) NOT NULL default '0',
  closed tinyint(3) NOT NULL default '0',
  ignore_all tinyint(3) NOT NULL default '0',
  PRIMARY KEY (id_report),
  KEY id_member (id_member),
  KEY id_topic (id_topic),
  KEY closed (closed),
  KEY time_started (time_started),
  KEY id_msg (id_msg)
) ENGINE=MyISAM;

#
# Table structure for table `log_reported_comments`
#

CREATE TABLE smf_1log_reported_comments (
  id_comment mediumint(8) unsigned NOT NULL auto_increment,
  id_report mediumint(8) NOT NULL default '0',
  id_member mediumint(8) NOT NULL,
  membername varchar(255) NOT NULL default '',
  email_address varchar(255) NOT NULL default '',
  member_ip varchar(255) NOT NULL default '',
  comment varchar(255) NOT NULL default '',
  time_sent int(10) NOT NULL,
  PRIMARY KEY (id_comment),
  KEY id_report (id_report),
  KEY id_member (id_member),
  KEY time_sent (time_sent)
) ENGINE=MyISAM;

#
# Table structure for table `log_scheduled_tasks`
#

CREATE TABLE smf_1log_scheduled_tasks (
  id_log mediumint(8) NOT NULL auto_increment,
  id_task smallint(5) NOT NULL default '0',
  time_run int(10) NOT NULL default '0',
  time_taken float NOT NULL default '0',
  PRIMARY KEY (id_log)
) ENGINE=MyISAM;

#
# Table structure for table `log_search_messages`
#

CREATE TABLE smf_1log_search_messages (
  id_search tinyint(3) unsigned NOT NULL default '0',
  id_msg int(10) unsigned NOT NULL default '0',
  PRIMARY KEY (id_search, id_msg)
) ENGINE=MyISAM;

#
# Table structure for table `log_search_results`
#

CREATE TABLE smf_1log_search_results (
  id_search tinyint(3) unsigned NOT NULL default '0',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  id_msg int(10) unsigned NOT NULL default '0',
  relevance smallint(5) unsigned NOT NULL default '0',
  num_matches smallint(5) unsigned NOT NULL default '0',
  PRIMARY KEY (id_search, id_topic)
) ENGINE=MyISAM;

#
# Table structure for table `log_search_subjects`
#

CREATE TABLE smf_1log_search_subjects (
  word varchar(20) NOT NULL default '',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (word, id_topic),
  KEY id_topic (id_topic)
) ENGINE=MyISAM;

#
# Table structure for table `log_search_topics`
#

CREATE TABLE smf_1log_search_topics (
  id_search tinyint(3) unsigned NOT NULL default '0',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (id_search, id_topic)
) ENGINE=MyISAM;

#
# Table structure for table `log_spider_hits`
#

CREATE TABLE smf_1log_spider_hits (
	id_hit int(10) unsigned NOT NULL auto_increment,
  id_spider smallint(5) unsigned NOT NULL default '0',
  log_time int(10) unsigned NOT NULL default '0',
  url varchar(255) NOT NULL default '',
  processed tinyint(3) NOT NULL default '0',
  PRIMARY KEY (id_hit),
  KEY id_spider(id_spider),
  KEY log_time(log_time),
  KEY processed (processed)
) ENGINE=MyISAM;

#
# Table structure for table `log_spider_stats`
#

CREATE TABLE smf_1log_spider_stats (
  id_spider smallint(5) unsigned NOT NULL default '0',
  page_hits smallint(5) unsigned NOT NULL default '0',
  last_seen int(10) unsigned NOT NULL default '0',
  stat_date date NOT NULL default '0001-01-01',
  PRIMARY KEY (stat_date, id_spider)
) ENGINE=MyISAM;

#
# Table structure for table `log_subscribed`
#

CREATE TABLE smf_1log_subscribed (
  id_sublog int(10) unsigned NOT NULL auto_increment,
  id_subscribe mediumint(8) unsigned NOT NULL default '0',
  id_member int(10) NOT NULL default '0',
  old_id_group smallint(5) NOT NULL default '0',
  start_time int(10) NOT NULL default '0',
  end_time int(10) NOT NULL default '0',
  status tinyint(3) NOT NULL default '0',
  payments_pending tinyint(3) NOT NULL default '0',
  pending_details text NOT NULL,
  reminder_sent tinyint(3) NOT NULL default '0',
  vendor_ref varchar(255) NOT NULL default '',
  PRIMARY KEY (id_sublog),
  UNIQUE KEY id_subscribe (id_subscribe, id_member),
  KEY end_time (end_time),
  KEY reminder_sent (reminder_sent),
  KEY payments_pending (payments_pending),
  KEY status (status),
  KEY id_member (id_member)
) ENGINE=MyISAM;

#
# Table structure for table `log_topics`
#

CREATE TABLE smf_1log_topics (
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_topic mediumint(8) unsigned NOT NULL default '0',
  id_msg int(10) unsigned NOT NULL default '0',
  PRIMARY KEY (id_member, id_topic),
  KEY id_topic (id_topic)
) ENGINE=MyISAM;

#
# Table structure for table `mail_queue`
#

CREATE TABLE smf_1mail_queue (
  id_mail int(10) unsigned NOT NULL auto_increment,
  time_sent int(10) NOT NULL default '0',
  recipient varchar(255) NOT NULL default '',
  body text NOT NULL,
  subject varchar(255) NOT NULL default '',
  headers text NOT NULL,
  send_html tinyint(3) NOT NULL default '0',
  priority tinyint(3) NOT NULL default '1',
  private tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (id_mail),
  KEY time_sent (time_sent),
  KEY mail_priority (priority, id_mail)
) ENGINE=MyISAM;

#
# Table structure for table `membergroups`
#

CREATE TABLE smf_1membergroups (
  id_group smallint(5) unsigned NOT NULL auto_increment,
  group_name varchar(80) NOT NULL default '',
  description text NOT NULL,
  online_color varchar(20) NOT NULL default '',
  min_posts mediumint(9) NOT NULL default '-1',
  max_messages smallint(5) unsigned NOT NULL default '0',
  stars varchar(255) NOT NULL default '',
  group_type tinyint(3) NOT NULL default '0',
  hidden tinyint(3) NOT NULL default '0',
  id_parent smallint(5) NOT NULL default '-2',
  PRIMARY KEY (id_group),
  KEY min_posts (min_posts)
) ENGINE=MyISAM;

#
# Dumping data for table `membergroups`
#

# --------------------------------------------------------

#
# Table structure for table `members`
#

CREATE TABLE smf_1members (
  id_member mediumint(8) unsigned NOT NULL auto_increment,
  member_name varchar(80) NOT NULL default '',
  date_registered int(10) unsigned NOT NULL default '0',
  posts mediumint(8) unsigned NOT NULL default '0',
  id_group smallint(5) unsigned NOT NULL default '0',
  lngfile varchar(255) NOT NULL default '',
  last_login int(10) unsigned NOT NULL default '0',
  real_name varchar(255) NOT NULL default '',
  instant_messages smallint(5) NOT NULL default 0,
  unread_messages smallint(5) NOT NULL default 0,
  new_pm tinyint(3) unsigned NOT NULL default '0',
  buddy_list text NOT NULL,
  pm_ignore_list varchar(255) NOT NULL default '',
  pm_prefs mediumint(8) NOT NULL default '0',
  mod_prefs varchar(20) NOT NULL default '',
  message_labels text NOT NULL,
  passwd varchar(64) NOT NULL default '',
  openid_uri text NOT NULL,
  email_address varchar(255) NOT NULL default '',
  personal_text varchar(255) NOT NULL default '',
  gender tinyint(4) unsigned NOT NULL default '0',
  birthdate date NOT NULL default '0001-01-01',
  website_title varchar(255) NOT NULL default '',
  website_url varchar(255) NOT NULL default '',
  location varchar(255) NOT NULL default '',
  icq varchar(255) NOT NULL default '',
  aim varchar(255) NOT NULL default '',
  yim varchar(32) NOT NULL default '',
  msn varchar(255) NOT NULL default '',
  hide_email tinyint(4) NOT NULL default '0',
  show_online tinyint(4) NOT NULL default '1',
  time_format varchar(80) NOT NULL default '',
  signature text NOT NULL,
  time_offset float NOT NULL default '0',
  avatar varchar(255) NOT NULL default '',
  pm_email_notify tinyint(4) NOT NULL default '0',
  karma_bad smallint(5) unsigned NOT NULL default '0',
  karma_good smallint(5) unsigned NOT NULL default '0',
  usertitle varchar(255) NOT NULL default '',
  notify_announcements tinyint(4) NOT NULL default '1',
  notify_regularity tinyint(4) NOT NULL default '1',
  notify_send_body tinyint(4) NOT NULL default '0',
  notify_types tinyint(4) NOT NULL default '2',
  member_ip varchar(255) NOT NULL default '',
  member_ip2 varchar(255) NOT NULL default '',
  secret_question varchar(255) NOT NULL default '',
  secret_answer varchar(64) NOT NULL default '',
  id_theme tinyint(4) unsigned NOT NULL default '0',
  is_activated tinyint(3) unsigned NOT NULL default '1',
  validation_code varchar(10) NOT NULL default '',
  id_msg_last_visit int(10) unsigned NOT NULL default '0',
  additional_groups varchar(255) NOT NULL default '',
  smiley_set varchar(48) NOT NULL default '',
  id_post_group smallint(5) unsigned NOT NULL default '0',
  total_time_logged_in int(10) unsigned NOT NULL default '0',
  password_salt varchar(255) NOT NULL default '',
  ignore_boards text NOT NULL,
  warning tinyint(4) NOT NULL default '0',
  passwd_flood varchar(12) NOT NULL default '',
  pm_receive_from tinyint(4) unsigned NOT NULL default '1',
  PRIMARY KEY (id_member),
  KEY member_name (member_name),
  KEY real_name (real_name),
  KEY date_registered (date_registered),
  KEY id_group (id_group),
  KEY birthdate (birthdate),
  KEY posts (posts),
  KEY last_login (last_login),
  KEY lngfile (lngfile(30)),
  KEY id_post_group (id_post_group),
  KEY warning (warning),
  KEY total_time_logged_in (total_time_logged_in),
  KEY id_theme (id_theme)
) ENGINE=MyISAM;

#
# Table structure for table `message_icons`
#

CREATE TABLE smf_1message_icons (
  id_icon smallint(5) unsigned NOT NULL auto_increment,
  title varchar(80) NOT NULL default '',
  filename varchar(80) NOT NULL default '',
  id_board smallint(5) unsigned NOT NULL default '0',
  icon_order smallint(5) unsigned NOT NULL default '0',
  PRIMARY KEY (id_icon),
  KEY id_board (id_board)
) ENGINE=MyISAM;

#
# Dumping data for table `message_icons`
#

# // !!! i18n
# --------------------------------------------------------

#
# Table structure for table `messages`
#

CREATE TABLE smf_1messages (
  id_msg int(10) unsigned NOT NULL auto_increment,
  id_topic mediumint(8) unsigned NOT NULL default '0',
  id_board smallint(5) unsigned NOT NULL default '0',
  poster_time int(10) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  id_msg_modified int(10) unsigned NOT NULL default '0',
  subject varchar(255) NOT NULL default '',
  poster_name varchar(255) NOT NULL default '',
  poster_email varchar(255) NOT NULL default '',
  poster_ip varchar(255) NOT NULL default '',
  smileys_enabled tinyint(4) NOT NULL default '1',
  modified_time int(10) unsigned NOT NULL default '0',
  modified_name varchar(255) NOT NULL default '',
  body text NOT NULL,
  icon varchar(16) NOT NULL default 'xx',
  approved tinyint(3) NOT NULL default '1',
  PRIMARY KEY (id_msg),
  UNIQUE topic (id_topic, id_msg),
  UNIQUE id_board (id_board, id_msg),
  UNIQUE id_member (id_member, id_msg),
  KEY approved (approved),
  KEY ip_index (poster_ip(15), id_topic),
  KEY participation (id_member, id_topic),
  KEY show_posts (id_member, id_board),
  KEY id_topic (id_topic),
  KEY id_member_msg (id_member, approved, id_msg),
  KEY current_topic (id_topic, id_msg, id_member, approved),
  KEY related_ip (id_member, poster_ip, id_msg)
) ENGINE=MyISAM;

#
# Dumping data for table `messages`
#

# --------------------------------------------------------

#
# Table structure for table `moderators`
#

CREATE TABLE smf_1moderators (
  id_board smallint(5) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  PRIMARY KEY (id_board, id_member)
) ENGINE=MyISAM;

#
# Table structure for table `openid_assoc`
#

CREATE TABLE smf_1openid_assoc (
  server_url text NOT NULL,
  handle varchar(255) NOT NULL default '',
  secret text NOT NULL,
  issued int(10) NOT NULL default '0',
  expires int(10) NOT NULL default '0',
  assoc_type varchar(64) NOT NULL,
  PRIMARY KEY (server_url(125), handle(125)),
  KEY expires (expires)
) ENGINE=MyISAM;

#
# Table structure for table `package_servers`
#

CREATE TABLE smf_1package_servers (
  id_server smallint(5) unsigned NOT NULL auto_increment,
  name varchar(255) NOT NULL default '',
  url varchar(255) NOT NULL default '',
  PRIMARY KEY (id_server)
) ENGINE=MyISAM;

#
# Dumping data for table `package_servers`
#

# --------------------------------------------------------

#
# Table structure for table `permission_profiles`
#

CREATE TABLE smf_1permission_profiles (
  id_profile smallint(5) NOT NULL auto_increment,
  profile_name varchar(255) NOT NULL default '',
  PRIMARY KEY (id_profile)
) ENGINE=MyISAM;

#
# Dumping data for table `permission_profiles`
#

# --------------------------------------------------------

#
# Table structure for table `permissions`
#

CREATE TABLE smf_1permissions (
  id_group smallint(5) NOT NULL default '0',
  permission varchar(30) NOT NULL default '',
  add_deny tinyint(4) NOT NULL default '1',
  PRIMARY KEY (id_group, permission)
) ENGINE=MyISAM;

#
# Dumping data for table `permissions`
#

# --------------------------------------------------------

#
# Table structure for table `personal_messages`
#

CREATE TABLE smf_1personal_messages (
  id_pm int(10) unsigned NOT NULL auto_increment,
  id_pm_head int(10) unsigned NOT NULL default '0',
  id_member_from mediumint(8) unsigned NOT NULL default '0',
  deleted_by_sender tinyint(3) unsigned NOT NULL default '0',
  from_name varchar(255) NOT NULL default '',
  msgtime int(10) unsigned NOT NULL default '0',
  subject varchar(255) NOT NULL default '',
  body text NOT NULL,
  PRIMARY KEY (id_pm),
  KEY id_member (id_member_from, deleted_by_sender),
  KEY msgtime (msgtime),
  KEY id_pm_head (id_pm_head)
) ENGINE=MyISAM;

#
# Table structure for table `pm_recipients`
#

CREATE TABLE smf_1pm_recipients (
  id_pm int(10) unsigned NOT NULL default '0',
  id_member mediumint(8) unsigned NOT NULL default '0',
  labels varchar(60) NOT NULL default '-1',
  bcc tinyint(3) unsigned NOT NULL default '0',
  is_read tinyint(3) unsigned NOT NULL default '0',
  is_new tinyint(3) unsigned NOT NULL default '0',
  deleted tinyint(3) unsigned NOT NULL default '0',
  PRIMARY KEY (id_pm, id_member),
  UNIQUE id_member (id_member, deleted, id_pm)
) ENGINE=MyISAM;

#
# Table structure for table `pm_rules`
#

CREATE TABLE smf_1pm_rules (
  id_rule int(10) unsigned NOT NULL auto_increment,
  id_member int(10) unsigned NOT NULL default '0',
  rule_name varchar(60) NOT NULL,
  criteria text NOT NULL,
  actions text NOT NULL,
  delete_pm tinyint(3) unsigned NOT NULL default '0',
  is_or tinyint(3) unsigned NOT NULL default '0',
  PRIMARY KEY (id_rule),
  KEY id_member (id_member),
  KEY delete_pm (delete_pm)
) ENGINE=MyISAM;

#
# Table structure for table `polls`
#

CREATE TABLE smf_1polls (
  id_poll mediumint(8) unsigned NOT NULL auto_increment,
  question varchar(255) NOT NULL default '',
  voting_locked tinyint(1) NOT NULL default '0',
  max_votes tinyint(3) unsigned NOT NULL default '1',
  expire_time int(10) unsigned NOT NULL default '0',
  hide_results tinyint(3) unsigned NOT NULL default '0',
  change_vote tinyint(3) unsigned NOT NULL default '0',
  guest_vote tinyint(3) unsigned NOT NULL default '0',
  num_guest_voters int(10) unsigned NOT NULL default '0',
  reset_poll int(10) unsigned NOT NULL default '0',
  id_member mediumint(8) NOT NULL default '0',
  poster_name varchar(255) NOT NULL default '',
  PRIMARY KEY (id_poll)
) ENGINE=MyISAM;

#
# Table structure for table `poll_choices`
#

CREATE TABLE smf_1poll_choices (
  id_poll mediumint(8) unsigned NOT NULL default '0',
  id_choice tinyint(3) unsigned NOT NULL default '0',
  label varchar(255) NOT NULL default '',
  votes smallint(5) unsigned NOT NULL default '0',
  PRIMARY KEY (id_poll, id_choice)
) ENGINE=MyISAM;

#
# Table structure for table `scheduled_tasks`
#

CREATE TABLE smf_1scheduled_tasks (
  id_task smallint(5) NOT NULL auto_increment,
  next_time int(10) NOT NULL default '0',
  time_offset int(10) NOT NULL default '0',
  time_regularity smallint(5) NOT NULL default '0',
  time_unit varchar(1) NOT NULL default 'h',
  disabled tinyint(3) NOT NULL default '0',
  task varchar(24) NOT NULL default '',
  PRIMARY KEY (id_task),
  KEY next_time (next_time),
  KEY disabled (disabled),
  UNIQUE task (task)
) ENGINE=MyISAM;

#
# Dumping data for table `scheduled_tasks`
#


# --------------------------------------------------------

#
# Table structure for table `settings`
#

CREATE TABLE smf_1settings (
  variable varchar(255) NOT NULL default '',
  value text NOT NULL,
  PRIMARY KEY (variable(30))
) ENGINE=MyISAM;

#
# Dumping data for table `settings`
#

# --------------------------------------------------------

#
# Table structure for table `sessions`
#

CREATE TABLE smf_1sessions (
  session_id char(32) NOT NULL,
  last_update int(10) unsigned NOT NULL,
  data text NOT NULL,
  PRIMARY KEY (session_id)
) ENGINE=MyISAM;

#
# Table structure for table `smileys`
#

CREATE TABLE smf_1smileys (
  id_smiley smallint(5) unsigned NOT NULL auto_increment,
  code varchar(30) NOT NULL default '',
  filename varchar(48) NOT NULL default '',
  description varchar(80) NOT NULL default '',
  smiley_row tinyint(4) unsigned NOT NULL default '0',
  smiley_order smallint(5) unsigned NOT NULL default '0',
  hidden tinyint(4) unsigned NOT NULL default '0',
  PRIMARY KEY (id_smiley)
) ENGINE=MyISAM;

#
# Dumping data for table `smileys`
#

# --------------------------------------------------------

#
# Table structure for table `spiders`
#

CREATE TABLE smf_1spiders (
  id_spider smallint(5) unsigned NOT NULL auto_increment,
  spider_name varchar(255) NOT NULL default '',
  user_agent varchar(255) NOT NULL default '',
  ip_info varchar(255) NOT NULL default '',
  PRIMARY KEY id_spider(id_spider)
) ENGINE=MyISAM;

#
# Dumping data for table `spiders`
#


#
# Table structure for table `subscriptions`
#

CREATE TABLE smf_1subscriptions(
  id_subscribe mediumint(8) unsigned NOT NULL auto_increment,
  name varchar(60) NOT NULL default '',
  description varchar(255) NOT NULL default '',
  cost text NOT NULL,
  length varchar(6) NOT NULL default '',
  id_group smallint(5) NOT NULL default '0',
  add_groups varchar(40) NOT NULL default '',
  active tinyint(3) NOT NULL default '1',
  repeatable tinyint(3) NOT NULL default '0',
  allow_partial tinyint(3) NOT NULL default '0',
  reminder tinyint(3) NOT NULL default '0',
  email_complete text NOT NULL,
  PRIMARY KEY (id_subscribe),
  KEY active (active)
) ENGINE=MyISAM;

#
# Table structure for table `themes`
#

CREATE TABLE smf_1themes (
  id_member mediumint(8) NOT NULL default '0',
  id_theme tinyint(4) unsigned NOT NULL default '1',
  variable varchar(255) NOT NULL default '',
  value text NOT NULL,
  PRIMARY KEY (id_theme, id_member, variable(30)),
  KEY id_member (id_member)
) ENGINE=MyISAM;

#
# Dumping data for table `themes`
#


# --------------------------------------------------------

#
# Table structure for table `topics`
#

CREATE TABLE smf_1topics (
  id_topic mediumint(8) unsigned NOT NULL auto_increment,
  is_sticky tinyint(4) NOT NULL default '0',
  id_board smallint(5) unsigned NOT NULL default '0',
  id_first_msg int(10) unsigned NOT NULL default '0',
  id_last_msg int(10) unsigned NOT NULL default '0',
  id_member_started mediumint(8) unsigned NOT NULL default '0',
  id_member_updated mediumint(8) unsigned NOT NULL default '0',
  id_poll mediumint(8) unsigned NOT NULL default '0',
  id_previous_board smallint(5) NOT NULL default '0',
  id_previous_topic mediumint(8) NOT NULL default '0',
  num_replies int(10) unsigned NOT NULL default '0',
  num_views int(10) unsigned NOT NULL default '0',
  locked tinyint(4) NOT NULL default '0',
  unapproved_posts smallint(5) NOT NULL default '0',
  approved tinyint(3) NOT NULL default '1',
  PRIMARY KEY (id_topic),
  UNIQUE last_message (id_last_msg, id_board),
  UNIQUE first_message (id_first_msg, id_board),
  UNIQUE poll (id_poll, id_topic),
  KEY is_sticky (is_sticky),
  KEY approved (approved),
  KEY id_board (id_board),
  KEY member_started (id_member_started, id_board),
  KEY last_message_sticky (id_board, is_sticky, id_last_msg),
  KEY board_news (id_board, id_first_msg)
) ENGINE=MyISAM;

#
# Dumping data for table `topics`
#

# --------------------------------------------------------

