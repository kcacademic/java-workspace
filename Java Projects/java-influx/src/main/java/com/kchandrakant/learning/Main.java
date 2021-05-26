package com.kchandrakant.learning;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import com.kchandrakant.learning.domain.MemoryPoint;

public class Main {

	public static void main(String[] args) {

		InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "admin", "admin");

		Pong response = influxDB.ping();
		if (response.getVersion().equalsIgnoreCase("unknown")) {
			System.out.println("Error pinging server.");
		}

		influxDB.createDatabase("mydb");
		influxDB.createRetentionPolicy("defaultPolicy", "mydb", "30d", 1, true);

		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);

		Point point = Point.measurement("memory").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("name", "server1").addField("free", 4743656L).addField("used", 1015096L)
				.addField("buffer", 1010467L).build();

		BatchPoints batchPoints = BatchPoints.database("mydb").retentionPolicy("defaultPolicy").build();

		batchPoints.point(point);
		influxDB.write(batchPoints);

		Query queryObject = new Query("Select * from memory order by time desc", "mydb");
		QueryResult queryResult = influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<MemoryPoint> memoryPointList = resultMapper.toPOJO(queryResult, MemoryPoint.class);

		System.out.println(memoryPointList.size());
		System.out.println(memoryPointList.get(0).getFree());
	}
}