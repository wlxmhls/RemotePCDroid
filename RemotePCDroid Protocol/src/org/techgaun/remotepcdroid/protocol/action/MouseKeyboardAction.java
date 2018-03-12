package org.techgaun.remotepcdroid.protocol.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MouseKeyboardAction extends RemotePCDroidAction
{
	public int choice = -1;
	
	public MouseKeyboardAction(int choice)
	{
		this.choice = choice;
	}
	
	public static MouseKeyboardAction parse(DataInputStream dis) throws IOException
	{
		int ch = dis.readInt();
		return new MouseKeyboardAction(ch);
	}
	
	public void toDataOutputStream(DataOutputStream dos) throws IOException
	{
		
		dos.writeByte(MOUSEKEYBOARD);
		dos.writeInt(this.choice);
	}
}
