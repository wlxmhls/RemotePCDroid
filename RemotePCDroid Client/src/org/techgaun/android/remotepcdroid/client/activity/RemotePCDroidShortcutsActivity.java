package org.techgaun.android.remotepcdroid.client.activity;

import org.techgaun.android.remotepcdroid.client.R;
import org.techgaun.android.remotepcdroid.client.app.RemotePCDroid;
import org.techgaun.remotepcdroid.protocol.RemotePCDroidActionReceiver;
import org.techgaun.remotepcdroid.protocol.action.HibernateAction;
import org.techgaun.remotepcdroid.protocol.action.MouseKeyboardAction;
import org.techgaun.remotepcdroid.protocol.action.RebootAction;
import org.techgaun.remotepcdroid.protocol.action.RemotePCDroidAction;
import org.techgaun.remotepcdroid.protocol.action.ShutDownAction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

public class RemotePCDroidShortcutsActivity extends Activity implements RemotePCDroidActionReceiver, OnItemClickListener
{
	private RemotePCDroid application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.application = (RemotePCDroid) this.getApplication();
		this.setContentView(R.layout.shortcuts);
		
		this.initViews();
	}
	
	private void initViews()
	{
		// initializes view
		final Button btnShutDown = (Button) findViewById(R.id.btnShutDown);
		final Button btnReboot = (Button) findViewById(R.id.btnReboot);
		final Button btnHibernate = (Button) findViewById(R.id.btnHibernate);
		final Button btnSpace = (Button) findViewById(R.id.btnSpace);
		final Button btnEsc = (Button) findViewById(R.id.btnEsc);
		final Button btnEnter = (Button) findViewById(R.id.btnEnter);
		final Button btnAltEnter = (Button) findViewById(R.id.btnAltEnter);
		final Button btnAltF4 = (Button) findViewById(R.id.btnAltF4);
		final Button btnSingleClk = (Button) findViewById(R.id.btnSingleClk);
		final Button btnDblClk = (Button) findViewById(R.id.btnDblClk);
		
		btnShutDown.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendShutDownRequest();
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_pcshutdown);
			}
		});
		
		btnReboot.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendRebootRequest();
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_pcreboot);
			}
		});
		
		btnHibernate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sendHibernateRequest();
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_pchibernate);
			}
		});
		
		btnSpace.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sentMouseKeyboard(0);
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_space);
			}
		});
		
		btnEsc.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sentMouseKeyboard(1);
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_esc);
			}
		});
		
		btnEnter.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sentMouseKeyboard(2);
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_enter);
			}
		});
		
		btnAltEnter.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sentMouseKeyboard(3);
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_altenter);
			}
		});
		
		btnAltF4.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sentMouseKeyboard(4);
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_altf4);
			}
		});
		
		btnSingleClk.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sentMouseKeyboard(5);
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_singleclk);
			}
		});
		
		btnDblClk.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				sentMouseKeyboard(6);
				RemotePCDroidShortcutsActivity.this.application.showMyAppToast(R.string.text_dblclk);
			}
		});
	}
	
	protected void sentMouseKeyboard(int ch)
	{
		switch (ch)
		{
			case 0: // 空格键
				this.application.sendAction(new MouseKeyboardAction(0));
				break;
			case 1: // Esc键
				this.application.sendAction(new MouseKeyboardAction(1));
				break;
			case 2: // 回车键
				this.application.sendAction(new MouseKeyboardAction(2));
				break;
			case 3: // ALT 回车键
				this.application.sendAction(new MouseKeyboardAction(3));
				break;
			case 4: // ALT F4
				this.application.sendAction(new MouseKeyboardAction(4));
				break;
			case 5: // 鼠标单击
				this.application.sendAction(new MouseKeyboardAction(5));
				break;
			case 6: // 鼠标双击
				this.application.sendAction(new MouseKeyboardAction(6));
				break;
		}
	}
	
	protected void sendShutDownRequest()
	{
		this.application.sendAction(new ShutDownAction(0));
	}
	
	protected void sendRebootRequest()
	{
		this.application.sendAction(new RebootAction(0));
	}
	
	protected void sendHibernateRequest()
	{
		this.application.sendAction(new HibernateAction());
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		this.application.unregisterActionReceiver(this);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		this.application.registerActionReceiver(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
	}
	
	@Override
	public void receiveAction(RemotePCDroidAction action)
	{
	}
}
