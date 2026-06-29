
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smf` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smf`;
DROP TABLE IF EXISTS `smf_1admin_info_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1admin_info_files` (
  `id_file` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(255) NOT NULL DEFAULT '',
  `parameters` varchar(255) NOT NULL DEFAULT '',
  `data` text NOT NULL,
  `filetype` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_file`),
  KEY `filename` (`filename`(30))
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1admin_info_files` WRITE;
/*!40000 ALTER TABLE `smf_1admin_info_files` DISABLE KEYS */;
INSERT INTO `smf_1admin_info_files` VALUES (1,'current-version.js','/smf/','version=%3$s','','text/javascript'),(2,'detailed-version.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(3,'latest-news.js','/smf/','language=%1$s&format=%2$s','','text/javascript'),(4,'latest-packages.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(5,'latest-smileys.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(6,'latest-support.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(7,'latest-themes.js','/smf/','language=%1$s&version=%3$s','','text/javascript');
/*!40000 ALTER TABLE `smf_1admin_info_files` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1approval_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1approval_queue` (
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  `id_attach` int unsigned NOT NULL DEFAULT '0',
  `id_event` smallint unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1approval_queue` WRITE;
/*!40000 ALTER TABLE `smf_1approval_queue` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1approval_queue` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1attachments` (
  `id_attach` int unsigned NOT NULL AUTO_INCREMENT,
  `id_thumb` int unsigned NOT NULL DEFAULT '0',
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_folder` tinyint NOT NULL DEFAULT '1',
  `attachment_type` tinyint unsigned NOT NULL DEFAULT '0',
  `filename` varchar(255) NOT NULL DEFAULT '',
  `file_hash` varchar(40) NOT NULL DEFAULT '',
  `fileext` varchar(8) NOT NULL DEFAULT '',
  `size` int unsigned NOT NULL DEFAULT '0',
  `downloads` mediumint unsigned NOT NULL DEFAULT '0',
  `width` mediumint unsigned NOT NULL DEFAULT '0',
  `height` mediumint unsigned NOT NULL DEFAULT '0',
  `mime_type` varchar(20) NOT NULL DEFAULT '',
  `approved` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_attach`),
  UNIQUE KEY `id_member` (`id_member`,`id_attach`),
  KEY `id_msg` (`id_msg`),
  KEY `attachment_type` (`attachment_type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1attachments` WRITE;
/*!40000 ALTER TABLE `smf_1attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1attachments` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1ban_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1ban_groups` (
  `id_ban_group` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `ban_time` int unsigned NOT NULL DEFAULT '0',
  `expire_time` int unsigned DEFAULT NULL,
  `cannot_access` tinyint unsigned NOT NULL DEFAULT '0',
  `cannot_register` tinyint unsigned NOT NULL DEFAULT '0',
  `cannot_post` tinyint unsigned NOT NULL DEFAULT '0',
  `cannot_login` tinyint unsigned NOT NULL DEFAULT '0',
  `reason` varchar(255) NOT NULL DEFAULT '',
  `notes` text NOT NULL,
  PRIMARY KEY (`id_ban_group`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1ban_groups` WRITE;
/*!40000 ALTER TABLE `smf_1ban_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1ban_groups` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1ban_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1ban_items` (
  `id_ban` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `id_ban_group` smallint unsigned NOT NULL DEFAULT '0',
  `ip_low1` tinyint unsigned NOT NULL DEFAULT '0',
  `ip_high1` tinyint unsigned NOT NULL DEFAULT '0',
  `ip_low2` tinyint unsigned NOT NULL DEFAULT '0',
  `ip_high2` tinyint unsigned NOT NULL DEFAULT '0',
  `ip_low3` tinyint unsigned NOT NULL DEFAULT '0',
  `ip_high3` tinyint unsigned NOT NULL DEFAULT '0',
  `ip_low4` tinyint unsigned NOT NULL DEFAULT '0',
  `ip_high4` tinyint unsigned NOT NULL DEFAULT '0',
  `hostname` varchar(255) NOT NULL DEFAULT '',
  `email_address` varchar(255) NOT NULL DEFAULT '',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `hits` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_ban`),
  KEY `id_ban_group` (`id_ban_group`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1ban_items` WRITE;
/*!40000 ALTER TABLE `smf_1ban_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1ban_items` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1board_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1board_permissions` (
  `id_group` smallint NOT NULL DEFAULT '0',
  `id_profile` smallint unsigned NOT NULL DEFAULT '0',
  `permission` varchar(30) NOT NULL DEFAULT '',
  `add_deny` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_group`,`id_profile`,`permission`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1board_permissions` WRITE;
/*!40000 ALTER TABLE `smf_1board_permissions` DISABLE KEYS */;
INSERT INTO `smf_1board_permissions` VALUES (-1,1,'poll_view',1),(0,1,'remove_own',1),(0,1,'lock_own',1),(0,1,'mark_any_notify',1),(0,1,'mark_notify',1),(0,1,'modify_own',1),(0,1,'poll_add_own',1),(0,1,'poll_edit_own',1),(0,1,'poll_lock_own',1),(0,1,'poll_post',1),(0,1,'poll_view',1),(0,1,'poll_vote',1),(0,1,'post_attachment',1),(0,1,'post_new',1),(0,1,'post_reply_any',1),(0,1,'post_reply_own',1),(0,1,'post_unapproved_topics',1),(0,1,'post_unapproved_replies_any',1),(0,1,'post_unapproved_replies_own',1),(0,1,'post_unapproved_attachments',1),(0,1,'delete_own',1),(0,1,'report_any',1),(0,1,'send_topic',1),(0,1,'view_attachments',1),(2,1,'moderate_board',1),(2,1,'post_new',1),(2,1,'post_reply_own',1),(2,1,'post_reply_any',1),(2,1,'post_unapproved_topics',1),(2,1,'post_unapproved_replies_any',1),(2,1,'post_unapproved_replies_own',1),(2,1,'post_unapproved_attachments',1),(2,1,'poll_post',1),(2,1,'poll_add_any',1),(2,1,'poll_remove_any',1),(2,1,'poll_view',1),(2,1,'poll_vote',1),(2,1,'poll_lock_any',1),(2,1,'poll_edit_any',1),(2,1,'report_any',1),(2,1,'lock_own',1),(2,1,'send_topic',1),(2,1,'mark_any_notify',1),(2,1,'mark_notify',1),(2,1,'delete_own',1),(2,1,'modify_own',1),(2,1,'make_sticky',1),(2,1,'lock_any',1),(2,1,'remove_any',1),(2,1,'move_any',1),(2,1,'merge_any',1),(2,1,'split_any',1),(2,1,'delete_any',1),(2,1,'modify_any',1),(2,1,'approve_posts',1),(2,1,'post_attachment',1),(2,1,'view_attachments',1),(3,1,'moderate_board',1),(3,1,'post_new',1),(3,1,'post_reply_own',1),(3,1,'post_reply_any',1),(3,1,'post_unapproved_topics',1),(3,1,'post_unapproved_replies_any',1),(3,1,'post_unapproved_replies_own',1),(3,1,'post_unapproved_attachments',1),(3,1,'poll_post',1),(3,1,'poll_add_any',1),(3,1,'poll_remove_any',1),(3,1,'poll_view',1),(3,1,'poll_vote',1),(3,1,'poll_lock_any',1),(3,1,'poll_edit_any',1),(3,1,'report_any',1),(3,1,'lock_own',1),(3,1,'send_topic',1),(3,1,'mark_any_notify',1),(3,1,'mark_notify',1),(3,1,'delete_own',1),(3,1,'modify_own',1),(3,1,'make_sticky',1),(3,1,'lock_any',1),(3,1,'remove_any',1),(3,1,'move_any',1),(3,1,'merge_any',1),(3,1,'split_any',1),(3,1,'delete_any',1),(3,1,'modify_any',1),(3,1,'approve_posts',1),(3,1,'post_attachment',1),(3,1,'view_attachments',1),(-1,2,'poll_view',1),(0,2,'remove_own',1),(0,2,'lock_own',1),(0,2,'mark_any_notify',1),(0,2,'mark_notify',1),(0,2,'modify_own',1),(0,2,'poll_view',1),(0,2,'poll_vote',1),(0,2,'post_attachment',1),(0,2,'post_new',1),(0,2,'post_reply_any',1),(0,2,'post_reply_own',1),(0,2,'post_unapproved_topics',1),(0,2,'post_unapproved_replies_any',1),(0,2,'post_unapproved_replies_own',1),(0,2,'post_unapproved_attachments',1),(0,2,'delete_own',1),(0,2,'report_any',1),(0,2,'send_topic',1),(0,2,'view_attachments',1),(2,2,'moderate_board',1),(2,2,'post_new',1),(2,2,'post_reply_own',1),(2,2,'post_reply_any',1),(2,2,'post_unapproved_topics',1),(2,2,'post_unapproved_replies_any',1),(2,2,'post_unapproved_replies_own',1),(2,2,'post_unapproved_attachments',1),(2,2,'poll_post',1),(2,2,'poll_add_any',1),(2,2,'poll_remove_any',1),(2,2,'poll_view',1),(2,2,'poll_vote',1),(2,2,'poll_lock_any',1),(2,2,'poll_edit_any',1),(2,2,'report_any',1),(2,2,'lock_own',1),(2,2,'send_topic',1),(2,2,'mark_any_notify',1),(2,2,'mark_notify',1),(2,2,'delete_own',1),(2,2,'modify_own',1),(2,2,'make_sticky',1),(2,2,'lock_any',1),(2,2,'remove_any',1),(2,2,'move_any',1),(2,2,'merge_any',1),(2,2,'split_any',1),(2,2,'delete_any',1),(2,2,'modify_any',1),(2,2,'approve_posts',1),(2,2,'post_attachment',1),(2,2,'view_attachments',1),(3,2,'moderate_board',1),(3,2,'post_new',1),(3,2,'post_reply_own',1),(3,2,'post_reply_any',1),(3,2,'post_unapproved_topics',1),(3,2,'post_unapproved_replies_any',1),(3,2,'post_unapproved_replies_own',1),(3,2,'post_unapproved_attachments',1),(3,2,'poll_post',1),(3,2,'poll_add_any',1),(3,2,'poll_remove_any',1),(3,2,'poll_view',1),(3,2,'poll_vote',1),(3,2,'poll_lock_any',1),(3,2,'poll_edit_any',1),(3,2,'report_any',1),(3,2,'lock_own',1),(3,2,'send_topic',1),(3,2,'mark_any_notify',1),(3,2,'mark_notify',1),(3,2,'delete_own',1),(3,2,'modify_own',1),(3,2,'make_sticky',1),(3,2,'lock_any',1),(3,2,'remove_any',1),(3,2,'move_any',1),(3,2,'merge_any',1),(3,2,'split_any',1),(3,2,'delete_any',1),(3,2,'modify_any',1),(3,2,'approve_posts',1),(3,2,'post_attachment',1),(3,2,'view_attachments',1),(-1,3,'poll_view',1),(0,3,'remove_own',1),(0,3,'lock_own',1),(0,3,'mark_any_notify',1),(0,3,'mark_notify',1),(0,3,'modify_own',1),(0,3,'poll_view',1),(0,3,'poll_vote',1),(0,3,'post_attachment',1),(0,3,'post_reply_any',1),(0,3,'post_reply_own',1),(0,3,'post_unapproved_replies_any',1),(0,3,'post_unapproved_replies_own',1),(0,3,'post_unapproved_attachments',1),(0,3,'delete_own',1),(0,3,'report_any',1),(0,3,'send_topic',1),(0,3,'view_attachments',1),(2,3,'moderate_board',1),(2,3,'post_new',1),(2,3,'post_reply_own',1),(2,3,'post_reply_any',1),(2,3,'post_unapproved_topics',1),(2,3,'post_unapproved_replies_any',1),(2,3,'post_unapproved_replies_own',1),(2,3,'post_unapproved_attachments',1),(2,3,'poll_post',1),(2,3,'poll_add_any',1),(2,3,'poll_remove_any',1),(2,3,'poll_view',1),(2,3,'poll_vote',1),(2,3,'poll_lock_any',1),(2,3,'poll_edit_any',1),(2,3,'report_any',1),(2,3,'lock_own',1),(2,3,'send_topic',1),(2,3,'mark_any_notify',1),(2,3,'mark_notify',1),(2,3,'delete_own',1),(2,3,'modify_own',1),(2,3,'make_sticky',1),(2,3,'lock_any',1),(2,3,'remove_any',1),(2,3,'move_any',1),(2,3,'merge_any',1),(2,3,'split_any',1),(2,3,'delete_any',1),(2,3,'modify_any',1),(2,3,'approve_posts',1),(2,3,'post_attachment',1),(2,3,'view_attachments',1),(3,3,'moderate_board',1),(3,3,'post_new',1),(3,3,'post_reply_own',1),(3,3,'post_reply_any',1),(3,3,'post_unapproved_topics',1),(3,3,'post_unapproved_replies_any',1),(3,3,'post_unapproved_replies_own',1),(3,3,'post_unapproved_attachments',1),(3,3,'poll_post',1),(3,3,'poll_add_any',1),(3,3,'poll_remove_any',1),(3,3,'poll_view',1),(3,3,'poll_vote',1),(3,3,'poll_lock_any',1),(3,3,'poll_edit_any',1),(3,3,'report_any',1),(3,3,'lock_own',1),(3,3,'send_topic',1),(3,3,'mark_any_notify',1),(3,3,'mark_notify',1),(3,3,'delete_own',1),(3,3,'modify_own',1),(3,3,'make_sticky',1),(3,3,'lock_any',1),(3,3,'remove_any',1),(3,3,'move_any',1),(3,3,'merge_any',1),(3,3,'split_any',1),(3,3,'delete_any',1),(3,3,'modify_any',1),(3,3,'approve_posts',1),(3,3,'post_attachment',1),(3,3,'view_attachments',1),(-1,4,'poll_view',1),(0,4,'mark_any_notify',1),(0,4,'mark_notify',1),(0,4,'poll_view',1),(0,4,'poll_vote',1),(0,4,'report_any',1),(0,4,'send_topic',1),(0,4,'view_attachments',1),(2,4,'moderate_board',1),(2,4,'post_new',1),(2,4,'post_reply_own',1),(2,4,'post_reply_any',1),(2,4,'post_unapproved_topics',1),(2,4,'post_unapproved_replies_any',1),(2,4,'post_unapproved_replies_own',1),(2,4,'post_unapproved_attachments',1),(2,4,'poll_post',1),(2,4,'poll_add_any',1),(2,4,'poll_remove_any',1),(2,4,'poll_view',1),(2,4,'poll_vote',1),(2,4,'poll_lock_any',1),(2,4,'poll_edit_any',1),(2,4,'report_any',1),(2,4,'lock_own',1),(2,4,'send_topic',1),(2,4,'mark_any_notify',1),(2,4,'mark_notify',1),(2,4,'delete_own',1),(2,4,'modify_own',1),(2,4,'make_sticky',1),(2,4,'lock_any',1),(2,4,'remove_any',1),(2,4,'move_any',1),(2,4,'merge_any',1),(2,4,'split_any',1),(2,4,'delete_any',1),(2,4,'modify_any',1),(2,4,'approve_posts',1),(2,4,'post_attachment',1),(2,4,'view_attachments',1),(3,4,'moderate_board',1),(3,4,'post_new',1),(3,4,'post_reply_own',1),(3,4,'post_reply_any',1),(3,4,'post_unapproved_topics',1),(3,4,'post_unapproved_replies_any',1),(3,4,'post_unapproved_replies_own',1),(3,4,'post_unapproved_attachments',1),(3,4,'poll_post',1),(3,4,'poll_add_any',1),(3,4,'poll_remove_any',1),(3,4,'poll_view',1),(3,4,'poll_vote',1),(3,4,'poll_lock_any',1),(3,4,'poll_edit_any',1),(3,4,'report_any',1),(3,4,'lock_own',1),(3,4,'send_topic',1),(3,4,'mark_any_notify',1),(3,4,'mark_notify',1),(3,4,'delete_own',1),(3,4,'modify_own',1),(3,4,'make_sticky',1),(3,4,'lock_any',1),(3,4,'remove_any',1),(3,4,'move_any',1),(3,4,'merge_any',1),(3,4,'split_any',1),(3,4,'delete_any',1),(3,4,'modify_any',1),(3,4,'approve_posts',1),(3,4,'post_attachment',1),(3,4,'view_attachments',1);
/*!40000 ALTER TABLE `smf_1board_permissions` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1boards` (
  `id_board` smallint unsigned NOT NULL AUTO_INCREMENT,
  `id_cat` tinyint unsigned NOT NULL DEFAULT '0',
  `child_level` tinyint unsigned NOT NULL DEFAULT '0',
  `id_parent` smallint unsigned NOT NULL DEFAULT '0',
  `board_order` smallint NOT NULL DEFAULT '0',
  `id_last_msg` int unsigned NOT NULL DEFAULT '0',
  `id_msg_updated` int unsigned NOT NULL DEFAULT '0',
  `member_groups` varchar(255) NOT NULL DEFAULT '-1,0',
  `id_profile` smallint unsigned NOT NULL DEFAULT '1',
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `num_topics` mediumint unsigned NOT NULL DEFAULT '0',
  `num_posts` mediumint unsigned NOT NULL DEFAULT '0',
  `count_posts` tinyint NOT NULL DEFAULT '0',
  `id_theme` tinyint unsigned NOT NULL DEFAULT '0',
  `override_theme` tinyint unsigned NOT NULL DEFAULT '0',
  `unapproved_posts` smallint NOT NULL DEFAULT '0',
  `unapproved_topics` smallint NOT NULL DEFAULT '0',
  `redirect` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_board`),
  UNIQUE KEY `categories` (`id_cat`,`id_board`),
  KEY `id_parent` (`id_parent`),
  KEY `id_msg_updated` (`id_msg_updated`),
  KEY `member_groups` (`member_groups`(48))
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1boards` WRITE;
/*!40000 ALTER TABLE `smf_1boards` DISABLE KEYS */;
INSERT INTO `smf_1boards` VALUES (1,1,0,0,1,1,1,'-1,0,2',1,'General Discussion','Feel free to talk about anything and everything in this board.',1,1,0,0,0,0,0,'');
/*!40000 ALTER TABLE `smf_1boards` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1calendar` (
  `id_event` smallint unsigned NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL DEFAULT '0001-01-01',
  `end_date` date NOT NULL DEFAULT '0001-01-01',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL DEFAULT '',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_event`),
  KEY `start_date` (`start_date`),
  KEY `end_date` (`end_date`),
  KEY `topic` (`id_topic`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1calendar` WRITE;
/*!40000 ALTER TABLE `smf_1calendar` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1calendar` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1calendar_holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1calendar_holidays` (
  `id_holiday` smallint unsigned NOT NULL AUTO_INCREMENT,
  `event_date` date NOT NULL DEFAULT '0001-01-01',
  `title` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_holiday`),
  KEY `event_date` (`event_date`)
) ENGINE=MyISAM AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1calendar_holidays` WRITE;
/*!40000 ALTER TABLE `smf_1calendar_holidays` DISABLE KEYS */;
INSERT INTO `smf_1calendar_holidays` VALUES (1,'0004-01-01','New Year\'s'),(2,'0004-12-25','Christmas'),(3,'0004-02-14','Valentine\'s Day'),(4,'0004-03-17','St. Patrick\'s Day'),(5,'0004-04-01','April Fools'),(6,'0004-04-22','Earth Day'),(7,'0004-10-24','United Nations Day'),(8,'0004-10-31','Halloween'),(9,'2010-05-09','Mother\'s Day'),(10,'2011-05-08','Mother\'s Day'),(11,'2012-05-13','Mother\'s Day'),(12,'2013-05-12','Mother\'s Day'),(13,'2014-05-11','Mother\'s Day'),(14,'2015-05-10','Mother\'s Day'),(15,'2016-05-08','Mother\'s Day'),(16,'2017-05-14','Mother\'s Day'),(17,'2018-05-13','Mother\'s Day'),(18,'2019-05-12','Mother\'s Day'),(19,'2020-05-10','Mother\'s Day'),(20,'2008-06-15','Father\'s Day'),(21,'2009-06-21','Father\'s Day'),(22,'2010-06-20','Father\'s Day'),(23,'2011-06-19','Father\'s Day'),(24,'2012-06-17','Father\'s Day'),(25,'2013-06-16','Father\'s Day'),(26,'2014-06-15','Father\'s Day'),(27,'2015-06-21','Father\'s Day'),(28,'2016-06-19','Father\'s Day'),(29,'2017-06-18','Father\'s Day'),(30,'2018-06-17','Father\'s Day'),(31,'2019-06-16','Father\'s Day'),(32,'2020-06-21','Father\'s Day'),(33,'2010-06-21','Summer Solstice'),(34,'2011-06-21','Summer Solstice'),(35,'2012-06-20','Summer Solstice'),(36,'2013-06-21','Summer Solstice'),(37,'2014-06-21','Summer Solstice'),(38,'2015-06-21','Summer Solstice'),(39,'2016-06-20','Summer Solstice'),(40,'2017-06-20','Summer Solstice'),(41,'2018-06-21','Summer Solstice'),(42,'2019-06-21','Summer Solstice'),(43,'2020-06-20','Summer Solstice'),(44,'2010-03-20','Vernal Equinox'),(45,'2011-03-20','Vernal Equinox'),(46,'2012-03-20','Vernal Equinox'),(47,'2013-03-20','Vernal Equinox'),(48,'2014-03-20','Vernal Equinox'),(49,'2015-03-20','Vernal Equinox'),(50,'2016-03-19','Vernal Equinox'),(51,'2017-03-20','Vernal Equinox'),(52,'2018-03-20','Vernal Equinox'),(53,'2019-03-20','Vernal Equinox'),(54,'2020-03-19','Vernal Equinox'),(55,'2010-12-21','Winter Solstice'),(56,'2011-12-22','Winter Solstice'),(57,'2012-12-21','Winter Solstice'),(58,'2013-12-21','Winter Solstice'),(59,'2014-12-21','Winter Solstice'),(60,'2015-12-21','Winter Solstice'),(61,'2016-12-21','Winter Solstice'),(62,'2017-12-21','Winter Solstice'),(63,'2018-12-21','Winter Solstice'),(64,'2019-12-21','Winter Solstice'),(65,'2020-12-21','Winter Solstice'),(66,'2010-09-22','Autumnal Equinox'),(67,'2011-09-23','Autumnal Equinox'),(68,'2012-09-22','Autumnal Equinox'),(69,'2013-09-22','Autumnal Equinox'),(70,'2014-09-22','Autumnal Equinox'),(71,'2015-09-23','Autumnal Equinox'),(72,'2016-09-22','Autumnal Equinox'),(73,'2017-09-22','Autumnal Equinox'),(74,'2018-09-22','Autumnal Equinox'),(75,'2019-09-23','Autumnal Equinox'),(76,'2020-09-22','Autumnal Equinox'),(77,'0004-07-04','Independence Day'),(78,'0004-05-05','Cinco de Mayo'),(79,'0004-06-14','Flag Day'),(80,'0004-11-11','Veterans Day'),(81,'0004-02-02','Groundhog Day'),(82,'2010-11-25','Thanksgiving'),(83,'2011-11-24','Thanksgiving'),(84,'2012-11-22','Thanksgiving'),(85,'2013-11-28','Thanksgiving'),(86,'2014-11-27','Thanksgiving'),(87,'2015-11-26','Thanksgiving'),(88,'2016-11-24','Thanksgiving'),(89,'2017-11-23','Thanksgiving'),(90,'2018-11-22','Thanksgiving'),(91,'2019-11-28','Thanksgiving'),(92,'2020-11-26','Thanksgiving'),(93,'2010-05-31','Memorial Day'),(94,'2011-05-30','Memorial Day'),(95,'2012-05-28','Memorial Day'),(96,'2013-05-27','Memorial Day'),(97,'2014-05-26','Memorial Day'),(98,'2015-05-25','Memorial Day'),(99,'2016-05-30','Memorial Day'),(100,'2017-05-29','Memorial Day'),(101,'2018-05-28','Memorial Day'),(102,'2019-05-27','Memorial Day'),(103,'2020-05-25','Memorial Day'),(104,'2010-09-06','Labor Day'),(105,'2011-09-05','Labor Day'),(106,'2012-09-03','Labor Day'),(107,'2013-09-02','Labor Day'),(108,'2014-09-01','Labor Day'),(109,'2015-09-07','Labor Day'),(110,'2016-09-05','Labor Day'),(111,'2017-09-04','Labor Day'),(112,'2018-09-03','Labor Day'),(113,'2019-09-02','Labor Day'),(114,'2020-09-07','Labor Day'),(115,'0004-06-06','D-Day');
/*!40000 ALTER TABLE `smf_1calendar_holidays` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1categories` (
  `id_cat` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `cat_order` tinyint NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `can_collapse` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_cat`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1categories` WRITE;
/*!40000 ALTER TABLE `smf_1categories` DISABLE KEYS */;
INSERT INTO `smf_1categories` VALUES (1,0,'General Category',1);
/*!40000 ALTER TABLE `smf_1categories` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1collapsed_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1collapsed_categories` (
  `id_cat` tinyint unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_cat`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1collapsed_categories` WRITE;
/*!40000 ALTER TABLE `smf_1collapsed_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1collapsed_categories` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1custom_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1custom_fields` (
  `id_field` smallint NOT NULL AUTO_INCREMENT,
  `col_name` varchar(12) NOT NULL DEFAULT '',
  `field_name` varchar(40) NOT NULL DEFAULT '',
  `field_desc` varchar(255) NOT NULL DEFAULT '',
  `field_type` varchar(8) NOT NULL DEFAULT 'text',
  `field_length` smallint NOT NULL DEFAULT '255',
  `field_options` text NOT NULL,
  `mask` varchar(255) NOT NULL DEFAULT '',
  `show_reg` tinyint NOT NULL DEFAULT '0',
  `show_display` tinyint NOT NULL DEFAULT '0',
  `show_profile` varchar(20) NOT NULL DEFAULT 'forumprofile',
  `private` tinyint NOT NULL DEFAULT '0',
  `active` tinyint NOT NULL DEFAULT '1',
  `bbc` tinyint NOT NULL DEFAULT '0',
  `can_search` tinyint NOT NULL DEFAULT '0',
  `default_value` varchar(255) NOT NULL DEFAULT '',
  `enclose` text NOT NULL,
  `placement` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_field`),
  UNIQUE KEY `col_name` (`col_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1custom_fields` WRITE;
/*!40000 ALTER TABLE `smf_1custom_fields` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1custom_fields` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1group_moderators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1group_moderators` (
  `id_group` smallint unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_group`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1group_moderators` WRITE;
/*!40000 ALTER TABLE `smf_1group_moderators` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1group_moderators` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_actions` (
  `id_action` int unsigned NOT NULL AUTO_INCREMENT,
  `id_log` tinyint unsigned NOT NULL DEFAULT '1',
  `log_time` int unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `ip` char(16) NOT NULL DEFAULT '',
  `action` varchar(30) NOT NULL DEFAULT '',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  `extra` text NOT NULL,
  PRIMARY KEY (`id_action`),
  KEY `id_log` (`id_log`),
  KEY `log_time` (`log_time`),
  KEY `id_member` (`id_member`),
  KEY `id_board` (`id_board`),
  KEY `id_msg` (`id_msg`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_actions` WRITE;
/*!40000 ALTER TABLE `smf_1log_actions` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_actions` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_activity` (
  `date` date NOT NULL DEFAULT '0001-01-01',
  `hits` mediumint unsigned NOT NULL DEFAULT '0',
  `topics` smallint unsigned NOT NULL DEFAULT '0',
  `posts` smallint unsigned NOT NULL DEFAULT '0',
  `registers` smallint unsigned NOT NULL DEFAULT '0',
  `most_on` smallint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`date`),
  KEY `most_on` (`most_on`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_activity` WRITE;
/*!40000 ALTER TABLE `smf_1log_activity` DISABLE KEYS */;
INSERT INTO `smf_1log_activity` VALUES ('2026-07-12',0,1,1,1,1);
/*!40000 ALTER TABLE `smf_1log_activity` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_banned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_banned` (
  `id_ban_log` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `ip` char(16) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `log_time` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_ban_log`),
  KEY `log_time` (`log_time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_banned` WRITE;
/*!40000 ALTER TABLE `smf_1log_banned` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_banned` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_boards` (
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_board`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_boards` WRITE;
/*!40000 ALTER TABLE `smf_1log_boards` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_boards` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_comments` (
  `id_comment` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `member_name` varchar(80) NOT NULL DEFAULT '',
  `comment_type` varchar(8) NOT NULL DEFAULT 'warning',
  `id_recipient` mediumint unsigned NOT NULL DEFAULT '0',
  `recipient_name` varchar(255) NOT NULL DEFAULT '',
  `log_time` int NOT NULL DEFAULT '0',
  `id_notice` mediumint unsigned NOT NULL DEFAULT '0',
  `counter` tinyint NOT NULL DEFAULT '0',
  `body` text NOT NULL,
  PRIMARY KEY (`id_comment`),
  KEY `id_recipient` (`id_recipient`),
  KEY `log_time` (`log_time`),
  KEY `comment_type` (`comment_type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_comments` WRITE;
/*!40000 ALTER TABLE `smf_1log_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_comments` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_digest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_digest` (
  `id_topic` mediumint unsigned NOT NULL,
  `id_msg` int unsigned NOT NULL,
  `note_type` varchar(10) NOT NULL DEFAULT 'post',
  `daily` tinyint unsigned NOT NULL DEFAULT '0',
  `exclude` mediumint unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_digest` WRITE;
/*!40000 ALTER TABLE `smf_1log_digest` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_digest` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_errors` (
  `id_error` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `log_time` int unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `ip` char(16) NOT NULL DEFAULT '',
  `url` text NOT NULL,
  `message` text NOT NULL,
  `session` char(32) NOT NULL DEFAULT '',
  `error_type` char(15) NOT NULL DEFAULT 'general',
  `file` varchar(255) NOT NULL DEFAULT '',
  `line` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_error`),
  KEY `log_time` (`log_time`),
  KEY `id_member` (`id_member`),
  KEY `ip` (`ip`)
) ENGINE=MyISAM AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_errors` WRITE;
/*!40000 ALTER TABLE `smf_1log_errors` DISABLE KEYS */;
INSERT INTO `smf_1log_errors` VALUES (1,1783859712,0,'','?step=5','Could not retrieve the file http://www.simplemachines.org/smf/current-version.js?version=2.0.15.','bc603a92fbd98acd7e674aafeb1e62c2','general','',0),(2,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1088),(3,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1130),(4,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1179),(5,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1195),(6,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1214),(7,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1225),(8,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1305),(9,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1316),(10,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1392),(11,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1491),(12,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1516),(13,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1553),(14,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1584),(15,1783859761,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','6ea8b9300a2376fc85cb224a6843e064','general','/var/www/html/Sources/Subs.php',1595),(16,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1088),(17,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1130),(18,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1179),(19,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1195),(20,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1214),(21,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1225),(22,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1305),(23,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1316),(24,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1392),(25,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1491),(26,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1516),(27,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1553),(28,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1584),(29,1783859836,0,'10.89.3.7','?http://localhost:8090/','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1595),(30,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1088),(31,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1130),(32,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1179),(33,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1195),(34,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1214),(35,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1225),(36,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1305),(37,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1316),(38,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1392),(39,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1491),(40,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1516),(41,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1553),(42,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1584),(43,1783859862,0,'10.89.3.7','?board=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1595),(44,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1088),(45,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1130),(46,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1179),(47,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1195),(48,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1214),(49,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1225),(50,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1305),(51,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1316),(52,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1392),(53,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1491),(54,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1516),(55,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1553),(56,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1584),(57,1783859864,0,'10.89.3.7','?topic=1.0','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1595),(58,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1088),(59,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1130),(60,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1179),(61,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1195),(62,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1214),(63,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1225),(64,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1305),(65,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1316),(66,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1392),(67,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1491),(68,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1516),(69,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1553),(70,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1584),(71,1783859865,0,'10.89.3.7','?','8192: Function create_function() is deprecated','60ca29a83e47a546f588058585119991','general','/var/www/html/Sources/Subs.php',1595);
/*!40000 ALTER TABLE `smf_1log_errors` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_floodcontrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_floodcontrol` (
  `ip` char(16) NOT NULL DEFAULT '',
  `log_time` int unsigned NOT NULL DEFAULT '0',
  `log_type` varchar(8) NOT NULL DEFAULT 'post',
  PRIMARY KEY (`ip`,`log_type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_floodcontrol` WRITE;
/*!40000 ALTER TABLE `smf_1log_floodcontrol` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_floodcontrol` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_group_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_group_requests` (
  `id_request` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_group` smallint unsigned NOT NULL DEFAULT '0',
  `time_applied` int unsigned NOT NULL DEFAULT '0',
  `reason` text NOT NULL,
  PRIMARY KEY (`id_request`),
  UNIQUE KEY `id_member` (`id_member`,`id_group`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_group_requests` WRITE;
/*!40000 ALTER TABLE `smf_1log_group_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_group_requests` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_karma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_karma` (
  `id_target` mediumint unsigned NOT NULL DEFAULT '0',
  `id_executor` mediumint unsigned NOT NULL DEFAULT '0',
  `log_time` int unsigned NOT NULL DEFAULT '0',
  `action` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_target`,`id_executor`),
  KEY `log_time` (`log_time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_karma` WRITE;
/*!40000 ALTER TABLE `smf_1log_karma` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_karma` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_mark_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_mark_read` (
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_board`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_mark_read` WRITE;
/*!40000 ALTER TABLE `smf_1log_mark_read` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_mark_read` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_member_notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_member_notices` (
  `id_notice` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  PRIMARY KEY (`id_notice`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_member_notices` WRITE;
/*!40000 ALTER TABLE `smf_1log_member_notices` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_member_notices` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_notify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_notify` (
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `sent` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_topic`,`id_board`),
  KEY `id_topic` (`id_topic`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_notify` WRITE;
/*!40000 ALTER TABLE `smf_1log_notify` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_notify` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_online`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_online` (
  `session` varchar(32) NOT NULL DEFAULT '',
  `log_time` int NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_spider` smallint unsigned NOT NULL DEFAULT '0',
  `ip` int unsigned NOT NULL DEFAULT '0',
  `url` text NOT NULL,
  PRIMARY KEY (`session`),
  KEY `log_time` (`log_time`),
  KEY `id_member` (`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_online` WRITE;
/*!40000 ALTER TABLE `smf_1log_online` DISABLE KEYS */;
INSERT INTO `smf_1log_online` VALUES ('ip10.89.3.7',1783859862,0,0,173605639,'a:2:{s:5:\"board\";i:1;s:10:\"USER_AGENT\";s:70:\"Mozilla/5.0 (X11; Linux x86_64; rv:153.0) Gecko/20100101 Firefox/153.0\";}');
/*!40000 ALTER TABLE `smf_1log_online` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_packages` (
  `id_install` int NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL DEFAULT '',
  `package_id` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '',
  `version` varchar(255) NOT NULL DEFAULT '',
  `id_member_installed` mediumint NOT NULL DEFAULT '0',
  `member_installed` varchar(255) NOT NULL DEFAULT '',
  `time_installed` int NOT NULL DEFAULT '0',
  `id_member_removed` mediumint NOT NULL DEFAULT '0',
  `member_removed` varchar(255) NOT NULL DEFAULT '',
  `time_removed` int NOT NULL DEFAULT '0',
  `install_state` tinyint NOT NULL DEFAULT '1',
  `failed_steps` text NOT NULL,
  `themes_installed` varchar(255) NOT NULL DEFAULT '',
  `db_changes` text NOT NULL,
  PRIMARY KEY (`id_install`),
  KEY `filename` (`filename`(15))
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_packages` WRITE;
/*!40000 ALTER TABLE `smf_1log_packages` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_packages` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_polls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_polls` (
  `id_poll` mediumint unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_choice` tinyint unsigned NOT NULL DEFAULT '0',
  KEY `id_poll` (`id_poll`,`id_member`,`id_choice`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_polls` WRITE;
/*!40000 ALTER TABLE `smf_1log_polls` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_polls` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_reported`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_reported` (
  `id_report` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `membername` varchar(255) NOT NULL DEFAULT '',
  `subject` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  `time_started` int NOT NULL DEFAULT '0',
  `time_updated` int NOT NULL DEFAULT '0',
  `num_reports` mediumint NOT NULL DEFAULT '0',
  `closed` tinyint NOT NULL DEFAULT '0',
  `ignore_all` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_report`),
  KEY `id_member` (`id_member`),
  KEY `id_topic` (`id_topic`),
  KEY `closed` (`closed`),
  KEY `time_started` (`time_started`),
  KEY `id_msg` (`id_msg`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_reported` WRITE;
/*!40000 ALTER TABLE `smf_1log_reported` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_reported` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_reported_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_reported_comments` (
  `id_comment` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `id_report` mediumint NOT NULL DEFAULT '0',
  `id_member` mediumint NOT NULL,
  `membername` varchar(255) NOT NULL DEFAULT '',
  `email_address` varchar(255) NOT NULL DEFAULT '',
  `member_ip` varchar(255) NOT NULL DEFAULT '',
  `comment` varchar(255) NOT NULL DEFAULT '',
  `time_sent` int NOT NULL,
  PRIMARY KEY (`id_comment`),
  KEY `id_report` (`id_report`),
  KEY `id_member` (`id_member`),
  KEY `time_sent` (`time_sent`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_reported_comments` WRITE;
/*!40000 ALTER TABLE `smf_1log_reported_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_reported_comments` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_scheduled_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_scheduled_tasks` (
  `id_log` mediumint NOT NULL AUTO_INCREMENT,
  `id_task` smallint NOT NULL DEFAULT '0',
  `time_run` int NOT NULL DEFAULT '0',
  `time_taken` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_log`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_scheduled_tasks` WRITE;
/*!40000 ALTER TABLE `smf_1log_scheduled_tasks` DISABLE KEYS */;
INSERT INTO `smf_1log_scheduled_tasks` VALUES (1,1,1783859761,0),(2,2,1783859837,0),(3,3,1783859862,0),(4,5,1783859864,0),(5,6,1783859866,0);
/*!40000 ALTER TABLE `smf_1log_scheduled_tasks` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_search_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_search_messages` (
  `id_search` tinyint unsigned NOT NULL DEFAULT '0',
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_search`,`id_msg`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_search_messages` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_search_messages` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_search_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_search_results` (
  `id_search` tinyint unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  `relevance` smallint unsigned NOT NULL DEFAULT '0',
  `num_matches` smallint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_search`,`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_search_results` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_results` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_search_results` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_search_subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_search_subjects` (
  `word` varchar(20) NOT NULL DEFAULT '',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`word`,`id_topic`),
  KEY `id_topic` (`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_search_subjects` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_subjects` DISABLE KEYS */;
INSERT INTO `smf_1log_search_subjects` VALUES ('SMF',1),('to',1),('Welcome',1);
/*!40000 ALTER TABLE `smf_1log_search_subjects` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_search_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_search_topics` (
  `id_search` tinyint unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_search`,`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_search_topics` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_topics` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_search_topics` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_spider_hits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_spider_hits` (
  `id_hit` int unsigned NOT NULL AUTO_INCREMENT,
  `id_spider` smallint unsigned NOT NULL DEFAULT '0',
  `log_time` int unsigned NOT NULL DEFAULT '0',
  `url` varchar(255) NOT NULL DEFAULT '',
  `processed` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_hit`),
  KEY `id_spider` (`id_spider`),
  KEY `log_time` (`log_time`),
  KEY `processed` (`processed`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_spider_hits` WRITE;
/*!40000 ALTER TABLE `smf_1log_spider_hits` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_spider_hits` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_spider_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_spider_stats` (
  `id_spider` smallint unsigned NOT NULL DEFAULT '0',
  `page_hits` smallint unsigned NOT NULL DEFAULT '0',
  `last_seen` int unsigned NOT NULL DEFAULT '0',
  `stat_date` date NOT NULL DEFAULT '0001-01-01',
  PRIMARY KEY (`stat_date`,`id_spider`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_spider_stats` WRITE;
/*!40000 ALTER TABLE `smf_1log_spider_stats` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_spider_stats` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_subscribed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_subscribed` (
  `id_sublog` int unsigned NOT NULL AUTO_INCREMENT,
  `id_subscribe` mediumint unsigned NOT NULL DEFAULT '0',
  `id_member` int NOT NULL DEFAULT '0',
  `old_id_group` smallint NOT NULL DEFAULT '0',
  `start_time` int NOT NULL DEFAULT '0',
  `end_time` int NOT NULL DEFAULT '0',
  `status` tinyint NOT NULL DEFAULT '0',
  `payments_pending` tinyint NOT NULL DEFAULT '0',
  `pending_details` text NOT NULL,
  `reminder_sent` tinyint NOT NULL DEFAULT '0',
  `vendor_ref` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_sublog`),
  UNIQUE KEY `id_subscribe` (`id_subscribe`,`id_member`),
  KEY `end_time` (`end_time`),
  KEY `reminder_sent` (`reminder_sent`),
  KEY `payments_pending` (`payments_pending`),
  KEY `status` (`status`),
  KEY `id_member` (`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_subscribed` WRITE;
/*!40000 ALTER TABLE `smf_1log_subscribed` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_subscribed` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1log_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1log_topics` (
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  `id_msg` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_topic`),
  KEY `id_topic` (`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1log_topics` WRITE;
/*!40000 ALTER TABLE `smf_1log_topics` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_topics` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1mail_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1mail_queue` (
  `id_mail` int unsigned NOT NULL AUTO_INCREMENT,
  `time_sent` int NOT NULL DEFAULT '0',
  `recipient` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  `subject` varchar(255) NOT NULL DEFAULT '',
  `headers` text NOT NULL,
  `send_html` tinyint NOT NULL DEFAULT '0',
  `priority` tinyint NOT NULL DEFAULT '1',
  `private` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_mail`),
  KEY `time_sent` (`time_sent`),
  KEY `mail_priority` (`priority`,`id_mail`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1mail_queue` WRITE;
/*!40000 ALTER TABLE `smf_1mail_queue` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1mail_queue` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1membergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1membergroups` (
  `id_group` smallint unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(80) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `online_color` varchar(20) NOT NULL DEFAULT '',
  `min_posts` mediumint NOT NULL DEFAULT '-1',
  `max_messages` smallint unsigned NOT NULL DEFAULT '0',
  `stars` varchar(255) NOT NULL DEFAULT '',
  `group_type` tinyint NOT NULL DEFAULT '0',
  `hidden` tinyint NOT NULL DEFAULT '0',
  `id_parent` smallint NOT NULL DEFAULT '-2',
  PRIMARY KEY (`id_group`),
  KEY `min_posts` (`min_posts`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1membergroups` WRITE;
/*!40000 ALTER TABLE `smf_1membergroups` DISABLE KEYS */;
INSERT INTO `smf_1membergroups` VALUES (1,'Administrator','','#FF0000',-1,0,'5#staradmin.gif',1,0,-2),(2,'Global Moderator','','#0000FF',-1,0,'5#stargmod.gif',0,0,-2),(3,'Moderator','','',-1,0,'5#starmod.gif',0,0,-2),(4,'Newbie','','',0,0,'1#star.gif',0,0,-2),(5,'Jr. Member','','',50,0,'2#star.gif',0,0,-2),(6,'Full Member','','',100,0,'3#star.gif',0,0,-2),(7,'Sr. Member','','',250,0,'4#star.gif',0,0,-2),(8,'Hero Member','','',500,0,'5#star.gif',0,0,-2);
/*!40000 ALTER TABLE `smf_1membergroups` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1members` (
  `id_member` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `member_name` varchar(80) NOT NULL DEFAULT '',
  `date_registered` int unsigned NOT NULL DEFAULT '0',
  `posts` mediumint unsigned NOT NULL DEFAULT '0',
  `id_group` smallint unsigned NOT NULL DEFAULT '0',
  `lngfile` varchar(255) NOT NULL DEFAULT '',
  `last_login` int unsigned NOT NULL DEFAULT '0',
  `real_name` varchar(255) NOT NULL DEFAULT '',
  `instant_messages` smallint NOT NULL DEFAULT '0',
  `unread_messages` smallint NOT NULL DEFAULT '0',
  `new_pm` tinyint unsigned NOT NULL DEFAULT '0',
  `buddy_list` text NOT NULL,
  `pm_ignore_list` varchar(255) NOT NULL DEFAULT '',
  `pm_prefs` mediumint NOT NULL DEFAULT '0',
  `mod_prefs` varchar(20) NOT NULL DEFAULT '',
  `message_labels` text NOT NULL,
  `passwd` varchar(64) NOT NULL DEFAULT '',
  `openid_uri` text NOT NULL,
  `email_address` varchar(255) NOT NULL DEFAULT '',
  `personal_text` varchar(255) NOT NULL DEFAULT '',
  `gender` tinyint unsigned NOT NULL DEFAULT '0',
  `birthdate` date NOT NULL DEFAULT '0001-01-01',
  `website_title` varchar(255) NOT NULL DEFAULT '',
  `website_url` varchar(255) NOT NULL DEFAULT '',
  `location` varchar(255) NOT NULL DEFAULT '',
  `icq` varchar(255) NOT NULL DEFAULT '',
  `aim` varchar(255) NOT NULL DEFAULT '',
  `yim` varchar(32) NOT NULL DEFAULT '',
  `msn` varchar(255) NOT NULL DEFAULT '',
  `hide_email` tinyint NOT NULL DEFAULT '0',
  `show_online` tinyint NOT NULL DEFAULT '1',
  `time_format` varchar(80) NOT NULL DEFAULT '',
  `signature` text NOT NULL,
  `time_offset` float NOT NULL DEFAULT '0',
  `avatar` varchar(255) NOT NULL DEFAULT '',
  `pm_email_notify` tinyint NOT NULL DEFAULT '0',
  `karma_bad` smallint unsigned NOT NULL DEFAULT '0',
  `karma_good` smallint unsigned NOT NULL DEFAULT '0',
  `usertitle` varchar(255) NOT NULL DEFAULT '',
  `notify_announcements` tinyint NOT NULL DEFAULT '1',
  `notify_regularity` tinyint NOT NULL DEFAULT '1',
  `notify_send_body` tinyint NOT NULL DEFAULT '0',
  `notify_types` tinyint NOT NULL DEFAULT '2',
  `member_ip` varchar(255) NOT NULL DEFAULT '',
  `member_ip2` varchar(255) NOT NULL DEFAULT '',
  `secret_question` varchar(255) NOT NULL DEFAULT '',
  `secret_answer` varchar(64) NOT NULL DEFAULT '',
  `id_theme` tinyint unsigned NOT NULL DEFAULT '0',
  `is_activated` tinyint unsigned NOT NULL DEFAULT '1',
  `validation_code` varchar(10) NOT NULL DEFAULT '',
  `id_msg_last_visit` int unsigned NOT NULL DEFAULT '0',
  `additional_groups` varchar(255) NOT NULL DEFAULT '',
  `smiley_set` varchar(48) NOT NULL DEFAULT '',
  `id_post_group` smallint unsigned NOT NULL DEFAULT '0',
  `total_time_logged_in` int unsigned NOT NULL DEFAULT '0',
  `password_salt` varchar(255) NOT NULL DEFAULT '',
  `ignore_boards` text NOT NULL,
  `warning` tinyint NOT NULL DEFAULT '0',
  `passwd_flood` varchar(12) NOT NULL DEFAULT '',
  `pm_receive_from` tinyint unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_member`),
  KEY `member_name` (`member_name`),
  KEY `real_name` (`real_name`),
  KEY `date_registered` (`date_registered`),
  KEY `id_group` (`id_group`),
  KEY `birthdate` (`birthdate`),
  KEY `posts` (`posts`),
  KEY `last_login` (`last_login`),
  KEY `lngfile` (`lngfile`(30)),
  KEY `id_post_group` (`id_post_group`),
  KEY `warning` (`warning`),
  KEY `total_time_logged_in` (`total_time_logged_in`),
  KEY `id_theme` (`id_theme`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1members` WRITE;
/*!40000 ALTER TABLE `smf_1members` DISABLE KEYS */;
INSERT INTO `smf_1members` VALUES (1,'devadmin',1783859711,0,1,'',0,'devadmin',0,0,0,'','',0,'','','c398c51cd1502b8a7dbed773e1a184ca0c44224e','','admin@zfgc.test','',0,'0001-01-01','','','','','','','',0,1,'','',0,'',0,0,0,'',1,1,0,2,'10.89.3.7','10.89.3.7','','',0,1,'',0,'','',0,0,'1759','',0,'',1);
/*!40000 ALTER TABLE `smf_1members` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1message_icons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1message_icons` (
  `id_icon` smallint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL DEFAULT '',
  `filename` varchar(80) NOT NULL DEFAULT '',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `icon_order` smallint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_icon`),
  KEY `id_board` (`id_board`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1message_icons` WRITE;
/*!40000 ALTER TABLE `smf_1message_icons` DISABLE KEYS */;
INSERT INTO `smf_1message_icons` VALUES (1,'Standard','xx',0,0),(2,'Thumb Up','thumbup',0,1),(3,'Thumb Down','thumbdown',0,2),(4,'Exclamation point','exclamation',0,3),(5,'Question mark','question',0,4),(6,'Lamp','lamp',0,5),(7,'Smiley','smiley',0,6),(8,'Angry','angry',0,7),(9,'Cheesy','cheesy',0,8),(10,'Grin','grin',0,9),(11,'Sad','sad',0,10),(12,'Wink','wink',0,11);
/*!40000 ALTER TABLE `smf_1message_icons` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1messages` (
  `id_msg` int unsigned NOT NULL AUTO_INCREMENT,
  `id_topic` mediumint unsigned NOT NULL DEFAULT '0',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `poster_time` int unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `id_msg_modified` int unsigned NOT NULL DEFAULT '0',
  `subject` varchar(255) NOT NULL DEFAULT '',
  `poster_name` varchar(255) NOT NULL DEFAULT '',
  `poster_email` varchar(255) NOT NULL DEFAULT '',
  `poster_ip` varchar(255) NOT NULL DEFAULT '',
  `smileys_enabled` tinyint NOT NULL DEFAULT '1',
  `modified_time` int unsigned NOT NULL DEFAULT '0',
  `modified_name` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  `icon` varchar(16) NOT NULL DEFAULT 'xx',
  `approved` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_msg`),
  UNIQUE KEY `topic` (`id_topic`,`id_msg`),
  UNIQUE KEY `id_board` (`id_board`,`id_msg`),
  UNIQUE KEY `id_member` (`id_member`,`id_msg`),
  KEY `approved` (`approved`),
  KEY `ip_index` (`poster_ip`(15),`id_topic`),
  KEY `participation` (`id_member`,`id_topic`),
  KEY `show_posts` (`id_member`,`id_board`),
  KEY `id_topic` (`id_topic`),
  KEY `id_member_msg` (`id_member`,`approved`,`id_msg`),
  KEY `current_topic` (`id_topic`,`id_msg`,`id_member`,`approved`),
  KEY `related_ip` (`id_member`,`poster_ip`,`id_msg`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1messages` WRITE;
/*!40000 ALTER TABLE `smf_1messages` DISABLE KEYS */;
INSERT INTO `smf_1messages` VALUES (1,1,1,1783859655,0,1,'Welcome to SMF!','Simple Machines','info@simplemachines.org','127.0.0.1',1,0,'','Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines','xx',1);
/*!40000 ALTER TABLE `smf_1messages` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1moderators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1moderators` (
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_board`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1moderators` WRITE;
/*!40000 ALTER TABLE `smf_1moderators` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1moderators` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1openid_assoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1openid_assoc` (
  `server_url` text NOT NULL,
  `handle` varchar(255) NOT NULL DEFAULT '',
  `secret` text NOT NULL,
  `issued` int NOT NULL DEFAULT '0',
  `expires` int NOT NULL DEFAULT '0',
  `assoc_type` varchar(64) NOT NULL,
  PRIMARY KEY (`server_url`(125),`handle`(125)),
  KEY `expires` (`expires`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1openid_assoc` WRITE;
/*!40000 ALTER TABLE `smf_1openid_assoc` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1openid_assoc` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1package_servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1package_servers` (
  `id_server` smallint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_server`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1package_servers` WRITE;
/*!40000 ALTER TABLE `smf_1package_servers` DISABLE KEYS */;
INSERT INTO `smf_1package_servers` VALUES (1,'Simple Machines Third-party Mod Site','http://custom.simplemachines.org/packages/mods');
/*!40000 ALTER TABLE `smf_1package_servers` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1permission_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1permission_profiles` (
  `id_profile` smallint NOT NULL AUTO_INCREMENT,
  `profile_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_profile`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1permission_profiles` WRITE;
/*!40000 ALTER TABLE `smf_1permission_profiles` DISABLE KEYS */;
INSERT INTO `smf_1permission_profiles` VALUES (1,'default'),(2,'no_polls'),(3,'reply_only'),(4,'read_only');
/*!40000 ALTER TABLE `smf_1permission_profiles` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1permissions` (
  `id_group` smallint NOT NULL DEFAULT '0',
  `permission` varchar(30) NOT NULL DEFAULT '',
  `add_deny` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_group`,`permission`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1permissions` WRITE;
/*!40000 ALTER TABLE `smf_1permissions` DISABLE KEYS */;
INSERT INTO `smf_1permissions` VALUES (-1,'search_posts',1),(-1,'calendar_view',1),(-1,'view_stats',1),(-1,'profile_view_any',1),(0,'view_mlist',1),(0,'search_posts',1),(0,'profile_view_own',1),(0,'profile_view_any',1),(0,'pm_read',1),(0,'pm_send',1),(0,'calendar_view',1),(0,'view_stats',1),(0,'who_view',1),(0,'profile_identity_own',1),(0,'profile_extra_own',1),(0,'profile_remove_own',1),(0,'profile_server_avatar',1),(0,'profile_upload_avatar',1),(0,'profile_remote_avatar',1),(0,'karma_edit',1),(2,'view_mlist',1),(2,'search_posts',1),(2,'profile_view_own',1),(2,'profile_view_any',1),(2,'pm_read',1),(2,'pm_send',1),(2,'calendar_view',1),(2,'view_stats',1),(2,'who_view',1),(2,'profile_identity_own',1),(2,'profile_extra_own',1),(2,'profile_remove_own',1),(2,'profile_server_avatar',1),(2,'profile_upload_avatar',1),(2,'profile_remote_avatar',1),(2,'profile_title_own',1),(2,'calendar_post',1),(2,'calendar_edit_any',1),(2,'karma_edit',1),(2,'access_mod_center',1);
/*!40000 ALTER TABLE `smf_1permissions` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1personal_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1personal_messages` (
  `id_pm` int unsigned NOT NULL AUTO_INCREMENT,
  `id_pm_head` int unsigned NOT NULL DEFAULT '0',
  `id_member_from` mediumint unsigned NOT NULL DEFAULT '0',
  `deleted_by_sender` tinyint unsigned NOT NULL DEFAULT '0',
  `from_name` varchar(255) NOT NULL DEFAULT '',
  `msgtime` int unsigned NOT NULL DEFAULT '0',
  `subject` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  PRIMARY KEY (`id_pm`),
  KEY `id_member` (`id_member_from`,`deleted_by_sender`),
  KEY `msgtime` (`msgtime`),
  KEY `id_pm_head` (`id_pm_head`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1personal_messages` WRITE;
/*!40000 ALTER TABLE `smf_1personal_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1personal_messages` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1pm_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1pm_recipients` (
  `id_pm` int unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint unsigned NOT NULL DEFAULT '0',
  `labels` varchar(60) NOT NULL DEFAULT '-1',
  `bcc` tinyint unsigned NOT NULL DEFAULT '0',
  `is_read` tinyint unsigned NOT NULL DEFAULT '0',
  `is_new` tinyint unsigned NOT NULL DEFAULT '0',
  `deleted` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_pm`,`id_member`),
  UNIQUE KEY `id_member` (`id_member`,`deleted`,`id_pm`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1pm_recipients` WRITE;
/*!40000 ALTER TABLE `smf_1pm_recipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1pm_recipients` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1pm_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1pm_rules` (
  `id_rule` int unsigned NOT NULL AUTO_INCREMENT,
  `id_member` int unsigned NOT NULL DEFAULT '0',
  `rule_name` varchar(60) NOT NULL,
  `criteria` text NOT NULL,
  `actions` text NOT NULL,
  `delete_pm` tinyint unsigned NOT NULL DEFAULT '0',
  `is_or` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_rule`),
  KEY `id_member` (`id_member`),
  KEY `delete_pm` (`delete_pm`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1pm_rules` WRITE;
/*!40000 ALTER TABLE `smf_1pm_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1pm_rules` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1poll_choices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1poll_choices` (
  `id_poll` mediumint unsigned NOT NULL DEFAULT '0',
  `id_choice` tinyint unsigned NOT NULL DEFAULT '0',
  `label` varchar(255) NOT NULL DEFAULT '',
  `votes` smallint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_poll`,`id_choice`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1poll_choices` WRITE;
/*!40000 ALTER TABLE `smf_1poll_choices` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1poll_choices` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1polls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1polls` (
  `id_poll` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL DEFAULT '',
  `voting_locked` tinyint(1) NOT NULL DEFAULT '0',
  `max_votes` tinyint unsigned NOT NULL DEFAULT '1',
  `expire_time` int unsigned NOT NULL DEFAULT '0',
  `hide_results` tinyint unsigned NOT NULL DEFAULT '0',
  `change_vote` tinyint unsigned NOT NULL DEFAULT '0',
  `guest_vote` tinyint unsigned NOT NULL DEFAULT '0',
  `num_guest_voters` int unsigned NOT NULL DEFAULT '0',
  `reset_poll` int unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint NOT NULL DEFAULT '0',
  `poster_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_poll`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1polls` WRITE;
/*!40000 ALTER TABLE `smf_1polls` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1polls` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1scheduled_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1scheduled_tasks` (
  `id_task` smallint NOT NULL AUTO_INCREMENT,
  `next_time` int NOT NULL DEFAULT '0',
  `time_offset` int NOT NULL DEFAULT '0',
  `time_regularity` smallint NOT NULL DEFAULT '0',
  `time_unit` varchar(1) NOT NULL DEFAULT 'h',
  `disabled` tinyint NOT NULL DEFAULT '0',
  `task` varchar(24) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_task`),
  UNIQUE KEY `task` (`task`),
  KEY `next_time` (`next_time`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1scheduled_tasks` WRITE;
/*!40000 ALTER TABLE `smf_1scheduled_tasks` DISABLE KEYS */;
INSERT INTO `smf_1scheduled_tasks` VALUES (1,1783864800,0,2,'h',0,'approval_notification'),(2,1784419200,0,7,'d',0,'auto_optimize'),(3,1783987260,60,1,'d',0,'daily_maintenance'),(5,1783987200,0,1,'d',0,'daily_digest'),(6,1784419200,0,1,'w',0,'weekly_digest'),(7,0,136183,1,'d',0,'fetchSMfiles'),(8,0,0,1,'d',1,'birthdayemails'),(9,0,0,1,'w',0,'weekly_maintenance'),(10,0,120,1,'d',1,'paid_subscriptions');
/*!40000 ALTER TABLE `smf_1scheduled_tasks` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1sessions` (
  `session_id` char(32) NOT NULL,
  `last_update` int unsigned NOT NULL,
  `data` text NOT NULL,
  PRIMARY KEY (`session_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1sessions` WRITE;
/*!40000 ALTER TABLE `smf_1sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1sessions` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1settings` (
  `variable` varchar(255) NOT NULL DEFAULT '',
  `value` text NOT NULL,
  PRIMARY KEY (`variable`(30))
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1settings` WRITE;
/*!40000 ALTER TABLE `smf_1settings` DISABLE KEYS */;
INSERT INTO `smf_1settings` VALUES ('smfVersion','2.0.15'),('news','SMF - Just Installed!'),('compactTopicPagesContiguous','5'),('compactTopicPagesEnable','1'),('enableStickyTopics','1'),('todayMod','1'),('karmaMode','0'),('karmaTimeRestrictAdmins','1'),('enablePreviousNext','1'),('pollMode','1'),('enableVBStyleLogin','1'),('enableCompressedOutput','0'),('karmaWaitTime','1'),('karmaMinPosts','0'),('karmaLabel','Karma:'),('karmaSmiteLabel','[smite]'),('karmaApplaudLabel','[applaud]'),('attachmentSizeLimit','128'),('attachmentPostLimit','192'),('attachmentNumPerPostLimit','4'),('attachmentDirSizeLimit','10240'),('attachmentUploadDir','/var/www/html/attachments'),('attachmentExtensions','doc,gif,jpg,mpg,pdf,png,txt,zip'),('attachmentCheckExtensions','0'),('attachmentShowImages','1'),('attachmentEnable','1'),('attachmentEncryptFilenames','1'),('attachmentThumbnails','1'),('attachmentThumbWidth','150'),('attachmentThumbHeight','150'),('censorIgnoreCase','1'),('mostOnline','1'),('mostOnlineToday','1'),('mostDate','1783859865'),('allow_disableAnnounce','1'),('trackStats','1'),('userLanguage','1'),('titlesEnable','1'),('topicSummaryPosts','15'),('enableErrorLogging','1'),('max_image_width','0'),('max_image_height','0'),('onlineEnable','0'),('cal_enabled','0'),('cal_maxyear','2030'),('cal_minyear','2008'),('cal_daysaslink','0'),('cal_defaultboard',''),('cal_showholidays','1'),('cal_showbdays','1'),('cal_showevents','1'),('cal_showweeknum','0'),('cal_maxspan','7'),('smtp_host',''),('smtp_port','25'),('smtp_username',''),('smtp_password',''),('mail_type','0'),('timeLoadPageEnable','0'),('totalMembers','1'),('totalTopics','1'),('totalMessages','1'),('simpleSearch','0'),('censor_vulgar',''),('censor_proper',''),('enablePostHTML','0'),('theme_allow','1'),('theme_default','1'),('theme_guests','1'),('enableEmbeddedFlash','0'),('xmlnews_enable','1'),('xmlnews_maxlen','255'),('hotTopicPosts','15'),('hotTopicVeryPosts','25'),('registration_method','0'),('send_validation_onChange','0'),('send_welcomeEmail','1'),('allow_editDisplayName','1'),('allow_hideOnline','1'),('guest_hideContacts','1'),('spamWaitTime','5'),('pm_spam_settings','10,5,20'),('reserveWord','0'),('reserveCase','1'),('reserveUser','1'),('reserveName','1'),('reserveNames','Admin\nWebmaster\nGuest\nroot'),('autoLinkUrls','1'),('banLastUpdated','0'),('smileys_dir','/var/www/html/Smileys'),('smileys_url','http://localhost:8090/Smileys'),('avatar_directory','/var/www/html/avatars'),('avatar_url','http://localhost:8090/avatars'),('avatar_max_height_external','65'),('avatar_max_width_external','65'),('avatar_action_too_large','option_html_resize'),('avatar_max_height_upload','65'),('avatar_max_width_upload','65'),('avatar_resize_upload','1'),('avatar_download_png','1'),('failed_login_threshold','3'),('oldTopicDays','120'),('edit_wait_time','90'),('edit_disable_time','0'),('autoFixDatabase','1'),('allow_guestAccess','1'),('time_format','%B %d, %Y, %I:%M:%S %p'),('number_format','1234.00'),('enableBBC','1'),('max_messageLength','20000'),('signature_settings','1,300,0,0,0,0,0,0:'),('autoOptMaxOnline','0'),('defaultMaxMessages','15'),('defaultMaxTopics','20'),('defaultMaxMembers','30'),('enableParticipation','1'),('recycle_enable','0'),('recycle_board','0'),('maxMsgID','1'),('enableAllMessages','0'),('fixLongWords','0'),('knownThemes','1,2,3'),('who_enabled','1'),('time_offset','0'),('cookieTime','60'),('lastActive','15'),('smiley_sets_known','default,aaron,akyhne'),('smiley_sets_names','Alienine\'s Set\nAaron\'s Set\nAkyhne\'s Set'),('smiley_sets_default','default'),('cal_days_for_index','7'),('requireAgreement','1'),('unapprovedMembers','0'),('default_personal_text',''),('package_make_backups','1'),('databaseSession_enable','0'),('databaseSession_loose','1'),('databaseSession_lifetime','2880'),('search_cache_size','50'),('search_results_per_page','30'),('search_weight_frequency','30'),('search_weight_age','25'),('search_weight_length','20'),('search_weight_subject','15'),('search_weight_first_message','10'),('search_max_results','1200'),('search_floodcontrol_time','5'),('permission_enable_deny','0'),('permission_enable_postgroups','0'),('mail_next_send','0'),('mail_recent','0000000000|0'),('settings_updated','0'),('next_task_time','0'),('warning_settings','1,20,0'),('warning_watch','10'),('warning_moderate','35'),('warning_mute','60'),('admin_features',''),('last_mod_report_action','0'),('pruningOptions','30,180,180,180,30,0'),('cache_enable','1'),('reg_verification','1'),('visual_verification_type','3'),('enable_buddylist','1'),('birthday_email','happy_birthday'),('dont_repeat_theme_core','1'),('dont_repeat_smileys_20','1'),('dont_repeat_buddylists','1'),('attachment_image_reencode','1'),('attachment_image_paranoid','0'),('attachment_thumb_png','1'),('avatar_reencode','1'),('avatar_paranoid','0'),('global_character_set','UTF-8'),('globalCookies','1'),('default_timezone','Etc/GMT0'),('memberlist_updated','1783859711'),('latestMember','1'),('latestRealName','devadmin'),('rand_seed','722123804'),('mostOnlineUpdated','2026-07-12');
/*!40000 ALTER TABLE `smf_1settings` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1smileys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1smileys` (
  `id_smiley` smallint unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL DEFAULT '',
  `filename` varchar(48) NOT NULL DEFAULT '',
  `description` varchar(80) NOT NULL DEFAULT '',
  `smiley_row` tinyint unsigned NOT NULL DEFAULT '0',
  `smiley_order` smallint unsigned NOT NULL DEFAULT '0',
  `hidden` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_smiley`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1smileys` WRITE;
/*!40000 ALTER TABLE `smf_1smileys` DISABLE KEYS */;
INSERT INTO `smf_1smileys` VALUES (1,':)','smiley.gif','Smiley',0,0,0),(2,';)','wink.gif','Wink',0,1,0),(3,':D','cheesy.gif','Cheesy',0,2,0),(4,';D','grin.gif','Grin',0,3,0),(5,'>:(','angry.gif','Angry',0,4,0),(6,':(','sad.gif','Sad',0,5,0),(7,':o','shocked.gif','Shocked',0,6,0),(8,'8)','cool.gif','Cool',0,7,0),(9,'???','huh.gif','Huh?',0,8,0),(10,'::)','rolleyes.gif','Roll Eyes',0,9,0),(11,':P','tongue.gif','Tongue',0,10,0),(12,':-[','embarrassed.gif','Embarrassed',0,11,0),(13,':-X','lipsrsealed.gif','Lips Sealed',0,12,0),(14,':-\\','undecided.gif','Undecided',0,13,0),(15,':-*','kiss.gif','Kiss',0,14,0),(16,':\'(','cry.gif','Cry',0,15,0),(17,'>:D','evil.gif','Evil',0,16,1),(18,'^-^','azn.gif','Azn',0,17,1),(19,'O0','afro.gif','Afro',0,18,1),(20,':))','laugh.gif','Laugh',0,19,1),(21,'C:-)','police.gif','Police',0,20,1),(22,'O:-)','angel.gif','Angel',0,21,1);
/*!40000 ALTER TABLE `smf_1smileys` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1spiders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1spiders` (
  `id_spider` smallint unsigned NOT NULL AUTO_INCREMENT,
  `spider_name` varchar(255) NOT NULL DEFAULT '',
  `user_agent` varchar(255) NOT NULL DEFAULT '',
  `ip_info` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_spider`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1spiders` WRITE;
/*!40000 ALTER TABLE `smf_1spiders` DISABLE KEYS */;
INSERT INTO `smf_1spiders` VALUES (1,'Google','googlebot',''),(2,'Yahoo!','slurp',''),(3,'MSN','msnbot',''),(4,'Google (Mobile)','Googlebot-Mobile',''),(5,'Google (Image)','Googlebot-Image',''),(6,'Google (AdSense)','Mediapartners-Google',''),(7,'Google (Adwords)','AdsBot-Google',''),(8,'Yahoo! (Mobile)','YahooSeeker/M1A1-R2D2',''),(9,'Yahoo! (Image)','Yahoo-MMCrawler',''),(10,'MSN (Mobile)','MSNBOT_Mobile',''),(11,'MSN (Media)','msnbot-media',''),(12,'Cuil','twiceler',''),(13,'Ask','Teoma',''),(14,'Baidu','Baiduspider',''),(15,'Gigablast','Gigabot',''),(16,'InternetArchive','ia_archiver-web.archive.org',''),(17,'Alexa','ia_archiver',''),(18,'Omgili','omgilibot',''),(19,'EntireWeb','Speedy Spider','');
/*!40000 ALTER TABLE `smf_1spiders` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1subscriptions` (
  `id_subscribe` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `cost` text NOT NULL,
  `length` varchar(6) NOT NULL DEFAULT '',
  `id_group` smallint NOT NULL DEFAULT '0',
  `add_groups` varchar(40) NOT NULL DEFAULT '',
  `active` tinyint NOT NULL DEFAULT '1',
  `repeatable` tinyint NOT NULL DEFAULT '0',
  `allow_partial` tinyint NOT NULL DEFAULT '0',
  `reminder` tinyint NOT NULL DEFAULT '0',
  `email_complete` text NOT NULL,
  PRIMARY KEY (`id_subscribe`),
  KEY `active` (`active`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1subscriptions` WRITE;
/*!40000 ALTER TABLE `smf_1subscriptions` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1subscriptions` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1themes` (
  `id_member` mediumint NOT NULL DEFAULT '0',
  `id_theme` tinyint unsigned NOT NULL DEFAULT '1',
  `variable` varchar(255) NOT NULL DEFAULT '',
  `value` text NOT NULL,
  PRIMARY KEY (`id_theme`,`id_member`,`variable`(30)),
  KEY `id_member` (`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1themes` WRITE;
/*!40000 ALTER TABLE `smf_1themes` DISABLE KEYS */;
INSERT INTO `smf_1themes` VALUES (0,1,'name','SMF Default Theme - Curve'),(0,1,'theme_url','http://localhost:8090/Themes/default'),(0,1,'images_url','http://localhost:8090/Themes/default/images'),(0,1,'theme_dir','/var/www/html/Themes/default'),(0,1,'show_bbc','1'),(0,1,'show_latest_member','1'),(0,1,'show_modify','1'),(0,1,'show_user_images','1'),(0,1,'show_blurb','1'),(0,1,'show_gender','0'),(0,1,'show_newsfader','0'),(0,1,'number_recent_posts','0'),(0,1,'show_member_bar','1'),(0,1,'linktree_link','1'),(0,1,'show_profile_buttons','1'),(0,1,'show_mark_read','1'),(0,1,'show_stats_index','1'),(0,1,'linktree_inline','0'),(0,1,'show_board_desc','1'),(0,1,'newsfader_time','5000'),(0,1,'allow_no_censored','0'),(0,1,'additional_options_collapsable','1'),(0,1,'use_image_buttons','1'),(0,1,'enable_news','1'),(0,1,'forum_width','90%'),(0,2,'name','Core Theme'),(0,2,'theme_url','http://localhost:8090/Themes/core'),(0,2,'images_url','http://localhost:8090/Themes/core/images'),(0,2,'theme_dir','/var/www/html/Themes/core'),(-1,1,'display_quick_reply','1'),(-1,1,'posts_apply_ignore_list','1');
/*!40000 ALTER TABLE `smf_1themes` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `smf_1topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1topics` (
  `id_topic` mediumint unsigned NOT NULL AUTO_INCREMENT,
  `is_sticky` tinyint NOT NULL DEFAULT '0',
  `id_board` smallint unsigned NOT NULL DEFAULT '0',
  `id_first_msg` int unsigned NOT NULL DEFAULT '0',
  `id_last_msg` int unsigned NOT NULL DEFAULT '0',
  `id_member_started` mediumint unsigned NOT NULL DEFAULT '0',
  `id_member_updated` mediumint unsigned NOT NULL DEFAULT '0',
  `id_poll` mediumint unsigned NOT NULL DEFAULT '0',
  `id_previous_board` smallint NOT NULL DEFAULT '0',
  `id_previous_topic` mediumint NOT NULL DEFAULT '0',
  `num_replies` int unsigned NOT NULL DEFAULT '0',
  `num_views` int unsigned NOT NULL DEFAULT '0',
  `locked` tinyint NOT NULL DEFAULT '0',
  `unapproved_posts` smallint NOT NULL DEFAULT '0',
  `approved` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_topic`),
  UNIQUE KEY `last_message` (`id_last_msg`,`id_board`),
  UNIQUE KEY `first_message` (`id_first_msg`,`id_board`),
  UNIQUE KEY `poll` (`id_poll`,`id_topic`),
  KEY `is_sticky` (`is_sticky`),
  KEY `approved` (`approved`),
  KEY `id_board` (`id_board`),
  KEY `member_started` (`id_member_started`,`id_board`),
  KEY `last_message_sticky` (`id_board`,`is_sticky`,`id_last_msg`),
  KEY `board_news` (`id_board`,`id_first_msg`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `smf_1topics` WRITE;
/*!40000 ALTER TABLE `smf_1topics` DISABLE KEYS */;
INSERT INTO `smf_1topics` VALUES (1,0,1,1,1,0,0,0,0,0,0,1,0,0,1);
/*!40000 ALTER TABLE `smf_1topics` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

