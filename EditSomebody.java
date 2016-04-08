package com.ex.somebody;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EditSomebody extends Activity{
	//////////数据组定义//////////////////////////
	private Somebody person;
	/////以下实际为person.getRelatedPerson()[][]={relatedPersonType[],relatedPersonName[],relatedPersonId[],rPersonId[]}
	private EditText[] relatedPersonType=new EditText[10],relatedPersonName=new EditText[10];
	//private EditText[] relatedPersonId=new EditText[10];
	//private TextView[] rTableId=new TextView[10];
	private int relatedPersonNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_somebody);
		
	    Intent intent=getIntent();
	    
	    //test.setText(intent.getStringExtra("name"));
	    //查询name的值,显示第一个满足的条件
	    
	    String personName=intent.getStringExtra("name").toString().trim();
	    
	    int personId=Integer.valueOf(intent.getIntExtra("personId",-1));
	    if(personId==-1) {
	    	Toast.makeText(this, "人员ID错误", Toast.LENGTH_SHORT).show();
	    }
	    else
	    {
	    //Log.i("id",String.valueOf(personId)+personName);
	    DBHelper helper=new DBHelper(this);
	    person=helper.query(personName,personId);
	    
	    ///////////////////////////////完成初值赋予///////////////////////////////
	    EditText name=(EditText)findViewById(R.id.name);	    
	    name.setText(person.getName());
	    RadioGroup sex=(RadioGroup)findViewById(R.id.sex);
	    for(int i=0;i<sex.getChildCount();i++){
	    	RadioButton r=(RadioButton)sex.getChildAt(i);
	    	if(r.getText().equals(person.getSex())){
	    		r.setChecked(true);  //获得单选内容
	    		break;
	    	}
	    }
	    EditText birthday=(EditText)findViewById(R.id.birthday);
	    birthday.setText(person.getBirthday());
	    EditText nativePlace=(EditText)findViewById(R.id.nativePlace);
	    nativePlace.setText(person.getNativePlace());
	    EditText nickName=(EditText)findViewById(R.id.nickName);
	    nickName.setText(person.getNickName());
	    EditText telephone=(EditText)findViewById(R.id.telephone);
	    telephone.setText(person.getTelephone());
	    EditText email=(EditText)findViewById(R.id.email);
	    email.setText(person.getEmail());
	    EditText qq=(EditText)findViewById(R.id.qq);
	    qq.setText(person.getQq());
	    EditText weibo=(EditText)findViewById(R.id.weibo);
	    weibo.setText(person.getWeibo());
	    EditText boke=(EditText)findViewById(R.id.boke);
	    boke.setText(person.getBoke());
	    EditText wechat=(EditText)findViewById(R.id.wechat);
	    wechat.setText(person.getWechat());
	    EditText address=(EditText)findViewById(R.id.address);
	    address.setText(person.getAddress());
	    EditText needs=(EditText)findViewById(R.id.needs);
	    needs.setText(DataAdd(person.getNeeds()));
	    EditText height=(EditText)findViewById(R.id.height);
	    height.setText(String.valueOf(person.getHeight()));
	    EditText weight=(EditText)findViewById(R.id.weight);
	    weight.setText(String.valueOf(person.getWeight()));
	    final EditText bloodType=(EditText)findViewById(R.id.bloodType);
	    bloodType.setText(person.getBloodType());
	    EditText nationality=(EditText)findViewById(R.id.nationality);
	    nationality.setText(person.getNationality());
	    final EditText party=(EditText)findViewById(R.id.party);
	    party.setText(person.getParty());
	    RadioGroup married=(RadioGroup)findViewById(R.id.married);
	    for(int i=0;i<married.getChildCount();i++){
	    	RadioButton r=(RadioButton)married.getChildAt(i);
	    	if(r.getText().equals(person.isMarried())){
	    		r.setChecked(true);  //获得单选内容
	    		break;
	    	}
	    }
	    RadioGroup healthCondition=(RadioGroup)findViewById(R.id.healthCondition);
	    for(int i=0;i<married.getChildCount();i++){
	    	RadioButton r=(RadioButton)healthCondition.getChildAt(i);
	    	if(r.getText().equals(person.isHealthCondition())){
	    		r.setChecked(true);  //获得单选内容
	    		break;
	    	}
	    }
	    final EditText occupation=(EditText)findViewById(R.id.occupation);
	    occupation.setText(person.getOccupation());
	    EditText workUnits=(EditText)findViewById(R.id.workUnits);
	    workUnits.setText(person.getWorkUnits());
	    EditText workTitle=(EditText)findViewById(R.id.workTitle);
	    workTitle.setText(person.getWorkTitle());
	    final EditText income=(EditText)findViewById(R.id.income);
	    income.setText(person.getIncome());
	    final EditText characterType=(EditText)findViewById(R.id.characterType);
	    switch(person.getCharacterType()){
	    case 1:{characterType.setText("完美型");break;}
	    case 2:{characterType.setText("助人型");break;}
	    case 3:{characterType.setText("成就型");break;}
	    case 4:{characterType.setText("忧郁型");break;}
	    case 5:{characterType.setText("理智型");break;}
	    case 6:{characterType.setText("怀疑型");break;}
	    case 7:{characterType.setText("活跃型");break;}
	    case 8:{characterType.setText("领袖型");break;}
	    case 9:{characterType.setText("平和型");break;}
	    }
	    final EditText hobby=(EditText)findViewById(R.id.hobby);
	    hobby.setText(DataAdd(person.getHobby()));
	    EditText strongPoint=(EditText)findViewById(R.id.strongPoint);
	    strongPoint.setText(DataAdd(person.getStrongPoint()));
	    EditText weakPoint=(EditText)findViewById(R.id.weakPoint);
	    weakPoint.setText(DataAdd(person.getWeakPoint()));
	    final EditText personalWealth=(EditText)findViewById(R.id.personalWealth);
	    switch(person.getPersonalWealth()){
	    case 1:{personalWealth.setText("大于1000W");break;}
	    case 2:{personalWealth.setText("100-1000W");break;}
	    case 3:{personalWealth.setText("50-100W");break;}
	    case 4:{personalWealth.setText("10-50W");break;}
	    case 5:{personalWealth.setText("<10W");break;}
	    }
	    final EditText socialClass=(EditText)findViewById(R.id.socialClass);
	    switch(person.getSocialClass()){
	    case 1:{socialClass.setText("副军|正厅以上");break;}
	    case 2:{socialClass.setText("正师|副厅");break;}
	    case 3:{socialClass.setText("副师|副厅");break;}
	    case 4:{socialClass.setText("正团|正处");break;}
	    case 5:{socialClass.setText("副团|副处");break;}
	    case 6:{socialClass.setText("正营|正科");break;}
	    case 7:{socialClass.setText("副营|副科");break;}
	    case 8:{socialClass.setText("正连|科员以下");break;}
	    default:{socialClass.setText("正连|科员以下");break;}
	    }
	    final EditText educationalStatus=(EditText)findViewById(R.id.educationalStatus);
	    switch(person.getEducationalStatus()){
	    case 1:{educationalStatus.setText("教授专家");break;}
	    case 2:{educationalStatus.setText("博士");break;}
	    case 3:{educationalStatus.setText("研究生");break;}
	    case 4:{educationalStatus.setText("本科");break;}
	    case 5:{educationalStatus.setText("大专");break;}
	    case 6:{educationalStatus.setText("其它");break;}
	    default:{educationalStatus.setText("其它");break;} 
	    }
	    EditText cohesion=(EditText)findViewById(R.id.cohesion);
	    cohesion.setText(String.valueOf(person.getCohesion()));
	    final EditText relationship=(EditText)findViewById(R.id.relationship);
	    relationship.setText(person.getRelationship());
	    
	    TableLayout tbLayout1=(TableLayout)findViewById(R.id.tableL1);
	    //Log.i("type0",String.valueOf(person.getRelatedPerson()[3][0]));
	   
	    for(int i=0;i<10;i++){
	    	
	    	if(String.valueOf(person.getRelatedPerson()[i][1]).trim().equals("null")||String.valueOf(person.getRelatedPerson()[i][2]).trim().equals("null")){
	    		relatedPersonNum=i;
	    		//Log.i("i value:",String.valueOf(i));
	    		break;
	    		}
	    	TableRow tbRow=new TableRow(this);
	    	//rTableId[i]=new TextView(this);        //存储表中的id值，未启用
	    	//relatedPersonId[i]=new EditText(this);
		    relatedPersonType[i]=new EditText(this);
		    relatedPersonName[i]=new EditText(this);
		    //tbRow.setGravity(Gravity.LEFT);
	    	tbLayout1.addView(tbRow);
	    	relatedPersonType[i].setId(i);
	    	//relatedPersonId[i].setText(String.valueOf(person.getRelatedPerson()[i][4]));  //可以修改关联人id号，默认值为0
	    	//relatedPersonId[i].setGravity(Gravity.LEFT);
	    	//relatedPersonId[i].setWidth(5);
	    	relatedPersonType[i].setText(person.getRelatedPerson()[i][1]);
	    	relatedPersonName[i].setText(person.getRelatedPerson()[i][2]);
	    	relatedPersonType[i].setTextSize(14);
	    	relatedPersonName[i].setTextSize(14);
	    	relatedPersonType[i].setGravity(1);
	    	relatedPersonName[i].setGravity(1);
	    	//rTableId[i].setText(String.valueOf(person.getRelatedPerson()[i][0]));
	    	//rTableId[i].setVisibility(View.INVISIBLE);
	    	//tbRow.addView(rTableId[i]);
	    	//tbRow.addView(relatedPersonId[i]);	    	
	    	tbRow.addView(relatedPersonType[i]);
	    	tbRow.addView(relatedPersonName[i]);
	    	
	    	relatedPersonType[i].setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// 编辑时弹出提示框可以供选择
					
					final String[] items= {"直系亲属","旁系亲属","同事","领导","合伙人","同学","老师","老乡","朋友","其它"};
					final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
					final int selectId=Integer.valueOf(v.getId());
					new AlertDialog.Builder(EditSomebody.this).setTitle("与他|她的关系").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// 以"|"分割，将组合的result设置到EditText中
							String result ="";
							int j=0;
							for(int i=0;i<checkedItems.length;i++){
								
								if(checkedItems[i]){
									j++;            //已选择的计数
									if(j>1)
									{   
										result+="、"+items[i];
										
										}
									else {
										result+=items[i];
									}
									
								}
							}
							//Log.i("result:",result);
							//Log.i("personnum----1111",String.valueOf(relatedPersonNum-1));
							relatedPersonType[selectId].setText(String.valueOf(result));	
						}
					}).create().show();
					
				}});
	    	
	    }
	    TextView button_relatedPerson_id=(TextView)findViewById(R.id.button_relatedPerson_id);
		
		
		button_relatedPerson_id.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				relatedPersonType[relatedPersonNum]=new EditText(EditSomebody.this);
				relatedPersonName[relatedPersonNum]=new EditText(EditSomebody.this);
				//relatedPersonId[relatedPersonNum]=new EditText(EditSomebody.this);
				
				if(relatedPersonNum>9){
					return;}//超过设定的数值后，点无效;上一项人名和类型没有填写，不能增加。
				int temp=relatedPersonNum-1;
				if(temp>=0&&(relatedPersonType[temp].getText().toString().equals("")||relatedPersonName[temp].getText().toString().equals(""))){
					Toast.makeText(EditSomebody.this, "关系或人名为空", Toast.LENGTH_SHORT).show();
					return;
				}
				relatedPersonType[relatedPersonNum].setTextSize(14);
				relatedPersonType[relatedPersonNum].setGravity(1);
				relatedPersonName[relatedPersonNum].setTextSize(14);
				relatedPersonName[relatedPersonNum].setGravity(1);
				//relatedPersonId[relatedPersonNum].setTextSize(14);
				//relatedPersonId[relatedPersonNum].setGravity(1);
				TableLayout tbLayout1=(TableLayout)findViewById(R.id.tableL1);
				TableRow tableR=new TableRow(EditSomebody.this);
				tbLayout1.addView(tableR);
				//tableR.addView(relatedPersonId[relatedPersonNum]);
				tableR.addView(relatedPersonType[relatedPersonNum]);						
				tableR.addView(relatedPersonName[relatedPersonNum]);
				relatedPersonType[relatedPersonNum].setId(relatedPersonNum);
				relatedPersonNum=relatedPersonNum+1;
				
				//Log.i("personnum---0000",String.valueOf(relatedPersonNum));
				
				relatedPersonType[relatedPersonNum-1].setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"直系亲属","旁系亲属","同事","领导","合伙人","同学","老师","老乡","朋友","其它"};
						final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
						final int selectId=Integer.valueOf(v.getId());
						new AlertDialog.Builder(EditSomebody.this).setTitle("与他|她的关系").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which, boolean isChecked) {
								// 以"、"分割，将组合的result设置到EditText中
								String result ="";
								int j=0;
								for(int i=0;i<checkedItems.length;i++){
									
									if(checkedItems[i]){
										j++;            //已选择的计数
										if(j>1)
										{   
											result+="、"+items[i];
											
											}
										else {
											result+=items[i];
										}
										
									}
								}
								//Log.i("result:",result);
								//Log.i("personnum----1111",String.valueOf(relatedPersonNum-1));
								relatedPersonType[selectId].setText(String.valueOf(result));	
							}
						}).create().show();
						
						
						
					}});
				
				
				
				
			}});
			
	    /////////////弹出显示提示对话框//////////////////
	  //血型选择
		//bloodType=(EditText)findViewById(R.id.bloodType);
		bloodType.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"A型","B型","AB型","O型","其它血型"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("血型选择").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						bloodType.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		
		//政治面貌选择
		//party=(EditText)findViewById(R.id.party);
		party.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"共产党员","共青团员","其它党派","群众"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("政治面貌").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						party.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		//职业选择
		//occupation=(EditText)findViewById(R.id.occupation);
		occupation.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"军人","教师","商人","农民","公务员","其它"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("选择职业").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						occupation.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		//收入状况选择
		//income=(EditText)findViewById(R.id.income);
		income.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"大于100W","50-100W","20-50W","10-20W","5-10W","<5W"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("年收入").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						income.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});

