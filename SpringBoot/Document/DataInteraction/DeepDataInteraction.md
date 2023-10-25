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
同样的 我们只需要导入stater依赖即可:

```xml
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-data-jpa</artifactId>
                    </dependency>
```

接着我们可以直接创建一个类 比如用户类 我们只需要把一个账号对应的属性全部定义好即可:

```java
                    @Data
                    public class Account {
    
                        int id;
                        String username;
                        String password;
                        
                    }
```

接着 我们可以通过注解形式 在属性上添加数据库映射关系 这样就能够让JPA知道我们的实体类对应的数据库表长啥样 这里用到了很多注解:

```java
                    @Data
                    @Entity // 表示这个类是一个实体类
                    @Table(name = "account") // 对应的数据库中表名称
                    public class Account {
                    
                        @GeneratedValue(strategy = GenerationType.IDENTITY) // 生成策略 这里配置为自增
                        @Column(name = "id") // 对应表中id这一列
                        @Id // 此属性为主键
                        int id;
                    
                        @Column(name = "username") // 对应表中username这一列
                        String username;
                    
                        @Column(name = "password") // 对应表中password这一列
                        String password;
                        
                    }
```

接着我们来修改一下配置文件 把日志打印给打开:

```yaml
                    spring:
                        jpa:
                          # 开启SQL语句执行日志信息
                          show-sql: true
                          hibernate:
                            # 配置为检查数据库表结构 没有时会自动创建
                            ddl-auto: update
```

`ddl-auto`属性用于设置自动表定义 可以实现自动在数据库中为我们创建一个表 表的结构会根据我们定义的实体类决定 它有以下几种:
- `none`: 不执行任何操作 数据库表结构需要手动创建
- `create`: 框架在每次运行时都会删除所有表 并重新创建
- `create-drop`: 框架在每次运行时都会删除所有表 然后再创建 但在程序结束时会再次删除所有表
- `update`: 框架会检查数据库表结构 如果与实体类定义不匹配 则会做相应的修改 以保持它们的一致性
- `validate`: 框架会检查数据库表结构与实体类定义是否匹配 如果不匹配 则会抛出异常

这个配置置顶的作用是为了避免手动管理数据库表结构 使开发者可以更方便地进行开发和测试 但在生产环境中 更推荐使用数据库迁移工具来管理表结构的变更

我们可以在日志中发现 在启动时执行了如下SQL语句:

<img src="https://image.itbaima.net/markdown/2023/07/20/kABZVhJ8vjKSqzT.png"/>

我们的数据库中对应的表已经自动创建好了

我们接着来看如何访问我们的表 我们需要创建一个Repository实现类:

```java
                    @Repository
                    public interface AccountRepository extends JpaRepository<Account, Integer> {
    
                    }
```

注意: JpaRepository有两个泛型 前者是具体操作的对象实体 也就是对应的表 后者是ID的类型
接口中已经定义了比较常用的数据库操作 编写接口继承即可 我们可以直接注入此接口获得实现类:

```java
                    @Resource
                    AccountRepository repository;
                    
                    @Test
                    void contextLoads() {
                        
                        Account account = new Account();
                        account.setUsername("小红");
                        account.setPassword("1234567");
                        System.out.println(repository.save(account).getId()); // 使用save来快速插入数据 并且会返回插入的对象 如果存在自增ID 对象的自增id属性会自动被赋值 这就很方便了
                            
                    }
```

执行结果如下:

<img src="https://image.itbaima.net/markdown/2023/07/20/ksI3J5eidzTrvyL.png"/>

同时 查询操作也很方便:

```java
                    @Test
                    void contextLoads() {
    
                      	// 默认通过通过ID查找的方法 并且返回的结果是Optional包装的对象 非常人性化
                        repository.findById(1).ifPresent(System.out::println);
                        
                    }
```

得到结果:

<img src="https://image.itbaima.net/markdown/2023/07/20/TRHOWbop267Al4Q.png"/>

包括常见的一些计数,删除操作等都包含在里面 仅仅配置应该接口就能完美实现增删改查:

<img src="https://image.itbaima.net/markdown/2023/07/21/uIBciLqFsH5tdDR.png"/>

我们发现 使用了JPA之后 整个项目的代码中没有出现任何的SQL语句 可以说是非常方便了 JPA依靠我们提供的注解信息自动完成了所有信息的映射和关联

相比Mybatis JPA几乎就是一个全自动的ORM框架 而Mybatis则顶多算是半自动ORM框架

