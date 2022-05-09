# 第一章 计算机系统漫游

## 1.1 信息就是位+上下文

系统中的所有信息——包括磁盘文件、内存中的程序、内存中存放的用户数据以及网络上传送的数据都是由一串比特表示的，区分不同对象的唯一方法是我么不能读到这些对象时的上下文。比如在不同的上下文中，一个相同的字节序列可能表示一个整数、浮点数、字符串或者机器指令。

![](images\ch01\hello.c的ASCII文本表示.jpg)

## 1.2 程序被其他程序翻译成不同的格式

![](images\ch01\编译系统.jpg)

* 预处理阶段

  预处理器（cpp）：`hello.c -> hello.i`

* 编译阶段

  编译器（ccl）：`hello.i -> hello.s（汇编语言程序）` 

```assembly
main:
	subq	$8, 	%rsp
	movl	$.LCO,	%edi
	call	puts
	movl	$0,		%eax
	addq	$8,		%rsp
	ret
```

* 汇编阶段

  汇编器（as）：`hello.s -> hello.o（可重定位目标程序，为二进制文件）`

* 链接阶段

  连接器（dl）将程序与库函数处理合并得到`hello`文件，称为可执行文件，可以被加载到内存中，由系统执行。

## 1.3 了解编译系统如何工作是大有益处的

* 优化程序性能

* 理解链接时出现的错误
* 避免安全漏洞

## 1.4 处理器读并解释存储在内存中的指令

若要在Unix系统上运行可执行文件`hello`需要将文件名输入到成为shell的应用程序中：

```
linux> ./hello
hello, world
linux>
```

### 1.4.1 系统的硬件组成

* 总线

* I/O设备

* 主存

* 处理器

  ![](images\ch01\系统的硬件组成.jpg)

### 1.4.2 运行hello程序

*1、从键盘读取hello指令*

![](images\ch01\从键盘读取hello指令.jpg)

*2、从磁盘加载可执行文件到主存*

![](images\ch01\从磁盘加载可执行文件到主存.jpg)

*3、将输出字符串从存储器写到显示器*

![](images\ch01\将输出字符串从存储器写到显示器.jpg)

## 1.5 高速缓存至关重要

意识到高速缓存存储器存在的应用程序员能够利用高速缓存将程序的性能提高一个数量级。

![](images\ch01\高速缓存存储器.jpg)

## 1.6 存储设备形成层次结构

在存储器层次结构中，从上至下设备的访问速度越来越慢，容量越来越大，也越来越便宜。

![](images\ch01\存储器层次结构.jpg)

## 1.7 操作系统管理硬件

计算机系统的分层视图：

![](images\ch01\计算机系统的分层视图.jpg)

操作系统的两个基本功能：

* 防止硬件被失控的应用程序滥用。
* 向应用程序提供简单一致的机制来控制复杂而又通常大不相同的低级硬件设备。

![](images\ch01\操作系统提供的抽象表示.jpg)

### 1.7.1 进程

进程时操作系统对一个正在运行的程序的一种抽象。进程间切换机制成为上下文切换。

### 1.7.2 线程

一个进程可以由多个称为线程的执行单元组成。

### 1.7.2 虚拟内存

进程的虚拟地址空间：

![](images\ch01\进程的虚拟地址空间.jpg)

* 程序代码和数据

* 堆

* 共享库

* 栈

* 内核虚拟内存

  

### 1.7.3 文件

文件就是字节序列，仅此而已。每个I/O设备，包括磁盘、键盘、显示器，甚至网络都可以看成是文件系统中所有输入输出都是通过使用一小组称为Unix I/O的系统函数调用读写文件来实现的。

## 1.8 系统之间利用网络通信

现代系统经常通过网络和其他系统连接到一起。从一个单独的系统来看，网络可视为一个I/O设备。

![](images\ch01\网络也是一种IO设备.jpg)

## 1.9 重要主题

### 1.9.1 Amdahl定律

