package com.kchandrakant.learning;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;
import com.kchandrakant.learning.model.Customer;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		Client client = new PreBuiltTransportClient(
				Settings.builder().put("client.transport.sniff", true).put("cluster.name", "elasticsearch").build())
						.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", 100)
				.field("firstName", "Kumar").field("lastName", "Chandrakant").endObject();
		IndexResponse indexResponse = client.prepareIndex("people", "customer").setSource(builder).get();
		String id = indexResponse.getId();
		System.out.println(id);
		
		GetResponse getResponse = client.prepareGet("people","customer",id).get();
		System.out.println(getResponse.getSourceAsString());
		
		DeleteResponse deleteResponse = client.prepareDelete("people", "customer", id).get();
		System.out.println(deleteResponse.getResult());

		SearchResponse searchResponse = client.prepareSearch("people").execute().actionGet();
		List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());
		List<Customer> results = new ArrayList<Customer>();
		searchHits.forEach(hit -> results.add(JSON.parseObject(hit.getSourceAsString(), Customer.class)));
		System.out.println(results);

		client.close();
	}

}