package client.ihm;

import client.Client;
import client.job.form.*;
import client.job.MessageHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

class Canvas extends JPanel implements MouseInputListener
{
	private List<Form> forms;
	private IHM ihm;

	Canvas(IHM ihm)
	{
		super();

		forms = new ArrayList<Form>();
		this.ihm = ihm;
		addMouseMotionListener(this);
		setBackground(Color.white);
		draw("clear");
	}

	/**
	 * redessine le canvas
	 * @param g le graphic du JPanel
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		for (Form form : forms)
		{
			g2.setColor(form.getColor());
			int[] formParams = form.getParams();
			switch (form.getType())
			{
				case "square":
					g2.fillRect(formParams[0], formParams[1], formParams[2], formParams[2]);
				break;
				case "circle":
					g2.fillOval(formParams[0] - formParams[2], formParams[1] - formParams[2], 2 * formParams[2], 2 * formParams[2]);
				break;
			}
		}
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

	void draw(String type, String... params)
	{
		try
		{
			System.out.println(type + " " + params[0]);
			switch (type)
			{
				case "DRAW":
					forms.add(Form.fromTram(params));
					System.out.println(forms.size());
					redrawImage();
				break;

				case "DEL":
					for (int i = forms.size() - 1; i >= 0; i--)
					{
						if (forms.get(i).isAt( Integer.parseInt(params[0]), Integer.parseInt(params[1]) ))
						{
							forms.remove(i);
						}
					}

					redrawImage();
				break;

				case "CLEAR":
					forms.clear();
				break;
			}

			repaint();
		}
		catch (Exception e) {}

	}

	private void redrawImage()
	{
		Graphics2D g2 = image.createGraphics();


	}

	public void mouseClicked(MouseEvent e)
	{
		String message = MessageHandler.DRAW_MESSAGE + ":DRAW:" + "SQUARE" + ":" + "#ff0000" + ":" +  "2" + ":" + e.getX() + ":" + e.getY() + ":" + "10";
		ihm.getClient().getNetwork().sendMessage(message);
		System.out.println("envoi de : " + message);
	}

	public void mouseDragged(MouseEvent e)
	{
		System.out.println(e.getX() + " " + e.getY());
		String message = MessageHandler.DRAW_MESSAGE + ":DRAW:" + "SQUARE" + ":" + "#ff0000" + ":" +  "2" + ":" + e.getX() + ":" + e.getY() + ":" + "10";
		ihm.getClient().getNetwork().sendMessage(message);
		System.out.println(message);
	}

	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
