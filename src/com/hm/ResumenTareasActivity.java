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
import android.widget.Toast;

public class ResumenTareasActivity extends Activity implements OnClickListener {

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
		setContentView(R.layout.activity_resumentareas);
		View boton = findViewById(R.id.aceptar_resumen_tareas);
        boton.setOnClickListener(this);
        
        datos = (GridView) findViewById(R.id.datos_resumen_tareas);
        
        abrirBasedatos();
        Cursor x = baseDatos.rawQuery("SELECT * FROM tarea", null);
        gridArray.add(new Item("Clase"));
        gridArray.add(new Item("Alumno"));
        gridArray.add(new Item("Descripción"));
        gridArray.add(new Item("Fecha"));
        gridArray.add(new Item("Estado"));
        if(x.getCount()>0){
      	  while(x.moveToNext()){
      		Cursor y = baseDatos.rawQuery("SELECT * FROM trabajo WHERE id = '" +x.getString(1)+  "'", null);
      		Cursor z = baseDatos.rawQuery("SELECT * FROM alumno WHERE id in (select alumnoid from trabajoalumno where trabajoid = '" +x.getString(1)+  "')", null);
      		y.moveToNext();
      		z.moveToNext();
      		  	gridArray.add(new Item(y.getString(1)));
      		  gridArray.add(new Item(z.getString(1)));
      		  gridArray.add(new Item(x.getString(2)));
      		  gridArray.add(new Item(x.getString(3)));
      		  gridArray.add(new Item(x.getString(4).equals("0") ? "Terminada" : "No Terminada"));
				y.close();
				z.close();
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
		
		if(vista.getId()==findViewById(R.id.aceptar_resumen_tareas).getId()){
			Intent i = new Intent(this,HMActivity.class);
			i.putExtra("x", 0);
			startActivity(i);
			finish();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Toast.makeText(getApplicationContext(), "No puede regresar en estaa etapa" , Toast.LENGTH_SHORT).show();
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
