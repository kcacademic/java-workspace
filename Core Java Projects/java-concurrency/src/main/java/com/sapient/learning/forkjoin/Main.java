package com.sapient.learning.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class Main {
	
	private static Logger logger = Logger.getAnonymousLogger();

	public static void main(String[] args) {
		
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		
        CustomRecursiveAction myRecursiveAction = new CustomRecursiveAction("ddddffffgggghhhh");
        forkJoinPool.invoke(myRecursiveAction);
        logger.info("CustomRecursiveAction Result: " + myRecursiveAction.isDone());
		
        Random random = new Random();
        int[] arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(35);
        }
        CustomRecursiveTask customRecursiveTask = new CustomRecursiveTask(arr);
        forkJoinPool.execute(customRecursiveTask);
        int resultExecute = customRecursiveTask.join();
        logger.info("CustomRecursiveTask Result: " + customRecursiveTask.isDone() + " " + resultExecute);

        forkJoinPool.submit(customRecursiveTask);
        int resultSubmit = customRecursiveTask.join();
        logger.info("CustomRecursiveTask Result: " + customRecursiveTask.isDone() + " " + resultSubmit);

	}

}
