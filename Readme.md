## QuickSearch —— Windows系统下的文件、文本内容快搜工具

### 简介

<u>windows上的快搜工具？我直接用Everything或者Listrary不就行了，你这有啥用？</u>

好吧，一开始我也是用的上面两款工具，不过，**当我遇到下面的情况时**，我就emmmm了:sob::sob::sob::sob::sob::sob::

1. 这些工具时不时得要重建索引，否则的话，有些你新建不旧的文件就搜索不到了 :cry::cry::cry:
2. 这些工具都**只能根据文件名进行搜索**，如果我想根据文本的内容进行搜索，就不行了，唉。。。
3. 担心这些是不是有联网行为，我的数据安全吗?:scream::scream::scream:

<u>**So!我就萌生了想自己写一个小工具的想法，主要解决几个问题：**</u>

- 我的工具可以根据文本内容去搜索，然后找到文本内容所在的文件，那么你就可以有以下应用场景了：
  - 我上周记录了一个`linux`相关的笔记，但是电脑上有linux相关的文件太多了，我忘记了在哪个文件里了
- 我的小工具可以根据文件后缀进行查找，同时对于“大小写”也可以忽略
- 如果我想找的是某个`目录`而不是文件呢，完全可以的。
- 模糊查找；多个关键词查找；....
- 还有很多。。。。

### 使用效果

https://user-images.githubusercontent.com/83388493/232200964-0c7e2c04-10cf-4da9-bc0d-8bb288d35168.mp4

### 图文分解

#### 文本内容查找

![image-20230408114440170](img/Readme.assets/image-20230408114440170.png)

#### 分号分割进行多个关键词同时查找

例如：linux;JDK表示同时包含：linux和jdk

![image-20230408114604631](img/Readme.assets/image-20230408114604631.png)

#### 分隔符进行多个关键词任意查找

例如：linux|进程表示包含linux或者进程关键词

![image-20230408114749527](img/Readme.assets/image-20230408114749527.png)

#### 文件名查找

![image-20230408114836246](img/Readme.assets/image-20230408114836246.png)

#### 目录名查找

![image-20230408114906492](img/Readme.assets/image-20230408114906492.png)

#### 指定后缀查找

例如：只查找.md后缀的文件

![image-20230408114930048](img/Readme.assets/image-20230408114930048.png)

### 使用

电脑有Jdk1.8以上环境

Clone本项目，点击start.bat即可启动程序，然后在浏览器输入：http://127.0.0.1/，点击shutdown.bat即可关闭程

### 后续更新的方向

目前对文本文件的支持就好，其他类型文件较差，将会进行改进

考虑加入搜索引擎，提高检索速度