$$
T_{new} = (1 - \alpha)T_{old} + (\alpha T_{old}) / k = T_{old} [(1 - \alpha) + \alpha / k]
$$

由此,可计算加速比$$S = T_{old}/T_{new}$$为
$$
S = \frac{1}{(1 - \alpha) + \alpha / k}
$$

### 1.9.2 并发和并行

* 线程级并发

  ![](images\ch01\多核处理器组织结构.jpg)

* 指令级并行

  如果处理器可以达到比一个周期一条指令更快的执行速率，就称之为超标量（super-scalar）处理器

* 单指令、多数据并行（SIMD）

### 1.9.3 计算机系统中抽象的重要性

* 文件是对I/O设备的抽象
* 虚拟内存是对程序存储器的抽象
* 进程是对一个正在运行的程序的抽象

![](images\ch01\计算机系统提供的一些抽象.jpg)

## 1.10 小结



# 第二章 信息的表示和处理

## 2.1 信息存储

### 2.1.1 信息表示

基本C数据类型大小：

![](images\ch02\基本C数据类型大小.jpg)

* 大端法：高有效字节在最前面
* 小端法：低有效字节在最前面

### 2.1.2 移位运算

Java中
$$
x >> k
$$
表示算术右移
$$
x >>> k
$$
表示逻辑右移



## 2.2 整数表示

### 2.2.1 整型数据类型

32位程序上C语言整型数据类型的典型取值范围：

![](images\ch02\32位程序上C语言整型数据类型的典型取值范围.jpg)

​	64位程序上C语言整型数据类型的典型取值范围：

![](images\ch02\64位程序上C语言整型数据类型的典型取值范围.jpg)

### 2.2.2 无符号数的编码

$$
B2U_w(\vec x) = \sum_{i=0}^{w-1}{x_i2^i}
$$

### 2.2.3 补码编码

$$
B2U_{w}(\vec x) = -x_{w-1}2^{w-1} \sum_{i=0}^{w-1}{x_i2^i}
$$

### 2.2.4 有符号数和无符号数之间的转换

按照各自的计算规则直接转换（就硬转）

* 补码转换为无符号数
  $$
  T2U_w(x) =\begin{cases} x + 2^w，x < 0\\ x， x\geq0\end{cases}
  $$
  
* 无符号数转换为补码
  $$
  U2T_w(u) =\begin{cases} u，u\leq TMax_w\\ u-2^w， u>TMax_w\end{cases}
  $$

### 2.2.5 C语言中的有符号数和无符号数

C语言升级规则的效果：

![](E:\Books\CSAPP\images\ch02\C语言升级规则的效果.jpg)

### 2.2.6 拓展一个数字的位表示

* 无符号数的零拓展
* 补码数的符号拓展

### 2.2.7 截断数字

* 截断无符号数
  $$
  B2U_w([x_{w-1}, x_{w-2}, \cdots, x_0])mod2^k
  $$

* 截断补码数值
  $$
  B2T_k[x_{k-1}, x_{k-2}, \cdots, x_0] = U2T_k(B2U_w([x_{w-1}, x_{w-2}, \cdots, x_0])mod2^k)
  $$
  

## 2.3 整数运算

* 无符号加法
  $$
  x + ^u_wy =\begin{cases} x + y，x + y < 2^w\quad正常\\ x + y - 2_w， 2^w\leq x + y < 2 ^{w-1}\quad 溢出\end{cases}
  $$

* 补码加法
  $$
  x + ^t_wy = U2T_w(T2U_w(x) + ^u_wT2U_w(y))
  $$

* 补码的非
  $$
  -^t_wx = \begin{cases} TMin_w,\quad x\quad x = TMin_w\\-x,\quad x > TMin_w \end{cases}
  $$

* 无符号乘法
  $$
  x * ^t_wy = (x \cdot y)mod2^w
  $$

