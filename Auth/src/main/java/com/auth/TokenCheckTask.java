package com.auth;

public class TokenCheckTask implements Runnable
{
	private int CHECK_TIME = 1000 * 60;

	@Override
	public void run()
	{
		while (true)
		{
			AuthUtil.TOKEN_MEMORY_REPOGITRY.removeIf(x ->
			{
				if (x.isTimeOut())
				{
					System.out.println("remove :" + x.getValue());
					return true;
				}
				return false;
			});

			try
			{
				Thread.sleep(CHECK_TIME);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}