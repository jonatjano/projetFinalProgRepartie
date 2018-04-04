package server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import server.job.Network;
import server.job.MessageHandler;


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

	private static int nbClients = 0;

	/** Socket de connexion au serveur. */
	Socket          socket;
	/** Objet gérant uniquement l'envoi et la réception de messages. */
	Network         network;
	/** Thread recevant les messages des autres clients. */
	Thread          threadReceiver;
	/** Objet traitant les messages du serveur. */
	MessageHandler  msgHandler;
    /** Pseudo du client. */
    String          pseudo;


	/**
	 * Creer un client en initialisant une Socket de mêmes parametres puis les objets de gestion des messages et l'IHM
	 * @param  ip           l'ip de la Socket à initialiser
	 * @param  port         le port de la Socket à initialiser
	 */
	public Client (String ip, int port)
	{
	    this.msgHandler     = new MessageHandler(this);
		this.network 	    = new Network(ip, port, msgHandler);
		this.threadReceiver = new Thread(network);
		this.threadReceiver.start();

		this.pseudo = "";
	}

	public Network  getNetwork ()
	{
		return network;
	}

    public int calcNbClients ()
    {
        return Client.nbClients++;
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
}
