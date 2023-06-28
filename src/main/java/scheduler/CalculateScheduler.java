package scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CalculateScheduler implements ServletContextListener {
    private volatile ScheduledExecutorService executor;

    public void contextInitialized(ServletContextEvent sce)
    {
        executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate([실행시킬메소드], 0, 3, TimeUnit.MINUTES);
    }

    public void contextDestroyed(ServletContextEvent sce)
    {
        final ScheduledExecutorService executor = this.executor;

        if (executor != null)
        {
            executor.shutdown();
            this.executor = null;
        }
    }
}