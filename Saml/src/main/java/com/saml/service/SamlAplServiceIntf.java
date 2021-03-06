package com.saml.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface SamlAplServiceIntf
{
	ModelAndView execute(HttpServletRequest request,HttpServletResponse response);
}
