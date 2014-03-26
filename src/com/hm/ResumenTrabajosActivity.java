package com.hm;

import java.util.ArrayList;

import com.hm.R;
import com.hm.CustomGridViewAdapter;
import com.hm.Item;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
import android.widget.GridView;
//import android.widget.TextView;

public class ResumenTrabajosActivity extends Activity implements OnClickListener {

	private GridView datos;
	public static SQLiteDatabase baseDatos;   
	private static final String TAG = "bdhomeworks";   
	private static final String nombreBD = "homeworks";
	
	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	 CustomGridViewAdapter customGridAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resumentrabajos);
		View boton = findViewById(R.id.aceptar_resumen);
        boton.setOnClickListener(this);
        
        datos = (GridView) findViewById(R.id.datos_resumen);
        
        abrirBasedatos();
        Cursor x = baseDatos.rawQuery("SELECT * FROM trabajo", null);
        gridArray.add(new Item("Clase"));
        gridArray.add(new Item("Profesor"));
        gridArray.add(new Item("DescripciÛn"));
        gridArray.add(new Item("Fecha"));
        gridArray.add(new Item("Estado"));
        if(x.getCount()>0){
      	  while(x.moveToNext()){
      		  	//TextView t = new TextView(this);
      		  	//t.setText(x.getString(1));
      		  	//datos.addView(t);
      		  	gridArray.add(new Item(x.getString(1)));
      		  gridArray.add(new Item(x.getString(3)));
      		  gridArray.add(new Item(x.getString(4)));
      		  gridArray.add(new Item(x.getString(5)));
      		  gridArray.add(new Item(x.getString(6).equals("0") ? "Terminada" : "No Terminada"));
				/*ar.add(t);
				t.setText(x.getString(3));
				//datos.addView(t);
				ar.add(t);
				t.setText(x.getString(4));
				//datos.addView(t);
				ar.add(t);
				t.setText(x.getString(5));
				//datos.addView(t);
				ar.add(t);
				t.setText(x.getString(6)=="0" ? "No Terminada" : "Terminada");
				//datos.addView(t);
				ar.add(t);
				*/
			  }
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
		datos.setAdapter(customGridAdapter);
        }
        x.close();
        
        
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
		
		if(vista.getId()==findViewById(R.id.aceptar_resumen).getId()){
			Intent i = new Intent(this,TrabajosActivity.class);
			i.putExtra("x", 0);
			startActivity(i);
			finish();
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
			//Mostrar un mensaje de confirmaci√≥n antes de salir  
	      	salir();
		}
		*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //moveTaskToBack(true);          
	    	Intent i = new Intent(this,TrabajosActivity.class);
			i.putExtra("x", 0);
			startActivity(i);
			finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
	private void abrirBasedatos(){ 
	    try{   
	      baseDatos = openOrCreateDatabase(nombreBD, MODE_WORLD_WRITEABLE, null);
	    }    
	    catch (Exception e){   
	      Log.i(TAG, "Error al abrir la base de datos:\n " + e);   
	    }   
	  }

}
