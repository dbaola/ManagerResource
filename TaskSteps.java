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
	
	//步骤分割
	private int splitNum=0;          //分割为几个步骤
	private EditText[] stepContent=new EditText[10];  //定义可编辑view的数组
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
	    	Toast.makeText(this, "id传值错误", Toast.LENGTH_SHORT).show();
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
		// 列表显示步骤图景
		
		DBHelper helper=new DBHelper(TaskSteps.this);
		//helper.adjustStepsValue(taskOne.getTaskId());
    	taskOne=helper.findTaskOne(taskOne.getTaskId());//默认情况下查询表中所有内容
    	
			//显示步骤画面
			mData=getData();
    		MyAdapter adapter=new MyAdapter(TaskSteps.this);
    		setListAdapter(adapter);
			
		
	}
	

    /*==============================================================
     * ListView列表显示相关任务
     *        使用MyAdapter，自制适配器
     * 
     * 
     * 
     * ============================================================
     */
	public List<Map<String, Object>> getData() {
		// TODO 自动生成的方法存根
		
		
				
		List <Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;		
		
		//创建结束一行
		   map=new HashMap<String,Object>();
		   String sc="↓任务完成。时间轴控制任务分割。";
		   map.put("stepTime", taskOne.getEndTime());
		   map.put("stepContent", sc);
		   map.put("whichStepNum", "+");
		   map.put("performersImg", R.drawable.over1);
		   map.put("stepId", -1);
		   
		   if(taskOne.getTaskStatus()==-1) {//没有开始的任务全部用绿表示
				map.put("whichStepBack", R.drawable.whichstep1);
				map.put("timeTreeDown", R.drawable.timetree1);
				map.put("timeTreeUp", R.drawable.timetree0);
			   }
		   else if(taskOne.getTaskStatus()==0) {//正在开启，开启处红色表示，结束处绿色表示
				map.put("whichStepBack", R.drawable.whichstep1);
				map.put("timeTreeDown", R.drawable.timetree1);
				map.put("timeTreeUp", R.drawable.timetree0);
			   }
		   else {//已经完成，开启处用灰色表示
				map.put("whichStepBack", R.drawable.whichstep3);
				map.put("timeTreeDown", R.drawable.timetree3); 
				map.put("timeTreeUp", R.drawable.timetree0);
			   }
		   list.add(map); 
		
		   
		   
		 //创建中间步骤
			if(taskOne.getSteps()!=null) {//已经分割了步骤
			for(int i=0;i<taskOne.getSteps().length;i++) {
				map=new HashMap<String,Object>();
				map.put("stepTime",taskOne.getSteps()[i].getStepEndtime());
				
				map.put("stepContent","→ "+taskOne.getSteps()[i].getStepContent());
				map.put("performersImg", R.drawable.addman);
				map.put("stepId", taskOne.getSteps()[i].getStepId());
				
				//对任务分步完成情况设置
				if(taskOne.getTaskStatus()==-1) {//没有开始的任务全部用绿表示
					map.put("whichStepBack", R.drawable.whichstep1);
					map.put("timeTreeUp", R.drawable.timetree1);
					map.put("timeTreeDown", R.drawable.timetree1);
					map.put("whichStepNum", String.valueOf(taskOne.getSteps()[i].getWhichStep()));
				   }
				else if(taskOne.getTaskStatus()==0) {//正在开启，开启处红色表示，结束处绿色表示
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
						 //判断时间是否到了该步，如果时间到了完成了，则显示修改状态符合，否则显示数字
						 
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
				else {//已经完成，开启处用灰色表示
					map.put("whichStepBack", R.drawable.whichstep3);
					map.put("timeTreeUp", R.drawable.timetree3); 
					map.put("timeTreeDown", R.drawable.timetree3); 
					map.put("whichStepNum", String.valueOf(taskOne.getSteps()[i].getWhichStep()));
				   }
				list.add(map);
				
			  }
			}
		   
		   
		 //创建开始值一行
		   map=new HashMap<String,Object>();
		   String sc0="↑任务分步实现";
		   map.put("stepTime", taskOne.getBeginTime());		   
		   map.put("stepContent", sc0);
		   map.put("whichStepNum", "—");
		   map.put("performersImg", R.drawable.start0);
		   map.put("stepId", -1);
		   if(taskOne.getTaskStatus()==-1) {//没有开始的任务全部用绿表示
				map.put("whichStepBack", R.drawable.whichstep1);
				map.put("timeTreeUp", R.drawable.timetree1);
				map.put("timeTreeDown", R.drawable.timetree0);
			   }
		   else if(taskOne.getTaskStatus()==0) {//正在开启，开启处红色表示，结束处绿色表示
				map.put("whichStepBack", R.drawable.whichstep2);
				map.put("timeTreeUp", R.drawable.timetree2);
				map.put("timeTreeDown", R.drawable.timetree0);
			   }
		   else {//已经完成，开启处用灰色表示
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
			// TODO 自动生成的构造函数存根
			this.mInflater=LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO 自动生成的方法存根	
			
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO 自动生成的方法存根
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			
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
					// 增加参与者
					//addStepPerformer(v.getId());
					
				}

				});
			holder.stepTime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//修改时间值
					final TextView  setEndtime=(TextView)v;
					final String time0=setEndtime.getText().toString();
					String[] ss=time0.split("-");
					int sYear=Integer.valueOf(ss[0]);
					int sMonth=Integer.valueOf(ss[1])-1;
					int sDay=Integer.valueOf(ss[2]);
					new DatePickerDialog(TaskSteps.this,new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year, int month, int day) {
							// 时间要比分割的原stepOne时间小，比上一步时间大
							String date=year+"-"+(month+1)+"-"+day;							
							setEndtime.setText(date);
							//当时间发生更改，将修改后的时间保存到数据库中
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
					// 点击修改步骤内容
					editStepContent(v.getId());
					
				}

				});
			
			holder.whichStep.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					Button step=(Button)v;
					if(String.valueOf(step.getText()).trim().equals("+")){
						//如果是"+",弹出对话框,新增加步骤
						addTaskStep();
					}
					else if(String.valueOf(step.getText()).trim().equals("—")&&taskOne.getSteps()!=null){
						//如果是开始点,则不动
						delTaskStep();
						}
					else if(String.valueOf(step.getText()).trim().equals("?")) {
						//如果显示为"?"，是确定已经完成还是决定延长时间，修改状态直接点问号，延长时间修改时间
						DBHelper helper=new DBHelper(TaskSteps.this);
						helper.editStepStatus(v.getId());
						init();
						
					}
					else{
						//其它情况则分割任务
						splitTaskStep(v.getId());
					}
					//Log.i("text:",String.valueOf(step.getText()));
					
				}

				

				

				
			});
			
			//如果是任务开始，timeTreeDown不可见；如果是任务结束，timeTreeUp不可见
			if(String.valueOf(mData.get(position).get("whichStepNum"))=="—") {
				//holder.timeTreeDown.setVisibility(View.INVISIBLE);
				//holder.performer.setVisibility(View.INVISIBLE);
			}
			if(String.valueOf(mData.get(position).get("whichStepNum"))=="+") {
				//holder.timeTreeUp.setVisibility(View.INVISIBLE);
				//holder.performer.setVisibility(View.INVISIBLE);
			}
			//Log.i("stepTime",String.valueOf(mData.get(position).get("stepTime")));
			///将当前的日期与任务计划时间比较
			return convertView;
		}
		
	}
	
	private void addTaskStep() {
		// 增加任务步骤
		
		final EditText stepContent;
		final EditText poinTime;
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		LayoutInflater inflater1=LayoutInflater.from(this);
		final View view1=inflater1.inflate(R.layout.addstepdialog, null);
		stepContent=(EditText)view1.findViewById(R.id.stepContent);
		poinTime=(EditText)view1.findViewById(R.id.pointime);
		final TextView addOne=(TextView)view1.findViewById(R.id.addOne);
		 
		String title="+["+taskOne.getTaskName()+"]步骤:";
		poinTime.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// 设定时间
				new DatePickerDialog(TaskSteps.this,new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO 自动生成的方法存根
						String date=year+"-"+(month+1)+"-"+day;
						poinTime.setText(date);
					}
					
			},2016,0,1).show();
				
			}
			
		});
		
		addOne.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//增加步骤
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
		
		
		builder.setTitle(title).setView(view1).setPositiveButton("增加", new DialogInterface.OnClickListener(){

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
					//建立增加的新步骤参数
					TaskStep step=new TaskStep();
					step.setStepContent(stepC);
					step.setStepEndtime(pTime);
					step.setTaskId(taskOne.getTaskId());
					//没有步骤,则设置为1,否则设置+1
					if(taskOne.getSteps()==null){
						step.setWhichStep(1);
					}
					else{
						step.setWhichStep(taskOne.getSteps().length+1);
					}
					//Log.i("whichstep:",String.valueOf(step.getWhichStep()));
					//判断是否完成
					if(taskOne.getTaskStatus()==-1){
						//任务没开启,过程也没有开启
						step.setCompleted(false);
					}
					else if(taskOne.getTaskStatus()==1){
						//任务已经完成
						step.setCompleted(true);
					}
					else{
						//对正在进行中的任务,肯定没完成
						step.setCompleted(false);
					}
				
				 if(helper.insertTaskStep(step)){
					init();
				}
				}
				else{
					Toast.makeText(TaskSteps.this, "时间不在设定区域",Toast.LENGTH_SHORT).show();
				}
				
				
			}
		}).setNegativeButton("取消", null).create().show();
		
		
	}
	
	private void delTaskStep() {
		// 减少某一步或者某些步
		final String [] items=new String[taskOne.getSteps().length];
		final boolean[] isChecked=new boolean[taskOne.getSteps().length];
		//Log.i("length:",String.valueOf(taskOne.getSteps().length));
		for(int i=0;i<taskOne.getSteps().length;i++) {
			items[i]="第"+taskOne.getSteps()[i].getWhichStep()+"步："+taskOne.getSteps()[i].getStepContent();
			//Log.i("items:",i+items[i]);
			isChecked[i]=false;
		}
		new AlertDialog.Builder(this).setTitle("选择要删除的步骤").setMultiChoiceItems(items, isChecked, new OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean checked) {
				// 选择要删除的项
				Log.i("which",String.valueOf(which));
				
			}}).setPositiveButton("删除",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// 删除操作
					int stepid=-1;
					int delN=0;   //计算删除了多少个
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
					//没有删完，调整whichstep值
					if(items.length>delN) {
					helper.adjustStepsValue(taskOne.getTaskId());
					}
					init();
				}
			}).setNegativeButton("取消", null).create().show();
		
	}
	private void splitTaskStep(final int stepid) {
		//分割某一步,1、弹出对话框，获取内容，并实现存储；2、调整whichStep值
		
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
		new AlertDialog.Builder(this).setIcon(R.drawable.slit).setTitle("分割").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// 如果确定就增加数据
				
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
				
				helper.addSteps(addSteps);    //将对话框中的内容添加到数据库中
				helper.delSomeStep(stepid);   //删除数据库中某一步原值
				helper.adjustStepsValue(taskOne.getTaskId());//对任务whichStep进行调整,>stepid的所有+splitNum值
				init();
				
			}}).setNegativeButton("取消", null).create().show();
		
		addOne.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//增加步骤
				
				splitNum=splitNum+1;     //增加一个输入信息
				if(splitNum>=10){return;}//最多分割成9个步骤
				//stepContent=new EditText[splitNum+1];
				//poinTime=new EditText[splitNum+1];
				LinearLayout LL=(LinearLayout)view.findViewById(R.id.LinearL);
				LinearLayout L1=new LinearLayout(TaskSteps.this);
				
				stepContent[splitNum]=new EditText(TaskSteps.this);
				stepContent[splitNum].setWidth(190);//数值可以根据以上内容改变
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
						// 设定时间
						final TextView pt=(TextView)v;
						//Log.i("id-split:",String.valueOf(pt.getId()+"-"+String.valueOf(splitNum)));
						new DatePickerDialog(TaskSteps.this,new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year, int month, int day) {
								// 时间要比分割的原stepOne时间小，比上一步时间大
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
		// 编辑step内容
		LayoutInflater inflater=LayoutInflater.from(this);
		final View view=inflater.inflate(R.layout.editstepcontent, null); 
		final EditText stepContent=(EditText)view.findViewById(R.id.etContent);
		DBHelper helper=new DBHelper(TaskSteps.this);
		TaskStep step=helper.findOneStep(stepid);
		stepContent.setText(step.getStepContent());
		new AlertDialog.Builder(this).setIcon(R.drawable.edit).setTitle("修改内容").setView(view).setPositiveButton("修改", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// 更新修改后的值
				DBHelper h1=new DBHelper(TaskSteps.this);
				h1.updateStepContent(stepid,stepContent.getText().toString());
				init();
			}
		}).setNegativeButton("取消", null).create().show();
		
	}
	
	
	private void addStepPerformer(int stepid) {
		// 给某个步骤增加参与者
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
		new AlertDialog.Builder(this).setTitle("+ 增加参与者").setView(view).setPositiveButton("增加", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO 自动生成的方法存根
				
			}
		}).setNegativeButton("取消", null).create().show();
		//字符串数组Performers[][]设定查询范围
			
		
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
