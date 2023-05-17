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
			Statement s = c.createStatement(); 
			String sql = "INSERT INTO Patient (id_document, name, surname) VALUES ('" + patient.getId_document() + "', '"
					+ patient.getName() + "', '" + patient.getSurname() + "')";
			s.execute(sql);
			s.close(); 
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	 
	
	
	@Override
	public List<Patient> searchPatientByDoctor(int d_id) {
		
		List<Patient> patients_list=new LinkedList<>();
		try {
			String sql = "SELECT * FROM Patient WHERE doctor LIKE ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, "%"+d_id+"%");   // the percentages are so it looks for every name that contains that word. Ex: if you type dri it looks for rodrigo too. 
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				Integer id=rs.getInt("id");
				String id_document=rs.getString("id_document");
				String name = rs.getString("surname");
				String surname = rs.getString("surname");
				Patient patient = new Patient(id,id_document, name, surname); 
				
				patients_list.add(patient);
			}	
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return patients_list; 
	}
	
	
	@Override
	//TODO HACER ESTE GETPATIENT
	public Patient getPatient(int p_id) {
		try {
		String sql = "SELECT * FROM Patient WHERE id LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setInt(1, p_id); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  //since there is only one unique result.
        String id_document=rs.getString("id_document");
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        Integer doctor_id = rs.getInt("doctor_id");
        Doctor doctor = new Doctor(doctor_id); 
        String d_name = rs.getString("d_name");
        Disease disease = new Disease(d_name); 
        String c_name = rs.getString("c_name"); 
        Condition condition = new Condition(c_name); 
        Patient patient = new Patient(p_id,id_document,name, surname,disease,condition);
        return patient; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}

	/*@Override
	public void assignDiseaseToPatient(int p_id, int d_id) {
		try {
			String sql = "INSERT INTO Patient_Disease (patient_id, disease_id) VALUES (?,?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, p_id);
			p.setInt(2, d_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		
	}*/
	@Override
	public List<Patient> searchPatientByName(String name){
		List<Patient> list = new ArrayList<Patient>(); 
		try {
			String sql = "SELECT * FROM Patient WHERE name LIKE ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, "%"+name+"%");   // the percentages are so it looks for every name that contains that word. Ex: if you type dri it looks for rodrigo too. 
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String id_document=rs.getString("id_document");
				String n = rs.getString("name"); 
				String surname= rs.getString("surname");
				String email = rs.getString("email"); 
				Patient patient = new Patient(id, id_document,n, surname, email); 
				list.add(patient); 
			}
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list; 
	}
	@Override
	public Patient getPatientByEmail(String email) {
		try {
		String sql = "SELECT * FROM Patient WHERE email LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setString(1, email); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  
        int id = rs.getInt("p_id"); 
        String id_document=rs.getString("id_document");
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        Patient patient = new Patient(id, id_document,name, surname, email); 
        rs.close();
        p.close(); 
        return patient; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
}
