package com.sapient.learning;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;

import com.sapient.learning.handlers.ClientCallbackHandler;
import com.sapient.learning.handlers.ServerCallbackHandler;

public class Main {

	private static final String MECHANISM = "DIGEST-MD5";
	private static final String SERVER_NAME = "myServer";
	private static final String PROTOCOL = "myProtocol";
	private static final String AUTHORIZATION_ID = null;
	private static final String QOP_LEVEL = "auth-conf";

	public static void main(String[] args) throws SaslException {

		ServerCallbackHandler serverHandler = new ServerCallbackHandler();
		ClientCallbackHandler clientHandler = new ClientCallbackHandler();

		Map<String, String> props = new HashMap<>();
		props.put(Sasl.QOP, QOP_LEVEL);

		SaslServer saslServer = Sasl.createSaslServer(MECHANISM, PROTOCOL, SERVER_NAME, props, serverHandler);
		SaslClient saslClient = Sasl.createSaslClient(new String[] { MECHANISM }, AUTHORIZATION_ID, PROTOCOL,
				SERVER_NAME, props, clientHandler);

		byte[] challenge;
		byte[] response;

		challenge = saslServer.evaluateResponse(new byte[0]);
		response = saslClient.evaluateChallenge(challenge);

		challenge = saslServer.evaluateResponse(response);
		response = saslClient.evaluateChallenge(challenge);

		System.out.println(saslServer.isComplete());
		System.out.println(saslClient.isComplete());

		String qop = (String) saslClient.getNegotiatedProperty(Sasl.QOP);
		System.out.println(qop);

		byte[] outgoing = "Kumar Chandrakant".getBytes();
		byte[] secureOutgoing = saslClient.wrap(outgoing, 0, outgoing.length);

		byte[] secureIncoming = secureOutgoing;
		byte[] incoming = saslServer.unwrap(secureIncoming, 0, secureIncoming.length);
		System.out.println(new String(incoming, StandardCharsets.UTF_8));
	}

}