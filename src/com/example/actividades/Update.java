package com.example.actividades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Update extends Activity{
	String ruta="";
	SQLiteDatabase db;
	String fuente = "dataapp/";
	String DATABASE_PATH = "/mnt/sdcard/"+fuente;	
	String path="/mnt/sdcard/"+fuente;
	
	private static final String crearAct="create table if not exists Actividad(id_actividad integer primary key autoincrement," +
			"nameAct text," +
			"compAct text," +
			"descAct text," +
			"evalAct text," +	
			"destAct text," +
			"pathApk text," +
			"pathI text," +
			"pathV text," +
			"pathA text)";
	
	TextView I,V,A;
	EditText N,C,D,E,Dt;
	Button aI,aV,aA,act;
	boolean flagi=false,flagv=false,flaga=false;
	String oimg="",ovid="",oaud="";
	String dimg="",dvid="",daud="";
	String rimg="",rvid="",raud="";
	
	Spinner apks;
	ArrayList<String> archivos= new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		
		N = (EditText) this.findViewById(R.id.nombreActividad);
		C = (EditText) this.findViewById(R.id.competenciasActividad);
		D = (EditText) this.findViewById(R.id.descripcionActividad);
		E = (EditText) this.findViewById(R.id.evaluacionAct);
		Dt = (EditText) this.findViewById(R.id.destinoAct);
		apks = (Spinner) this.findViewById(R.id.spinner);
		
		I = (TextView) this.findViewById(R.id.texImagen);
		V = (TextView) this.findViewById(R.id.texVideo);
		A = (TextView) this.findViewById(R.id.texAudio);
		
		aI = (Button) this.findViewById(R.id.bImagen);
		aV = (Button) this.findViewById(R.id.bVideo);
		aA = (Button) this.findViewById(R.id.bAudio);
		
		act = (Button) this.findViewById(R.id.Actualizar);
		
		Intent upd = this.getIntent();
		ruta=upd.getStringExtra("ruta");
		
		N.setText(upd.getStringExtra("name"));
		C.setText(upd.getStringExtra("comp"));
		D.setText(upd.getStringExtra("desc"));
		E.setText(upd.getStringExtra("eval"));
		Dt.setText(upd.getStringExtra("dest"));
		
		
		if ((upd.getStringExtra("img")).isEmpty()){
			I.setText("");
			rimg="";
		}
		else{
			I.setText(ruta+upd.getStringExtra("img"));
			rimg=upd.getStringExtra("img");
		}
		
		if ((upd.getStringExtra("vid")).isEmpty()){
			V.setText("");
			rvid="";
		}
		else{
			V.setText(ruta+upd.getStringExtra("vid"));
			rvid=upd.getStringExtra("vid");
		}
		
		if ((upd.getStringExtra("aud")).isEmpty()){
			A.setText("");
			raud="";
		}
		else{
			A.setText(ruta+upd.getStringExtra("aud"));
			raud=upd.getStringExtra("aud");
		}
		
		archivos.add("");
		leerDir();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,archivos);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		apks.setAdapter(adapter);
	}
	
	private void abrirBasedatos() 

	  {   
	    try 
	    {   
	    	db = openOrCreateDatabase(ruta+"DB", MODE_PRIVATE, null);	    	
	    	db.execSQL(crearAct);
	    	Log.i("base", "Si");      
	    }    
	    catch (Exception e)
	    { 
	      Log.i("base", "No" + e);   
	    }   
	  }
	
	public void cargar(String n,String c,String d,String e,String dt,String a,String pI,String pV,String pA){
		ContentValues values = new ContentValues();   
	    values.put("nameAct",n);
	    values.put("compAct",c);
	    values.put("descAct",d);
	    values.put("evalAct",e);
	    values.put("destAct",dt);
	    values.put("pathApk",a);
	    values.put("pathI", pI);
	    values.put("pathV", pV);
	    values.put("pathA", pA);
	    
	    db.update("Actividad", values, "nameAct ="+"'"+N.getText().toString()+"'", null);	    
	    Toast.makeText(getApplicationContext(),"Actividad Actualizada Correctamente", Toast.LENGTH_LONG).show();
        this.finish();
	}
	
	public void leerDir(){		
		File f = new File(ruta+"apps");
		File[] files = f.listFiles();		
		for (int i = 0; i < files.length; i++){
			File file = files[i];			
			if(file.isFile()){
				archivos.add(file.getName());				
			}
		}
	}
	
	public void actualizar (View v){
		String n = N.getEditableText().toString();		
		String c = C.getEditableText().toString();
		String d = D.getEditableText().toString();
		String e = E.getEditableText().toString();
		String dt = Dt.getEditableText().toString();
		String a = apks.getSelectedItem().toString();
		String img = rimg;
		String vid = rvid;
		String aud = raud;

		abrirBasedatos();		
		cargar(n,c,d,e,dt,a,img,vid,aud);
	}
	
	
	public void addimagen (View v){

		flagi = true;
		flagv = false;
		flaga = false;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("image/*");
	    Intent c = Intent.createChooser(i, "Seleccione Imagen");
	    startActivityForResult(c,1);
	}	
		
	public void addvideo (View v){

		flagi = false;
		flagv = true;
		flaga = false;				
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("video/*");
	    Intent c = Intent.createChooser(i, "Seleccione Video");
	    startActivityForResult(c,1);	    
	}
	
	public void addaudio (View v){

		flagi = false;
		flagv = false;
		flaga = true;		
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("audio/*");
	    Intent c = Intent.createChooser(i, "Seleccione Audio");
	    startActivityForResult(c,1);
	}
	
	public String getRealPath(Context context, Uri contenturi){
		Cursor cursor = null;
		try{
			String[] proj = {MediaStore.Images.Media.DATA};
			cursor = context.getContentResolver().query(contenturi, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally{
			if (cursor != null){
				cursor.close();
			}				
		}
		
	}
	
	public String getRealPathv(Context context, Uri contenturi){
		Cursor cursor = null;
		try{
			String[] proj = {MediaStore.Video.Media.DATA};
			cursor = context.getContentResolver().query(contenturi, proj, null, null, null);			
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally{
			if (cursor != null){
				cursor.close();
			}				
		}		
	}
	
	public String getRealPatha(Context context, Uri contenturi){
		Cursor cursor = null;
		try{
			String[] proj = {MediaStore.Audio.Media.DATA};
			cursor = context.getContentResolver().query(contenturi, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally{
			if (cursor != null){
				cursor.close();
			}				
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);	
		
		if (requestCode == 1){
			if (resultCode == RESULT_OK){
				File sourceLocation;
				File targetLocation;
				//Agregar Imagen
				if (flagi){					
					Uri imageuri = data.getData();
					data.getType();
					oimg =getRealPath(this, imageuri);					
					//oimg = imageuri.getPath();
					//dimg = path+"img/"+N.getText().toString()+".jpg";
					rimg = "img/"+N.getText().toString()+".jpg";
					dimg = ruta+rimg;
					//addimg.setText(oimg+dimg);
					try{			
						sourceLocation = new File (oimg);
						targetLocation = new File (dimg);
				        // asegurar que existe el archivo			        
				        if(sourceLocation.exists()){			            
				            InputStream in = new FileInputStream(sourceLocation);
				            OutputStream out = new FileOutputStream(targetLocation);			
				            // Copy the bits from instream to outstream
				            byte[] buf = new byte[1024];
				            int len;			            
				            while ((len = in.read(buf)) > 0) {
				                out.write(buf, 0, len);
				            }			            
				            in.close();
				            out.close();
				            I.setText(dimg);			            
				        }
					} catch (NullPointerException e) {
					    e.printStackTrace();
					} catch (Exception e) {
					    e.printStackTrace();			
					}
				}//end Imagen
				
				//Agregar Video
				if (flagv){					
					Uri videouri = data.getData();
					data.getType();
					ovid =getRealPathv(this, videouri);
					//ovid = videouri.getPath();
//					dvid = path+"vid/"+N.getText().toString()+".mp4";				
					rvid = "vid/"+N.getText().toString()+".mp4";
					dvid = ruta+rvid;
					try{			
						sourceLocation = new File (ovid);
						targetLocation = new File (dvid);
				        // asegurar que existe el archivo			        
				        if(sourceLocation.exists()){			            
				            InputStream in = new FileInputStream(sourceLocation);
				            OutputStream out = new FileOutputStream(targetLocation);			
				            // Copiar de Input a Output
				            byte[] buf = new byte[1024];
				            int len;			            
				            while ((len = in.read(buf)) > 0) {
				                out.write(buf, 0, len);
				            }			            
				            in.close();
				            out.close();
				            V.setText(dvid);			            
				        }
					} catch (NullPointerException e) {
					    e.printStackTrace();
					} catch (Exception e) {
					    e.printStackTrace();			
					}
				}//end Video
				
				//Agregar Audio
				if (flaga){					
					Uri audiouri = data.getData();
					data.getType();
					oaud =getRealPatha(this, audiouri);
					//oaud = audiouri.getPath();
//					daud = path+"audio/"+N.getText().toString()+".mp3";				
					raud ="audio/"+N.getText().toString()+".mp3";
					daud = ruta+raud;
					try{			
						sourceLocation = new File (oaud);
						targetLocation = new File (daud);
				        // asegurar que existe el archivo			        
				        if(sourceLocation.exists()){			            
				            InputStream in = new FileInputStream(sourceLocation);
				            OutputStream out = new FileOutputStream(targetLocation);			
				            // Copiar de Input a Output
				            byte[] buf = new byte[1024];
				            int len;			            
				            while ((len = in.read(buf)) > 0) {
				                out.write(buf, 0, len);
				            }			            
				            in.close();
				            out.close();
				        }
					} catch (NullPointerException e) {
					    e.printStackTrace();
					} catch (Exception e) {
					    e.printStackTrace();			
					}
				}//end Audio
			}
		}

	}
}
