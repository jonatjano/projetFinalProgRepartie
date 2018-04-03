package client.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Jonathan Selle, Adam Bernouy
 * @version 2017-12-23
 */
public class MessageReceiver implements Runnable
{
	private MessageHandler msgHandler;
	private MulticastSocket ms;
	private boolean stop;

	public Network(String IP, int port, MessageHandler msgHandler)
	{
		stop = false;
		
		this.ms = new MulticastSocket( InetAddress.getByName (IP) );
		this.msgHandler = msgHandler;
	}
	
	public void sendMessage(String message)
	{
		writer.println(message);
	}

	public void run()
	{
		while (!stop)
		{
			try {
				String line = reader.readLine();
				msgHandler.onMessage(line);
			}
			catch (Exception e) {

			}
		}
	}
}
