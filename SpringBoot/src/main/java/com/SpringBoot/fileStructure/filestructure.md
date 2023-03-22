SpringBoot项目文件结构

我们在创建SpringBoot项目之后 首先会自动生成一个主类 而主类中的main方法中调用了SpringApplication类的静态方法来启动整个SpringBoot项目
并且我们可以看到主类的上方有一个@SpringBootApplication注解:

                    @SpringBootApplication
                    public class SpringBootTestApplication {
                        
                        public static void main(String[] args) {
                            SpringApplication.run(SpringBootTestApplication.class, args);
                        }
                    
                    }

同时还自带了一个测试类 测试类的上方仅添加了一个@SpringBootTest注解:

                    @SpringBootTest
                    class SpringBootTestApplicationTests {
                    
                        @Test
                        void contextLoads() {
                            
                        }
                    
                    }

我们接着看Maven中写了哪些内容:

                    <?xml version="1.0" encoding="UTF-8"?>
                    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                        <modelVersion>4.0.0</modelVersion>
                        <parent>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-starter-parent</artifactId>
                            <version>3.0.4</version>
                            <relativePath/> <!-- lookup parent from repository -->
                        </parent>
                        <groupId>com.boot</groupId>
                        <artifactId>SpringBoot-study</artifactId>
                        <version>0.0.1-SNAPSHOT</version>
                        <name>SpringBoot</name>
                        <description>SpringBoot</description>
                        <properties>
                            <java.version>17</java.version>
                        </properties>
                    
                        <dependencies>

                            <!-- spring-boot-starter SpringBoot核心启动器 -->
                            <dependency>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-starter</artifactId>
                            </dependency>
                            <!-- spring-boot-starter-test SpringBoot测试模块启动器 -->
                            <dependency>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-starter-test</artifactId>
                                <scope>test</scope>
                            </dependency>
                    
                        </dependencies>
                    
                        <build>
                            <plugins>
                                 <!-- SpringBoot Maven插件 打包Jar都不用你操心了 -->
                                <plugin>
                                    <groupId>org.springframework.boot</groupId>
                                    <artifactId>spring-boot-maven-plugin</artifactId>
                                </plugin>
                            </plugins>
                        </build>
                    
                    </project>

除了以上这些文件以外 我们的项目目录下还有:

        > .gitignore - Git忽略名单 下一章我们会专门讲解Git版本控制
        > application.properties - SpringBoot的配置文件 所有依赖的配置都在这里编写 但是一般情况下只需要配置必要项即可