package client.job;

import client.Client;

/**
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class MessageHandler
{
	public static final String NAME_REQUEST = "NAME_REQUEST";
	public static final String NORMAL_MESSAGE = "NORMAL_MESSAGE";
	public static final String DRAW_MESSAGE = "DRAW_MESSAGE";
	public static final String DISCONNECT = "DISCONNECTED";
	public static final String CONNECT = "CONNECTED";


	Client client;

	public MessageHandler(Client client)
	{
		this.client = client;
	}

	void onMessage(String message)
	{
		System.out.println(message);
		String messageType = message.substring(0, message.indexOf(":"));
		String messageBody = message.substring(message.indexOf(":") + 1);

		switch (messageType)
		{
			case NAME_REQUEST:
				this.client.getNetwork().sendMessage(this.client.getIhm().askPseudo());
				break;
			case NORMAL_MESSAGE:
				System.out.println(messageBody);
				this.client.getIhm().printMessage(messageBody);
				break;
			case DRAW_MESSAGE:
				this.client.getIhm().draw(messageBody.split(":"));
				break;
			case DISCONNECT:
				this.client.getIhm().printMessage("Deconnexion de : " + messageBody);
				break;
			case CONNECT:
				this.client.getIhm().printMessage("Connexion de : " + messageBody);
				break;
		}
	}
}
