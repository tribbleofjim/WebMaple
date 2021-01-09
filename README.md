# WebMaple
以webmagic为内核的分布式可视化爬虫框架，参考webmagic-avalon的架构，目标是提供可分布式部署的爬虫框架，以及可视化的爬虫管理界面。

目前分为三个模块：

1. maple-admin，为webmagic-avalon中的admin节点，对应一个java进程。负责与多个worker通信，监听worker的存活；从worker处获得当前爬虫信息，并在前端页面展示；调用worker的接口创建爬虫。它是一个Springboot项目，可以直接打包上传到服务器并启动。
2. maple-worker，为worker节点，对应一个java进程。一台机器上可部署多个worker。负责具体爬虫（spider）的创建和管理，以及向admin节点发送心跳包确保自己的存活。它和admin一样是可以直接打成jar包部署的。
3. maple-network，为网络通信部分的通用模块。

