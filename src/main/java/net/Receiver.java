package net;

import UI.Game;
import misc.Player;
import observers.GameObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver implements Runnable {

	boolean running = true;
	Socket clientSocket;

	public Receiver(Socket s) {
		clientSocket = s;
	}
	
	public void run()
	{
		BufferedReader in;
		try
		{
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while(running) {
				String data = in.readLine();
				if(data != null) {
					if (data.contains("Start:")) {
						data = data.substring(6);
						Game game = new Game(data.equals("DM") ? Player.DM : Player.HERO);
						GameObserver.Instance().subscribe(game);
					} else {
						GameObserver.Instance().play(data);
					}
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("connection closed");
			running = false;
		}
	}
	
	public void setRunning(boolean r) {
		running = r;
	}
}
