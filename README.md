### 项目介绍
星云ERP是基于SpringBoot框架的中小企业完全开源的ERP。

### 演示地址
<a href="http://erp.lframework.com" target="_blank">点此进入</a>

### 前端项目源码
<a href="https://gitee.com/lframework/xingyun-front" target="_blank">点此进入</a>

### 使用说明文档
<a href="https://gitee.com/lframework/xingyun-doc" target="_blank">点此进入</a>

### 系统功能
|系统功能|功能描述|
| ------------ | ------------ |
|系统管理|菜单、部门、角色、岗位、用户、操作日志|
|基础信息|仓库、供应商、客户、会员基础信息|
|商品中心|商品主数据、类目、品牌、销售属性、属性|
|采购管理|采购订单、收货单、退货单|
|销售管理|销售订单、出库单、退货单|
|零售管理|零售出库单、退货单|
|库存管理|商品库存、商品批次库存、批次库存变动记录|
|结算管理|供应商费用单、预付款单、对账单、结算单、收入/支出项目|

### 待开发功能
* 库存盘点
* 库存损溢

后续还会开发更多功能。

### 主要技术框架
* Springboot 2.2.2.RELEASE
* MyBatis-plus 3.4.2
* Spring-session-data-redis 2.2.0.RELEASE
* HuTool 5.7.17 (只依赖了HuTool的core module)
* Lombok 1.18.10
* EasyExcel 2.2.10（内置了两种导出excel方式：一次性导出、分段导出（只支持简单表头））

### 开发环境
* JDK 1.8
* Mysql 5.7.18
* Redis 4.0.8（版本可以根据自己的redis进行调整，项目本身依赖Redis的功能很简单，就是两部分：缓存、Session，不会出现大的兼容问题）

### 技术交流
QQ交流群号：717574596，目前项目刚发布不久，人数较少，希望大家不要嫌弃。

### 注意事项
项目依赖的底层框架全部封装成starter，关于这部分的代码详见：<a href="https://gitee.com/lframework/jugg" target="_blank">点此进入</a>

### License
项目使用LGPL3.0许可证，请遵守此许可证的限制条件。

### 其他说明
作者是两只热爱工作、热爱开源的程序猿、产品经理，欢迎大家提出批评、建议！