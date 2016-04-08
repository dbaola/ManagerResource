                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     package com.ex.somebody;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ex.task.Task;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Tasks extends ListActivity{
	private Task taskOne;
	private List<Map<String,Object>>mData;
	private String[][] tasks;
	
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasks);
		init();
		//Log.i("ceshi","ok");
		//使用ListView组件将所有任务列出
		//ListView ls=(ListView)findViewById(android.R.id.list);
		/*
		noteslist=datasource.findAll();
		ArrayAdapter<Task> adapter=new ArrayAdapter<Task>(this,android.R.id.list,noteslist);
		setListAdapter(adapter);
		*/
			
	}
    
    public void init() {
		//主显示界面
    	DBHelper helpers=new DBHelper(Tasks.this);
    	tasks=helpers.findTasks();//默认情况下查询表中所有内容
    	
    	if(tasks!=null){
    		//如果任务查询不为空的,则使用listView显示内容
    		/*
    		for(int i=0;i<tasks.length;i++) {
    			Log.i("name:",String.valueOf(tasks[i][0])+tasks[i][1]+tasks[i][2]+tasks[i][3]+tasks[i][4]+tasks[i][5]);
    		}*/
    		mData=getData();
    		MyAdapter adapter=new MyAdapter(this);
    		setListAdapter(adapter);
    	}
    	
		
	}
    
    /*==============================================================
     * ListView列表显示相关任务
     *        使用MyAdapter，自制适配器
     * 
     * 
     * 
     * ============================================================
     */
	private List<Map<String, Object>> getData() {
		// TODO 自动生成的方法存根
		
		
				
		List <Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		for(int i=0;i<tasks.length;i++) {
			map=new HashMap<String,Object>();
			map.put("taskid",tasks[i][0]);
			map.put("taskName", tasks[i][1]);
			map.put("taskGoal",tasks[i][2]);
			map.put("beginTime", tasks[i][3]);
			map.put("endTime", tasks[i][4]);
			//map.put("taskStatus",statusLabel[0]);
			if(tasks[i][5].toString().trim().equals("0")) {map.put("taskStatus",R.drawable.go);}
			else if(tasks[i][5].toString().trim().equals("1")){map.put("taskStatus",R.drawable.over);}
			else {map.put("taskStatus",R.drawable.ready);}
			//Log.i("tasks:",tasks[i][5].toString().trim());
				
			list.add(map);
			
		}
		return list;
	}
	public final class ViewHolder{
		public ImageView taskStatus;
		public TextView taskName;
		public TextView taskGoal;
		public Button taskControlStatus;
		public LinearLayout Llayout;
	}
	
	@SuppressLint("SimpleDateFormat")
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
				convertView=mInflater.inflate(R.layout.taskitems, null);
				
				holder.taskStatus=(ImageView)convertView.findViewById(R.id.taskStatus);
				holder.taskName=(TextView)convertView.findViewById(R.id.taskName);
				holder.taskGoal=(TextView)convertView.findViewById(R.id.taskGoal);
				holder.taskControlStatus=(Button)convertView.findViewById(R.id.taskControlStatus);
				holder.Llayout=(LinearLayout)convertView.findViewById(R.id.LLayout);
				convertView.setTag(holder);
			}
			else {
				holder=(ViewHolder)convertView.getTag();				
			}
			//Log.i("status:",String.valueOf((Integer)mData.get(position).get("taskStatus")));
			holder.taskStatus.setBackgroundResource((Integer)mData.get(position).get("taskStatus"));
			holder.taskName.setText((String)mData.get(position).get("taskName"));
			holder.taskGoal.setText((String)mData.get(position).get("taskGoal"));
			
			//Log.i("taskid",String.valueOf(mData.get(position).get("taskid")));
			holder.taskControlStatus.setId(Integer.valueOf((String.valueOf( mData.get(position).get("taskid")))));
			holder.Llayout.setId(Integer.valueOf((String.valueOf( mData.get(position).get("taskid")))));
			///将当前的日期与任务计划时间比较
			SimpleDateFormat dfs=new SimpleDateFormat("yyyy-MM-dd");
			String curTime=dfs.format(new Date());
			String bgTime=(String)mData.get(position).get("beginTime");
			String edTime=(String)mData.get(position).get("endTime");
			LIB lib=new LIB();
			if(lib.CompABtime(curTime,bgTime)<0){holder.taskControlStatus.setVisibility(View.INVISIBLE);}  //没有到开启时间
			else if(lib.CompABtime(curTime,edTime)>0){
				      if((Integer)mData.get(position).get("taskStatus")!=R.drawable.over){
				      holder.taskControlStatus.setBackgroundResource(R.drawable.end);
				      holder.taskControlStatus.setText("结束");
				      holder.taskControlStatus.setTextSize(0);
				     
				      }
				      else{holder.taskControlStatus.setVisibility(View.INVISIBLE);}
					}  //已经结束
			else{
				      if((Integer)mData.get(position).get("taskStatus")!=R.drawable.go){
					  holder.taskControlStatus.setBackgroundResource(R.drawable.start);
					  holder.taskControlStatus.setText("开启");
					  holder.taskControlStatus.setTextSize(0);
				      }
				      else{holder.taskControlStatus.setVisibility(View.INVISIBLE);}			
			}    //正在开启中
			
			holder.Llayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					//Log.i("taskid:",String.valueOf(v.getId()));
					//popTaskOneContent(v.getId());  //v.getId()为任务Id号
					
					Intent intent=new Intent();
					intent.putExtra("taskid", v.getId());					
					intent.setClass(Tasks.this,TaskSteps.class);
					startActivity(intent);
				}
			});
			
			
			holder.taskControlStatus.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 修改数据库任务状态值,使得任务状态与时间一致
					int taskId=v.getId();
					//Log.i("taskId",String.valueOf(taskId));
					Button taskControlS=(Button)v;
					//如果按扭要求开启任务,则将taskStatus=0传递到数据库,如果要求结束,则taskStatus=1
					DBHelper helper=new DBHelper(Tasks.this);
					//Log.i("text",String.valueOf(taskControlS.getText()));
					if(taskControlS.getText().equals("开启")){
						//Log.i("tag","start");
						helper.AdjustTaskStatus(taskId,0);
						init();
					}
					else if(taskControlS.getText().equals("结束")){
						helper.AdjustTaskStatus(taskId,1);
						//Log.i("tag","over");
						init();
					}
					else{init();}
					
				}
			});
			
			
			
			return convertView;
		}
		
	}
	
	public void showInfo() {
		// 按扭,如果时间到了,询问是否启用任务
		new AlertDialog.Builder(this).setTitle("任务时间到了").setMessage("是否开启该任务?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO 自动生成的方法存根
				
			}}).show();
		
	}
	
	///////////////////////////////////////////////////////////////////////列表结束
	///////////////////////菜单

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.tasks_menu, menu);
		
		return true;
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().toString().trim().equals("添加")) {
			AddTaskDialog();
			return true;
		}
		else if(item.getTitle().toString().trim().equals("清除")) {
			delAllTasks();
			init();
			return true;
		}
		
		return false;
	}
	public void delAllTasks() {
		// 清除任务表所有数据
		DBHelper helper=new DBHelper(this);
		helper.clearTasks();
		init();
		
	}

	////////////////////////任务添加
	public void AddTaskDialog() {
		// 增加任务对话框
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		
    	LayoutInflater inflater1=LayoutInflater.from(this);
    	View view1=inflater1.inflate(R.layout.edittask, null);
    	final EditText taskName=(EditText)view1.findViewById(R.id.taskName);
    	final EditText taskGoal=(EditText)view1.findViewById(R.id.taskGoal);
    	final EditText beginTime=(EditText)view1.findViewById(R.id.beginTime);
    	final EditText endTime=(EditText)view1.findViewById(R.id.endTime);
    	
    	beginTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//弹出开始时间设置的对话框,设置时间
				new DatePickerDialog(Tasks.this,new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO 自动生成的方法存根
						String date=year+"-"+(month+1)+"-"+day;
						beginTime.setText(date);
					}
					
			},2016,0,6).show();
				
			}
		});
    	
        endTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//弹出结束时间设置的对话框,设置时间
				new DatePickerDialog(Tasks.this,new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO 自动生成的方法存根
						String date=year+"-"+(month+1)+"-"+day;
						endTime.setText(date);
					}
					
			},2016,0,6).show();
				
			}
		});
    	
		builder.setTitle("+增加任务").setView(view1).setPositiveButton("确定",new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// 保存任务数据
				taskOne=new Task();
				
				if(!taskName.getText().toString().trim().equals("")){
					taskOne.setTaskName(taskName.getText().toString());
					if(!taskGoal.getText().toString().trim().equals("")){
					taskOne.setTaskGoal(taskGoal.getText().toString());}
					if(!beginTime.getText().toString().trim().equals("")){
					taskOne.setBeginTime(beginTime.getText().toString());}
					if(!endTime.getText().toString().trim().equals("")){
					taskOne.setEndTime(endTime.getText().toString());}
					//如果开始时间>结束时间，则不进行录入
					LIB lib=new LIB();
					if(lib.CompABtime(taskOne.getBeginTime(), taskOne.getEndTime())<0) {
						DBHelper helper=new DBHelper(Tasks.this);
						boolean isOk=helper.saveTaskone(taskOne);
						if(isOk==true){
						//Toast.makeText(Tasks.this, "内容保存成功", Toast.LENGTH_SHORT).show();
						init();
						}
						else {
							Toast.makeText(Tasks.this, "内容保存失败", Toast.LENGTH_SHORT).show();
							init();
						}	
					}
					
					else {
						Toast.makeText(Tasks.this, "开启时间要小于结束时间，保存失败", Toast.LENGTH_SHORT).show();
						init();
					}
					
				}
				
			}}).setNegativeButton("取消", null).create().show();
	}
	
	 ///////////////////////////功能：显示任务详细内容
	/*
	public void popTaskOneContent(int taskOneId) {
		// 关键获取分步步骤和增加分步步骤
		DBHelper helper=new DBHelper(Tasks.this);
		Task taskOne=new Task();
		taskOne=helper.findTaskOne(taskOneId);
		
        AlertDialog.Builder builder=new AlertDialog.Builder(this);		
    	LayoutInflater inflater1=LayoutInflater.from(this);
    	View view2=inflater1.inflate(R.layout.taskonecontent, null);
    	builder.setTitle(taskOne.getTaskName()+"分步实现：").setView(view2).setPositiveButton("退出",new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// 
				
			}}).create().show();
		
	}
	*/

}
