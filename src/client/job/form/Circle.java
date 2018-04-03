package client.job.form;

import java.awt.Color;

public class Circle extends Form
{
	private int x;
	private int y;
	private int radius;

	public Circle (Color color, int filling, int x, int y, int radius)
	{
		super(color, filling);
		this.x = x;
		this.x = x;
		this.radius = radius;
	}

	public String toTram ()
	{
		return "";
	}

	public boolean isAt (int posX, int posY)
	{
		return false;
	}

	public String getType ()
	{
		return "circle";
	}

	public int[] getParams ()
	{
		return new int[] {x, y, radius};
	}
}
