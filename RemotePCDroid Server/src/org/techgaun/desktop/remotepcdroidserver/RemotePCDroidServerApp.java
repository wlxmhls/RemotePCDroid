package org.techgaun.desktop.remotepcdroidserver;

import java.awt.AWTException;
import java.io.IOException;
import java.util.prefs.Preferences;

import org.techgaun.desktop.remotepcdroidserver.gui.RemotePCDroidServerGui;

public class RemotePCDroidServerApp
{
	public static String os_type = System.getProperty("os.name");
	private Preferences preferences;
	private RemotePCDroidServerGui trayIcon;
	
	private RemotePCDroidServerTcp serverTcp;
	
	public RemotePCDroidServerApp() throws AWTException, IOException
	{
		this.preferences = Preferences.userNodeForPackage(this.getClass());
		
		this.trayIcon = new RemotePCDroidServerGui(this);
		
		try
		{
			this.serverTcp = new RemotePCDroidServerTcp(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Preferences getPreferences()
	{
		return preferences;
	}
	
	public RemotePCDroidServerGui getTrayIcon()
	{
		return trayIcon;
	}
	
	public RemotePCDroidServerTcp getServerTcp()
	{
		return serverTcp;
	}
	
	public void exit()
	{
		this.trayIcon.close();
		
		if (this.serverTcp != null)
		{
			this.serverTcp.close();
		}
		
		System.exit(0);
	}
	
	public static void main(String[] args)
	{
		try
		{
			new RemotePCDroidServerApp();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
