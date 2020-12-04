package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class JoinClient implements Client
{

	Socket clientSocket = null;
	boolean connected = false;
	PrintWriter out = null;
	Receiver receiver = null;
	public JoinClient(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Failed to connect to server");
		}
		connected = true;
		receiver = new Receiver(clientSocket);
		(new Thread(receiver)).start();
	}

	@Override
	public void send(String data)
	{
		out.println(data);
	}
	
	public void close() {
		if(!connected) {
			return;
		}
		receiver.setRunning(false);
		try {
			clientSocket.close();
			connected = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean hasConnected()
	{
		return connected;
	}

}
