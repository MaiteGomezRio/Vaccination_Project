package vaccination.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class Doctor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4716008704423906822L;

	private int id;
	private String id_document;
	private String name;
	private String surname;
	private String email; 
	private List<Patient> patients;

	public Doctor() {
		super();
		patients = new ArrayList<Patient>();
	}
	
	public Doctor(int id) {
		this.id=id;
		this.id_document=this.getId_document();
		this.name=this.getName();
		this.surname=this.getSurname();
		patients = new ArrayList<Patient>();
	}

	public Doctor(int id, String id_document, String name, String surname) { 
		super();
		this.id = id;
		this.id_document=id_document;
		this.name = name;
		this.surname = surname;
		patients = new ArrayList<Patient>(); // ALWAYS INITIALIZE LISTS
	}
	public Doctor(String id_document, String name, String surname, String email) {
		super();
		this.id_document=id_document;
		this.name = name;
		this.surname = surname;
		this.email = email; 
		patients = new ArrayList<Patient>();
	}
	public Doctor(int id, String id_document, String name, String surname, String email) {
		super();
		this.id = id;
		this.id_document = id_document;
		this.name = name;
		this.surname = surname;
		this.email = email;
		patients = new ArrayList<Patient>();
	}

	@Override
	public String toString() {
		return "Doctor [id_document:" + id_document + ", name: " + name + ", surname: " + surname + ", patients: " + patients + "]";
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public void addPatient(Patient patient) {
		if (!patients.contains(patient)) {
			patients.add(patient);
		}
	}

	public void removePatient(Patient patient) {
		if (patients.contains(patient)) {
			patients.remove(patient);
		}
	}

	public int getId() {
		return id;
	}
	
	public String getId_document() {
		return id_document;
	}

	public void setId(int id) {
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
		if (this == obj) { // Checks if both objects have the same memory reference (the same piece of
							// paper)
			return true;
		} else if (getClass() != obj.getClass()) { // If not, check if both objects are of the same class
			return false;
		}
		Doctor other = (Doctor) obj; // If they are, cast the other object to this class
		return Objects.equals(id, other.id); // Compare the appropriate attributes
	}
	// methods hashcode and equals are already implemented in classes such as
	// Integer, String etc,
	// but not in the classes we create such as Doctor, this is why we need to add
	// those methods.
	// These methods make sure that we are not adding to the list two objects that
	// are the same.

	// TODO finish class
	
	
}
