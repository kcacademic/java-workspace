package com.sapient.learning.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

	public static void main(String[] args) throws InterruptedException, TimeoutException {

		// Instantiating the ExecutorService

		ExecutorService executorServiceFromFactory = Executors.newFixedThreadPool(10);

		ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		// Assigning Runnable Tasks to ExecutorService

		Runnable runnableTask = () -> {
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		executorServiceFromFactory.execute(runnableTask);

		// Assigning Single Callable Tasks to ExecutorService

		Callable<String> callableTask = () -> {
			TimeUnit.MILLISECONDS.sleep(300);
			return "Task's execution";
		};

		Future<String> future = executorService.submit(callableTask);

		try {
			String result = null;
			result = future.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		// Assigning Multiple Callable Tasks to ExecutorService

		List<Callable<String>> callableTasks = new ArrayList<>();
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);

		List<Future<String>> futures = executorService.invokeAll(callableTasks);

		try {
			List<String> results = new ArrayList<>();
			for (Future<String> f : futures)
				results.add(f.get(200, TimeUnit.MILLISECONDS));
			for (String s : results)
				System.out.println(s);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		// The ScheduledExecutorService Interface

		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		Future<?> resultFutureScheduled = scheduledExecutorService.schedule(runnableTask, 1, TimeUnit.SECONDS);
		try {
			Object result = resultFutureScheduled.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		Future<?> resultFutureFixedRate = scheduledExecutorService.scheduleAtFixedRate(runnableTask, 100, 450,
				TimeUnit.MILLISECONDS);
		try {
			Object result = resultFutureFixedRate.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		Future<?> resultFutureFixedDelay = scheduledExecutorService.scheduleWithFixedDelay(runnableTask, 100, 150,
				TimeUnit.MILLISECONDS);
		try {
			Object result = resultFutureFixedDelay.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		// Shutting Down an ExecutorService

		executorServiceFromFactory.shutdown();
		scheduledExecutorService.shutdown();
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}

	}

}
