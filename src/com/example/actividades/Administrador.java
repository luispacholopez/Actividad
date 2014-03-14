package com.example.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Administrador extends Activity{
	
	Button insert,update,delete;
	String ruta="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administrador);
		Intent adm = this.getIntent();
		ruta=adm.getStringExtra("ruta");
		insert = (Button) this.findViewById(R.id.btnCrear);
		update = (Button) this.findViewById(R.id.btnModificar);
		delete = (Button) this.findViewById(R.id.btnEliminar);
		Toast.makeText(getApplicationContext(),""+ruta, Toast.LENGTH_LONG).show();
	}
	
	public void crear (View v){
		Intent intCrear;
		intCrear = new Intent(this,NuevaActividad.class);
		intCrear.putExtra("ruta", ruta);
    	this.startActivityForResult(intCrear, 0);
	}
	
	public void modificar (View v){
		Intent intModificar;
		intModificar = new Intent(this,ModificarActividad.class);	
		intModificar.putExtra("ruta", ruta);
    	this.startActivityForResult(intModificar, 0);
	}
	
	public void eliminar (View v){
		Intent intEliminar;
		intEliminar = new Intent(this,EliminarActividad.class);
		intEliminar.putExtra("ruta", ruta);
    	this.startActivityForResult(intEliminar, 0);		
	}

}
