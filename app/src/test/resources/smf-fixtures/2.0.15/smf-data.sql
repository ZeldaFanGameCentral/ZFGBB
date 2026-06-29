-- MySQL dump 10.13  Distrib 5.7.44, for Linux (x86_64)
--
-- Host: localhost    Database: smf
-- ------------------------------------------------------
-- Server version	5.7.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `smf_1admin_info_files`
--

/*!40000 ALTER TABLE `smf_1admin_info_files` DISABLE KEYS */;
INSERT INTO `smf_1admin_info_files` (`id_file`, `filename`, `path`, `parameters`, `data`, `filetype`) VALUES (1,'current-version.js','/smf/','version=%3$s','','text/javascript');
INSERT INTO `smf_1admin_info_files` (`id_file`, `filename`, `path`, `parameters`, `data`, `filetype`) VALUES (2,'detailed-version.js','/smf/','language=%1$s&version=%3$s','','text/javascript');
INSERT INTO `smf_1admin_info_files` (`id_file`, `filename`, `path`, `parameters`, `data`, `filetype`) VALUES (3,'latest-news.js','/smf/','language=%1$s&format=%2$s','','text/javascript');
INSERT INTO `smf_1admin_info_files` (`id_file`, `filename`, `path`, `parameters`, `data`, `filetype`) VALUES (4,'latest-packages.js','/smf/','language=%1$s&version=%3$s','','text/javascript');
INSERT INTO `smf_1admin_info_files` (`id_file`, `filename`, `path`, `parameters`, `data`, `filetype`) VALUES (5,'latest-smileys.js','/smf/','language=%1$s&version=%3$s','','text/javascript');
INSERT INTO `smf_1admin_info_files` (`id_file`, `filename`, `path`, `parameters`, `data`, `filetype`) VALUES (6,'latest-support.js','/smf/','language=%1$s&version=%3$s','','text/javascript');
INSERT INTO `smf_1admin_info_files` (`id_file`, `filename`, `path`, `parameters`, `data`, `filetype`) VALUES (7,'latest-themes.js','/smf/','language=%1$s&version=%3$s','','text/javascript');
/*!40000 ALTER TABLE `smf_1admin_info_files` ENABLE KEYS */;

--
-- Dumping data for table `smf_1attachments`
--

/*!40000 ALTER TABLE `smf_1attachments` DISABLE KEYS */;
INSERT INTO `smf_1attachments` (`id_attach`, `id_thumb`, `id_msg`, `id_member`, `id_folder`, `attachment_type`, `filename`, `file_hash`, `fileext`, `size`, `downloads`, `width`, `height`, `mime_type`, `approved`) VALUES (1,0,6,0,1,0,'favicon_old.gif','3d1160084695ad900f62700576ff0ec0ea3b8700','gif',5597,1,16,16,'image/gif',1);
INSERT INTO `smf_1attachments` (`id_attach`, `id_thumb`, `id_msg`, `id_member`, `id_folder`, `attachment_type`, `filename`, `file_hash`, `fileext`, `size`, `downloads`, `width`, `height`, `mime_type`, `approved`) VALUES (2,0,0,3,1,0,'avatar_3_1783863270.gif','a47887b4bed8ea42bb8550a8aec69955c27c61df','gif',5597,222,16,16,'image/gif',1);
INSERT INTO `smf_1attachments` (`id_attach`, `id_thumb`, `id_msg`, `id_member`, `id_folder`, `attachment_type`, `filename`, `file_hash`, `fileext`, `size`, `downloads`, `width`, `height`, `mime_type`, `approved`) VALUES (3,0,16,0,1,0,'favicon.png','cbbec572c69b428ff3e7e6a4c848b5b2a8d13ff4','png',8399,0,64,64,'image/png',1);
/*!40000 ALTER TABLE `smf_1attachments` ENABLE KEYS */;

--
-- Dumping data for table `smf_1board_permissions`
--

