package com.auth.repositry;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.auth.AuthUtil;
import com.auth.entity.TokenInfo;

@Repository
public class TokenRepositoryMemoryImpl implements TokenRepositoryIntf
{
	@Override
	public Optional<TokenInfo> select(String token)
	{
		return AuthUtil.TOKEN_MEMORY_REPOGITRY.stream().filter(x -> x.getValue().equals(token)).findFirst();
	}

	@Override
	public void save(TokenInfo tokenInfo)
	{
		var tokennInfo = select(tokenInfo.getValue()).orElse(null);
		if (tokennInfo == null)
		{
			System.out.println("insert : " + tokenInfo.getValue());
			AuthUtil.TOKEN_MEMORY_REPOGITRY.add(tokenInfo);
		}
	}

	@Override
	public void update(TokenInfo token)
	{
		System.out.println("update : " + token.getValue() + "[" + token.getUpdateCount() + "]");
	}

	@Override
	public boolean remove(TokenInfo token)
	{
		throw new UnsupportedOperationException();
	}
}
