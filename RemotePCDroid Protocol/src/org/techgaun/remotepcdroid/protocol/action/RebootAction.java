package org.techgaun.remotepcdroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RebootAction extends RemotePCDroidAction
{
	public int time;
	
	public RebootAction(int time)
	{
		this.time = time;
	}
	
	@Override
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		dos.writeByte(REBOOT_SERVER);
		dos.writeInt(this.time);
	}
	
	public static RebootAction parse(DataInputStream dis) throws IOException
	{
		int time = dis.readInt();
		return new RebootAction(time);
	}
}
