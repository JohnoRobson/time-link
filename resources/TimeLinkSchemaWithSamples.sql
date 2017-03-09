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
-- Table structure for table `Budget_Hour`
--

DROP TABLE IF EXISTS `Budget_Hour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Budget_Hour` (
  `bh_id` int(11) NOT NULL AUTO_INCREMENT,
  `bh_level` int(11) DEFAULT NULL,
  `bh_wp_id` int(11) DEFAULT NULL,
  `bh_man_day` int(11) DEFAULT NULL,
  PRIMARY KEY (`bh_id`),
  UNIQUE KEY `ph_id_UNIQUE` (`bh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `prj_id` int(11) NOT NULL AUTO_INCREMENT,
  `prj_name` varchar(45) DEFAULT NULL,
  `prj_descr` varchar(45) DEFAULT NULL,
  `prj_customer` varchar(45) DEFAULT NULL,
  `prj_wp_id` int(11) DEFAULT NULL,
  `prj_manager_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`prj_id`),
  UNIQUE KEY `prjh_id_UNIQUE` (`prj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WorkPackage`
--

DROP TABLE IF EXISTS `WorkPackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkPackage` (
  `wp_id` int(11) NOT NULL AUTO_INCREMENT,
  `wp_prj_id` int(11) DEFAULT NULL,
  `wp_code` varchar(45) DEFAULT NULL,
  `wp_lvl_id` int(11) DEFAULT NULL,
  `wp_re_eng_id` int(11) DEFAULT NULL,
  `wp_descr` varchar(45) DEFAULT NULL,
  `wp_charged` tinyint(1) DEFAULT NULL,
  `wp_closed` tinyint(1) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
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
  `emp_flex_time` int(11) DEFAULT NULL,
  `emp_vacation_time` int(11) DEFAULT NULL,
  `emp_vacation_rate` int(11) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `emp_id_UNIQUE` (`emp_id`),
  UNIQUE KEY `emp_name_UNIQUE` (`emp_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
  UNIQUE KEY `lg_id_UNIQUE` (`lg_id`),
  UNIQUE KEY `lg_name_UNIQUE` (`lg_level`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supvemp`
--

DROP TABLE IF EXISTS `supvemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supvemp` (
  `se_id` int(11) NOT NULL,
  `se_supv_id` varchar(45) DEFAULT NULL,
  `se_emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`se_id`)
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
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
  `tsho_date` date DEFAULT NULL,
  `tsho_hour` float DEFAULT NULL,
  `tsho_labour_cost` float DEFAULT NULL,
  PRIMARY KEY (`tsho_id`),
  UNIQUE KEY `tsho_id_UNIQUE` (`tsho_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-05 16:14:59
  
  USE timelink_dev;
  
INSERT INTO employee VALUES (1, "Admin", "adminfname", "adminlname", 1, "a@a.com", 2, null, null, null, null, null);
INSERT INTO employee VALUES (2, "tsa", "tsafname", "tsalname", 2, "tsa@tsa.com", 1, null, null, null, null, null);
INSERT INTO employee VALUES (3, "pm", "pmfname", "pmlname", 3, "pm@pm.com", 1, null, null, null, null, null);
INSERT INTO employee VALUES (4, "re", "refname", "relname", 4, "ref@ref.com", 1, null, null, null, null, null);

INSERT INTO job_title VALUES (1, 1, 0);
INSERT INTO job_title VALUES (2, 2, 0);
INSERT INTO job_title VALUES (3, 3, 0);
INSERT INTO job_title VALUES (4, 4, 0);
INSERT INTO job_title VALUES (5, 3, 4);
INSERT INTO job_title VALUES (6, 4, 5);

INSERT INTO ts_approver VALUES (0, 2, 1);

INSERT INTO credential VALUES (1, 1, "Admin", "Admin");
INSERT INTO credential VALUES (2, 2, "tsa", "tsa");
INSERT INTO credential VALUES (3, 3, "pm", "pm");
INSERT INTO credential VALUES (4, 4, "re", "re");

INSERT INTO Project VALUES (1, "Cool Project 1", "A Cool Project", "Customer name for cool project 1", null, 3);
INSERT INTO Project VALUES (2, "Cool Project 2", "A Cool Project: the sequel", "Customer name for cool project 2", null, 3);

INSERT INTO prj_emp VALUES (0, 1, 1);
INSERT INTO prj_emp VALUES (2, 2, 1);

INSERT INTO WorkPackage VALUES (1, 1, "100000000", null, null, "Part of the cool project", 0, 0);
INSERT INTO WorkPackage VALUES (2, 1, "200000000", null, null, "second part of the cool project", 0, 0);
INSERT INTO WorkPackage VALUES (3, 2, "100000000", null, null, "work package for the cool project the sequel", 0, 0);
INSERT INTO WorkPackage VALUES (4, 2, "200000000", null, null, "second work package for the sequel", 0, 0);

INSERT INTO wp_emp VALUES (0, 1, 1);
INSERT INTO wp_emp VALUES (2, 2, 1);
INSERT INTO wp_emp VALUES (3, 3, 1);
INSERT INTO wp_emp VALUES (4, 4, 1);

INSERT INTO ts_header VALUES(0, 1, "2017-01-30", null, 2, 0, 1, 1, null);

INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 0, null);
INSERT INTO ts_hour VALUES (2, 1, 1, 1, 1, null, 0, null);
INSERT INTO ts_hour VALUES (3, 1, 1, 1, 1, null, 6, null);
INSERT INTO ts_hour VALUES (4, 1, 1, 1, 1, null, 5, null);
INSERT INTO ts_hour VALUES (5, 1, 1, 1, 1, null, 9, null);
INSERT INTO ts_hour VALUES (6, 1, 1, 1, 1, null, 6, null);
INSERT INTO ts_hour VALUES (7, 1, 1, 1, 1, null, 8, null);

INSERT INTO ts_line VALUES (0, 1, "row 1 note");

INSERT INTO labour_grade VALUES (1, null, "P1", 100);
INSERT INTO labour_grade VALUES (2, null, "P2", 200);
INSERT INTO labour_grade VALUES (3, null, "P3", 300);
INSERT INTO labour_grade VALUES (4, null, "P4", 400);
INSERT INTO labour_grade VALUES (5, null, "P5", 500);

INSERT INTO Budget_Hour VALUES(0, 1, 1, 3);
INSERT INTO Budget_Hour VALUES(2, 2, 1, 5);
