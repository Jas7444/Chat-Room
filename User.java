import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// This class represents a user. It contains their name and ID, as well as facilities to generate that ID.
public class User {

	private String name;
	private byte[] userAuthID;
	
	public User(String username, String password)
	{
		this.name = username;
		//userAuthID = getHash(password);
	}
	
	public byte[] getID()
	{
		return userAuthID;
	}
	
	public String getUsername()
	{
		return name;
	}
	
	
	
//  Code not used as basic functions not working yet. 
	
//	private byte[] getHash(String password)
//	{
//		String series = null;
//		series = name.toLowerCase() + ":" + series;
//	
//		try {
//			
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		
//		return series.getBytes();
//	}
	
}
