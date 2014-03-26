package com.hm;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.R;


public class SensorActivity extends Activity implements SensorEventListener{
	private SensorManager sensorManager = null;
	private Sensor sensorDeProximidad = null;
	private Sensor sensorAcelerometro = null;
	private Sensor sensorDeOrientacion = null;
	private int movimiento = 0;
	private long last_update = 0, last_movement = 0;
	private float prevX = 0, prevY = 0, prevZ = 0;
	private float curX = 0, curY = 0, curZ = 0;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensoractivity);
	    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    sensorDeProximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
	    sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    sensorDeOrientacion = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	    
	    if(sensorAcelerometro != null)
	       	sensorManager.registerListener(this, sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL);
	    if(sensorDeProximidad != null)
	       	sensorManager.registerListener(this, sensorDeProximidad, SensorManager.SENSOR_DELAY_NORMAL);
	    if(sensorDeOrientacion == null)
	       	sensorManager.registerListener(this, sensorDeOrientacion, SensorManager.SENSOR_DELAY_NORMAL);

	    	    
	    
	    View boton = findViewById(R.id.atras_sensor);
        boton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View vista) {
				// TODO Auto-generated method stub
				if(vista.getId()==findViewById(R.id.atras_sensor).getId()){
					Intent i = new Intent(SensorActivity.this,HMActivity.class);
					startActivity(i);
					finish();
				}
			}
        	
        });
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		synchronized (this){
			long current_time = arg0.timestamp;
	         
	        curX = arg0.values[0];
	        curY = arg0.values[1];
	        curZ = arg0.values[2];
	         
	        if (prevX == 0 && prevY == 0 && prevZ == 0) {
	            last_update = current_time;
	            last_movement = current_time;
	            prevX = curX;
	            prevY = curY;
	            prevZ = curZ;
	        }
	 
	        long time_difference = current_time - last_update;
	        if (time_difference > 0) {
	            float movement = Math.abs((curX + curY + curZ) - (prevX - prevY - prevZ)) / time_difference;
	            int limit = 3000;
	            float min_movement = (float) 0.00000013886;
	            if (movement > min_movement) {
	                if (current_time - last_movement >= limit) {
	                	movimiento++;
	                	if(movimiento > 2){
	                		Toast.makeText(getApplicationContext(), "Alarma Apagada" , Toast.LENGTH_SHORT).show();
	                		movimiento = 0;
	                	}
	                }
	                last_movement = current_time;
	            }
	            prevX = curX;
	            prevY = curY;
	            prevZ = curZ;
	            last_update = current_time;
	        }
	         
	         
	        ((TextView) findViewById(R.id.etiqSensorDeMovimiento)).setText("Acelerómetro X: " + curX);
	        ((TextView) findViewById(R.id.etiqSensorDeOrientacion)).setText("Acelerómetro Y: " + curY);
	        ((TextView) findViewById(R.id.txtAccZ)).setText("Acelerómetro Z: " + curZ);
		}
	        
	        /*
			float[] masData;
			float x;
			float y;
			float z;
			// TODO Auto-generated method stub
			switch(arg0.sensor.getType()){
				case Sensor.TYPE_PROXIMITY:
					masData = arg0.values;
					if(masData[0]==0){
						textViewAcelerometro.setTextSize(textViewAcelerometro.getTextSize()+10);
					}
					else{
						textViewAcelerometro.setTextSize(textViewAcelerometro.getTextSize()-10);
					}
					break;
	                        case Sensor.TYPE_ACCELEROMETER:
	                         	masData = arg0.values;
			                x = masData[0];
					y = masData[1];
	                                z = masData[2];
	                                textViewAcelerometro.setText("x: " + x + "\ny: "+y + "\nz: "+z);
					break;
					
	            case Sensor.TYPE_ORIENTATION:
					masData = arg0.values;
					x = masData[0];
					y = masData[1];
					textViewOrientacion.setText("x: " + x + "\ny: "+y);
					if(x < -4 && !movimiento){
						movimiento = true;
					}else{
						if(x > 2 && movimiento){
							movimiento = false;
							Toast.makeText(getApplicationContext(), "Se detecto movimiento", Toast.LENGTH_SHORT).show();
						}
					}
					break;
				default:
					break;
				
			}
		}
		*/
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);
	}
 
	@Override
	protected void onPause() {
		sensorManager.unregisterListener(this);
		super.onStop();
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