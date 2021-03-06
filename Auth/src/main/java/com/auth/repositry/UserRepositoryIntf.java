package com.auth.repositry;

import java.util.Optional;

import com.auth.entity.User;

public interface UserRepositoryIntf
{
	Optional<User> select(String userName);
	void save(User user);
	void update(User user);
	boolean remove(User user);
}
