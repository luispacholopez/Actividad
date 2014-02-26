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

public class Main extends Activity {	
    boolean flagi=false,flagv=false,flaga=false;
    ArrayList<Uri> uris =new ArrayList<Uri>();    
    Uri imageuri,videouri,audiouri;
    EditText etEmail, etSubject, etBody;    
    CheckBox chkAttachment,chkAttachment2,chkAttachment3;
    Button btnSend;
    Button addImagen,addVideo,addAudio;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);            
        
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSubject = (EditText) findViewById(R.id.etSubject);
        etBody = (EditText) findViewById(R.id.etBody);	
        
        chkAttachment = (CheckBox) findViewById(R.id.chkAttachment);
        chkAttachment2 = (CheckBox) findViewById(R.id.chkAttachmentvid);
        chkAttachment3 = (CheckBox) findViewById(R.id.chkAttachmentaud);
        
        btnSend = (Button) findViewById(R.id.btnSend);
        addImagen = (Button) findViewById(R.id.addImagen);
        addVideo = (Button) findViewById(R.id.addVideo);
        addAudio = (Button) findViewById(R.id.addAudio);        
    }
        
	public void metodo(View v) {
    	Intent itSend = new Intent(android.content.Intent.ACTION_SEND);
        itSend.setType("plain/text");
        itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ etEmail.getText().toString()});                            
        itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        itSend.putExtra(android.content.Intent.EXTRA_TEXT, etBody.getText());
                
        if (chkAttachment.isChecked()) {        	
        	//itSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageuri.toString()));
        	uris.add(Uri.parse(imageuri.toString()));
        	itSend.setType("image/jpg");                            	
        }                        
        if (chkAttachment2.isChecked()){
        	//itSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(videouri.toString()));
        	uris.add(Uri.parse(videouri.toString()));
        	itSend.setType("video/mp4");
        }
        if (chkAttachment3.isChecked()){
        	//itSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(audiouri.toString()));
        	uris.add(Uri.parse(audiouri.toString()));
        	itSend.setType("audio/mp3");
        }
                /* iniciamos la actividad */
//        itSend.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
          startActivity(itSend);

	}
    
    public void addImagen (View v){    	
		flagi = true;
		flagv = false;
		flaga = false;		
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("image/*");
	    Intent c = Intent.createChooser(i, "Seleccione Imagen");
	    startActivityForResult(c,1);
	}
	
	public void addVideo (View v){
		flagi = false;
		flagv = true;
		flaga = false;		
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("video/*");
	    Intent c = Intent.createChooser(i, "Seleccione Video");
	    startActivityForResult(c,1);
	}
	
	public void addAudio (View v){
		flagi = false;
		flagv = false;
		flaga = true;		
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("audio/*");
	    Intent c = Intent.createChooser(i, "Seleccione Audio");
	    startActivityForResult(c,1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1){
			if (resultCode == RESULT_OK){
				if (flagi){					
					imageuri = data.getData();
					data.getType();				
				}
				if (flagv){					
					videouri = data.getData();
					data.getType();				
				}
				if (flaga){					
					audiouri = data.getData();
					data.getType();				
				}
			}
		}
			
	}
}
