package com.auth.component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.entity.TokenInfo;
import com.auth.entity.User;
import com.auth.repositry.TokenRepositoryIntf;
import com.auth.repositry.UserRepositoryIntf;

@Service
public class AuthorizationComponentImpl implements AuthorizationComponentIntf
{
	private final int TIMEOUT = 60 * 60 * 2;
	private final String AUTH_TOKEN = "authtoken";

	@Autowired
	private UserRepositoryIntf userRepositry;

	@Autowired
	private TokenRepositoryIntf tokenRepositry;

	@Override
	public boolean checkToken(String tokenValue)
	{
		if(tokenValue == null)
		{
			return false;
		}

		var token = tokenRepositry.select(tokenValue).orElse(null);
		if(token != null)
		{
			token.update();
			tokenRepositry.update(token);
			return true;
		}

		return false;
	}

	@Override
	public boolean login(String userName, String passWord)
	{
		if(userName == null || passWord == null)
		{
			return false;
		}

		var user = User.create(userName, passWord);
		var repoUser = userRepositry.select(user.getUserName()).orElse(null);
		if(repoUser == null)
		{
			return false;
		}

		return repoUser.equals(user);
	}

	@Override
	public Cookie createToken()
	{
		try
		{
			var value = createTokentValue();
			var token = new Cookie(AUTH_TOKEN,value);
			token.setPath("/");
			token.setSecure(false);
			token.setMaxAge(TIMEOUT);
			tokenRepositry.save(TokenInfo.create(value));
			return token;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private synchronized String createTokentValue() throws NoSuchAlgorithmException
	{
		var md5 = MessageDigest.getInstance("MD5");
		var result = md5.digest(String.valueOf(System.currentTimeMillis()).getBytes());
		return Hex.encodeHexString(result);
	}
}
