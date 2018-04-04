package client.job.shape;

import java.awt.Color;


/**
 * Cerle à dessiner.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
public class Circle extends Shape
{
	private int x;
	private int y;
	private int radius;

	public Circle (Color color, int filling, int x, int y, int radius)
	{
		super(color, filling);
		this.x 		= x;
		this.y 		= y;
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
		return Shape.CIRCLE;
	}

	public int[] getParams ()
	{
		return new int[] {x, y, radius};
	}
}
