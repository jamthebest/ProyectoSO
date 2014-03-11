package com.hm;

import com.hm.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
        View boton2 = findViewById(R.id.buscar);
        boton2.setOnClickListener(this);
        View boton3 = findViewById(R.id.resumen);
        boton3.setOnClickListener(this);
        View boton4 = findViewById(R.id.atras);
        boton4.setOnClickListener(this);
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
		}else{		//boton atras
			Intent i = new Intent(this,HMActivity.class);
			startActivity(i);
			finish();;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //moveTaskToBack(true);          
	    	salir();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	public void salir(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(TrabajosActivity.this);
      	alertDialog.setMessage("¿Desea Salir de la Aplicación?");
      	alertDialog.setTitle("Salir");
      	alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
      	alertDialog.setCancelable(false);
      	alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
              Toast.makeText(getApplicationContext(), 
            		  "Hasta Pronto!", Toast.LENGTH_LONG).show();
              finish();
            }
        }); 
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
        	public void onClick(DialogInterface dialog, int which){
        		Toast.makeText(getApplicationContext(), 
        			"Ok!, XD", Toast.LENGTH_LONG).show();
        	}
        }); 
        alertDialog.show();
	}

}
