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
import vaccination.pojos.Doctor;
import vaccination.pojos.Vaccine;

public class JDBCConditionManager implements ConditionManager{
	
	private Connection c;

	public JDBCConditionManager(Connection c) {
		this.c = c;

	}
	@Override
	public List<Condition> checkConditionsOfAVaccine(int v_id) {
		List<Condition> list = new ArrayList<Condition>();
		try {
			String sql = "SELECT condition_id FROM Patient_Condition_Vaccine WHERE vaccine_id = ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, v_id);   
			ResultSet rs = p.executeQuery(); 
			while(rs.next()) {
				int id = rs.getInt("condition_id");
				Condition condition = new Condition(id); 
				list.add(condition); 
			}
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return list; 
	}
	public Condition getCondition(String type){

		try {
			String sql = "SELECT * FROM Condition WHERE type LIKE ?";
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
			Statement s = c.createStatement();
			String sql = "UPDATE Patient_Condition SET condition_id = ? WHERE patient_id = ? ";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, c_id);
			p.setInt(2, p_id);
			s.execute(sql); 
			s.close(); 			
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	public Vaccine getVaccineDependingOnCondition(int d_id, int c_id) {
		try {
			String sql = "SELECT vaccine_id FROM Disease_Vaccine JOIN Vaccine_Condition ON Disease_Vaccine.vaccine_id = Vaccine_Condition.vaccine_id JOIN Patient_Condition ON Patient_Condition.condition_id = Vaccine_Condition.condition_id WHERE disease_id = ? AND vaccine_id NOT CONTAINT condition_id = ?";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, d_id); 
			p.setInt(2, c_id);
			ResultSet rs = p.executeQuery(); 
			rs.next(); 
		    int id = rs.getInt("vaccine_id"); 
		    Vaccine vaccine = new Vaccine(id);
		    return vaccine;
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		return null; 
	}
}
