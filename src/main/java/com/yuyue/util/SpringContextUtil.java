package com.yuyue.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * redis缓存和事务用的是aop技术 service里面的方法在service里直接调用是不会触发的 需要绕一绕 使aop来拦截
 * @author 吴俭
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	private SpringContextUtil() {
		
	}
	
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }
    
    public static <T> T getBean(Class<T> clazz) {
    	return applicationContext.getBean(clazz);
    }

}