* 补码乘法
  $$
  x * ^t_wy =U2T_w((x \cdot y)mod2^w)
  $$

* 乘以常数、除以2的幂，通过移位和加减法来简化运算流程。


## 2.4 浮点数

### 2.4.1 二进制小数

考虑一个形如$$b_mb_{m-1}\cdots b_1b_0.b_{-1}b_{-2}\cdots b_{-n-1}b_{-n}$$的表示法，其中每个二进制数字，或者称为位，$$b_i$$的取值范围为0和1。这种表示法表示的数$$b$$定义如下：
$$
b = \sum_{i = -n}^m2^i \times b_i
$$

### 2.4.2 IEEE浮点表示

IEEE浮点标准用$$V = (-1)^s\times M \times 2^E$$的形式来表示一个数：

* 符号（sign）s
* 尾数（significand）M
* 阶码（exponent）E

![](\images\ch02\标准浮点格式.jpg)

根绝exp的值，被编码的值可以分为三种不同的情况（最后一种情况有两个变种）。

**情况1：规格化的值**

exp的值既不全为0也不全为1：

* 阶码$$E = e - Bias \quad 其中Bias = 2^k - 1$$
* 尾数$$M = 1 + f \quad 其中0\leq f < 1$$ 隐含的以1开头

**情况2：非规格化的值**

exp的值全为0：

* 阶码$$E = 1 - Bias$$
* 尾数$$M = f$$ 不包含隐含的开头的1

**情况3：特殊值**

exp的值全为1：

* 小数域全0时表示无穷$$(s = 0表示+\infty, s = 1表示-\infty)$$
* 小数域为非零时结果被称为$$NaN(Not\quad a\quad Number)$$

![](\images\ch02\单精度浮点数值的分类.jpg)

### 2.4.3 数字示例

6位浮点格式可表示的值（k=3的阶码位和n=2的尾数位吗，偏置量是3）：

![](\images\ch02\6位浮点格式可表示的值(k=3的阶码位和n=2的尾数位,偏置量是3).jpg)

8位浮点格式的非负值示例（k=4的阶码位和n=3的小数位，偏置量是7）：

![](\images\ch02\8位浮点格式的非负值示例(k=4的阶码位和n=3的小数位,偏置量是7).jpg)

### 2.4.4 舍入

向偶数舍入法，一般来讲，只有对形如$$XX\cdots X.YY100$$的二进制位模式的数这种方式才有效，其中$$X$$和$$Y$$表示任意值，最右边的$$Y$$是要被舍入的位置。只有这种位模式表示在两个可能结果正中间的值，并且我们倾向于使最低有效位为零。

例如：

​	$$10.11100_2$$向上舍入成$$11.00_2$$，而$$10.10100_2$$向下舍入成$$10.10_2$$

### 2.4.5 浮点运算

* $$1/-0 = -\infty,\quad 1/+0 = +\infty$$
* 由于舍入的机制，浮点加法在某种情况下有可能不符合加法的结合律
* 浮点加法满足单调性：如果$$a \geq b，那么对于任何a，b以及x的值，除了NaN，都有x+a \geq x+b$$。无符号或补码加法不具有这个实数（和整数）加法的属性。
* 浮点乘法是可交换的且符合单调属性，但由于可能发生溢出，或者由于舍入而失去精度，它不具有可结合性。

## 2.5 小结





# 第三章 程序的机器级表示

## 3.1 历史观点

IA32也就是“Intel Architecture 32-bit”，最新的Intel64，即IA32的64位扩展我们也称为x86-64，常称x86指代整个系列。

## 3.2 程序编码

gcc调用了一整套的程序将源代码转换成可执行代码。首先C预处理器扩展源码，插入所有用#include命令指定的文件，并扩展所有#define声明指定的宏。其次编译器产生两个源文件的汇编代码（.s），接下来，汇编器会将汇编代码转化成二进制目标代码文件（.o）。目标代码是机器代码的一种形式，它包含所有指令的二进制表示但是还没有填入全局值的地址。最后，连接器将两个目标代码与实现库函数的代码合并，并产生最终的可执行代码文件。可执行代码文件是我们要考虑的机器代码的第二种形式，也就是处理器执行的代码格式。

