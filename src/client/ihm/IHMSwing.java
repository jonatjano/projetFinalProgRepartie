package client.ihm;

import client.Client;
import client.job.shape.Shape;
import client.job.MessageHandler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
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
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import client.Client;
import java.util.Arrays;

/**
 * IHM Swing du client.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
public class IHMSwing extends IHM implements KeyListener, ActionListener
{
    /** Couleur des éléments normaux sur le panel de dessin. */
    private static Color       normalColor      = Color.cyan;
    /** Couleur des éléments sélectionnés sur le panel de dessin. */
    private static Color       selectedColor    = Color.red;
    /** Couleur du bouton draw. */
    private static Color       drawColor        = Color.green;
    /** Couleur ddu bouton delete. */
    private static Color       delColor         = Color.red;

    /** Fenêtre de l'application. */
	private JFrame      frame;
	/** Champ où s'affiche les dialogues du chat. */
	private JTextPane   recepField;
	/** Champ de texte à envoyer avec le bouton approprié sur le chat. */
	private JTextField  sendField;
	private JButton     sendButton;
	private JButton     drawDelBut;
	private JButton     fillEmptyBut;
	/** Panel de dessin. */
	private JPanel      drawPanel;
	/** Canvas de dessin de l'application */
	private Canvas      canvas;


	public IHMSwing (Client client)
	{
		super(client);

		this.frame = new JFrame("Client");
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		/*--------------*/
		/*     CHAT     */
        /*--------------*/

		JPanel chatPanel = new JPanel( new BorderLayout() );

        this.recepField = new JTextPane();
        this.recepField.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(recepField);
		chatPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel botPanel = new JPanel(new BorderLayout());
		{
            this.sendField = new JTextField();
            this.sendField.addKeyListener(this);
			botPanel.add(sendField, BorderLayout.CENTER);

            this.sendButton = new JButton("Envoyer");
            this.sendButton.addActionListener(this);
			botPanel.add(sendButton, BorderLayout.EAST);
		}
		chatPanel.add(botPanel, BorderLayout.SOUTH);
        this.frame.add(chatPanel, BorderLayout.EAST);


        /*--------------*/
        /*    DESSIN    */
        /*--------------*/

        // Créations de deux bordures
        Border raisedbevel  = BorderFactory.createRaisedBevelBorder();;
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();

        this.drawPanel = new JPanel( new BorderLayout() );

        /* Canvas de dessin */
        this.canvas = new Canvas(this);
		this.drawPanel.add(canvas);

		/* Panel de changement de formes */
		JPanel leftPlaceholderPanel = new JPanel();
		JPanel leftPanel            = new JPanel( new GridLayout(9, 1));
        leftPanel.add( new JLabel("FORME") );

		// CARRE
        JButton squareB = new JButton("Carré");
        squareB.setBorder( BorderFactory.createCompoundBorder(raisedbevel, loweredbevel) );
        squareB.setBackground( (this.canvas.getShapeToDraw() == Shape.SQUARE) ? this.selectedColor : this.normalColor );
        leftPanel.add( squareB );

        // CERCLE
		JButton circleB = new JButton("Cercle");
		circleB.setBorder( BorderFactory.createCompoundBorder(raisedbevel, loweredbevel) );
        circleB.setBackground( (this.canvas.getShapeToDraw() == Shape.CIRCLE) ? this.selectedColor : this.normalColor );
        leftPanel.add( circleB );

        // Evènements liés à ces deux boutons
        circleB.addActionListener( new ActionListener () {
            /**
             * Lorsque le bouton est cliqué, la forme à dessiner est modifiée.
             * @param e Evènement.
             */
            public void actionPerformed (ActionEvent e)
            {
                canvas.setShapeToDraw( Shape.CIRCLE );
                squareB.setBackground( normalColor );
                circleB.setBackground( selectedColor );
            }
        });

        squareB.addActionListener( new ActionListener () {
            /**
             * Lorsque le bouton est cliqué, la forme à dessiner est modifiée.
             * @param e Evènement.
             */
            public void actionPerformed (ActionEvent e)
            {
                canvas.setShapeToDraw( Shape.SQUARE );
                circleB.setBackground( normalColor );
                squareB.setBackground( selectedColor );
            }
        });

        leftPlaceholderPanel.add(leftPanel);
		this.drawPanel.add(leftPlaceholderPanel, BorderLayout.WEST);

        /* Choix de l'épaisseur */
        leftPanel.add( new JLabel("SIZE") );
        JTextField sizeTF = new IntegerJTextField( "" + this.canvas.getShapeSize() );
        sizeTF.addKeyListener( new KeyAdapter() {
            /**
             * Modifie l'épaisseur du trait du canvas à chaque modification
             * @param e
             */
            public void keyReleased (KeyEvent e)
            {
                canvas.setShapeSize( Integer.parseInt(sizeTF.getText()) );
            }
        });

        leftPanel.add( sizeTF );

        leftPanel.add( new JLabel("EPAISSEUR") );
        JTextField thicknessTF = new IntegerJTextField( "" + this.canvas.getThickness() );
        thicknessTF.addKeyListener( new KeyAdapter() {
            /**
             * Modifie l'épaisseur du trait du canvas à chaque modification
             * @param e
             */
            public void keyReleased (KeyEvent e)
            {
                canvas.setThickness( Integer.parseInt(thicknessTF.getText()) );
            }
        });

        leftPanel.add( thicknessTF );

		String[] fillEmptyText = new String[] {"Fill", "Empty"};
		Color[] fillEmptycolors = new Color[] {drawColor, delColor};
        fillEmptyBut = new JButton(fillEmptyText[0]);
        fillEmptyBut.setBackground( fillEmptycolors[0] );
        fillEmptyBut.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
				int fillEmptyPos = 1 - Arrays.asList(fillEmptyText).indexOf(fillEmptyBut.getText());
                fillEmptyBut.setText(fillEmptyText[fillEmptyPos]);
                fillEmptyBut.setBackground(fillEmptycolors[fillEmptyPos]);
				canvas.setFilling(fillEmptyText[fillEmptyPos]);
            }
        });
        leftPanel.add(fillEmptyBut);

        /* Choix de la couleur */
        leftPanel.add( new JLabel("COULEUR") );
        JButton colorB = new JButton();
        colorB.setBackground( this.canvas.getColor() );
        colorB.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                Color newColor = JColorChooser.showDialog(frame, "Choix de la couleur", canvas.getColor());
                if (newColor != null)
                {
                    canvas.setColor( newColor );
                    colorB.setBackground(newColor);
                }
            }
        });
        leftPanel.add(colorB);
		String[] drawDelText = new String[] {"DRAW", "DEL"};
		Color[] drawDelcolors = new Color[] {drawColor, delColor};
        drawDelBut = new JButton(drawDelText[0]);
        drawDelBut.setBackground( drawDelcolors[0] );
        drawDelBut.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
				int drawDelPos = 1 - Arrays.asList(drawDelText).indexOf(drawDelBut.getText());
                drawDelBut.setText(drawDelText[drawDelPos]);
                drawDelBut.setBackground(drawDelcolors[drawDelPos]);
				canvas.setDrawDel(drawDelText[drawDelPos]);
            }
        });
        leftPanel.add(drawDelBut);

        /* Fonctions de suppression */
        // Gomme

        // Suppression
        JButton clearB = new JButton("Nettoyer");
        clearB.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                String message = MessageHandler.DRAW_MESSAGE + ":CLEAR";
                client.getNetwork().sendMessage(message);
            }
        });
        leftPanel.add( clearB );

		this.frame.add(this.drawPanel);


        /*--------------*/
        /*  EVENEMENTS  */
        /*--------------*/

		frame.addComponentListener( new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
				chatPanel.setPreferredSize(new Dimension(frame.getWidth() / 4, 1));
				SwingUtilities.updateComponentTreeUI(frame);
		    }

		    public void componentHidden(ComponentEvent e) {}
		    public void componentShown(ComponentEvent e) {}
		    public void componentMoved(ComponentEvent e) {}
		});


        /*--------------*/
        /*    FENETRE   */
        /*--------------*/

		frame.setSize(800, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);


		// Demande au client son pseudo
		String pseudo = this.askPseudo();
		// Si le pseudo reçu est null, le client est arrêté
		if (pseudo == null)
		    System.exit(0);
		else
        {
            this.client.setPseudo(pseudo);
            this.client.getNetwork().sendMessage( MessageHandler.CONNECTED + ":" + pseudo);
        }
	}

    /**
     * Envoie au canvas les paramètres de l'action à réaliser.
     * @param type      Le type de l'action
     * @param params    Paramètres de l'action, comme la position du clic.
     */
	public void draw (String type, String... params)
	{
		canvas.draw(type, params);
	}

	public void printMessage (String message, Color color)
	{
		appendToPane(message, color);
	}

	public void printMessage (String message, String style)
	{
		appendToPane(message, Color.BLACK);
	}

    /**
     * Demande le pseudo du client qui essaye de se connecter.
     * @return  Pseudo du client.
     */
	public String askPseudo ()
	{
		String input = "";
		do
		{
			input = (String) JOptionPane.showInputDialog(   frame,
                                                            "Veuillez entrer votre pseudo:\n",
                                                            "Votre pseudo",
                                                            JOptionPane.PLAIN_MESSAGE );
		} while ( input != null && input.length() == 0 );
		return input;
	}

	private void appendToPane (String msg, Color c)
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

    /**
     * Envoie un message à travers le chat à tous les autres utilisateurs.
     * @param e
     */
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == sendButton)
		{
			String message = sendField.getText().replaceAll("[ \t\n]", "");
			if (!message.equals(""))
			{
				client.getNetwork().sendMessage(MessageHandler.NORMAL_MESSAGE + ":" + this.client.getPseudo() + " : " +  sendField.getText());
				sendField.setText("");
			}
		}
	}

	public void keyPressed (KeyEvent e)
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

	public void keyReleased (KeyEvent e) {}
	public void keyTyped (KeyEvent e) {}
}
