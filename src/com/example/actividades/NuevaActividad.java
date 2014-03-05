package com.example.actividades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaActividad extends Activity{
	
	SQLiteDatabase db;
	String fuente = "dataapp/";
	String DATABASE_PATH = "/mnt/sdcard/"+fuente;	
	String path="/mnt/sdcard/"+fuente;

	EditText nombre, competencias, descripcion, evaluacion, destinatario;	
	TextView addimg,addvid,addaud;	
	Button adjimg,adjvid,adjaud,ag;	
	Spinner apks;
		
	boolean flagi=false, flagv=false,flaga=false;	
	String oimg="",ovid="",oaud="";
	String dimg="",dvid="",daud="";
	
	ArrayList<String> archivos= new ArrayList<String>();
	ArrayAdapter<String> adapter;	
		
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stubt
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevaactividad);
		
		nombre = (EditText) this.findViewById(R.id.nameAct);
		competencias = (EditText) this.findViewById(R.id.compAct);
		descripcion = (EditText) this.findViewById(R.id.descAct);
		evaluacion= (EditText) this.findViewById(R.id.evalAct);
		destinatario= (EditText) this.findViewById(R.id.destino);
		
		addimg = (TextView) this.findViewById(R.id.texImagen);
		addvid = (TextView) this.findViewById(R.id.texVideo);
		addaud = (TextView) this.findViewById(R.id.texAudio);
		
		adjimg = (Button) this.findViewById(R.id.bImagen);
		adjvid = (Button) this.findViewById(R.id.bVideo);
		adjaud = (Button) this.findViewById(R.id.bAudio);
		
		apks = (Spinner) this.findViewById(R.id.spinner);
		
		ag = (Button) this.findViewById(R.id.Agregar);		
		Intent newA = this.getIntent();
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
	    	db = openOrCreateDatabase(DATABASE_PATH+"DB", MODE_PRIVATE, null);	    	
	    	db.execSQL(crearAct);
	    	Log.i("base", "Si");      
	    }    
	    catch (Exception e)
	    { 
	      Log.i("base", "No" + e);   
	    }   
	  }  
	
	public boolean insertarActividad(String n, String c, String d, String e, String dest, String apk,String pI, String pV, String pA) 
	  {   
	    ContentValues values = new ContentValues();   
	    values.put("nameAct",n);
	    values.put("compAct",c);
	    values.put("descAct",d);
	    values.put("evalAct",e);
	    values.put("destAct",dest);
	    values.put("pathApk",apk);
	    values.put("pathI", pI);
	    values.put("pathV", pV);
	    values.put("pathA", pA);	    
	    return (db.insert("Actividad", null, values) > 0);   
	  }	

	public void leerDir(){		
		File f = new File(path+"apps");
		File[] files = f.listFiles();		
		for (int i = 0; i < files.length; i++){
			File file = files[i];			
			if(file.isFile()){
				archivos.add(file.getName());				
			}
		}
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
			
	public void agregar (View v){
		String n = nombre.getEditableText().toString();		
		String c = competencias.getEditableText().toString();
		String d = descripcion.getEditableText().toString();
		String e = evaluacion.getEditableText().toString();
		String des = destinatario.getEditableText().toString();
		String apk = apks.getSelectedItem().toString();
		String img = dimg;
		String vid = dvid;
		String aud = daud;

		abrirBasedatos();
		
		boolean resultado = insertarActividad(n,c,d,e,des,apk,img,vid,aud);
	        if(resultado) {
	          Toast.makeText(getApplicationContext(),"Actividad Añadida Correctamente", Toast.LENGTH_LONG).show();
	          this.finish();
	        }
	        else {
	          Toast.makeText(getApplicationContext(),"No se ha podido añadir Actividad" + resultado, Toast.LENGTH_LONG).show();
	        }
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
					dimg = path+"img/"+nombre.getText().toString()+".jpg";				
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
				            addimg.setText(dimg);			            
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
					dvid = path+"vid/"+nombre.getText().toString()+".mp4";				
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
				            addvid.setText(dvid);			            
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
					daud = path+"audio/"+nombre.getText().toString()+".mp3";				
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
				            addaud.setText(daud);			            
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
