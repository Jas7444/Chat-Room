import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
  
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
  
import net.jini.space.*;
import net.jini.core.entry.Entry;
import net.jini.core.lease.*;
import net.jini.core.transaction.*;
import net.jini.core.transaction.server.*; 
  
 
public class Manager implements Observer {
      
 
    //GUI properties.
    static private JTextArea chatArea = null;
    static private JList topicsJList = null;
    static private JList usersJList = null;
    static private JTextField topicsJTF = null;
     
    //User and Topic.
    private User currentUser;
    private Topic activeChat;
    private TopicList topicList;
    private UserList userList;
      
    //Javaspace and connected boolean tracking.
    private JavaSpace space;
    public boolean connected = false;
      
    //Topic and Messages Listeners.
    private TopicsListener TopicsListener;
    private MessagesListener messagesListener;
      
    //Pass in the main GUI parts of the chat that will need to be used and updated.
    public Manager(JTextArea text, JList topicList, JList usersList, JTextField topicsJTF)
    {
        Manager.chatArea = text;
        Manager.topicsJList = topicList;
        Manager.usersJList = usersList;
        Manager.topicsJTF = topicsJTF;
    }
     
    public void addNewUser(String username, String password)
    {
        //Set current user with the inputted username and password.
        currentUser = new User(username, password);
        //Check for a JavaSpace.
        space = (JavaSpace) SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }
          
        startSpaceTopicList();
        updateUserList();
         
        append("Connected as : " + username);
         
