# PCC-like架构设计
## 1.需求核心点
> ### 功能

- 可以对一个对象（一条feed、文章、或者url）进行like操作，禁止like两次，第二次like返回错误码
- 需要看到一个对象的like总数
- 可以看到一个对象的like用户列表；
- 有isLike接口，返回参数指定的对象有没有被当前用户like过
- 加分项：优先显示我的好友列表(social list)。

> ### 性能

- 数据量：用户1000万；平均好友数1000，正态分布；
- 性能要求：like计数器查询30万次/秒；每日新增like（写入）数量为1000万。
- **考虑平均？是否需要处理峰值情况？** 写入平均1000万/86400 ≈ 116个/s;查询30万次/s

> ### 其他要求

- 数据要求必须用关系数据库持久化，其他自选
- 提供三台机器
- 部署方案自定

## 2.技术选型
- **关系数据库**：MySQL 5.7.13(homebrew安装，方便调试)
- **计数、查询缓存**：Redis
- **接口实现**：spring-boot

## 3.数据结构设计

> ### MySQL DDL：

 注：目前只做单库测试，所以主键采用自增。如果需要分库分表，主键可采用发号器，当当的sharding-jdbc提供了一个简单实现，时间戳+hostname+sequence。

1. p_user

```sql
CREATE TABLE `p_user` (
  `uid` bigint unsigned not null AUTO_INCREMENT COMMENT 'pk',
  `username` varchar(20) not null DEFAULT '' COMMENT '用户名',
  `nickname` varchar(50) not null DEFAULT '' COMMENT '昵称',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `avatar` varchar(100) not null DEFAULT '' COMMENT '头像',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

2. p_user_relation

```sql
CREATE TABLE `p_user_relation` (
  `id` bigint unsigned not null AUTO_INCREMENT COMMENT 'pk',
  `from_uid` bigint unsigned not null DEFAULT 0 COMMENT '关注方',
  `to_uid` bigint unsigned not null DEFAULT 0  COMMENT '被关注方',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint not null DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE index_relation (`from_uid`,`to_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

3. b_feed

```sql
CREATE TABLE `b_feed` (
  `id` bigint unsigned not null AUTO_INCREMENT COMMENT 'pk',
  `content` varchar(200) not null DEFAULT '' COMMENT '内容',
  `uid` bigint unsigned not null DEFAULT 0  COMMENT '作者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint not null DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

4. b_feed_like

```sql
CREATE TABLE `b_feed_like` (
  `id` bigint unsigned not null AUTO_INCREMENT COMMENT 'pk',
  `feed_id` bigint unsigned not null DEFAULT 0 COMMENT 'feed_id',
  `like_uid` bigint unsigned not null DEFAULT 0  COMMENT '点赞人',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE index_like (`feed_id`,`like_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## 4.接口设计

1. like
2. unlike
3. likeUserList
4. likeUserCount
5. hasLiked
6. listFeed

## 5.实现方案

> ### 持久化存储

- p_user：千万级别，单表搞定
- p_user_relation:平均好友1000，1000万*1000=100亿。需分库、分表
- b_feed：每日新增未给出，暂定500万，500万*30天=1.5亿。需分库、分表
- b_feed_like：每日新增1000万，1000万*30天=3亿。需分库、分表

> ### 缓存设计

- feedLike计数器：key：like_count:feed_id；value:存储String，like,unlike操作时，分别incr,decr。
- likeList列表：key: like_list:feed_id;value:lists，like时，rpush插入用户id，unlike时，srem删除对应的用户id。
- friends列表：key: friends:uid；value:lists，存储所有好友的uid列表。
- 点赞的好友列表：key:like_friends:feed_id;value:lists,likeList列表与friends列表求交集，得到好友list。
- 点赞的其他人列表：key:like_others:feed_id;value:lists，likeList列表与friends列表求差集，得到非好友list。

> ### 其他设计

- 用户、feed可采用hash结构存储在redis中，也可以直接从数据库中通过pk获取。
- 用户的timeline，可以将feed_id列表存储在redis中，以timeline:uid作为key。

## 6.性能及扩展性

- 数据库分库分表可采用proxy的模式，不存在问题。
- redis如果get压力大，采用一致性hash，把key分散在不同的redis实例上，也可直接使用redis cluster。
- 以上，均需求测试中优化。

## 7.使用
项目依赖于maven构建，需要jdk1.8版本。
项目下载后，在主项目下面运行：

```
mvn install
```
上述命令完成后，进入pcc-restapi项目，运行：

```
mvn spring-boot:run
```
等系统启动后，访问：http://localhost:8080/likes/1 即可测试。