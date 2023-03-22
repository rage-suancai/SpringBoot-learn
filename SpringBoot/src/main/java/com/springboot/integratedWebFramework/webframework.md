整合Web相关框架

我们来看一下 既然我们前面提到SpringBoot会内嵌一个Tomcat服务器 也就是说我们的Jar打包后
相当于就是一个可以直接运行的应用程序 我们来看一下如何创建一个SpringBootWeb项目

这里我们演示使用IDEA来创建一个基于SpringBoot的Web应用程序

它是真的快

创建完成后 直接开启项目 我们就可以直接访问: http://localhost:8080/
我们可以看到 但是由于我们没有编写任何的请求映射 所以没有数据 我们可以来看看日志:

                    2023-03-22T11:19:17.328+08:00  INFO 11936 --- [           main] com.springboot.Application               : Starting Application using Java 17.0.5 with PID 11936 (D:\back-end learning\java-exercise\JavaEE\javaSpringBoot-yxs\javaSpringBoot\SpringBoot\target\classes started by AKAtravis-yxs in D:\back-end learning\java-exercise\JavaEE\javaSpringBoot-yxs\javaSpringBoot\SpringBoot)
                    2023-03-22T11:19:17.330+08:00  INFO 11936 --- [           main] com.springboot.Application               : No active profile set, falling back to 1 default profile: "default"
                    2023-03-22T11:19:17.869+08:00  INFO 11936 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
                    2023-03-22T11:19:17.876+08:00  INFO 11936 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
                    2023-03-22T11:19:17.876+08:00  INFO 11936 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.5]
                    2023-03-22T11:19:17.931+08:00  INFO 11936 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
                    2023-03-22T11:19:17.932+08:00  INFO 11936 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 564 ms
                    2023-03-22T11:19:18.155+08:00  INFO 11936 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
                    2023-03-22T11:19:18.160+08:00  INFO 11936 --- [           main] com.springboot.Application               : Started Application in 1.137 seconds (process running for 1.582)

我们可以看到 日志中除了最基本的SpringBoot启动日志以外 还新增了内嵌Web服务器(Tomcat)的启动日志 并且显示了当前web服务器所开发的端口
并且自动帮助我们初始化了DispatchServlet 但是我们只是创建了项目 导入了web相关的starter依赖 没有进行任何的配置 实际上它使用的是starter提供的默认配置进行初始化的

由于SpringBoot是自动扫描的 因此我们直接创建一个Controller即可被加载:

                    @Controller
                    public class MainController {
                    
                        // 直接访问http: //localhost:8080/index即可 不用加web应用程序名称了
                        @RequestMapping("/index")
                        @ResponseBody
                        public String index(){
                            return "你好 欢迎访问主页";
                        }

                    }

我们几乎没有做任何配置 但是可以直接开始配置Controller SpringBoot创建一个Web项目的速度就是这么快

它还可以自动识别类型 如果我们返回的是一个对象类型的数据 那么它会自动转换为JSON数据格式 无需配置:

                    @Data
                    public class Student {

                        int sid;
                        String name;
                        String sex;

                    }

最后浏览器能够直接得到application/json的响应数据 就是这么方便