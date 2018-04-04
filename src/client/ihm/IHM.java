package client.ihm;

import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

import client.Client;
import client.job.MessageHandler;
import client.job.shape.Shape;

public abstract class IHM
{
	protected Client client;
	protected List<Shape>     shapes;

	public IHM (Client client)
	{
		this.client = client;
		this.shapes = new ArrayList<Shape>();
	}

	public void printMessage (String message)
	{
		printMessage(message, "");
	}

	public Client getClient ()
	{
		return client;
	}
	
	public void reSend ()
	{
		List<Shape> shapes = new ArrayList(this.shapes);
		String message = MessageHandler.DRAW_MESSAGE + ":CLEAR";
		client.getNetwork().sendMessage(message);
		
		for (Shape shape : shapes)
		{
			message = MessageHandler.DRAW_MESSAGE + ":" + "DRAW" + ":" + shape.getType() + ":" + Canvas.colorToString(shape.getColor()) + ":" +  shape.getFilling();
			for (int param : shape.getParams())
			{
				message += ":" + param;
			}
			client.getNetwork().sendMessage(message);
		}
	}

	public abstract void printMessage (String message, Color color);

	public abstract void printMessage (String message, String style);

	public void draw (String type, String... params)
	{
		try
		{
			switch (type)
			{
				case "DRAW":
				    Shape newShape = Shape.fromTram(params);
					this.shapes.add( newShape );
				    break;

				case "DEL":
					for (int i = 0; i < this.shapes.size(); i++)
					{
						int id = this.shapes.size() - 1 - i;
						if (this.shapes.get(id).isAt( Integer.parseInt(params[2]), Integer.parseInt(params[3]) ))
						{
                            this.shapes.remove(id);
							break;
						}
					}
				    break;

				case "CLEAR":
                    this.shapes.clear();
				    break;
			}
		}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public List<Shape> getShapes()
	{
		return this.shapes;
	}

	public abstract String getDrawDelState ();

	public void draw (String... params)
	{
		String[] finalParams = new String[params.length - 1];

		for(int i = 1; i < params.length; i++)
		{
			finalParams [i - 1] = params[i];
		}

		draw(params[0], finalParams);
	}

	public abstract String askPseudo();
}
