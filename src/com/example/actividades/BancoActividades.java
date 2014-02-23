package com.example.actividades;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class BancoActividades extends Activity {
	SQLiteDatabase db;
	static String nameDB="apli";
	private static final String crearAct="create table if not exists Actividad(id_actividad integer primary key autoincrement," +
			"nameAct text," +
			"descAct text," +
			"pathI text," +
			"pathV text," +
			"pathA text)";

	TextView num;
	ImageView im;
	VideoView vi;
	LinearLayout c1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bancoactividades);
		c1 = (LinearLayout) this.findViewById(R.id.Capa1);
		num = (TextView) this.findViewById(R.id.numeroreg);
		im = (ImageView) this.findViewById(R.id.im);
		vi = (VideoView) this.findViewById(R.id.vi);
		Intent bAct = this.getIntent();
		abrirBasedatos();
		consulta();
			
		 //ideo_player_view.setVideoPath("/sdcard/video.mp4");
		
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

	private void consulta(){
		Cursor consultor = db.rawQuery("select * from Actividad", null);
		//num.setText(consultor.getCount());
		consultor.moveToFirst();		
		int conta = 1;
		Button []b = new Button[10];
		while(consultor.moveToNext() == true){
			
//			p = p+"\n"+consultor.getString(1)+", "+consultor.getString(2)+", "+consultor.getString(3)+", ";
//			num.setText(p);
			
			b[conta] = new Button(this);
			b[conta].setText(consultor.getString(1));
			conta=conta+1;
			//b[conta].s
			
		}
		for (int i=1;i< conta; i++) {
			c1.addView(b[i]);	
		}
		
		
		db.close();
	}
	
}
