package org.ksushka.letyclone.emailservice.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Component
public class QuartzRunner implements ApplicationRunner {

    @Autowired
    private Scheduler scheduler;

    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(SendEmailJob.class).withIdentity("SendEmailJobDetail").build();
    }

    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .withIdentity("Friday")
                .withSchedule(cronSchedule("0 33 11 ? * TUE"))
                .build();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduler.scheduleJob(jobDetail(), trigger());
    }
}