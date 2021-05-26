package com.kchandrakant.learning;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.github.tomakehurst.wiremock.WireMockServer;

public class Main {

	public static void main(String[] args) throws IOException {

		WireMockServer wireMockServer = new WireMockServer();

		wireMockServer.start();

		configureFor("localhost", 8080);
		stubFor(get(urlEqualTo("/baeldung")).willReturn(aResponse().withBody("Welcome to Baeldung!")));

		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet request = new HttpGet("http://localhost:8080/baeldung");
		HttpResponse httpResponse = httpClient.execute(request);

		String stringResponse = convertResponseToString(httpResponse);
		
		verify(getRequestedFor(urlEqualTo("/baeldung")));
		assertEquals("Welcome to Baeldung!", stringResponse);

		wireMockServer.stop();

	}

	private static String convertResponseToString(HttpResponse response) throws IOException {
		InputStream responseStream = response.getEntity().getContent();
		Scanner scanner = new Scanner(responseStream, "UTF-8");
		String responseString = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return responseString;
	}

}
