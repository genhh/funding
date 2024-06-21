# CrowdFunding
a web for Crowdfunding 
## 1st day
create project and install dependencies
- springMVC+mybatis+jsp+jQuery+SmartTomcat+zTree+layer弹层
```bash
# mysql
mysql -u root -p
source creat.sql
# in reverse dir
mvn mybatis-generator:generate 
```
## 2nd day 
ResultEntity, request type judge, exception handle, js page;

Tomcat 404 can't work, because servlet:2.5 and jsp:2.1.3 are too old;

[Tomcat support version](https://tomcat.apache.org/whichversion.html)

then download version 9x;

Mybatis Generator make 5 repeat context in AdminMapper.xml;->delete->run success

## 3rd day
It's easier to coding when you follow controller->service->dao's order.--Top-down approach;

DB navigator plugin not work;