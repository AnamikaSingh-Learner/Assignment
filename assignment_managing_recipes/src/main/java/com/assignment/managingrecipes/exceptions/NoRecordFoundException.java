package com.assignment.managingrecipes.exceptions;

public class NoRecordFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRecordFoundException(String Message) {
		super(Message);
	}

}
