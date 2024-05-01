package ru.nsu.salina.threadpool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class ThreadPool implements Executor {
    private final Queue<Runnable> taskQueue;
    private boolean isRunning;
    private int threadNumb;
    public ThreadPool(int threadNumb) {
        this.threadNumb = threadNumb;
        taskQueue = new ConcurrentLinkedQueue<>();
        isRunning = true;
        for (int i = 0; i < threadNumb; ++i) {
            new Thread(new ThreadInPool()).start();
        }
    }

    @Override
    public synchronized void execute(Runnable command) {
        if (isRunning) {
            taskQueue.offer(command);
        }
    }
    public synchronized void shutdown() {
        isRunning = false;
    }

    public int countTasksInQueue() {
        return taskQueue.size();
    }

    private final class ThreadInPool implements Runnable {
        @Override
        public void run() {
            while (isRunning) {
                Runnable nextThread = taskQueue.poll();
                if (nextThread != null) {
                    nextThread.run();
                }
            }
        }
    }
}
