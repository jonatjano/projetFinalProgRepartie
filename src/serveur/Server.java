package server;

import java.net.ServerSocket;
import java.net.URL;
import java.net.Inet4Address;
import java.net.DatagramSocket;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Map;
import java.util.HashMap;

import client.Client;

import server.job.AcceptClient;
import server.ihm.IHM;
import server.ihm.IHMConsol;


public class Server
{
	public static final int DEFAULT_PORT = 6000;

	public static final String DEFAULT_MULT_CAST_IP = "231.246.46.52";
	public static final int DEFAULT_MULT_CAST_PORT = 2375;

	private static Map<String, String> config;

	private Client client;

	private DatagramSocket ds;
	private String mCastIP;
	private int mCastPort;

	private AcceptClient accCli;
	private Thread thAccCli;
	private IHM ihm;

	public IHM getIHM()
	{
		return this.ihm;
	}

	private String getIp() throws Exception
	{
		/*URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

		String ip = in.readLine();
		if (ip.matches("([0-9]{1,3}.){3}[0-9]{0,3}"))
			return ip;
*/
		return Inet4Address.getLocalHost().getHostAddress();
	}

	public String getMultiCast()
	{
		return this.mCastIP + ":" + this.mCastPort;
	}

	public Server(IHM ihm)
	{
		this(Server.DEFAULT_PORT, ihm);
	}


	public Server(int port, IHM ihm)
	{
		try
		{
			this.mCastIP = Server.DEFAULT_MULT_CAST_IP;
			this.mCastPort = Server.DEFAULT_MULT_CAST_PORT;
			this.ds = new DatagramSocket (port);

			this.accCli = new AcceptClient(this, this.ds);
			this.thAccCli = new Thread(this.accCli);
			this.thAccCli.start();

			this.ihm = ihm;
			ihm.pMessage(IHM.SERVER_INFO, this.getIp() + ":" + port);

			this.client = new Client (this.getIp(), port);
			this.client.reSend();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void readConfig(String fileName)
	{
		config = new HashMap<String, String>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			line = reader.readLine();

			do
			{
				if (!line.equals(""))
				{
					String[] sLine = line.split(":");
					config.put(sLine[0], sLine[1]);
				}

				line = reader.readLine();
			} while (line != null);
		}
		catch (Exception e)
		{
			//System.out.println("La lecture du fichier de configuration à échoué");
		}
	}

	public static void main(String[] arg)
	{
		readConfig("../config");

		IHM ihm;
		int port = Server.DEFAULT_PORT;

		if (config.size() != 0)
		{
			switch (config.get("defaultIHM"))
			{
				// case "Swing":
				// 	ihm = new IHMSwing();
				// break;
				// case "javaFx":
				// 	ihm = new IHMJavaFx();
				// break;
				case "CUI":
				default:
					ihm = new IHMConsol();
			}



			try {
				port = Integer.parseInt(config.get("defaultPort"));
			}
			catch (Exception e)
			{
				ihm.pError(IHM.CONF_PORT_ERROR);
			}
		}
		else
			ihm = new IHMConsol();

		if (arg.length >= 1)
		{
			try
			{
				port = Integer.parseInt(arg[0]);
			}
			catch(Exception e)
			{
				ihm.pError(IHM.PORT_ERROR);
			}
		}
		Server s  = new Server(port,ihm);
	}

}
