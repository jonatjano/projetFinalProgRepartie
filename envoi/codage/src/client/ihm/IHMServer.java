package client.ihm;

import java.awt.Color;
import client.Client;

public class IHMServer extends IHM
{
	public IHMServer(Client client)
	{
		super(client);
	}

	public void printMessage(String message, Color color) {}

	public void printMessage(String message, String style)
	{
		if (message.startsWith("Connexion"))
			reSend();
	}

	public void draw(String type, String... params)
	{
		super.draw(type, params);
	}

	public String askPseudo()
	{
		return "SERVER";
	}

	public String getDrawDelState()
	{
		return "DRAW";
	}
}
