# 单体后端容器运行说明

本文档描述 `xingyun-api` 的单体后端容器运行方式。`MySQL`、`Redis`、`RabbitMQ` 均视为外部基础设施，容器启动时通过环境变量接入，不由当前仓库内编排提供。

## 前置条件

- JDK 21
- Maven 3.9+
- Docker 24+
- 可用的外部 `MySQL`、`Redis`、`RabbitMQ`

## 构建 Jar 与镜像

先在仓库根目录执行 Maven 打包：

```bash
mvn -pl xingyun-api -am package -DskipTests
```

打包完成后，`xingyun-api/target/` 中会同时生成 jar 和经过 Maven 资源过滤后的 `Dockerfile`。使用该目录作为镜像构建上下文：

```bash
docker build -t xingyun-api:local -f xingyun-api/target/Dockerfile xingyun-api/target
```

如果希望使用脚本构建镜像，请先手动完成 Maven 打包，再执行脚本：

```bash
mvn -pl xingyun-api -am package -DskipTests
cd xingyun-api
sh build-image.sh
```

自定义镜像标签示例：

```bash
mvn -pl xingyun-api -am package -DskipTests
cd xingyun-api
sh build-image.sh my-registry.example.com/xingyun-api:1.0.0
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

如果更习惯 `docker compose`，`xingyun-api/` 目录还提供了只启动 `xingyun-api` 的 [`compose.yaml`](../xingyun-api/compose.yaml)。它同样依赖已打包完成的 `xingyun-api/target/` 目录：

```bash
cd xingyun-api
cp .env.example .env
docker compose up -d --build
```

`compose.yaml` 只保留部署层高频参数；完整变量清单和示例值见 [`xingyun-api/.env.example`](../xingyun-api/.env.example)。

## 运行容器

示例：

```bash
docker run -d --name xingyun-api ^
  -p 8080:8080 ^
  -e XINGYUN_DB_URL="jdbc:mysql://mysql.example.com:3306/platform?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&autoReconnect=true&failOverReadOnly=false&serverTimezone=GMT%2B8" ^
  -e XINGYUN_DB_USERNAME="root" ^
  -e XINGYUN_DB_PASSWORD="password" ^
  -e XINGYUN_REDIS_HOST="redis.example.com" ^
  -e XINGYUN_REDIS_PORT="6379" ^
  -e XINGYUN_REDIS_DATABASE="0" ^
  -e XINGYUN_REDIS_PASSWORD="password" ^
  -e XINGYUN_RABBITMQ_ADDRESSES="rabbitmq.example.com:5672" ^
  -e XINGYUN_RABBITMQ_VHOST="/" ^
  -e XINGYUN_RABBITMQ_USERNAME="username" ^
  -e XINGYUN_RABBITMQ_PASSWORD="password" ^
  -e XINGYUN_UPLOAD_DOMAIN="http://localhost:8080" ^
  -e JAVA_OPTS="-Xms512m -Xmx512m" ^
  -v xingyun-tmp:/opt/data/tmp ^
  -v xingyun-upload:/opt/data/upload ^
  -v xingyun-security-upload:/opt/data/security-upload ^
  -v xingyun-logs:/opt/logs ^
  xingyun-api:local
```

如果在 Linux/macOS 下执行，请将续行符 `^` 替换为 `\`。

## 环境变量约定

### 必填

- `XINGYUN_DB_URL`
- `XINGYUN_DB_USERNAME`
- `XINGYUN_DB_PASSWORD`
- `XINGYUN_REDIS_HOST`
- `XINGYUN_REDIS_PASSWORD`
- `XINGYUN_RABBITMQ_ADDRESSES`
- `XINGYUN_RABBITMQ_USERNAME`
- `XINGYUN_RABBITMQ_PASSWORD`

推荐把以上变量写入 `xingyun-api/.env`，再执行 `docker compose up -d --build`。

### 常用可选项

- `XINGYUN_REDIS_PORT`
  默认值：`6379`
- `XINGYUN_REDIS_DATABASE`
  默认值：`0`
- `XINGYUN_REDIS_PASSWORD`
  默认值：`password`
- `XINGYUN_REDIS_TIMEOUT`
  默认值：`10s`
- `XINGYUN_RABBITMQ_VHOST`
  默认值：`/`
- `XINGYUN_RABBITMQ_CONNECTION_TIMEOUT`
  默认值：`60000`
- `XINGYUN_UPLOAD_DOMAIN`
  默认值：`http://localhost:8080`
- `JAVA_OPTS`
  追加 JVM 参数，例如堆大小、GC、诊断开关

以下变量通常不需要在 `compose.yaml` 中重复声明，镜像和应用配置已提供默认值：

- `SPRING_PROFILES_ACTIVE`
- `XINGYUN_TMP_DIR`
- `XINGYUN_UPLOAD_DIR`
- `XINGYUN_SECURITY_UPLOAD_DIR`
- `XINGYUN_LOG_DIR`

## 数据卷与目录

容器默认使用以下目录：

- `/opt/data/tmp`
- `/opt/data/upload`
- `/opt/data/security-upload`
- `/opt/logs`

推荐全部挂载为持久化卷，避免上传文件和日志随容器删除而丢失。

## 说明

- 当前方案只覆盖单体模块 `xingyun-api`
- 当前方案不自动初始化数据库结构或导入演示数据
- 当前方案不包含 `xingyun-front` 与云端模块的容器部署
