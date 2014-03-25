package com.hm;

import java.util.Calendar;

import com.hm.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoActivity extends Activity implements OnClickListener {

	EditText clase, codigo, profesor, descripcion;
	RatingBar prioridad;
	
	//variables para interfaz de fecha
	private TextView mDateDisplay;    
    private Button mPickDate;    
    private int mYear;    
    private int mMonth;    
    private int mDay;    
    static final int DATE_DIALOG_ID = 0;
    
    // the callback received when the user "sets" the date in the dialog    
    private DatePickerDialog.OnDateSetListener mDateSetListener =            
    	new DatePickerDialog.OnDateSetListener() {                
    	public void onDateSet(DatePicker view, int year,                                       
    			int monthOfYear, int dayOfMonth) {                    
    		mYear = year;                    
    		mMonth = monthOfYear;                    
    		mDay = dayOfMonth;                    
    		updateDisplay();                
    		}            
    	};
	
	
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
	    prioridad = (RatingBar) findViewById(R.id.prioridad);

	    mDateDisplay = (TextView) findViewById(R.id.dateDisplay);        
    	mPickDate = (Button) findViewById(R.id.pickDate);        
    	// add a click listener to the button        
    	mPickDate.setOnClickListener(this);
    	// get the current date        
    	final Calendar c = Calendar.getInstance();        
    	mYear = c.get(Calendar.YEAR);        
    	mMonth = c.get(Calendar.MONTH);        
    	mDay = c.get(Calendar.DAY_OF_MONTH);        
    	// display the current date (this method is below)        
    	updateDisplay();
        
		View boton = findViewById(R.id.aceptarNuevo);
        boton.setOnClickListener(this);
        View boton2 = findViewById(R.id.cancelarNuevo);
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
		if(vista.getId()==findViewById(R.id.aceptarNuevo).getId()){
			if(clase.getText().toString().length() == 0 || 
					codigo.getText().toString().length() == 0 ||
					profesor.getText().toString().length() == 0 ||
					descripcion.getText().toString().length() == 0 ||
					mDateDisplay.getText().toString().length() == 0 ||
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
						mDateDisplay.getText().toString(), prioridad.getRating());
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
		}else{
			if(vista.getId()==findViewById(R.id.pickDate).getId()){
				showDialog(DATE_DIALOG_ID);
			}else{
				cancelar();
			}
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
			String descripcion, String fecha, Float prioridad){   
	    ContentValues values = new ContentValues(); 
	    values.putNull("id");
	    values.put("clase",clase );
	    values.put("codigoclase",codigo );
	    values.put("profesor", profesor);
	    values.put("descripcion",descripcion );
	    values.put("fecha",fecha );
	    values.put("prioridad",prioridad );
	    values.put("terminado", 0);
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
	
	private void updateDisplay() {        
    	mDateDisplay.setText(
    			new StringBuilder()
    			// Month is 0 based so add 1
    			.append(mDay).append("/")
    			.append(mMonth + 1).append("/")                    
    			.append(mYear).append(" "));
    }
    
    
    @Override
    protected Dialog onCreateDialog(int id) {    
    	switch (id) {   
    		case DATE_DIALOG_ID:        
    			return new DatePickerDialog(this,                    
    					mDateSetListener,                    
    					mYear, mMonth, mDay);    
    	}    
    	return null;
    	}

}
