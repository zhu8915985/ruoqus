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


### RuoYi Permission Management System Based on Quarkus, a Cloud-Native RuoYi System

*RuoYi permission management system built on the Quarkus framework, providing core functions such as user authentication and permission management. Supports Java Native with excellent startup speed and lower resource consumption (smaller memory footprint), making it a cloud-native RuoYi system*

**[🇨🇳 中文](README.md)**  | **[🚀 Online Demo](https://ruoqus.hongdux.com)** | **[🐛 Report Issues](https://github.com/zhu8915985/ruoqus/issues)** | **[💡 Feature Suggestions](https://github.com/zhu8915985/ruoqus/issues)**

</div>

## ✨ Core Highlights

### High-Performance Framework
- **Quarkus Architecture**: Built on the Quarkus framework, providing ultra-fast startup speed and extremely low memory footprint
- **Modern Technology Stack**: Utilizes modern Java standards such as Jakarta EE and MicroProfile
- **Native Compilation Support**: Supports GraalVM native image compilation with startup times in just milliseconds

### Comprehensive Security System
- **JWT Authentication Mechanism**: User authentication and authorization based on JWT
- **Access Control**: Supports Role-Based Access Control (RBAC)
- **Security Protection**: Integrated with Quarkus security framework for multi-layered security protection

### Flexible System Design
- **Modular Architecture**: Clean code structure for easy expansion and maintenance
- **RESTful API**: Provides standard RESTful interfaces
- **Annotation-Based Permissions**: Implements interface permission control through annotations

### Developer-Friendly Experience
- **Rapid Development**: Provides complete foundational functions like user management and permission control
- **Simple Configuration**: Can be run with simple configuration via application.properties
- **CORS Support**: Built-in CORS configuration for convenient front-end and back-end separation development

### Core Framework
This project features a front-end and back-end separated architecture, supporting both independent and hybrid deployment of front-end and back-end.
- **Front-end Architecture**: Front-end uses Vue3 and Element UI
- **Back-end Architecture**: quarkus 3.28 + quarkus-rest + smallrye-jwt
- **Data Storage**: MySQL 8.0 + Redis 

## 🚀 Version Information

### Community Edition (Current Version)
This is the base version providing core authentication functions, including:
- User login/logout
- JWT Token authentication mechanism
- Role-based access control
- RESTful API interfaces

### Pro Version
### Online Demo
For enterprise-level application requirements, we also provide a more comprehensive Pro version

- **Serverless deployment with fast cold start**
- [ruoqus.hongdux.com](https://ruoqus.hongdux.com) (Account: admin Password: admin123)

Features:
- Complete RuoYi system modules
1. User Management: Configuration and management of system users
2. Department Management: Organizational structure configuration, supporting tree structure display and data permission control
3. Position Management: User position configuration
4. Menu Management: System menu, operation permissions, and button permission identifier configuration
5. Role Management: Role-menu permission assignment, supporting data scope permissions divided by department
6. Dictionary Management: Fixed data maintenance function
7. Parameter Management: Dynamic configuration of system runtime parameters
8. Notice Announcement: Publishing and maintenance of system messages
9. Operation Log: Query of user operation records, including exception log tracking
10. Login Log: User login record and exception monitoring
11. Online Users: Monitoring of current online user status
12. System Interface: Automatic API documentation generation based on business code
13. Service Monitoring: Real-time monitoring of system CPU, memory, disk and other resources
14. Cache Monitoring: Redis cache information query and command statistics


## 🚀 Quick Start

## 📦 Service Startup

### Community Edition
```bash
# Run in development mode
mvn quarkus:dev
# Build jar package
mvn clean package
# Native image compilation
mvn package -Pnative
```
In the runner/community directory, a compiled native image is available. Unzip ruoyi-quarkus-runner.zip
```bash
# Run directly in Linux environment
chmod 755 ruoyi-quarkus-runner
./ruoyi-quarkus-runner
```

### Pro Version
In the runner/pro directory, a compiled native image is available. Unzip runner.zip
```bash
# Run directly in Linux environment
Execute ruoqus.sql to create database
Modify custom.properties configuration information
chmod 755 runner
./runner
```

To obtain the Pro version source code, please contact us.

### Contact Us

📧 Email: [zhujunjie@hongdux.com](mailto:service@hongdux.com)  
🌐 Website: [www.hongdux.com](http://www.hongdux.com)  
📱 WeChat: hongdux


## 🤝 Contributing

We welcome contributions of any form, including but not limited to:

1. Submitting Issues to report bugs or suggest new features
2. Forking the project and submitting Pull Requests
3. Improving documentation and sample code
4. Participating in discussions and Q&A

### Development Process
1. Fork this repository
2. Create a feature branch (`git checkout -b feature/NewFeature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/NewFeature`)
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgements

Thanks to the following open-source projects:

- [Ruoyi](https://ruoyi.vip) - Permission management system based on SpringBoot
- [Quarkus](https://quarkus.io) - Java framework for the cloud-native era
- [SmallRye JWT](https://github.com/smallrye/smallrye-jwt) - JWT processing library
- [Apache Commons](https://commons.apache.org/) - Tool library collection from the Apache Software Foundation
- [JBCrypt](https://github.com/jeremyh/jBCrypt) - Java implementation of BCrypt

---
<div align="center">

*Built with ❤️ for modern Java application development*

</div>


<!-- Badge Links -->
[contributors-shield]: https://img.shields.io/github/contributors/zhu8915985/ruoqus.svg?style=flat-square
[contributors-url]: https://github.com/zhu8915985/ruoqus/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/zhu8915985/ruoqus.svg?style=flat-square
[forks-url]: https://github.com/zhu8915985/ruoqus/network/members
[stars-shield]: https://img.shields.io/github/stars/zhu8915985/ruoqus.svg?style=flat-square
[stars-url]: https://github.com/zhu8915985/ruoqus/stargazers
[issues-shield]: https://img.shields.io/github/issues/zhu8915985/ruoqus.svg?style=flat-square
[issues-url]: https://github.com/zhu8915985/ruoqus/issues
[license-shield]: https://img.shields.io/github/license/zhu8915985/ruoqus.svg?style=flat-square
[license-url]: https://github.com/zhu8915985/ruoqus/blob/main/LICENSE