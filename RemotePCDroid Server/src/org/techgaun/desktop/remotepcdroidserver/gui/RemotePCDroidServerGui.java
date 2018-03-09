package org.techgaun.desktop.remotepcdroidserver.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.techgaun.desktop.remotepcdroidserver.RemotePCDroidServerApp;
import org.techgaun.remotepcdroid.protocol.RemotePCDroidConnection;
import org.techgaun.remotepcdroid.protocol.tcp.RemotePCDroidConnectionTcp;

public class RemotePCDroidServerGui
{
	private Preferences preferences;
	private RemotePCDroidServerApp application;
	private TrayIcon trayIcon;
	private JFrame frame;
	private JLabel lblInfo;
	
	public RemotePCDroidServerGui(RemotePCDroidServerApp application) throws AWTException, IOException
	{
		this.application = application;
		this.preferences = this.application.getPreferences(); // several
		                                                      // preferences for
		                                                      // server
		this.initializeGui();
	}
	
	public void notifyConnection(RemotePCDroidConnection connection)
	{
		String message = "";
		
		if (connection instanceof RemotePCDroidConnectionTcp)
		{
			RemotePCDroidConnectionTcp connectionTcp = (RemotePCDroidConnectionTcp) connection;
			message = connectionTcp.getInetAddress().getHostAddress() + ":" + connectionTcp.getPort();
		}
		
		this.lblInfo.setText("检测到客户端连接 : " + message);
		this.trayIcon.displayMessage("提示", "检测到客户端连接 : " + message, MessageType.INFO);
	}
	
	public void notifyProtocolProblem()
	{
		this.lblInfo.setText("发生未知错误.");
		this.trayIcon.displayMessage("警告", "发生未知错误.", MessageType.WARNING);
	}
	
	public void close()
	{
		SystemTray.getSystemTray().remove(this.trayIcon);
	}
	
