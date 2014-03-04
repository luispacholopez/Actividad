package com.example.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Administrador extends Activity{
	
	Button insert,update,delete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administrador);
		
		insert = (Button) this.findViewById(R.id.btnCrear);
		update = (Button) this.findViewById(R.id.btnModificar);
		delete = (Button) this.findViewById(R.id.btnEliminar);
	}
	
	public void crear (View v){
		Intent intCrear;
		intCrear = new Intent(this,NuevaActividad.class);		    	
    	this.startActivityForResult(intCrear, 0);
	}
	
	public void modificar (View v){
		Intent intModificar;
		intModificar = new Intent(this,ModificarActividad.class);		    	
    	this.startActivityForResult(intModificar, 0);
	}
	
	public void eliminar (View v){
		Intent intEliminar;
		intEliminar = new Intent(this,EliminarActividad.class);		    	
    	this.startActivityForResult(intEliminar, 0);		
	}

}
