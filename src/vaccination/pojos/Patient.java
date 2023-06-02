package vaccination.pojos; 

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType; 

@XmlAccessorType(XmlAccessType.FIELD)//we must annotate patient because the xml is of doctor and we want to include the list of patients
@XmlRootElement(name = "Patient")
@XmlType(propOrder = { "id_document", "name", "surname", "email"})

public class Patient implements Serializable{
    
	private static final long serialVersionUID = 3148378678755598680L;
	
	@XmlTransient
	private int id; 
	@XmlAttribute
	private String id_document;
	@XmlAttribute
	private String name; 
	@XmlAttribute
	private String surname;
	@XmlAttribute
	private String email;
	@XmlTransient
	private List<Vaccine> vaccines;
	@XmlTransient
	private Doctor doctor; //transient because it would be an infinite loop	
	@XmlTransient
    private List<Condition> conditions; 
	@XmlTransient
    private List<Appointment> appointments; 

	
	
	public Patient () {
		super(); 
		vaccines = new ArrayList<Vaccine>(); 	
		conditions= new ArrayList<Condition>();
	}
	public Patient(int id) {
		this.id = id; 	
		vaccines = new ArrayList<Vaccine>();   
		conditions= new ArrayList<Condition>();
	}
	public Patient(int id, String name, String surname) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();
		conditions= new ArrayList<Condition>();

	}
	
	public Patient(String id_document, String name, String surname, String email,Doctor doctor) {
		
		super();
		this.id_document = id_document; 
		this.name = name; 
		this.surname = surname;
		this.email=email;
		this.doctor=doctor;
		vaccines = new ArrayList<Vaccine>();	
		conditions= new ArrayList<Condition>();

	}
	

	public Patient(int id, String id_document, String name, String surname, String email) {
		super();
		this.id = id;
		this.id_document = id_document;
		this.name = name;
		this.surname = surname;
		this.email = email;
		vaccines = new ArrayList<Vaccine>(); 		
		conditions= new ArrayList<Condition>();
	}
	public Patient(int id,String id_document, String name, String surname) {
		this.id = id; 
		this.id_document=id_document;
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>(); 	
		conditions= new ArrayList<Condition>();
	}
	
	
	public Patient(int id, String id_document, String name, String surname, Doctor doctor) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		vaccines = new ArrayList<Vaccine>();   
		conditions= new ArrayList<Condition>();
		this.doctor = doctor; 
	}
	
	
	
	

	@Override
	public String toString() {
		return "Patient [id=" + id + ", id_document=" + id_document + ", name=" + name + ", surname=" + surname + "]";
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
	
	public List<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
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