### 3.2.1 机器级代码

x86-64的机器代码和原始的C代码差别非常大。一些通常对C语言程序员隐藏的处理器状态都是可见的：

* 程序计数器（通常称为“PC”，在x86-64中用%rip表示）给出即将要执行的下一条指令在内存中的地址。
* 整数寄存器文件包含16个命名的位置，分别存储64位的值。这些寄存器可以存储地址或整数数据。有点寄存器被用来记录某些重要的程序状态，而其他寄存器用来保存临时数据，例如过程参数和局部变量，以及函数返回值。
* 条件码寄存器保存着最近执行的算数或逻辑指令的状态信息。他们用来实现控制或数据流中的条件变化，比如实现if和while语句。
* 一组向量寄存器可以存放一个或多个数据浮点数值。

### 3.2.2 代码示例

```assembly
#Disassembly of function sum in binary file mstore.o
1	0000000000000000 <mulstore>:
#	Offset	Bytes				Equivalent assembly language
2	0:		53								push	%rbx
3	1:		48 89 d3					mov		%rdx, %rbx
4	4:		e8 00 00 00 00		callq	9 <mulstore+0x9>
5	9:		48 89 03					mov		%rax, (%rbx)
6	c:		5b								pop		%rbx
7	d:		c3								retq
```



在左边，按照前面给出的字节顺序排列的14个十六进制字节值，它们分别成了若干组，每组有1~5个字节。每组都是一条指令，右边是等价的汇编语言。其中一些关于机器代码和它的反汇编表示的特性值值得注意：

* x86-64的指令长度从1到15个字节不等。常用的指令以及操作数较少的指令所需的字节数少，不太常用的操作或操作数较多的指令所需的字节数较多。
* 设计指令格式的方式是，从某个指定位置开始，可以将字节唯一地编码成机器指令。
* 反汇编器只是基于机器代码文件中的字节序列来确定汇编代码。它不需要访问该程序的源代码或汇编代码。
* 反汇编器使用的指令命名规则与GCC生成的汇编代码使用的有些细微的差别。

## 3.3 数据格式

大多数GCC生成的汇编代码指令都有一个字符的后缀，表明操作数的大小。

![](\images\ch03\C语言数据类型在x86-64中的大小,在64位机器中,指针长8字节.jpg)

汇编代码也使用 $$l$$ 用来表示4字节整数和8字节双精度浮点数。这不会产生歧异，因为浮点数使用的是一组完全不同的指令和寄存器。

## 3.4 访问信息

一个x86-64的中央处理器（CPU）包含一组16个存储64位值的$$通用目的寄存器$$。这些寄存器都用来存储整数数据和指针。

![](\images\ch03\整数寄存器.所有16个寄存器的低位字节部分都可以作为字节,字(16位),双字(32位)和四字(64位)数字来访问.jpg)

有一组标准的编程规范控制着如何使用寄存器来管理栈、传递函数参数、从函数的返回值，以及存储局部和临时数目。

### 3.4.1 操作数指示符

各种不同的操作数的可能性被分为三种类型：

* 立即数
* 寄存器（register）
* 内存引用

![](\images\ch03\操作数格式.操作数可以表示立即数(常数)值,寄存器值或是来自内存的值.比例因子s必须是1,2,4或8.jpg)

### 3.4.2 数据传送指令

