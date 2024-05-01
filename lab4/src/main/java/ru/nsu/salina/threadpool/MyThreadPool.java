package ru.nsu.salina.threadpool;
/*
import ru.nsu.salina.model.Delay;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class MyThreadPool implements ExecutorService {
    private BlockingQueue<Runnable> taskQueue;
    private int thread_numb;
    public MyThreadPool(int threadsLimit, BlockingQueue<Runnable> taskQueue) { //TODO add BlockingQueue<Runnable> taskQueue
        this.taskQueue = taskQueue;
        this.thread_numb = threadsLimit;
    }

    public void interrupt() {

    }

    public void setDelay(Delay delay) {
        
    }

    @Override
    public void shutdown() {
        isShutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {

    }

}
*/