import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.DefaultListModel;

import net.jini.core.entry.Entry;

public class Topic implements Entry {

	public String name;
	public String topicDesc;
	public byte[] creatorID;
	
	//Hashset to aviod duplicates of users.
	public HashSet<String> userList;
	
	public Topic(){
		
	}
	public Topic(String name, String topicDesc,  byte[] ID)
	
	{
		userList = new HashSet<String>();
		this.name = name;
		this.topicDesc = topicDesc;
		this.creatorID = ID;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getTopic()
	{
		return this.topicDesc;
	}
	
	public void addUser(String name)
	{
		userList.add(name);
	}
	

	public void removeUser(String name)
	{
		userList.remove(name);
	}
	
	public Boolean compareID(byte[] ID)
	{
		return Arrays.equals(ID, this.creatorID);
	}
	
	public DefaultListModel getTopicModel()
	{
		DefaultListModel newListModel = new DefaultListModel<String>();
		for (Object user : userList.toArray())
		{
			newListModel.addElement(user);
		}
		
		return newListModel;
	}
}
