/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit360.threading;

import byui.cit360.Handler;
import byui.cit360.collections.AppController;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dale
 */
public class Threads implements Handler{
    
    public void Threads() {
    }
    
    public void simpleThread() {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("This is " + threadName);
        };
        task.run();
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("Done.");
    }
    
    public void concurrentRun() throws InterruptedException {
        AppController ac = new AppController();
        
        Callable<String> task1 = () -> {
            try {
                ac.runCommand("list");
                return "Initial task complete.";
            } catch (Exception e) {
                throw new IllegalStateException("task interrupted",e);
            }
        };
        
        Callable<String> task2 = () -> {
            try {
                for (int i = 9;i >= 1; i--) {
                    System.out.print(i + " ...");
                    TimeUnit.SECONDS.sleep(1);
                }
                ac.runCommand("del");
                return "Secondary task complete.";
            } catch (Exception e) {
                throw new IllegalStateException("task interrupted",e);
            }
        };
        
        List<Callable<String>> taskList = Arrays.asList(task1,task2);
        
        ExecutorService exec = Executors.newWorkStealingPool();
        
        exec.invokeAll(taskList).stream().map(future -> {
            try {
                return future.get(10,TimeUnit.SECONDS);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }).forEach(System.out::println);
        
        try {
            exec.shutdown();
            exec.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Tasks interrupted");
        } finally {
            if (!exec.isTerminated()) {
                System.err.println("Tasks failed to close; cancelling forcefully.");
            }
            exec.shutdownNow();
        }
    }
    
    @Override
    public void execute() {
        simpleThread();
        try {
            concurrentRun();
        } catch (InterruptedException ex) {
            Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
