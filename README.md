# CrowdFunding
a web for Crowdfunding 
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

完成功能:分页显示账户信息, 添加、删除账户