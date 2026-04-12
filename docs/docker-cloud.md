# Cloud 后端容器运行说明

本文档描述 `xingyun-cloud-api` 与 `xingyun-cloud-gateway` 的容器运行方式。当前方案默认将 `Nacos` 视为外部基础设施，通过环境变量注入地址，不在仓库内编排。

## 前置条件

- JDK 21
- Maven 3.9+
- Docker 24+
- 可用的外部 `Nacos`

## xingyun-cloud-api

手动构建：

```bash
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
docker build -t xingyun-cloud-api:local -f cloud/xingyun-cloud-api/target/Dockerfile cloud/xingyun-cloud-api/target
```

脚本构建前请先手动完成 Maven 打包：

```bash
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
cd cloud/xingyun-cloud-api
sh build-image.sh
```

Windows PowerShell:

```powershell
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
cd cloud/xingyun-cloud-api
.\build-image.ps1
```

Windows `cmd`:

```bat
mvn -pl cloud/xingyun-cloud-api -am package -DskipTests
cd cloud\xingyun-cloud-api
build-image.bat
```

Compose 运行：

```bash
cd cloud/xingyun-cloud-api
cp .env.example .env
docker compose up -d --build
```

必填变量：

- `XINGYUN_NACOS_SERVER_ADDR`

可选变量：

- `JAVA_OPTS`

## xingyun-cloud-gateway

手动构建：

```bash
mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
docker build -t xingyun-cloud-gateway:local -f cloud/xingyun-cloud-gateway/target/Dockerfile cloud/xingyun-cloud-gateway/target
```

脚本构建前请先手动完成 Maven 打包：

```bash
mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
cd cloud/xingyun-cloud-gateway
sh build-image.sh
```

Windows PowerShell:

```powershell
mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
cd cloud/xingyun-cloud-gateway
.\build-image.ps1
```

Windows `cmd`:

```bat
mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests
cd cloud\xingyun-cloud-gateway
build-image.bat
```

Compose 运行：

```bash
cd cloud/xingyun-cloud-gateway
cp .env.example .env
docker compose up -d --build
```

必填变量：

- `XINGYUN_NACOS_SERVER_ADDR`

可选变量：

- `JAVA_OPTS`

## 说明

- 当前方案只覆盖 `xingyun-cloud-api` 与 `xingyun-cloud-gateway`
- `MySQL`、`Redis`、`RabbitMQ` 等业务依赖默认通过 `Nacos` 配置中心间接接入
- 当前方案不在仓库内提供 `Nacos` 容器编排
