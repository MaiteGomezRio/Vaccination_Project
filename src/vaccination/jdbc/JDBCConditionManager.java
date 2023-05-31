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
			Statement s = c.createStatement();
			String sql = "UPDATE Patient_Condition SET condition_id = ? WHERE patient_id = ? ";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, c_id);
			p.setInt(2, p_id);
			s.execute(sql); 
			p.close();
			s.close(); 			
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

	/*SELECT v_id FROM Vaccine JOIN Disease ON Vaccine.disease_id = Disease.id 
	JOIN Condition ON vaccine.condition_id = condition.id
	JOIN Patient ON Patient.condition_id = */
	
	//selects vaccine for this disease that do not have the patient conditions that matches the vaccines conditions. 
	//1) encontrar las vacunas que match las condiciones que tiene el paciente
	//2) no seleccionar esas vacunas
	//3) de las restantes, elegir las que disease_id = ?
	
	/*SELECT v_id FROM Vaccine JOIN Disease ON Vaccine.disease_id = Disease.id 
	JOIN Vaccine_Condition ON Vaccine.id=Vaccine_Condition.vaccine_id
	JOIN Condition ON Vaccine_Condition.condition_id = Condition.id
	JOIN Patient_Condition ON Patient_Condition.condition_id=Condition.id
	WHERE disease_id = ? AND condition_id!=(SELECT condition_id FROM Patient_Condition WHERE )*/
	
	public Vaccine getVaccineDependingOnCondition(int d_id, int p_id) {
		try {
			String sql = "SELECT vaccine_id"
					+ " FROM Vaccine"
					+ " JOIN Vaccine_Condition ON Disease_Vaccine.vaccine_id = Vaccine_Condition.vaccine_id"
					+ " JOIN Patient_Condition ON Patient_Condition.condition_id = Vaccine_Condition.condition_id"
					+ " WHERE disease_id = ? AND patient_id = ? AND vaccine_id NOT IN (SELECT condition_id FROM Vaccine_Condition)";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setInt(1, d_id); 
			p.setInt(2, p_id);
			ResultSet rs = p.executeQuery(); 
			rs.next(); 
		    int id = rs.getInt("vaccine_id"); 
		    Vaccine vaccine = new Vaccine(id);
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