//个人健康状态选择
//personalWealth=(EditText)findViewById(R.id.personalWealth);
personalWealth.setOnClickListener(new OnClickListener(){

	@Override
	public void onClick(View v) {
		// 弹出对话框
		final String[] items= {"大于1000W","100-1000W","50-100W","10-50W","<10W"};
		new AlertDialog.Builder(EditSomebody.this).setTitle("个人拥有财富").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO 自动生成的方法存根
				personalWealth.setText(items[which]);
			}
		}).create().show();

	}
	
});
//社会地位-官位
//socialClass=(EditText)findViewById(R.id.socialClass);
socialClass.setOnClickListener(new OnClickListener() {

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		final String[] items= {"副军|正厅以上","正师|副厅","副师|副厅","正团|正处","副团|副处","正营|正科","副营|副科","正连|科员以下"};
		new AlertDialog.Builder(EditSomebody.this).setTitle("官位大小").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO 自动生成的方法存根
				socialClass.setText(items[which]);
			}
		}).create().show();
		
	}});
//教育情况
		//educationalStatus=(EditText)findViewById(R.id.educationalStatus);
		educationalStatus.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"教授专家","博士","研究生","本科","大专","其它"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("教育状况").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						educationalStatus.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
//性格类型
		//characterType=(EditText)findViewById(R.id.characterType);
		characterType.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"完美型","助人型","成就型","忧郁型","理智型","怀疑型","活跃型","领袖型","平和型"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("性格类型").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						characterType.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		/*
		 * 完成somebody信息所需的多项选择
		 */
		//选择兴趣爱好
		//hobby=(EditText)findViewById(R.id.hobby);
		hobby.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"读书","书法","唱歌","网络","收藏","户外运动","钓鱼","赌博","其它"};
				final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false};
				new AlertDialog.Builder(EditSomebody.this).setTitle("兴趣爱好").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// 以"|"分割，将组合的result设置到EditText中
						String result ="";
						int j=0;
						for(int i=0;i<checkedItems.length;i++){
							
							if(checkedItems[i]){
								j++;            //已选择的计数
								if(j>1)
								{   
									result+="、"+items[i];
									
									}
								else {
									result+=items[i];
								}
								
							}
						}
						hobby.setText(result);
							
					}
				}).create().show();
			}
		});
		//选择与我的关系
		//relationship=(EditText)findViewById(R.id.relationship);
		relationship.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"直系亲属","旁系亲属","同事","领导","合伙人","同学","老师","老乡","朋友","其它"};
				final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
				new AlertDialog.Builder(EditSomebody.this).setTitle("与我的关系").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// 以"、"分割，将组合的result设置到EditText中
						String result ="";
						int j=0;
						for(int i=0;i<checkedItems.length;i++){
							
							if(checkedItems[i]){
								j++;            //已选择的计数
								if(j>1)
								{   
									result+="、"+items[i];
									
									}
								else {
									result+=items[i];
								}
								
							}
						}
						relationship.setText(result);
							
					}
				}).create().show();
			}
		});
			
	  }
    }

	private String DataAdd(String[] elements) {
		//将字符串数组中所有值组合成一个字符串以“、”号隔开
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
	
	/////////菜单选择///////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.actions, menu);
		return true;
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		if(item.getTitle().toString().trim().equals("是")) {
			update();                     //保存新建数据到数据库表
			return true;
		 }
		else if(item.getTitle().toString().trim().equals("否")) {
			//Toast.makeText(this, "取消保存", Toast.LENGTH_LONG).show();
			EditText name=(EditText)findViewById(R.id.name);
			if(name.getText().toString().trim().equals(person.getName())) {
				finish();
			    return false;	
			}
			else 
			{
			new AlertDialog.Builder(EditSomebody.this).setTitle("系统提示").setMessage("数据未保存,是否退出?").setNegativeButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					finish();
				}
			}).setPositiveButton("取消",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// 
					
				}
			}).create().show();
			
			return false;
			
		}}
		else {return false;}
		
		}
	

	public void update() {
		// upate方法：对sqlite数据进行更新
		
		
		EditText name=(EditText)findViewById(R.id.name);
	    if(name.getText().toString().trim().equals("")) {
	    	Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
	    	return;
	    	
	    }
	    else
	    {
	    person.setName(String.valueOf(name.getText()));
	    }
	    RadioGroup sex=(RadioGroup)findViewById(R.id.sex);
	    for(int i=0;i<sex.getChildCount();i++){
	    	RadioButton r=(RadioButton)sex.getChildAt(i);
	    	if(r.isChecked()){
	    		person.setSex(String.valueOf(r.getText()));  //获得单选内容
	    		break;
	    	}
	    }
	    
	    EditText birthday=(EditText)findViewById(R.id.birthday);
	    if(birthday.getText().toString().equals("")) {person.setBirthday(" ");}
	    else{person.setBirthday(birthday.getText().toString());}
	    //Log.i("birth",person.getBirthday());设置空值存到数据库
	    
	    EditText nativePlace=(EditText)findViewById(R.id.nativePlace);
	    if(nativePlace.getText().toString().equals("")) {person.setNativePlace(" ");}
	    else{person.setNativePlace(String.valueOf(nativePlace.getText()));}
	    
	    EditText nickName=(EditText)findViewById(R.id.nickName);
	    if(nickName.getText().toString().equals("")) {person.setNickName(" ");}
	    else{person.setNickName(String.valueOf(nickName.getText()));}
	    
	    
	    EditText telephone=(EditText)findViewById(R.id.telephone);
	    if(telephone.getText().toString().equals("")) {person.setTelephone(" ");}
	    else{person.setTelephone(String.valueOf(telephone.getText()));}
	    EditText email=(EditText)findViewById(R.id.email);
	    if(email.getText().toString().equals("")) {person.setEmail(" ");}
	    else{person.setEmail(String.valueOf(email.getText()));}
	    EditText qq=(EditText)findViewById(R.id.qq);
	    if(qq.getText().toString().equals("")) {person.setQq(" ");}
	    else{person.setQq(String.valueOf(qq.getText()));}
	    EditText weibo=(EditText)findViewById(R.id.weibo);
	    if(weibo.getText().toString().equals("")) {person.setWeibo(" ");}
	    else{person.setWeibo(String.valueOf(weibo.getText()));}
	    EditText boke=(EditText)findViewById(R.id.boke);
	    if(boke.getText().toString().equals("")) {person.setBoke(" ");}
	    else{person.setBoke(String.valueOf(boke.getText()));}
	    EditText wechat=(EditText)findViewById(R.id.wechat);
	    if(wechat.getText().toString().equals("")) {person.setWechat(" ");}
	    else{person.setWechat(String.valueOf(wechat.getText()));}
	    EditText address=(EditText)findViewById(R.id.address);
	    if(address.getText().toString().equals("")) {person.setAddress(" ");}
	    else{person.setAddress(String.valueOf(address.getText()));}
	    EditText needs=(EditText)findViewById(R.id.needs);
	    //分割字符串数组，形成need[],后传递到person对象
	    if(needs.getText().toString().equals("")) {
	    	String[] need= {" "};
	    	person.setNeeds(need);
	    	}
	    else {
	    String[] need=String.valueOf(needs.getText()).split("、");
	    person.setNeeds(need);
	    }
	    /*
	    for(int i=0;i<person.getNeeds().length;i++) {
	    Log.i("needs",String.valueOf(person.getNeeds()[i]));
	    }*/
	    EditText height=(EditText)findViewById(R.id.height);
	    if(height.getText().toString().trim().equals("")) {
	    	person.setHeight(0);
	    }
	    else
	    {
	    person.setHeight(Float.valueOf(String.valueOf(height.getText())));
	    }
	    EditText weight=(EditText)findViewById(R.id.weight);
	    if(weight.getText().toString().trim().equals("")) {
	    	person.setWeight(0);
	    }
	    else {
	    person.setWeight(Float.valueOf(weight.getText().toString()));
	    }
	    EditText bloodType=(EditText)findViewById(R.id.bloodType);
	    if(bloodType.getText().toString().trim().equals("")) {person.setBloodType(" ");}
	    else{person.setBloodType(String.valueOf(bloodType.getText()));}
	    EditText nationality=(EditText)findViewById(R.id.nationality);
	    if(nationality.getText().toString().trim().equals("")) {person.setNationality(" ");}
	    else{person.setNationality(String.valueOf(nationality.getText()));}
	    EditText party=(EditText)findViewById(R.id.party);
	    if(party.getText().toString().trim().equals("")) {person.setParty(" ");}
	    else{person.setParty(String.valueOf(party.getText()));}
	    //EditText married=(EditText)findViewById(R.id.married);
	    RadioGroup married=(RadioGroup)findViewById(R.id.married);
	    for(int i=0;i<married.getChildCount();i++){
	    	RadioButton r=(RadioButton)married.getChildAt(i);
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("是")){
	    		person.setMarried(true);  //获得单选内容
	    		break;
	    	}
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("否")){
	    		person.setMarried(false);  //获得单选内容
	    		break;
	    	}
	    	
	    }
	    
	    RadioGroup healthCondition=(RadioGroup)findViewById(R.id.healthCondition);
	    for(int i=0;i<healthCondition.getChildCount();i++){
	    	RadioButton r=(RadioButton)healthCondition.getChildAt(i);
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("好")){
	    		person.setHealthCondition(true);  //获得单选内容
	    		break;
	    	}
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("差")){
	    		person.setHealthCondition(false);  //获得单选内容
	    		break;
	    	}
	    	
	    }
	    EditText occupation=(EditText)findViewById(R.id.occupation);
	    if(occupation.getText().toString().trim().equals("")) {person.setOccupation(" ");}
	    else{person.setOccupation(String.valueOf(occupation.getText()));}
	   
	    
	    EditText workUnits=(EditText)findViewById(R.id.workUnits);
	    if(workUnits.getText().toString().trim().equals("")) {person.setWorkUnits(" ");}
	    else{person.setWorkUnits(String.valueOf(workUnits.getText()));}
	    EditText workTitle=(EditText)findViewById(R.id.workTitle);
	    if(workTitle.getText().toString().trim().equals("")) {person.setWorkTitle(" ");}
	    else{person.setWorkTitle(String.valueOf(workTitle.getText()));}
	    EditText income=(EditText)findViewById(R.id.income);
	    if(income.getText().toString().trim().equals("")) {person.setIncome(" ");}
	    else{person.setIncome(String.valueOf(income.getText()));}
	    //Log.i("income", person.getIncome());
	    EditText characterType=(EditText)findViewById(R.id.characterType);
	    //person.setCharacterType(Integer.valueOf(characterType.getText().toString()));
	    if(characterType.getText().toString().trim().equals("完美型"))
	    {person.setCharacterType(1);}
	    else if(characterType.getText().toString().trim().equals("助人型"))
	    {person.setCharacterType(2);}
	    else if(characterType.getText().toString().trim().equals("成就型"))
	    {person.setCharacterType(3);}
	    else if(characterType.getText().toString().trim().equals("忧郁型"))
	    {person.setCharacterType(4);}
	    else if(characterType.getText().toString().trim().equals("理智型"))
	    {person.setCharacterType(5);}
	    else if(characterType.getText().toString().trim().equals("怀疑型"))
	    {person.setCharacterType(6);}
	    else if(characterType.getText().toString().trim().equals("活跃型"))
	    {person.setCharacterType(7);}
	    else if(characterType.getText().toString().trim().equals("领袖型"))
	    {person.setCharacterType(8);}
	    else if(characterType.getText().toString().trim().equals("平和型"))
	    {person.setCharacterType(9);} 
	    else 
	    {person.setCharacterType(0);}     //空，没有值
	    EditText hobbys=(EditText)findViewById(R.id.hobby);
	    //分割字符串数组，形成need[],后传递到person对象
	    if(hobbys.getText().toString().trim().equals("")) {
	    	String[] hobby= {" "};
	        person.setHobby(hobby);	
	    }
	    else
	    {
	    String[] hobby=String.valueOf(hobbys.getText()).split("、");
	    person.setHobby(hobby);
	    }
	    EditText strongPoints=(EditText)findViewById(R.id.strongPoint);
	    //分割字符串数组，形成need[],后传递到person对象
	    if(strongPoints.getText().toString().trim().equals("")) {
	    	String[] strongPoint= {" "};
	    	person.setStrongPoint(strongPoint);
	    }
	    else {
	    String[] strongPoint=String.valueOf(strongPoints.getText()).split("、");
	    person.setStrongPoint(strongPoint);
	    }
	    EditText weakPoints=(EditText)findViewById(R.id.weakPoint);
	    //分割字符串数组，形成need[],后传递到person对象
	    String[] weakPoint=String.valueOf(weakPoints.getText()).split("、");
	    person.setWeakPoint(weakPoint);
	    
	    EditText personalWealth=(EditText)findViewById(R.id.personalWealth);
	    if(personalWealth.getText().toString().trim().equals("大于1000W"))
	    {person.setPersonalWealth(1);}
	    else if(personalWealth.getText().toString().trim().equals("100-1000W"))
	    {person.setPersonalWealth(2);}
	    else if(personalWealth.getText().toString().trim().equals("50-100W"))
	    {person.setPersonalWealth(3);}
	    else if(personalWealth.getText().toString().trim().equals("10-50W"))
	    {person.setPersonalWealth(4);}
	    else if(personalWealth.getText().toString().trim().equals("<10W"))
	    {person.setPersonalWealth(5);}
	    else
	    {person.setPersonalWealth(5);}//没有填写，为空
	    
	        
	    EditText socialClass=(EditText)findViewById(R.id.socialClass);
	    if(socialClass.getText().toString().trim().equals("副军|正厅以上"))
	    {person.setSocialClass(1);}
	    else if(socialClass.getText().toString().trim().equals("正师|副厅"))
	    {person.setSocialClass(2);}
	    else if(socialClass.getText().toString().trim().equals("副师|副厅"))
	    {person.setSocialClass(3);}
	    else if(socialClass.getText().toString().trim().equals("正团|正处"))
	    {person.setSocialClass(4);}
	    else if(socialClass.getText().toString().trim().equals("副团|副处"))
	    {person.setSocialClass(5);}
	    else if(socialClass.getText().toString().trim().equals("正营|正科"))
	    {person.setSocialClass(6);}
	    else if(socialClass.getText().toString().trim().equals("副营|副科"))
	    {person.setSocialClass(7);}
	    else if(socialClass.getText().toString().trim().equals("正连|科员以下"))
	    {person.setSocialClass(8);}
	    
	    else
	    {person.setSocialClass(8);}
	    
	    EditText educationalStatus=(EditText)findViewById(R.id.educationalStatus);
	    if(educationalStatus.getText().toString().trim().equals("教授专家"))
	    {person.setEducationalStatus(1);}
	    else if(educationalStatus.getText().toString().trim().equals("博士"))
	    {person.setEducationalStatus(2);}
	    else if(educationalStatus.getText().toString().trim().equals("研究生"))
	    {person.setEducationalStatus(3);}
	    else if(educationalStatus.getText().toString().trim().equals("本科"))
	    {person.setEducationalStatus(4);}
	    else if(educationalStatus.getText().toString().trim().equals("大专"))
	    {person.setEducationalStatus(5);}
	    else if(educationalStatus.getText().toString().trim().equals("其它"))
	    {person.setEducationalStatus(6);}
	    else
	    {person.setEducationalStatus(6);}
	    
	    EditText cohesion=(EditText)findViewById(R.id.cohesion);
	    if(cohesion.getText().toString().trim().equals("")) {
	    	person.setCohesion(0);//没有填写填0
	    }
	    else {
	    person.setCohesion(Integer.valueOf(String.valueOf(cohesion.getText())));
	    }
	    EditText relationship=(EditText)findViewById(R.id.relationship);
	    if(relationship.getText().toString().trim().equals("")) {
	    	person.setRelationship(" ");
	    }
	    else {
	    person.setRelationship(String.valueOf(relationship.getText()));   
	    }
	    //将relatedPerson中内容保存到二维数组中，并插入到表person_relatives
	    
	    String[][] relatedPerson=new String[10][5];
	    for(int i=0;i<relatedPersonNum;i++) {
	    	
	    	relatedPerson[i][1]=relatedPersonType[i].getText().toString();
	    	relatedPerson[i][2]=relatedPersonName[i].getText().toString();
	    	if(relatedPerson[i][1].equals(person.getRelatedPerson()[i][1])&&relatedPerson[i][2].equals(person.getRelatedPerson()[i][2]))
	    	{//没有发生变化，则其它值不变
	    		relatedPerson[i][0]=person.getRelatedPerson()[i][0];
	    		relatedPerson[i][3]=person.getRelatedPerson()[i][3];
	    		relatedPerson[i][4]=person.getRelatedPerson()[i][4];
	    		
	    	}
	    	else {
	    		//对于新的值，则产生新计录
	    		relatedPerson[i][0]="0";//以0表示为未给予值，本值由表自动产生
	    		relatedPerson[i][3]=String.valueOf(person.getId());//person id
	    		if(relatedPerson[i][2].equals(person.getRelatedPerson()[i][2])) {
	    			///只是关系发生了改变
	    	    relatedPerson[i][4]=String.valueOf(person.getRelatedPerson()[i][4]);  //relatedPerson id不变	
	    		}
	    		relatedPerson[i][4]="0";                           //等待匹配值
	    	}
	    		    	                                       //[3]中存放person id号,[4]存放relatedPerson ID
	        
	    }
	    person.setRelatedPerson(relatedPerson);
	    /*
	    for(int i=0;i<relatedPersonNum;i++) {
	    	Log.i("type&name:",person.getRelatedPerson()[i][1]+person.getRelatedPerson()[i][2]);
	    }*/
	    
	    //连接数据库进行更新数据
	    DBHelper helper=new DBHelper(EditSomebody.this);
	    boolean updateOK=helper.update(person);
	    if(updateOK) {
	    	Toast.makeText(this, "数据更新成功",Toast.LENGTH_SHORT).show();
	    	Intent intent=new Intent();
	    	intent.setClass(this, MainActivity.class);
	    	startActivity(intent);
	    }
	   
		
	}
}
