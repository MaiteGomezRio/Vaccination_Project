package vaccination.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Appointment implements Serializable{
	
	private static final long serialVersionUID = -8007500555158752133L;
	private int id;
	private Date date;
	private Doctor doctor;
	private Patient patient; 
	private Vaccine vaccine; 

	
	public Appointment() {
		super(); 
	}
	public Appointment(Date date) {
		super(); 
		this.date = date; 
	}
	//TODO EL DOCTOR SE ASIGNA AUTOMATICAMENTE??
	public Appointment(Date date, Patient patient, Vaccine vaccine) {
		super();
	
		this.date = date;
		this.patient = patient;
		this.vaccine = vaccine;
	}
	
	public Appointment(Date date, Doctor doctor, Patient patient, Vaccine vaccine) {
		super();
		this.date = date;
		this.doctor = doctor;
		this.patient = patient;
		this.vaccine = vaccine;

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {


		return "Puts [id=" + id + ", date=" + date + ", doctor=" + doctor + ", patient=" + patient + ", vaccine="+vaccine;


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
		this.vaccine=vaccine;
	}
}
