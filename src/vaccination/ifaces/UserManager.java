package vaccination.ifaces;

import java.util.List;

import vaccination.pojos.*; 

public interface UserManager {
	  
	  public void connect();
	  public void disconnect();
      public void register(User user);   
      public void assignRole(User user, Role role);
      public Role getRole(String name);
      public User login(String name, String password);   //if user doesnt exit return null 
      public void createRole(Role role); 
      public List<Role> getRoles(); 
      public void deleteUser(String name, String password);
      public void updatePassword(User user, String new_password);
}
