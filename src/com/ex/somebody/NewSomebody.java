package com.ex.somebody;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.util.Log;
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


public class NewSomebody extends Activity{
	private Somebody person=new Somebody();
	private EditText personalWealth;
	private EditText socialClass;
	private EditText bloodType;
	private EditText party;
	private EditText occupation;
	private EditText income;
	private EditText educationalStatus;
	private EditText characterType;
	private EditText hobby;
	private EditText relationship;
	//增加somebody关系人员
	private EditText [] relatedPersonType_et1=new EditText[10];
	private EditText[] relatedPersonName_et2=new EditText[10];
	private int relatedPersonNum=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_relatives);
		
		/*
		 * 完成新建somebody信息所需的单项选择
		 */
		
		//血型选择
				bloodType=(EditText)findViewById(R.id.bloodType);
				bloodType.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"A型","B型","AB型","O型","其它血型"};
						new AlertDialog.Builder(NewSomebody.this).setTitle("血型选择").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int which) {
								// TODO 自动生成的方法存根
								bloodType.setText(items[which]);
							}
						}).create().show();
				
					}
					
				});
				//政治面貌选择
				party=(EditText)findViewById(R.id.party);
				party.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"共产党员","共青团员","其它党派","群众"};
						new AlertDialog.Builder(NewSomebody.this).setTitle("政治面貌").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int which) {
								// TODO 自动生成的方法存根
								party.setText(items[which]);
							}
						}).create().show();
				
					}
					
				});
				//职业选择
				occupation=(EditText)findViewById(R.id.occupation);
				occupation.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"军人","教师","商人","农民","公务员","其它"};
						new AlertDialog.Builder(NewSomebody.this).setTitle("选择职业").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int which) {
								// TODO 自动生成的方法存根
								occupation.setText(items[which]);
							}
						}).create().show();
				
					}
					
				});
				//收入状况选择
				income=(EditText)findViewById(R.id.income);
				income.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"大于100W","50-100W","20-50W","10-20W","5-10W","<5W"};
						new AlertDialog.Builder(NewSomebody.this).setTitle("年收入").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int which) {
								// TODO 自动生成的方法存根
								income.setText(items[which]);
							}
						}).create().show();
				
					}
					
				});
		
		//个人健康状态选择
		personalWealth=(EditText)findViewById(R.id.personalWealth);
		personalWealth.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 弹出对话框
				final String[] items= {"大于1000W","100-1000W","50-100W","10-50W","<10W"};
				new AlertDialog.Builder(NewSomebody.this).setTitle("个人拥有财富").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						personalWealth.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		//社会地位-官位
		socialClass=(EditText)findViewById(R.id.socialClass);
		socialClass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				final String[] items= {"副军|正厅以上","正师|副厅","副师|副厅","正团|正处","副团|副处","正营|正科","副营|副科","正连|科员以下"};
				new AlertDialog.Builder(NewSomebody.this).setTitle("官位大小").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO 自动生成的方法存根
						socialClass.setText(items[which]);
					}
				}).create().show();
				
			}});
		//教育情况
				educationalStatus=(EditText)findViewById(R.id.educationalStatus);
				educationalStatus.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"教授专家","博士","研究生","本科","大专","其它"};
						new AlertDialog.Builder(NewSomebody.this).setTitle("教育状况").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int which) {
								// TODO 自动生成的方法存根
								educationalStatus.setText(items[which]);
							}
						}).create().show();
				
					}
					
				});
		//性格类型
				characterType=(EditText)findViewById(R.id.characterType);
				characterType.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"完美型","助人型","成就型","忧郁型","理智型","怀疑型","活跃型","领袖型","平和型"};
						new AlertDialog.Builder(NewSomebody.this).setTitle("性格类型").setPositiveButton("确定",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
							
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
				hobby=(EditText)findViewById(R.id.hobby);
				hobby.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"读书","书法","唱歌","网络","收藏","户外运动","钓鱼","赌博","其它"};
						final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false};
						new AlertDialog.Builder(NewSomebody.this).setTitle("兴趣爱好").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
							
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
				relationship=(EditText)findViewById(R.id.relationship);
				relationship.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// 弹出对话框
						final String[] items= {"直系亲属","旁系亲属","同事","领导","合伙人","同学","老师","老乡","朋友","其它"};
						final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
						new AlertDialog.Builder(NewSomebody.this).setTitle("与我的关系").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
							
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
								relationship.setText(result);
									
							}
						}).create().show();
					}
				});
					
				/*临时新增加EditText控件
				 * 
				 * */
				//增加somebody关系人员
				relatedPersonType_et1[0]=(EditText)findViewById(R.id.relatedPersonType);
				relatedPersonName_et2[0]=(EditText)findViewById(R.id.relatedPersonName);
				//点激关系自动弹出多选框
				relatedPersonType_et1[0].setId(0);
				relatedPersonType_et1[0].setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// 弹出对话框
						final String[] items= {"直系亲属","旁系亲属","同事","领导","合伙人","同学","老师","老乡","朋友","其它"};
						final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
						new AlertDialog.Builder(NewSomebody.this).setTitle("与他|她的关系").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
							
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
								//Log.i("personnum",String.valueOf(relatedPersonNum));
								relatedPersonType_et1[0].setText(result);
									
							}
						}).create().show();
						
						
					}});
				TextView button_relatedPerson_id=(TextView)findViewById(R.id.button_relatedPerson_id);
				final TableLayout tableL1=(TableLayout)findViewById(R.id.tableL1);
				//final TableRow tableR=new TableRow(this);
				relatedPersonNum=1;
				button_relatedPerson_id.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						relatedPersonType_et1[relatedPersonNum]=new EditText(NewSomebody.this);
						relatedPersonName_et2[relatedPersonNum]=new EditText(NewSomebody.this);
						
						if(relatedPersonNum>9){
							return;}//超过设定的数值后，点无效
						int temp=relatedPersonNum-1;
						if(temp>=0&&(relatedPersonType_et1[temp].getText().toString().equals("")||relatedPersonName_et2[temp].getText().toString().equals(""))){
							Toast.makeText(NewSomebody.this, "关系或人名为空", Toast.LENGTH_SHORT).show();
							return;
						}
						relatedPersonType_et1[relatedPersonNum].setTextSize(14);
						relatedPersonType_et1[relatedPersonNum].setGravity(1);
						relatedPersonName_et2[relatedPersonNum].setTextSize(14);
						relatedPersonName_et2[relatedPersonNum].setGravity(1);
						TableRow tableR=new TableRow(NewSomebody.this);
						tableL1.addView(tableR);
						tableR.addView(relatedPersonType_et1[relatedPersonNum]);						
						tableR.addView(relatedPersonName_et2[relatedPersonNum]);
						relatedPersonType_et1[relatedPersonNum].setId(relatedPersonNum);
						relatedPersonNum=relatedPersonNum+1;
						//Log.i("personnum---0000",String.valueOf(relatedPersonNum));
						
						relatedPersonType_et1[relatedPersonNum-1].setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// 弹出对话框
								final String[] items= {"直系亲属","旁系亲属","同事","领导","合伙人","同学","老师","老乡","朋友","其它"};
								final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
								final int selectId=Integer.valueOf(v.getId());
								new AlertDialog.Builder(NewSomebody.this).setTitle("与他|她的关系").setPositiveButton("确定",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
									
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
										relatedPersonType_et1[selectId].setText(String.valueOf(result));	
									}
								}).create().show();
								
								
								
							}});
						
						
						
						
					}});
				
						
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.actions, menu);
		return true;
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		if(item.getTitle().toString().trim().equals("是")) {
			save();                     //保存新建数据到数据库表
			
			
		    return true;
		    
		}
		else if(item.getTitle().toString().trim().equals("否")) {
			//Toast.makeText(this, "取消保存", Toast.LENGTH_LONG).show();
			EditText name=(EditText)findViewById(R.id.name);
			if(name.getText().toString().trim().equals("")) {
				finish();
			    return false;	
			}
			else 
			{
			new AlertDialog.Builder(NewSomebody.this).setTitle("系统提示").setMessage("数据未保存,是否退出?").setNegativeButton("确定", new DialogInterface.OnClickListener() {
				
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

	private void save() {
		// TODO Auto-generated method stub
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
	    
	    String[][] relatedPerson=new String[10][4];
	    for(int i=0;i<relatedPersonNum;i++) {
	    	relatedPerson[i][0]= relatedPersonType_et1[i].getText().toString();
	    	relatedPerson[i][1]=relatedPersonName_et2[i].getText().toString();
	    	relatedPerson[i][2]="0";                                       //[2]中存放person id号,[3]存放relatedPerson ID，在未知的情况下存值为0,实际表中号还有编号数据
	        relatedPerson[i][3]="0";
	    }
	    person.setRelatedPerson(relatedPerson);
	    //for(int i=0;i<person.getNeeds().length;i++)
	    //{Log.i("needs loop",person.getNeeds()[i]);}
	    
	    //保存二维数组与somebody：relatedPerson到数据库，relatedPersonType_et1[],relatedPersonName_et2[]
	    //relatedPerson[][]
	    
	    //EditText telephone=(EditText)findViewById(R.id.telephone);
	    //person.setTelephone(String.valueOf(telephone.getText()));
	    //Log.i("nickname",person.getNickName());
	    //写入数据
	    for(int i=0;i<person.getStrongPoint().length;i++) {
	    	Log.i("strongPoint",person.getStrongPoint()[i]);
	    }
	    DBHelper helper=new DBHelper(NewSomebody.this);
	    int somebody_id=helper.insert(person);
	    person.setId(somebody_id);
	    //helper.insert(person);
	    Toast.makeText(this, "数据已经保存", Toast.LENGTH_LONG).show();
	    finish();
	    
	    
	  //弹出新建信息的显示页面主窗口
		
		Intent intent=new Intent();
		//intent.putExtra("name", person.getName());
		//intent.putExtra("personId", person.getId());
		
		
		intent.setClass(this,MainActivity.class);
		startActivity(intent);
	    
	    
		
	}

}


