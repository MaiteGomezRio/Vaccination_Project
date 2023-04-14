package vaccination.pojos; 

public class Vaccine {

	private String name; 
    private Integer dose; 
    private Patient patient; 
    
    //nose si es esta clase hay que poner hashcode y equals porque un paciente se puede poner la misma vacuna dos veces 
    
    public Vaccine() {
    	super(); 
    }
    public Vaccine(String name, Integer dose, Patient patient) {
    	this.name = name; 
    	this.dose = dose; 
    	this.patient = patient; 
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
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}	
}
