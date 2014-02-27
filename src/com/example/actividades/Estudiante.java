package com.example.actividades;

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
import android.widget.VideoView;

@SuppressLint("NewApi")
public class Estudiante extends Activity {
	
	TextView N,D;
	ImageView I;
	VideoView V;
	MediaPlayer A= new MediaPlayer();
	Button startV,playA,stopA,sendD;
	Uri path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estudiante);
		
		N = (TextView) this.findViewById(R.id.nombreact);
		D = (TextView) this.findViewById(R.id.descripact);		
		I = (ImageView) this.findViewById(R.id.Imgact);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		I.setMaxWidth(size.x);
		I.setMaxHeight(size.y);
		
		V = (VideoView) this.findViewById(R.id.Vidact);		
		sendD = (Button) this.findViewById(R.id.botonenviar);
		stopA = (Button) this.findViewById(R.id.stop);
		playA = (Button) this.findViewById(R.id.play);
		
		Intent e= this.getIntent();
		N.setText(e.getStringExtra("name"));
		//Log.v("mensaje","a"+e.getStringExtra("name"));
		
		D.setText(e.getStringExtra("desc"));
		
		if (e.getStringExtra("img") != null){			
			I.setImageBitmap(BitmapFactory.decodeFile(e.getStringExtra("img")));
		}
		
		if (e.getStringExtra("vid") != null){
			path = Uri.parse(e.getStringExtra("vid"));
			V.setVideoURI(path);
			V.setMediaController(new MediaController(this));
		}
			
		if (e.getStringExtra("aud") != null){			
			try {
				A.setDataSource(e.getStringExtra("aud"));
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
	
	public void senddata(View v){
		Intent send;
		send = new Intent(this,Main.class);
		this.startActivityForResult(send, 0);
	}
}
