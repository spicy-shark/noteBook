#  一、Spring Boot入门

## 1、Srping Boot简介

>简化Srping应用开发框架
>
>整个Spring技术栈的大整合
>
>J2EE开发的一站式解决方案

## 2、微服务

微服务：架构风格（服务微化）

一个应用是一组小型服务；可以通过HTTP的方式进行互通；

每一个功能元素最终都是一个可独立替换和升级的软件单元；

## 3、Spring Boot Hello World

### 创建工程

<img src="E:\spring boot笔记\images\image-20201120163005835.png" align= 'left'/>



**HelloworldApplication：**

```java
package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication	//说明这个类是Spring boot的住配置类，从这个类的main方法来启动SpringBoot应用
public class HelloworldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloworldApplication.class, args);
	}

}
```

@SpringBootApplication：

```Java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration	//表示这是一个SpringBoot的配置类
@EnableAutoConfiguration	//开启自动配置功能
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```



**InitController：**

```java
package com.example.helloworld.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InitController {

  @ResponseBody
  @RequestMapping("/hello")
  public String hello(){
    return "Hello world";
  }
}
```

### 打包运行

> Lifecycle -> package 打包
>
> java -jar name.jar 运行

## 4、Hello World探究

### 1、pom文件探究

>spring-boot-starter：Spring Boot将所有的功能场景抽取出来，做成一个个的启动器，只需要在项目里面引入这些starter相关的场景依赖都会导入进来。要用什么功能就导入什么场景启动器。

# 二、Spring Boot配置

## 1、配置文件

Spring Boot使用一个全局配置文件，配置文件名是固定的；

· application.properties

· application.yml

配置文件的作用：修改Spring Boot自动配置的默认值；

 

