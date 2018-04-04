package server.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.net.MulticastSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * Gère l'envoi et la réception des messages des autres clients.
 * @author	Adam Bernouy, Florent Dewilde, Jonathan Selle
 * @version	2018-04-01
 */
public class Network implements Runnable
{
	private MessageHandler  msgHandler;
	private MulticastSocket ms;
	private InetAddress     mcast;
	/** Port sur lequel le se trouve le serveur. */
	private int             port;
	private boolean         stop;


	public Network (String ip, int port, MessageHandler msgHandler)
	{
		try
		{
			this.stop       = false;

			DatagramSocket ds = new DatagramSocket ();
			String message = "Salut";
			DatagramPacket dp = new DatagramPacket (message.getBytes("UTF-8"), message.getBytes("UTF-8").length, InetAddress.getByName (ip), port);
			
			ds.send(dp);
			
			DatagramPacket msg = new DatagramPacket ( new byte[512], 512 );
			ds.receive(msg);
			
			
			byte[] data = msg.getData();

			int i = 0;
			for (;i < data.length && data[i] != 0; i++);
				
			byte[] newData = new byte[i];
			
			for (int j = 0 ; j < newData.length ; j++)
				newData[j] = data[j];
			
			
			msg.setData(newData);
			
			
			
			String[] info = new String(msg.getData(),"UTF-8").split(":");
			
			this.mcast = InetAddress.getByName (info[0]);
			this.port = Integer.parseInt(info[1]);
			this.ms = new MulticastSocket(this.port);
			ms.joinGroup ( mcast );
			
			this.msgHandler = msgHandler;	
			
			ds.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


    /**
     * Envoie un message à tous les clients.
     * @param message
     */
	public void sendMessage (String message)
	{
		try
		{
			DatagramPacket dp = new DatagramPacket (message.getBytes("UTF-8"), message.getBytes("UTF-8").length, this.mcast, this.port);	
			ms.send(dp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run ()
	{
		while (!stop)
		{
			try 
			{
				DatagramPacket msg = new DatagramPacket (new byte[512], 512);
				ms.receive(msg);
				
				byte[] data = msg.getData();

				int i = 0;
				for (;i < data.length && data[i] != 0; i++);
					
				byte[] newData = new byte[i];
				
				for (int j = 0 ; j < newData.length ; j++)
					newData[j] = data[j];
				
				msg.setData(newData);
				
				String line = new String( msg.getData(), "UTF-8");
				msgHandler.onMessage(line);
			}
			catch (Exception e) {

			}
		}
	}
}
