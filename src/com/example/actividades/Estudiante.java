package com.example.actividades;

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
	Button startV,sendD;
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
		
		Intent e= this.getIntent();
		N.setText(e.getStringExtra("name"));
		//Log.v("mensaje","a"+e.getStringExtra("name"));
		
		D.setText(e.getStringExtra("desc"));
		
		I.setImageBitmap(BitmapFactory.decodeFile(e.getStringExtra("img")));
		path = Uri.parse(e.getStringExtra("vid"));
		
		V.setVideoURI(path);
		V.setMediaController(new MediaController(this));	
		
		
	}
//	public void iniciar(View v){
		//A.prepare();
//		A.start();//		
//	}
	public void senddata(View v){
		Intent send;
		send = new Intent(this,Main.class);
		this.startActivityForResult(send, 0);
	}
}
