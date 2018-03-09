package org.techgaun.android.remotepcdroid.client.activity;

import org.techgaun.android.remotepcdroid.client.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class RemotePCDroidInit extends Activity
{
	private ProgressDialog startProgress;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.init);
		
		initialize();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		initialize();
	}
	
	protected void onPause()
	{
		super.onPause();
	}
	
	private void initialize()
	{
		startProgress = new ProgressDialog(this);
		startProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		startProgress.setCancelable(true);
		
		ProgressDialog.show(RemotePCDroidInit.this, "", "正在加载...");
		
		new Handler().postDelayed(new Runnable()
		{
			
			@Override
			public void run()
			{
				startProgress.dismiss();
				Intent intent = new Intent(RemotePCDroidInit.this, RemotePCDroidControlActivity.class);
				startActivity(intent);
			}
		}, 2000);
		
	}
}
