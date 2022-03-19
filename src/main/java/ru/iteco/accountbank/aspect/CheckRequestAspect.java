package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.ExternalInfo;

@Aspect
@Component
@Slf4j
public class CheckRequestAspect {

    @Value("${id-not-process}")
    private Integer idNotProcess;

    @Around("annotatedCheckRequestMethod() && consistExternalInfoArgMethod(externalInfo)")
    public Object aroundAnnotationCheckRequestAdvice(ProceedingJoinPoint proceedingJoinPoint, ExternalInfo externalInfo) throws Throwable {
        log.info("aroundAnnotationCheckRequestAdvice:: Around {} with ExternalInfo: {}",
                proceedingJoinPoint.getSignature().toShortString(),
                externalInfo);
        if (externalInfo.getId().equals(idNotProcess)) {
            log.info("aroundAnnotationCheckRequestAdvice:: Around {} id-not-process", proceedingJoinPoint.getSignature().toShortString());
            throw new RuntimeException("aroundAnnotationCheckRequestAdvice:: Id not process");
        } else {
            log.info("aroundAnnotationCheckRequestAdvice:: Around {} Start method", proceedingJoinPoint.getSignature().toShortString());
            Object result = proceedingJoinPoint.proceed();
            log.info("aroundAnnotationCheckRequestAdvice:: Around {} finished with result {}", proceedingJoinPoint.getSignature().toShortString(), result);
            return result;
        }
    }

    @Pointcut("@annotation(ru.iteco.accountbank.model.annotation.CheckRequest)")
    public void annotatedCheckRequestMethod() {}

    @Pointcut("execution(* *(.., ru.iteco.accountbank.model.ExternalInfo, ..)) && args(externalInfo)")
    public void consistExternalInfoArgMethod(ExternalInfo externalInfo) {}

}
