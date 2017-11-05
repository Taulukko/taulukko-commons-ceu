package com.taulukko.ceu;

import com.taulukko.commons.TaulukkoException;

public class CEUException extends TaulukkoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8188824518240749194L;

	public CEUException(String message, Throwable err) {
		super(message, err);
	}

	public CEUException(Throwable err) {
		super((err.getMessage() != null) ? err.getMessage() : "CEUException",
				err);
	}

	public CEUException(String message) {
		super(message);
	}
}
