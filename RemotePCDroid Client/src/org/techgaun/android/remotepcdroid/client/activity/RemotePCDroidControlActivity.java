package org.techgaun.android.remotepcdroid.client.activity;

import org.techgaun.android.remotepcdroid.client.R;
import org.techgaun.android.remotepcdroid.client.activity.connection.RemotePCDroidConnListActivity;
import org.techgaun.android.remotepcdroid.client.app.RemotePCDroid;
import org.techgaun.remotepcdroid.protocol.RemotePCDroidActionReceiver;
import org.techgaun.remotepcdroid.protocol.action.RemotePCDroidAction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author samar
 */
public class RemotePCDroidControlActivity extends Activity implements RemotePCDroidActionReceiver
{
	/**
	 * @uml.property name="application"
	 * @uml.associationEnd
	 */
	private RemotePCDroid application;
	private SharedPreferences preferences;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.application = (RemotePCDroid) this.getApplication();
		
		this.preferences = this.application.getPreferences();
		
		this.checkFullscreen();
		
		this.setContentView(R.layout.control);
		
	}
	
	private void checkFullscreen()
	{
		if (this.preferences.getBoolean("fullscreen", false))
		{
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(Menu.NONE, 0, Menu.NONE, this.getResources().getString(R.string.text_file_explorer));
		menu.add(Menu.NONE, 1, Menu.NONE, this.getResources().getString(R.string.text_shortcuts));
		menu.add(Menu.NONE, 2, Menu.NONE, this.getResources().getString(R.string.text_connections));
		menu.add(Menu.NONE, 3, Menu.NONE, this.getResources().getString(R.string.text_settings));
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case 0:
				this.startActivity(new Intent(this, RemotePCDroidFEActivity.class));
				break;
			case 1:
				this.startActivity(new Intent(this, RemotePCDroidShortcutsActivity.class));
				break;
			case 2:
				this.startActivity(new Intent(this, RemotePCDroidConnListActivity.class));
				break;
			case 3:
				this.startActivity(new Intent(this, RemotePCDroidSettingsActivity.class));
				break;
		}
		
		return true;
	}
	
	@Override
	public void receiveAction(RemotePCDroidAction action)
	{
	}
	
	protected void onResume()
	{
		super.onResume();
		
		this.application.registerActionReceiver(this);
	}
	
	protected void onPause()
	{
		super.onPause();
		this.application.unregisterActionReceiver(this);
	}
	
}
