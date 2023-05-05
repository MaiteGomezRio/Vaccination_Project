package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import vaccination.ifaces.PatientManager;
import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Doctor;
import vaccination.pojos.Patient;

public class JDBCPatientManager implements PatientManager {
	
	private Connection c;

	public JDBCPatientManager(Connection c) {
		this.c = c;

	}

	@Override
	public void insertPatient(Patient patient) {
		try {
			Statement s = c.createStatement(); //TODO change it into a PreparedSTatement?
			String sql = "INSERT INTO Patient (id_document, name, surname, doctor, disease, condition) VALUES ('" + patient.getId_document() + "', "
					+ patient.getName() + ", '" + patient.getSurname() + ",'"+patient.getDoctor() + 
					patient.getDisease() + ", '"+patient.getCondition()+"')"; 
			s.executeUpdate(sql); 
			s.close(); 
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		//TODO  when inserting a patient, shoud we insert it also on the table patientDoctor, and patientVaccine?
	}
	@Override
	public List<Patient> searchPatientByName(String name) {
		Patient patient = null;
		List<Patient> list_patients=new LinkedList<>();
		try {
			String sql = "SELECT * FROM Patient WHERE name LIKE ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, "%"+name+"%");   // the percentages are so it looks for every name that contains that word. Ex: if you type dri it looks for rodrigo too. 
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				if(name.equals(patient.getName())) {
					int id = rs.getInt("id"); 
					String surname = rs.getString("surname");
					patient = new Patient(id, name, surname); 
					list_patients.add(patient);
				}
				
			}
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list_patients; 
	}
	
	@Override
	public void removePatient(Patient patient) {
		
	}
	
	
	
	
	@Override
	//This method is used by the patient, when he wants to see his information. 
	public Patient getPatientBeingAPatient(int p_id) {
		try {
		String sql = "SELECT * FROM Patient WHERE id LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setInt(1, p_id); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  //since there is only one unique result.
        String id_document=rs.getString("id_document");
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        int doctor_id = rs.getInt("doctor_id");
        Doctor doctor = new Doctor(doctor_id); 
        String d_name = rs.getString("d_name");
        Disease disease = new Disease(d_name); 
        String c_name = rs.getString("c_name"); 
        Condition condition = new Condition(c_name); 
        Patient patient = new Patient(p_id,id_document,name, surname, disease, condition); 
       
        return patient; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
	@Override
	//This method is used by the doctor, when he wants to see the information of one patient. 
	public Patient getPatientBeingADoctor(int p_id) {
		try {
		String sql = "SELECT * FROM Patient WHERE id LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setInt(1, p_id); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  //since there is only one unique result.
        String id_document=rs.getString("id_document");
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        String d_name = rs.getString("d_name");
        Disease disease = new Disease(d_name); 
        String c_type = rs.getString("c_type"); 
        Condition condition = new Condition(c_type); 
        Patient patient = new Patient(p_id,id_document, name, surname, disease, condition); 
        return patient; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}

	@Override//i think this method will make sense later, when we link condition to patient and vaccine
	public Condition getCondition(int c_id){
		try {
			String sql = "SELECT * FROM Condition WHERE type LIKE ?";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, c_id); 
			ResultSet rs = p.executeQuery(); 
	       String type = rs.getString("type"); 
	        Condition condition=new Condition(c_id, type);
	        return condition; 
	        
			}catch(SQLException e) {
				System.out.println("database error");
				e.printStackTrace();
			}
			return null; 
	}
	//TODO think how to ask and get the condition: id? type?
	

	@Override   //i think this method will make sense later, when we link disease to patient and vaccine
	public Disease getDisease(String name) {
		try {
			String sql = "SELECT * FROM Disease WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, name); 
			ResultSet rs = p.executeQuery(); 
	        String n = rs.getString("name"); 
	        Disease disease=new Disease(n);
	        return disease; 
	        
			}catch(SQLException e) {
				System.out.println("database error");
				e.printStackTrace();
			}
			return null; 
	}
	
}
