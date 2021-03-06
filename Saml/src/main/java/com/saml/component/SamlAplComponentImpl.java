package com.saml.component;

import java.io.IOException;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saml.SamlUtil;

@Service
public class SamlAplComponentImpl implements SamlAplComponentIntf
{
	private final String AUTH_TOKEN = "authtoken";
	private final String AUTH_OK = "ok";
	private final int STATUS_CODE_SUCCES = 200;
	private final String AUTH_URL = "http://localhost:8090/auth/check";

	@Override
	public boolean check(String token)
	{
		if (StringUtils.isEmpty(token))
		{
			return false;
		}

		System.out.println("token : " + token);
		HttpMethod method = null;
		try
		{
			method =  SamlUtil.doPost(AUTH_URL, new NameValuePair[]{new NameValuePair(AUTH_TOKEN,token)});
			if (method.getStatusLine() != null && method.getStatusCode() == STATUS_CODE_SUCCES)
			{
				return method.getResponseBodyAsString().equals(AUTH_OK);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(method != null)
			{
				method.releaseConnection();
			}
		}

		return false;
	}
}
