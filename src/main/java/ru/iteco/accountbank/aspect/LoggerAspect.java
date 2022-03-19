package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
    @Before("allServiceMethod()")
    public void beforeAllServiceMethodAdvice(JoinPoint joinPoint) {
        log.info("beforeServiceMethodAdvice:: Call method: {} with arguments: {}", joinPoint.toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(value = "allServiceMethod()", returning = "result")
    public void afterAllServiceMethodAdvice(JoinPoint.StaticPart staticPart, Object result) {
        log.info("afterServiceMethodAdvice:: Finish method: {} with result: {}", staticPart.toShortString(), result);
    }

    @AfterThrowing(value = "allMethod()", throwing = "exception")
    public void afterAllMethodThrowAdvice(JoinPoint.StaticPart staticPart, Exception exception) {
        log.info("afterAllMethodThrowAdvice:: After throw {} with exception {}", staticPart.toShortString(), exception.getMessage());
    }

    @Pointcut("within(ru.iteco.accountbank.service.*)")
    public void allServiceMethod() {}

    @Pointcut("execution(* *(..))")
    public void allMethod() {}
}
