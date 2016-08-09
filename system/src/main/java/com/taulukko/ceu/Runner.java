package com.taulukko.ceu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

import com.taulukko.ceu.data.Connection;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.handler.Handler;

import cql.Token;
import cql.TokenType;
import cql.lexicalparser.ConsumerToken;
import cql.lexicalparser.LexicalParser;
import cql.lexicalparser.exceptions.CQLReplaceException;
import cql.lexicalparser.exceptions.LexicalParserException;

public class Runner {

	private Connection con = null;

	public Runner(Connection con) {
		this.con = con;
	}

	public void exec(Command command) throws CEUException {

		String query = command.getQuery();
		Object parameters[] = command.getParameters();
		query = fillParameters(query, parameters);

		con.execute(query); 
	}

	public <T> Optional<T> query(Command command, Handler<T> handler)
			throws CEUException {
		String query = command.getQuery();
		Object parameters[] = command.getParameters();
		query = fillParameters(query, parameters);

		ResultSet resultset = con.execute(query);

		return handler.convert(resultset);
	}

	public static String fillParameters(String query, Object[] parameters)
			throws CEUException {

		LexicalParser parser = new LexicalParser();
		Token cql = null;

		try {
			cql = parser.isCQL(query);
		} catch (LexicalParserException e) {
			throw new CEUException("LexicalParserException", e);
		}

		if (cql == null) {
			throw new CEUException("LexicalParserException : command [" + query
					+ "] hasn't a CQL Query");
		}

		int injectCount = cql.count(TokenType.INJECT);

		if (injectCount != parameters.length) {
			throw new CEUException("Wrong number of parameters in : " + query
					+ " (" + parameters.toString() + ")");
		}

		for (int index = 0; index < injectCount; index++) {
			try {
				cql.replace(TokenType.INJECT, parameters[index], index);
			} catch (CQLReplaceException e) {
				throw new CEUException(
						"Error in replace inject parameter index [" + index
								+ "]");
			}
		}

		ConsumerToken process = null;

		if (CEUConfig.isAutoWrapItemName) {
			process = t -> {
				if (t.getSubTokens().size() > 0
						&& t.getSubTokens().get(0).getType() == TokenType.ITEM_NAME_CASE_INSENSITIVE) {
					t.setContent("\"" + t.getContent() + "\"");
				}
			};

		} else {
			process = t -> {
				if (t.getSubTokens().size() > 0
						&& t.getSubTokens().get(0).getType() == TokenType.ITEM_NAME_CASE_INSENSITIVE) {
					t.setContent(t.getContent().toLowerCase());
				}
			};

		}

		try {
			cql.replaceAll(TokenType.ENTITY_NAME, process);
		} catch (CQLReplaceException e) {
			e.printStackTrace();
			throw new CEUException("Error in auto Wrapper  [" + cql + "] ");
		}

		return cql.getContent();
	}

	public static String format(Object value) throws CEUException {
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
}
