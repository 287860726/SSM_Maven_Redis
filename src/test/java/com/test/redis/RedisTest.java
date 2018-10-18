package com.test.redis;
import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * @ahthor zhaocm
 * @date 2018年8月6日
 * @time 下午4:15:13
 */

/**
 * @author zhaocm
 * @data 2018年8月6日
 * @time 下午4:15:13
 */
public class RedisTest {

	public static void main(String[] args) throws Exception {
		// 连接Redis服务器
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		// 查看服务器是否正常运行
		System.out.println("服务器正常运行" + jedis.ping());

		System.out.println(jedis.exists("k1"));
		System.out.println(jedis.exists("k2"));

		Set<String> set = jedis.keys("*");
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		// String类型的相关操作
		jedis.set("k1", "v1");
		System.out.println("k1的值----" + jedis.get("k1"));

		System.out.println("k2自增的值----" + jedis.incr("k2"));
		System.out.println("k2自减的值----" + jedis.decr("k2"));

		jedis.setex("k3", 3, "v3"); // 设置K3三秒
		System.out.println("三秒前的K3值---" + jedis.get("k3"));
		Thread.sleep(4000);// 线程休眠4
		System.out.println("休眠四秒后的K3值----" + jedis.get("k3"));

		jedis.setnx("K4", "V4");
		System.out.println("setnx k4 的值----" + jedis.get("K4"));

		System.out.println("getset k5 的值----" + jedis.getSet("k5", "v5"));

		// HASH操作
		System.out.println("设置hash的key值");
	}
}
