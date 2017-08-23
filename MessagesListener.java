

import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.lease.Lease;
import net.jini.space.JavaSpace;

import net.jini.core.event.UnknownEventException;



import java.rmi.RemoteException;
import java.util.Observable;

import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;

//Observable helps manage the classes and javaspace.
public class MessagesListener extends Observable implements RemoteEventListener {

	//space stuff
	private JavaSpace space;
	
	//Stub remote 
	private RemoteEventListener theStub;
	
	//ID For the topics.
	private String topicID;
	
	public MessagesListener(JavaSpace space, String topicID)
	{
		this.space = space;
		this.topicID = topicID;
		
		// create the exporter
		Exporter myDefaultExporter = 
		    new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
					  new BasicILFactory(), false, true);

		try {
		    // register this as a remote object.
		    theStub = (RemoteEventListener) myDefaultExporter.export(this);

		    //Adds a listener
		    Message templateMessage = new Message();
		    templateMessage.topicID = this.topicID;
		    space.notify(templateMessage, null, this.theStub, Lease.FOREVER, null);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	@Override
	public void notify(RemoteEvent arg0) throws UnknownEventException,
			RemoteException {
	    Message templateMessage = new Message();
	    templateMessage.topicID = this.topicID;
		 try
		  {
				Message spaceMessage = (Message)space.read(templateMessage, null, 3000);
				this.setChanged();

				this.notifyObservers(spaceMessage);

		  } catch (Exception e){
		  }		
		
	}

}