        connected = true;
    }
      
    //Starts the topic list and adds an observer to TopicsListener.
    private void startSpaceTopicList()
    {   
            topicList = getTopicSpaceList();
            TopicsListener = new TopicsListener(space);
            TopicsListener.addObserver(this);
    }
      
     
     
    //Handles the message
    private void handleMessage(Message message)
    {
        switch (message.messageType) { 
            case NORMALTXTMSG : handleStandardMessage(message);
                            break;
                default : handleStandardMessage(message);
        } 
    }
      
    //Normal TXTMSG 
    private void handleStandardMessage(Message message)
    {
        append(message.formattedMessage());
    }
      
    //Append medthod to print out messages
    private void append(String message)
    {
        final String TXT = message;
    
        //Appends then adds a new line.
        chatArea.append(TXT + "\n");
    }
  
    @Override
    //Observe Update
    public void update(Observable observableObject, Object argument) {
         
        if (observableObject instanceof TopicsListener)
        {
            if (argument instanceof TopicList)
            {
                topicList = (TopicList) argument;
                topicsJList.setModel(getTopicListModel());
            }
        }
        if (observableObject instanceof MessagesListener)
        {
            if (argument instanceof Message)
            {
                handleMessage((Message) argument);
            }
        }
          
    }
      
   
      
    //Adding a new topic to the javaspace.
    public void addNewTopics(String topicName, String topicDescripton)
    {
        Topic topic = new Topic(topicName, topicDescripton, currentUser.getID());
        if (topicList.containsTopic(topic.getName()) == false)
        {
            topicList.addTopic(topic.getName());
            // Add the new topic into the Javaspace.
            try {
                space.write(topic, null, Lease.FOREVER);
            } catch (Exception e) {
                
            }
              
            // Update the topic list.
            updateTopicList();
        }
    }
      
     
      
 
    public void topicConnect(String topicName)
    {
        try
        {
 
            if (activeChat != null)
            {
                leaveCurrentTopic();
            }
              
            Topic templateRoom = new Topic();
            
            templateRoom.name = topicName;
            Topic newTopic = (Topic)space.takeIfExists(templateRoom, null, 1000);
             
            newTopic.addUser(currentUser.getUsername());
            
            space.write(newTopic, null, Lease.FOREVER);
            
            activeChat = newTopic;
 
            if (activeChat.compareID(currentUser.getID()))
            {
                messagesListener = new MessagesListener(this.space, newTopic.getName());
  
            } else
            {
                messagesListener = new MessagesListener(this.space, newTopic.getName());
  
            }
            messagesListener.addObserver(this);
            //Updates to tell you what chat you are in.
            append("* Now chatting in " + topicName);
              
            //Updates a JTF to give you the topic name and description 
            //of the topic you are viewing.
            topicsJTF.setText(activeChat.getName() + " | " + activeChat.getTopic());
            usersJList.setModel(activeChat.getTopicModel());
            
        } catch (Exception e){
        }
    }
    
    void updateUserList()
    {
    	try
    	{
    		UserList templateUserList = new UserList();
    		UserList USerSpaceList = (UserList)space.take(templateUserList, null, 2000);
    		space.write(userList, null, Lease.FOREVER);
    		
    	}
    	catch(Exception e){  		
    	}
    }
     
    //Updates the topic List
    private void updateTopicList()
    {
        try
        {   
            TopicList templateTopicList = new TopicList();
            TopicList topicsSpaceList = (TopicList)space.take(templateTopicList, null, 2000);
            space.write(topicList, null, Lease.FOREVER);
              
        }
        catch (Exception e){
 
        }
    }
    
    public void deleteTopic(String topicName)
    {
          
        if (ownerOfTopic(topicName))
        {
 
 
            topicList.removeTopic(topicName);
            updateTopicList();
              
 
            try
            {
                  
            Topic templateRoom = new Topic();
            templateRoom.name = topicName;
            Topic newTopic = (Topic)space.takeIfExists(templateRoom, null, 3000);
 
            Message templateMessage = new Message();
            templateMessage.topicID = newTopic.getName();
              
            LinkedList templateArray = new LinkedList();
            templateArray.add(templateMessage);
              
            //takes the entrys from the space
            space.take((Entry) templateArray, null, 3000);
              
            } catch (Exception e)
            {
                e.printStackTrace();
            }                                       
        }
        else {
        	System.out.print("You are not the owner of this channel!");
        }
    }
      
    //returns true if the current user owns the topic.
    private boolean ownerOfTopic(String topicName)
    {
        try
        {
        Topic templateRoom = new Topic();
        templateRoom.name = topicName;
        Topic verifyRoom = (Topic)space.readIfExists(templateRoom, null, 3000);
          
        return verifyRoom.compareID(currentUser.getID());
          
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
      
    public void sendMessage(String topicName, String messageText, UserMessage messageType)
    {
        try
        {
        Message message = new Message(topicName, currentUser.getUsername());
        message.setMessage(messageType, messageText);
         
        space.write(message, null, 500);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
      
    //Sends a message to the topic a user it currently in.
    public void sendMessage(String messageText, UserMessage messageType)
    {
        try
        {
            if (activeChat != null)
            {
                Message message = new Message(activeChat.getName(), currentUser.getUsername());
                message.setMessage(messageType, messageText);
          
            space.write(message, null, 500);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
     
    public void leaveCurrentTopic()
    {
        messagesListener.deleteObservers();
        messagesListener = null;
          
        //Appends to the user when they leave a topic
        append("* You have left " + activeChat.getName());
        activeChat = null;
        topicsJTF.setText("");
  
    }
     
  //Returns the topic list from the space, if there is none it will write one in.
    public TopicList getTopicSpaceList()
    {
        TopicList templateTopicList = new TopicList();
          
        try
        {
            TopicList topicsSpaceList = (TopicList)space.readIfExists(templateTopicList, null, 2000);
            
            if (topicsSpaceList == null)
            {
                templateTopicList.newTopicList();
                  
                //Writes the list into the space.
                space.write(templateTopicList, null, Lease.FOREVER);
                  
            }
            else
            {
                templateTopicList = topicsSpaceList;
            }
        }
        catch (Exception e)
            {
                e.printStackTrace();
            }
        return templateTopicList;
    }
      
     
    //Default list for topics.
    public DefaultListModel getTopicListModel()
    {
        return topicList.getTopicModel();
    }
      
  
      
  
}