package client.job;

import client.Client;

/**
 * @author Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version 2017-12-23
 */
public class MessageHandler
{
	public static final String NAME_REQUEST 	= "NAME_REQUEST";
	public static final String NORMAL_MESSAGE 	= "NORMAL_MESSAGE";
	public static final String DRAW_MESSAGE 	= "DRAW_MESSAGE";
	public static final String DISCONNECTED 	= "DISCONNECTED";
	public static final String CONNECTED 		= "CONNECTED";
    public static final String NEWCOMER 		= "NEWCOMER";

	Client client;


	public MessageHandler (Client client)
	{
		this.client = client;
	}

	void onMessage (String message)
	{
		//System.out.println("Réception : " + message);
		String messageType = message.substring(0, message.indexOf(":"));
		String messageBody = message.substring(message.indexOf(":") + 1);

		switch (messageType)
		{
			case NAME_REQUEST:
                String pseudo = this.client.getIhm().askPseudo();
                this.client.getNetwork().sendMessage( pseudo );
				break;

			case NORMAL_MESSAGE:
                this.client.getIhm().printMessage(messageBody);
				break;

			case DRAW_MESSAGE:
                this.client.getIhm().draw( messageBody.split(":") );
				break;

			case DISCONNECTED:
                this.client.getIhm().printMessage( "Déconnexion de : " + messageBody );
				break;

			case CONNECTED:
                this.client.getIhm().printMessage( "Connexion de : " + messageBody );
				break;

            case NEWCOMER:
                this.client.setId( messageBody );
                break;
		}
	}
}
