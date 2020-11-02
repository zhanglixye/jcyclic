package com.kaiwait.core.utils;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	//服务器IP地址
    private static String ADDR = "aec-j-cyclic.2g3uxh.ng.0001.cnn1.cache.amazonaws.com.cn";
    //端口
    private static int PORT = 6379;
	
    public static Jedis getJedis()
    {
    	try {
    		Jedis jedis = new Jedis(ADDR, PORT, 100000);
        	return jedis;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
    }
}
