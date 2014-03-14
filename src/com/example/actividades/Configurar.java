package com.example.actividades;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Configurar extends Activity{
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configurar);		
		b = (Button) this.findViewById(R.id.bconf);
		
	}
	
	public void configurar(View v){
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("text/*");
	    Intent c = Intent.createChooser(i, "Seleccione Archivo de Configuración");
	    startActivityForResult(c,1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1){
			if (resultCode == RESULT_OK){
				Uri config = data.getData();
				data.getType();
				String x = config.getPath();
				int q = x.length()-10;//config.txt
				String w = x.substring(0, q);
//				Toast.makeText(getApplicationContext(),"Configuración Correcta\n"+w, Toast.LENGTH_LONG).show();
				Intent e = new Intent();
				e.putExtra("ruta", w);
				this.setResult(0,e);
		        this.finish();
			}
		}
	}
	
	
}
