package vaccination.pojos;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Disease implements Serializable{

	private static final long serialVersionUID = -6053416530349593639L;
	private int d_id;
	private String name;
	private List<Vaccine> vaccines;
	private List<Patient> patients; 

	public Disease(String name) {
		super();
		this.name = name;
	}
	public Disease(int d_id) {
		super();
		this.d_id = d_id;
	}
	public Disease(int d_id,String name) {
		super();
		this.d_id=d_id;
		this.name = name;
	}

	
	
	@Override
	public String toString() {
		return "Disease [name=" + name + "]";
	}

	public String getName() {
		return name;
	}
	public int getId() {
		return d_id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
