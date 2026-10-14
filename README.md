# PsychonautWiki Journal (中文分支)

PsychonautWiki Journal 是一个基于 [Jetpack Compose](https://developer.android.com/jetpack/compose) 开发的 Android 本地应用，旨在从风险减害（Harm Reduction）视角为物质使用提供科学的记录管理与药学参考。

本项目为原作者在闭源前的最后一个开源版本（v11.11 / Build 62）的独立分支，当前主线演进至大版本 v12.0.0 (Build 63)，遵循 GNU GPL-3.0 协议继续维护与本地化。

---

## 项目背景与分支维护声明

PsychonautWiki Journal 原由 Isaak Hanimann 开源维护。原作者于 2024 年底停止该项目的开源迭代，并将后续版本转入私有闭源仓库开发。

为了使该开源健康减害工具得以在开源社区持续延续，本项目在闭源前的最后公开代码基础（v11.11 / Build 62）上建立独立维护分支：

- **开源承诺**：永久遵照 GNU General Public License v3.0 协议发布，完全公开透明；
- **离线与隐私优先**：不含任何网络分析、追踪或遥测代码，所有数据仅存储于用户设备本地；
- **中文与药典本土化**：系统性规范中文药理学数据与物质命名，保持长期维护。

---

## 主要工作与调整

1. **界面与交互完整中文化**：对日记记录、搜索、给药途径说明、试剂检测、统计等全流程界面进行字符串解耦与中文本地化。
2. **物质学名与药理数据规范化**：物质名称严格参考《中华人民共和国药典》通用名称及 IUPAC 命名原则进行标准化整理与双语对照；物质属性整合 PsychonautWiki、TripSit 与 FreeODwiki 的公开药学资料。
3. **独立包名与共存支持**：配置应用包名后缀（`com.isaakhanimann.journal.zh`），可与官方原版并存安装使用。
4. **无损数据解耦**：多语言层与底层 Room 数据库严格隔离，不篡改枚举原名与数据库字段，保证数据备份的 100% 导入导出兼容性。

---

## 核心功能

- **物质信息查询**：提供物质分类、药代动力学时序（起效、峰值、持续时间）、给药途径（ROA）及参考剂量区间。
- **相互作用参考**：提供多物质联用时的相互作用分类（危险、不安全、未明确等）与药理机制提示。
- **体验记录与时间线**：支持用药剂量、摄入时间、主观体验评级（基于 Alexander Shulgin 六级评分标准）及事件记录。
- **减害与安全资料**：内置常见化学试剂显色比对参考、容积法稀释给药说明及给药途径生理防护知识。
- **用药统计**：提供本地数据的多维度图表统计与物质伴侣频次分析。

---

## 下载与安装

已编译完成的 Android 安装包（APK）提供以下多种获取方式：

- **📦 最新安装包直接下载（APK 直链）**：[PsychonautWiki-Journal-v12.0.0-zh.apk](https://github.com/yuanyuan2233/psychonautwiki-journal-android/releases/download/v12.0.0-zh/PsychonautWiki-Journal-v12.0.0-zh.apk)
- **🏷️ 最新版本发布说明**：[Release v12.0.0-zh](https://github.com/yuanyuan2233/psychonautwiki-journal-android/releases/tag/v12.0.0-zh)
- **📜 所有历史版本 Releases 列表**：[GitHub Releases 汇总](https://github.com/yuanyuan2233/psychonautwiki-journal-android/releases)
- **📂 仓库内嵌直存副本**：[项目根目录 release/ 归档文件夹](https://github.com/yuanyuan2233/psychonautwiki-journal-android/tree/main/release)（无需依赖 Releases 页面，可在代码仓库树中直接获取）

### 运行环境与安装说明
- **系统要求**：Android 8.0 (API 26) 及以上版本
- **应用包名**：已配置独立包名（`com.isaakhanimann.journal.zh`），当前大版本为 `12.0.0 (Build 63)`，可与官方原版直接共存，无须卸载已有应用。

---

## 本地构建

### 构建要求
- JDK 17
- Android SDK 35

### 编译命令
```bash
git clone https://github.com/yuanyuan2233/psychonautwiki-journal-android.git
cd psychonautwiki-journal-android
./gradlew assembleDebug
```
构建生成的 APK 位于：`app/build/outputs/apk/debug/app-zh-debug.apk`。

### 数据集维护工具
项目内置了基于公开数据源的同步与格式化清洗脚本：
```bash
python tools/sync_upstream_substances.py
```

---

## 致谢与数据来源 (Acknowledgments & Data Sources)

本项目的物质数据整理、汉化补充与减害指南扩充，深度受益于以下开源项目与社群知识库的贡献，特此致以诚挚敬意：

- **[FreeODwiki 社群](https://github.com/SalviaSWC/FreeODwiki)**：本项目在中文物质词典规范化、学名考证、主观效应及中文减害科普文档的本土化过程中，重点参考了 FreeODwiki 社区共创整理的中文知识库。
- **[Isaak Hanimann](https://github.com/isaakhanimann)**：原版 PsychonautWiki Journal 的架构设计与核心代码作者，为该开源项目奠定了优秀的工程基础。
- **[PsychonautWiki](https://psychonautwiki.org/)**：提供了全球领先的同行评议物质药理学数据、药代动力学阶段模型与开放 API 支持。
- **[TripSit](https://tripsit.me/)**：提供了经过社区实践检验的开源物质数据集（TripSit/drugs）与多物质相互作用安全对照矩阵。

---

## 减害与免责声明

1. 本应用为科学信息参考与个人健康记录工具，旨在推行健康减害（Harm Reduction）理念，所收录的药理参数、剂量区间及相互作用数据均来源于公开文献与社区研究，不作为临床诊疗凭据。
2. 本应用不鼓励、诱导或便利任何违法涉药或滥用行为。
3. 遇到任何急性药物反应或医疗意外，请立即联系当地紧急医疗救助中心寻求专业支持。

---

## 许可证

本项目依据 [GNU General Public License v3.0](LICENSE) 继续开源分发。

- 原项目版权所有 (C) 2022–2024 Isaak Hanimann.
- 中文分支维护 (C) 2026 yuanyuan2233 与开源贡献者.
