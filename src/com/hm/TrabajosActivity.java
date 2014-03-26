package com.hm;

import com.hm.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TrabajosActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trabajos);
		View boton = findViewById(R.id.nuevo);
        boton.setOnClickListener(this);
        View boton3 = findViewById(R.id.resumen);
        boton3.setOnClickListener(this);
        View boton4 = findViewById(R.id.atras);
        boton4.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("id"))
        	Toast.makeText(getApplicationContext(), bundle.getString("id"), Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trabajos, menu);
		return true;
	}

	@Override
	public void onClick(View vista) {
		if(vista.getId()==findViewById(R.id.nuevo).getId()){
			Intent i = new Intent(this,NuevoActivity.class);
			startActivity(i);
			finish();
		}else if(vista.getId()==findViewById(R.id.resumen).getId()){
			Intent i = new Intent(this,ResumenTrabajosActivity.class);
			startActivity(i);
			finish();
		}else{		//boton atras
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
