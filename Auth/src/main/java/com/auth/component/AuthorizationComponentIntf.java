package com.auth.component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthorizationComponentIntf
{
	boolean checkToken(HttpServletRequest request,HttpServletResponse response);
	boolean login(HttpServletRequest request,HttpServletResponse response);
	Cookie createToken();
}
