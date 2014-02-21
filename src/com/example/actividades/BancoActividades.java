package com.example.actividades;



import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class BancoActividades extends Activity {
	
	ArrayAdapter adapter;
	ArrayList<String> datos;
	ListView im;
	Cursor c;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bancoactividades);
		im= (ListView) this.findViewById(R.id.lista);
		datos = new ArrayList();
		Intent bAct = this.getIntent();
				
		
		//ContentResolver cr = this.getContentResolver();
//		Uri u=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//		String projection [] = {MediaStore.Images.Media.TITLE,MediaStore.Images.Media.DATA};
//		
//		c = cr.query(u, projection, null, null, null);
		
		 //ideo_player_view.setVideoPath("/sdcard/video.mp4");
		
		adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,datos);
		im.setAdapter(adapter);
		
		
	}
	
}
