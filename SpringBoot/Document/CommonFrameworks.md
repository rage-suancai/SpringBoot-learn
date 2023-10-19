## 常用框架介绍
前面我们介绍了SpringBoot项目的基本搭建 相信各位小伙伴已经体验到SpringBoot3带来的超强便捷性了 不过光靠这些还不够 我们还需要了解更多框架来丰富我们的网站
通过了解其它的SpringBoot整合框架 我们就可以在我们自己的Web服务器上实现更多更高级的功能 同时也是为了给我们后续学习前后端分离项目做准备

### 邮件发送模块
都什么年代了 还在发传统邮件 我们来看看电子邮件

我们还在注册很多的网站时 都会遇到邮件或是手机号验证 也就是通过你的邮箱或是手机短信去接受网站发给你的注册验证信息 填写验证码之后 就可以完成注册了 同时 网站也会绑定你的手机号或是邮箱

那么 像这样的功 我们如何实现呢? SpringBoot已经给我们提供了封装好的邮件模块使用:

```xml
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-mail</artifactId>
                    </dependency>
```

在学习邮件发送之前 我们需要先了解一下什么是电子邮件

    电子邮件也是一种通信方式 是互联网应用最广的服务 通过网络的电子邮件系统 用户可以以非常低廉的价格
    (不管发送到哪里 都只需负担网费 实际上就是把信息发送到对方服务器而已)非常快速的方式 与世界上任何一个地方的电子邮箱用户联系

虽说方便倒是方便 虽然是曾经的霸主 不过现在这个时代 QQ微信横行 手机短信和电子邮箱貌似就只剩收验证码这一个功能了

要在Internet上提供电子邮件功能 必须有专门的电子邮件服务器 例如现在Internet很多提供邮件服务的厂商: 新浪,搜狐,163,QQ邮箱等
他们都有自己的邮件服务器 这些服务器类似于现实生活中的邮局 它主要负责接收用户投递过来的邮件 并把邮件投递到邮件接收者的电子邮箱中

所有的用户都可以在电子邮件服务器上申请一个账号用于邮件发送和接收 那么邮件是以什么样的格式发送的呢? 实际上和HTTP一样 邮件发送也有自己的协议 也就是约定邮件数据长啥样以及如何通信

<img src="https://image.itbaima.net/markdown/2023/07/16/sL56YdmgGblfFjo.png"/>

比较常用的协议有两种:
1. SMTP协议(主要用于发送邮件 Simple Mail Transfer Protocol)
2. POP3协议(主要用于接收邮件 Post Office Protocol 3)

整个发送/接收流程大致如下:

<img src="https://image.itbaima.net/markdown/2023/07/16/sOyWQguFonJKXNw.jpg"/>

实际上每个邮箱服务器都有一个smtp发送服务器和pop3接收服务器 比如要从QQ邮箱发送邮件到163邮箱 那么我们只需要通过QQ邮箱客户端告知QQ邮箱的smtp服务器我们需要发送邮件
以及邮件的相关信息 然后QQ邮箱的smtp服务器就会帮助我们发送到163邮箱的pop3服务器上 163邮箱会通过163邮箱客户端告知对应用户收到一封新邮件

而我们如果想要实现给别人发送邮件 那么就需要连接到对应电子邮箱的smtp服务器上 并告知其我们要发送邮件
而SpringBoot已经帮助我们将最基本的底层通信全部实现了 我们只需要关心smtp服务器的地址以及我们要发送的邮件长啥样即可

这里以163邮箱 https://mail.163.com 为例 我们需要在配置文件中告诉SpringBootMail我们的smtp服务器的地址以及你的邮箱账号和密码
首先我们要去设置中开启smtp/pop3服务才可以 开启后会得到一个随机生成的密钥 这个就是我们的密码

```yaml
                    spring:
                        mail:
                          # 163邮箱的地址为smtp.163.com 直接填写即可
                          host: smtp.163.com
                          # 你申请的163邮箱
                          username: javastudy111@163.com
                          # 注意密码是在开启smtp/pop3时自动生成的 记得保存一下 不然就找不到了
                          password: AZJTOAWZESLMHTNI
```

配置完成后 接着我们来进行一下测试:

```java
                    @SpringBootTest
                    class SpringBootTestApplicationTests {
                    
                        // JavaMailSender是专门用于发送邮件的对象 自动配置类已经提供了Bean
                        @Autowired
                        JavaMailSender sender;
                    
                        @Test
                        void contextLoads() {
                            
                            // SimpleMailMessage是一个比较简易的邮件封装 支持设置一些比较简单内容
                            SimpleMailMessage message = new SimpleMailMessage();
                            // 设置邮件标题
                            message.setSubject("【电子科技大学教务处】关于近期学校对您的处分决定");
                            // 设置邮件内容
                            message.setText("XXX同学您好 经监控和教务巡查发现 您近期存在旷课,迟到,早退,上课刷抖音行为 " +
                                    "现已通知相关辅导员 请手写99999字书面检讨 并在2023年12月1日17点前交到辅导员办公室");
                            // 设置邮件发送给谁 可以多个 这里就发给你的QQ邮箱
                            message.setTo("你的QQ号@qq.com");
                            // 邮件发送者 这里要与配置文件中的保持一致
                            message.setFrom("javastudy111@163.com");
                            // OK 万事俱备只欠发送
                            sender.send(message);
                            
                        }
                    
                    }
```

