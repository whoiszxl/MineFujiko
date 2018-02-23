package com.whoiszxl.house.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.whoiszxl.house.common.constants.CommonConstants;
import com.whoiszxl.house.common.model.User;

@Component
public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String reqUri = request.getRequestURI();
		if(reqUri.startsWith("/static") || reqUri.startsWith("/error")) {
			return true;
		}
		//当session没有自动创建一个（true的作用）
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
		if(user != null) {
			UserContext.setUser(user);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserContext.remove();
	}

}
