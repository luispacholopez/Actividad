package com.example.actividades;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

@SuppressLint("NewApi")
public class Estudiante extends Activity {
	String ruta="";
	TextView N,C,D,E,t;
	ImageView I;
	VideoView V;
	MediaPlayer A= new MediaPlayer();
	String apl,destino;
	Button startV,playA,stopA,sendD,installApk;
	Uri path;
	
	String textoComp = "Las competencias que vas a desarrollar con esta actividad son las siguientes: \n";
	String textoEval = "La manera en que se evaluara la actividad será: \n";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estudiante);
		t = (TextView) this.findViewById(R.id.t);
		N = (TextView) this.findViewById(R.id.nombreact);
		C = (TextView) this.findViewById(R.id.comppact);
		D = (TextView) this.findViewById(R.id.descripact);
		E = (TextView) this.findViewById(R.id.evalpact);
		I = (ImageView) this.findViewById(R.id.Imgact);		
		V = (VideoView) this.findViewById(R.id.Vidact);		
		sendD = (Button) this.findViewById(R.id.botonenviar);
		stopA = (Button) this.findViewById(R.id.stop);
		playA = (Button) this.findViewById(R.id.play);
		installApk = (Button) this.findViewById(R.id.apk);
		
		Intent e= this.getIntent();
		ruta=e.getStringExtra("ruta");
		N.setText(e.getStringExtra("name"));		
		C.setText(textoComp+e.getStringExtra("comp"));
		D.setText(e.getStringExtra("desc"));
		E.setText(textoEval+e.getStringExtra("eval"));		
		destino = e.getStringExtra("dest");
		apl = e.getStringExtra("papk");
		
		installApk.setText("Instalar "+apl);		
		I.setImageBitmap(BitmapFactory.decodeFile(ruta+e.getStringExtra("img")));
		
		if ((e.getStringExtra("vid")).isEmpty()){			
			V.setVisibility(View.INVISIBLE);
			//t.setText("vacio");
		}
		else{
			path = Uri.parse(ruta+e.getStringExtra("vid"));
			V.setVideoURI(path);
			V.setMediaController(new MediaController(this));
		}		
			
		if ((e.getStringExtra("aud")).isEmpty()){
			playA.setVisibility(View.INVISIBLE);
			stopA.setVisibility(View.INVISIBLE);			
			playA.setEnabled(false);
			stopA.setEnabled(false);
		}	
		else{
			try {
				playA.setAlpha(100);
				stopA.setAlpha(100);
				playA.setEnabled(true);
				stopA.setEnabled(true);
				A.setDataSource(ruta+e.getStringExtra("aud"));
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void play(View v) throws IllegalStateException, IOException{
		A.prepare();
		A.start();
	}
	
	public void stop(View v){
		A.stop();		
	}	
	
	
	public void install(View v){		
		//t.setText(apl+" uno");
		Intent inte = new Intent(Intent.ACTION_VIEW);
		inte.setDataAndType(Uri.fromFile(new File(ruta+"apps/"+apl)), "application/vnd.android.package-archive");
		inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(inte);
		
	}
	public void senddata(View v){
		Intent send;
		send = new Intent(this,Main.class);
		send.putExtra("destinatario", destino);
		this.startActivityForResult(send, 0);
	}
}
