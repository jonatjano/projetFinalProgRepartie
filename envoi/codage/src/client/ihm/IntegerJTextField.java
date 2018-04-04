package client.ihm;

import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class IntegerJTextField extends JTextField
{
    public IntegerJTextField (String text)
    {
        super(text);

        this.addKeyListener( new KeyAdapter()
        {
            /**
             * A chaque caractère entré, vérifie s'il s'agit ou non d'un chiffre. Si ce n'est pas le cas, le
             * caractère n'est pas accepté.
             * @param e
             */
            public void keyTyped (KeyEvent e) {
                char c = e.getKeyChar();
                if (!isNumber(c))
                    e.consume();
            }
        });
    }

    public IntegerJTextField ()
    {
        this("");
    }

    /**
     * Vérifie si le caractère passé en paramètre est un chiffre.
     *
     * @param ch La caractère à vérifier.
     * @return Vrai si le cahractère passé en paramètre est un chiffre, sinon faux.
     */
    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }
}