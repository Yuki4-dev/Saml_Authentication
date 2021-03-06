package com.auth.repositry;

import java.util.Optional;

import com.auth.entity.TokenInfo;

public interface TokenRepositoryIntf
{
	Optional<TokenInfo> select(String value);
	void save(TokenInfo token);
	void update(TokenInfo token);
	boolean remove(TokenInfo token);
}
