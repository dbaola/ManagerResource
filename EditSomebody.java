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
	//////////�����鶨��//////////////////////////
	private Somebody person;
	/////����ʵ��Ϊperson.getRelatedPerson()[][]={relatedPersonType[],relatedPersonName[],relatedPersonId[],rPersonId[]}
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
	    //��ѯname��ֵ,��ʾ��һ�����������
	    
	    String personName=intent.getStringExtra("name").toString().trim();
	    
	    int personId=Integer.valueOf(intent.getIntExtra("personId",-1));
	    if(personId==-1) {
	    	Toast.makeText(this, "��ԱID����", Toast.LENGTH_SHORT).show();
	    }
	    else
	    {
	    //Log.i("id",String.valueOf(personId)+personName);
	    DBHelper helper=new DBHelper(this);
	    person=helper.query(personName,personId);
	    
	    ///////////////////////////////��ɳ�ֵ����///////////////////////////////
	    EditText name=(EditText)findViewById(R.id.name);	    
	    name.setText(person.getName());
	    RadioGroup sex=(RadioGroup)findViewById(R.id.sex);
	    for(int i=0;i<sex.getChildCount();i++){
	    	RadioButton r=(RadioButton)sex.getChildAt(i);
	    	if(r.getText().equals(person.getSex())){
	    		r.setChecked(true);  //��õ�ѡ����
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
	    		r.setChecked(true);  //��õ�ѡ����
	    		break;
	    	}
	    }
	    RadioGroup healthCondition=(RadioGroup)findViewById(R.id.healthCondition);
	    for(int i=0;i<married.getChildCount();i++){
	    	RadioButton r=(RadioButton)healthCondition.getChildAt(i);
	    	if(r.getText().equals(person.isHealthCondition())){
	    		r.setChecked(true);  //��õ�ѡ����
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
	    case 1:{characterType.setText("������");break;}
	    case 2:{characterType.setText("������");break;}
	    case 3:{characterType.setText("�ɾ���");break;}
	    case 4:{characterType.setText("������");break;}
	    case 5:{characterType.setText("������");break;}
	    case 6:{characterType.setText("������");break;}
	    case 7:{characterType.setText("��Ծ��");break;}
	    case 8:{characterType.setText("������");break;}
	    case 9:{characterType.setText("ƽ����");break;}
	    }
	    final EditText hobby=(EditText)findViewById(R.id.hobby);
	    hobby.setText(DataAdd(person.getHobby()));
	    EditText strongPoint=(EditText)findViewById(R.id.strongPoint);
	    strongPoint.setText(DataAdd(person.getStrongPoint()));
	    EditText weakPoint=(EditText)findViewById(R.id.weakPoint);
	    weakPoint.setText(DataAdd(person.getWeakPoint()));
	    final EditText personalWealth=(EditText)findViewById(R.id.personalWealth);
	    switch(person.getPersonalWealth()){
	    case 1:{personalWealth.setText("����1000W");break;}
	    case 2:{personalWealth.setText("100-1000W");break;}
	    case 3:{personalWealth.setText("50-100W");break;}
	    case 4:{personalWealth.setText("10-50W");break;}
	    case 5:{personalWealth.setText("<10W");break;}
	    }
	    final EditText socialClass=(EditText)findViewById(R.id.socialClass);
	    switch(person.getSocialClass()){
	    case 1:{socialClass.setText("����|��������");break;}
	    case 2:{socialClass.setText("��ʦ|����");break;}
	    case 3:{socialClass.setText("��ʦ|����");break;}
	    case 4:{socialClass.setText("����|����");break;}
	    case 5:{socialClass.setText("����|����");break;}
	    case 6:{socialClass.setText("��Ӫ|����");break;}
	    case 7:{socialClass.setText("��Ӫ|����");break;}
	    case 8:{socialClass.setText("����|��Ա����");break;}
	    default:{socialClass.setText("����|��Ա����");break;}
	    }
	    final EditText educationalStatus=(EditText)findViewById(R.id.educationalStatus);
	    switch(person.getEducationalStatus()){
	    case 1:{educationalStatus.setText("����ר��");break;}
	    case 2:{educationalStatus.setText("��ʿ");break;}
	    case 3:{educationalStatus.setText("�о���");break;}
	    case 4:{educationalStatus.setText("����");break;}
	    case 5:{educationalStatus.setText("��ר");break;}
	    case 6:{educationalStatus.setText("����");break;}
	    default:{educationalStatus.setText("����");break;} 
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
	    	//rTableId[i]=new TextView(this);        //�洢���е�idֵ��δ����
	    	//relatedPersonId[i]=new EditText(this);
		    relatedPersonType[i]=new EditText(this);
		    relatedPersonName[i]=new EditText(this);
		    //tbRow.setGravity(Gravity.LEFT);
	    	tbLayout1.addView(tbRow);
	    	relatedPersonType[i].setId(i);
	    	//relatedPersonId[i].setText(String.valueOf(person.getRelatedPerson()[i][4]));  //�����޸Ĺ�����id�ţ�Ĭ��ֵΪ0
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
					// �༭ʱ������ʾ����Թ�ѡ��
					
					final String[] items= {"ֱϵ����","��ϵ����","ͬ��","�쵼","�ϻ���","ͬѧ","��ʦ","����","����","����"};
					final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
					final int selectId=Integer.valueOf(v.getId());
					new AlertDialog.Builder(EditSomebody.this).setTitle("����|���Ĺ�ϵ").setPositiveButton("ȷ��",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// ��"|"�ָ����ϵ�result���õ�EditText��
							String result ="";
							int j=0;
							for(int i=0;i<checkedItems.length;i++){
								
								if(checkedItems[i]){
									j++;            //��ѡ��ļ���
									if(j>1)
									{   
										result+="��"+items[i];
										
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
					return;}//�����趨����ֵ�󣬵���Ч;��һ������������û����д���������ӡ�
				int temp=relatedPersonNum-1;
				if(temp>=0&&(relatedPersonType[temp].getText().toString().equals("")||relatedPersonName[temp].getText().toString().equals(""))){
					Toast.makeText(EditSomebody.this, "��ϵ������Ϊ��", Toast.LENGTH_SHORT).show();
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
						// �����Ի���
						final String[] items= {"ֱϵ����","��ϵ����","ͬ��","�쵼","�ϻ���","ͬѧ","��ʦ","����","����","����"};
						final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
						final int selectId=Integer.valueOf(v.getId());
						new AlertDialog.Builder(EditSomebody.this).setTitle("����|���Ĺ�ϵ").setPositiveButton("ȷ��",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which, boolean isChecked) {
								// ��"��"�ָ����ϵ�result���õ�EditText��
								String result ="";
								int j=0;
								for(int i=0;i<checkedItems.length;i++){
									
									if(checkedItems[i]){
										j++;            //��ѡ��ļ���
										if(j>1)
										{   
											result+="��"+items[i];
											
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
			
	    /////////////������ʾ��ʾ�Ի���//////////////////
	  //Ѫ��ѡ��
		//bloodType=(EditText)findViewById(R.id.bloodType);
		bloodType.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"A��","B��","AB��","O��","����Ѫ��"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("Ѫ��ѡ��").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO �Զ����ɵķ������
						bloodType.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		
		//������òѡ��
		//party=(EditText)findViewById(R.id.party);
		party.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"������Ա","������Ա","��������","Ⱥ��"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("������ò").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO �Զ����ɵķ������
						party.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		//ְҵѡ��
		//occupation=(EditText)findViewById(R.id.occupation);
		occupation.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"����","��ʦ","����","ũ��","����Ա","����"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("ѡ��ְҵ").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO �Զ����ɵķ������
						occupation.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		//����״��ѡ��
		//income=(EditText)findViewById(R.id.income);
		income.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"����100W","50-100W","20-50W","10-20W","5-10W","<5W"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("������").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO �Զ����ɵķ������
						income.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});

//���˽���״̬ѡ��
//personalWealth=(EditText)findViewById(R.id.personalWealth);
personalWealth.setOnClickListener(new OnClickListener(){

	@Override
	public void onClick(View v) {
		// �����Ի���
		final String[] items= {"����1000W","100-1000W","50-100W","10-50W","<10W"};
		new AlertDialog.Builder(EditSomebody.this).setTitle("����ӵ�вƸ�").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO �Զ����ɵķ������
				personalWealth.setText(items[which]);
			}
		}).create().show();

	}
	
});
//����λ-��λ
//socialClass=(EditText)findViewById(R.id.socialClass);
socialClass.setOnClickListener(new OnClickListener() {

	@Override
	public void onClick(View arg0) {
		// TODO �Զ����ɵķ������
		final String[] items= {"����|��������","��ʦ|����","��ʦ|����","����|����","����|����","��Ӫ|����","��Ӫ|����","����|��Ա����"};
		new AlertDialog.Builder(EditSomebody.this).setTitle("��λ��С").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int which) {
				// TODO �Զ����ɵķ������
				socialClass.setText(items[which]);
			}
		}).create().show();
		
	}});
//�������
		//educationalStatus=(EditText)findViewById(R.id.educationalStatus);
		educationalStatus.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"����ר��","��ʿ","�о���","����","��ר","����"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("����״��").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO �Զ����ɵķ������
						educationalStatus.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
//�Ը�����
		//characterType=(EditText)findViewById(R.id.characterType);
		characterType.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"������","������","�ɾ���","������","������","������","��Ծ��","������","ƽ����"};
				new AlertDialog.Builder(EditSomebody.this).setTitle("�Ը�����").setPositiveButton("ȷ��",null).setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int which) {
						// TODO �Զ����ɵķ������
						characterType.setText(items[which]);
					}
				}).create().show();
		
			}
			
		});
		/*
		 * ���somebody��Ϣ����Ķ���ѡ��
		 */
		//ѡ����Ȥ����
		//hobby=(EditText)findViewById(R.id.hobby);
		hobby.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"����","�鷨","����","����","�ղ�","�����˶�","����","�Ĳ�","����"};
				final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false};
				new AlertDialog.Builder(EditSomebody.this).setTitle("��Ȥ����").setPositiveButton("ȷ��",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// ��"|"�ָ����ϵ�result���õ�EditText��
						String result ="";
						int j=0;
						for(int i=0;i<checkedItems.length;i++){
							
							if(checkedItems[i]){
								j++;            //��ѡ��ļ���
								if(j>1)
								{   
									result+="��"+items[i];
									
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
		//ѡ�����ҵĹ�ϵ
		//relationship=(EditText)findViewById(R.id.relationship);
		relationship.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// �����Ի���
				final String[] items= {"ֱϵ����","��ϵ����","ͬ��","�쵼","�ϻ���","ͬѧ","��ʦ","����","����","����"};
				final boolean[] checkedItems= {false,false,false,false,false,false,false,false,false,false};
				new AlertDialog.Builder(EditSomebody.this).setTitle("���ҵĹ�ϵ").setPositiveButton("ȷ��",null).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// ��"��"�ָ����ϵ�result���õ�EditText��
						String result ="";
						int j=0;
						for(int i=0;i<checkedItems.length;i++){
							
							if(checkedItems[i]){
								j++;            //��ѡ��ļ���
								if(j>1)
								{   
									result+="��"+items[i];
									
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
		//���ַ�������������ֵ��ϳ�һ���ַ����ԡ������Ÿ���
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
	
	/////////�˵�ѡ��///////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.actions, menu);
		return true;
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		if(item.getTitle().toString().trim().equals("��")) {
			update();                     //�����½����ݵ����ݿ��
			return true;
		 }
		else if(item.getTitle().toString().trim().equals("��")) {
			//Toast.makeText(this, "ȡ������", Toast.LENGTH_LONG).show();
			EditText name=(EditText)findViewById(R.id.name);
			if(name.getText().toString().trim().equals(person.getName())) {
				finish();
			    return false;	
			}
			else 
			{
			new AlertDialog.Builder(EditSomebody.this).setTitle("ϵͳ��ʾ").setMessage("����δ����,�Ƿ��˳�?").setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					finish();
				}
			}).setPositiveButton("ȡ��",new DialogInterface.OnClickListener() {
				
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
		// upate��������sqlite���ݽ��и���
		
		
		EditText name=(EditText)findViewById(R.id.name);
	    if(name.getText().toString().trim().equals("")) {
	    	Toast.makeText(this, "��������Ϊ��", Toast.LENGTH_SHORT).show();
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
	    		person.setSex(String.valueOf(r.getText()));  //��õ�ѡ����
	    		break;
	    	}
	    }
	    
	    EditText birthday=(EditText)findViewById(R.id.birthday);
	    if(birthday.getText().toString().equals("")) {person.setBirthday(" ");}
	    else{person.setBirthday(birthday.getText().toString());}
	    //Log.i("birth",person.getBirthday());���ÿ�ֵ�浽���ݿ�
	    
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
	    //�ָ��ַ������飬�γ�need[],�󴫵ݵ�person����
	    if(needs.getText().toString().equals("")) {
	    	String[] need= {" "};
	    	person.setNeeds(need);
	    	}
	    else {
	    String[] need=String.valueOf(needs.getText()).split("��");
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
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("��")){
	    		person.setMarried(true);  //��õ�ѡ����
	    		break;
	    	}
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("��")){
	    		person.setMarried(false);  //��õ�ѡ����
	    		break;
	    	}
	    	
	    }
	    
	    RadioGroup healthCondition=(RadioGroup)findViewById(R.id.healthCondition);
	    for(int i=0;i<healthCondition.getChildCount();i++){
	    	RadioButton r=(RadioButton)healthCondition.getChildAt(i);
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("��")){
	    		person.setHealthCondition(true);  //��õ�ѡ����
	    		break;
	    	}
	    	if(r.isChecked()&&String.valueOf(r.getText()).equals("��")){
	    		person.setHealthCondition(false);  //��õ�ѡ����
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
	    if(characterType.getText().toString().trim().equals("������"))
	    {person.setCharacterType(1);}
	    else if(characterType.getText().toString().trim().equals("������"))
	    {person.setCharacterType(2);}
	    else if(characterType.getText().toString().trim().equals("�ɾ���"))
	    {person.setCharacterType(3);}
	    else if(characterType.getText().toString().trim().equals("������"))
	    {person.setCharacterType(4);}
	    else if(characterType.getText().toString().trim().equals("������"))
	    {person.setCharacterType(5);}
	    else if(characterType.getText().toString().trim().equals("������"))
	    {person.setCharacterType(6);}
	    else if(characterType.getText().toString().trim().equals("��Ծ��"))
	    {person.setCharacterType(7);}
	    else if(characterType.getText().toString().trim().equals("������"))
	    {person.setCharacterType(8);}
	    else if(characterType.getText().toString().trim().equals("ƽ����"))
	    {person.setCharacterType(9);} 
	    else 
	    {person.setCharacterType(0);}     //�գ�û��ֵ
	    EditText hobbys=(EditText)findViewById(R.id.hobby);
	    //�ָ��ַ������飬�γ�need[],�󴫵ݵ�person����
	    if(hobbys.getText().toString().trim().equals("")) {
	    	String[] hobby= {" "};
	        person.setHobby(hobby);	
	    }
	    else
	    {
	    String[] hobby=String.valueOf(hobbys.getText()).split("��");
	    person.setHobby(hobby);
	    }
	    EditText strongPoints=(EditText)findViewById(R.id.strongPoint);
	    //�ָ��ַ������飬�γ�need[],�󴫵ݵ�person����
	    if(strongPoints.getText().toString().trim().equals("")) {
	    	String[] strongPoint= {" "};
	    	person.setStrongPoint(strongPoint);
	    }
	    else {
	    String[] strongPoint=String.valueOf(strongPoints.getText()).split("��");
	    person.setStrongPoint(strongPoint);
	    }
	    EditText weakPoints=(EditText)findViewById(R.id.weakPoint);
	    //�ָ��ַ������飬�γ�need[],�󴫵ݵ�person����
	    String[] weakPoint=String.valueOf(weakPoints.getText()).split("��");
	    person.setWeakPoint(weakPoint);
	    
	    EditText personalWealth=(EditText)findViewById(R.id.personalWealth);
	    if(personalWealth.getText().toString().trim().equals("����1000W"))
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
	    {person.setPersonalWealth(5);}//û����д��Ϊ��
	    
	        
	    EditText socialClass=(EditText)findViewById(R.id.socialClass);
	    if(socialClass.getText().toString().trim().equals("����|��������"))
	    {person.setSocialClass(1);}
	    else if(socialClass.getText().toString().trim().equals("��ʦ|����"))
	    {person.setSocialClass(2);}
	    else if(socialClass.getText().toString().trim().equals("��ʦ|����"))
	    {person.setSocialClass(3);}
	    else if(socialClass.getText().toString().trim().equals("����|����"))
	    {person.setSocialClass(4);}
	    else if(socialClass.getText().toString().trim().equals("����|����"))
	    {person.setSocialClass(5);}
	    else if(socialClass.getText().toString().trim().equals("��Ӫ|����"))
	    {person.setSocialClass(6);}
	    else if(socialClass.getText().toString().trim().equals("��Ӫ|����"))
	    {person.setSocialClass(7);}
	    else if(socialClass.getText().toString().trim().equals("����|��Ա����"))
	    {person.setSocialClass(8);}
	    
	    else
	    {person.setSocialClass(8);}
	    
	    EditText educationalStatus=(EditText)findViewById(R.id.educationalStatus);
	    if(educationalStatus.getText().toString().trim().equals("����ר��"))
	    {person.setEducationalStatus(1);}
	    else if(educationalStatus.getText().toString().trim().equals("��ʿ"))
	    {person.setEducationalStatus(2);}
	    else if(educationalStatus.getText().toString().trim().equals("�о���"))
	    {person.setEducationalStatus(3);}
	    else if(educationalStatus.getText().toString().trim().equals("����"))
	    {person.setEducationalStatus(4);}
	    else if(educationalStatus.getText().toString().trim().equals("��ר"))
	    {person.setEducationalStatus(5);}
	    else if(educationalStatus.getText().toString().trim().equals("����"))
	    {person.setEducationalStatus(6);}
	    else
	    {person.setEducationalStatus(6);}
	    
	    EditText cohesion=(EditText)findViewById(R.id.cohesion);
	    if(cohesion.getText().toString().trim().equals("")) {
	    	person.setCohesion(0);//û����д��0
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
	    //��relatedPerson�����ݱ��浽��ά�����У������뵽��person_relatives
	    
	    String[][] relatedPerson=new String[10][5];
	    for(int i=0;i<relatedPersonNum;i++) {
	    	
	    	relatedPerson[i][1]=relatedPersonType[i].getText().toString();
	    	relatedPerson[i][2]=relatedPersonName[i].getText().toString();
	    	if(relatedPerson[i][1].equals(person.getRelatedPerson()[i][1])&&relatedPerson[i][2].equals(person.getRelatedPerson()[i][2]))
	    	{//û�з����仯��������ֵ����
	    		relatedPerson[i][0]=person.getRelatedPerson()[i][0];
	    		relatedPerson[i][3]=person.getRelatedPerson()[i][3];
	    		relatedPerson[i][4]=person.getRelatedPerson()[i][4];
	    		
	    	}
	    	else {
	    		//�����µ�ֵ��������¼�¼
	    		relatedPerson[i][0]="0";//��0��ʾΪδ����ֵ����ֵ�ɱ��Զ�����
	    		relatedPerson[i][3]=String.valueOf(person.getId());//person id
	    		if(relatedPerson[i][2].equals(person.getRelatedPerson()[i][2])) {
	    			///ֻ�ǹ�ϵ�����˸ı�
	    	    relatedPerson[i][4]=String.valueOf(person.getRelatedPerson()[i][4]);  //relatedPerson id����	
	    		}
	    		relatedPerson[i][4]="0";                           //�ȴ�ƥ��ֵ
	    	}
	    		    	                                       //[3]�д��person id��,[4]���relatedPerson ID
	        
	    }
	    person.setRelatedPerson(relatedPerson);
	    /*
	    for(int i=0;i<relatedPersonNum;i++) {
	    	Log.i("type&name:",person.getRelatedPerson()[i][1]+person.getRelatedPerson()[i][2]);
	    }*/
	    
	    //�������ݿ���и�������
	    DBHelper helper=new DBHelper(EditSomebody.this);
	    boolean updateOK=helper.update(person);
	    if(updateOK) {
	    	Toast.makeText(this, "���ݸ��³ɹ�",Toast.LENGTH_SHORT).show();
	    	Intent intent=new Intent();
	    	intent.setClass(this, MainActivity.class);
	    	startActivity(intent);
	    }
	   
		
	}
}
