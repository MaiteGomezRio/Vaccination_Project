package vaccination.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Doctor")
@XmlType(propOrder = { "id_document", "name", "surname", "email", "patients" })
public class Doctor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4716008704423906822L;
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
	@XmlElement(name="Patient")
	@XmlElementWrapper(name="Patients")
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
		return "Doctor [id:" + id + ", id_document:" + id_document + ", name: " + name + ", surname: " + surname + ", patients: " + patients + "]";
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
		if (this == obj) { 
			return true;
		} else if (getClass() != obj.getClass()) { 
			return false;
		}
		Doctor other = (Doctor) obj;
		return Objects.equals(id, other.id); 
	}
}
