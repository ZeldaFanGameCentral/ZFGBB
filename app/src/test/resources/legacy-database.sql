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
-- Table structure for table `ci_potms`
--

DROP TABLE IF EXISTS `ci_potms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_potms` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `preview` varchar(255) NOT NULL,
  `project_id` int(10) unsigned NOT NULL,
  `project_title` tinytext NOT NULL,
  `time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `time` (`time`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_potms`
--

LOCK TABLES `ci_potms` WRITE;
/*!40000 ALTER TABLE `ci_potms` DISABLE KEYS */;
INSERT INTO `ci_potms` VALUES (1,'79710dc985fd32164a66c724a750b4c9.png',7,'Ocarina of Time',1699584000),(2,'a3c2e1909b1f22164a66c724a750d1e8.png',99,'The Wind Waker',1673304000);
/*!40000 ALTER TABLE `ci_potms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_project_downloads`
--

DROP TABLE IF EXISTS `ci_project_downloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_project_downloads` (
  `description` tinytext NOT NULL,
  `downloads` int(10) unsigned NOT NULL,
  `edited_member_name` varchar(80) NOT NULL,
  `edited_member_id` int(10) unsigned NOT NULL,
  `edited_time` int(10) unsigned NOT NULL,
  `file` varchar(255) NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `member_id` int(10) unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `project_id` int(10) unsigned NOT NULL,
  `size` int(10) unsigned NOT NULL,
  `time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file` (`file`),
  KEY `project_id` (`project_id`),
  KEY `size` (`size`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_project_downloads`
--

LOCK TABLES `ci_project_downloads` WRITE;
/*!40000 ALTER TABLE `ci_project_downloads` DISABLE KEYS */;
INSERT INTO `ci_project_downloads` VALUES ('Lorem ipsum demo.',171,'',0,0,'oot_demo.zip',4,'10.0.0.7',585,'StalfosSlayer',7,170,1695544774),('Dolor sit demo. (file lost)',113,'',0,0,'oot_demo_old.zip',5,'10.0.0.7',585,'StalfosSlayer',7,1362881,1695594943),('Consectetur demo.',64,'',0,0,'mm3d_demo.zip',6,'10.0.0.23',4577,'NaviBot',23,170,1760985470),('Adipiscing slice. (file lost)',22,'',0,0,'mm_demo_2011.zip',7,'10.0.0.66',18966,'MajoraMain',66,80000,1728940370),('Eiusmod demo.',35,'',0,0,'mm_demo_2012.zip',8,'10.0.0.90',27774,'MajoraMain',90,170,1760985480),('Tempor demo.',51,'',0,0,'ww_demo.zip',9,'10.0.0.99',27718,'TriforceTim',99,170,1760985490);
/*!40000 ALTER TABLE `ci_project_downloads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_project_news`
--

DROP TABLE IF EXISTS `ci_project_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_project_news` (
  `project_id` int(10) unsigned NOT NULL,
  `topic_id` mediumint(8) unsigned NOT NULL,
  PRIMARY KEY (`project_id`,`topic_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_project_news`
--

LOCK TABLES `ci_project_news` WRITE;
/*!40000 ALTER TABLE `ci_project_news` DISABLE KEYS */;
INSERT INTO `ci_project_news` VALUES (7,9);
/*!40000 ALTER TABLE `ci_project_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_project_screenshots`
--

DROP TABLE IF EXISTS `ci_project_screenshots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_project_screenshots` (
  `description` tinytext NOT NULL,
  `edited_member_name` varchar(80) NOT NULL,
  `edited_member_id` int(10) unsigned NOT NULL,
  `edited_time` int(10) unsigned NOT NULL,
  `file` varchar(255) NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `member_id` int(10) unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `preview` varchar(255) NOT NULL,
  `project_id` int(10) unsigned NOT NULL,
  `time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file` (`file`),
  KEY `project_id` (`project_id`)
) ENGINE=MyISAM AUTO_INCREMENT=214 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_project_screenshots`
--

LOCK TABLES `ci_project_screenshots` WRITE;
/*!40000 ALTER TABLE `ci_project_screenshots` DISABLE KEYS */;
INSERT INTO `ci_project_screenshots` VALUES ('Lorem ipsum dolor','',0,0,'majora_screenshot.png',160,'10.0.0.66',18966,'MajoraMain','majora_screenshot_thumb.png',66,1728940364),('Sit amet consectetur (file lost)','',0,0,'majora_beta_shot.png',211,'10.0.0.90',27774,'MajoraMain','majora_beta_shot_thumb.png',90,1760985467),('Adipiscing elit sed','',0,0,'windwaker_screenshot.jpg',213,'10.0.0.99',27718,'TriforceTim','windwaker_screenshot_thumb.jpg',99,1760985561);
/*!40000 ALTER TABLE `ci_project_screenshots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_project_tags`
--

DROP TABLE IF EXISTS `ci_project_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_project_tags` (
  `project_id` int(10) unsigned NOT NULL,
  `tag_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`project_id`,`tag_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_project_tags`
--

LOCK TABLES `ci_project_tags` WRITE;
/*!40000 ALTER TABLE `ci_project_tags` DISABLE KEYS */;
INSERT INTO `ci_project_tags` VALUES (7,1),(7,2);
/*!40000 ALTER TABLE `ci_project_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_projects`
--

DROP TABLE IF EXISTS `ci_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_projects` (
  `description` text NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `language` tinytext NOT NULL,
  `preview` tinytext NOT NULL,
  `progress` tinyint(3) unsigned NOT NULL,
  `rating` float unsigned NOT NULL,
  `score` int(10) unsigned NOT NULL,
  `status` tinyint(1) NOT NULL,
  `team_id` int(10) unsigned NOT NULL,
  `time_created` int(10) unsigned NOT NULL,
  `topic_id` int(10) unsigned NOT NULL,
  `member_id` int(10) unsigned NOT NULL,
  `views` smallint(5) unsigned NOT NULL,
  `downloads` smallint(5) unsigned NOT NULL,
  `last_updated` int(10) unsigned NOT NULL,
  `title` tinytext NOT NULL,
  `votes` int(10) unsigned NOT NULL,
  `screenshot_id` int(10) unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `requirements` tinytext NOT NULL,
  `topic_template` text NOT NULL,
  `type` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_screenshot` (`member_id`),
  KEY `screenshot_id` (`screenshot_id`),
  KEY `score` (`score`),
  KEY `time_created` (`time_created`),
  KEY `status` (`status`)
) ENGINE=MyISAM AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_projects`
--

LOCK TABLES `ci_projects` WRITE;
/*!40000 ALTER TABLE `ci_projects` DISABLE KEYS */;
INSERT INTO `ci_projects` VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',7,'84.86.240.136','Game Maker','oot_boxart.jpg',15,5,16570,3,0,0,9,3,8125,0,1700422207,'Ocarina of Time',64,0,'KokiriKid','none','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',23,'75.23.217.24','Misc','mm3d_boxart.png',0,5,15430,2,0,0,0,4577,7590,0,1703483163,'Majora\'s Mask 3D',50,0,'NaviBot','','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',66,'83.245.200.193','Misc','mm_boxart.jpg',0,5,4459,3,0,1728940125,38607,2,2152,0,1728940495,'Majora&#39;s Mask',31,0,'MajoraMain','Windows only','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',90,'91.158.25.45','Misc','mm_boxart.jpg',0,0,5292,4,0,1760985176,40115,27774,2646,0,1760985779,'Majora&#39;s Mask',5,0,'MajoraMain','Windows, could work in WINE','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',99,'71.190.100.96','Game Maker','ww_boxart.jpg',0,4.5,122850,3,0,1775963613,0,2,61416,0,1779810230,'The Wind Waker',4,0,'TriforceTim','','',2);
/*!40000 ALTER TABLE `ci_projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_resources_backup`
--

DROP TABLE IF EXISTS `ci_resources_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_resources_backup` (
  `description` text NOT NULL,
  `file` tinytext NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `preview` tinytext NOT NULL,
  `rating` float unsigned NOT NULL,
  `member_id` int(10) unsigned NOT NULL,
  `score` int(10) unsigned NOT NULL,
  `time_created` int(10) unsigned NOT NULL,
  `topic_id` int(10) unsigned NOT NULL,
  `views` smallint(5) unsigned NOT NULL,
  `downloads` smallint(5) unsigned NOT NULL,
  `last_updated` int(10) unsigned NOT NULL,
  `title` tinytext NOT NULL,
  `votes` int(10) unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `size` int(10) unsigned NOT NULL,
  `type` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `type` (`type`),
  KEY `time_created` (`time_created`),
  KEY `last_updated` (`last_updated`),
  KEY `score` (`score`),
  KEY `size` (`size`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_resources_backup`
--

LOCK TABLES `ci_resources_backup` WRITE;
/*!40000 ALTER TABLE `ci_resources_backup` DISABLE KEYS */;
INSERT INTO `ci_resources_backup` VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit.','aonuma_pack.zip',5,'10.0.2.5','aonuma_photo.png',5,41,0,1629219249,0,953,532,1629219249,'Eiji Aonuma Photo Collection',4,'',549682,2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.','kondo_tracks.zip',8,'10.0.2.8','kondo_photo.jpg',5,52,0,1629235602,0,147,20,1629235602,'Koji Kondo Zelda Themes',1,'',170,1),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.','aonuma_credits.zip',12,'10.0.2.12','',0,41,0,1629300000,0,42,7,1629300000,'Eiji Aonuma Zelda Credits List',0,'',382,4);
/*!40000 ALTER TABLE `ci_resources_backup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_tags`
--

DROP TABLE IF EXISTS `ci_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_tags` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_tags`
--

LOCK TABLES `ci_tags` WRITE;
/*!40000 ALTER TABLE `ci_tags` DISABLE KEYS */;
INSERT INTO `ci_tags` VALUES (1,'engine'),(2,'zelda'),(3,'soundtrack');
/*!40000 ALTER TABLE `ci_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_team_members`
--

DROP TABLE IF EXISTS `ci_team_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_team_members` (
  `id_team` int(10) unsigned NOT NULL,
  `id_member` int(10) unsigned NOT NULL,
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `join_time` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_team`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_team_members`
--

LOCK TABLES `ci_team_members` WRITE;
/*!40000 ALTER TABLE `ci_team_members` DISABLE KEYS */;
INSERT INTO `ci_team_members` VALUES (1,2,1,1711000100),(1,1,1,1711000200);
/*!40000 ALTER TABLE `ci_team_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ci_teams`
--

DROP TABLE IF EXISTS `ci_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ci_teams` (
  `id_team` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `logo` tinytext,
  `description` text,
  `ip` varchar(255) NOT NULL DEFAULT '',
  `id_member` int(10) unsigned NOT NULL DEFAULT '0',
  `time` int(10) unsigned NOT NULL DEFAULT '0',
  `public` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_team`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ci_teams`
--

LOCK TABLES `ci_teams` WRITE;
/*!40000 ALTER TABLE `ci_teams` DISABLE KEYS */;
INSERT INTO `ci_teams` VALUES (1,'Fixture Team','','A team for testing dev teams.','127.0.0.1',2,1711000000,1);
/*!40000 ALTER TABLE `ci_teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curated_collection`
--

DROP TABLE IF EXISTS `curated_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curated_collection` (
  `code` varchar(64) NOT NULL,
  `title` varchar(255) NOT NULL,
  `kind` varchar(32) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curated_collection`
--

LOCK TABLES `curated_collection` WRITE;
/*!40000 ALTER TABLE `curated_collection` DISABLE KEYS */;
INSERT INTO `curated_collection` VALUES ('fixture-jam','Fixture Game Jam','EVENT'),('potm','Project of the Month','FEATURE');
/*!40000 ALTER TABLE `curated_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curated_collection_item`
--

DROP TABLE IF EXISTS `curated_collection_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curated_collection_item` (
  `collection_code` varchar(64) NOT NULL,
  `entity_type` varchar(32) NOT NULL,
  `legacy_id` int(11) NOT NULL,
  `ordinal` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`collection_code`,`entity_type`,`legacy_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curated_collection_item`
--

LOCK TABLES `curated_collection_item` WRITE;
/*!40000 ALTER TABLE `curated_collection_item` DISABLE KEYS */;
INSERT INTO `curated_collection_item` VALUES ('fixture-jam','PROJECT',7,0),('fixture-jam','GAME',169,1),('fixture-jam','PROJECT',9999,2),('potm','PROJECT',7,0);
/*!40000 ALTER TABLE `curated_collection_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curated_project_merge`
--

DROP TABLE IF EXISTS `curated_project_merge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curated_project_merge` (
  `source_entity_type` varchar(32) NOT NULL,
  `source_legacy_id` int(11) NOT NULL,
  `target_entity_type` varchar(32) NOT NULL,
  `target_legacy_id` int(11) NOT NULL,
  PRIMARY KEY (`source_entity_type`,`source_legacy_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curated_project_merge`
--

LOCK TABLES `curated_project_merge` WRITE;
/*!40000 ALTER TABLE `curated_project_merge` DISABLE KEYS */;
/*!40000 ALTER TABLE `curated_project_merge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curated_wiki_project_link`
--

DROP TABLE IF EXISTS `curated_wiki_project_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curated_wiki_project_link` (
  `wiki_title` varchar(255) NOT NULL,
  `entity_type` varchar(16) NOT NULL,
  `legacy_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`wiki_title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curated_wiki_project_link`
--

LOCK TABLES `curated_wiki_project_link` WRITE;
/*!40000 ALTER TABLE `curated_wiki_project_link` DISABLE KEYS */;
INSERT INTO `curated_wiki_project_link` VALUES ('Ocarina_of_Time','PROJECT',7);
/*!40000 ALTER TABLE `curated_wiki_project_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1admin_info_files`
--

DROP TABLE IF EXISTS `smf_1admin_info_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1admin_info_files` (
  `id_file` tinyint(4) unsigned NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(255) NOT NULL DEFAULT '',
  `parameters` varchar(255) NOT NULL DEFAULT '',
  `data` text NOT NULL,
  `filetype` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_file`),
  KEY `filename` (`filename`(30))
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1admin_info_files`
--

LOCK TABLES `smf_1admin_info_files` WRITE;
/*!40000 ALTER TABLE `smf_1admin_info_files` DISABLE KEYS */;
INSERT INTO `smf_1admin_info_files` VALUES (1,'current-version.js','/smf/','version=%3$s','','text/javascript'),(2,'detailed-version.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(3,'latest-news.js','/smf/','language=%1$s&format=%2$s','','text/javascript'),(4,'latest-packages.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(5,'latest-smileys.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(6,'latest-support.js','/smf/','language=%1$s&version=%3$s','','text/javascript'),(7,'latest-themes.js','/smf/','language=%1$s&version=%3$s','','text/javascript');
/*!40000 ALTER TABLE `smf_1admin_info_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1approval_queue`
--

DROP TABLE IF EXISTS `smf_1approval_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1approval_queue` (
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `id_attach` int(10) unsigned NOT NULL DEFAULT '0',
  `id_event` smallint(5) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1approval_queue`
--

LOCK TABLES `smf_1approval_queue` WRITE;
/*!40000 ALTER TABLE `smf_1approval_queue` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1approval_queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1attachments`
--

DROP TABLE IF EXISTS `smf_1attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1attachments` (
  `id_attach` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_thumb` int(10) unsigned NOT NULL DEFAULT '0',
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_folder` tinyint(3) NOT NULL DEFAULT '1',
  `attachment_type` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `filename` varchar(255) NOT NULL DEFAULT '',
  `file_hash` varchar(40) NOT NULL DEFAULT '',
  `fileext` varchar(8) NOT NULL DEFAULT '',
  `size` int(10) unsigned NOT NULL DEFAULT '0',
  `downloads` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `width` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `height` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `mime_type` varchar(20) NOT NULL DEFAULT '',
  `approved` tinyint(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_attach`),
  UNIQUE KEY `id_member` (`id_member`,`id_attach`),
  KEY `id_msg` (`id_msg`),
  KEY `attachment_type` (`attachment_type`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1attachments`
--

LOCK TABLES `smf_1attachments` WRITE;
/*!40000 ALTER TABLE `smf_1attachments` DISABLE KEYS */;
INSERT INTO `smf_1attachments` VALUES (1,0,6,0,1,0,'favicon_old.gif','3d1160084695ad900f62700576ff0ec0ea3b8700','gif',5597,1,16,16,'image/gif',1),(2,0,0,3,1,0,'avatar_3_1783863270.gif','a47887b4bed8ea42bb8550a8aec69955c27c61df','gif',5597,222,16,16,'image/gif',1),(3,0,16,0,1,0,'favicon.png','cbbec572c69b428ff3e7e6a4c848b5b2a8d13ff4','png',8399,0,64,64,'image/png',1);
/*!40000 ALTER TABLE `smf_1attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1ban_groups`
--

DROP TABLE IF EXISTS `smf_1ban_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1ban_groups` (
  `id_ban_group` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `ban_time` int(10) unsigned NOT NULL DEFAULT '0',
  `expire_time` int(10) unsigned DEFAULT NULL,
  `cannot_access` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `cannot_register` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `cannot_post` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `cannot_login` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `reason` varchar(255) NOT NULL DEFAULT '',
  `notes` text NOT NULL,
  PRIMARY KEY (`id_ban_group`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1ban_groups`
--

LOCK TABLES `smf_1ban_groups` WRITE;
/*!40000 ALTER TABLE `smf_1ban_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1ban_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1ban_items`
--

DROP TABLE IF EXISTS `smf_1ban_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1ban_items` (
  `id_ban` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id_ban_group` smallint(5) unsigned NOT NULL DEFAULT '0',
  `ip_low1` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip_high1` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip_low2` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip_high2` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip_low3` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip_high3` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip_low4` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ip_high4` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `hostname` varchar(255) NOT NULL DEFAULT '',
  `email_address` varchar(255) NOT NULL DEFAULT '',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `hits` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_ban`),
  KEY `id_ban_group` (`id_ban_group`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1ban_items`
--

LOCK TABLES `smf_1ban_items` WRITE;
/*!40000 ALTER TABLE `smf_1ban_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1ban_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1board_permissions`
--

DROP TABLE IF EXISTS `smf_1board_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1board_permissions` (
  `id_group` smallint(5) NOT NULL DEFAULT '0',
  `id_profile` smallint(5) unsigned NOT NULL DEFAULT '0',
  `permission` varchar(30) NOT NULL DEFAULT '',
  `add_deny` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_group`,`id_profile`,`permission`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1board_permissions`
--

LOCK TABLES `smf_1board_permissions` WRITE;
/*!40000 ALTER TABLE `smf_1board_permissions` DISABLE KEYS */;
INSERT INTO `smf_1board_permissions` VALUES (-1,1,'poll_view',1),(0,1,'remove_own',1),(0,1,'lock_own',1),(0,1,'mark_any_notify',1),(0,1,'mark_notify',1),(0,1,'modify_own',1),(0,1,'poll_add_own',1),(0,1,'poll_edit_own',1),(0,1,'poll_lock_own',1),(0,1,'poll_post',1),(0,1,'poll_view',1),(0,1,'poll_vote',1),(0,1,'post_attachment',1),(0,1,'post_new',1),(0,1,'post_reply_any',1),(0,1,'post_reply_own',1),(0,1,'post_unapproved_topics',1),(0,1,'post_unapproved_replies_any',1),(0,1,'post_unapproved_replies_own',1),(0,1,'post_unapproved_attachments',1),(0,1,'delete_own',1),(0,1,'report_any',1),(0,1,'send_topic',1),(0,1,'view_attachments',1),(2,1,'moderate_board',1),(2,1,'post_new',1),(2,1,'post_reply_own',1),(2,1,'post_reply_any',1),(2,1,'post_unapproved_topics',1),(2,1,'post_unapproved_replies_any',1),(2,1,'post_unapproved_replies_own',1),(2,1,'post_unapproved_attachments',1),(2,1,'poll_post',1),(2,1,'poll_add_any',1),(2,1,'poll_remove_any',1),(2,1,'poll_view',1),(2,1,'poll_vote',1),(2,1,'poll_lock_any',1),(2,1,'poll_edit_any',1),(2,1,'report_any',1),(2,1,'lock_own',1),(2,1,'send_topic',1),(2,1,'mark_any_notify',1),(2,1,'mark_notify',1),(2,1,'delete_own',1),(2,1,'modify_own',1),(2,1,'make_sticky',1),(2,1,'lock_any',1),(2,1,'remove_any',1),(2,1,'move_any',1),(2,1,'merge_any',1),(2,1,'split_any',1),(2,1,'delete_any',1),(2,1,'modify_any',1),(2,1,'approve_posts',1),(2,1,'post_attachment',1),(2,1,'view_attachments',1),(3,1,'moderate_board',1),(3,1,'post_new',1),(3,1,'post_reply_own',1),(3,1,'post_reply_any',1),(3,1,'post_unapproved_topics',1),(3,1,'post_unapproved_replies_any',1),(3,1,'post_unapproved_replies_own',1),(3,1,'post_unapproved_attachments',1),(3,1,'poll_post',1),(3,1,'poll_add_any',1),(3,1,'poll_remove_any',1),(3,1,'poll_view',1),(3,1,'poll_vote',1),(3,1,'poll_lock_any',1),(3,1,'poll_edit_any',1),(3,1,'report_any',1),(3,1,'lock_own',1),(3,1,'send_topic',1),(3,1,'mark_any_notify',1),(3,1,'mark_notify',1),(3,1,'delete_own',1),(3,1,'modify_own',1),(3,1,'make_sticky',1),(3,1,'lock_any',1),(3,1,'remove_any',1),(3,1,'move_any',1),(3,1,'merge_any',1),(3,1,'split_any',1),(3,1,'delete_any',1),(3,1,'modify_any',1),(3,1,'approve_posts',1),(3,1,'post_attachment',1),(3,1,'view_attachments',1),(-1,2,'poll_view',1),(0,2,'remove_own',1),(0,2,'lock_own',1),(0,2,'mark_any_notify',1),(0,2,'mark_notify',1),(0,2,'modify_own',1),(0,2,'poll_view',1),(0,2,'poll_vote',1),(0,2,'post_attachment',1),(0,2,'post_new',1),(0,2,'post_reply_any',1),(0,2,'post_reply_own',1),(0,2,'post_unapproved_topics',1),(0,2,'post_unapproved_replies_any',1),(0,2,'post_unapproved_replies_own',1),(0,2,'post_unapproved_attachments',1),(0,2,'delete_own',1),(0,2,'report_any',1),(0,2,'send_topic',1),(0,2,'view_attachments',1),(2,2,'moderate_board',1),(2,2,'post_new',1),(2,2,'post_reply_own',1),(2,2,'post_reply_any',1),(2,2,'post_unapproved_topics',1),(2,2,'post_unapproved_replies_any',1),(2,2,'post_unapproved_replies_own',1),(2,2,'post_unapproved_attachments',1),(2,2,'poll_post',1),(2,2,'poll_add_any',1),(2,2,'poll_remove_any',1),(2,2,'poll_view',1),(2,2,'poll_vote',1),(2,2,'poll_lock_any',1),(2,2,'poll_edit_any',1),(2,2,'report_any',1),(2,2,'lock_own',1),(2,2,'send_topic',1),(2,2,'mark_any_notify',1),(2,2,'mark_notify',1),(2,2,'delete_own',1),(2,2,'modify_own',1),(2,2,'make_sticky',1),(2,2,'lock_any',1),(2,2,'remove_any',1),(2,2,'move_any',1),(2,2,'merge_any',1),(2,2,'split_any',1),(2,2,'delete_any',1),(2,2,'modify_any',1),(2,2,'approve_posts',1),(2,2,'post_attachment',1),(2,2,'view_attachments',1),(3,2,'moderate_board',1),(3,2,'post_new',1),(3,2,'post_reply_own',1),(3,2,'post_reply_any',1),(3,2,'post_unapproved_topics',1),(3,2,'post_unapproved_replies_any',1),(3,2,'post_unapproved_replies_own',1),(3,2,'post_unapproved_attachments',1),(3,2,'poll_post',1),(3,2,'poll_add_any',1),(3,2,'poll_remove_any',1),(3,2,'poll_view',1),(3,2,'poll_vote',1),(3,2,'poll_lock_any',1),(3,2,'poll_edit_any',1),(3,2,'report_any',1),(3,2,'lock_own',1),(3,2,'send_topic',1),(3,2,'mark_any_notify',1),(3,2,'mark_notify',1),(3,2,'delete_own',1),(3,2,'modify_own',1),(3,2,'make_sticky',1),(3,2,'lock_any',1),(3,2,'remove_any',1),(3,2,'move_any',1),(3,2,'merge_any',1),(3,2,'split_any',1),(3,2,'delete_any',1),(3,2,'modify_any',1),(3,2,'approve_posts',1),(3,2,'post_attachment',1),(3,2,'view_attachments',1),(-1,3,'poll_view',1),(0,3,'remove_own',1),(0,3,'lock_own',1),(0,3,'mark_any_notify',1),(0,3,'mark_notify',1),(0,3,'modify_own',1),(0,3,'poll_view',1),(0,3,'poll_vote',1),(0,3,'post_attachment',1),(0,3,'post_reply_any',1),(0,3,'post_reply_own',1),(0,3,'post_unapproved_replies_any',1),(0,3,'post_unapproved_replies_own',1),(0,3,'post_unapproved_attachments',1),(0,3,'delete_own',1),(0,3,'report_any',1),(0,3,'send_topic',1),(0,3,'view_attachments',1),(2,3,'moderate_board',1),(2,3,'post_new',1),(2,3,'post_reply_own',1),(2,3,'post_reply_any',1),(2,3,'post_unapproved_topics',1),(2,3,'post_unapproved_replies_any',1),(2,3,'post_unapproved_replies_own',1),(2,3,'post_unapproved_attachments',1),(2,3,'poll_post',1),(2,3,'poll_add_any',1),(2,3,'poll_remove_any',1),(2,3,'poll_view',1),(2,3,'poll_vote',1),(2,3,'poll_lock_any',1),(2,3,'poll_edit_any',1),(2,3,'report_any',1),(2,3,'lock_own',1),(2,3,'send_topic',1),(2,3,'mark_any_notify',1),(2,3,'mark_notify',1),(2,3,'delete_own',1),(2,3,'modify_own',1),(2,3,'make_sticky',1),(2,3,'lock_any',1),(2,3,'remove_any',1),(2,3,'move_any',1),(2,3,'merge_any',1),(2,3,'split_any',1),(2,3,'delete_any',1),(2,3,'modify_any',1),(2,3,'approve_posts',1),(2,3,'post_attachment',1),(2,3,'view_attachments',1),(3,3,'moderate_board',1),(3,3,'post_new',1),(3,3,'post_reply_own',1),(3,3,'post_reply_any',1),(3,3,'post_unapproved_topics',1),(3,3,'post_unapproved_replies_any',1),(3,3,'post_unapproved_replies_own',1),(3,3,'post_unapproved_attachments',1),(3,3,'poll_post',1),(3,3,'poll_add_any',1),(3,3,'poll_remove_any',1),(3,3,'poll_view',1),(3,3,'poll_vote',1),(3,3,'poll_lock_any',1),(3,3,'poll_edit_any',1),(3,3,'report_any',1),(3,3,'lock_own',1),(3,3,'send_topic',1),(3,3,'mark_any_notify',1),(3,3,'mark_notify',1),(3,3,'delete_own',1),(3,3,'modify_own',1),(3,3,'make_sticky',1),(3,3,'lock_any',1),(3,3,'remove_any',1),(3,3,'move_any',1),(3,3,'merge_any',1),(3,3,'split_any',1),(3,3,'delete_any',1),(3,3,'modify_any',1),(3,3,'approve_posts',1),(3,3,'post_attachment',1),(3,3,'view_attachments',1),(-1,4,'poll_view',1),(0,4,'mark_any_notify',1),(0,4,'mark_notify',1),(0,4,'poll_view',1),(0,4,'poll_vote',1),(0,4,'report_any',1),(0,4,'send_topic',1),(0,4,'view_attachments',1),(2,4,'moderate_board',1),(2,4,'post_new',1),(2,4,'post_reply_own',1),(2,4,'post_reply_any',1),(2,4,'post_unapproved_topics',1),(2,4,'post_unapproved_replies_any',1),(2,4,'post_unapproved_replies_own',1),(2,4,'post_unapproved_attachments',1),(2,4,'poll_post',1),(2,4,'poll_add_any',1),(2,4,'poll_remove_any',1),(2,4,'poll_view',1),(2,4,'poll_vote',1),(2,4,'poll_lock_any',1),(2,4,'poll_edit_any',1),(2,4,'report_any',1),(2,4,'lock_own',1),(2,4,'send_topic',1),(2,4,'mark_any_notify',1),(2,4,'mark_notify',1),(2,4,'delete_own',1),(2,4,'modify_own',1),(2,4,'make_sticky',1),(2,4,'lock_any',1),(2,4,'remove_any',1),(2,4,'move_any',1),(2,4,'merge_any',1),(2,4,'split_any',1),(2,4,'delete_any',1),(2,4,'modify_any',1),(2,4,'approve_posts',1),(2,4,'post_attachment',1),(2,4,'view_attachments',1),(3,4,'moderate_board',1),(3,4,'post_new',1),(3,4,'post_reply_own',1),(3,4,'post_reply_any',1),(3,4,'post_unapproved_topics',1),(3,4,'post_unapproved_replies_any',1),(3,4,'post_unapproved_replies_own',1),(3,4,'post_unapproved_attachments',1),(3,4,'poll_post',1),(3,4,'poll_add_any',1),(3,4,'poll_remove_any',1),(3,4,'poll_view',1),(3,4,'poll_vote',1),(3,4,'poll_lock_any',1),(3,4,'poll_edit_any',1),(3,4,'report_any',1),(3,4,'lock_own',1),(3,4,'send_topic',1),(3,4,'mark_any_notify',1),(3,4,'mark_notify',1),(3,4,'delete_own',1),(3,4,'modify_own',1),(3,4,'make_sticky',1),(3,4,'lock_any',1),(3,4,'remove_any',1),(3,4,'move_any',1),(3,4,'merge_any',1),(3,4,'split_any',1),(3,4,'delete_any',1),(3,4,'modify_any',1),(3,4,'approve_posts',1),(3,4,'post_attachment',1),(3,4,'view_attachments',1),(9,1,'poll_lock_own',1),(9,1,'poll_edit_any',1),(9,1,'poll_add_any',1),(9,1,'modify_any',1),(9,1,'delete_any',1),(9,1,'announce_topic',1),(9,1,'delete_replies',1),(9,1,'modify_replies',1),(9,1,'remove_any',1),(9,1,'lock_any',1),(9,1,'lock_own',1),(9,1,'move_any',1),(9,1,'move_own',1),(9,1,'make_sticky',1),(9,1,'send_topic',1),(9,1,'split_any',1),(9,1,'merge_any',1),(9,1,'moderate_board',1),(9,2,'delete_own',1),(9,2,'lock_own',1),(9,2,'mark_any_notify',1),(9,2,'mark_notify',1),(9,2,'modify_own',1),(9,2,'poll_view',1),(9,2,'poll_vote',1),(9,2,'post_attachment',1),(9,2,'post_new',1),(9,2,'post_reply_any',1),(9,2,'post_reply_own',1),(9,2,'post_unapproved_attachments',1),(9,2,'post_unapproved_replies_any',1),(9,2,'post_unapproved_replies_own',1),(9,2,'post_unapproved_topics',1),(9,2,'remove_own',1),(9,2,'report_any',1),(9,2,'send_topic',1),(9,2,'view_attachments',1),(9,3,'delete_own',1),(9,3,'lock_own',1),(9,3,'mark_any_notify',1),(9,3,'mark_notify',1),(9,3,'modify_own',1),(9,3,'poll_view',1),(9,3,'poll_vote',1),(9,3,'post_attachment',1),(9,3,'post_reply_any',1),(9,3,'post_reply_own',1),(9,3,'post_unapproved_attachments',1),(9,3,'post_unapproved_replies_any',1),(9,3,'post_unapproved_replies_own',1),(9,3,'remove_own',1),(9,3,'report_any',1),(9,3,'send_topic',1),(9,3,'view_attachments',1),(9,4,'mark_any_notify',1),(9,4,'mark_notify',1),(9,4,'poll_view',1),(9,4,'poll_vote',1),(9,4,'report_any',1),(9,4,'send_topic',1),(9,4,'view_attachments',1),(9,1,'poll_lock_any',1),(9,1,'poll_remove_any',1),(9,1,'post_new',1),(9,1,'post_reply_own',1),(9,1,'post_reply_any',1),(9,1,'post_unapproved_topics',1),(9,1,'post_unapproved_replies_own',1),(9,1,'post_unapproved_replies_any',1),(9,1,'post_unapproved_attachments',1),(9,1,'remove_own',1),(9,1,'delete_own',1),(9,1,'modify_own',1),(9,1,'poll_edit_own',1),(9,1,'report_any',1),(9,1,'poll_view',1),(9,1,'poll_vote',1),(9,1,'view_attachments',1),(9,1,'poll_post',1),(9,1,'poll_add_own',1),(9,1,'mark_any_notify',1),(9,1,'mark_notify',1),(9,1,'post_attachment',1);
/*!40000 ALTER TABLE `smf_1board_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1boards`
--

DROP TABLE IF EXISTS `smf_1boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1boards` (
  `id_board` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `id_cat` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `child_level` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `id_parent` smallint(5) unsigned NOT NULL DEFAULT '0',
  `board_order` smallint(5) NOT NULL DEFAULT '0',
  `id_last_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `id_msg_updated` int(10) unsigned NOT NULL DEFAULT '0',
  `member_groups` varchar(255) NOT NULL DEFAULT '-1,0',
  `id_profile` smallint(5) unsigned NOT NULL DEFAULT '1',
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `num_topics` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `num_posts` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `count_posts` tinyint(4) NOT NULL DEFAULT '0',
  `id_theme` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `override_theme` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `unapproved_posts` smallint(5) NOT NULL DEFAULT '0',
  `unapproved_topics` smallint(5) NOT NULL DEFAULT '0',
  `redirect` varchar(255) NOT NULL DEFAULT '',
  `countMoney` tinyint(4) NOT NULL DEFAULT '0',
  `is_redirect` tinyint(4) NOT NULL DEFAULT '0',
  `redirect_clicks` int(10) unsigned NOT NULL DEFAULT '0',
  `redirect_count_clicks` tinyint(4) NOT NULL DEFAULT '0',
  `redirect_target` text,
  `redirect_url` text,
  PRIMARY KEY (`id_board`),
  UNIQUE KEY `categories` (`id_cat`,`id_board`),
  KEY `id_parent` (`id_parent`),
  KEY `id_msg_updated` (`id_msg_updated`),
  KEY `member_groups` (`member_groups`(48))
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1boards`
--

LOCK TABLES `smf_1boards` WRITE;
/*!40000 ALTER TABLE `smf_1boards` DISABLE KEYS */;
INSERT INTO `smf_1boards` VALUES (1,1,0,0,2,185,185,'-1,0,2,9',1,'General Discussion','Feel free to talk about anything and everything in this board.',11,160,0,0,0,0,0,'',0,0,0,0,NULL,NULL),(2,2,0,0,1,184,184,'-1,0,2,4,5,6,7,8,9',1,'New Board','',23,24,0,0,0,0,0,'',0,0,0,0,NULL,NULL),(4,1,0,0,3,0,0,'-1,0,2,9',1,'Projects','Discussion threads for CMS projects.',0,0,0,0,0,0,0,'',0,0,0,0,NULL,NULL),(5,1,0,0,4,0,0,'-1,0,2,9',1,'Resources','Discussion threads for CMS resources.',0,0,0,0,0,0,0,'',0,0,0,0,NULL,NULL);
/*!40000 ALTER TABLE `smf_1boards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1calendar`
--

DROP TABLE IF EXISTS `smf_1calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1calendar` (
  `id_event` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL DEFAULT '0001-01-01',
  `end_date` date NOT NULL DEFAULT '0001-01-01',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL DEFAULT '',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_event`),
  KEY `start_date` (`start_date`),
  KEY `end_date` (`end_date`),
  KEY `topic` (`id_topic`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1calendar`
--

LOCK TABLES `smf_1calendar` WRITE;
/*!40000 ALTER TABLE `smf_1calendar` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1calendar_holidays`
--

DROP TABLE IF EXISTS `smf_1calendar_holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1calendar_holidays` (
  `id_holiday` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `event_date` date NOT NULL DEFAULT '0001-01-01',
  `title` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_holiday`),
  KEY `event_date` (`event_date`)
) ENGINE=MyISAM AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1calendar_holidays`
--

LOCK TABLES `smf_1calendar_holidays` WRITE;
/*!40000 ALTER TABLE `smf_1calendar_holidays` DISABLE KEYS */;
INSERT INTO `smf_1calendar_holidays` VALUES (1,'0004-01-01','New Year\'s'),(2,'0004-12-25','Christmas'),(3,'0004-02-14','Valentine\'s Day'),(4,'0004-03-17','St. Patrick\'s Day'),(5,'0004-04-01','April Fools'),(6,'0004-04-22','Earth Day'),(7,'0004-10-24','United Nations Day'),(8,'0004-10-31','Halloween'),(9,'2010-05-09','Mother\'s Day'),(10,'2011-05-08','Mother\'s Day'),(11,'2012-05-13','Mother\'s Day'),(12,'2013-05-12','Mother\'s Day'),(13,'2014-05-11','Mother\'s Day'),(14,'2015-05-10','Mother\'s Day'),(15,'2016-05-08','Mother\'s Day'),(16,'2017-05-14','Mother\'s Day'),(17,'2018-05-13','Mother\'s Day'),(18,'2019-05-12','Mother\'s Day'),(19,'2020-05-10','Mother\'s Day'),(20,'2008-06-15','Father\'s Day'),(21,'2009-06-21','Father\'s Day'),(22,'2010-06-20','Father\'s Day'),(23,'2011-06-19','Father\'s Day'),(24,'2012-06-17','Father\'s Day'),(25,'2013-06-16','Father\'s Day'),(26,'2014-06-15','Father\'s Day'),(27,'2015-06-21','Father\'s Day'),(28,'2016-06-19','Father\'s Day'),(29,'2017-06-18','Father\'s Day'),(30,'2018-06-17','Father\'s Day'),(31,'2019-06-16','Father\'s Day'),(32,'2020-06-21','Father\'s Day'),(33,'2010-06-21','Summer Solstice'),(34,'2011-06-21','Summer Solstice'),(35,'2012-06-20','Summer Solstice'),(36,'2013-06-21','Summer Solstice'),(37,'2014-06-21','Summer Solstice'),(38,'2015-06-21','Summer Solstice'),(39,'2016-06-20','Summer Solstice'),(40,'2017-06-20','Summer Solstice'),(41,'2018-06-21','Summer Solstice'),(42,'2019-06-21','Summer Solstice'),(43,'2020-06-20','Summer Solstice'),(44,'2010-03-20','Vernal Equinox'),(45,'2011-03-20','Vernal Equinox'),(46,'2012-03-20','Vernal Equinox'),(47,'2013-03-20','Vernal Equinox'),(48,'2014-03-20','Vernal Equinox'),(49,'2015-03-20','Vernal Equinox'),(50,'2016-03-19','Vernal Equinox'),(51,'2017-03-20','Vernal Equinox'),(52,'2018-03-20','Vernal Equinox'),(53,'2019-03-20','Vernal Equinox'),(54,'2020-03-19','Vernal Equinox'),(55,'2010-12-21','Winter Solstice'),(56,'2011-12-22','Winter Solstice'),(57,'2012-12-21','Winter Solstice'),(58,'2013-12-21','Winter Solstice'),(59,'2014-12-21','Winter Solstice'),(60,'2015-12-21','Winter Solstice'),(61,'2016-12-21','Winter Solstice'),(62,'2017-12-21','Winter Solstice'),(63,'2018-12-21','Winter Solstice'),(64,'2019-12-21','Winter Solstice'),(65,'2020-12-21','Winter Solstice'),(66,'2010-09-22','Autumnal Equinox'),(67,'2011-09-23','Autumnal Equinox'),(68,'2012-09-22','Autumnal Equinox'),(69,'2013-09-22','Autumnal Equinox'),(70,'2014-09-22','Autumnal Equinox'),(71,'2015-09-23','Autumnal Equinox'),(72,'2016-09-22','Autumnal Equinox'),(73,'2017-09-22','Autumnal Equinox'),(74,'2018-09-22','Autumnal Equinox'),(75,'2019-09-23','Autumnal Equinox'),(76,'2020-09-22','Autumnal Equinox'),(77,'0004-07-04','Independence Day'),(78,'0004-05-05','Cinco de Mayo'),(79,'0004-06-14','Flag Day'),(80,'0004-11-11','Veterans Day'),(81,'0004-02-02','Groundhog Day'),(82,'2010-11-25','Thanksgiving'),(83,'2011-11-24','Thanksgiving'),(84,'2012-11-22','Thanksgiving'),(85,'2013-11-28','Thanksgiving'),(86,'2014-11-27','Thanksgiving'),(87,'2015-11-26','Thanksgiving'),(88,'2016-11-24','Thanksgiving'),(89,'2017-11-23','Thanksgiving'),(90,'2018-11-22','Thanksgiving'),(91,'2019-11-28','Thanksgiving'),(92,'2020-11-26','Thanksgiving'),(93,'2010-05-31','Memorial Day'),(94,'2011-05-30','Memorial Day'),(95,'2012-05-28','Memorial Day'),(96,'2013-05-27','Memorial Day'),(97,'2014-05-26','Memorial Day'),(98,'2015-05-25','Memorial Day'),(99,'2016-05-30','Memorial Day'),(100,'2017-05-29','Memorial Day'),(101,'2018-05-28','Memorial Day'),(102,'2019-05-27','Memorial Day'),(103,'2020-05-25','Memorial Day'),(104,'2010-09-06','Labor Day'),(105,'2011-09-05','Labor Day'),(106,'2012-09-03','Labor Day'),(107,'2013-09-02','Labor Day'),(108,'2014-09-01','Labor Day'),(109,'2015-09-07','Labor Day'),(110,'2016-09-05','Labor Day'),(111,'2017-09-04','Labor Day'),(112,'2018-09-03','Labor Day'),(113,'2019-09-02','Labor Day'),(114,'2020-09-07','Labor Day'),(115,'0004-06-06','D-Day');
/*!40000 ALTER TABLE `smf_1calendar_holidays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1categories`
--

DROP TABLE IF EXISTS `smf_1categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1categories` (
  `id_cat` tinyint(4) unsigned NOT NULL AUTO_INCREMENT,
  `cat_order` tinyint(4) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `can_collapse` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_cat`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1categories`
--

LOCK TABLES `smf_1categories` WRITE;
/*!40000 ALTER TABLE `smf_1categories` DISABLE KEYS */;
INSERT INTO `smf_1categories` VALUES (1,1,'General Category',1),(2,0,'New Category',1);
/*!40000 ALTER TABLE `smf_1categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1collapsed_categories`
--

DROP TABLE IF EXISTS `smf_1collapsed_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1collapsed_categories` (
  `id_cat` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_cat`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1collapsed_categories`
--

LOCK TABLES `smf_1collapsed_categories` WRITE;
/*!40000 ALTER TABLE `smf_1collapsed_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1collapsed_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1custom_fields`
--

DROP TABLE IF EXISTS `smf_1custom_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1custom_fields` (
  `id_field` smallint(5) NOT NULL AUTO_INCREMENT,
  `col_name` varchar(12) NOT NULL DEFAULT '',
  `field_name` varchar(40) NOT NULL DEFAULT '',
  `field_desc` varchar(255) NOT NULL DEFAULT '',
  `field_type` varchar(8) NOT NULL DEFAULT 'text',
  `field_length` smallint(5) NOT NULL DEFAULT '255',
  `field_options` text NOT NULL,
  `mask` varchar(255) NOT NULL DEFAULT '',
  `show_reg` tinyint(3) NOT NULL DEFAULT '0',
  `show_display` tinyint(3) NOT NULL DEFAULT '0',
  `show_profile` varchar(20) NOT NULL DEFAULT 'forumprofile',
  `private` tinyint(3) NOT NULL DEFAULT '0',
  `active` tinyint(3) NOT NULL DEFAULT '1',
  `bbc` tinyint(3) NOT NULL DEFAULT '0',
  `can_search` tinyint(3) NOT NULL DEFAULT '0',
  `default_value` varchar(255) NOT NULL DEFAULT '',
  `enclose` text NOT NULL,
  `placement` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_field`),
  UNIQUE KEY `col_name` (`col_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1custom_fields`
--

LOCK TABLES `smf_1custom_fields` WRITE;
/*!40000 ALTER TABLE `smf_1custom_fields` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1custom_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1game_comments`
--

DROP TABLE IF EXISTS `smf_1game_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1game_comments` (
  `ID_COMMENT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_GAME` int(11) NOT NULL,
  `ID_MEMBER` int(11) NOT NULL,
  `body` mediumtext NOT NULL,
  `postTime` int(11) NOT NULL,
  `postIP` mediumtext NOT NULL,
  PRIMARY KEY (`ID_COMMENT`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1game_comments`
--

LOCK TABLES `smf_1game_comments` WRITE;
/*!40000 ALTER TABLE `smf_1game_comments` DISABLE KEYS */;
INSERT INTO `smf_1game_comments` VALUES (36,169,3,'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',1629061017,'10.89.0.114'),(37,169,4,'Lorem ipsum dolor sit amet, consectetur adipiscing elit&#039;s. Mauris ac quam blandit, tempor tellus ut, hendrerit justo.',1629220706,'10.89.0.114'),(38,169,2,'Curabitur tempus [b]placerat[/b] tortor, vel convallis enim mollis vitae.',1630233638,'10.89.0.114'),(40,9999,3,'Suspendisse eget ligula vehicula, congue ante id, dictum nisi.',1630300000,'10.89.0.114');
/*!40000 ALTER TABLE `smf_1game_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1game_downloads`
--

DROP TABLE IF EXISTS `smf_1game_downloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1game_downloads` (
  `ID_DOWNLOAD` int(11) NOT NULL AUTO_INCREMENT,
  `ID_GAME` int(11) NOT NULL,
  `description` mediumtext NOT NULL,
  `fileSize` int(11) NOT NULL,
  `fileURL` mediumtext NOT NULL,
  `postTime` int(11) NOT NULL,
  `postIP` mediumtext NOT NULL,
  `downloads` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `ID_MEMBER` int(11) NOT NULL,
  PRIMARY KEY (`ID_DOWNLOAD`)
) ENGINE=MyISAM AUTO_INCREMENT=713 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1game_downloads`
--

LOCK TABLES `smf_1game_downloads` WRITE;
/*!40000 ALTER TABLE `smf_1game_downloads` DISABLE KEYS */;
INSERT INTO `smf_1game_downloads` VALUES (709,169,'Preview',112026,'oot3d_boxart.png',1689501891,'10.0.1.69',11246,1,0),(710,169,'Demo.',170,'oot3d_demo.zip',1689502125,'10.0.1.69',116,3,4599),(712,169,'Lorem ipsum progress shot.',45694,'ocarina3d_screenshot.jpg',1689546055,'10.0.1.69',953,2,4599);
/*!40000 ALTER TABLE `smf_1game_downloads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1game_news`
--

DROP TABLE IF EXISTS `smf_1game_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1game_news` (
  `ID_NEWS` int(11) NOT NULL AUTO_INCREMENT,
  `ID_GAME` int(11) NOT NULL,
  `ID_MEMBER` int(11) NOT NULL DEFAULT '0',
  `subject` mediumtext,
  `body` mediumtext,
  `postTime` int(11) NOT NULL DEFAULT '0',
  `postIP` mediumtext,
  PRIMARY KEY (`ID_NEWS`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1game_news`
--

LOCK TABLES `smf_1game_news` WRITE;
/*!40000 ALTER TABLE `smf_1game_news` DISABLE KEYS */;
INSERT INTO `smf_1game_news` VALUES (1,169,3,'Engine update','We fixed the collision bugs.',1689600000,'127.0.0.1');
/*!40000 ALTER TABLE `smf_1game_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1games`
--

DROP TABLE IF EXISTS `smf_1games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1games` (
  `ID_GAME` int(11) NOT NULL AUTO_INCREMENT,
  `ID_MEMBER` int(11) NOT NULL,
  `title` mediumtext NOT NULL,
  `views` int(11) NOT NULL,
  `body` mediumtext NOT NULL,
  `votes` mediumtext NOT NULL,
  `voters` mediumtext NOT NULL,
  `language` mediumtext NOT NULL,
  `requirements` mediumtext NOT NULL,
  `progress` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `postTime` int(11) NOT NULL,
  `postIP` mediumtext NOT NULL,
  `downloads` int(11) NOT NULL,
  `rating` float NOT NULL,
  `ID_PREVIEW` int(11) NOT NULL,
  `voteCount` int(11) NOT NULL,
  `zfgcapiApproved` int(11) NOT NULL,
  `zfgcapiPassCode` mediumtext NOT NULL,
  `zfgcapiOpenCount` int(11) NOT NULL,
  `zfgcapiRupeeCount` int(11) NOT NULL,
  `zfgcapiLog` longtext NOT NULL,
  PRIMARY KEY (`ID_GAME`)
) ENGINE=MyISAM AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1games`
--

LOCK TABLES `smf_1games` WRITE;
/*!40000 ALTER TABLE `smf_1games` DISABLE KEYS */;
INSERT INTO `smf_1games` VALUES (169,3,'Ocarina of Time 3D',593,'Testing It\\\'s','3,1','4599,4247','Game Maker','',40,1,1689501891,'68.59.173.178',116,2,709,2,0,'',0,0,''),(174,1969,'Majora\'s Mask 3D',329,'Bending dimensions and mastering forbidden secrets, Link must embark on his most perilous journey yet.\r\n\r\nREMEMBER TO VIEW THE README.','5','1969','Blitz Max','',0,2,1691702536,'68.84.191.230',145,5,0,1,0,'',0,0,'');
/*!40000 ALTER TABLE `smf_1games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1group_moderators`
--

DROP TABLE IF EXISTS `smf_1group_moderators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1group_moderators` (
  `id_group` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_group`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1group_moderators`
--

LOCK TABLES `smf_1group_moderators` WRITE;
/*!40000 ALTER TABLE `smf_1group_moderators` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1group_moderators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_actions`
--

DROP TABLE IF EXISTS `smf_1log_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_actions` (
  `id_action` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_log` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `log_time` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `ip` char(16) NOT NULL DEFAULT '',
  `action` varchar(30) NOT NULL DEFAULT '',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `extra` text NOT NULL,
  PRIMARY KEY (`id_action`),
  KEY `id_log` (`id_log`),
  KEY `log_time` (`log_time`),
  KEY `id_member` (`id_member`),
  KEY `id_board` (`id_board`),
  KEY `id_msg` (`id_msg`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_actions`
--

LOCK TABLES `smf_1log_actions` WRITE;
/*!40000 ALTER TABLE `smf_1log_actions` DISABLE KEYS */;
INSERT INTO `smf_1log_actions` VALUES (1,1,1783870500,1,'10.89.3.12','lock',1,6,0,'a:0:{}');
/*!40000 ALTER TABLE `smf_1log_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_activity`
--

DROP TABLE IF EXISTS `smf_1log_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_activity` (
  `date` date NOT NULL DEFAULT '0001-01-01',
  `hits` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `topics` smallint(5) unsigned NOT NULL DEFAULT '0',
  `posts` smallint(5) unsigned NOT NULL DEFAULT '0',
  `registers` smallint(5) unsigned NOT NULL DEFAULT '0',
  `most_on` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`date`),
  KEY `most_on` (`most_on`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_activity`
--

LOCK TABLES `smf_1log_activity` WRITE;
/*!40000 ALTER TABLE `smf_1log_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_banned`
--

DROP TABLE IF EXISTS `smf_1log_banned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_banned` (
  `id_ban_log` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `ip` char(16) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `log_time` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_ban_log`),
  KEY `log_time` (`log_time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_banned`
--

LOCK TABLES `smf_1log_banned` WRITE;
/*!40000 ALTER TABLE `smf_1log_banned` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_banned` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_boards`
--

DROP TABLE IF EXISTS `smf_1log_boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_boards` (
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_board`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_boards`
--

LOCK TABLES `smf_1log_boards` WRITE;
/*!40000 ALTER TABLE `smf_1log_boards` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_boards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_comments`
--

DROP TABLE IF EXISTS `smf_1log_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_comments` (
  `id_comment` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `member_name` varchar(80) NOT NULL DEFAULT '',
  `comment_type` varchar(8) NOT NULL DEFAULT 'warning',
  `id_recipient` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `recipient_name` varchar(255) NOT NULL DEFAULT '',
  `log_time` int(10) NOT NULL DEFAULT '0',
  `id_notice` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `counter` tinyint(3) NOT NULL DEFAULT '0',
  `body` text NOT NULL,
  PRIMARY KEY (`id_comment`),
  KEY `id_recipient` (`id_recipient`),
  KEY `log_time` (`log_time`),
  KEY `comment_type` (`comment_type`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_comments`
--

LOCK TABLES `smf_1log_comments` WRITE;
/*!40000 ALTER TABLE `smf_1log_comments` DISABLE KEYS */;
INSERT INTO `smf_1log_comments` VALUES (1,2,'mgzero','warning',3,'gm112',1783870000,0,20,'farted in the room');
/*!40000 ALTER TABLE `smf_1log_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_digest`
--

DROP TABLE IF EXISTS `smf_1log_digest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_digest` (
  `id_topic` mediumint(8) unsigned NOT NULL,
  `id_msg` int(10) unsigned NOT NULL,
  `note_type` varchar(10) NOT NULL DEFAULT 'post',
  `daily` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `exclude` mediumint(8) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_digest`
--

LOCK TABLES `smf_1log_digest` WRITE;
/*!40000 ALTER TABLE `smf_1log_digest` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_digest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_errors`
--

DROP TABLE IF EXISTS `smf_1log_errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_errors` (
  `id_error` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `log_time` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `ip` char(16) NOT NULL DEFAULT '',
  `url` text NOT NULL,
  `message` text NOT NULL,
  `session` char(32) NOT NULL DEFAULT '',
  `error_type` char(15) NOT NULL DEFAULT 'general',
  `file` varchar(255) NOT NULL DEFAULT '',
  `line` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_error`),
  KEY `log_time` (`log_time`),
  KEY `id_member` (`id_member`),
  KEY `ip` (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_errors`
--

LOCK TABLES `smf_1log_errors` WRITE;
/*!40000 ALTER TABLE `smf_1log_errors` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_errors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_floodcontrol`
--

DROP TABLE IF EXISTS `smf_1log_floodcontrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_floodcontrol` (
  `ip` char(16) NOT NULL DEFAULT '',
  `log_time` int(10) unsigned NOT NULL DEFAULT '0',
  `log_type` varchar(8) NOT NULL DEFAULT 'post',
  PRIMARY KEY (`ip`,`log_type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_floodcontrol`
--

LOCK TABLES `smf_1log_floodcontrol` WRITE;
/*!40000 ALTER TABLE `smf_1log_floodcontrol` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_floodcontrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_group_requests`
--

DROP TABLE IF EXISTS `smf_1log_group_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_group_requests` (
  `id_request` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_group` smallint(5) unsigned NOT NULL DEFAULT '0',
  `time_applied` int(10) unsigned NOT NULL DEFAULT '0',
  `reason` text NOT NULL,
  PRIMARY KEY (`id_request`),
  UNIQUE KEY `id_member` (`id_member`,`id_group`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_group_requests`
--

LOCK TABLES `smf_1log_group_requests` WRITE;
/*!40000 ALTER TABLE `smf_1log_group_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_group_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_karma`
--

DROP TABLE IF EXISTS `smf_1log_karma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_karma` (
  `id_target` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_executor` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `log_time` int(10) unsigned NOT NULL DEFAULT '0',
  `action` tinyint(4) NOT NULL DEFAULT '0',
  `is_read` tinyint(4) NOT NULL DEFAULT '0',
  `description` text,
  `link` text,
  PRIMARY KEY (`id_target`,`id_executor`),
  KEY `log_time` (`log_time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_karma`
--

LOCK TABLES `smf_1log_karma` WRITE;
/*!40000 ALTER TABLE `smf_1log_karma` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_karma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_mark_read`
--

DROP TABLE IF EXISTS `smf_1log_mark_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_mark_read` (
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_board`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_mark_read`
--

LOCK TABLES `smf_1log_mark_read` WRITE;
/*!40000 ALTER TABLE `smf_1log_mark_read` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_mark_read` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_member_notices`
--

DROP TABLE IF EXISTS `smf_1log_member_notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_member_notices` (
  `id_notice` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  PRIMARY KEY (`id_notice`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_member_notices`
--

LOCK TABLES `smf_1log_member_notices` WRITE;
/*!40000 ALTER TABLE `smf_1log_member_notices` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_member_notices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_notify`
--

DROP TABLE IF EXISTS `smf_1log_notify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_notify` (
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `sent` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_topic`,`id_board`),
  KEY `id_topic` (`id_topic`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_notify`
--

LOCK TABLES `smf_1log_notify` WRITE;
/*!40000 ALTER TABLE `smf_1log_notify` DISABLE KEYS */;
INSERT INTO `smf_1log_notify` VALUES (3,0,1,0),(2,0,2,0),(3,1,0,0),(2,2,0,0),(3,3,0,0);
/*!40000 ALTER TABLE `smf_1log_notify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_online`
--

DROP TABLE IF EXISTS `smf_1log_online`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_online` (
  `session` varchar(32) NOT NULL DEFAULT '',
  `log_time` int(10) NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_spider` smallint(5) unsigned NOT NULL DEFAULT '0',
  `ip` int(10) unsigned NOT NULL DEFAULT '0',
  `url` text NOT NULL,
  PRIMARY KEY (`session`),
  KEY `log_time` (`log_time`),
  KEY `id_member` (`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_online`
--

LOCK TABLES `smf_1log_online` WRITE;
/*!40000 ALTER TABLE `smf_1log_online` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_online` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_packages`
--

DROP TABLE IF EXISTS `smf_1log_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_packages` (
  `id_install` int(10) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL DEFAULT '',
  `package_id` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '',
  `version` varchar(255) NOT NULL DEFAULT '',
  `id_member_installed` mediumint(8) NOT NULL DEFAULT '0',
  `member_installed` varchar(255) NOT NULL DEFAULT '',
  `time_installed` int(10) NOT NULL DEFAULT '0',
  `id_member_removed` mediumint(8) NOT NULL DEFAULT '0',
  `member_removed` varchar(255) NOT NULL DEFAULT '',
  `time_removed` int(10) NOT NULL DEFAULT '0',
  `install_state` tinyint(3) NOT NULL DEFAULT '1',
  `failed_steps` text NOT NULL,
  `themes_installed` varchar(255) NOT NULL DEFAULT '',
  `db_changes` text NOT NULL,
  PRIMARY KEY (`id_install`),
  KEY `filename` (`filename`(15))
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_packages`
--

LOCK TABLES `smf_1log_packages` WRITE;
/*!40000 ALTER TABLE `smf_1log_packages` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_polls`
--

DROP TABLE IF EXISTS `smf_1log_polls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_polls` (
  `id_poll` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_choice` tinyint(3) unsigned NOT NULL DEFAULT '0',
  KEY `id_poll` (`id_poll`,`id_member`,`id_choice`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_polls`
--

LOCK TABLES `smf_1log_polls` WRITE;
/*!40000 ALTER TABLE `smf_1log_polls` DISABLE KEYS */;
INSERT INTO `smf_1log_polls` VALUES (1,1,2),(1,3,2),(1,4,1);
/*!40000 ALTER TABLE `smf_1log_polls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_reported`
--

DROP TABLE IF EXISTS `smf_1log_reported`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_reported` (
  `id_report` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `membername` varchar(255) NOT NULL DEFAULT '',
  `subject` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  `time_started` int(10) NOT NULL DEFAULT '0',
  `time_updated` int(10) NOT NULL DEFAULT '0',
  `num_reports` mediumint(6) NOT NULL DEFAULT '0',
  `closed` tinyint(3) NOT NULL DEFAULT '0',
  `ignore_all` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_report`),
  KEY `id_member` (`id_member`),
  KEY `id_topic` (`id_topic`),
  KEY `closed` (`closed`),
  KEY `time_started` (`time_started`),
  KEY `id_msg` (`id_msg`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_reported`
--

LOCK TABLES `smf_1log_reported` WRITE;
/*!40000 ALTER TABLE `smf_1log_reported` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_reported` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_reported_comments`
--

DROP TABLE IF EXISTS `smf_1log_reported_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_reported_comments` (
  `id_comment` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `id_report` mediumint(8) NOT NULL DEFAULT '0',
  `id_member` mediumint(8) NOT NULL,
  `membername` varchar(255) NOT NULL DEFAULT '',
  `email_address` varchar(255) NOT NULL DEFAULT '',
  `member_ip` varchar(255) NOT NULL DEFAULT '',
  `comment` varchar(255) NOT NULL DEFAULT '',
  `time_sent` int(10) NOT NULL,
  PRIMARY KEY (`id_comment`),
  KEY `id_report` (`id_report`),
  KEY `id_member` (`id_member`),
  KEY `time_sent` (`time_sent`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_reported_comments`
--

LOCK TABLES `smf_1log_reported_comments` WRITE;
/*!40000 ALTER TABLE `smf_1log_reported_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_reported_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_scheduled_tasks`
--

DROP TABLE IF EXISTS `smf_1log_scheduled_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_scheduled_tasks` (
  `id_log` mediumint(8) NOT NULL AUTO_INCREMENT,
  `id_task` smallint(5) NOT NULL DEFAULT '0',
  `time_run` int(10) NOT NULL DEFAULT '0',
  `time_taken` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_log`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_scheduled_tasks`
--

LOCK TABLES `smf_1log_scheduled_tasks` WRITE;
/*!40000 ALTER TABLE `smf_1log_scheduled_tasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_scheduled_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_search_messages`
--

DROP TABLE IF EXISTS `smf_1log_search_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_search_messages` (
  `id_search` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_search`,`id_msg`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_search_messages`
--

LOCK TABLES `smf_1log_search_messages` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_search_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_search_results`
--

DROP TABLE IF EXISTS `smf_1log_search_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_search_results` (
  `id_search` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `relevance` smallint(5) unsigned NOT NULL DEFAULT '0',
  `num_matches` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_search`,`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_search_results`
--

LOCK TABLES `smf_1log_search_results` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_results` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_search_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_search_subjects`
--

DROP TABLE IF EXISTS `smf_1log_search_subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_search_subjects` (
  `word` varchar(20) NOT NULL DEFAULT '',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`word`,`id_topic`),
  KEY `id_topic` (`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_search_subjects`
--

LOCK TABLES `smf_1log_search_subjects` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_subjects` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_search_subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_search_topics`
--

DROP TABLE IF EXISTS `smf_1log_search_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_search_topics` (
  `id_search` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_search`,`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_search_topics`
--

LOCK TABLES `smf_1log_search_topics` WRITE;
/*!40000 ALTER TABLE `smf_1log_search_topics` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_search_topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_spider_hits`
--

DROP TABLE IF EXISTS `smf_1log_spider_hits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_spider_hits` (
  `id_hit` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_spider` smallint(5) unsigned NOT NULL DEFAULT '0',
  `log_time` int(10) unsigned NOT NULL DEFAULT '0',
  `url` varchar(255) NOT NULL DEFAULT '',
  `processed` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_hit`),
  KEY `id_spider` (`id_spider`),
  KEY `log_time` (`log_time`),
  KEY `processed` (`processed`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_spider_hits`
--

LOCK TABLES `smf_1log_spider_hits` WRITE;
/*!40000 ALTER TABLE `smf_1log_spider_hits` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_spider_hits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_spider_stats`
--

DROP TABLE IF EXISTS `smf_1log_spider_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_spider_stats` (
  `id_spider` smallint(5) unsigned NOT NULL DEFAULT '0',
  `page_hits` smallint(5) unsigned NOT NULL DEFAULT '0',
  `last_seen` int(10) unsigned NOT NULL DEFAULT '0',
  `stat_date` date NOT NULL DEFAULT '0001-01-01',
  PRIMARY KEY (`stat_date`,`id_spider`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_spider_stats`
--

LOCK TABLES `smf_1log_spider_stats` WRITE;
/*!40000 ALTER TABLE `smf_1log_spider_stats` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_spider_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_subscribed`
--

DROP TABLE IF EXISTS `smf_1log_subscribed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_subscribed` (
  `id_sublog` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_subscribe` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_member` int(10) NOT NULL DEFAULT '0',
  `old_id_group` smallint(5) NOT NULL DEFAULT '0',
  `start_time` int(10) NOT NULL DEFAULT '0',
  `end_time` int(10) NOT NULL DEFAULT '0',
  `status` tinyint(3) NOT NULL DEFAULT '0',
  `payments_pending` tinyint(3) NOT NULL DEFAULT '0',
  `pending_details` text NOT NULL,
  `reminder_sent` tinyint(3) NOT NULL DEFAULT '0',
  `vendor_ref` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_sublog`),
  UNIQUE KEY `id_subscribe` (`id_subscribe`,`id_member`),
  KEY `end_time` (`end_time`),
  KEY `reminder_sent` (`reminder_sent`),
  KEY `payments_pending` (`payments_pending`),
  KEY `status` (`status`),
  KEY `id_member` (`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_subscribed`
--

LOCK TABLES `smf_1log_subscribed` WRITE;
/*!40000 ALTER TABLE `smf_1log_subscribed` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_subscribed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1log_topics`
--

DROP TABLE IF EXISTS `smf_1log_topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1log_topics` (
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_member`,`id_topic`),
  KEY `id_topic` (`id_topic`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1log_topics`
--

LOCK TABLES `smf_1log_topics` WRITE;
/*!40000 ALTER TABLE `smf_1log_topics` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1log_topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1mail_queue`
--

DROP TABLE IF EXISTS `smf_1mail_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1mail_queue` (
  `id_mail` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `time_sent` int(10) NOT NULL DEFAULT '0',
  `recipient` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  `subject` varchar(255) NOT NULL DEFAULT '',
  `headers` text NOT NULL,
  `send_html` tinyint(3) NOT NULL DEFAULT '0',
  `priority` tinyint(3) NOT NULL DEFAULT '1',
  `private` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_mail`),
  KEY `time_sent` (`time_sent`),
  KEY `mail_priority` (`priority`,`id_mail`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1mail_queue`
--

LOCK TABLES `smf_1mail_queue` WRITE;
/*!40000 ALTER TABLE `smf_1mail_queue` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1mail_queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1membergroups`
--

DROP TABLE IF EXISTS `smf_1membergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1membergroups` (
  `id_group` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(80) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `online_color` varchar(20) NOT NULL DEFAULT '',
  `min_posts` mediumint(9) NOT NULL DEFAULT '-1',
  `max_messages` smallint(5) unsigned NOT NULL DEFAULT '0',
  `stars` varchar(255) NOT NULL DEFAULT '',
  `group_type` tinyint(3) NOT NULL DEFAULT '0',
  `hidden` tinyint(3) NOT NULL DEFAULT '0',
  `id_parent` smallint(5) NOT NULL DEFAULT '-2',
  `GroupModOptions` varchar(8) NOT NULL DEFAULT '',
  `monitorGroup` tinyint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id_group`),
  KEY `min_posts` (`min_posts`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1membergroups`
--

LOCK TABLES `smf_1membergroups` WRITE;
/*!40000 ALTER TABLE `smf_1membergroups` DISABLE KEYS */;
INSERT INTO `smf_1membergroups` VALUES (1,'Administrator','','#FF0000',-1,0,'5#staradmin.gif',1,0,-2,'',0),(2,'Global Moderator','','#0000FF',-1,0,'5#stargmod.gif',0,0,-2,'',0),(3,'Moderator','','',-1,0,'5#starmod.gif',0,0,-2,'',0),(4,'Newbie','','',0,0,'1#star.gif',0,0,-2,'',0),(5,'Jr. Member','','',50,0,'2#star.gif',0,0,-2,'',0),(6,'Full Member','','',100,0,'3#star.gif',0,0,-2,'',0),(7,'Sr. Member','','',250,0,'4#star.gif',0,0,-2,'',0),(8,'Hero Member','','',500,0,'5#star.gif',0,0,-2,'',0),(9,'Wiki Moderator','','',-1,0,'5#starmod.gif',0,0,-2,'',0);
/*!40000 ALTER TABLE `smf_1membergroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1members`
--

DROP TABLE IF EXISTS `smf_1members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1members` (
  `id_member` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `member_name` varchar(80) NOT NULL DEFAULT '',
  `date_registered` int(10) unsigned NOT NULL DEFAULT '0',
  `posts` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_group` smallint(5) unsigned NOT NULL DEFAULT '0',
  `lngfile` varchar(255) NOT NULL DEFAULT '',
  `last_login` int(10) unsigned NOT NULL DEFAULT '0',
  `real_name` varchar(255) NOT NULL DEFAULT '',
  `instant_messages` smallint(5) NOT NULL DEFAULT '0',
  `unread_messages` smallint(5) NOT NULL DEFAULT '0',
  `new_pm` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `buddy_list` text NOT NULL,
  `pm_ignore_list` varchar(255) NOT NULL DEFAULT '',
  `pm_prefs` mediumint(8) NOT NULL DEFAULT '0',
  `mod_prefs` varchar(20) NOT NULL DEFAULT '',
  `message_labels` text NOT NULL,
  `passwd` varchar(64) NOT NULL DEFAULT '',
  `openid_uri` text NOT NULL,
  `email_address` varchar(255) NOT NULL DEFAULT '',
  `personal_text` varchar(255) NOT NULL DEFAULT '',
  `gender` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `birthdate` date NOT NULL DEFAULT '0001-01-01',
  `website_title` varchar(255) NOT NULL DEFAULT '',
  `website_url` varchar(255) NOT NULL DEFAULT '',
  `location` varchar(255) NOT NULL DEFAULT '',
  `icq` varchar(255) NOT NULL DEFAULT '',
  `aim` varchar(255) NOT NULL DEFAULT '',
  `yim` varchar(32) NOT NULL DEFAULT '',
  `msn` varchar(255) NOT NULL DEFAULT '',
  `hide_email` tinyint(4) NOT NULL DEFAULT '0',
  `show_online` tinyint(4) NOT NULL DEFAULT '1',
  `time_format` varchar(80) NOT NULL DEFAULT '',
  `signature` text NOT NULL,
  `time_offset` float NOT NULL DEFAULT '0',
  `avatar` varchar(255) NOT NULL DEFAULT '',
  `pm_email_notify` tinyint(4) NOT NULL DEFAULT '0',
  `karma_bad` smallint(5) unsigned NOT NULL DEFAULT '0',
  `karma_good` smallint(5) unsigned NOT NULL DEFAULT '0',
  `usertitle` varchar(255) NOT NULL DEFAULT '',
  `notify_announcements` tinyint(4) NOT NULL DEFAULT '1',
  `notify_regularity` tinyint(4) NOT NULL DEFAULT '1',
  `notify_send_body` tinyint(4) NOT NULL DEFAULT '0',
  `notify_types` tinyint(4) NOT NULL DEFAULT '2',
  `member_ip` varchar(255) NOT NULL DEFAULT '',
  `member_ip2` varchar(255) NOT NULL DEFAULT '',
  `secret_question` varchar(255) NOT NULL DEFAULT '',
  `secret_answer` varchar(64) NOT NULL DEFAULT '',
  `id_theme` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `is_activated` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `validation_code` varchar(10) NOT NULL DEFAULT '',
  `id_msg_last_visit` int(10) unsigned NOT NULL DEFAULT '0',
  `additional_groups` varchar(255) NOT NULL DEFAULT '',
  `smiley_set` varchar(48) NOT NULL DEFAULT '',
  `id_post_group` smallint(5) unsigned NOT NULL DEFAULT '0',
  `total_time_logged_in` int(10) unsigned NOT NULL DEFAULT '0',
  `password_salt` varchar(255) NOT NULL DEFAULT '',
  `ignore_boards` text NOT NULL,
  `warning` int(11) NOT NULL DEFAULT '0',
  `passwd_flood` varchar(12) NOT NULL DEFAULT '',
  `pm_receive_from` tinyint(4) unsigned NOT NULL DEFAULT '1',
  `is_spammer` tinyint(4) NOT NULL DEFAULT '0',
  `warnLevel` tinyint(4) NOT NULL DEFAULT '0',
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
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1members`
--

LOCK TABLES `smf_1members` WRITE;
/*!40000 ALTER TABLE `smf_1members` DISABLE KEYS */;
INSERT INTO `smf_1members` VALUES (1,'devadmin',1783859711,9,1,'',1783862758,'devadmin',2,0,0,'','',0,'','','c398c51cd1502b8a7dbed773e1a184ca0c44224e','','admin@zfgc.test','',0,'0001-01-01','','','','','','','',0,1,'','',0,'',0,0,0,'',1,1,0,2,'10.89.3.12','10.89.3.12','','',0,1,'',1,'','',4,1684,'f6fb','',0,'',1,0,0),(2,'mgzero',1783862530,20,1,'',1783886844,'mgzero',1,0,0,'','',0,'','','c43bc0f53171aa2f99ee7145e386698bdc714dd8','','mgzero@zfgc.test','',0,'1986-02-21','Crystalrook Arts','https://www.etsy.com/shop/CrystalRookArts','','','','','',0,1,'','[Chorus: James Hetfield]<br />I am the view<br />I am the table<br />I am the view, I am the table<br />I am all this<br />I am the root<br />The progress<br />The aggressor<br />I am the table<br />I am the ten stories<br />[b]I am the table[/b]<br />I am, I am, I am, I am<br />I am',0,'Musicians/Queen.jpg',1,0,0,'',1,1,0,2,'10.89.3.18','10.89.3.18','','',2,1,'',19,'','',4,660,'90e3','',0,'',1,0,0),(3,'gm112',1783862549,148,1,'',1783995511,'gm112',1,1,1,'','',0,'','','f30a919782499cf41bbaf2b6e14386504bf4d92a','','gm112@zfgc.test','',0,'1991-11-21','','','','','mgzeromustplaysoulreaverdefiancebtw','','',0,1,'','',0,'',1,0,0,'',1,1,0,2,'10.89.3.9','10.89.3.9','','',2,1,'',185,'9','',6,2808,'496a','',0,'',1,0,0),(4,'testmember',1783862672,5,9,'',1783994108,'testmember',0,0,0,'','',0,'','','afbef6362e61043e2c5dfb355efc5dc1612e700e','','testmember@zfgc.test','Hey, Listen!',2,'1987-01-14','','','Hyrule Castle','','superfakescreenname','','',0,1,'','',0,'https://upload.wikimedia.org/wikipedia/en/5/57/The_Legend_of_Zelda_Ocarina_of_Time.jpg',1,0,0,'',1,1,0,2,'10.89.3.9','10.89.3.9','','',0,1,'',181,'','',4,246,'beee','',0,'',1,0,0),(5,'gmod',1783862708,1,2,'',1783994118,'gmod',0,0,0,'','',0,'','','e4b6bff7c35be79babf52706abaa9983b62d1f9c','','gmod@zfgc.test','',0,'0001-01-01','','','','','','','',0,1,'','',0,'',1,0,0,'',1,1,0,2,'10.89.3.9','10.89.3.9','','',0,1,'',184,'','',4,0,'a698','',0,'',1,0,0);
/*!40000 ALTER TABLE `smf_1members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1message_icons`
--

DROP TABLE IF EXISTS `smf_1message_icons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1message_icons` (
  `id_icon` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL DEFAULT '',
  `filename` varchar(80) NOT NULL DEFAULT '',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `icon_order` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_icon`),
  KEY `id_board` (`id_board`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1message_icons`
--

LOCK TABLES `smf_1message_icons` WRITE;
/*!40000 ALTER TABLE `smf_1message_icons` DISABLE KEYS */;
INSERT INTO `smf_1message_icons` VALUES (1,'Standard','xx',0,0),(2,'Thumb Up','thumbup',0,1),(3,'Thumb Down','thumbdown',0,2),(4,'Exclamation point','exclamation',0,3),(5,'Question mark','question',0,4),(6,'Lamp','lamp',0,5),(7,'Smiley','smiley',0,6),(8,'Angry','angry',0,7),(9,'Cheesy','cheesy',0,8),(10,'Grin','grin',0,9),(11,'Sad','sad',0,10),(12,'Wink','wink',0,11);
/*!40000 ALTER TABLE `smf_1message_icons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1messages`
--

DROP TABLE IF EXISTS `smf_1messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1messages` (
  `id_msg` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_topic` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `poster_time` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_msg_modified` int(10) unsigned NOT NULL DEFAULT '0',
  `subject` varchar(255) NOT NULL DEFAULT '',
  `poster_name` varchar(255) NOT NULL DEFAULT '',
  `poster_email` varchar(255) NOT NULL DEFAULT '',
  `poster_ip` varchar(255) NOT NULL DEFAULT '',
  `smileys_enabled` tinyint(4) NOT NULL DEFAULT '1',
  `modified_time` int(10) unsigned NOT NULL DEFAULT '0',
  `modified_name` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  `icon` varchar(16) NOT NULL DEFAULT 'xx',
  `approved` tinyint(3) NOT NULL DEFAULT '1',
  `description` text,
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
) ENGINE=MyISAM AUTO_INCREMENT=186 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1messages`
--

LOCK TABLES `smf_1messages` WRITE;
/*!40000 ALTER TABLE `smf_1messages` DISABLE KEYS */;
INSERT INTO `smf_1messages` VALUES (1,1,1,1783859655,0,1,'Welcome to SMF!','Simple Machines','info@simplemachines.org','127.0.0.1',1,0,'','Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines','xx',1,NULL),(2,2,1,1783861925,1,2,'BBCode','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Hello World!<br /><br />Text Style BBCode<br />[b]Steve[/b]<br />[i]Steeevveee[/i]<br />[u]Steve?[/u]<br />[s]Stove[/s]<br />[b][i][u]Steeeeeevvveee[/u][/i][/b]<br />[u][i][b]Stteve[/b][/i][/u]<br />[font=comic sans ms]Comic Sans ya[/font]<br />[size=36pt]BIG Font[/size]<br />[color=purple]PURPLE font[/color]<br /><br />Text Effect BBCode<br />[glow=red,2,300]Glowing Red[/glow]<br />[glow=blue,2,300]Glowin Blue[/glow]<br />[shadow=red,left]Red Shadow[/shadow]<br />[glow=red,2,300][shadow=red,left]Red glow, red shadow[/shadow][/glow]<br />[move]steve[/move]<br />[tt]Teletype ya[/tt]<br />[pre]Preformatted text[/pre]<br /><br /><br />Text Positioning BBCode[left]Left Align[/left][center]Center Align[/center][right]Right Align[/right][sup]superscript[/sup]<br />[sub]subscript[/sub]<br /><br />Content BBCode<br />[list]<br />	[li]item 1[/li]<br />	[li]item 2[/li]<br />	<br />[/list]<br />[list]<br />	[li]item one[/li]<br />	[li]item two[/li]<br />[/list]<br /><br />[quote]<br />Derp<br />[/quote]<br /><br />Layout BBCode<br /><br />[hr]<br /><br />[table]<br />[tr]<br />[td]row[/td]<br />[/tr]<br />[/table]<br /><br />','thumbup',1,NULL),(3,2,1,1783861973,1,3,'Re: BBCode','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','[quote author=devadmin link=topic=2.msg2#msg2 date=1783861925]<br />Hello World!<br /><br />Text Style BBCode<br />[b]Steve[/b]<br />[i]Steeevveee[/i]<br />[u]Steve?[/u]<br />[s]Stove[/s]<br />[b][i][u]Steeeeeevvveee[/u][/i][/b]<br />[u][i][b]Stteve[/b][/i][/u]<br />[font=comic sans ms]Comic Sans ya[/font]<br />[size=36pt]BIG Font[/size]<br />[color=purple]PURPLE font[/color]<br /><br />Text Effect BBCode<br />[glow=red,2,300]Glowing Red[/glow]<br />[glow=blue,2,300]Glowin Blue[/glow]<br />[shadow=red,left]Red Shadow[/shadow]<br />[glow=red,2,300][shadow=red,left]Red glow, red shadow[/shadow][/glow]<br />[move]steve[/move]<br />[tt]Teletype ya[/tt]<br />[pre]Preformatted text[/pre]<br /><br /><br />Text Positioning BBCode[left]Left Align[/left][center]Center Align[/center][right]Right Align[/right][sup]superscript[/sup]<br />[sub]subscript[/sub]<br /><br />Content BBCode<br />[list]<br />	[li]item 1[/li]<br />	[li]item 2[/li]<br />	<br />[/list]<br />[list]<br />	[li]item one[/li]<br />	[li]item two[/li]<br />[/list]<br /><br />[quote]<br />Derp<br />[/quote]<br /><br />Layout BBCode<br /><br />[hr]<br /><br />[table]<br />[tr]<br />[td]row[/td]<br />[/tr]<br />[/table]<br />[/quote]<br /><br />quoting another post','xx',1,NULL),(4,2,1,1783861989,1,4,'Re: BBCode','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','[quote author=Simple Machines link=topic=1.msg1#msg1 date=1783859655]<br />Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines<br />[/quote]<br /><br />quoting a post from another thread','xx',1,NULL),(5,3,1,1783862062,1,5,'Poll Thread','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Derp','xx',1,NULL),(6,4,1,1783862140,1,6,'Attachment Thread','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Test','clip',1,NULL),(7,5,1,1783862153,1,7,'Sticky Thread Test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','asdfadfgfghrtfsh','xx',1,NULL),(8,6,1,1783862169,1,8,'Locked Thread Test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','Locked&nbsp; :-X','xx',1,NULL),(9,7,2,1783862239,1,9,'Moved thread test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','oic','xx',1,NULL),(10,8,1,1783862244,1,10,'MOVED: Moved thread test','devadmin','admin@zfgc.test','10.89.3.12',1,0,'','This topic has been moved to [url=http://localhost:8090/index.php?board=2.0]New Board[/url].<br /><br />[iurl]http://localhost:8090/index.php?topic=7.0[/iurl]','moved',1,NULL),(11,2,1,1783862780,3,11,'Re: BBCode','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','[quote author=devadmin link=topic=2.msg4#msg4 date=1783861989]<br />[quote author=Simple Machines link=topic=1.msg1#msg1 date=1783859655]<br />Welcome to Simple Machines Forum!<br /><br />We hope you enjoy using your forum.&nbsp; If you have any problems, please feel free to [url=http://www.simplemachines.org/community/index.php]ask us for assistance[/url].<br /><br />Thanks!<br />Simple Machines<br />[/quote]<br />[/quote]<br /><br />Hey Steve look, a quote pyramid!<br /><br />quoting a post from another thread','xx',1,NULL),(12,2,1,1783862854,4,12,'Re: BBCode','testmember','testmember@zfgc.test','10.89.3.12',1,0,'',' :) ;) :D ;D &gt;:( :( :o 8) ??? ::) :P :-[ :-X :-\\ :-* :&#039;(','xx',1,NULL),(13,2,1,1783862871,4,13,'Re: BBCode','testmember','testmember@zfgc.test','10.89.3.12',1,0,'','I&#039;m linking another thread and double posting woohoo!<br /><br />http://localhost:8090/index.php?topic=3.0','xx',1,NULL),(14,3,1,1783862922,2,14,'Re: Poll Thread','mgzero','mgzero@zfgc.test','10.89.3.12',1,0,'',' ???<br /><br />Obviously Stove, and that&#039;s not even an option!','xx',1,NULL),(15,3,1,1783863296,3,15,'Re: Poll Thread','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','[quote author=mgzero link=topic=3.msg14#msg14 date=1783862922]<br /> ???<br /><br />Obviously Stove, and that&#039;s not even an option!<br />[/quote]<br />Yes, but we&#039;re all Steve.','xx',1,NULL),(16,4,1,1783863347,3,16,'Re: Attachment Thread','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','Another attachment test','clip',1,NULL),(17,2,1,1783863379,3,17,'Re: BBCode','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','[img]https://upload.wikimedia.org/wikipedia/en/a/a1/OcarinaOfTimeBattle.JPG[/img]<br /><br />img bbcode test','xx',1,NULL),(19,9,1,1783868360,3,19,'Ocarina of Time 2D','gm112','gm112@zfgc.test','10.89.3.12',1,0,'','Ocarina of Time 2D was made by Daniel Barras. He founded ZFGC.com to host the community that was forming around his project in 2003 on EZBoard.','xx',1,NULL),(20,3,1,1783885391,2,20,'Re: Poll Thread','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','Steve=mc^stove','xx',1,NULL),(21,3,1,1783885417,3,21,'Re: Poll Thread','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','yes indeed','xx',1,NULL),(22,10,1,1783885448,3,22,'Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','How many derps can we get in here?','xx',1,NULL),(23,10,1,1783885461,3,23,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(24,10,1,1783885465,3,24,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(25,10,1,1783885469,3,25,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(26,10,1,1783885473,3,26,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(27,10,1,1783885477,3,27,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(28,10,1,1783885481,3,28,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(29,10,1,1783885488,3,29,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(30,10,1,1783885502,3,30,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(31,10,1,1783885543,3,31,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(32,10,1,1783885548,3,32,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(33,10,1,1783885551,3,33,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(34,10,1,1783885556,3,34,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(35,10,1,1783886060,3,35,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(36,10,1,1783886064,3,36,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(37,10,1,1783886068,3,37,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(38,10,1,1783886071,3,38,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(39,10,1,1783886075,3,39,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(40,10,1,1783886079,3,40,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(41,10,1,1783886083,3,41,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(42,10,1,1783886087,3,42,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(43,10,1,1783886091,3,43,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(44,10,1,1783886095,3,44,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(45,10,1,1783886099,3,45,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(46,10,1,1783886103,3,46,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(47,10,1,1783886106,3,47,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(48,10,1,1783886110,3,48,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(49,10,1,1783886114,3,49,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(50,10,1,1783886118,3,50,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(51,10,1,1783886122,3,51,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(52,10,1,1783886126,3,52,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(53,10,1,1783886130,3,53,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(54,10,1,1783886134,3,54,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(55,10,1,1783886138,3,55,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(56,10,1,1783886141,3,56,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(57,10,1,1783886145,3,57,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(58,10,1,1783886149,3,58,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(59,10,1,1783886153,3,59,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(60,10,1,1783886157,3,60,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(61,10,1,1783886161,3,61,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(62,10,1,1783886165,3,62,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(63,10,1,1783886169,3,63,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(64,10,1,1783886173,3,64,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(65,10,1,1783886177,3,65,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(66,10,1,1783886180,3,66,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(67,10,1,1783886184,3,67,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(68,10,1,1783886188,3,68,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(69,10,1,1783886192,3,69,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(70,10,1,1783886196,3,70,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(71,10,1,1783886200,3,71,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(72,10,1,1783886204,3,72,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(73,10,1,1783886208,3,73,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(74,10,1,1783886212,3,74,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(75,10,1,1783886215,3,75,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(76,10,1,1783886219,3,76,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(77,10,1,1783886223,3,77,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(78,10,1,1783886227,3,78,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(79,10,1,1783886231,3,79,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(80,10,1,1783886235,3,80,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(81,10,1,1783886239,3,81,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(82,10,1,1783886243,3,82,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(83,10,1,1783886247,3,83,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(84,10,1,1783886250,3,84,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(85,10,1,1783886254,3,85,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(86,10,1,1783886258,3,86,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(87,10,1,1783886262,3,87,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(88,10,1,1783886266,3,88,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(89,10,1,1783886270,3,89,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(90,10,1,1783886274,3,90,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(91,10,1,1783886278,3,91,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(92,10,1,1783886282,3,92,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(93,10,1,1783886286,3,93,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(94,10,1,1783886289,3,94,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(95,10,1,1783886293,3,95,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(96,10,1,1783886297,3,96,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(97,10,1,1783886301,3,97,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(98,10,1,1783886305,3,98,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(99,10,1,1783886309,3,99,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(100,10,1,1783886313,3,100,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(101,10,1,1783886317,3,101,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(102,10,1,1783886321,3,102,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(103,10,1,1783886325,3,103,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(104,10,1,1783886328,3,104,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(105,10,1,1783886332,3,105,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(106,10,1,1783886336,3,106,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(107,10,1,1783886340,3,107,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(108,10,1,1783886344,3,108,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(109,10,1,1783886348,3,109,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(110,10,1,1783886352,3,110,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(111,10,1,1783886368,2,111,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(112,10,1,1783886372,2,112,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(113,10,1,1783886376,2,113,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(114,10,1,1783886380,2,114,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(115,10,1,1783886383,2,115,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(116,10,1,1783886388,2,116,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(117,10,1,1783886391,2,117,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(118,10,1,1783886399,2,118,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(119,10,1,1783886612,3,119,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(120,10,1,1783886615,3,120,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(121,10,1,1783886618,3,121,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(122,10,1,1783886622,3,122,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(123,10,1,1783886625,3,123,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(124,10,1,1783886628,3,124,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(125,10,1,1783886632,3,125,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(126,10,1,1783886635,3,126,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(127,10,1,1783886638,3,127,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(128,10,1,1783886642,3,128,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(129,10,1,1783886645,3,129,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(130,10,1,1783886648,3,130,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(131,10,1,1783886656,3,131,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(132,10,1,1783886659,3,132,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(133,10,1,1783886663,3,133,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(134,10,1,1783886666,3,134,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(135,10,1,1783886669,3,135,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(136,10,1,1783886673,3,136,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(137,10,1,1783886676,3,137,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(138,10,1,1783886679,3,138,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(139,10,1,1783886683,3,139,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(140,10,1,1783886686,3,140,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(141,10,1,1783886689,3,141,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(142,10,1,1783886692,3,142,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(143,10,1,1783886696,3,143,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(144,10,1,1783886699,3,144,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(145,10,1,1783886702,3,145,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(146,10,1,1783886706,3,146,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(147,10,1,1783886709,3,147,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(148,10,1,1783886712,3,148,'Re: Multiple Page Test','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','derp','xx',1,NULL),(149,10,1,1783886730,2,149,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(150,10,1,1783886735,2,150,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(151,10,1,1783886738,2,151,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(152,10,1,1783886752,2,152,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(153,10,1,1783886756,2,153,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(154,10,1,1783886759,2,154,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(155,10,1,1783886763,2,155,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(156,10,1,1783886766,2,156,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(157,10,1,1783886770,2,157,'Re: Multiple Page Test','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','that&#039;s a lot of derps!','xx',1,NULL),(158,11,1,1783886842,2,158,'Me Steve','mgzero','mgzero@zfgc.test','10.89.3.18',1,0,'','hi','xx',1,NULL),(159,12,1,1783886870,3,159,'Me Jon','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','hi','xx',1,NULL),(160,13,2,1783886896,3,160,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(161,14,2,1783886902,3,161,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','cheesy',1,NULL),(162,15,2,1783886907,3,162,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','thumbup',1,NULL),(163,16,2,1783886911,3,163,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','thumbdown',1,NULL),(164,17,2,1783886916,3,164,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','exclamation',1,NULL),(165,18,2,1783886921,3,165,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','question',1,NULL),(166,19,2,1783886927,3,166,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','lamp',1,NULL),(167,20,2,1783886934,3,167,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','smiley',1,NULL),(168,21,2,1783886940,3,168,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','angry',1,NULL),(169,22,2,1783886944,3,169,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','grin',1,NULL),(170,23,2,1783886953,3,170,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','sad',1,NULL),(171,24,2,1783886958,3,171,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','wink',1,NULL),(172,25,2,1783886962,3,172,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(173,26,2,1783886966,3,173,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(174,27,2,1783886969,3,174,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(175,28,2,1783886979,3,175,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(176,29,2,1783886984,3,176,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(177,30,2,1783886987,3,177,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(178,31,2,1783886991,3,178,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(179,32,2,1783887000,3,179,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(180,33,2,1783887003,3,180,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(181,34,2,1783887006,3,181,'Spam thread for pagination in boards','gm112','gm112@zfgc.test','10.89.3.18',1,0,'','Spam thread for pagination in boards','xx',1,NULL),(182,12,1,1783994099,4,182,'Re: Me Jon','testmember','testmember@zfgc.test','10.89.3.9',1,0,'','no me steve','xx',1,NULL),(183,11,1,1783994105,4,183,'Re: Me Steve','testmember','testmember@zfgc.test','10.89.3.9',1,0,'','stove','xx',1,NULL),(184,34,2,1783994112,4,184,'Re: Spam thread for pagination in boards','testmember','testmember@zfgc.test','10.89.3.9',1,0,'','pizza','xx',1,NULL),(185,11,1,1783994127,5,185,'Re: Me Steve','gmod','gmod@zfgc.test','10.89.3.9',1,0,'','gordon steven in the flesh','xx',1,NULL);
/*!40000 ALTER TABLE `smf_1messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1messages_history`
--

DROP TABLE IF EXISTS `smf_1messages_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1messages_history` (
  `id_edit` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `modified_name` varchar(255) NOT NULL DEFAULT '',
  `modified_time` int(10) unsigned NOT NULL DEFAULT '0',
  `body` text,
  PRIMARY KEY (`id_edit`),
  KEY `id_msg` (`id_msg`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1messages_history`
--

LOCK TABLES `smf_1messages_history` WRITE;
/*!40000 ALTER TABLE `smf_1messages_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1messages_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1moderators`
--

DROP TABLE IF EXISTS `smf_1moderators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1moderators` (
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_board`,`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1moderators`
--

LOCK TABLES `smf_1moderators` WRITE;
/*!40000 ALTER TABLE `smf_1moderators` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1moderators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1openid_assoc`
--

DROP TABLE IF EXISTS `smf_1openid_assoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1openid_assoc` (
  `server_url` text NOT NULL,
  `handle` varchar(255) NOT NULL DEFAULT '',
  `secret` text NOT NULL,
  `issued` int(10) NOT NULL DEFAULT '0',
  `expires` int(10) NOT NULL DEFAULT '0',
  `assoc_type` varchar(64) NOT NULL,
  PRIMARY KEY (`server_url`(125),`handle`(125)),
  KEY `expires` (`expires`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1openid_assoc`
--

LOCK TABLES `smf_1openid_assoc` WRITE;
/*!40000 ALTER TABLE `smf_1openid_assoc` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1openid_assoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1package_servers`
--

DROP TABLE IF EXISTS `smf_1package_servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1package_servers` (
  `id_server` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_server`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1package_servers`
--

LOCK TABLES `smf_1package_servers` WRITE;
/*!40000 ALTER TABLE `smf_1package_servers` DISABLE KEYS */;
INSERT INTO `smf_1package_servers` VALUES (1,'Simple Machines Third-party Mod Site','http://custom.simplemachines.org/packages/mods');
/*!40000 ALTER TABLE `smf_1package_servers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1permission_profiles`
--

DROP TABLE IF EXISTS `smf_1permission_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1permission_profiles` (
  `id_profile` smallint(5) NOT NULL AUTO_INCREMENT,
  `profile_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_profile`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1permission_profiles`
--

LOCK TABLES `smf_1permission_profiles` WRITE;
/*!40000 ALTER TABLE `smf_1permission_profiles` DISABLE KEYS */;
INSERT INTO `smf_1permission_profiles` VALUES (1,'default'),(2,'no_polls'),(3,'reply_only'),(4,'read_only');
/*!40000 ALTER TABLE `smf_1permission_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1permissions`
--

DROP TABLE IF EXISTS `smf_1permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1permissions` (
  `id_group` smallint(5) NOT NULL DEFAULT '0',
  `permission` varchar(30) NOT NULL DEFAULT '',
  `add_deny` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_group`,`permission`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1permissions`
--

LOCK TABLES `smf_1permissions` WRITE;
/*!40000 ALTER TABLE `smf_1permissions` DISABLE KEYS */;
INSERT INTO `smf_1permissions` VALUES (-1,'search_posts',1),(-1,'calendar_view',1),(-1,'view_stats',1),(-1,'profile_view_any',1),(0,'view_mlist',1),(0,'search_posts',1),(0,'profile_view_own',1),(0,'profile_view_any',1),(0,'pm_read',1),(0,'pm_send',1),(0,'calendar_view',1),(0,'view_stats',1),(0,'who_view',1),(0,'profile_identity_own',1),(0,'profile_extra_own',1),(0,'profile_remove_own',1),(0,'profile_server_avatar',1),(0,'profile_upload_avatar',1),(0,'profile_remote_avatar',1),(0,'karma_edit',1),(2,'view_mlist',1),(2,'search_posts',1),(2,'profile_view_own',1),(2,'profile_view_any',1),(2,'pm_read',1),(2,'pm_send',1),(2,'calendar_view',1),(2,'view_stats',1),(2,'who_view',1),(2,'profile_identity_own',1),(2,'profile_extra_own',1),(2,'profile_remove_own',1),(2,'profile_server_avatar',1),(2,'profile_upload_avatar',1),(2,'profile_remote_avatar',1),(2,'profile_title_own',1),(2,'calendar_post',1),(2,'calendar_edit_any',1),(2,'karma_edit',1),(2,'access_mod_center',1),(9,'calendar_view',1),(9,'karma_edit',1),(9,'pm_read',1),(9,'pm_send',1),(9,'profile_extra_own',1),(9,'profile_identity_own',1),(9,'profile_remote_avatar',1),(9,'profile_remove_own',1),(9,'profile_server_avatar',1),(9,'profile_upload_avatar',1),(9,'profile_view_any',1),(9,'profile_view_own',1),(9,'search_posts',1),(9,'view_mlist',1),(9,'view_stats',1),(9,'who_view',1);
/*!40000 ALTER TABLE `smf_1permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1personal_messages`
--

DROP TABLE IF EXISTS `smf_1personal_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1personal_messages` (
  `id_pm` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_pm_head` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member_from` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `deleted_by_sender` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `from_name` varchar(255) NOT NULL DEFAULT '',
  `msgtime` int(10) unsigned NOT NULL DEFAULT '0',
  `subject` varchar(255) NOT NULL DEFAULT '',
  `body` text NOT NULL,
  PRIMARY KEY (`id_pm`),
  KEY `id_member` (`id_member_from`,`deleted_by_sender`),
  KEY `msgtime` (`msgtime`),
  KEY `id_pm_head` (`id_pm_head`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1personal_messages`
--

LOCK TABLES `smf_1personal_messages` WRITE;
/*!40000 ALTER TABLE `smf_1personal_messages` DISABLE KEYS */;
INSERT INTO `smf_1personal_messages` VALUES (1,1,1,0,'devadmin',1783862007,'(No subject)','Private message to myself'),(2,2,1,1,'devadmin',1783862287,'BBCode Test in Private Messages','BBCode PM Test<br /><br />Hello World!<br /><br />Text Style BBCode<br />[b]Steve[/b]<br />[i]Steeevveee[/i]<br />[u]Steve?[/u]<br />[s]Stove[/s]<br />[b][i][u]Steeeeeevvveee[/u][/i][/b]<br />[u][i][b]Stteve[/b][/i][/u]<br />[font=comic sans ms]Comic Sans ya[/font]<br />[size=36pt]BIG Font[/size]<br />[color=purple]PURPLE font[/color]<br /><br />Text Effect BBCode<br />[glow=red,2,300]Glowing Red[/glow]<br />[glow=blue,2,300]Glowin Blue[/glow]<br />[shadow=red,left]Red Shadow[/shadow]<br />[glow=red,2,300][shadow=red,left]Red glow, red shadow[/shadow][/glow]<br />[move]steve[/move]<br />[tt]Teletype ya[/tt]<br />[pre]Preformatted text[/pre]<br /><br /><br />Text Positioning BBCode[left]Left Align[/left][center]Center Align[/center][right]Right Align[/right][sup]superscript[/sup]<br />[sub]subscript[/sub]<br /><br />Content BBCode<br />[list]<br />	[li]item 1[/li]<br />	[li]item 2[/li]<br />	<br />[/list]<br />[list]<br />	[li]item one[/li]<br />	[li]item two[/li]<br />[/list]<br /><br />[quote]<br />Derp<br />[/quote]<br /><br />Layout BBCode<br /><br />[hr]<br /><br />[table]<br />[tr]<br />[td]row[/td]<br />[/tr]<br />[/table]<br /><br />'),(3,3,3,0,'gm112',1783862640,'kamehameha','IMMA CHARGIN UP!<br /><br />AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH<br /><br /><br />[size=36pt]AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH[/size]<br /><br />Now this is only 1/4 of my power! HEH.'),(4,3,2,1,'mgzero',1783885346,'Re: kamehameha','[quote author=gm112 link=action=profile;u=3 date=1783862640]<br />IMMA CHARGIN UP!<br /><br />AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH<br /><br /><br />[size=36pt]AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH[/size]<br /><br />Now this is only 1/4 of my power! HEH.<br />[/quote]<br /><br />IMMA CHARGIN UP');
/*!40000 ALTER TABLE `smf_1personal_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1pm_recipients`
--

DROP TABLE IF EXISTS `smf_1pm_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1pm_recipients` (
  `id_pm` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `labels` varchar(60) NOT NULL DEFAULT '-1',
  `bcc` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `is_read` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `is_new` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_pm`,`id_member`),
  UNIQUE KEY `id_member` (`id_member`,`deleted`,`id_pm`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1pm_recipients`
--

LOCK TABLES `smf_1pm_recipients` WRITE;
/*!40000 ALTER TABLE `smf_1pm_recipients` DISABLE KEYS */;
INSERT INTO `smf_1pm_recipients` VALUES (1,1,'-1',0,1,0,0),(2,1,'-1',0,1,0,0),(3,2,'-1',0,3,0,1),(4,3,'-1',0,0,1,0),(3,5,'-1',1,0,1,0);
/*!40000 ALTER TABLE `smf_1pm_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1pm_rules`
--

DROP TABLE IF EXISTS `smf_1pm_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1pm_rules` (
  `id_rule` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_member` int(10) unsigned NOT NULL DEFAULT '0',
  `rule_name` varchar(60) NOT NULL,
  `criteria` text NOT NULL,
  `actions` text NOT NULL,
  `delete_pm` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `is_or` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_rule`),
  KEY `id_member` (`id_member`),
  KEY `delete_pm` (`delete_pm`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1pm_rules`
--

LOCK TABLES `smf_1pm_rules` WRITE;
/*!40000 ALTER TABLE `smf_1pm_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1pm_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1poll_choices`
--

DROP TABLE IF EXISTS `smf_1poll_choices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1poll_choices` (
  `id_poll` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_choice` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `label` varchar(255) NOT NULL DEFAULT '',
  `votes` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_poll`,`id_choice`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1poll_choices`
--

LOCK TABLES `smf_1poll_choices` WRITE;
/*!40000 ALTER TABLE `smf_1poll_choices` DISABLE KEYS */;
INSERT INTO `smf_1poll_choices` VALUES (1,0,'Steve?',0),(1,1,'Steve!',1),(1,2,'Stone',2);
/*!40000 ALTER TABLE `smf_1poll_choices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1polls`
--

DROP TABLE IF EXISTS `smf_1polls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1polls` (
  `id_poll` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL DEFAULT '',
  `voting_locked` tinyint(1) NOT NULL DEFAULT '0',
  `max_votes` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `expire_time` int(10) unsigned NOT NULL DEFAULT '0',
  `hide_results` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `change_vote` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `guest_vote` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `num_guest_voters` int(10) unsigned NOT NULL DEFAULT '0',
  `reset_poll` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member` mediumint(8) NOT NULL DEFAULT '0',
  `poster_name` varchar(255) NOT NULL DEFAULT '',
  `ID_TOPIC` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_poll`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1polls`
--

LOCK TABLES `smf_1polls` WRITE;
/*!40000 ALTER TABLE `smf_1polls` DISABLE KEYS */;
INSERT INTO `smf_1polls` VALUES (1,'How many Steves can Steve?',0,1,1784121262,0,1,0,0,0,1,'devadmin',0);
/*!40000 ALTER TABLE `smf_1polls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1resource_comments`
--

DROP TABLE IF EXISTS `smf_1resource_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1resource_comments` (
  `ID_COMMENT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_RESOURCE` int(11) NOT NULL,
  `ID_MEMBER` int(11) NOT NULL,
  `body` mediumtext NOT NULL,
  `postTime` int(11) NOT NULL,
  `postIP` mediumtext NOT NULL,
  PRIMARY KEY (`ID_COMMENT`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1resource_comments`
--

LOCK TABLES `smf_1resource_comments` WRITE;
/*!40000 ALTER TABLE `smf_1resource_comments` DISABLE KEYS */;
INSERT INTO `smf_1resource_comments` VALUES (1,5,4,'Maecenas nulla tortor, maximus eu molestie id, convallis sed tellus.',1629137919,'10.89.0.114'),(2,5,27774,'Cras ullamcorper urna quam, eget vulputate massa viverra ut.',1629137954,'10.89.0.114'),(4,8,3,'Nulla in risus posuere, varius erat non, interdum augue.',1629220013,'10.89.0.114');
/*!40000 ALTER TABLE `smf_1resource_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1resource_downloads`
--

DROP TABLE IF EXISTS `smf_1resource_downloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1resource_downloads` (
  `ID_DOWNLOAD` int(11) NOT NULL AUTO_INCREMENT,
  `ID_RESOURCE` int(11) NOT NULL,
  `description` mediumtext NOT NULL,
  `fileSize` int(11) NOT NULL,
  `fileURL` mediumtext NOT NULL,
  `postTime` int(11) NOT NULL,
  `postIP` mediumtext NOT NULL,
  `downloads` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `ID_MEMBER` int(11) NOT NULL,
  PRIMARY KEY (`ID_DOWNLOAD`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1resource_downloads`
--

LOCK TABLES `smf_1resource_downloads` WRITE;
/*!40000 ALTER TABLE `smf_1resource_downloads` DISABLE KEYS */;
INSERT INTO `smf_1resource_downloads` VALUES (4,5,'Preview',550600,'aonuma_photo.png',1629219249,'10.0.2.5',8422,1,0),(5,5,'Lorem ipsum pack.',549682,'aonuma_photos.zip',1629219807,'10.0.2.5',540,3,41),(15,8,'Preview',49471,'kondo_photo.jpg',1629235602,'10.0.2.8',1885,1,0),(16,8,'Dolor sit pack.',170,'kondo_tracks.zip',1629236553,'10.0.2.8',20,3,52),(17,9,'Preview',93571,'miyamoto_photo.jpg',1629258886,'10.0.2.9',9203,1,0),(18,9,'Consectetur photo.',93571,'miyamoto_photo.jpg',1629258900,'10.0.2.9',77,3,92);
/*!40000 ALTER TABLE `smf_1resource_downloads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1resources_main`
--

DROP TABLE IF EXISTS `smf_1resources_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1resources_main` (
  `ID_RESOURCE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_MEMBER` int(11) NOT NULL,
  `title` mediumtext NOT NULL,
  `views` int(11) NOT NULL,
  `downloads` int(11) NOT NULL,
  `votes` mediumtext NOT NULL,
  `voters` mediumtext NOT NULL,
  `body` mediumtext NOT NULL,
  `postTime` int(11) NOT NULL,
  `postIP` mediumtext NOT NULL,
  `type` int(11) NOT NULL,
  `fileSize` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `ID_PREVIEW` int(11) NOT NULL,
  `voteCount` int(11) NOT NULL,
  PRIMARY KEY (`ID_RESOURCE`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1resources_main`
--

LOCK TABLES `smf_1resources_main` WRITE;
/*!40000 ALTER TABLE `smf_1resources_main` DISABLE KEYS */;
INSERT INTO `smf_1resources_main` VALUES (5,2,'Eiji Aonuma Photo Collection',930,518,'5,5,4,5','41,4089,4247,23298','Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n\r\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris.',1629219249,'10.0.2.5',2,0,5,4,4),(8,3,'Koji Kondo Zelda Themes',147,20,'5','52','Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n\r\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris.',1629235602,'10.0.2.8',1,0,5,5,1),(9,3,'Shigeru Miyamoto Photo Archive',964,603,'5,5,5,4','92,101,204,305','Lorem ipsum dolor sit amet, consectetur adipiscing elit.',1629258886,'10.0.2.9',2,0,5,5,4);
/*!40000 ALTER TABLE `smf_1resources_main` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1scheduled_tasks`
--

DROP TABLE IF EXISTS `smf_1scheduled_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1scheduled_tasks` (
  `id_task` smallint(5) NOT NULL AUTO_INCREMENT,
  `next_time` int(10) NOT NULL DEFAULT '0',
  `time_offset` int(10) NOT NULL DEFAULT '0',
  `time_regularity` smallint(5) NOT NULL DEFAULT '0',
  `time_unit` varchar(1) NOT NULL DEFAULT 'h',
  `disabled` tinyint(3) NOT NULL DEFAULT '0',
  `task` varchar(24) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_task`),
  UNIQUE KEY `task` (`task`),
  KEY `next_time` (`next_time`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1scheduled_tasks`
--

LOCK TABLES `smf_1scheduled_tasks` WRITE;
/*!40000 ALTER TABLE `smf_1scheduled_tasks` DISABLE KEYS */;
INSERT INTO `smf_1scheduled_tasks` VALUES (1,1784001600,0,2,'h',0,'approval_notification'),(2,1784419200,0,7,'d',0,'auto_optimize'),(3,1784073660,60,1,'d',0,'daily_maintenance'),(5,1784073600,0,1,'d',0,'daily_digest'),(6,1784419200,0,1,'w',0,'weekly_digest'),(7,1784036940,136183,1,'d',0,'fetchSMfiles'),(8,0,0,1,'d',1,'birthdayemails'),(9,1784419200,0,1,'w',0,'weekly_maintenance'),(10,0,120,1,'d',1,'paid_subscriptions');
/*!40000 ALTER TABLE `smf_1scheduled_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1sessions`
--

DROP TABLE IF EXISTS `smf_1sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1sessions` (
  `session_id` char(32) NOT NULL,
  `last_update` int(10) unsigned NOT NULL,
  `data` text NOT NULL,
  PRIMARY KEY (`session_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1sessions`
--

LOCK TABLES `smf_1sessions` WRITE;
/*!40000 ALTER TABLE `smf_1sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1settings`
--

DROP TABLE IF EXISTS `smf_1settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1settings` (
  `variable` varchar(255) NOT NULL DEFAULT '',
  `value` text NOT NULL,
  PRIMARY KEY (`variable`(30))
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1settings`
--

LOCK TABLES `smf_1settings` WRITE;
/*!40000 ALTER TABLE `smf_1settings` DISABLE KEYS */;
INSERT INTO `smf_1settings` VALUES ('smfVersion','2.0.15'),('news','SMF - Just Installed!'),('compactTopicPagesContiguous','5'),('compactTopicPagesEnable','1'),('enableStickyTopics','1'),('todayMod','1'),('karmaMode','0'),('karmaTimeRestrictAdmins','1'),('enablePreviousNext','1'),('pollMode','1'),('enableVBStyleLogin','1'),('enableCompressedOutput','0'),('karmaWaitTime','1'),('karmaMinPosts','0'),('karmaLabel','Karma:'),('karmaSmiteLabel','[smite]'),('karmaApplaudLabel','[applaud]'),('attachmentSizeLimit','128'),('attachmentPostLimit','192'),('attachmentNumPerPostLimit','4'),('attachmentDirSizeLimit','10240'),('attachmentUploadDir','/var/www/html/attachments'),('attachmentExtensions','doc,gif,jpg,mpg,pdf,png,txt,zip'),('attachmentCheckExtensions','0'),('attachmentShowImages','1'),('attachmentEnable','1'),('attachmentEncryptFilenames','1'),('attachmentThumbnails','1'),('attachmentThumbWidth','150'),('attachmentThumbHeight','150'),('censorIgnoreCase','1'),('mostOnline','2'),('mostOnlineToday','1'),('mostDate','1783886844'),('allow_disableAnnounce','1'),('trackStats','1'),('userLanguage','1'),('titlesEnable','1'),('topicSummaryPosts','15'),('enableErrorLogging','1'),('max_image_width','0'),('max_image_height','0'),('onlineEnable','0'),('cal_enabled','0'),('cal_maxyear','2030'),('cal_minyear','2008'),('cal_daysaslink','0'),('cal_defaultboard',''),('cal_showholidays','1'),('cal_showbdays','1'),('cal_showevents','1'),('cal_showweeknum','0'),('cal_maxspan','7'),('smtp_host',''),('smtp_port','25'),('smtp_username',''),('smtp_password',''),('mail_type','0'),('timeLoadPageEnable','0'),('totalMembers','5'),('totalTopics','34'),('totalMessages','184'),('simpleSearch','0'),('censor_vulgar',''),('censor_proper',''),('enablePostHTML','0'),('theme_allow','1'),('theme_default','1'),('theme_guests','1'),('enableEmbeddedFlash','0'),('xmlnews_enable','1'),('xmlnews_maxlen','255'),('hotTopicPosts','15'),('hotTopicVeryPosts','25'),('registration_method','0'),('send_validation_onChange','0'),('send_welcomeEmail','1'),('allow_editDisplayName','1'),('allow_hideOnline','1'),('guest_hideContacts','1'),('spamWaitTime','5'),('pm_spam_settings','10,5,20'),('reserveWord','0'),('reserveCase','1'),('reserveUser','1'),('reserveName','1'),('reserveNames','Admin\nWebmaster\nGuest\nroot'),('autoLinkUrls','1'),('banLastUpdated','0'),('smileys_dir','/var/www/html/Smileys'),('smileys_url','http://localhost:8090/Smileys'),('avatar_directory','/var/www/html/avatars'),('avatar_url','http://localhost:8090/avatars'),('avatar_max_height_external','65'),('avatar_max_width_external','65'),('avatar_action_too_large','option_html_resize'),('avatar_max_height_upload','65'),('avatar_max_width_upload','65'),('avatar_resize_upload','1'),('avatar_download_png','1'),('failed_login_threshold','3'),('oldTopicDays','120'),('edit_wait_time','90'),('edit_disable_time','0'),('autoFixDatabase','1'),('allow_guestAccess','1'),('time_format','%B %d, %Y, %I:%M:%S %p'),('number_format','1234.00'),('enableBBC','1'),('max_messageLength','20000'),('signature_settings','1,300,0,0,0,0,0,0:'),('autoOptMaxOnline','0'),('defaultMaxMessages','15'),('defaultMaxTopics','20'),('defaultMaxMembers','30'),('enableParticipation','1'),('recycle_enable','0'),('recycle_board','0'),('maxMsgID','185'),('enableAllMessages','0'),('fixLongWords','0'),('knownThemes','1,2,3'),('who_enabled','1'),('time_offset','0'),('cookieTime','60'),('lastActive','15'),('smiley_sets_known','default,aaron,akyhne'),('smiley_sets_names','Alienine\'s Set\nAaron\'s Set\nAkyhne\'s Set'),('smiley_sets_default','default'),('cal_days_for_index','7'),('requireAgreement','1'),('unapprovedMembers','0'),('default_personal_text',''),('package_make_backups','1'),('databaseSession_enable','0'),('databaseSession_loose','1'),('databaseSession_lifetime','2880'),('search_cache_size','50'),('search_results_per_page','30'),('search_weight_frequency','30'),('search_weight_age','25'),('search_weight_length','20'),('search_weight_subject','15'),('search_weight_first_message','10'),('search_max_results','1200'),('search_floodcontrol_time','5'),('permission_enable_deny','0'),('permission_enable_postgroups','0'),('mail_next_send','0'),('mail_recent','0000000000|0'),('settings_updated','1783994152'),('next_task_time','1784001600'),('warning_settings','1,20,0'),('warning_watch','10'),('warning_moderate','35'),('warning_mute','60'),('admin_features',''),('last_mod_report_action','0'),('pruningOptions','30,180,180,180,30,0'),('cache_enable','1'),('reg_verification','1'),('visual_verification_type','3'),('enable_buddylist','1'),('birthday_email','happy_birthday'),('dont_repeat_theme_core','1'),('dont_repeat_smileys_20','1'),('dont_repeat_buddylists','1'),('attachment_image_reencode','1'),('attachment_image_paranoid','0'),('attachment_thumb_png','1'),('avatar_reencode','1'),('avatar_paranoid','0'),('global_character_set','UTF-8'),('globalCookies','1'),('default_timezone','Etc/GMT0'),('memberlist_updated','1783862708'),('latestMember','5'),('latestRealName','gmod'),('rand_seed','679934102'),('mostOnlineUpdated','2026-07-14'),('calendar_updated','1783863430');
/*!40000 ALTER TABLE `smf_1settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1smileys`
--

DROP TABLE IF EXISTS `smf_1smileys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1smileys` (
  `id_smiley` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL DEFAULT '',
  `filename` varchar(48) NOT NULL DEFAULT '',
  `description` varchar(80) NOT NULL DEFAULT '',
  `smiley_row` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `smiley_order` smallint(5) unsigned NOT NULL DEFAULT '0',
  `hidden` tinyint(4) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_smiley`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1smileys`
--

LOCK TABLES `smf_1smileys` WRITE;
/*!40000 ALTER TABLE `smf_1smileys` DISABLE KEYS */;
INSERT INTO `smf_1smileys` VALUES (1,':)','smiley.gif','Smiley',0,0,0),(2,';)','wink.gif','Wink',0,1,0),(3,':D','cheesy.gif','Cheesy',0,2,0),(4,';D','grin.gif','Grin',0,3,0),(5,'>:(','angry.gif','Angry',0,4,0),(6,':(','sad.gif','Sad',0,5,0),(7,':o','shocked.gif','Shocked',0,6,0),(8,'8)','cool.gif','Cool',0,7,0),(9,'???','huh.gif','Huh?',0,8,0),(10,'::)','rolleyes.gif','Roll Eyes',0,9,0),(11,':P','tongue.gif','Tongue',0,10,0),(12,':-[','embarrassed.gif','Embarrassed',0,11,0),(13,':-X','lipsrsealed.gif','Lips Sealed',0,12,0),(14,':-\\','undecided.gif','Undecided',0,13,0),(15,':-*','kiss.gif','Kiss',0,14,0),(16,':\'(','cry.gif','Cry',0,15,0),(17,'>:D','evil.gif','Evil',0,16,1),(18,'^-^','azn.gif','Azn',0,17,1),(19,'O0','afro.gif','Afro',0,18,1),(20,':))','laugh.gif','Laugh',0,19,1),(21,'C:-)','police.gif','Police',0,20,1),(22,'O:-)','angel.gif','Angel',0,21,1);
/*!40000 ALTER TABLE `smf_1smileys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1spiders`
--

DROP TABLE IF EXISTS `smf_1spiders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1spiders` (
  `id_spider` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `spider_name` varchar(255) NOT NULL DEFAULT '',
  `user_agent` varchar(255) NOT NULL DEFAULT '',
  `ip_info` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_spider`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1spiders`
--

LOCK TABLES `smf_1spiders` WRITE;
/*!40000 ALTER TABLE `smf_1spiders` DISABLE KEYS */;
INSERT INTO `smf_1spiders` VALUES (1,'Google','googlebot',''),(2,'Yahoo!','slurp',''),(3,'MSN','msnbot',''),(4,'Google (Mobile)','Googlebot-Mobile',''),(5,'Google (Image)','Googlebot-Image',''),(6,'Google (AdSense)','Mediapartners-Google',''),(7,'Google (Adwords)','AdsBot-Google',''),(8,'Yahoo! (Mobile)','YahooSeeker/M1A1-R2D2',''),(9,'Yahoo! (Image)','Yahoo-MMCrawler',''),(10,'MSN (Mobile)','MSNBOT_Mobile',''),(11,'MSN (Media)','msnbot-media',''),(12,'Cuil','twiceler',''),(13,'Ask','Teoma',''),(14,'Baidu','Baiduspider',''),(15,'Gigablast','Gigabot',''),(16,'InternetArchive','ia_archiver-web.archive.org',''),(17,'Alexa','ia_archiver',''),(18,'Omgili','omgilibot',''),(19,'EntireWeb','Speedy Spider','');
/*!40000 ALTER TABLE `smf_1spiders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1subscriptions`
--

DROP TABLE IF EXISTS `smf_1subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1subscriptions` (
  `id_subscribe` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `cost` text NOT NULL,
  `length` varchar(6) NOT NULL DEFAULT '',
  `id_group` smallint(5) NOT NULL DEFAULT '0',
  `add_groups` varchar(40) NOT NULL DEFAULT '',
  `active` tinyint(3) NOT NULL DEFAULT '1',
  `repeatable` tinyint(3) NOT NULL DEFAULT '0',
  `allow_partial` tinyint(3) NOT NULL DEFAULT '0',
  `reminder` tinyint(3) NOT NULL DEFAULT '0',
  `email_complete` text NOT NULL,
  PRIMARY KEY (`id_subscribe`),
  KEY `active` (`active`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1subscriptions`
--

LOCK TABLES `smf_1subscriptions` WRITE;
/*!40000 ALTER TABLE `smf_1subscriptions` DISABLE KEYS */;
/*!40000 ALTER TABLE `smf_1subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1themes`
--

DROP TABLE IF EXISTS `smf_1themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1themes` (
  `id_member` mediumint(8) NOT NULL DEFAULT '0',
  `id_theme` tinyint(4) unsigned NOT NULL DEFAULT '1',
  `variable` varchar(255) NOT NULL DEFAULT '',
  `value` text NOT NULL,
  PRIMARY KEY (`id_theme`,`id_member`,`variable`(30)),
  KEY `id_member` (`id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1themes`
--

LOCK TABLES `smf_1themes` WRITE;
/*!40000 ALTER TABLE `smf_1themes` DISABLE KEYS */;
INSERT INTO `smf_1themes` VALUES (0,1,'name','SMF Default Theme - Curve'),(0,1,'theme_url','http://localhost:8090/Themes/default'),(0,1,'images_url','http://localhost:8090/Themes/default/images'),(0,1,'theme_dir','/var/www/html/Themes/default'),(0,1,'show_bbc','1'),(0,1,'show_latest_member','1'),(0,1,'show_modify','1'),(0,1,'show_user_images','1'),(0,1,'show_blurb','1'),(0,1,'show_gender','0'),(0,1,'show_newsfader','0'),(0,1,'number_recent_posts','0'),(0,1,'show_member_bar','1'),(0,1,'linktree_link','1'),(0,1,'show_profile_buttons','1'),(0,1,'show_mark_read','1'),(0,1,'show_stats_index','1'),(0,1,'linktree_inline','0'),(0,1,'show_board_desc','1'),(0,1,'newsfader_time','5000'),(0,1,'allow_no_censored','0'),(0,1,'additional_options_collapsable','1'),(0,1,'use_image_buttons','1'),(0,1,'enable_news','1'),(0,1,'forum_width','90%'),(0,2,'name','Core Theme'),(0,2,'theme_url','http://localhost:8090/Themes/core'),(0,2,'images_url','http://localhost:8090/Themes/core/images'),(0,2,'theme_dir','/var/www/html/Themes/core'),(-1,1,'display_quick_reply','1'),(-1,1,'posts_apply_ignore_list','1'),(1,1,'use_sidebar_menu','1'),(0,2,'header_logo_url',''),(0,2,'smiley_sets_default',''),(0,2,'forum_width','90%'),(0,2,'show_mark_read','1'),(0,2,'allow_no_censored','0'),(0,2,'enable_news','1'),(0,2,'use_image_buttons','1'),(0,2,'show_newsfader','1'),(0,2,'newsfader_time','5000'),(0,2,'number_recent_posts','0'),(0,2,'show_stats_index','1'),(0,2,'show_latest_member','1'),(0,2,'show_group_key','0'),(0,2,'display_who_viewing','0'),(0,2,'show_modify','1'),(0,2,'show_profile_buttons','1'),(0,2,'show_user_images','1'),(0,2,'show_blurb','1'),(0,2,'show_gender','0'),(0,2,'hide_post_group','0'),(0,2,'show_bbc','1'),(0,2,'additional_options_collapsable','1');
/*!40000 ALTER TABLE `smf_1themes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smf_1topics`
--

DROP TABLE IF EXISTS `smf_1topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smf_1topics` (
  `id_topic` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `is_sticky` tinyint(4) NOT NULL DEFAULT '0',
  `id_board` smallint(5) unsigned NOT NULL DEFAULT '0',
  `id_first_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `id_last_msg` int(10) unsigned NOT NULL DEFAULT '0',
  `id_member_started` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_member_updated` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_poll` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `id_previous_board` smallint(5) NOT NULL DEFAULT '0',
  `id_previous_topic` mediumint(8) NOT NULL DEFAULT '0',
  `num_replies` int(10) unsigned NOT NULL DEFAULT '0',
  `num_views` int(10) unsigned NOT NULL DEFAULT '0',
  `locked` tinyint(4) NOT NULL DEFAULT '0',
  `unapproved_posts` smallint(5) NOT NULL DEFAULT '0',
  `approved` tinyint(3) NOT NULL DEFAULT '1',
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
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smf_1topics`
--

LOCK TABLES `smf_1topics` WRITE;
/*!40000 ALTER TABLE `smf_1topics` DISABLE KEYS */;
INSERT INTO `smf_1topics` VALUES (1,0,1,1,1,0,0,0,0,0,0,3,0,0,1),(2,0,1,2,17,1,3,0,0,0,6,12,0,0,1),(3,0,1,5,21,1,3,1,0,0,4,14,0,0,1),(4,0,1,6,16,1,3,0,0,0,1,1,0,0,1),(5,1,1,7,7,1,1,0,0,0,0,0,0,0,1),(6,0,1,8,8,1,1,0,0,0,0,0,1,0,1),(7,1,2,9,9,1,1,0,0,0,0,1,0,0,1),(8,0,1,10,10,1,1,0,0,0,0,0,1,0,1),(9,0,1,19,19,3,3,0,0,0,0,0,0,0,1),(10,0,1,22,157,3,2,0,0,0,135,43,0,0,1),(11,0,1,158,185,2,5,0,0,0,2,4,0,0,1),(12,0,1,159,182,3,4,0,0,0,1,3,0,0,1),(13,0,2,160,160,3,3,0,0,0,0,0,0,0,1),(14,0,2,161,161,3,3,0,0,0,0,0,0,0,1),(15,0,2,162,162,3,3,0,0,0,0,0,0,0,1),(16,0,2,163,163,3,3,0,0,0,0,0,0,0,1),(17,0,2,164,164,3,3,0,0,0,0,0,0,0,1),(18,0,2,165,165,3,3,0,0,0,0,0,0,0,1),(19,0,2,166,166,3,3,0,0,0,0,0,0,0,1),(20,0,2,167,167,3,3,0,0,0,0,0,0,0,1),(21,0,2,168,168,3,3,0,0,0,0,0,0,0,1),(22,0,2,169,169,3,3,0,0,0,0,0,0,0,1),(23,0,2,170,170,3,3,0,0,0,0,0,0,0,1),(24,0,2,171,171,3,3,0,0,0,0,0,0,0,1),(25,0,2,172,172,3,3,0,0,0,0,0,0,0,1),(26,0,2,173,173,3,3,0,0,0,0,0,0,0,1),(27,0,2,174,174,3,3,0,0,0,0,0,0,0,1),(28,0,2,175,175,3,3,0,0,0,0,0,0,0,1),(29,0,2,176,176,3,3,0,0,0,0,0,0,0,1),(30,0,2,177,177,3,3,0,0,0,0,0,0,0,1),(31,0,2,178,178,3,3,0,0,0,0,0,0,0,1),(32,0,2,179,179,3,3,0,0,0,0,0,0,0,1),(33,0,2,180,180,3,3,0,0,0,0,0,0,0,1),(34,0,2,181,184,3,4,0,0,0,1,2,0,0,1);
/*!40000 ALTER TABLE `smf_1topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zfgc_wikicategorylinks`
--

DROP TABLE IF EXISTS `zfgc_wikicategorylinks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zfgc_wikicategorylinks` (
  `cl_from` int(10) unsigned NOT NULL DEFAULT '0',
  `cl_to` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `cl_sortkey` varbinary(230) NOT NULL DEFAULT '',
  `cl_sortkey_prefix` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `cl_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cl_collation` varbinary(32) NOT NULL DEFAULT '',
  `cl_type` enum('page','subcat','file') NOT NULL DEFAULT 'page',
  UNIQUE KEY `cl_from` (`cl_from`,`cl_to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zfgc_wikicategorylinks`
--

LOCK TABLES `zfgc_wikicategorylinks` WRITE;
/*!40000 ALTER TABLE `zfgc_wikicategorylinks` DISABLE KEYS */;
INSERT INTO `zfgc_wikicategorylinks` VALUES (44,'KOT_Items','','','2026-07-26 07:18:43','','page'),(44,'King_Of_Thieves','','','2026-07-26 07:18:43','','page'),(84,'KOT_Locations',_binary 'HYRULE CASTLE','','2012-09-18 00:09:18',_binary 'uppercase','page'),(84,'King_Of_Thieves',_binary 'HYRULE CASTLE','','2012-09-17 23:43:16',_binary 'uppercase','page'),(91,'KOT_Locations',_binary 'ZORA\'S DOMAIN','','2012-09-18 00:00:20',_binary 'uppercase','page'),(91,'King_Of_Thieves',_binary 'ZORA\'S DOMAIN','','2012-09-17 23:58:55',_binary 'uppercase','page'),(117,'KOT_NPCs',_binary 'KOT ENEMIES','','2012-09-18 20:58:53',_binary 'uppercase','subcat'),(117,'King_Of_Thieves',_binary 'KOT ENEMIES','','2012-09-18 20:58:53',_binary 'uppercase','subcat'),(290,'ZFGC_Projects','','','2026-07-26 07:18:43','','page'),(367,'ZFGC_Projects','','','2026-07-26 07:18:43','','page');
/*!40000 ALTER TABLE `zfgc_wikicategorylinks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zfgc_wikipage`
--

DROP TABLE IF EXISTS `zfgc_wikipage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zfgc_wikipage` (
  `page_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `page_namespace` int(11) NOT NULL,
  `page_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `page_restrictions` tinyblob NOT NULL,
  `page_is_redirect` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `page_is_new` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `page_random` double unsigned NOT NULL,
  `page_touched` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `page_latest` int(10) unsigned NOT NULL,
  `page_len` int(10) unsigned NOT NULL,
  `page_content_model` varbinary(32) DEFAULT NULL,
  `page_links_updated` varbinary(14) DEFAULT NULL,
  `page_lang` varbinary(35) DEFAULT NULL,
  PRIMARY KEY (`page_id`),
  UNIQUE KEY `name_title` (`page_namespace`,`page_title`),
  KEY `page_random` (`page_random`),
  KEY `page_len` (`page_len`),
  KEY `page_redirect_namespace_len` (`page_is_redirect`,`page_namespace`,`page_len`)
) ENGINE=InnoDB AUTO_INCREMENT=974 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zfgc_wikipage`
--

LOCK TABLES `zfgc_wikipage` WRITE;
/*!40000 ALTER TABLE `zfgc_wikipage` DISABLE KEYS */;
INSERT INTO `zfgc_wikipage` VALUES (1,0,'Main_Page','',0,0,0.157671252285,_binary '20150401024610',2314,5139,NULL,NULL,NULL),(44,100,'Master_Sword','',0,0,0.923096591485,_binary '20241212161000',1983,425,NULL,NULL,NULL),(84,100,'Hyrule_Castle','',0,0,0.831383912903,_binary '20120923010434',651,158,NULL,NULL,NULL),(91,100,'Zora\'s_Domain','',0,0,0.511133556178,_binary '20120923010354',648,218,NULL,NULL,NULL),(117,14,'KOT_Enemies','',0,1,0.728065511807,_binary '20121029012150',305,138,NULL,NULL,NULL),(236,8,'Sidebar','',0,0,0.564775749879,_binary '20120926213247',814,475,NULL,NULL,NULL),(266,0,'TC:Chapters','',0,0,0.372420587269,_binary '20121114193640',907,200,NULL,NULL,NULL),(290,0,'Ocarina_of_Time','',0,0,0.817255273102,_binary '20260612201550',2912,7526,NULL,NULL,NULL),(367,0,'Ocarina_of_Time_3D','',0,0,0.286609593393,_binary '20251010170723',2581,15711,NULL,NULL,NULL),(490,0,'Wind_Fish_Society','',0,1,0.132512646582,_binary '20140324004944',1521,386,NULL,NULL,NULL),(900,0,'Broken_Image_Test','',0,0,0.5,_binary '20140101000000',3000,107,NULL,NULL,NULL),(950,10,'tasks','',0,0,0.5,_binary '20140101000000',3100,199,NULL,NULL,NULL),(951,10,'FeaturedProject','',0,0,0.5,_binary '20140101000000',3101,133,NULL,NULL,NULL),(952,10,'KOT:News','',0,0,0.5,_binary '20140101000000',3102,99,NULL,NULL,NULL),(960,6,'KoT_Master_Sword.jpg','',0,0,0.113355,_binary '20241212161000',3111,123,NULL,NULL,NULL),(961,6,'Lost_Screenshot.png','',0,0,0.224466,_binary '20241212161000',3112,53,NULL,NULL,NULL),(970,0,'Thestig','',0,0,0.606,_binary '20130424142508',3300,680,NULL,NULL,NULL),(971,0,'mgzero','',0,0,0.222,_binary '20150620120000',3301,170,NULL,NULL,NULL),(973,3,'mgzero','',0,0,0.333,_binary '20150701120000',3303,8,NULL,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (38,100,'Gerudo_Sword','',0,0,0.630726217751,_binary '20130227175302',1006,444,NULL,NULL,NULL),(43,100,'Noble_Sword','',0,0,0.664999546122,_binary '20120923020108',674,424,NULL,NULL,NULL),(48,0,'King_Of_Thieves','',0,0,0.961558810805,_binary '20141210211006',1972,835,NULL,NULL,NULL),(67,100,'Flippers','',0,0,0.425172832249,_binary '20120923015351',664,573,NULL,NULL,NULL),(70,100,'Magnetic_gloves','',0,0,0.095050345047,_binary '20120923015911',669,933,NULL,NULL,NULL),(86,100,'Lake_Hylia','',0,0,0.3578666049,_binary '20120923010248',645,205,NULL,NULL,NULL),(121,100,'Iron_Knuckle','',0,0,0.67352622775,_binary '20121029012158',868,1991,NULL,NULL,NULL),(122,100,'Armored_Crab','',0,0,0.299268236839,_binary '20120922053320',512,2268,NULL,NULL,NULL),(129,100,'Darknut','',0,0,0.824137717349,_binary '20120922204340',552,1674,NULL,NULL,NULL),(132,100,'Keese','',0,0,0.851959415816,_binary '20120922230158',568,946,NULL,NULL,NULL),(138,100,'Octoroc','',0,0,0.267511113476,_binary '20120923000216',576,1261,NULL,NULL,NULL),(143,100,'Mad_scrub','',0,0,0.731480820678,_binary '20120922235055',571,2222,NULL,NULL,NULL),(146,100,'Blue_ChuChu','',0,0,0.364967360783,_binary '20120922053519',514,642,NULL,NULL,NULL),(152,100,'Moldorm','',0,0,0.813102380728,_binary '20120922235905',574,331,NULL,NULL,NULL),(163,100,'Wizzrobe','',0,0,0.823145986599,_binary '20130227174147',1001,2082,NULL,NULL,NULL),(170,0,'ZFGC','',1,1,0.904757855684,_binary '20150215065700',395,36,NULL,NULL,NULL),(180,12,'Contents','',0,0,0.447603365387,_binary '20121014022856',837,7175,NULL,NULL,NULL),(182,12,'Tables','',0,1,0.101108987801,_binary '20120923085251',698,2664,NULL,NULL,NULL),(191,4,'Community_portal','',0,0,0.924937984355,_binary '20130424143059',1030,34,NULL,NULL,NULL),(192,0,'Pingas','',0,1,0.534482741652,_binary '20130424142920',439,22,NULL,NULL,NULL),(198,100,'News','',0,0,0.593388878798,_binary '20130425031959',1038,634,NULL,NULL,NULL),(199,100,'Team','',0,0,0.808064365576,_binary '20120922041020',486,115,NULL,NULL,NULL),(205,12,'Rules','',0,0,0.277283761232,_binary '20120925111118',697,2393,NULL,NULL,NULL),(206,100,'Rope','',0,0,0.403598446433,_binary '20150203185122',2185,1172,NULL,NULL,NULL),(213,100,'Magnetic_Gloves','',1,1,0.328166565793,_binary '20120923015912',670,33,NULL,NULL,NULL),(214,100,'Developers_Bible','',0,0,0.899416751773,_binary '20130424142920',815,897,NULL,NULL,NULL),(225,12,'CustomTemplates','',0,0,0.541288241925,_binary '20131130174034',775,3507,NULL,NULL,NULL),(226,0,'Zelda_Fan_Game_Central','',0,0,0.644894453621,_binary '20150215065659',2199,38936,NULL,NULL,NULL),(383,100,'Lake_Hylia_Temple','',0,0,0.656813399984,_binary '20131130174151',1222,1040,NULL,NULL,NULL);
/*!40000 ALTER TABLE `zfgc_wikipage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zfgc_wikirevision`
--

DROP TABLE IF EXISTS `zfgc_wikirevision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zfgc_wikirevision` (
  `rev_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rev_page` int(10) unsigned NOT NULL,
  `rev_text_id` int(10) unsigned NOT NULL,
  `rev_comment` varbinary(767) NOT NULL,
  `rev_user` int(10) unsigned NOT NULL DEFAULT '0',
  `rev_user_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `rev_timestamp` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `rev_minor_edit` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rev_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `rev_len` int(10) unsigned DEFAULT NULL,
  `rev_parent_id` int(10) unsigned DEFAULT NULL,
  `rev_sha1` varbinary(32) NOT NULL DEFAULT '',
  `rev_content_format` varbinary(64) DEFAULT NULL,
  `rev_content_model` varbinary(32) DEFAULT NULL,
  PRIMARY KEY (`rev_id`),
  UNIQUE KEY `rev_page_id` (`rev_page`,`rev_id`),
  KEY `rev_timestamp` (`rev_timestamp`),
  KEY `page_timestamp` (`rev_page`,`rev_timestamp`),
  KEY `user_timestamp` (`rev_user`,`rev_timestamp`),
  KEY `usertext_timestamp` (`rev_user_text`,`rev_timestamp`),
  KEY `page_user_timestamp` (`rev_page`,`rev_user`,`rev_timestamp`)
) ENGINE=InnoDB AUTO_INCREMENT=3302 DEFAULT CHARSET=utf8 MAX_ROWS=10000000 AVG_ROW_LENGTH=1024;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zfgc_wikirevision`
--

LOCK TABLES `zfgc_wikirevision` WRITE;
/*!40000 ALTER TABLE `zfgc_wikirevision` DISABLE KEYS */;
INSERT INTO `zfgc_wikirevision` VALUES (305,117,244,_binary 'Created page with \"This category groups together the single enemy NPCs that exist in [[King Of Thieves]].  [[Category:King Of Thieves]] [[Category:KOT NPCs]]\"',8,'MidnightMoblin',_binary '20120918165853',0,0,138,0,_binary '8bymz8xmyryz4piinpy2nu85hd6cdpc',NULL,NULL),(648,91,579,'',7,'EponaRider',_binary '20120923010354',0,0,218,225,_binary 'tahg1r40mg031dj5wul3py9kgbkpx7b',NULL,NULL),(651,84,582,'',7,'EponaRider',_binary '20120923010434',0,0,158,252,_binary 's3i73chayw8xyo5xpoifjmihhxl5bjy',NULL,NULL),(814,236,722,'',17,'TingleFan',_binary '20120926213247',0,0,475,813,_binary 'iq8nguf5992jz2jfji2h6xupgmlm0ex',NULL,NULL),(873,44,900,_binary 'Added damage numbers',77,'SheikahSlate',_binary '20230305121500',0,0,134,0,_binary 'dummysha1mastersword',NULL,NULL),(907,266,812,'',66,'GoronBros',_binary '20121114183717',0,0,200,906,_binary 'rg9nx8qmeeakdwx02lahaosev4zk0j0',NULL,NULL),(1521,490,1421,_binary 'Created page with \"\'\'\'Leader\'\'\' - Alice Leontus  \'\'\'Group\'\'\' - Warriors  \'\'\'Original(Other) Name\'\'\' - Frozen Fire Clan  A group of warriors, who had lost a member of it\'s own due to a successful...\"',53,'WolfosGray',_binary '20140324004944',0,0,386,0,_binary '2iaaetgmy9d2xgkxw2my6r5js94upqw',NULL,NULL),(1983,44,1882,'',8,'MidnightMoblin',_binary '20241212161000',0,0,425,873,_binary '8whfxwxvznkyoqfifjm239pe7md7bbi',NULL,NULL),(2314,1,2210,'',8,'MidnightMoblin',_binary '20150401024610',0,0,5139,2047,_binary 'p1wdl1i1vd43r8ysy98ximrhp17pnzp',NULL,NULL),(2581,367,2464,'',289,'ZoraZora',_binary '20251010170723',0,0,15711,2580,_binary '5m9qallufnfcez6285vmspbujrxjyst',NULL,NULL),(2912,290,2789,_binary '/* Latest demo */',42,'KokiriKid',_binary '20260612201550',0,0,7526,975,_binary '8mjcqt2m7ip0ymp4w3xlo5z4xg09jl2',NULL,NULL),(3000,900,3001,_binary 'fixture: intentionally missing image',7,'EponaRider',_binary '20140101000000',0,0,107,0,_binary 'brokenimagefixture00000000000z',NULL,NULL),(3100,950,3200,'',8,'MidnightMoblin',_binary '20140101000000',0,0,199,0,_binary 'devfixturetaskstemplate000000z',NULL,NULL),(3101,951,3201,'',8,'MidnightMoblin',_binary '20140101000000',0,0,133,0,_binary 'devfixturefeaturedproject00000z',NULL,NULL),(3102,952,3202,'',8,'MidnightMoblin',_binary '20140101000000',0,0,99,0,_binary 'devfixturekotnewstemplate0000z',NULL,NULL),(3110,960,3210,_binary 'maximus eu molestie',8,'MidnightMoblin',_binary '20230405120000',0,0,62,0,_binary '0000000000000000000000000000000',NULL,NULL),(3111,960,3211,_binary 'congue ante id',77,'SheikahSlate',_binary '20241212161000',0,0,123,3110,_binary '0000000000000000000000000000000',NULL,NULL),(3112,961,3212,'',17,'TingleFan',_binary '20250301090000',0,0,53,0,_binary '0000000000000000000000000000000',NULL,NULL),(3300,970,3400,'',98,'LorentzChronon',_binary '20130424142508',0,0,680,0,_binary 'thestiguserpagefixture123',NULL,NULL),(3301,971,3401,'',2,'mgzero',_binary '20150620120000',0,0,170,0,_binary 'mgzerouserpagefixture123',NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (395,170,333,_binary 'Rafa moved page [[ZFGC]] to [[Zelda Fan Game Central]]',17,'Rafa',_binary '20120921201124',0,0,36,0,_binary 'dncw4ly8tzspf3uf399z9fdpglzvhat',NULL,NULL),(439,192,375,_binary 'Created page with "{{stub}} Who is Katie?"',7,'Thestig',_binary '20120922022622',0,0,22,0,_binary '4wycmndo7hekemvxg5w1tl2otrcm7gf',NULL,NULL),(486,199,421,_binary '',7,'Thestig',_binary '20120922041020',0,0,115,485,_binary 'dhjs9u8cr3ps4pelrngs849jf3bwa69',NULL,NULL),(512,122,445,_binary '',7,'Thestig',_binary '20120922053320',0,0,2268,510,_binary '0zkc30y94x9n4142ofh8avkndt5ldcd',NULL,NULL),(514,146,447,_binary '',7,'Thestig',_binary '20120922053519',0,0,642,349,_binary 'ryxs9w1fwzthvzbronguq8slqwhlppv',NULL,NULL),(552,129,483,_binary '',7,'Thestig',_binary '20120922204340',0,0,1674,327,_binary '6h9dvbqkgbmr5b1f56bds5vb03fzywe',NULL,NULL),(568,132,499,_binary '',7,'Thestig',_binary '20120922230158',0,0,946,333,_binary '33h04m8rc6yehq08xmsk14kzjfxi35o',NULL,NULL),(571,143,502,_binary '',7,'Thestig',_binary '20120922235055',0,0,2222,346,_binary '5iwx036xzft6zzrpew2q348ejsq377d',NULL,NULL),(574,152,505,_binary '',7,'Thestig',_binary '20120922235905',0,0,331,355,_binary 'nuu7obi35m93wqdk93c61fckr67z63i',NULL,NULL),(576,138,507,_binary '',7,'Thestig',_binary '20120923000216',0,0,1261,341,_binary '5d5c4olugp5py8l2ccxnzlqrhfh1w28',NULL,NULL),(645,86,576,_binary '',7,'Thestig',_binary '20120923010248',0,0,205,258,_binary '9umarkgpc0oejyqlfa4ec0e47a53veh',NULL,NULL),(664,67,595,_binary '',17,'Rafa',_binary '20120923015351',0,0,573,606,_binary '26v9l1037erd6460f22y9ik03w3o64j',NULL,NULL),(669,70,599,_binary 'Rafa moved page [[KOT:Magnetic Gloves]] to [[KOT:Magnetic gloves]]',17,'Rafa',_binary '20120923015911',1,0,933,668,_binary 'hwzru6jjxgsj5tj6nlhxp6mdicrc9r7',NULL,NULL),(670,213,600,_binary 'Rafa moved page [[KOT:Magnetic Gloves]] to [[KOT:Magnetic gloves]]',17,'Rafa',_binary '20120923015912',0,0,33,0,_binary 'da50qlbohcbg4v7udlnecgzr9hl4crc',NULL,NULL),(674,43,604,_binary '',17,'Rafa',_binary '20120923020108',0,0,424,614,_binary 'ru98qs0ffrd1hyzrorku3qh7kjwcz07',NULL,NULL),(697,205,476,_binary 'Protected "[[Help:Rules]]": High traffic page (‎[edit=sysop] (indefinite) ‎[move=sysop] (indefinite)) [cascading]',7,'Thestig',_binary '20120923085212',1,0,2393,544,_binary 'qkzn2d8z6szlcv3a45p1x8o1e8hh61k',NULL,NULL),(698,182,359,_binary 'Protected "[[Help:Tables]]" (‎[edit=sysop] (indefinite) ‎[move=sysop] (indefinite))',7,'Thestig',_binary '20120923085251',1,0,2664,423,_binary 'dc2a185ttnc6rhgm8ixn4kczcbwrjqh',NULL,NULL),(775,225,685,_binary '',7,'Thestig',_binary '20120925173020',0,0,3507,774,_binary 's4796n9ht80fepofdw88elsilavn71c',NULL,NULL),(815,214,723,_binary '',17,'Rafa',_binary '20120926213355',0,0,897,686,_binary 'aazxx1z7g6mhft3pycb6cuirlgazzi1',NULL,NULL),(837,180,744,_binary '/* Cache Problem - Ctrl-F5 is another way to purge the cache*/',58,'Miles07',_binary '20121014022856',1,0,7175,770,_binary '7d6okzbjkpcdxa6853jgkydo1m5lzqn',NULL,NULL),(868,121,775,_binary '',8,'MasterGohan Zero',_binary '20121029012158',0,0,1991,867,_binary 'laufcusi2r0jfbcyenfocaj4tllaff1',NULL,NULL),(1001,163,904,_binary '',8,'MasterGohan Zero',_binary '20130227174147',0,0,2082,1000,_binary '50twv3re4qs1aowr6ja2xaoh8jen5ar',NULL,NULL),(1006,38,909,_binary '',8,'MasterGohan Zero',_binary '20130227175302',0,0,444,1005,_binary 'gv1jgru5azmgsxqdexkqt2vkmhb801k',NULL,NULL),(1030,191,933,_binary '',98,'LorentzChronon',_binary '20130424143059',0,0,34,436,_binary 'nxq4b3j4mdegar1vshg64epukhmxxgc',NULL,NULL),(1038,198,941,_binary '',98,'LorentzChronon',_binary '20130425031959',0,0,634,768,_binary 'dw5muggqrh383ki0bykjgffaz14pezy',NULL,NULL),(1222,383,1122,_binary '/* Enemies Encountered */',8,'MasterGohan Zero',_binary '20131130174151',0,0,1040,1221,_binary 'eu3ldpxxfndg0xej8s9wvc502tcsyxt',NULL,NULL),(1972,48,1871,_binary '',8,'MasterGohan Zero',_binary '20141210211006',0,0,835,1962,_binary 'fzwjgdcbmdfc8yfr9ajzw368mr2zn25',NULL,NULL),(2185,206,2081,_binary '/* Locations */',98,'LorentzChronon',_binary '20150203185122',0,0,1172,584,_binary '2qnct3j9l36exbhvk5oaoxfnlaf0rik',NULL,NULL),(2199,226,2095,_binary 'Ported from uncyclopedia.',162,'AJAX',_binary '20150215065659',0,0,38936,1028,_binary 'njzvrigelavtgkmk1n8t5e7qkajozfo',NULL,NULL);
/*!40000 ALTER TABLE `zfgc_wikirevision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zfgc_wikitext`
--

DROP TABLE IF EXISTS `zfgc_wikitext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zfgc_wikitext` (
  `old_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `old_text` mediumblob NOT NULL,
  `old_flags` tinyblob NOT NULL,
  PRIMARY KEY (`old_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3404 DEFAULT CHARSET=utf8 MAX_ROWS=10000000 AVG_ROW_LENGTH=10240;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zfgc_wikitext`
--

LOCK TABLES `zfgc_wikitext` WRITE;
/*!40000 ALTER TABLE `zfgc_wikitext` DISABLE KEYS */;
INSERT INTO `zfgc_wikitext` VALUES (244,_binary 'This category groups together the single enemy NPCs that exist in [[King Of Thieves]].\n\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]',_binary 'utf-8'),(579,_binary 'Home of the Zoras and currently their only safe zone from the River Zolas. Only those of Zora descent or those with permission may enter.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Locations]]\n</noinclude>',_binary 'utf-8'),(582,_binary 'Home of the Royal Family of Hyrule. Its splendid beuty is a wonder to behold.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Locations]]\n</noinclude>',_binary 'utf-8'),(722,_binary '* navigation\n** mainpage|mainpage-description\n** portal-url|portal\n** helppage|help\n\n* Content\n** recentchanges-url|recentchanges\n** randompage-url|randompage\n** :Category:Members|List of Members\n** :Category:ZFGC_Projects|List of Projects\n\n* ZFGC\n** http://zfgc.com/index.php/|Home\n** http://zfgc.com/index.php/projects/|Projects\n** http://zfgc.com/index.php/resources/|Resources\n** http://zfgc.com/forum/index.php|Community\n** http://zfgc.com/index.php/chat|Chat\n\n* TOOLBOX',_binary 'utf-8'),(812,_binary 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n==Chapters==\n*Chapter 1 - Lorem ipsum\n*Chapter 2 - Dolor sit amet\n*Chapter 3 - Consectetur\n*Chapter 4 - Adipiscing elit\n*Chapter 5 - Eiusmod tempor\n',_binary 'utf-8'),(900,_binary 'The \'\'\'Master Sword\'\'\' is found in the final dungeon. Capable of shooting beams.\n<noinclude>\n[[Category:King Of Thieves]]\n</noinclude>',_binary 'utf-8'),(1421,_binary 'The \'\'\'Wind Fish Society\'\'\' — Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\n==Activities==\n*Lorem ipsum dolor\n*Sit amet consectetur\n',_binary 'utf-8'),(1882,_binary '{{ItemInfobox\n|img1 = [[File:KoT Master Sword.jpg|200px|frameless]]\n}}\n{{ItemInfobox\n|title = Master Sword\n|type = Weapon (Sword)\n|obtained = Final dungeon\n|damagerate = 2x Noble Sword\n}}\nThe \'\'\'Master Sword\'\'\' is found in the final dungeon. Twice as much damage as the [[KOT:Noble Sword|Noble Sword]]. Capable of shooting beams and smashing rocks.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Items]]\n</noinclude>',_binary 'utf-8'),(2210,_binary '<!-- <div style=\"border: 1px solid #fcc; background: #fee; padding: 0.5em 1em 0.5em 1em; font-weight: bold; text-align: center; margin-bottom: 0.5em; border-radius: 4px;\">\nWelcome to the Zelda Fan Game Centrikia! [[Special:AllPages|{{NUMBEROFARTICLES}} articles]] and [[Special:Statistics|counting!]]\n</div>\n[[Brbiamfat]]\n-->\n<!-- Top bar -->\n{|style=\"width:100%;margin-top:+.7em;background-color:#e0e0ff;border:1px solid #ccc\"\n|style=\"width:56%;color:#000\"|\n{|style=\"width:280px;border:solid 0px;background:none\"\n|-\n|style=\"width:50%px;text-align:center;color:#000\"|<div style=\"font-size:162%;border:none;margin: 0;padding:.1em;color:#000\">Welcome to [[ZFGC|ZFGCpedia]]</div><div style=\"top:+0.2em;font-size: 95%;color:#000\">The Official ZFGC Wiki</div>\n<div id=\"articlecount\" style=\"width:100%;text-align:center;font-size:85%;color:#000\">There are currently [[Special:Statistics|{{NUMBEROFARTICLES}}]] articles in ZFGCpedia!</div>\n|}\n\n<!-- Categories -->\n|style=\"width:11%;font-size:95%;color:#000\"|\n*\'\'\'[[Help:Contents|Help]]\'\'\'\n*\'\'\'[[Help:Rules|Rules]]\'\'\'\n*\'\'\'[[Help:CustomTemplates|Tutorials]]\'\'\'\n|style=\"width:11%;font-size:95%;color:#000\"|\n*[http://zfgc.com ZFGC]\n*[[Special:ListUsers|User List]]\n*[[King Of Thieves|ZFGC Community Project]]\n|style=\"width:11%;font-size:95%\"|\n*[[:Category:ZFGC Projects|ZFGC Projects]]\n*[[:Category:Video Games|Video Games]]\n*[[:Category:Online Games|Online Games]]\n|style=\"width:11%;font-size:95%\"|\n|}<!-- end of cats! -->\n__NOTOC__ <!-- Removes Table of Contents -->\n<!---------- Left box base -->\n{| style=\"border-spacing:8px; margin:0px;\"\n| style=\"width:50%; border:1px solid #cccccc; vertical-align:top; background-color:#e0e0ff;\" |\n\n<!------------ Left box -->\n{| width=\"100%\" cellpadding=\"2\" cellspacing=\"5\" style=\"vertical-align:top; background-color:#e0e0ff;\"\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">The Basics</h2>\n|-\n|\n\'\'\'ZFGCpedia is fully integrated with ZFGC\'\'\'\n* You must be logged into {{ZFGC}} to edit ZFGCpedia, however non-ZFGC Members can view the wiki.\n* Your {{ZFGC}} account and Wiki account are linked. If you break the [[Help:Rules#Wiki_Rules|rules]] on [[ZFGC|ZFGCpedia]], you will be suspended or even banned from {{ZFGC}}. You have been warned.\n\n\'\'\'Being useful\'\'\'\n* Read the [[Help:Contents|Help]] and [[Help:Rules#Wiki_Rules|Rules]] sections to learn the How\'s, Do\'s, and Don\'t\'s of ZFGCpedia editing.\n* Help expand and improve ZFGCpedia! Create relevant pages, or improve [[:Category:Stub|stub]] articles. Know a lot about [[pingas]]? Make some edits!\n* Format your articles. ZFGCpedia is more than just a text dump!\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">The Community Portal</h2>\n|-\n|\nIf you have a comment or suggestion, make yourself known at the [[ZFGCpedia:Community_portal|Community Portal]]!\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Useful Templates</h2>\n|-\n|\n\'\'\'Maintenance\'\'\'\n* {{tl|stub}} - Add this to articles that are too short\n* {{tl|VideoGame}} - Add this to articles about video games which {{ZFGC}} users play but do not have online multiplayer\n* {{tl|OnlineGame}} - Add this to articles about games which {{ZFGC}} users play online\n* {{tl|ZFGCProject}} - Add this to articles about games which are either developed or published by members of {{ZFGC}}\n* {{tl|UserProfile}} - Add this to link to a {{ZFGC}} user\'s article\n\n\'\'\'Other\'\'\'\n* We.. should have a public discussion about this. Also [[gm112]] is a lazy guy.\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Helping out</h2>\n|-\n|{{tasks}}\n\n\n|}<!--\n\n\n---------- Right box -->\n| style=\"width:50%; border:1px solid #cccccc; vertical-align:top; background-color:#e0e0ff;\" |\n{| width=\"100%\" cellpadding=\"2\" cellspacing=\"5\" style=\"vertical-align:top; background-color:#e0e0ff;\"\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">ZFGCCP: King Of Thieves News</h2>\n|-\n|\n{{KOT:News}}\n\n<!--|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Featured User: None</h2>\n|-\n|\n{{FeaturedUser}}\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Featured Article: None</h2>\n|-\n|\n{{FeaturedArticle}}-->\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Featured Project: [[Ocarina of Time]]</h2>\n|-\n|\n{{FeaturedProject}}\n\n|}\n|}',_binary 'utf-8'),(2464,_binary '{{ZFGCProject}}\n{{Game\n|title=[[File:OoT3D_Boxart.png|400px]]\nOcarina of Time 3D\n|genre=Remaster\n|developer=Ocarina of Time 3D Team\n|publisher=WindfishDreamer\n|platform=Windows\n }}\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit.\n\n[[File:Ocarina3D_Screenshot.jpg]]\n\n<noinclude>\n[[Category:Featured Project]]\n</noinclude>\n',_binary 'utf-8'),(2789,_binary '{{Game\n|title=Ocarina of Time\n|genre=Action-adventure\n|developer=KokiriKid\n|platform=Windows\n }}\n[[File:OoT_Boxart.jpg]]\n\n\'\'\'Ocarina of Time\'\'\' — Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n\n==ZFGC Staff Commentary==\nLorem ipsum dolor sit amet, consectetur adipiscing elit.\n\n==Development==\nDemo builds are posted to the [http://zfgc.com/forum/index.php?topic=9.0 forum thread] as milestones are reached.',_binary 'utf-8'),(3001,_binary 'This fixture page intentionally references a missing image for tests: [[File:This_Image_Does_Not_Exist.png]]',_binary 'utf-8'),(3200,_binary '* Create pages for the games ZFGC members are playing.\n* Expand short articles with more detail and screenshots.\n* Add info and downloads to project pages.\n* Fix broken links and clean up formatting.',_binary 'utf-8'),(3201,_binary '\'\'\'Ocarina of Time\'\'\' is the featured community project. Visit its project page for the latest screenshots, downloads, and progress updates.',_binary 'utf-8'),(3202,_binary '\'\'\'King of Thieves\'\'\' is the ZFGC community project. No news updates at this time - check back soon!',_binary 'utf-8'),(3210,_binary 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n[[Category:KOT Images]]',_binary 'utf-8'),(3211,_binary 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. [[KOT:Master Sword|Master Sword]] Mauris ac quam blandit, tempor tellus ut, hendrerit justo.\n[[Category:KOT Images]]',_binary 'utf-8'),(3212,_binary 'Curabitur tempus placerat tortor, vel convallis enim mollis vitae. Suspendisse eget ligula vehicula, congue ante id, dictum nisi.',_binary 'utf-8'),(3400,_binary '{{UserProfile|userid=3}}\n\nSeriously who is this guy?\n== History ==\nlol. gm112.\n\nAlso below is some random stuff that people put on my profile. ;p --[[User:Thestig|gm112]] ([[User talk:Thestig|gm112]]) 18:48, 30 September 2012 (EDT)\n\n== Butts ==\nI have to admit, I really enjoy butts. Here\'s an entire section devoted to my love to butts. It all started when I was in grade 4 and I saw my teacher\'s butt by accident when he was bending down in front of the class during Math. Since I don\'t really like Math, I decided to look at his butt instead. Dat ass. That round butt in them tight jeans, just how I like it.\n\nWAIT A SECOND, WHAT AM I DOING ON GM112\'S PROFILE? -Steve Calandra.',_binary 'utf-8'),(3401,_binary '{{UserProfile|userid=2}}\n\n== About ==\nmgzero is a longtime member of [[ZFGC]]. This is a basic profile page.\n\n== Interests ==\nFan game development and community projects.',_binary 'utf-8'),(3403,_binary 'he rulez',_binary 'utf-8');
INSERT INTO `zfgc_wikitext` VALUES (333,_binary '#REDIRECT [[Zelda Fan Game Central]]',_binary 'utf-8'),(359,_binary '__FORCETOC__Tables are an amazing way to make tabular things.\n\n== The Basics ==\n\n{| border="1"\n|+ Table caption\n|-\n! Column Header 1\n! Column Header 2\n! Column Header 3\n|-\n! Row Header 1\n| Row 1, Cell 2\n| Row 1, Cell 3\n|-\n! Row Header 2\n| Row 2, Cell 2\n| Row 2, Cell 3\n|}\n\n* All table code begins and ends in curly braces with pipes\n\n \'\'\'<nowiki>{|</nowiki>\'\'\'\n    \'\'Insert Table Code\'\'\n \'\'\'|}\'\'\'\n\n* An optional table caption appears at the top of the table when added with a |+\n\n <nowiki>{|</nowiki>\n \'\'\'|+ Table Caption\'\'\'\n    \'\'More Table Code\'\'\n |}\n\n* Rows begin with |--- (Or however many dashes you want)\n\n <nowiki>{|</nowiki>\n |+ Table Caption\n \'\'\'|---\'\'\'\n    \'\'Cells Go Here\'\'\n \'\'\'|---\'\'\'\n    \'\'Cells Go Here Too\'\'\n |}\n\n* Cells begin with a single pipe on each new line\n\n <nowiki>{|</nowiki>\n |+ Table Caption\n |---\n \'\'\'|\'\'\' \'\'Cell 1\'\'\n \'\'\'|\'\'\' \'\'Cell 2\'\'\n |---\n \'\'\'|\'\'\' \'\'Cell 1\'\'\n \'\'\'|\'\'\' \'\'Cell 2\'\'\n |}\n\n* You could also just put them all on one line, with each cell separated by a double bar\n\n <nowiki>{|</nowiki>\n |+ Table Caption\n |---\n \'\'\'|\'\'\' \'\'Cell 1\'\' \'\'\'||\'\'\' \'\'Cell 2\'\'\n |---\n \'\'\'|\'\'\' \'\'Cell 1\'\' \'\'\'||\'\'\' \'\'Cell 2\'\'\n |}\n\n* Row and column headings are denoted by a ! instead of a |\n\n <nowiki>{|</nowiki>\n |+ Table Caption\n \'\'\'!\'\'\' \'\'Column Heading 1\'\'\n \'\'\'!\'\'\' \'\'Column Heading 2\'\'\n |---\n \'\'\'!\'\'\' \'\'Row Header\'\'\n | Cell 2\n |---\n | Cell 1\n | Cell 2\n |}\n\n* Optional parameters can affect individual tables, rows, or cells\n\n <nowiki>{|</nowiki> \'\'\'border="1"\'\'\'\n |+ Table Caption\n ! Column Heading 1\n ! Column Heading 2\n |--- \'\'\'style="align: right"\'\'\'\n ! Row Header\n | \'\'\'style="color: #9000A1; text-decoration: blink"\'\'\' | Cell 2\n |---\n | \'\'\'style="font-family: Courier New"\'\'\' | Cell 1 || \'\'\'style="background-color: red"\'\'\' | Cell 2\n |}\n\n* The final table looks like this:\n\n{| border="1"\n|+ Table Caption\n! Column Heading 1\n! Column Heading 2\n|--- align="right"\n! Row Header\n| style="color: #9000A1; text-decoration: blink" | Cell 2\n|---\n| style="font-family: Courier New" | Cell 1 || style="background-color: red" | Cell 2\n|}\n\n== Sortable Tables ==\n\nTo make your tables sortable, add <code>class="sortable"</code> at the top.\n\nExample:\n\n{| class="sortable" border="1"\n|+ Random stuff\n! User\n! Snipes Derped\n|---\n! gm112\n| 9001 (loool who dis)\n|---\n! Rafa\n| 6 (dis guy)\n|---\n! Star\n| 0 (THE TABLE)\n|--\n! MG-Zero\n| 5 (Derp king)\n|}\n\nTables sort based on what\'s in the first cell below the column header. This means that the sorting occasionally gets confused when you supply confusing inputs. Try clicking the sort button 4 times in the following example:\n\n{| class="sortable" border="1"\n|+ pingas\n! Column\n|---\n| 50\n|---\n| Z\n|---\n| A\n|--\n| 4\n|}',_binary 'utf-8'),(375,_binary '{{stub}}\nWho is Katie?',_binary 'utf-8'),(421,_binary 'Up to date list can be found [http://zfgc.com/mycat_isfat/index.php?title=Special:ListUsers&group=kot-access here].',_binary 'utf-8'),(445,_binary '===Location===\nAppear in watery areas such as Lake Hylia and its dungeon.  They are a semi-rare creature at the lake, and more common within the dungeon.\n\n===Description===\n====Appearance====\nA crab-like enemy that is a bit larger than an Octoroc.  It has 2 large pincers of equal size and 4 legs.  Its outer shell is spiky and brown, but its inner body is smooth and pink.\n\n====Behavior====\nArmored Crabs normally scuttle side to side within a small area, stopping for a second when reaching the end of their path.  When Link draws near, they will become much more aggressive, actively pursuing Link as long as he stays within their comfort zone.  Contact with one deals .5 HP and pushes Link back mildly, but it often leaves him within their range of attack.\n\nThe other ability Armored Crabs employ is to spit explosive rocks a short distance, which they do after pursuing Link for 4 seconds.  The crab pauses for a brief moment before spitting, giving Link a chance to evade or get out of the crab\'s range of attack.  These will explode after 5 seconds on their own, but Link may also carry and throw these rocks, in which case they will explode on impact.\n\nLike many armored enemies, Link has to first destroy the outer shell by using explosives, either with the Armored Crab\'s explosive rocks or with bombs.  It only takes a single explosion to destroy the shell, but it can be difficult to land a hit on one that is already chasing Link.  After getting rid of its shell, Link can defeat the Armored Crab with a single hit from any weapon.\n\nIf Link has the Roc\'s cape, he can use a down thrust to flip over a crab, stunning it for several seconds.  However, this only works if he lands next to it using the shockwave, as the hard shell will protect it from a direct attack.  When the shell is destroyed, both a direct hit and the shockwave will defeat the crab outright.\n\nArmored Crabs normally drop a small heart when defeated, but after Link has acquired bombs, they will always drop bombs if Link has not maximized his supply.\n\n===Attacks===\n====Explosive Rock====\nA longed ranged explosive rock that deals 1.5HP.  The explosions are the same size as a bomb\'s\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(447,_binary '===Location===\nLake Hylia Dungeon\n\n===Description===\n====Appearance====\nA blue living pile of Goop it has electricity coming out of it unless it\'s stunned.\n\n====Behavior====\nIf you touch it while it has electricity coming out you are electricuted.\nPlayer Combat Countermeasures:\n*Fire Arrow burns it, Ice Arrow Freezes it\n*Projectile weapons damage it as well.\n*Shield Bash, Boomerang, & Hookshot Stun it. \n**If it is stunned.\n***All short range items effect it.\n\n===Attacks===\n====Jump====\n1.5 HP It jumps at you. Electricity 1.5 HP damage.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(476,_binary 'We reserve the right to ban you at anytime should we have to. Get used to it.\n\n= Site Rules =\nThis is the {{ZFGC}} Code of Conduct. By posting on these forums, you agree to the following:\n\nYou will not...\n# Flaming - We do not tolerate abusive, malicious and/or personal attacks on any third party[1].\n# Trolls - Anyone deliberately antagonizing other forum users by posting \'flame bait\' type messages are not welcome.\n# Unsuitable Content - Distribution[2] of illegal, pornographic, copyrighted material or any other offensive material is strictly prohibited.\n# Impersonation of any user, forum moderator, administrator or third party is strictly prohibited.\n# Posting any content that contains a virus, Trojan or any other malicious content, even if done unknowingly, may result in a ban.\n# Posting any unsolicited messages: Advertisements, Chain Letters or similar content is prohibited and will result in an immediate ban.\n# Excessively use phrases, fads, memes or words will result in punishment, depending on use.\n# Messages containing "Spoilers" will result in punishment. Please use the "Spoiler" tag.\n# "Rent-a-Modding" is not allowed. Please use the "Report Post" function to notify moderators of a post or message.\n# Use common sense. If you think you might be breaking the rules, you probably are. Remember to ask if you are unsure.\n\nThese are more guidelines than rules, so just keep it clean, use common sense. {{ZFGC}} is supposed to be a friendly place, though [[Gm112]] and co. should probably get around to revising the rules. ololol\n\n= Wiki Rules =\n* The Wiki rules can be found [[Help:Contents#Rules|here]]\n\n= Punishments =\nFailure to follow the rules may result in:\n* Verbal warning\n* Suspension\n* Ban\n** Bans can include:\n*** Username Ban\n*** IP Ban\n*** E-Mail Ban\n* KOS (Kill on Sight)\n** KOS means that if you are spotted by an Admin or GMod, you will be promptly banned again\n*** All banned users are given KOS status for at least 60 days. Ban doesn\'t mean go and make a new account and troll some more.\n\nUse of proxies is prohibited. We will find you if you\'re using a proxy, and we will ban your proxy.\n\n= FAQ =\n* Why is Pikachu yellow?\n** Because you broke the rules\n* Who is [[pingas|Katie]]?\n** Katie is her.\n\n= Current Staff =\nAn up to date staff list can always be found [http://zfgc.com/forum/index.php?action=mlist;sort=id_group;start=0|here] under "Manager".',_binary 'utf-8'),(483,_binary '===Location===\nLake Hylia Temple, Goron Mines, Final Dungeon\n\n===Decription===\n====Appearance====\nA Heavily Armored Knight, it carries a sword and shield. Each Darknut is a swordmaster with decades of training before they are given objectives or tasks. Design from MC.\n\n====Behavior====\n-A Bomb Stuns Him.\n-Melee Weapons and Projectile weapons, except Bombs, can only affect the darknut by hitting it from behind or it\'s sides.\n-Fire Arrows and Ice Arrows don\'t need to hit it from behind in order to effect the Darknut.\n-Shield Bash is ineffective unless it\'s about to slash or stab you.\n-If you use your shield whaen it\'s about to hit you, you get pushed back 2 squares, and theres a 50% chance of it dropping it\'s weapon for to pick up in battle.\nExceptions\n-A rolling stab knocks it back, When he\'s about to attack you can use the pursuit slash to counter it. A downward thrust knocks his helmet off allowing for you to stun it with a boomerand, and it it allows you to shoot it w/ arrows.\n-A Boomerang stuns him.\n-Hookshot stuns him.\n-If you stab him from behind twice his armor falls off.\n-If you use the magnetic gloves when he\'s about to shield bash you, the darknut\'s shield is removed; dissabling it\'s shield bash attack.\n\n===Attacks===\n====Unarmed Attack====\n1.25 HP Must be Unarmed. It uses arm thrusts or punches you.\n====Weapon Attack====\n1.5 HP Requires Weapon. It stabs or slashes at link. The Darknut pauses for a moment after this attack.\n====Shield Bash====\n1.25 HP Requires shield. If you get to close when he\'s facing you he\'ll hit you with his shield.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(499,_binary '===Locations===\nCaves, dungeons and any dark place.\n\n===Description===\n====Appearance==== \nSmall black bat creatures as you have them in MC.\n\n====Behavior====\nThese are bat like creatures that like dark places. They are not particularly strong or though to defeat. Their strength lies in numbers as they there is always more than one Keese in a room.\n\nThey move around from one spot to the next in a bit of an irregular pattern. The never really fly in a straight line. Except when really close to the player, they will make a straight line towards him, before continuing to the destination spot. There they will sit still for a while and start the pattern again.\nDefeating: Just hit them with any weapon you have, even the boomerang kills them. \n\n===Attacks===\n====Bump====\n.25HP They move around in a bit of irregular pattern from spot to spot\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(502,_binary '===Location===\nDeku Swamp, Kokiri Forest, Forest Temple and a few in Hyrule field\n\n===Description===\n====Appearance====\nThey have a large orange and yellow leaf cap as if the fall is whithering them. In the leafs there are some spots and streaks of purple showing where the rot has reached the outside. Although they have yellowish mouth and eyes, the rest of the body has turn completely black and purple. The nuts they shoot are brown with a purple center.\n\n====Behavior====\nThese were once normal Deku Scrubs but they got in touch with a poison that was spilled in a part of their swamp. Instead of killing them the poison started to rot them from the inside out. It drove them mad and put the Deku\'s natural paranoia into overdrive. They no longer differentiate friends from foes and attack indiscriminately and with vigor. They hide in the ground waiting for others to approach before attacking.\n\nThey are hidden in the ground as long as the player is further than a maximum distance. Once the player is lower then the maximum distance and further then the minimum distance away, they come out of the ground and start shooting nuts (with timed breaks in between) at the player. Once the player is closer then the minimum distance they go back into hiding and they swing the Leaf Blade, which has the reach of the minimum distance and is further then a short range weapon. The madness makes them also immune to stunning. If the player creates a shadow clone within maximum range the scrub will be focused on that and will not dive back in the ground if the player reaches minimum range.\n\nThe player can defeat the Scrubs by deflecting their nuts back at them. Using a projectile weapon, because the minimum distance is further than a handheld weapon would reach. A handheld weapon can be used if the scrub is distracted by a shadow clone.\n\n===Attacks===\n====Nut Spit====\n.5HP on player, and 2HP on Mab Scrub spits out a nut at Link when he is in range (Damage .5HP on player, and 2HP on Mab Scrub).\n====Leaf Blade====\n1HP A razor sharp from the Mad Scrub\'s head makes a full circle motion cutting anything in its path\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(505,_binary '===Location===\nDungeons/Caves\n\n===Description===\n====Appearance====\nMC Design\n\n====Behavior====\nIt moves around the room randomly, and if it hits link, link takes damage and is knocked down.\n\n===Attacks===\n====Ram====\n.5 hp damage\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(507,_binary '===Location===\nAll along the river, Lake Hylia, Lake Hylia Temple\n\n===Description===\n====Appearance====\nPurple in color with a large spiral shell over their head.  They have 8 tentacles At the base of their body.  Their giant eyes allow them to have a large field of view, allowing them to see the player even from the side.\n\n====Behavior====\nThey like to sneak up on unsuspecting travelers and then blast them with rocks.  They usually just try to annoy the hell out of those passing by, but do get aggressive from time to time.\n\nAll projectile weapons are able to kill them.  The exception is the boomerang which stuns, and the ice arrow which freezes them into a block to use as as stepping stone.  Roc\'s Cape must be used to jump to it.  When the player approaches, they hide underwater.  This forces the player to have to use projectiles.\n\n===Attacks===\n====Rock Spit====\n.5HP to player, 1HP to self (2HP with properly timed bash) Spits out a rock that can be reflected with a shield bash.  The bash will reflect the same distance regardless of the timing.  However, a carefully timed bash will result in a faster reflection, dealing twice as much damage.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(576,_binary 'The hallowed grounds of the Zoras. Here the Lake Hylia Temple was built to worship their deities as well as house their cog.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Locations]]\n</noinclude>',_binary 'utf-8'),(595,_binary '{{ItemInfobox\n|title = Flippers\n|type = Equipment (Passive)\n|obtained = After Zora rescue\n}}\nGiven to you by a member of the Zora Royal family after you rescue him from the cave where they were stored.  You can swim and dive using these. Press the action button to dash, b button to dive. Diving in shallow water lets you dodge enemies. Diving in certain deep waters lets you enter another under water map.\n\nMost other items wouldn\'t work while diving; however, some would, such as the water cog.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Items]]\n</noinclude>',_binary 'utf-8'),(599,_binary '{{ItemInfobox\n|title = Magnetic gloves\n|type = Equipment (Active)\n|obtained = water dungeon\n}}\n[[KOT:Link|Link]] can use them to climb metal walls and attach himself to magnetic platforms and cranes, particularly in the Goron dungeon. Certain switches can also be toggled with the magnetic gloves.\n===Usage===\nPressing the button activates the magnetic gloves, one polarity at a time. Pressing the equivalent of the R trigger switches polarity instantly. The magnetic charge is active for as long as you hold down the button.\n===Effects===\nA strong magnetic pulse is sent out. Items of opposite polarity are attracted to it, and if they\'re mobile, they\'ll head toward Link. If they\'re stationary, Link will be dragged toward them. Items of opposite polarity will be repelled away from Link, or push him away, depending on whether or not they\'re stationary.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Items]]\n</noinclude>',_binary 'utf-8'),(600,_binary '#REDIRECT [[KOT:Magnetic gloves]]',_binary 'utf-8'),(604,_binary '{{ItemInfobox\n|title = Noble Sword\n|type = Weapon (Sword)\n|obtained = Side quest\n|damagerate = 2x Gerudo Sword\n}}\nThe \'\'\'Noble Sword\'\'\' is an optional blade Link can find through a side quest, available after the second dungeon. Does twice as much damage as the [[KOT:Gerudo Sword|Gerudo Sword]] and is capable of smashing pots and shooting beams.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Items]]\n</noinclude>',_binary 'utf-8'),(685,_binary 'Here on ZFGCpedia, we deploy use of some custom-written templates. This page is to provide documentation on those templates.\n\n\'\'A Note on templates: You do NOT need to satisfy each attribute in order for certain templates to draw. [[Help:CustomTemplates#Examples|Click here to see examples]]\'\'\n\n= Game Template =\n[[Template:Game]]\nThe game template is used to create a metadata info "badge" of sorts detailing information on the given title. You \'\'MUST\'\' fill in all of the attributes for this template to draw properly.\n\n== Syntax ==\nIn a wiki article, to employ the user of the game template, the following has to be entered:\n {{Game\n |title=\n |release=\n |genre=\n |developer=\n |publisher=\n |ESRB=\n |platform=<code><nowiki>\n }}</nowiki></code>\n*title - For specifying the title of the game. \nEx: |title=Legend of Zelda\n*release - For sepcifying the release date. Append a <code><nowiki><br /></nowiki></code> should you want to add a line break for additional release date information(this applies to any entry here).\nEx: |release=NA: July 1, 1999\n*genre - For specifying the genre of the title.\n*developer - Who are the developer(s) working on the game\n*ESRB - ESRB rating, if applicable.\n*platform - Platform(s) the title was published on.\n\n== Examples ==\n{{Game\n|title=Skyward Sword\n|release=EU Nov 18, 2011<br />\nNA Nov 20, 2011<br />\nJP Nov 23, 2011<br />\nAU Nov 24, 2011\n|genre=Action-Adventure\n|developer=Nintendo EAD<br />\nNintendo SPD<br />\nMonolith Soft\n|publisher=Nintendo\n|ESRB=E10+\n|platform=Wii\n}}\n <code><nowiki>\n {{</nowiki></code>Game\n |title=Skyward Sword\n |release=EU Nov 18, 2011<code><nowiki><br /></nowiki></code><br />\n NA Nov 20, 2011<code><nowiki><br /></nowiki></code><br />\n JP Nov 23, 2011<code><nowiki><br /></nowiki></code><br />\n AU Nov 24, 2011\n |genre=Action-Adventure\n |developer=Nintendo EAD<code><nowiki><br /></nowiki></code><br />Nintendo SPD<code><nowiki><br /></nowiki></code><br />Monolith Soft\n |publisher=Nintendo\n |ESRB=E10+\n |platform=Wii\n <code><nowiki>}}\n</nowiki>\n</code>\n\n= User Badge Templates =\nI will use my own user badge as an example here. Mediawiki gives each user a personal page associated with their user account. \n\n\'\'NOTE: You DO NOT need to satisfy each attribute for this template to draw properly.\'\'\n\n== Syntax ==\n <code><nowiki>{{</nowiki></code>UserProfile\n |avatar=\n |userid=\n |gender=\n |location=\n |notes=\n <code><nowiki>}}</nowiki></code>\n\n== Example ==\n{{UserProfile\n|avatar=gm112_avatar.png\n|userid=15168\n|gender=Male\n|location=Ohio\n|notes=gm112 smellz\n}} \n <code><nowiki>{{</nowiki></code>UserProfile\n |avatar=gm112_avatar.png\n |userid=15168\n |gender=Male\n |location=Ohio\n |notes=gm112 smellz\n <code><nowiki>}}</nowiki></code>\n\n= King Of Thieves Item Template =\nThis is the template for drawing ONLY ITEM information. Should there be content in King of Thieves using this template, it must be replaced with a proper template for said content.\n\n\'\'NOTE: Not ALL attributes have to be met for this template\'\'\n\n <code><nowiki>{{</nowiki></code>ItemInfoBox\n |title=\n |img1=\n |type=\n |location=\n |required=\n |cost=\n |damagerate=\n |ammo=\n <code><nowiki>}}</nowiki></code>\n\n== Examples ==\n{{ItemInfobox\n|title = Sea Cog\n|img1 = <!-- Insert some type of art here later on -->\n|obtained = [[KOT:Lake Hylia Temple|Lake Hylia Temple]]\n}}\n <code><nowiki>{{</nowiki></code>ItemInfobox\n |title = Sea Cog\n |img1 = <!-- Insert some type of art here later on -->\n |obtained = [[KOT:Lake Hylia Temple|Lake Hylia Temple]]\n <code><nowiki>}}</nowiki></code>',_binary 'utf-8'),(723,_binary '{{stub}}\n\'\'\'This document is incomplete. Stick to the forums for now.\'\'\'\n\nHey derp! Do you want to make a game? Not just any game, but a Zelda Fan Game with a bunch of other derps?! Well, you\'ve come to the right place. In this document, you will learn the basic rules and process behind what drives the [[King Of Thieves]] development process! \n\n\'\'NOTE:\'\' This document is only written enough to acknowledge the process and rules of the Plot / NPC portions of the Community Project!\n\n= Game Design =\nThere\'s a ton of detail behind Game Design. What you should expect to find under Game Design is:\n*Plot Development\n*NPC Design\n** Friendly / Neutral\n** Bosses\n** Enemy Units\n<s>World Design\nOverworld Locations\nDungeons\nInteriors\nBonus Content\nStub.</s>\n\nObviously there is a whole lot to what gets factored into just the game design portion alone! \n\n== NPC Design ==\n\n[[Category:King Of Thieves]]',_binary 'utf-8'),(744,_binary '= The Basics =\n* Directions on creating your user page can be found here: \n* If an article is too short, please add <nowiki>{{stub}}</nowiki> to the top of it\n* [http://en.wikipedia.org/wiki/Help:Contents Wikipedia\'s help files] are much more complete than these will ever be, and a lot of it should be applicable.\n* We have some tutorials too:\n** [[Help:Tables]]\n** [[Help:CustomTemplates]]\n** Also scroll down for some more.\n= Rules =\n# Don\'t be stupid\n\n== Vandalism ==\n# Vandalism will get you suspended or banned\n# This includes, but is definitely not limited to:\n## Deleting articles\n## Spamming up articles with nonsense\n## Blanking pages\n## Just use some common sense, its not that hard\n# Please report any vandalism to an Admin, Global Moderator, or [[Rafa]].\n\n== Content ==\n# Try to keep articles related to {{ZFGC}}\n# Joke articles are permitted, as long as they\'re funny\n# Go nuts\n\n== Pictures ==\n# Try to keep pictures somewhat Work Safe\n## Generally rules on the Wiki are similar to rules on the forum\n## Therefore, stuff that\'s obviously porn will probably be deleted. Unless its exceptionally hawt :)\n# If you upload a picture, its part of ZFGC Wikipedia\n## That means that when you upload a picture, its here for keeps, for better or worse\n## Don\'t upload a picture you don\'t want the rest of {{ZFGC}} to see\n## If its really necessary that a picture be deleted, contact an Admin, Global Moderator, or [[Rafa]].\n# If pictures aren\'t used in an article, they\'re going to be deleted\n\n== Community Pages and User Pages ==\n# There\'s a difference between the two\n## Community Pages are the pages searched for in the box\n### These usually contain relevant, somewhat objective information on a user\n## User Pages are the pages you get by clicking your name at the top of your monitor\n### These can contain whatever you want to put up on it, provided its within the bounds of the rules\n# DO NOT edit another user\'s User Page. We will suspend for this\n## Notable exception would be something like signing a guest book on their page\n# If your User Page is empty, you can have it redirect to your Community Page\n## This can be done by putting <code><nowiki>#REDIRECT [[pagename]]</nowiki></code> in the field for your User Page\n\n== Edit Wars ==\n# Are for the pricks on Wikipedia\n##If you get into an edit war with somebody, contact an Admin, Global Moderator, or [[Rafa]].\n\n= Help =\n\n== Creating an Article ==\nYou can do this in the following ways:\n* Type the name of the article into the search box, then press enter. The first line will read: \'\'\'There is no page titled "pagetitlenamehere".\'\'\' You can <font color=#ba0000>create this page.</font> Click create, and then start writing.\n* Type the article into the URL: <nowiki>http://wiki.zfgc.com/index.php/pagetitlenamehere</nowiki> If it doesn\'t exist, it\'ll let you create the article.\n\n== Redirecting ==\nTo redirect a page to another page:\n# Create the Page to redirect to the other page\n# Place <code><nowiki>#REDIRECT [[page]]</nowiki></code> as the content\n# If necessary, you can explain why it redirects on the page as well\n\n== Reverting a Page ==\nSometimes people can be poor sports. People like [[Pingas|some Pingas\'s]] come on {{ZFGC}} and decide to annoy everyone by deleting your page. No problem.\n# Click History\n# Click the date of the last good edit\n# You\'ll see the article as it was back then\n## Click edit\n## Click save\n# Leave a quick note in the summary box of what the edit was for, as a courtesy\n\n== Getting Local Time ==\nNobody actually goes by UTC time, so to convert times to local times:\n# Click preferences on the top right corner\n# Click the \'\'\'Date and Time\'\'\' tab\n# Click \'\'\'Fill in from browser.\'\'\'\n# Save and exit\n# All the times will now be in your local time zone\n\n== Images ==\nYou can embed .jpg and .png files\n# Upload the file: Click [[Special:Upload|Upload file]] on the toolbox, right under the search box\n# Link to the image using <code><nowiki>[[Image:file.jpg|left/right|thumb|alt text]]</nowiki></code>\n## Image:file.jpg tells the Wiki what file you\'re embedding into an article\n## Put in either left or right depending on where you want it positioned\n## Put in thumb so it\'ll give a thumbnail, Wikipedia style\n## Type in a caption where it says alt text\n# If you\'re adding many related images to one section, you may want to use the <nowiki><gallery></nowiki> tag\n\n== Categories ==\n\n=== Adding a Page to a Category ===\n<code><nowiki>[[Category:name]]</nowiki></code>\n\nTo choose the text and link to a category:\n<code><nowiki>[[Category:name|alternate text]]</nowiki></code>\n\n=== Linking to a Category ===\n<code><nowiki>[[:Category:name]]</nowiki></code> (Note: There is a colon before "Category".)\n\n== Lists and Indentation ==\n* For unordered lists like the ones on this page, simply put a <nowiki>*</nowiki> in front of each line\n# For ordered lists, use a <nowiki>#</nowiki> in front of each line\n## Put <nowiki>##</nowiki> to indent a level\n#* You can mix the two if you\'d like\n#*# As much as you want\n:For indentation like this, put a <nowiki>:</nowiki> in front of the line.\n::Multiple <nowiki>:\'</nowiki>s will indent things further\n:::<nowiki>...and further</nowiki>\n::::This is usually used when replying to posts on talk pages.\n\n== Tables ==\nTables are an easy way to organize tabular data. For more information, see [[Help:Tables]]\n\n== Cache Problem ==\nIf your most recent contribution isn\'t showing up, it\'s most likely caused by the cache on your browser. \n* Hit F5 to refresh the page. This works most of the time. If it doesn\'t, try Ctrl-F5 to purge the cache.\n* Alternatively, you can click [[Special:Preferences|here]] and go to "Misc. Settings" and then check mark the "Disable Page Caching" option\n\n== Talk Pages ==\n* Sign your comments on talk pages with <nowiki>~~~~</nowiki>.  Do not use these when making articles!\n* If you see someone writing comments on talk pages without signing them, use {{subst:[[Template:unsigned|unsigned]]|username}} to sign it for them.\n\n== Wiki Links ==\n* <code><nowiki>[[Main Page]]</nowiki></code> = [[Main Page]]\n* <code><nowiki>[[Main Page|Click Me!]]</nowiki></code> = [[Main Page|Click Me!]]\n* <code><nowiki>[[Help:Tables|]]</nowiki></code> = [[Help:Tables|Tables]] (Hides namespaces and things in parentheses)\n\n== External Links ==\n* <code><nowiki>http://www.google.com</nowiki></code> = http://www.google.com\n* <code><nowiki>[http://www.google.com]</nowiki></code> = [http://www.google.com]\n* <code><nowiki>[http://www.google.com Google]</nowiki></code> = [http://www.google.com Google]\n\n== User Profiles ==\n* Community Pages are public pages, User Pages are private pages. User Pages are where you can put whatever you want, within bounds of the rules.\n* For example, [[Gm112]] is gm112\'s Community Page, for the Community to edit as they see the user. [[User:Thestig]] is gm112\'s User Page, for him to put whatever useless garbage he wants to. Note: User: pages do not follow your \'\'DISPLAY NAME\'\' on {{ZFGC}}, but only your \'\'USERNAME\'\'.\n* The syntax to link to a Community Page is <code><nowiki>[[username]]</nowiki></code>\n* The syntax to link to a User Page is <code><nowiki>[[User:username]]</nowiki></code>',_binary 'utf-8'),(775,_binary '==Location==\nCelestial Clock, Lake Hylia Temple\n\n==Description==\n===Appearance===\nHeavy armored, slow moving brutes.  They wield an axe as big as they are.  They come in 2 flavors, one in silver armor and one in black.  The black armored ones appear later in the game and have an additional 5 HP.  The black variant carries a war hammer as opposed to an axe.\n\n===Behavior===\nAs they do in OoT, they move slowly towards the player.  Should you come in range of the axe, they\'ll take a swing at you.  They\'ll be wide open to a sword attack after swinging.  This will slowly tear apart their armor.  Additionally, the player can use the magnet gloves at a distance to take the armor apart (reverse the polarity to shoot it back for some humor) although this won\'t do any damage.  This means that the charge mode (see below) is activated by the loss of their armor, not by damage taken.\n\nOnce the armor is gone, they begin to charge at the player.  The Axe attack remains the same and they gain their lightning attack.  This can either be dodged or reflected back with the magnet gloves (but make sure you use the right polarity, or you\'ll just attract it to yourself! Polarity will always be consistent, north reflects south attracts).  This attack is used if the player gets too far away.\n\n==Attacks==\n===Axe Swing===\n4HP A powerful yet easy to dodge melee attack.  Can be shield bashed, but it will also knock Link back (no damage will be taken).  The player can use the pursuit slash to avoid it, however the armor protects their back from damage.\n\n===Ranged Lightning=== \nA long range lightning attack similar to Agahnim\'s attack in lttp.  This can be avoided either by dodging or by pushing it back with the magnet gloves.  Does 2HP to the player, 1HP to the Iron Knuckle if reflected.\n\n\n\nThe sword will be the only weapon that can damage them, however the Kokiri Cog can be used to slow them down when charging.\n\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]',_binary 'utf-8'),(904,_binary '{{ItemInfobox\n|img1 = [[File:KoTWizzrobe.png|200px|frameless]]\n|title = Wizzrobe\n}}\n===Locations===\nCaves, dungeons and dungeon like interiors. (starting from the Forest Temple) Also in places of the dead.\n\n===Description===\n\nThese are some weird witches that are made of residual magic from long ago. Wrapped in cloth and with pointed hats, they scare any passerby. These creatures can suddenly appear on your path.\n====Appearance====\nIt follows the MC design. Garbed in cloth and have a pointed hat. Their faces are mostly invisible except for two white blinking eyes. Their dark clawed hands are only visible when raised to the sky. This variation has green as its primary color.\n\n====Behavior====\nWhen there is only one it appears on either the vertical height or horizontal width at a random distance that Link is on. With multiple wizzrobes (variations) one or two will position themselves on the same horizontal width or vertical height as the player, while the rest take a strategically back up position.\nWhen they appear they shoot a magic projectile in the horizontal or vertical direction of Link (depending on his position). They linger for a while before they disappear again. They stay gone for a random (between 5- 10 second) amount of time.  Unlike the MC design they appear and disappear in a puff of smoke.\nThe more damage they receive shorter the time is that they linger before disappearing and shorter the time is that they appear again.\nDefeating: Weapons have their normal effect on wizzrobes. The ice and fire arrows kill these bastards with one hit. A shadow clone has only 50% chances of distracting them.\n\n===Attacks===\n====Bump====\n.25HP They appear in a place and stay there visible for a while. If Link is there when they appear or he just walks into them he receives damage.\n====Magic====\n.5HP A small wave of magic (projectile) shot in the direction of Link. The projectile size is about 24 pixels. And the projectiles are not bothered by obstacles.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(909,_binary '{{ItemInfobox\n|img1 = [[File:KoTGerudoSword.jpg|200px|frameless]]\n|title = Gerudo Sword\n|type = Weapon (Sword)\n|obtained = Starter weapon\n|damagerate = Some amount of HP (someone edit this with a real value)\n}}\nThe \'\'\'Gerudo Sword\'\'\' is a blade [[KOT:Link|Link]] is presented with before starting out on his quest. It can\'t shoot beams or smash through pots or rocks.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Items]]\n</noinclude>',_binary 'utf-8'),(933,_binary '\n== ZFGC Community Portal ==\n\nTODO',_binary 'utf-8'),(941,_binary 'The [[KOT:Team|King Of Thieves Team]] is now announcing the port of the [[King Of Thieves]] Design Document to ZFGCpedia!\n\n* Accepted entries now require a tag specifying wiki status\n** \'\'NODOC\'\' stands for NO DOCUMENTATION AVAIL, which just means we hadn\'t gotten around to porting given entry to the wiki.\n** \'\'INCOM\'\' stands for the existence of a wiki entry being present, but the wiki port isn\'t 100% finished.\n** \'\'COMPLETED\'\' is as what the name implies. Entry exists completely on the wiki.\n\n* New developer-geared document teaching users how to get involved. Check it out here! [[KOT:Developers Bible|Developer\'s Bible]]\netc.',_binary 'utf-8'),(1122,_binary '{{Infobox\n|bodystyle   = width:20em;\n|name        = Infobox/doc\n|title       = Lake Hylia Temple\n|labelstyle  = width:33%\n\n|header1 = \n|label1  = Location\n|data1   = [[KOT:Lake Hylia]]\n|header2 =\n|label2  = Item Found\n|data2   = [[KOT:Magnetic Gloves]]\n|header3 = \n|label3  = Item Needed\n|data3   = [[KOT:Flippers]]\n|header4 = \n|label4  = Category\n|data4   = Major Temple\n|header5 = \n|label5  = Mini-Boss\n|data5   = ???\n|header6 = \n|label6  = Boss\n|data6   = ???\n}}\n==Background==\nA place of worship for the Zora\'s and their most sacred grounds.  It has been recently commandeered by the River Zola\'s, causing a state of war between the two races.\n\n==Enemies Encountered==\n===[[KOT:Rope]]===\n===[[KOT:Wizzrobe]] (Standard, Ice)===\n===[[KOT:Darknut]]===\n===[[KOT:Iron Knuckle]]===\n===[[KOT:Octoroc]]===\n===[[KOT:Moldorm]]===\n===[[KOT:Blue ChuChu]]===\n===[[KOT:Armored Crab]]===\n===[[KOT:Keese]]===\n===[[KOT:Mad scrub]]===\n\n==Other NPCs Encountered==\n\n==Maps==\n\n<noinclude>[[Category:King Of Thieves]] [[Category:KOT Game Design]]</noinclude>',_binary 'utf-8'),(1871,_binary '{{ZFGCProject}}\n{{game\n|title=\n[[File:KotTitle.png]]\nThe Legend of Zelda: The King of Thieves\n| release=TBA\n| genre=Adventure\n| developer=ZFGC\n| ESRB=Everyone\n| platform=PC\n}}\n\n\'\'\'King Of Thieves\'\'\' is a [[ZFGC]] community project managed by some people. Someone more knowledgeable about this should finish writing this article.\n\nIt is highly recommended to find specific information through [[:Category:King Of Thieves|main category]] as the main category is the most streamlined and organized method of browsing the Design Document. However, you can on this link for a [{{canonicalurl:Special:AllPages|namespace=100}} list of KOT articles].\n\nFor access to the King Of Thieves news page, go to [[KOT:News]]. Any content on this page will show up automagically on the main page.\n\n[[Category:King Of Thieves]]\n[[Category:ZFGC Projects]]',_binary 'utf-8'),(2081,_binary '===Locations===\nCaves and dungeons ( the higher the dHP the higher the dungeon). Green ones can also be found outdoors in the field and yellow ones can be found in the desert.\n\n===Description===\n====Appearance====\nThey are your regular snake enemies as they appear in any 2D Zelda game and especially MC. The most basic rope is green of color with a red belly. There are three other colors, but they are essentially the same enemy just a bit more health: 1) Reb skin and blue belly (2x HP), 2) Blue skin and yellow belly (3x HP), and 3) Yellow skin and green belly (4x HP).\n\n====Behavior====\nThey move either horizontal or vertical around an area. They move at a normal pace, but when Link is in direct line of sight the rope will dash at double speed towards Link until it hits an obstacle or Link. Direct line of sight means on the same horizontal tile row or vertical tile column and the rope is facing towards Link.\n\nWeapons have their normal effect on ropes.\n\n===Attacks===\n====Bump====\nWhen Link physically hits these creatures he will sustain damage. (Damage 0.25)\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]\n[[Category:KOT Enemies]]\n</noinclude>',_binary 'utf-8'),(2095,_binary 'ZFGC a.k.a. Zordon\'s Funky Grass Cutters (formed May 17th, 2003), is a famous band consiting of over 500 people, and only about 100 or so actually appear at concerts. The most famous ones are known to kill the other ones for making a single mistake on their notes. Their most famous songs are "WTF I SUMMOND KUESHI INSTEAD OF CAPTAIN PLANET" and "DEMO 4 IS OUT". What exactly Demo 4 is, is never explained in the song, but we can guess it has something to do with becoming more than a walking engine. Whatever that means.\n[[Image:Popeeyebeams.jpg|thumb|(>^_(@_@)]]\n\n== The Members ==\n=== The Regular Members ===\n*\'\'\'Your Mom\'\'\' - One of the oldest, wisest of ZFGC, she fought in the great ZFGC war and she fu**ing owns Retro.\n*DYLAN623!!!\'\'\' - Jacks off to MC sprites, sucks at spriting and life. He also found new porn called "gay porno\'s" men on men stuff. Dylan soon went to jail just to get some.\n*\'\'\'Silverlink\'\'\' (moffermn) - Joined when zfgc was 2 weeks old. but got banned in the third week :p and unbanned the day after that. Was a mod at the Invisionfree forum.\n*\'\'\'Knives\'\'\' - Proud owner of... DJVENOM\'S PHONE NUMBER! *gasp* Doesn\'t have enough minutes.\n*\'\'\'InvaderLupus\'\'\' - He lives in Iowa. May God have mercy on his soul. Then again, he\'s the one who scared God away from the band, so he\'s screwed.\n*\'\'\'MaJoRa\'\'\' - Proud owner of the MaJoRa wOot Link song album, moderator and frequent visitor for Tubgirl.com\n*\'\'\'Pedlya\'\'\' - He is not da master king of hyrule, but a small furry animal, somewhat like a squirell. \n*\'\'\'halotank\'\'\' - Awesomeness getting hit by a car.\n*\'\'\'Michael Jackson\'\'\' - \'\'"Hello boys!"\'\'\n*\'\'\'Hero of Fire\'\'\' ZFGC\'s only source of heat, Hero of Fire is often known as "HoF". liek pie etc lolz\n*\'\'\'Moldorma\'\'\' - He was once a very good global mod but someone killed him. He\'s been rumored to get fairly far into development of millions of fangames and never finish a single one.\n*\'\'\'Regile\'\'\' - One of the oldest, wisest and meanest ZFGCers. Aka Count Chocula.\n*\'\'\'[[Penguin]]\'\'\' - penguin the squeeks at Pedlya\'s call.\n*\'\'\'Fox\'\'\': Was the first mascot for ZFGC and supposed to be on the album-cover. On a live-concert from  2004, some minors and their parents felt offended by his "Barrel Roll". He got sued and didn\'t made it on the cover. His current location is unknown. Fox has leet skills to making graphics. Him and his leet graphics pwn all of juu. Fox lost touch with pepper hair when andross stole peppys barrel roll line and put a copyright on it.\n*\'\'\'Hikij\'\'\' - Now a regular member, Hikij enjoys touching childrens ding-dongs in the bathroom stalls at Walmart. He gets reeeal good action next Friday\'s\'s\'ss.\n*\'\'\'Issac_Amisov\'\'\' - He used to be samware. Then he died, and he was ressurected as Issac_Amisov. Rumours are that he is the evil spawn of the flying spaghetti monster.\n*\'\'\'Source\'\'\' - Arguably the greatest user to ever join ZFGC, Source began as a humble newbie named Cronian. Finding the community to be somewhat immature, he left, only to get laid before returning as Source. He gained popularity due to his arrogance and complete disregard for the rules, which he was never punished for. He somehow amassed a following that has since moved on to less amazing things. During his tenure at ZFGC, he coined the concept of masculinity, which had been lost among the ZFGCers. So great was his manliness and so massive was his dick that he beat out resident forum viking Chesu during an impromptu contest of macho, thus allowing him to retain his title. Chesu then disappeared. Nowadays, Source is a high school teacher, a married man, and father-to-be.\n*\'\'\'EliteJason\'\'\' - Registered since 2004 and was on regulery, but other than participating in Gaveno\'s 0ot2d project as a level designer and posting a few threads and replies, was most likely lurking the rest of the time.\n\n=== The Legendary LUEnin ===\n\n*\'\'\'walnut100\'\'\' - Last seen in a pool in Utah. (Who the fuck goes there?) Rumored to wield the power of LUEshi and champion smiter of mormons. Medical ninja of the legendary LUEnin. Summons LUEshi and a walnut that can become 100 smaller walnuts.  walnut100 stands to win a 400 dollar shopping spree if he keeps playing the GameFAQs Best Series Ever poll right.  walnut100 is the only actual active LUEser of the LUEnin and therefore led many of their endeavors.  He is known for occaisonally employing the weapon known as Speedy_G, now codenamed Winnar.  He was last seen teaching a whiney kid to swim for a grand total of $15 (for the whole day).\n*\'\'\'DJvenom\'\'\' - Chef on the almighty pir8 ship Mayo, he is often seen with his silly blue hat and glasses(he doesnt even need them, he thinks they\'re sexy). Computer died and now uses is step brothers shitty laptop. Has been known to eat hairy gummy bears.  DJ enjoys his job at RiteAid and is sometimes known for his temper.  He is famous for his farting Behemoth sprite.  DJvenom pulled several large pranks with Mamjo including the fake OOT2D Demo.  After this he sang Gunic Heroes with Pyrazor and sprited some guns.  He was involved in many miscellaneous pranks and assisted wally and Pyra when needed.  DJ currently resides in the Pixeltendo forums where he produces sprites when he can with HUMOROUS avatars.\n*\'\'\'Mamoruanime\'\'\' - Codenamed \'\'\'"Mamjo"\'\'\', this woman is known for defiling the Dan Barraz way by releasing a fake version of the Album "Demo 4". This move sparked much controversy, as the only track on the record was a .midi (The hell is that?) version of Gunic Heroes, and a repeating track saying "Pawned by DJ and Mamjo". But other than that, shes a sweet lay, just put the bag over her face.  Mamo was often partnered with DJvenom, the sly devils.  He was last seen on ZFGC IRC chatting in a private channel with the other LUEnin.  Mamo left ZFGC long long ago and was eventually replaced by Pyrazor who also went AWOL (LOL Defense against the Dark Arts).  He is not an official LUEnin but totally could be if wanted to.\n*\'\'\'Kirby\'\'\' - Kirby first joined wally and Pyra\'s alliance when it was intended to repair and restore ZFGC.  After TRM set back nearly all they had worked for, Kirby continued to work and left the LUEnin.  Kirby participated in such events as the Starfox Spamathon playing the role of Slippy.  He is a whore of php an pcp and is actually kinda fat.  Though Kirby left, he never interfered with the other LUEnin and respected their abilities.  His whereabouts are often unknown to even the LUEnin though they still consider him fat.\n*\'\'\'Pyrazor\'\'\' - Though a self-proclaimed ass who viewed ZFGC as an "inferior forum" that acted rather n00bishly, Pyrazor is in fact a professional moderator at neoseeker.com (what shawk) and is extremely intelligent as he is attending Cornell University.  He was last seen asking CommanderStabby for a ban hammer image.  Pyra is often partnered with walnut in their idiotic plans to do stupid things that lack intelligence.  Their plans included Box3, changing wally\'s name to Artificer (an ex member) and Pyra\'s name to walnut100, the I AM A SUCK competition, and the great Star Fox spamathon.  He is probably the least liked LUEnin (and possibly member) because of his attitude held towards many of the ZFGCers.  The LUEnin agreed with some of his dislike of other members but did not publically display it due to their forum positions.  He is currently developing of an army of owls. h00t\n*\'\'\'Delko/Darklink\'\'\' - Captain of the Pirate ship Mayo. He pwns noobs with his Golden Axe that he got from Pascal by trading in a white scallop. He leads the PiR8z fearlessly on their voyage to rid the seas of fux0r-n00bs!\n\n=== Other Members ===\n****olo penis lol****\n*\'\'\'Q.K.\'\'\' - Apparently wrote some book while he was on an acid trip that had to do with a place called Binary Phoenix. The book then supposedly devoured some people. It was fun. Goes by his alias, Itoh.\n*\'\'\'Helios\'\'\' - He fell down a hole in the remote mountain regions of Iraq some time in 2003, some say he\'s been arrested and accused of being a facist dictator, some even say he\'s died and gone to a strange new world called Binary Phoenix. One thing is for sure, the band will never be the same again. That damn helios stole my cheerios and never returned them.\n*\'\'\'Americanto\'\'\' - By the way, hes gay. Very gay\n*\'\'\'Sol\'\'\' - Made the Frider Waves album with some other ex-band members. Kicks serious ass. Is also the sun, and lord of all things.  Tends to whine now and again without cheese.  Serious Business. <nowiki>*</nowiki>Mod Edit: This profile didn\'t include enough information. Please change that.\n*\'\'\'Lord Spaztic\'\'\' - retarded ass fag who cant sprite for shit. He can\'t spell and doesnt know how to even appear intelligent. he Capitalizes random Letters and puts apostro\'phes in word\'s that doe\'s not need thm\'e.\n*\'\'\'Ripped\'\'\' - The ultra cool member who should never been forgotten.\n*\'\'\'Regulus\'\'\' - A twit who lies abot his age and name to try and sound cool.\n*\'\'\'Zackdude23\'\'\' - The idiot who used the account "Acboy555" to harrass TRM and pissed everyone off. Also a rampant trouble maker, but damn does he rock. ACBOY?!! LIEK ACGUY! LOL ACGUY\n*\'\'\'Ishdarian\'\'\' - Thinks he is "1337", but it turns out he\'s just a poser. Frequently seen insulting/messing around with other band member\'s mothers. Biggest pet peev: LiNk1090. "He needs to die" (Quote from Ishdarian.)\n*\'\'\'abcgum09\'\'\' - The Best 3D modeler Ther ever was on zfgc. (\'\'\'SYKE\'\'\')\n*\'\'\'araknidkid\'\'\' - has herpes LOL\n*\'\'\'Streblo\'\'\' - Formerly known as Hylian Hero, not involved in many forum activities very often but he still been a ZFGCer since EZ boards.\n*\'\'\'cb43569\'\'\' - A bird-boy who used to think he could fly, and used to cry "Weena!" at regular intervals. Now is utterly depressed and likes to pee in the toilet. Also made ZFGC what it is today with his amazing 3D Ghoma engine for Blitz3D and a 32-day gameplay length cool MMORPG called Diputsosgniebrofpukcifaruoyknihti, but also called Diput for short. Also, none of the two things exist. Rofl, visit ZFGC and my forum, GMRealm (http://gmrealm.uni.cc).\n*\'\'\'Retro\'\'\' - Teh OWNZORS of all souls.  Damn.\n*\'\'\'Torchie\'\'\'* - \'\'\'THE BULLA\'S DRY NOW\'\'\'\n*\'\'\'Pyru\'\'\'* - lol, haxed? Very clearly NOT Pyro, Pyru undertook a seperation from the band, with a few others such as Helios and Indy Blue Vuples, releasing the \'Nexus Chat\' album. Not selling enough copies, it was back to zfgc from then on.  Called a knock-off by LUEnin Pyrazor for having a remotely similar name.\n*\'\'\'Shiro\'\'\'* - 1337 h4x0r \\|/h0 41\\|/4y$ t41k$ 1337 101zz111!1\n*\'\'\'MegaworM\'\'\' - Amazing small worm with a brain the size of a rock...a BIG rock :D. Nuf sed. Created the Potato. \'nuff said. Is a black man in disguise. Nuf sed. Likes big butts and he cannot lie. \'\'\'THATS FUCKING ENOUGH SAID.\'\'\'\n*\'\'\'MrBubbles\'\'\' - A scandal broke out with young Bubbles when he did not appear in May 2004 and ended up in a hotel with a 16 year old girl. He had no idea how he landed there and said he was intoxicated and was raped by an European girl. The rapest only comments were, "got any mor jellor thar11?". He also enjoys GunNRoses music and short walks on the beaches with a sweetheart and a Chicago Typewriter on the other hand to pwnarize all. His favorite past times include but not limited to: Tingle hunting, EZD slap fight, playing the kiddiecube and crapstation 2. Most memorable quote: "You know...if fapping makes you stronger and healthier...I\'m completely set for the next 1000 years. Biatch".\n*\'\'\'Winnar\'\'\' - Highest CS:S score was 3 and 35 in De_Dust2.  Currently perfecting the LOLOLOL p90 rush.\n*\'\'\'Peppy Hare\'\'\' - Once escaped from the clutches of Andross, Peppy steered out of Venom, and headed back to Corneria. He warned General Pepper of the evil, and they started a revolt against the crime lord. He soon taught Fox McCloud how to do the Barrel Roll (Press Z and R twice!) and saved the day.  Known ally to DJvenom.\n*\'\'\'Dayjo\'\'\' - Ruler of the Star Wars universe and everything outside, has afro, is officially; the man.\n*\'\'\'Mamoruanime\'\'\' - The ancient demon himself, created over 30,000 worthless songs that were never used by the band. He was killed personally by Metal, who was banned afterwards and plans to ressurect him began immediately.  This is the second time he is mentioned in this uncyclo article.\n*\'\'\'Naz\'\'\' - A guy with 3 heads, 3 eyes, and a website O.o.o\n*\'\'\'halotank\'\'\' - a PWNIN guy that\'s a ZFGC Vet been here since EZBoard, we all love \'em :D  Commonly reffered to as halu, tanku, tanky, HAWWWUUUUUUU(Tabu n.n)\n*\'\'\'[[User:FISSURE|FISSURE]]\'\'\' - Master poet specialising in repetition\n*\'\'\'[[User:DBRalph|DBRalph]]\'\'\' - Resident communist and ZFGC\'s own President, himself, with plans for world domination. Has plans to take over ZFGC that nobody has ever seen. Actually, his head explode!\n*\'\'\'[[User:Piers|Piers]]\'\'\' - Is the most amazing guy ever. and still no one figured out who I am.\n*\'\'\'Bizarro Phil\'\'\' - With over 10,000 alternate names and counting, he yells at everybody for no reason. M I RITE? \'\'\'M I RITE\'\'\'?\n*\'\'\'Gourry Inverse\'\'\' - Known for making ZFGC Serious Business. Very, serious business. Also the forum LUElinks representitive. VIVA LA REVOLUETION!\n*\'\'\'LttS\'\'\' (aka LinkToTheSpam, The Fabled One) -  Known for spamming ZFGC and #zfgc on many, many, many occasions. Known for repeating random sayings. Pedlya is his bitch. LttS is Hikij\'s bitch.\n*\'\'\'StarReaver\'\'\' - After fapping to furry porn, this little nova goes home and listens to japanese porn music, shortly before he steals my fucking chips.\n*\'\'\'Hero of Fire\'\'\' - He likes to burn you. He burnt my face once... Mamoruanime beat him up, which explains why he is so firey. Lol he is awesome and is also ZoraFan89\'s favorite \'\'\'bitch\'\'\'. A dedicated member of the band, he once made a pwnsome encylopedia of fangames, including even reviews of those that rest in peace.\n*\'\'\'Mastergohan Zero\'\'\' - With his hideous rant about Jack Thompson, Jack Thompson is so seskie sweet though. he was sent to hell with the demon Mamoruanime, and also closed down the sector of the band known as the incoherentescence.\n*\'\'\'Psylocke\'\'\' - An easy target for n00bs to hate on. \'Nuff said. -_-;\n*\'\'\'alspal\'\'\' - This guy is an insane human, dont get to close to him... omfg gonna be done coding demo 2 soon, alspal?\n*\'\'\'Alex2539\'\'\' - He\'s Canadian, he\'s got 4 numbers, don\'t mess. Is officially the best admin. ;)\n*\'\'\'Limecat\'\'\' - The coolest guy ever. Infact the coolest guy on zfgc. everyone wants to be like limecat. Because, Limecat is the coolest guy ever. He has a picture of a freakin cat with a limepeel on his head as his avatar. thats right. a cat. with a lime peel. Limecat rox. he roxorz your boxorz.\n*\'\'\'[[User:Hyperhal|Hyperhal]]\'\'\' - Being black, he has an extra bone in his ankle, allowing him to steal TVs like an expert, and hide them under his shirt.\n*\'\'\'HK\'\'\'  - HK rocks the house with songs like "I look like a lady", "Pwn me baby one more time", and the band\'s all-time favorite song, "Trapped On The Banlist(Parts 1-5)"(This list was later expanded to parts 1-30). HK then quit the band to join rivaling band "The ZeldaPowers", but they had no success so he went right back to ZFGC. 5 years later, HK died of a bandwidth addiction, and we all miss him greatly.\n*\'\'\'ZeAlMo\'\'\'- A.K.A Dr_ZeAlMo, known for being a Doctor and having an asassination list which sorta voids his promise of saving people as a doctor. Known for asking \'Dmaybe hios hamster tyoed this?\'\n*\'\'\'bran371\'\'\'- He likes cereal? (\'\'\'Word 2 yo mutha\'\'\')\n*\'\'\'Tippz\'\'\' - self proclaimed \'educated fanboy\', has written many a song with the sole purpose of proving a point. Occasional inappropriateness has led to little respect from higher ups in the band...\n*\'\'\'Srehpog\'\'\' - Quit the band to start his own. He made famous songs like "I love you, by the way check out Ark22", "Let\'s dance...and play Ark22", "Hands in the air...Ark22 on the pc" and "Bohemian Arksody 22". In his free time, Srehpog plays/develops Ark22.  His name can be manipulated to Srephiroth.  I\'m gunna play pogs and eat a sammich with mayo.  LOTS OF MAYO.\n*\'\'\'Nintendo Maniac 64\'\'\' - Joined the band thinking he\'d be able to download free RAM. He was suspicious about it at first, but after all his MILLIONS of questions were answered, he believed them. He told others about it but they called him a n00b. Eventually he found out that the other band members were joking and they thought he was joking along with them. NM64 then held a grudge against \'\'iamme123\'\' after that for fooling him into thinking there\'s free RAM to download. Many see him as the most questioning and gullible member of the band.  Wrote the single "It was FAKE?!"\n*\'\'\'The MAZZTer\'\'\' - After gaining absolute power 10 years ago, he ceeded it, and proceeded to lower himself through the ranks back to that of an ordinary member. Unbeknownst to the others, while he was in power, he established a puppet government, and so now controls ZFGC even as a normal member. Often confused with \'\'\'MegaworM\'\'\' for no apparant reason.  His last name is Buggins, he phails at life.\n*\'\'\'Some_Damn_Canadian\'\'\' - Some damn person from Canada.\n*\'\'\'Tendo\'\'\' - A huge fan of the band Pink Floyd, he strongly believes that Halo 1 is much better than Halo 2. Of course, everyone knows that this is complete blasphemy as Halo 2 was voted as "liek teh best gaem evah!!!11" in the 2006 "liek 1337 g4m1ng 4w4rd5", and Halo 1 is just myth. We have all heard tales of the legendary game, but nobody over the age of 5 believes it ever existed.\n*\'\'\'[[User:BPM|BPM]]\'\'\' - Assumed not to truly exist at all. And if it does, it is assumed that BPM looks like Link, but a female with large breasts. BPM\'s favorite instrument is the cowbell.\n*\'\'\'Acid Rain\'\'\' - Plays the guitar in the band. He\'s the cause of many of the classic songs, such as: "OMFGBBQ!!!I"M A MOD!!!11!111" and "Dude, check out PotS! It\'s l33t!". The only thing we know about this "PotS" is that it\'s something he likes to smoke. We have idea why....\n*\'\'\'Blackl3232\'\'\' - LOL HAY DO YOU COME HERE OFTEN??!1?\n*\'\'\'SwiftHunterX\'\'\' - O RLY? YA RLY, COMBO BREAKER. EVERYONE LOVAS ME\n*\'\'\'JesseTyler\'\'\' - <nowiki>*</nowiki>\'\'\'Currently running from the pedobear.\'\'\' OSHT HES HERE\n*\'\'\'Joeshmo\'\'\'- Joeshmo>ZFGC. Also the founder of ZFCOSBY. ZFGCOSBY>zfgc, I\'m cereal. EXCELSIOR \n*\'\'\'bob23\'\'\' - Band member that goes by the name bob23, no one is sure of his real name but we all know its not bob.\n*\'\'\'shadow_calibur\'\'\' - ZOMG! BEST PURPLE EVER!\n*\'\'\'legofreak\'\'\' - This band member is a lazy SOB... its a good thing he was put into management\n*\'\'\'Kren\'\'\' - He likes to kick monkeys.\n*\'\'\'Linkdude\'\'\' - Despite the n00bish nickname, he\'s not a n00b, and he hates everybody else because he\'s the only important one.\n*\'\'\'Yoshi\'\'\' - One of t3h oldest band members. He bothers to bitch about Sonic and crap. WHOMG SONIC CD TIME!!!111oneoneone\n*\'\'\'[[User:Sterlin254|Sterlin254]]\'\'\' - One-Hundred-Post Wonder...\n*\'\'\'goroneater\'\'\' - if ur a goron get away from me cuz ill eat ur face, I R WEENAH I SUCK MAH muMz dick itz hary, grroons taste good ssSEXING TEHM good. The previous was \'\'so\'\' not in a song. He was kicked out for insulting the backup singers.\n*\'\'\'Chaos Master\'\'\' - Due to mass internal explosions, Chaos Master hasn\'t come to many of the recent concerts. When the bleeding stops, he\'s been known to attend.\n*\'\'\'Chaotic_Death\'\'\' - Just some random person.\n*\'\'\'Takuthehedgehog\'\'\' - Pillaged on many occasions by the pir8s of the mayo, but defeated them with the legendary ban hammer.\n*\'\'\'TP\'\'\' - A roll of toilet paper. Or at least that\'s what his brain seems like n_n\n*\'\'\'[[User:Brethren|Brethren]]\'\'\' - Your friendly neighbourhood fanboy. He likes to refer to himself as the \'Nintendork\'. He is also popular for unexpectedly starting debates with the band.\n*\'\'\'Hero of Vortex:\'\'\' Who is he? Where does he come from? Why does light bend when it gets to the area around his head?? This mysterious band member seems to be an astronaut that was saved from a black hole. It is unknown why he got shorter. His current song, "Pokemon Tan Walking Demo", seems to actually be a cover-up, a place holder, something to hide his possibly evil intentions. It is rumored that he hitched a ride when Buckaroo Bonzai broke into the eigth dimension.\n*\'\'\'\'\'\'Digi - EX:\'\'\' - That dude, thats in the corner watching, and then gets involved, then hides again and comes out to show some stuff he made in shadows. He likes Sonic games, and...stuff, he likes Good Charllotte and Greenday, he loves Grahmmmaaarrrr.\n* \'\'\'[[User:Zombiebaron|Zombiebaron]]\'\'\' - \'\'\'b\'\'\'i\'\'\'t\'\'\'c\'\'\'h\'\'\'e\'\'\'s\'\'\'\n*\'\'\'-=Limey=-\'\'\' - steals images for sig\n*\'\'\'aab\'\'\'* Claimed he wanted to be "done" by Pyru. Pyru refused, leading to great tension within the band. Was involved in a number of side-projects, including "LOL I B SMARTUR THAN ALL OF U PUT TOGETHER" which was scarily true. AAB IS A PINK BUNNY WHO RUNS FROM PEOPLE THEN TURNS INTO GIGANTOR AND HAVES THEM FOR LUNCH!!!!!!\n*\'\'\'DugOLas\'\'\' - Nobody can forget that weird dumb ass Dug. With his spammy posts and such, Dug was well known for being banned "Liek a jilli0n times". Nowdays, dug is the troll under the off-topic bridge...Man it smells down here o.o;\n*\'\'\'ZeldaFN\'\'\' - \'\'\'his stil hre\'\'\'\n*\'\'\'Potato\'\'\' - He took on the Wanderer in the character comp.  It\'s also rumored that he has an awesome power to make carbohydrates.\n*\'\'\'NeoGeo-x:\'\'\' - Likes to be an ass and GET AWAY WITH IT. Though, he doesn\'t take the people serious who flame him. They can\'t even spell his goddamn name right.\n*\'\'\'LiNk1090\'\'\' - The biggest n00b and liar of all time. He made OOT2D in MC-style with his uncle, but he left it on a disk and lost it. He also made Halo and Super Mario Bros.\n*\'\'\'XdragonSB\'\'\' - The one who uses Zelda Classic instead of the ol\' Gamemaker. HAVE FUN ZELDACLASSICING! he says.\n*\'\'\'Hyrule_boy\'\'\' - Do u speka any engrish?\n*\'\'\'TomPel\'\'\' - Also known as "Master Of Disaster", was the quiet little fella in the old days. Later he learned the secret art of spriting and his powers grew. Later, he grew as a member and many people started to respect this guy\n*\'\'\'gm112\'\'\' - A former programmer/random guy of ZFGC. gm112 came from when he used Game Maker, so he tried to register as "gm", but that was too short, so gm decided to put in random numbers. Recently it was announced that gm released the album "Zelda III Prototype Rom" which allegedely turned to another kind of music, but it turned out to be a prank. A completly random prank. That my friends is the story of the member who is O_o......wtf mate!?!\n*\'\'\'Shawn\'\'\' - One of those guys that are kinda cool and only come online sometimes and but yeah hes pretty cool for a guy with no arms\n*\'\'\'OSM\'\'\' - Cooler than OMGFossil and Batman put together\n*\'\'\'Four Sword\'\'\' - Never posted once\n*\'\'\'tommy_boy283/Sir Cyurs/Hanshi/Uber-Leet-British-God\'\'\' - Thinks he\'s well-pro because he posts that he thinks he\'s well-pro on the ZFGC Uncyclopedia article.\n\'\'\'ZaC-UK\'\'\' - Maker of MMGB. Then he quit....\n\'\'\'LOL DRACON\'\'\' - posted by Limey.\n*\'\'\'AtomicD1\'\'\' - Not very active member, slowly programming zelda games which are unknown to most members.\n\n=== Former Members ===\n\n*\'\'\'Lunar\'\'\' - Shutup Lunar.\n*\'\'\'Daniel Barras\'\'\' - Some guy who made the albums Demo 1, Demo 2, Demo 3, Demo 3a, Demo 3b and Demo 3c. Demo 4 was never completed, because its a myth. :D Currently working on "packs" for "guitars" that let you play them in different languages, and they also make them look different. Though, progress is slowing down because of his ho, Amanda.\n*\'\'\'TylerRusselMackenzie\'\'\' - Commonly confused with Daniel Barras. But this guy is nothing like him: TylerRusselMackenzie\'s a cheat, a liar, a theif, a fake; nothing like Daniel >_>\n*\'\'\'Kleaver\'\'\' - Simply known as \'THE ADMININSTRATOR\'. He lost this title when he got angry at the other members for crazy decisions, one including ressurecting the ancient demon Mamoruanime.\n*\'\'\'Satan\'\'\' - See "Drewdelz"\n*\'\'\'Metal\'\'\' - HE WAS BANNED BY STABBY. WE MISS YOU METAL. <3\n*\'\'\'ZServ\'\'\' - They kicked him out because they thought he was a whiney emo kid, in a time when the band focuses solely on country music. They did not let him back in after they returned to rock, however.\n*\'\'\'cpprograms\'\'\' - Leader of ZFGC\'s private army of penguins and old producer, helped the band avoid numerous copyright violations by producing all of the music on a boat in the middle of international waters.\n*\'\'\'God\'\'\' - Has never resided in zfgc :(\n*\'\'\'Drewdelz\'\'\' \'\'\'Is next to GOD on this list\'\'\'\n\n*\'\'\'General O\'Neil: \'\'\'Previously one of the lead members, he left because he got an Ancient Library in his head while on another planet.\n*\'\'\'Richard Dean Anderson: \'\'\'Thought to be the same person as O\'Neil, he left around the same time.\n*\'\'\'therabidwombat\'\'\' - omg t3h rabies I will get uz...lol rabid review: 10/10 good job I am global mod ph34r my post count!\n*\'\'\'ArcticFerret\'\'\' A master of crap spriting and is known to the community as tara the gender bender goddess.\n\n*\'\'\'Written in Loving Memory\'\'\' - I miss you very much - I will not mention your or my name here, but when I find a peanut under my couch, I\'ll remember you forever... *sniff*. I\'m actually crying now... \n\n*\'\'\'Tet\'\'\' - \'\'\'Fuck Tet.\'\'\' Hal, stop messing with my FUCKING UNCLYCLOPEDIA stuff. I\'M FUCKING SERIOUS this FUCKING time, MAN. Don\'t FUCK it UP this TIME.\n*\'\'\'??? says\'\'\' Don\'t fucking worry, Tet, nobody\'s gonna fuck with your fucking stuff.\n\n=== Sweet Computers ===\n*\'\'\'Pedlya\'s Computer\'\'\' - He just formatted his computer, so he lost all his porn. Poor, poor soul.\n*\'\'\'MegaworM\'s Computer\'\'\' - 3.8 OC\'ed to 4.2 GHZ, 27" Widescreen monitor, 21" CRT, 2048 MB DDR2, 430 Gb combined storage, Dual-boot Windows XP/Mac OS X, 7.2 Surround sound (12" and double 6" subs), webcam security system, over $4000 in \'borrowed\' software >_>\n*\'\'\'Snoopy\'\'\' - Max\'s 14 processor cluster, named after Our favorite beagle, runs completely on FreeBSD, some are dual-boot Solaris. \'\'\'Slackware is better\'\'\'\n*\'\'\'Shiro\'s \'\'Future\'\' Computer\'\'\' - 2 3.4 gHz AMD 64 X2 Dual Core Processors, socket 939 for upgrades, with an SLI Motherboard, 32" plasma TV monitor, 21" CRT monitor, 5000+ GB DDR3, 400 GB + 180 GB hard drive, Dual booting Microsoft Windows Vista and Ubuntu Linux, 7.3 surround sound speakers, 2 512 mb DDR3 Nvidea Video cards. Code named = Mana; Estimated Price (CAD) = $4237.49; Duration = 5-10 years until it becomes obsolete; When I\'m buying it = 1-2 years. But he REALLY can\'t buy it because hes \'\'\'black\'\'\'. Don\'t \'\'\'deny your inner nigger.\'\'\'\n*\'\'\'Nintendo Maniac 64\'s Laptop\'\'\' - 700 MHz Pentium 3, 1024 x 768 LCD screen with a max color depth of 32 bit, 128 MB of RAM, 10 GB harddrive, and Windows 2000 as OS.  Not a sweet computer until you add in...  IT ONLY COST $20!\n*\'\'\'cb43569\'s Desktop Hewlett Packard\'\'\' - Yay! Everybody should run a 850Mhz Windows 2000 machine with 256MB of RAM, a 27GB hard drive, a 1280x1024+ resolution, using an NVidia TnT2 Model 64 Pro graphics card on an AGP slot, and remember 32MB texture memory is ggoooooodd...\n*\'\'\'MasterGoan Zero\'s black box of hardware\'\'\' - Dual Core Processors intel Xeon 2.8 ghz,  19" flatscreen monitor,  1 gig of ram, Nvidia quadrofx500 128mb (hate it..) directX8 (i want my all-in wonder 9600 back =( ), 80 gig hdd + 40 gig hdd + 30 gig ipod, Cd reader, 250 zip reader/writer, Windows XP proffesional,  logitech webcam, HP photo printer, Epson 3490 photo scanner, DSL at 220kb/sec /w a linksys 2.4 ghz wireless router for teh wifi.  Price: nfc, it was free... (\'\'\'Aka he stole it AKA his dad got it from work AKA he got it for free AKA objection\'\'\')\n*\'\'\'Torchie\'s 448 MHz Pentium III PC running Windows XP, with 256 MB RAM, an 8.0 MB video card, always in 1600x1200. You know how awesome this computer is? It\'s 10 years old and it runs playstation games at an amazingly badass 40 FPS! AND it can play japanese porn games without a hitch! Sweet!\n*\'\'\'Linky\'s Computer\'\'\' - 2.01 gHz AMD 64 X2 Dual Core Processors 3800+, ePox Motherboard S939 NF4 ULTRA+, 2000MT/s PCI Express DDR400 Audio (8-channel) S/PDIF-in/out Serial ATA 3 Gb/s RAID 0/1/0+1 Gigabit LAN and 10 USB, 17" LCD TFT flatscreen Monitor, Corsair Dual Channel 1024 MB (512 MBx2) PC3200 CL2.5, Western Digital Caviar 250 GB S-ATA II (2G) HDD 7200 rpm/8 MB, Windows XP Home Swe, nVIDIA GeForce 7900GT 256 MB (256Bit) TV DVI PCI-E, Samsung DVD+-RW DS 16X LightScribe, Xion II Miditower ATF 420W, Logitech ULTRA-X Flat keyboard, Razer Copperhead Chaos Green, Razer Mantis Control mousepad, and a hell lot of neons!\n*\'\'\'dylan623\'s computer\'\'\' - Do you really think I\'m going to tell YOU about it? BECAUSE I GOT GAY PORNOS ON IT!!!111\n\n=== The Females ===\nRumored to not even exist amongst us, there are only a few who have \'willingly\'(harassed in other words) revelealed themselves.\n\n*\'\'\'Mogrymillian\'\'\' - What, another girl!? GIRLS DON\'T EXIST ON ZE INTRANET. And with the legendary words..."How\'s the view up there on your throne of lies?" ~Swiftu. Well, she IS a girl. Fox approves. And damn, that guy\'s one cool fox.\n\n*\'\'\'Sukarusun\'\'\' - Who the fuck is this?\n\n*\'\'\'Angela\'\'\' - "I LOVE ANGELA"; most famous girl on ZFGC, never to exist.\n\n*\'\'\'Tabby\'\'\' - Swedish person who doesnt want to show her pic.\n\n*\'\'\'Cally\'\'\' - AKA Valentine.  A deadly and dangerous hybrid combo of a squirrel and a chipmunk, Valentine can be seen in her natural environment nibbling on JesseTylers.\n• Valentine eats JesseTyler\n\n• JesseTyler returns the favor. ;_;\n\n*\'\'\'Angelwolf\'\'\' - The nicest internet whore since sliced paint thinner.\n\n*\'\'\'Tedy_Bear05\'\'\' - tits or GTFO\n\n*\'\'\'Kiriona777\'\'\' - "I LOVE KIRIONA777"... or something Nebetsu said.\n\n*\'\'\'68.238.225.10\'\'\' - Is going to be removed by his ISP when his computer slows the entire Internet Service Provider to a halt.\n\n*\'\'\'Briannabo\'\'\' - Something never to exist in Lunar\'s future. \'\'\'Lunar phails\'\'\'\n\n*\'\'\'CrystalAngel04\'\'\' - Some people just think of her as just the ZFGC girl; I think of her as awesome. She\'s the nicest person on ZFGC, closely followed by aab, and she\'s always happy to help someone out.\n\n*\'\'\'FUCK THIS SHIT.\'\'\' - \'\'\'I\'M THE MOST NICEREST PERSON ON ZFGC. THIS AIN\'T AAB EITHER MUTHA CHUKAS. I\'M DEFINATLY GAY, THOUGH.\'\'\'\n\n*\'\'\'TerrificTara\'\'\' - Not a girl, and YOU flirted with him, \'\'\'GG XD\'\'\'\n\n*\'\'\'xMiNalienx\'\'\' - Not a female O_O GG HF DD\n\n*\'\'\'Super-Zelda-Kiwi-whatever\'\'\' - Keeps getting new accounts. sh always tlks in "chatspeak" lik ths\n\n*\'\'\'Starforsaken10\'\'\' - Guitars, beyatches.\n\n*\'\'\'OniNekoChan\'\'\' - What?  When did she get here?  Last we checked, MG had her tied up with Ed Elric\'s coat to keep her sanity in check.  But whatever, don\'t disturb her, she\'s thinking.\n\nP.S: Females. \'\'\'Serious business.\'\'\'\n\n=== Crappy Computers ===\n*\'\'\'TRM\'s Computer\'\'\' - Where the \'\'\'fuck\'\'\' is OoT2d? I don\'t see it anywhere.\n*\'\'\'Drewdelz\'s Computer\'\'\' - Completely fucked up this page. It should burn in hell and be put in a wheel chair and roll down the longest hill in the world.\n*\'\'\'Max\'s old SPARCs\'\'\' - 53 MHz? These badboys could play graphically challenged games like F.E.A.R., or Doom 3.\n*\'\'\'DJvenom\'s Computer\'\'\' - The CPU is actually a Pterodactyl, and when you open up the case he pops out and says "It\'s a living!"*RIP* -2006\n*\'\'\'Hyperhal\'s HDD\'\'\' - Its just a Hard drive. You jack any computer you see and put it in. Instant access.\n*\'\'\'ZServ\'s Family Computer\'\'\' - The shit doesn\'t even work. He tried to put some RAM in and melted the entire room. His Mom then proceeded to beat the fuck out of him with a sledgehammer. Pretty hawt Mom.\n*\'\'\'Nintendo Maniac 64\'s family desktop computer\'\'\' - come on, 533 MHz?!  And it\'s running \'\'\'Windows 98 SE\'\'\'?!?! And get this, a 20 GB HDD for the entire family?!  Not to mention the monitor is dark and getting blurry, so sometimes 1024x768 isn\'t very good because of the blurriness!  OLD\'D-D-D-D-D!!!!\n*\'\'\'Nintendo Maniac 64\'s really old computer\'\'\' It\'s so old, I don\'t know all the specs. Runs Mac and Windows 3.1, I think 8 MB of RAM for DOS, 10MB RAM for Mac, less than a GB HDD for the entire PC.\n*\'\'\'[[User:Sterlin254|Sterlin254\'s]] Server(but not his laptop)\'\'\' - 400Mhz Pentium II. Has a nice monitor though, resoultions up to 1600x1200, with acceptable refresh rates. For some wild reason, I recently(well actually in 2003, and early 2004) installed a new Radeon 9000 64MB AGP 2x/4x, and a 5.1 Surround Card(only $15, but suprisingly good). Which is ironic, since I barely use the thing for anything besides HD Space, and a Web Server. And aside from that, my Laptop totally pwns it in all ways.\n*\'\'\'MasterGohan Zero\'s "new" junk box\'\'\' - 1.8 ghz pentium 4, what the crap is this???  and a 40 gig hdd??? c\'mon, i\'m using that much space now!  AND WTH IS AN ATI RAGE 28 PRO UBER??  FREAKIN PRIMITIVE!! ME CAVEMAN PROBABLY MADE THE STUPID THING!! I MEAN, DIRECTX 1.2??? WHAT????  I expect the thing to turn off and say "your winblows has exploded" everytime he plays HL2!\n*\'\'\'The MAZZTer\'s POS\'\'\' - An ordinary computer, until MAZZ\'s freshman year in college, where he was across the hall from the laundry room.  So close, his computer developed the power of DUST MAGNET, and to this day regularly either attracts or produces dust/lint to a startling degree.  I mean really, I still don\'t know where the hell it comes from.\n*\'\'\'Drandula\'s Computer\'\'\' - "You found the Old Computer" Someone threw his computer away and it got picked up by Drandula. It could barely run BF 1942 before he started upgrading it, now-days it almost runs TF 2.\n\n=== \'\'\'The Mothers\'\'\' ===\n*\'\'\'Max\'\'\' - is cool....NOT!!!!\n*\'\'\'Pedobear\'\'\' - "\'\'Hello ladies!\'\'"\n*\'\'\'Mamouranime\'\'\' - The old \'ex-member-ex-admin-ex-moderator-ex-\'\'\'gay\'\'\'lover-ex-whore-ex-assassin\'. It is also rumored has has tea parties with the Italian mafia.\n*\'\'\'Ray Charles\'\'\' - A man said to be playing Doom 3 his entire life.\n*\'\'\'Vash\'\'\' - lol samoa\n\n== The Music ==\nTheir music is world renowned and has been translated into every relevant language*!\n\n<nowiki>*</nowiki> - Relevant languages include English and... uh... American English.\n\nUnfortunately, someone copyrighted the music and sent them to ZServ, who then proceeded to blow our shit up.\n\n==\'\'\'New Jersey\'\'\'==\n\nThe_Mega_ZZTer lives here. God have mercy on his sad, confused soul.\n\n==\'\'\'z3(Zebra WHAT?)\'\'\'==\n\nAt the moment, z3 is a secret name being used by the fake LUEnin of \'\'\'ZFGC\'\'\'. We don\'t really know what kind of shit they\'re planning, or smoking while planning it, but who the fuck really cares. \'\'\'Terrorists win.\'\'\'\n\n==\'\'\'Whoracle Of Life\'\'\'==\n\nThe most recent project yet, funded by the proceeds of albums 1 through 4. \nLittle is known of this project or it\'s progress, some rumours say it\'s a freakish quest to find the Oracle Of Life, a diety known to be able to give life to the dead, and to beseach her to put some life back into the band. We all hope it goes well for the good of the band. Rumors are, that the hairball prevention system, closes cIRM. Because of that, AODC (aka. An Old Dead Cat) had to sleep in the crack house next Wednesday. LAWLK >.>\n\nHero of Fire is supposed to be really good at drawing up "good quality whores", which is why he got the job as the Spriter.\n\n== Ripped his  Sig ==\n\nYes, now I see it\n\nWhat I\'ve felt\nWhat I\'ve known\nTurn the pages\nTurn the stone\nBehind the door\nShould I open it for you?\n\nYeah\nWhat I\'ve felt\nWhat I\'ve known\nSo sick and tired\nI stand alone\nCould you be there\ncause I\'m the one who waits\nThe one who waits for you\n\nOh\nWhat I\'ve felt\nWhat I\'ve known\nTurn the pages\nTurn the stone\nBehind the door\nShould I open it for you?\nSo I dub thee unforgiven\n\n\nThe Future arrived and the past hated it!\nProject X came and turned into a merge with DSrevolution.com but ZFGC\'s fake veterans took cover in a makeshift shelter known as "ZFGC-X"GANGSTA YO (ripping off both zfgc and the shunned project x) eventually the complaints landed on sympathetic/strategic ears and the original ZFGC was put back up.\n\nIn doing this ZFGC X won the following awards (yes seriously)\n"Most cowardly members 2006"\n"Most repetitive members 2006"\n"Most Fissure members 2006"\n"Biggest Wigger on internet 2006*\n\nNow DSR peacefully resides in the same URL with plans of eventually becoming the highly acclaimed gamersadvanced, just very slowly\n\nand ZFGC is back to its sty where it can become an even more disrespected community than before *YAY FOR SHITTY STAFF*\n\n== The Stuff ==\nThis is an assortment of the stuff some of the ZFGC\'ers are on.\n*\'\'\'pr0n. Obviously.\'\'\'\n*\'\'\'Cocaine. (lol, MegaworM)\'\'\'\n*\'\'\'Bizarro ZFGC\'\'\'\n*\'\'\'[[Cancer]]\'\'\'\n*\'\'\'[[ZFGC/YTMND]]\'\'\'\n*\'\'\'FISSURE WAS HERE\'\'\'\n*\'\'\'Pink puff\'\'\'\n*\'\'\'[[LUE]]\'\'\'\n*\'\'\'[[Severus Snape|Snape]]\'\'\'\n*\'\'\'[[Zelda]]\'\'\'\n*\'\'\'[[Power Rangers]]\'\'\'\n*\'\'\'[[Maddox]]\'\'\'\n*\'\'\'DEMO \'\'3.0002898383\'\' of OoT2D IS RELEASED YEH!!!!\'\'\'\n*\'\'\'CUCCIOLOPELOSO is not here... why do everyone forget the cuccio??\'\'\'\n*\'\'\'NField\'\'\'\n*\'\'\'The [[Gman]]\'\'\'\n*\'\'\'The Adventures of Gordon Freeman\'\'\'\n*\'\'\'Gordon Freeman Vs. Mr. Friendly\'\'\'\n*\'\'\'Combine lol\'\'\'\n*\'\'\'THE BRAINS!!!\'\'\'\n*\'\'\'LOL DRACON!!!\'\'\'\n*\'\'\'[Insert Company Name Here] SUCKS! aka OBJECTION\'\'\'*\n*\'\'\'trains\'\'\'\n*\'\'\'I\'M GORONEATER!! I\'M GONNA EAT YOU IF YOU\'RE A G0R0N!!11!11!!111ONEONE\n*\'\'\'Herpes...\'\'\'\n*\'\'\'Each other\'\'\'\n*\'\'\'Dracon\'s \n*\'\'\'It\'s cool to hate final fantasy 7 now vengance\'\'\'http://uncyclopedia.org/wiki/Uncyclopedia:Beginner%27s_Guide_to_Being_an_Uncyclopedian\nBeginner\'s Guide\n*\'\'\'Hay, Dirge of Cerberus??\'\'\'\n\n==Change of subject==\nThis topic sucks and is now about ZFGC the movie of the band of the book.\n\nRetro, Damn\n\n*attn Fox: If you\'re there, I have your secks money\n\n==\'\'\'¬_¬\'\'\'==\nThis section is too cool for words. WHOOPS.\n\n==\'\'\'The ZFGC Gameshow\'\'\'==\n\nRecently, a new type of paint thinner was revealed. This allowed puffguards like the ZFGCers to come up with the brilliant ZFGC game show. The game show was only ran once, and the end results forced the band to split up, but come back under "new managment" (sounds hawt!). The first band was called \'\'\'DSRevolution\'\'\', while the second was called \'\'\'ZFGC\'\'\'. Ironically, both these bands just sat around and yelled at each other about \'\'\'Demo4\'\'\'.\n\nThe mighty LUEnin are planning on bombing both of the band\'s locations with radioactive rubber sausage DDR RAM. \'\'\'What the fuck?\'\'\'\n\n==\'\'\'Locations\'\'\'==\n\n*\'\'\'Corner\'\'\'- Get the HELL in there Shiro. NOW!\n*\'\'\'Snicker\'s Castle\'\'\'- Hide out of the LUEnin, meow ha ha.\n*\'\'\'Maturity\'\'\'- A MYSTICAL LAND EXISTING OUTSIDE OF ZFGC\n*\'\'\'LOL! IN DA BUTT\'\'\'- The memorial park in southern Maine named by the ZFGC Community.  Many dogs are walked here including MiN and FISSURE.\n*\'\'\'Box 3\'\'\'- Located between Box 2 and Box 4.  You\'ll never find it, seriously.\n*\'\'\'FISSURE\'s House\'\'\'- Sommmewheeeereee over the rainbow!\n*\'\'\'The Way Out\'\'\'- This place connects the ZFGC community and Maturity. Sadly, it only works for about a week. Blame ZeldaFN.\n*\'\'\'Germany\'\'\'- What the.\n\n==\'\'\'OBJECTION SUCKS\'\'\'==\n\nRoflamzo! lik3 t3h new3st and c001 F4d! LAwozor!\n\nhey you know what you know what you know what.\n\nFUCK YOU!',_binary 'utf-8');
/*!40000 ALTER TABLE `zfgc_wikitext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'smf'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed

--
-- fixture: a MediaWiki User: page whose title matches a migrated member, so the
-- member-page generator has something of its own to collide with
--
INSERT INTO `zfgc_wikipage` VALUES (974,2,'mgzero','',0,0,0.444,_binary '20150801120000',3304,105,NULL,NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (3304,974,3404,'',2,'mgzero',_binary '20150801120000',0,0,105,0,_binary 'mgzerouserns2fixture1234',NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3404,_binary '== Who I am ==\nmgzero is the only power ranger.\n\n== Projects ==\nThe first HD Zelda game ever in existence',_binary 'utf-8');
