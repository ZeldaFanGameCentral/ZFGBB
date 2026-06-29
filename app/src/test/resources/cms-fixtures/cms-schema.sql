
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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zfgc_wikipage` (
  `page_id` int unsigned NOT NULL AUTO_INCREMENT,
  `page_namespace` int NOT NULL,
  `page_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `page_restrictions` tinyblob NOT NULL,
  `page_is_redirect` tinyint unsigned NOT NULL DEFAULT '0',
  `page_is_new` tinyint unsigned NOT NULL DEFAULT '0',
  `page_random` double unsigned NOT NULL,
  `page_touched` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `page_latest` int unsigned NOT NULL,
  `page_len` int unsigned NOT NULL,
  `page_content_model` varbinary(32) DEFAULT NULL,
  `page_links_updated` varbinary(14) DEFAULT NULL,
  `page_lang` varbinary(35) DEFAULT NULL,
  PRIMARY KEY (`page_id`),
  UNIQUE KEY `name_title` (`page_namespace`,`page_title`),
  KEY `page_random` (`page_random`),
  KEY `page_len` (`page_len`),
  KEY `page_redirect_namespace_len` (`page_is_redirect`,`page_namespace`,`page_len`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zfgc_wikirevision` (
  `rev_id` int unsigned NOT NULL AUTO_INCREMENT,
  `rev_page` int unsigned NOT NULL,
  `rev_text_id` int unsigned NOT NULL,
  `rev_comment` varbinary(767) NOT NULL,
  `rev_user` int unsigned NOT NULL DEFAULT '0',
  `rev_user_text` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `rev_timestamp` binary(14) NOT NULL DEFAULT '\0\0\0\0\0\0\0\0\0\0\0\0\0\0',
  `rev_minor_edit` tinyint unsigned NOT NULL DEFAULT '0',
  `rev_deleted` tinyint unsigned NOT NULL DEFAULT '0',
  `rev_len` int unsigned DEFAULT NULL,
  `rev_parent_id` int unsigned DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 MAX_ROWS=10000000 AVG_ROW_LENGTH=1024;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zfgc_wikitext` (
  `old_id` int unsigned NOT NULL AUTO_INCREMENT,
  `old_text` mediumblob NOT NULL,
  `old_flags` tinyblob NOT NULL,
  PRIMARY KEY (`old_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 MAX_ROWS=10000000 AVG_ROW_LENGTH=10240;
CREATE TABLE `curated_wiki_project_link` (
  `wiki_title` varchar(255) NOT NULL,
  `entity_type` varchar(16) NOT NULL,
  `legacy_id` int unsigned NOT NULL,
  PRIMARY KEY (`wiki_title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
CREATE TABLE `zfgc_wikicategorylinks` (
  `cl_from` int unsigned NOT NULL DEFAULT '0',
  `cl_to` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `cl_sortkey` varbinary(230) NOT NULL DEFAULT '',
  `cl_sortkey_prefix` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `cl_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cl_collation` varbinary(32) NOT NULL DEFAULT '',
  `cl_type` enum('page','subcat','file') NOT NULL DEFAULT 'page',
  UNIQUE KEY `cl_from` (`cl_from`,`cl_to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ci_projects` (
  `description` text NOT NULL,
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `language` tinytext NOT NULL,
  `preview` tinytext NOT NULL,
  `progress` tinyint unsigned NOT NULL,
  `rating` float unsigned NOT NULL,
  `score` int unsigned NOT NULL,
  `status` tinyint(1) NOT NULL,
  `team_id` int unsigned NOT NULL,
  `time_created` int unsigned NOT NULL,
  `topic_id` int unsigned NOT NULL,
  `member_id` int unsigned NOT NULL,
  `views` smallint unsigned NOT NULL,
  `downloads` smallint unsigned NOT NULL,
  `last_updated` int unsigned NOT NULL,
  `title` tinytext NOT NULL,
  `votes` int unsigned NOT NULL,
  `screenshot_id` int unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `requirements` tinytext NOT NULL,
  `topic_template` text NOT NULL,
  `type` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_screenshot` (`member_id`),
  KEY `screenshot_id` (`screenshot_id`),
  KEY `score` (`score`),
  KEY `time_created` (`time_created`),
  KEY `status` (`status`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ci_resources_backup` (
  `description` text NOT NULL,
  `file` tinytext NOT NULL,
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `preview` tinytext NOT NULL,
  `rating` float unsigned NOT NULL,
  `member_id` int unsigned NOT NULL,
  `score` int unsigned NOT NULL,
  `time_created` int unsigned NOT NULL,
  `topic_id` int unsigned NOT NULL,
  `views` smallint unsigned NOT NULL,
  `downloads` smallint unsigned NOT NULL,
  `last_updated` int unsigned NOT NULL,
  `title` tinytext NOT NULL,
  `votes` int unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `size` int unsigned NOT NULL,
  `type` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `type` (`type`),
  KEY `time_created` (`time_created`),
  KEY `last_updated` (`last_updated`),
  KEY `score` (`score`),
  KEY `size` (`size`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1games` (
  `ID_GAME` int NOT NULL AUTO_INCREMENT,
  `ID_MEMBER` int NOT NULL,
  `title` mediumtext NOT NULL,
  `views` int NOT NULL,
  `body` mediumtext NOT NULL,
  `votes` mediumtext NOT NULL,
  `voters` mediumtext NOT NULL,
  `language` mediumtext NOT NULL,
  `requirements` mediumtext NOT NULL,
  `progress` int NOT NULL,
  `status` int NOT NULL,
  `postTime` int NOT NULL,
  `postIP` mediumtext NOT NULL,
  `downloads` int NOT NULL,
  `rating` float NOT NULL,
  `ID_PREVIEW` int NOT NULL,
  `voteCount` int NOT NULL,
  `zfgcapiApproved` int NOT NULL,
  `zfgcapiPassCode` mediumtext NOT NULL,
  `zfgcapiOpenCount` int NOT NULL,
  `zfgcapiRupeeCount` int NOT NULL,
  `zfgcapiLog` longtext NOT NULL,
  PRIMARY KEY (`ID_GAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1resources_main` (
  `ID_RESOURCE` int NOT NULL AUTO_INCREMENT,
  `ID_MEMBER` int NOT NULL,
  `title` mediumtext NOT NULL,
  `views` int NOT NULL,
  `downloads` int NOT NULL,
  `votes` mediumtext NOT NULL,
  `voters` mediumtext NOT NULL,
  `body` mediumtext NOT NULL,
  `postTime` int NOT NULL,
  `postIP` mediumtext NOT NULL,
  `type` int NOT NULL,
  `fileSize` int NOT NULL,
  `rating` int NOT NULL,
  `ID_PREVIEW` int NOT NULL,
  `voteCount` int NOT NULL,
  PRIMARY KEY (`ID_RESOURCE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE `smf_1game_comments` (
  `ID_COMMENT` int NOT NULL AUTO_INCREMENT,
  `ID_GAME` int NOT NULL,
  `ID_MEMBER` int NOT NULL,
  `body` mediumtext NOT NULL,
  `postTime` int NOT NULL,
  `postIP` mediumtext NOT NULL,
  PRIMARY KEY (`ID_COMMENT`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `smf_1resource_comments` (
  `ID_COMMENT` int NOT NULL AUTO_INCREMENT,
  `ID_RESOURCE` int NOT NULL,
  `ID_MEMBER` int NOT NULL,
  `body` mediumtext NOT NULL,
  `postTime` int NOT NULL,
  `postIP` mediumtext NOT NULL,
  PRIMARY KEY (`ID_COMMENT`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `ci_potms` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `preview` varchar(255) NOT NULL,
  `project_id` int unsigned NOT NULL,
  `project_title` tinytext NOT NULL,
  `time` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `time` (`time`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ci_project_screenshots` (
  `description` tinytext NOT NULL,
  `edited_member_name` varchar(80) NOT NULL,
  `edited_member_id` int unsigned NOT NULL,
  `edited_time` int unsigned NOT NULL,
  `file` varchar(255) NOT NULL,
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `member_id` int unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `preview` varchar(255) NOT NULL,
  `project_id` int unsigned NOT NULL,
  `time` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file` (`file`),
  KEY `project_id` (`project_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ci_project_downloads` (
  `description` tinytext NOT NULL,
  `downloads` int unsigned NOT NULL,
  `edited_member_name` varchar(80) NOT NULL,
  `edited_member_id` int unsigned NOT NULL,
  `edited_time` int unsigned NOT NULL,
  `file` varchar(255) NOT NULL,
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `member_id` int unsigned NOT NULL,
  `member_name` varchar(80) NOT NULL,
  `project_id` int unsigned NOT NULL,
  `size` int unsigned NOT NULL,
  `time` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file` (`file`),
  KEY `project_id` (`project_id`),
  KEY `size` (`size`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smf_1game_downloads` (
  `ID_DOWNLOAD` int NOT NULL AUTO_INCREMENT,
  `ID_GAME` int NOT NULL,
  `description` mediumtext NOT NULL,
  `fileSize` int NOT NULL,
  `fileURL` mediumtext NOT NULL,
  `postTime` int NOT NULL,
  `postIP` mediumtext NOT NULL,
  `downloads` int NOT NULL,
  `type` int NOT NULL,
  `ID_MEMBER` int NOT NULL,
  PRIMARY KEY (`ID_DOWNLOAD`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `ci_teams` (
  `id_team` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `logo` tinytext,
  `description` text,
  `ip` varchar(255) NOT NULL DEFAULT '',
  `id_member` int unsigned NOT NULL DEFAULT 0,
  `time` int unsigned NOT NULL DEFAULT 0,
  `public` tinyint unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_team`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `ci_team_members` (
  `id_team` int unsigned NOT NULL,
  `id_member` int unsigned NOT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT 0,
  `join_time` int unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_team`, `id_member`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `ci_tags` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `ci_project_tags` (
  `project_id` int unsigned NOT NULL,
  `tag_id` int unsigned NOT NULL,
  PRIMARY KEY (`project_id`, `tag_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `ci_project_news` (
  `project_id` int unsigned NOT NULL,
  `topic_id` mediumint unsigned NOT NULL,
  PRIMARY KEY (`project_id`, `topic_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `smf_1game_news` (
  `ID_NEWS` int NOT NULL AUTO_INCREMENT,
  `ID_GAME` int NOT NULL,
  `ID_MEMBER` int NOT NULL DEFAULT 0,
  `subject` mediumtext,
  `body` mediumtext,
  `postTime` int NOT NULL DEFAULT 0,
  `postIP` mediumtext,
  PRIMARY KEY (`ID_NEWS`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `curated_project_merge` (
  `source_entity_type` varchar(32) NOT NULL,
  `source_legacy_id` int NOT NULL,
  `target_entity_type` varchar(32) NOT NULL,
  `target_legacy_id` int NOT NULL,
  PRIMARY KEY (`source_entity_type`, `source_legacy_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `curated_collection` (
  `code` varchar(64) NOT NULL,
  `title` varchar(255) NOT NULL,
  `kind` varchar(32) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `curated_collection_item` (
  `collection_code` varchar(64) NOT NULL,
  `entity_type` varchar(32) NOT NULL,
  `legacy_id` int NOT NULL,
  `ordinal` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`collection_code`, `entity_type`, `legacy_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE `smf_1resource_downloads` (
  `ID_DOWNLOAD` int NOT NULL AUTO_INCREMENT,
  `ID_RESOURCE` int NOT NULL,
  `description` mediumtext NOT NULL,
  `fileSize` int NOT NULL,
  `fileURL` mediumtext NOT NULL,
  `postTime` int NOT NULL,
  `postIP` mediumtext NOT NULL,
  `downloads` int NOT NULL,
  `type` int NOT NULL,
  `ID_MEMBER` int NOT NULL,
  PRIMARY KEY (`ID_DOWNLOAD`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

