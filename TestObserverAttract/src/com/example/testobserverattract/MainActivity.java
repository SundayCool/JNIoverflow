package com.example.testobserverattract;

import com.example.testobserverattract.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.provider.Settings;

public class MainActivity extends ActionBarActivity 
implements OnClickListener{

	public TextView mTextView;
	private Handler mHandler = new Handler();
	public long mNum = 0;
	private Runnable myRunnable = new Runnable() {    
        public void run() {  
        	/*
			for (int i = 0; i < 25600; i++) {
				SettingsObserver observer = new SettingsObserver(null);
				observer.observe();	
				if (i % 500 == 0) {
					mTextView.setText(i + " Observers had been created");
				}
			} */
        	mTextView.setText(mNum + " Observers had been created");
        }
    }; 
	
	class SettingsObserver extends ContentObserver {
	    SettingsObserver(Handler handler) {
	        super(handler);
	    }
	    
	    void observe() {
	    	ContentResolver resolver = getContentResolver();
	    	resolver.registerContentObserver(
	    			Settings.System.getUriFor(Settings.System.ALARM_ALERT), false, this );
	
	    	updateSettings();
	    }
	    @Override 
		public void onChange(boolean selfChange) {
	    	updateSettings();
	    }
	    
	    @Override
	    protected void finalize() throws Throwable {
//	    	Log.d(TAG, "===== finalize called");
	    	getContentResolver().unregisterContentObserver(this);
	    	// TODO Auto-generated method stub
	    	super.finalize();
	    }
	}
	
	public void updateSettings() {
	}
	
	public void onClick(View src)
	{
		try {
			
			new Thread() {
				@Override
				public void run(){
					
					for (int i = 0; i < 25600; i++) {
						SettingsObserver observer = new SettingsObserver(null);
						observer.observe();	
						if (i % 100 == 0) {
							mNum = i;
							mHandler.post(myRunnable);
						}
					}
				}
			}.start();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		

	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		Button aButton = (Button)findViewById(R.id.button1);
		aButton.setOnClickListener(this);
		
		mTextView = (TextView)findViewById(R.id.textView1);
		mTextView.setText(0 + " Observers had been created");

/*		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
