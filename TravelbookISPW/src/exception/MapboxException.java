package exception;

public class MapboxException extends Exception {
	private static final long serialVersionUID = 1L;
	public MapboxException(String motive) {
		super("Unable to connect with Mapbox due to "+motive+" error");
	}
}
