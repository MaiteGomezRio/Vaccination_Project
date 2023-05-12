package vaccination.pojos; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; 

public class Patient implements Serializable{
    
	private static final long serialVersionUID = 3148378678755598680L;
	private int id; 
	private String id_document;
	private String name; 
	private String surname; 
	private String email;
	private List<Vaccine> vaccines;
	private Doctor doctor; 
	private List <Disease> diseases;
    private List<Condition> conditions; 

	
	
	public Patient () {
		super(); 
		vaccines = new ArrayList<Vaccine>(); 
		diseases= new ArrayList<Disease>();
		conditions= new ArrayList<Condition>();
	}
	public Patient(int id) {
		this.id = id; 	
		vaccines = new ArrayList<Vaccine>();  //ALWAYS INITIALIZE THE LISTS  
		diseases= new ArrayList<Disease>();
		conditions= new ArrayList<Condition>();
	}
	public Patient(int id, String name, String surname) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();
		diseases= new ArrayList<Disease>();
		conditions= new ArrayList<Condition>();

	}
	
	public Patient(String id_document, String name, String surname, String email) {
		
		super();
		this.id_document = id_document; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();
		diseases= new ArrayList<Disease>();
		conditions= new ArrayList<Condition>();

	}
	

	public Patient(int id, String id_document, String name, String surname, String email) {
		super();
		this.id = id;
		this.id_document = id_document;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	public Patient(int id,String id_document, String name, String surname, Disease disease, Condition condition) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>(); 
		diseases= new ArrayList<Disease>();
		conditions= new ArrayList<Condition>();
	}
	
	
	public Patient(int id, String id_document, String name, String surname, Doctor doctor) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();   
		diseases= new ArrayList<Disease>();
		conditions= new ArrayList<Condition>();
		this.doctor = doctor; 
	}
	
	
	// TODO remove this constructor
	public Patient(String id_document, String name, String surname, Disease disease, Condition condition) {
		
		this.name = name; 
		this.surname = surname; 
		//this.disease = disease;
		//this.condition = condition; 
		 
	}
	
	public Patient(int id,String id_document, String name, String surname) {
		
		this.id_document=id_document;
		this.name = name; 
		this.surname = surname;
	 
	}
	
	@Override 
	public String toString() {
		return "Patient [id document: "+id_document+", name: "+name+" ,surname: "+surname+"]";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getId_document() {
		return id_document;
	}
	
	public void setId_document(String id_document) {
		this.id_document=id_document;
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
	
	public List<Condition> getConditions(){
		return conditions;
	}
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
	
	public List<Disease> getDiseases(){
		return diseases;
	}
	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
