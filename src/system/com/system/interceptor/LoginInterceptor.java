package com.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response,
			Object obj) throws Exception {
		if("/page/login.html".equals(request.getServletPath())){
			//如果是登陆请求 , 则直接给予放行
			return true;
		}
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null){
			//如果用户未登录 ,则终止该请求 ,并将请求转发到登陆页面
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
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
