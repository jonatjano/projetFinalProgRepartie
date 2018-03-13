package client.ihm;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

class Canvas extends JPanel implements MouseInputListener
{
	BufferedImage image;

	Canvas()
	{
		super();

		setBackground(Color.white);
		image = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_ARGB);
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

		g2d.drawImage(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT), null, null);

	}

	void draw(String... params)
	{
		for (String param : params)
		{
			System.out.print(param + " , ");
		}
		System.out.println();

		Graphics2D g2 = image.createGraphics();

		g2.setColor(Color.blue);
		g2.fillRect(500, 0, 1000, 1000);

		g2.setColor(Color.white);
		g2.fillOval(600, 300, 200, 400);

		repaint();
	}

	public void mouseClicked(MouseEvent e)
	{
		// client.getMessageWriter().sendMessage(MessageHandler.DRAW_MESSAGE + ":" +  sendField.getText());
	}
	public void mouseDragged(MouseEvent e)
	{
		// client.getMessageWriter().sendMessage(MessageHandler.DRAW_MESSAGE + ":SQUARE:1,10,100,1000,1000");
	}

	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
