package com.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * 验证登陆状态的拦截器
 * @author 结发受长生
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	private final String TIMEOUT_PAGE = "/WEB-INF/error_pages/timeout.jsp";
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response,
			Object obj) throws Exception {
		if(request.getSession().getAttribute("user") == null){
			//如果用户未登录或登陆已超时 ,则终止该请求 ,并将请求转发到登陆超时页面
			request.getRequestDispatcher(TIMEOUT_PAGE).forward(request, response);
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response,
			Object obj, 
			ModelAndView model) throws Exception {
		//到达目标控制器之后执行该方法
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response,
			Object obj,
			Exception ecp) throws Exception {
		//在整个请求完成之后执行
	}

}
