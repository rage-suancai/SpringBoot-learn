修改Web相关配置

如果我们需要修改Web服务器的端口或是一些其他的内容 我们可以直接在application.properties中进行修改 它是整个SpringBoot的配置文件:

                    # 修改端口为80
                    server.port = 80

我们还可以编写自定义的配置项 并在我们项目中通过@Value直接注入:

                    test.data = 100

                    @Controller
                    public class MainController {
                        
                        @Value("${test.data}")
                        int data;

                    }

通过这种方式 我们就可以更好地将一些需要频繁修改的配置项写在配置文件中 并通过注解方式去获取值

配置文件除了使用properties格式以外 还有一种叫做yaml格式 它的语法如下:

                    一级目录:
                        二级目录:
                          三级目录1: 值
                          三级目录2: 值
                          三级目录List: 
                          - 元素1
                          - 元素2
                          - 元素3

我们可以看到 每一级目录都是通过缩进(不能使用Tab 只能使用空格)区分 并且键和值之间需要添加冒号+空格来表示

SpringBoot也支持这种格式的配置文件 我们可以将application.properties修改为application.yaml或是application.yml来使用YAML语法编写配置:

                    server:
                        port: 80