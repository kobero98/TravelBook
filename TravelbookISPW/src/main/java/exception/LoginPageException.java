package main.java.exception;


public class LoginPageException extends DBException{

	private static final long serialVersionUID = 1L;

	public LoginPageException(String errorMessage) {
		super(errorMessage);
	}
}
