package server.job;

import server.Server;
import server.ihm.IHM;

import java.util.List;
import java.util.ArrayList;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class AcceptClient implements Runnable
{
	public static final int WAITING_TIME_CONNECTION = 500;
	
	private Server serv;
	DatagramSocket ds;
	
	public AcceptClient (Server s, DatagramSocket ds)
	{
		this.serv 	= s;
		this.ds 	= ds;
	}

	/**
	 * Accepte les clients qui tentent de se connecter.
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				DatagramPacket msg = new DatagramPacket (new byte[512], 512);
				
				ds.receive(msg);
				String message = serv.getMultiCast();
				
				DatagramPacket response = new DatagramPacket(message.getBytes("UTF-8"), message.getBytes("UTF-8").length, msg.getAddress(), msg.getPort());
				
				Thread.sleep(AcceptClient.WAITING_TIME_CONNECTION);
				ds.send(response);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}