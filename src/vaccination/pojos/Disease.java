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
	public Disease(int d_id,String name) {
		super();
		this.d_id=d_id;
		this.name = name;
	}
	
	//TODO POR QUÉ AQUI UN RESGISTER VACCINE?
	public static void registerVaccine() throws IOException {
		System.out.println("Please, introduce the following information: ");
		System.out.println("name: ");
		String name = r.readLine();
		System.out.println("doses: ");
		Integer dose = Integer.parseInt(r.readLine());
		Vaccine vaccine = new Vaccine(name, dose);
		directorMan.insertVaccine(vaccine);
		System.out.println("Vaccine "+ name+" registered");
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
