package com.example.actividades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaActividad extends Activity{
	
	SQLiteDatabase db;
	static String nameDB="apli";
	String path="mnt/extsd/dataapp/";
	
	EditText nombre, descripcion;	
	TextView addimg,addvid,addaud;	
	Button adjimg,adjvid,adjaud,ag;	
		
	boolean flagi=false, flagv=false,flaga=false;	
	String oimg="",ovid="",oaud="";
	String dimg="",dvid="",daud="";
	
	private static final String crearAct="create table if not exists Actividad(id_actividad integer primary key autoincrement," +
			"nameAct text," +
			"descAct text," +
			"pathI text," +
			"pathV text," +
			"pathA text)";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stubt
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevaactividad);
		
		nombre = (EditText) this.findViewById(R.id.nameAct);
		descripcion = (EditText) this.findViewById(R.id.descAct);
		
		addimg = (TextView) this.findViewById(R.id.texImagen);
		addvid = (TextView) this.findViewById(R.id.texVideo);
		addaud = (TextView) this.findViewById(R.id.texAudio);
		
		adjimg = (Button) this.findViewById(R.id.bImagen);
		adjvid = (Button) this.findViewById(R.id.bVideo);
		adjaud = (Button) this.findViewById(R.id.bAudio);
		
		ag = (Button) this.findViewById(R.id.Agregar);		
		Intent newA = this.getIntent();
		path = "mnt/extsd/dataapp";
		//path = Environment.getExternalStorageState();
	}

	private void abrirBasedatos() 

	  {   
	    try 
	    {   
	      db = openOrCreateDatabase(nameDB, MODE_WORLD_WRITEABLE, null);   
	      db.execSQL(crearAct);	       
	    }    
	    catch (Exception e)
	    {   
	      Log.i("base", "No" + e);   
	    }   
	  }  
	
	private boolean insertarActividad(String n, String d, String pI, String pV, String pA) 
	  {   
	    ContentValues values = new ContentValues();   
	    values.put("nameAct",n );   
	    values.put("descAct", d);
	    values.put("pathI", pI);
	    values.put("pathV", pV);
	    values.put("pathA", pA);
	    
	    Toast.makeText(getApplicationContext(), "Nombre: " + n + ", " +
	    		"descr: " + d, Toast.LENGTH_LONG).show();
	    return (db.insert("Actividad", null, values) > 0);   
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
		String d = descripcion.getEditableText().toString();		
		String img = dimg;
		String vid = dvid;
		String aud = daud;

		abrirBasedatos();
		
		 boolean resultado = insertarActividad(n,d,img,vid,aud);
			        if(resultado) 
			          Toast.makeText(getApplicationContext(), 
			        		  "Actividad Añadida Correctamente", Toast.LENGTH_LONG).show();
			        else 
			          Toast.makeText(getApplicationContext(), 
			        		  "No se ha podido añadir Actividad" ,   Toast.LENGTH_LONG).show();             		
		
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
					oimg = imageuri.getPath();
					dimg = "mnt/extsd/dataapp/img/"+nombre.getText().toString()+".jpg";				
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
					ovid = videouri.getPath();
					dvid = "mnt/extsd/dataapp/vid/"+nombre.getText().toString()+".mp4";				
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
					oaud = audiouri.getPath();
					daud = "mnt/extsd/dataapp/audio/"+nombre.getText().toString()+".mp3";				
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
