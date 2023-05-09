package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			String sql = "INSERT INTO Patient (id_document, name, surname, doctor) VALUES ('" + patient.getId_document() + "', "
					+ patient.getName() + ", '" + patient.getSurname() + ",'"+patient.getDoctor() +"')";
			s.execute(sql);
			s.close(); 
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	 
	
	@Override
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
	public void removePatient(Patient patient) {
		//TODO remove patient method
	}
	
	
	
	
	@Override
	//This method is used by the patient, when he wants to see his information. 
	public Patient getPatientInfoBeingAPatient(int p_id) {
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
        //Patient patient = new Patient(p_id,id_document,name, surname, doctor); TODO how indicate a doctor
        Patient patient = new Patient(p_id,id_document,name, surname);
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
	public Condition getCondition(String type){

		try {
			String sql = "SELECT * FROM Condition WHERE type LIKE ?";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, type); 
			ResultSet rs = p.executeQuery();
			rs.next();
			Integer c_id=rs.getInt("id");
	        Condition condition=new Condition(c_id,type);
	        rs.close();
	        p.close();
	        return condition; 
	        
			}catch(SQLException e) {
				System.out.println("database error");
				e.printStackTrace();
			}
			return null; 
	}
	
	

	@Override   //i think this method will make sense later, when we link disease to patient and vaccine
	public Disease getDisease(String name) {
		try {
			String sql = "SELECT * FROM Disease WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1,name);
			ResultSet rs = p.executeQuery(); 
			rs.next();
			Integer d_id = rs.getInt("id");
	        Disease disease=new Disease(d_id,name);
	        rs.close();
	        p.close();
	        return disease; 
	        
			}catch(SQLException e) {
				System.out.println("database error");
				e.printStackTrace();
			}
			return null; 
	}
	
}
