package ru.iteco.accountbank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import ru.iteco.accountbank.model.AccountInfo;
import ru.iteco.accountbank.service.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@EnableAspectJAutoProxy
@ComponentScan
@PropertySource("classpath:application.properties")
public class AccountBankApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext  applicationContext = new AnnotationConfigApplicationContext(AccountBankApplication.class);
        homeworkTwo(applicationContext);
    }

    private static void homeworkTwo(AnnotationConfigApplicationContext  applicationContext) {
        Flow flow = applicationContext.getBean(Flow.class);

        List<Integer> idList = Arrays.asList(1, 2, 2, 3, 4, 5);

            for (Integer id : idList) {
                try {
                    flow.run(id);
                } catch (Exception ex) {
                    log.warn("Exception log:: {} with id {}", ex.getMessage(), id);
                }
            }

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
