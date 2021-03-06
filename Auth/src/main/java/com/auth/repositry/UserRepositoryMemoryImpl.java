package com.auth.repositry;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.auth.AuthUtil;
import com.auth.entity.User;

@Repository
public class UserRepositoryMemoryImpl implements UserRepositoryIntf
{
	@Override
	public Optional<User> select(String userName)
	{
		return AuthUtil.USER_MEMORY_REPOGITRY.stream().filter(x -> x.getUserName().equals(userName)).findFirst();
	}

	@Override
	public void save(User user)
	{
		var repoUser = select(user.getUserName()).orElse(null);
		if(repoUser == null)
		{
			AuthUtil.USER_MEMORY_REPOGITRY.add(user);
		}
	}

	@Override
	public void update(User user)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(User user)
	{
		throw new UnsupportedOperationException();
	}
}
