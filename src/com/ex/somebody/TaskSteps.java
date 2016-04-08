package com.ex.somebody;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ex.task.Task;
import com.ex.task.TaskStep;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TaskSteps extends ListActivity{
	private Task taskOne;
	private List<Map<String,Object>>mData;
	
	//����ָ�
	private int splitNum=0;          //�ָ�Ϊ��������
	private EditText[] stepContent=new EditText[10];  //����ɱ༭view������
	private EditText[] poinTime=new EditText[10];
	private TaskStep stepOne;
	
	
	//private DBHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasks);
		Intent intent=getIntent();
	    int taskId=Integer.valueOf(intent.getIntExtra("taskid", -1));
	    if(taskId==-1) {
	    	Toast.makeText(this, "id��ֵ����", Toast.LENGTH_SHORT).show();
	    }
	    else
	    {
	    	//Log.i("taskIdd:",String.valueOf(taskId));
	    	taskOne=new Task();
	        taskOne.setTaskId(taskId);
	    	init();	
	    }
	}

	private void init() {
		// �б���ʾ����ͼ��
		
		DBHelper helper=new DBHelper(TaskSteps.this);
		//helper.adjustStepsValue(taskOne.getTaskId());
    	taskOne=helper.findTaskOne(taskOne.getTaskId());//Ĭ������²�ѯ������������
    	
			//��ʾ���軭��
			mData=getData();
    		MyAdapter adapter=new MyAdapter(TaskSteps.this);
    		setListAdapter(adapter);
			
		
	}
	

    /*==============================================================
     * ListView�б���ʾ�������
     *        ʹ��MyAdapter������������
     * 
     * 
     * 
     * ============================================================
     */
	public List<Map<String, Object>> getData() {
		// TODO �Զ����ɵķ������
		
		
				
		List <Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;		
		
		//��������һ��
		   map=new HashMap<String,Object>();
		   String sc="��������ɡ�ʱ�����������ָ";
		   map.put("stepTime", taskOne.getEndTime());
		   map.put("stepContent", sc);
		   map.put("whichStepNum", "+");
		   map.put("performersImg", R.drawable.over1);
		   map.put("stepId", -1);
		   
		   if(taskOne.getTaskStatus()==-1) {//û�п�ʼ������ȫ�����̱�ʾ
				map.put("whichStepBack", R.drawable.whichstep1);
				map.put("timeTreeDown", R.drawable.timetree1);
				map.put("timeTreeUp", R.drawable.timetree0);
			   }
		   else if(taskOne.getTaskStatus()==0) {//���ڿ�������������ɫ��ʾ����������ɫ��ʾ
				map.put("whichStepBack", R.drawable.whichstep1);
				map.put("timeTreeDown", R.drawable.timetree1);
				map.put("timeTreeUp", R.drawable.timetree0);
			   }
		   else {//�Ѿ���ɣ��������û�ɫ��ʾ
				map.put("whichStepBack", R.drawable.whichstep3);
				map.put("timeTreeDown", R.drawable.timetree3); 
				map.put("timeTreeUp", R.drawable.timetree0);
			   }
		   list.add(map); 
		
		   
		   
		 //�����м䲽��
			if(taskOne.getSteps()!=null) {//�Ѿ��ָ��˲���
			for(int i=0;i<taskOne.getSteps().length;i++) {
				map=new HashMap<String,Object>();
				map.put("stepTime",taskOne.getSteps()[i].getStepEndtime());
				
				map.put("stepContent","�� "+taskOne.getSteps()[i].getStepContent());
				map.put("performersImg", R.drawable.addman);
				map.put("stepId", taskOne.getSteps()[i].getStepId());
				
				//������ֲ�����������
				if(taskOne.getTaskStatus()==-1) {//û�п�ʼ������ȫ�����̱�ʾ
					map.put("whichStepBack", R.drawable.whichstep1);
					map.put("timeTreeUp", R.drawable.timetree1);
					map.put("timeTreeDown", R.drawable.timetree1);
					map.put("whichStepNum", String.valueOf(taskOne.getSteps()[i].getWhichStep()));
				   }
				else if(taskOne.getTaskStatus()==0) {//���ڿ�������������ɫ��ʾ����������ɫ��ʾ
					   if(taskOne.getSteps()[i].isCompleted()) {
						 map.put("whichStepBack", R.drawable.whichstep3);
						 map.put("timeTreeUp", R.drawable.timetree3);
						 map.put("timeTreeDown", R.drawable.timetree3);
						 map.put("whichStepNum", String.valueOf(taskOne.getSteps()[i].getWhichStep()));
						}
						else {
						 map.put("whichStepBack", R.drawable.whichstep1);
						 map.put("timeTreeUp", R.drawable.timetree1);
						 map.put("timeTreeDown", R.drawable.timetree1);	
						 //�ж�ʱ���Ƿ��˸ò������ʱ�䵽������ˣ�����ʾ�޸�״̬���ϣ�������ʾ����
						 
                         LIB lib=new LIB();
                         if(lib.CompNow(taskOne.getSteps()[i].getStepEndtime())>=0) {
                        	 map.put("whichStepNum", "?");
                         }
                         else {
                        	 map.put("whichStepNum", String.valueOf(taskOne.getSteps()[i].getWhichStep())); 
                         }
                         
                         //map.put("whichStepNum", String.valueOf(taskOne.getSteps()[i].getWhichStep())); 
                         
                         
						}				 
				   }
				else {//�Ѿ���ɣ��������û�ɫ��ʾ
					map.put("whichStepBack", R.drawable.whichstep3);
					map.put("timeTreeUp", R.drawable.timetree3); 
					map.put("timeTreeDown", R.drawable.timetree3); 
					map.put("whichStepNum", String.valueOf(taskOne.getSteps()[i].getWhichStep()));
				   }
				list.add(map);
				
			  }
			}
		   
		   
		 //������ʼֵһ��
		   map=new HashMap<String,Object>();
		   String sc0="������ֲ�ʵ��";
		   map.put("stepTime", taskOne.getBeginTime());		   
		   map.put("stepContent", sc0);
		   map.put("whichStepNum", "��");
		   map.put("performersImg", R.drawable.start0);
		   map.put("stepId", -1);
		   if(taskOne.getTaskStatus()==-1) {//û�п�ʼ������ȫ�����̱�ʾ
				map.put("whichStepBack", R.drawable.whichstep1);
				map.put("timeTreeUp", R.drawable.timetree1);
				map.put("timeTreeDown", R.drawable.timetree0);
			   }
		   else if(taskOne.getTaskStatus()==0) {//���ڿ�������������ɫ��ʾ����������ɫ��ʾ
				map.put("whichStepBack", R.drawable.whichstep2);
				map.put("timeTreeUp", R.drawable.timetree2);
				map.put("timeTreeDown", R.drawable.timetree0);
			   }
		   else {//�Ѿ���ɣ��������û�ɫ��ʾ
				map.put("whichStepBack", R.drawable.whichstep3);
				map.put("timeTreeUp", R.drawable.timetree3);
				map.put("timeTreeDown", R.drawable.timetree0);
			   }
		   list.add(map);
		   
		return list;
	}
	public final class ViewHolder{
		//public ImageView taskStatus;
		public TextView stepContent;
		public TextView stepTime;
		public ImageView timeTreeUp;
		public ImageView timeTreeDown;
		public Button whichStep;
		public Button performer;
		}
	
	public class MyAdapter extends BaseAdapter{
		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			// TODO �Զ����ɵĹ��캯�����
			this.mInflater=LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO �Զ����ɵķ������
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO �Զ����ɵķ������	
			
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO �Զ����ɵķ������
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO �Զ����ɵķ������
			
			ViewHolder holder=null;
			
			if(convertView==null) {
				holder=new ViewHolder();
				convertView=mInflater.inflate(R.layout.taskonecontent, null);
				
				holder.stepContent=(TextView)convertView.findViewById(R.id.stepContent);
				holder.stepTime=(TextView)convertView.findViewById(R.id.steptime);
				holder.timeTreeUp=(ImageView)convertView.findViewById(R.id.timetreeup);
				holder.timeTreeDown=(ImageView)convertView.findViewById(R.id.timetreedown);
				holder.whichStep=(Button)convertView.findViewById(R.id.bt_whichstep);
				holder.performer=(Button)convertView.findViewById(R.id.performer);
				convertView.setTag(holder);
			}
			else {
				holder=(ViewHolder)convertView.getTag();				
			}
			//Log.i("status:",String.valueOf((Integer)mData.get(position).get("taskStatus")));
			holder.stepContent.setText((String)mData.get(position).get("stepContent"));
			holder.stepContent.setTextSize(18);
			holder.stepContent.setId((Integer)mData.get(position).get("stepId"));
			holder.stepTime.setText((String)mData.get(position).get("stepTime"));
			holder.stepTime.setId((Integer)mData.get(position).get("stepId"));
			holder.timeTreeUp.setBackgroundResource((Integer)mData.get(position).get("timeTreeUp"));
			holder.timeTreeDown.setBackgroundResource((Integer)mData.get(position).get("timeTreeDown"));
			holder.whichStep.setBackgroundResource((Integer)mData.get(position).get("whichStepBack"));
			
			holder.whichStep.setText((String)mData.get(position).get("whichStepNum"));			
			holder.whichStep.setTextColor(Color.WHITE);
			holder.whichStep.setTextSize(24);
			holder.whichStep.setId((Integer)mData.get(position).get("stepId"));
			
			holder.performer.setId((Integer)mData.get(position).get("stepId"));
			holder.performer.setBackgroundResource((Integer)mData.get(position).get("performersImg"));
			
			holder.performer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// ���Ӳ�����
					//addStepPerformer(v.getId());
					
				}

				});
			holder.stepTime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//�޸�ʱ��ֵ
					final TextView  setEndtime=(TextView)v;
					final String time0=setEndtime.getText().toString();
					String[] ss=time0.split("-");
					int sYear=Integer.valueOf(ss[0]);
					int sMonth=Integer.valueOf(ss[1])-1;
					int sDay=Integer.valueOf(ss[2]);
					new DatePickerDialog(TaskSteps.this,new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year, int month, int day) {
							// ʱ��Ҫ�ȷָ��ԭstepOneʱ��С������һ��ʱ���
							String date=year+"-"+(month+1)+"-"+day;							
							setEndtime.setText(date);
							//��ʱ�䷢�����ģ����޸ĺ��ʱ�䱣�浽���ݿ���
							if(!time0.equals(date)) {
								DBHelper helper=new DBHelper(TaskSteps.this);
								helper.editStepTime(setEndtime.getId(),date);
								}
						}
						
				     },sYear,sMonth,sDay).show();
					
					
					
				}
				
			});
			holder.stepContent.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// ����޸Ĳ�������
					editStepContent(v.getId());
					
				}

				});
			
			holder.whichStep.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					Button step=(Button)v;
					if(String.valueOf(step.getText()).trim().equals("+")){
						//�����"+",�����Ի���,�����Ӳ���
						addTaskStep();
					}
					else if(String.valueOf(step.getText()).trim().equals("��")&&taskOne.getSteps()!=null){
						//����ǿ�ʼ��,�򲻶�
						delTaskStep();
						}
					else if(String.valueOf(step.getText()).trim().equals("?")) {
						//�����ʾΪ"?"����ȷ���Ѿ���ɻ��Ǿ����ӳ�ʱ�䣬�޸�״ֱ̬�ӵ��ʺţ��ӳ�ʱ���޸�ʱ��
						DBHelper helper=new DBHelper(TaskSteps.this);
						helper.editStepStatus(v.getId());
						init();
						
					}
					else{
						//���������ָ�����
						splitTaskStep(v.getId());
					}
					//Log.i("text:",String.valueOf(step.getText()));
					
				}

				

				

				
			});
			
			//���������ʼ��timeTreeDown���ɼ�����������������timeTreeUp���ɼ�
			if(String.valueOf(mData.get(position).get("whichStepNum"))=="��") {
				//holder.timeTreeDown.setVisibility(View.INVISIBLE);
				//holder.performer.setVisibility(View.INVISIBLE);
			}
			if(String.valueOf(mData.get(position).get("whichStepNum"))=="+") {
				//holder.timeTreeUp.setVisibility(View.INVISIBLE);
				//holder.performer.setVisibility(View.INVISIBLE);
			}
			//Log.i("stepTime",String.valueOf(mData.get(position).get("stepTime")));
			///����ǰ������������ƻ�ʱ��Ƚ�
			return convertView;
		}
		
	}
	
	private void addTaskStep() {
		// ����������
		
		final EditText stepContent;
		final EditText poinTime;
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		LayoutInflater inflater1=LayoutInflater.from(this);
		final View view1=inflater1.inflate(R.layout.addstepdialog, null);
		stepContent=(EditText)view1.findViewById(R.id.stepContent);
		poinTime=(EditText)view1.findViewById(R.id.pointime);
		final TextView addOne=(TextView)view1.findViewById(R.id.addOne);
		 
		String title="+["+taskOne.getTaskName()+"]����:";
		poinTime.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// �趨ʱ��
				new DatePickerDialog(TaskSteps.this,new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO �Զ����ɵķ������
						String date=year+"-"+(month+1)+"-"+day;
						poinTime.setText(date);
					}
					
			},2016,0,1).show();
				
			}
			
		});
		
		addOne.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//���Ӳ���
				LinearLayout LL=(LinearLayout)view1.findViewById(R.id.LinearL);
				LinearLayout L1=new LinearLayout(TaskSteps.this);
				EditText stepC=new EditText(TaskSteps.this);
				stepC.setWidth(380);
				stepC.setGravity(1);
				
				EditText pT=new EditText(TaskSteps.this);
				pT.setWidth(190);
				L1.addView(stepC);
				L1.addView(pT);
				LL.addView(L1);
				
			}
			
		});
		
		
		builder.setTitle(title).setView(view1).setPositiveButton("����", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface v, int arg1) {
				// TODO Auto-generated method stub
				
				//String stepC=stepContent.getText().toString().trim();
				//Log.i("content:",String.valueOf(stepContent.getText()));
				
				String pTime=poinTime.getText().toString().trim();
				String stepC=stepContent.getText().toString().trim();
				LIB lib=new LIB();
				
				if(lib.CompABtime(pTime, taskOne.getBeginTime())>0&&lib.CompABtime(taskOne.getEndTime(), pTime)>0){
					
					DBHelper helper=new DBHelper(TaskSteps.this);
					//�������ӵ��²������
					TaskStep step=new TaskStep();
					step.setStepContent(stepC);
					step.setStepEndtime(pTime);
					step.setTaskId(taskOne.getTaskId());
					//û�в���,������Ϊ1,��������+1
					if(taskOne.getSteps()==null){
						step.setWhichStep(1);
					}
					else{
						step.setWhichStep(taskOne.getSteps().length+1);
					}
					//Log.i("whichstep:",String.valueOf(step.getWhichStep()));
					//�ж��Ƿ����
					if(taskOne.getTaskStatus()==-1){
						//����û����,����Ҳû�п���
						step.setCompleted(false);
					}
					else if(taskOne.getTaskStatus()==1){
						//�����Ѿ����
						step.setCompleted(true);
					}
					else{
						//�����ڽ����е�����,�϶�û���
						step.setCompleted(false);
					}
				
				 if(helper.insertTaskStep(step)){
					init();
				}
				}
				else{
					Toast.makeText(TaskSteps.this, "ʱ�䲻���趨����",Toast.LENGTH_SHORT).show();
				}
				
				
			}
		}).setNegativeButton("ȡ��", null).create().show();
		
		
	}
	
	private void delTaskStep() {
		// ����ĳһ������ĳЩ��
		final String [] items=new String[taskOne.getSteps().length];
		final boolean[] isChecked=new boolean[taskOne.getSteps().length];
		//Log.i("length:",String.valueOf(taskOne.getSteps().length));
		for(int i=0;i<taskOne.getSteps().length;i++) {
			items[i]="��"+taskOne.getSteps()[i].getWhichStep()+"����"+taskOne.getSteps()[i].getStepContent();
			//Log.i("items:",i+items[i]);
			isChecked[i]=false;
		}
		new AlertDialog.Builder(this).setTitle("ѡ��Ҫɾ���Ĳ���").setMultiChoiceItems(items, isChecked, new OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean checked) {
				// ѡ��Ҫɾ������
				Log.i("which",String.valueOf(which));
				
			}}).setPositiveButton("ɾ��",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// ɾ������
					int stepid=-1;
					int delN=0;   //����ɾ���˶��ٸ�
					DBHelper helper=new DBHelper(TaskSteps.this);
					for(int i=0;i<items.length;i++) {
						if(isChecked[i]) {
						   delN=delN+1;
				           int whichStep=items.length-i;
				           for(int j=0;j<taskOne.getSteps().length;j++) {
				    	   if(taskOne.getSteps()[j].getWhichStep()==whichStep) {
				    		stepid=taskOne.getSteps()[j].getStepId();
				    	       }
				           }
				           if(stepid>0) {					
					       helper.delSomeStep(stepid);
				           }				    
						}
					}
					//û��ɾ�꣬����whichstepֵ
					if(items.length>delN) {
					helper.adjustStepsValue(taskOne.getTaskId());
					}
					init();
				}
			}).setNegativeButton("ȡ��", null).create().show();
		
	}
	private void splitTaskStep(final int stepid) {
		//�ָ�ĳһ��,1�������Ի��򣬻�ȡ���ݣ���ʵ�ִ洢��2������whichStepֵ
		
		stepOne=new TaskStep();
		DBHelper helper=new DBHelper(this);
		stepOne=helper.findOneStep(stepid);
		if(stepOne==null){return;}
		//Log.i("content111:",stepOne.getStepContent());
		
		LayoutInflater inflater=LayoutInflater.from(this);
		final View view=inflater.inflate(R.layout.addstepdialog, null);
		final TextView addOne=(TextView)view.findViewById(R.id.addOne);
		stepContent[0]=(EditText)view.findViewById(R.id.stepContent);
		poinTime[0]=(EditText)view.findViewById(R.id.pointime);
		
		stepContent[0].setText(stepOne.getStepContent());
		poinTime[0].setText(stepOne.getStepEndtime());
		stepContent[0].setTextColor(Color.GRAY);
		poinTime[0].setTextColor(Color.GRAY);
		new AlertDialog.Builder(this).setIcon(R.drawable.slit).setTitle("�ָ�").setView(view).setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// ���ȷ������������
				
				TaskStep[] addSteps=new TaskStep[splitNum];
				for(int i=0;i<splitNum;i++){
					if(stepContent[i+1].getText().toString().trim().equals("")||poinTime[i+1].getText().toString().trim().equals("")){break;}
					addSteps[i]=new TaskStep();
					addSteps[i].setStepContent(stepContent[i+1].getText().toString());
					addSteps[i].setStepEndtime(poinTime[i+1].getText().toString());
					addSteps[i].setCompleted(stepOne.isCompleted());
					addSteps[i].setWhichStep(stepOne.getWhichStep()+i);
					addSteps[i].setTaskId(stepOne.getTaskId());
					
				}
				//Log.i("CONTENT:",String.valueOf(addSteps[0].getStepContent()));
				DBHelper helper=new DBHelper(TaskSteps.this);
				
				helper.addSteps(addSteps);    //���Ի����е�������ӵ����ݿ���
				helper.delSomeStep(stepid);   //ɾ�����ݿ���ĳһ��ԭֵ
				helper.adjustStepsValue(taskOne.getTaskId());//������whichStep���е���,>stepid������+splitNumֵ
				init();
				
			}}).setNegativeButton("ȡ��", null).create().show();
		
		addOne.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//���Ӳ���
				
				splitNum=splitNum+1;     //����һ��������Ϣ
				if(splitNum>=10){return;}//���ָ��9������
				//stepContent=new EditText[splitNum+1];
				//poinTime=new EditText[splitNum+1];
				LinearLayout LL=(LinearLayout)view.findViewById(R.id.LinearL);
				LinearLayout L1=new LinearLayout(TaskSteps.this);
				
				stepContent[splitNum]=new EditText(TaskSteps.this);
				stepContent[splitNum].setWidth(190);//��ֵ���Ը����������ݸı�
				stepContent[splitNum].setGravity(1);
				stepContent[splitNum].setId(splitNum);
				
				poinTime[splitNum]=new EditText(TaskSteps.this);
				poinTime[splitNum].setWidth(95);
				poinTime[splitNum].setId(splitNum);
				L1.addView(stepContent[splitNum]);
				L1.addView(poinTime[splitNum]);
				LL.addView(L1);
				
				String[] ss=stepOne.getStepEndtime().split("-");
				
				final int sYear=Integer.valueOf(ss[0]);
				final int sMonth=Integer.valueOf(ss[1])-1;
				final int sDay=Integer.valueOf(ss[2]);
				//Log.i("yy-mm-dd",sYear+"."+sMonth+"."+sDay);
				poinTime[splitNum].setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// �趨ʱ��
						final TextView pt=(TextView)v;
						//Log.i("id-split:",String.valueOf(pt.getId()+"-"+String.valueOf(splitNum)));
						new DatePickerDialog(TaskSteps.this,new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year, int month, int day) {
								// ʱ��Ҫ�ȷָ��ԭstepOneʱ��С������һ��ʱ���
								String date=year+"-"+(month+1)+"-"+day;
								
								poinTime[pt.getId()].setText(date);
							}
							
					},sYear,sMonth,sDay).show();
						
					}
					
				});
			}
			
		});
		
	}


	
	private void editStepContent(final int stepid) {
		// �༭step����
		LayoutInflater inflater=LayoutInflater.from(this);
		final View view=inflater.inflate(R.layout.editstepcontent, null); 
		final EditText stepContent=(EditText)view.findViewById(R.id.etContent);
		DBHelper helper=new DBHelper(TaskSteps.this);
		TaskStep step=helper.findOneStep(stepid);
		stepContent.setText(step.getStepContent());
		new AlertDialog.Builder(this).setIcon(R.drawable.edit).setTitle("�޸�����").setView(view).setPositiveButton("�޸�", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// �����޸ĺ��ֵ
				DBHelper h1=new DBHelper(TaskSteps.this);
				h1.updateStepContent(stepid,stepContent.getText().toString());
				init();
			}
		}).setNegativeButton("ȡ��", null).create().show();
		
	}
	
	
	private void addStepPerformer(int stepid) {
		// ��ĳ���������Ӳ�����
		//Log.i("stepid add:",String.valueOf(stepid));
		
		LayoutInflater inflater=LayoutInflater.from(this);
		final View view=inflater.inflate(R.layout.addperformer, null); 
		AutoCompleteTextView autoT1=(AutoCompleteTextView)view.findViewById(R.id.selectPerformers);
		DBHelper helper=new DBHelper(this);
		String[][] performers=helper.listPersons(0, true);
		String[] sWords = new String[performers.length];
		Log.i("len:",String.valueOf(sWords.length));
		for(int i=0;i<performers.length;i++) {
			sWords[i]=performers[i][1];
			//Log.i("name:",sWords[i]);
		}	
		
		ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,sWords);
		autoT1.setAdapter(adapter);
		new AlertDialog.Builder(this).setTitle("+ ���Ӳ�����").setView(view).setPositiveButton("����", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO �Զ����ɵķ������
				
			}
		}).setNegativeButton("ȡ��", null).create().show();
		//�ַ�������Performers[][]�趨��ѯ��Χ
			
		
		autoT1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				  
				TextView tv = (TextView)v; 
				String keyWords=tv.getText().toString().trim();
				
				Log.i("arg0",String.valueOf(keyWords));
				
				
			}});
			
	}
	
	
	
}
