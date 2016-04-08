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
		//ʹ��ListView��������������г�
		//ListView ls=(ListView)findViewById(android.R.id.list);
		/*
		noteslist=datasource.findAll();
		ArrayAdapter<Task> adapter=new ArrayAdapter<Task>(this,android.R.id.list,noteslist);
		setListAdapter(adapter);
		*/
			
	}
    
    public void init() {
		//����ʾ����
    	DBHelper helpers=new DBHelper(Tasks.this);
    	tasks=helpers.findTasks();//Ĭ������²�ѯ������������
    	
    	if(tasks!=null){
    		//��������ѯ��Ϊ�յ�,��ʹ��listView��ʾ����
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
     * ListView�б���ʾ�������
     *        ʹ��MyAdapter������������
     * 
     * 
     * 
     * ============================================================
     */
	private List<Map<String, Object>> getData() {
		// TODO �Զ����ɵķ������
		
		
				
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
			///����ǰ������������ƻ�ʱ��Ƚ�
			SimpleDateFormat dfs=new SimpleDateFormat("yyyy-MM-dd");
			String curTime=dfs.format(new Date());
			String bgTime=(String)mData.get(position).get("beginTime");
			String edTime=(String)mData.get(position).get("endTime");
			LIB lib=new LIB();
			if(lib.CompABtime(curTime,bgTime)<0){holder.taskControlStatus.setVisibility(View.INVISIBLE);}  //û�е�����ʱ��
			else if(lib.CompABtime(curTime,edTime)>0){
				      if((Integer)mData.get(position).get("taskStatus")!=R.drawable.over){
				      holder.taskControlStatus.setBackgroundResource(R.drawable.end);
				      holder.taskControlStatus.setText("����");
				      holder.taskControlStatus.setTextSize(0);
				     
				      }
				      else{holder.taskControlStatus.setVisibility(View.INVISIBLE);}
					}  //�Ѿ�����
			else{
				      if((Integer)mData.get(position).get("taskStatus")!=R.drawable.go){
					  holder.taskControlStatus.setBackgroundResource(R.drawable.start);
					  holder.taskControlStatus.setText("����");
					  holder.taskControlStatus.setTextSize(0);
				      }
				      else{holder.taskControlStatus.setVisibility(View.INVISIBLE);}			
			}    //���ڿ�����
			
			holder.Llayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					//Log.i("taskid:",String.valueOf(v.getId()));
					//popTaskOneContent(v.getId());  //v.getId()Ϊ����Id��
					
					Intent intent=new Intent();
					intent.putExtra("taskid", v.getId());					
					intent.setClass(Tasks.this,TaskSteps.class);
					startActivity(intent);
				}
			});
			
			
			holder.taskControlStatus.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// �޸����ݿ�����״ֵ̬,ʹ������״̬��ʱ��һ��
					int taskId=v.getId();
					//Log.i("taskId",String.valueOf(taskId));
					Button taskControlS=(Button)v;
					//�����ŤҪ��������,��taskStatus=0���ݵ����ݿ�,���Ҫ�����,��taskStatus=1
					DBHelper helper=new DBHelper(Tasks.this);
					//Log.i("text",String.valueOf(taskControlS.getText()));
					if(taskControlS.getText().equals("����")){
						//Log.i("tag","start");
						helper.AdjustTaskStatus(taskId,0);
						init();
					}
					else if(taskControlS.getText().equals("����")){
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
		// ��Ť,���ʱ�䵽��,ѯ���Ƿ���������
		new AlertDialog.Builder(this).setTitle("����ʱ�䵽��").setMessage("�Ƿ���������?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO �Զ����ɵķ������
				
			}}).show();
		
	}
	
	///////////////////////////////////////////////////////////////////////�б����
	///////////////////////�˵�

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.tasks_menu, menu);
		
		return true;
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().toString().trim().equals("���")) {
			AddTaskDialog();
			return true;
		}
		else if(item.getTitle().toString().trim().equals("���")) {
			delAllTasks();
			init();
			return true;
		}
		
		return false;
	}
	public void delAllTasks() {
		// ����������������
		DBHelper helper=new DBHelper(this);
		helper.clearTasks();
		init();
		
	}

	////////////////////////�������
	public void AddTaskDialog() {
		// ��������Ի���
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
				//������ʼʱ�����õĶԻ���,����ʱ��
				new DatePickerDialog(Tasks.this,new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO �Զ����ɵķ������
						String date=year+"-"+(month+1)+"-"+day;
						beginTime.setText(date);
					}
					
			},2016,0,6).show();
				
			}
		});
    	
        endTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��������ʱ�����õĶԻ���,����ʱ��
				new DatePickerDialog(Tasks.this,new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						// TODO �Զ����ɵķ������
						String date=year+"-"+(month+1)+"-"+day;
						endTime.setText(date);
					}
					
			},2016,0,6).show();
				
			}
		});
    	
		builder.setTitle("+��������").setView(view1).setPositiveButton("ȷ��",new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// ������������
				taskOne=new Task();
				
				if(!taskName.getText().toString().trim().equals("")){
					taskOne.setTaskName(taskName.getText().toString());
					if(!taskGoal.getText().toString().trim().equals("")){
					taskOne.setTaskGoal(taskGoal.getText().toString());}
					if(!beginTime.getText().toString().trim().equals("")){
					taskOne.setBeginTime(beginTime.getText().toString());}
					if(!endTime.getText().toString().trim().equals("")){
					taskOne.setEndTime(endTime.getText().toString());}
					//�����ʼʱ��>����ʱ�䣬�򲻽���¼��
					LIB lib=new LIB();
					if(lib.CompABtime(taskOne.getBeginTime(), taskOne.getEndTime())<0) {
						DBHelper helper=new DBHelper(Tasks.this);
						boolean isOk=helper.saveTaskone(taskOne);
						if(isOk==true){
						//Toast.makeText(Tasks.this, "���ݱ���ɹ�", Toast.LENGTH_SHORT).show();
						init();
						}
						else {
							Toast.makeText(Tasks.this, "���ݱ���ʧ��", Toast.LENGTH_SHORT).show();
							init();
						}	
					}
					
					else {
						Toast.makeText(Tasks.this, "����ʱ��ҪС�ڽ���ʱ�䣬����ʧ��", Toast.LENGTH_SHORT).show();
						init();
					}
					
				}
				
			}}).setNegativeButton("ȡ��", null).create().show();
	}
	
	 ///////////////////////////���ܣ���ʾ������ϸ����
	/*
	public void popTaskOneContent(int taskOneId) {
		// �ؼ���ȡ�ֲ���������ӷֲ�����
		DBHelper helper=new DBHelper(Tasks.this);
		Task taskOne=new Task();
		taskOne=helper.findTaskOne(taskOneId);
		
        AlertDialog.Builder builder=new AlertDialog.Builder(this);		
    	LayoutInflater inflater1=LayoutInflater.from(this);
    	View view2=inflater1.inflate(R.layout.taskonecontent, null);
    	builder.setTitle(taskOne.getTaskName()+"�ֲ�ʵ�֣�").setView(view2).setPositiveButton("�˳�",new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// 
				
			}}).create().show();
		
	}
	*/

}
