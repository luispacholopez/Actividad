package com.example.actividades;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

@SuppressLint("NewApi")
public class BancoActividades extends Activity implements OnClickListener{
	SQLiteDatabase db;
	//static String nameDB="apli";
	//String DATABASE_PATH = "mnt/extsd/dataapp";
	//static String nameDB="apli";
	private static final String crearAct="create table if not exists Actividad(id_actividad integer primary key autoincrement," +
			"nameAct text," +
			"compAct text," +
			"descAct text," +
			"evalAct text," +	
			"destAct text," +
			"pathApk text," +
			"pathI text," +
			"pathV text," +
			"pathA text)";

	ScrollView general;
	LinearLayout capa1;
	TextView box,t;	
	Button b;
	ArrayList<Button> botones = new ArrayList<Button>();
	
	ArrayList<String> nombres=new ArrayList<String>();
	ArrayList<String> competencias=new ArrayList<String>();
	ArrayList<String> descripcion=new ArrayList<String>();
	ArrayList<String> evaluacion=new ArrayList<String>();
	ArrayList<String> destinatario=new ArrayList<String>();
	ArrayList<String> pathApk=new ArrayList<String>();
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
	      db = openOrCreateDatabase("/mnt/sdcard/dataapp/"+"DB", MODE_WORLD_WRITEABLE, null);	      
	      if (db == null){
	    	  Toast.makeText(getApplicationContext(),"Error Lectura", Toast.LENGTH_LONG).show();
	      }
	      else{
	    	  db.execSQL(crearAct);
//		      Log.i("base", "Si" );  
	      }
	    }    
	    catch (Exception e)
	    {   
	      Log.i("base", "No" + e);
	      Toast.makeText(getApplicationContext(),"base"+e, Toast.LENGTH_LONG).show();
	    }   
	 }  

	public void consulta(){
		
		Cursor consultor = db.rawQuery("select * from Actividad", null);			
		int conta = 0;		
		while(consultor.moveToNext() == true){
			nombres.add(consultor.getString(1));
			competencias.add(consultor.getString(2));
			descripcion.add(consultor.getString(3));
			evaluacion.add(consultor.getString(4));
			destinatario.add(consultor.getString(5));
			pathApk.add(consultor.getString(6));
			pathI.add(consultor.getString(7));
			pathV.add(consultor.getString(8));
			pathA.add(consultor.getString(9));
			b = new Button(this);
			b.setText(consultor.getString(1));
			b.setId(conta);
			b.setOnClickListener(this);
			b.setTextColor(getResources().getColor(R.color.gris));
			b.setBackground(getResources().getDrawable(R.drawable.botones));
			//b.setGravity(Gravity.CENTER);			
			//b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			t = new TextView(this);
			capa1.addView(t);
			capa1.addView(b);
			botones.add(b);			
			conta++;			
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
		act.putExtra("comp",competencias.get(id));
		act.putExtra("desc",descripcion.get(id));
		act.putExtra("eval",evaluacion.get(id));
		act.putExtra("dest",destinatario.get(id));
		act.putExtra("papk",pathApk.get(id));
		act.putExtra("img",pathI.get(id));
		act.putExtra("vid",pathV.get(id));
		act.putExtra("aud",pathA.get(id));
		this.startActivityForResult(act, 0);
	}
}
