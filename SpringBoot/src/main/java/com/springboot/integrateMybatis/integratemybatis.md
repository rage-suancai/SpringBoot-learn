整合Mybatis框架

我们接着来看如何整合Mybatis框架 同样的 我们只需要导入对应的starter依赖即可:

                    <dependency>
                        <groupId>org.mybatis.spring.boot</groupId>
                        <artifactId>mybatis-spring-boot-starter</artifactId>
                        <version>2.2.0</version>
                    </dependency>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                    </dependency>

导入依赖后 直接启动会报错 是因为有必要的配置我们没有去编写 我们需要指定数据源的相关信息:

                    spring:
                      datasource:
                        url: jdbc:mysql://localhost:3306
                        username: root
                        password: 123456
                        driver-class-name: com.mysql.cj.jdbc.Driver

再次启动 成功

我们发现日志会出现这样一句话:

                    2023-3-23 17:48:40.106  WARN 6917 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : No MyBatis mapper was found in '[com.example]' package. Please check your configuration.

这是Mybatis自动扫描输出的语句 导入依赖后 我们不需要再去设置 Mybatis的相关Bean了 也不需要添加任何@MapperScan注解
因为starter已经帮助我们做了 它会自动扫描项目中添加了@Mapper注解的接口 直接将其注册为Bean 不需要进行任何配置

                    @Mapper
                    public interface MainMapper {
                    
                        @Select("select * from users where username = #{username}")
                        UserData findUserByName(String username);
                        
                    }

当然 如果你觉得每个接口都去加一个@Mapper比较麻烦的话也可以用回到之前的方式 直接@MapperScan使用包扫描

添加Mapper之后 使用方法和SSM阶段是一样的 我们可以将其与SpringSecurity结合使用:

                    @Service
                    public class UserAuthService implements UserDetailsService {
                    
                        @Resouce
                        MainMapper mapper;
                    
                        @Override
                        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                            
                            UserData data = mapper.findUserByName(username)
                            if(data == null) throw new UsernameNotFoundException()
                            return User
                                     .withUsername(data.getUsername())
                                     .password(data.getPassword())
                                     .roles(data.getRole())
                                     .build();
                    
                        }
                    
                    }

最后配置一下自定义验证即可 注意这样之前配置文件里面配置的用户就失效了:

                    @Override
                    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                        
                        auth
                                .userDetailsService(service)
                                .passwordEncoder(new BCryptPasswordEncoder());
                    
                    }

在首次使用时 我们发现日志中输出以下语句:

                    2023-03-24 17:39:18.710  INFO 6988 --- [p-nio-80-exec-4] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
                    2023-03-24 17:39:18.919  INFO 6988 --- [p-nio-80-exec-4] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.

实际上 SpringBoot会自动为Mybatis配置数据源 默认使用的就是HikariCP数据源