MOV类，这些指令把数据从源位置复制到目的位置不做任何变化。MOV类由四条指令构成：movb、movw、movl和movq。这些指令都执行同样的操作，主要区别在于它们的操作数大小不同：分别为1、2、4和8字节。这些指令的操作数可以是16个寄存器中有标号部分中的任意一个，寄存器部分的大小必须与指令最后一个字符（’b‘，’w‘，’l‘或’q‘）指定的大小匹配。大多数情况中，MOV指令只会更新目的寄存器指定的那些寄存器字节或位置。唯一例外的情况是movl指令以寄存器位目的时，它会把该寄存器的高32位设置为0.造成该例外的原因是x86-64采的用的管理，级任何寄存器生成32位值的指令都会把该寄存器的高位部分置为0。

![](\images\ch03\简单的数据传送指令.jpg)

零扩展数据传送：

![](\images\ch02\零扩展数据传送.jpg)

符号扩展数据传送（cltq指令只适用于寄存器%eax和%rax）：

![](\images\ch03\符号扩展数据传送.jpg)

没有一条明确的指令把4字节源值零扩展到8字节目的。这样的指令逻辑上应被命名为movzlq，但是并没有这样的指令。不过，这样的指令可以用以寄存器为目的的movl指令来实现。这一技术利用的属性是，生成4字节值并以寄存器为目的的指令会把高4字节置为0。对于64位的目标，所有三种源类型都有对应的符号扩展传送，而只有两种较小的源类型有零扩展传送。

### 3.4.3 数据传送示例

```c
long exchange(long *xp, long y)
{
	long x = *xp;
	*xp = y;
	return x;
}
```

```assembly
#long exchange(long *xp, long y)
#x in %rdi, y in %rsi
1	exchange:
2	  movq	(%rdi), %rax	#Get x at xp. Set as renturn vslue.
3	  movq	%rsi, (%rdi)	#Store y at xp.
4	  ret									#Return
```

### 3.4.4 压入和弹出栈数据

![](\images\ch03\入栈和出栈指令.jpg)

pushq指令的功能是把数据压入到栈上，而popq指令是弹出数据。这些指令都只有一个操作数——压入的数据源和弹出的数据目的。将一个四字值压入栈中，首先要将栈指针减8，然后将值写入到新的栈顶地址。因此，指令pushq %rbq的行为等价于下面两条指令：

```assembly
subq	$8, %rsp			#Decrement stack pointer
movq	%rbp, (%rsp)	#Store %rbp on stack
```

他们之间的区别是在机器代码中pushq指令编码为一个字节，而上面那两条指令一共需要8个字节。

弹出一个四字的操作包括从栈顶位置读出数据，然后将栈指针加8。因此指令pop %rax等价于下面两条指令：

```assembly
movq	(%rsp), %rax	#Read %rax from stack
addq	$8, %rsp			#Increment stack pointer
```

## 3.5 算术和逻辑操作

这些操作分为四组：加载有效地址、一元操作、二元操作和移位。

![](\images\ch03\整数算数操作.jpg)

加载有效地址（leaq）指令通常用来执行简单的算术操作。其余的是更加标准的一元或二元操作。我们用 >>~A~ 和>>~L~来分别表示算术右移和逻辑右移。

### 3.5.1 加载有效地址

$$加载有效地址指令（load\quad effective\quad address）$$指令leaq实际上是movq指令的变形。它的指令形式是从内存读取数据到寄存器，但实际上它根本没有引用内存。它的第一个操作数看上去是一个内存引用，但该指令并不是从指定的位置读入数据，而是将有效地址写入到目的操作数。目的操作数必须是一个寄存器。

为了说明leaq在编译出的代码中的使用，看看下面这个C程序：

```C
long scale(long x, long y, long z) {
    long t = x + 4 * y + 12 *z;
    return t;
}
```

```assembly
#long scale(long x, long y, long z)
#x in %rdi, y in %rsi, z in %rdx
scale:
  leaq	(%rdi, %rsi, 4), %rax	#x + 4 * y
  leaq	(%rdx, %rdx, 2), %rdx	#z + 2 * z
  leaq	(%rax, %rdx, 4), %rax	#(x + 4 * y) + 4 * (3 * z) = x + 4 * y + 12 * z
  ret
```

