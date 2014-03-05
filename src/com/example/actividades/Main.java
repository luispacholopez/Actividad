package com.example.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity {	
    boolean flagi=false,flagv=false,flaga=false;
    ArrayList<Uri> uris =new ArrayList<Uri>();    
    Uri imageuri,videouri,audiouri;
    EditText etEmail, etSubject, etBody;
    TextView adjuntos;
    Button btnSend,btnAdj;    
    Uri path;
    int contadoradd=0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);            
        
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSubject = (EditText) findViewById(R.id.etSubject);
        etBody = (EditText) findViewById(R.id.etBody);
        adjuntos = (TextView) this.findViewById(R.id.agregados);
        
        btnSend = (Button) findViewById(R.id.btnSend);
        btnAdj = (Button) findViewById(R.id.btnAdj);
        Intent e= this.getIntent();
		etEmail.setText(e.getStringExtra("destinatario"));
    }        
	    
    public void adjuntar (View v){
    	contadoradd = contadoradd+1;
    	
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("file/*");
	    Intent c = Intent.createChooser(i, "Seleccione Archivo");
	    startActivityForResult(c,1);
	}
	
    public void enviar(View v){
		Intent itSend = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
        itSend.setType("plain/text");
        itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ etEmail.getText().toString()});                            
        itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        itSend.putExtra(android.content.Intent.EXTRA_TEXT, etBody.getText().toString());
        itSend.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        itSend.setType("file/*");
        startActivity(itSend);                
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1){
			if (resultCode == RESULT_OK){
				adjuntos.setText(contadoradd+" archivo(s) adjunto(s)");
				path = data.getData();
				data.getType();
				uris.add(Uri.parse(path.toString()));
			}
		}			
	}
}
