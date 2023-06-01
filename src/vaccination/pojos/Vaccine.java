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
    public Vaccine( String name, Integer dose, Disease disease) {    	
    	this.name = name; 
    	this.dose = dose; 
    	this.disease=disease;
    }
    public Vaccine(String name, Integer dose, int id) {
    	this.name = name; 
    	this.dose = dose;
    	this.id = id;
    }
    public Vaccine(String name) {
    	this.name=name;
    }
    
    public Vaccine(int id) {
		this.id=id;
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
		
	
	 public Disease getDisease() {
			return disease;
		}
		public void setDisease(Disease disease) {
			this.disease = disease;
		}
 public int getDiseaseId() {
	 return disease.getId();
 }
 
	public Integer getDose() {
		return dose;
	}
	public void setDose(Integer dose) {
		this.dose = dose;
	}
	
	@Override
	public String toString(){
		String text ="Vaccine "+name+": "+ "Number of dose/s: ["+dose+ "] "+"Disease id:"+getDiseaseId(); 
		return text;
	}
}

