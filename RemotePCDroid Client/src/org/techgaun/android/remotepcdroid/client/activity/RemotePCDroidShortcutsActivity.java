package org.techgaun.android.remotepcdroid.client.activity;

import org.techgaun.android.remotepcdroid.client.R;
import org.techgaun.android.remotepcdroid.client.app.RemotePCDroid;
import org.techgaun.remotepcdroid.protocol.RemotePCDroidActionReceiver;
import org.techgaun.remotepcdroid.protocol.action.HibernateAction;
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
