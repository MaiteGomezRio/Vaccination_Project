package vaccination.pojos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Puts implements Serializable{
	
	private static final long serialVersionUID = -8007500555158752133L;
	private int id;
	private LocalDate date;
	private Doctor doctor;
	private Patient patients; 
	private Vaccine vaccine; 

	
	public Puts() {
		super(); 
	}
	public Puts(LocalDate date) {
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
		return "Puts [id=" + id + ", date=" + date + ", doctor=" + doctor + ", patients=" + patients + ", vaccine="
				+ vaccine + "]";
	}
    
    
}
