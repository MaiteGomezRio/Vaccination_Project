package vaccination.pojos;

public class Condition {
	
	protected String type;
	
	

	public Condition(String type) {
		super();
		this.type = type;
	}



	@Override
	public String toString() {
		return "Condition [type=" + type + "]";
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}
	
	

}
