package ru.iteco.accountbank.config;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.annotation.CacheResult;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CacheResultBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, final String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        log.info("postProcessAfterInitialization: {}", beanName);

        for (Method method : methods) {
            if (method.isAnnotationPresent(CacheResult.class)) {
                log.info("Bean {} is proxy. Has annotation @CacheResult on method: {}", beanName, method.getName());
                ProxyFactory proxyFactory = new ProxyFactory(bean);
                proxyFactory.addAdvice(new CacheResultMethodInterceptor());

                return proxyFactory.getProxy();
            }
        }
        return bean;
    }

}
