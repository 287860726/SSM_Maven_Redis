package com.test.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.util.SharedByteArrayInputStream;
import javax.swing.JWindow;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;

/**
 * @author zhaocm
 * @data 2018年8月7日
 * @time 上午10:53:16
 */
public class RedisClient {
	private Jedis jedis;// 非切片客户端连接
	private JedisPool jedisPool; // 非切片连接池
	private ShardedJedis shardedJedis; // 切片客户端连接
	private ShardedJedisPool shardedJedisPool; // 切片连接池

	public RedisClient() {
		initalPool();
		initalShardedPool();
		jedis = jedisPool.getResource();
		shardedJedis = shardedJedisPool.getResource();
	}

	/*
	 * 初始化非切片池
	 */
	private void initalPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 最大空闲连接数
		config.setMaxIdle(8);
		// 获取连接时的最大等待毫秒数
		config.setMaxWaitMillis(10001);
		// 最大连接数
		config.setMaxTotal(20);
		// 在获取连接的时候检查有效性，默认false
		config.setTestOnBorrow(false);

		jedisPool = new JedisPool(config, "127.0.0.1", 6379);
	}

	/*
	 * 初始化切片池
	 */
	private void initalShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 最大空闲连接数
		config.setMaxIdle(8);
		// 获取连接时的最大等待毫秒数
		config.setMaxWaitMillis(10001);
		// 最大连接数
		config.setMaxTotal(20);
		// 在获取连接的时候检查有效性，默认false
		config.setTestOnBorrow(false);
		// slave连接,
		// 也就是我们所说的主从复制，主机数据更新后根据配置和策略，自动同步到备机的master/slaver机制，
		// Master以写为主，Slave以读为主。
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	public void show() {
		KeyOperate();
		StringOperate();
		ListOperate();
		SetOperate();
		SortedSetOperate();
		HashOperate();
		jedisPool.close();
		shardedJedisPool.close();
	}

	/*
	 * KEY功能
	 */
	private void KeyOperate() {
		System.out.println("---------------KEY功能----------------");
		// 清空数据
		System.out.println("清空库中所有数据：" + jedis.flushDB());
		// 判断key是否存在
		System.out.println("判断key999键是否存在：" + shardedJedis.exists("key999"));
		System.out.println("新增key001，value001键值对：" + shardedJedis.set("key001", "value001"));
		System.out.println("判断key001是否存在：" + shardedJedis.exists("key001"));
		// 输出系统中所有的key
		System.out.println("新增key002，value002键值对：" + shardedJedis.set("key002", "value002"));
		System.out.println("系统中所有的键如下：");
		Set<String> keys = jedis.keys("*");
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		// 删除key
		System.out.println("判断key002键是否存在：" + shardedJedis.exists("key002"));
		System.out.println("删除key002：" + shardedJedis.del("key002"));
		System.out.println("判断key002键是否存在：" + shardedJedis.exists("key002"));
		// 设置key001过期时间
		System.out.println("设置k001过期时间为5秒：" + shardedJedis.expire("key001", 5));
		System.out.println("k001的值：" + shardedJedis.get("key001"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("查看k001的剩余生存时间：" + shardedJedis.ttl("key001"));
		// 移除某个key的生存时间
		System.out.println("移除key001的生存时间：" + shardedJedis.persist("key001"));
		System.out.println("查看key001的剩余生存时间：" + shardedJedis.ttl("key001"));
		// 查看key所存储的数据类型
		System.out.println("查看key001所存储的数据类型：" + shardedJedis.type("key001"));

	}

	private void StringOperate() {
		System.out.println("---------------String功能----------------");
		// 清空数据
		System.out.println("清空库中所有数据：" + jedis.flushDB());

		System.out.println("===============增加===============");
		jedis.set("k001", "v001");
		jedis.set("k002", "v002");
		jedis.set("k003", "v003");
		jedis.set("k004", "v004");
		jedis.set("k005", "v005");
		System.out.println("已经新增5个键值对如下");
		for (int i = 1; i < 6; i++) {
			System.out.println(jedis.get("k00" + i));
		}

		System.out.println("===============删除===============");
		System.out.println("删除k003键值对：" + jedis.del("k003"));
		System.out.println("获取k003键的值：" + jedis.get("k003"));

		System.out.println("===============修改===============");
		// 直接覆盖原来得数据
		System.out.println("直接覆盖k001原来得数据：" + jedis.set("k001", "value001-update"));
		System.out.println("获取k001对应的 新值：" + jedis.get("k001"));
		// 直接覆盖原来得数据
		System.out.println("在k002原来得值后面追加：" + jedis.append("k002", "+appendString"));
		System.out.println("k002追加后的值为：" + jedis.get("k002"));

		System.out.println("===============增、删、查（多个）===============");
		/**
		 * mset、mget同时新增、修改、查询多个键值对 等价于： jedis.set("name","sss")
		 * jedis.set("age","45")
		 */
		System.out.println("一次性新增k201,k202,k203,k204及其对应的值："
				+ jedis.mset("k201", "v201", "k202", "v202", "k203", "v203", "k204", "v204"));
		System.out.println("一次性获取k201,k202,k203,k204的各自对应的值：" + jedis.mget("k201", "k202", "k203", "k204"));
		System.out.println("一次性删除k201,k202：" + jedis.del(new String[] { "k201", "k202" }));
		System.out.println("一次性获取k201,k202,k203,k204的各自对应的值：" + jedis.mget("k201", "k202", "k203", "k204"));

		// jedis具备的功能sharedJedis中也可以直接使用，下面测试一些前面没有用过的方法
		System.out.println("===============sharedJedis操作===============");
		// 清空数据
		System.out.println("清空库中所有数据：" + jedis.flushDB());
		System.out.println("===============新增键值对时防止覆盖原来得值===============");
		System.out.println("原来k001不存在的时候添加k001:" + shardedJedis.setnx("k001", "v001"));
		System.out.println("原来k002不存在的时候添加k002:" + shardedJedis.setnx("k002", "v002"));
		System.out.println("原来k002不存在的时候添加k002:" + shardedJedis.setnx("k002", "v00222222"));
		System.out.println("获取k001的值：" + shardedJedis.get("k001"));
		System.out.println("获取k002的值：" + shardedJedis.get("k002"));

		System.out.println("===============超过有效期的键值对被删除===============");
		// 设置key的有效期，并存储数据
		System.out.println("新增k003,并指定过期时间为2秒：" + shardedJedis.setex("k003", 2, "v003"));
		System.out.println("获取k003的值：" + shardedJedis.get("k003"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("3秒之后，获取k003的值：" + shardedJedis.get("k003"));

		System.out.println("===============获取原值，更新为新值一步完成===============");
		System.out.println("k002原来的值：" + shardedJedis.getSet("k002", "v002_after_getset"));
		System.out.println("k002新值：" + shardedJedis.get("k002"));

		System.out.println("===============获取子串===============");
		System.out.println("获取k002的子串：" + shardedJedis.getrange("k002", 5, 7));
	}

	private void ListOperate() {
		System.out.println("---------------List功能----------------");
		// 清空数据
		System.out.println("清空库中所有数据" + jedis.flushDB());

		System.out.println("==========增==========");
		shardedJedis.lpush("stringlist", "vector");
		shardedJedis.lpush("stringlist", "ArrayList");
		shardedJedis.lpush("stringlist", "vector");
		shardedJedis.lpush("stringlist", "vector");
		shardedJedis.lpush("stringlist", "LinkedList");
		shardedJedis.lpush("stringlist", "MapList");
		shardedJedis.lpush("stringlist", "SerialList");
		shardedJedis.lpush("stringlist", "HashList");

		shardedJedis.lpush("numlist", "3");
		shardedJedis.lpush("numlist", "2");
		shardedJedis.lpush("numlist", "4");
		shardedJedis.lpush("numlist", "5");

		System.out.println("stringlist所有元素：" + shardedJedis.lrange("stringlist", 0, -1));
		System.out.println("numlist所有元素：" + shardedJedis.lrange("numlist", 0, -1));

		System.out.println("========删========");
		// 删除列表指定的值，第二个参数为删除的个数（有重复时），后add进去的值先被删除，类似于出栈
		System.out.println("stringlist成功删除指定元素个数" + shardedJedis.lrem("stringlist", 2, "vector"));
		System.out.println("stringlist删除指定元素后" + shardedJedis.lrange("stringlist", 0, -1));

		// 删除区间之外的数据
		System.out.println("stringlist删除下标0-3区间之外的元素：" + shardedJedis.ltrim("stringlist", 0, 3));
		System.out.println("stringlist删除下标0-3区间之外的元素后:" + shardedJedis.lrange("stringlist", 0, -1));

		// 列表元素出栈
		System.out.println("stirnglist列表元素出站：" + shardedJedis.lpop("stringlist"));
		System.out.println("stringlist元素出栈后：" + shardedJedis.lrange("stringlist", 0, -1));

		System.out.println("=======修改=======");
		// 修改列表中指定下标的值
		shardedJedis.lset("stringlist", 0, "Hello World!");
		System.out.println("stringlist下标为0 的值修改之后：" + shardedJedis.lrange("stringlist", 0, -1));

		System.out.println("=======查=======");
		System.out.println("stringlist长度:" + shardedJedis.llen("stringlist"));
		System.out.println("numlist长度:" + shardedJedis.llen("numlist"));

		// 排序
		/**
		 * list中存字符串时必须指定参数为alpha，如果不使用SortingParams，而是直接使用sort("list"), 会出现"ERR
		 * One or more scores can't be converted into double"
		 */
		SortingParams sortingParams = new SortingParams();
		sortingParams.alpha();
		sortingParams.limit(0, 3);
		System.out.println("stringlist返回排序后的结果：" + shardedJedis.sort("stringlist", sortingParams));
		System.out.println("numlist返回排序后的结果：" + shardedJedis.sort("numlist"));
		// 子串：start为元素下标，end也为元素下标，-1代表倒数第一个元素，-2代表倒数第二个元素
		System.out.println("子串——第二个元素开始到结束：" + shardedJedis.lrange("stringlist", 1, -1));
		// 获取列表指定小标的值
		System.out.println("获取小标为2的元素：" + shardedJedis.lindex("stringlist", 2) + "\n");
	}

	private void SetOperate() {
		System.out.println("---------------Set功能----------------");
		// 清空数据
		System.out.println("清苦库中所有数据：" + jedis.flushDB());

		System.out.println("=======新增=======");
		System.out.println("向sets集合中加入元素element001:" + jedis.sadd("sets", "element001"));
		System.out.println("向sets集合中加入元素element002:" + jedis.sadd("sets", "element002"));
		System.out.println("向sets集合中加入元素element003:" + jedis.sadd("sets", "element003"));
		System.out.println("向sets集合中加入元素element004:" + jedis.sadd("sets", "element004"));
		System.out.println("查看sets集合中的所有元素：" + jedis.smembers("sets") + "\n");

		System.out.println("=======删除=======");
		System.out.println("集合中删除element002:" + jedis.srem("sets", "element002"));
		System.out.println("查看sets集合中的所有元素：" + jedis.smembers("sets"));

		System.out.println("=======查======");
		System.out.println("判断element001是否在集合sets中：" + jedis.sismember("sets", "element001"));
		System.out.println("循环查询获取sets中的每个元素：");
		Set<String> sets = jedis.smembers("sets");
		Iterator iterator = sets.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		System.out.println("======集合运算======");
		System.out.println("sets1中添加元素element001：" + jedis.sadd("sets1", "element001"));
		System.out.println("sets1中添加元素element002：" + jedis.sadd("sets1", "element002"));
		System.out.println("sets1中添加元素element003：" + jedis.sadd("sets1", "element003"));

		System.out.println("sets2中添加元素element002：" + jedis.sadd("sets2", "element002"));
		System.out.println("sets2中添加元素element003：" + jedis.sadd("sets2", "element003"));
		System.out.println("sets2中添加元素element004：" + jedis.sadd("sets2", "element004"));

		System.out.println("查看sets1集合中所有的元素：" + jedis.smembers("sets1"));
		System.out.println("查看sets2集合中所有的元素：" + jedis.smembers("sets2"));
		System.out.println("sets1和sets2交集：" + jedis.sinter("sets1", "sets2"));
		System.out.println("sets1和sets2并集：" + jedis.sunion("sets1", "sets2"));
		// 差集：sets1中有，sets2中没有的元素
		System.out.println("sets1和sets2差集：" + jedis.sdiff("sets1", "sets2"));

	}

	private void SortedSetOperate() {
		System.out.println("---------------SortedSet(有序集合)功能----------------");
		// 清空数据
		jedis.flushDB();

		System.out.println("======新增=====");
		// zset中添加元素
		jedis.zadd("zset", 7.0, "element001");
		jedis.zadd("zset", 8.0, "element002");
		jedis.zadd("zset", 5.0, "element003");
		jedis.zadd("zset", 3.0, "element004");
		jedis.zadd("zset", 2.0, "element005");
		jedis.zadd("zset", 1.0, "element006");
		jedis.zadd("zset", 4.0, "element007");
		jedis.zadd("zset", 6.0, "element008");
		jedis.zadd("zset", 9.0, "element009");
		// 按照权重值排序
		System.out.println("zset集合中所有元素：" + jedis.zrange("zset", 0, -1));
		System.out.println();

		System.err.println("======删除=====");
		System.out.println("zset中删除元素element002:" + jedis.zrem("zset", "element002"));
		System.out.println("zset集合中所有元素：" + jedis.zrange("zset", 0, -1));

		System.out.println("=======查=======");
		System.out.println("统计zset集合中的元素个数：" + jedis.zcard("zset"));
		System.out.println("统计zset集合中权重在某个范围内（1.0-5.0），元素的个数：" + jedis.zcount("zset", 1.0, 5.0));
		System.out.println("查看zset集合中element004的权重：" + jedis.zscore("zset", "element004"));
		System.out.println("查看下标1到2范围内的元素值：" + jedis.zrange("zset", 1, 2));
	}

	private void HashOperate() {
		System.out.println("---------------Hash功能----------------");
		System.out.println("清空库中的数据：" + jedis.flushDB());
		System.out.println("======新增======");
		System.out.println("hashs中添加h-k001和h-v001键值对：" + jedis.hset("hashs", "h-k001", "h-v001"));
		System.out.println("hashs中添加h-k002和h-v002键值对：" + jedis.hset("hashs", "h-k002", "h-v002"));
		System.out.println("hashs中添加h-k003和h-v003键值对：" + jedis.hset("hashs", "h-k003", "h-v003"));
		System.out.println("hashs中添加h-k004和4的整型键值对：" + jedis.hincrBy("hashs", "h-k004", 4));
		System.out.println("设置hashs过期时间为5秒" + jedis.expire("hashs", 5));
		System.out.println("hashs中的所有值：" + jedis.hvals("hashs"));
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("6秒后hashs中的所有值：" + jedis.hvals("hashs"));
		System.out.println();

		System.out.println("======删除=====");
		System.out.println("hashs中删除h-k002键值对：" + jedis.hdel("hashs", "h-k002"));
		System.out.println("hashs中所有的键值对： " + jedis.hvals("hashs"));

		System.out.println("=======修改=======");
		System.out.println("h-k004键值对的值增加100;" + jedis.hincrBy("hashs", "h-k004", 100));
		System.out.println("hashs中所有值：" + jedis.hvals("hashs"));

		System.out.println("========查看=======");
		System.out.println("判断h-k003是否存在：" + jedis.hexists("hashs", "h-k003"));
		System.out.println("获取h-k004对应的值：" + jedis.hget("hashs", "h-k004"));
		System.out.println("获取h-k001和h-k003对应的值：" + jedis.hmget("hashs", "h-k001", "h-k003"));
		System.out.println("获取hashs中所有的key：" + jedis.hkeys("hashs"));
		System.out.println("获取hashs中所有的values：" + jedis.hvals("hashs"));

	}
}