如果需要添加附件等更多功能 可以使用MimeMessageHelper来帮助我们完成:

```java
                    @Test
                    void contextLoads() throws MessagingException {
    
                        // 创建一个MimeMessage
                        MimeMessage message = sender.createMimeMessage();
                        // 使用MimeMessageHelper来帮我们修改MimeMessage中的信息
                        MimeMessageHelper helper = new MimeMessageHelper(message, true);
                        helper.setSubject("Test");
                        helper.setText("lbwnb");
                        helper.setTo("你的QQ号@qq.com");
                        helper.setFrom("javastudy111@163.com");
                        // 发送修改好的MimeMessage
                        sender.send(message);
                        
                    }
```

最后 我们来尝试为我们的网站实现一个邮件注册功能 首先明确验证流程: `请求验证码 -> 生成验证码(临时有效 注意设定过期时间)-> 用户输入验证码并填写注册信息 -> 验证通过注册成功`

接着我们就来着手写一下

### 接口规则校验
通常我们在使用SpringMVC框架编写接口时 很有可能用户发送的数据存在一些问题 比如下面这个接口:

```java
                    @ResponseBody
                    @PostMapping("/submit")
                    public String submit(String username,
                                         String password) {
    
                        System.out.println(username.substring(3));
                        System.out.println(password.substring(2, 10));
                        return "请求成功!";
                        
                    }
```

这个接口中 我们需要将用户名和密码分割然后打印 在正常情况下 因为用户名长度规定不小于5 如果用户发送的数据是没有问题的
那么就可以正常运行 这也是我们所希望的情况 但是如果用户发送的数据并不是按照规定的 那么就会直接报错:

<img src="https://image.itbaima.net/markdown/2023/07/16/n1FMADOiQCRcGw6.png"/>

这个时候 我们就需要在请求进来之前进行校验了 最简单的办法就是判断一下:

```java
                    @ResponseBody
                    @PostMapping("/submit")
                    public String submit(String username,
                                         String password){
                                         
                        if(username.length() > 3 && password.length() > 10) {
                            System.out.println(username.substring(3));
                            System.out.println(password.substring(2, 10));
                            return "请求成功!";
                        } else {
                            return "请求失败";
                        }
                        
                    }
```

虽然这样就能直接解决问题 但是如果我们的每一个接口都需要这样去进行配置 那么是不是太麻烦了一点? SpringBoot为我们提供了很方便的接口校验框架:

```xml
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-validation</artifactId>
                    </dependency>
```

现在 我们可以直接使用注解完成全部接口的校验:

```java
                    @Slf4j
                    @Validated // 首先在Controller上开启接口校验
                    @Controller
                    public class TestController {
                    
                        ...
                    
                        @ResponseBody
                        @PostMapping("/submit")
                        public String submit(@Length(min = 3) String username, // 使用@Length注解一步到位
                                             @Length(min = 10) String password) {
                            
                            System.out.println(username.substring(3));
                            System.out.println(password.substring(2, 10));
                            return "请求成功!";
                            
                        }
                        
                    }
```

现在 我们的接口校验就可以快速进行配置了 一个接口就能搞定:

<img src="https://image.itbaima.net/markdown/2023/07/16/EibCc4sHWflywek.png"/>

不过这样依然会抛出一个异常 对用户不太友好 我们可以稍微处理一下 这里我们可以直接使用之前在SSM阶段中学习的异常处理Controller来自行处理这类异常:

```java
                    @ControllerAdvice
                    public class ValidationController {
                    
                        @ResponseBody
                        @ExceptionHandler(ConstraintViolationException.class)
                        public String error(ValidationException e) {
                            return e.getMessage(); // 出现异常直接返回消息
                        }
                        
                    }
```

<img src="https://image.itbaima.net/markdown/2023/07/16/7JH6BzOhlUe9gkG.png"/>

除了@Length之外 我们也可以使用其他的接口来实现各种数据校验:

| 验证注解   | 验证的数据类型                                                                  | 说明                              | 
|--------|--------------------------------------------------------------------------|---------------------------------|
| @AssertFalse | Boolean,boolean                                                          | 值必须是false                       |
| @AssertTrue   | Boolean,boolean                                                          | 值必须是true                        |
| @NotNull | 任意类型                                                                     | 值不能是null                        |
| @Null | 任意类型                                                                     | 值必须是null                        |
| @Min | BigDecimal,BigInteger,byte,short,int,long,double 以及任何Number或CharSequence子类型 | 大于等于@Min指定的值                    |
| @Max | 同上                                                                       | 小于等于@Max指定的值                    |
| @DecimalMin | 同上                                                                       | 大于等于@DecimalMin指定的值(超高精)        |
| @DecimalMax | 同上                                                                       | 小于等于@DecimalMax指定的值(超高精度)       |
| @Digits | 同上                                                                       | 限制整数位数和小数位数上限                   |
| @Size | 字符串,Collection,Map,数组等                                                   | 长度在指定区间之内 如字符串长度,集合大小等          |
| @Past | 如 java.util.Date, java.util.Calendar等日期类型                                | 值必须比当前时间早                       |
| @Future | 同上                                                                       | 值必须比当前时间晚                       |
| @NotBlank | CharSequence及其子类                                                         | 值不为空 在比较时会去除字符串的首位空格            |
| @Length | CharSequence及其子类                                                         | 字符串长度在指定区间内                     |
| @NotEmpty | CharSequence及其子类,Collection,Map,数组                                       | 值不为null且长度不为空(字符串长度不为0 集合大小不为0) |
| @Range | BigDecimal,BigInteger、CharSequence、byte、short、int、long 以及原子类型和包装类型       | 值在指定区间内 |
| @Email | CharSequence及其子类 | 值必须是邮件格式                        |
| @Pattern | CharSequence及其子类 | 值需要与指定的正则表达式匹配 |
| @Valid | 任何非原子类型 | 用于验证对象属性 |


虽然这样已经很方便了 但是在遇到对象的时候 依然不太方便 比如:

```java
                    @Data
                    public class Account {
    
                        String username;
                        String password;
                        
                    }
```

```java
                    @ResponseBody
                    @PostMapping("/submit")
                    public String submit(Account account) { // 直接使用对象接收
                        
                        System.out.println(account.getUsername().substring(3));
                        System.out.println(account.getPassword().substring(2, 10));
                        return "请求成功!";
                        
                    }
```

此时接口是以对象形式接收前端发送的表单数据的 这个时候就没办法向上面一样编写对应的校验规则了 那么现在又该怎么做呢?

对应对象类型 我们也可以进行验证 方法如下:

```java
                    @ResponseBody
                    @PostMapping("/submit") // 在参数上添加@Valid注解表示需要验证
                    public String submit(@Valid Account account) {
                        
                        System.out.println(account.getUsername().substring(3));
                        System.out.println(account.getPassword().substring(2, 10));
                        return "请求成功!";
                        
                    }
```

```java
                    @Data
                    public class Account {
    
                        @Length(min = 3) // 只需要在对应的字段上添加校验的注解即可
                        String username;
                        @Length(min = 10)
                        String password;
                        
                    }
```

这样当受到请求时 就会对对象中的字段进行校验了 这里我们稍微修改一下ValidationController的错误处理
对于实体类接收参数的验证 会抛出MethodArgumentNotValidException异常 这里也进行一下处理:

```java
                    @ResponseBody
                    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
                    public String error(Exception e) {
    
                        if(e instanceof ConstraintViolationException exception) {
                            return exception.getMessage();
                        } else if(e instanceof MethodArgumentNotValidException exception){
                            if (exception.getFieldError() == null) return "未知错误";
                            return exception.getFieldError().getDefaultMessage();
                        }
                        return "未知错误";
                        
                    }
```

这样就可以正确返回对应的错误信息了

### 接口文档生成(选学)
在后续学习前后端分离开发中 前端现在由专业的人来做 而我们往往只需要关心后端提供什么接口给前端人员调用
我们的工作被进一步细分了 这个时候为前端开发人员提供一个可以参考的文档是很有必要的

但是这样的一个文档 我们也不可能单独写一个项目去进行维护 并且随着我们的后端项目不断更新 文档也需要跟随更新 这显然是很麻烦的一件事情 那么有没有一种比较好的解决方案呢?

当然有 那就是丝袜哥: Swagger

Swagger的主要功能如下:
- 支持API自动生成同步的在线文档: 使用Swagger后可以直接通过代码生成文档 不再需要自己手动编写接口文档了 对程序员来说非常方便 
- 提供Web页面在线测试API: 光有文档还不够Swagger生成的文档还支持在线测试 参数和格式都定好了 直接在界面上输入参数对应的值即可在线测试接口

结合Spring框架(Spring-doc 官网: https://springdoc.org/) Swagger可以很轻松地利用注解以及扫描机制 来快速生成在线文档
以实现当我们项目启动之后 前端开发人员就可以打开Swagger提供的前端页面 查看和测试接口 依赖如下:

```xml
                    <dependency>
                        <groupId>org.springdoc</groupId>
                        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
                        <version>2.1.0</version>
                    </dependency>
```

项目启动之后 我们可以直接访问: http://localhost:8080/swagger-ui/index.html 就能看到我们的开发文档了:

<img src="https://image.itbaima.net/markdown/2023/07/17/yb68Oolm1Xp5qFU.png"/>

可以看到这个开发文档中自动包含了我们定义的接口 并且还有对应的实体类也放在了下面 这个页面不仅仅是展示接口 也可以直接在上面进行调试:

<img src="https://image.itbaima.net/markdown/2023/07/17/whLprBimgTqWxFR.png"/>















