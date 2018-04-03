package client.ihm;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import client.job.MessageHandler;
import client.Client;
import client.job.MessageWriter;

class Canvas extends JPanel implements MouseInputListener
{
	private IHM ihm;
	private BufferedImage image;

	Canvas(IHM ihm)
	{
		super();

		this.ihm = ihm;
		addMouseMotionListener(this);
		setBackground(Color.white);
		image = new BufferedImage(1500, 1500, BufferedImage.TYPE_INT_ARGB);
		draw();
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

		g2d.drawImage(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), null, null);

	}

	void draw(String... params)
	{
		// System.out.print("reception de : ");
		// for (String param : params)
		// {
		// 	System.out.print(param + " , ");
		// }
		// System.out.println();

		Graphics2D g2 = image.createGraphics();

		g2.setColor(Color.black);
		g2.fillOval(600, 300, 200, 400);

		g2.setColor(Color.white);
		g2.fillRect(700, 500, 1, 1);

		repaint();
	}

	public void mouseClicked(MouseEvent e)
	{
		ihm.getClient().getMessageWriter().sendMessage(MessageHandler.DRAW_MESSAGE + ":SQUARE:1,10,100,1000,1000");
		System.out.println("envoi de : " + MessageHandler.DRAW_MESSAGE + ":SQUARE:1,10,100,1000,1000");
	}

	public void mouseDragged(MouseEvent e)
	{
		ihm.getClient().getMessageWriter().sendMessage(MessageHandler.DRAW_MESSAGE + ":SQUARE::1,10,100,1000,1000");
		System.out.println("envoi de : " + MessageHandler.DRAW_MESSAGE + ":SQUARE:1,10,100,1000,1000");
	}

	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
