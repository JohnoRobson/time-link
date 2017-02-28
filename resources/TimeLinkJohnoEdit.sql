CREATE SCHEMA `timelink_dev` ;

-- employee
CREATE TABLE `timelink_dev`.`employee` (
  `emp_id` INT NOT NULL AUTO_INCREMENT,
  `emp_name` VARCHAR(45) NULL,  
  `emp_fname` VARCHAR(45) NULL,  
  `emp_lname` VARCHAR(45) NULL,  
  `emp_jt_id` VARCHAR(45) NULL,  
  `emp_email` VARCHAR(45) NULL,  
  `emp_lg_id` INT NULL,  
  `effect_from` DATE NULL,  
  `effect_to` DATE NULL,  
  PRIMARY KEY (`emp_id`),  
  UNIQUE INDEX `emp_id_UNIQUE` (`emp_id` ASC),  
  UNIQUE INDEX `emp_name_UNIQUE` (`emp_name` ASC));

-- job_title
CREATE TABLE `timelink_dev`.`job_title` (
  `jt_id` INT NOT NULL AUTO_INCREMENT,
  `jt_code` INT NOT NULL,
  `jt_descr` INT NOT NULL,
  -- `jt_descr` VARCHAR(45) NULL,
  PRIMARY KEY (`jt_id`),
  -- UNIQUE INDEX `jt_code_UNIQUE` (`jt_code` ASC),
  UNIQUE INDEX `jt_id_UNIQUE` (`jt_id` ASC));

 -- ts_approver
  CREATE TABLE `timelink_dev`.`ts_approver` (
  `tsa_id` INT NOT NULL AUTO_INCREMENT,
  `tsa_appr_emp_id` INT NULL,
  `tsa_emp_id` INT NULL,
  PRIMARY KEY (`tsa_id`),
  UNIQUE INDEX `tsa_id_UNIQUE` (`tsa_id` ASC));

-- TS_Header
  CREATE TABLE `timelink_dev`.`ts_header` (
  `tsh_id` INT NOT NULL AUTO_INCREMENT,
  `tsh_emp_Id` INT NULL,
  `tsh_date_created` DATE NULL,
  `tsh_cw_id` INT NULL,
  `tsh_overtime` FLOAT NULL,
  `tsh_flextime` FLOAT NULL,
  `tsh_status` VARCHAR(10) NULL,
  PRIMARY KEY (`tsh_id`),
  UNIQUE INDEX `tsh_id_UNIQUE` (`tsh_id` ASC));

-- Cal_Week
CREATE TABLE `timelink_dev`.`cal_week` (
  `cw_id` INT NOT NULL AUTO_INCREMENT,
  `cw_year` YEAR NULL,
  `cw_month` DATE NULL,
  `cw_week_num` INT NULL,
  `cw_week_from` DATE NULL,
  `cw_week_to` DATE NULL,
  PRIMARY KEY (`cw_id`),
  UNIQUE INDEX `cw_id_UNIQUE` (`cw_id` ASC));

-- Credential
CREATE TABLE `timelink_dev`.`credential` (
  `cre_emp_id` INT NOT NULL AUTO_INCREMENT,
  `cre_emp_name` VARCHAR(45) NULL,
  `cre_pw` VARCHAR(45) NULL,
  PRIMARY KEY (`cre_emp_id`),
  UNIQUE INDEX `cre_emp_id_UNIQUE` (`cre_emp_id` ASC),
  UNIQUE INDEX `cre_emp_name_UNIQUE` (`cre_emp_name` ASC));

 -- ts_hour
  CREATE TABLE `timelink_dev`.`ts_hour` (
  `tsho_id` INT NOT NULL AUTO_INCREMENT,
  `tsh_id` INT NULL,
  `tsho_prjh_id` INT NULL,
  `tsho_wp_id` INT NULL,
  `tsl_id` INT NULL,
  `tsho_date` DATE NULL,
  `tsho_hour` FLOAT NULL,
  PRIMARY KEY (`tsho_id`),
  UNIQUE INDEX `tsho_id_UNIQUE` (`tsho_id` ASC));

-- TS_Line
CREATE TABLE `timelink_dev`.`ts_line` (
  `tsl_id` INT NOT NULL AUTO_INCREMENT,
  `tsl_tsh_id` INT NULL,
  `tsl_note` VARCHAR(45) NULL,
  PRIMARY KEY (`tsl_id`),
  UNIQUE INDEX `tsl_id_UNIQUE` (`tsl_id` ASC));

  -- Labour_Grade
  CREATE TABLE `timelink_dev`.`labour_grade` (
  `lg_id` INT NOT NULL AUTO_INCREMENT,
  `lg_name` VARCHAR(45) NULL,
  `lg_cost_rate` FLOAT NULL,
  PRIMARY KEY (`lg_id`),
  UNIQUE INDEX `lg_id_UNIQUE` (`lg_id` ASC),
  UNIQUE INDEX `lg_name_UNIQUE` (`lg_name` ASC));

-- Emp_Prj
CREATE TABLE `timelink_dev`.`emp_proj` (
  `empprj_id` INT NOT NULL AUTO_INCREMENT,
  `empprj_code` VARCHAR(45) NULL,
  `empprj_emp_id` INT NULL,
  PRIMARY KEY (`empprj_id`),
  UNIQUE INDEX `empprj_id_UNIQUE` (`empprj_id` ASC),
  UNIQUE INDEX `empprj_code_UNIQUE` (`empprj_code` ASC));


