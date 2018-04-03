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
	
	public AcceptClient(Server s, DatagramSocket ds)
	{
		this.serv = s;
		this.ds = ds;
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				DatagramPacket msg = new DatagramPacket (new byte[512], 512);
				
				ds.receive(msg);
				
				String message = new String( s.getMultiCast() ,"UTF-8");
				
				DatagramPacket reponse = new DatagramPacket (message, message.length(), msg.getAddress(), msg.getPort());
				
				ds.send(reponse);
				
				Thread.sleep(AcceptClient.WAITING_TIME_CONNECTION);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}