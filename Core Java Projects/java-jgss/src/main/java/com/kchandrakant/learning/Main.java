package com.kchandrakant.learning;

import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.MessageProp;
import org.ietf.jgss.Oid;

public class Main {

	public static void main(String[] args) throws GSSException {

		GSSContext serverContext = serverContext();
		GSSContext clientContext = clientContext();
		byte[] serverToken;
		byte[] clientToken;

		// On the client-side
		clientToken = clientContext.initSecContext(new byte[0], 0, 0);
		//sendToServer(clientToken);
		
		// On the server-side
		serverToken = serverContext.acceptSecContext(clientToken, 0, clientToken.length);
		//sendToClient(serverToken);
		
		// Back on the client side
		clientContext.initSecContext(serverToken, 0, serverToken.length);

		System.out.println("Server Auth State: " + serverContext.isEstablished());
		System.out.println("Client Auth State: " + clientContext.isEstablished());

		System.out.println("Server Mutual Auth: " + serverContext.getMutualAuthState());
		System.out.println("Client Mutual Auth: " + clientContext.getMutualAuthState());

		// On the client-side
		byte[] messageBytes = "Hello There!\0".getBytes();
		MessageProp clientProp = new MessageProp(0, true);
		clientToken = clientContext.wrap(messageBytes, 0, messageBytes.length, clientProp);
		System.out.println("Will send wrap token of size " + clientToken.length);
		//sendToServer(clientToken);
		
		// On the server-side
		MessageProp serverProp = new MessageProp(0, false);
		byte[] bytes = serverContext.unwrap(clientToken, 0, clientToken.length, serverProp);
		String str = new String(bytes);
		System.out.println("Received data \"" + str + "\" of length " + str.length());

		System.out.println("Server Confidentiality: " + serverProp.getPrivacy());
		System.out.println("Client Confidentiality: " + clientProp.getPrivacy());

		// On the server-side
		serverProp.setQOP(0);
		serverToken = serverContext.getMIC(bytes, 0, bytes.length, serverProp);
		System.out.println("Will send MIC token of size " + serverToken.length);
		//sendToClient(serverToken);
		
		// On the client-side
		clientContext.verifyMIC(serverToken, 0, serverToken.length, messageBytes, 0, messageBytes.length, clientProp);
		System.out.println("Verified received MIC for message.");

	}

	private static GSSContext serverContext() throws GSSException {

		GSSManager manager = GSSManager.getInstance();

		GSSContext context = manager.createContext((GSSCredential) null);

		return context;
	}

	private static GSSContext clientContext() throws GSSException {

		GSSManager manager = GSSManager.getInstance();

		String serverPrinciple = "HTTP/localhost@EXAMPLE.COM";

		GSSName serverName = manager.createName(serverPrinciple, null);

		Oid krb5Oid = new Oid("1.2.840.113554.1.2.2");

		GSSContext context = manager.createContext(serverName, krb5Oid, (GSSCredential)null, GSSContext.DEFAULT_LIFETIME);
		context.requestMutualAuth(true); // Mutual authentication
		context.requestConf(true); // Will use encryption later
		context.requestInteg(true); // Will use integrity later

		return context;

	}

}