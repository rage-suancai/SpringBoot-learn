package com.SpringBoot.springboot1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot一站式开发
 * 官网: https://spring.io/projects/spring-boot
 *      Spring Boot可以轻松篡创建独立的 基于Spring的生产级应用程序 它可以让你"运行即可" 大多数Spring Boot应用程序只需要少量的Spring配置
 *
 * SpringBott功能:
 *      > 创建独立的Spring应用程序
 *      > 直接嵌入Tomcat jetty或Undertow (无需部署WAR包 打包成Jar本身就是一个可以运行的应用程序)
 *      > 提供一站式的"starter"依赖项 以简化Maven配置 (需要整合扫描框架 直接导对应框架的starter依赖)
 *      > 尽可能自动配置Spring和第三方库 (除非特殊情况 负责几乎不需要你进行扫描配置)
 *      > 提供生产就绪功能 如指标 运行状况检查和外部化配置
 *      > 没有代码生成 也没有XML配置的要求 (XML是什么 好吃吗)
 *  SpringBoot是现在最主流的开发框架 它提供了一站式的开发体验 大幅度提高了我们的开发效率
 *
 * 走进SpringBoot
 * 在SSM阶段 当我们需要搭建一个基于Spring全家桶的Web应用程序时 我们不得不做大量的依赖导入和框架整合相关的Bean定义 光是整合框架就花费了我们大量的时间
 * 但是实际上我们发现 整合框架其实基本都是一些固定流程 我们每创建一个新的Web应用程序 基本都会使用同样的方式去整合框架 我们完全可以将一些重复的配置作为约定
 * 只要框架遵守这个约定 为我们提供默认的配置就好 这样就不用我们再去配置了 约定优于配置
 *
 * 而SpringBoot正是将这些过程大幅度进行了简化 它可以自动进行配置 我们只需要导入对应的启动器(starter)依赖即可
 *
 * 完成本阶段的学习 基本能够胜任部分网站系统的后端开发工作 也建议同学们学习完SpringBoot之后寻找合适的队友去参加计算机项目相关的高校竞赛
 *
 * 我们可以通过IDEA来演示如何快速创建一个SpringBoot项目 并且无需任何配置 就可以实现Bean注入
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
