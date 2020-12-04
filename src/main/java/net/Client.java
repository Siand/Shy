package net;

public interface Client
{
	public void send(String data);
	public void close();
	public boolean hasConnected();
}
