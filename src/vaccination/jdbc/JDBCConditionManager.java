package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.ConditionManager;
import vaccination.pojos.Condition;
import vaccination.pojos.Disease;
import vaccination.pojos.Doctor;
import vaccination.pojos.Vaccine;

public class JDBCConditionManager implements ConditionManager{
	
	private Connection c;

	public JDBCConditionManager(Connection c) {
		this.c = c;

	}
	
	public Condition getCondition(String type){

		try {
			String sql = "SELECT * FROM Condition WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, type); 
			ResultSet rs = p.executeQuery();
			rs.next();
		    int c_id=rs.getInt("id");
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
	@Override
	public void updateConditionsOfPatient(int p_id, int c_id) {
		try {
			String sql = "INSERT INTO Patient_Condition (patient_id, condition_id) VALUES (?, ?) ";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, p_id);
			p.setInt(2, c_id);
			p.executeUpdate();
			p.close();		
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	@Override 
	public List<Condition> getConditionsOfPatient(int p_id) {
		List<Condition> conditions = new ArrayList<Condition>();
		try {
			String sql = "SELECT condition_id FROM Patient_Condition WHERE patient_id = ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, p_id);  
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				int id = rs.getInt("condition_id");
				Condition condition = new Condition(id); 
			    conditions.add(condition); 
			}
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return conditions; 
	}
	
	public Vaccine getVaccineDependingOnCondition(int d_id, int p_id) {
		try {
			String sql = "SELECT v.id, v.name, v.dose, v.disease_id FROM Vaccine v LEFT JOIN Vaccine_Condition vc ON v.id = vc.vaccine_id "
					+ "WHERE v.disease_id = ? AND (vc.condition_id NOT IN (SELECT pc.condition_id FROM Patient p INNER JOIN Patient_Condition pc ON p.id = pc.patient_id WHERE p.id = ?) OR vc.condition_id IS NULL)"
					+ " GROUP BY v.id";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, d_id);
			p.setInt(2, p_id); 
			ResultSet rs = p.executeQuery(); 
			rs.next(); 
			Disease disease = new Disease(rs.getInt("disease_id"));
			
		    Vaccine vaccine = new Vaccine(rs.getInt("id"), rs.getString("name"), rs.getInt("dose"), disease);
		    rs.close();
		    p.close();
		    return vaccine; 
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void removeConditionOfPatient(int p_id, int c_id) {
		try {
			String sql = "DELETE condition_id FROM Patient_Condition WHERE condition_id = ? AND patient_id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1,c_id);
			p.setInt(2,p_id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	
	
}
