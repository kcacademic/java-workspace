package com.kchandrakant.learning;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.kchandrakant.learning.client.CarClient;
import com.kchandrakant.learning.domain.Car;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8081/")
				.addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();

		CarClient service = retrofit.create(CarClient.class);

		Call<List<Car>> callSync = service.findAll();

		try {
			Response<List<Car>> response = callSync.execute();
			List<Car> cars = response.body();
			System.out.println(cars);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Call<List<Car>> callAsync = service.findAll();

		callAsync.enqueue(new Callback<List<Car>>() {
			@Override
			public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
				List<Car> cars = response.body();
				System.out.println(cars);
			}

			@Override
			public void onFailure(Call<List<Car>> call, Throwable throwable) {
				System.out.println(throwable);
			}
		});
	}

}
