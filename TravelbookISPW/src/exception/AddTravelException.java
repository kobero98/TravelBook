package exception;

public class AddTravelException extends DBException {
	private static final long serialVersionUID = 1L;
	private final Integer id;
	public AddTravelException(String msg,Integer id) {
		super(msg);
		this.id=id;
	}
	public Integer getId() {
		return this.id;
	}

}