### 3.5.2 一元和二元操作

第二组中的操作是一元操作，只有一个操作数，既是源又是目的。这个操作数可以是一个寄存器，也可以是一个内存位置。第三组是二元操作，其中，第二个操作数既是源又是目的。第一个操作数可以是立即数、寄存器或是内存位置。第二个操作数可以是寄存器或内存位置。当第二个操作数为内存地址时，处理器必须从内存读出值，执行操作，再把结果写回内存。

### 3.5.3 移位操作

最后一组是移位操作，先给出移位量，然后第二项给出的是要位移的数。可以进行算数和逻辑右移，移位量可以是一个立即数，或者放在单字节寄存器%cl中。（这些指令很特别因为只允许以这个特定的寄存器作为操作数。）移位量是由%cl寄存器的低m位决定的，这里2^m^ = w高位被忽略。所以，例如当寄存器%cl的十六进制值位0xFF时指令salb会移7位，salw会移15位，sall会移31位，而salq会移63位。

### 3.5.4 讨论

### 3.5.5 特殊的算术操作

这些操作提供了有符号和无符号数的全128位乘法和除法。一对寄存器%rdx和%rax组成一个128位的八字：

![](\images\ch03\特殊的算数操作.jpg)

乘法操作要求一个参数必须存储在寄存器%rax中，而另一个作为指令的源操作数给出。然后乘积存放在寄存器%rdx（高64位）和%rax（低64位）中。虽然imulq这个名字可以用于两个不同的乘法操作，但是汇编器能够通过计算操作数的数目，分辨出想用哪条指令。

有符号除法指令idivl将寄存器%rdx（高64 位）和%rax（低64位）中的128位数作为被除数，而除数作为指令的操作数给出。指令将商存储在%rax将余数存储在%rdx中。

## 3.6 控制

C语言中的某些结构，比如条件语句、循环语句和分支语句，要求有条件的执行，根据数据测试的结果来决定操作执行的顺序。机器代码提供两种基本的低级机制来实现有条件的行为：测试数据值，然后根据测试的结果来改变控制流或者数据流。

### 3.6.1 条件码

除了整数寄存器，CPU还维护着一组单个位的条件码寄存器，它们除了描述最近的算数或逻辑操作的属性。可以检测这些寄存器来执行条件分支指令。最常用的条件码有：

```
CF：进位标志。最近的操作使最高位产生了进位。可以用来检查无符号的溢出。
ZF：零标志。最近的操作得出的结果为0。
SF：符号标志。最近的操作得到的结果为负数。
OF：溢出标志。最近的操作导致一个补码溢出——正溢出或负溢出。
```

leaq指令不改变任何条件码,因为它使用来进行地址计算的除此之外下图中的所有指令都会设置条件码：

![](\images\ch03\整数算数操作.jpg)

还有两类指令，它们只设置条件码而不改变任何其他寄存器：

![](\images\ch03\比较和测试指令.jpg)

### 3.6.2 访问条件码

条件码通常不会直接读取，常用的使用方法有三种：

* 可以根据条件码的某种组合将一个字节设置为0或1；
* 可以条件跳转到程序的某个其他的部分；
* 可以有条件的传送数据。

一条SET指令的目的操作数是低位单字节寄存器元素之一，或是一个字节的内存位置，指令会将这个字节设置成0或1。为了得到32位或64位结果，我们必须对高位清零。

![](\images\ch03\SET指令.jpg)

### 3.6.3 跳转指令

跳转指令会导致执行切换到程序中一个全新的位置,这些跳转的目的地通常用一个标号指明。

![](\images\ch03\jump指令.jpg)

### 3.6.4 跳转指令的编码

在汇编代码中，跳转目标用符号标号编写。汇编器，以及后来的连接器，会产生跳转目标的适当编码。跳转指令有几种不同的编码，但是最常用的都是PC相对的（PC-relative）。也就是，它们会将目标指令的地址与紧跟在跳转指令后面那条钟灵的地址之间的差作为编码。这些地址偏移量可以编码为1、2或4个字节。第二种编码方法是给出“绝对地址”，用4个字节直接指定目标。会编码器和连接器会选择适当的跳转目的编码。