-- Prj_Header
CREATE TABLE `timelink_dev`.`prj_header` (
  `prjh_id` INT NOT NULL AUTO_INCREMENT,
  `prjh_name` VARCHAR(45) NULL,
  `prjh_descr` VARCHAR(45) NULL,
  `prjh_customer` VARCHAR(45) NULL,
  `prjh_wph_id` INT NULL,
  `prjh_manager_id` INT NULL,
  PRIMARY KEY (`prjh_id`),
  UNIQUE INDEX `prjh_id_UNIQUE` (`prjh_id` ASC));

 -- WP_Header
  CREATE TABLE `timelink_dev`.`wp_header` (
  `wph_id` INT NOT NULL AUTO_INCREMENT,
  `wph_prjh_id` INT NULL,
  `wph_code` VARCHAR(45) NULL,
  `wph_descr` VARCHAR(45) NULL,
  PRIMARY KEY (`wph_id`),
  UNIQUE INDEX `wph_id_UNIQUE` (`wph_id` ASC));

-- Prj_Line
CREATE TABLE `timelink_dev`.`prj_line` (
  `prjl_id` INT NOT NULL AUTO_INCREMENT,
  `prjl_prjh_id` INT NULL,
  `prjl_emp_id` INT NULL,
  PRIMARY KEY (`prjl_id`),
  UNIQUE INDEX `prjl_id_UNIQUE` (`prjl_id` ASC));

  -- WP_Line
  CREATE TABLE `timelink_dev`.`wp_line` (
  `wpl_id` INT NOT NULL AUTO_INCREMENT,
  `wpl_wph_id` INT NULL,
  `wpl_emp_id` INT NULL,
  PRIMARY KEY (`wpl_id`),
  UNIQUE INDEX `wpl_id_UNIQUE` (`wpl_id` ASC));
  
  -- Plan_Hour
  CREATE TABLE `timelink_dev`.`plan_hour` (
  `ph_id` INT NOT NULL AUTO_INCREMENT,
  `ph_level` INT NULL,
  `ph_prjl_id` INT NULL,
  `ph_wpl_id` INT NULL,
  `ph_man_day` INT NULL,
  PRIMARY KEY (`ph_id`),
  UNIQUE INDEX `ph_id_UNIQUE` (`ph_id` ASC));
  
  USE timelink_dev;
  
INSERT INTO employee VALUES (0, null, "adminfname", "adminlname", 1, null, null, null, null);
INSERT INTO employee VALUES (0, null, "tsafname", "tsalname", 2, null, null, null, null);
INSERT INTO employee VALUES (0, null, "pmfname", "pmlname", 3, null, null, null, null);
INSERT INTO employee VALUES (0, null, "refname", "relname", 4, null, null, null, null);

INSERT INTO job_title VALUES (0, 0, 1);
INSERT INTO job_title VALUES (0, 0, 2);
INSERT INTO job_title VALUES (0, 0, 3);
INSERT INTO job_title VALUES (0, 0, 4);
INSERT INTO job_title VALUES (0, 4, 3);
INSERT INTO job_title VALUES (0, 5, 4);

INSERT INTO ts_approver VALUES (0, 2, 1);

INSERT INTO credential VALUES (1, "Admin", "Admin");
INSERT INTO credential VALUES (2, "tsa", "tsa");
INSERT INTO credential VALUES (3, "pm", "pm");
INSERT INTO credential VALUES (4, "re", "re");

INSERT INTO prj_header VALUES (0, "Cool Project 1", "A Cool Project", "Customer name for cool project 1", 1, 3);
INSERT INTO prj_header VALUES (0, "Cool Project 2", "A Cool Project: the sequel", "Customer name for cool project 2", 2, 3);

INSERT INTO prj_line VALUES (0, 1, 1);
INSERT INTO prj_line VALUES (0, 2, 1);

INSERT INTO wp_header VALUES (0, 1, "proj1wp1", "Part of the cool project");
INSERT INTO wp_header VALUES (0, 1, "proj1wp2", "second part of the cool project");
INSERT INTO wp_header VALUES (0, 2, "proj2wp1", "work package for the cool project the sequel");
INSERT INTO wp_header VALUES (0, 2, "proj2wp2", "second work package for the sequel");

INSERT INTO wp_line VALUES (0, 1, 1);
INSERT INTO wp_line VALUES (0, 2, 1);
INSERT INTO wp_line VALUES (0, 3, 1);
INSERT INTO wp_line VALUES (0, 4, 1);

INSERT INTO ts_header VALUES(0, 1, "2017-01-30", null, 0, 0, 1);

INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 0);
INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 0);
INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 6);
INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 5);
INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 9);
INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 6);
INSERT INTO ts_hour VALUES (0, 1, 1, 1, 1, null, 8);

INSERT INTO ts_line VALUES (0, 1, "row 1 note");

INSERT INTO labour_grade VALUES (0, "P1", 100);
INSERT INTO labour_grade VALUES (0, "P2", 200);
INSERT INTO labour_grade VALUES (0, "P3", 300);
INSERT INTO labour_grade VALUES (0, "P4", 400);
INSERT INTO labour_grade VALUES (0, "P5", 500);

INSERT INTO plan_hour VALUES(0, 1, 1, 1, 3);
INSERT INTO plan_hour VALUES(0, 2, 1, 1, 5);