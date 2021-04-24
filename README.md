# WebMaple
以webmagic为内核的分布式可视化爬虫框架，参考webmagic-avalon的架构，目标是提供可分布式部署的爬虫框架，以及可视化的爬虫管理界面。

目前分为三个模块：

1. maple-admin，为webmagic-avalon中的admin节点，对应一个java进程。负责：

   - 与多个worker通信，监听worker的存活；
   - 从worker处获得当前爬虫信息，并在前端页面展示；
   - 调用worker的接口创建爬虫。

   它是一个Springboot项目，可以直接打包上传到服务器并启动。

2. maple-worker，为worker节点，对应一个java进程。一台机器上可部署多个worker。负责：

   - 具体爬虫（spider）的创建和管理；
   - 向admin节点发送心跳包确保自己的存活。

   它和admin一样是可以直接打成jar包部署的。

3. maple-common，两个包之间的公用模块，存放一些通用的类。

# 使用文档
使用worker时需要：

1. 引入worker包：

   ```xml
   <dependency>
       <groupId>org.webmaple</groupId>
       <artifactId>worker</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   ```
   
   注意：当前worker包还未发布到maven中央仓库，可以下载项目后自行将worker文件夹打包并部署到本地仓库，然后就可以在本地项目中引入worker包查看效果了。

2. 在application.yml里填写webmaple的选项如下：（目前均为必填）

   ```yaml
   webmaple:
     admin:
       host: 127.0.0.1 # 自由修改
       port: 8080 # 自由修改
       heartbeat: heartbeat
     worker:
       port: 8080 # 自由修改
       package-path: org.webmaple.worker # 此处修改成你的项目的根目录，比如你的项目名叫testPro，包路径为com.testPro，则修改为com.testPro
   ```

3. 在worker项目中，完成 你想使用web Magic去爬取的网站 对应的downloader，processor和pipeline，并使用注解@MaplePipeline和@MapleProcessor去标明它们，以及它们对应的网站site；

4. 实现JedisSPI接口，worker将使用这个接口获取jedispool，并在项目中使用。



使用admin时需要：

1. 在github上下载java工程，在本地ide打开；

2. 在yml文件里配置如下：

   ```yaml
   server:
     port: 8080 # 非必填
   spring:
     servlet:
       multipart: # jar包上传配置
         max-file-size: 1000MB
         max-request-size: 1GB
     datasource: # mysql配置
       username: root
       password: 123456
       url: jdbc:mysql://127.0.0.1:3306/webmaple?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
       driver-class-name: com.mysql.cj.jdbc.Driver
     redis: # redis配置
       host: 127.0.0.1
       port: 6379
       timeout: 10000
       block-when-exhausted: true
       password: password
       jedis:
         pool:
           max-idle: 50
           max-wait: 5000
   mybatis: # mybatis配置
     type-aliases-package: org.webmaple.admin.model
     configuration:
       map-underscore-to-camel-case: true
   webmaple: # webmaple相关配置
     jar: /Users/lyifee/TriSources # 此处替换成服务器上的jar包暂存路径
   ```

3. 在对应的mysql中创建一个名为webmaple的数据库；

4. 运行test文件夹下，org.webmaple.admin.CreateTable这个类中的createTable方法，执行sql语句建立需要的数据表，如下：

   ```java
   @SpringBootTest(classes = AdminApplication.class)
   public class CreateTable {
       @Test
       @Sql("/table.sql")
       public void createTable() {}
   }
   ```

5. 将admin项目打成jar包并部署到服务器上；

6. 首次部署可使用初始管理员账号登录admin系统，初始管理员账号手机号为123456，昵称和密码均为admin。登录后请及时在系统中进行修改。如果允许的话也可以直接在数据库里进行修改。


[chromedriver_mac64.zip](../../Downloads/chromedriver_mac64.zip) 存放一个mac版的chromedriver。。。

