

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.jini.core.entry.Entry;

public class Message implements Entry {

	// Message Content
	
	public String topicID;
	public String SID;
	public UserMessage messageType;
	
	public String messageContent;
	
	public Message(){
	}

    //Formats the message into the type needed only one type is shown but can be extended to show other things such as private mesages etc.
	public String formattedMessage()
	{
		String defaultmessage;
		 switch (messageType) { 
			case NORMALTXTMSG : defaultmessage = formattedStandardMessage(); 
							break;

				default : defaultmessage = "Message Error";
		} 
				
		return defaultmessage;
	}
	
	public Message(String topicID, String SID)
	{
		this.topicID = topicID;
		this.SID = SID;
	}
	
	
	public void setMessage(UserMessage userMessage, String messageContent)
	{
		this.messageType = userMessage;
		this.messageContent = messageContent;
	}
	
	//Basic format for a user message
	private String formattedStandardMessage()
	{
		return  " <" + SID + "> " + messageContent;
	}
}
