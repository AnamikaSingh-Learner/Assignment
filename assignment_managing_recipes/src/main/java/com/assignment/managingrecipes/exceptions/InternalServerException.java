package com.assignment.managingrecipes.exceptions;

public class InternalServerException extends RuntimeException {
	private static final long serialVersionUID = -366232381656044795L;
	private Object[] args;
	/**
	 * @param message
	 */
	public InternalServerException(String message) {
		super(message);
	}
	/**
	 * @param args
	 */
	public InternalServerException(Object[] args) {
		super();
		this.args = args;
	}
	/**
	 * @param message, args
	 */
	public InternalServerException(String message, Object[] args) {
		super(message);
		this.args = args;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
}
