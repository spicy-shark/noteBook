# HashMap底层原理

数组加红黑树，初始大小为16每次扩容为原来的二倍，链表长度大于8时将链表转换为红黑树，但是如果数组长度小于64则优先扩容数组。允许null-key和null-value

HashTable是线程安全的，使用synchorized修饰但是性能较低，

**建议使用concurrentHashMap（对Node加锁不会锁住整个Map）**

# 手撕代码

[https://leetcode.cn/problems/longest-substring-without-repeating-characters/](https://hd.nowcoder.com/link.html?target=https://leetcode.cn/problems/longest-substring-without-repeating-characters/)

[https://leetcode.cn/problems/SsGoHC/](https://hd.nowcoder.com/link.html?target=https://leetcode.cn/problems/SsGoHC/)

给定包含加减乘除的字符串，设计计算器

# Java跨平台的机制和原理

字节码一次编译到处运行，JVM，也就是 Java 虚拟机，就是一个平台，包含于 JRE 的下面。当你需要执行某个 Java 程序时，由 JVM 帮你进行编译和执行。我们编写的 Java 源码，编译后会生成一种 .class 文件，称为字节码文件。Java 虚拟机就是负责将字节码文件翻译成特定平台下的机器码然后运行。
————————————————
版权声明：本文为CSDN博主「来杯咖啡」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_62996462/article/details/124695438

# JVM内存是如何管理的

堆

方法区

虚拟机栈

本地方法栈

程序计数器

# GC发生在什么时候

对象在新生代的Eden区中分配，当Eden区域满了以后会触发Minor GC

GC触发机制：

1. 当新生代Eden区域满时触发Minor GC，此时如果Survive空间不足则有些对象会晋升至老年代；
2. 如果根绝之前的统计数据发现平均晋升空间比目前老年代剩余空间大，则触发Full GC，Full GC会收集整个GC堆包括新生

其他问题：

- 调用GC（System.gc()）之后会立即触发吗？
- 如果立马触发的话，会导致频繁回收，又会带来什么问题？如果不是，是什么机制来保障的（finalize）
- Java GC之后还会有内存泄漏问题吗：举例ThreadLocal弱引用，ThreadLoaclMap的key为弱引用，而value是强引用，当key被回收后value仍存在，可能造成内存泄漏，所以建议使用完后调用remove()方法。
- 哪些可以作为GC Root：虚拟机栈（栈帧）中引用的对象、本地方法栈中引用的对象、方法区中静态变量引用的对象、方法区中常量引用的对象、所有被同步锁持有的对象。

# String为什么不可修改

- 字符串常量，或者new一个String对象，创建对象的时候有什么差异吗？
- 非要改String类型，能改的到吗？：通过反射获取String中的char[] value，修改value
- new一个String对象，是放在哪里的？：字符串常量池
- StringBuilder和StringBuffer有什么区别？：StringBuffer线程安全但是速度较慢

# 反射机制

- 原理：根据方法区中的Class对象获取类信息。
- 反射创建对象和new创建对象有什么区别？：如果使用new创建对象那么在程序执行之前就已经知道要运行的类是什么，而如果使用反射创建则知道程序运行时才会知道要操作的类是什么。
- 反射创建和new的性能哪个更高一点？：new的性能更高

# 多线程实现方式

实现Runnable

实现Callable

继承Thread重写run方法

线程池

# 设计模式

# Java I/O

# Redis和大型数据库的区别

Redis、mongoDB、MySQL

## 如何使用Redis实现消息队列

# 计算机网络

## TCP/IP，HTTP和HTTPS

三次握手四次挥手

ACK机制

socket、HTTP、TCP/IP的联系与区别：https://blog.csdn.net/NRWHF/article/details/127950990

HTTP1.0、HTTP1.1、HTTP2.0：https://blog.csdn.net/qq_34272760/article/details/126398841

TCP三次握手四次挥手：

https://blog.csdn.net/m0_38106923/article/details/108292454

https://blog.csdn.net/qzcsu/article/details/72861891

# 数字证书

- 数字证书使用了什么机制？
- 数字证书为什么可以被创建？

# RabbitMQ消息丢失如何解决

confirm机制，rabbitMQ收到消息并持久化后，会给生产者回传一个消息告诉生产者，这条消息已经确认，如果未能处理也会回传一条消息，这时生产者可以选择重新发送消息。

# IOC

循环依赖以及如何解决：https://blog.csdn.net/weixin_44129618/article/details/122839774

# AOP的实现和应用场景

# 数据库隔离级别

## SQL优化

## 索引的存储原理

# 一面

上来介绍工作经历和项目，然后从项目延伸问了很多，不算太深但是各个方面都涉及一点。

印象比较深刻的几个问题：

1）如何进行服务间的通信，比如管理员将用户封号如何实时影响到用户端

答：可以使用消息队列将消息通知到用户服务。

还有没有其他实现方式

答：Redis也可以实现消息队列功能

2）服务间通信为什么用HTTP而不用HTTPS

答：所有服务在内网，相对来说安全可以使用HTTP

3）项目里使用的token加密算法

答：SHA-256（这里没记住，后来看了一下是HS256，即Hamc using SHA-256）

4）token的过期判断

答：token有效期6小时，以及生成token后会将token存入redis6小时后过期

5）业务中印象最深的一个bug如何处理的

答的不太好，面试官本意应该是从日志处分析追踪，但我答对却是从代码结构层面的分析，问到bug追踪其实首要的就是应该答从日志追踪开始。

6）然后问了一点Java八股

- HashMap底层实现，数组加链表和数组加红黑树的优劣；
- 如果一个对象被添加进map后hashCode改变会导致什么问题；答：内存泄漏

7）计算机网络相关

网络之前没有复习，答的不好，应该花时间好好复习一下

- TCP和UDP的区别和优劣
- TCP如何保证数据可靠性；答：序列号确认和重传机制。
- TCP拥塞控制；答：只答上了慢启动和快速重传，这里应该仔细看下

8）最后手撕代码LeetCode-150逆波兰算式求值 https://leetcode.cn/problems/evaluate-reverse-polish-notation/description/

代码很简单写的也比较快写完一遍过，然后讲了一下思路就结束了。

# 二面

二面只面了四十分钟

上来自我介绍然后问了一点简单的基础知识

1）线程安全除了锁还有几种实现方式

volatile、原子类

2）HashMap是否可以为空

key和value都允许为null，HashTable不允许（这个问题居然记反了。。。）

3）单例模式有几种形式，有什么优劣

https://blog.csdn.net/worldchinalee/article/details/100972352

4）手撕代码

```
题目：
给出一个排序好的数组和一个数，求数组中连续元素的和等于所给数的子数组
例如：
      int[] num = {1,2,2,3,4,5,6,7,8,9};
              int sum = 7;
输出：
2,2,3
3,4
7
```

写了二十分钟左右，一开始有点紧张写完又调试了一会才完成，最后讲了一下思路就结束了。

后来hr跟我讲只要能写出代码就算通过。。。

# 加面

Java设计模式

TCP和UDP

集合，HashMap底层

AOP工作原理

注解工作原理

RabbitMQ如何保证消息可消息