#### 方法名称拼接自定义SQL
虽然接口预置的方法使用起来非常方便 但是如果我们需要进行条件查询等操作或是一些判断 就需要自定义一些方法来实现 同样的
我们不需要编写SQL语句 而是通过方法名称的拼接来实现条件判断 这里列出了所有支持的条件判断名称:

| 属性           | 拼接方法名称示例                   | 执行的语句                                                        |     
|--------------|----------------------------|--------------------------------------------------------------|
| Distinct       | findDistinctByLastnameAndFirstname | select distinct … where x.lastname = ?1 and x.firstname = ?2 |
| And          | findByLastnameAndFirstname | ️… where x.lastname = ?1 and x.firstname = ?2                |
| Is，Equals | findByFirstname,findByFirstnameIs,findByFirstnameEquals | … where x.firstname = ?1                                     |
| Between | 	findByStartDateBetween  | … where x.startDate between ?1 and ?2                        |
| LessThan | findByAgeLessThan  | … where x.age < ?1                                           |
| LessThanEqual | 	findByAgeLessThanEqual  | … where x.age <= ?1                                          |
| GreaterThan | findByAgeGreaterThan  | … where x.age > ?1                                           |
| GreaterThanEqual | findByAgeGreaterThanEqual  | … where x.age >= ?1                                          |
| After | findByStartDateAfter  | 	… where x.startDate > ?1                                    |
| Before | findByStartDateBefore  | … where x.startDate < ?1                                     |
| IsNull，Null | findByAge(Is)Null  | … where x.age is null                                        |
| IsNotNull，NotN | findByAge(Is)NotNull  | … where x.age not null                                       |
| Like | findByFirstnameLike  | … where x.firstname like ?1                                  |
| NotLike | findByFirstnameNotLike | … where x.firstname not like ?1                              |
| StartingWith | findByFirstnameStartingWith  | … where x.firstname like ?1（参数与附加%绑定） |
| EndingWith | findByFirstnameEndingWith  | … where x.firstname like ?1（参数与前缀%绑定） |
| Containing | findByLastnameOrFirstname  | … where x.lastname = ?1 or x.firstname = ?2                  |
| OrderBy | findByAgeOrderByLastnameDesc | … where x.lastname = ?1 or x.firstname = ?2                  |
| Not | findByLastnameNot  | … where x.age = ?1 order by x.lastname desc |
| In | findByAgeIn(Collection ages) | … where x.age in ?1 |
| NotIn | findByAgeNotIn(Collection ages)  | … where x.age not in ?1 |
| True | findByActiveTrue | … where x.active = true |
| False | findByActiveFalse | … where x.active = false  |
| IgnoreCase | findByFirstnameIgnoreCase | … where UPPER(x.firstname) = UPPER(?1) |

比如我们想要实现根据用户名模糊匹配查找用户:

```java
                    @Repository
                    public interface AccountRepository extends JpaRepository<Account, Integer> {
                    
                        // 按照表中的规则进行名称拼接 不用刻意去记 IDEA会有提示
                        List<Account> findAllByUsernameLike(String str);
                        
                    }
```

我们来测试一下:

```java
                    @Test
                    void contextLoads() {
                        repository.findAllByUsernameLike("%明%").forEach(System.out::println);
                    }
```

<img src="https://image.itbaima.net/markdown/2023/07/21/mioZaUk7Yj3QDxb.png"/>

又比如我们想同时根据用户名和ID一起查询:

```java
                    @Repository
                    public interface AccountRepository extends JpaRepository<Account, Integer> {
    
                        List<Account> findAllByUsernameLike(String str);
                    
                        Account findByIdAndUsername(int id, String username);
                        // 也可以使用Optional类进行包装 Optional<Account> findByIdAndUsername(int id, String username);
    
                    }
```

```java
                    @Test
                    void contextLoads() {
                        System.out.println(repository.findByIdAndUsername(1, "小明"));
                    }
```

比如我们想要判断数据库中是否存在某个ID的用户:

```java
                    @Repository
                    public interface AccountRepository extends JpaRepository<Account, Integer> {
    
                        List<Account> findAllByUsernameLike(String str);
                        Account findByIdAndUsername(int id, String username);
                        // 使用exists判断是否存在
                        boolean existsAccountById(int id);
                        
                    }
```

注意自定义条件操作的方法名称一定要遵循规则 不然会出现异常:

```log
                    Caused by: org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract  ...
```

有了这些操作 我们在编写一些简单SQL的时候就很方便了 用久了甚至直接忘记SQL怎么写

