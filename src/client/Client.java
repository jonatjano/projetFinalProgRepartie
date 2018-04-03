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
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class Client
{
	/**
	 * ip de connexion par defaut
	 */
	private static final String DEFAULT_IP = "127.0.0.1";
	/**
	 * port de connexion par defaut
	 */
	private static final int DEFAULT_PORT = 6000;

	/**
	 * socket de connexion au server
	 */
	Socket socket;

	/**
	 * objet gérant l'envoi et la reception de message venant du serveur (ne les traite pas)
	 * @see Network
	 */
	Network network;
	/**
	 * Thread de msgIn
	 * @see msgReceiver
	 */
	Thread threadReceiver;
	/**
	 * objet traitant les messages venant du serveur
	 */
	MessageHandler msgHandler;

	/**
	 * IHM du programme
	 */
	static IHM ihm;

	/**
	 * Creer un client en initialisant une Socket de mêmes parametres puis les objets de gestion des messages et l'IHM
	 * @param  ip           l'ip de la Socket à initialiser
	 * @param  port         le port de la Socket à initialiser
	 */
	public Client(String ip, int port)
	{
		this(ip, port, false);
	}

	/**
	 * Creer un client en initialisant une Socket de mêmes parametres puis les objets de gestion des messages et l'IHM
	 * @param  ip           l'ip de la Socket à initialiser
	 * @param  port         le port de la Socket à initialiser
	 * @param
	 */
	public Client(String ip, int port, boolean hasIHM)
	{
		if (hasIHM)
		{
			ihm = new IHMSwing(this);
		}
		else
		{
			ihm = new IHMServer(this);
		}

		msgHandler = new MessageHandler(this);

		network = new Network(ip, port, msgHandler);

		threadReceiver = new Thread(network);
		threadReceiver.start();
	}

	public Network getNetwork()
	{
		return network;
	}

	public IHM getIhm()
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
	public static void main(String[] args)
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
