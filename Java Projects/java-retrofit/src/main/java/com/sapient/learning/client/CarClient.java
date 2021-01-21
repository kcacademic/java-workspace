package com.sapient.learning.client;

import java.util.List;

import com.sapient.learning.domain.Car;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarClient {
	
	@GET("/cars")
    public Call<List<Car>> findAll();
}
