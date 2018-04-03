package client.job.form;

import java.awt.Color;

public class Square extends Form
{
	private int x;
	private int y;
	private int side;

	public Square (Color color, int filling, int x, int y, int side)
	{
		super(color, filling);
		this.x = x;
		this.x = x;
		this.side = side;
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
		return "square";
	}

	public int[] getParams ()
	{
		return new int[] {x, y, side};
	}
}
