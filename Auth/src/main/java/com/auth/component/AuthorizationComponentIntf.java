package com.auth.component;

import javax.servlet.http.Cookie;

public interface AuthorizationComponentIntf
{
	boolean checkToken(String tokenValue);
	boolean login(String userName, String passWord);
	Cookie createToken();
}
