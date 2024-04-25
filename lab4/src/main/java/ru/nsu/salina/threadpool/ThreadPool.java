package ru.nsu.salina.threadpool;

import ru.nsu.salina.model.Delay;

import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    private BlockingQueue<Runnable> taskQueue;
    private int threadsLimit;
    public ThreadPool(int threadsLimit) { //TODO add BlockingQueue<Runnable> taskQueue
        //this.taskQueue = taskQueue;
        this.threadsLimit = threadsLimit;
    }

    public void start() {
    }

    public void interrupt() {
    }

    public void setDelay(Delay delay) {
    }
}
