package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.DiseaseManager;
import vaccination.pojos.Disease;
import vaccination.pojos.Patient;
import vaccination.pojos.Vaccine;

public class JDBCDiseaseManager implements DiseaseManager{
	
	private Connection c;

	public JDBCDiseaseManager(Connection c) {
		this.c = c;

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
	public void assignDiseaseToVaccine(int d_id, int v_id) {
		try {
			String sql = "INSERT INTO Patient_Disease (patient_id, disease_id) VALUES (?,?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, d_id);
			p.setInt(2, v_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Disease> searchDiseaseByVaccine(int vaccine_id) {
		
		List<Disease> list = new ArrayList<Disease>();
		try {
			String sql = "SELECT disease_id FROM Disease_Vaccine WHERE vaccine_id LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, "%" + vaccine_id + "%"); 
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				String disease_name = rs.getString("name");
				Disease disease=new Disease(disease_name);
				list.add(disease);
			}
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list;
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

	@Override
	public List<Disease> searchDiseasesByPatient(int p_id) {
		List<Disease> list = new ArrayList<Disease>();
		try {
			String sql = "SELECT disease_id FROM Patient_Disease WHERE patient_id LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, p_id); 
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				String disease_name = rs.getString("name");
				Disease disease=new Disease(disease_name);
				list.add(disease);
			}
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list;
		
	}



}
