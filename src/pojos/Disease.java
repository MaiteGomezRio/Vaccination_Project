package pojos;

public class Disease {

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
