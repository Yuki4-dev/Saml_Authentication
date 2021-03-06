package com.auth.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class User
{
	private final static String DIGEST ="MD5";
	private String userName;
	private String passWord;

	public User(){}

	private User(String userName,String passWord)
	{
		this.userName = userName;
		this.passWord = passWord;
	}

	public static User create(String userName,String passWord)
	{
		String convertPassWord ;
		try
		{
			var cy = MessageDigest.getInstance(DIGEST);
			var digestPass = cy.digest(passWord.getBytes());
			convertPassWord = Hex.encodeHexString(digestPass);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}

		return new User(userName,convertPassWord);
	}

	public String getUserName()
	{
		return userName;
	}

	public String getPassWord()
	{
		return passWord;
	}

	@Override
	public boolean equals(Object obj)
	{
		if( obj != null && obj instanceof User)
		{
			var othreUser = (User)obj;
			return this.userName.equals(othreUser.getUserName()) && this.passWord.equals(othreUser.getPassWord());
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		var result = 37;
		result = result * 31 + userName.hashCode();
		result = result * 31 + passWord.hashCode();
		return result;
	}
}
