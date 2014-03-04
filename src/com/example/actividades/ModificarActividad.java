package com.example.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ModificarActividad extends Activity implements OnClickListener{
	
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
	ArrayList<String> competencias=new ArrayList<String>();
	ArrayList<String> descripcion=new ArrayList<String>();
	ArrayList<String> evaluacion=new ArrayList<String>();
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
	private void abrirBasedatos()
	  {   
	    try 
	    {   
	    	db = openOrCreateDatabase(DATABASE_PATH+"DB", MODE_PRIVATE, null);	    	
	    	db.execSQL(crearAct);
	    	Log.i("base", "Si");      
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
			competencias.add(consultor.getString(2));
			descripcion.add(consultor.getString(3));
			evaluacion.add(consultor.getString(4));
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
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		
		Intent upd;
		upd = new Intent(this,Update.class);
		upd.putExtra("name", nombres.get(id));
		upd.putExtra("comp",competencias.get(id));
		upd.putExtra("desc",descripcion.get(id));
		upd.putExtra("eval",evaluacion.get(id));
		upd.putExtra("img",pathI.get(id));
		upd.putExtra("vid",pathV.get(id));
		upd.putExtra("aud",pathA.get(id));
		this.startActivityForResult(upd, 0);
	}
}