package com.hm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.IOException;

public class Base extends SQLiteOpenHelper {

	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/com.hm/databases/";
	private static String DB_NAME = "homeworks";
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	
	String sqlCreate = "CREATE TABLE trabajo(id INTEGER PRIMARY KEY AUTOINCREMENT" +
			", clase TEXT not null, codigoclase TEXT, profesor TEXT, descripcion TEXT not null, " +
			"fecha TEXT not null, prioridad TEXT, terminado INTEGER)";
	String sqlCreate2 = "CREATE TABLE if not exists tarea(id INTEGER PRIMARY KEY AUTOINCREMENT" +
			", trabajoid INTEGER not null, descripcion TEXT, fecha TEXT not null, terminado INTEGER)";
	String sqlCreate3 = "CREATE TABLE if not exists alumno(id INTEGER PRIMARY KEY AUTOINCREMENT" +
			", nombre TEXT not null, email TEXT)";
	String sqlCreate4 = "CREATE TABLE if not exists trabajoalumno(id INTEGER PRIMARY KEY AUTOINCREMENT" +
			", trabajoid INTEGER not null, alumnoid INTEGER not null)";
	
	public Base(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.myContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		if(db.isReadOnly()){
			db = getWritableDatabase();
		}
		db.execSQL(sqlCreate);
		db.execSQL(sqlCreate2);
		db.execSQL(sqlCreate3);
		db.execSQL(sqlCreate4);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion) {
		
	}
	
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if(!dbExist){
			this.getReadableDatabase();
			/*
			try{
				copyDataBase();
			} catch(IOException e){
				throw new Error("Error al copiar la base de datos!");
			}
			*/
		}
	}
	
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		} catch(SQLiteException e){
			//Base no creada
			Toast.makeText(myContext,"Creando Base de Datos", Toast.LENGTH_LONG).show();
		}
		
		if(checkDB != null){
			checkDB.close();
		}
		return (checkDB != null);
	}
	
	public void openDataBase() throws Exception{
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	public synchronized void close(){
		if(myDataBase != null){
			myDataBase.close();
		}
		super.close();
	}
	
	/*
	private void copyDataBase() throws IOException{
		OutputStream databaseOutputStream = new FileOutputStream("" + DB_PATH+DB_NAME);
		InputStream databaseInputStream;
		
		byte[] buffer = new byte[1024];
		@SuppressWarnings("unused")
		int length;
		
		databaseInputStream = myContext.getAssets().open("BaseHomeworks");
		while((length = databaseInputStream.read(buffer)) > 0){
			databaseOutputStream.write(buffer);
		}
		
		databaseInputStream.close();
		databaseOutputStream.flush();
		databaseOutputStream.close();
	}
	*/
}
