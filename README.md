# RuoYi-Quarkus

<div align="center">

### 基于 Quarkus 的 RuoYi 权限管理系统，更贴合云原生的 RuoYi 系统

*采用 Quarkus 框架构建的 RuoYi 权限管理系统，提供用户认证、权限管理等核心功能。支持 Java Native，具备极致的启动速度和更低的资源消耗（更小的内存占用），是更贴合云原生的 RuoYi 系统*

</div>

## ✨ 核心亮点

### 高性能框架
- **Quarkus 架构**：基于 Quarkus 框架，提供超快的启动速度和极低的内存占用
- **现代化技术栈**：采用 Jakarta EE、MicroProfile 等现代 Java 标准
- **原生编译支持**：支持 GraalVM 原生镜像编译，启动时间仅需几毫秒

### 完善的安全体系
- **JWT 认证机制**：基于 JWT 的用户身份认证和授权
- **权限控制**：支持基于角色的访问控制（RBAC）
- **安全防护**：集成 Quarkus 安全框架，提供多层次安全防护

### 灵活的系统设计
- **模块化架构**：清晰的代码结构，便于扩展和维护
- **RESTful API**：提供标准的 RESTful 接口
- **注解式权限**：通过注解方式实现接口权限控制

### 易用的开发体验
- **快速开发**：提供完整的用户管理、权限控制等基础功能
- **配置简单**：通过 application.properties 简单配置即可运行
- **跨域支持**：内置 CORS 配置，方便前后端分离开发

## 🚀 快速开始

## 📦 部署方式

### 开发环境运行
```bash
# 克隆项目
git clone https://github.com/your-repo/ruoyi-quarkus.git

# 进入项目目录
cd ruoyi-quarkus

# 开发模式运行
./mvnw quarkus:dev
```

### 生产环境打包
```bash
# JVM 模式打包
./mvnw clean package

# 运行应用
java -jar target/quarkus-app/quarkus-run.jar
```

### 使用预编译的可执行文件
项目 `runner` 目录下已提供预编译好的可执行文件，可直接运行：
```bash
# 进入 runner 目录
cd runner

# 直接运行预编译文件
./ruoyi-quarkus-runner
```

### 原生镜像编译
```bash
# 编译为原生镜像
./mvnw package -Pnative

# 运行原生应用
./target/ruoyi-quarkus-1.0.0-SNAPSHOT-runner
```

### 核心功能模块

#### 用户认证模块
- 用户登录/登出
- JWT Token 生成与验证
- 密码加密存储（BCrypt）

#### 权限管理模块
- 用户管理
- 角色管理
- 权限控制
- 注解式权限验证

#### 系统工具模块
- IP 地址工具
- 安全工具
- Servlet 工具
- 字符串处理工具

### 使用组件
- **后端框架**：Quarkus 3.15
- **安全框架**：SmallRye JWT + Quarkus Security
- **JSON 处理**：Jackson
- **依赖注入**：CDI (Contexts and Dependency Injection)

## 🚀 版本说明

### 社区版（当前版本）
本项目为基础版本，提供了核心的权限认证功能，包括：
- 用户登录/登出
- JWT Token 认证机制
- 基于角色的权限控制
- RESTful API 接口

### 企业版
针对企业级应用需求，我们还提供了功能更全面的 Pro 版本，包含：
- RuoYi 系统的完整功能模块
- 更丰富的权限管理机制
- 系统监控和日志管理
- 数据字典和参数配置
- 在线用户管理
- 操作日志记录
- 通知公告模块
- 更多企业级功能特性

如需获取 Pro 版本，请联系我们。

### 联系我们

📧 邮箱：[zhujunjie@hongdux.com](mailto:service@hongdux.com)  
🌐 官网：[www.hongdux.com](http://www.hongdux.com)  
📱 微信：hongdux


## 🤝 参与贡献

我们欢迎任何形式的贡献，包括但不限于：

1. 提交 Issue 报告 Bug 或建议新功能
2. Fork 项目并提交 Pull Request
3. 改进文档和示例代码
4. 参与讨论和问题解答

### 开发流程
1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/新功能`)
3. 提交更改 (`git commit -am '添加新功能'`)
4. 推送到分支 (`git push origin feature/新功能`)
5. 创建 Pull Request

## 📄 开源协议

本项目采用 MIT 协议，详细信息请查看 [LICENSE](LICENSE) 文件。

## 🙏 致谢

感谢以下开源项目：

- [Ruoyi](https://ruoyi.vip) - 基于SpringBoot的权限管理系统
- [Quarkus](https://quarkus.io) - 为云原生时代而生的 Java 框架
- [SmallRye JWT](https://github.com/smallrye/smallrye-jwt) - JWT 处理库
- [Apache Commons](https://commons.apache.org/) - Apache 软件基金会的工具库集合
- [JBCrypt](https://github.com/jeremyh/jBCrypt) - Java 版本的 BCrypt 实现

---
<div align="center">

*用 ❤️ 构建，为现代化 Java 应用开发而生*

</div>