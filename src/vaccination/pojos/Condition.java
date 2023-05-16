package vaccination.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Condition implements Serializable{
	
	private static final long serialVersionUID = 2436972428011759518L;
	protected String type;
	private int c_id;
	private List<Vaccine> vaccines;
	private List<Disease> diseases; 
	

	public Condition(int c_id, String type) {
		super();
		this.c_id=c_id;
		this.type = type;	
		vaccines = new ArrayList<Vaccine>();
		diseases = new ArrayList<Disease>();
	}

	public Condition(String type) {
		super();
		this.type = type;
		vaccines = new ArrayList<Vaccine>();
		diseases = new ArrayList<Disease>();
	}

	public Condition(int c_id) {
		super();
		this.c_id = c_id;
		vaccines = new ArrayList<Vaccine>();
		diseases = new ArrayList<Disease>();
	}
	@Override
	public String toString() {
		return "Condition [type=" + type + "]";
	}



	public String getType() {
		return type;
	}

	public int getId() {
		return c_id;
	}

	public void setId(int c_id) {
		this.c_id = c_id;
	}

	public void setType(String type) {
		this.type = type;
	}
}
