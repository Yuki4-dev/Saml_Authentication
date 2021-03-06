package com.auth.service;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.auth.AuthUtil;
import com.auth.component.AuthorizationComponentIntf;

@Service
public class AuthorizationServiceImpl implements AuthorizationServiceIntf
{
	private final String TARGET_URL = "targeturl";
	private final String AUTH_TOKEN = "authtoken";
	private final String AUTH_OK = "ok";
	private final String AUTH_NG = "ng";
	private final String USERNAME = "username";
	private final String PASSWORD = "password";

	@Autowired
	private AuthorizationComponentIntf authorizationComponent;

	@Autowired
	private HttpSession session;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		var result = AUTH_NG;
		if (authorizationComponent.checkToken(request.getParameter(AUTH_TOKEN)))
		{
			result = AUTH_OK;
		}

		response.setContentType("text/html;");
		response.setStatus(200);
		try (var pw = response.getWriter())
		{
			System.out.println("check : " + result);
			pw.print(result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
	{
		var name = request.getParameter(USERNAME);
		var pass = request.getParameter(PASSWORD);
		if (authorizationComponent.login(name, pass))
		{
			System.out.println("login succes");
			var token = authorizationComponent.createToken();
			var redirectUrl = (String) session.getAttribute(TARGET_URL);

			try
			{
				var url = new URL(redirectUrl);
				var domain = url.getHost().startsWith("www") ? url.getHost().substring("www".length() + 1) : url.getHost();
				token.setDomain(domain);
				response.addCookie(token);
				AuthUtil.redirect(response, redirectUrl, null);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			return null;
		}
		else
		{
			System.out.println("login error");
			var model = new ModelAndView("login");
			model.addObject("error", true);
			return model;
		}
	}
}
