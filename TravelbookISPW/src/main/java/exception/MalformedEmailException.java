package main.java.exception;

public class MalformedEmailException extends Exception {

	private static final long serialVersionUID = 1L;
	public MalformedEmailException(String msg) {
		super(msg);
	}

}
