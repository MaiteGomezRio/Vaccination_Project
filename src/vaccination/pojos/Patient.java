package vaccination.pojos; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; 

public class Patient implements Serializable{
    
	private static final long serialVersionUID = 3148378678755598680L;
	private String id; 
	private String name; 
	private String surname; 
	private List<Vaccine> vaccines;
	private Doctor doctor; 
	private Disease disease;
    private Condition condition; 

	
	
	public Patient () {
		super(); 
		vaccines = new ArrayList<Vaccine>(); 
	}
	public Patient(String id) {
		this.id = id; 	
		vaccines = new ArrayList<Vaccine>();  //ALWAYS INITIALIZE THE LISTS  
	}
	public Patient(String id, String name, String surname) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();  //ALWAYS INITIALIZE THE LISTS  

	}
	public Patient(String id, String name, String surname, Disease disease, Condition condition) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();  //ALWAYS INITIALIZE THE LISTS  
		this.disease = disease;
		this.condition = condition; 
	}
	public Patient(String id, String name, String surname, Doctor doctor, Disease disease, Condition condition) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();  //ALWAYS INITIALIZE THE LISTS  
		this.disease = disease;
		this.condition = condition; 
		this.doctor = doctor; 
	}
	@Override 
	public String toString() {
		return "Patient [id: "+id+", name: "+name+" ,surname: "+surname+"]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public List<Vaccine> getVaccines() {
		return vaccines;
	}
	public void setVaccines(List<Vaccine> vaccines) {
		this.vaccines = vaccines;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public Disease getDisease() {
		return disease;
	}
	public void setDisease(Disease disease) {
		this.disease = disease;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(id, other.id);
	}
	
}
