public class Task implements Runnable {

    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
