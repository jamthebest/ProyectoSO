package com.hm;

import java.io.IOException;

import com.hm.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

public class InicioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		
		Base db = new Base(this, "homeworks", null, 1);
		try {
			db.createDataBase();
			db.openDataBase();
		} catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent i = new Intent(InicioActivity.this,HMActivity.class);
				startActivity(i);
				finish();
			}
		}, 5000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //moveTaskToBack(true);          
	    	Toast.makeText(getApplicationContext(), 
	        		  "No puede salir de este punto", Toast.LENGTH_LONG).show();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
