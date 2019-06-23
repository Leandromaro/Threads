public class Main {
    // Note: The Drop class was written in order to demonstrate guarded blocks.
    // To avoid re-inventing the wheel, examine the existing data structures
    // in the Java Collections Framework
    // before trying to code your own data-sharing objects.
    public static void main(String[] args) {
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
