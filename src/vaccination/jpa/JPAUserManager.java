package vaccination.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import vaccination.ifaces.*;
import vaccination.pojos.Role;
import vaccination.pojos.User; 

public class JPAUserManager implements UserManager{
	
	private EntityManager em; 
	public JPAUserManager() {
		this.connect();
	}
	
	public void connect() {
		// TODO Auto-generated method stub
		
		em = Persistence.createEntityManagerFactory("Vaccination_Project-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		
		if( this.getRoles().isEmpty()) {
			Role doctor = new Role("doctor");
			Role patient = new Role("patient");
			Role director = new Role("director");
			this.createRole(doctor);
			this.createRole(patient);
			this.createRole(director);
			
		}
		
	}
	
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		em.close();
	}
	
	public void close() {
		em.close(); 
	}

	@Override
	public void register(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit(); 
	}

	@Override
	public User login(String name, String password) {
		try {
		    Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND password = ? ", User.class);
		    q.setParameter(1, name);
		    q.setParameter(2, password); 
		    User user = (User)q.getSingleResult();
		    return user;
		}catch(NoResultException e){
			return null;
		}
	}
	@Override
	public void assignRole(User user, Role role) {
		 em.getTransaction().begin();
		 user.setRole(role);
		 role.addUser(user); 
		 em.getTransaction().commit(); 
	}

	@Override
	public void createRole(Role role) {
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit(); 
		
	}
    @Override
    public List<Role> getRoles(){
    	Query q = em.createNativeQuery("SELECT * FROM roles", Role.class); 
    	List<Role> roles = (List<Role>)q.getResultList(); 
    	return roles; 
    }
    @Override
    public Role getRole(String name) {
    	Query q = em.createNativeQuery("SELECT * FROM roles WHERE name LIKE ?", Role.class);
    	q.setParameter(1, name);
    	Role r = (Role)q.getSingleResult();
    	return r; 
    }
  //TODO check if it works
    @Override 
    public void deleteUser(String name,String password) {
    	Query q=em.createNativeQuery("SELECT* FROM users WHERE name LIKE ? AND password LIKE ?");
    	em.getTransaction().begin();
    	q.setParameter(1, name);
    	q.setParameter(2,password);
    	User user=(User)q.getSingleResult();
    	Role role=user.getRole();
    	role.deleteUser(user);
    	em.getTransaction().commit();	
    }
    
    
}
