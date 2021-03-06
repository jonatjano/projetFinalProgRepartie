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
import java.awt.BasicStroke;


/**
 * Canvas de dessin où peuvent être placées des formes.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
class Canvas extends JPanel implements MouseInputListener
{
    /** Type de forme à dessiner. */
    private String          shapeToDraw;
    private String          drawDel;
    private int             size;
    private int             thickness;
    private Color           color;
    private int             filling;

	private IHM             ihm;


	Canvas (IHM ihm)
	{
		super();

		this.ihm            = ihm;

        this.shapeToDraw    = Shape.SQUARE;
		this.drawDel        = "DRAW";
		this.size           = 10;
        this.thickness      = 5;
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
		super.paintComponent(g);
        // Dessine toutes les formes jamais enregistrées sur le canvas
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (int i = 0; i < this.ihm.getShapes().size(); i++)
        {
			Shape shape = this.ihm.getShapes().get(i);
			if (shape != null)
			{
				g2.setColor( shape.getColor() );
				this.drawShape(g2, shape);
			}
        }
        g2.setColor( this.color );
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
     * Renvoie la taille de la forme.
     * @return La taille de la forme.
     */
    int getShapeSize ()
    {
        return this.size;
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
     * Modifie le type d'action.
     * @param drawDel Le type d'action.
     */
    void setDrawDel (String drawDel)
    {
		this.drawDel = drawDel;
    }

    /**
     * Modifie le type de remplissage.
     * @param filling Le type de remplissage.
     */
    void setFilling (String filling)
    {
		switch (filling.toUpperCase())
		{
			case "FILL": this.filling = 2; break;
			case "EMPTY": this.filling = 1; break;
		}
    }

    /**
     * Modifie la taille de la forme.
     * @param size La taille de la forme.
     */
    void setShapeSize (int size)
    {
        this.size = size;
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

    public static String colorToString (Color color)
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
		this.repaint();
	}

    /**
     * Dessine la forme passée en paramètres.
     * @param shape La forme à dessiner.
     */
	private void drawShape (Graphics2D g2, Shape shape)
    {
        int[] shapeParams = shape.getParams();
        switch (shape.getType())
        {
            case Shape.SQUARE:
				if (shape.getFilling() == 1)
				{
					g2.setStroke(new BasicStroke(shapeParams[3]));
					g2.drawRect(shapeParams[0] - shapeParams[2] / 2, shapeParams[1] - shapeParams[2] / 2, shapeParams[2], shapeParams[2]);
				}
				else if (shape.getFilling() == 2)
				{
					g2.fillRect(shapeParams[0] - shapeParams[2] / 2, shapeParams[1] - shapeParams[2] / 2, shapeParams[2], shapeParams[2]);
				}
                break;
            case Shape.CIRCLE:
				if (shape.getFilling() == 1)
				{
					g2.setStroke(new BasicStroke(shapeParams[3]));
					g2.drawOval(shapeParams[0] - shapeParams[2], shapeParams[1] - shapeParams[2], 2 * shapeParams[2], 2 * shapeParams[2]);
				}
				else if (shape.getFilling() == 2)
				{
					g2.fillOval(shapeParams[0] - shapeParams[2], shapeParams[1] - shapeParams[2], 2 * shapeParams[2], 2 * shapeParams[2]);
				}
                break;
        }
    }

    /**
     * Envoie un message de dessin de forme à tous les utilisateurs.
     */
    private void sendDrawMessage (int x, int y)
    {
        String  colorStr    = this.colorToString(this.color);
        /*this.drawShape( (Graphics2D) this.image.getGraphics(), newShape );*/

		String message = MessageHandler.DRAW_MESSAGE + ":" +
				this.drawDel + ":" + this.shapeToDraw + ":" +
				colorStr + ":" +  this.filling + ":" +
				x + ":" + y + ":" +
				this.size + ":" + this.thickness;
        this.ihm.getClient().getNetwork().sendMessage(message);
    }

    /**
     * Lorsque le canvas est cliqué, un message de dessin est envoyé à tous.
     * @param e Evènement.
     */
	public void mouseClicked (MouseEvent e)
	{
        this.sendDrawMessage(e.getX(), e.getY());
	}

    /**
     * Lorsque la souris est traînée sur le canvas, un message de dessin est envoyé à tous.
     * @param e Evènement.
     */
	public void mouseDragged (MouseEvent e)
	{
        this.sendDrawMessage(e.getX(), e.getY());
    }

	public void mouseExited (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mousePressed (MouseEvent e) {}
	public void mouseMoved (MouseEvent e) {}
}
