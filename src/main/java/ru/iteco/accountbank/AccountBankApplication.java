package ru.iteco.accountbank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.iteco.accountbank.model.AccountInfo;
import ru.iteco.accountbank.service.*;

@Slf4j
@ComponentScan
@PropertySource("classpath:application.properties")
public class AccountBankApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext  applicationContext = new AnnotationConfigApplicationContext(AccountBankApplication.class);
        homeworkOne(applicationContext);
    }

    private static void homeworkOne(AnnotationConfigApplicationContext  applicationContext) {
        Flow flow = applicationContext.getBean(Flow.class);

        flow.run(1);
        flow.run(2);
        flow.run(2);
        flow.run(3);
        flow.run(4);

        applicationContext.close();
    }

    private static void iocProcess(ApplicationContext applicationContext) {
        AccountService accountService = applicationContext.getBean(AccountService.class);
        log.info("Personal info class: {}", accountService.getPersonalInfoClass());
        AccountInfo accountInfo = accountService.getAccountInfoById(1);
        log.info("Personal info class: {}", accountService.getPersonalInfoClass());

        System.out.println(accountInfo);

        IObject objectValue = applicationContext.getBean(IObject.class);
        log.info("objectValue type: {}", objectValue.getClass());
        log.info("result info: {}", objectValue.getInfo());
    }
}
