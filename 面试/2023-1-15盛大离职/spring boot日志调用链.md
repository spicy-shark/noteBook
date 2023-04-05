# spring boot日志调用链

## log4j添加requestid

> [SpringBoot-日志收集与设置调用链requestid到日志中_BtWangZhi的博客-CSDN博客](https://blog.csdn.net/BtWangZhi/article/details/103688314)	~

> https://blog.csdn.net/w8y56f/article/details/103496534

使用ThreadlLocal保存requestId

## MDC

>[SpringBoot+MDC实现全链路调用日志跟踪 (juejin.cn)](https://juejin.cn/post/6844904101483020295)

使用MDC保存traceId,MDC底层为ThreadLocal实现,是一个线程安全的存放争端日志的容器,方便在多线程条件下记录日志的功能

在旧版本中新启线程时MDC会自动将父线程的MDC内容复制给子线程，因为MDC内部使用的是InheritableThreadLocal，但是因为性能问题在最新的版本中被取消了，所以子线程不会自动获取父线程MDC的内容。官方建议我们在父线程新建子线程之前调用MDC.getCopyOfContextMap()方法将父线程的MDC内容取出传给子线程，子线程在执行操作之前先调用MDC.setContextMap()方法将父线程的MDC内容设置到子线程中去。
————————————————
版权声明：本文为CSDN博主「May的博客」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/lmb55/article/details/88418191

### MDC使用

- 添加拦截器

  ```java
  public class LogInterceptor implements HandlerInterceptor {
      @Override
      public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
          //如果有上层调用就用上层的ID
          String traceId = request.getHeader(Constants.TRACE_ID);
          if (traceId == null) {
              traceId = TraceIdUtil.getTraceId();
          }
  
          MDC.put(Constants.TRACE_ID, traceId);
          return true;
      }
  
      @Override
      public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
              throws Exception {
      }
  
      @Override
      public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
              throws Exception {
          //调用结束后删除
          MDC.remove(Constants.TRACE_ID);
      }
  }
  
  ```

- 修改日志格式

  ```xml
  <property name="pattern">[TRACEID:%X{traceId}] %d{HH:mm:ss.SSS} %-5level %class{-1}.%M()/%L - %msg%xEx%n</property>
  ```

### 子线程日志打印丢失traceId

```java
public class ThreadPoolExecutorMdcWrapper extends ThreadPoolExecutor {
    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                        RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
```





## 在SpringBoot项目中添加logback的MDC

> [在SpringBoot项目中添加logback的MDC_上善若水-CSDN博客](https://blog.csdn.net/hongyang321/article/details/78803584)