	private void initializeGui() throws AWTException, IOException
	{
		this.frame = new JFrame();
		this.frame.setVisible(true);
		Color bg = new Color(255, 255, 255);
		this.frame.getContentPane().setBackground(bg);
		
		this.lblInfo = new JLabel("正在初始化...\n");
		
		this.lblInfo.setHorizontalAlignment(JLabel.CENTER);
		this.lblInfo.setVerticalAlignment(JLabel.CENTER);
		this.frame.getContentPane().add(lblInfo);
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(UIManager.getColor("MenuBar.foreground"));
		this.frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("操作");
		menuBar.add(mnFile);
		JMenuItem mntmExit = new JMenuItem("退出");
		mntmExit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnServer = new JMenu("服务端");
		menuBar.add(mnServer);
		
		JMenuItem mntmPass = new JMenuItem("修改连接密码");
		mnServer.add(mntmPass);
		mntmPass.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String password = RemotePCDroidServerGui.this.preferences.get("password", RemotePCDroidConnection.DEFAULT_PASSWORD);
				password = JOptionPane.showInputDialog("请输入新的连接密码", password);
				if (password != null)
				{
					RemotePCDroidServerGui.this.preferences.put("password", password);
				}
				else
				{
					RemotePCDroidServerGui.this.preferences.put("password", "");
				}
			}
		});
		
		JMenuItem mntmStatus = new JMenuItem("服务信息");
		mnServer.add(mntmStatus);
		mntmStatus.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				StringBuilder message = new StringBuilder();
				
				if (RemotePCDroidServerGui.this.application.getServerTcp() != null)
				{
					message.append("服务正在运行:\n");
					message.append(RemotePCDroidServerGui.this.getTcpListenAddresses());
				}
				else
				{
					message.append("服务未运行.");
				}
				lblInfo.setText(message.toString());
				JOptionPane.showMessageDialog(null, message.toString());
			}
		});
		
		JMenuItem mntmPort = new JMenuItem("修改端口");
		mnServer.add(mntmPort);
		mntmPort.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					int port = RemotePCDroidServerGui.this.preferences.getInt("port", RemotePCDroidConnectionTcp.DEFAULT_PORT);
					String newPortString = JOptionPane.showInputDialog("端口号", port);
					int newPort = Integer.parseInt(newPortString);
					RemotePCDroidServerGui.this.preferences.putInt("port", newPort);
					JOptionPane.showMessageDialog(null, "请重启服务使新的端口号生效.");
					lblInfo.setText("请重启服务使新的端口号生效.");
				}
				catch (NumberFormatException nfe)
				{
					nfe.printStackTrace();
				}
			}
		});
		
		JMenu mnHelp = new JMenu("帮助");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelpTopicsf = new JMenuItem("使用说明");
		mnHelp.add(mntmHelpTopicsf);
		
		mntmHelpTopicsf.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// help information
				JFrame helpFrame = new JFrame("程序使用指南");
				helpFrame.setSize(300, 300);
				helpFrame.setVisible(true);
				helpFrame.setLayout(new GridLayout());
				helpFrame.setResizable(false);
				
				JTextArea help = new JTextArea();
				help.setBackground(null);
				help.setBorder(null);
				help.setEditable(false);
				help.setLineWrap(true);
				help.setWrapStyleWord(true);
				help.setFocusable(false);
				help.setBorder(BorderFactory.createTitledBorder("使用说明"));
				String info = "程序使用指南\n";
				info += "1 - 在电脑上运行服务端.\n";
				info += "2 - 在安卓手机上运行客户端.\n";
				info += "3 - 在客户端上设置服务端的相关信息即可.";
				help.setText(info);
				help.setVisible(true);
				helpFrame.add(help);
				
			}
		});
		
		JMenuItem mntmAbout = new JMenuItem("关于");
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// help information
				JFrame helpFrame = new JFrame("关于程序");
				helpFrame.setSize(300, 200);
				helpFrame.setVisible(true);
				helpFrame.setLayout(new GridLayout());
				helpFrame.setResizable(false);
				
				JTextArea help = new JTextArea();
				help.setBackground(null);
				help.setBorder(null);
				help.setEditable(false);
				help.setLineWrap(true);
				help.setWrapStyleWord(true);
				help.setFocusable(false);
				help.setBorder(BorderFactory.createTitledBorder("程序开发者"));
				String info = "该程序基于 https://github.com/neaGaze/Remote-PC-Droid，感谢原作者。\n";
				
				help.setText(info);
				help.setVisible(true);
				helpFrame.add(help);
				
			}
		});
		
		PopupMenu menu = new PopupMenu();
		
		MenuItem menuItemOpenWindow = new MenuItem("显示窗口");
		menuItemOpenWindow.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RemotePCDroidServerGui.this.frame.setVisible(true);
			}
			
		});
		menu.add(menuItemOpenWindow);
		menu.addSeparator();
		
		MenuItem menuItemPassword = new MenuItem("修改连接密码");
		menuItemPassword.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String password = RemotePCDroidServerGui.this.preferences.get("password", RemotePCDroidConnection.DEFAULT_PASSWORD);
				password = JOptionPane.showInputDialog("请输入新的连接密码", password);
				if (password != null)
				{
					RemotePCDroidServerGui.this.preferences.put("password", password);
				}
				else
				{
					RemotePCDroidServerGui.this.preferences.put("password", "");
				}
			}
		});
		menu.add(menuItemPassword);
		
		menu.addSeparator();
		
		MenuItem menuItemWifiServer = new MenuItem("服务信息");
		menuItemWifiServer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				StringBuilder message = new StringBuilder();
				
				if (RemotePCDroidServerGui.this.application.getServerTcp() != null)
				{
					message.append("服务正在运行:\n");
					message.append(RemotePCDroidServerGui.this.getTcpListenAddresses());
				}
				else
				{
					message.append("服务未运行.");
				}
				lblInfo.setText(message.toString());
				JOptionPane.showMessageDialog(null, message.toString());
			}
		});
		menu.add(menuItemWifiServer);
		menu.addSeparator();
		
		MenuItem menuItemPort = new MenuItem("修改端口");
		menuItemPort.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int port = RemotePCDroidServerGui.this.preferences.getInt("port", RemotePCDroidConnectionTcp.DEFAULT_PORT);
					String newPortString = JOptionPane.showInputDialog("端口号", port);
					int newPort = Integer.parseInt(newPortString);
					RemotePCDroidServerGui.this.preferences.putInt("port", newPort);
					JOptionPane.showMessageDialog(null, "请重启服务使新的端口号生效.");
					lblInfo.setText("请重启服务使新的端口号生效.");
				}
				catch (NumberFormatException nfe)
				{
					nfe.printStackTrace();
				}
			}
		});
		menu.add(menuItemPort);
		
		menu.addSeparator();
		
		MenuItem menuItemExit = new MenuItem("退出");
		menuItemExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				RemotePCDroidServerGui.this.application.exit();
			}
		});
		menu.add(menuItemExit);
		
		this.trayIcon = new TrayIcon(ImageIO.read(this.getClass().getResourceAsStream("icon.png")));
		this.trayIcon.setImageAutoSize(true);
		this.trayIcon.setToolTip("RemotePCDroid服务端");
		this.trayIcon.setPopupMenu(menu);
		
		SystemTray.getSystemTray().add(this.trayIcon);
		
		StringBuilder message = new StringBuilder("服务正在运行\n");
		message.append(this.getTcpListenAddresses());
		
		this.trayIcon.displayMessage("提示", message.toString(), TrayIcon.MessageType.INFO);
		lblInfo.setText("服务正在运行.");
		
		this.frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowIconified(WindowEvent e)
			{
				RemotePCDroidServerGui.this.frame.dispose();
			}
		});
		
		this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.frame.setVisible(false);
	}
	
	private String getTcpListenAddresses()
	{
		int port = this.preferences.getInt("port", RemotePCDroidConnectionTcp.DEFAULT_PORT);
		
		StringBuilder message = new StringBuilder();
		
		try
		{
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements())
			{
				NetworkInterface currentInterface = interfaces.nextElement();
				
				Enumeration<InetAddress> addresses = currentInterface.getInetAddresses();
				
				while (addresses.hasMoreElements())
				{
					InetAddress currentAddress = addresses.nextElement();
					
					if (!currentAddress.isLoopbackAddress() && !(currentAddress instanceof Inet6Address))
					{
						message.append(currentAddress.getHostAddress() + ":" + port + " \n");
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return message.toString();
	}
}
