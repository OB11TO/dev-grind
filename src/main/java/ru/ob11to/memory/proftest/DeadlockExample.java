package ru.ob11to.memory.proftest;

public class DeadlockExample {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK1) {
                System.out.println("Thread 1: Захватил LOCK1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK2) {
                    System.out.println("Thread 1: Захватил LOCK2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (LOCK2) {
                System.out.println("Thread 2: Захватил LOCK2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK1) {
                    System.out.println("Thread 2: Захватил LOCK1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
