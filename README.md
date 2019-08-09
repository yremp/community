# community
### 项目预览<br>
#### 1.[在线演示地址](http://www.yremp.live:1234)
#### 2.图片预览：
##### 1.主页
![](http://yremp.hk.ufileos.com/38ae6d89-5de4-44c3-8ee9-703c962432cd.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=q2oyg%2BotvDmb6IjT3NR%2F72XuH68%3D&Expires=1880288397)
##### 2.帖子详情
![](http://yremp.hk.ufileos.com/bdf1cbab-1be6-4dfc-af2f-ff57064b0199.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=azCuVUGOxEUj3%2FZ4sEAZcfr1hg0%3D&Expires=1880288722)
##### 3.回复和评论
![](http://yremp.hk.ufileos.com/75665e02-252f-4295-82da-a174d5430fc3.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=G2FIXPULPGAef0EzgYTJTMkBzac%3D&Expires=1880288840)
##### 4.个人资料
![](http://yremp.hk.ufileos.com/9f34b6fc-6280-4f06-b532-811fddcf8af1.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=0McqMH40VePPvAzurjIBcDszH9U%3D&Expires=1880288973)
![](http://yremp.hk.ufileos.com/1887c95d-f645-46df-97b2-53166a3a6cf4.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=AFayW12hx1W7kjiY09EIv3fOa6M%3D&Expires=1880288990)
![](http://yremp.hk.ufileos.com/1dbff5ae-f910-4234-b3c9-01ec481b1522.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=WemP%2FVVBLhBJkkKjlDlTT4E3t5s%3D&Expires=1880289014)
![](http://yremp.hk.ufileos.com/2bb452c7-ee07-4b69-aad7-8fbb28994e07.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=LOcBj1T8uT%2FXdFMWQC2MzAmGFU8%3D&Expires=1880289025)
##### 公共资料面板
![](http://yremp.hk.ufileos.com/6198ce14-398e-4e63-bfc8-5606806fc1f1.png?UCloudPublicKey=TOKEN_c8840aa4-b6d1-4b64-b8d0-4f759247250b&Signature=9oZv9wbnJ6mDiIhBk1AY06v0DHs%3D&Expires=1880289942)
#### 个人资料的前端页面还有很大修改的空间
### 目录说明
#### pom.xml maven文件，项目依赖
#### 主要的文件在/src/main/ 下面：
1. community：项目的主包
2. resources：资源文件目录
#### community：
1.advice &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;下面的类统一处理controller异常<br>
2.cntroller&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;处理前端请求<br>
3.dto&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;数据传输对象<br>
3.entity&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;实体类<br>
4.enums&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;枚举<br>
5.exception&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;自定义异常<br>
6.intercept&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;拦截器【因为有问题下面的类全部注释了】<br>
7.mapper&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;mybatis xml 接口<br>
8.provider&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;主要用于连接github uclound<br>
9.service&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;service层，处理复杂的业务逻辑<br>
10.CommunityApplication&ensp;启动类<br>
#### resource：
1.database &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;sql文件<br>
2.mybatis&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;存放mybatis配置文件和xml文件<br>
3.static&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;静态资源文件[基本没有使用，都是cdn]<br>
4.templates&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;html文件目录<br>
5.application.properties&ensp;&ensp;&ensp; 配置文件<br>
#### 注意事项：
1.intercept 这个包可以删除<br>
2.static下面的editormd 是markdown编辑器文件，但是我并没有使用，而是将它放到了我个人服务器上面然后引用的。<br>
3.clone项目后可能无法使用，需要你修改一下 OAuth Apps 信息，并且在html模板中修改client_id;新建mysql数据库<br>
4.数据库名称就是项目名称，数据库字段相关都在 database 下面的sql文件里面<br>
###  具体的使用逻辑
这个暂时不细说，代码里面都有注释。有任何问题请到[我的博客](https://yremp.live)留言，博客上也有我的联系方式，这个社区在线演示也会长期有效。



