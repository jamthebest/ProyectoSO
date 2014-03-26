package com.hm;

import com.hm.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class AcercaDeActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acercade);
		View boton = findViewById(R.id.aceptar_acerca);
        boton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trabajos, menu);
		return true;
	}

	@Override
	public void onClick(View vista) {
		if(vista.getId()==findViewById(R.id.aceptar_acerca).getId()){
			Intent i = new Intent(this,HMActivity.class);
			startActivity(i);
			finish();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //moveTaskToBack(true);          
	    	Intent i = new Intent(this,HMActivity.class);
			startActivity(i);
			finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
