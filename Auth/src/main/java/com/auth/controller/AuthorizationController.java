package com.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auth.service.AuthorizationServiceIntf;

@Controller
public class AuthorizationController
{
	private final String TARGET_URL = "targeturl";

	@Autowired
	private AuthorizationServiceIntf authorizationService;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/check", method = {RequestMethod.GET,RequestMethod.POST})
	public void authorizationToken(HttpServletRequest request,HttpServletResponse response)
	{
		authorizationService.execute(request, response);
	}

	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public String login(HttpServletRequest request,HttpServletResponse response)
	{
		session.setAttribute(TARGET_URL, request.getParameter(TARGET_URL));
		return "login";
	}

	@RequestMapping(value = "/login.do", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView loginDo(HttpServletRequest request,HttpServletResponse response)
	{
		return authorizationService.login(request, response);
	}
}
