整合SpringSecurity依赖

我们接着来整合一下SpringSecurity依赖 继续感受SpringBoot带来的光速开发体验✈ 只需要导入SpringSecurity的Starter依赖即可:

                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-security</artifactId>
                    </dependency>

导入依赖后 我们直接启动SpringBoot应用程序 可以发现SpringSecurity已经生效了

并且SpringSecurity会自动为我们生成一个一个默认用户user 它的密码会出现在日志中:

                    2023-03-23T17:29:01.510+08:00  INFO 6848 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
                    2023-03-23T17:29:01.511+08:00  INFO 6848 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.5]
                    2023-03-23T17:29:01.571+08:00  INFO 6848 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
                    2023-03-23T17:29:01.571+08:00  INFO 6848 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 664 ms
                    2023-03-23T17:29:01.807+08:00  WARN 6848 --- [           main] .s.s.UserDetailsServiceAutoConfiguration :
                    
                    Using generated security password: 10526ee5-872b-40e5-942f-06a112b86873

其中10526ee5-872b-40e5-942f-06a112b86873就是随机生成的一个密码 我们可以使用此用户登录

我们也可以在配置文件中直接配置:

                    spring:
                      security:
                        user:
                          name: test   # 用户名
                          password: 123456  # 密码
                          roles:   # 角色
                          - user
                          - admin

实际上这样的配置方式就是一个inMemoryAuthentication 只是我们可以直接配置而已

当然 页面的控制和数据库验证我们还是需要提供WebSecurityConfigurerAdapter的实现类去完成:

                    @Configuration
                    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
                    
                        @Override
                        protected void configure(HttpSecurity http) throws Exception {
                    
                            http
                                    .authorizeRequests()
                                    .antMatchers("/login").permitAll()
                                    .anyRequest().hasRole("user")
                    
                                    .and
                                    
                                    .formLogin();
                                    
                        }
                    
                    }

注意: 这里不需要再添加@EnableWebSecurity了 因为starter依赖已经帮我们添加了

使用了SpringBoot之后 我们发现 需要什么功能 只需要导入对应的starter依赖即可
设置都不需要你去进行额外的配置 你只需要关注依赖本身的必要设置即可 大大提高了我们的开发效率