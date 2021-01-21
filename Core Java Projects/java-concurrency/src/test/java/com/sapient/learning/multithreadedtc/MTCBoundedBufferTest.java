package com.sapient.learning.multithreadedtc;

import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

@SuppressWarnings("deprecation")
public class MTCBoundedBufferTest extends MultithreadedTestCase {
	
    ArrayBlockingQueue<Integer> buf;
    
    @Override
    public void initialize() {
        buf = new ArrayBlockingQueue<Integer>(1);
    }

    public void thread1() throws InterruptedException {
        buf.put(42);
        buf.put(17);
        assertTick(1);
    }
    
	public void thread2() throws InterruptedException {
    	waitForTick(1);
        assertEquals(Integer.valueOf(42), buf.take());
        assertEquals(Integer.valueOf(17), buf.take());
    }

    @Override
    public void finish() {
        assertTrue(buf.isEmpty());
    }
    
    @Test
    public void testMTCBoundedBuffer() throws Throwable {
        TestFramework.runOnce( new MTCBoundedBufferTest() );
    }
}
