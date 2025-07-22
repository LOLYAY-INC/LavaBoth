package io.lolyay.lavaboth;

import com.google.common.util.concurrent.ForwardingExecutorService;
import io.lolyay.lavaboth.utils.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * A custom ExecutorService decorator that adds a method to submit a task
 * with a specific thread name prefix for the duration of its execution.
 */
public class ThreadNamingExecutorService extends ForwardingExecutorService {

    private final ExecutorService delegate; // The real executor we are wrapping

    /**
     * Creates a new ThreadNamingExecutorService that wraps the given delegate.
     * @param delegate The actual ExecutorService that will run the tasks.
     */
    public ThreadNamingExecutorService(ExecutorService delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ExecutorService delegate() {
        return delegate;
    }

    /**
     * Our new custom method. Submits a task and ensures the executing thread
     * is temporarily renamed for easier debugging and logging.
     *
     * @param task The Runnable task to execute.
     * @param threadNamePrefix The prefix for the thread's name while this task runs.
     * @return a Future representing pending completion of the task.
     */
    public Future<?> submit(Runnable task, String threadNamePrefix) {
        // Create a new "wrapper" task that includes the thread-naming logic.
        Runnable wrapperTask = () -> {
            Thread currentThread = Thread.currentThread();
            String originalName = currentThread.getName(); // 1. Store the original name

            try {
                // 2. Set the custom name
                currentThread.setName(threadNamePrefix + " | " + originalName);

                // 3. Run the actual task provided by the user
                task.run();

            } finally {
                // 4. IMPORTANT: Always change the name back in a finally block
                currentThread.setName(originalName);
            }
        };

        // 5. Submit the WRAPPER task to the REAL executor.
        return delegate.submit(wrapperTask);
    }
}