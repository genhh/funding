# CrowdFunding
a web for Crowdfunding , Java8
## 技术总结
| 注册中心   | 消息队列  | 分布式文件系统 |
|--------|-------|------|
| Eureka | Kafka | HDFS |
| Nacos  | RabbitMQ  |      |
| Consul | ActiveMQ |      |
前台分布式端口设置

| 服务                      | 端口   |
|-------------------------|------|
| eureka                  | 1000 |
| mysql-provider          | 2000 |
| redis-provider          | 3000 |
| authentication-consumer | 4000 |
| project-consumer        | 5000 |
| fdfs-provider           | 6000 |
| order-consumer          | 7000 |
| pay-consumer            | 8000 |
| Nginx                   | 80   |
| zuul                    | 9000 |

![img](https://ucc.alicdn.com/pic/developer-ecology/8f2f08de0b4442f1b93a8c169deb7c04.png)

## 1st day
create project and install dependencies
- springMVC+mybatis+jsp+jQuery+SmartTomcat+zTree+layer弹层+pageHelper
```bash
# mysql
mysql -u root -p
source creat.sql
# in reverse dir
mvn mybatis-generator:generate 
```
完成功能：搭建环境，反向生成mapper

## 2nd day 
ResultEntity, request type judge, exception handle, js page;

Tomcat 404 can't work, because servlet:2.5 and jsp:2.1.3 are too old;

[Tomcat support version](https://tomcat.apache.org/whichversion.html)

then download version 9x;

Mybatis Generator make 5 repeat context in AdminMapper.xml;->delete->run success

完成功能：搭建环境，管理员登录

## 3rd day
It's easier to coding when you follow controller->service->dao's order.--Top-down approach;

DB navigator plugin not work;
完成功能：登录检查

## 4th day
PageHelper plugin config error in spring-persist-mybatis.xml;

->page plugin and search not work;

->move to mybatis-config.xml

-> the problem of jsp -> 一直请求加载死循环 -[启发](https://blog.csdn.net/Stone__Fly/article/details/107098569)->代码更新后未清除缓存，清除后正常工作

mybatis error:xxx with invalid types () or values ().->(忘了写无参构造函数)提供了其他有参数的构造方法的话，虚拟机就不再为你提供默认构造方法，这时必须手动把无参构造器写在代码里

完成功能:分页显示账户信息, 添加、更新账户

## 5th day

周日开会

完成功能:删除账户(无提示直接删除); 角色分页，更新ing，删除ing, 查询用js实现; 权限管理菜单展示

## 6th day
Page指令：非法出现多次出现的'contentType'具有不同的值-> jsp头声明统一即可解决

nested exception is org.apache.ibatis.binding.BindingException:“roleIdList” not found 

解决方案-> AdminMapper对应接口加上@Param(多个参数时必须加上，单个参数不加也没事)

roleId无法获取为null值，导致inner_role_auth表无法记录数据，把my-role.js文件中的window.roleId
改为roleId即可。但每次重新分配时无法记住上次的选择，可能是逻辑设计问题。

完成功能：添加更新删除菜单节点，(但是侧边栏没有随之更新)

## 7th day
引入 springSecurity.后所有网页都404,但是已经设置permitAll()了—>No-bean-named-springSecurityFilterChain-available->容器启动顺序问题
，->把所有配置放到springMVC ioc中

SpringSecurity遇到Bad Credentials 

org.springframework.beans.NotReadablePropertyException: Invalid property 'principal.originalAdmin'
->显示出来才发现，principal原来是我们自己封装的SecurityAdmin对象（admin-main.jsp忘了修改）
## 8th day
SpringSecurity遇到Bad Credentials 且点击登录后302 not found ->换了密码编码方式为原先的md5+盐值处理即可成功登录

但是菜单维护显示成了给用户分配角色的界面?-- new bug

有时候点分配权限给角色会卡住?

有权限但是无法访问用户维护?->得等下一次刷新缓存登录才行

## 9th day
开始搭建前台用户系统,技术栈:springBoot+springCloud

Unsupported class file major version 61 ->jdk版本过高或springBoot版本过低，->升级springCloud和springBoot版本，->
测试时RunWith找不到->高版本springBoot不再用Runwith,junitTest也换成了.jupiter.api.Test;->测试运行时编译不通过"找不到符号"。->
清除缓存，重新打包util模块->
Please refer to dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.maven打包错误->看日志java.
lang.TypeNotPresentException: Type org.springframework.test.context.junit.jupiter.SpringExtension not present->
[重新选择test依赖](https://blog.csdn.net/W521125W/article/details/134919138)->jvm崩溃->把mvn的test关掉,然后重新install观察具体错误->
TestEngine with ID 'junit-jupiter' failed to discover tests
## 10th day
->换成junit-engine
->java.lang.AbstractMethodError: Receiver class org.springframework.boot.logging.logback.RootLogLevelConfigurator does 
not define or inherit an implementation of the resolved method 'abstract void configure(ch.qos.logback.classic.LoggerContext)' 
of interface ch.qos.logback.classic.spi.Configurator->lomback的问题 ->只有slf4j，这个只是接口，具体还需要log4j、logback或log4j2->
org.springframework.test.context不存在->删了maven本地库重新下载，配置mavenHelper插件解决依赖冲突->还是不行


记错了，之前后台的Java sdk应该是8的，后面可能是清除idea缓存，在项目结构中显示的都是sdk17,然后就没看父pom文件。后面全换成sdk8的试试.
idea一刷新maven之前配置就都变回17去了，后面按照网上教程在父Pom里面加了个插件->清缓存->编译失败无法访问com.zh.funding.entity.po.MemberPO。
但是按住ctrl可以跳转过去->清除idea缓存还是不行->rebuild-> Cannot load driver class: com.mysql.cj.jdbc.Driver->following method did not exist:
org.mybatis.spring.SqlSessionFactoryBean.setVfs(Ljava/lang/Class;)V->统一一下mybatis依赖版本->jvm老是崩崩崩->突然发现子模块的maven.compiler.source都是17，改为8->
mybatis版本和springBoot不兼容，套了两个父pom搞的现在很不好搞，maven helper也没显示依赖冲突->The following method did not exist:
org.springframework.web.servlet.DispatcherServlet.setEnableLoggingRequestDetails(Z)V springWebMVC依赖有问题->spring-core版本不统一->修改后终于成功

idea是真麻烦，每次install不了几次就得重启，吃内存吃的有点离谱

## 11th day

搭建远程调用接口模块，授权登录模块，Mysql处理数据模块，redis共享缓存模块。。。

## 12th day

用docker 创建redis镜像容器进行redis可行性测试，网关模块搭建

报错找不到测试类，，，刷新idea缓存，maven clean包，然后reimport,然后重启解决，通过RSEP查看redis成功存储相关测试用例

要跑主程序就要把测试类ban掉否则运行主程序也会运行测试类，然后导致错误，入口是`http://127.0.0.1:4000/`
，但是很奇怪，关了相关主程序，该网址依然能访问

springSession主要是基于Redis来实现分布式会话管理。接管TomCat

[fastDFS教程](https://developer.aliyun.com/article/1201734#:~:text=%E5%88%9D%E8%AF%86FastDFS%201%201.%E6%9E%B6%E6%9E%84%20%E8%B7%9F%E8%B8%AA%E6%9C%8D%E5%8A%A1%E5%99%A8%EF%BC%88tracker%20server%EF%BC%89%20%E4%B8%BB%E8%A6%81%E5%81%9A%E8%B0%83%E5%BA%A6%E5%B7%A5%E4%BD%9C%EF%BC%8C%E8%B5%B7%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1%E7%9A%84%E4%BD%9C%E7%94%A8%E3%80%82%20%E5%9C%A8%E5%86%85%E5%AD%98%E4%B8%AD%E8%AE%B0%E5%BD%95%E9%9B%86%E7%BE%A4%E4%B8%AD%E6%89%80%E6%9C%89%E5%AD%98%E5%82%A8%E7%BB%84%E5%92%8C%E5%AD%98%E5%82%A8%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%9A%84%E7%8A%B6%E6%80%81%E4%BF%A1%E6%81%AF%EF%BC%8C%E6%98%AF%E5%AE%A2%E6%88%B7%E7%AB%AF%E5%92%8C%E5%AD%98%E5%82%A8%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%BA%A4%E4%BA%92%E7%9A%84%E6%9E%A2%E7%BA%BD%EF%BC%8C%E4%B8%8D%E8%AE%B0%E5%BD%95%E6%96%87%E4%BB%B6%E7%B4%A2%E5%BC%95%E4%BF%A1%E6%81%AF%EF%BC%8C%E5%8D%A0%E7%94%A8%E7%9A%84%E5%86%85%E5%AD%98%E9%87%8F%E5%BE%88%E5%B0%91%20...,3.fastdfs%E4%B8%8B%E8%BD%BD%20Storage%E4%BC%9A%E5%AE%9A%E6%97%B6%E7%9A%84%E5%90%91Tracker%E5%AE%89%E8%A3%85%E5%8F%91%E9%80%81%E5%BF%83%E8%B7%B3%EF%BC%8C%E5%91%8A%E8%AF%89Tracker%E8%87%AA%E5%B7%B1%E8%BF%98%E8%BF%98%E6%B4%BB%E7%9D%80%EF%BC%8C%E8%BF%99%E6%A0%B7Fastdfs%E5%B0%B1%E5%8F%AF%E4%BB%A5%E5%B7%A5%E4%BD%9C%E4%BA%86%20...%204%204.%E6%8B%93%E5%B1%95%E6%A8%A1%E5%9D%97%20%E5%9C%A8%E5%A4%A7%E5%A4%9A%E6%95%B0%E4%B8%9A%E5%8A%A1%E5%9C%BA%E6%99%AF%E4%B8%AD%EF%BC%8C%E5%BE%80%E5%BE%80%E9%9C%80%E8%A6%81%E4%B8%BAFastDFS%E5%AD%98%E5%82%A8%E7%9A%84%E6%96%87%E4%BB%B6%E6%8F%90%E4%BE%9Bhttp%E4%B8%8B%E8%BD%BD%E6%9C%8D%E5%8A%A1%EF%BC%8C%E8%80%8C%E5%B0%BD%E7%AE%A1FastDFS%E5%9C%A8%E5%85%B6storage%E5%8F%8Atracker%E9%83%BD%E5%86%85%E7%BD%AE%E4%BA%86http%E6%9C%8D%E5%8A%A1%EF%BC%8C%20%E4%BD%86%E6%80%A7%E8%83%BD%E8%A1%A8%E7%8E%B0%E5%8D%B4%E4%B8%8D%E5%B0%BD%E5%A6%82%E4%BA%BA%E6%84%8F%EF%BC%9B%20)

[fastDFS Java连接](https://blog.csdn.net/bcz1992/article/details/121615293)

计划用docker启动fastDFS镜像容器代替阿里云oss

## 13th day
[FastDFSClient github](https://github.com/tobato/FastDFS_Client)

报错 util-1.0-SNAPSHOT.jar.396611389894939640.tmp:是对应java程序一直在运行无法关闭，最后通过任务管理器强制关闭然后清空对应本地maven

完成登录，注册，发送验证码功能

## 14th day

Error invoking remote method 'docker-start-container': Error: (HTTP code 500) server error - Ports are not available: 
exposing port TCP 0.0.0.0:6379 -> 0.0.0.0:0: listen tcp 0.0.0.0:6379: bind: Only one usage of each socket address (protocol/network address/port) 
is normally permitted.

端口被java程序莫名其妙的占用了，为什么每次都是正常关闭程序还会有java程序残留运行，可能是我先关的redis容器，然后再关java程序导致->是之前idea设置的问题，
把设置中的maven build run中的“将IDEA构建/运行操作委托给maven”选项去勾->还是不行

DefaultSerializer requires a Serializable payload but received an object of type ->存入redis的对象需要实现序列化

Load balancer does not have available server for client: crowd-redis

前一周上午都有其他事没有时间

## 15th day
[MultipartFile类讲解](https://blog.csdn.net/weixin_45393094/article/details/112056436)

要用fastFDS存储数据，就要引入Nginx和网关配合，有点复杂 [Nginx+springCloud](https://cloud.tencent.com/developer/article/1931848)

配置127映射域名为www.zh.test.com

调整了upload函数， 先测试引入Nginx，待正常工作后引入fastDFS
```
#在有nginx配置文件对应目录下运行该命令生成nginx docker容器
# windows续行符^, linux续行符\
# 要挂载宿主机上的目录作为数据卷，需要用绝对路径，
docker run -p 80:80 --name nginx-test ^
-v E:/JavaProject/funding/nginx/html:/usr/share/nginx/html ^
-v E:/JavaProject/funding/nginx/logs:/var/log/nginx ^
-v E:/JavaProject/funding/nginx/conf:/etc/nginx ^
-d nginx:latest
```

nginx容器报错：connect() failed (111: Connection refused) while connecting to upstream, client: 172.17.0.1, server: xxx.com
->因为是在docker容器中，所以127是指向容器本身而非宿主机，把127改成宿主机192开头的ip即可

fastDFS容器自带nginx,路径:/usr/local/src/fastdfs/nginx-1.7.8/conf

配置 /etc/nginx/nginx.conf
```
server {
        listen       80;
        server_name  www.zh.test.com;;

        location / {

            proxy_pass http://192.168.1.103:9000/;#zuul
        } 
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   /usr/share/nginx/html;
        }      
}
```
配置/etc/fdfs/mod_fastdfs.conf，


配置/etc/fdfs/tracker.conf，
配置/etc/fdfs/storage.conf，
配置/etc/fdfs/client.conf
tracker_server 和bind_addr都写容器自己内部eth0对应ip

fastdfs errno: 111, error info: Connection refused
storge 23000口一直启动不起来
tracker_server 和bind_addr都写容器自己内部eth0对应ip

docker容器本地可以上传了，但是外部好像还访问不了,503error

dockerV4.29之后才在windows下支持host模式

[docker官网教程](https://docs.docker.com/network/network-tutorial-host/#goal)

c盘满了没法安装，清理下c盘,安装完毕，docker引擎启动不起来

后续计划：完成文件上传，生成订单，支付宝支付等功能，然后添加/更新其他springCloud组件