### 3.6.5 用条件控制来实现条件分支

C语言中if-else语句的通用形式模板如下：

```c
if(test_expr)
	then_statement
else
	else_statement
```

对于这种通用形式，汇编实现通常会使用下面这种形式，这里，我们用C语法来描述控制流：

```c
	t = test_expr;
	if(!t)
        goto false;
	then_statement
    goto done;
false:
	else_statement
done:
```

也就是，汇编器为then_statement和else_statement产生各自的代码块。它会插入条件和无条件分支，以保证能执行正确的代码块。

已知下列C代码：

```c
void cond(long a, long *p){
    if (p && a > *p)
        *p = a;
}
```

GCC会产生下面的汇编代码：

```assembly
# void cond(long a, long *p)
# a in %rdi, p in %rsi
cond:
	testq	%rsi, %rsi
	je		.L1
	cmpq	%rdi, (%rsi)
	jge		.L1
	movq	%rdi, (%rsi)
.L1
	rep;ret
```

### 3.6.6 用条件传送来实现条件分支

汇编器可以从目标寄存器的名字推断出条件传送指令的操作数长度，所以对所有操作数长度，都可以使用同一个指令名字。

![](\images\ch03\条件传送指令.jpg)

C语言中条件传送语句的通用形式模板如下：

```c
v = test_expr ? then_expr : else_expr;
```

用条件控制转移的标准方法来编译这个表达式会得到如下形式：

```c
    if (!test_expr)
        goto false;
    v = then_expr;
    goto done;
false:
	v = else_expr;
done:
```

基于条件传送的代码会得到如下表示：

```c
v = then_expr;
ve = else_expr;
t = test_expr;
if (!t) v = ve;
```

条件传送也不总是会提高代码的效率，如果then_expr或者else_expr的求值需要大量的计算那么当对应条件不满足时，这些工作就白做了。编译器必须考虑浪费的计算和由于分支预测错误造成的性能除法之间的相对性能。

### 3.6.7 循环

#### do-while循环

```c
do
    body_statement
    while (test_expr);

# goto形式
loop:
	body_statement
    t = test_expr;
	if (t)
        goto loop;
```

例：

```c
long fact_do(long n){
    long result = 1;
    do{
        result *= n;
        n = n - 1;
    }while (n > 1);
    return result;
}

# 等价goto形式
long fact_do_goto(long n){
    long result = 1;
loop:
    result *= n;
    n = n - 1;
    if (n > 1)
        goto loop;
    return result;
}
```

对应汇编代码：

```assembly
# long fact_do(long n)
# n in %rdi
fact_do:
	movl	$1,		%eax	# 	Set result = 1
.L2:						# loop:
	imulq	%rdi,	%rax	# 	Compute result *= n
	subq	$1,		%rdi	# 	Decrement n
	cmpq	$1,		%rdi	# 	Compare n:1
	jg		.L2				# 	If >, goto loop
	rep;ret					# 	Return
```

#### while循环

```c
while (test_expr)
    body_statement

# 第一种goto形式（jump to middle）
    goto test;
loop:
	body_statement
test:
	t = test_expr;
    if (t)
        goto loop;

# 第二种goto形式（guarded-do）
t = test_expr;
if (!t)
    goto done;
loop:
	body_statement
    t = test_expr;
	if (t)
        goto loop;
done:
```

#### for循环

```c
for (init_expr; test_expr; update_expr)
    body_statement
    
# 第一种goto形式（jump to middle）
    init_expr;
	goto test;
loop:
	body_statement
    update_expr;
test:
	t = test_expr;
	if (t)
        goto loop;
# 第二种goto形式（guarded-do）
	init_expr;
	t = test_expr;
	if (!t)
        goto done;
loop:
	body_statement
    update_expr;
	t = test_expr;
	if (t)
        goto loop;
done:
```

