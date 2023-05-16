package vaccination.pojos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Appointment implements Serializable{
	
	private static final long serialVersionUID = -8007500555158752133L;
	private int id;
	private LocalDate date;
	private Doctor doctor;
	private Patient patient; 
	private Vaccine vaccine; 

	
	public Appointment() {
		super(); 
	}
	public Appointment(LocalDate date) {
		super(); 
		this.date = date; 
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Puts [id=" + id + ", date=" + date + ", doctor=" + doctor + ", patient=" + patient + ", vaccine="
				+ vaccine + "]";
	}
    
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient=patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	
	public void setDoctor(Doctor doctor) {
		this.doctor=doctor;
	}
	
	public Vaccine getVaccine() {
		return vaccine;
	}
	
	public void setVaccine(Vaccine vaccine) {
		this.doctor=vaccine;
	}
}
