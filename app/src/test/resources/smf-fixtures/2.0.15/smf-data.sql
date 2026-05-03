-- MySQL dump 10.13  Distrib 8.4.9, for Linux (x86_64)
--
-- Host: localhost    Database: smf
-- ------------------------------------------------------
-- Server version	8.4.9
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `smf_1attachments`
--
-- ORDER BY:  `id_attach`,`id_member`,`id_attach`

/*!40000 ALTER TABLE `smf_1attachments` DISABLE KEYS */;
INSERT INTO `smf_1attachments` (`id_attach`, `id_thumb`, `id_msg`, `id_member`, `id_folder`, `attachment_type`, `filename`, `file_hash`, `fileext`, `size`, `downloads`, `width`, `height`, `mime_type`, `approved`) VALUES (1,0,8,0,1,0,'favicon.png','ea50a9d916d3b3598d2d8a3347b7907dcce1d283','png',8399,2,64,64,'image/png',1);
/*!40000 ALTER TABLE `smf_1attachments` ENABLE KEYS */;

--
-- Dumping data for table `smf_1boards`
--
-- ORDER BY:  `id_board`,`id_cat`,`id_board`

/*!40000 ALTER TABLE `smf_1boards` DISABLE KEYS */;
INSERT INTO `smf_1boards` (`id_board`, `id_cat`, `child_level`, `id_parent`, `board_order`, `id_last_msg`, `id_msg_updated`, `member_groups`, `id_profile`, `name`, `description`, `num_topics`, `num_posts`, `count_posts`, `id_theme`, `override_theme`, `unapproved_posts`, `unapproved_topics`, `redirect`, `countMoney`, `is_redirect`, `redirect_clicks`, `redirect_count_clicks`, `redirect_target`, `redirect_url`) VALUES (1,1,0,0,3,10,10,'-1,0,2',1,'General Discussion','Feel free to talk about anything and everything in this board.',3,8,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
INSERT INTO `smf_1boards` (`id_board`, `id_cat`, `child_level`, `id_parent`, `board_order`, `id_last_msg`, `id_msg_updated`, `member_groups`, `id_profile`, `name`, `description`, `num_topics`, `num_posts`, `count_posts`, `id_theme`, `override_theme`, `unapproved_posts`, `unapproved_topics`, `redirect`, `countMoney`, `is_redirect`, `redirect_clicks`, `redirect_count_clicks`, `redirect_target`, `redirect_url`) VALUES (2,2,0,0,1,0,0,'-1,0,2,4,5,6,7,8',1,'New Board','',0,0,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
INSERT INTO `smf_1boards` (`id_board`, `id_cat`, `child_level`, `id_parent`, `board_order`, `id_last_msg`, `id_msg_updated`, `member_groups`, `id_profile`, `name`, `description`, `num_topics`, `num_posts`, `count_posts`, `id_theme`, `override_theme`, `unapproved_posts`, `unapproved_topics`, `redirect`, `countMoney`, `is_redirect`, `redirect_clicks`, `redirect_count_clicks`, `redirect_target`, `redirect_url`) VALUES (3,2,0,0,2,4,4,'2',1,'Staff Only','',2,2,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
/*!40000 ALTER TABLE `smf_1boards` ENABLE KEYS */;

--
-- Dumping data for table `smf_1categories`
--
-- ORDER BY:  `id_cat`

/*!40000 ALTER TABLE `smf_1categories` DISABLE KEYS */;
INSERT INTO `smf_1categories` (`id_cat`, `cat_order`, `name`, `can_collapse`) VALUES (1,1,'General Category',1);
INSERT INTO `smf_1categories` (`id_cat`, `cat_order`, `name`, `can_collapse`) VALUES (2,0,'New Category',1);
/*!40000 ALTER TABLE `smf_1categories` ENABLE KEYS */;

--
-- Dumping data for table `smf_1log_karma`
--
-- ORDER BY:  `id_target`,`id_executor`

/*!40000 ALTER TABLE `smf_1log_karma` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_karma` ENABLE KEYS */;

--
-- Dumping data for table `smf_1log_polls`
--

/*!40000 ALTER TABLE `smf_1log_polls` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_polls` ENABLE KEYS */;

--
-- Dumping data for table `smf_1members`
--
-- ORDER BY:  `id_member`

/*!40000 ALTER TABLE `smf_1members` DISABLE KEYS */;
INSERT INTO `smf_1members` (`id_member`, `member_name`, `date_registered`, `posts`, `id_group`, `lngfile`, `last_login`, `real_name`, `instant_messages`, `unread_messages`, `new_pm`, `buddy_list`, `pm_ignore_list`, `pm_prefs`, `mod_prefs`, `message_labels`, `passwd`, `openid_uri`, `email_address`, `personal_text`, `gender`, `birthdate`, `website_title`, `website_url`, `location`, `icq`, `aim`, `yim`, `msn`, `hide_email`, `show_online`, `time_format`, `signature`, `time_offset`, `avatar`, `pm_email_notify`, `karma_bad`, `karma_good`, `usertitle`, `notify_announcements`, `notify_regularity`, `notify_send_body`, `notify_types`, `member_ip`, `member_ip2`, `secret_question`, `secret_answer`, `id_theme`, `is_activated`, `validation_code`, `id_msg_last_visit`, `additional_groups`, `smiley_set`, `id_post_group`, `total_time_logged_in`, `password_salt`, `ignore_boards`, `warning`, `passwd_flood`, `pm_receive_from`, `is_spammer`, `warnLevel`) VALUES (1,'admin',1777693299,7,1,'',1777695250,'admin',2,1,1,'','',0,'','','dd94709528bb1c83d08f3088d4043f4742891f4f','','fake-email@not-real.fake.tld.thing','',0,'0001-01-01','','','','','','','',0,1,'','',0,'',0,0,0,'',1,1,0,2,'10.89.0.114','10.89.0.114','','',2,1,'',1,'','',4,1943,'02be','',0,'',1,0,0);
INSERT INTO `smf_1members` (`id_member`, `member_name`, `date_registered`, `posts`, `id_group`, `lngfile`, `last_login`, `real_name`, `instant_messages`, `unread_messages`, `new_pm`, `buddy_list`, `pm_ignore_list`, `pm_prefs`, `mod_prefs`, `message_labels`, `passwd`, `openid_uri`, `email_address`, `personal_text`, `gender`, `birthdate`, `website_title`, `website_url`, `location`, `icq`, `aim`, `yim`, `msn`, `hide_email`, `show_online`, `time_format`, `signature`, `time_offset`, `avatar`, `pm_email_notify`, `karma_bad`, `karma_good`, `usertitle`, `notify_announcements`, `notify_regularity`, `notify_send_body`, `notify_types`, `member_ip`, `member_ip2`, `secret_question`, `secret_answer`, `id_theme`, `is_activated`, `validation_code`, `id_msg_last_visit`, `additional_groups`, `smiley_set`, `id_post_group`, `total_time_logged_in`, `password_salt`, `ignore_boards`, `warning`, `passwd_flood`, `pm_receive_from`, `is_spammer`, `warnLevel`) VALUES (2,'random-person',1777695019,2,0,'',1777695222,'random-person',0,0,0,'','',0,'','','43b672b909cde1d1f5bfdd8f1576d5842f1e7664','','random-person@fake-email.fake.tld.thing','Personal Text',0,'2005-05-01','zfgc','http://www.zfgc.com','','','','','',1,1,'','signature ya',0,'Musicians/Linkin_Park.jpg',1,0,0,'',1,1,0,2,'10.89.0.114','10.89.0.114','','',0,1,'',8,'','',4,243,'4da4','',0,'',1,0,0);
/*!40000 ALTER TABLE `smf_1members` ENABLE KEYS */;

--
-- Dumping data for table `smf_1messages`
--
-- ORDER BY:  `id_msg`,`id_topic`,`id_msg`,`id_board`,`id_msg`,`id_member`,`id_msg`

/*!40000 ALTER TABLE `smf_1messages` DISABLE KEYS */;
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (1,1,1,1777693278,0,1,'Welcome to SMF!','Simple Machines','info@simplemachines.org','127.0.0.1',1,0,'','Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (2,2,1,1777693448,1,2,'Poll Thread','admin','basniakgm112@gmail.com','10.89.0.114',1,0,'','test','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (3,3,3,1777693465,1,3,'Sticky Thread Test','admin','basniakgm112@gmail.com','10.89.0.114',1,0,'','Test&nbsp; :D','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (4,4,3,1777693487,1,4,'Thread Test','admin','basniakgm112@gmail.com','10.89.0.114',1,0,'','test','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (5,5,1,1777693806,1,5,'BBCode','admin','basniakgm112@gmail.com','10.89.0.114',1,0,'','[b]Bold[/b]<br /><br />[i]Italics[/i]<br /><br />[u]Underline[/u]<br /><br />[s]Strikethrough[/s]<br /><br />[s][u][i][b]Bold, italics, underline, strikethrough combined[/b][/i][/u][/s]<br /><br />[pre]Preformatted text[/pre]<br /><br />[left]Left Align Text[/left]<br /><br />[center]Centered Text[/center]<br /><br />[right]Right Aligned[/right]<br /><br />Hyperlink<br />[url=https://youtu.be/l0sZPaz8lU4?t=1961&amp;si=G8AlSABRKJD_o0c6]https://youtu.be/l0sZPaz8lU4?t=1961&amp;si=G8AlSABRKJD_o0c6[/url]<br /><br />[glow=red,2,300]Red Glow Text[/glow]<br /><br />[shadow=red,left]Red Dropshadow Text[/shadow]<br /><br />[move]Marquee test[/move]<br /><br />[sup]Superscript test[/sup]<br />[sub]Subscript test[/sub]<br /><br />[tt]Teletype test[/tt]<br /><br />[table]<br />[tr]<br />[td]Simple table test[/td]<br />[/tr]<br />[/table]<br /><br /><br />[code]<br />function someFunction() {<br />&nbsp; console.log(&#039;code test&#039;)<br />}<br />[/code]<br /><br />[quote]<br />Quote test<br />[/quote]<br /><br />[list]<br />[li]Bullet list test[/li]<br />[li]Bullet list test[/li]<br />[/list]<br /><br />[list type=decimal]<br />[li]Decimal list test[/li]<br />[li]Decimal list test[/li]<br />[/list]<br /><br />Horizontal divider test<br />[hr]<br /><br />[size=36pt]Font Size Test[/size]<br /><br />[font=comic sans ms]Font Face Test[/font]<br /><br />[color=navy]Font Color Test[/color]<br />','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (6,5,1,1777693830,1,6,'Re: BBCode','admin','basniakgm112@gmail.com','10.89.0.114',1,0,'','Wow, much bbcode!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (7,5,1,1777694031,1,7,'Re: BBCode','admin','fake-email@not-real.fake.tld.thing','10.89.0.114',1,0,'','[img]http://localhost:8090//Themes/core/images/smflogo.gif[/img]','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (8,2,1,1777694259,1,8,'Re: Poll Thread','admin','fake-email@not-real.fake.tld.thing','10.89.0.114',1,0,'','attachment test','clip',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (9,2,1,1777695034,2,9,'Re: Poll Thread','random-person','random-person@fake-email.fake.tld.thing','10.89.0.114',1,0,'','[quote author=admin link=topic=2.msg8#msg8 date=1777694259]<br />attachment test<br />[/quote]<br /><br />I&#039;m quoting you!&nbsp; :P','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (10,5,1,1777695222,2,10,'Re: BBCode','random-person','random-person@fake-email.fake.tld.thing','10.89.0.114',1,0,'','I&#039;m linking the empty &quot;New Board&quot; here!<br /><br />[url=http://localhost:8090/index.php?board=2.0]http://localhost:8090/index.php?board=2.0[/url]','xx',1,NULL);
/*!40000 ALTER TABLE `smf_1messages` ENABLE KEYS */;

--
-- Dumping data for table `smf_1messages_history`
--
-- ORDER BY:  `id_edit`

/*!40000 ALTER TABLE `smf_1messages_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1messages_history` ENABLE KEYS */;

--
-- Dumping data for table `smf_1poll_choices`
--
-- ORDER BY:  `id_poll`,`id_choice`

/*!40000 ALTER TABLE `smf_1poll_choices` DISABLE KEYS */;
INSERT INTO `smf_1poll_choices` (`id_poll`, `id_choice`, `label`, `votes`) VALUES (1,0,'Option 1',0);
INSERT INTO `smf_1poll_choices` (`id_poll`, `id_choice`, `label`, `votes`) VALUES (1,1,'Option 2',0);
INSERT INTO `smf_1poll_choices` (`id_poll`, `id_choice`, `label`, `votes`) VALUES (1,2,'Option 3',0);
/*!40000 ALTER TABLE `smf_1poll_choices` ENABLE KEYS */;

--
-- Dumping data for table `smf_1polls`
--
-- ORDER BY:  `id_poll`

/*!40000 ALTER TABLE `smf_1polls` DISABLE KEYS */;
INSERT INTO `smf_1polls` (`id_poll`, `question`, `voting_locked`, `max_votes`, `expire_time`, `hide_results`, `change_vote`, `guest_vote`, `num_guest_voters`, `reset_poll`, `id_member`, `poster_name`, `ID_TOPIC`) VALUES (1,'Poll Question?',0,1,1778298248,0,1,0,0,0,1,'admin',0);
/*!40000 ALTER TABLE `smf_1polls` ENABLE KEYS */;

--
-- Dumping data for table `smf_1settings`
--
-- ORDER BY:  `variable`

/*!40000 ALTER TABLE `smf_1settings` DISABLE KEYS */;
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('admin_features','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_disableAnnounce','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_editDisplayName','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_guestAccess','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_hideOnline','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentCheckExtensions','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentDirSizeLimit','10240');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentEnable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentEncryptFilenames','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentExtensions','doc,gif,jpg,mpg,pdf,png,txt,zip');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentNumPerPostLimit','4');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentPostLimit','192');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentShowImages','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentSizeLimit','128');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentThumbHeight','150');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentThumbnails','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentThumbWidth','150');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentUploadDir','/var/www/html/attachments');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachment_image_paranoid','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachment_image_reencode','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachment_thumb_png','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('autoFixDatabase','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('autoLinkUrls','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('autoOptMaxOnline','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_action_too_large','option_html_resize');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_directory','/var/www/html/avatars');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_download_png','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_height_external','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_height_upload','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_width_external','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_width_upload','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_paranoid','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_reencode','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_resize_upload','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_url','http://localhost:8090/avatars');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('banLastUpdated','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('birthday_email','happy_birthday');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cache_enable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('calendar_updated','1777695162');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_daysaslink','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_days_for_index','7');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_defaultboard','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_enabled','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_maxspan','7');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_maxyear','2030');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_minyear','2008');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showbdays','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showevents','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showholidays','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showweeknum','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('censorIgnoreCase','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('censor_proper','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('censor_vulgar','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('compactTopicPagesContiguous','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('compactTopicPagesEnable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cookieTime','60');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('databaseSession_enable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('databaseSession_lifetime','2880');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('databaseSession_loose','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('defaultMaxMembers','30');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('defaultMaxMessages','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('defaultMaxTopics','20');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('default_personal_text','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('default_timezone','Etc/GMT0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('dont_repeat_buddylists','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('dont_repeat_smileys_20','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('dont_repeat_theme_core','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('edit_disable_time','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('edit_wait_time','90');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableAllMessages','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableBBC','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableCompressedOutput','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableEmbeddedFlash','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableErrorLogging','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableParticipation','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enablePostHTML','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enablePreviousNext','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableStickyTopics','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableVBStyleLogin','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enable_buddylist','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('failed_login_threshold','3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('fixLongWords','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('globalCookies','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('guest_hideContacts','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('hotTopicPosts','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('hotTopicVeryPosts','25');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaApplaudLabel','[applaud]');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaLabel','Karma:');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaMinPosts','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaMode','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaSmiteLabel','[smite]');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaTimeRestrictAdmins','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaWaitTime','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('knownThemes','1,2,3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('lastActive','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('last_mod_report_action','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('latestMember','2');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('latestRealName','random-person');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mail_next_send','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mail_recent','0000000000|0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mail_type','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('maxMsgID','10');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('max_image_height','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('max_image_width','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('max_messageLength','20000');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('memberlist_updated','1777695162');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostDate','1777695190');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostOnline','3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostOnlineToday','3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostOnlineUpdated','2026-05-02');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('news','SMF - Just Installed!');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('next_task_time','1777701600');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('number_format','1234.00');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('oldTopicDays','120');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('onlineEnable','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('package_make_backups','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('permission_enable_deny','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('permission_enable_postgroups','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('pm_spam_settings','10,5,20');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('pollMode','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('pruningOptions','30,180,180,180,30,0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('rand_seed','641294615');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('recycle_board','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('recycle_enable','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('registration_method','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reg_verification','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('requireAgreement','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveCase','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveName','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveNames','Admin\nWebmaster\nGuest\nroot');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveUser','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveWord','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_cache_size','50');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_floodcontrol_time','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_max_results','1200');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_results_per_page','30');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_age','25');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_first_message','10');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_frequency','30');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_length','20');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_subject','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('send_validation_onChange','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('send_welcomeEmail','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('settings_updated','1777693416');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('signature_settings','1,300,0,0,0,0,0,0:');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('simpleSearch','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smfVersion','2.0.15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smileys_dir','/var/www/html/Smileys');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smileys_url','http://localhost:8090/Smileys');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smiley_sets_default','default');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smiley_sets_known','default,aaron,akyhne');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smiley_sets_names','Alienine\'s Set\nAaron\'s Set\nAkyhne\'s Set');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_host','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_password','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_port','25');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_username','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('spamWaitTime','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('theme_allow','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('theme_default','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('theme_guests','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('timeLoadPageEnable','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('time_format','%B %d, %Y, %I:%M:%S %p');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('time_offset','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('titlesEnable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('todayMod','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('topicSummaryPosts','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('totalMembers','2');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('totalMessages','10');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('totalTopics','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('trackStats','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('unapprovedMembers','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('userLanguage','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('visual_verification_type','3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_moderate','35');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_mute','60');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_settings','1,20,0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_watch','10');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('who_enabled','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('xmlnews_enable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('xmlnews_maxlen','255');
/*!40000 ALTER TABLE `smf_1settings` ENABLE KEYS */;

--
-- Dumping data for table `smf_1topics`
--
-- ORDER BY:  `id_topic`,`id_last_msg`,`id_board`,`id_first_msg`,`id_board`,`id_poll`,`id_topic`

/*!40000 ALTER TABLE `smf_1topics` DISABLE KEYS */;
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (1,0,1,1,1,0,0,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (2,0,1,2,9,1,2,1,0,0,2,4,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (3,1,3,3,3,1,1,0,0,0,0,1,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (4,0,3,4,4,1,1,0,0,0,0,1,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (5,0,1,5,10,1,2,0,0,0,3,6,0,0,1);
/*!40000 ALTER TABLE `smf_1topics` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

