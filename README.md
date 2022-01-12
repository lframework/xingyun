### 项目介绍
星云ERP是基于SpringBoot框架的中小企业完全开源的ERP。
#### 注意事项
目前项目的登录主要是使用session而不是jwt，后续会增加jwt-starter提供更多的选择。
### 演示地址
[点此进入][demoUrl]

### 注意事项
项目依赖的底层框架全部封装成starter，关于这部分的代码详见：[点此进入][juggGitee]

### 主要技术框架
* springboot 2.2.2.RELEASE
* myBatis-plus 3.4.2
* spring-session-data-redis 2.2.0.RELEASE
* HuTool 5.7.17 (只依赖了HuTool的core module)
* lombok 1.18.10
* EasyExcel 2.2.10（内置了两种导出excel方式：一次性导出、分段导出（只支持简单表头））

### 开发环境
* JDK 1.8
* Mysql 5.7.18
* Redis 4.0.8（版本可以根据自己的redis进行调整，项目本身依赖Redis的功能很简单，就是两部分：缓存、Session，不会出现大的兼容问题）

### License
项目使用LGPL3.0许可证，请遵守此许可证的限制条件。

### 其他说明
* 目前项目刚刚发布，使用人数很少，暂不提供交流群，Bug请提Issue。
* 作者是一个只有几年开发经验的菜鸡，如有错误之处，望斧正。
* 前端项目Gitee地址：[点此进入][frontGitee]

[frontGitee]: https://gitee.com/lframework/xingyun-front
[demoUrl]: http://erp.lframework.com
[juggGitee]: https://gitee.com/lframework/jugg
