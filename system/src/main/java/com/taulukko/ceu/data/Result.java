package com.taulukko.ceu.data;


public interface Result {

	  

	public ResultSet getResultset();
	
	
	public default boolean isEmpty(){
		return this.getResultset().isEmpty();
	}
 
}