### 3.6.8 switch语句

switch语句可以根据一个整数索引值进行多重分支。不仅提高了C代码的可读性，而且通过使用跳转表这种数据结构使得实现更加高效。跳转表是一个数组，表项i是一个代码段不能地址，这个代码段实现当开关索引值等于i时程序应该采取的动作。跳转表的优点是执行开关语句的时间与开关的情况和数量无关。当开关群情况比较多（例如4个以上），并且值的范围跨度比较小的时候，就会使用跳转表。

```c
void switch_eg(long x, long n, long *dest) {
  long val = x;
  switch(n) {
    case 100:
      val *= 13;
      break;
    case 102:
      val += 10;
      /*Fall through*/
    case 103:
      val += 11;
      break;
    case 104:
    case 106:
      val *= val;
      break;
    default:
      val = 0;
  }
  *dest = val;
}
```

#### 翻译到拓展的C语言

```c
void switch_ef_impl(long x, long n, long dest) {
  /*Table of code pointers*/
  static void *jt[7] = {
    &&loc_A, &&loc_def, &&loc_B, 
    &&loc_C, &&loc_D, &&loc_def, 
    &&loc_D
  };
  unsigned long index = n - 100;
  long val;
  
  if (index > 6)
    goto loc_def;
  /*Multiway branch*/
  goto *jt[index];
  
  loc_A:	/*Case 100*/
	  val = x * 13;
  loc_B:	/*Case 102*/
  	x = x + 10;
  loc_C:	/*Case 103*/
  	val = x + 11;
  	goto done;
  loc_D:	/*Case 104, 106*/
  	val = x * x;
  loc_def: /*Default case*/
  	val = 0;
  done:
  	*dest = val;
}
```

#### 汇编代码示例

```assembly
# void switch_eg(long x, long n, long *dest)
# x in %rdi, n in %rsi, dest in %rdx
switch_eg:
		subq	$100, %rsi							#Compute index = n - 100
		cmpq	$6, %rsi								#Compare index:6
		ja		.L8											#If >, goto loc_def
		jmp		*.L4(, %rsi, 8)					#Goto *jt[index]
	.L3:														#loc_A:
		leaq	(%rdi, %rdi, 2), %rax		#		3*x
		leaq	(%rdi, %rax, 4), %rdi		#		val = 13 * x
		jmp		.L2											#		Goto done
	.L5:														#loc_B:
		addq	$10, %rdi								#		x = x +10
	.L6:														#loc_C:
		addq	$10, %rdi								# 	val = x + 11
		jmp		.L2											# 	Goto done
	.L7:														#loc_D:
		imulq	%rdi, %rdi							# 	val = x * x
		jmp		.L2											# 	Goto done
	.L8:														#loc_def:
		movl	$0, %edi								# 	val = 0
	.L2:														#done:
		movq	%rdi, (%rdx)						# 	*dest = val
		ret														# 	Return
```

##### 跳转表用以下声明表示

```assembly
	.section		.rodata
	.align 8			# Align address to multiple of 8
.L4
	.quad		.L3		# Case 100: loc_A
	.quad		.L8		# Case 101: loc_def
	.quad		.L5		# Case 102: loc_B
	.quad		.L6		# Case 103: loc_C
	.quad 	.L7		# Case 104: loc_D
	.quad		.L8		# Case 105: loc_def
	.quad		.L7		# Case 106: loc_D
```

这些声明表示，在叫做“.rodata”（只读数据，Read-Only Data）的目标代码文件的段中，应该有一组7个“四”字（8个字节），每个字的值都是与指定的汇编代码标号（例如：.L3）相关联的指令地址。标号.L4标记出这个分配地址的起始。与这个标号相对应的地址会作为简介跳转（汇编代码示例中第7行）的基地址。

