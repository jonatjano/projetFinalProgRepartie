package client.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.net.MulticastSocket;
import java.net.InetAddress;

/**
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class Network implements Runnable
{
	private MessageHandler msgHandler;
	private MulticastSocket ms;
	private boolean stop;

	public Network(String ip, int port, MessageHandler msgHandler)
	{
		stop = false;

		this.ms = new MulticastSocket( InetAddress.getByName (ip) );
		this.msgHandler = msgHandler;
	}

	public void sendMessage(String message)
	{
		// TODO writer.println(message);
	}

	public void run()
	{
		while (!stop)
		{
			try {
				String line = ""; // TODO reader.readLine();
				msgHandler.onMessage(line);
			}
			catch (Exception e) {

			}
		}
	}
}
