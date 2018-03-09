package org.techgaun.remotepcdroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ProtocolException;

public abstract class RemotePCDroidAction
{
	public static final byte AUTHENTICATION = 0;
	public static final byte AUTHENTICATION_RESPONSE = 1;
	public static final byte FILE_EXPLORE_REQUEST = 2;
	public static final byte FILE_EXPLORE_RESPONSE = 3;
	public static final byte SHUTDOWN_SERVER = 4;
	public static final byte REBOOT_SERVER = 5;
	public static final byte HIBERNATE_SERVER = 6;
	
	public static RemotePCDroidAction parse(DataInputStream dis) throws IOException
	{
		byte type = dis.readByte();
		
		switch (type)
		{
			case AUTHENTICATION:
				return AuthenticationAction.parse(dis);
			case AUTHENTICATION_RESPONSE:
				return AuthenticationResponseAction.parse(dis);
			case FILE_EXPLORE_REQUEST:
				return FileExploreRequestAction.parse(dis);
			case FILE_EXPLORE_RESPONSE:
				return FileExploreResponseAction.parse(dis);
			case SHUTDOWN_SERVER:
				return ShutDownAction.parse(dis);
			case REBOOT_SERVER:
				return RebootAction.parse(dis);
			case HIBERNATE_SERVER:
				return HibernateAction.parse(dis);
				
			default:
				throw new ProtocolException();
		}
	}
	
	public abstract void toDataOutputStream(DataOutputStream dos) throws IOException;
}
