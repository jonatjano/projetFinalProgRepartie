package client.ihm;

import java.awt.Color;

import client.Client;

public abstract class IHM
{
	protected Client client;

	public IHM(Client client)
	{
		this.client = client;
	}

	// public boolean setClient(Client client)
	// {
	// 	if (this.client == null)
	// 	{
	// 		this.client = client;
	// 		return true;
	// 	}
	// 	return false;
	// }

	public void printMessage(String message)
	{
		printMessage(message, "");
	}

	public Client getClient()
	{
		return client;
	}

	public abstract void printMessage(String message, Color color);

	public abstract void printMessage(String message, String style);

	public abstract void draw(String type, String... params);

	public void draw(String... params)
	{
		String[] finalParams = new String[params.length - 1];

		for(int i = 1; i < params.length; i++)
		{
			finalParams [i - 1] = params[i];
		}

		draw(params[0], finalParams);
	}

	public abstract String askPseudo();
}
