# Threads
A learning repo!


## Definitions:

### Processes and Threads
In concurrent programming, there are two basic units of execution: __processes__ and __threads__. In the Java programming language, concurrent programming is mostly concerned with threads. However, processes are also important.

1. Processes
   - A process has a self-contained execution environment. A process generally has a complete, private set of basic run-time resources; in particular, each process has its own memory space.

   - Processes are often seen as synonymous with programs or applications. However, what the user sees as a single application may in fact be a set of cooperating processes. To facilitate communication between processes, most operating systems support Inter Process Communication (IPC) resources, such as pipes and sockets. IPC is used not just for communication between processes on the same system, but processes on different systems.

   - Most implementations of the Java virtual machine run as a single process. A Java application can create additional processes using a ProcessBuilder object. Multiprocess applications are beyond the scope of this lesson.

2. Threads
   - Threads are sometimes called __lightweight processes__. Both processes and threads provide an execution environment, but creating a new thread requires fewer resources than creating a new process.

   - __Threads exist within a process__ (every process has at least one). __Threads share the process's resources__, including memory and open files. This makes for efficient, but potentially problematic, communication.

   - Multithreaded execution is an essential feature of the Java platform. Every application has at least one thread — or several, if you count "system" threads that do things like memory management and signal handling. But from the application programmer's point of view, you start with just one thread, called the main thread. This thread has the ability to create additional threads, as we'll demonstrate in the next section.

### Monitors and Semaphore

A __Monitor__ is an object designed to be accessed from multiple threads. The member functions or methods of a monitor object will enforce mutual exclusion, so only one thread may be performing any action on the object at a given time. If one thread is currently executing a member function of the object then any other thread that tries to call a member function of that object will have to wait until the first has finished.

A __Semaphore__ is a lower-level object. You might well use a semaphore to implement a monitor. A semaphore essentially is just a counter. When the counter is positive, if a thread tries to acquire the semaphore then it is allowed, and the counter is decremented. When a thread is done then it releases the semaphore, and increments the counter.
If the counter is already zero when a thread tries to acquire the semaphore then it has to wait until another thread releases the semaphore. If multiple threads are waiting when a thread releases a semaphore then one of them gets it. The thread that releases a semaphore need not be the same thread that acquired it.

#### Analogies
A monitor is like a public toilet. Only one person can enter at a time. They lock the door to prevent anyone else coming in, do their stuff, and then unlock it when they leave.

A semaphore is like a bike hire place. They have a certain number of bikes. If you try and hire a bike and they have one free then you can take it, otherwise you must wait. When someone returns their bike then someone else can take it. If you have a bike then you can give it to someone else to return --- the bike hire place doesn't care who returns it, as long as they get their bike back.

## Producer - Consumer
Producer–consumer problem (also known as the bounded-buffer problem) is a classic example of a multi-process synchronization problem. The problem describes two processes, the producer and the consumer, which share a common, fixed-size buffer used as a queue.

The producer’s job is to generate data, put it into the buffer, and start again.
At the same time, the consumer is consuming the data (i.e. removing it from the buffer), one piece at a time.

To make sure that the producer won’t try to add data into the buffer if it’s full and that the consumer won’t try to remove data from an empty buffer.
 
The producer is to either go to sleep or discard data if the buffer is full. The next time the consumer removes an item from the buffer, it notifies the producer, who starts to fill the buffer again. In the same way, the consumer can go to sleep if it finds the buffer to be empty. The next time the producer puts data into the buffer, it wakes up the sleeping consumer.
An inadequate solution could result in a deadlock where both processes are waiting to be awakened.

## Defining and Starting a Thread
An application that creates an instance of Thread must provide the code that will run in that thread. There are two ways to do this:

1. __Provide a Runnable object__. The Runnable interface defines a single method, run, meant to contain the code executed in the thread. The Runnable object is passed to the Thread constructor, as in the HelloRunnable example:

```
   public class HelloRunnable implements Runnable {

       public void run() {
           System.out.println("Hello from a thread!");
       }

       public static void main(String args[]) {
           (new Thread(new HelloRunnable())).start();
       }

   }
```


1 .__Subclass Thread__. The Thread class itself implements Runnable, though its run method does nothing. An application can subclass Thread, providing its own implementation of run, as in the HelloThread example:

```
public class HelloThread extends Thread {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
    }

}

```
Notice that both examples invoke Thread.start in order to start the new thread.
The first idiom, which employs a Runnable object, is more general, because the Runnable object can subclass a class other than Thread. The second idiom is easier to use in simple applications, but is limited by the fact that your task class must be a descendant of Thread. The first approach, which separates the Runnable task from the Thread object that executes the task is more flexible.

The Thread class defines a number of methods useful for thread management. These include static methods, which provide information about, or affect the status of, the thread invoking the method. The other methods are invoked from other threads involved in managing the thread and Thread object. 
