package client.ihm;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Canvas extends JPanel
{
	BufferedImage image;

	Canvas()
	{
		super();

		setBackground(Color.white);
		image = new BufferedImage(2000, 2000, BufferedImage.TYPE_INT_ARGB);
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

		// g2d.setColor(Color.blue);
		// g2d.fillOval(10, 10, 50, 50);
		// g2d.setColor(Color.orange);
		// g2d.fillOval(10, 70, 50, 50);
		// g2d.setColor(Color.black);
		// g2d.setFont(g2d.getFont().deriveFont(40.0f));
		// g2d.drawString("Dors / Pense", 70, 50);
		// g2d.drawString("Mange", 70, 110);
	}

	void draw(String... params)
	{
		Graphics2D g2 = image.createGraphics();
	}
}
