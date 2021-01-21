package com.sapient.learning.sasl;

import java.io.*;
import java.net.Socket;

class AppConnection {
    public static final int AUTH_CMD = 100;
    public static final int DATA_CMD = 200;

    public static final int SUCCESS = 0;
    public static final int AUTH_INPROGRESS = 1;
    public static final int FAILURE = 2;

    private DataInputStream inStream;
    private DataOutputStream outStream;
    private Socket socket;

    // Client application
    AppConnection(String hostName, int port) throws IOException {
	socket = new Socket(hostName, port);

	inStream = new DataInputStream(socket.getInputStream());
	outStream = new DataOutputStream(socket.getOutputStream());

	System.out.println("Connected to address " +
	    socket.getInetAddress());
    }

    // Server side application
    AppConnection(Socket socket) throws IOException {
	this.socket = socket;
	inStream = new DataInputStream(socket.getInputStream());
	outStream = new DataOutputStream(socket.getOutputStream());

	System.out.println("Got connection from client " +
	    socket.getInetAddress());
    }

    byte[] receive(int expected) throws IOException {
	if (expected != -1) {
	    int cmd = inStream.readInt();
	    if (expected != cmd) {
		throw new IOException("Received unexpected code: " + cmd);
	    }
	    //System.out.println("Read cmd: " + cmd);
	}

	byte[] reply = null;
	int len;
	try {
	    len = inStream.readInt();
	    //System.out.println("Read length: " + len);

	} catch (IOException e) {
	    len = 0;
	}
	if (len > 0) {
	    reply = new byte[len];
	    inStream.readFully(reply);
	} else {
	    reply = new byte[0];
	}
	return reply;
    }

    AppReply send(int cmd, byte[] bytes) throws IOException {
	//System.out.println("Write cmd: " + cmd);
	outStream.writeInt(cmd);
	if (bytes != null) {
	    //System.out.println("Write length: " + bytes.length);
	    outStream.writeInt(bytes.length);
	    if (bytes.length > 0) {
		outStream.write(bytes);
	    }
	} else {
	    //System.out.println("Write length: " + 0);
	    outStream.writeInt(0);
	}

	outStream.flush();

	if (cmd == SUCCESS || cmd == FAILURE) {
	    return null;   // Done
	}

	int returnCode = inStream.readInt();
	//System.out.println("Read cmd: " + returnCode);

	byte[] reply = null;
	if (returnCode != FAILURE) {
	    reply = receive(-1);
	}
	return new AppReply(returnCode, reply);
    }

    static class AppReply {
	private int code;
	private byte[] bytes;

	AppReply(int code, byte[] bytes) {
	    this.bytes = bytes;
	    this.code = code;
	}
	
	int getStatus() {
	    return code;
	}

	byte[] getBytes() {
	    return bytes;
	}
    }

    void close() {
	try {
	    socket.close();
	} catch (IOException e) {
	}
    }
}

    

    
