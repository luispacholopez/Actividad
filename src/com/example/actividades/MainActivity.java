package com.example.actividades;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button Admin, Estudiante;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);				
	}
	
	public void onAdministrador (View v) {
		Intent logA; 
    	logA = new Intent(this,LoginAdmin.class);    	
    	this.startActivityForResult(logA, 0);
	}
	
	public void onEstudiante (View v){
		Intent BancoAct; 
		BancoAct = new Intent(this,BancoActividades.class);    	
    	this.startActivityForResult(BancoAct, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String x = "si";
	}

}
