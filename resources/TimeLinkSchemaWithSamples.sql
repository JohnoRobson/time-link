DROP DATABASE IF EXISTS `timelink_dev`;

CREATE DATABASE  IF NOT EXISTS `timelink_dev` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `timelink_dev`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 138.197.142.3    Database: timelink_dev
-- ------------------------------------------------------
-- Server version	5.7.17

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
-- Table structure for table `admin_message`
--

DROP TABLE IF EXISTS `admin_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_message` (
  `am_id` int(11) NOT NULL AUTO_INCREMENT,
  `am_user_name` varchar(45) DEFAULT NULL,
  `am_user_email` varchar(45) DEFAULT NULL,
  `am_issue_title` varchar(45) DEFAULT NULL,
  `am_issue_content` varchar(255) DEFAULT NULL,
  `am_isread` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`am_id`),
  UNIQUE KEY `bb_id_UNIQUE` (`am_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_message`
--

--
-- Table structure for table `budget_project_work_day`
--

DROP TABLE IF EXISTS `budget_project_work_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budget_project_work_day` (
  `bpd_id` int(11) NOT NULL AUTO_INCREMENT,
  `bpd_prj_id` int(11) DEFAULT NULL,
  `bpd_level` int(11) DEFAULT NULL,
  `bpd_man_day` int(11) DEFAULT NULL,
  `bpd_date_created` date DEFAULT NULL,
  PRIMARY KEY (`bpd_id`),
  UNIQUE KEY `bpd_id_UNIQUE` (`bpd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `budget_wp_work_day`
--

DROP TABLE IF EXISTS `budget_wp_work_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budget_wp_work_day` (
  `bwd_id` int(11) NOT NULL AUTO_INCREMENT,
  `bwd_wp_id` int(11) DEFAULT NULL,
  `bwd_level` int(11) DEFAULT NULL,
  `bwd_man_day` int(11) DEFAULT NULL,
  `bwd_date_created` date DEFAULT NULL,
  PRIMARY KEY (`bwd_id`),
  UNIQUE KEY `bwd_id_UNIQUE` (`bwd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `estimate_wp_work_day`
--

DROP TABLE IF EXISTS `estimate_wp_work_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estimate_wp_work_day` (
  `ewd_id` int(11) NOT NULL AUTO_INCREMENT,
  `ewd_wp_id` int(11) DEFAULT NULL,
  `ewd_level` int(11) DEFAULT NULL,
  `ewd_man_day` int(11) DEFAULT NULL,
  `ewd_date_created` date DEFAULT NULL,
  PRIMARY KEY (`ewd_id`),
  UNIQUE KEY `ewd_id_UNIQUE` (`ewd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `prj_id` int(11) NOT NULL AUTO_INCREMENT,
  `prj_name` varchar(45) DEFAULT NULL,
  `prj_descr` varchar(45) DEFAULT NULL,
  `prj_customer` varchar(45) DEFAULT NULL,
  `prj_wp_id` int(11) DEFAULT NULL,
  `prj_manager_id` int(11) DEFAULT NULL,
  `prj_manager_assist_id` int(11) DEFAULT NULL,
  `prj_closed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`prj_id`),
  UNIQUE KEY `prjh_id_UNIQUE` (`prj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WorkPackage`
--

DROP TABLE IF EXISTS `workpackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workpackage` (
  `wp_id` int(11) NOT NULL AUTO_INCREMENT,
  `wp_prj_id` int(11) DEFAULT NULL,
  `wp_code` varchar(45) DEFAULT NULL,
  `wp_lvl_id` int(11) DEFAULT NULL,
  `wp_re_eng_id` int(11) DEFAULT NULL,
  `wp_descr` varchar(45) DEFAULT NULL,
  `wp_charged` tinyint(1) DEFAULT NULL,
  `wp_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`wp_id`),
  UNIQUE KEY `wph_id_UNIQUE` (`wp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cal_week`
--

DROP TABLE IF EXISTS `cal_week`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cal_week` (
  `cw_id` int(11) NOT NULL AUTO_INCREMENT,
  `cw_year` year(4) DEFAULT NULL,
  `cw_month` date DEFAULT NULL,
  `cw_week_num` int(11) DEFAULT NULL,
  `cw_week_from` date DEFAULT NULL,
  `cw_week_to` date DEFAULT NULL,
  PRIMARY KEY (`cw_id`),
  UNIQUE KEY `cw_id_UNIQUE` (`cw_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credential`
--

DROP TABLE IF EXISTS `credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credential` (
  `cre_id` int(11) NOT NULL AUTO_INCREMENT,
  `cre_emp_id` int(11) DEFAULT NULL,
  `cre_emp_user_id` varchar(45) DEFAULT NULL,
  `cre_pw` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cre_id`),
  UNIQUE KEY `cre_emp_id_UNIQUE` (`cre_id`),
  UNIQUE KEY `cre_emp_name_UNIQUE` (`cre_emp_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_user_id` varchar(45) DEFAULT NULL,
  `emp_fname` varchar(45) DEFAULT NULL,
  `emp_lname` varchar(45) DEFAULT NULL,
  `emp_jt_id` varchar(45) DEFAULT NULL,
  `emp_email` varchar(45) DEFAULT NULL,
  `emp_lg_id` int(11) DEFAULT NULL,
  `emp_effect_from` date DEFAULT NULL,
  `emp_effect_to` date DEFAULT NULL,
  `emp_flex_time` float DEFAULT NULL,
  `emp_vacation_time` float DEFAULT NULL,
  `emp_vacation_rate` float DEFAULT NULL,
  `emp_default_tsh_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `emp_id_UNIQUE` (`emp_id`),
  UNIQUE KEY `emp_name_UNIQUE` (`emp_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `job_title`
--

DROP TABLE IF EXISTS `job_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_title` (
  `jt_id` int(11) NOT NULL AUTO_INCREMENT,
  `jt_emp_id` int(11) DEFAULT NULL,
  `jt_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`jt_id`),
  UNIQUE KEY `jt_id_UNIQUE` (`jt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `labour_grade`
--

DROP TABLE IF EXISTS `labour_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `labour_grade` (
  `lg_id` int(11) NOT NULL AUTO_INCREMENT,
  `lg_year` int(11) DEFAULT NULL,
  `lg_level` varchar(45) DEFAULT NULL,
  `lg_cost` float DEFAULT NULL,
  PRIMARY KEY (`lg_id`),
  UNIQUE KEY `lg_id_UNIQUE` (`lg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prj_emp`
--

DROP TABLE IF EXISTS `prj_emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prj_emp` (
  `pe_id` int(11) NOT NULL AUTO_INCREMENT,
  `pe_prj_id` int(11) DEFAULT NULL,
  `pe_emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pe_id`),
  UNIQUE KEY `prjl_id_UNIQUE` (`pe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supvemp`
--

DROP TABLE IF EXISTS `supvemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supvemp` (
  `se_id` int(11) NOT NULL AUTO_INCREMENT,
  `se_supv_id` int(11) DEFAULT NULL,
  `se_emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`se_id`),
  UNIQUE KEY `se_id_UNIQUE` (`se_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ts_approver`
--

DROP TABLE IF EXISTS `ts_approver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_approver` (
  `tsa_id` int(11) NOT NULL AUTO_INCREMENT,
  `tsa_appr_id` int(11) DEFAULT NULL,
  `tsa_emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tsa_id`),
  UNIQUE KEY `tsa_id_UNIQUE` (`tsa_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ts_header`
--

DROP TABLE IF EXISTS `ts_header`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_header` (
  `tsh_id` int(11) NOT NULL AUTO_INCREMENT,
  `tsh_emp_Id` int(11) DEFAULT NULL,
  `tsh_date_created` date DEFAULT NULL,
  `tsh_cw_id` int(11) DEFAULT NULL,
  `tsh_approver_id` int(11) DEFAULT NULL,
  `tsh_overtime` float DEFAULT NULL,
  `tsh_flextime` float DEFAULT NULL,
  `tsh_status` varchar(10) DEFAULT NULL,
  `tsh_rejectreason` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`tsh_id`),
  UNIQUE KEY `tsh_id_UNIQUE` (`tsh_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ts_hour`
--

DROP TABLE IF EXISTS `ts_hour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_hour` (
  `tsho_id` int(11) NOT NULL AUTO_INCREMENT,
  `tsh_id` int(11) DEFAULT NULL,
  `tsho_prjh_id` int(11) DEFAULT NULL,
  `tsho_wp_id` int(11) DEFAULT NULL,
  `tsl_id` int(11) DEFAULT NULL,
  `lg_id` int(11) DEFAULT NULL,
  `tsho_date` date DEFAULT NULL,
  `tsho_hour` float DEFAULT NULL,
  `tsho_labour_cost` float DEFAULT NULL,
  PRIMARY KEY (`tsho_id`),
  UNIQUE KEY `tsho_id_UNIQUE` (`tsho_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ts_line`
--

DROP TABLE IF EXISTS `ts_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_line` (
  `tsl_id` int(11) NOT NULL AUTO_INCREMENT,
  `tsl_tsh_id` int(11) DEFAULT NULL,
  `tsl_note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tsl_id`),
  UNIQUE KEY `tsl_id_UNIQUE` (`tsl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wp_emp`
--

DROP TABLE IF EXISTS `wp_emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wp_emp` (
  `we_id` int(11) NOT NULL AUTO_INCREMENT,
  `we_wp_id` int(11) DEFAULT NULL,
  `we_emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`we_id`),
  UNIQUE KEY `wpl_id_UNIQUE` (`we_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-16 19:14:30


  
  USE timelink_dev;
  
INSERT INTO employee VALUES (1, "Admin", "Andy", "Admin", null, "a@a.com", 2, null, null, 100, 100, 120, null);
INSERT INTO employee VALUES (2, "tsa", "Tessa", "Timesheet Approver", null, "tsa@tsa.com", 1, null, null, 100, 100, 80, null);
INSERT INTO employee VALUES (3, "pm", "Marcus", "Project Manager", null, "pm@pm.com", 3, null, null, 100, 100, 160, null);
INSERT INTO employee VALUES (4, "re", "Rhodey", "Responsible Engineer", null, "ref@ref.com", 2, null, null, 100, 100, 120, null);
INSERT INTO employee VALUES (5, "hr", "Harry", "Human Resources", null, "hr@hr.com", 1, null, null, 72, 30, 120, null);
INSERT INTO employee VALUES (6, "sup", "Suzie", "Supervisor", null, "sup@sup.com", 1, null, null, 72, 30, 120, null);
INSERT INTO employee VALUES (7, "pma", "Penny", "Project Manager Assistant", null, "pma@pma.com", 1, null, null, 72, 30, 120, null);

INSERT INTO job_title VALUES (1, 1, 0);
INSERT INTO job_title VALUES (2, 2, 0);
INSERT INTO job_title VALUES (3, 3, 0);
INSERT INTO job_title VALUES (4, 4, 0);
INSERT INTO job_title VALUES (5, 3, 4);
INSERT INTO job_title VALUES (6, 4, 5);
INSERT INTO job_title VALUES (7, 5, 1);
INSERT INTO job_title VALUES (8, 1, 2);
INSERT INTO job_title VALUES (9, 1, 3);
INSERT INTO job_title VALUES (10, 1, 4);
INSERT INTO job_title VALUES (11, 1, 5);
INSERT INTO job_title VALUES (12, 6, 2);
INSERT INTO job_title VALUES (13, 6, 0);
INSERT INTO job_title VALUES (14, 5, 0);

INSERT INTO supvemp VALUES (1, 6, 1);
INSERT INTO supvemp VALUES (2, 6, 2);
INSERT INTO supvemp VALUES (3, 6, 3);
INSERT INTO supvemp VALUES (4, 6, 4);
INSERT INTO supvemp VALUES (5, 6, 7);

INSERT INTO ts_approver VALUES (1, 2, 1);
INSERT INTO ts_approver VALUES (2, 2, 3);
INSERT INTO ts_approver VALUES (3, 2, 4);
INSERT INTO ts_approver VALUES (4, 2, 5);
INSERT INTO ts_approver VALUES (5, 2, 6);
INSERT INTO ts_approver VALUES (6, 2, 7);

INSERT INTO credential VALUES (1, 1, "Admin", "Admin");
INSERT INTO credential VALUES (2, 2, "tsa", "tsa");
INSERT INTO credential VALUES (3, 3, "pm", "pm");
INSERT INTO credential VALUES (4, 4, "re", "re");
INSERT INTO credential VALUES (5, 5, "hr", "hr");
INSERT INTO credential VALUES (6, 6, "sup", "sup");
INSERT INTO credential VALUES (7, 7, "pma", "pma");

INSERT INTO project VALUES (1, "001", "TimeLink", "TimeLink", null, 3, 7, 0);
INSERT INTO project VALUES (2, "002", "Responsible Students", "BCIT", null, 3, 7, 0);
INSERT INTO project VALUES (3, "003", "Hungry Electron", "BCIT", null, 3, 7, 0);
INSERT INTO project VALUES (10, "010", "HR Codes", "TimeLink", null, 5, 5, 0);

INSERT INTO prj_emp VALUES (18, 1, 1);
INSERT INTO prj_emp VALUES (1, 1, 2);
INSERT INTO prj_emp VALUES (2, 1, 3);
INSERT INTO prj_emp VALUES (3, 1, 4);
INSERT INTO prj_emp VALUES (4, 1, 6);
INSERT INTO prj_emp VALUES (5, 1, 7);
INSERT INTO prj_emp VALUES (6, 2, 1);
INSERT INTO prj_emp VALUES (7, 2, 2);
INSERT INTO prj_emp VALUES (8, 2, 3);
INSERT INTO prj_emp VALUES (9, 2, 4);
INSERT INTO prj_emp VALUES (10, 2, 6);
INSERT INTO prj_emp VALUES (11, 2, 7);
INSERT INTO prj_emp VALUES (12, 3, 1);
INSERT INTO prj_emp VALUES (13, 3, 2);
INSERT INTO prj_emp VALUES (14, 3, 3);
INSERT INTO prj_emp VALUES (15, 3, 4);
INSERT INTO prj_emp VALUES (16, 3, 6);
INSERT INTO prj_emp VALUES (17, 3, 7);

INSERT INTO workpackage VALUES (1, 10, "SICK", null, 5, "Sick Leave", 0, 5);
INSERT INTO workpackage VALUES (2, 1, "1000000", null, 4, "Inception", 0, 0);
INSERT INTO workpackage VALUES (3, 1, "2000000", null, 4, "Elaboration", 0, 0);
INSERT INTO workpackage VALUES (4, 2, "1000000", null, 4, "A Work Package", 0, 0);
INSERT INTO workpackage VALUES (5, 2, "2000000", null, 4, "Another Work Package", 0, 0);
INSERT INTO workpackage VALUES (6, 10, "FLEX", null, 5, "Flextime", 0, 5);
INSERT INTO workpackage VALUES (7, 10, "VACA", null, 5, "Vacation", 0, 5);
INSERT INTO workpackage VALUES (8, 10, "STAT", null, 5, "Statutory Holiday", 0, 5);
INSERT INTO workpackage VALUES (9, 10, "LDIS", null, 5, "Long-term Disability", 0, 5);
INSERT INTO workpackage VALUES (10, 10, "SDIS", null, 5, "Short-Term Disability", 0, 5);
INSERT INTO workpackage VALUES (11, 1, "3000000", null, 4, "Construction", 0, 0);
INSERT INTO workpackage VALUES (12, 1, "4000000", null, 4, "Transition", 0, 0);
INSERT INTO workpackage VALUES (13, 1, "2100000", null, 4, "Software Development Plan", 0, 0);
INSERT INTO workpackage VALUES (14, 1, "2F00000", null, 4, "Use Cases", 0, 0);

INSERT INTO wp_emp VALUES (18, 4, 1);
INSERT INTO wp_emp VALUES (1, 4, 2);
INSERT INTO wp_emp VALUES (2, 4, 3);
INSERT INTO wp_emp VALUES (3, 4, 4);
INSERT INTO wp_emp VALUES (4, 4, 6);
INSERT INTO wp_emp VALUES (5, 4, 7);
INSERT INTO wp_emp VALUES (6, 2, 1);
INSERT INTO wp_emp VALUES (7, 2, 2);
INSERT INTO wp_emp VALUES (8, 2, 3);
INSERT INTO wp_emp VALUES (9, 2, 4);
INSERT INTO wp_emp VALUES (10, 2, 6);
INSERT INTO wp_emp VALUES (11, 2, 7);
INSERT INTO wp_emp VALUES (12, 3, 1);
INSERT INTO wp_emp VALUES (13, 3, 2);
INSERT INTO wp_emp VALUES (14, 3, 3);
INSERT INTO wp_emp VALUES (15, 3, 4);
INSERT INTO wp_emp VALUES (16, 3, 6);
INSERT INTO wp_emp VALUES (17, 3, 7);
INSERT INTO wp_emp VALUES (20, 5, 3);
INSERT INTO wp_emp VALUES (21, 5, 4);
INSERT INTO wp_emp VALUES (22, 5, 7);
INSERT INTO wp_emp VALUES (23, 11, 3);
INSERT INTO wp_emp VALUES (24, 11, 4);
INSERT INTO wp_emp VALUES (25, 12, 3);
INSERT INTO wp_emp VALUES (26, 12, 4);
INSERT INTO wp_emp VALUES (27, 13, 3);
INSERT INTO wp_emp VALUES (28, 13, 4);
INSERT INTO wp_emp VALUES (29, 14, 3);
INSERT INTO wp_emp VALUES (30, 14, 4);

INSERT INTO labour_grade VALUES (1, 2017, "P1", 100);
INSERT INTO labour_grade VALUES (2, 2017, "P2", 200);
INSERT INTO labour_grade VALUES (3, 2017, "P3", 300);
INSERT INTO labour_grade VALUES (4, 2017, "P4", 400);
INSERT INTO labour_grade VALUES (5, 2017, "P5", 500);
INSERT INTO labour_grade VALUES (6, 2018, "P1", 150);
INSERT INTO labour_grade VALUES (7, 2018, "P2", 250);
INSERT INTO labour_grade VALUES (8, 2018, "P3", 350);
INSERT INTO labour_grade VALUES (9, 2018, "P4", 450);
INSERT INTO labour_grade VALUES (10, 2018, "P5", 550);
