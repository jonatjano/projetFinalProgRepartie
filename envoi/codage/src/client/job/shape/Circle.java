package client.job.shape;

import java.awt.Color;


/**
 * Cercle à dessiner.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
public class Circle extends Shape
{
	private int x;
	private int y;
	private int radius;
	private int thickness;

	public Circle (Color color, int filling, int x, int y, int radius, int thickness)
	{
		super(color, filling);
		this.x 		= x;
		this.y 		= y;
		this.radius = radius;
		this.thickness = thickness;
	}

	public String toTram ()
	{
		return "";
	}

	public boolean isAt (int posX, int posY)
	{
		//  (x - a)² + (y - b)² = R²
		switch (this.getFilling())
		{
			case 1: return Math.pow(this.radius + this.thickness / 2, 2) >= Math.pow(this.x - posX, 2) + Math.pow(this.y - posY, 2);
			case 2: return Math.pow(this.radius, 2) >= Math.pow(this.x - posX, 2) + Math.pow(this.y - posY, 2);
		}
		return false;
	}

	public String getType ()
	{
		return Shape.CIRCLE;
	}

	public int[] getParams ()
	{
		return new int[] {this.x, this.y, this.radius, this.thickness};
	}
}
