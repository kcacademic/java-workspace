package com.sapient.learning;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Main {

	public static void main(String[] args) {
		
		// Using Redisson Client
		Config config = new Config();
		config.useSingleServer().setAddress("redis://127.0.0.1:6379");
		RedissonClient redisson = Redisson.create(config);
		

        RBucket<String> bucket = redisson.getBucket("simpleObject");
        bucket.set("This is object value");
        RMap<String, String> map = redisson.getMap("simpleMap");
        map.put("mapKey", "This is map value");
        String objectValue = bucket.get();
        System.out.println("stored object value: " + objectValue);
        String mapValue = map.get("mapKey");
        System.out.println("stored map value: " + mapValue);
        
        redisson.shutdown();

	}

}