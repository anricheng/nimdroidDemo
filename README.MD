## 一、MVP简介

![MVP](MVP.png)

* Model: 数据层，负责与网络层和数据库层的逻辑交互。
* View: UI层，显示数据, 并向Presenter报告用户行为。
* Presenter: 从Model拿数据，应用到UI层，管理UI的状态，响应用户的行为。

## 二.MVP 优势

* 分离了视图逻辑和业务逻辑，降低了耦合。
* Activity只处理生命周期的任务，代码变得更加简洁。
* 视图逻辑和业务逻辑分别抽象到了View和Presenter的接口中，提高代码的阅读性。
* Presenter被抽象成接口，可以有多种具体的实现，所以方便进行单元测试。
* 把业务逻辑抽到Presenter中去，避免后台线程引用着Activity导致Activity的资源无法被系统回收从而引起内存泄露和OOM。

## 三.分支简介

这是一个MVP 的Simple Demo,总共包含四个分支：

* mvp 分支：简单的MVP Demo;

* mvp-dagger 分支： 基于dagger 搭建的MVP 的简单Demo;

* master 分支：目前跟mvp-dagger 分支同步；

* feature/sample-google-mvp-rxjava 分支: 主要介绍 rxJava 的使用示例




