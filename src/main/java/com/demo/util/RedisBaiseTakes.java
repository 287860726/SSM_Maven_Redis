/**
 * @ahthor zhaocm
 * @date 2018年8月3日
 * @time 上午9:43:08
 */
package com.demo.util;

import java.util.List;

import redis.clients.jedis.JedisPool;

/**
 * @author zhaocm
 * @data 2018年8月3日
 * @time 上午9:43:08 redis 工具类
 */
public interface RedisBaiseTakes<H, K, V> {
	// 增
	public void add(K key, String value);

	public void addObj(H objectKey, K key, V object);

	// 删
	public void delete(K key);

	public void delete(List<K> listKeys);

	public void deletObj(H objecyKey, K key);

	// 改
	public void update(K key, String value);

	public void updateObj(H objectKey, K key, V object);

	// 查
	public String get(K key);

	public V getObj(H objectKey, K key);
}
