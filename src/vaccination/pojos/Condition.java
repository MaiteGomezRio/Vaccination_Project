package vaccination.pojos;

import java.io.Serializable;

public class Condition implements Serializable{
	
	private static final long serialVersionUID = 2436972428011759518L;
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
