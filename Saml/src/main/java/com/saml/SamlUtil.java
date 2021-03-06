package com.saml;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SamlUtil
{
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

	public static HttpMethod doPost(String requestUrl,NameValuePair[] params) throws HttpException, IOException
	{
		var method = new PostMethod(requestUrl);
		method.setFollowRedirects(false);
		method.setQueryString(params);
		return request(method);
	}

	private static HttpMethod request(HttpMethod method) throws HttpException, IOException
	{
		var client = new  HttpClient();
		client.executeMethod(method);
		return method;
	}
}
