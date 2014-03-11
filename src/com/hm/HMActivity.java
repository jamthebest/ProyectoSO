package com.hm;

import com.hm.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class HMActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hm);
		View boton = findViewById(R.id.trabajos);
        boton.setOnClickListener(this);
        View boton2 = findViewById(R.id.tareas);
        boton2.setOnClickListener(this);
        View boton3 = findViewById(R.id.reporte);
        boton3.setOnClickListener(this);
        View boton4 = findViewById(R.id.acerca);
        boton4.setOnClickListener(this);
        View boton5 = findViewById(R.id.salir);
        boton5.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hm, menu);
		return true;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onClick(View vista) {
		// TODO Auto-generated method stub
		
		if(vista.getId()==findViewById(R.id.trabajos).getId()){
			Intent i = new Intent(this,TrabajosActivity.class);
			startActivity(i);
			finish();
		}else{
			salir();
		}
    	/*
		}else if(vista.getId() == findViewById(R.id.reporte).getId()){
			Intent j = new Intent(this,AgregarActivity.class);
			startActivity(j);
    		finish();
		}else if(vista.getId() == findViewById(R.id.acerca).getId()){
			Intent j = new Intent(this,AgregarActivity.class);
			startActivity(j);
    		finish();
		}else{
			//Mostrar un mensaje de confirmaciÃ³n antes de salir  
	      	salir();
		}
		*/
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
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(HMActivity.this);
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
