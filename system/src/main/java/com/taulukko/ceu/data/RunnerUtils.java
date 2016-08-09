package com.taulukko.ceu.data;

import cql.Token;
import cql.TokenType;

public class RunnerUtils {

	public static int countToken(Token token, final TokenType type) {
		int count = (token.getType().equals(type)) ? 1 : 0;

		count += token.getSubTokens().stream()
				.mapToInt(t -> countToken(t, type)).sum();

		return count;

	}
}
