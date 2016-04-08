package com.ex.somebody;
import com.ex.task.Task;
import com.ex.task.TaskStep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper {
	//定义表名
	
	private static final String DATABASE_NAME="somebodydata";                  
	private static final int DATABASE_VERSION=1;
	private static final String TABLE_NAME="person";                              //COLUMNS
	private static final String TABLE_NAME_RELATEDPERSON="person_relatives";      //COLUMNS1
	private static final String TABLE_NAME_TASK="task";                           //COLUMNS2
	private static final String TABLE_NAME_TASKSTEP="taskstep";                   //COLUMNS3
	private static final String TABLE_NAME_PERFORMER="performer";                 //COLUMNS3
	
	private static final String[] COLUMNS={"_somebodyid","name","sex","birthday","nativePlace","nickName","telephone","email","qq","weibo","boke","wechat","address","needsAdd","height","weight","bloodType","nationnality","party","married","healthCondition","occupation","workUnits","workTitle","income","characterType","hobbyAdd","strongPointAdd","weakPointAdd","personalWealth","socialClass","educationalStatus","cohesion","relationship"};
	private static final String[] COLUMNS1= {"_id","relatedPersonType","relatedPersonName","_somebodyid","_relatedSomebodyid"};//_id为主键
	private static final String[] COLUMNS2= {"_taskid","taskName","taskGoal","beginTime","endTime","taskStatus"};
	private static final String[] COLUMNS3= {"_stepid","whichStep","stepContent","stepEndtime","isCompleted","_taskid"};
	private static final String[] COLUMNS4= {"_id","_taskid","_stepid","performerid","performertask"};
	private DBOpenHelper helper;
	private static SQLiteDatabase db;
	
	////////////////////////////////////////////////////////////////////////////////
	//////任务处理数据//////////////////////////////////////////////////////////////
	private TaskStep[] tss;//任务步骤
	private class Performer{
		int[] performerId;
		String[] performerTask;
	}
	
	

	//
	private static class DBOpenHelper extends SQLiteOpenHelper{
		               //创建表格
		//private static final String CREATE_TABLE="create table "+TABLE_NAME+" ("+COLUMNS[0]+" integer primary key autoincrement,"+COLUMNS[1]+" text,"+COLUMNS[2]+" text,"+COLUMNS[3]+" text,"+COLUMNS[4]+" text,"+COLUMNS[5]+" text,"+COLUMNS[6]+" text,"+COLUMNS[7]+" text,"+COLUMNS[8]+" text,"+COLUMNS[9]+" text,"+COLUMNS[10]+" text,"+COLUMNS[11]+" text,"+COLUMNS[12]+" text,"+COLUMNS[13]+" text,"+COLUMNS[14]+" float,"+COLUMNS[15]+" float,"+COLUMNS[16]+" text,"+COLUMNS[17]+" text,"+COLUMNS[18]+" text,"+COLUMNS[19]+" boolean,"+COLUMNS[20]+" boolean,"+COLUMNS[21]+" text,"+COLUMNS[22]+" text,"+COLUMNS[23]+" text not null,"+COLUMNS[24]+" text not null,"+COLUMNS[25]+" integer not null,"+COLUMNS[26]+" text not null,"+COLUMNS[27]+" text not null,"+COLUMNS[28]+" text not null,"+COLUMNS[29]+" integer not null,"+COLUMNS[30]+" integer not null,"+COLUMNS[31]+" integer not null,"+COLUMNS[32]+" integer not null,"+COLUMNS[33]+" text not null);";
		private static final String CREATE_TABLE="create table "+TABLE_NAME+" ("+COLUMNS[0]+" integer primary key autoincrement,"+COLUMNS[1]+" text not null,"+COLUMNS[2]+" text not null,"+COLUMNS[3]+" text not null,"+COLUMNS[4]+" text not null,"+COLUMNS[5]+" text not null,"+COLUMNS[6]+" text not null,"+COLUMNS[7]+" text not null,"+COLUMNS[8]+" text not null,"+COLUMNS[9]+" text not null,"+COLUMNS[10]+" text not null,"+COLUMNS[11]+" text not null,"+COLUMNS[12]+" text not null,"+COLUMNS[13]+" text not null,"+COLUMNS[14]+" float not null,"+COLUMNS[15]+" float not null,"+COLUMNS[16]+" text not null,"+COLUMNS[17]+" text not null,"+COLUMNS[18]+" text not null,"+COLUMNS[19]+" boolean not null,"+COLUMNS[20]+" boolean not null,"+COLUMNS[21]+" text not null,"+COLUMNS[22]+" text not null,"+COLUMNS[23]+" text not null,"+COLUMNS[24]+" text not null,"+COLUMNS[25]+" integer not null,"+COLUMNS[26]+" text not null,"+COLUMNS[27]+" text not null,"+COLUMNS[28]+" text not null,"+COLUMNS[29]+" integer not null,"+COLUMNS[30]+" integer not null,"+COLUMNS[31]+" integer not null,"+COLUMNS[32]+" integer not null,"+COLUMNS[33]+" text not null);";
		
		
		public DBOpenHelper(Context context){
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
			}
		@Override
		public void onCreate(SQLiteDatabase db){
			//Log.i("table",CREATE_TABLE1);
			db.execSQL(CREATE_TABLE);
			
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			            // 删除旧表格，创建新表格
			db.execSQL("drop table if exists "+TABLE_NAME);
			//db.execSQL("drop table if exists "+TABLE_NAME_RELATEDPERSON);
			onCreate(db);
			
		}
		     
	}                   
	//创建SQLiteOpenHelper对象
	public DBHelper(Context context){    
		helper=new DBOpenHelper(context);
		db=helper.getWritableDatabase();
	}
	//插入数据,变量批量插入
	public int insert(Somebody somebody1){
		ContentValues values=new ContentValues();
		values.put(COLUMNS[1], somebody1.getName());
		values.put(COLUMNS[2], somebody1.getSex());
		values.put(COLUMNS[3], somebody1.getBirthday());
		values.put(COLUMNS[4], somebody1.getNativePlace());
		values.put(COLUMNS[5], somebody1.getNickName());
		values.put(COLUMNS[6], somebody1.getTelephone());
		values.put(COLUMNS[7], somebody1.getEmail());
		values.put(COLUMNS[8], somebody1.getQq());
		values.put(COLUMNS[9], somebody1.getWeibo());
		values.put(COLUMNS[10], somebody1.getBoke());
		values.put(COLUMNS[11], somebody1.getWechat());
		values.put(COLUMNS[12], somebody1.getAddress());
		values.put(COLUMNS[13], DataAdd(somebody1.getNeeds()));
		values.put(COLUMNS[14], somebody1.getHeight());
		values.put(COLUMNS[15], somebody1.getWeight());
		values.put(COLUMNS[16], somebody1.getBloodType());
		values.put(COLUMNS[17], somebody1.getNationality());
		values.put(COLUMNS[18], somebody1.getParty());
		values.put(COLUMNS[19], somebody1.isMarried());
		values.put(COLUMNS[20], somebody1.isHealthCondition());
		values.put(COLUMNS[21], somebody1.getOccupation());
		values.put(COLUMNS[22], somebody1.getWorkUnits());
		values.put(COLUMNS[23], somebody1.getWorkTitle());
		values.put(COLUMNS[24], somebody1.getIncome());
		values.put(COLUMNS[25], somebody1.getCharacterType());
		values.put(COLUMNS[26], DataAdd(somebody1.getHobby()));
		values.put(COLUMNS[27], DataAdd(somebody1.getStrongPoint()));
		values.put(COLUMNS[28], DataAdd(somebody1.getWeakPoint()));
		values.put(COLUMNS[29], somebody1.getPersonalWealth());
		values.put(COLUMNS[30], somebody1.getSocialClass());
		values.put(COLUMNS[31], somebody1.getEducationalStatus());
		values.put(COLUMNS[32], somebody1.getCohesion());
		values.put(COLUMNS[33], somebody1.getRelationship());
		
		String[][] relatedPerson=somebody1.getRelatedPerson();   //产生一个二维数组
		int somebody1_id;
		Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid"},null,null,null,null,null);
		if(cursor.getCount()>0) {
			cursor.moveToLast();
			somebody1_id=Integer.valueOf(cursor.getString(0))+1;  //加1才得当前的ID号
		}
		else
		{
			somebody1_id=1;                                     //新表设置为1
		}
		//String somebody_id=String.valueOf(values.get("_somebodyid"));
		//Log.i("name value",String.valueOf(somebody1_id));
			
			
		db.insert(TABLE_NAME,null, values);
		
		//添加关系资源
		if(!String.valueOf(relatedPerson[0][0]).equals("未知")) {
		addRelatedPerson(somebody1_id,relatedPerson);
		}
		
		//db.query(TABLE_NAME,new String[] {"_somebodyid"},null,null,null,null,null);
		//cursor.close();
		//Log.i("id",String.valueOf(somebody1_id));
		//db.close();
		return somebody1_id;
		
	}
	    
	    //插入一个人几个特定的信息，主要用于电话簿批量数据导入和更新
	    public int insertContact(Somebody somebody1){
		ContentValues values=new ContentValues();
		values.put(COLUMNS[1], somebody1.getName());
		values.put(COLUMNS[6], somebody1.getTelephone());
		
		int somebody1_id;
		Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid"},null,null,null,null,null);
		if(cursor.getCount()>0) {
			cursor.moveToLast();
			somebody1_id=Integer.valueOf(cursor.getString(0))+1;  //加1才得当前的ID号
		}
		else
		{
			somebody1_id=1;                                     //新表设置为1
		}
		db.insert(TABLE_NAME,null, values);
		return somebody1_id;
	}
	
	
	private void addRelatedPerson(int somebody1_id,String[][] relatedPerson) {
		// 用于添加关系资源的方法
		//Log.i("TABLE",CREATE_TABLE_ADDRELATEDPERSON);
		final String CREATE_TABLE1="create table if not exists "+TABLE_NAME_RELATEDPERSON+" ("+COLUMNS1[0]+" integer primary key autoincrement,"+COLUMNS1[1]+" text not null,"+COLUMNS1[2]+" text not null,"+COLUMNS1[3]+" integer not null,"+COLUMNS1[4]+" integer not null);";
		db.execSQL(CREATE_TABLE1);
		ContentValues values1=new ContentValues();
		
		//Log.i("lenth",String.valueOf(relatedPerson[0][0])+String.valueOf(relatedPerson[0][1])+String.valueOf(relatedPerson[2][1]));
		//db.execSQL("insert into "+TABLE_NAME_RELATEDPERSON+" VALUES (NULL,?,?,?,NULL)",new String[]{relatedPerson[i][0],relatedPerson[i][1],somebody1_id});
		for(int i=0;i<relatedPerson.length;i++){
		if(String.valueOf(relatedPerson[i][1]).trim().equals("null")) {break;}
		//relatedPerson[i][2]=somebody1_id;
		values1.put(COLUMNS1[1], relatedPerson[i][0]);
		values1.put(COLUMNS1[2], relatedPerson[i][1]);
		values1.put(COLUMNS1[3], somebody1_id);     
		values1.put(COLUMNS1[4], Integer.valueOf(relatedPerson[i][3]));   //实际将0值给relatedSomebody的id值，待以后查找统一分配id值
		
		
		//Log.i("lenth",String.valueOf(values1.get(COLUMNS1[1]))+"|"+String.valueOf(values1.get(COLUMNS1[2]))+"|"+values1.get(COLUMNS1[3]));
		
		db.insert(TABLE_NAME_RELATEDPERSON, null, values1);
		
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
	
	
	
	//查询数据
	public Somebody query(String person_name,int person_id){
		Somebody person=new Somebody();
	
		Cursor cursor=db.query(TABLE_NAME,null, "name='"+person_name+"' and _somebodyid='"+person_id+"'", null,null, null, null);
		//Cursor cursor=db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
		if(cursor.getCount()>0){ //如查询记录数>0
			cursor.moveToFirst();
			person.setId(cursor.getInt(0));
			person.setName(cursor.getString(1));
			person.setSex(cursor.getString(2));
			//Log.i("cursornum",String.valueOf(cursor.getString(1)));
			
			person.setBirthday(cursor.getString(3));
			person.setNativePlace(cursor.getString(4));
			person.setNickName(cursor.getString(5));
			person.setTelephone(cursor.getString(6));
			person.setEmail(cursor.getString(7));
			person.setQq(cursor.getString(8));
			person.setWeibo(cursor.getString(9));
			person.setBoke(cursor.getString(10));
			person.setWechat(cursor.getString(11));
			person.setAddress(cursor.getString(12));
			String[] need=cursor.getString(13).split("、");
			person.setNeeds(need);
			person.setHeight(cursor.getFloat(14));
			person.setWeight(cursor.getFloat(15));
			
			person.setBloodType(cursor.getString(16));
			person.setNationality(cursor.getString(17));
			person.setParty(cursor.getString(18));
			person.setMarried(Boolean.valueOf(cursor.getString(19)));
			person.setHealthCondition(Boolean.valueOf(cursor.getString(20)));
			person.setOccupation(cursor.getString(21));
			person.setWorkUnits(cursor.getString(22));
			person.setWorkTitle(cursor.getString(23));
			person.setIncome(cursor.getString(24));
			person.setCharacterType(cursor.getInt(25));
			String[] hobby=cursor.getString(26).split("、");
			person.setHobby(hobby);
			String[] strongPoint=cursor.getString(27).split("、");
			person.setStrongPoint(strongPoint);
			String[] weakPoint=cursor.getString(28).split("、");
			person.setWeakPoint(weakPoint);
			person.setPersonalWealth(cursor.getInt(29));
			person.setSocialClass(cursor.getInt(30));
			person.setEducationalStatus(cursor.getInt(31));
			person.setCohesion(cursor.getInt(32));			
			person.setRelationship(cursor.getString(33));
			person.setRelatedPerson(queryRelatedPerson(person_id));//根据person_id查询其它信息
			
			return person;
		}
		cursor.close();
		db.close();
		return null;
		
		
	}
	private String[][] queryRelatedPerson(int person_id) {
		// 将二维字符串数组的值返回
		String [][] relatedPerson = new String [10][5];
		Cursor cursor1=db.query(TABLE_NAME_RELATEDPERSON,null, "_somebodyid='"+person_id+"'", null,null, null, null);
		cursor1.moveToFirst();
		if(cursor1.getCount()>0){
			for(int i=0;i<cursor1.getCount();i++){
				relatedPerson[i][0]=cursor1.getString(0);  //表_id，主键
				relatedPerson[i][1]=cursor1.getString(1);  //relatedPersonType，关系类型
				relatedPerson[i][2]=cursor1.getString(2);  //relatedPersonName,关联人名
				relatedPerson[i][3]=cursor1.getString(3);  //somebodyid，中心人物id
				relatedPerson[i][4]=cursor1.getString(4);  //relatedPersonId，关联人id
				cursor1.moveToNext();
			}
		}
		return relatedPerson;
	}
	
	
	public String[][] listPersons(int updownDepend, boolean updownFlag) {
		// TODO 自动生成的方法存根
		String[][] personsInfo = new String[1000][5];   //初始化人员信息的大小
		switch(updownDepend) {
		case 1:{
			//socialClass   官位
			
		if(updownFlag==true) {
		    Cursor cursor=db.query(TABLE_NAME, new String[] {"_somebodyid","name","sex","socialClass"}, null, null, null, null, "socialClass desc");
			if(cursor.getCount()>0) {
				//Log.i("count",String.valueOf(cursor.getCount()));
				cursor.moveToFirst();
				for(int i=0;i<cursor.getCount();i++){
					personsInfo[i][0]=String.valueOf(cursor.getInt(0));
		    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
		    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
		    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
		    		cursor.moveToNext();
		    		//Log.i("0 value",String.valueOf(personsInfo[i][0]));
				}
			}
		}
		else {
			Cursor cursor=db.query(TABLE_NAME, new String[] {"_somebodyid","name","sex","socialClass"}, null, null, null, null, "socialClass");					
			if(cursor.getCount()>0) {
				//Log.i("count",String.valueOf(cursor.getCount()));
				cursor.moveToFirst();
				for(int i=0;i<cursor.getCount();i++){
				personsInfo[i][0]=String.valueOf(cursor.getInt(0));
	    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
	    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
	    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
	    		cursor.moveToNext();
	    		//Log.i("0 value",String.valueOf(personsInfo[i][0]));
			  }
			}
			
		 }
		break;	
		}
		
		case 2:{     //personalWealth 财富
			if(updownFlag==true){
				Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","personalWealth"},null, null, null,null,"personalWealth desc");
			    if(cursor.getCount()>0) {
			    	//Log.i("count", String.valueOf(cursor.getCount()));
			    	cursor.moveToFirst();
			    	
			    	for(int i=0;i<cursor.getCount();i++) {
			    		
			    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
			    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
			    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
			    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
			    		
			    		//Log.i("wealth", String.valueOf(personsInfo[i][3]));
			    	    cursor.moveToNext();    
			    	
			    	}
			    	
			    }}
				else{
					Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","personalWealth"},null, null, null,null,"personalWealth");
				    if(cursor.getCount()>0) {
				    	//Log.i("count", String.valueOf(cursor.getCount()));
				    	cursor.moveToFirst();
				    	
				    	for(int i=0;i<cursor.getCount();i++) {
				    		
				    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
				    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
				    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
				    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
				    		
				    		//Log.i("wealth", String.valueOf(personsInfo[i][3]));
				    	    cursor.moveToNext();    
				    	
				    	}
				    	
				    }
					
				}
			break;
		}
		case 3:{
			if(updownFlag==true){
			//educationalStatus   知识
			Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","educationalStatus"},null, null, null,null,"educationalStatus");
		    if(cursor.getCount()>0) {
		    	//Log.i("count", String.valueOf(cursor.getCount()));
		    	cursor.moveToFirst();
		    	
		    	for(int i=0;i<cursor.getCount();i++) {
		    		
		    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
		    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
		    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
		    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
		    		
		    		//Log.i("name", String.valueOf(personsInfo[i][2]));
		    	    cursor.moveToNext();    
		    	
		    	}
		    	
		    }}
			else{
				Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","educationalStatus"},null, null, null,null,"educationalStatus desc");
			    if(cursor.getCount()>0) {
			    	//Log.i("count", String.valueOf(cursor.getCount()));
			    	cursor.moveToFirst();
			    	
			    	for(int i=0;i<cursor.getCount();i++) {
			    		
			    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
			    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
			    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
			    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
			    		
			    		//Log.i("name", String.valueOf(personsInfo[i][2]));
			    	    cursor.moveToNext();    
			    	
			    	}
			    	
			    }
				
			}
		break;	
		}
		case 4:{
			//cohesion  亲密度
			if(updownFlag==true){
			Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","cohesion","relationship"},null, null, null,null,"cohesion");
		    if(cursor.getCount()>0) {
		    	//Log.i("count", String.valueOf(cursor.getCount()));
		    	cursor.moveToFirst();
		    	
		    	for(int i=0;i<cursor.getCount();i++) {
		    		
		    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
		    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
		    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
		    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
		    		personsInfo[i][4]=String.valueOf(cursor.getString(4));
		    		
		    		//Log.i("name", String.valueOf(personsInfo[i][2]));
		    	    cursor.moveToNext();    
		    	
		    	}
		    	
		    }}
			else{
				Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","cohesion","relationship"},null, null, null,null,"cohesion desc");
			    if(cursor.getCount()>0) {
			    	//Log.i("count", String.valueOf(cursor.getCount()));
			    	cursor.moveToFirst();
			    	
			    	for(int i=0;i<cursor.getCount();i++) {
			    		
			    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
			    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
			    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
			    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
			    		personsInfo[i][4]=String.valueOf(cursor.getString(4));
			    		
			    		//Log.i("name", String.valueOf(personsInfo[i][2]));
			    	    cursor.moveToNext();    
			    	
			    	}
			    	
			    }
				
			}
		break;	
		}
		case 0:{//无特殊要求，则按照id顺序逆序排列后推送到二维数组,主要显示联系方式
			if(updownFlag==true){
			Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","telephone"},null, null, null,null,"_somebodyid desc");
		    if(cursor.getCount()>0) {
		    	//Log.i("count", String.valueOf(cursor.getCount()));
		    	cursor.moveToFirst();
		    	
		    	for(int i=0;i<cursor.getCount();i++) {
		    		
		    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
		    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
		    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
		    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
		    		
		    		//Log.i("name", String.valueOf(personsInfo[i][2]));
		    	    cursor.moveToNext();    
		    	
		    	}
		    	
		    }}
			else{
				Cursor cursor=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","telephone"},null, null, null,null,"_somebodyid");
			    if(cursor.getCount()>0) {
			    	//Log.i("count", String.valueOf(cursor.getCount()));
			    	cursor.moveToFirst();
			    	
			    	for(int i=0;i<cursor.getCount();i++) {
			    		
			    		personsInfo[i][0]=String.valueOf(cursor.getInt(0));
			    		personsInfo[i][1]=String.valueOf(cursor.getString(1));
			    		personsInfo[i][2]=String.valueOf(cursor.getString(2));
			    		personsInfo[i][3]=String.valueOf(cursor.getString(3));
			    		
			    		//Log.i("name", String.valueOf(personsInfo[i][2]));
			    	    cursor.moveToNext();    
			    	
			    	}
			    	
			    }
				
			}
		}
		}
		    
			//Log.i("updownFlag", String.valueOf(updownFlag));
			
		return personsInfo;
	}
	
	public Somebody dataWasher(Somebody somebody1)   //对somebody数据进行过滤，使之符合插入数据库条件
	{
		
		if(String.valueOf(somebody1.getName()).equals("null")) {somebody1.setName("noname");}
		//Log.i("testValue:",String.valueOf(somebody1.getSex()));
		if(String.valueOf(somebody1.getSex()).equals("null")) {somebody1.setSex("男");}
		if(String.valueOf(somebody1.getBirthday()).equals("null")) {somebody1.setBirthday("未知");}
		if(String.valueOf(somebody1.getNativePlace()).equals("null")) {somebody1.setNativePlace("未知");}
		if(String.valueOf(somebody1.getNickName()).equals("null")) {somebody1.setNickName("未知");}
		if(String.valueOf(somebody1.getTelephone()).equals("null")) {somebody1.setTelephone("未知");}
		if(String.valueOf(somebody1.getEmail()).equals("null")) {somebody1.setEmail("未知");}
		if(String.valueOf(somebody1.getQq()).equals("null")) {somebody1.setQq("未知");}
		if(String.valueOf(somebody1.getWeibo()).equals("null")) {somebody1.setWeibo("未知");}
		if(String.valueOf(somebody1.getBoke()).equals("null")) {somebody1.setBoke("未知");}
		if(String.valueOf(somebody1.getWechat()).equals("null")) {somebody1.setWechat("未知");}
		if(String.valueOf(somebody1.getAddress()).equals("null")) {somebody1.setAddress("未知");}
		if(String.valueOf(somebody1.getNeeds()).equals("null")) {String[] need= {"未知"};somebody1.setNeeds(need);}
		if(String.valueOf(somebody1.getHeight()).equals("null")) {somebody1.setHeight(0);}
		if(String.valueOf(somebody1.getWeight()).equals("null")) {somebody1.setWeight(0);}
		if(String.valueOf(somebody1.getBloodType()).equals("null")) {somebody1.setBloodType("未知");}
		if(String.valueOf(somebody1.getNationality()).equals("null")) {somebody1.setNationality("未知");}
		if(String.valueOf(somebody1.getParty()).equals("null")) {somebody1.setParty("未知");}
		if(String.valueOf(somebody1.isMarried()).equals("null")) {somebody1.setMarried(false);}
		if(String.valueOf(somebody1.isHealthCondition()).equals("null")) {somebody1.setHealthCondition(true);}
		if(String.valueOf(somebody1.getOccupation()).equals("null")) {somebody1.setOccupation("未知");}
		if(String.valueOf(somebody1.getWorkUnits()).equals("null")) {somebody1.setWorkUnits("未知");}
		if(String.valueOf(somebody1.getWorkTitle()).equals("null")) {somebody1.setWorkTitle("未知");}
		if(String.valueOf(somebody1.getIncome()).equals("null")) {somebody1.setIncome("未知");}
		if(String.valueOf(somebody1.getCharacterType()).equals("null")) {somebody1.setCharacterType(0);}
		if(String.valueOf(somebody1.getHobby()).equals("null")) {String[] hobby= {"未知"};somebody1.setHobby(hobby);}
		if(String.valueOf(somebody1.getStrongPoint()).equals("null")) {String[] strongPoint= {"未知"};somebody1.setStrongPoint(strongPoint);}
		if(String.valueOf(somebody1.getWeakPoint()).equals("null")) {String[] weakPoint= {"未知"};somebody1.setWeakPoint(weakPoint);}
		if(String.valueOf(somebody1.getPersonalWealth()).equals("null")) {somebody1.setPersonalWealth(5);}
		if(String.valueOf(somebody1.getSocialClass()).equals("null")) {somebody1.setSocialClass(8);}
		if(String.valueOf(somebody1.getEducationalStatus()).equals("null")) {somebody1.setEducationalStatus(6);}
		if(String.valueOf(somebody1.getCohesion()).equals("null")) {somebody1.setCohesion(0);}
		if(String.valueOf(somebody1.getRelationship()).equals("null")) {somebody1.setRelationship("未知");}
		if(String.valueOf(somebody1.getRelatedPerson()).equals("null")) {String[][] relatedPerson= {{"未知"},{"未知"}};somebody1.setRelatedPerson(relatedPerson);}
		return somebody1;		
	}
	public boolean update(Somebody person) {
		//更新person的数据
		ContentValues values=new ContentValues();
		values.put(COLUMNS[1], person.getName());
		values.put(COLUMNS[2], person.getSex());
		values.put(COLUMNS[3], person.getBirthday());
		values.put(COLUMNS[4], person.getNativePlace());
		values.put(COLUMNS[5], person.getNickName());
		values.put(COLUMNS[6], person.getTelephone());
		values.put(COLUMNS[7], person.getEmail());
		values.put(COLUMNS[8], person.getQq());
		values.put(COLUMNS[9], person.getWeibo());
		values.put(COLUMNS[10], person.getBoke());
		values.put(COLUMNS[11], person.getWechat());
		values.put(COLUMNS[12], person.getAddress());
		values.put(COLUMNS[13], DataAdd(person.getNeeds()));
		values.put(COLUMNS[14], person.getHeight());
		values.put(COLUMNS[15], person.getWeight());
		values.put(COLUMNS[16], person.getBloodType());
		values.put(COLUMNS[17], person.getNationality());
		values.put(COLUMNS[18], person.getParty());
		values.put(COLUMNS[19], person.isMarried());
		values.put(COLUMNS[20], person.isHealthCondition());
		values.put(COLUMNS[21], person.getOccupation());
		values.put(COLUMNS[22], person.getWorkUnits());
		values.put(COLUMNS[23], person.getWorkTitle());
		values.put(COLUMNS[24], person.getIncome());
		values.put(COLUMNS[25], person.getCharacterType());
		values.put(COLUMNS[26], DataAdd(person.getHobby()));
		values.put(COLUMNS[27], DataAdd(person.getStrongPoint()));
		values.put(COLUMNS[28], DataAdd(person.getWeakPoint()));
		values.put(COLUMNS[29], person.getPersonalWealth());
		values.put(COLUMNS[30], person.getSocialClass());
		values.put(COLUMNS[31], person.getEducationalStatus());
		values.put(COLUMNS[32], person.getCohesion());
		values.put(COLUMNS[33], person.getRelationship());
		String[] ids= {String.valueOf(person.getId())};
		db.update(TABLE_NAME, values, "_somebodyid=?",ids );
		//Log.i("更新人数：",String.valueOf(upn));
		String[][] relatedPerson=person.getRelatedPerson();
		if(!(String.valueOf(person.getRelatedPerson()[0][0]).equals("null"))) {
			updateRelatedPerson(person.getId(),relatedPerson);
			/*
			for(int i=0;i<relatedPerson.length;i++) {
				Log.i("tableId|type|name",relatedPerson[i][0]+"|"+relatedPerson[i][1]+"|"+relatedPerson[i][2]);
			}*/
		  }
		return true;
	}
	private void updateRelatedPerson(int id, String[][] relatedPerson) {
		// 保持没有更改的值不变，删除其它值，增加新值
        
		
		//Log.i("lenth",String.valueOf(relatedPerson[0][0])+String.valueOf(relatedPerson[0][1])+String.valueOf(relatedPerson[2][1]));
		//db.execSQL("insert into "+TABLE_NAME_RELATEDPERSON+" VALUES (NULL,?,?,?,NULL)",new String[]{relatedPerson[i][0],relatedPerson[i][1],somebody1_id});
       
        //1、清除旧有的记录
        String[] ids = new String[10];
        int k=0;
        
        for(int i=0;i<relatedPerson.length;i++) {
        	if(String.valueOf(relatedPerson[i][2]).equals("null")) {
        		//Log.i("i:",String.valueOf(i));
        		break;}//没有姓名值就跳出
        	
        	if(!relatedPerson[i][0].equals("0")) {
        		ids[k]=relatedPerson[i][0];
        		//Log.i("ids:",ids[k]);
        		k=k+1;
        		
        	}
        }
      
       Cursor cursor=db.query(TABLE_NAME_RELATEDPERSON, new String[] {"_id","_somebodyid"}, "_somebodyid='"+id+"'",  null, null, null,null);
       //Log.i("cursor count:",String.valueOf(cursor.getCount()));
       cursor.moveToFirst();
       
       for(int j=0;j<cursor.getCount();j++) {
    	   String cTabId=String.valueOf(cursor.getInt(0));
    	   //判断表中表的id值是否ids[k]中存在,不存在删除记录
    	   int index=-1;
    	   for(int n=0;n<k;n++) {
    		   if(cTabId.equals(ids[n])) {
    			   index=n;
    			   break;
    		   }
    	   }
    	   if(index>=0) {return;}
    	   else {//表中_id不在ids[]中
    		  // Log.i("del id:",cTabId);
    	   db.execSQL("delete from "+TABLE_NAME_RELATEDPERSON+" where _id="+cTabId+";");   
    	   }
    	   cursor.moveToNext();
       } 
       //2、后增加改变了的记录
       
        for(int i=0;i<relatedPerson.length;i++) {
        	if(String.valueOf(relatedPerson[i][2]).equals("null")) {break;}//没有值就跳出
        	                                                 //用一个字符串数组记录表中没有改变的relatedPerson对应的id
        	if(relatedPerson[i][0].equals("0")) {
        		//为”0“表示表中无记录，增加新记录
        		ContentValues value1s=new ContentValues();
        		value1s.put(COLUMNS1[1], relatedPerson[i][1]);
        		value1s.put(COLUMNS1[2], relatedPerson[i][2]);
        		value1s.put(COLUMNS1[3], relatedPerson[i][3]);
        		value1s.put(COLUMNS1[4], relatedPerson[i][4]);
        		db.insert(TABLE_NAME_RELATEDPERSON, null, value1s);
        	}
        	else {
        		//不为”0“表示表中有没改动的记录，不进行动作
        		
        		return;
        		
        	}
        }
        
        
        
		
		
		//Log.i("ivalue", String.valueOf(n));
	}
	public int delSomebody(int personId, String personName) {
		// 删除某人的所有信息，分两步：删除主表数据和删除分表数据
		String[] ids= {String.valueOf(personId)};
		db.delete(TABLE_NAME_RELATEDPERSON, "_somebodyid=?", ids);
		return db.delete(TABLE_NAME, "_somebodyid=?", ids);
		
	}
	
	//特殊关键词查询
	private String[][] ssPersons;
	
	public String[][] searchSpecialPersons(String keyWords,String className){
		//Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","nativePlace"}, "nativePlace='"+keyWords+"'", null,null, null, null);
		
		//Log.i("className|keyWords",className+"|"+keyWords);
		//Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","nativePlace"}, "nativePlace='"+keyWords+"'", null,null, null, null);
	    
		
		if(className.equals("籍贯")) {
			//查询籍贯为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","nativePlace"}, "nativePlace='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				Log.i("count1:",String.valueOf(cursorS.getCount()));
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				//Log.i("name",ssPersons[i][1]);
				cursorS.moveToNext();
			} 
			
			}
			else{
				return null;
			}
			
		}
		else if(className.equals("职业")) {
			//查询职业为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","occupation"}, "occupation='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		else if(className.equals("单位")) {
			//查询单位为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","workUnits"}, "workUnits='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		else if(className.equals("性格")) {
			//查询性格为keyWords的人员
			
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","characterType"}, "characterType='"+keyWords+"'", null,null, null, null);
			Log.i("性格",String.valueOf(cursorS.getCount()));
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		else if(className.equals("爱好")) {
			//查询爱好为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","hobby"}, "hobby='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		else if(className.equals("关系")) {
			//查询关系为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","relationship"}, "relationship='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		else if(className.equals("教育程度")) {
			//查询教育程度为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","educationStatus"}, "educationalStatus='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		else if(className.equals("职务级别")) {
			//查询职务级别为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","socialClass"},"socialClass='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		else if(className.equals("个人财富")) {
			//查询职业为keyWords的人员
			Cursor cursorS=db.query(TABLE_NAME,new String[] {"_somebodyid","name","sex","personalWealth"}, "personalWealth='"+keyWords+"'", null,null, null, null);
			if(cursorS.getCount()>0) {
				cursorS.moveToFirst();
			ssPersons=new String[cursorS.getCount()][5];
			for(int i=0;i<cursorS.getCount();i++){
				ssPersons[i][0]=String.valueOf(cursorS.getInt(0));
				ssPersons[i][1]=String.valueOf(cursorS.getString(1));
				ssPersons[i][2]=String.valueOf(cursorS.getString(2));
				ssPersons[i][3]=String.valueOf(cursorS.getString(3));
				cursorS.moveToNext();
			} 
			}
			else{
				return null;
			}
		}
		
		return ssPersons;
	}
	
	
	/////保存某个任务到任务栏目中
	    
		public boolean saveTaskone(Task taskOne) {
			final String CREATE_TASK="create table if not exists "+TABLE_NAME_TASK+" ("+COLUMNS2[0]+" integer primary key autoincrement,"+COLUMNS2[1]+" text,"+COLUMNS2[2]+" text,"+COLUMNS2[3]+" text,"+COLUMNS2[4]+" text,"+COLUMNS2[5]+" integer);";
			db.execSQL(CREATE_TASK);
			ContentValues values=new ContentValues();
			values.put(COLUMNS2[1], taskOne.getTaskName());
			//Log.i("name:",String.valueOf(taskOne.getTaskName()));
			values.put(COLUMNS2[2], taskOne.getTaskGoal());
			values.put(COLUMNS2[3], taskOne.getBeginTime());     
			values.put(COLUMNS2[4], taskOne.getEndTime()); 
			values.put(COLUMNS2[5], -1);
			
			if(db.insert(TABLE_NAME_TASK, null, values)>0) {
			return true;}
			else {
		    return false;
			}
		}
		public String[][] findTasks() {			
			String[][] tasks;
			//如果没有数据表，则创建一个
			final String CREATE_TASK="create table if not exists "+TABLE_NAME_TASK+" ("+COLUMNS2[0]+" integer primary key autoincrement,"+COLUMNS2[1]+" text,"+COLUMNS2[2]+" text,"+COLUMNS2[3]+" text,"+COLUMNS2[4]+" text,"+COLUMNS2[5]+" integer);";
			db.execSQL(CREATE_TASK);
			
			Cursor cursor=db.query(TABLE_NAME_TASK, null,null,  null, null, null,COLUMNS2[0]+" desc");
			
			if(cursor.getCount()>0){
				tasks=new String[cursor.getCount()][6];
				cursor.moveToFirst();
				
				for(int i=0;i<cursor.getCount();i++){
					//Log.i("name:",String.valueOf(cursor.getString(1)));
					tasks[i][0]=String.valueOf(cursor.getInt(0));
					tasks[i][1]=cursor.getString(1);
					tasks[i][2]=cursor.getString(2);
					tasks[i][3]=cursor.getString(3);
					tasks[i][4]=cursor.getString(4);
					if(String.valueOf(cursor.getInt(5))!=null) {
					tasks[i][5]=String.valueOf(cursor.getInt(5));}
					cursor.moveToNext();
				}
			
			return tasks;
			}
			else{
			return null;
			}
			
			//return null;
		
			
		}
		public void clearTasks() {
			// 清除任务表中所有的任务内容及附表中内容
			//db.execSQL("delete from "+TABLE_NAME_TASK); 
			db.execSQL("drop table if exists "+TABLE_NAME_TASK);
			db.execSQL("drop table if exists "+TABLE_NAME_TASKSTEP);
			db.execSQL("drop table if exists "+TABLE_NAME_PERFORMER);
		}
		public void AdjustTaskStatus(int taskId, int taskStatusSet) {
			// 调整任务状态,开启或者结束,主要修改数据库中taskStatus值
			///将当前的日期与任务计划时间比较
			ContentValues values=new ContentValues();
			values.put(COLUMNS2[5], taskStatusSet);
			String[] taskids= {String.valueOf(taskId)};
			db.update(TABLE_NAME_TASK, values, "_taskid=?",taskids );
			//Log.i("count:",String.valueOf(count));
		}
		public Task findTaskOne(int taskOneId) {
			// 根据任务Id号寻找任务所有数值
			Task taskOne=new Task();
			Cursor cursor=db.query(TABLE_NAME_TASK,null, "_taskid="+taskOneId, null,null, null, "_taskid desc");
			//if(cursor.getCount()>0) {Log.i("tag:","ok");}
			if(cursor.getCount()==1) {
			   cursor.moveToFirst();
			   taskOne.setTaskId(taskOneId);
			   taskOne.setTaskName(cursor.getString(1));
			   taskOne.setTaskGoal(cursor.getString(2));
			   taskOne.setBeginTime(cursor.getString(3));
			   taskOne.setEndTime(cursor.getString(4));
			   taskOne.setTaskStatus(cursor.getInt(5));
			   
			   taskOne.setSteps(findTaskStep(taskOneId));
			   
			}
			else {
				return null;
			}			
			
			return taskOne;
		}
		private Performer findPerformer(int taskId, int stepId) {
			// TODO 自动生成的方法存根
			//根据taskid和stepid在表performer中查找performerid,performertask
			   Performer performer=new Performer();
			   final String CREATE_TASK_PERFORMER="create table if not exists "+TABLE_NAME_PERFORMER+" ("+COLUMNS4[0]+" integer primary key autoincrement,"+COLUMNS4[1]+" integer,"+COLUMNS4[2]+" integer,"+COLUMNS4[3]+" integer,"+COLUMNS4[4]+" text);";
			   db.execSQL(CREATE_TASK_PERFORMER);
			   Cursor cursor2=db.query(TABLE_NAME_PERFORMER,null, "_taskid="+taskId+" and _stepid="+stepId, null,null, null, null);
			   if(cursor2.getCount()>0) {//如果performer表中有数据，则获取数据
				 cursor2.moveToFirst();
				 for(int i=0;i<cursor2.getCount();i++) {
					 performer.performerId[i]=cursor2.getInt(3);
					 performer.performerTask[i]=cursor2.getString(4);
				 }
				   
			   }
			   else {
				   performer=null;
			   }
			   return performer;
				
		}
		public TaskStep[] findTaskStep(int taskOneId){
			//此时产生步骤的数据了，如果没有步骤的表，则自动建立，如果存在则进行赋值
			
			   final String CREATE_TASKSTEP="create table if not exists "+TABLE_NAME_TASKSTEP+" ("+COLUMNS3[0]+" integer primary key autoincrement,"+COLUMNS3[1]+" integer,"+COLUMNS3[2]+" text,"+COLUMNS3[3]+" text,"+COLUMNS3[4]+" boolean,"+COLUMNS3[5]+" integer);";
			   db.execSQL(CREATE_TASKSTEP);
			   Cursor cursor1=db.query(TABLE_NAME_TASKSTEP,null, "_taskid="+taskOneId, null,null, null, "whichStep desc");
			   //Log.i("count:",String.valueOf(cursor1.getCount()));
			   tss=new TaskStep [cursor1.getCount()];
			   if(cursor1.getCount()>0) {
				   //已经做了分步
				   cursor1.moveToFirst();
				   for(int i=0;i<cursor1.getCount();i++) {
					   tss[i]=new TaskStep();
					   //Log.i("ccc:",String.valueOf(cursor1.getString(2)));
					   tss[i].setStepId(cursor1.getInt(0));
					   tss[i].setWhichStep(cursor1.getInt(1));
					   tss[i].setStepContent(cursor1.getString(2));
					   tss[i].setStepEndtime(cursor1.getString(3));
					   tss[i].setCompleted(Boolean.valueOf(cursor1.getString(4)));
					   tss[i].setTaskId(cursor1.getInt(5));
					   
					   //int stepId=cursor1.getInt(0);
					   // Performer performer=findPerformer(taskOneId,stepId);
					   /*
					   if(performer!=null) {
					   tss[i].setPerformerId(performer.performerId);
					   tss[i].setPerformerTask(performer.performerTask);
					   }*/
					   cursor1.moveToNext();
				   }
				   
			   }
			   else {
				   //没有分步，则提示分步
				   tss=null;
			   }
			   
			   return tss;
			
		}
		public boolean insertTaskStep(TaskStep step) {
			// 插入任务步骤
			
			
			final String CREATE_TASKSTEP="create table if not exists "+TABLE_NAME_TASKSTEP+" ("+COLUMNS3[0]+" integer primary key autoincrement,"+COLUMNS3[1]+" integer,"+COLUMNS3[2]+" text,"+COLUMNS3[3]+" text,"+COLUMNS3[4]+" boolean,"+COLUMNS3[5]+" integer);";
			db.execSQL(CREATE_TASKSTEP);
			ContentValues values=new ContentValues();
			values.put(COLUMNS3[1], step.getWhichStep());
			values.put(COLUMNS3[2], step.getStepContent());     
			values.put(COLUMNS3[3], step.getStepEndtime()); 
			values.put(COLUMNS3[4], step.isCompleted());
			values.put(COLUMNS3[5], step.getTaskId());
			if(db.insert(TABLE_NAME_TASKSTEP, null, values)>0) {
				//Log.i("ceshi:",step.getStepEndtime());
				return true;
			}
			else{
				return false;
			}
			
			
		}
		public void delSomeStep(int stepid) {
			// TODO Auto-generated method stub
			db.execSQL("delete from "+TABLE_NAME_TASKSTEP+" where _stepid="+stepid+";");
			
		}
		public void addSteps(TaskStep[] addSteps) {
			// TODO Auto-generated method stub
			for(int i=0;i<addSteps.length;i++){
				insertTaskStep(addSteps[i]);
			}
		}
		public TaskStep findOneStep(int stepid) {
			// 通过stepid信息查找该step所有信息
			TaskStep stepOne=new TaskStep();
			final String CREATE_TASKSTEP="create table if not exists "+TABLE_NAME_TASKSTEP+" ("+COLUMNS3[0]+" integer primary key autoincrement,"+COLUMNS3[1]+" integer,"+COLUMNS3[2]+" text,"+COLUMNS3[3]+" text,"+COLUMNS3[4]+" boolean,"+COLUMNS3[5]+" integer);";
			db.execSQL(CREATE_TASKSTEP);
			Cursor cursor=db.query(TABLE_NAME_TASKSTEP,null, "_stepid="+stepid, null,null, null, null);
			if(cursor.getCount()==1){//如果能够找到1条,将获得的数值赋予
				cursor.moveToFirst();
				stepOne.setStepId(cursor.getInt(0));
				stepOne.setWhichStep(cursor.getInt(1));
				stepOne.setStepContent(cursor.getString(2));
				stepOne.setStepEndtime(cursor.getString(3));
				stepOne.setCompleted(Boolean.valueOf(cursor.getString(4)));
				stepOne.setTaskId(cursor.getInt(5));
				
			}
			
			else{
				stepOne=null;
			}
			
			
			   
			return stepOne;
		}
		
		public void updateStepContent(int stepid, String stepContent) {
			// 更新Content数据
			ContentValues values=new ContentValues();
			values.put(COLUMNS3[2], stepContent);
			String[] stepids= {String.valueOf(stepid)};
			db.update(TABLE_NAME_TASKSTEP, values, "_stepid=?",stepids);
			
		}
		public void adjustStepsValue(int taskId) {
			// 按照大小调整whichSteps值
			
			TaskStep[] steps=findTaskStep(taskId);
			//因为是根据whichStep逆序查询，第一个即最大一个
			for(int i=0;i<steps.length;i++) {
				if(steps[i].getWhichStep()!=steps.length-i) {
				steps[i].setWhichStep(steps.length-i);
				}				
			}
			for(int j=0;j<steps.length;j++) {
			ContentValues values=new ContentValues();
			values.put(COLUMNS3[1], steps[j].getWhichStep());
			String[] stepids= {String.valueOf(steps[j].getStepId())};
			db.update(TABLE_NAME_TASKSTEP, values, "_stepid=?",stepids);
			}
			
			
		}
		public void editStepTime(int stepid, String time1) {
			// 将步骤修改后的时间在数据库中更新
			ContentValues values=new ContentValues();
			values.put(COLUMNS3[3], time1);
			String[] stepids= {String.valueOf(stepid)};
			db.update(TABLE_NAME_TASKSTEP, values, "_stepid=?",stepids);		
			
		}
		public void editStepStatus(int stepid) {
			// 修改step中isCompleted值
			ContentValues values=new ContentValues();
			values.put(COLUMNS3[4],"true");
			String[] stepids= {String.valueOf(stepid)};
			db.update(TABLE_NAME_TASKSTEP, values, "_stepid=?",stepids);
		}
		
	

}
