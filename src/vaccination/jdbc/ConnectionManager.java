package vaccination.jdbc;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vaccination.pojos.Condition;


public class ConnectionManager {

	private Connection c;

	public ConnectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/vaccination.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			createTables();
			insertTables();
		} catch (Exception e) {
			System.out.println("Database access error");
			e.printStackTrace();
		}

	}
	private void createTables() {
		try {
			Statement s = c.createStatement();
			String table_Doctor = "CREATE TABLE Doctor (id INTEGER PRIMARY KEY AUTOINCREMENT,"+ " id_document TEXT NOT NULL," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			s.executeUpdate(table_Doctor);
			
			String table_Patient = "CREATE TABLE Patient (id INTEGER PRIMARY KEY AUTOINCREMENT,"+ " id_document TEXT NOT NULL," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL,"+ "doctor TEXT REFERENCES Doctor(id))";
			s.executeUpdate(table_Patient);
			
			String table_Vaccine = "CREATE TABLE Vaccine (id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " dose INTEGER"+ "Disease TEXT NOT NULL REFERENCES Disease(id))";
			s.executeUpdate(table_Vaccine);
			
			String table_Disease = "CREATE TABLE Disease (id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			s.executeUpdate(table_Disease);
			
			String table_Condition = "CREATE TABLE Condition (identifier INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			s.executeUpdate(table_Condition); 
			
			String table_Patient_Vaccine = "CREATE TABLE Patient_Vaccine(patient_id INTEGER REFERENCES Patient(id)," + "vaccine_id INTEGER REFERENCES Vaccine(id))";
			s.executeUpdate(table_Patient_Vaccine);
			
			String table_Patient_Disease = "CREATE TABLE Patient_Disease (patient_id INTEGER REFERENCES Patient(id),"+"disease_id INTEGER REFERENCES Disease(id))";
			s.executeUpdate(table_Patient_Disease);
			
			String table_Patient_Condition = "CREATE TABLE Patient_Condition (patient_id INTEGER REFERENCES Patient(id)," + "condition_id INTEGER REFERENCES Condition(id))"; 
			s.executeUpdate(table_Patient_Condition);  
			
			String table_Disease_Vaccine = "CREATE TABLE Disease_Vaccine (disease_id INTEGER REFERENCES Disease(identifier)," 
			         + "vaccine_id REFERENCES Vaccine(id))"; 
			s.executeUpdate(table_Disease_Vaccine); 
			
			String table_Appointment = "CREATE TABLE Appointment(id INTEGER PRIMARY KEY AUTOINCREMENT Date Date NOT NULL"+"patient_id INTEGER NOT NULL REFERENCES Patient(id),"+" vaccine_id INTEGER NOT NULL REFERENCES Vaccine(id))" +
					                   "doctor_id INTEGER NOT NULL REFERENCES Doctor()id";
			s.executeUpdate(table_Appointment);
			
			String table_Is_Immune="CREATE TABLE Is_Immune(patient_id INTEGER NOT NULL REFERENCES Patient(id),"+"disease_id INTEGER NOT NULL REFERENCES"
					+ "Disease(id))";
			s.executeUpdate(table_Is_Immune);

			String table_Vaccine_Condition= "CREATE TABLE Vaccine_Condition (vaccine_id INTEGER NOT NULL REFERENCES Vaccine(id),"+"condition_id INTEGER NOT NULL REFERENCES Condition(id)";
			s.executeUpdate(table_Vaccine_Condition);

			s.close();
		} catch (SQLException e) {
			
			if (e.getMessage().contains("already exist")) {
				return;
			}
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return c;
	}
	public void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}
	
	
		
	public void insertTables() { 
		try {
		Statement s = c.createStatement(); 
		String insert_Disease= "INSERT INTO Disease (name)VALUES ('Haemophilus'),"
				+ "('Pneumococcal'), ('SRP'),('Measles'),('Papilloma(HPV)'),('Poliomyelitis'),('Diphteria'),('HepatitisB'),"
				+ "('Dtpa'),('Meningococcus'),('Covid-19')"; 
		s.execute(insert_Disease);
		
		String insert_Condition="INSERT INTO Condition(name)VALUES ('Pregnant'),('Allergies'),('HIV'),('Stroke')"
				+ "('Ictus'),('Epilepsy'),('Pneumonia'),('Special medical condition')";
		s.execute(insert_Condition);
		
		s.close(); 
		}catch(SQLException e) {
		System.out.println("database error");
		e.printStackTrace();
		}
	}
	
	public void insertVaccines() {
		int id;
		try {
		String select_Disease="SELECT id FROM Disease WHERE name=?";
		String insert_Vaccine="INSERT INTO Vaccine(name, dose, disease_id) VALUES (?,?,?)";
		PreparedStatement p = c.prepareStatement(select_Disease); 
		PreparedStatement p2=c.prepareStatement(insert_Vaccine);
		p.setString(1, "Haemophilus"); 
		ResultSet rs = p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		p2.setString(1, "Pfizer");
		p2.setInt(2, 3);
		p2.setInt(3,id);
		
		p.setString(1, "Pneumococcal"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "SRP"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "Measles"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "Papilloma(HPV)"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "Poliomyelitis"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "Diphteria"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "HepatitisB"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "Dtpa"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		p.setString(1, "Meningococcus"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		
		
		
		p.setString(1, "Covid-19"); 
		rs=p.executeQuery();
		rs.next();
		id=rs.getInt("id");
		p2.setString(1, "Pfizer");
		p2.setInt(2, 3);
		p2.setInt(3,id);
		
		
		
		
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
		}
		
	}
	

	/*public void getDiseasesFromTables() {
		try {
			String sql="SELECT id FROM Disease WHERE name=?";
			PreparedStatement p = c.prepareStatement(sql); 
			p.setString(1, ""); 
			ResultSet rs = p.executeQuery();
			rs.next();
		    int c_id=rs.getInt("id");
	        Condition condition=new Condition(c_id,type);
	        rs.close();
	        p.close();
		}catch(SQLException e) {
			System.out.println("database error");
			e.printStackTrace();
			}
	}*/
	public void assignDiseaseToVaccineConnection(int d_id,int v_id) {
		try {
		String sql = "INSERT into Disease_Vaccine(disease_id, vaccine_id) WHERE VALUES (?,?)"; // vaccine
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, d_id);
		p.setInt(2, v_id);
		p.executeUpdate();
		p.close();
		} catch (SQLException e) {
		System.out.println("database error");
		e.printStackTrace();
		}
	}
	
	public int associateDisease(String name) {
		try { 
		String sql = "SELECT * FROM Disease WHERE name LIKE ?"; // vaccine
		PreparedStatement p = c.prepareStatement(sql);
		p.setString(1, name);
		ResultSet rs = p.executeQuery();
		rs.next(); 
		int disease_id = rs.getInt("id");
		p.close();
		return disease_id ;
		
		} catch (SQLException e) {
		System.out.println("database error");
			e.printStackTrace();
			return -1;
		}
	}
		

}
