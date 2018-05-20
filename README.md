# EasyBilibili
仿b站安卓客户端
-----

##介绍
依赖项目：

* [flowlayout:flyco](https://github.com/H07000223/FlycoTabLayout)
* [flowlayout:zhy](https://github.com/hongyangAndroid/FlowLayout)
* [RxJava2](https://github.com/ReactiveX/RxJava)
* [RxAndroid2](https://github.com/ReactiveX/RxAndroid)
* [Rxbinding2](https://github.com/JakeWharton/RxBinding)
* [Rxlifecycle2](https://github.com/trello/RxLifecycle)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [dagger](https://github.com/square/dagger)
* [glide](https://github.com/bumptech/glide)
* [okhttp3](https://github.com/square/okhttp)
* [retrofit2](https://github.com/square/retrofit)
* [ijkplayer](https://github.com/Bilibili/ijkplayer)
* [烈焰弹幕使](https://github.com/Bilibili/DanmakuFlameMaster)
* [materialsearchview](https://github.com/MiguelCatalan/MaterialSearchView)
* [jsoup](https://jsoup.org/)
* [bugly](https://bugly.qq.com/docs/)

参考项目：

* [bilibili-android-client](https://github.com/HotBitmapGG/bilibili-android-client)
* [bilisoleil](https://github.com/yoyiyi/bilisoleil)

实现功能：

* 由于b站的api改用了ssl加密，因此大部分api已经看不到了，只能从网页端去扒。在这里特别感谢参考的两位大神辛苦提供的api。
* b站的app页面逻辑复杂，recyclerview内item继承自StatelessSection，根据不同的数据实现不同的item格式。
* 部分页面使用的是本地的json数据，覆盖了失效的api。
* 使用MVP进行重构。
* 目前app还能稳定运行（2018-5-20）。但是什么时候如果app运行崩溃或者页面空白，那不用说，肯定是老api又失效了。所以请珍惜现在的时光。。。

预览：              

<img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110237.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110241.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110301.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110311.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110316.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110324.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110328.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110332.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110336.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110341.jpg"/>   <img width="300" height="550" src="https://github.com/GramYang/EasyBilibili/blob/master/images/Screenshot_20180520-110348.jpg"/>



## License

    Copyright 2014 GramYang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



