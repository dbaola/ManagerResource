package com.ex.somebody;



import com.ex.analysor.SearchWords;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String[][] personsInfo;     //��¼��ѯ���ص���Ա��Ϣ
	//����Ϊ���Ʊ���
	private boolean updownFlag=true;   //trueΪ����,falseΪ����
	private int updownDepend=0;  //0Ϊ��Ĭ����ʾ����ʾ�绰�Ͷ��š�1��socialClass����λ��������ʾ��2��personalWealth���Ƹ�����ʾ��3��educationalStatus������״������ʾ��4��cohesion�����ܶȣ���ʾ
	private String keyWords="";     //��ѯ�ؼ���
	//��̬��ά����,��Ų�ѯ������
	//[0][x],���᣻[1][x]��ְҵ��[2][x]����λ��[3][x]�������˸����ͣ�[4][x]����Ȥ���ã�[5][x]��ϵ����;[6][x]ѧ����[7][x]��λ��С
	private static String[][] searchWords=new String[][] {
		{"����","������","����","����","����","����","����","����","�½�","����","����","����","�Ĵ�","ɽ��","�ӱ�","����","ɽ��","����","����","�㶫","����","����","�㽭","����","����","����","̨��","����","����","���","�Ϻ�","����"},
		{"ְҵ","����","��ʦ","����","����Ա","ũ��"},
		{"��λ","518bd","531bd"},
		{"�Ը�","������","������","�ɾ���","������","������","������","��Ծ��","������","ƽ����"},
		{"����","����","����","�鷨","�˶�","�ղ�","�Ĳ�","����"},
		{"��ϵ","����","ͬѧ","����","�쵼","��ʦ","�ϻ���","ͬ��"},
		{"�����̶�","����ר��","��ʿ","�о���","����","��ר"},
		{"ְ�񼶱�","�����ɲ�","�����ɲ�","ʦ���ɲ�","�ż��ɲ�","�����ɲ�","Ӫ���ɲ�","������Ա"},
		{"���˲Ƹ�","������","ǧ����","������"}};
	
	private SearchWords sw=new SearchWords();
	private int[] path= {R.drawable.man,R.drawable.woman,R.drawable.man1,R.drawable.woman1};
	//private ImageView[] imv_sex;
	
	private int tempi;
	private int personCounts;//�ܹ�¼�������
	
	////��½�������
	private AlertDialog setPrefernAlertDialog;
	private AlertDialog showPasswordDialog;
	private AlertDialog someoneInfoDialog;
	private boolean isFirst;
	private SharedPreferences sp;
	
	///����绰������
	private String[] columns={
			Contacts._ID,
			Contacts.DISPLAY_NAME,
			Phone.NUMBER,
			Phone.CONTACT_ID,};
	//-------------------
	///����ɾ����־
	private boolean delFlags=false;       //false����ʾɾ��ѡ��true��ʾɾ��ѡ��
	//--------------------------
	///����ؼ�
	//private TextView pBirthday; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//NeTranStr trans=new NeTranStr();
		sp=getSharedPreferences("data", 0);
		
	    isFirst=sp.getBoolean("first", true);
	                    //��ֵ�Ѿ�����,���½;��û��,��ע��
	    if(isFirst){
	    	showSetPasswordDialog();
	    	
	    }
	    else if(!sp.getBoolean("isLogined", false))
	    {
	    	showInputPasswordDialog();
	    }
	    else{
	    	init();
	    }
	    
	    
     }
	
	

	public void init(){
		
		/* ���ĺͿ��Ʋ�ѯ�������õ���ͬ����ʾ���
		 * ��ѯ���ݿ������м�¼����Ա���֣���������ʾ��������ԱId�š�
		 * �������Ա𡢹�λ���Ƹ���֪ʶ�����ܶȡ��绰
		 * ʹ��һ����ά����personsInfo[][]����ֵ�����ұ�����ά����ֵ  
		 * */
		//0Ϊ��Ĭ����ʾ����ʾ�绰��1��socialClass����λ��������ʾ��2��personalWealth���Ƹ�����ʾ��3��educationalStatus������״������ʾ��4��cohesion�����ܶȣ���ʾ
	if(sp.getBoolean("isLogined", false)){    //���ȷ���Ѿ���½����ʾ����
		//�Լ������ư��������ж�
		setContentView(R.layout.activity_main);
		/*���·�һ�Ź�������ť����
		*/
		ImageView taskManager=(ImageView)findViewById(R.id.task);//�������ҳ��
		//ImageView circlePower=(ImageView)findViewById(R.id.circlePower);//��������ҳ��
		//ImageView findRelatives=(ImageView)findViewById(R.id.findRelatives);//Ѱ�ҹ�ϵ
		
		taskManager.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��������������ҳ��
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, Tasks.class);
				startActivity(intent);
				
			}});
		
		final TextView updown_bt=(TextView)findViewById(R.id.updown);
		final TextView wealth_bt=(TextView)findViewById(R.id.personalWealth);
		final TextView position_bt=(TextView)findViewById(R.id.officialPosition);
		final TextView knowledge_bt=(TextView)findViewById(R.id.educationalStatus);
		final TextView cohesion_bt=(TextView)findViewById(R.id.cohesion);
		final TextView defaultc_bt=(TextView)findViewById(R.id.defaultc);
		
        
		updown_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ���������ǽ����ɲ�������updownFlag����
				if(updownFlag) {updownFlag=false;}
				else {updownFlag=true;}
				init();		
				
			}});
		defaultc_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ����λ��С������ʾ����
				
				updownDepend=0;
				init();
				
				
				
			}});
		position_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ����λ��С������ʾ����
				
				updownDepend=1;
				init();
				
				
				
			}});
		wealth_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����˲Ƹ�����������ʾ���ݡ�
				updownDepend=2;
				init();
				
			}});
		knowledge_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �������̶�������ʾ����
				updownDepend=3;
				init();
				
				
			}});
		cohesion_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//�����ҵĹ�ϵ�����̶ܳ�����
				updownDepend=4;
				init();
				
				
			}});
		
		//��ʾģ�顣��֧����ͬupdownDepend��keyWords������ʾ��ͬЧ��
		DBHelper helper=new DBHelper(this);
	    personsInfo=helper.listPersons(updownDepend,updownFlag);		
		TableLayout tbLayout2=(TableLayout)findViewById(R.id.tableL2);
		if(String.valueOf(keyWords.trim()).equals("")){	
		switch(updownDepend) {//0Ϊ��Ĭ����ʾ����ʾ�绰��1��socialClass����λ��������ʾ��2��personalWealth���Ƹ�����ʾ��3��educationalStatus������״������ʾ��4��cohesion�����ܶȣ���ʾ
	    case 1:
		{
			//Toast.makeText(this, "��λ", Toast.LENGTH_SHORT).show();
		  position_bt.setTextColor(Color.RED);
		  for(int i=0;i<personsInfo.length;i++) {
			     personCounts=i;                                   //��¼¼�������
				 if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {break;}
				 //��ʾ��
				 //Log.i("name",personNames[i]);
				 TableRow tbRow=new TableRow(MainActivity.this);
				 tbRow.setPadding(0,0 ,0 , 40);
				 
				 tbRow.setId(i);
				 tbLayout2.addView(tbRow);
						
				 ImageView imv_sex = new ImageView(this);
				 imv_sex.setImageResource(path[0]);
						//Log.i("sex",personsInfo[i][1]);
						
				 if(String.valueOf(personsInfo[i][2]).trim().equals("��")) {
				      imv_sex.setImageResource(path[0]);
					   }
				 else {
					  imv_sex.setImageResource(path[1]);
					   }
				 imv_sex.setAdjustViewBounds(true);
				 imv_sex.setMaxWidth(60);
				 imv_sex.setMaxHeight(60);
				 imv_sex.setPadding(0, 0, 15, 0);
				 tbRow.addView(imv_sex);
						
				 TextView tv=new TextView(this);
				 if(personsInfo[i][1].length()<=4){tv.setText(personsInfo[i][1]);}
				 else{tv.setText(personsInfo[i][1].substring(0, 4)+".");}
				 tv.setTextSize(20);
				 tv.setId(i);
				 tbRow.addView(tv);
				 
				 TextView tvPosition=new TextView(this);
				 tvPosition.setPadding(10, 0, 0, 0);
				 tvPosition.setTextSize(20);
				 tvPosition.setId(i);
				 switch(Integer.valueOf(personsInfo[i][3])){
				 case 1:{tvPosition.setText("����|��������");break;}
				 case 2:{tvPosition.setText("��ʦ|����");break;}
				 case 3:{tvPosition.setText("��ʦ|����");break;}
				 case 4:{tvPosition.setText("����|����");break;}
				 case 5:{tvPosition.setText("����|����");break;}
				 case 6:{tvPosition.setText("��Ӫ|����");break;}
				 case 7:{tvPosition.setText("��Ӫ|����");break;}
				 case 8:{tvPosition.setText("����|��Ա����");break;}
				 default:{tvPosition.setText("����|��Ա����");}
				 
				 }
				 
				 tbRow.addView(tvPosition);
						
				 tv.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// ͨ��intent����displaySomebody Activity�����ݲ���Ϊname��somebodyid��
								tempi=Integer.valueOf(v.getId());
								popSomeoneDialog(tempi);
								/*
								Intent intent=new Intent();
								//Log.i("name",String.valueOf(personsInfo[tempi][1]));
								intent.putExtra("name", String.valueOf(personsInfo[tempi][1]));
								intent.putExtra("personId", Integer.valueOf(personsInfo[tempi][0]));
								
								intent.setClass(MainActivity.this,DisplaySomebody.class);
								startActivity(intent);*/
								
				  }});
						
				 }
					//��ʾ��Ա����
				 TextView tvCounts=(TextView)findViewById(R.id.personCounts);
				 tvCounts.setText("����"+personCounts+"��");
					  		  
					  
					  //�Զ���ʾ���������ݣ�Ĭ�������ʾ������|�绰���롰
				 String[] pNameAndPhone=new String[personCounts];
				 for(int i=0;i<personCounts;i++){
						 pNameAndPhone[i]=personsInfo[i][1];         //������������Ϣ
					 }
					  
					  //Log.i("content change",keyWords);
				 AutoCompleteTextView autoT1=(AutoCompleteTextView)findViewById(R.id.autoT);
				 autoT1.setText(keyWords);
				 ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,pNameAndPhone);
				 autoT1.setAdapter(adapter);
				 
				 autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> parent, View v,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							//ListView listview = (ListView) parent;  
							//ArrayAdapter<String> adapter  =  (ArrayAdapter<String>) parent.getAdapter();  
							TextView tv = (TextView)v; 
							keyWords=tv.getText().toString().trim();
							init();
							//Log.i("arg0",String.valueOf(keyWords));
							
							
						}});	
		
		 break;
		}
		case 2:{
			wealth_bt.setTextColor(Color.RED);
			//Toast.makeText(this, "�Ƹ�", Toast.LENGTH_SHORT).show();
			
			for(int i=0;i<personsInfo.length;i++) {
				
			                                        //��¼¼�������
				 if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {personCounts=i;break;}
				 //��ʾ��
				 //Log.i("name",personNames[i]);
				 TableRow tbRow=new TableRow(MainActivity.this);
				 tbRow.setPadding(0,0 ,0 , 40);
				 
				 tbRow.setId(i);
				 tbLayout2.addView(tbRow);
						
				 ImageView imv_sex = new ImageView(this);
				 imv_sex.setImageResource(path[0]);
						//Log.i("sex",personsInfo[i][1]);
						
				 if(String.valueOf(personsInfo[i][2]).trim().equals("��")) {
				      imv_sex.setImageResource(path[0]);
					   }
				 else {
					  imv_sex.setImageResource(path[1]);
					   }
				 imv_sex.setAdjustViewBounds(true);
				 imv_sex.setMaxWidth(60);
				 imv_sex.setMaxWidth(60);
				 imv_sex.setPadding(0, 0, 15, 0);
				 tbRow.addView(imv_sex);
						
				 TextView tv=new TextView(this);
				 if(personsInfo[i][1].length()<=4){tv.setText(personsInfo[i][1]);}
				 else{tv.setText(personsInfo[i][1].substring(0, 4)+".");}
				 tv.setTextSize(20);
				 tv.setId(i);
				 tbRow.addView(tv);
				 
				 TextView tvWealth=new TextView(this);
				 tvWealth.setPadding(10, 0, 0, 0);
				 tvWealth.setTextSize(20);
				 tvWealth.setId(i);
				// if(personsInfo[i][3].trim().equals("")){personsInfo[i][3]="5";}
				 
				 switch(Integer.valueOf(personsInfo[i][3])){
				 case 1:{tvWealth.setText(">1000W");break;}
				 case 2:{tvWealth.setText("100W-1000W");break;}
				 case 3:{tvWealth.setText("50W-100W");break;}
				 case 4:{tvWealth.setText("10W-50W");break;}
				 case 5:{tvWealth.setText("<10W");break;}
				 default:{tvWealth.setText("<10W");}
				 
				 }
				 //tvWealth.setText(personsInfo[i][3]);
				 tbRow.addView(tvWealth);
					
				 tv.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// ͨ��intent����displaySomebody Activity�����ݲ���Ϊname��somebodyid��
								tempi=Integer.valueOf(v.getId());
								popSomeoneDialog(tempi);
								/*
								Intent intent=new Intent();
								//Log.i("name",String.valueOf(personsInfo[tempi][1]));
								intent.putExtra("name", String.valueOf(personsInfo[tempi][1]));
								intent.putExtra("personId", Integer.valueOf(personsInfo[tempi][0]));
								
								intent.setClass(MainActivity.this,DisplaySomebody.class);
								startActivity(intent);*/
								
				  }});
						
				 }
					//��ʾ��Ա����
				 TextView tvCounts=(TextView)findViewById(R.id.personCounts);
				 tvCounts.setText("����"+personCounts+"��");
					  		  
					  
					  //�Զ���ʾ���������ݣ�Ĭ�������ʾ������|�绰���롰
				 String[] pNameAndPhone=new String[personCounts];
				 for(int i=0;i<personCounts;i++){
						 pNameAndPhone[i]=personsInfo[i][1];         //������������Ϣ
					 }
					  
					  //Log.i("content change",keyWords);
				 AutoCompleteTextView autoT1=(AutoCompleteTextView)findViewById(R.id.autoT);
				 autoT1.setText(keyWords);
				 ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,pNameAndPhone);
				 autoT1.setAdapter(adapter);
				 autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> parent, View v,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							//ListView listview = (ListView) parent;  
							//ArrayAdapter<String> adapter  =  (ArrayAdapter<String>) parent.getAdapter();  
							TextView tv = (TextView)v; 
							keyWords=tv.getText().toString().trim();
							init();
							//Log.i("arg0",String.valueOf(keyWords));
							
							
						}});
				 
		break;
		}
		case 3:{
			knowledge_bt.setTextColor(Color.RED);
			//Toast.makeText(this, "֪ʶ", Toast.LENGTH_SHORT).show();
			for(int i=0;i<personsInfo.length;i++) {
				
                //��¼¼�������
                if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {personCounts=i;break;}
                //��ʾ��
                //Log.i("name",personNames[i]);
                TableRow tbRow=new TableRow(MainActivity.this);
                tbRow.setPadding(0,0 ,0 , 40);

                tbRow.setId(i);
                tbLayout2.addView(tbRow);

                ImageView imv_sex = new ImageView(this);
                imv_sex.setImageResource(path[0]);
                //Log.i("sex",personsInfo[i][1]);

                if(String.valueOf(personsInfo[i][2]).trim().equals("��")) {
                imv_sex.setImageResource(path[0]);
                }
                else {
                imv_sex.setImageResource(path[1]);
                }
                imv_sex.setAdjustViewBounds(true);
                imv_sex.setMaxWidth(60);
                imv_sex.setMaxWidth(60);
                imv_sex.setPadding(0, 0, 15, 0);
                tbRow.addView(imv_sex);

                TextView tv=new TextView(this);
                if(personsInfo[i][1].length()<=4){tv.setText(personsInfo[i][1]);}
                else{tv.setText(personsInfo[i][1].substring(0, 4)+".");}
                tv.setTextSize(20);
                tv.setId(i);
                tbRow.addView(tv);

                TextView tvKnowledge=new TextView(this);
                tvKnowledge.setPadding(10, 0, 0, 0);
                tvKnowledge.setTextSize(20);
                tvKnowledge.setId(i);
// if(personsInfo[i][3].trim().equals("")){personsInfo[i][3]="5";}

                switch(Integer.valueOf(personsInfo[i][3])){
                case 1:{tvKnowledge.setText("����ר��");break;}
                case 2:{tvKnowledge.setText("��ʿ");break;}
                case 3:{tvKnowledge.setText("�о���");break;}
                case 4:{tvKnowledge.setText("����");break;}
                case 5:{tvKnowledge.setText("��ר");break;}
                case 6:{tvKnowledge.setText("����");break;}
                default:{tvKnowledge.setText("����");}

}
//tvWealth.setText(personsInfo[i][3]);
                tbRow.addView(tvKnowledge);

                tv.setOnClickListener(new OnClickListener(){

@Override
                public void onClick(View v) {
// ͨ��intent����displaySomebody Activity�����ݲ���Ϊname��somebodyid��
                tempi=Integer.valueOf(v.getId());
                popSomeoneDialog(tempi);
                /*
                Intent intent=new Intent();
//Log.i("name",String.valueOf(personsInfo[tempi][1]));
                intent.putExtra("name", String.valueOf(personsInfo[tempi][1]));
                intent.putExtra("personId", Integer.valueOf(personsInfo[tempi][0]));

                intent.setClass(MainActivity.this,DisplaySomebody.class);
                startActivity(intent);*/

                 }});

                }
//��ʾ��Ա����
               TextView tvCounts=(TextView)findViewById(R.id.personCounts);
               tvCounts.setText("����"+personCounts+"��");


//�Զ���ʾ���������ݣ�Ĭ�������ʾ������|�绰���롰
               String[] pNameAndPhone=new String[personCounts];
               for(int i=0;i<personCounts;i++){
               pNameAndPhone[i]=personsInfo[i][1];         //������������Ϣ
               }

//Log.i("content change",keyWords);
               AutoCompleteTextView autoT1=(AutoCompleteTextView)findViewById(R.id.autoT);
               autoT1.setText(keyWords);
               ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,pNameAndPhone);
               autoT1.setAdapter(adapter);
               autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

   				@Override
   				public void onItemClick(AdapterView<?> parent, View v,
   						int arg2, long arg3) {
   					// TODO Auto-generated method stub
   					//ListView listview = (ListView) parent;  
   					//ArrayAdapter<String> adapter  =  (ArrayAdapter<String>) parent.getAdapter();  
   					TextView tv = (TextView)v; 
   					keyWords=tv.getText().toString().trim();
   					init();
   					//Log.i("arg0",String.valueOf(keyWords));
   					
   					
   				}});
			
		break;}
		case 4:{
			cohesion_bt.setTextColor(Color.RED);
			//Toast.makeText(this, "��ϵ", Toast.LENGTH_SHORT).show();
            for(int i=0;i<personsInfo.length;i++) {
				
                //��¼¼�������
                if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {personCounts=i;break;}
                //��ʾ��
                //Log.i("name",personNames[i]);
                TableRow tbRow=new TableRow(MainActivity.this);
                tbRow.setPadding(0,0 ,0 , 40);

                tbRow.setId(i);
                tbLayout2.addView(tbRow);

                ImageView imv_sex = new ImageView(this);
                imv_sex.setImageResource(path[0]);
                //Log.i("sex",personsInfo[i][1]);

                if(String.valueOf(personsInfo[i][2]).trim().equals("��")) {
                imv_sex.setImageResource(path[0]);
                }
                else {
                imv_sex.setImageResource(path[1]);
                }
                imv_sex.setAdjustViewBounds(true);
                imv_sex.setMaxWidth(60);
                imv_sex.setMaxWidth(60);
                imv_sex.setPadding(0, 0, 15, 0);
                tbRow.addView(imv_sex);

                TextView tv=new TextView(this);
                if(personsInfo[i][1].length()<=4){tv.setText(personsInfo[i][1]);}
                else{tv.setText(personsInfo[i][1].substring(0, 4)+".");}
                tv.setTextSize(20);
                tv.setId(i);
                tbRow.addView(tv);

                TextView tvRelationship=new TextView(this);
                tvRelationship.setPadding(10, 0, 0, 0);
                tvRelationship.setTextSize(20);
                tvRelationship.setId(i);
// if(personsInfo[i][3].trim().equals("")){personsInfo[i][3]="5";}

               tvRelationship.setText(personsInfo[i][4]);
//tvWealth.setText(personsInfo[i][3]);
                tbRow.addView(tvRelationship);

                tv.setOnClickListener(new OnClickListener(){

@Override
                public void onClick(View v) {
// ͨ��intent����displaySomebody Activity�����ݲ���Ϊname��somebodyid��
                tempi=Integer.valueOf(v.getId());
                popSomeoneDialog(tempi);
                /*
                Intent intent=new Intent();
//Log.i("name",String.valueOf(personsInfo[tempi][1]));
                intent.putExtra("name", String.valueOf(personsInfo[tempi][1]));
                intent.putExtra("personId", Integer.valueOf(personsInfo[tempi][0]));

                intent.setClass(MainActivity.this,DisplaySomebody.class);
                startActivity(intent);*/

                 }});

                }
//��ʾ��Ա����
               TextView tvCounts=(TextView)findViewById(R.id.personCounts);
               tvCounts.setText("����"+personCounts+"��");


//�Զ���ʾ���������ݣ�Ĭ�������ʾ������|�绰���롰
               String[] pNameAndPhone=new String[personCounts];
               for(int i=0;i<personCounts;i++){
               pNameAndPhone[i]=personsInfo[i][1];         //������������Ϣ
               }

//Log.i("content change",keyWords);
               AutoCompleteTextView autoT1=(AutoCompleteTextView)findViewById(R.id.autoT);
               autoT1.setText(keyWords);
               ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,pNameAndPhone);
               autoT1.setAdapter(adapter);
               autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

   				@Override
   				public void onItemClick(AdapterView<?> parent, View v,
   						int arg2, long arg3) {
   					// TODO Auto-generated method stub
   					//ListView listview = (ListView) parent;  
   					//ArrayAdapter<String> adapter  =  (ArrayAdapter<String>) parent.getAdapter();  
   					TextView tv = (TextView)v; 
   					keyWords=tv.getText().toString().trim();
   					init();
   					//Log.i("arg0",String.valueOf(keyWords));
   					
   					
   				}});
		break;}
		case 0:{
			defaultc_bt.setTextColor(Color.RED);

			
			//Log.i("name",personsInfo[0][1]);
			
				
			  for(int i=0;i<personsInfo.length;i++) {
				personCounts=i;                                   //��¼¼�������
				//Log.i("personsInfo[i][1]",personsInfo[i][1]);
				if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {break;}
				//if(!String.valueOf(keyWords).trim().equals("null")&&(!personsInfo[i][1].trim().equals(keyWords))){return;}
				//if(!String.valueOf(keyWords).trim().equals("�ؼ���")&&String.valueOf(keyWords).trim().equals(personsInfo[i][1])){Log.i("keyWords",String.valueOf(keyWords));return;}
				//��ʾ��
				//Log.i("name",personNames[i]);
				TableRow tbRow=new TableRow(MainActivity.this);
				tbRow.setPadding(0,0 ,0 , 10);
				tbRow.setId(i);
				tbLayout2.addView(tbRow);
				
				ImageView imv_sex = new ImageView(this);
				imv_sex.setImageResource(path[0]);
				//Log.i("sex",personsInfo[i][1]);
				
				if(String.valueOf(personsInfo[i][2]).trim().equals("��")) {
					imv_sex.setImageResource(path[0]);
					}
				else {
					imv_sex.setImageResource(path[1]);
				}
				imv_sex.setAdjustViewBounds(true);
				imv_sex.setMaxWidth(60);
				imv_sex.setMaxWidth(60);
				imv_sex.setPadding(0, 0, 15, 0);
				
				
				
				tbRow.addView(imv_sex);
				
				TextView tv=new TextView(this);
				if(personsInfo[i][1].length()<=4){tv.setText(personsInfo[i][1]);}
				 else{tv.setText(personsInfo[i][1].substring(0, 4)+".");}
				//tv.setText(personsInfo[i][1]);
				//tv.setGravity(1);
				tv.setTextSize(23);
				tv.setId(i);
				
				tbRow.addView(tv);
				
				ImageView ivPhone=new ImageView(this);
				ivPhone.setMaxWidth(48);
				ivPhone.setMaxHeight(48);
				ivPhone.setImageResource(R.drawable.telephone);
				ivPhone.setPadding(50, 0, 0, 0);
				ivPhone.setId(i);
				tbRow.addView(ivPhone);
				//ivPhone.setImageDrawable(R.drawable.telephone);
				
				ImageView ivSms=new ImageView(this);
				ivSms.setMaxWidth(48);
				ivSms.setMaxHeight(48);
				ivSms.setImageResource(R.drawable.ems);
				ivSms.setPadding(10, 0, 0, 0);
				ivSms.setId(i);
				tbRow.addView(ivSms);
				//���Ҫ������ɾ��
				if(delFlags==true) {
				ImageView delPs=new ImageView(this);
				delPs.setMaxHeight(48);
				delPs.setMaxWidth(48);
				delPs.setImageResource(R.drawable.del2);
				delPs.setId(i);
				tbRow.addView(delPs);
				delPs.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// ����ɾ����ť����ɾ��
						int j=v.getId();
						//ɾ����ʾ������
						
						/*
						TextView tv=(TextView)findViewById(j);
						TableRow tbRow=(TableRow)findViewById(j);
						tbRow.removeView(tv);*/
						//ɾ������
						
						ImageView delPs=(ImageView)v.findViewById(j);
						delPs.setImageResource(R.drawable.del0);
						int personId=Integer.valueOf(personsInfo[j][0]);
						String personName=personsInfo[j][1];
						DBHelper helper=new DBHelper(MainActivity.this);
						int flag=helper.delSomebody(personId, personName);
						if(flag>0) {
							Toast.makeText(MainActivity.this, personName+"����ɾ��", Toast.LENGTH_SHORT).show();
							//init();
						}
					}});
				}
				
				//init()���ô������ƿ�
				
				//����ĳ���򵯳�displaySomebody��ʾ��
				
				tv.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// ͨ��intent����displaySomebody Activity�����ݲ���Ϊname��somebodyid��
						
						
						tempi=Integer.valueOf(v.getId());					    	
				    	popSomeoneDialog(tempi);      //������ʾ�Ի���
					}

					});
				//��绰ͼ�겦��绰
				ivPhone.setOnTouchListener(new OnTouchListener(){

					@Override
					public boolean onTouch(View v, MotionEvent arg1) {
						// �������绰
						
						//Log.i("tag","telphone"+personsInfo[v.getId()][3]);
						String telNo=personsInfo[v.getId()][3];
						if((telNo!=null)&&(!"".equals(telNo.trim()))){
					Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telNo));
					startActivity(intent);
					return true;
				}
						else {
							Toast.makeText(MainActivity.this, "û�б���绰���룡", Toast.LENGTH_SHORT).show();
							return false;	
						}
						
					}});
				//�����ͼ�귢�Ͷ���
				ivSms.setOnTouchListener(new OnTouchListener(){

					@Override
					public boolean onTouch(View v, MotionEvent arg1) {
						// ���Ͷ���
						//Log.i("tag","Sms"+personsInfo[v.getId()][3]);
						//�����Ի��򣬱�д��������
						final EditText smsContent=new EditText(MainActivity.this);
						final String telNo1=personsInfo[v.getId()][3];
						if(telNo1!=null&&(!"".equals(telNo1.trim()))){
							//new AlertDialog.Builder(MainActivity.this).setTitle("��д��������").setView(smsContent).setPositiveButton("ȷ��",null).create().show();
							//Log.i("smsContent:",smsContent.getText().toString());
							Intent intent=new Intent();
							intent.setAction(Intent.ACTION_SENDTO);
							intent.setData(Uri.parse("smsto:"+telNo1));
							intent.putExtra("sms_body", smsContent.getText().toString());
							startActivity(intent);
							return true;
							
						}
						else {
							Toast.makeText(MainActivity.this, "û�б���绰���룡", Toast.LENGTH_SHORT).show();
							return false;	
						}
					}});
				}
			//��ʾ��Ա����
			  TextView tvCounts=(TextView)findViewById(R.id.personCounts);
			  tvCounts.setText("����"+personCounts+"��");
			  
			  			  
			  
			  //�Զ���ʾ���������ݣ�Ĭ�������ʾ������|�绰���롰
			 
			  
			 String[] pNameAndPhone=new String[personCounts];
			 for(int i=0;i<personCounts;i++){
				 pNameAndPhone[i]=personsInfo[i][1];         //������������Ϣ
				//Log.i("name:",pNameAndPhone[i]);
			 }
			
			  
			  //Log.i("content change",keyWords);
			 sw.strArr2to1(searchWords);//������ʵ���������
			 
			 //�������������ַ��������������ַ�
			 String[] sWords=new String[pNameAndPhone.length+sw.getStringArr1().length];
			 for(int i=0;i<pNameAndPhone.length;i++) {
				 sWords[i]=pNameAndPhone[i];
			 }
			 for(int j=0;j<sw.getStringArr1().length;j++) {
				 sWords[pNameAndPhone.length+j]=sw.getStringArr1()[j];
			 }
			 /*
			 for(int i=0;i<sWords.length;i++) {
				 Log.i("content:",String.valueOf(sWords[i]));
			 }*/
			 
			  AutoCompleteTextView autoT1=(AutoCompleteTextView)findViewById(R.id.autoT);
			  autoT1.setText(keyWords);
			  ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,sWords);
			  autoT1.setAdapter(adapter);
			  autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					//ListView listview = (ListView) parent;  
					//ArrayAdapter<String> adapter  =  (ArrayAdapter<String>) parent.getAdapter();  
					TextView tv = (TextView)v; 
					keyWords=tv.getText().toString().trim();
					init();
					//Log.i("arg0",String.valueOf(keyWords));
					
					
				}});
			 
			  
			
		}			
		
	}}
		else{//���������keyWords����������������1��keyWords��������2��keyWords��������ʣ� �ڶ�ά����sWords[][]�У�
			//���ж��Ƿ���sw��
			
			int n=0,nTemp=sw.getArrLenth()[n];//className[n]�е�ֵ
			for(int i=0;i<sw.getStringArr1().length;i++) {
				
			if(i>=nTemp) {n=n+1;nTemp=nTemp+sw.getArrLenth()[n];}	
			if(sw.getStringArr1()[i].contains(keyWords)) {
				//Log.i("className",sw.getClassName()[n]);
			//�������Ѱ�ұ���в�ѯ������ssPersons[][]��Ϣ����Ҫ����������Id���Լ�����keyWords���ص���Ϣ
			//����i��ֵ������	
				DBHelper sshelper=new DBHelper(this);
			    final String[][] ssPersons=sshelper.searchSpecialPersons(keyWords,sw.getClassName()[n]);
			    
			    if(ssPersons!=null){
			    	//�����������������,��ʾ�û�
			    	TableRow tbRow0=new TableRow(MainActivity.this);
					tbRow0.setPadding(0,0 ,0 , 10);
					
					TextView tvDisp=(TextView)findViewById(R.id.personCounts);
					tvDisp.setText("��ѯ"+sw.getClassName()[n]+":"+keyWords);
					
				    
				    //��ʾssPersons����
				    for(int dN=0;dN<ssPersons.length;dN++) {
				    
					
				    TableRow tbRow=new TableRow(MainActivity.this);
					tbRow.setPadding(0,0 ,0 , 10);
					tbRow.setId(dN);
					tbLayout2.addView(tbRow);
					
					ImageView imv_sex = new ImageView(this);
					imv_sex.setImageResource(path[0]);
					//Log.i("sex",personsInfo[i][1]);
					
					if(String.valueOf(ssPersons[dN][2]).trim().equals("��")) {
						imv_sex.setImageResource(path[0]);
						}
					else {
						imv_sex.setImageResource(path[1]);
					}
					imv_sex.setAdjustViewBounds(true);
					imv_sex.setMaxWidth(60);
					imv_sex.setMaxWidth(60);
					imv_sex.setPadding(0, 0, 15, 0);		
					
					tbRow.addView(imv_sex);
					TextView tv=new TextView(this);
					if(ssPersons[dN][1].length()<=4){tv.setText(ssPersons[dN][1]);}
					 else{tv.setText(ssPersons[dN][1].substring(0, 4)+".");}
					//tv.setText(personsInfo[i][1]);
					tv.setTextSize(20);
					tv.setId(dN);
					
					tbRow.addView(tv);
					tv.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							// ͨ��intent����displaySomebody Activity�����ݲ���Ϊname��somebodyid��
							
							
							tempi=Integer.valueOf(v.getId());					    	
					    	popSomeoneDialog(tempi);      //������ʾ�Ի���
						}

						});
					if(delFlags==true) {
						ImageView delPs=new ImageView(this);
						delPs.setMaxHeight(48);
						delPs.setMaxWidth(48);
						delPs.setImageResource(R.drawable.del2);
						delPs.setId(dN);
						tbRow.addView(delPs);
						delPs.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// ����ɾ����ť����ɾ��
								int j=v.getId();
								ImageView delPs=(ImageView)v.findViewById(j);
								delPs.setImageResource(R.drawable.del0);
								int personId=Integer.valueOf(ssPersons[j][0]);
								String personName=ssPersons[j][1];
								DBHelper helper=new DBHelper(MainActivity.this);
								int flag=helper.delSomebody(personId, personName);
								if(flag>0) {
									Toast.makeText(MainActivity.this, personName+"����ɾ��", Toast.LENGTH_SHORT).show();
									//init();
								}
							}});
					}

				    }
					
			    }
			    else{
			    	Toast.makeText(this, "û��������������", Toast.LENGTH_SHORT).show();
			    }
			    
			   
			}
		   }
			for(int i=0;i<personsInfo.length;i++) {
				if(String.valueOf(personsInfo[i][1]).trim().contains(keyWords)){
					
					if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {break;}
					//if(!String.valueOf(keyWords).trim().equals("null")&&(!personsInfo[i][1].trim().equals(keyWords))){return;}
					//if(!String.valueOf(keyWords).trim().equals("�ؼ���")&&String.valueOf(keyWords).trim().equals(personsInfo[i][1])){Log.i("keyWords",String.valueOf(keyWords));return;}
					//��ʾ��
					//Log.i("name",personNames[i]);
					TableRow tbRow=new TableRow(MainActivity.this);
					tbRow.setPadding(0,0,0, 10);
					tbRow.setId(i);
					tbLayout2.addView(tbRow);
					
					ImageView imv_sex = new ImageView(this);
					imv_sex.setImageResource(path[0]);
					//Log.i("sex",personsInfo[i][1]);
					
					if(String.valueOf(personsInfo[i][2]).trim().equals("��")) {
						imv_sex.setImageResource(path[0]);
						}
					else {
						imv_sex.setImageResource(path[1]);
					}
					imv_sex.setAdjustViewBounds(true);
					imv_sex.setMaxWidth(60);
					imv_sex.setMaxWidth(60);
					imv_sex.setPadding(0, 0, 15, 0);
					
					
					
					tbRow.addView(imv_sex);
					
					TextView tv=new TextView(this);
					if(personsInfo[i][1].length()<=4){tv.setText(personsInfo[i][1]);}
					 else{tv.setText(personsInfo[i][1].substring(0, 4)+".");}
					//tv.setText(personsInfo[i][1]);
					tv.setTextSize(20);
					tv.setId(i);
					
					tbRow.addView(tv);
					
					tv.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// ͨ��intent����displaySomebody Activity�����ݲ���Ϊname��somebodyid��
								
								
								tempi=Integer.valueOf(v.getId());					    	
						    	popSomeoneDialog(tempi);      //������ʾ�Ի���
							}

							});
					if(delFlags==true) {
						ImageView delPs=new ImageView(this);
						delPs.setMaxHeight(48);
						delPs.setMaxWidth(48);
						delPs.setImageResource(R.drawable.del2);
						delPs.setId(i);
						tbRow.addView(delPs);
						delPs.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// ����ɾ����ť����ɾ��
								int j=v.getId();
								ImageView delPs=(ImageView)v.findViewById(j);
								delPs.setImageResource(R.drawable.del0);
								int personId=Integer.valueOf(personsInfo[j][0]);
								String personName=personsInfo[j][1];
								DBHelper helper=new DBHelper(MainActivity.this);
								int flag=helper.delSomebody(personId, personName);
								if(flag>0) {
									Toast.makeText(MainActivity.this, personName+"����ɾ��", Toast.LENGTH_SHORT).show();
									//init();
								}
							}});
					}
				}
			}
			 String[] pNameAndPhone=new String[personCounts];
			 for(int k=0;k<personCounts;k++){
				 pNameAndPhone[k]=personsInfo[k][1];         //������������Ϣ
			 }
			  AutoCompleteTextView autoT1=(AutoCompleteTextView)findViewById(R.id.autoT);
			  autoT1.setText(keyWords);
			  ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,pNameAndPhone);
			  autoT1.setAdapter(adapter);
			  autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					//ListView listview = (ListView) parent;  
					//ArrayAdapter<String> adapter  =  (ArrayAdapter<String>) parent.getAdapter();  
					TextView tv = (TextView)v; 
					keyWords=tv.getText().toString().trim();
					init();
					//Log.i("arg0",String.valueOf(keyWords));
					
					
				}});
			
			
		}
		 //�Զ���ʾ���������ݣ�Ĭ�������ʾ������|�绰���롰
		 
		  
		 String[] pNameAndPhone=new String[personCounts];
		 for(int i=0;i<personCounts;i++){
			 pNameAndPhone[i]=personsInfo[i][1];         //������������Ϣ
			//Log.i("name:",pNameAndPhone[i]);
		 }
		
		  
		  //Log.i("content change",keyWords);
		 sw.strArr2to1(searchWords);//������ʵ���������
		 
		 //�������������ַ��������������ַ�
		 String[] sWords=new String[pNameAndPhone.length+sw.getStringArr1().length];
		 for(int i=0;i<pNameAndPhone.length;i++) {
			 sWords[i]=pNameAndPhone[i];
		 }
		 for(int j=0;j<sw.getStringArr1().length;j++) {
			 sWords[pNameAndPhone.length+j]=sw.getStringArr1()[j];
		 }
		 /*
		 for(int i=0;i<sWords.length;i++) {
			 Log.i("content:",String.valueOf(sWords[i]));
		 }*/
		 
		  AutoCompleteTextView autoT1=(AutoCompleteTextView)findViewById(R.id.autoT);
		  autoT1.setText(keyWords);
		  ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,sWords);
		  autoT1.setAdapter(adapter);
		  autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//ListView listview = (ListView) parent;  
				//ArrayAdapter<String> adapter  =  (ArrayAdapter<String>) parent.getAdapter();  
				TextView tv = (TextView)v; 
				keyWords=tv.getText().toString().trim();
				init();
				//Log.i("arg0",String.valueOf(keyWords));
				
				
			}});
		
		
  }
  else{
        	showInputPasswordDialog();
        }
		
}
	
	
	
	
	protected void popSomeoneDialog(int tempi2) {
		// TODO �Զ����ɵķ������
		//Log.i("name|id",personsInfo[tempi2][1]+personsInfo[tempi2][0]);
		
////////����������������idֵ��ʹ�õ����Ի���ʽ��ʾ��Ϣ����
						AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
				    	LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
				    	View view=inflater.inflate(R.layout.display_somebody, null);
						 
				    	//����xml��ʾ����Ϣ
				    	DBHelper helper=new DBHelper(MainActivity.this);
				    	 Somebody someone=new Somebody();
				    	
				    	//Log.i("name",personName);
					    String personName=String.valueOf(personsInfo[tempi][1]);
					    int personId=Integer.valueOf(personsInfo[tempi][0]);
				    	someone=helper.query(personName,personId);
				    	
				    	//ImageView personIv=(ImageView)view.findViewById(R.id.person_iv);
				    			    	
				    	if(String.valueOf(someone.getSex()).trim().equals("��")) {
						      builder.setIcon(path[2]);
							   }
						 else {
							  builder.setIcon(path[3]);
							   }
				    	
				    	
				    	
				    	TextView pBirthday=(TextView)view.findViewById(R.id.birthday);
				    	//Log.i("birth",someone.getBirthday());
				    	pBirthday.setText(String.valueOf(someone.getBirthday()));
				    	
				    	TextView pNativePlace=(TextView)view.findViewById(R.id.nativePlace);
				    	pNativePlace.setText(String.valueOf(someone.getNativePlace()));
				    	
				    	TextView pNickName=(TextView)view.findViewById(R.id.nickName);
				    	pNickName.setText(String.valueOf(someone.getNickName()));
				    	
				    	TextView pTelephone=(TextView)view.findViewById(R.id.telephone);
				    	pTelephone.setText(String.valueOf(someone.getTelephone()));
				    	
				    	TextView pEmail=(TextView)view.findViewById(R.id.email);
				    	pEmail.setText(String.valueOf(someone.getEmail()));
				    	
				    	TextView pQq=(TextView)view.findViewById(R.id.qq);
				    	pQq.setText(String.valueOf(someone.getQq()));
				    	
				    	TextView pWeibo=(TextView)view.findViewById(R.id.weibo);
				    	pWeibo.setText(String.valueOf(someone.getWeibo()));
				    	
				    	TextView pBoke=(TextView)view.findViewById(R.id.boke);
				    	pBoke.setText(String.valueOf(someone.getBoke()));
				    	
				    	TextView pWechat=(TextView)view.findViewById(R.id.wechat);
				    	pWechat.setText(String.valueOf(someone.getWechat()));
				    	
				    	TextView pAddress=(TextView)view.findViewById(R.id.address);
				    	pAddress.setText(String.valueOf(someone.getAddress()));
				    	
				    	TextView pNeeds=(TextView)view.findViewById(R.id.needs);
				    	pNeeds.setText(String.valueOf(DataAdd(someone.getNeeds())));
				    	
				    	TextView pHeigt=(TextView)view.findViewById(R.id.height);
				    	pHeigt.setText(String.valueOf(someone.getHeight()));
				    	
				    	TextView pWeight=(TextView)view.findViewById(R.id.weight);
				    	pWeight.setText(String.valueOf(someone.getWeight()));
				    	
				    	TextView pBloodType=(TextView)view.findViewById(R.id.bloodType);
				    	pBloodType.setText(String.valueOf(someone.getBloodType()));
				    	
				    	TextView pNationality=(TextView)view.findViewById(R.id.nationality);
				    	pNationality.setText(String.valueOf(someone.getNationality()));
				    	
				    	TextView pParty=(TextView)view.findViewById(R.id.party);
				    	pParty.setText(String.valueOf(someone.getParty()));
				    	
				    	TextView pOccupation=(TextView)view.findViewById(R.id.occupation);
				    	pOccupation.setText(String.valueOf(someone.getOccupation()));
				    	
				    	TextView pWorkUnits=(TextView)view.findViewById(R.id.workUnits);
				    	pWorkUnits.setText(String.valueOf(someone.getWorkUnits()));
				    	
				    	TextView pWorkTitle=(TextView)view.findViewById(R.id.workTitle);
				    	pWorkTitle.setText(String.valueOf(someone.getWorkTitle()));
				    	
				    	TextView pIncome=(TextView)view.findViewById(R.id.income);
				    	pIncome.setText(String.valueOf(someone.getIncome()));
				    	 
				    	TextView pCharacterType=(TextView)view.findViewById(R.id.characterType);
				    	switch(someone.getCharacterType()){
					    case 1:{pCharacterType.setText("������");break;}
					    case 2:{pCharacterType.setText("������");break;}
					    case 3:{pCharacterType.setText("�ɾ���");break;}
					    case 4:{pCharacterType.setText("������");break;}
					    case 5:{pCharacterType.setText("������");break;}
					    case 6:{pCharacterType.setText("������");break;}
					    case 7:{pCharacterType.setText("��Ծ��");break;}
					    case 8:{pCharacterType.setText("������");break;}
					    case 9:{pCharacterType.setText("ƽ����");break;}
					    default:{pCharacterType.setText(" ");}
					    }
				    	TextView pHobby=(TextView)view.findViewById(R.id.hobby);
				    	pHobby.setText(String.valueOf(DataAdd(someone.getHobby())));
				    	
				    	TextView pStrongPoint=(TextView)view.findViewById(R.id.strongPoint);
				    	pStrongPoint.setText(String.valueOf(DataAdd(someone.getStrongPoint())));
				    	
				    	TextView pWeakPoint=(TextView)view.findViewById(R.id.weakPoint);
				    	pWeakPoint.setText(String.valueOf(DataAdd(someone.getWeakPoint())));
				    	
				    	TextView pPersonalWealth=(TextView)view.findViewById(R.id.personalWealth);
					    switch(someone.getPersonalWealth()){
					    case 1:{pPersonalWealth.setText("����1000W");break;}
					    case 2:{pPersonalWealth.setText("100-1000W");break;}
					    case 3:{pPersonalWealth.setText("50-100W");break;}
					    case 4:{pPersonalWealth.setText("10-50W");break;}
					    case 5:{pPersonalWealth.setText("<10W");break;}
					    default:{pPersonalWealth.setText(" ");}
					    }
					    TextView pSocialClass=(TextView)view.findViewById(R.id.socialClass);
					    switch(someone.getSocialClass()){
					    case 1:{pSocialClass.setText("����|��������");break;}
					    case 2:{pSocialClass.setText("��ʦ|����");break;}
					    case 3:{pSocialClass.setText("��ʦ|����");break;}
					    case 4:{pSocialClass.setText("����|����");break;}
					    case 5:{pSocialClass.setText("����|����");break;}
					    case 6:{pSocialClass.setText("��Ӫ|����");break;}
					    case 7:{pSocialClass.setText("��Ӫ|����");break;}
					    case 8:{pSocialClass.setText("����|��Ա����");break;}
					    default:{pSocialClass.setText(" ");}
					    }
					    
					    TextView pEducationalStatus=(TextView)view.findViewById(R.id.educationalStatus);
					    switch(someone.getEducationalStatus()){
					    case 1:{pEducationalStatus.setText("����ר��");break;}
					    case 2:{pEducationalStatus.setText("��ʿ");break;}
					    case 3:{pEducationalStatus.setText("�о���");break;}
					    case 4:{pEducationalStatus.setText("����");break;}
					    case 5:{pEducationalStatus.setText("��ר");break;}
					    case 6:{pEducationalStatus.setText("����");break;}
					    default:{pEducationalStatus.setText(" ");} 
					    }
					    
					    TextView pCohesion=(TextView)view.findViewById(R.id.cohesion);
					    pCohesion.setText(String.valueOf(someone.getCohesion()));
					    TextView pRelationship=(TextView)view.findViewById(R.id.relationship);
					    pRelationship.setText(someone.getRelationship());
					    
					    TableLayout tbL1=(TableLayout)view.findViewById(R.id.tableL1);
					   
					    for(int i=0;i<10;i++){
					    	TableRow tbRow=new TableRow(MainActivity.this);
					    	
					    	if(String.valueOf(someone.getRelatedPerson()[i][1]).trim().equals("null")||String.valueOf(someone.getRelatedPerson()[i][2]).trim().equals("null")){
					    		break;
					    		}
					    	TextView relatedPersonType=new TextView(MainActivity.this);
					    	TextView relatedPersonName=new TextView(MainActivity.this);
					    	relatedPersonType.setText(someone.getRelatedPerson()[i][1]);
					    	relatedPersonName.setText(someone.getRelatedPerson()[i][2]);
					    	relatedPersonType.setTextSize(14);
					    	relatedPersonName.setTextSize(14);
					    	relatedPersonType.setGravity(1);
					    	relatedPersonName.setGravity(1);
					    	tbL1.addView(tbRow);
					    	tbRow.addView(relatedPersonName);
					    	tbRow.addView(relatedPersonType);
					    	
					    }
					    
					    Button bt_edit=(Button)view.findViewById(R.id.edit);
					    Button bt_cancel=(Button)view.findViewById(R.id.cancel);
					    
				    	builder.setTitle(String.valueOf(someone.getName())+"����Ϣ");
			    	    builder.setView(view);
			    	    someoneInfoDialog=builder.create();
			    	    someoneInfoDialog.show();
			    	    
			    	    bt_edit.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								//������ر���ʾ������༭Activity
								someoneInfoDialog.dismiss();
								//��ת��display activity������ʾ����
								
								Intent intent=new Intent();
								//Log.i("name",String.valueOf(personsInfo[tempi][1]));
								
								intent.putExtra("name", String.valueOf(personsInfo[tempi][1]));
								intent.putExtra("personId", Integer.valueOf(personsInfo[tempi][0]));
								
								intent.setClass(MainActivity.this,EditSomebody.class);
								startActivity(intent);
							}});
			    	     bt_cancel.setOnClickListener(new OnClickListener() {

						   @Override
						   public void onClick(View v) {
							//�˳������˳��Ի���
							someoneInfoDialog.dismiss();
							
						}});
		
	}



	private String DataAdd(String[] elements) {
		// TODO �Զ����ɵķ������
		String result = "";
		if(elements[0].toString().trim().equals("")) {
			//Log.i("elements[0]",elements[0]);
			result=" ";
		}
		else {		
		for(int i=0;i<elements.length;i++) {
			result+=String.valueOf(elements[i])+"��";
		}
		}
		//for(int i=0;i<elements.length;i++)
		//Log.i("lenth",String.valueOf(elements[i]));
		return result;
	}


	/*��һ��ʹ��,����ע��Ի���,�����û�����*/
    private void showSetPasswordDialog()
    {
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	LayoutInflater inflater=LayoutInflater.from(this);
    	View view=inflater.inflate(R.layout.show_password_dialog, null);
    	final EditText username=(EditText)view.findViewById(R.id.username);
    	final EditText userpassword=(EditText)view.findViewById(R.id.password);
    	Button ok=(Button)view.findViewById(R.id.ok);
    	Button cancel=(Button)view.findViewById(R.id.cancel);
    	           //ȷ��ע�������
    	ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String name=username.getText().toString();
				String password=userpassword.getText().toString();
				//�û������ж�
				if(name.trim().equals("")||password.trim().equals("")){
					Toast.makeText(MainActivity.this, "�û��������벻��Ϊ��", Toast.LENGTH_LONG).show();
					return;
				}
				//sharedpreference����,��ֵ���浽data�ļ�
				Editor editor=sp.edit();
				editor.putString("name", name);
				editor.putString("password", password);
				editor.putBoolean("first", false);
				editor.putBoolean("isLogined", true);
				//�ύ���浽���ɵ�XML�ļ���
				editor.commit();
				setPrefernAlertDialog.dismiss();
				//Toast.makeText(MainActivity.this, String.valueOf(sp.getBoolean("isLogined", false)), Toast.LENGTH_SHORT).show();
				init();
			}
    		
    	});
    	        cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						
						if(isFirst){
							setPrefernAlertDialog.dismiss();
							MainActivity.this.finish();
						}
						setPrefernAlertDialog.dismiss();
					}
    	        	
    	        });   
    	        
    	        builder.setTitle("�û�ע��");
    	        builder.setView(view);
    	        setPrefernAlertDialog=builder.create();
    	        setPrefernAlertDialog.show();                
    	
    }
    /*�����ǵ�һ�β���,������½�Ի��򣬽��е�½��֤*/
    public void showInputPasswordDialog(){
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	LayoutInflater inflater=LayoutInflater.from(this);
    	View view=inflater.inflate(R.layout.show_password_dialog, null);
    	final EditText et_username=(EditText)view.findViewById(R.id.username);
    	final EditText et_password=(EditText)view.findViewById(R.id.password);
    	Button btn_ok=(Button)view.findViewById(R.id.ok);
    	Button btn_cancel=(Button)view.findViewById(R.id.cancel);
    	
    	btn_ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String name=et_username.getText().toString();
				String password=et_password.getText().toString();
				
				if(name.trim().equals("")||password.trim().equals("")){
					Toast.makeText(MainActivity.this, "�û���������Ϊ��", Toast.LENGTH_LONG).show();
					return;
				}
				
				//����뱣���ļ��е��û����������Ƿ���ͬ
				String savedUsername=sp.getString("name", "");
				String savedPassword=sp.getString("password", "");
				if(name.trim().equals(savedUsername)&&password.trim().equals(savedPassword)){
					  //sp.edit().putBoolean("isLogined", true);
					sp.edit().putBoolean("isLogined", true).commit();
					
					  init();
    		          showPasswordDialog.dismiss();
    	         }
				else{
					Toast.makeText(MainActivity.this, "�û������������", Toast.LENGTH_LONG).show();
					return;
				}
				
			}
    		
    	});
    	     //ȡ��
    	btn_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				showPasswordDialog.dismiss();
				MainActivity.this.finish();
			}
    		
    	});
    	
    	builder.setTitle("�û���½");
    	builder.setView(view);
    	showPasswordDialog=builder.create();
    	showPasswordDialog.show();
    	
    }
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
		}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
	if(sp.getBoolean("isLogined", false)){
		if(item.getTitle().toString().trim().equals("���")) {
			//Log.i("isLogined",String.valueOf(sp.getBoolean("isLogined", false)));
			
				//Toast.makeText(MainActivity.this, String.valueOf(sp.getBoolean("isLogined",false)), Toast.LENGTH_SHORT).show();
			Intent intent=new Intent();
			intent.setClass(MainActivity.this,NewSomebody.class);
			startActivity(intent);
		    }
		else if(item.getTitle().toString().trim().equals("ɾ��")) {
			//����ɾ������
			if(delFlags) {
				delFlags=false;
				item.setIcon(R.drawable.del);
				keyWords="";
				updownDepend=0;
				init();				
			}
			else {
				delFlags=true;
				item.setIcon(R.drawable.del3);
				updownDepend=0;
				init();
			    }
		 }
		else if(item.getTitle().toString().trim().equals("����")) {
		
			//����绰������Ա��Ϣ
              new AlertDialog.Builder(MainActivity.this).setTitle("ϵͳ��ʾ").setMessage("ȷ������绰������?").setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//ȷ��������ContentProvider��ѯ����������
					int getN=getPhoneQueryData();
					//int getN=4;
					new AlertDialog.Builder(MainActivity.this).setMessage("����"+String.valueOf(getN)+"�����ݵ���").setNegativeButton("ȷ��",null).create().show();
					init();
				}
			}).setPositiveButton("ȡ��",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// ȡ���򲻽����κζ���
					
				}
			}).create().show();
		}
		else if(item.getTitle().toString().trim().equals("�˳�")) {
			sp.edit().putBoolean("isLogined", false).commit();
			finish();
			int currentVersion=android.os.Build.VERSION.SDK_INT;
			if(currentVersion>android.os.Build.VERSION_CODES.ECLAIR_MR1){
				Intent startMain=new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				System.exit(0);
			}
			else{
				ActivityManager am=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
				am.restartPackage(getPackageName());
			}
		   }
	    }
	
		return false;
	}
	private int getPhoneQueryData() {
		// TODO �Զ����ɵķ������
		Somebody personInforGet=new Somebody();
		ContentResolver resolver=getContentResolver();
		Cursor cursor=resolver.query(Contacts.CONTENT_URI, null, null, null, null);
		
		//Log.i("cursorCount",String.valueOf(cursor.getCount()));
		DBHelper helper=new DBHelper(MainActivity.this);
		int i=0;
		while(cursor.moveToNext()){
			int idIndex=cursor.getColumnIndex(columns[0]);
			int displayNameIndex=cursor.getColumnIndex(columns[1]);			
			int id=cursor.getInt(idIndex);
			i=i+1;
			personInforGet.setName(cursor.getString(displayNameIndex));   //��ȡ����
			Cursor phone=resolver.query(Phone.CONTENT_URI, null,columns[3]+"="+id, null, null);
			
			while(phone.moveToNext()){
				int phoneNumberIndex=phone.getColumnIndex(columns[2]);
				String phoneNumber=phone.getString(phoneNumberIndex);
				phoneNumber=phoneNumber.replace("-", "");
				phoneNumber=phoneNumber.replace(" ", "");
				personInforGet.setTelephone(phoneNumber);
				//Log.i("name:", personInforGet.getName());
				//Log.i("number:", personInforGet.getTelephone());
				if(!String.valueOf(phoneNumber).equals(null)) {break;}//ֻ��ȡ��һ����Ч�绰����
				//sb.append(displayName+":"+phoneNumber+"\n");
			}
			//����Ĭ������µ�ֵ
			
			
			personInforGet=helper.dataWasher(personInforGet);  //��ʽ������ı���
			//Log.i("name|health",personInforGet.getName()+"|"+String.valueOf(personInforGet.getRelatedPerson()[0][0]));
			//Displayer displayer=new Displayer();
		    //displayer.Somebody(personInforGet);                                 //��ʾĳ�˵���Ϣ��ǰ̨
		    
			helper.insert(personInforGet);
		}		
		cursor.close();
		//return sb.toString();
		return i;
		
	}

}
