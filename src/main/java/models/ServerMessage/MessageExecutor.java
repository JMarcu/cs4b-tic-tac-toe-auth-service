package models.ServerMessage; 

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageExecutor extends Thread{
    private static MessageExecutor instance;

    private ExecutorService executor;
    private LinkedBlockingQueue<Runnable> queue; 
    
    private MessageExecutor(){
        executor = Executors.newFixedThreadPool(20);
        queue = new LinkedBlockingQueue<Runnable>();
    }

    public void queueMessageHandler(Runnable handler) throws InterruptedException{
        System.out.println("Inside of Executor");
        queue.put(handler);
    }
    
    public void run() {
        System.out.println("Inside of Executor2");
        int count = 3;

        boolean flag = true;
        //while(flag){
            if(!queue.isEmpty()){
                try {
                    executor.execute(queue.take());
                } catch (/*InterruptedException*/ Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Inside of Executor end");
                flag = false;
            }
       // }
    }

   /** 
    * Creates an instance of this object type. The method checks if there is a preexisting instance. 
    * If there is a preexisting instance of this object type, the method returns the existing object. 
    * If there isn't a preexisting instance of this object type, the method creates a new instance and returns it.
    * @return   
    */
    public static MessageExecutor getInstance(){
        if(instance == null){
            instance = new MessageExecutor();
        }

        return instance; 
    }
}