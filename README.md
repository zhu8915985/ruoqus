# RuoQus

<div align="center">

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]


<p align="center">
  <a href="https://trendshift.io/repositories/13209">
    <img src="https://trendshift.io/api/badge/repositories/13209" alt="GitHub Trending">
  </a>
</p>


<img src="image/favicon.ico" alt="Hongdux Logo" width="120" height="120">


### 基于 Quarkus 的 RuoYi 权限管理系统，更贴合云原生的 RuoYi 系统

*采用 Quarkus 框架构建的 RuoYi 权限管理系统，提供用户认证、权限管理等核心功能。支持 Java Native，具备极致的启动速度和更低的资源消耗（更小的内存占用），是更贴合云原生的 RuoYi 系统*

**[🇺🇸 English](README_EN.md)**  | **[🚀 在线体验](https://ruoqus.hongdux.com)** | **[🐛 问题反馈](https://github.com/zhu8915985/ruoqus/issues)** | **[💡 功能建议](https://github.com/zhu8915985/ruoqus/issues)**

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

### 核心框架
本项目为前后端分离架构，支持前后端独立部署，同时也支持前后端混合部署。
- **前端架构**：前端采用Vue3、Element UI。
- **后端架构**：quarkus 3.28 + quarkus-rest + smallrye-jwt
- **数据存储**：MySQL 8.0 + Redis 

## 🚀 版本说明

### 社区版（当前版本）
本项目为基础版本，提供了核心的权限认证功能，包括：
- 用户登录/登出
- JWT Token 认证机制
- 基于角色的权限控制
- RESTful API 接口

### Pro版
### 在线演示
针对企业级应用需求，我们还提供了功能更全面的 Pro 版本

- **使用serverless 无服务器部署，快速冷启动**
- [ruoqus.hongdux.com](https://ruoqus.hongdux.com) (账号：admin 密码：admin123)

功能：
- RuoYi 系统的完整功能模块
1. 用户管理：系统用户的配置与管理
2. 部门管理：组织架构配置，支持树形结构展示与数据权限控制
3. 岗位管理：用户职务配置
4. 菜单管理：系统菜单、操作权限、按钮权限标识配置
5. 角色管理：角色菜单权限分配，支持按部门划分数据范围权限
6. 字典管理：固定数据维护功能
7. 参数管理：系统运行参数动态配置
8. 通知公告：系统消息发布与维护
9. 操作日志：用户操作记录查询，包括异常日志追踪
10. 登录日志：用户登录记录与异常监控
11. 在线用户：当前在线用户状态监控
12. 系统接口：基于业务代码自动生成API文档
13. 服务监控：系统CPU、内存、磁盘等资源实时监控
14. 缓存监控：Redis缓存信息查询与命令统计


## 🚀 快速开始

## 📦 服务启动

### 社区版
```bash
# 开发模式运行
mvn quarkus:dev
# jar包构建
mvn clean package
# 原生镜像编译
mvn package -Pnative
```
在runner/community目录下,已编译好原生镜像,解压ruoyi-quarkus-runner.zip
```bash
#在linux环境下直接运行
chmod 755 ruoyi-quarkus-runner
./ruoyi-quarkus-runner
```

### Pro版
在runner/pro目录下,已编译好原生镜像,解压runner.zip
```bash
#在linux环境下直接运行
执行ruoqus.sql创建数据库
修改custom.properties配置信息
chmod 755 runner
./runner
```

如需获取 Pro 版本源码，请联系我们。

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