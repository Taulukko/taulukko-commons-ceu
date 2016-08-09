package com.taulukko.cassandra;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.data.RunnerUtils;

import cql.Token;
import cql.TokenType;

public class RunnerUtilsTest {

	@Test(timeout = 5000)
	public void countToken() {
		Token tokenMain = new Token(TokenType.ACESSOR);

		Token tokenAnd = new Token(TokenType.AND);

		tokenMain.getSubTokens().add(tokenMain);
		tokenMain.getSubTokens().add(tokenAnd);

		try {
			RunnerUtils.countToken(tokenMain, TokenType.ACESSOR);
			Assert.fail("Expected a Throwable");
			return;
		} catch (Throwable e) {
			tokenMain = new Token(TokenType.ACESSOR);

			Token tokenAcessor2 = new Token(TokenType.ACESSOR);
			tokenAnd.getSubTokens().add(tokenAcessor2);

			tokenMain.getSubTokens().add(tokenAnd);

			Token tokenAcessor3 = new Token(TokenType.ACESSOR);
			tokenMain.getSubTokens().add(tokenAcessor3);

			Assert.assertEquals(3,
					RunnerUtils.countToken(tokenMain, TokenType.ACESSOR));
		}

	}
}
