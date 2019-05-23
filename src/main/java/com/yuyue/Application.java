package com.yuyue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.yuyue.filter.HttpServletRequestReplacedFilter;

@SpringBootApplication
@EnableCaching
@SuppressWarnings("all")
public class Application {
	
	/**
	 * redis 的 模板对象
	 */
	@Autowired
	private RedisTemplate redisTemplate = null;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/**
	 * @RequestBady IO错误 比如两个json一起传输
	 * @return
	 */
	/*@Bean
	public FilterRegistrationBean httpServletRequestReplacedRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HttpServletRequestReplacedFilter());
		registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
		registration.setName("httpServletRequestReplacedFilter");
		registration.setOrder(1);
		return registration;
	}*/
	
	@PostConstruct
	public void init() {
		initRedisTemplate();
	}
	
	/**
	 * 初始化 redis模板
	 */
	private void initRedisTemplate() {
		RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
	}
	
}