import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

    private int produce;
    private Queue<String> produced;
    private Semaphore mutex; // Created to protect the critical block
    private Semaphore producedSemaphore;

    public Producer(int produce, Queue<String> produced, Semaphore mutex, Semaphore producedSemaphore) {
        this.produce = produce;
        this.produced = produced;
        this.mutex = mutex;
        this.producedSemaphore = producedSemaphore;
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<produce; i++){
                mutex.acquire();
                produced.offer("Product" + (i+1));
                mutex.release();
                producedSemaphore.release();
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
