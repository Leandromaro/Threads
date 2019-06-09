package SecondPart.src;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static SecondPart.src.ConcurrentUtils.sleep;
import static SecondPart.src.ConcurrentUtils.stop;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * ReentrantLock#
         *
         * The class ReentrantLock is a mutual exclusion lock with the same basic behavior as the implicit monitors
         * accessed via the synchronized keyword but with extended capabilities.
         * As the name suggests this lock implements reentrant characteristics just as implicit monitors.
         */
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();

        executor.submit(() -> {
            lock.lock();
            try {
                sleep(1);
            } finally {
                lock.unlock();
            }
        });

        executor.submit(() -> {
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        stop(executor);


        /**
         * ReadWriteLock
         *
         * The interface ReadWriteLock specifies another type of lock maintaining a pair of locks for read and write access.
         * The idea behind read-write locks is that it's usually safe to read mutable variables concurrently
         * as long as nobody is writing to this variable.
         * So the read-lock can be held simultaneously by multiple threads as long as no threads hold the write-lock.
         * This can improve performance and throughput in case that reads are more frequent than writes.
         */

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                sleep(1);
                map.put("foo", "bar");
            } finally {
                lock.writeLock().unlock();
            }
        });
        /**
         * The above example first acquires a write-lock in order to put
         * a new value to the map after sleeping for one second.
         * Before this task has finished two other tasks are being submitted
         * trying to read the entry from the map and sleep for one second:
         */

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                sleep(1);
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);
    }
}