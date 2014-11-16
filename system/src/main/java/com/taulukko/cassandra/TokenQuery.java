package br.com.evon.cassandra;

import java.util.List;

public class TokenQuery {
	
	
	//Ex: UPDATE(1)   (7)accounts(5) (7)set(1) (7)login(5) (7)=(1) (7)'gandb'(2) (7)WHERE(3) (7)key(5) (7)=(1) (7)?(4)

	public static final int STRING = 2;
	public static final int WHERE = 3;
	public static final int VALUE_INJECT = 4;
	public static final int ITEM_NAME = 5;
	public static final int OPERATOR = 6;
	public static final int SPACE = 7;	
	public static final int OTHER_RESERVED_WORD = 8;
	

	private String content;
	private int type;
	private List<TokenQuery> innerTokens;
	private TokenQuery nextToken = null;
	private TokenQuery lastToken = null;
	
	public TokenQuery getLastToken() {
		return lastToken;
	}

	public void setLastToken(TokenQuery lastToken) {
		this.lastToken = lastToken;
	}

	public TokenQuery getNextToken() {
		return nextToken;
	}

	public void setNextToken(TokenQuery nextToken) {
		this.nextToken = nextToken;
	}

	public List<TokenQuery> getInnerTokens() {
		return innerTokens;
	}

	public void setInnerTokens(List<TokenQuery> innerTokens) {
		this.innerTokens = innerTokens;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
