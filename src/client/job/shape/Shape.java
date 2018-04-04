package client.job.shape;

import java.awt.Color;


/**
 * Forme dessinable sur un canvas.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
public abstract class Shape
{
    public static final String CIRCLE   = "CIRCLE";
    public static final String SQUARE   = "SQUARE";
    public static final String RUBBER   = "RUBBER";

	private Color   color;
	private int     filling;

	public Shape (Color color, int filling)
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

	private static Color stringToColor (String colorAsStr)
    {
        Color color = Color.black;
        try
        {
            color = new Color(  Integer.parseInt(colorAsStr.substring(0,2), 16),
                                Integer.parseInt(colorAsStr.substring(2,4), 16),
                                Integer.parseInt(colorAsStr.substring(4,6), 16) );
        }
        catch (Exception e) { }
        return color;
    }

	/**
	 * Crée une forme à partir d'un message découpé reçu.
	 * @param params	Paramètres de la forme.
	 * @return			Objet Shape créé à partir des options passées en paramètre.
	 */
	public static Shape fromTram (String... params)
	{
        Shape   shape       = null;

        String  shapeName   = params[0];
		Color   color       = Shape.stringToColor( params[1].substring(1) );
		int     filling     = Integer.parseInt(params[2]);

		try
		{
			switch (shapeName)
			{
				case "SQUARE":
				    shape = new Square(color, filling, Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]));
				    break;
                case "CIRCLE":
				    shape = new Circle(color, filling, Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]));
			        break;
			}
		}
		catch (Exception e) {}

		return shape;
	}
}
