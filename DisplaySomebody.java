package com.ex.somebody;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DisplaySomebody extends Activity{
	private SharedPreferences sp;
	private String delName;
	private int delNameId;
	private DBHelper helper;
	/*
	 * ��Ҫ������ʾĳ���˵���Ϣ
	 * 
	 * */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("data", 0);
		if(sp.getBoolean("isLogined", false)){
			init();
			//Log.i("login",String.valueOf(sp.getBoolean("isLogined", false)));
		}
		else{
			finish();
		}
	}
	
	public void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.display_somebody);
		
		//TextView test=(TextView)findViewById(R.id.test);
	    Intent intent=getIntent();
	    
	    //test.setText(intent.getStringExtra("name"));
	    //��ѯname��ֵ,��ʾ��һ�����������
	    
	    //String personName=intent.getStringExtra("name").toString().trim();
	    
	    int personId=Integer.valueOf(intent.getIntExtra("personId",-1));
	    if(personId==-1) {
	    	Toast.makeText(this, "��ԱID����", Toast.LENGTH_SHORT).show();
	    }
	    else
	    {
	    //Log.i("id",String.valueOf(personId)+personName);
	    //DBHelper helper=new DBHelper(this);
	    //Somebody person=helper.query(personName,personId);
	    //test.setText(person.getName().toString());
	    //Displayer player=new Displayer();
	    //player.Somebody(person);
	    }
	    
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.display, menu);
		
		return true;
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		if(item.getTitle().toString().trim().equals("�˳�")) {
			
			sp.edit().putBoolean("isLogined", false).commit();
			finish();
			
			}
		else if(item.getTitle().toString().trim().equals("���")) {
			//Log.i("isLogined",String.valueOf(sp.getBoolean("isLogined", false)));
			if(sp.getBoolean("isLogined", false)){
				//Toast.makeText(MainActivity.this, String.valueOf(sp.getBoolean("isLogined",false)), Toast.LENGTH_SHORT).show();
			Intent intent=new Intent();
			intent.setClass(DisplaySomebody.this,NewSomebody.class);
			startActivity(intent);
			}
			}
		else if(item.getTitle().toString().trim().equals("ɾ��")) {
			Intent intent=getIntent();
			delName=intent.getStringExtra("name").toString().trim();
		    delNameId=Integer.valueOf(intent.getIntExtra("personId",-1));
		    if(delNameId==-1) {
		    	Toast.makeText(this, "��ԱID����", Toast.LENGTH_SHORT).show();
		    }
		    else
		    {
		    new AlertDialog.Builder(DisplaySomebody.this).setTitle("ϵͳ��ʾ").setMessage("ȷ��ɾ��"+delName+"������Ϣ��").setNegativeButton("ȡ��", null).setPositiveButton("ȷ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO �Զ����ɵķ������
					helper=new DBHelper(DisplaySomebody.this);
				   int n=helper.delSomebody(delNameId,delName);	
				   //Log.i("i",String.valueOf(n));
				   if(n>0) {
					   Toast.makeText(DisplaySomebody.this, delName+"��Ϣ�ɹ�ɾ��", Toast.LENGTH_SHORT).show();
					   finish();
					   Intent intent=new Intent();
					   intent.setClass(DisplaySomebody.this, MainActivity.class);
					   startActivity(intent);
					   
					   }
				
				}}).create().show();
		    
		    }
		}
		else if(item.getTitle().toString().trim().equals("�༭")) {
			Intent intent=getIntent();
			String personName=intent.getStringExtra("name").toString().trim();
		    int personId=Integer.valueOf(intent.getIntExtra("personId",-1));
		    if(personId==-1) {
		    	Toast.makeText(this, "��ԱID����", Toast.LENGTH_SHORT).show();
		    }
		    else
		    {
		    Intent intent1=new Intent();
		    intent1.putExtra("personId", personId);
		    intent1.putExtra("name",personName);
		    intent1.setClass(DisplaySomebody.this,EditSomebody.class);
			startActivity(intent1);
		    }
		 }
		 return true;
		
	}

}
