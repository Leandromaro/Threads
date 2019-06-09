package ExecutorCallable;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * ExecutorService its a higher level replacement for working with threads directly.
         * Executors are capable of running asynchronous tasks and typically manage a pool of threads
         * so we don't have to create new threads manually.
         */
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });
        try {
            /**
             * Executors have to be stopped explicitly - otherwise they keep listening for new tasks.
             * It provides two methods for that purpose: shutdown() waits for currently running tasks to finish
             * while shutdownNow() interrupts all running tasks and shut the executor down immediately.
             */
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }

        /**
         * Callables are functional interfaces just like runnables but instead of being void they return a value
         */
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
        /**
         * Callables can be submitted to executor services just like runnables.
         * Since submit() doesn't wait until the task completes,
         * the executor service cannot return the result of the callable directly.
         * Instead the executor returns a special result of type Future
         * which can be used to retrieve the actual result at a later point in time.
         */
        executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

        System.out.println("future done? " + future.isDone());

        Integer result = future.get();

        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
    }
}
