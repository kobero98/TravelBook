package exception;

import java.io.IOException;

import javafx.application.Platform;

public class MissingPageException extends IOException{
	

	private static final long serialVersionUID = 1L;

	public MissingPageException() {
		super();
	}
	public void exit() {
		new TriggerAlert().triggerAlertCreate("An unexpected problem occurred, the application will be closed", "err").showAndWait();
		Platform.exit();
	    System.exit(0);
	}
}
