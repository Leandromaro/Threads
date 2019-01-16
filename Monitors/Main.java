/**
* Class created to learn how monitors works
**/
public class SafeDualCounter {

    private Integer count1 = 0;
    private Integer count2 = 0;

    //Method monitor
    public synchronized  void incrementCount1() {
        count1++;
    }

    //Block Monitor
    public void incrementCount2(){
        // This kind of monitor let us to check a partycular 
        // part of code, not the entire method like the 
        // Method monitor does
        synchronized(this){
            count2++;    
        }
    }

    public synchronized int getCount1(){
        return count1;
    }
    public int getCount2(){
        synchronized(this){
            return count2;
        }
    }
}
