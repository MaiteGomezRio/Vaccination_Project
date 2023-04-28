package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			String sql = "INSERT INTO Patient (id, name, surname, doctor, disease, condition) VALUES ('" + patient.getId() + "', "
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
		List<Patient> list = new ArrayList<Patient>(); 
		try {
			String sql = "SELECT * FROM Patient WHERE name LIKE ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, "%"+name+"%");   // the percentages are so it looks for every name that contains that word. Ex: if you type dri it looks for rodrigo too. 
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				String id = rs.getString("id"); 
				String n = rs.getString("name"); 
				String surname = rs.getString("surname");
				//Doctor doctor = rs.getDoctor("doctor"); 
				//Disease disease = rs.getDisease("disease"); 
				//Condition condition = rs.getCondition("condition");
				Patient patient = new Patient(id, n, surname); 
				list.add(patient); 
			}
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list; 
	}
	@Override
	//This method is used by the patient, when he wants to see his information. 
	public Patient getPatientBeingAPatient(String p_id) {
		try {
		String sql = "SELECT * FROM Patient WHERE id LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setString(1, p_id); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  //since there is only one unique result.
        String id = rs.getString("p_id"); 
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        String doctor_id = rs.getString("doctor_id");
        Doctor doctor = new Doctor(doctor_id); 
        String d_name = rs.getString("d_name");
        Disease disease = new Disease(d_name); 
        String c_name = rs.getString("c_name"); 
        Condition condition = new Condition(c_name); 
        Patient patient = new Patient(id, name, surname,doctor, disease, condition); 
        return patient; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
	@Override
	//This method is used by the doctor, when he wants to see the information of one patient. 
	public Patient getPatientBeingADoctor(String p_id) {
		try {
		String sql = "SELECT * FROM Patient WHERE id LIKE ?";
		PreparedStatement p = c.prepareStatement(sql); 
		p.setString(1, p_id); 
		ResultSet rs = p.executeQuery(); 
        rs.next();  //since there is only one unique result.
        String id = rs.getString("p_id"); 
        String name = rs.getString("name"); 
        String surname = rs.getString("surname"); 
        String d_name = rs.getString("d_name");
        Disease disease = new Disease(d_name); 
        String c_name = rs.getString("c_name"); 
        Condition condition = new Condition(c_name); 
        Patient patient = new Patient(id, name, surname, disease, condition); 
        return patient; 
        
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
}
