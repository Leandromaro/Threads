import static java.lang.Thread.*;

public class Main {

    public static void main(String[] args) {
        Thread thread = currentThread();
        thread.setName("This is the Main Thread");
        System.out.println(thread.getName());

        Thread newThread = new Thread(() -> {
            try {
                sleep(1000);
                System.out.println("This is a new thread");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread test = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        test.run();
        newThread.run();

    }
}
