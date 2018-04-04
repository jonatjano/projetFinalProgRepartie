package client.ihm;

import client.Client;
import client.job.shape.*;
import client.job.MessageHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Canvas de dessin où peuvent être placées des formes.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
class Canvas extends JPanel implements MouseInputListener
{
    /** Type de forme à dessiner. */
    private String          shapeToDraw;
    private int             thickness;
    private Color           color;
    private int             filling;

	private List<Shape>     shapes;
	private IHM             ihm;


	Canvas (IHM ihm)
	{
		super();

		this.shapes         = new ArrayList<Shape>();
		this.ihm            = ihm;

        this.shapeToDraw    = Shape.SQUARE;
        this.thickness      = 10;
        this.color          = Color.black;
        this.filling        = 2;

        this.setBackground(Color.white);
        //this.setBorder( BorderFactory.createLineBorder(Color.red, 10) );
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

		this.draw("clear");
	}

	/**
	 * Redessine le canvas.
	 * @param g le graphic du JPanel
	 */
	@Override
	protected void paintComponent (Graphics g)
	{
        // Dessine toutes les formes jamais enregistrées sur le canvas
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Shape shape : this.shapes)
        {
            g.setColor( shape.getColor() );
            this.drawShape(g, shape);
        }
        g.setColor( this.color );
	}


    /*-------------*/
    /*   GETTERS   */
    /*-------------*/

    /**
     * Renvoie la forme à dessiner.
     * @param shapeToDraw La nouvelle forme à dessiner.
     */
    String getShapeToDraw ()
    {
        return this.shapeToDraw;
    }

    /**
     * Renvoie l'épaisseur du trait.
     * @return L'épaisseur du trait.
     */
    int getThickness ()
    {
        return this.thickness;
    }

    /**
     * Renvoie la couleur du pinceau.
     * @return La couleur du pinceau.
     */
    Color getColor ()
    {
        return this.color;
    }


	/*-------------*/
	/*   SETTERS   */
    /*-------------*/

    /**
     * Modifie la forme à dessiner.
     * @param shapeToDraw La nouvelle forme à dessiner.
     */
    void setShapeToDraw (String shapeToDraw)
    {
        this.shapeToDraw = shapeToDraw;
    }

    /**
     * Modifie l'épaisseur du trait.
     * @param thickness La nouvelle épaisseur du trait.
     */
    void setThickness (int thickness)
    {
        this.thickness = thickness;
    }

    /**
     * Modifie la couleur du pinceau.
     * @param color La nouvelle couleur du pinceau.
     */
    void setColor (Color color)
    {
        this.color = color;
    }



/*
draw:FORM:RGBA:PLEIN:ARG...
	draw:CARRE:RGBA:REMPLISSAGE:X:Y:COTE
	draw:CERCLE:RGBA:REMPLISSAGE:X:Y:RAYON

del:X:Y

clear

param :
	FONC : draw | del
	RGBA : hexa
	REMPLISAGE : 0, 1, 2
	FORM : CARRE | CERCLE
	pos : entier[2]
	COTE | RAYON : entier
 */

    private static String colorToString (Color color)
    {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Fait l'action envoyé au client.
     * @param type      Type de l'action (DRAW, DEL, CLEAR).
     * @param params    Paramètres de l'action, comme la position du curseur.
     */
	void draw (String type, String... params)
	{
		try
		{
			switch (type)
			{
				case "DRAW":
				    Shape newShape = Shape.fromTram(params);
					this.shapes.add( newShape );
                    this.repaint();
					//System.out.println("Nombre : " + this.shapes.size());
				    break;

				case "DEL":
					for (int i = 0; i < this.shapes.size(); i++)
					{
						int id = this.shapes.size() - 1 - i;
						if (this.shapes.get(id).isAt( Integer.parseInt(params[3]), Integer.parseInt(params[4]) ))
						{
                            this.shapes.remove(id);
							this.repaint();
							break;
						}
					}
				    break;

				case "CLEAR":
                    this.shapes.clear();
                    this.repaint();
				    break;
			}

			repaint();
		}
		catch (Exception e) {e.printStackTrace();}

	}

    /**
     * Dessine la forme passée en paramètres.
     * @param shape La forme à dessiner.
     */
	private void drawShape (Graphics g, Shape shape)
    {
        int[] shapeParams = shape.getParams();
        switch (shape.getType())
        {
            case Shape.SQUARE:
                g.fillRect(shapeParams[0] - shapeParams[2] / 2, shapeParams[1] - shapeParams[2] / 2, shapeParams[2], shapeParams[2]);
                break;
            case Shape.CIRCLE:
                g.fillOval(shapeParams[0] - shapeParams[2], shapeParams[1] - shapeParams[2], 2 * shapeParams[2], 2 * shapeParams[2]);
                break;
        }
    }

    /**
     * Envoie un message de dessin de forme à tous les utilisateurs.
     */
    private void sendDrawMessage (MouseEvent e)
    {
        String  colorStr    = this.colorToString(this.color);
        Shape   newShape    = Shape.fromTram(new String[] { this.shapeToDraw,
                                                            colorStr,
                                                            "" + this.filling,
                                                            "" + e.getX(),
                                                            "" + e.getY(),
                                                            "" + this.thickness });
        /*this.drawShape( (Graphics2D) this.image.getGraphics(), newShape );*/

        String message = MessageHandler.DRAW_MESSAGE + ":" + this.ihm.getDrawDelState() + ":" + this.shapeToDraw + ":" + colorStr + ":" +  this.filling + ":" + e.getX() + ":" + e.getY() + ":" + this.thickness;
        this.ihm.getClient().getNetwork().sendMessage(message);
    }

    /**
     * Lorsque le canvas est cliqué, un message de dessin est envoyé à tous.
     * @param e Evènement.
     */
	public void mouseClicked (MouseEvent e)
	{
        this.sendDrawMessage(e);
	}

    /**
     * Lorsque la souris est traînée sur le canvas, un message de dessin est envoyé à tous.
     * @param e Evènement.
     */
	public void mouseDragged (MouseEvent e)
	{
        this.sendDrawMessage(e);
    }

	public void mouseExited (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseMoved (MouseEvent e) {}
}