YAML(YAML Ain't Markup Language)

​	YAML A Markup Language：是一个标记语言

​	YAML isn't Markup Language：不是一个标记语言；

标记语言：

​	以前的配置文件：大多是xxxx.xml文件；

​	YAML：**以数据为中心**，比json、xml等更适合做配置文件；

## 2、YAML语法：

### 1、基本语法

k: v ：

表示一对键值对（空格必须有）；

以空格的缩进来表示控制层级关系

```yaml
server: 
	port: 8081
	path: /hello
```

属性和值是大小写敏感的；

### 2、值的写法

### 字面量：普通的值（数字，字符串。布尔值）

​	k: v：字面直接来写；

​		字符串默认不加引号；

​		""：不会转义字符串中的特殊字符，例："\n"表示换行符

​		''：会转义特殊字符，例：'\n'表示字符串\n

### 对象、Map（键值对）：

​	k: v：在下一行写对象的属性和值；注意缩进

```yaml
friends:
 age: 20
 name: jack
```

行内写法：

```yaml
frients: {age: 20, name: jack}
```

### 数组（list、Set）：

用- 值表示数组中的一个元素：

```yaml
pets:
 - cat
 - dog
 - pig
```

行内写法：

```yaml
pets: [cat, dog, pig]
```

### 3、配置文件值注入

#### 1、两种方式对比

|                | @ConfigrationPropertices | @Value                |
| -------------- | ------------------------ | --------------------- |
| 功能           | 批量注入文件属性         | 逐个注入              |
| 松散绑定       | 支持                     | 不支持                |
| SpEL           | 不支持（${}，#{}）       | 支持                  |
| JSR303数据校验 | 支持                     | 不支持                |
| 复杂类型封装   | 支持                     | 不支持（如Map，List） |

如果只需要在代码中获取一下文件中的值则使用@Value

如果专门编写了一个javaBean文件来和配置文件进行映射则使用@ConfigrationPropertices

可使用@PropertySource(value = {"classpath:person.properties"})加载指定文件

使用@Importresource(localtions = {"classpath：beans.xml"})来导入Spring配置文件

**Spring Boot推荐给容器中添加组件的方式（推荐使用全注解的方式）：**

>1、配置类=====Spring配置文件
>
>2、使用@Bean给容器中添加组件

#### 2、配置文件注入值数据校验

``` java
@Component
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {

    /**
     * <bean class="Person">
     *      <property name="lastName" value="字面量/${key}从环境变量、配置文件中获取值/#{SpEL}"></property>
     * <bean/>
     */

   //lastName必须是邮箱格式
    @Email
    //@Value("${person.last-name}")
    private String lastName;
    //@Value("#{11*2}")
    private Integer age;
    //@Value("true")
    private Boolean boss;

    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
```

#### 3、配置文件占位符

##### 1、随机数

```java
${random.uuids}
${random.int}
```

##### 2、占位符

获取之前配置的值，如果没有可以用”`:`“来指定默认值

```java
person.dog.name=${person.name}'s_dog
```

## 3、Profile文件

### 1、多Profile文件

我们在主配置文件编写的时候，文件名可以是applicaltion-{profile}.porperties/yml

默认使用applicaltion.properties的配置；



### 2、yml支持多文档块方式

使用`---`定义文档块

```yaml
server:
  port: 8081
spring:
  profiles:
    active: prod

---
server:
  port: 8083
spring:
  profiles: dev


---

server:
  port: 8084
spring:
  profiles: prod  #指定属于哪个环境
```



### 3、激活指定profile

1、在application.properties配置文件中指定spring.profiles.active=dev

2、命令行激活：

​	--spring.profiles.active=dev

3、虚拟机参数：

​	-Dspring.profiles.active=dev

### 4、配置文件加载位置

按照优先级加载

>- file:./config
>- file:./
>- classpath:/config
>- classpath:/

高优先级会覆盖低优先级，也遵循互补配置。

优先加载带profile的

### 5、自动配置原理

**自动配置：**

1）Spring Boot 启动的时候加载主配置类，开启了自动配置功能@EnableAutoConfigration

2）@EnableAutoConfigration：利用EnableAutoConfigrationImportSelector给容器导入一些组件

**精髓：** 

​	**1）SpringBoot启动会加载大量的自动配置类**

​	**2）我们看我们需要的功能有没有SpringBoot默认写好的自动配置类；**

​	**3）我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件有，我们就不需要再来配置了）**

​	**4）给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们就可以在配置文件中指定这些属性的值**



#### 1、细节

##### @Conditional派生注解（Spring注解版原生的@Conditional作用）

作用：必须是@Condiitional指定的条件成立，才给容器中添加组件，配置类里面的所有内容才生效；

| @Conditional扩展注解            | 作用（判断是否满足当前指定条件）                 |
| ------------------------------- | ------------------------------------------------ |
| @ConditionalOnJava              | 系统的java版本是否符合要求                       |
| @ConditionalOnBean              | 容器中存在指定Bean；                             |
| @ConditionalOnMissingBean       | 容器中不存在指定Bean；                           |
| @ConditionalOnExpression        | 满足SpEL表达式指定                               |
| @ConditionalOnClass             | 系统中有指定的类                                 |
| @ConditionalOnMissingClass      | 系统中没有指定的类                               |
| @ConditionalOnSingleCandidate   | 容器中只有一个指定的Bean，或者这个Bean是首选Bean |
| @ConditionalOnProperty          | 系统中指定的属性是否有指定的值                   |
| @ConditionalOnResource          | 类路径下是否存在指定资源文件                     |
| @ConditionalOnWebApplication    | 当前是web环境                                    |
| @ConditionalOnNotWebApplication | 当前不是web环境                                  |
| @ConditionalOnJndi              | JNDI存在指定项                                   |

**自动配置类必须在一定条件下才能生效；**

哪些些自动配置类生效：通过启用`debug=true`属性来让控制台打印自动配置报告；

# 三、日志

## 1、日志框架

用来记录系统的运行时信息

市面上的日志框架：JUL、JCL、Jboss-logging、logback、log4j、log4j2、slf4j....

## 2、SLF4j使用

### 1、如何在系统中使用SLF4j https://www.slf4j.org

调用日志抽象层来实现

给系统里面导入slf4j的jar和  logback的实现jar

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

没有一个日志的实现框架都有自己的配置文件。使用slf4j后，**配置文件还是做成日志实现框架自己本身的配置文件**

### 2、遗留问题

a系统（slf4j+logback）：Spring（commons-logging）、Hibernate（jboss-logging）、MyBatis、xxx

统一日志记录，即使是别的框架和我一起使用slf4j进行输出。

## 3、Spring Boot日志关系

1）SpringBoot底层是使用slf4j+logback的方式进行日志记录

2）也把其他日志都替换成了slf4j

3）如果要引入其他框架，一定要把这个框架的默认日志依赖移除掉

## 4、日志使用

### 默认配置

SpringBoot已经默认配置好日志

```java
	//记录器
	Logger logger = LoggerFactory.getLogger(getClass());
	@Test
	public void contextLoads() {
		//System.out.println();

		//日志的级别；
		//由低到高   trace<debug<info<warn<error
		//可以调整输出的日志级别；日志就只会在这个级别以以后的高级别生效
		logger.trace("这是trace日志...");
		logger.debug("这是debug日志...");
		//SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
		logger.info("这是info日志...");
		logger.warn("这是warn日志...");
		logger.error("这是error日志...");


	}
```

| logging.file | logging.path | Example  | Description                        |
| ------------ | ------------ | -------- | ---------------------------------- |
| (none)       | (none)       |          | 只在控制台输出                     |
| 指定文件名   | (none)       | my.log   | 输出日志到my.log文件               |
| (none)       | 指定目录     | /var/log | 输出到指定目录的 spring.log 文件中 |

## 5、切换日志框架

可以按照slf4j的日志适配图，进行相关的切换；

slf4j+log4j的方式；

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <exclusions>
    <exclusion>
      <artifactId>logback-classic</artifactId>
      <groupId>ch.qos.logback</groupId>
    </exclusion>
    <exclusion>
      <artifactId>log4j-over-slf4j</artifactId>
      <groupId>org.slf4j</groupId>
    </exclusion>
  </exclusions>
</dependency>

<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
</dependency>

```





切换为log4j2

```xml
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>spring-boot-starter-logging</artifactId>
                    <groupId>org.springframework.boot</groupId>
                </exclusion>
            </exclusions>
        </dependency>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

-----------------

# 四、Web开发

## 1、简介

使用Spring Boot

1、创建Spring Boot应用，选中需要的模块；

2、Spring Boot将会配置好这些场景，只需要在配置文件中指定少量配置就可以运行起来。

3、编写业务代码；



**自动配置原理**

Spring Boot帮我们配置了什么？能不能修改？那些能修改？能不能扩展？xxx

## 2、Spring Boot 对静态资源的映射规则

1、所有/webjars/**，都去classpath:/META-INF/resources/webjar找资源；

webjars：以jar包方式引入静态资源；

2、"/**" 访问当前项目的任何资源，都去（静态资源的文件夹）找映射

```
"classpath:/META-INF/resources/", 
"classpath:/resources/",
"classpath:/static/", 
"classpath:/public/" 
"/"：当前项目的根路径
```

localhost:8080/abc  去静态资源文件夹里面找abc

3、欢迎页； 静态资源文件夹下的所有index.html页面；被"/**"映射；

​	localhost:8080/   找index页面

4、所有的 **/favicon.ico  都是在静态资源文件下找；

## 3、模板引擎

Spring Boot推荐Thymleaf，语法简单，功能强大；

### 1、引入Thymeleaf

```java
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 2、Thymeleaf使用及语法

只要把HTML页面放在classpath/，thymeleaf就能自动渲染；

使用：

1、导入thymeleaf

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

  ```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>SUCCESS!!</title>
</head>
<body>
  <h1>chenggong!!</h1>
  <div th:text="${hello}">这是显示</div>
</body>
</html>
  ```

### 3、语法规则

## 4、Spring MVC自动配置

https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications

### 1. Spring MVC auto-configuration

Spring Boot 自动配置好了SpringMVC

以下是SpringBoot对SpringMVC的默认配置:**==（WebMvcAutoConfiguration）==**

- Inclusion of `ContentNegotiatingViewResolver` and `BeanNameViewResolver` beans.

  - 自动配置了ViewResolver（视图解析器：根据方法的返回值得到视图对象（View），视图对象决定如何渲染（转发？重定向？））
  - ContentNegotiatingViewResolver：组合所有的视图解析器的；
  - ==如何定制：我们可以自己给容器中添加一个视图解析器；自动的将其组合进来；==

- Support for serving static resources, including support for WebJars (see below).静态资源文件夹路径,webjars

- Static `index.html` support. 静态首页访问

- Custom `Favicon` support (see below).  favicon.ico

  

- 自动注册了 of `Converter`, `GenericConverter`, `Formatter` beans.

  - Converter：转换器；  public String hello(User user)：类型转换使用Converter
  - `Formatter`  格式化器；  2017.12.17===Date；

```java
		@Bean
		@ConditionalOnProperty(prefix = "spring.mvc", name = "date-format")//在文件中配置日期格式化的规则
		public Formatter<Date> dateFormatter() {
			return new DateFormatter(this.mvcProperties.getDateFormat());//日期格式化组件
		}
```

​	==自己添加的格式化器转换器，我们只需要放在容器中即可==

- Support for `HttpMessageConverters` (see below).

  - HttpMessageConverter：SpringMVC用来转换Http请求和响应的；User---Json；

  - `HttpMessageConverters` 是从容器中确定；获取所有的HttpMessageConverter；

    ==自己给容器中添加HttpMessageConverter，只需要将自己的组件注册容器中（@Bean,@Component）==

    

- Automatic registration of `MessageCodesResolver` (see below).定义错误代码生成规则

- Automatic use of a `ConfigurableWebBindingInitializer` bean (see below).

  ==我们可以配置一个ConfigurableWebBindingInitializer来替换默认的；（添加到容器）==

  ```
  初始化WebDataBinder；
  请求数据=====JavaBean；
  ```

**org.springframework.boot.autoconfigure.web：web的所有自动场景；**

If you want to keep Spring Boot MVC features, and you just want to add additional [MVC configuration](https://docs.spring.io/spring/docs/4.3.14.RELEASE/spring-framework-reference/htmlsingle#mvc) (interceptors, formatters, view controllers etc.) you can add your own `@Configuration` class of type `WebMvcConfigurerAdapter`, but **without** `@EnableWebMvc`. If you wish to provide custom instances of `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter` or `ExceptionHandlerExceptionResolver` you can declare a `WebMvcRegistrationsAdapter` instance providing such components.

If you want to take complete control of Spring MVC, you can add your own `@Configuration` annotated with `@EnableWebMvc`.