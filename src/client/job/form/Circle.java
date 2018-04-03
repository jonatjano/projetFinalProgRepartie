package commun.form;

import java.awt.Color;

public class Circle extends Form
{
	public Circle (Color color, int filling)
	{
		super(color, filling);
	}

	public String toTram ()
	{
		return "";
	}

	public boolean isAt (int posX, int posY)
	{
		return false;
	}
}
