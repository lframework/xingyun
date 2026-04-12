# 星云 ERP 后端

星云 ERP 是面向中小企业的开源进销存系统后端仓库，覆盖基础资料、商品中心、采购、销售、零售、库存、盘点、结算、报表和系统管理等核心业务场景。当前仓库同时保留单体部署与 Spring Cloud 部署两种运行形态，便于快速落地或按需拆分。

## 仓库定位

- `xingyun-api`：单体启动模块
- `cloud/xingyun-cloud-api`：云端业务服务入口
- `cloud/xingyun-cloud-gateway`：云端网关
- `xingyun-core`：基础依赖与公共模型
- `xingyun-comp`：公共组件服务
- `xingyun-basedata`：基础资料
- `xingyun-sc`：采购、销售、零售、库存、物流
- `xingyun-settle`：费用、预付款、对账、结算
- `xingyun-chart`：统计分析与图表报表

## 运行形态

### 单体模式

适合本地开发和快速部署，统一从 `xingyun-api` 启动。

### Cloud 模式

保留 `xingyun-cloud-api + xingyun-cloud-gateway` 双服务形态，适合接入 Nacos、Gateway 等云端基础设施后使用。

## 环境要求

- JDK 21
- Maven 3.9+
- MySQL 5.7.18+
- Redis 4.0.8+
- RabbitMQ 3.12.4+
- Cloud 模式额外需要：Nacos，以及对应的云端配置环境

## 快速开始

### 构建全部模块

```bash
mvn clean package -DskipTests
```

### 启动单体应用

本地联调优先使用 `self` Profile，配置文件组合为 `application.yml + application-self.yml`：

```bash
mvn -pl xingyun-api -am spring-boot:run -Dspring-boot.run.profiles=self
```

如果使用默认环境，也可以通过 Maven Profile 启动：

```bash
mvn -pl xingyun-api -am spring-boot:run -Pdev
```

### 构建云端模块

```bash
mvn -pl cloud/xingyun-cloud-api -am package
mvn -pl cloud/xingyun-cloud-gateway -am package
```

### Cloud 后端容器运行

`xingyun-cloud-api` 与 `xingyun-cloud-gateway` 支持作为独立云端服务镜像运行，默认通过外部 `Nacos` 获取配置与注册发现信息。

```bash
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
```

如果希望直接在模块目录里执行镜像构建脚本，请先手动完成对应模块的 Maven 打包：

Linux/macOS:

```bash
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
cd cloud/xingyun-cloud-api
sh build-image.sh

mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
cd ../xingyun-cloud-gateway
sh build-image.sh
```

Windows PowerShell:

```powershell
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
cd cloud/xingyun-cloud-api
.\build-image.ps1

mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
cd ..\xingyun-cloud-gateway
.\build-image.ps1
```

Windows `cmd`:

```bat
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
cd cloud\xingyun-cloud-api
build-image.bat

mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
cd ..\xingyun-cloud-gateway
build-image.bat
```

具体的镜像构建、模块级 `docker compose` 用法和环境变量约定见 [`docs/docker-cloud.md`](docs/docker-cloud.md)。

### 单体后端容器运行

`xingyun-api` 支持作为单体后端镜像运行，`MySQL`、`Redis`、`RabbitMQ` 默认按外部基础设施接入。

```bash
mvn -pl xingyun-api -am package -DskipTests
docker build -t xingyun-api:local -f xingyun-api/target/Dockerfile xingyun-api/target
```

如果希望直接在模块目录里执行镜像构建脚本，请先手动完成 `xingyun-api` 的 Maven 打包：

Linux/macOS:

```bash
mvn -pl xingyun-api -am package -DskipTests
cd xingyun-api
sh build-image.sh
```

Windows PowerShell:

```powershell
mvn -pl xingyun-api -am package -DskipTests
cd xingyun-api
.\build-image.ps1
```

Windows `cmd`:

```bat
mvn -pl xingyun-api -am package -DskipTests
cd xingyun-api
build-image.bat
```

运行参数、环境变量与卷挂载约定见 [`docs/docker-monolith.md`](docs/docker-monolith.md)。如果使用 `docker compose`，请在 `xingyun-api/` 目录执行。

## 数据与配置说明

- 数据库迁移脚本位于 `xingyun-api/src/main/resources/db/migration/platform` 与 `.../tenant`
- 多租户模式已固定开启，不再支持关闭
- 主库数据源名称为 `master`
- 缓存命名沿用实体上的 `CACHE_NAME` 常量，不使用零散自定义命名
- 创建子线程时必须使用 `DefaultCallable` 或 `DefaultRunnable` 传递租户上下文

## 主要技术栈

- Spring Boot 3.5.0
- Spring Cloud 2025.0.0
- Spring Cloud Alibaba 2025.0.0.0
- MyBatis-Plus 3.5.6
- Sa-Token 1.39.0
- Knife4j 4.5.0
- Warm-Flow 1.7.4
- Hutool 5.7.17
- Lombok 1.18.32

## 系统能力概览

- 系统管理：菜单、部门、角色、用户、权限、操作日志
- 基础资料：仓库、供应商、客户、会员、商品、品牌、分类、属性
- 供应链：采购订单、收货单、退货单、销售订单、出库单、零售单
- 库存管理：库存查询、库存调整、库存盘点、库存预警、调拨
- 结算管理：费用单、预付款单、对账单、结算单
- 图表分析：经营统计、业务报表、图表展示

## 相关仓库

- 底层框架 Jugg：<https://gitee.com/lframework/jugg>
- 前端项目 xingyun-front：<https://gitee.com/lframework/xingyun-front>
- 使用说明文档：<https://www.lframework.com>
- 演示地址：<http://erp.lframework.com>

## 开源说明

项目代码可开源商用，但请保留 `LICENSE`、源码头部版权声明以及项目出处信息。分发源码时请注明项目来源 <https://www.lframework.com/>，且不要基于本项目二次开源参与同类竞品分发。

## License

项目使用 Apache 2.0 许可证，请遵守许可证约束。
