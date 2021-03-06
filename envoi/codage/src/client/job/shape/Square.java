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
	private int size;
	private int thickness;

	public Square (Color color, int filling, int x, int y, int size, int thickness)
	{
		super(color, filling);
		this.x 			= x;
		this.y 			= y;
		this.size 	= size;
		this.thickness = thickness;
	}

	public String toTram ()
	{
		return "";
	}

	public boolean isAt (int posX, int posY)
	{
   		switch (this.getFilling())
   		{
   			case 1: 		return posX >= this.x - (this.size + this.thickness) / 2 && posX <= this.x + (this.size + this.thickness) / 2 &&
					       posY >= this.y - (this.size + this.thickness) / 2 && posY <= this.y + (this.size + this.thickness) / 2;

   			case 2: 		return posX >= this.x - this.size / 2 && posX <= this.x + this.size / 2 &&
					       posY >= this.y - this.size / 2 && posY <= this.y + this.size / 2;
   		}
   		return false;
	}

	public String getType ()
	{
		return Shape.SQUARE;
	}

	public int[] getParams ()
	{
		return new int[] { this.x, this.y, this.size, this.thickness };
	}
}
