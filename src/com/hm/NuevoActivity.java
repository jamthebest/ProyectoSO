package com.hm;

import com.hm.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class NuevoActivity extends Activity implements OnClickListener {

	EditText clase, codigo, profesor, descripcion, fecha;
	RatingBar prioridad;
	
	public static SQLiteDatabase baseDatos;   
	private static final String TAG = "bdhomeworks";   
	private static final String nombreBD = "homeworks";   
	private static final String tablaTrabajo = "trabajo";
	//private static final String id = "SELECT id FROM trabajo ORDER BY id DESC LIMIT 1;";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo);
		
		clase   = (EditText) findViewById(R.id.clase);   
	    codigo = (EditText) findViewById(R.id.codigo);
	    profesor = (EditText) findViewById(R.id.profesor);
	    descripcion = (EditText) findViewById(R.id.descripcion_tra);
	    fecha = (EditText) findViewById(R.id.fecha);
	    prioridad = (RatingBar) findViewById(R.id.prioridad);
	    
		View boton = findViewById(R.id.aceptar);
        boton.setOnClickListener(this);
        View boton2 = findViewById(R.id.cancelar);
        boton2.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo, menu);
		return true;
	}

	@Override
	public void onClick(View vista) {
		if(vista.getId()==findViewById(R.id.nuevo).getId()){
			if(clase.getText().toString().length() == 0 || 
					codigo.getText().toString().length() == 0 ||
					profesor.getText().toString().length() == 0 ||
					descripcion.getText().toString().length() == 0 ||
					fecha.getText().toString().length() == 0 ||
					prioridad.toString().length() == 0){
				Toast.makeText(getApplicationContext(), 
		        		  "Ingrese Todos los Datos", Toast.LENGTH_LONG).show();
			}else{
			
				//Abrir la base de datos, se creará si no existe             		
				abrirBasedatos();

				//Insertar una fila o registro en la tabla "trabajo"
				//si la inserción es correcta devolverá true     
				boolean resultado = insertarFila(clase.getText().toString(), codigo.getText().toString(),
						profesor.getText().toString(), descripcion.getText().toString(),
						fecha.getText().toString(), prioridad.toString());
				if(resultado){
					Toast.makeText(getApplicationContext(),"Trabajo añadido correctamente"
	        			, Toast.LENGTH_LONG).show();
					Intent i = new Intent(this,TrabajosActivity.class);
					startActivity(i);
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "No se ha podido guardar el Trabajo", Toast.LENGTH_LONG).show();
				}
			}
		}
		else{
			cancelar();
		}
	}
	
	private void abrirBasedatos(){ 
	    try{   
	      baseDatos = openOrCreateDatabase(nombreBD, MODE_WORLD_WRITEABLE, null);
	    }    
	    catch (Exception e){   
	      Log.i(TAG, "Error al abrir la base de datos:\n " + e);   
	    }   
	  }
	
	
	private boolean insertarFila(String clase, String codigo, String profesor, 
			String descripcion, String fecha, String prioridad){   
	    ContentValues values = new ContentValues(); 
	    values.put("id", 1);
	    values.put("clase",clase );
	    values.put("codigoclase",codigo );
	    values.put("profesor", profesor);
	    values.put("descripcion",descripcion );
	    values.put("fecha",fecha );
	    values.put("prioridad",prioridad );
	    return (baseDatos.insert(tablaTrabajo, null, values) > 0);
	  }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        //moveTaskToBack(true);          
	    	cancelar();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	private void cancelar(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(NuevoActivity.this);
      	alertDialog.setMessage("¿Seguro que desea cancelar?, Perderá todos los cambios!");
      	alertDialog.setTitle("Cancelar");
      	alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
      	alertDialog.setCancelable(false);
      	alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
              Intent i = new Intent(NuevoActivity.this, TrabajosActivity.class);
              startActivity(i);
              finish();
            }
        }); 
      	alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
              
            }
        });
        alertDialog.show();
	}

}
