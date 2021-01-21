package com.sapient.learning;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class MainTests {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8080);

	@Before
	public void setup() {

		stubFor(get(urlPathMatching("/baeldung/.*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "application/json").withBody("\"testing-library\": \"WireMock\"")));

	}

	@Test
	public void testData() throws ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost:8080/baeldung/wiremock");
		HttpResponse httpResponse = httpClient.execute(request);
		String stringResponse = convertHttpResponseToString(httpResponse);

		verify(getRequestedFor(urlEqualTo("/baeldung/wiremock")));
		assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		assertEquals("application/json", httpResponse.getFirstHeader("Content-Type").getValue());
		assertEquals("\"testing-library\": \"WireMock\"", stringResponse);

	}

	private String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
		InputStream inputStream = httpResponse.getEntity().getContent();
		return convertInputStreamToString(inputStream);
	}

	private String convertInputStreamToString(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream, "UTF-8");
		String string = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return string;
	}

}
