package com.kchandrakant.learning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

public class RedisTests {
	
	private static RedisServer redisServer;

	@BeforeAll
	public static void setUp() throws IOException {
		redisServer = new RedisServer(6379);
		redisServer.start();
	}

	@Test
	public void testData() {
		
		Jedis jedis = new Jedis();
		jedis.set("events/city/rome", "32,15,223,828");
		String cachedResponse = jedis.get("events/city/rome");
		assertEquals("32,15,223,828", cachedResponse);
		jedis.close();
	}
	
	@AfterAll
	public static void tearDown() {
		redisServer.stop();
	}

}
