package com.saml.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.saml.SamlUtil;
import com.saml.component.SamlAplComponentIntf;

@Service
public class SamlAplServiseImpl implements SamlAplServiceIntf
{
	private final String AUTH_TOKEN = "authtoken";
	private final String TARGET_URL = "targeturl";
	private final String LOGIN_URL = "http://localhost:8090/auth/login";

	@Autowired
	private SamlAplComponentIntf samlAplComponent;

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
	{
		Cookie  token = null;
		if(request.getCookies() != null)
		{
			token = Stream.of(request.getCookies()).filter(c ->AUTH_TOKEN.equals(c.getName())).findFirst().orElse(null);
		}

		if (token != null &&  samlAplComponent.check(token.getValue()))
		{
			var model = new ModelAndView("top.html");
			model.addObject("token", token.getValue()).addObject("requesturl", request.getRequestURL().toString());
			return model;
		}
		else
		{
			try
			{
				var url =  URLEncoder.encode(request.getRequestURL().toString(), request.getCharacterEncoding());
				var param = new NameValuePair(TARGET_URL,url);
				SamlUtil.redirect(response, LOGIN_URL, List.of(param));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			return null;
		}
	}
}
