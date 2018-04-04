package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.job.Network;
import client.job.MessageHandler;

import client.ihm.IHM;
import client.ihm.IHMSwing;
import client.ihm.IHMServer;

/**
 * @author Adam Bernouy, Florent Dewilde, Jonathan Selle,
 * @version 2017-12-23
 */
public class Client
{
	/** IP de connexion par défaut. */
	private static final String DEFAULT_IP = "127.0.0.1";
	/** Port de connexion par défaut. */
	private static final int DEFAULT_PORT = 6000;

    /** IHM du programme. */
    static IHM ihm;

	/** Socket de connexion au serveur. */
	Socket          socket;
	/** Objet gérant uniquement l'envoi et la réception de messages. */
	Network         network;
	/** Thread recevant les messages des autres clients. */
	Thread          threadReceiver;
	/** Objet traitant les messages reçus. */
	MessageHandler  msgHandler;
    /** Pseudo du client. */
    String          pseudo;
    /** Identifiant du client. */
    String          id;


	/**
	 * Creer un client en initialisant une Socket de mêmes parametres puis les objets de gestion des messages et l'IHM.
	 * @param  ip           L'ip de la Socket à initialiser.
	 * @param  port         Le port de la Socket à initialiser.
	 */
	public Client (String ip, int port)
	{
		this(ip, port, false);
	}

	/**
	 * Creer un client en initialisant une Socket de mêmes parametres puis les objets de gestion des messages et l'IHM.
	 * @param ip            L'ip de la Socket à initialiser.
	 * @param port          Le port de la Socket à initialiser.
	 * @param hasIHM        Vrai si le client à une IHM, sinon faux.
	 */
	public Client (String ip, int port, boolean hasIHM)
	{
		this.msgHandler = new MessageHandler(this);
		this.network = new Network(ip, port, msgHandler);
		this.threadReceiver = new Thread(network);
		this.threadReceiver.start();

		this.pseudo = "";

		// Création de l'interface
        if (hasIHM)
        {
            ihm = new IHMSwing(this);
        }
        else
        {
            ihm = new IHMServer(this);
        }
	}

	public Network getNetwork()
	{
		return network;
	}
	
	public void reSend ()
	{
		System.out.println("reSendOk");
		ihm.reSend ();
	}


    /*-------------*/
    /*   SETTERS   */
    /*-------------*/

    /**
     * Modifie le pseudo du client.
     * @param pseudo Le nouveau pseudo du client.
     */
    public void setPseudo (String pseudo)
    {
        this.pseudo = pseudo;
    }

    /**
     * Modifie l'identifiant du client.
     * @param id Nouvel identifiant du client.
     */
    public void setId (String id)
    {
        this.id = id;
    }


    /*-------------*/
    /*   GETTERS   */
	/*-------------*/

    /**
     * Retourne le pseudo du client.
     * @return Le pseudo du client.
     */
    public String getPseudo ()
    {
        return this.pseudo;
    }

    /**
     * Retourne l'interface utilisateur du programme.
     * @return L'interface utilisateur.
     */
	public IHM getIhm ()
	{
		return ihm;
	}


	/**
	 * méthode d'entrée dans le programme
	 * @param args tableau contenant les valeurs passée dans la ligne de commande
	 *             		Il peut avoir plusieurs formes de contenu :
	 *             			{} : un tableau vide, les port et IP par defaut sont utilisés
	 *                      {IP} :
	 *                      	La valeur unique est l'IP du serveur, si elle n'est pas valide elle sera remplacée par l'IP par defaut.
	 *                      	Le port utilisé est le port par defaut
	 *                      {IP, port[, inutilisé]} :
	 *                      	Comme dans le cas d'un argument unique le premier est un IP vérifié et remplacé au besoin.
	 *                      	Le port est lui aussi testé et remplacé par le port par défaut s'il est invalide.
	 *                      	Toute valeurs supplémentaire est ignorée.
	 * @see DEFAULT_IP
	 * @see DEFAULT_PORT
	 */
	public static void main (String[] args)
	{
		String ip = DEFAULT_IP;
		int port = DEFAULT_PORT;

		if (args.length == 0)
		{
			System.out.println("Usage : java client <IP> <port>");
			System.out.println("Les IP (" + DEFAULT_IP + ") et port (" + DEFAULT_PORT + ") par defaut seront utilisé");
		}

		if (args.length == 1)
		{
			System.out.println("Usage : java client <IP> <port>");
			System.out.println("Le port (" + port + ") par defaut sera utilisé");
			if (args[0].matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"))
			{
				ip = args[0];
			}
			else
			{
				System.out.println("L'ip entrée " + args[0] + " n'est pas valide, l'ip par defaut (" + DEFAULT_IP + ") va être utilisée");
			}
		}

		if (args.length >= 2)
		{
			if (args[0].matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"))
			{
				ip = args[0];
			}
			else
			{
				System.out.println("L'ip entrée " + args[0] + " n'est pas valide, l'ip par defaut (" + DEFAULT_IP + ") va être utilisée");
			}
			try
			{
				port = Integer.parseInt(args[1]);
			}
			catch (Exception e)
			{
				System.out.println("Le port entré " + args[1] + " n'est pas valide, le port par defaut (" + DEFAULT_PORT + ") va être utilisé");
			}
		}
		new Client(ip, port, true);
	}
}
