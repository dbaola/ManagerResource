package com.ex.somebody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class LIB {
	/*
	 * �Լ���д�Ĺ��÷�����
	 * 
	 * 
	 */
	
	
	
	/*
	 * ������:���ڱȽϴ�С
	 * ����:ʵ���������ڱȽϴ�С,����һ��intֵ,�������=0,aTime=bTime;����>0,aTime>bTime;����<0,aTime<bTime
	 */
	@SuppressLint("SimpleDateFormat")
	public int CompABtime(String dateA, String dateB) {
		// TODO Auto-generated method stub
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date aTime = null,bTime = null;
		
		try {
			aTime = df.parse(dateA);
			bTime=df.parse(dateB);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int result=aTime.compareTo(bTime);
		return result;
	}

	@SuppressLint("SimpleDateFormat")
	public int CompNow(String someTime) {
		// TODO �Զ����ɵķ������
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String today = df.format(new Date());		
		int result=CompABtime(today,someTime);
		return result;
	}
	
    


}
