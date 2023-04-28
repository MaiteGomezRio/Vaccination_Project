package vaccination.pojos; 

import java.util.ArrayList;
import java.util.List;
import java.util.Objects; 

public class Patient {
    
	private String id; 
	private String name; 
	private String surname; 
	private List<Vaccine> vaccines;
	private Doctor doctor; 
	
	
	public Patient () {
		super(); 
		vaccines = new ArrayList<Vaccine>(); 
	}
	public Patient(String id, String name, String surname, Boolean attendance) {
		this.id = id; 
		this.name = name; 
		this.surname = surname;
		this.attendance = attendance; 
		this.doctor = doctor;
		vaccines = new ArrayList<Vaccine>();  //ALWAYS INITIALIZE THE LISTS  
	}
	@Override 
	public String toString() {
		return "Patient [id: "+id+", name: "+name+" ,surname: "+surname+" attendance: "+attendance+"]";
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
	public boolean isAttendance() {
		return attendance;
	}
	public void setAttendance(boolean attendance) {
		this.attendance = attendance;
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
