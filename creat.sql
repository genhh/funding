CREATE DATABASE IF NOT EXISTS `project_crowd`  CHARACTER SET utf8;
USE project_crowd;
DROP TABLE if EXISTS t_admin;
CREATE TABLE t_admin
(
id int NOT NULL auto_increment, # 主键
login_acct varchar(255) NOT NULL, # 登录账号
user_pswd char(32) NOT NULL, # 登录密码
user_name varchar(255) NOT NULL, # 昵称
email varchar(255) NOT NULL, # 邮件地址
create_time char(19), # 创建时间
PRIMARY KEY (id)
);
INSERT INTO t_admin  VALUES(1,'admin','E10ADC3949BA59ABBE56E057F20F883E','testAdminer','222@examp.com','4-4');#pswd:123456
#后续的插入用户案例在com.zh.funding.test
ALTER TABLE `t_admin` ADD UNIQUE INDEX (`login_acct`);
DROP TABLE if EXISTS t_role;
CREATE TABLE `t_role` ( `id` INT(11) NOT NULL AUTO_INCREMENT, `name` CHAR(100), PRIMARY KEY (`id`) );