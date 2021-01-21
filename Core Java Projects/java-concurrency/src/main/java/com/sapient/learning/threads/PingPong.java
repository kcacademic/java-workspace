package com.sapient.learning.threads;


public class PingPong {

	private static String state = "";

	public static void main(String[] args) {

		Thread t1 = new Thread(() -> {
			while (true) {
				synchronized (state) {
					System.out.println("Ping");
					state.notify();
					try {
						state.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t1.start();

		Thread t2 = new Thread(() -> {
			while (true) {
				synchronized (state) {
					System.out.println("Pong");
					state.notify();
					try {
						state.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});	
		t2.start();
	}
}
