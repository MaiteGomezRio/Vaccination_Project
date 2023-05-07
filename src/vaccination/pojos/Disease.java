package vaccination.pojos;

import java.io.Serializable;

public class Disease implements Serializable{

	private static final long serialVersionUID = -6053416530349593639L;
	private int d_id;
	private String name;

	public Disease(String name) {
		super();
		this.name = name;
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
