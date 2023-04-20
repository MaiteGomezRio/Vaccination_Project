package vaccination.jdbc;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionManager {

	private Connection c;

	public ConnectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/vaccination.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			createTables();
		} catch (Exception e) {
			System.out.println("Database access error");
			e.printStackTrace();
		}

	}
	private void createTables() {
		try {
			Statement s = c.createStatement();
			String table_Doctor = "CREATE TABLE Doctor (id TEXT PRIMARY KEY," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			s.executeUpdate(table_Doctor);
			
			String table_Patient = "CREATE TABLE Patient (id TEXT PRIMARY KEY," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL," + " attendance BOOLEAN )";
			s.executeUpdate(table_Patient);
			
			String table_Vaccine = "CREATE TABLE Vaccine (id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " dose INTEGER,";
			s.executeUpdate(table_Vaccine);
			
			String table_Disease = "CREATE TABLE Disease (id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			s.executeUpdate(table_Disease);
			
			String table_Condition = "CREATE TABLE Condition (id INTEGER PRIMARY KEY AUTOINCREMENT," + "type TEXT NOT NULL)";
			s.executeUpdate(table_Condition); 
			
			String table_Patient_Disease = "CREATE TABLE Patient_Disease (patient_id TEXT NOT NULL,"+"disease_id INTEGER)";
			s.executeUpdate(table_Patient_Disease);
			
			String table_Condition_Vaccine = "CREATE TABLE Condition_Vaccine(vaccine_id INTEGER" + "vaccine_id INTEGER REFERENCES Vaccine(id)" +
				     "condition_id INTEGER REFERENCES Condition(id)"; 
			s.executeUpdate(table_Condition_Vaccine); 
			
			String table_Disease_Vaccine = "CREATE TABLE Disease_Vaccine (disease_id INTEGER REFERENCES Disease(id)," 
			         + "vaccine_id REFERENCES Vaccine(id))"; 
			s.executeUpdate(table_Disease_Vaccine); 
			
			String table_Immune = "CREATE TABLE Immune(patient_id TEXT NOT NULL,"+" disease_id INTEGER)";
			s.executeUpdate(table_Immune);

			s.close();
		} catch (SQLException e) {
			// Check if the exception is because the tables already exist
			if (e.getMessage().contains("already exist")) {
				return;
			}
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}
	
}
