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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

public class BancoActividades extends Activity implements OnClickListener{
	SQLiteDatabase db;
	static String nameDB="apli";
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
	//Adapter ad1,ad2,ad3,ad4,ad5;
	ArrayList<String> nombres=new ArrayList();
	ArrayList<String> descripcion=new ArrayList();
	ArrayList<String> pathI=new ArrayList();
	ArrayList<String> pathV=new ArrayList();
	ArrayList<String> pathA=new ArrayList();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		general = new ScrollView(this);
		
		capa1 = new LinearLayout(this);
		capa1.setOrientation(LinearLayout.VERTICAL);
		
		TextView box = new TextView(this);		
		capa1.addView(box);
		Intent bAct = this.getIntent();
		
		abrirBasedatos();
		consulta();		
		general.addView(capa1);
		setContentView(general);	
		 //video_player_view.setVideoPath("/sdcard/video.mp4");
		
	}
	
	private void abrirBasedatos() 

	  {   
	    try 
	    {   
	      db = openOrCreateDatabase(nameDB, MODE_WORLD_WRITEABLE, null);   
	      db.execSQL(crearAct);Log.i("base", "Si" );	       
	    }    
	    catch (Exception e)
	    {   
	      Log.i("base", "No" + e);   
	    }   
	  }  

	public void consulta(){
		Cursor consultor = db.rawQuery("select * from Actividad", null);

		consultor.moveToFirst();		
		int conta = 0;
		Button []b = new Button[10];
		while(consultor.moveToNext() == true || consultor.isLast() == true){
			nombres.add(consultor.getString(0));
			descripcion.add(consultor.getString(1));
			pathI.add(consultor.getString(2));
			pathV.add(consultor.getString(3));
			pathA.add(consultor.getString(4));			
			conta=conta+1;			
			b[conta] = new Button(this);			
			b[conta].setText(consultor.getString(1));
			b[conta].setId(conta);			
		}
		for (int i=1;i <= conta; i++) {			
			b[i].setOnClickListener(this);
			capa1.addView(b[i]);			
		}		
		
		db.close();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
			
		case 1 : box.setText("uno");
		 	     break;
		case 2 : box.setText("dos");
		 		 break;
		default: box.setText("defa");
				break;			
		}
	}
	 
		
	
	
}
