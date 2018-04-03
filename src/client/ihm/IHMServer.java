package client.ihm;

import java.awt.Color;

public abstract class IHMServer extends IHM
{
	public void printMessage(String message, Color color) {}

	public void printMessage(String message, String style) {}

	public void draw(String... params) {}

	public String askPseudo()
	{
		return "SERVER";
	}
}
