package com.kchandrakant.learning;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.bind.JAXB;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kchandrakant.learning.model.Order;

public class ApplicationTests {

	private static final String BASE_URL = "http://localhost:8080/myshop/customers/";
	private static CloseableHttpClient client;

	@BeforeClass
	public static void createClient() {
		client = HttpClients.createDefault();
	}

	@Test
	public void whenCreateValidOrder_thenReceiveOKResponse() throws IOException {
		final HttpPost httpPost = new HttpPost(BASE_URL + "2/orders");
		final InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("order.xml");
		httpPost.setEntity(new InputStreamEntity(resourceStream));
		httpPost.setHeader("Content-Type", "text/xml");

		final HttpResponse response = client.execute(httpPost);
		assertEquals(200, response.getStatusLine().getStatusCode());

		final Order order = getOrder(2, 3);
		assertEquals(3, order.getId());
		assertEquals("Order C", order.getName());
	}

	private Order getOrder(int customerId, int orderId) throws IOException {
		final URL url = new URL(BASE_URL + customerId + "/orders/" + orderId);
		final InputStream input = url.openStream();
		return JAXB.unmarshal(new InputStreamReader(input), Order.class);
	}

	@AfterClass
	public static void closeClient() throws IOException {
		client.close();
	}

}
