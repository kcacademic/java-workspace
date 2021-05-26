package com.kchandrakant.learning.jsse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Principal;
import java.security.PrivilegedExceptionAction;
import java.util.Date;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.kchandrakant.learning.jaas.Jaas;

/*
 * Tests support for RFC 2712. Specify use of only a KRB5 cipher for both
 * client and server, by first doing a JAAS login for the server 
 * without first doing a JAAS login for the client.
 */

@SuppressWarnings("rawtypes")
public class JsseServer {

	private static final String KRB5_CIPHER = "TLS_KRB5_WITH_3DES_EDE_CBC_SHA";

	private static final int PORT = 4569;
	@SuppressWarnings("unused")
	private static final boolean verbose = false;
	private static final int LOOP_LIMIT = 1;
	private static int loopCount = 0;

	public static void main(String[] args) throws Exception {

		PrivilegedExceptionAction action = new JsseServerAction(PORT);

		Jaas.loginAndAction("server", action);
	}

	static class JsseServerAction implements PrivilegedExceptionAction {
		private int localPort;

		JsseServerAction(int port) {
			this.localPort = port;
		}

		public Object run() throws Exception {

			SSLServerSocketFactory sslssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket sslServerSocket = (SSLServerSocket) sslssf.createServerSocket(localPort);

			// Enable only a KRB5 cipher suite.
			String enabledSuites[] = { KRB5_CIPHER };
			sslServerSocket.setEnabledCipherSuites(enabledSuites);
			// Should check for exception if enabledSuites is not supported

			while (loopCount++ < LOOP_LIMIT) {
				System.out.println("Waiting for incoming connection...");

				SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

				System.out.println("Got connection from client " + sslSocket.getInetAddress());

				BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream()));

				String inStr = in.readLine();
				System.out.println("Received " + inStr);

				String outStr = inStr + " " + new Date().toString() + "\n";
				out.write(outStr);
				System.out.println("Sending " + outStr);
				out.flush();

				String cipherSuiteChosen = sslSocket.getSession().getCipherSuite();
				System.out.println("Cipher suite in use: " + cipherSuiteChosen);
				Principal self = sslSocket.getSession().getLocalPrincipal();
				System.out.println("I am: " + self.toString());
				Principal peer = sslSocket.getSession().getPeerPrincipal();
				System.out.println("Client is: " + peer.toString());

				sslSocket.close();
			}
			return null;
		}
	}
}
