package server.job;

import server.Client;

/**
 * @author Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version 2017-12-23
 */
public class MessageHandler
{
    public static final String CONNECTED 		= "CONNECTED";
    public static final String NEWCOMER 		= "CONNECTED";

	Client client;


	public MessageHandler (Client client)
	{
		this.client = client;
	}

	void onMessage (String message)
	{
		String messageType = message.substring(0, message.indexOf(":"));
		String messageBody = message.substring(message.indexOf(":") + 1);

		switch (messageType)
		{
			case CONNECTED:
				System.out.println( "Connexion de : " + messageBody );
				break;
		}
	}
}
