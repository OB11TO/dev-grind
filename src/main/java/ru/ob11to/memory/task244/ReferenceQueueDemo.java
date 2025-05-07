package ru.ob11to.memory.task244;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class ReferenceQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        example3();
    }

    private static void example3() throws InterruptedException {
        Object object = new Object() {
            /**
             * @throws Throwable Лучше не вызывать этот метод
             */
            @Override
            protected void finalize() throws Throwable {
                System.out.println("Removed");
            }
        };
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        WeakReference<Object> weak = new WeakReference<>(object, queue);
        object = null;

        System.gc();

        TimeUnit.SECONDS.sleep(3);
        System.out.println("from link " + weak.get());
        System.out.println("from queue " + queue.poll());
    }
}
