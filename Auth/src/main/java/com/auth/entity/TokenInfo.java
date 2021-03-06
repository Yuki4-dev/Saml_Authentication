package com.auth.entity;

import java.time.LocalDateTime;

public class TokenInfo
{
	private static final int TIME_OUT = 5;
	private static final int UPDATE_LIMIT = 3;

	private String value;
	private LocalDateTime limitDate;
	private int updateCount = 0;

	private TokenInfo(String value)
	{
		this.value = value;
		this.limitDate = LocalDateTime.now().plusMinutes(TIME_OUT);
	}

	public static TokenInfo create(String value)
	{
		return new TokenInfo(value);
	}

	public boolean isTimeOut()
	{
		return limitDate.isBefore(LocalDateTime.now());
	}

	public void update()
	{
		if(updateCount < UPDATE_LIMIT)
		{
			this.limitDate = LocalDateTime.now().plusMinutes(TIME_OUT);
			updateCount++;
		}
	}

	public String getValue()
	{
		return value;
	}

	public LocalDateTime getLimitDate()
	{
		return limitDate;
	}

	public int getUpdateCount()
	{
		return updateCount;
	}
}
