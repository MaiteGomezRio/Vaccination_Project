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
import vaccination.pojos.Vaccine;

public class JDBCPatientManager implements PatientManager {
	
	private Connection c;

	public JDBCPatientManager(Connection c) {
		this.c = c;

	}
	@Override
	public void insertPatient(Patient patient, Doctor doctor) {
		try {
			Statement s = c.createStatement(); 
			String sql = "INSERT INTO Patient (id_document, name, surname, email, doctor_id) VALUES ('" + patient.getId_document() + "', '"
					+ patient.getName() + "', '" + patient.getSurname() +"', '"+patient.getEmail()+ "', '"+doctor.getId()+"')";
			s.execute(sql);
			s.close(); 
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	@Override
	public List<Patient> searchPatientsByDoctor(int d_id) {
		
		List<Patient> patients_list=new LinkedList<>();
		try {
			String sql = "SELECT * FROM Patient WHERE doctor_id LIKE ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, d_id);   // the percentages are so it looks for every name that contains that word. Ex: if you type dri it looks for rodrigo too. 
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				Integer id=rs.getInt("id");
				String id_document=rs.getString("id_document");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				Patient patient = new Patient(id,id_document, name, surname); 
				
				patients_list.add(patient);
			}	
			
			p.close();
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return patients_list; 
	}

	@Override
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
        Patient patient = new Patient(p_id,id_document,name, surname, new Doctor());
        rs.close();
        p.close();
        
		String sql2 = "SELECT * FROM Doctor WHERE id LIKE ?";
		PreparedStatement p2 = c.prepareStatement(sql2); 
		p2.setInt(1, doctor_id); 
		ResultSet rs2 = p2.executeQuery(); 
        rs2.next();   
        Doctor doctor = new Doctor(doctor_id, rs2.getString("id_document"),rs2.getString("name"), rs2.getString("surname"), rs2.getString("email"));
        patient.setDoctor(doctor);
        rs2.close();
        p2.close(); 
        
        
        return patient; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}

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
        int id = rs.getInt("id"); 
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
