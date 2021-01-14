package exception;

import java.sql.SQLException;

public class LoginPageException extends SQLException{

	private static final long serialVersionUID = 1L;

	public LoginPageException(String errorMessage) {
		super(errorMessage);
	}
}
