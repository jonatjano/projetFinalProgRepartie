package client.job.shape;

import java.awt.Color;


/**
 * Carré à dessiner.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
public class Square extends Shape
{
	private int x;
	private int y;
	private int thickness;

	public Square (Color color, int filling, int x, int y, int thickness)
	{
		super(color, filling);
		this.x 			= x;
		this.y 			= y;
		this.thickness 	= thickness;
	}

	public String toTram ()
	{
		return "";
	}

	public boolean isAt (int posX, int posY)
	{
		return posX >= this.x - this.thickness / 2 && posX <= this.x + this.thickness / 2 &&
		       posY >= this.y - this.thickness / 2 && posY <= this.y + this.thickness / 2;
	}

	public String getType ()
	{
		return Shape.SQUARE;
	}

	public int[] getParams ()
	{
		return new int[] { this.x, this.y, this.thickness };
	}
}