#### 关联查询
在实际开发中 比较常见的场景还有关联查询 也就是我们会在表中添加一个外键字段 而此外键字段有指向了另一个表中的数据 当我们查询数据时 可能会需要将关联数据也一并获取
比如我们想要查询某个用户的详细信息 一般用户简略信息会单独存放一个表 而用户详细信息会单独存放在另一个表中 当然 除了用户详细信息之外
可能在某些电商平台还会有用户的购买记录,用户的购物车 交流社区中的用户帖子,用户评论等 这些都是需要根据用户信息进行关联查询的内容

<img src="https://image.itbaima.net/markdown/2023/03/06/WnPEmdR2sDLuwGN.jpg"/>

我们知道 在JPA 每张表实际上就是一个实体类的映射 而表之间的关联关系 也可以看作对象之间的依赖关系
比如用户表中包含了用户详细信息的ID字段作为外键 那么实际上就是用户表实体类中包括了用户详细信息实体对象:

```java
                    @Data
                    @Entity
                    @Table(name = "users_detail")
                    public class AccountDetail {
                    
                        @Column(name = "id")
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        @Id
                        int id;
                    
                        @Column(name = "address")
                        String address;
                    
                        @Column(name = "email")
                        String email;
                    
                        @Column(name = "phone")
                        String phone;
                    
                        @Column(name = "real_name")
                        String realName;
                        
                    }
```

而用户信息和用户详细信息之间形成了一对一的关系 那么这时我们就可以直接在类中指定这种关系:

```java
                    @Data
                    @Entity
                    @Table(name = "users")
                    public class Account {
                    
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        @Column(name = "id")
                        @Id
                        int id;
                    
                        @Column(name = "username")
                        String username;
                    
                        @Column(name = "password")
                        String password;
                    
                        @JoinColumn(name = "detail_id") // 指定存储外键的字段名称
                        @OneToOne // 声明为一对一关系
                        AccountDetail detail;
                        
                    }
```

在修改实体类信息后 我们发现在启动时也进行了更新 日志如下:

```log
                    Hibernate: alter table users add column detail_id integer
                    Hibernate: create table users_detail (id integer not null auto_increment, address varchar(255), email varchar(255), phone varchar(255), real_name varchar(255), primary key (id)) engine=InnoDB
                    Hibernate: alter table users add constraint FK7gb021edkxf3mdv5bs75ni6jd foreign key (detail_id) references users_detail (id)
```

是不是感觉非常方便 都懒得去手动改表结构了

接着我们往用户详细信息中添加一些数据 一会我们可以直接进行查询:

```java
                    @Test
                    void pageAccount() {
                        repository.findById(1).ifPresent(System.out::println);
                    }
```

查询后 可以发现 得到如下结果:

```log
                    Hibernate: select account0_.id as id1_0_0_, account0_.detail_id as detail_i4_0_0_, account0_.password as password2_0_0_, account0_.username as username3_0_0_, accountdet1_.id as id1_1_1_, accountdet1_.address as address2_1_1_, accountdet1_.email as email3_1_1_, accountdet1_.phone as phone4_1_1_, accountdet1_.real_name as real_nam5_1_1_ from users account0_ left outer join users_detail accountdet1_ on account0_.detail_id=accountdet1_.id where account0_.id=?
                    Account(id=1, username=Test, password=123456, detail=AccountDetail(id=1, address=四川省成都市青羊区, email=8371289@qq.com, phone=1234567890, realName=本伟))
```

也就是 在建立关系之后 我们查询Account对象时 会自动将关联数据的结果也一并进行查询

那要是我们只想要Account的数据 不想要用户详细信息数据怎么办呢? 我希望在我要用的时候再获取详细信息 这样可以节省一些网络开销 我们可以设置懒加载 这样只有在需要时才会向数据库获取

```java
                    @JoinColumn(name = "detail_id")
                    @OneToOne(fetch = FetchType.LAZY) // 将获取类型改为LAZY
                    AccountDetail detail;
```

接着我们测试一下:

```java
                    @Transactional // 懒加载属性需要在事务环境下获取 因为repository方法调用完后Session会立即关闭
                    @Test
                    void pageAccount() {
    
                        repository.findById(1).ifPresent(account -> {
                            System.out.println(account.getUsername()); // 获取用户名
                            System.out.println(account.getDetail()); // 获取详细信息(懒加载)
                        });
                        
                    }
```

接着我们来看看控制台输出了什么:

