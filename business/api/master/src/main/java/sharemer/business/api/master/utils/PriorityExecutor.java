package sharemer.business.api.master.utils;

import java.util.concurrent.*;

/**
 * 带有执行优先级性质的线程池
 */
public class PriorityExecutor extends ThreadPoolExecutor {

	public static final PriorityExecutor ThreadPool = (PriorityExecutor) PriorityExecutor
			.newFixedThreadPool(4);

	public PriorityExecutor(int corePoolSize, int maximumPoolSize,
                            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	/** 设置一个线程池*/
	public static ExecutorService newFixedThreadPool(int nThreads) {
		return new PriorityExecutor(nThreads, nThreads, 0L,
				TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
	}

	/** 执行任务（带有执行结果返回值）到这个线程池中*/
	public static Future<?> submit(Runnable task, int priority) {
		return ThreadPool.submit(new ComparableFutureTask(task, null, priority));
	}

	/** 执行任务（不带执行结果返回值）到这个线程池中*/
	public static void execute(Runnable command, int priority) {
		ThreadPool.execute(new ComparableFutureTask(command, null, priority));
	}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
		return (RunnableFuture<T>) callable;
	}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
		return (RunnableFuture<T>) runnable;
	}
}
