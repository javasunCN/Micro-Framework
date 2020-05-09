package com.microboot.cn.configuration.context;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 用来监控方法的执行时间
 * @author ZhiHong Zhang
 * @email it_javasun@yeah.net
 * @sine 上午11:47:52
 */
@Component("methodTimeAdvice")
public class WatchMethodTimeAdvice implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(WatchMethodTimeAdvice.class);

	/**
     * @see MethodInterceptor#invoke(MethodInvocation)
     */  
	@Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch  
        StopWatch clock = new StopWatch();
          
        Object result = null;  
        //监控的类名  
        String className = invocation.getMethod().getDeclaringClass().getSimpleName();  
        //监控的方法名  
        String methodName = className + "." + invocation.getMethod().getName();
        String clName = invocation.getMethod().getDeclaringClass().getName();
        
        clock.start(clName+ "." + invocation.getMethod().getName()); //计时开始
        try {  
            //这个是我们监控的bean的执行并返回结果  
            result = invocation.proceed();  
        } catch (Throwable e) {  
            //监控的参数  
            Object[] objs = invocation.getArguments();  
            logger.error("执行方法异常,方法名：" + methodName + "参数:" + getString(objs), e);  
            throw e;  
        }  
        clock.stop(); //计时结束  

        if (logger.isDebugEnabled()) {
            logger.debug("执行时间:" + clock.getTotalTimeMillis() + " ms 方法名：[" + methodName + "]"); 
        	logger.debug("执行分析:" + clock.prettyPrint());  
        }
        return result;  
    } 
	
	
	 /** 
     * 这个类主要是用于输出方法的参数 
     *  
     * @param objs 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public String getString(Object[] objs) {  
        StringBuffer stringBuffer = new StringBuffer();  
        for (int i = 0, len = objs.length; i < len; i++) {  
            if (objs[i] instanceof String) {  
                stringBuffer.append("String类型：" + objs[i].toString());  
            } else if (objs[i] instanceof Map) {  
                HashMap<String, Object> hashMap = (HashMap<String, Object>) objs[i];  
                HashMap<String, Object> map = hashMap;  
                HashSet<String> set = (HashSet<String>) map.keySet();  
                stringBuffer.append("Map类型：");  
                for (String str : set) {  
                    stringBuffer.append(str + "=" + map.get(str));  
                }  
            } else if (objs[i] instanceof Integer) {  
                stringBuffer.append("整数类型：");  
                stringBuffer.append(objs[i].toString());  
            } else {  
                stringBuffer.append(objs[i].toString());  
            }  
        }  
        return stringBuffer.toString();  
    }  

}
