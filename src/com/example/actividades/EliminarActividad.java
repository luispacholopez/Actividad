package com.example.actividades;

import java.io.File;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class EliminarActividad extends Activity implements OnClickListener{
	
	SQLiteDatabase db;
	String fuente = "dataapp/";
	String DATABASE_PATH = "/mnt/sdcard/"+fuente;	
	String path="/mnt/sdcard/"+fuente;
	
	private static final String crearAct="create table if not exists Actividad(id_actividad integer primary key autoincrement," +
			"nameAct text," +
			"compAct text," +
			"descAct text," +
			"evalAct text," +			
			"pathI text," +
			"pathV text," +
			"pathA text)";
	
	ScrollView general;
	LinearLayout capa1;
	
	Button b;
	ArrayList<Button> botones = new ArrayList<Button>();
	ArrayList<String> nombres=new ArrayList<String>();	
	ArrayList<String> pathI=new ArrayList<String>();
	ArrayList<String> pathV=new ArrayList<String>();
	ArrayList<String> pathA=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		general = new ScrollView(this);
		
		capa1 = new LinearLayout(this);
		capa1.setOrientation(LinearLayout.VERTICAL);
		
		abrirBasedatos();
		botones();
		general.addView(capa1);
		setContentView(general);
	}
	
	private void abrirBasedatos()  {   
	    try 
	    {   
	    	db = openOrCreateDatabase(DATABASE_PATH+"DB", MODE_PRIVATE, null);	    	
	    	db.execSQL(crearAct);
	    }    
	    catch (Exception e)
	    { 
	      Log.i("base", "No" + e);   
	    }   
	 } 
	
	public void botones(){
		
		Cursor consultor = db.rawQuery("select * from Actividad", null);			
		int conta = 0;		
		while(consultor.moveToNext() == true){			
			nombres.add(consultor.getString(1));
			pathI.add(consultor.getString(5));
			pathV.add(consultor.getString(6));
			pathA.add(consultor.getString(7));
			b = new Button(this);
			b.setText(consultor.getString(1));
			b.setId(conta);
			b.setOnClickListener(this);
			b.setTextColor(getResources().getColor(R.color.azul));
			capa1.addView(b);
			botones.add(b);
			conta++;
		}
//		db.close();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		borrar(v.getId());		
	}

	@SuppressLint("NewApi")
	public void borrar(int id){
		
		String nombre = nombres.get(id).toString();
		
		if (!(pathI.get(id).isEmpty())){
			File file = new File(pathI.get(id));
			file.delete();				
		}
		if (!(pathV.get(id).isEmpty())){
			File file = new File(pathV.get(id));
			file.delete();				
		}
		if (!(pathA.get(id).isEmpty())){
			File file = new File(pathA.get(id));
			file.delete();				
		}			
		
		try{
			db.delete("Actividad", "nameAct="+"'"+nombre+"'", null);
			db.close();
			Toast.makeText(getApplicationContext(),"Actividad "+nombre+" Borrada.", Toast.LENGTH_LONG).show();
			this.finish();
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),""+e, Toast.LENGTH_LONG).show();	
		}
	}
	

}
