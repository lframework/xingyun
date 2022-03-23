### 项目介绍
星云ERP基于SpringBoot框架，为中小企业提供完全开源、永久免费、用户体验好的进销存ERP系统，解决开店难、管理难、数据统计难的问题。星云ERP主要包括基础信息管理、商品中心、采购管理、销售管理、零售管理、库存管理、盘点管理、结算管理等，各业务模块均支持参数配置，满足实际遇到的各种业务场景。丰富的报表模块支持用户做各项数据分析。同时支持对部门、岗位、角色、用户、权限等进行精细化管理。最终，达到业务线上化、透明化、简易化管理的目标，实现物流、资金流、信息流的一体化管控。

### 演示地址
星云ERP平台地址：http://erp.lframework.com   <a href="http://erp.lframework.com" target="_blank">点此进入</a>

### 前端项目源码
前端项目码源：https://gitee.com/lframework/xingyun-front   <a href="https://gitee.com/lframework/xingyun-front" target="_blank">点此进入</a>

### 使用说明文档
星云ERP说明文档：https://gitee.com/lframework/xingyun-doc   <a href="https://gitee.com/lframework/xingyun-doc" target="_blank">点此进入</a>

### 系统功能
| 系统功能 | 功能描述                        |
|------|-----------------------------|
| 系统管理 | 系统设置、菜单、部门、角色、岗位、用户、操作日志         |
| 基础信息 | 仓库、供应商、客户、会员基础信息            |
| 商品中心 | 商品主数据、类目、品牌、销售属性、属性（自定义属性）  |
| 采购管理 | 采购订单、收货单、退货单                |
| 销售管理 | 销售订单、出库单、退货单                |
| 零售管理 | 零售出库单、退货单                   |
| 库存管理 | 商品库存、商品批次库存、批次库存变动记录        |
| 库存盘点 | 盘点参数设置、预先盘点单管理、盘点任务管理、盘点单管理  |
| 库存调整 | 库存成本调整                           |
| 结算管理 | 供应商费用单、预付款单、对账单、结算单、收入/支出项目 |
| 代码生成 | 生成增删改查代码                    |

### 待开发功能
| 系统功能 | 开发进度         |
|------|--------------|
| 库存数量调整 | 开发文档已完成，正在开发     |
| 库存损溢 | 开发文档尚未完成     |

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
QQ交流群号： **717574596** ，目前项目刚发布不久，人数较少，希望大家不要嫌弃。

### 注意事项
项目依赖的底层框架全部封装成starter，关于这部分的代码详见：<a href="https://gitee.com/lframework/jugg" target="_blank">点此进入</a>

### License
项目使用LGPL3.0许可证，请遵守此许可证的限制条件。

### 系统展示
* 首页
![输入图片说明](screenshots/1%E9%A6%96%E9%A1%B5.png)
* 系统管理
![输入图片说明](screenshots/2%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86.jpeg)
* 基础信息管理
![输入图片说明](screenshots/3%E5%9F%BA%E7%A1%80%E4%BF%A1%E6%81%AF%E7%AE%A1%E7%90%86.png)
* 商品中心
![输入图片说明](screenshots/4%E5%95%86%E5%93%81%E4%B8%AD%E5%BF%83.png)
* 采购管理
![输入图片说明](screenshots/5%E9%87%87%E8%B4%AD%E7%AE%A1%E7%90%86.png)
* 销售管理
![输入图片说明](screenshots/6%E9%94%80%E5%94%AE%E7%AE%A1%E7%90%86.png)
* 零售管理
![输入图片说明](screenshots/7%E9%9B%B6%E5%94%AE%E7%AE%A1%E7%90%86.png)
* 库存管理
![输入图片说明](screenshots/8%E5%BA%93%E5%AD%98%E7%AE%A1%E7%90%86.png)
* 库存盘点
![输入图片说明](screenshots/9%E5%BA%93%E5%AD%98%E7%9B%98%E7%82%B9.png)
* 库存调整
![输入图片说明](screenshots/10%E5%BA%93%E5%AD%98%E8%B0%83%E6%95%B4.png)
* 结算管理
![输入图片说明](screenshots/11%E7%BB%93%E7%AE%97%E7%AE%A1%E7%90%86.png)
* 开发管理
![输入图片说明](screenshots/12%E5%BC%80%E5%8F%91%E7%AE%A1%E7%90%86.png)

### 其他说明
作者是两只热爱工作、热爱开源的程序猿、产品经理，欢迎大家提出批评、建议！