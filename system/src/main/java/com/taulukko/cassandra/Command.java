package com.taulukko.cassandra;

public class Command {

	private String query;
	private Object parameters[];
	private Result result = null;

	public Command(String query, Object... parameters) {
		this.query = query;
		this.parameters = parameters;
	}

	public String getQuery() {
		return query;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}