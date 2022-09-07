# 简介

Redis是完全开源免费的，遵守BSD协议，是一个高性能的key-value数据库。

Redis 与其他 key - value 缓存产品有以下三个特点：

* Redis支持数据的持久化，可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用。
* Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
* Redis支持数据的备份，即master-slave模式的数据备份。

## 优势

* 性能极高 – Redis能读的速度是110000次/s,写的速度是81000次/s 。
* 丰富的数据类型 – Redis支持二进制案例的 Strings, Lists, Hashes, Sets 及 Ordered Sets 数据类型操作。
* 原子 – Redis的所有操作都是原子性的，同时Redis还支持对几个操作全并后的原子性执行。
* 丰富的特性 – Redis还支持 publish/subscribe, 通知, key 过期等等特性。

## Redis与其他key-value存储有什么不同

* Redis有着更为复杂的数据结构并且提供对他们的原子性操作，这是一个不同于其他数据库的进化路径。Redis的数据类型都是基于基本数据结构的同时对程序员透明，无需进行额外的抽象。
* Redis运行在内存中但是可以持久化到磁盘，所以在对不同数据集进行高速读写时需要权衡内存，应为数据量不能大于硬件内存。在内存数据库方面的另一个优点是， 相比在磁盘上相同的复杂的数据结构，在内存中操作起来非常简单，这样Redis可以做很多内部复杂性很强的事情。 同时，在磁盘格式方面他们是紧凑的以追加的方式产生的，因为他们并不需要进行随机访问。

# 配置

