package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class CacheResultAspect {

    private static final Map<String, Map<MethodArgs, Object>> CACHE = new ConcurrentHashMap<>();

    @Around("@annotation(ru.iteco.accountbank.model.annotation.CacheResult)")
    public Object aroundCacheResultAnnotationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("aroundCacheResultAnnotationAdvice:: call method mark @CacheResult");

        Map<MethodArgs, Object> methodArgsObjectMap = CACHE.get(proceedingJoinPoint.getSignature().toShortString());

        if (methodArgsObjectMap != null) {
            log.info("Method: {} has cache. Cache: {}", proceedingJoinPoint.getSignature().toShortString(), methodArgsObjectMap);
            final MethodArgs methodArgs = getMethodArgs(proceedingJoinPoint.getArgs());
            log.info("Check cache result by method with args: {}({})", proceedingJoinPoint.getSignature().toShortString(), proceedingJoinPoint.getArgs());
            Object result = methodArgsObjectMap.get(methodArgs);
            if (result != null) {
                log.info("Return result from cache: method: {}({}), result: {}", proceedingJoinPoint.getSignature().toShortString(), proceedingJoinPoint.getArgs(), result);
            } else {
                log.info("Call original method and record result into cache");
                result = proceedingJoinPoint.proceed();
                methodArgsObjectMap.put(methodArgs, result);
            }
            return result;
        } else {
            log.info("Method: {} not cache.", proceedingJoinPoint.getSignature().toShortString());
            Object result = proceedingJoinPoint.proceed();
            methodArgsObjectMap = new ConcurrentHashMap<>();
            methodArgsObjectMap.put(
                    getMethodArgs(proceedingJoinPoint.getArgs()),
                    result
            );
            CACHE.put(proceedingJoinPoint.getSignature().toShortString(), methodArgsObjectMap);
            return result;
        }
    }

    private MethodArgs getMethodArgs(Object[] args) {
        LinkedList<Object> linkedArgs = new LinkedList<>();
        Collections.addAll(linkedArgs, args);
        return new MethodArgs(linkedArgs);
    }

    private static class MethodArgs {

        private final LinkedList<Object> args;

        public MethodArgs(LinkedList<Object> args) {
            this.args = args;
        }

        public LinkedList<Object> getArgs() {
            return args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodArgs that = (MethodArgs) o;

            return args.equals(that.args);
        }

        @Override
        public int hashCode() {
            return args.hashCode();
        }

        @Override
        public String toString() {
            return "MethodArgs{" +
                    "args=" + args +
                    '}';
        }
    }
}
