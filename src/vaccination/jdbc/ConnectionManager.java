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
			String table_Doctor = "CREATE TABLE Doctor (id INTEGER PRIMARY KEY," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			s.executeUpdate(table_Doctor);
			
			String table_Patient = "CREATE TABLE Patient (id INTEGER PRIMARY KEY," + " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			s.executeUpdate(table_Patient);
			
			String table_Vaccine = "CREATE TABLE Vaccine (identifier INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " dose INTEGER)";
			s.executeUpdate(table_Vaccine);
			
			String table_Disease = "CREATE TABLE Disease (identifier INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			s.executeUpdate(table_Disease);
			
			String table_Condition = "CREATE TABLE Condition (identifier INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			s.executeUpdate(table_Condition); 
			
			String table_Patient_Vaccine = "CREATE TABLE Patient_Vaccine(patient_id INTEGER REFERENCES Patient(id)," + "vaccine_identifier INTEGER REFERENCES Vaccine(identifier))";
			s.executeUpdate(table_Patient_Vaccine);
			
			String table_Patient_Disease = "CREATE TABLE Patient_Disease (patient_id INTEGER NOT NULL,"+"disease_identifier INTEGER)";
			s.executeUpdate(table_Patient_Disease);
			
			String table_Patient_Condition = "CREATE TABLE Patient_Condition (patient_id INTEGER REFERENCES Patient(id)," + "condition_identifier INTEGER REFERENCES Condition(identifier))"; 
			s.executeUpdate(table_Patient_Condition); 
			
			String table_Condition_Vaccine = "CREATE TABLE Condition_Vaccine(condition_identifier INTEGER REFERENCES Condition(identifier)" + "vaccine_identifier INTEGER REFERENCES Vaccine(identifier))"; 
			s.executeUpdate(table_Condition_Vaccine); 
			
			String table_Disease_Vaccine = "CREATE TABLE Disease_Vaccine (disease_identifier INTEGER REFERENCES Disease(identifier)," 
			         + "vaccine_identifier REFERENCES Vaccine(identifier))"; 
			s.executeUpdate(table_Disease_Vaccine); 
			
			String table_Puts = "CREATE TABLE Puts(Date Date PRIMARY KEY"+"patient_id INTEGER NOT NULL REFERENCES Patient(id),"+" vaccine_identifier INTEGER NOT NULL REFERENCES Vaccine(identifier)," + "attendance BOOLEAN)";
			s.executeUpdate(table_Puts);

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
	
}
