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
INSERT INTO t_admin  VALUES(1,'admin','E10ADC3949BA59ABBE56E057F20F883E','testAdminer','222@examp.com','4-4');#pswd:123456,encoder:md5
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

CREATE TABLE t_member
(id INT(11) NOT NULL auto_increment,
loginacct VARCHAR(255) NOT NULL,
userpswd CHAR(200) NOT NULL,
username VARCHAR(255),
email VARCHAR(255),
authstatus INT(4) comment '实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证',
usertype INT(4) comment ' 0 - 个人， 1 - 企业',
realname VARCHAR(255),
cardnum VARCHAR(255),
accttype INT(4) comment '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府',
PRIMARY KEY (id));
# 分类表
create table t_type
(
id int(11) not null auto_increment,
name varchar(255) comment '分类名称',
remark varchar(255) comment '分类介绍',
primary key (id)
);
INSERT INTO `project_crowd`.`t_type` (`id`, `name`) VALUES ('1', '科技');
INSERT INTO `project_crowd`.`t_type` (`id`, `name`) VALUES ('2', '设计');
INSERT INTO `project_crowd`.`t_type` (`id`, `name`) VALUES ('3', '公益');
INSERT INTO `project_crowd`.`t_type` (`id`, `name`) VALUES ('4', '农业');

# 项目分类中间表
create table t_project_type(
id int not null auto_increment,
projectid int(11),
typeid int(11),
primary key (id)
);

# 标签表
create table t_tag(
id int(11) not null auto_increment,
pid int(11),
name varchar(255),
primary key (id)
);

# 项目标签中间表
create table t_project_tag(
id int(11) not null auto_increment,
projectid int(11),
tagid int(11),
primary key (id)
);

# 项目表
create table t_project(
id int(11) not null auto_increment,
project_name varchar(255) comment '项目名称',
project_description varchar(255) comment '项目描述',
money bigint (11) comment '筹集金额',
day int(11) comment '筹集天数',
status int(4) comment '0-即将开始，1-众筹中，2-众筹成功，3-众筹失败',
deploydate varchar(10) comment '项目发起时间',
supportmoney bigint(11) comment '已筹集到的金额',
supporter int(11) comment '支持人数',
completion int(3) comment '百分比完成度',
memberid int(11) comment '发起人的会员 id',
createdate varchar(19) comment '项目创建时间',
follower int(11) comment '关注人数',
header_picture_path varchar(255) comment '头图路径',
primary key (id)
);

# 项目表项目详情图片表
create table t_project_item_pic
(
id int(11) not null auto_increment,
projectid int(11),
item_pic_path varchar(255),
primary key (id)
);

# 项目发起人信息表
create table t_member_launch_info(
id int(11) not null auto_increment,
memberid int(11) comment '会员 id',
description_simple varchar(255) comment '简单介绍',
description_detail varchar(255) comment '详细介绍',
phone_num varchar(255) comment '联系电话',
service_num varchar(255) comment '客服电话',
primary key (id)
);
ALTER TABLE `project_crowd`.`t_member_launch_info` ADD UNIQUE INDEX `memberid_UNIQUE` (`memberid` ASC) VISIBLE;


# 回报信息表
create table t_return(
id int(11) not null auto_increment,
projectid int(11),
type int(4) comment '0 - 实物回报， 1 虚拟物品回报',
supportmoney int(11) comment '支持金额',
content varchar(255) comment '回报内容',
count int(11) comment '回报产品限额，“0”为不限回报数量',
signalpurchase int(11) comment '是否设置单笔限购',
purchase int(11) comment '具体限购数量',
freight int(11) comment '运费，“0”为包邮',
invoice int(4) comment '0 - 不开发票， 1 - 开发票',
returndate int(11) comment '项目结束后多少天向支持者发送回报',
describ_pic_path varchar(255) comment '说明图片路径',
primary key (id)
);

#  发起人确认信息表
create table t_member_confirm_info(
id int(11) not null auto_increment,
memberid int(11) comment '会员 id',
paynum varchar(200) comment '易付宝企业账号',
cardnum varchar(200) comment '法人身份证号',
primary key (id)
);

# 订单表
drop table if exists t_order;
CREATE TABLE t_order(
	id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	order_num CHAR(100) COMMENT '订单号',
	pay_order_num CHAR(100) COMMENT '支付宝流水号',
	order_amount DOUBLE(10,5) COMMENT '订单金额',
	invoice INT COMMENT '是否开发票（0 不开， 1 开） ',
	invoice_title CHAR(100) COMMENT '发票抬头',
	order_remark CHAR(100) COMMENT '订单备注',
	address_id CHAR(100) COMMENT '收货地址 id',
	PRIMARY KEY (id)
);

# 收货地址表
drop table if exists t_address;
CREATE TABLE t_address(
	id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	receive_name CHAR(100) COMMENT '收件人',
	phone_num CHAR(100) COMMENT '手机号',
	address CHAR(200) COMMENT '收货地址',
	member_id INT COMMENT '用户 id',
	PRIMARY KEY (id)
);

# 项目信息表
drop table if exists t_order_project;
CREATE TABLE t_order_project(
	id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	project_name CHAR(200) COMMENT '项目名称',
	launch_name CHAR(100) COMMENT '发起人',
	return_content CHAR(200) COMMENT '回报内容',
	return_count INT COMMENT '回报数量',
	support_price INT COMMENT '支持单价',
	freight INT COMMENT '配送费用',
	order_id INT COMMENT '订单表的主键',
	PRIMARY KEY (`id`)
);