# Agora Online PK

*Read this in other languages: [English](README.md)*

这个开源示例项目演示了如果快速集成 [Agora](www.agora.io) 视频 SDK 和信令SDK，并利用 [ijkplayer](https://github.com/Bilibili/ijkplayer) 实现视频直播PK场景。

在这个示例项目中包含以下功能：

Agora（主播端） 

- 加入通话和离开通话
- 推流到CDN
- 实现一对一视频PK
- 静音和解除静音

ijkplayer（观众端）

- 利用ijkplayer从CDN拉流观看直播

## 运行示例程序
首先在 [Agora.io 注册](https://dashboard.agora.io/cn/signup/) 注册账号，并创建自己的测试项目，获取到 AppID。将 AppID 填写进PKConstants的MEDIA_APP_ID以及SIGNALING_APP_ID中

```

```
然后在 [Agora.io SDK](https://www.agora.io/cn/download/) 下载 视频通话 + 直播 SDK，解压后将其中的jar和so复制到项目对应文件夹下。

获取 [ijkplayer](https://github.com/Bilibili/ijkplayer)， 根据文档生成 so，放入项目文件夹下。

## 运行环境
* AndroidStudio
* 不支持模拟器

## FAQ
- 关于 ijkplayer 的相关使用可参看一下官方Git：

  [Android开发ijkplayer框架的集成](https://github.com/Bilibili/ijkplayer)
  

## 联系我们

- 完整的 API 文档见 [文档中心](https://docs.agora.io/cn/)
- 如果在集成中遇到问题，你可以到 [开发者社区](https://dev.agora.io/cn/) 提问
- 如果有售前咨询问题，可以拨打 400 632 6626，或加入官方Q群 12742516 提问
- 如果需要售后技术支持，你可以在 [Agora Dashboard](https://dashboard.agora.io) 提交工单
- 如果发现了示例代码的bug，欢迎提交 [issue](https://github.com/AgoraIO/ARD-Agora-Online-PK/issues)

## 代码许可

The MIT License (MIT).


