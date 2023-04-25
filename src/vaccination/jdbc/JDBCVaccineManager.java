package vaccination.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vaccination.ifaces.VaccineManager;
import vaccination.pojos.Vaccine;

public class JDBCVaccineManager implements VaccineManager {
	
	private Connection c;

	public JDBCVaccineManager(Connection c) {
		this.c = c;

	}
	
	@Override 
	public void removeVaccine(String name) {
		try {
			String sql = "DELETE FROM Vaccine WHERE name = ?"; 
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, name); 
			p.executeUpdate();
			p.close(); 
		}catch(SQLException e) {
			System.out.println("database error"); 
			e.printStackTrace();
		}
	}

	@Override
	public void insertVaccine(Vaccine vaccine) {
		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO Vaccine (id, name, dose, patient) VALUES ('"vaccine.getId()+"',"+vaccine.getName() + "',"
					+vaccine.getDose()+",'"vaccine.getPatient()"')"; 
			s.executeUpdate(sql; 
			s.close();
		}catch(SQLException e ) {
			System.out.println("database exception");
			e.printStackTrace();
		}
		
	}
	@Override
	public List<Vaccine> searchVaccinesByPatient(String p_id) {
		List<Vaccine> list = new ArrayList<Vaccine>(); 
			try {
				String sql = "SELECT name FROM Vaccine JOIN Patient ON Vaccine.patient.id = Patient.id WHERE patient.id LIKE ?"; 
				PreparedStatement p = c.prepareStatement(sql); 
				p.setString(1, "%"+p_id+"%");   // the percentages are so it looks for every name that contains that word. Ex: if you type dri it looks for rodrigo too. 
				ResultSet rs = p.executeQuery(); 
				while(rs.next()) {
					Integer id = rs.getInt("id");
					String n = rs.getString("name"); 
					Integer dose = rs.getInt("dose");
					Vaccine v = new Vaccine(id, name, dose, patient);   //TODO here i dont know if i have to put the patient
					list.add(v); 
				}
			}catch(SQLException e) {
				System.out.println("database error");
				e.printStackTrace();
			}
			return list; 
		}
	}

	@Override
	public Vaccine getVaccine(String name) {    
	    try {
	    	String sql = "SELECT * FROM Vaccine WHERE name LIKE ?"
	        PreparedStatement p = c.prepareStatement(sql); 
	        p.setString(1, name);
	        ResultSet rs = p.executeQuery();
	        rs.next(); 
	        Integer id = rs.getInt("id"); 
	        String n = rs.getString("name"); 
	        Integer dose = rs.getInt("dose"); 
	        Vaccine v = new Vaccine(id, name, dose, patient); 
	        return patient; 
	        //TODO i dont know if i should get the patient
	    }catch(SQLException e) {
	    	System.out.println("database error");
	    	e.printStackTrace();
		return null;
	}

    @Override
    public void assignVaccineToPatient(int v_id, String p_id) {
   	 try {
   	 String sql = "INSERT into Patient_Vaccine(v_name, p_id) WHERE VALUES (?,?)";  //TODO i think we need to create another table that relations patient with vaccine
   	 PreparedStatement p = c.prepareStatement(sql); 
   	 p.setString(1, v_id);
   	 p.setString(2, p_id);
   	 p.executeUpdate();
   	 p.close();
   	 }catch(SQLException e) {
   		 System.out.println("database error"); 
   		 e.printStackTrace();
   	 }
    }

}
