package com.ex.somebody;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Displayer extends Activity{

	public void Somebody(Somebody person) {
		// TODO 自动生成的方法存根
		LinearLayout Llayout1=new LinearLayout(Displayer.this);
		TextView pName=new TextView(Displayer.this);
		pName.setText(person.getName());
		Llayout1.addView(pName);
	}

}
