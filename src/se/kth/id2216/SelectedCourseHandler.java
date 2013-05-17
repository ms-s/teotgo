package se.kth.id2216;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SelectedCourseHandler  extends Activity{
	
	ArrayList<String> selectedc = new ArrayList<String>();
	ArrayList<String> selectedcid = new ArrayList<String>();	
	ArrayList<String> newnames = new ArrayList<String>();
	ArrayList<String> reminders = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 Bundle extras = getIntent().getExtras() ;
		    if( extras != null ){
		    	selectedc=extras.getStringArrayList("courses");
		    	selectedcid=extras.getStringArrayList("coursesid");
		        }
		setContentView(R.layout.mylist);
		TableLayout tl = (TableLayout)findViewById(R.id.table);
		try{
		int counter=0;
		while(counter<selectedc.size())
		{
			TableRow.LayoutParams params1 = new TableRow.LayoutParams(); 
			TableRow.LayoutParams params2 = new TableRow.LayoutParams(); 
			TableRow.LayoutParams params3 = new TableRow.LayoutParams(); 
			TableRow.LayoutParams params4 = new TableRow.LayoutParams(); 
			params1.span = 2;
			params2.span = 2;
			params3.span = 2;
			params4.span = 2;
			params4.setMargins(2, 2, 2,2);
			
			
			TableRow tr1 = new TableRow(this);
			TableRow tr2 = new TableRow(this);
			TableRow tr3 = new TableRow(this);
			TableRow tr4 = new TableRow(this);
			
			TextView cname=new TextView(this);
			cname.setText(selectedc.get(counter)+"");
			cname.setTextColor(0xffff0000);
			cname.setTextSize(20);
			cname.setTypeface(null, Typeface.BOLD);
			EditText rcname=new EditText(this);
			rcname.setId(0x7f06000A+counter);
			
			View hr=new View(this);
			
			
			Spinner remindname=new Spinner(this);
			remindname.setId(0x7f061000+counter);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			remindname.setAdapter(adapter);
			
			
			tr1.addView(cname,params1);
			tr2.addView(rcname,params2);
			tr3.addView(remindname,params3);
			tr4.addView(hr,params4);
			tr4.setBackgroundColor(0xffcccccc);
			
			
			tl.addView(tr1);
			tl.addView(tr2);
			tl.addView(tr3);
			tl.addView(tr4);
			
			counter++;
		}
		Button back=new Button(this);
		Button addtoc=new Button(this);
		back.setText("Back");
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                finishActivity(0);

			}});
		addtoc.setText("Add to Calender");
		addtoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(SelectedCourseHandler.this, AddCourseHandler.class);
				Bundle bundle=new Bundle();
				int counter=0;
				while(counter<selectedc.size())
				{
					EditText temp=(EditText)findViewById(0x7f06000A+counter);
					String tstring=temp.getText().toString();
					newnames.add(""+tstring);
					counter++;
				}
				counter=0;
				while(counter<selectedc.size())
				{
					Spinner temp=(Spinner)findViewById(0x7f061000+counter);
					String tstring=temp.getSelectedItem().toString();
					reminders.add(tstring);
					counter++;
				}
				bundle.putStringArrayList("courses", selectedc);
				bundle.putStringArrayList("coursesid", selectedcid);
				bundle.putStringArrayList("newnames", newnames);
				bundle.putStringArrayList("reminders", reminders);
				myIntent.putExtras(bundle) ;
				SelectedCourseHandler.this.startActivityForResult(myIntent,0);
				
			}
		});
		TableRow tr1 = new TableRow(this);
		tr1.addView(back);
		tr1.addView(addtoc);
		tl.addView(tr1);
		tl.requestLayout();

		}
		catch(Exception e)
		{}
	}
}



