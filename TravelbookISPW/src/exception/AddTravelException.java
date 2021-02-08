package exception;

public class AddTravelException extends DBException {
	private static final long serialVersionUID = 1L;
	private Integer id;
	public AddTravelException(String msg) {
		super(msg);
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id=id;
	}
}
