package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.ConditionManager;
import vaccination.pojos.Condition;
import vaccination.pojos.Doctor;

public class JDBCConditionManager implements ConditionManager{
	
	private Connection c;

	public JDBCConditionManager(Connection c) {
		this.c = c;

	}
	@Override
	public List<Condition> checkConditionsOfAVaccine(int v_id) {
		List<Condition> list = new ArrayList<Condition>();
		try {
			String sql = "SELECT Condition FROM Patient_Condition_Vaccine WHERE vaccine_id = ?"; 
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
	
}
