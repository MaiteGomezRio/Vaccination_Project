package vaccination.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import vaccination.ifaces.PutsManager;
import vaccination.pojos.Puts;

public class JDBCPutsManager implements PutsManager{
	private Connection c;

	public JDBCPutsManager(Connection c) {
		this.c = c;
	}

	@Override
	public void insertAppointment(Puts puts) {
		//TODO
	}
	
	@Override
	public List<Puts> checkAppointmentsOfPatient(int p_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Puts> checkAppointmentsOfDoctor(int d_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAppointment(Puts puts) {
		// TODO Auto-generated method stub
		
	}
}
