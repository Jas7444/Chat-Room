import java.rmi.RemoteException;
import java.util.Observable;

import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.event.UnknownEventException;
import net.jini.core.lease.Lease;
import net.jini.space.JavaSpace;

import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;

//Topic Listener 
public class TopicsListener extends Observable implements RemoteEventListener
{

	//space stuff
	private JavaSpace space;
	//remote stub
	private RemoteEventListener theStub;
	
	public TopicsListener(JavaSpace space)
	{
		this.space = space;
		// create the exporter
		
		Exporter myDefaultExporter = 
		    new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
					  new BasicILFactory(), false, true);

		try {

		    theStub = (RemoteEventListener) myDefaultExporter.export(this);

		    //Adds the Listener
		    
		    TopicList templateTopicList = new TopicList();
		    
		    space.notify(templateTopicList, null, this.theStub, Lease.FOREVER, null);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	@Override
	public void notify(RemoteEvent arg0) throws UnknownEventException,
			RemoteException {
		  TopicList templateTopicList = new TopicList();
		  //Notify
		  try
		  {
				TopicList topicsSpaceList = (TopicList)space.read(templateTopicList, null, 3000);
				
				this.setChanged();
				this.notifyObservers(topicsSpaceList);

		  } catch (Exception e){

		  }		
	} 

}
