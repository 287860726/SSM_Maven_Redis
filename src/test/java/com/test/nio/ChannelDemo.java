package com.test.nio;

import java.util.HashMap;
import java.util.Map;

public class ChannelDemo {
	public static void main(String args[]) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		for(String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
		}
		System.out.println("map中key1的值为:" + map.get("key1"));
		System.out.println("map中移除key1的值后:" + map.remove("key1") + map.get("key1"));
		map.clear();
		System.out.println("清空map后:" + map.get("key2"));
	}
}