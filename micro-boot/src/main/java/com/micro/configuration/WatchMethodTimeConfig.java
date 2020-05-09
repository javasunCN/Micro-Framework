package com.micro.configuration;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatchMethodTimeConfig {


    /**
     * 添加方法监控
     * @return
     */
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
        BeanNameAutoProxyCreator beanNameAutoProxyCreator=new BeanNameAutoProxyCreator();
        //设置要创建代理的那些Bean的名字   可以是*Controller、*ServiceImpl、*Mapper
        beanNameAutoProxyCreator.setBeanNames(
                "*ServiceImpl",
                "*CalculatePointService");
        //设置拦截链名字(这些拦截器是有先后顺序的)
        beanNameAutoProxyCreator.setInterceptorNames("methodTimeAdvice");
        return beanNameAutoProxyCreator;
    }
}
