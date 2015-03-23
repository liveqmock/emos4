package com.ultrapower.eoms.common.core.exception;

/**
 * Service层公用的Exception. 继承自RuntimeException,会触发Spring的事务管理引起事务回退.
 * 
 * @author ANDY
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9090844594606802795L;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public String getErrorMes() {
		String mess = null;
		return mess;
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

}
