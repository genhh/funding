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

DROP TABLE if EXISTS t_menu;
CREATE TABLE t_menu
(id INT(11) NOT NULL auto_increment, pid INT(11), name VARCHAR(200), url VARCHAR(200), icon VARCHAR(200), PRIMARY KEY (id));
INSERT INTO `t_menu`
(`id`, `pid`, `name`, `icon`, `url`)
values
('1',NULL,'系统权限菜单','glyphicon glyphicon-th-list',NULL),
('2','1',' 控 制 面 板 ','glyphicon glyphicon-dashboard','main.htm'),
('3','1','权限管理','glyphicon glyphicon glyphicon-tasks',NULL),
('4','3',' 用 户 维 护 ','glyphicon glyphicon-user','user/index.htm'),
('5','3',' 角 色 维 护 ','glyphicon glyphicon-king','role/index.htm'),
('6','3',' 菜 单 维 护 ','glyphicon glyphicon-lock','permission/index.htm'),
('7','1',' 业 务 审 核 ','glyphicon glyphicon-ok',NULL),
('8','7','实名认证审核','glyphicon glyphicon-check','auth_cert/index.htm'),
('9','7',' 广 告 审 核 ','glyphicon glyphicon-check','auth_adv/index.htm'),
('10','7',' 项 目 审 核 ','glyphicon glyphicon-check','auth_project/index.htm'),
('11','1',' 业 务 管 理 ','glyphicon glyphicon-th-large',NULL),
('12','11',' 资 质 维 护 ','glyphicon glyphicon-picture','cert/index.htm'),
('13','11',' 分 类 管 理 ','glyphicon glyphicon-equalizer','certtype/index.htm'),
('14','11',' 流 程 管 理 ','glyphicon glyphicon-random','process/index.htm'),
('15','11',' 广 告 管 理 ','glyphicon glyphicon-hdd','advert/index.htm'),
('16','11',' 消 息 模 板 ','glyphicon glyphicon-comment','message/index.htm'),
('17','11',' 项 目 分 类 ','glyphicon glyphicon-list','projectType/index.htm'),
('18','11',' 项 目 标 签 ','glyphicon glyphicon-tags','tag/index.htm'),
('19','1',' 参 数 管 理 ','glyphicon glyphicon-list-alt','param/index.htm');

DROP TABLE if EXISTS inner_admin_role;
CREATE TABLE inner_admin_role ( `id` INT NOT NULL AUTO_INCREMENT,
`admin_id` INT, `role_id` INT, PRIMARY KEY (`id`) );

DROP TABLE if EXISTS t_auth;
CREATE TABLE `t_auth` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(200) DEFAULT NULL,
`title` varchar(200) DEFAULT NULL,
`category_id` int(11) DEFAULT NULL,
PRIMARY KEY (`id`)
);
INSERT INTO t_auth(id,`name`,title,category_id) VALUES(1,'','用户模块',NULL);
INSERT INTO t_auth(id,`name`,title,category_id) VALUES(2,'user:delete','删除',1);
INSERT INTO t_auth(id,`name`,title,category_id) VALUES(3,'user:get','查询',1);
INSERT INTO t_auth(id,`name`,title,category_id) VALUES(4,'','角色模块',NULL);
INSERT INTO t_auth(id,`name`,title,category_id) VALUES(5,'role:delete','删除',4);
INSERT INTO t_auth(id,`name`,title,category_id) VALUES(6,'role:get','查询',4);
INSERT INTO t_auth(id,`name`,title,category_id) VALUES(7,'role:add','新增',4);

DROP TABLE if EXISTS inner_role_auth;
CREATE TABLE inner_role_auth ( `id` INT NOT NULL AUTO_INCREMENT,
`role_id` INT, `auth_id` INT, PRIMARY KEY (`id`) );

ALTER TABLE `project_crowd`.`t_admin` CHANGE `user_pswd` `user_pswd` CHAR(100) CHARSET
utf8 COLLATE utf8_general_ci NOT NULL;
