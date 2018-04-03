package client.job.form;

import java.awt.Color;

public abstract class Form
{
	private Color color;
	private int filling;

	public Form (Color color, int filling)
	{
		this.color = color;
		this.filling = filling;
	}

	public abstract String toTram ();

	public abstract boolean isAt (int posX, int posY);

	public abstract String getType ();

	public abstract int[] getParams ();

	public Color getColor()
	{
		return color;
	}

	public static Form fromTram(String... message)
	{
		String formName = message[0];
		System.out.println(formName);

		Form form = null;

		Color color = Color.getColor(message[1]);
		if (color == null)
		{
			color = Color.black;
		}
		int filling = Integer.parseInt(message[2]);

		try
		{
			switch (formName)
			{
				case "SQUARE":
				form = new Square (color, filling, Integer.parseInt(message[3]), Integer.parseInt(message[4]), Integer.parseInt(message[5]));
				case "CIRCLE":
				form = new Circle (color, filling, Integer.parseInt(message[3]), Integer.parseInt(message[4]), Integer.parseInt(message[5]));
			}
		}
		catch (Exception e) {}

		return form;
	}
}
