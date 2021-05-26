package com.kchandrakant.learning.jsse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Principal;
import java.security.PrivilegedExceptionAction;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.kchandrakant.learning.jaas.Jaas;

@SuppressWarnings("rawtypes")
public class JsseClient {

	private static final String KRB5_CIPHER = "TLS_KRB5_WITH_3DES_EDE_CBC_SHA";

	private static final int PORT = 4569;
	@SuppressWarnings("unused")
	private static final boolean verbose = false;

	public static void main(String[] args) throws Exception {
		// Obtain the command-line arguments and parse the server's name

		if (args.length < 1) {
			System.err.println("Usage: java <options> JsseClient <serverName>");
			System.exit(-1);
		}

		PrivilegedExceptionAction action = new JsseClientAction(args[0], PORT);

		Jaas.loginAndAction("client", action);
	}

	static class JsseClientAction implements PrivilegedExceptionAction {
		private String server;
		private int port;

		JsseClientAction(String server, int port) {
			this.port = port;
			this.server = server;
		}

		public Object run() throws Exception {
			SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket sslSocket = (SSLSocket) sslsf.createSocket(server, port);

			// Enable only a KRB5 cipher suite.
			String enabledSuites[] = { KRB5_CIPHER };
			sslSocket.setEnabledCipherSuites(enabledSuites);
			// Should check for exception if enabledSuites is not supported

			BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream()));

			String outStr = "Hello There!\n";
			out.write(outStr);
			out.flush();
			System.out.print("Sending " + outStr);

			String inStr = in.readLine();
			System.out.println("Received " + inStr);

			String cipherSuiteChosen = sslSocket.getSession().getCipherSuite();
			System.out.println("Cipher suite in use: " + cipherSuiteChosen);
			Principal self = sslSocket.getSession().getLocalPrincipal();
			System.out.println("I am: " + self.toString());
			Principal peer = sslSocket.getSession().getPeerPrincipal();
			System.out.println("Server is: " + peer.toString());

			sslSocket.close();
			return null;
		}
	}
}