```log
                    Hibernate: select account0_.id as id1_0_0_, account0_.detail_id as detail_i4_0_0_, account0_.password as password2_0_0_, account0_.username as username3_0_0_ from users account0_ where account0_.id=?
                    Test
                    Hibernate: select accountdet0_.id as id1_1_0_, accountdet0_.address as address2_1_0_, accountdet0_.email as email3_1_0_, accountdet0_.phone as phone4_1_0_, accountdet0_.real_name as real_nam5_1_0_ from users_detail accountdet0_ where accountdet0_.id=?
                    AccountDetail(id=1, address=四川省成都市青羊区, email=8371289@qq.com, phone=1234567890, realName=卢本)
```

可以看到 获取用户名之前 并没有去查询用户的详细信息 而是当我们获取详细信息时才进行查询并返回AccountDetail对象

那么我们是否也可以在添加数据时 利用实体类之间的关联信息 一次性添加两张表的数据呢? 可以 但是我们需要稍微修改一下级联关联操作设定:

```java
                    @JoinColumn(name = "detail_id")
                    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 设置关联操作为ALL
                    AccountDetail detail;
```

- ALL: 所有操作都进行关联操作
- PERSIST: 插入操作时才进行关联操作
- REMOVE: 删除操作时才进行关联操作
- MERGE: 修改操作时才进行关联操作

可以多个并存 接着我们来进行一下测试:

```java
                    @Test
                    void addAccount() {
                    
                        Account account = new Account();
                        AccountDetail detail = new AccountDetail();
                        
                        account.setUsername("Nike");
                        account.setPassword("123456");
                        
                        detail.setAddress("重庆市渝中区解放碑");
                        detail.setPhone("1234567890");
                        detail.setEmail("73281937@qq.com");
                        detail.setRealName("张三");
                        
                      	account.setDetail(detail);
                        account = repository.save(account);
                        
                        System.out.println("插入时 自动生成的主键ID为: " + account.getId() + " 外键ID为: " + account.getDetail().getId());
                        
                    }
```

可以看到日志结果:

```log
                    Hibernate: insert into users_detail (address, email, phone, real_name) values (?, ?, ?, ?)
                    Hibernate: insert into users (detail_id, password, username) values (?, ?, ?)
                    插入时 自动生成的主键ID为: 6 外键ID为: 3
```

结束后会发现数据库中两张表都同上存在数据

接着我们来看一下一对多关联 比如每个用户的成绩信息:

```java
                    @JoinColumn(name = "uid") // 注意这里的name指的是Score表中的uid字段对应的就是当前的主键 会将uid外键设置为当前的主键
                    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // 在移除Account时 一并移除所有的成绩信息 依然使用懒加载
                    List<Score> scoreList;
```

```java
                    @Data
                    @Entity
                    @Table(name = "users_score") // 成绩表 注意只存成绩 不存学科信息 学科信息id做外键
                    public class Score {
                    
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        @Column(name = "id")
                        @Id
                        int id;
                    
                        @OneToOne // 一对一对应到学科上
                        @JoinColumn(name = "cid")
                        Subject subject;
                    
                        @Column(name = "socre")
                        double score;
                    
                        @Column(name = "uid")
                        int uid;
                        
                    }
```

```java
                    @Data
                    @Entity
                    @Table(name = "subjects") // 学科信息表
                    public class Subject {
                    
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        @Column(name = "cid")
                        @Id
                        int cid;
                    
                        @Column(name = "name")
                        String name;
                    
                        @Column(name = "teacher")
                        String teacher;
                    
                        @Column(name = "time")
                        int time;
                        
                    }
```

数据库中填写相应数据 接着我们就可以查询用户的成绩信息了:

```java
                    @Transactional
                    @Test
                    void test() {
    
                        repository.findById(1).ifPresent(account -> {
                            account.getScoreList().forEach(System.out::println);
                        });
                        
                    }
```

成功得到用户所有的成绩信息 包括得分和学科信息

同样的 我们还可以将对应成绩中的教师信息单独分出一张表存储 并建立多对一的关系 因为多门课程可能由同一个老师教授(千万别搞晕了 一定要理清楚关联关系 同时也是考验你的基础扎不扎实)

```java
                    @ManyToOne(fetch = FetchType.LAZY)
                    @JoinColumn(name = "tid") // 存储教师ID的字段 和一对一是一样的 也会当前表中创个外键
                    Teacher teacher;
```

接着就是教师实体类了:

```java
                    @Data
                    @Entity
                    @Table(name = "teachers")
                    public class Teacher {
                    
                        @Column(name = "id")
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        @Id
                        int id;
                    
                        @Column(name = "name")
                        String name;
                    
                        @Column(name = "sex")
                        String sex;
                        
                    }
```

































