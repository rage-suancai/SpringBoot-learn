整合Thymeleaf框架

整合Thymeleaf也只需要导入对应的starter即可:

                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-thymeleaf</artifactId>
                    </dependency>

接着我们只需要直接使用即可:

                    @RequestMapping("/index")
                    public String index() {
                        return "index";
                    }

但是注意 这样只能正常解析HTML页面 但是js, css等静态资源我们需要进行路径指定 不然无法访问 我们在配置文件中配置一下静态资源的访问前缀:

                    spring:
                    mvc:
                      static-path-pattern: /static/**

接着我们像之前一样 把登录页面实现一下吧

                    <html lang="en" xmlns:th=http://www.thymeleaf.org
                    xmlns:sec=http://www.thymeleaf.org/extras/spring-security>