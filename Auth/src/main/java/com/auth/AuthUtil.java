package com.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;

import com.auth.entity.TokenInfo;
import com.auth.entity.User;

public class AuthUtil
{
	public static final List<TokenInfo> TOKEN_MEMORY_REPOGITRY = Collections.synchronizedList(new ArrayList<TokenInfo>());
	public static final List<User> USER_MEMORY_REPOGITRY = Collections.synchronizedList(new ArrayList<User>());
	private static final ExecutorService EXECUTER  =  Executors.newFixedThreadPool(1);
	static
	{
		EXECUTER.submit(new TokenCheckTask());
		USER_MEMORY_REPOGITRY.add(User.create("test", "1111"));
	}

	public static void redirect(HttpServletResponse response, String redirectUrl, List<NameValuePair> params) throws IOException
	{
		if(response.isCommitted())
		{
			return;
		}

		var sb = new StringBuilder(redirectUrl);
		if (params != null && !params.isEmpty())
		{
			sb.append("?");
			for(var param : params)
			{
				sb.append(param.getName());
				sb.append("=");
				sb.append(param.getValue());
				sb.append("&");
			}
			sb.deleteCharAt(sb.length()-1);
		}

		response.sendRedirect(sb.toString());
	}
}
