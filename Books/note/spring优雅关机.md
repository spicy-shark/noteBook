# Spring优雅关机

## ShutDownHook(Java)

> 用于异常状态的结束,被动

```java
// 创建HookTest，我们通过main方法来模拟应用程序
public class HookTest {
 
    public static void main(String[] args) {
 
        // 添加hook thread，重写其run方法
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("this is hook demo...");
                // TODO
            }
        });
 
        int i = 0;
        // 这里会报错，我们验证写是否会执行hook thread
        int j = 10/i;
        System.out.println("j" + j);
    }
}
 
// res
Exception in thread "main" java.lang.ArithmeticException: / by zero
	at hook.HookTest.main(HookTest.java:23)
this is hook demo...
 
Process finished with exit code 1
————————————————
版权声明：本文为CSDN博主「恐龙弟旺仔」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_26323323/article/details/89814410
```

### 触发场景

  - 程序正常退出

  - 使用System.exit()

  - 终端使用Ctrl+C触发的中断

  - 系统关闭

  - OutofMemory宕机

  - 使用Kill pid杀死进程（使用kill -9是不会被调用的）

## Actuator(springboot)

> 主动结束服务,不是简单的kill

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 添加yml

```yml
management:
  endpoint:
    shutdown:
      enabled: true  #启用shutdown
  endpoints:
    web:
      exposure:
        include: "*"
      base-path: /MyActuator # 自定义管理端点的前缀(保证安全)
  server:
    port: 12888
    address: 127.0.0.1 # 不允许远程管理连接(不允许外部调用保证安全)
————————————————
版权声明：本文为CSDN博主「大飞NO1」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/sinat_34454743/article/details/99680633
```

### 主启动类中添加tomcat的停机支持

```java
package smartt.styy.auth;
 
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
 
import org.springframework.context.event.ContextClosedEvent;
import smartt.styy.auth.filter.HttpServletRequestReplacedFilter;
 
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
/**
 * @author shangtengfei
 * 启动方法 ，入口
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan("smartt.styy.auth.mapper")
@Slf4j
public class AuthSpringBootApplication 
{
    public static void main( String[] args ){
    	SpringApplication.run(AuthSpringBootApplication.class, args);
    }
    
    
 
	/**
	 * 9     * 用于接受 shutdown 事件
	 * 10
	 */
	@Bean
	public GracefulShutdown gracefulShutdown() {
		return new GracefulShutdown();
	}
 
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
		tomcatServletWebServerFactory.addConnectorCustomizers(gracefulShutdown());
		return tomcatServletWebServerFactory;
	}
 
	private class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
		private volatile Connector connector;
		private final int waitTime = 10;
 
		@Override
		public void customize(Connector connector) {
			this.connector = connector;
		}
 
		@Override
		public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
			this.connector.pause();
			Executor executor = this.connector.getProtocolHandler().getExecutor();
			try {
				if (executor instanceof ThreadPoolExecutor) {
					ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
					threadPoolExecutor.shutdown();
					if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
						log.warn("Tomcat 进程在" + waitTime + " 秒内无法结束，尝试强制结束");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
 
}
————————————————
版权声明：本文为CSDN博主「大飞NO1」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/sinat_34454743/article/details/99680633
```



## YML配置

> 新版本配置非常简单，server.shutdown=graceful 就搞定了（注意，优雅停机配置需要配合Tomcat 9.0.33（含）以上版本）

```yml
server:
  port: 6080
  shutdown: graceful #开启优雅停机
spring:
  lifecycle:
    timeout-per-shutdown-phase: 20s #设置缓冲时间 默认30s
```

## 实现SignalHandler接口

### 1、项目启动时注册信号

可以直接在main方法里注册，spring项目也可以在容器初始化的时候注册，这里采用后者。

```Java
package com.example.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.Signal;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class KillManager {

    @Autowired
    private KillHandler killHandler;

    @PostConstruct
    public void registerKillHandler() {
        registerSignal("TERM");
//        killHandler.registerSignal("HUP");
        registerSignal("INT");
//        killHandler.registerSignal("QUIT");
//        killHandler.registerSignal("ILL");
        registerSignal("ABRT");
//        killHandler.registerSignal("BUS");
//        killHandler.registerSignal("KILL");
    }

    private void registerSignal(String signalName) {
        log.info("register signal: {}  ", signalName);
        Signal signal = new Signal(signalName);
        Signal.handle(signal, killHandler);
    }

}
————————————————
版权声明：本文为CSDN博主「闪光的岁月」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/Caide3/article/details/108782865
```

### 2、实现SignalHandler接口

```java
package com.example.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.misc.Signal;
import sun.misc.SignalHandler;

@Slf4j
@Service
public class KillHandler implements SignalHandler {

    /**
     * 强制停止服务，用于程序主动停止
     */
    public void forceShutdownServer() {
        log.error(" KillHandler force to shutdown server !!!! ");
        Signal signal = new Signal("TERM");
        handle(signal);
    }

    @Override
    public void handle(Signal signal) {
        log.info("server get signal to stop server : {}  {}", signal);
        if (signal.getName().equals("TERM")) {
            stopServer();
        }
        if (signal.getName().equals("INT") || signal.getName().equals("HUP")) {
            System.out.println("server get signal"+signal.getName());
        }
    }

    /**
     * 停止服务，可以做一些善后工作，这里主动注销eurekaClient
     */
    private void stopServer() {
        //改变服务运行状态，需要自己设置状态
        Application.setRunningStatus(false);
        try {
            eurekaAutoServiceRegistration.stop();
            if (client != null) {
                client.shutdown();
                client = null;
            }
            DiscoveryManager.getInstance().shutdownComponent();
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error(" KillHandler handle sleep current exception ", e);
        }
        log.info("KillHandler instance start to stop server!!! ");
        Runtime.getRuntime().exit(0);
    }

}
————————————————
版权声明：本文为CSDN博主「闪光的岁月」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/Caide3/article/details/108782865
```

## 区别

第一种方法在进程被kill的时候main函数就已经结束了，仅会运行shutdownHook中run()方法的代码。

第二种方法handle函数会在进程被kill时收到信号，对main函数的运行不会有影响，
我们可以自己在main函数中添加状态，当收到信号时修改状态，根据状态灵活处理程序。
————————————————
版权声明：本文为CSDN博主「闪光的岁月」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/Caide3/article/details/108782865
