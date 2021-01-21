package com.sapient.learning.threads;


public class BadThreads {
	
	private static class Message{
		private String message;
		
		public synchronized String getMessage() {
			return this.message;
		}
		
		public synchronized void setMessage(String message) {
			this.message = message;
		}
	}

    static volatile String message;
    
    static Message message1 = new Message();

    private static class CorrectorThread
        extends Thread {

        public void run() {
            try {
                sleep(1000); 
            } catch (InterruptedException e) {}
            // Key statement 1:
            message = "Mares do eat oats."; 
            message1.setMessage("Mares do eat oats.");
        }
    }

    public static void main(String args[])
        throws InterruptedException {

    	Thread t = new CorrectorThread();
        t.start();
        message = "Mares do not eat oats.";
        message1.setMessage("Mares do not eat oats.");
        Thread.sleep(2000);
        //t.join();
        // Key statement 2:
        System.out.println(message);
        System.out.println(message1.getMessage());
    }
}