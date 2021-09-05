package com.nsergey.validation.computer;

import com.vmlens.api.AllInterleavings;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class CachingComputerTest {

    @Test
    void simpleTest() throws ExecutionException, InterruptedException {
        Computer<Integer, Integer> computer = new CachingComputer<>();

        Integer key1 = Integer.valueOf(12345);
        Integer key2 = Integer.valueOf(12345);
        assertNotSame(key1, key2);

        Future<Integer> future1 = computer.compute(key1, k -> {
            return String.valueOf(k).length();
        });
        Future<Integer> future2 = computer.compute(key2, k -> {
            return String.valueOf(k).length() * 10;
        });

        assertSame(future1, future2);
        assertEquals(future1.get(), future2.get());

        System.out.println("Future1: " + future1.get());
        System.out.println("Future2: " + future2.get());
        sleep(2000L);
    }

    @Test
    void testWithVMlens() throws InterruptedException, ExecutionException {
        try (AllInterleavings allInterleavings = new AllInterleavings("TestCachingComputer_Not_Thread_Safe");) {
            Computer<Integer, Integer> computer = new CachingComputer<>();
            while (allInterleavings.hasNext()) {
                Integer key1 = Integer.valueOf(12345);
                Integer key2 = Integer.valueOf(12345);
                assertNotSame(key1, key2);

                Future<Integer> future1 = computer.compute(key1, k -> String.valueOf(k).length());
                Future<Integer> future2 = computer.compute(key2, k -> String.valueOf(k).length() * 10);

                assertSame(future1, future2);
                assertEquals(future1.get(), future2.get());

                System.out.println("Future1: " + future1.get());
                System.out.println("Future2: " + future2.get());
            }
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}