/*!40000 ALTER TABLE `smf_1board_permissions` DISABLE KEYS */;
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (-1,1,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'remove_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'poll_add_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'poll_edit_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'poll_lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,1,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,1,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,1,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (-1,2,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'remove_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,2,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,2,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,2,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (-1,3,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'remove_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,3,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,3,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,3,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (-1,4,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,4,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,4,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,4,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,4,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,4,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,4,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (0,4,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (2,4,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'approve_posts',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (3,4,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_edit_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_add_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'modify_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'delete_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'announce_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'delete_replies',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'modify_replies',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'move_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'move_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'make_sticky',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'split_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'merge_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'moderate_board',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'remove_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,2,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'lock_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'post_attachment',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'remove_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,3,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,4,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,4,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,4,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,4,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,4,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,4,'send_topic',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,4,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_lock_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_remove_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_new',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_reply_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_reply_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_unapproved_topics',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_unapproved_replies_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_unapproved_replies_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_unapproved_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'remove_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'delete_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'modify_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_edit_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'report_any',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_view',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_vote',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'view_attachments',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_post',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'poll_add_own',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'mark_any_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'mark_notify',1);
INSERT INTO `smf_1board_permissions` (`id_group`, `id_profile`, `permission`, `add_deny`) VALUES (9,1,'post_attachment',1);
/*!40000 ALTER TABLE `smf_1board_permissions` ENABLE KEYS */;

--
-- Dumping data for table `smf_1boards`
--

/*!40000 ALTER TABLE `smf_1boards` DISABLE KEYS */;
INSERT INTO `smf_1boards` (`id_board`, `id_cat`, `child_level`, `id_parent`, `board_order`, `id_last_msg`, `id_msg_updated`, `member_groups`, `id_profile`, `name`, `description`, `num_topics`, `num_posts`, `count_posts`, `id_theme`, `override_theme`, `unapproved_posts`, `unapproved_topics`, `redirect`, `countMoney`, `is_redirect`, `redirect_clicks`, `redirect_count_clicks`, `redirect_target`, `redirect_url`) VALUES (1,1,0,0,2,185,185,'-1,0,2,9',1,'General Discussion','Feel free to talk about anything and everything in this board.',11,160,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
INSERT INTO `smf_1boards` (`id_board`, `id_cat`, `child_level`, `id_parent`, `board_order`, `id_last_msg`, `id_msg_updated`, `member_groups`, `id_profile`, `name`, `description`, `num_topics`, `num_posts`, `count_posts`, `id_theme`, `override_theme`, `unapproved_posts`, `unapproved_topics`, `redirect`, `countMoney`, `is_redirect`, `redirect_clicks`, `redirect_count_clicks`, `redirect_target`, `redirect_url`) VALUES (2,2,0,0,1,184,184,'-1,0,2,4,5,6,7,8,9',1,'New Board','',23,24,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
INSERT INTO `smf_1boards` (`id_board`, `id_cat`, `child_level`, `id_parent`, `board_order`, `id_last_msg`, `id_msg_updated`, `member_groups`, `id_profile`, `name`, `description`, `num_topics`, `num_posts`, `count_posts`, `id_theme`, `override_theme`, `unapproved_posts`, `unapproved_topics`, `redirect`, `countMoney`, `is_redirect`, `redirect_clicks`, `redirect_count_clicks`, `redirect_target`, `redirect_url`) VALUES (4,1,0,0,3,0,0,'-1,0,2,9',1,'Projects','Discussion threads for CMS projects.',0,0,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
INSERT INTO `smf_1boards` (`id_board`, `id_cat`, `child_level`, `id_parent`, `board_order`, `id_last_msg`, `id_msg_updated`, `member_groups`, `id_profile`, `name`, `description`, `num_topics`, `num_posts`, `count_posts`, `id_theme`, `override_theme`, `unapproved_posts`, `unapproved_topics`, `redirect`, `countMoney`, `is_redirect`, `redirect_clicks`, `redirect_count_clicks`, `redirect_target`, `redirect_url`) VALUES (5,1,0,0,4,0,0,'-1,0,2,9',1,'Resources','Discussion threads for CMS resources.',0,0,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
/*!40000 ALTER TABLE `smf_1boards` ENABLE KEYS */;

--
-- Dumping data for table `smf_1calendar_holidays`
--

/*!40000 ALTER TABLE `smf_1calendar_holidays` DISABLE KEYS */;
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (1,'0004-01-01','New Year\'s');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (2,'0004-12-25','Christmas');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (3,'0004-02-14','Valentine\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (4,'0004-03-17','St. Patrick\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (5,'0004-04-01','April Fools');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (6,'0004-04-22','Earth Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (7,'0004-10-24','United Nations Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (8,'0004-10-31','Halloween');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (9,'2010-05-09','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (10,'2011-05-08','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (11,'2012-05-13','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (12,'2013-05-12','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (13,'2014-05-11','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (14,'2015-05-10','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (15,'2016-05-08','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (16,'2017-05-14','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (17,'2018-05-13','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (18,'2019-05-12','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (19,'2020-05-10','Mother\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (20,'2008-06-15','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (21,'2009-06-21','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (22,'2010-06-20','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (23,'2011-06-19','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (24,'2012-06-17','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (25,'2013-06-16','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (26,'2014-06-15','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (27,'2015-06-21','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (28,'2016-06-19','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (29,'2017-06-18','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (30,'2018-06-17','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (31,'2019-06-16','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (32,'2020-06-21','Father\'s Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (33,'2010-06-21','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (34,'2011-06-21','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (35,'2012-06-20','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (36,'2013-06-21','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (37,'2014-06-21','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (38,'2015-06-21','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (39,'2016-06-20','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (40,'2017-06-20','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (41,'2018-06-21','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (42,'2019-06-21','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (43,'2020-06-20','Summer Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (44,'2010-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (45,'2011-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (46,'2012-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (47,'2013-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (48,'2014-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (49,'2015-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (50,'2016-03-19','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (51,'2017-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (52,'2018-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (53,'2019-03-20','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (54,'2020-03-19','Vernal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (55,'2010-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (56,'2011-12-22','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (57,'2012-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (58,'2013-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (59,'2014-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (60,'2015-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (61,'2016-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (62,'2017-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (63,'2018-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (64,'2019-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (65,'2020-12-21','Winter Solstice');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (66,'2010-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (67,'2011-09-23','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (68,'2012-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (69,'2013-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (70,'2014-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (71,'2015-09-23','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (72,'2016-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (73,'2017-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (74,'2018-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (75,'2019-09-23','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (76,'2020-09-22','Autumnal Equinox');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (77,'0004-07-04','Independence Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (78,'0004-05-05','Cinco de Mayo');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (79,'0004-06-14','Flag Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (80,'0004-11-11','Veterans Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (81,'0004-02-02','Groundhog Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (82,'2010-11-25','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (83,'2011-11-24','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (84,'2012-11-22','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (85,'2013-11-28','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (86,'2014-11-27','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (87,'2015-11-26','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (88,'2016-11-24','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (89,'2017-11-23','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (90,'2018-11-22','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (91,'2019-11-28','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (92,'2020-11-26','Thanksgiving');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (93,'2010-05-31','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (94,'2011-05-30','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (95,'2012-05-28','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (96,'2013-05-27','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (97,'2014-05-26','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (98,'2015-05-25','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (99,'2016-05-30','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (100,'2017-05-29','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (101,'2018-05-28','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (102,'2019-05-27','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (103,'2020-05-25','Memorial Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (104,'2010-09-06','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (105,'2011-09-05','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (106,'2012-09-03','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (107,'2013-09-02','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (108,'2014-09-01','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (109,'2015-09-07','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (110,'2016-09-05','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (111,'2017-09-04','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (112,'2018-09-03','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (113,'2019-09-02','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (114,'2020-09-07','Labor Day');
INSERT INTO `smf_1calendar_holidays` (`id_holiday`, `event_date`, `title`) VALUES (115,'0004-06-06','D-Day');
/*!40000 ALTER TABLE `smf_1calendar_holidays` ENABLE KEYS */;

--
-- Dumping data for table `smf_1categories`
--

/*!40000 ALTER TABLE `smf_1categories` DISABLE KEYS */;
INSERT INTO `smf_1categories` (`id_cat`, `cat_order`, `name`, `can_collapse`) VALUES (1,1,'General Category',1);
INSERT INTO `smf_1categories` (`id_cat`, `cat_order`, `name`, `can_collapse`) VALUES (2,0,'New Category',1);
/*!40000 ALTER TABLE `smf_1categories` ENABLE KEYS */;

--
-- Dumping data for table `smf_1log_actions`
--

/*!40000 ALTER TABLE `smf_1log_actions` DISABLE KEYS */;
INSERT INTO `smf_1log_actions` (`id_action`, `id_log`, `log_time`, `id_member`, `ip`, `action`, `id_board`, `id_topic`, `id_msg`, `extra`) VALUES (1,1,1783870500,1,'10.89.3.12','lock',1,6,0,'a:0:{}');
/*!40000 ALTER TABLE `smf_1log_actions` ENABLE KEYS */;

--
-- Dumping data for table `smf_1log_comments`
--

/*!40000 ALTER TABLE `smf_1log_comments` DISABLE KEYS */;
INSERT INTO `smf_1log_comments` (`id_comment`, `id_member`, `member_name`, `comment_type`, `id_recipient`, `recipient_name`, `log_time`, `id_notice`, `counter`, `body`) VALUES (1,2,'mgzero','warning',3,'gm112',1783870000,0,20,'farted in the room');
/*!40000 ALTER TABLE `smf_1log_comments` ENABLE KEYS */;

--
-- Dumping data for table `smf_1log_karma`
--

/*!40000 ALTER TABLE `smf_1log_karma` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_karma` ENABLE KEYS */;

--
-- Dumping data for table `smf_1log_notify`
--

/*!40000 ALTER TABLE `smf_1log_notify` DISABLE KEYS */;
INSERT INTO `smf_1log_notify` (`id_member`, `id_topic`, `id_board`, `sent`) VALUES (3,0,1,0);
INSERT INTO `smf_1log_notify` (`id_member`, `id_topic`, `id_board`, `sent`) VALUES (2,0,2,0);
INSERT INTO `smf_1log_notify` (`id_member`, `id_topic`, `id_board`, `sent`) VALUES (3,1,0,0);
INSERT INTO `smf_1log_notify` (`id_member`, `id_topic`, `id_board`, `sent`) VALUES (2,2,0,0);
INSERT INTO `smf_1log_notify` (`id_member`, `id_topic`, `id_board`, `sent`) VALUES (3,3,0,0);
/*!40000 ALTER TABLE `smf_1log_notify` ENABLE KEYS */;

--
-- Dumping data for table `smf_1log_polls`
--

/*!40000 ALTER TABLE `smf_1log_polls` DISABLE KEYS */;
INSERT INTO `smf_1log_polls` (`id_poll`, `id_member`, `id_choice`) VALUES (1,1,2);
INSERT INTO `smf_1log_polls` (`id_poll`, `id_member`, `id_choice`) VALUES (1,3,2);
INSERT INTO `smf_1log_polls` (`id_poll`, `id_member`, `id_choice`) VALUES (1,4,1);
/*!40000 ALTER TABLE `smf_1log_polls` ENABLE KEYS */;

--
-- Dumping data for table `smf_1membergroups`
--

/*!40000 ALTER TABLE `smf_1membergroups` DISABLE KEYS */;
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (1,'Administrator','','#FF0000',-1,0,'5#staradmin.gif',1,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (2,'Global Moderator','','#0000FF',-1,0,'5#stargmod.gif',0,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (3,'Moderator','','',-1,0,'5#starmod.gif',0,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (4,'Newbie','','',0,0,'1#star.gif',0,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (5,'Jr. Member','','',50,0,'2#star.gif',0,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (6,'Full Member','','',100,0,'3#star.gif',0,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (7,'Sr. Member','','',250,0,'4#star.gif',0,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (8,'Hero Member','','',500,0,'5#star.gif',0,0,-2,'',0);
INSERT INTO `smf_1membergroups` (`id_group`, `group_name`, `description`, `online_color`, `min_posts`, `max_messages`, `stars`, `group_type`, `hidden`, `id_parent`, `GroupModOptions`, `monitorGroup`) VALUES (9,'Wiki Moderator','','',-1,0,'5#starmod.gif',0,0,-2,'',0);
/*!40000 ALTER TABLE `smf_1membergroups` ENABLE KEYS */;

--
-- Dumping data for table `smf_1members`
--

/*!40000 ALTER TABLE `smf_1members` DISABLE KEYS */;
INSERT INTO `smf_1members` (`id_member`, `member_name`, `date_registered`, `posts`, `id_group`, `lngfile`, `last_login`, `real_name`, `instant_messages`, `unread_messages`, `new_pm`, `buddy_list`, `pm_ignore_list`, `pm_prefs`, `mod_prefs`, `message_labels`, `passwd`, `openid_uri`, `email_address`, `personal_text`, `gender`, `birthdate`, `website_title`, `website_url`, `location`, `icq`, `aim`, `yim`, `msn`, `hide_email`, `show_online`, `time_format`, `signature`, `time_offset`, `avatar`, `pm_email_notify`, `karma_bad`, `karma_good`, `usertitle`, `notify_announcements`, `notify_regularity`, `notify_send_body`, `notify_types`, `member_ip`, `member_ip2`, `secret_question`, `secret_answer`, `id_theme`, `is_activated`, `validation_code`, `id_msg_last_visit`, `additional_groups`, `smiley_set`, `id_post_group`, `total_time_logged_in`, `password_salt`, `ignore_boards`, `warning`, `passwd_flood`, `pm_receive_from`, `is_spammer`, `warnLevel`) VALUES (1,'devadmin',1783859711,9,1,'',1783862758,'devadmin',2,0,0,'','',0,'','','c398c51cd1502b8a7dbed773e1a184ca0c44224e','','admin@zfgc.test','',0,'0001-01-01','','','','','','','',0,1,'','',0,'',0,0,0,'',1,1,0,2,'10.89.3.12','10.89.3.12','','',0,1,'',1,'','',4,1684,'f6fb','',0,'',1,0,0);
INSERT INTO `smf_1members` (`id_member`, `member_name`, `date_registered`, `posts`, `id_group`, `lngfile`, `last_login`, `real_name`, `instant_messages`, `unread_messages`, `new_pm`, `buddy_list`, `pm_ignore_list`, `pm_prefs`, `mod_prefs`, `message_labels`, `passwd`, `openid_uri`, `email_address`, `personal_text`, `gender`, `birthdate`, `website_title`, `website_url`, `location`, `icq`, `aim`, `yim`, `msn`, `hide_email`, `show_online`, `time_format`, `signature`, `time_offset`, `avatar`, `pm_email_notify`, `karma_bad`, `karma_good`, `usertitle`, `notify_announcements`, `notify_regularity`, `notify_send_body`, `notify_types`, `member_ip`, `member_ip2`, `secret_question`, `secret_answer`, `id_theme`, `is_activated`, `validation_code`, `id_msg_last_visit`, `additional_groups`, `smiley_set`, `id_post_group`, `total_time_logged_in`, `password_salt`, `ignore_boards`, `warning`, `passwd_flood`, `pm_receive_from`, `is_spammer`, `warnLevel`) VALUES (2,'mgzero',1783862530,20,1,'',1783886844,'mgzero',1,0,0,'','',0,'','','c43bc0f53171aa2f99ee7145e386698bdc714dd8','','mgzero@zfgc.test','',0,'1986-02-21','Crystalrook Arts','https://www.etsy.com/shop/CrystalRookArts','','','','','',0,1,'','[Chorus: James Hetfield]<br />I am the view<br />I am the table<br />I am the view, I am the table<br />I am all this<br />I am the root<br />The progress<br />The aggressor<br />I am the table<br />I am the ten stories<br />[b]I am the table[/b]<br />I am, I am, I am, I am<br />I am',0,'Musicians/Queen.jpg',1,0,0,'',1,1,0,2,'10.89.3.18','10.89.3.18','','',2,1,'',19,'','',4,660,'90e3','',0,'',1,0,0);
INSERT INTO `smf_1members` (`id_member`, `member_name`, `date_registered`, `posts`, `id_group`, `lngfile`, `last_login`, `real_name`, `instant_messages`, `unread_messages`, `new_pm`, `buddy_list`, `pm_ignore_list`, `pm_prefs`, `mod_prefs`, `message_labels`, `passwd`, `openid_uri`, `email_address`, `personal_text`, `gender`, `birthdate`, `website_title`, `website_url`, `location`, `icq`, `aim`, `yim`, `msn`, `hide_email`, `show_online`, `time_format`, `signature`, `time_offset`, `avatar`, `pm_email_notify`, `karma_bad`, `karma_good`, `usertitle`, `notify_announcements`, `notify_regularity`, `notify_send_body`, `notify_types`, `member_ip`, `member_ip2`, `secret_question`, `secret_answer`, `id_theme`, `is_activated`, `validation_code`, `id_msg_last_visit`, `additional_groups`, `smiley_set`, `id_post_group`, `total_time_logged_in`, `password_salt`, `ignore_boards`, `warning`, `passwd_flood`, `pm_receive_from`, `is_spammer`, `warnLevel`) VALUES (3,'gm112',1783862549,148,1,'',1783995511,'gm112',1,1,1,'','',0,'','','f30a919782499cf41bbaf2b6e14386504bf4d92a','','gm112@zfgc.test','',0,'1991-11-21','','','','','mgzeromustplaysoulreaverdefiancebtw','','',0,1,'','',0,'',1,0,0,'',1,1,0,2,'10.89.3.9','10.89.3.9','','',2,1,'',185,'9','',6,2808,'496a','',0,'',1,0,0);
INSERT INTO `smf_1members` (`id_member`, `member_name`, `date_registered`, `posts`, `id_group`, `lngfile`, `last_login`, `real_name`, `instant_messages`, `unread_messages`, `new_pm`, `buddy_list`, `pm_ignore_list`, `pm_prefs`, `mod_prefs`, `message_labels`, `passwd`, `openid_uri`, `email_address`, `personal_text`, `gender`, `birthdate`, `website_title`, `website_url`, `location`, `icq`, `aim`, `yim`, `msn`, `hide_email`, `show_online`, `time_format`, `signature`, `time_offset`, `avatar`, `pm_email_notify`, `karma_bad`, `karma_good`, `usertitle`, `notify_announcements`, `notify_regularity`, `notify_send_body`, `notify_types`, `member_ip`, `member_ip2`, `secret_question`, `secret_answer`, `id_theme`, `is_activated`, `validation_code`, `id_msg_last_visit`, `additional_groups`, `smiley_set`, `id_post_group`, `total_time_logged_in`, `password_salt`, `ignore_boards`, `warning`, `passwd_flood`, `pm_receive_from`, `is_spammer`, `warnLevel`) VALUES (4,'testmember',1783862672,5,9,'',1783994108,'testmember',0,0,0,'','',0,'','','afbef6362e61043e2c5dfb355efc5dc1612e700e','','testmember@zfgc.test','Hey, Listen!',2,'1987-01-14','','','Hyrule Castle','','superfakescreenname','','',0,1,'','',0,'https://upload.wikimedia.org/wikipedia/en/5/57/The_Legend_of_Zelda_Ocarina_of_Time.jpg',1,0,0,'',1,1,0,2,'10.89.3.9','10.89.3.9','','',0,1,'',181,'','',4,246,'beee','',0,'',1,0,0);
INSERT INTO `smf_1members` (`id_member`, `member_name`, `date_registered`, `posts`, `id_group`, `lngfile`, `last_login`, `real_name`, `instant_messages`, `unread_messages`, `new_pm`, `buddy_list`, `pm_ignore_list`, `pm_prefs`, `mod_prefs`, `message_labels`, `passwd`, `openid_uri`, `email_address`, `personal_text`, `gender`, `birthdate`, `website_title`, `website_url`, `location`, `icq`, `aim`, `yim`, `msn`, `hide_email`, `show_online`, `time_format`, `signature`, `time_offset`, `avatar`, `pm_email_notify`, `karma_bad`, `karma_good`, `usertitle`, `notify_announcements`, `notify_regularity`, `notify_send_body`, `notify_types`, `member_ip`, `member_ip2`, `secret_question`, `secret_answer`, `id_theme`, `is_activated`, `validation_code`, `id_msg_last_visit`, `additional_groups`, `smiley_set`, `id_post_group`, `total_time_logged_in`, `password_salt`, `ignore_boards`, `warning`, `passwd_flood`, `pm_receive_from`, `is_spammer`, `warnLevel`) VALUES (5,'gmod',1783862708,1,2,'',1783994118,'gmod',0,0,0,'','',0,'','','e4b6bff7c35be79babf52706abaa9983b62d1f9c','','gmod@zfgc.test','',0,'0001-01-01','','','','','','','',0,1,'','',0,'',1,0,0,'',1,1,0,2,'10.89.3.9','10.89.3.9','','',0,1,'',184,'','',4,0,'a698','',0,'',1,0,0);
/*!40000 ALTER TABLE `smf_1members` ENABLE KEYS */;

--
-- Dumping data for table `smf_1message_icons`
--

/*!40000 ALTER TABLE `smf_1message_icons` DISABLE KEYS */;
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (1,'Standard','xx',0,0);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (2,'Thumb Up','thumbup',0,1);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (3,'Thumb Down','thumbdown',0,2);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (4,'Exclamation point','exclamation',0,3);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (5,'Question mark','question',0,4);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (6,'Lamp','lamp',0,5);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (7,'Smiley','smiley',0,6);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (8,'Angry','angry',0,7);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (9,'Cheesy','cheesy',0,8);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (10,'Grin','grin',0,9);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (11,'Sad','sad',0,10);
INSERT INTO `smf_1message_icons` (`id_icon`, `title`, `filename`, `id_board`, `icon_order`) VALUES (12,'Wink','wink',0,11);
/*!40000 ALTER TABLE `smf_1message_icons` ENABLE KEYS */;

--
-- Dumping data for table `smf_1messages`
--

/*!40000 ALTER TABLE `smf_1messages` DISABLE KEYS */;
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (1,1,1,1783859655,0,1,'Welcome to SMF!','Simple Machines','info@simplemachines.org','127.0.0.1',1,0,'','Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (2,2,1,1783861925,1,2,'BBCode','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Hello World!<br /><br />Text Style BBCode<br />[b]Steve[/b]<br />[i]Steeevveee[/i]<br />[u]Steve?[/u]<br />[s]Stove[/s]<br />[b][i][u]Steeeeeevvveee[/u][/i][/b]<br />[u][i][b]Stteve[/b][/i][/u]<br />[font=comic sans ms]Comic Sans ya[/font]<br />[size=36pt]BIG Font[/size]<br />[color=purple]PURPLE font[/color]<br /><br />Text Effect BBCode<br />[glow=red,2,300]Glowing Red[/glow]<br />[glow=blue,2,300]Glowin Blue[/glow]<br />[shadow=red,left]Red Shadow[/shadow]<br />[glow=red,2,300][shadow=red,left]Red glow, red shadow[/shadow][/glow]<br />[move]steve[/move]<br />[tt]Teletype ya[/tt]<br />[pre]Preformatted text[/pre]<br /><br /><br />Text Positioning BBCode[left]Left Align[/left][center]Center Align[/center][right]Right Align[/right][sup]superscript[/sup]<br />[sub]subscript[/sub]<br /><br />Content BBCode<br />[list]<br />	[li]item 1[/li]<br />	[li]item 2[/li]<br />	<br />[/list]<br />[list]<br />	[li]item one[/li]<br />	[li]item two[/li]<br />[/list]<br /><br />[quote]<br />Derp<br />[/quote]<br /><br />Layout BBCode<br /><br />[hr]<br /><br />[table]<br />[tr]<br />[td]row[/td]<br />[/tr]<br />[/table]<br /><br />','thumbup',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (3,2,1,1783861973,1,3,'Re: BBCode','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','[quote author=devadmin link=topic=2.msg2#msg2 date=1783861925]<br />Hello World!<br /><br />Text Style BBCode<br />[b]Steve[/b]<br />[i]Steeevveee[/i]<br />[u]Steve?[/u]<br />[s]Stove[/s]<br />[b][i][u]Steeeeeevvveee[/u][/i][/b]<br />[u][i][b]Stteve[/b][/i][/u]<br />[font=comic sans ms]Comic Sans ya[/font]<br />[size=36pt]BIG Font[/size]<br />[color=purple]PURPLE font[/color]<br /><br />Text Effect BBCode<br />[glow=red,2,300]Glowing Red[/glow]<br />[glow=blue,2,300]Glowin Blue[/glow]<br />[shadow=red,left]Red Shadow[/shadow]<br />[glow=red,2,300][shadow=red,left]Red glow, red shadow[/shadow][/glow]<br />[move]steve[/move]<br />[tt]Teletype ya[/tt]<br />[pre]Preformatted text[/pre]<br /><br /><br />Text Positioning BBCode[left]Left Align[/left][center]Center Align[/center][right]Right Align[/right][sup]superscript[/sup]<br />[sub]subscript[/sub]<br /><br />Content BBCode<br />[list]<br />	[li]item 1[/li]<br />	[li]item 2[/li]<br />	<br />[/list]<br />[list]<br />	[li]item one[/li]<br />	[li]item two[/li]<br />[/list]<br /><br />[quote]<br />Derp<br />[/quote]<br /><br />Layout BBCode<br /><br />[hr]<br /><br />[table]<br />[tr]<br />[td]row[/td]<br />[/tr]<br />[/table]<br />[/quote]<br /><br />quoting another post','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (4,2,1,1783861989,1,4,'Re: BBCode','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','[quote author=Simple Machines link=topic=1.msg1#msg1 date=1783859655]<br />Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines<br />[/quote]<br /><br />quoting a post from another thread','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (5,3,1,1783862062,1,5,'Poll Thread','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (6,4,1,1783862140,1,6,'Attachment Thread','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Test','clip',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (7,5,1,1783862153,1,7,'Sticky Thread Test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','asdfadfgfghrtfsh','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (8,6,1,1783862169,1,8,'Locked Thread Test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Locked&nbsp; :-X','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (9,7,2,1783862239,1,9,'Moved thread test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','oic','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (10,8,1,1783862244,1,10,'MOVED: Moved thread test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','This topic has been moved to [url=http://localhost:8090/index.php?board=2.0]New Board[/url].<br /><br />[iurl]http://localhost:8090/index.php?topic=7.0[/iurl]','moved',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (11,2,1,1783862780,3,11,'Re: BBCode','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','[quote author=devadmin link=topic=2.msg4#msg4 date=1783861989]<br />[quote author=Simple Machines link=topic=1.msg1#msg1 date=1783859655]<br />Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines<br />[/quote]<br />[/quote]<br /><br />Hey Steve look, a quote pyramid!<br /><br />quoting a post from another thread','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (12,2,1,1783862854,4,12,'Re: BBCode','testmember','testmember@zfgc.test','10.89.3.12',1,0,'',' :) ;) :D ;D &gt;:( :( :o 8) ??? ::) :P :-[ :-X :-\\ :-* :&#039;(','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (13,2,1,1783862871,4,13,'Re: BBCode','testmember','testmember@zfgc.test','10.89.3.12',1,0,'','I&#039;m linking another thread and double posting woohoo!<br /><br />http://localhost:8090/index.php?topic=3.0','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (14,3,1,1783862922,2,14,'Re: Poll Thread','mgzero','mgzero@zfgc.test','10.89.3.12',1,0,'',' ???<br /><br />Obviously Stove, and that&#039;s not even an option!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (15,3,1,1783863296,3,15,'Re: Poll Thread','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','[quote author=mgzero link=topic=3.msg14#msg14 date=1783862922]<br /> ???<br /><br />Obviously Stove, and that&#039;s not even an option!<br />[/quote]<br />Yes, but we&#039;re all Steve.','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (16,4,1,1783863347,3,16,'Re: Attachment Thread','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','Another attachment test','clip',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (17,2,1,1783863379,3,17,'Re: BBCode','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','[img]https://upload.wikimedia.org/wikipedia/en/a/a1/OcarinaOfTimeBattle.JPG[/img]<br /><br />img bbcode test','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (19,9,1,1783868360,3,19,'Ocarina of Time 2D','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','Ocarina of Time 2D was made by Daniel Barras. He founded ZFGC.com to host the community that was forming around his project in 2003 on EZBoard.','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (20,3,1,1783885391,2,20,'Re: Poll Thread','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','Steve=mc^stove','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (21,3,1,1783885417,3,21,'Re: Poll Thread','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','yes indeed','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (22,10,1,1783885448,3,22,'Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','How many derps can we get in here?','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (23,10,1,1783885461,3,23,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (24,10,1,1783885465,3,24,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (25,10,1,1783885469,3,25,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (26,10,1,1783885473,3,26,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (27,10,1,1783885477,3,27,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (28,10,1,1783885481,3,28,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (29,10,1,1783885488,3,29,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (30,10,1,1783885502,3,30,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (31,10,1,1783885543,3,31,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (32,10,1,1783885548,3,32,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (33,10,1,1783885551,3,33,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (34,10,1,1783885556,3,34,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (35,10,1,1783886060,3,35,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (36,10,1,1783886064,3,36,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (37,10,1,1783886068,3,37,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (38,10,1,1783886071,3,38,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (39,10,1,1783886075,3,39,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (40,10,1,1783886079,3,40,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (41,10,1,1783886083,3,41,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (42,10,1,1783886087,3,42,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (43,10,1,1783886091,3,43,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (44,10,1,1783886095,3,44,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (45,10,1,1783886099,3,45,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (46,10,1,1783886103,3,46,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (47,10,1,1783886106,3,47,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (48,10,1,1783886110,3,48,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (49,10,1,1783886114,3,49,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (50,10,1,1783886118,3,50,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (51,10,1,1783886122,3,51,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (52,10,1,1783886126,3,52,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (53,10,1,1783886130,3,53,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (54,10,1,1783886134,3,54,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (55,10,1,1783886138,3,55,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (56,10,1,1783886141,3,56,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (57,10,1,1783886145,3,57,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (58,10,1,1783886149,3,58,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (59,10,1,1783886153,3,59,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (60,10,1,1783886157,3,60,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (61,10,1,1783886161,3,61,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (62,10,1,1783886165,3,62,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (63,10,1,1783886169,3,63,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (64,10,1,1783886173,3,64,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (65,10,1,1783886177,3,65,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (66,10,1,1783886180,3,66,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (67,10,1,1783886184,3,67,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (68,10,1,1783886188,3,68,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (69,10,1,1783886192,3,69,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (70,10,1,1783886196,3,70,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (71,10,1,1783886200,3,71,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (72,10,1,1783886204,3,72,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (73,10,1,1783886208,3,73,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (74,10,1,1783886212,3,74,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (75,10,1,1783886215,3,75,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (76,10,1,1783886219,3,76,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (77,10,1,1783886223,3,77,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (78,10,1,1783886227,3,78,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (79,10,1,1783886231,3,79,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (80,10,1,1783886235,3,80,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (81,10,1,1783886239,3,81,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (82,10,1,1783886243,3,82,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (83,10,1,1783886247,3,83,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (84,10,1,1783886250,3,84,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (85,10,1,1783886254,3,85,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (86,10,1,1783886258,3,86,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (87,10,1,1783886262,3,87,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (88,10,1,1783886266,3,88,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (89,10,1,1783886270,3,89,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (90,10,1,1783886274,3,90,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (91,10,1,1783886278,3,91,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (92,10,1,1783886282,3,92,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (93,10,1,1783886286,3,93,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (94,10,1,1783886289,3,94,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (95,10,1,1783886293,3,95,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (96,10,1,1783886297,3,96,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (97,10,1,1783886301,3,97,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (98,10,1,1783886305,3,98,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (99,10,1,1783886309,3,99,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (100,10,1,1783886313,3,100,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (101,10,1,1783886317,3,101,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (102,10,1,1783886321,3,102,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (103,10,1,1783886325,3,103,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (104,10,1,1783886328,3,104,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (105,10,1,1783886332,3,105,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (106,10,1,1783886336,3,106,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (107,10,1,1783886340,3,107,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (108,10,1,1783886344,3,108,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (109,10,1,1783886348,3,109,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (110,10,1,1783886352,3,110,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (111,10,1,1783886368,2,111,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (112,10,1,1783886372,2,112,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (113,10,1,1783886376,2,113,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (114,10,1,1783886380,2,114,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (115,10,1,1783886383,2,115,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (116,10,1,1783886388,2,116,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (117,10,1,1783886391,2,117,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (118,10,1,1783886399,2,118,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (119,10,1,1783886612,3,119,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (120,10,1,1783886615,3,120,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (121,10,1,1783886618,3,121,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (122,10,1,1783886622,3,122,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (123,10,1,1783886625,3,123,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (124,10,1,1783886628,3,124,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (125,10,1,1783886632,3,125,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (126,10,1,1783886635,3,126,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (127,10,1,1783886638,3,127,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (128,10,1,1783886642,3,128,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (129,10,1,1783886645,3,129,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (130,10,1,1783886648,3,130,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (131,10,1,1783886656,3,131,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (132,10,1,1783886659,3,132,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (133,10,1,1783886663,3,133,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (134,10,1,1783886666,3,134,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (135,10,1,1783886669,3,135,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (136,10,1,1783886673,3,136,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (137,10,1,1783886676,3,137,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (138,10,1,1783886679,3,138,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (139,10,1,1783886683,3,139,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (140,10,1,1783886686,3,140,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (141,10,1,1783886689,3,141,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (142,10,1,1783886692,3,142,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (143,10,1,1783886696,3,143,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (144,10,1,1783886699,3,144,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (145,10,1,1783886702,3,145,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (146,10,1,1783886706,3,146,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (147,10,1,1783886709,3,147,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (148,10,1,1783886712,3,148,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (149,10,1,1783886730,2,149,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (150,10,1,1783886735,2,150,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (151,10,1,1783886738,2,151,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (152,10,1,1783886752,2,152,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (153,10,1,1783886756,2,153,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (154,10,1,1783886759,2,154,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (155,10,1,1783886763,2,155,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (156,10,1,1783886766,2,156,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (157,10,1,1783886770,2,157,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (158,11,1,1783886842,2,158,'Me Steve','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','hi','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (159,12,1,1783886870,3,159,'Me Jon','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','hi','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (160,13,2,1783886896,3,160,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (161,14,2,1783886902,3,161,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','cheesy',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (162,15,2,1783886907,3,162,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','thumbup',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (163,16,2,1783886911,3,163,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','thumbdown',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (164,17,2,1783886916,3,164,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','exclamation',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (165,18,2,1783886921,3,165,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','question',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (166,19,2,1783886927,3,166,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','lamp',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (167,20,2,1783886934,3,167,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','smiley',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (168,21,2,1783886940,3,168,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','angry',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (169,22,2,1783886944,3,169,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','grin',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (170,23,2,1783886953,3,170,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','sad',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (171,24,2,1783886958,3,171,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','wink',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (172,25,2,1783886962,3,172,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (173,26,2,1783886966,3,173,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (174,27,2,1783886969,3,174,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (175,28,2,1783886979,3,175,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (176,29,2,1783886984,3,176,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (177,30,2,1783886987,3,177,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (178,31,2,1783886991,3,178,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (179,32,2,1783887000,3,179,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (180,33,2,1783887003,3,180,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (181,34,2,1783887006,3,181,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (182,12,1,1783994099,4,182,'Re: Me Jon','testmember','testmember@zfgc.test','10.89.3.9',1,0,'','no me steve','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (183,11,1,1783994105,4,183,'Re: Me Steve','testmember','testmember@zfgc.test','10.89.3.9',1,0,'','stove','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (184,34,2,1783994112,4,184,'Re: Spam thread for pagination in boards','testmember','testmember@zfgc.test','10.89.3.9',1,0,'','pizza','xx',1,NULL);
INSERT INTO `smf_1messages` (`id_msg`, `id_topic`, `id_board`, `poster_time`, `id_member`, `id_msg_modified`, `subject`, `poster_name`, `poster_email`, `poster_ip`, `smileys_enabled`, `modified_time`, `modified_name`, `body`, `icon`, `approved`, `description`) VALUES (185,11,1,1783994127,5,185,'Re: Me Steve','gmod','gmod@zfgc.test','10.89.3.9',1,0,'','gordon steven in the flesh','xx',1,NULL);
/*!40000 ALTER TABLE `smf_1messages` ENABLE KEYS */;

--
-- Dumping data for table `smf_1messages_history`
--

/*!40000 ALTER TABLE `smf_1messages_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1messages_history` ENABLE KEYS */;

--
-- Dumping data for table `smf_1package_servers`
--

/*!40000 ALTER TABLE `smf_1package_servers` DISABLE KEYS */;
INSERT INTO `smf_1package_servers` (`id_server`, `name`, `url`) VALUES (1,'Simple Machines Third-party Mod Site','http://custom.simplemachines.org/packages/mods');
/*!40000 ALTER TABLE `smf_1package_servers` ENABLE KEYS */;

--
-- Dumping data for table `smf_1permission_profiles`
--

/*!40000 ALTER TABLE `smf_1permission_profiles` DISABLE KEYS */;
INSERT INTO `smf_1permission_profiles` (`id_profile`, `profile_name`) VALUES (1,'default');
INSERT INTO `smf_1permission_profiles` (`id_profile`, `profile_name`) VALUES (2,'no_polls');
INSERT INTO `smf_1permission_profiles` (`id_profile`, `profile_name`) VALUES (3,'reply_only');
INSERT INTO `smf_1permission_profiles` (`id_profile`, `profile_name`) VALUES (4,'read_only');
/*!40000 ALTER TABLE `smf_1permission_profiles` ENABLE KEYS */;

--
-- Dumping data for table `smf_1permissions`
--

/*!40000 ALTER TABLE `smf_1permissions` DISABLE KEYS */;
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (-1,'search_posts',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (-1,'calendar_view',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (-1,'view_stats',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (-1,'profile_view_any',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'view_mlist',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'search_posts',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_view_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_view_any',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'pm_read',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'pm_send',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'calendar_view',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'view_stats',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'who_view',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_identity_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_extra_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_remove_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_server_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_upload_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'profile_remote_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (0,'karma_edit',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'view_mlist',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'search_posts',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_view_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_view_any',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'pm_read',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'pm_send',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'calendar_view',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'view_stats',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'who_view',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_identity_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_extra_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_remove_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_server_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_upload_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_remote_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'profile_title_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'calendar_post',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'calendar_edit_any',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'karma_edit',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (2,'access_mod_center',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'calendar_view',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'karma_edit',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'pm_read',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'pm_send',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_extra_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_identity_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_remote_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_remove_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_server_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_upload_avatar',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_view_any',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'profile_view_own',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'search_posts',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'view_mlist',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'view_stats',1);
INSERT INTO `smf_1permissions` (`id_group`, `permission`, `add_deny`) VALUES (9,'who_view',1);
/*!40000 ALTER TABLE `smf_1permissions` ENABLE KEYS */;

--
-- Dumping data for table `smf_1personal_messages`
--

/*!40000 ALTER TABLE `smf_1personal_messages` DISABLE KEYS */;
INSERT INTO `smf_1personal_messages` (`id_pm`, `id_pm_head`, `id_member_from`, `deleted_by_sender`, `from_name`, `msgtime`, `subject`, `body`) VALUES (1,1,1,0,'devadmin',1783862007,'(No subject)','Private message to myself');
INSERT INTO `smf_1personal_messages` (`id_pm`, `id_pm_head`, `id_member_from`, `deleted_by_sender`, `from_name`, `msgtime`, `subject`, `body`) VALUES (2,2,1,1,'devadmin',1783862287,'BBCode Test in Private Messages','BBCode PM Test<br /><br />Hello World!<br /><br />Text Style BBCode<br />[b]Steve[/b]<br />[i]Steeevveee[/i]<br />[u]Steve?[/u]<br />[s]Stove[/s]<br />[b][i][u]Steeeeeevvveee[/u][/i][/b]<br />[u][i][b]Stteve[/b][/i][/u]<br />[font=comic sans ms]Comic Sans ya[/font]<br />[size=36pt]BIG Font[/size]<br />[color=purple]PURPLE font[/color]<br /><br />Text Effect BBCode<br />[glow=red,2,300]Glowing Red[/glow]<br />[glow=blue,2,300]Glowin Blue[/glow]<br />[shadow=red,left]Red Shadow[/shadow]<br />[glow=red,2,300][shadow=red,left]Red glow, red shadow[/shadow][/glow]<br />[move]steve[/move]<br />[tt]Teletype ya[/tt]<br />[pre]Preformatted text[/pre]<br /><br /><br />Text Positioning BBCode[left]Left Align[/left][center]Center Align[/center][right]Right Align[/right][sup]superscript[/sup]<br />[sub]subscript[/sub]<br /><br />Content BBCode<br />[list]<br />	[li]item 1[/li]<br />	[li]item 2[/li]<br />	<br />[/list]<br />[list]<br />	[li]item one[/li]<br />	[li]item two[/li]<br />[/list]<br /><br />[quote]<br />Derp<br />[/quote]<br /><br />Layout BBCode<br /><br />[hr]<br /><br />[table]<br />[tr]<br />[td]row[/td]<br />[/tr]<br />[/table]<br /><br />');
INSERT INTO `smf_1personal_messages` (`id_pm`, `id_pm_head`, `id_member_from`, `deleted_by_sender`, `from_name`, `msgtime`, `subject`, `body`) VALUES (3,3,3,0,'gm112',1783862640,'kamehameha','IMMA CHARGIN UP!<br /><br />AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH<br /><br /><br />[size=36pt]AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH[/size]<br /><br />Now this is only 1/4 of my power! HEH.');
INSERT INTO `smf_1personal_messages` (`id_pm`, `id_pm_head`, `id_member_from`, `deleted_by_sender`, `from_name`, `msgtime`, `subject`, `body`) VALUES (4,3,2,1,'mgzero',1783885346,'Re: kamehameha','[quote author=gm112 link=action=profile;u=3 date=1783862640]<br />IMMA CHARGIN UP!<br /><br />AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH<br /><br /><br />[size=36pt]AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH[/size]<br /><br />Now this is only 1/4 of my power! HEH.<br />[/quote]<br /><br />IMMA CHARGIN UP');
/*!40000 ALTER TABLE `smf_1personal_messages` ENABLE KEYS */;

--
-- Dumping data for table `smf_1pm_recipients`
--

/*!40000 ALTER TABLE `smf_1pm_recipients` DISABLE KEYS */;
INSERT INTO `smf_1pm_recipients` (`id_pm`, `id_member`, `labels`, `bcc`, `is_read`, `is_new`, `deleted`) VALUES (1,1,'-1',0,1,0,0);
INSERT INTO `smf_1pm_recipients` (`id_pm`, `id_member`, `labels`, `bcc`, `is_read`, `is_new`, `deleted`) VALUES (2,1,'-1',0,1,0,0);
INSERT INTO `smf_1pm_recipients` (`id_pm`, `id_member`, `labels`, `bcc`, `is_read`, `is_new`, `deleted`) VALUES (3,2,'-1',0,3,0,1);
INSERT INTO `smf_1pm_recipients` (`id_pm`, `id_member`, `labels`, `bcc`, `is_read`, `is_new`, `deleted`) VALUES (4,3,'-1',0,0,1,0);
INSERT INTO `smf_1pm_recipients` (`id_pm`, `id_member`, `labels`, `bcc`, `is_read`, `is_new`, `deleted`) VALUES (3,5,'-1',1,0,1,0);
/*!40000 ALTER TABLE `smf_1pm_recipients` ENABLE KEYS */;

--
-- Dumping data for table `smf_1poll_choices`
--

/*!40000 ALTER TABLE `smf_1poll_choices` DISABLE KEYS */;
INSERT INTO `smf_1poll_choices` (`id_poll`, `id_choice`, `label`, `votes`) VALUES (1,0,'Steve?',0);
INSERT INTO `smf_1poll_choices` (`id_poll`, `id_choice`, `label`, `votes`) VALUES (1,1,'Steve!',1);
INSERT INTO `smf_1poll_choices` (`id_poll`, `id_choice`, `label`, `votes`) VALUES (1,2,'Stone',2);
/*!40000 ALTER TABLE `smf_1poll_choices` ENABLE KEYS */;

--
-- Dumping data for table `smf_1polls`
--

/*!40000 ALTER TABLE `smf_1polls` DISABLE KEYS */;
INSERT INTO `smf_1polls` (`id_poll`, `question`, `voting_locked`, `max_votes`, `expire_time`, `hide_results`, `change_vote`, `guest_vote`, `num_guest_voters`, `reset_poll`, `id_member`, `poster_name`, `ID_TOPIC`) VALUES (1,'How many Steves can Steve?',0,1,1784121262,0,1,0,0,0,1,'devadmin',0);
/*!40000 ALTER TABLE `smf_1polls` ENABLE KEYS */;

--
-- Dumping data for table `smf_1scheduled_tasks`
--

/*!40000 ALTER TABLE `smf_1scheduled_tasks` DISABLE KEYS */;
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (1,1784001600,0,2,'h',0,'approval_notification');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (2,1784419200,0,7,'d',0,'auto_optimize');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (3,1784073660,60,1,'d',0,'daily_maintenance');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (5,1784073600,0,1,'d',0,'daily_digest');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (6,1784419200,0,1,'w',0,'weekly_digest');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (7,1784036940,136183,1,'d',0,'fetchSMfiles');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (8,0,0,1,'d',1,'birthdayemails');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (9,1784419200,0,1,'w',0,'weekly_maintenance');
INSERT INTO `smf_1scheduled_tasks` (`id_task`, `next_time`, `time_offset`, `time_regularity`, `time_unit`, `disabled`, `task`) VALUES (10,0,120,1,'d',1,'paid_subscriptions');
/*!40000 ALTER TABLE `smf_1scheduled_tasks` ENABLE KEYS */;

--
-- Dumping data for table `smf_1settings`
--

/*!40000 ALTER TABLE `smf_1settings` DISABLE KEYS */;
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smfVersion','2.0.15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('news','SMF - Just Installed!');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('compactTopicPagesContiguous','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('compactTopicPagesEnable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableStickyTopics','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('todayMod','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaMode','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaTimeRestrictAdmins','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enablePreviousNext','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('pollMode','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableVBStyleLogin','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableCompressedOutput','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaWaitTime','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaMinPosts','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaLabel','Karma:');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaSmiteLabel','[smite]');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('karmaApplaudLabel','[applaud]');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentSizeLimit','128');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentPostLimit','192');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentNumPerPostLimit','4');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentDirSizeLimit','10240');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentUploadDir','/var/www/html/attachments');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentExtensions','doc,gif,jpg,mpg,pdf,png,txt,zip');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentCheckExtensions','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentShowImages','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentEnable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentEncryptFilenames','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentThumbnails','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentThumbWidth','150');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachmentThumbHeight','150');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('censorIgnoreCase','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostOnline','2');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostOnlineToday','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostDate','1783886844');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_disableAnnounce','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('trackStats','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('userLanguage','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('titlesEnable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('topicSummaryPosts','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableErrorLogging','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('max_image_width','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('max_image_height','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('onlineEnable','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_enabled','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_maxyear','2030');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_minyear','2008');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_daysaslink','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_defaultboard','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showholidays','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showbdays','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showevents','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_showweeknum','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_maxspan','7');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_host','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_port','25');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_username','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smtp_password','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mail_type','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('timeLoadPageEnable','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('totalMembers','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('totalTopics','34');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('totalMessages','184');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('simpleSearch','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('censor_vulgar','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('censor_proper','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enablePostHTML','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('theme_allow','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('theme_default','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('theme_guests','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableEmbeddedFlash','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('xmlnews_enable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('xmlnews_maxlen','255');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('hotTopicPosts','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('hotTopicVeryPosts','25');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('registration_method','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('send_validation_onChange','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('send_welcomeEmail','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_editDisplayName','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_hideOnline','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('guest_hideContacts','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('spamWaitTime','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('pm_spam_settings','10,5,20');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveWord','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveCase','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveUser','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveName','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reserveNames','Admin\nWebmaster\nGuest\nroot');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('autoLinkUrls','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('banLastUpdated','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smileys_dir','/var/www/html/Smileys');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smileys_url','http://localhost:8090/Smileys');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_directory','/var/www/html/avatars');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_url','http://localhost:8090/avatars');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_height_external','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_width_external','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_action_too_large','option_html_resize');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_height_upload','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_max_width_upload','65');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_resize_upload','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_download_png','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('failed_login_threshold','3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('oldTopicDays','120');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('edit_wait_time','90');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('edit_disable_time','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('autoFixDatabase','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('allow_guestAccess','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('time_format','%B %d, %Y, %I:%M:%S %p');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('number_format','1234.00');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableBBC','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('max_messageLength','20000');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('signature_settings','1,300,0,0,0,0,0,0:');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('autoOptMaxOnline','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('defaultMaxMessages','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('defaultMaxTopics','20');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('defaultMaxMembers','30');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableParticipation','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('recycle_enable','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('recycle_board','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('maxMsgID','185');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enableAllMessages','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('fixLongWords','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('knownThemes','1,2,3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('who_enabled','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('time_offset','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cookieTime','60');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('lastActive','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smiley_sets_known','default,aaron,akyhne');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smiley_sets_names','Alienine\'s Set\nAaron\'s Set\nAkyhne\'s Set');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('smiley_sets_default','default');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cal_days_for_index','7');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('requireAgreement','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('unapprovedMembers','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('default_personal_text','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('package_make_backups','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('databaseSession_enable','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('databaseSession_loose','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('databaseSession_lifetime','2880');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_cache_size','50');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_results_per_page','30');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_frequency','30');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_age','25');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_length','20');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_subject','15');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_weight_first_message','10');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_max_results','1200');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('search_floodcontrol_time','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('permission_enable_deny','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('permission_enable_postgroups','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mail_next_send','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mail_recent','0000000000|0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('settings_updated','1783994152');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('next_task_time','1784001600');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_settings','1,20,0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_watch','10');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_moderate','35');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('warning_mute','60');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('admin_features','');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('last_mod_report_action','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('pruningOptions','30,180,180,180,30,0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('cache_enable','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('reg_verification','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('visual_verification_type','3');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('enable_buddylist','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('birthday_email','happy_birthday');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('dont_repeat_theme_core','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('dont_repeat_smileys_20','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('dont_repeat_buddylists','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachment_image_reencode','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachment_image_paranoid','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('attachment_thumb_png','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_reencode','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('avatar_paranoid','0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('global_character_set','UTF-8');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('globalCookies','1');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('default_timezone','Etc/GMT0');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('memberlist_updated','1783862708');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('latestMember','5');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('latestRealName','gmod');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('rand_seed','679934102');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('mostOnlineUpdated','2026-07-14');
INSERT INTO `smf_1settings` (`variable`, `value`) VALUES ('calendar_updated','1783863430');
/*!40000 ALTER TABLE `smf_1settings` ENABLE KEYS */;

--
-- Dumping data for table `smf_1smileys`
--

/*!40000 ALTER TABLE `smf_1smileys` DISABLE KEYS */;
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (1,':)','smiley.gif','Smiley',0,0,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (2,';)','wink.gif','Wink',0,1,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (3,':D','cheesy.gif','Cheesy',0,2,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (4,';D','grin.gif','Grin',0,3,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (5,'>:(','angry.gif','Angry',0,4,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (6,':(','sad.gif','Sad',0,5,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (7,':o','shocked.gif','Shocked',0,6,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (8,'8)','cool.gif','Cool',0,7,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (9,'???','huh.gif','Huh?',0,8,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (10,'::)','rolleyes.gif','Roll Eyes',0,9,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (11,':P','tongue.gif','Tongue',0,10,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (12,':-[','embarrassed.gif','Embarrassed',0,11,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (13,':-X','lipsrsealed.gif','Lips Sealed',0,12,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (14,':-\\','undecided.gif','Undecided',0,13,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (15,':-*','kiss.gif','Kiss',0,14,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (16,':\'(','cry.gif','Cry',0,15,0);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (17,'>:D','evil.gif','Evil',0,16,1);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (18,'^-^','azn.gif','Azn',0,17,1);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (19,'O0','afro.gif','Afro',0,18,1);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (20,':))','laugh.gif','Laugh',0,19,1);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (21,'C:-)','police.gif','Police',0,20,1);
INSERT INTO `smf_1smileys` (`id_smiley`, `code`, `filename`, `description`, `smiley_row`, `smiley_order`, `hidden`) VALUES (22,'O:-)','angel.gif','Angel',0,21,1);
/*!40000 ALTER TABLE `smf_1smileys` ENABLE KEYS */;

--
-- Dumping data for table `smf_1spiders`
--

/*!40000 ALTER TABLE `smf_1spiders` DISABLE KEYS */;
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (1,'Google','googlebot','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (2,'Yahoo!','slurp','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (3,'MSN','msnbot','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (4,'Google (Mobile)','Googlebot-Mobile','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (5,'Google (Image)','Googlebot-Image','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (6,'Google (AdSense)','Mediapartners-Google','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (7,'Google (Adwords)','AdsBot-Google','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (8,'Yahoo! (Mobile)','YahooSeeker/M1A1-R2D2','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (9,'Yahoo! (Image)','Yahoo-MMCrawler','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (10,'MSN (Mobile)','MSNBOT_Mobile','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (11,'MSN (Media)','msnbot-media','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (12,'Cuil','twiceler','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (13,'Ask','Teoma','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (14,'Baidu','Baiduspider','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (15,'Gigablast','Gigabot','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (16,'InternetArchive','ia_archiver-web.archive.org','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (17,'Alexa','ia_archiver','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (18,'Omgili','omgilibot','');
INSERT INTO `smf_1spiders` (`id_spider`, `spider_name`, `user_agent`, `ip_info`) VALUES (19,'EntireWeb','Speedy Spider','');
/*!40000 ALTER TABLE `smf_1spiders` ENABLE KEYS */;

--
-- Dumping data for table `smf_1themes`
--

/*!40000 ALTER TABLE `smf_1themes` DISABLE KEYS */;
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'name','SMF Default Theme - Curve');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'theme_url','http://localhost:8090/Themes/default');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'images_url','http://localhost:8090/Themes/default/images');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'theme_dir','/var/www/html/Themes/default');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_bbc','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_latest_member','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_modify','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_user_images','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_blurb','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_gender','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_newsfader','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'number_recent_posts','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_member_bar','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'linktree_link','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_profile_buttons','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_mark_read','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_stats_index','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'linktree_inline','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'show_board_desc','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'newsfader_time','5000');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'allow_no_censored','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'additional_options_collapsable','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'use_image_buttons','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'enable_news','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,1,'forum_width','90%');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'name','Core Theme');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'theme_url','http://localhost:8090/Themes/core');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'images_url','http://localhost:8090/Themes/core/images');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'theme_dir','/var/www/html/Themes/core');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (-1,1,'display_quick_reply','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (-1,1,'posts_apply_ignore_list','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (1,1,'use_sidebar_menu','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'header_logo_url','');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'smiley_sets_default','');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'forum_width','90%');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_mark_read','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'allow_no_censored','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'enable_news','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'use_image_buttons','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_newsfader','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'newsfader_time','5000');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'number_recent_posts','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_stats_index','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_latest_member','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_group_key','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'display_who_viewing','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_modify','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_profile_buttons','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_user_images','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_blurb','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_gender','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'hide_post_group','0');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'show_bbc','1');
INSERT INTO `smf_1themes` (`id_member`, `id_theme`, `variable`, `value`) VALUES (0,2,'additional_options_collapsable','1');
/*!40000 ALTER TABLE `smf_1themes` ENABLE KEYS */;

--
-- Dumping data for table `smf_1topics`
--

/*!40000 ALTER TABLE `smf_1topics` DISABLE KEYS */;
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (1,0,1,1,1,0,0,0,0,0,0,3,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (2,0,1,2,17,1,3,0,0,0,6,12,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (3,0,1,5,21,1,3,1,0,0,4,14,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (4,0,1,6,16,1,3,0,0,0,1,1,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (5,1,1,7,7,1,1,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (6,0,1,8,8,1,1,0,0,0,0,0,1,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (7,1,2,9,9,1,1,0,0,0,0,1,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (8,0,1,10,10,1,1,0,0,0,0,0,1,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (9,0,1,19,19,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (10,0,1,22,157,3,2,0,0,0,135,43,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (11,0,1,158,185,2,5,0,0,0,2,4,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (12,0,1,159,182,3,4,0,0,0,1,3,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (13,0,2,160,160,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (14,0,2,161,161,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (15,0,2,162,162,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (16,0,2,163,163,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (17,0,2,164,164,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (18,0,2,165,165,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (19,0,2,166,166,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (20,0,2,167,167,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (21,0,2,168,168,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (22,0,2,169,169,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (23,0,2,170,170,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (24,0,2,171,171,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (25,0,2,172,172,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (26,0,2,173,173,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (27,0,2,174,174,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (28,0,2,175,175,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (29,0,2,176,176,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (30,0,2,177,177,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (31,0,2,178,178,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (32,0,2,179,179,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (33,0,2,180,180,3,3,0,0,0,0,0,0,0,1);
INSERT INTO `smf_1topics` (`id_topic`, `is_sticky`, `id_board`, `id_first_msg`, `id_last_msg`, `id_member_started`, `id_member_updated`, `id_poll`, `id_previous_board`, `id_previous_topic`, `num_replies`, `num_views`, `locked`, `unapproved_posts`, `approved`) VALUES (34,0,2,181,184,3,4,0,0,0,1,2,0,0,1);
/*!40000 ALTER TABLE `smf_1topics` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-14  2:19:00
