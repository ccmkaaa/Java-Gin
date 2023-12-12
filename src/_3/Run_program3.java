package _3;

import java.util.concurrent.Semaphore;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import _3.Threads.ThreadForFile.NamePrinter;

public class Run_program3 {
    public static void runProgram3() {
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(0);
        Semaphore semaphore3 = new Semaphore(0);

        String[] files = { System.getProperty("user.dir") + File.separator + "_3" + File.separator + "1t.txt",
                System.getProperty("user.dir") + File.separator + "_3" + File.separator + "2t.txt",
                System.getProperty("user.dir") + File.separator + "_3" + File.separator + "3t.txt" }; // "src/_3/1t.txt",
                                                                                                      // "src/_3/2t.txt",
        // "src/_3/3t.txt"

        Thread thread1 = new Thread(new NamePrinter(files[0], semaphore1, semaphore2));
        Thread thread2 = new Thread(new NamePrinter(files[1], semaphore2, semaphore3));
        Thread thread3 = new Thread(new NamePrinter(files[2], semaphore3, semaphore1));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}