package com.saml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.saml.service.SamlAplServiceIntf;

@Controller
public class SamlAplController
{
	@Autowired
	private SamlAplServiceIntf samlAplService;

	@RequestMapping(value = "/**", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView execute(HttpServletRequest request,HttpServletResponse response)
	{
		return samlAplService.execute(request, response);
	}
}
