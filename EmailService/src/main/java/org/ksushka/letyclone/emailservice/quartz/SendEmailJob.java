package org.ksushka.letyclone.emailservice.quartz;

import org.ksushka.letyclone.emailservice.EmailService;
import org.ksushka.letyclone.emailservice.model.User;
import org.ksushka.letyclone.emailservice.model.UserRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendEmailJob implements Job {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new JobExecutionException("No users found");

        for (User user: users) {
            emailService.sendSimpleMessage(
                    user.getUsername(),
                    "Интересное, красивое и по привлекательным ценам на LetyClone",
                    "LetyClone по вам соскучился! ❤️\n" +
                            "Пора снова посетить наш сервис и оценить новинки\n\n" +
                            "Желаем приятных и выгодных покупок! :)"
            );
        }
    }
}