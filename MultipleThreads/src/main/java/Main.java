public class Main {

    static final String THREAD = "Thread";

    public static void main(String[] args) {
        Thread[] threads = new Thread[5];
        for (int i=0; i<threads.length; i++){
            threads[i] = new Thread(new Task(), THREAD +i);
            threads[i].start();
        }
        for (Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName());
    }
}