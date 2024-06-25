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