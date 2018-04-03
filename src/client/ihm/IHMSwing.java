package client.ihm;

import client.Client;
import client.job.MessageHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class IHMSwing extends IHM implements KeyListener, ActionListener
{
	private JFrame frame;
	private JTextPane recepField;
	private JTextField sendField;
	private JButton sendButton;
	private Canvas canvas;

	public IHMSwing()
	{
		frame = new JFrame("Client");
		frame.setLayout(new BorderLayout());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel chatPanel = new JPanel(new BorderLayout());

		recepField = new JTextPane();
		recepField.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(recepField);
		chatPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel botPanel = new JPanel(new BorderLayout());
		{
			sendField = new JTextField();
			sendField.addKeyListener(this);
			botPanel.add(sendField, BorderLayout.CENTER);

			sendButton = new JButton("Envoyer");
			sendButton.addActionListener(this);
			botPanel.add(sendButton, BorderLayout.EAST);
		}
		chatPanel.add(botPanel, BorderLayout.SOUTH);
		frame.add(chatPanel, BorderLayout.EAST);

		canvas = new Canvas(this);
		frame.add(canvas);


		frame.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
				chatPanel.setPreferredSize(new Dimension(frame.getWidth() / 4, 1));
				SwingUtilities.updateComponentTreeUI(frame);
		    }

		    public void componentHidden(ComponentEvent e) {}
		    public void componentShown(ComponentEvent e) {}
		    public void componentMoved(ComponentEvent e) {}
		});

		frame.setSize(800, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	public void draw(String... params)
	{
		canvas.draw(params);
	}

	public void printMessage(String message, Color color)
	{
		appendToPane(message, color);
	}

	public void printMessage(String message, String style)
	{
		switch (style)
		{
			case MessageHandler.NORMAL_COMMAND:
				appendToPane(message, Color.BLUE);
			break;
			case MessageHandler.ERROR_COMMAND:
				appendToPane(message, Color.RED);
			break;
			default:
				appendToPane(message, Color.BLACK);
		}
	}

	public String askPseudo()
	{
		String s = "";
		do
		{
			s = (String)JOptionPane.showInputDialog(
	                frame,
	                "Veuillez entrer votre pseudo:\n",
	                "Votre pseudo",
	                JOptionPane.PLAIN_MESSAGE
				);
		} while ((s == null) || (s.length() == 0));
		return s;
	}

	private void appendToPane(String msg, Color c)
	{
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		int len = recepField.getDocument().getLength();
		recepField.setEditable(true);
		recepField.setCaretPosition(len);
		recepField.setCharacterAttributes(aset, false);
		recepField.replaceSelection("\n" + msg);
		recepField.setCaretPosition(recepField.getDocument().getLength());
		recepField.setEditable(false);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == sendButton)
		{
			String message = sendField.getText().replaceAll("[ \t\n]", "");
			if (!message.equals(""))
			{
				client.getMessageWriter().sendMessage(MessageHandler.NORMAL_MESSAGE + ":" +  sendField.getText());
				sendField.setText("");
			}
		}
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			ActionEvent event;
			long when;

			when  = System.currentTimeMillis();
			event = new ActionEvent(sendButton, ActionEvent.ACTION_PERFORMED, "Anything", when, 0);

			actionPerformed(event);
		}
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}
}
