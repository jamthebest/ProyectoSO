package com.hm;

import com.hm.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BuscarActivity extends Activity implements OnClickListener {
	
	
	private EditText con;
	private SQLiteDatabase base;
	private static final String TAG = "bdhomeworks";   
	private static final String nombreBD = "homeworks";   
	private final String[] datos =
	        new String[]{"Código Clase","Nombre Clase","Profesor"};
	private Spinner cmbOpciones;
	  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		con = (EditText) findViewById(R.id.buscar_campo);
		View boton1 = findViewById(R.id.buscar_buscar);
		View boton2 = findViewById(R.id.buscar_atras);
		boton1.setOnClickListener(this);
		boton2.setOnClickListener(this);
		
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
		            android.R.layout.select_dialog_singlechoice, datos);
		cmbOpciones = (Spinner)findViewById(R.id.CmbOpciones);
		/*
		cmbOpciones.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				if(position == 1)
					con.setInputType(InputType.TYPE_CLASS_NUMBER);
				else
					con.setInputType(InputType.TYPE_CLASS_TEXT);
			}

			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		*/
		cmbOpciones.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				if(position == 0){
					con.setInputType(InputType.TYPE_CLASS_TEXT);
					con.setHint("Código de la Clase");
				}
				else{
					con.setInputType(InputType.TYPE_CLASS_TEXT);
					if(position == 1)
						con.setHint("Nombre de la Clase");
					else
						con.setHint("Nombre del Profesor");
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

		});
		cmbOpciones.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar, menu);
		return true;
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId() == findViewById(R.id.buscar_buscar).getId()){
			abrirBasedatos();
    		Cursor cursor = null;
    		String codigo="";
    		String nombre="";
    		String profesor="";
    		boolean pasar = true;
    		
    		String list = (cmbOpciones.getItemAtPosition
    				(cmbOpciones.getSelectedItemPosition()).toString());
    		
    		//if(this.getTitle().length()==8){
    		if(list == "Codigo Clase" && con.getText().length()>0){
    			codigo = con.getText().toString();
        		cursor = base.rawQuery("SELECT * " + 
        				"FROM trabajo WHERE codigoclase = '"+codigo+"'", null);
			}else if(list == "Nombre Clase" && con.getText().length()>0){
				nombre = con.getText().toString();
				cursor = base.rawQuery("SELECT * " + 
        				"FROM trabajo WHERE clase LIKE '%"+nombre+"%'", null);
			}else if(list == "Profesor" && con.getText().length()>0){
				profesor = con.getText().toString();
				cursor = base.rawQuery("SELECT * " + 
        				"FROM trabajo WHERE profesor LIKE '%"+profesor+"%'", null);
			}else{
				pasar = false;
				Toast.makeText(getApplicationContext(),"Ingrese el "+
			    list + " del Trabajo a Buscar", Toast.LENGTH_LONG).show();
			}
    		
    		
    		if(pasar){
    			if(cursor.getCount() == 0){
    				Toast.makeText(getApplicationContext(), "No se Encontraron Trabajos con esos Parámetros", Toast.LENGTH_LONG).show();
    				cursor.close();
    			}else{
    				while(cursor.moveToNext()){
    					codigo = cursor.getString(0);
    					nombre = cursor.getString(1);
    					profesor = cursor.getString(2);
    				}
    				cursor.close();
    				Intent i = new Intent(this,BuscarActivity.class); //Ir a pantalla Buscar
					i.putExtra("nombre", nombre);
					i.putExtra("conte", profesor);
					i.putExtra("num", codigo);
					startActivity(i);
					finish();
    			}
    			
    		}
    		
		}else{
			Intent j = new Intent(this, TrabajosActivity.class);
			startActivity(j);
			finish();
		}
	}
	
	private void abrirBasedatos(){   
	    try{   
	      base = openOrCreateDatabase(nombreBD, MODE_WORLD_WRITEABLE, null);
	    }    
	    catch (Exception e){   
	      Log.i(TAG, "Error al abrir o crear la base de datos " + e);   
	    }   
	  }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //moveTaskToBack(true);          
	    	Intent j = new Intent(this, HMActivity.class);
			startActivity(j);
			finish();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
