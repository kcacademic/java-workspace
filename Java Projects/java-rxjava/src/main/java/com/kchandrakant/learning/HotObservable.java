package com.kchandrakant.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;

public class HotObservable {

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {

		Socket socket = new Socket("localhost", 4005);
		InputStreamReader ir = new InputStreamReader(socket.getInputStream());
		BufferedReader br = new BufferedReader(ir);

		ConnectableObservable<String> hotStream = Observable.fromCallable(() -> br.readLine().trim()).repeat()
				.publish();

		hotStream.filter(new Predicate<String>() {
			@Override
			public boolean test(String str) throws Exception {
				return str.toLowerCase().contains("india");
			}
		}).subscribe(s -> System.out.println("India Message: " + s), Throwable::printStackTrace,
				() -> System.out.println("Done"));

		hotStream.filter(new Predicate<String>() {
			@Override
			public boolean test(String str) throws Exception {
				return (!str.toLowerCase().contains("india"));
			}
		}).subscribe(s -> System.out.println("Non India Message: " + s), Throwable::printStackTrace,
				() -> System.out.println("Done"));

		hotStream.connect();

		socket.close();
		Thread.sleep(1000000000);

	}

}
