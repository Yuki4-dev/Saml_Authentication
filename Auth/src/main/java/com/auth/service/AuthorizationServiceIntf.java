package com.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface AuthorizationServiceIntf
{
	void execute(HttpServletRequest request,HttpServletResponse response);
	ModelAndView login(HttpServletRequest request,HttpServletResponse response);
}
