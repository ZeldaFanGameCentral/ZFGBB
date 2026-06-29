
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

/*!40000 ALTER TABLE `zfgc_wikipage` DISABLE KEYS */;
INSERT INTO `zfgc_wikipage` VALUES (44,100,'Master_Sword','',0,0,0.923096591485,0x3230323431323132313631303030,1983,425,NULL,NULL,NULL),(290,0,'Ocarina_of_Time','',0,0,0.817255273102,0x3230323630363132323031353530,2912,7526,NULL,NULL,NULL),(367,0,'Ocarina_of_Time_3D','',0,0,0.286609593393,0x3230323531303130313730373233,2581,15711,NULL,NULL,NULL);
/*!40000 ALTER TABLE `zfgc_wikipage` ENABLE KEYS */;
INSERT INTO `zfgc_wikicategorylinks` (cl_from, cl_to) VALUES (44,'KOT_Items'),(44,'King_Of_Thieves'),(290,'ZFGC_Projects'),(367,'ZFGC_Projects');
INSERT INTO `curated_wiki_project_link` VALUES ('Ocarina_of_Time','PROJECT',7);
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

/*!40000 ALTER TABLE `zfgc_wikirevision` DISABLE KEYS */;
INSERT INTO `zfgc_wikirevision` VALUES (1983,44,1882,'',8,'MidnightMoblin',0x3230323431323132313631303030,0,0,425,873,0x38776866787778767A6E6B796F716669666A6D3233397065376D6437626269,NULL,NULL),(2581,367,2464,'',289,'ZoraZora',0x3230323531303130313730373233,0,0,15711,2580,0x356D3971616C6C75666E6663657A36323835766D737062756A72786A797374,NULL,NULL),(2912,290,2789,0x2F2A204C61746573742064656D6F202A2F,42,'KokiriKid',0x3230323630363132323031353530,0,0,7526,975,0x386D6A637174326D37697030796D70347733786C6F357A34786730396A6C32,NULL,NULL);
/*!40000 ALTER TABLE `zfgc_wikirevision` ENABLE KEYS */;
INSERT INTO `zfgc_wikitext` VALUES (900,0x546865202727274D61737465722053776F726427272720697320666F756E6420696E207468652066696E616C2064756E67656F6E2E2043617061626C65206F662073686F6F74696E67206265616D732E0A3C6E6F696E636C7564653E0A5B5B43617465676F72793A4B696E67204F6620546869657665735D5D0A3C2F6E6F696E636C7564653E,0x7574662D38);
INSERT INTO `zfgc_wikirevision` VALUES (873,44,900,0x41646465642064616D616765206E756D62657273,77,'SheikahSlate',0x3230323330333035313231353030,0,0,134,0,0x64756D6D79736861316D617374657273776F7264,NULL,NULL);
-- dev-fixture page that intentionally references a MISSING image, so the E2E can keep
-- exercising the migrator's unresolved wiki-file marker / skip-missing-asset path while
-- the real fixture pages carry real committed images.
INSERT INTO `zfgc_wikipage` VALUES (900,0,'Broken_Image_Test',_binary '',0,0,0.5,_binary '20140101000000',3000,107,NULL,NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (3000,900,3001,_binary 'fixture: intentionally missing image',7,'EponaRider',_binary '20140101000000',0,0,107,0,_binary 'brokenimagefixture00000000000z',NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3001,_binary 'This fixture page intentionally references a missing image for tests: [[File:This_Image_Does_Not_Exist.png]]',_binary 'utf-8');
-- dev-fixture Main Page + MediaWiki:Sidebar (drive the wiki landing page + nav sidebar)
INSERT INTO `zfgc_wikipage` VALUES (1,0,'Main_Page',_binary '',0,0,0.157671252285,_binary '20150401024610',2314,5139,NULL,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (236,8,'Sidebar',_binary '',0,0,0.564775749879,_binary '20120926213247',814,475,NULL,NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (814,236,722,_binary '',17,'TingleFan',_binary '20120926213247',0,0,475,813,_binary 'iq8nguf5992jz2jfji2h6xupgmlm0ex',NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (2314,1,2210,_binary '',8,'MidnightMoblin',_binary '20150401024610',0,0,5139,2047,_binary 'p1wdl1i1vd43r8ysy98ximrhp17pnzp',NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (722,_binary '* navigation\n** mainpage|mainpage-description\n** portal-url|portal\n** helppage|help\n\n* Content\n** recentchanges-url|recentchanges\n** randompage-url|randompage\n** :Category:Members|List of Members\n** :Category:ZFGC_Projects|List of Projects\n\n* ZFGC\n** http://zfgc.com/index.php/|Home\n** http://zfgc.com/index.php/projects/|Projects\n** http://zfgc.com/index.php/resources/|Resources\n** http://zfgc.com/forum/index.php|Community\n** http://zfgc.com/index.php/chat|Chat\n\n* TOOLBOX',_binary 'utf-8');
INSERT INTO `zfgc_wikitext` VALUES (2210,_binary '<!-- <div style=\"border: 1px solid #fcc; background: #fee; padding: 0.5em 1em 0.5em 1em; font-weight: bold; text-align: center; margin-bottom: 0.5em; border-radius: 4px;\">\nWelcome to the Zelda Fan Game Centrikia! [[Special:AllPages|{{NUMBEROFARTICLES}} articles]] and [[Special:Statistics|counting!]]\n</div>\n[[Brbiamfat]]\n-->\n<!-- Top bar -->\n{|style=\"width:100%;margin-top:+.7em;background-color:#e0e0ff;border:1px solid #ccc\"\n|style=\"width:56%;color:#000\"|\n{|style=\"width:280px;border:solid 0px;background:none\"\n|-\n|style=\"width:50%px;text-align:center;color:#000\"|<div style=\"font-size:162%;border:none;margin: 0;padding:.1em;color:#000\">Welcome to [[ZFGC|ZFGCpedia]]</div><div style=\"top:+0.2em;font-size: 95%;color:#000\">The Official ZFGC Wiki</div>\n<div id=\"articlecount\" style=\"width:100%;text-align:center;font-size:85%;color:#000\">There are currently [[Special:Statistics|{{NUMBEROFARTICLES}}]] articles in ZFGCpedia!</div>\n|}\n\n<!-- Categories -->\n|style=\"width:11%;font-size:95%;color:#000\"|\n*\'\'\'[[Help:Contents|Help]]\'\'\'\n*\'\'\'[[Help:Rules|Rules]]\'\'\'\n*\'\'\'[[Help:CustomTemplates|Tutorials]]\'\'\'\n|style=\"width:11%;font-size:95%;color:#000\"|\n*[http://zfgc.com ZFGC]\n*[[Special:ListUsers|User List]]\n*[[King Of Thieves|ZFGC Community Project]]\n|style=\"width:11%;font-size:95%\"|\n*[[:Category:ZFGC Projects|ZFGC Projects]]\n*[[:Category:Video Games|Video Games]]\n*[[:Category:Online Games|Online Games]]\n|style=\"width:11%;font-size:95%\"|\n|}<!-- end of cats! -->\n__NOTOC__ <!-- Removes Table of Contents -->\n<!---------- Left box base -->\n{| style=\"border-spacing:8px; margin:0px;\"\n| style=\"width:50%; border:1px solid #cccccc; vertical-align:top; background-color:#e0e0ff;\" |\n\n<!------------ Left box -->\n{| width=\"100%\" cellpadding=\"2\" cellspacing=\"5\" style=\"vertical-align:top; background-color:#e0e0ff;\"\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">The Basics</h2>\n|-\n|\n\'\'\'ZFGCpedia is fully integrated with ZFGC\'\'\'\n* You must be logged into {{ZFGC}} to edit ZFGCpedia, however non-ZFGC Members can view the wiki.\n* Your {{ZFGC}} account and Wiki account are linked. If you break the [[Help:Rules#Wiki_Rules|rules]] on [[ZFGC|ZFGCpedia]], you will be suspended or even banned from {{ZFGC}}. You have been warned.\n\n\'\'\'Being useful\'\'\'\n* Read the [[Help:Contents|Help]] and [[Help:Rules#Wiki_Rules|Rules]] sections to learn the How\'s, Do\'s, and Don\'t\'s of ZFGCpedia editing.\n* Help expand and improve ZFGCpedia! Create relevant pages, or improve [[:Category:Stub|stub]] articles. Know a lot about [[pingas]]? Make some edits!\n* Format your articles. ZFGCpedia is more than just a text dump!\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">The Community Portal</h2>\n|-\n|\nIf you have a comment or suggestion, make yourself known at the [[ZFGCpedia:Community_portal|Community Portal]]!\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Useful Templates</h2>\n|-\n|\n\'\'\'Maintenance\'\'\'\n* {{tl|stub}} - Add this to articles that are too short\n* {{tl|VideoGame}} - Add this to articles about video games which {{ZFGC}} users play but do not have online multiplayer\n* {{tl|OnlineGame}} - Add this to articles about games which {{ZFGC}} users play online\n* {{tl|ZFGCProject}} - Add this to articles about games which are either developed or published by members of {{ZFGC}}\n* {{tl|UserProfile}} - Add this to link to a {{ZFGC}} user\'s article\n\n\'\'\'Other\'\'\'\n* We.. should have a public discussion about this. Also [[gm112]] is a lazy guy.\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Helping out</h2>\n|-\n|{{tasks}}\n\n\n|}<!--\n\n\n---------- Right box -->\n| style=\"width:50%; border:1px solid #cccccc; vertical-align:top; background-color:#e0e0ff;\" |\n{| width=\"100%\" cellpadding=\"2\" cellspacing=\"5\" style=\"vertical-align:top; background-color:#e0e0ff;\"\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">ZFGCCP: King Of Thieves News</h2>\n|-\n|\n{{KOT:News}}\n\n<!--|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Featured User: None</h2>\n|-\n|\n{{FeaturedUser}}\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Featured Article: None</h2>\n|-\n|\n{{FeaturedArticle}}-->\n\n|-\n! <h2 style=\"margin:0; background:#b0b0ff; font-size:120%; font-weight:bold; border:1px dashed #6666ff; text-align:left; color:#000; padding:0.2em 0.4em; color:#000;\">Featured Project: [[Ocarina of Time]]</h2>\n|-\n|\n{{FeaturedProject}}\n\n|}\n|}',_binary 'utf-8');
-- dev-fixture Template pages backing the Main Page's dynamic sections
-- ({{tasks}}, {{FeaturedProject}}, {{KOT:News}}). The migrator's publishTemplate
-- turns Template-namespace pages into content_template rows (code = lower-cased
-- title), so these resolve at render time instead of showing literal {{...}}.
INSERT INTO `zfgc_wikipage` VALUES (950,10,'tasks',_binary '',0,0,0.5,_binary '20140101000000',3100,199,NULL,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (951,10,'FeaturedProject',_binary '',0,0,0.5,_binary '20140101000000',3101,133,NULL,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (952,10,'KOT:News',_binary '',0,0,0.5,_binary '20140101000000',3102,99,NULL,NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (3100,950,3200,_binary '',8,'MidnightMoblin',_binary '20140101000000',0,0,199,0,_binary 'devfixturetaskstemplate000000z',NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (3101,951,3201,_binary '',8,'MidnightMoblin',_binary '20140101000000',0,0,133,0,_binary 'devfixturefeaturedproject00000z',NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (3102,952,3202,_binary '',8,'MidnightMoblin',_binary '20140101000000',0,0,99,0,_binary 'devfixturekotnewstemplate0000z',NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3200,_binary '* Create pages for the games ZFGC members are playing.\n* Expand short articles with more detail and screenshots.\n* Add info and downloads to project pages.\n* Fix broken links and clean up formatting.',_binary 'utf-8');
INSERT INTO `zfgc_wikitext` VALUES (3201,_binary '\'\'\'Ocarina of Time\'\'\' is the featured community project. Visit its project page for the latest screenshots, downloads, and progress updates.',_binary 'utf-8');
INSERT INTO `zfgc_wikitext` VALUES (3202,_binary '\'\'\'King of Thieves\'\'\' is the ZFGC community project. No news updates at this time - check back soon!',_binary 'utf-8');
-- dev-fixture additional real wiki articles (6 pages: 3 MAIN, 2 KOT, 1 Category)
INSERT INTO `zfgc_wikipage` VALUES (84,100,'Hyrule_Castle',_binary '',0,0,0.831383912903,_binary '20120923010434',651,158,NULL,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (91,100,'Zora\'s_Domain',_binary '',0,0,0.511133556178,_binary '20120923010354',648,218,NULL,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (117,14,'KOT_Enemies',_binary '',0,1,0.728065511807,_binary '20121029012150',305,138,NULL,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (266,0,'TC:Chapters',_binary '',0,0,0.372420587269,_binary '20121114193640',907,200,NULL,NULL,NULL);
-- (all rows pruned)

INSERT INTO `zfgc_wikipage` VALUES (490,0,'Wind_Fish_Society',_binary '',0,1,0.132512646582,_binary '20140324004944',1521,386,NULL,NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (305,117,244,_binary 'Created page with \"This category groups together the single enemy NPCs that exist in [[King Of Thieves]].  [[Category:King Of Thieves]] [[Category:KOT NPCs]]\"',8,'MidnightMoblin',_binary '20120918165853',0,0,138,0,_binary '8bymz8xmyryz4piinpy2nu85hd6cdpc',NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (648,91,579,_binary '',7,'EponaRider',_binary '20120923010354',0,0,218,225,_binary 'tahg1r40mg031dj5wul3py9kgbkpx7b',NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (651,84,582,_binary '',7,'EponaRider',_binary '20120923010434',0,0,158,252,_binary 's3i73chayw8xyo5xpoifjmihhxl5bjy',NULL,NULL);
INSERT INTO `zfgc_wikirevision` VALUES (907,266,812,_binary '',66,'GoronBros',_binary '20121114183717',0,0,200,906,_binary 'rg9nx8qmeeakdwx02lahaosev4zk0j0',NULL,NULL);
-- (all rows pruned)

INSERT INTO `zfgc_wikirevision` VALUES (1521,490,1421,_binary 'Created page with \"\'\'\'Leader\'\'\' - Alice Leontus  \'\'\'Group\'\'\' - Warriors  \'\'\'Original(Other) Name\'\'\' - Frozen Fire Clan  A group of warriors, who had lost a member of it\'s own due to a successful...\"',53,'WolfosGray',_binary '20140324004944',0,0,386,0,_binary '2iaaetgmy9d2xgkxw2my6r5js94upqw',NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (244,_binary 'This category groups together the single enemy NPCs that exist in [[King Of Thieves]].\n\n[[Category:King Of Thieves]]\n[[Category:KOT NPCs]]',_binary 'utf-8');
INSERT INTO `zfgc_wikitext` VALUES (579,_binary 'Home of the Zoras and currently their only safe zone from the River Zolas. Only those of Zora descent or those with permission may enter.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Locations]]\n</noinclude>',_binary 'utf-8');
INSERT INTO `zfgc_wikitext` VALUES (582,_binary 'Home of the Royal Family of Hyrule. Its splendid beuty is a wonder to behold.\n<noinclude>\n[[Category:King Of Thieves]]\n[[Category:KOT Locations]]\n</noinclude>',_binary 'utf-8');
INSERT INTO `zfgc_wikitext` VALUES (812,0x4C6F72656D20697073756D20646F6C6F722073697420616D65742C20636F6E73656374657475722061646970697363696E6720656C69742C2073656420646F20656975736D6F642074656D706F7220696E6369646964756E74207574206C61626F726520657420646F6C6F7265206D61676E6120616C697175612E0A0A3D3D43686170746572733D3D0A2A436861707465722031202D204C6F72656D20697073756D0A2A436861707465722032202D20446F6C6F722073697420616D65740A2A436861707465722033202D20436F6E73656374657475720A2A436861707465722034202D2041646970697363696E6720656C69740A2A436861707465722035202D20456975736D6F642074656D706F720A,_binary 'utf-8');
-- (all rows pruned)

INSERT INTO `zfgc_wikitext` VALUES (1421,0x5468652027272757696E64204669736820536F636965747927272720E28094204C6F72656D20697073756D20646F6C6F722073697420616D65742C20636F6E73656374657475722061646970697363696E6720656C69742C2073656420646F20656975736D6F642074656D706F7220696E6369646964756E74207574206C61626F726520657420646F6C6F7265206D61676E6120616C697175612E0A0A3D3D416374697669746965733D3D0A2A4C6F72656D20697073756D20646F6C6F720A2A53697420616D657420636F6E73656374657475720A,_binary 'utf-8');
INSERT INTO `zfgc_wikicategorylinks` VALUES (84,'KOT_Locations',_binary 'HYRULE CASTLE','','2012-09-18 00:09:18',_binary 'uppercase','page');
INSERT INTO `zfgc_wikicategorylinks` VALUES (84,'King_Of_Thieves',_binary 'HYRULE CASTLE','','2012-09-17 23:43:16',_binary 'uppercase','page');
INSERT INTO `zfgc_wikicategorylinks` VALUES (91,'KOT_Locations',_binary 'ZORA\'S DOMAIN','','2012-09-18 00:00:20',_binary 'uppercase','page');
INSERT INTO `zfgc_wikicategorylinks` VALUES (91,'King_Of_Thieves',_binary 'ZORA\'S DOMAIN','','2012-09-17 23:58:55',_binary 'uppercase','page');
INSERT INTO `zfgc_wikicategorylinks` VALUES (117,'KOT_NPCs',_binary 'KOT ENEMIES','','2012-09-18 20:58:53',_binary 'uppercase','subcat');
INSERT INTO `zfgc_wikicategorylinks` VALUES (117,'King_Of_Thieves',_binary 'KOT ENEMIES','','2012-09-18 20:58:53',_binary 'uppercase','subcat');
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

/*!40000 ALTER TABLE `zfgc_wikitext` DISABLE KEYS */;
INSERT INTO `zfgc_wikitext` VALUES (1882,0x7B7B4974656D496E666F626F780A7C696D6731203D205B5B46696C653A4B6F54204D61737465722053776F72642E6A70677C32303070787C6672616D656C6573735D5D0A7D7D0A7B7B4974656D496E666F626F780A7C7469746C65203D204D61737465722053776F72640A7C74797065203D20576561706F6E202853776F7264290A7C6F627461696E6564203D2046696E616C2064756E67656F6E0A7C64616D61676572617465203D203278204E6F626C652053776F72640A7D7D0A546865202727274D61737465722053776F726427272720697320666F756E6420696E207468652066696E616C2064756E67656F6E2E205477696365206173206D7563682064616D61676520617320746865205B5B4B4F543A4E6F626C652053776F72647C4E6F626C652053776F72645D5D2E2043617061626C65206F662073686F6F74696E67206265616D7320616E6420736D617368696E6720726F636B732E0A3C6E6F696E636C7564653E0A5B5B43617465676F72793A4B696E67204F6620546869657665735D5D0A5B5B43617465676F72793A4B4F54204974656D735D5D0A3C2F6E6F696E636C7564653E,0x7574662D38),(2464,0x7B7B5A46474350726F6A6563747D7D0A7B7B47616D650A7C7469746C653D5B5B46696C653A4F6F5433445F426F786172742E706E677C34303070785D5D0A4F636172696E61206F662054696D652033440A7C67656E72653D52656D61737465720A7C646576656C6F7065723D4F636172696E61206F662054696D65203344205465616D0A7C7075626C69736865723D57696E6466697368447265616D65720A7C706C6174666F726D3D57696E646F77730A207D7D0A0A4C6F72656D20697073756D20646F6C6F722073697420616D65742C20636F6E73656374657475722061646970697363696E6720656C69742E0A0A5B5B46696C653A4F636172696E6133445F53637265656E73686F742E6A70675D5D0A0A3C6E6F696E636C7564653E0A5B5B43617465676F72793A46656174757265642050726F6A6563745D5D0A3C2F6E6F696E636C7564653E0A,0x7574662D38),(2789,0x7B7B47616D650A7C7469746C653D4F636172696E61206F662054696D650A7C67656E72653D416374696F6E2D616476656E747572650A7C646576656C6F7065723D4B6F6B6972694B69640A7C706C6174666F726D3D57696E646F77730A207D7D0A5B5B46696C653A4F6F545F426F786172742E6A70675D5D0A0A2727274F636172696E61206F662054696D6527272720E28094204C6F72656D20697073756D20646F6C6F722073697420616D65742C20636F6E73656374657475722061646970697363696E6720656C69742E0A0A3D3D5A46474320537461666620436F6D6D656E746172793D3D0A4C6F72656D20697073756D20646F6C6F722073697420616D65742C20636F6E73656374657475722061646970697363696E6720656C69742E0A0A3D3D446576656C6F706D656E743D3D0A44656D6F206275696C64732061726520706F7374656420746F20746865205B687474703A2F2F7A6667632E636F6D2F666F72756D2F696E6465782E7068703F746F7069633D392E3020666F72756D207468726561645D206173206D696C6573746F6E65732061726520726561636865642E,0x7574662D38);
/*!40000 ALTER TABLE `zfgc_wikitext` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `ci_projects` DISABLE KEYS */;
INSERT INTO `ci_projects` VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',7,'84.86.240.136','Game Maker','oot_boxart.jpg',15,5,16570,3,0,0,9,3,8125,0,1700422207,'Ocarina of Time',64,0,'KokiriKid','none','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',23,'75.23.217.24','Misc','mm3d_boxart.png',0,5,15430,2,0,0,0,4577,7590,0,1703483163,'Majora\'s Mask 3D',50,0,'NaviBot','','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',66,'83.245.200.193','Misc','mm_boxart.jpg',0,5,4459,3,0,1728940125,38607,2,2152,0,1728940495,'Majora&#39;s Mask',31,0,'MajoraMain','Windows only','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',90,'91.158.25.45','Misc','mm_boxart.jpg',0,0,5292,4,0,1760985176,40115,27774,2646,0,1760985779,'Majora&#39;s Mask',5,0,'MajoraMain','Windows, could work in WINE','',2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.',99,'71.190.100.96','Game Maker','ww_boxart.jpg',0,4.5,122850,3,0,1775963613,0,2,61416,0,1779810230,'The Wind Waker',4,0,'TriforceTim','','',2);
/*!40000 ALTER TABLE `ci_projects` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `smf_1games` DISABLE KEYS */;
INSERT INTO `smf_1games` VALUES (169,3,'Ocarina of Time 3D',593,'Testing It\\\'s','3,1','4599,4247','Game Maker','',40,1,1689501891,'68.59.173.178',116,2,709,2,0,'',0,0,''),(174,1969,'Majora\'s Mask 3D',329,'Bending dimensions and mastering forbidden secrets, Link must embark on his most perilous journey yet.\r\n\r\nREMEMBER TO VIEW THE README.','5','1969','Blitz Max','',0,2,1691702536,'68.84.191.230',145,5,0,1,0,'',0,0,'');
/*!40000 ALTER TABLE `smf_1games` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `ci_resources_backup` DISABLE KEYS */;
INSERT INTO `ci_resources_backup` VALUES ('Lorem ipsum dolor sit amet, consectetur adipiscing elit.','aonuma_pack.zip',5,'10.0.2.5','aonuma_photo.png',5,41,0,1629219249,0,953,532,1629219249,'Eiji Aonuma Photo Collection',4,'',549682,2),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.','kondo_tracks.zip',8,'10.0.2.8','kondo_photo.jpg',5,52,0,1629235602,0,147,20,1629235602,'Koji Kondo Zelda Themes',1,'',170,1),('Lorem ipsum dolor sit amet, consectetur adipiscing elit.','aonuma_credits.zip',12,'10.0.2.12','',0,41,0,1629300000,0,42,7,1629300000,'Eiji Aonuma Zelda Credits List',0,'',382,4);
/*!40000 ALTER TABLE `ci_resources_backup` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `smf_1resources_main` DISABLE KEYS */;
INSERT INTO `smf_1resources_main` VALUES (5,2,'Eiji Aonuma Photo Collection',930,518,'5,5,4,5','41,4089,4247,23298','Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n\r\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris.',1629219249,'10.0.2.5',2,0,5,4,4),(8,3,'Koji Kondo Zelda Themes',147,20,'5','52','Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n\r\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris.',1629235602,'10.0.2.8',1,0,5,5,1),(9,3,'Shigeru Miyamoto Photo Archive',964,603,'5,5,5,4','92,101,204,305','Lorem ipsum dolor sit amet, consectetur adipiscing elit.',1629258886,'10.0.2.9',2,0,5,5,4);
/*!40000 ALTER TABLE `smf_1resources_main` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `ci_project_screenshots` DISABLE KEYS */;

INSERT INTO `ci_project_screenshots` VALUES ('Lorem ipsum dolor','',0,0,'majora_screenshot.png',160,'10.0.0.66',18966,'MajoraMain','majora_screenshot_thumb.png',66,1728940364),('Sit amet consectetur (file lost)','',0,0,'majora_beta_shot.png',211,'10.0.0.90',27774,'MajoraMain','majora_beta_shot_thumb.png',90,1760985467),('Adipiscing elit sed','',0,0,'windwaker_screenshot.jpg',213,'10.0.0.99',27718,'TriforceTim','windwaker_screenshot_thumb.jpg',99,1760985561);
/*!40000 ALTER TABLE `ci_project_screenshots` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `ci_project_downloads` DISABLE KEYS */;
/*!40000 ALTER TABLE `ci_project_downloads` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `ci_project_downloads` DISABLE KEYS */;
INSERT INTO `ci_project_downloads` VALUES ('Lorem ipsum demo.',171,'',0,0,'oot_demo.zip',4,'10.0.0.7',585,'StalfosSlayer',7,170,1695544774),('Dolor sit demo. (file lost)',113,'',0,0,'oot_demo_old.zip',5,'10.0.0.7',585,'StalfosSlayer',7,1362881,1695594943),('Consectetur demo.',64,'',0,0,'mm3d_demo.zip',6,'10.0.0.23',4577,'NaviBot',23,170,1760985470),('Adipiscing slice. (file lost)',22,'',0,0,'mm_demo_2011.zip',7,'10.0.0.66',18966,'MajoraMain',66,80000,1728940370),('Eiusmod demo.',35,'',0,0,'mm_demo_2012.zip',8,'10.0.0.90',27774,'MajoraMain',90,170,1760985480),('Tempor demo.',51,'',0,0,'ww_demo.zip',9,'10.0.0.99',27718,'TriforceTim',99,170,1760985490);
/*!40000 ALTER TABLE `ci_project_downloads` ENABLE KEYS */;
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

/*!40000 ALTER TABLE `smf_1game_downloads` DISABLE KEYS */;
INSERT INTO `smf_1game_downloads` VALUES (709,169,'Preview',112026,'oot3d_boxart.png',1689501891,'10.0.1.69',11246,1,0),(710,169,'Demo.',170,'oot3d_demo.zip',1689502125,'10.0.1.69',116,3,4599),(712,169,'Lorem ipsum progress shot.',45694,'ocarina3d_screenshot.jpg',1689546055,'10.0.1.69',953,2,4599);

INSERT INTO `ci_teams` VALUES (1,'Fixture Team','','A team for testing dev teams.','127.0.0.1',2,1711000000,1);
INSERT INTO `ci_team_members` VALUES (1,2,1,1711000100),(1,1,1,1711000200);
INSERT INTO `ci_tags` VALUES (1,'engine'),(2,'zelda'),(3,'soundtrack');
INSERT INTO `ci_project_tags` VALUES (7,1),(7,2);
INSERT INTO `ci_project_news` VALUES (7,9);
INSERT INTO `smf_1game_news` VALUES (1,169,3,'Engine update','We fixed the collision bugs.',1689600000,'127.0.0.1');

INSERT INTO `curated_collection` VALUES ('fixture-jam','Fixture Game Jam','EVENT');
INSERT INTO `curated_collection` VALUES ('potm','Project of the Month','FEATURE');
INSERT INTO `curated_collection_item` VALUES ('fixture-jam','PROJECT',7,0),('fixture-jam','GAME',169,1),('fixture-jam','PROJECT',9999,2);
INSERT INTO `curated_collection_item` VALUES ('potm','PROJECT',7,0);

/*!40000 ALTER TABLE `smf_1resource_downloads` DISABLE KEYS */;
INSERT INTO `smf_1resource_downloads` VALUES (4,5,'Preview',550600,'aonuma_photo.png',1629219249,'10.0.2.5',8422,1,0),(5,5,'Lorem ipsum pack.',549682,'aonuma_photos.zip',1629219807,'10.0.2.5',540,3,41),(15,8,'Preview',49471,'kondo_photo.jpg',1629235602,'10.0.2.8',1885,1,0),(16,8,'Dolor sit pack.',170,'kondo_tracks.zip',1629236553,'10.0.2.8',20,3,52),(17,9,'Preview',93571,'miyamoto_photo.jpg',1629258886,'10.0.2.9',9203,1,0),(18,9,'Consectetur photo.',93571,'miyamoto_photo.jpg',1629258900,'10.0.2.9',77,3,92);
/*!40000 ALTER TABLE `smf_1resource_downloads` ENABLE KEYS */;
/*!40000 ALTER TABLE `smf_1game_downloads` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


INSERT INTO `smf_1game_comments` VALUES (36,169,41,'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',1629061017,'10.89.0.114');
INSERT INTO `smf_1game_comments` VALUES (37,169,52,'Lorem ipsum dolor sit amet, consectetur adipiscing elit&#039;s. Mauris ac quam blandit, tempor tellus ut, hendrerit justo.',1629220706,'10.89.0.114');
INSERT INTO `smf_1game_comments` VALUES (38,169,92,'Curabitur tempus [b]placerat[/b] tortor, vel convallis enim mollis vitae.',1630233638,'10.89.0.114');
INSERT INTO `smf_1game_comments` VALUES (40,9999,41,'Suspendisse eget ligula vehicula, congue ante id, dictum nisi.',1630300000,'10.89.0.114');
INSERT INTO `smf_1resource_comments` VALUES (1,5,52,'Maecenas nulla tortor, maximus eu molestie id, convallis sed tellus.',1629137919,'10.89.0.114');
INSERT INTO `smf_1resource_comments` VALUES (2,5,27774,'Cras ullamcorper urna quam, eget vulputate massa viverra ut.',1629137954,'10.89.0.114');
INSERT INTO `smf_1resource_comments` VALUES (4,8,41,'Nulla in risus posuere, varius erat non, interdum augue.',1629220013,'10.89.0.114');
INSERT INTO `ci_potms` VALUES (1,'79710dc985fd32164a66c724a750b4c9.png',7,'Ocarina of Time',1699584000);
INSERT INTO `ci_potms` VALUES (2,'a3c2e1909b1f22164a66c724a750d1e8.png',99,'The Wind Waker',1673304000);

INSERT INTO `zfgc_wikipage` VALUES (960,6,'KoT_Master_Sword.jpg','',0,0,0.113355,0x3230323431323132313631303030,3111,123,NULL,NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3210,0x4C6F72656D20697073756D20646F6C6F722073697420616D65742C20636F6E73656374657475722061646970697363696E6720656C69742E0A5B5B43617465676F72793A4B4F5420496D616765735D5D,'utf-8');
INSERT INTO `zfgc_wikirevision` VALUES (3110,960,3210,0x6D6178696D7573206575206D6F6C6573746965,8,'MidnightMoblin',0x3230323330343035313230303030,0,0,62,0,0x30303030303030303030303030303030303030303030303030303030303030,NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3211,0x4C6F72656D20697073756D20646F6C6F722073697420616D65742C20636F6E73656374657475722061646970697363696E6720656C69742E205B5B4B4F543A4D61737465722053776F72647C4D61737465722053776F72645D5D204D6175726973206163207175616D20626C616E6469742C2074656D706F722074656C6C75732075742C2068656E647265726974206A7573746F2E0A5B5B43617465676F72793A4B4F5420496D616765735D5D,'utf-8');
INSERT INTO `zfgc_wikirevision` VALUES (3111,960,3211,0x636F6E67756520616E7465206964,77,'SheikahSlate',0x3230323431323132313631303030,0,0,123,3110,0x30303030303030303030303030303030303030303030303030303030303030,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (961,6,'Lost_Screenshot.png','',0,0,0.224466,0x3230323431323132313631303030,3112,53,NULL,NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3212,0x4375726162697475722074656D70757320706C61636572617420746F72746F722C2076656C20636F6E76616C6C697320656E696D206D6F6C6C69732076697461652E2053757370656E64697373652065676574206C6967756C61207665686963756C612C20636F6E67756520616E74652069642C2064696374756D206E6973692E,'utf-8');
INSERT INTO `zfgc_wikirevision` VALUES (3112,961,3212,'',17,'TingleFan',0x3230323530333031303930303030,0,0,53,0,0x30303030303030303030303030303030303030303030303030303030303030,NULL,NULL);
-- ==== member profile pages (Thestig = gm112's real archive article, mgzero = basic) ====
INSERT INTO `zfgc_wikipage` VALUES (970,0,'Thestig','',0,0,0.606,0x3230313330343234313432353038,3300,680,NULL,NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3400,0x7b7b5573657250726f66696c657c7573657269643d337d7d0a0a536572696f75736c792077686f2069732074686973206775793f0a3d3d20486973746f7279203d3d0a6c6f6c2e20676d3131322e0a0a416c736f2062656c6f7720697320736f6d652072616e646f6d20737475666620746861742070656f706c6520707574206f6e206d792070726f66696c652e203b70202d2d5b5b557365723a546865737469677c676d3131325d5d20285b5b557365722074616c6b3a546865737469677c676d3131325d5d292031383a34382c2033302053657074656d62657220323031322028454454290a0a3d3d204275747473203d3d0a49206861766520746f2061646d69742c2049207265616c6c7920656e6a6f792062757474732e2048657265277320616e20656e746972652073656374696f6e206465766f74656420746f206d79206c6f766520746f2062757474732e20497420616c6c2073746172746564207768656e20492077617320696e206772616465203420616e64204920736177206d79207465616368657227732062757474206279206163636964656e74207768656e206865207761732062656e64696e6720646f776e20696e2066726f6e74206f662074686520636c61737320647572696e67204d6174682e2053696e6365204920646f6e2774207265616c6c79206c696b65204d6174682c2049206465636964656420746f206c6f6f6b20617420686973206275747420696e73746561642e20446174206173732e205468617420726f756e64206275747420696e207468656d207469676874206a65616e732c206a75737420686f772049206c696b652069742e0a0a574149542041205345434f4e442c205748415420414d204920444f494e47204f4e20474d31313227532050524f46494c453f202d53746576652043616c616e6472612e,0x7574662d38);
INSERT INTO `zfgc_wikirevision` VALUES (3300,970,3400,'',98,'LorentzChronon',0x3230313330343234313432353038,0,0,680,0,0x74686573746967757365727061676566697874757265313233,NULL,NULL);
INSERT INTO `zfgc_wikipage` VALUES (971,0,'mgzero','',0,0,0.222,0x3230313530363230313230303030,3301,170,NULL,NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3401,0x7b7b5573657250726f66696c657c7573657269643d327d7d0a0a3d3d2041626f7574203d3d0a6d677a65726f2069732061206c6f6e6774696d65206d656d626572206f66205b5b5a4647435d5d2e205468697320697320612062617369632070726f66696c6520706167652e0a0a3d3d20496e74657265737473203d3d0a46616e2067616d6520646576656c6f706d656e7420616e6420636f6d6d756e6974792070726f6a656374732e,0x7574662d38);
INSERT INTO `zfgc_wikirevision` VALUES (3301,971,3401,'',2,'mgzero',0x3230313530363230313230303030,0,0,170,0,0x6d677a65726f757365727061676566697874757265313233,NULL,NULL);
-- User talk page for mgzero (migrates to a Talk: thread on the User:mgzero page, not a wiki_page)
INSERT INTO `zfgc_wikipage` VALUES (973,3,'mgzero','',0,0,0.333,0x3230313530373031313230303030,3303,8,NULL,NULL,NULL);
INSERT INTO `zfgc_wikitext` VALUES (3403,0x68652072756c657a,0x7574662d38);
INSERT INTO `zfgc_wikirevision` VALUES (3303,973,3403,'',3,'gm112',0x3230313530373031313230303030,0,0,8,0,0x74616c6b6d677a65726f68657275313233343536373839303132,NULL,NULL);
-- (all rows pruned)

-- (all rows pruned)

-- (all rows pruned)

-- (all rows pruned)

-- (all rows pruned)

-- (all rows pruned)

