import java.util.Arrays;
import java.util.HashMap;

import net.jini.core.entry.*;


public class UserList implements Entry {
	
	public HashMap<String, byte[]> userList;
	
	public UserList()
	{
		
	}
	
//	public HashMap<String, byte[]> getUSerList()
//	{
//		return userList;
//	}
	
	public void newUserList()
	{
		userList = new HashMap<String, byte[]>();
	}
	
	public void addUser(String username, byte[] ID)
	{
		userList.put(username.toLowerCase(), ID);
	}
	
	//Checks if the user is valid. Works but not implemented.
	public boolean validUser(String username, byte[] ID)
	{
		username = username.toLowerCase();
		boolean valid = false;
				
		if (userList.containsKey(username))
		{
			if (Arrays.equals(userList.get(username), ID))
			{
				valid = true;
			} 
		} 
		else
		{
			addUser(username, ID);
			valid = true;
		}
	
		return valid;
	}
	

}
