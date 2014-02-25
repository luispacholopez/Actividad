package com.example.actividades;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdmin extends Activity {
	EditText user,pass;
	Button acceso;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginadmin);
		Intent logA = this.getIntent();
		user = (EditText) this.findViewById(R.id.eUsuario);
		user.setText("");
		pass = (EditText) this.findViewById(R.id.ePassword);
		db = this.openOrCreateDatabase("based", MODE_PRIVATE, null);
		String crearUsuario="create table if not exists Usuarios(id_usuario integer primary key autoincrement," +
				"usuario text," +
				"password text)";
		db.execSQL(crearUsuario);
		db.execSQL("insert into Usuarios(usuario,password) values ('admin','admin')");		
		Cursor consulta = db.rawQuery("select * from Usuarios", null);
		db.close();
		
		
	}
	public void acceso (View v){		
		
		if (user.getEditableText().toString().contentEquals("admin")) {
			
			if (pass.getEditableText().toString().contentEquals("admin")){
				Toast aviso;
		    	aviso = Toast.makeText(this,"Bienvenido",1000);			    	
		    	aviso.show();
				Intent newA; 
		    	newA = new Intent(this,NuevaActividad.class);    	
		    	this.startActivityForResult(newA, 0);
			}			
			else {
				Toast aviso;
		    	aviso = Toast.makeText(this,"Contraseña Erronea",1000);			    	
		    	aviso.show();
			}
					
    	}	

	}	
	
}





