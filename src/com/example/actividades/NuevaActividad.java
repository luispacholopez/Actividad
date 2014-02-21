package com.example.actividades;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevaActividad extends Activity{
	SQLiteDatabase db;
	EditText nombre, descripcion;
	Button ag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevaactividad);
		nombre = (EditText) this.findViewById(R.id.nameAct);
		descripcion = (EditText) this.findViewById(R.id.descAct);
		ag = (Button) this.findViewById(R.id.Agregar);
		Intent newA = this.getIntent();
		
		db = this.openOrCreateDatabase("based", MODE_PRIVATE, null);
		String crearAct="create table if not exists Actividad(id_actividad integer primary key autoincrement," +
				"nameAct text," +
				"descAct text," +
				"pathI text," +
				"pathS text," +
				"pathV text)";		
		db.execSQL(crearAct);
		
		//db.execSQL("insert into Usuarios(usuario,password) values ('admin','admin')");		
		//Cursor consulta = db.rawQuery("select * from Usuarios", null);
	}
	
	public void agregar (View v){
		String n = nombre.getEditableText().toString();
		String d = descripcion.getEditableText().toString();
		db.execSQL("insert into Actividad(nameAct,descAct) values ("+n+","+d+")");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
