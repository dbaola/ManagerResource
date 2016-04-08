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
	private String[][] personsInfo;     //记录查询返回的人员信息
	//以下为控制变量
	private boolean updownFlag=true;   //true为升序,false为降序
	private int updownDepend=0;  //0为按默认显示，显示电话和短信。1按socialClass（官位）排列显示，2按personalWealth（财富）显示，3按educationalStatus（教育状况）显示，4按cohesion（亲密度）显示
	private String keyWords="";     //查询关键字
	//静态二维数组,存放查询的内容
	//[0][x],籍贯；[1][x]，职业；[2][x]，单位；[3][x]，九型人格类型；[4][x]，兴趣爱好；[5][x]关系类型;[6][x]学历；[7][x]官位大小
	private static String[][] searchWords=new String[][] {
		{"籍贯","黑龙江","吉林","辽宁","内蒙","陕西","甘肃","宁夏","新疆","西藏","云南","贵州","四川","山西","河北","河南","山东","湖南","湖北","广东","广西","江苏","浙江","安徽","福建","江西","台湾","海南","北京","天津","上海","重庆"},
		{"职业","军人","教师","商人","公务员","农民"},
		{"单位","518bd","531bd"},
		{"性格","完美型","助人型","成就型","忧郁型","理智型","怀疑型","活跃型","领袖型","平和型"},
		{"爱好","读书","音乐","书法","运动","收藏","赌博","钓鱼"},
		{"关系","老乡","同学","亲属","领导","老师","合伙人","同事"},
		{"教育程度","教授专家","博士","研究生","本科","大专"},
		{"职务级别","军级干部","厅级干部","师级干部","团级干部","处级干部","营级干部","办事人员"},
		{"个人财富","亿万富翁","千万富翁","百万富翁"}};
	
	private SearchWords sw=new SearchWords();
	private int[] path= {R.drawable.man,R.drawable.woman,R.drawable.man1,R.drawable.woman1};
	//private ImageView[] imv_sex;
	
	private int tempi;
	private int personCounts;//总共录入的人数
	
	////登陆相关数据
	private AlertDialog setPrefernAlertDialog;
	private AlertDialog showPasswordDialog;
	private AlertDialog someoneInfoDialog;
	private boolean isFirst;
	private SharedPreferences sp;
	
	///导入电话本数据
	private String[] columns={
			Contacts._ID,
			Contacts.DISPLAY_NAME,
			Phone.NUMBER,
			Phone.CONTACT_ID,};
	//-------------------
	///批量删除标志
	private boolean delFlags=false;       //false不显示删除选择，true显示删除选择
	//--------------------------
	///定义控件
	//private TextView pBirthday; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//NeTranStr trans=new NeTranStr();
		sp=getSharedPreferences("data", 0);
		
	    isFirst=sp.getBoolean("first", true);
	                    //若值已经设置,则登陆;若没有,则注册
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
		
		/* 更改和控制查询条件，得到不同的显示结果
		 * 查询数据库中所有记录的人员名字，并进行显示，返回人员Id号、
		 * 人名、性别、官位、财富、知识、亲密度、电话
		 * 使用一个二维数组personsInfo[][]接收值，并且遍历二维数组值  
		 * */
		//0为按默认显示，显示电话。1按socialClass（官位）排列显示，2按personalWealth（财富）显示，3按educationalStatus（教育状况）显示，4按cohesion（亲密度）显示
	if(sp.getBoolean("isLogined", false)){    //如果确定已经登陆，显示内容
		//对几个控制按键进行判断
		setContentView(R.layout.activity_main);
		/*最下方一排工具栏按钮触发
		*/
		ImageView taskManager=(ImageView)findViewById(R.id.task);//任务管理页面
		//ImageView circlePower=(ImageView)findViewById(R.id.circlePower);//力量管理页面
		//ImageView findRelatives=(ImageView)findViewById(R.id.findRelatives);//寻找关系
		
		taskManager.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 点击进入任务管理页面
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
				// 设置升序还是降序，由布尔变量updownFlag控制
				if(updownFlag) {updownFlag=false;}
				else {updownFlag=true;}
				init();		
				
			}});
		defaultc_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 按官位大小排列显示内容
				
				updownDepend=0;
				init();
				
				
				
			}});
		position_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 按官位大小排列显示内容
				
				updownDepend=1;
				init();
				
				
				
			}});
		wealth_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 按个人财富多少排列显示内容。
				updownDepend=2;
				init();
				
			}});
		knowledge_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 按教育程度排列显示内容
				updownDepend=3;
				init();
				
				
			}});
		cohesion_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//按与我的关系和亲密程度排列
				updownDepend=4;
				init();
				
				
			}});
		
		//显示模块。分支，不同updownDepend、keyWords条件显示不同效果
		DBHelper helper=new DBHelper(this);
	    personsInfo=helper.listPersons(updownDepend,updownFlag);		
		TableLayout tbLayout2=(TableLayout)findViewById(R.id.tableL2);
		if(String.valueOf(keyWords.trim()).equals("")){	
		switch(updownDepend) {//0为按默认显示，显示电话。1按socialClass（官位）排列显示，2按personalWealth（财富）显示，3按educationalStatus（教育状况）显示，4按cohesion（亲密度）显示
	    case 1:
		{
			//Toast.makeText(this, "官位", Toast.LENGTH_SHORT).show();
		  position_bt.setTextColor(Color.RED);
		  for(int i=0;i<personsInfo.length;i++) {
			     personCounts=i;                                   //记录录入的人数
				 if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {break;}
				 //显示块
				 //Log.i("name",personNames[i]);
				 TableRow tbRow=new TableRow(MainActivity.this);
				 tbRow.setPadding(0,0 ,0 , 40);
				 
				 tbRow.setId(i);
				 tbLayout2.addView(tbRow);
						
				 ImageView imv_sex = new ImageView(this);
				 imv_sex.setImageResource(path[0]);
						//Log.i("sex",personsInfo[i][1]);
						
				 if(String.valueOf(personsInfo[i][2]).trim().equals("男")) {
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
				 case 1:{tvPosition.setText("副军|正厅以上");break;}
				 case 2:{tvPosition.setText("正师|副厅");break;}
				 case 3:{tvPosition.setText("副师|副厅");break;}
				 case 4:{tvPosition.setText("正团|正处");break;}
				 case 5:{tvPosition.setText("副团|副处");break;}
				 case 6:{tvPosition.setText("正营|正科");break;}
				 case 7:{tvPosition.setText("副营|副科");break;}
				 case 8:{tvPosition.setText("正连|科员以下");break;}
				 default:{tvPosition.setText("正连|科员以下");}
				 
				 }
				 
				 tbRow.addView(tvPosition);
						
				 tv.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// 通过intent弹出displaySomebody Activity。传递参数为name和somebodyid。
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
					//显示人员人数
				 TextView tvCounts=(TextView)findViewById(R.id.personCounts);
				 tvCounts.setText("共计"+personCounts+"人");
					  		  
					  
					  //自动提示搜索的内容，默认情况显示”人名|电话号码“
				 String[] pNameAndPhone=new String[personCounts];
				 for(int i=0;i<personCounts;i++){
						 pNameAndPhone[i]=personsInfo[i][1];         //将所有人名信息
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
			//Toast.makeText(this, "财富", Toast.LENGTH_SHORT).show();
			
			for(int i=0;i<personsInfo.length;i++) {
				
			                                        //记录录入的人数
				 if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {personCounts=i;break;}
				 //显示块
				 //Log.i("name",personNames[i]);
				 TableRow tbRow=new TableRow(MainActivity.this);
				 tbRow.setPadding(0,0 ,0 , 40);
				 
				 tbRow.setId(i);
				 tbLayout2.addView(tbRow);
						
				 ImageView imv_sex = new ImageView(this);
				 imv_sex.setImageResource(path[0]);
						//Log.i("sex",personsInfo[i][1]);
						
				 if(String.valueOf(personsInfo[i][2]).trim().equals("男")) {
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
								// 通过intent弹出displaySomebody Activity。传递参数为name和somebodyid。
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
					//显示人员人数
				 TextView tvCounts=(TextView)findViewById(R.id.personCounts);
				 tvCounts.setText("共计"+personCounts+"人");
					  		  
					  
					  //自动提示搜索的内容，默认情况显示”人名|电话号码“
				 String[] pNameAndPhone=new String[personCounts];
				 for(int i=0;i<personCounts;i++){
						 pNameAndPhone[i]=personsInfo[i][1];         //将所有人名信息
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
			//Toast.makeText(this, "知识", Toast.LENGTH_SHORT).show();
			for(int i=0;i<personsInfo.length;i++) {
				
                //记录录入的人数
                if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {personCounts=i;break;}
                //显示块
                //Log.i("name",personNames[i]);
                TableRow tbRow=new TableRow(MainActivity.this);
                tbRow.setPadding(0,0 ,0 , 40);

                tbRow.setId(i);
                tbLayout2.addView(tbRow);

                ImageView imv_sex = new ImageView(this);
                imv_sex.setImageResource(path[0]);
                //Log.i("sex",personsInfo[i][1]);

                if(String.valueOf(personsInfo[i][2]).trim().equals("男")) {
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
                case 1:{tvKnowledge.setText("教授专家");break;}
                case 2:{tvKnowledge.setText("博士");break;}
                case 3:{tvKnowledge.setText("研究生");break;}
                case 4:{tvKnowledge.setText("本科");break;}
                case 5:{tvKnowledge.setText("大专");break;}
                case 6:{tvKnowledge.setText("其它");break;}
                default:{tvKnowledge.setText("其它");}

}
//tvWealth.setText(personsInfo[i][3]);
                tbRow.addView(tvKnowledge);

                tv.setOnClickListener(new OnClickListener(){

@Override
                public void onClick(View v) {
// 通过intent弹出displaySomebody Activity。传递参数为name和somebodyid。
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
//显示人员人数
               TextView tvCounts=(TextView)findViewById(R.id.personCounts);
               tvCounts.setText("共计"+personCounts+"人");


//自动提示搜索的内容，默认情况显示”人名|电话号码“
               String[] pNameAndPhone=new String[personCounts];
               for(int i=0;i<personCounts;i++){
               pNameAndPhone[i]=personsInfo[i][1];         //将所有人名信息
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
			//Toast.makeText(this, "关系", Toast.LENGTH_SHORT).show();
            for(int i=0;i<personsInfo.length;i++) {
				
                //记录录入的人数
                if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {personCounts=i;break;}
                //显示块
                //Log.i("name",personNames[i]);
                TableRow tbRow=new TableRow(MainActivity.this);
                tbRow.setPadding(0,0 ,0 , 40);

                tbRow.setId(i);
                tbLayout2.addView(tbRow);

                ImageView imv_sex = new ImageView(this);
                imv_sex.setImageResource(path[0]);
                //Log.i("sex",personsInfo[i][1]);

                if(String.valueOf(personsInfo[i][2]).trim().equals("男")) {
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
// 通过intent弹出displaySomebody Activity。传递参数为name和somebodyid。
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
//显示人员人数
               TextView tvCounts=(TextView)findViewById(R.id.personCounts);
               tvCounts.setText("共计"+personCounts+"人");


//自动提示搜索的内容，默认情况显示”人名|电话号码“
               String[] pNameAndPhone=new String[personCounts];
               for(int i=0;i<personCounts;i++){
               pNameAndPhone[i]=personsInfo[i][1];         //将所有人名信息
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
				personCounts=i;                                   //记录录入的人数
				//Log.i("personsInfo[i][1]",personsInfo[i][1]);
				if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {break;}
				//if(!String.valueOf(keyWords).trim().equals("null")&&(!personsInfo[i][1].trim().equals(keyWords))){return;}
				//if(!String.valueOf(keyWords).trim().equals("关键字")&&String.valueOf(keyWords).trim().equals(personsInfo[i][1])){Log.i("keyWords",String.valueOf(keyWords));return;}
				//显示块
				//Log.i("name",personNames[i]);
				TableRow tbRow=new TableRow(MainActivity.this);
				tbRow.setPadding(0,0 ,0 , 10);
				tbRow.setId(i);
				tbLayout2.addView(tbRow);
				
				ImageView imv_sex = new ImageView(this);
				imv_sex.setImageResource(path[0]);
				//Log.i("sex",personsInfo[i][1]);
				
				if(String.valueOf(personsInfo[i][2]).trim().equals("男")) {
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
				//如果要求批量删除
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
						// 单击删除按钮进行删除
						int j=v.getId();
						//删除显示表内容
						
						/*
						TextView tv=(TextView)findViewById(j);
						TableRow tbRow=(TableRow)findViewById(j);
						tbRow.removeView(tv);*/
						//删除数据
						
						ImageView delPs=(ImageView)v.findViewById(j);
						delPs.setImageResource(R.drawable.del0);
						int personId=Integer.valueOf(personsInfo[j][0]);
						String personName=personsInfo[j][1];
						DBHelper helper=new DBHelper(MainActivity.this);
						int flag=helper.delSomebody(personId, personName);
						if(flag>0) {
							Toast.makeText(MainActivity.this, personName+"数据删除", Toast.LENGTH_SHORT).show();
							//init();
						}
					}});
				}
				
				//init()内置触发控制块
				
				//按该某人则弹出displaySomebody显示框
				
				tv.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 通过intent弹出displaySomebody Activity。传递参数为name和somebodyid。
						
						
						tempi=Integer.valueOf(v.getId());					    	
				    	popSomeoneDialog(tempi);      //弹出显示对话框
					}

					});
				//点电话图标拨打电话
				ivPhone.setOnTouchListener(new OnTouchListener(){

					@Override
					public boolean onTouch(View v, MotionEvent arg1) {
						// 点击拨打电话
						
						//Log.i("tag","telphone"+personsInfo[v.getId()][3]);
						String telNo=personsInfo[v.getId()][3];
						if((telNo!=null)&&(!"".equals(telNo.trim()))){
					Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telNo));
					startActivity(intent);
					return true;
				}
						else {
							Toast.makeText(MainActivity.this, "没有保存电话号码！", Toast.LENGTH_SHORT).show();
							return false;	
						}
						
					}});
				//点短信图标发送短信
				ivSms.setOnTouchListener(new OnTouchListener(){

					@Override
					public boolean onTouch(View v, MotionEvent arg1) {
						// 发送短信
						//Log.i("tag","Sms"+personsInfo[v.getId()][3]);
						//弹出对话框，编写短信内容
						final EditText smsContent=new EditText(MainActivity.this);
						final String telNo1=personsInfo[v.getId()][3];
						if(telNo1!=null&&(!"".equals(telNo1.trim()))){
							//new AlertDialog.Builder(MainActivity.this).setTitle("编写短信内容").setView(smsContent).setPositiveButton("确定",null).create().show();
							//Log.i("smsContent:",smsContent.getText().toString());
							Intent intent=new Intent();
							intent.setAction(Intent.ACTION_SENDTO);
							intent.setData(Uri.parse("smsto:"+telNo1));
							intent.putExtra("sms_body", smsContent.getText().toString());
							startActivity(intent);
							return true;
							
						}
						else {
							Toast.makeText(MainActivity.this, "没有保存电话号码！", Toast.LENGTH_SHORT).show();
							return false;	
						}
					}});
				}
			//显示人员人数
			  TextView tvCounts=(TextView)findViewById(R.id.personCounts);
			  tvCounts.setText("共计"+personCounts+"人");
			  
			  			  
			  
			  //自动提示搜索的内容，默认情况显示”人名|电话号码“
			 
			  
			 String[] pNameAndPhone=new String[personCounts];
			 for(int i=0;i<personCounts;i++){
				 pNameAndPhone[i]=personsInfo[i][1];         //将所有人名信息
				//Log.i("name:",pNameAndPhone[i]);
			 }
			
			  
			  //Log.i("content change",keyWords);
			 sw.strArr2to1(searchWords);//对特殊词的搜索分类
			 
			 //将人名和特殊字符合起来生成新字符
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
		else{//如果输入了keyWords情况，有两种情况，1、keyWords是人名；2、keyWords属于特殊词（ 在二维数组sWords[][]中）
			//先判断是否在sw中
			
			int n=0,nTemp=sw.getArrLenth()[n];//className[n]中的值
			for(int i=0;i<sw.getStringArr1().length;i++) {
				
			if(i>=nTemp) {n=n+1;nTemp=nTemp+sw.getArrLenth()[n];}	
			if(sw.getStringArr1()[i].contains(keyWords)) {
				//Log.i("className",sw.getClassName()[n]);
			//根据类别寻找表进行查询，返回ssPersons[][]信息，主要返回姓名和Id号以及根据keyWords返回的信息
			//根据i的值定类名	
				DBHelper sshelper=new DBHelper(this);
			    final String[][] ssPersons=sshelper.searchSpecialPersons(keyWords,sw.getClassName()[n]);
			    
			    if(ssPersons!=null){
			    	//如果存在搜索的内容,显示用户
			    	TableRow tbRow0=new TableRow(MainActivity.this);
					tbRow0.setPadding(0,0 ,0 , 10);
					
					TextView tvDisp=(TextView)findViewById(R.id.personCounts);
					tvDisp.setText("查询"+sw.getClassName()[n]+":"+keyWords);
					
				    
				    //显示ssPersons内容
				    for(int dN=0;dN<ssPersons.length;dN++) {
				    
					
				    TableRow tbRow=new TableRow(MainActivity.this);
					tbRow.setPadding(0,0 ,0 , 10);
					tbRow.setId(dN);
					tbLayout2.addView(tbRow);
					
					ImageView imv_sex = new ImageView(this);
					imv_sex.setImageResource(path[0]);
					//Log.i("sex",personsInfo[i][1]);
					
					if(String.valueOf(ssPersons[dN][2]).trim().equals("男")) {
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
							// 通过intent弹出displaySomebody Activity。传递参数为name和somebodyid。
							
							
							tempi=Integer.valueOf(v.getId());					    	
					    	popSomeoneDialog(tempi);      //弹出显示对话框
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
								// 单击删除按钮进行删除
								int j=v.getId();
								ImageView delPs=(ImageView)v.findViewById(j);
								delPs.setImageResource(R.drawable.del0);
								int personId=Integer.valueOf(ssPersons[j][0]);
								String personName=ssPersons[j][1];
								DBHelper helper=new DBHelper(MainActivity.this);
								int flag=helper.delSomebody(personId, personName);
								if(flag>0) {
									Toast.makeText(MainActivity.this, personName+"数据删除", Toast.LENGTH_SHORT).show();
									//init();
								}
							}});
					}

				    }
					
			    }
			    else{
			    	Toast.makeText(this, "没有您搜索的内容", Toast.LENGTH_SHORT).show();
			    }
			    
			   
			}
		   }
			for(int i=0;i<personsInfo.length;i++) {
				if(String.valueOf(personsInfo[i][1]).trim().contains(keyWords)){
					
					if(String.valueOf(personsInfo[i][0]).trim().equals("null")) {break;}
					//if(!String.valueOf(keyWords).trim().equals("null")&&(!personsInfo[i][1].trim().equals(keyWords))){return;}
					//if(!String.valueOf(keyWords).trim().equals("关键字")&&String.valueOf(keyWords).trim().equals(personsInfo[i][1])){Log.i("keyWords",String.valueOf(keyWords));return;}
					//显示块
					//Log.i("name",personNames[i]);
					TableRow tbRow=new TableRow(MainActivity.this);
					tbRow.setPadding(0,0,0, 10);
					tbRow.setId(i);
					tbLayout2.addView(tbRow);
					
					ImageView imv_sex = new ImageView(this);
					imv_sex.setImageResource(path[0]);
					//Log.i("sex",personsInfo[i][1]);
					
					if(String.valueOf(personsInfo[i][2]).trim().equals("男")) {
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
								// 通过intent弹出displaySomebody Activity。传递参数为name和somebodyid。
								
								
								tempi=Integer.valueOf(v.getId());					    	
						    	popSomeoneDialog(tempi);      //弹出显示对话框
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
								// 单击删除按钮进行删除
								int j=v.getId();
								ImageView delPs=(ImageView)v.findViewById(j);
								delPs.setImageResource(R.drawable.del0);
								int personId=Integer.valueOf(personsInfo[j][0]);
								String personName=personsInfo[j][1];
								DBHelper helper=new DBHelper(MainActivity.this);
								int flag=helper.delSomebody(personId, personName);
								if(flag>0) {
									Toast.makeText(MainActivity.this, personName+"数据删除", Toast.LENGTH_SHORT).show();
									//init();
								}
							}});
					}
				}
			}
			 String[] pNameAndPhone=new String[personCounts];
			 for(int k=0;k<personCounts;k++){
				 pNameAndPhone[k]=personsInfo[k][1];         //将所有人名信息
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
		 //自动提示搜索的内容，默认情况显示”人名|电话号码“
		 
		  
		 String[] pNameAndPhone=new String[personCounts];
		 for(int i=0;i<personCounts;i++){
			 pNameAndPhone[i]=personsInfo[i][1];         //将所有人名信息
			//Log.i("name:",pNameAndPhone[i]);
		 }
		
		  
		  //Log.i("content change",keyWords);
		 sw.strArr2to1(searchWords);//对特殊词的搜索分类
		 
		 //将人名和特殊字符合起来生成新字符
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
		// TODO 自动生成的方法存根
		//Log.i("name|id",personsInfo[tempi2][1]+personsInfo[tempi2][0]);
		
////////方法：给出人名和id值，使用弹出对话框方式显示信息内容
						AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
				    	LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
				    	View view=inflater.inflate(R.layout.display_somebody, null);
						 
				    	//设置xml显示的信息
				    	DBHelper helper=new DBHelper(MainActivity.this);
				    	 Somebody someone=new Somebody();
				    	
				    	//Log.i("name",personName);
					    String personName=String.valueOf(personsInfo[tempi][1]);
					    int personId=Integer.valueOf(personsInfo[tempi][0]);
				    	someone=helper.query(personName,personId);
				    	
				    	//ImageView personIv=(ImageView)view.findViewById(R.id.person_iv);
				    			    	
				    	if(String.valueOf(someone.getSex()).trim().equals("男")) {
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
					    case 1:{pCharacterType.setText("完美型");break;}
					    case 2:{pCharacterType.setText("助人型");break;}
					    case 3:{pCharacterType.setText("成就型");break;}
					    case 4:{pCharacterType.setText("忧郁型");break;}
					    case 5:{pCharacterType.setText("理智型");break;}
					    case 6:{pCharacterType.setText("怀疑型");break;}
					    case 7:{pCharacterType.setText("活跃型");break;}
					    case 8:{pCharacterType.setText("领袖型");break;}
					    case 9:{pCharacterType.setText("平和型");break;}
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
					    case 1:{pPersonalWealth.setText("大于1000W");break;}
					    case 2:{pPersonalWealth.setText("100-1000W");break;}
					    case 3:{pPersonalWealth.setText("50-100W");break;}
					    case 4:{pPersonalWealth.setText("10-50W");break;}
					    case 5:{pPersonalWealth.setText("<10W");break;}
					    default:{pPersonalWealth.setText(" ");}
					    }
					    TextView pSocialClass=(TextView)view.findViewById(R.id.socialClass);
					    switch(someone.getSocialClass()){
					    case 1:{pSocialClass.setText("副军|正厅以上");break;}
					    case 2:{pSocialClass.setText("正师|副厅");break;}
					    case 3:{pSocialClass.setText("副师|副厅");break;}
					    case 4:{pSocialClass.setText("正团|正处");break;}
					    case 5:{pSocialClass.setText("副团|副处");break;}
					    case 6:{pSocialClass.setText("正营|正科");break;}
					    case 7:{pSocialClass.setText("副营|副科");break;}
					    case 8:{pSocialClass.setText("正连|科员以下");break;}
					    default:{pSocialClass.setText(" ");}
					    }
					    
					    TextView pEducationalStatus=(TextView)view.findViewById(R.id.educationalStatus);
					    switch(someone.getEducationalStatus()){
					    case 1:{pEducationalStatus.setText("教授专家");break;}
					    case 2:{pEducationalStatus.setText("博士");break;}
					    case 3:{pEducationalStatus.setText("研究生");break;}
					    case 4:{pEducationalStatus.setText("本科");break;}
					    case 5:{pEducationalStatus.setText("大专");break;}
					    case 6:{pEducationalStatus.setText("其它");break;}
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
					    
				    	builder.setTitle(String.valueOf(someone.getName())+"的信息");
			    	    builder.setView(view);
			    	    someoneInfoDialog=builder.create();
			    	    someoneInfoDialog.show();
			    	    
			    	    bt_edit.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								//点击，关闭显示框，跳入编辑Activity
								someoneInfoDialog.dismiss();
								//跳转到display activity进行显示内容
								
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
							//退出，即退出对话框
							someoneInfoDialog.dismiss();
							
						}});
		
	}



	private String DataAdd(String[] elements) {
		// TODO 自动生成的方法存根
		String result = "";
		if(elements[0].toString().trim().equals("")) {
			//Log.i("elements[0]",elements[0]);
			result=" ";
		}
		else {		
		for(int i=0;i<elements.length;i++) {
			result+=String.valueOf(elements[i])+"、";
		}
		}
		//for(int i=0;i<elements.length;i++)
		//Log.i("lenth",String.valueOf(elements[i]));
		return result;
	}


	/*第一次使用,弹出注册对话框,创建用户密码*/
    private void showSetPasswordDialog()
    {
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	LayoutInflater inflater=LayoutInflater.from(this);
    	View view=inflater.inflate(R.layout.show_password_dialog, null);
    	final EditText username=(EditText)view.findViewById(R.id.username);
    	final EditText userpassword=(EditText)view.findViewById(R.id.password);
    	Button ok=(Button)view.findViewById(R.id.ok);
    	Button cancel=(Button)view.findViewById(R.id.cancel);
    	           //确认注册键按下
    	ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String name=username.getText().toString();
				String password=userpassword.getText().toString();
				//用户密码判断
				if(name.trim().equals("")||password.trim().equals("")){
					Toast.makeText(MainActivity.this, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
					return;
				}
				//sharedpreference操作,键值保存到data文件
				Editor editor=sp.edit();
				editor.putString("name", name);
				editor.putString("password", password);
				editor.putBoolean("first", false);
				editor.putBoolean("isLogined", true);
				//提交保存到生成的XML文件中
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
    	        
    	        builder.setTitle("用户注册");
    	        builder.setView(view);
    	        setPrefernAlertDialog=builder.create();
    	        setPrefernAlertDialog.show();                
    	
    }
    /*当不是第一次操作,弹出登陆对话框，进行登陆认证*/
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
					Toast.makeText(MainActivity.this, "用户名和密码为空", Toast.LENGTH_LONG).show();
					return;
				}
				
				//检查与保存文件中的用户名和密码是否相同
				String savedUsername=sp.getString("name", "");
				String savedPassword=sp.getString("password", "");
				if(name.trim().equals(savedUsername)&&password.trim().equals(savedPassword)){
					  //sp.edit().putBoolean("isLogined", true);
					sp.edit().putBoolean("isLogined", true).commit();
					
					  init();
    		          showPasswordDialog.dismiss();
    	         }
				else{
					Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
					return;
				}
				
			}
    		
    	});
    	     //取消
    	btn_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				showPasswordDialog.dismiss();
				MainActivity.this.finish();
			}
    		
    	});
    	
    	builder.setTitle("用户登陆");
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
		if(item.getTitle().toString().trim().equals("添加")) {
			//Log.i("isLogined",String.valueOf(sp.getBoolean("isLogined", false)));
			
				//Toast.makeText(MainActivity.this, String.valueOf(sp.getBoolean("isLogined",false)), Toast.LENGTH_SHORT).show();
			Intent intent=new Intent();
			intent.setClass(MainActivity.this,NewSomebody.class);
			startActivity(intent);
		    }
		else if(item.getTitle().toString().trim().equals("删除")) {
			//批量删除数据
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
		else if(item.getTitle().toString().trim().equals("导入")) {
		
			//导入电话本中人员信息
              new AlertDialog.Builder(MainActivity.this).setTitle("系统提示").setMessage("确定导入电话簿数据?").setNegativeButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//确认则利用ContentProvider查询并导入数据
					int getN=getPhoneQueryData();
					//int getN=4;
					new AlertDialog.Builder(MainActivity.this).setMessage("共有"+String.valueOf(getN)+"条数据导入").setNegativeButton("确定",null).create().show();
					init();
				}
			}).setPositiveButton("取消",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// 取消则不进行任何动作
					
				}
			}).create().show();
		}
		else if(item.getTitle().toString().trim().equals("退出")) {
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
		// TODO 自动生成的方法存根
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
			personInforGet.setName(cursor.getString(displayNameIndex));   //获取姓名
			Cursor phone=resolver.query(Phone.CONTENT_URI, null,columns[3]+"="+id, null, null);
			
			while(phone.moveToNext()){
				int phoneNumberIndex=phone.getColumnIndex(columns[2]);
				String phoneNumber=phone.getString(phoneNumberIndex);
				phoneNumber=phoneNumber.replace("-", "");
				phoneNumber=phoneNumber.replace(" ", "");
				personInforGet.setTelephone(phoneNumber);
				//Log.i("name:", personInforGet.getName());
				//Log.i("number:", personInforGet.getTelephone());
				if(!String.valueOf(phoneNumber).equals(null)) {break;}//只获取第一条有效电话号码
				//sb.append(displayName+":"+phoneNumber+"\n");
			}
			//设置默认情况下的值
			
			
			personInforGet=helper.dataWasher(personInforGet);  //格式化插入的变量
			//Log.i("name|health",personInforGet.getName()+"|"+String.valueOf(personInforGet.getRelatedPerson()[0][0]));
			//Displayer displayer=new Displayer();
		    //displayer.Somebody(personInforGet);                                 //显示某人的信息到前台
		    
			helper.insert(personInforGet);
		}		
		cursor.close();
		//return sb.toString();
		return i;
		
	}

}