[Redis 配置_redis教程](https://www.redis.net.cn/tutorial/3504.html)

# 数据类型

## Key


| 序号 | 命令及描述                                                                                                                                                                                      |
| ---- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1    | [DEL key](https://www.redis.net.cn/order/3528.html) 该命令用于在 key 存在是删除 key。                                                                                                              |
| 2    | [DUMP key](https://www.redis.net.cn/order/3529.html) 序列化给定 key ，并返回被序列化的值。                                                                                                         |
| 3    | [EXISTS key](https://www.redis.net.cn/order/3530.html) 检查给定 key 是否存在。                                                                                                                     |
| 4    | [EXPIRE key](https://www.redis.net.cn/order/3531.html) seconds 为给定 key 设置过期时间。                                                                                                           |
| 5    | [EXPIREAT key timestamp](https://www.redis.net.cn/order/3532.html) EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置过期时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。 |
| 6    | [PEXPIRE key milliseconds](https://www.redis.net.cn/order/3533.html) 设置 key 的过期时间亿以毫秒计。                                                                                               |
| 7    | [PEXPIREAT key milliseconds-timestamp](https://www.redis.net.cn/order/3534.html) 设置 key 过期时间的时间戳(unix timestamp) 以毫秒计                                                                |
| 8    | [KEYS pattern](https://www.redis.net.cn/order/3535.html) 查找所有符合给定模式( pattern)的 key 。                                                                                                   |
| 9    | [MOVE key db](https://www.redis.net.cn/order/3536.html) 将当前数据库的 key 移动到给定的数据库 db 当中。                                                                                            |
| 10   | [PERSIST key](https://www.redis.net.cn/order/3537.html) 移除 key 的过期时间，key 将持久保持。                                                                                                      |
| 11   | [PTTL key](https://www.redis.net.cn/order/3538.html) 以毫秒为单位返回 key 的剩余的过期时间。                                                                                                       |
| 12   | [TTL key](https://www.redis.net.cn/order/3539.html) 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。                                                                                   |
| 13   | [RANDOMKEY](https://www.redis.net.cn/order/3540.html) 从当前数据库中随机返回一个 key 。                                                                                                            |
| 14   | [RENAME key newkey](https://www.redis.net.cn/order/3541.html) 修改 key 的名称                                                                                                                      |
| 15   | [RENAMENX key newkey](https://www.redis.net.cn/order/3542.html) 仅当 newkey 不存在时，将 key 改名为 newkey 。                                                                                      |
| 16   | [TYPE key](https://www.redis.net.cn/order/3543.html) 返回 key 所储存的值的类型。                                                                                                                   |

## String

string是最基本的数据类型，可以理解为与Memcache一模一样的类型，一个key对应一个value。

string类型是二进制安全的。意思是redis的string可以包含任何数据，比如jpg图片或者序列化的对象。

string是redis最基本的数据类型，一个键最大能存储512MB。

```apache
redis 127.0.0.1:6379> SET name "redis.net.cn"
OK
redis 127.0.0.1:6379> GET name
"redis.net.cn"
```

键名为name值为redis.net.cn

| 序号 | 命令及描述                                                                                                                                                                   |
| ---- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1    | [SET key value](https://www.redis.net.cn/order/3544.html) 设置指定 key 的值                                                                                                     |
| 2    | [GET key](https://www.redis.net.cn/order/3545.html) 获取指定 key 的值。                                                                                                         |
| 3    | [GETRANGE key start end](https://www.redis.net.cn/order/3546.html) 返回 key 中字符串值的子字符                                                                                  |
| 4    | [GETSET key value](https://www.redis.net.cn/order/3547.html) 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。                                                         |
| 5    | [GETBIT key offset](https://www.redis.net.cn/order/3548.html) 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。                                                              |
| 6    | [MGET key1 [key2..]](https://www.redis.net.cn/order/3549.html) 获取所有(一个或多个)给定 key 的值。                                                                              |
| 7    | [SETBIT key offset value](https://www.redis.net.cn/order/3550.html) 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。                                                  |
| 8    | [SETEX key seconds value](https://www.redis.net.cn/order/3551.html) 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。                                      |
| 9    | [SETNX key value](https://www.redis.net.cn/order/3552.html) 只有在 key 不存在时设置 key 的值。                                                                                  |
| 10   | [SETRANGE key offset value](https://www.redis.net.cn/order/3553.html) 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。                                        |
| 11   | [STRLEN key](https://www.redis.net.cn/order/3554.html) 返回 key 所储存的字符串值的长度。                                                                                        |
| 12   | [MSET key value [key value ...]](https://www.redis.net.cn/order/3555.html) 同时设置一个或多个 key-value 对。                                                                    |
| 13   | [MSETNX key value [key value ...]](https://www.redis.net.cn/order/3556.html) 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。                                   |
| 14   | [PSETEX key milliseconds value](https://www.redis.net.cn/order/3557.html) 这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。 |
| 15   | [INCR key](https://www.redis.net.cn/order/3558.html) 将 key 中储存的数字值增一。                                                                                                |
| 16   | [INCRBY key increment](https://www.redis.net.cn/order/3559.html) 将 key 所储存的值加上给定的增量值（increment） 。                                                              |
| 17   | [INCRBYFLOAT key increment](https://www.redis.net.cn/order/3560.html) 将 key 所储存的值加上给定的浮点增量值（increment） 。                                                     |
| 18   | [DECR key](https://www.redis.net.cn/order/3561.html) 将 key 中储存的数字值减一。                                                                                                |
| 19   | [DECRBY key decrement](https://www.redis.net.cn/order/3562.html) key 所储存的值减去给定的减量值（decrement） 。                                                                 |
| 20   | [APPEND key value](https://www.redis.net.cn/order/3563.html) 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。                                |

## Hash

是一个键值对集合，是string类型的field喝value的映射表，hash特别适合用于存储对象。

```apache
redis 127.0.0.1:6379> HMSET user:1 username redis.net.cn password redis.net.cn points 200
OK
dev-tanka-sg-redis.aws.tankaapps.com:6379[2]> hgetall user:1
1) "username"
2) "redis.net.cn"
3) "password"
4) "redis.net.cn"
5) "points"
6) "200"
dev-tanka-sg-redis.aws.tankaapps.com:6379[2]> HGET user:1 username
"redis.net.cn"
```

第一个参数对应对象字段名，第二个参数对应字段值，字段值不可设置为空。

以上实例中 hash 数据类型存储了包含用户脚本信息的用户对象。 实例中我们使用了 Redis **HMSET, HEGTALL** 命令，**user:1** 为键值。 

每个 hash 可以存储 2^32 - 1^ 键值对（40多亿）。

## List

redis列表是最简单的字符串列表，按照插入顺序，你可以添加一个元素到列表的头部或尾部。

```apache
redis 127.0.0.1:6379> lpush redis.net.cn redis
(integer) 1
redis 127.0.0.1:6379> lpush redis.net.cn mongodb
(integer) 2
redis 127.0.0.1:6379> lpush redis.net.cn rabitmq
(integer) 3
redis 127.0.0.1:6379> lrange redis.net.cn 0 10
1) "rabitmq"
2) "mongodb"
3) "redis"
redis 127.0.0.1:6379>
```

列表最多可存储 2^32 - 1^ 元素 (4294967295, 每个列表可存储40多亿)。


| 序号 | 命令及描述                                                                                                                                                                                                 |
| ---- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1    | [BLPOP key1 [key2 ] timeout](https://www.redis.net.cn/order/3577.html) 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时（timeout单位为秒）或发现可弹出元素为止。                           |
| 2    | [BRPOP key1 [key2 ] timeout](https://www.redis.net.cn/order/3578.html) 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。                                            |
| 3    | [BRPOPLPUSH source destination timeout](https://www.redis.net.cn/order/3579.html) 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 |
| 4    | [LINDEX key index](https://www.redis.net.cn/order/3580.html) 通过索引获取列表中的元素                                                                                                                         |
| 5    | [LINSERT key BEFORE                                                                                                                                                                                        |
| 6    | [LLEN key](https://www.redis.net.cn/order/3582.html) 获取列表长度                                                                                                                                             |
| 7    | [LPOP key](https://www.redis.net.cn/order/3583.html) 移出并获取列表的第一个元素                                                                                                                               |
| 8    | [LPUSH key value1 [value2]](https://www.redis.net.cn/order/3584.html) 将一个或多个值插入到列表头部                                                                                                            |
| 9    | [LPUSHX key value](https://www.redis.net.cn/order/3585.html) 将一个或多个值插入到已存在的列表头部                                                                                                             |
| 10   | [LRANGE key start stop](https://www.redis.net.cn/order/3586.html) 获取列表指定范围内的元素                                                                                                                    |
| 11   | [LREM key count value](https://www.redis.net.cn/order/3587.html) 移除列表元素                                                                                                                                 |
| 12   | [LSET key index value](https://www.redis.net.cn/order/3588.html) 通过索引设置列表元素的值                                                                                                                     |
| 13   | [LTRIM key start stop](https://www.redis.net.cn/order/3589.html) 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。                                           |
| 14   | [RPOP key](https://www.redis.net.cn/order/3590.html) 移除并获取列表最后一个元素                                                                                                                               |
| 15   | [RPOPLPUSH source destination](https://www.redis.net.cn/order/3591.html) 移除列表的最后一个元素，并将该元素添加到另一个列表并返回                                                                             |
| 16   | [RPUSH key value1 [value2]](https://www.redis.net.cn/order/3592.html) 在列表中添加一个或多个值                                                                                                                |
| 17   | [RPUSHX key value](https://www.redis.net.cn/order/3593.html) 为已存在的列表添加值                                                                                                                             |

## Set

Redis的Set是string类型的无序集合。

集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。

### sadd 命令

添加一个string元素到,key对应的set集合中，成功返回1,如果元素以及在集合中返回0,key对应的set不存在返回错误。
