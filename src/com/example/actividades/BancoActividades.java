package com.example.actividades;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

public class BancoActividades extends Activity implements OnClickListener{
	SQLiteDatabase db;
	//static String nameDB="apli";
	//String DATABASE_PATH = "mnt/extsd/dataapp";
	//static String nameDB="apli";
	private static final String crearAct="create table if not exists Actividad(id_actividad integer primary key autoincrement," +
			"nameAct text," +
			"descAct text," +
			"pathI text," +
			"pathV text," +
			"pathA text)";

	ScrollView general;
	LinearLayout capa1;
	TextView box;
	Button []b = new Button[10];
	ArrayList<String> nombres=new ArrayList<String>();
	ArrayList<String> descripcion=new ArrayList<String>();
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
		
		box = new TextView(this);		
		capa1.addView(box);
		
		Intent bAct = this.getIntent();
		
		abrirBasedatos();
		consulta();		
		general.addView(capa1);
		setContentView(general);
	}
	
	private void abrirBasedatos(){   
	    try
	    {	
	      db = openOrCreateDatabase("/mnt/extsd/dataapp/"+"DB", MODE_PRIVATE, null);
	      db.execSQL(crearAct);
	      Log.i("base", "Si" );	       
	    }    
	    catch (Exception e)
	    {   
	      Log.i("base", "No" + e);   
	    }   
	 }  

	public void consulta(){
		
		Cursor consultor = db.rawQuery("select * from Actividad", null);			
		int conta = 0;		
		while(consultor.moveToNext() == true){
			nombres.add(consultor.getString(1));
			descripcion.add(consultor.getString(2));
			pathI.add(consultor.getString(3));
			pathV.add(consultor.getString(4));
			pathA.add(consultor.getString(5));
			b[conta] = new Button(this);			
			b[conta].setText(consultor.getString(1));
			b[conta].setId(conta);
			b[conta].setOnClickListener(this);
			//b[conta].setBackgroundColor(getResources().getColor(R.color.azul));
			b[conta].setTextColor(getResources().getColor(R.color.azul));			
			capa1.addView(b[conta]);
			conta=conta+1;			
		}
		db.close();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		saltar(v.getId());		
	}
	
	public void saltar(int id){		
		Intent act;
		act = new Intent(this,Estudiante.class);		
		act.putExtra("name",nombres.get(id));		
		act.putExtra("desc",descripcion.get(id));
		act.putExtra("img",pathI.get(id));		
		act.putExtra("vid",pathV.get(id));
		act.putExtra("aud",pathA.get(id));
		this.startActivityForResult(act, 0);
	}
}
