package com.taulukko.cassandra;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public abstract class Runner<Q> {

	private static Set<String> reservedWords = new HashSet<>();
	private static Set<String> operators = new HashSet<>();

	public abstract void exec(Command command) throws CEUException;

	public abstract <T> T query(Command command, Handler<T, Q> handler)
			throws CEUException;

	static {

		reservedWords.addAll(Arrays.asList("ADD", "ALL", "ALTER", "AND", "ANY",
				"APPLY", "ALLOW", "AS", "ASC", "ASCII", "AUTHORIZE", "BATCH",
				"BEGIN", "BIGINT", "BLOB", "BOOLEAN", "BY", "CLUSTERING",
				"COLUMNFAMILY", "COMPACT", "CONSISTENCY", "COUNT", "COUNTER",
				"CREATE", "DECIMAL", "DELETE", "DESC", "DOUBLE", "DROP",
				"EACH_QUORUM", "FILTERING", "FLOAT", "FROM", "GRANT", "IN",
				"INDEX", "CUSTOM", "INSERT", "INT", "INTO", "KEY", "KEYSPACE",
				"LEVEL", "LIMIT", "LOCAL_ONE", "LOCAL_QUORUM", "MODIFY",
				"RECURSIVE", "SUPERUSER", "OF", "ON", "ONE", "ORDER",
				"PASSWORD", "PERMISSION", "PERMISSIONS", "PRIMARY", "QUORUM",
				"REVOKE", "SCHEMA", "SELECT", "SET", "STORAGE", "SUPERUSER",
				"TABLE", "TEXT", "TIMESTAMP", "TIMEUUID", "THREE", "TOKEN",
				"TRUNCATE", "TTL", "TWO", "TYPE", "UPDATE", "USE", "USER",
				"USERS", "USING", "UUID", "VALUES", "VARCHAR", "VARINT",
				"WHERE", "WITH", "WRITETIME", "DISTINCT"));

		operators.addAll(Arrays.asList("=", "*", "=", "-", "+", "/", "%", ".",
				"^", "(", ")", "{", "}", "[", "]", "|", "&", ",", ":", ";"));
	}

	public String fillParameters(String query, Object[] parameters)
			throws CEUException {

		List<TokenQuery> tokens = lexicAnalizer(query);

		int count = countInjectValues(tokens);

		if (count != parameters.length) {
			throw new CEUException("Wrong number of parameters in : " + query
					+ " (" + parameters.toString() + ")");
		}

		StringBuffer ret = new StringBuffer();

		int indexParameter = 0;

		boolean whereFound = false;

		for (TokenQuery token : tokens) {
			if (token.getType() == TokenQuery.VALUE_INJECT) {
				if (indexParameter >= parameters.length) {
					throw new CEUException("Wrong number of parameters in : "
							+ query + " (" + parameters.toString() + ")");
				}
				Object parameter = parameters[indexParameter++];
				if (parameter == null) {
					if (whereFound) {
						throw new CEUException(
								"In WHERE clausule, parameter cannot be null : "
										+ query + " (" + parameters.toString()
										+ ")");
					}
					ret.append("null");
				} else {
					ret.append(format(parameter));
				}
			} else {
				if (token.getType() == TokenQuery.WHERE) {
					whereFound = true;
				}
				ret.append(token.getContent());
			}
		}

		return ret.toString().trim();
	}

	private List<TokenQuery> lexicAnalizer(String query) {

		List<TokenQuery> tokens = new ArrayList<>();
		query = query.trim();

		StringBuffer buffer = new StringBuffer();
		boolean innerString = false;

		TokenQuery lastToken = null;

		for (int index = 0; index < query.length(); index++) {
			String letter = query.substring(index, index + 1);
			String nextLetter = null;
			if ((index + 1) < query.length()) {
				nextLetter = query.substring(index + 1, index + 2);
			}
			buffer.append(letter);
			boolean lastLetter = letter.equals(" ")
					|| (index + 1) == query.length();
			boolean nextIsOperator = operators.contains(nextLetter);
			boolean isOperator = operators.contains(letter);
			boolean isSpace = letter.equals(" ");
			boolean createSpace = false;
			lastLetter = lastLetter || (!innerString && isOperator);
			lastLetter = lastLetter || (!innerString && letter.equals("?"));
			lastLetter = lastLetter
					|| (!innerString && !letter.equals("'") && nextIsOperator);

			if (!innerString && lastLetter) {
				String word = buffer.toString().trim();

				TokenQuery token = createNextToken(lastToken);
				lastToken = token.getLastToken();
				token.setContent(word);

				if (word.equalsIgnoreCase("where")) {
					token.setType(TokenQuery.WHERE);
				} else if (word.equalsIgnoreCase("?")) {
					token.setType(TokenQuery.VALUE_INJECT);
				} else if (reservedWords.contains(word.toUpperCase())) {
					token.setType(TokenQuery.OTHER_RESERVED_WORD);
				} else if (operators.contains(word)) {
					token.setType(TokenQuery.OPERATOR);
				} else if (word.trim().isEmpty() && isSpace) {
					token.setType(TokenQuery.SPACE);
					token.setContent(" ");
					createSpace = true;
				} else {
					if (CEUConfig.isAutoWrapItemName) {
						token.setContent("\"" + token.getContent() + "\"");
					}
					token.setType(TokenQuery.ITEM_NAME);
				}

				buffer = new StringBuffer();
				if (word.trim().isEmpty()
						&& token.getType() != TokenQuery.SPACE) {
					continue;
				}
				lastToken = addToken(tokens, token);

				if (!isSpace || createSpace) {
					continue;
				}

				token = createNextToken(lastToken);
				token.setType(TokenQuery.SPACE);
				token.setContent(" ");
				lastToken = addToken(tokens, token);
			} else if (!innerString && letter.equals("'")) {
				innerString = true;
			} else if (innerString && letter.equals("'")) {

				if (nextLetter == null || !nextLetter.equals("'")) {
					String word = buffer.toString().trim();
					TokenQuery token = createNextToken(lastToken);

					token.setContent(word);
					token.setType(TokenQuery.STRING);
					lastToken = addToken(tokens, token);
					buffer = new StringBuffer();
					innerString = false;
				}
			}
		}

		return removeDoubleSpaces(tokens);
	}

	private List<TokenQuery> removeDoubleSpaces(List<TokenQuery> tokens) {

		TokenQuery lastToken = null;
		List<TokenQuery> filtered = new ArrayList<>();
		for (TokenQuery token : tokens) {
			if (lastToken != null && lastToken.getType() == TokenQuery.SPACE
					&& token.getType() == TokenQuery.SPACE) {
				continue;
			}
			lastToken = token;
			filtered.add(token);
		}
		return filtered;
	}

	private TokenQuery addToken(List<TokenQuery> tokens, TokenQuery token) {
		TokenQuery lastToken;
		tokens.add(token);
		lastToken = token;
		return lastToken;
	}

	private TokenQuery createNextToken(TokenQuery lastToken) {
		TokenQuery token = new TokenQuery();
		if (lastToken != null) {
			lastToken.setNextToken(token);
		}
		token.setLastToken(lastToken);
		return token;
	}

	public String format(Object value) throws CEUException {
		if (value instanceof String) {
			value = ((String) value).replace("'", "''");
			return "'" + value + "'";
		}
		if (value instanceof Integer || value instanceof Long
				|| value instanceof Short || value instanceof Byte
				|| value instanceof Float || value instanceof Double
				|| value instanceof Boolean) {
			return String.valueOf(value);
		}

		if (List.class.isAssignableFrom(value.getClass())) {
			StringBuffer json = new StringBuffer();
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) value;

			for (Object subvalue : list) {
				if (json.length() == 0) {
					json.append("[");
				} else {
					json.append(",");
				}
				json.append(format(subvalue));
			}
			json.append("]");
			return json.toString();
		}

		if (Set.class.isAssignableFrom(value.getClass())) {
			StringBuffer json = new StringBuffer();
			@SuppressWarnings("unchecked")
			Set<Object> list = (Set<Object>) value;

			for (Object subvalue : list) {
				if (json.length() == 0) {
					json.append("{");
				} else {
					json.append(",");
				}
				json.append(format(subvalue));
			}
			json.append("}");
			return json.toString();
		}

		if (Map.class.isAssignableFrom(value.getClass())) {
			StringBuffer json = new StringBuffer();
			@SuppressWarnings("unchecked")
			Map<Object, Object> list = (Map<Object, Object>) value;

			Set<Object> keys = list.keySet();

			for (Object key : keys) {
				Object subvalue = list.get(key);
				if (json.length() == 0) {
					json.append("{");
				} else {
					json.append(",");
				}
				json.append(format(key));
				json.append(":");
				json.append(format(subvalue));
			}
			json.append("}");
			return json.toString();
		}

		if (value instanceof Date) {
			Date date = (Date) value;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			return "'" + sdf.format(date) + "'";
		}
		throw new CEUException("Type unknown of "
				+ value.getClass().getCanonicalName());
	}

	private int countInjectValues(List<TokenQuery> tokens) {
		int count = 0;
		for (TokenQuery token : tokens) {
			if (token.getType() == TokenQuery.VALUE_INJECT) {
				count++;
			}
		}
		return count;
	}
}
