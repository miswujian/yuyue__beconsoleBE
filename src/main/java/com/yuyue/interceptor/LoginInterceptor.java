package com.yuyue.interceptor;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yuyue.pojo.User;

import net.sf.json.JSONObject;

public class LoginInterceptor implements HandlerInterceptor {
	
	/**
	 * 操作字符串的
	 */
	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;
	
	@SuppressWarnings("all")
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {
		//获取请求的方式 路径 和 sessionid
		System.err.println(httpServletRequest.getMethod().toUpperCase()+"-"+
			httpServletRequest.getRequestURI()+"-"+httpServletRequest.getSession().getId());
		//复杂请求的嗅探 放行 一般cookie不会带上sessionid
		if("OPTIONS".equals(httpServletRequest.getMethod().toUpperCase())) {
			System.err.println("OP:OK");
			return true;
		}
		HttpSession session = httpServletRequest.getSession();
		String contextPath=session.getServletContext().getContextPath();
		//简单的权限拦截 除login接口以外 其他的接口都需要已经登录
		String[] requireAuthPages = new String[]{
        		"login",
        		"swagger-resources/configuration/ui",
        		"swagger-resources",
        		"v2/api-docs",
        		"swagger-resources/configuration/security",
        		"error"
        };
		String uri = httpServletRequest.getRequestURI();

        uri = StringUtils.remove(uri, contextPath+"/");
        String page = uri;
        if(begingWith(page, requireAuthPages)){
        	return true;
        }
        String u = stringRedisTemplate.opsForValue().get(httpServletRequest.getSession().getId().toString());
        /**
         * 允许多跨域设置
         */
        String []  allowDomain= {"http://localhost:3006","http://119.3.231.11:5000"};
		Set allowedOrigins= new HashSet(Arrays.asList(allowDomain));
		String originHeader=((HttpServletRequest) httpServletRequest).getHeader("Origin");
        if(u==null) {
        	System.err.println("失败了失败了");
			httpServletResponse.reset();
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json;charset=UTF-8");
			if(allowedOrigins.contains(originHeader))
				httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
			httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
			PrintWriter pw = httpServletResponse.getWriter();
		    JSONObject res = new JSONObject();
		    res.put("codeguess",0);
		    res.put("message","没登录被拦截了哟");
			pw.append(res.toString());
			return false;
        }
        JSONObject json = JSONObject.fromObject(u);
        User user = (User) JSONObject.toBean(json,User.class);
		//BeUser user = (BeUser) session.getAttribute("user");
		//未登录则拦截
		if (user == null) {
			System.err.println("失败了失败了");
			httpServletResponse.reset();
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json;charset=UTF-8");
			if(allowedOrigins.contains(originHeader))
				httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
			httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
			PrintWriter pw = httpServletResponse.getWriter();
		    JSONObject res = new JSONObject();
		    res.put("codeguess",0);
		    res.put("message","没登录被拦截了哟");
			pw.append(res.toString());
			return false;
		}
		System.err.println("user:"+user.getRoleType()+":"+StringUtils.startsWith(page, "system/users")+
				":"+!"GET".equals(httpServletRequest.getMethod().toUpperCase())+":"+
				(user.getRoleType()!=8)+":"+(user.getRoleType()!=10));
		if(StringUtils.startsWith(page, "system/users")&&!"GET".equals(httpServletRequest.getMethod().toUpperCase())) {
			if(user.getRoleType()!=8&&user.getRoleType()!=10) {
				httpServletResponse.reset();
				httpServletResponse.setCharacterEncoding("UTF-8");
				httpServletResponse.setContentType("application/json;charset=UTF-8");
				//这两个必须加 不然false会报跨域错误
				if(allowedOrigins.contains(originHeader))
					httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
				httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
				PrintWriter pw = httpServletResponse.getWriter();
			    JSONObject res = new JSONObject();
			    res.put("codeguess",0);
			    res.put("message","权限不足");
				pw.append(res.toString());
				return false;
			}
		}
		return true;
	}

	private boolean begingWith(String page, String[] requiredAuthPages) {
    	boolean result = false;
    	for (String requiredAuthPage : requiredAuthPages) {
			if(StringUtils.startsWith(page, requiredAuthPage)) {
				result = true;	
				break;
			}
		}
    	return result;
	}
	
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}
}
