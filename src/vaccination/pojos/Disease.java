package vaccination.pojos;

import java.io.Serializable;

public class Disease implements Serializable{

	private static final long serialVersionUID = -6053416530349593639L;
	private String name;

	public Disease(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Disease [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
