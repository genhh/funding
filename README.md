# CrowdFunding
a web for Crowdfunding , Java8
## 技术总结
| 注册中心   | 消息队列  | 分布式文件系统 |
|--------|-------|------|
| Eureka | Kafka | HDFS |
| Nacos  | RabbitMQ  |      |
| Consul | ActiveMQ |      |

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