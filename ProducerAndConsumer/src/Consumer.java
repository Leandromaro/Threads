import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
    private Queue<String> produced;
    private Semaphore mutex, producedSemaphore;

    public Consumer(Queue<String> produced, Semaphore mutex, Semaphore producedSemaphore) {
        this.produced = produced;
        this.mutex = mutex;
        this.producedSemaphore = producedSemaphore;
    }

    @Override
    public void run() {
        try {
            while (true){
                producedSemaphore.acquire(); // Checks if it can access to the Producer
                // Before consumer can consume an item,
                // it must acquire a permit
                mutex.acquire(); // it's checking if we can access to the Queue resource
                String product = produced.poll();
                System.out.println(Thread.currentThread().getName() +" got: "+ product);
                // After consumer consumes the item,
                // it releases consumer to notify producer
                mutex.release();
            }
        }catch (InterruptedException e){
            System.out.println(Thread.currentThread().getName() + " was interrupted");
        }
    }
}
