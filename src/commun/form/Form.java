package commun.form;

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


	public static Form fromTram(String message)
	{
		String formName = message.substring(0, message.indexOf(":"));
		String[] arg  = message.substring(message.indexOf(":") + 1).split(":");

		Form form = null;

		boolean isNew = (arg[0] == "draw");
		Color color = Color.getColor(arg[1]);
		if (color == null)
		{
			color = Color.black;
		}
		int filling = Integer.parseInt(arg[2]);

		switch (formName)
		{
			case "SQUARE":
				form = new Square (color, filling);
			case "CIRCLE":
				form = new Circle (color, filling);
		}

		return form;
	}
}
