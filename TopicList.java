import javax.swing.DefaultListModel;
import java.util.HashSet;
import net.jini.core.entry.*;

//Topic List
public class TopicList implements Entry {
	//hashset to avoid duplication.
	public HashSet<String> topics;
	
	public TopicList()
	{		
	}
	
	public boolean containsTopic(String topic)
	{
		return topics.contains(topic);
	}
	
	public void newTopicList()
	{
		topics = new HashSet<String>();
	}
	
	public boolean addTopic(String topic)
	{
		return topics.add(topic);
	}
	
	public boolean removeTopic(String topic)
	{
		return topics.remove(topic);
	}
	public DefaultListModel getTopicModel()
	{
		DefaultListModel newListModel = new DefaultListModel<String>();
		for (Object topic : topics.toArray())
		{
			newListModel.addElement(topic);
		}
		
		return newListModel;
	}

}
