<img src="https://image.itbaima.net/markdown/2023/07/22/yM9WDH5TS4a6I7e.png"/>

## 深入SpringBoot数据交互
前面我们了解了SpringBoot以及一些常用的框架整合 相信各位小伙伴已经体验到SpringBoot带来的超便捷开发体验了 本章我们将深入讲解SpringBoot的数据交互 使用更多方便好用的持久层框架

### JDBC交互框架
除了我们前面一直认识的Mybatis之外 实际上Spring官方也提供了一个非常方便的JDBC操作工具 它同样可以快速进行增删改查 首先我们还是通过starter依赖导入:

```xml
                    <dependency>
                       <groupId>org.springframework.boot</groupId>
                       <artifactId>spring-boot-starter-jdbc</artifactId>
                    </dependency>
```

导入完成之后就可以轻松使用了

#### JDBC模版类
SpringJDBC为我们提供了一个非常方便的JdbcTemplate类 它封装了常用的JDBC操作 我们可以快速使用这些方法来实现增删改查 这里我们还是配置一下MySQL数据源信息:

```xml
                    <dependency>
                        <groupId>com.mysql</groupId>
                        <artifactId>mysql-connector-j</artifactId>
                    </dependency>
```

```yaml
                    spring:
                      datasource:
                        url: jdbc:mysql://localhost:3306/test
                        username: root
                        password: 123456
                        driver-class-name: com.mysql.cj.jdbc.Driver
```

我们要操作数据库 最简单直接的方法就是使用JdbcTemplate来完成:

```java
                    @Resource
                    JdbcTemplate template;
```

它给我们封装了很多方法使用 比如我们要查询数据库中的一条记录:

<img src="https://image.itbaima.net/markdown/2023/07/16/ygRp98mDKafXkw1.png"/>

我们可以使用queryForMap快速以Map为结果的形式查询一行数据:

```java
                    @Test
                    void contextLoads() {
    
                        Map<String, Object> map = template.queryForMap("select * from user where id = ?", 1);
                        System.out.println(map);
                        
                    }
```

非常方便:

<img src="https://image.itbaima.net/markdown/2023/07/20/ijczpNxh4fXoQKv.png"/>

我们也可以编写自定义的Mapper用于直接得到查询结果:

```java
                    @Data
                    @AllArgsConstructor
                    public class User {
                    
                        int id;
                        String name;
                        String email;
                        String password;
                        
                    }
```

```java
                    @Test
                    void contextLoads() {
    
                        User user = template.queryForObject("select * from user where id = ?",
                            (r, i) -> new User(r.getInt(1), r.getString(2), r.getString(3), r.getString(4)), 1);
                        System.out.println(user);
                        
                    }
```

当然除了这些之外 它还提供了update方法适用于各种情况的查询, 更新, 删除操作:

```java
                    @Test
                    void contextLoads() {
    
                        int update = template.update("insert into user values(2, 'admin', '654321@qq.com', '123456')");
                        System.out.println("更新了 " + update + " 行");
                        
                    }
```

这样 如果是那种非常小型的项目 甚至是测试用例的话 都可以快速使用JdbcTemplate快速进行各种操作

#### JDBC简单封装
对于一些插入操作 SpringJDBC为我们提供了更方便的SimpleJdbcInsert工具 它可以实现更多高级的插入功能 比如我们的表主键采用的是自增ID 那么它支持插入后返回自动生成的ID 这就非常方便了:

```java
                    @Configuration
                    public class WebConfiguration {
                    
                        @Resource
                        DataSource source;
                    
                        @Test
                        void contextLoads() {
                            
                          	// 这个类需要自己创建对象
                            SimpleJdbcInsert simple = new SimpleJdbcInsert(source)
                                    .withTableName("user") // 设置要操作的表名称
                                    .usingGeneratedKeyColumns("id"); // 设置自增主键列
                            Map<String, Object> user = new HashMap<>(2); // 插入操作需要传入一个Map作为数据
                            user.put("name", "bob");
                            user.put("email", "112233@qq.com");
                            user.put("password", "123456");
                            Number number = simple.executeAndReturnKey(user); // 最后得到的Numver就是得到的自增主键
                            System.out.println(number);
                            
                        }
                        
                    }
```

这样就可以快速进行插入操作并且返回自增主键了 还是挺方便的

<img src="https://image.itbaima.net/markdown/2023/07/20/xMeBEY3sdKVGmly.png"/>

当然 虽然SpringJDBC给我们提供了这些小工具 但是其实只适用于简单小项目 稍微复杂一点就不太适合了 下一部分我们将介绍JPA框架

