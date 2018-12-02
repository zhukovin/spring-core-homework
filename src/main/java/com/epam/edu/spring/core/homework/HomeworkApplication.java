package com.epam.edu.spring.core.homework;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring.xml"})
public class HomeworkApplication implements ApplicationContextAware {

    private static BeanFactory beanFactory;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

    public static BeanFactory beanFactory() {
        return beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanFactory = applicationContext;
    }
}
