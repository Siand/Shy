package net;

public class ClientFactory
{
	private static Client client;
	static int port = 1237;
	
	public static Client getClient() {
		return client;
	}
	
	public static void create() {
		client = new HostClient(port);
	}
	
	public static void create(String host) {
		client = new JoinClient(host, port);
	}
	
	public static boolean hasConnected() {
		return client.hasConnected();
	}
}
