package vaccination.pojos;

import java.io.Serializable;
import java.util.List;

public class Vaccine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8790553409797612746L;
	private int id;
	private String name; 
    private Integer dose; 
    private List<Patient> patients;
    private Disease disease; 
    private List<Condition> conditions; 
    
    //nose si es esta clase hay que poner hashcode y equals porque un paciente se puede poner la misma vacuna dos veces 
    
    public Vaccine() {
    	super(); 
    }
    public Vaccine( String name, Integer dose) {    	
    	this.name = name; 
    	this.dose = dose; 
    }
    
    public Integer getId() {
		return id;
	}
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDose() {
		return dose;
	}
	public void setDose(Integer dose) {
		this.dose = dose;
	}
}