### JAP框架
<img src="https://image.itbaima.net/markdown/2023/07/20/mq4Ut7BMI5XTDoN.png"/>

- 用了Mybatis之后 你看那个JDBC 真是太逊了
- 这么说 你的项目很勇哦?
- 开玩笑 我的写代码超勇的好不好
- 阿伟 你可曾幻想过有一天你的项目里不再有SQL语句?
- 不再有SQL语句? 那我怎么和数据库交互啊?
- 我看你是完全不懂哦
- 懂 懂什么啊?
- 你想懂? 来 到我项目里来 我给你看点好康的
- 好康? 是什么新框架哦?
- 什么新框架 比新框架还刺激 还可以让你的项目登duang郎哦
- 哇 杰哥 你项目里面都没SQL语句诶 这是用的什么框架啊?

在我们之前编写的项目中 我们不难发现 实际上大部分的数据库交互操作 到最后都只会做一个事情 那就是把数据库中的数据映射为Java中的对象
比如我们要通过用户名去查找对应的用户或是通过ID查找对应的学生信息 在使用Mybatis时 我们只需要编写正确的SQL语句就可以直接将获取的数据映射为对应的Java对象
通过调用Mapper中的方法就能直接获得实体类 这样就方便我们在Java中数据库表中的相关信息了

但是以上这些操作都有一个共性 那就是它们都是通过某种条件去进行查询 而最后的查询结果 都是一个实体类 所以你会发现你写的很多SQL语句都是一个套路`select * from xxx where xxx=xxx`
实际上对于这种简单SQL语句 我们完全可以弄成一个模版来使用 那么能否有一种框架 帮我们把这些相同的套路给封装起来 直接把这类相似的SQL语句给屏蔽掉 不再由我们编写 而是让框架自己去组合拼接

#### 认识SpringData JPA
首先我们来看一下国外的统计:

<img src="https://image.itbaima.net/markdown/2023/03/06/XaoLIPrjDKzO9Tx.png"/>

不对吧 为什么Mybatis这么好用 这么强大 却只有10%的人喜欢呢? 然而事实就是 在国外JPA几乎占据了主导地位 而Mybatis并不像国内那样受待见
所以你会发现 JPA都有SpringBoot的官方直接提供的starter 而Mybatis没有 直到SpringBoot3才开始加入到官方模版中

那么 什么是JPA?

    JPA(Java Persistence API)和JDBC类似 也是官方定义的一组接口 但是它相比传统的JDBC 它是为了实现ORM而生的 即Object-Relationl Mapping
    它的作用是在关系型数据库和对象之间形成一个映射 这样 我们在具体的操作数据库的时候 就不需要再去和复杂的SQL语句打交道 只要像平时操作对象一样操作它就可以了

    其中比较常见的JPA实现有:
     1. Hibernate: Hibernate是JPA规范的一个具体实现 也是目前使用最广泛的JPA实现框架之一 它提供了强大的对象关系映射功能 可以将Java对象映射到数据库表中 并提供了丰富的查询语言和缓存机制

     2. EclipseLink: EclipseLink是另一个流行的JPA实现框架 由Eclipse基金会开发和维护 它提供了丰富的特性 如对象关系映射,缓存,查询语言和连接池管理等 并具有较高的性能和可扩展性

     3. OpenJPA: OpenJPA是Apache基金会的一个开源项目 也是JPA规范的一个实现 它提供了高性能的JPA实现和丰富的特性 如延迟加载,缓存和分布式事务等

     4. TopLink: TopLink是Oracle公司开发的一个对象关系映射框架 也是JPA规范的一个实现 虽然EclipseLink已经取代了TopLink成为Oracle推荐的JPA实现 但TopLink仍然得到广泛使用

在之前 我们使用JDBC或是Mybatis来操作数据 通过直接编写对应的SQL语句来实现数据访问 但是我们发现实际上我们在Java中大部分操作数据库的情况都是读取数据并封装为一个实体类
因此 为什么不直接将实体类直接对应到一个数据库表呢? 也就是说 一张表里面有什么属性 那么我们的对象就有什么属性 所有属性根据数据库里面的字段一一对应 而读取数据时
只需要读取一行的数据并封装为我们定义好的实体类既可 而具体的SQL语句执行 完全可以交给框架根据我们定义的映射关系去生成 不再由我们去编写 因为这些SQL实际上都是千篇一律的

而实现JPA规范的框架一般最常用的就是Hibernate 它是一个重量级框架 学习难度相比Mybatis也更高一些 而SpringDataJPA也是采用Hibernate框架作为底层实现 并对其加以封装

官网: https://spring.io/projects/spring-data-jpa

#### 使用JPA快速上手
































