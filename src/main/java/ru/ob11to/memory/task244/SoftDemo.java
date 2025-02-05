package ru.ob11to.memory.task244;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SoftDemo {

    public static void main(String[] args) throws InterruptedException {
        example1();
//        example2();
    }

    /*
    В первом методе, несмотря на то, что мы за'null'уляем сильную ссылку, на объект остается безопасная ссылка,
     а это значит, что объект будет удален только при нехватке памяти.
     */
    private static void example1() throws InterruptedException {
        Object strong = new Object();
        SoftReference<Object> soft = new SoftReference<>(strong);
        strong = null;
        System.gc();
        TimeUnit.SECONDS.sleep(5);
        System.out.println(soft.get());
    }

    /*
    Во втором методе мы создаем много объектов, но на них ссылается безопасная ссылка. При создании большого
     количества объектов при малом heap мы увидим, что объекты начнут удаляться, т.к. перестанет хватать памяти.
     */
    private static void example2() {
        List<SoftReference<Object>> objects = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            objects.add(new SoftReference<>(new Object() {
                String value = String.valueOf(System.currentTimeMillis());

                @Override
                protected void finalize() throws Throwable {
                    System.out.println("Object removed!");
                }
            }));
        }
        System.gc();
        int liveObject = 0;
        for (SoftReference<Object> ref : objects) {
            Object object = ref.get();
            if (object != null) {
                liveObject++;
            }
        }
        System.out.println(liveObject);
    }

    @SuppressWarnings("checkstyle:EmptyBlock")
    private static void unsafe() {
        List<SoftReference<Object>> someData = new ArrayList<>();
        if (someData.get(0).get() != null) {
            /* do something */
        } else {
            /* do something */
        }
        /* do something */
        someData.get(0).get();
    }

    private static void safe() {
        List<SoftReference<Object>> someData = new ArrayList<>();
        Object strong = someData.get(0).get();
        if (strong != null) {
            /* do something */
        } else {
            /* do something */
        }
        /* work with strong */
    }
}