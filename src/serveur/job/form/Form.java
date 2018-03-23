package serveur.job.form;

public abstract class Form
{
	private Color color;
	private int filling;
	
	public Form (Color color, int filling)
	{
		this.color = color;
		this.filling = filling;
	}
	
	public String toTram ();
	
	public boolean isAt (int posX, int posY);
	
	
	public static String[] (String message)
	{
		String formName = message.substring(0, message.indexOf(":"));
		String[] arg  = message.substring(message.indexOf(":") + 1).split(":");
		
		Form form = null;
		
		switch (formName)
		{
			case "SQUARE":
				form = new Square (arg);
			case "CIRCLE":
				form = new Circle (arg);
		}
	}
}