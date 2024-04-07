package com.intelligent.marking.intelligentmarking.service.impl;

import com.intelligent.marking.intelligentmarking.scheduletask.ScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class ScheduledTasksTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testScheduledTask() throws Exception {
        ScheduledTasks task = context.getBean(ScheduledTasks.class);
        
        // 假设你的定时任务的方法名是 reportCurrentTime
        Method method = ScheduledTasks.class.getMethod("getNews");
        Scheduled scheduled = method.getAnnotation(Scheduled.class);
        
        // 使用Cron表达式创建一个CronTrigger
        CronTrigger trigger = new CronTrigger(scheduled.cron());

        // 执行定时任务
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            SimpleTriggerContext triggerContext = new SimpleTriggerContext();
            Date nextExecutionTime = trigger.nextExecutionTime(triggerContext);
            while (nextExecutionTime != null) {
                triggerContext.update(nextExecutionTime, nextExecutionTime, nextExecutionTime);
                nextExecutionTime = trigger.nextExecutionTime(triggerContext);
                try {
                    method.invoke(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 模拟等待，直到下次执行时间
//                try {
//                    TimeUnit.MILLISECONDS.sleep(nextExecutionTime.getTime() - System.currentTimeMillis());
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
            }
        });
        
        // 等待一定时间，让定时任务有机会执行
//        executor.awaitTermination(1, TimeUnit.MINUTES);
//        executor.shutdownNow();
    }
}
