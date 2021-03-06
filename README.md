# dubbo学习项目
stu-springboot-dubbo-api服务接口  
stu-springboot-dubbo-provider服务提供者  
stu-springboot-dubbo-consumer服务消费者  
## 运行项目
#### 安装zookeeper
```shell script
docker run --name zookeeper -p 2181:2181 --restart always -d zookeeper
```
#### 安装dubbo-admin
```shell script
# root root / guest guest
docker run -d -p 8083:8080 -e dubbo.registry.address=zookeeper://172.16.1.5:2181 -e dubbo.admin.root.password=root -e dubbo.admin.guest.password=guest chenchuxin/dubbo-admin
```
#### 运行
1. 启动ProviderApplication
2. 启动ConsumerApplication
3. curl -i -X GET 'http://localhost:8081/getUserInfo?uid=1' 
#### dubbo基本概念
![dubbo.png](dubbo.png)
* 服务提供者（Provider）：暴露服务的服务提供方，服务提供者在启动时，向注册中心注册自己提供的服务。  
* 服务消费者（Consumer）: 调用远程服务的服务消费方，服务消费者在启动时，向注册中心订阅自己所需的服务，服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
* 注册中心（Registry）：注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者
* 监控中心（Monitor）：服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心
#### 调用关系说明
* 服务容器负责启动，加载，运行服务提供者。
* 服务提供者在启动时，向注册中心注册自己提供的服务。
* 服务消费者在启动时，向注册中心订阅自己所需的服务。
* 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
* 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
* 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。
## 常用配置和高可用
#### 版本号
当一个接口实现，出现不兼容升级时，可以用版本号过渡，版本号不同的服务相互间不引用。  
可以按照以下的步骤进行版本迁移：  
在低压力时间段，先升级一半提供者为新版本  
再将所有消费者升级为新版本  
然后将剩下的一半提供者升级为新版本  
#### 直连
注册中心全部宕掉后，服务提供者和服务消费者仍能通过本地缓存通讯
#### 负载均衡
1. Random LoadBalance  
随机，按权重设置随机概率。  
在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。
2. RoundRobin LoadBalance  
轮循，按公约后的权重设置轮循比率。  
存在慢的提供者累积请求的问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。
3. LeastActive LoadBalance   
最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。  
使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。
4. ConsistentHash LoadBalance  
一致性 Hash，相同参数的请求总是发到同一提供者。  
当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。
#### 服务降级
当服务器压力剧增的情况下，根据实际业务情况及流量，对一些服务和页面有策略的不处理或换种简单的方式处理，从而释放服务器资源以保证核心交易正常运作或高效运作。  
可以通过服务降级功能临时屏蔽某个出错的非关键服务，并定义降级后的返回策略。
#### 集群容错
在集群调用失败时，Dubbo 提供了多种容错方案
1. Failover Cluster  
失败自动切换，当出现失败，重试其它服务器。通常用于读操作，但重试会带来更长延迟。可通过 retries="2" 来设置重试次数(不含第一次)。
2. Failfast Cluster  
快速失败，只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录。
3. Failsafe Cluster  
失败安全，出现异常时，直接忽略。通常用于写入审计日志等操作。
4. Failback Cluster
失败自动恢复，后台记录失败请求，定时重发。通常用于消息通知操作。
5. Forking Cluster
并行调用多个服务器，只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 forks="2" 来设置最大并行数。
6. Broadcast Cluster
广播调用所有提供者，逐个调用，任意一台报错则报错 [2]。通常用于通知所有提供者更新缓存或日志等本地资源信息。