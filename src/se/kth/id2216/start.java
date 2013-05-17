package se.kth.id2216;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.ListIterator;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.thoughtworks.xstream.XStream;

public class start extends Activity {
	EditText text;
	TextView debug;
	XStream xstream;
	String [] courses=new String[20];
	ProgressDialog dialog;
	String [] tmpcourses;
	Handler handler;
	Context appc;
	ArrayList<String> selectedc = new ArrayList<String>();
	boolean[] selections;
	String [] tempcoursesid;
	String []coursesid=new String[20];
	int mswitch;
	ArrayList<String> selectedcid=new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (EditText) findViewById(R.id.entry);
        xstreamInit();  
    }
public void xstreamInit() {
	 xstream = new XStream();
 	xstream.alias("schema", Schema.class);
 	xstream.alias("course", Course.class);
 	xstream.useAttributeFor(Course.class, "id");
 	xstream.useAttributeFor(Course.class, "code");
 	xstream.useAttributeFor(Course.class, "name");
 	xstream.alias("event", CalEvent.class);
 	xstream.useAttributeFor(CalEvent.class, "moment");
 	xstream.useAttributeFor(CalEvent.class, "start");
 	xstream.useAttributeFor(CalEvent.class, "end");
 	xstream.useAttributeFor(CalEvent.class, "room");
 	xstream.useAttributeFor(CalEvent.class, "code");
 	xstream.useAttributeFor(CalEvent.class, "name");

    }
public void searchCourse(View view)
    { 
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
    	debug = (TextView) findViewById( R.id.debug );
    	debug.setText("");
    	try
    	{	
    		dialog = ProgressDialog.show(this, "Patience Is Appreciated", "Getting data from TimeEdit", true);
    		text=(EditText) findViewById(R.id.entry);	
    		Thread childt=new Thread() {
    			
    			public void run() {
    	    	  	try{
    	    	  		mswitch=0;
    	    	  		int iterator=0;
    	        		String search = URLEncoder.encode( text.getText().toString() );
    	        		String url = "http://www.toxicgold.com/school/searchxml.php?search=" + search;
    	          		DefaultHttpClient client = new DefaultHttpClient();
    	        		HttpGet get = new HttpGet( url );
    	        		HttpResponse response = client.execute(get);
    	                if (  response != null && response.getEntity() != null)
    	                {
    	                InputStream in = response.getEntity().getContent();
    	    	            if ( in != null)
    	    	            {
    	    	            	Schema sch = (Schema) xstream.fromXML( in );
    	    	            	
    	    	            	if ( sch.getCourses() == null || sch.getCourses().isEmpty() )
    	    	            	{
    	    	            		mswitch=0;
    	    	            		//dialog.dismiss();
    	    	            	}
    	    	            	
    	    	            	else if ( sch.getCourses().size() > 0 )
    	    	            		{
    	    	            		    
    	    	            		ListIterator<Course> cIt = sch.getCourses().listIterator();
    	    	            		mswitch=1;
    	    	            		    	while ( cIt.hasNext() )
    	    	            		    	{
    	    	            		    		courses[iterator]=cIt.next().toString();
    	    	            					coursesid[iterator++]=cIt.previous().getId()+"";    
    	    	            					cIt.next();
    	    	            		    	}
    	    	            		    	tmpcourses=new String[iterator];
    	    	            		    	tempcoursesid=new String[iterator];
    	    	            		    	for(int i=0;i<iterator;i++)
    	    	            		    	{
    	    	            		    		tmpcourses[i]=courses[i];
    	    	            		    		tempcoursesid[i]=coursesid[i];
    	    	            		    	}
    	    	            		    	
    	    	               		}
    	    	            }
    	                }
    	            handler.sendEmptyMessage(0);
    	    	  	dialog.dismiss();
    	    	  	}
    	    	  	catch (Exception e) { Log.e("Exception by me", e.getMessage());}
    	}
    	};
    	childt.start();
    
    	appc=getApplicationContext();
    	handler=new Handler() {
    		
       		 public void handleMessage(Message msg) {
       		
       			/*debug.setText("Courses Found:");
       			debug.setPadding(2, 12, 0, 5);
       			debug.requestLayout();
       			ListView courselist=(ListView) findViewById(R.id.list);
       			courselist.getLayoutParams().height=150;
       	        courselist.requestLayout();
       	        courselist.setEnabled(true);
       	        courselist.setClickable(true);
       	        //courselist.setOnItemClickListener(appc);
       	        courselist.setAdapter(new ArrayAdapter<String>(appc,android.R.layout.simple_list_item_multiple_choice,tmpcourses));
       	        courselist.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE);*/
       			try{
       				if(mswitch!=0)
       			showDialog( 0 );
       				else
       				{
       					showDialog(1);
       				}
       			}
       			catch (Exception e) { Log.e("Exception by me", e.getMessage());}
    	 }
    	 };
    	}
    	catch (Exception e) { Log.e("Exception by me", e.getMessage());}
    }
@Override
protected Dialog onCreateDialog( int id ) 
{
	if(id==1)
		{
		if(selectedc.isEmpty())
			debug.setText("");
		else
		debug.setText("Selected Courses:");
		return new AlertDialog.Builder( this ).setMessage("No courses found").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Action for 'Yes' Button
				}
				}).create();
		
		}
	try{
	selections =  new boolean[ tmpcourses.length ];
	return 
	new AlertDialog.Builder( this )
    	.setTitle( "Courses Found" )
    	.setMultiChoiceItems( tmpcourses,selections ,new DialogSelectionClickHandler() )
    	.setPositiveButton( "Done", new DialogButtonClickHandler() )
    	.create();
	
	}
	catch (Exception e) { Log.e("Exception by me", e.getMessage());}
	return 
	new AlertDialog.Builder( this )
    	.setTitle( "Courses Found" )
    	.setMultiChoiceItems( tmpcourses,selections ,new DialogSelectionClickHandler() )
    	.setPositiveButton( "Done", new DialogButtonClickHandler() )
    	.create();
	
}
public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
{
	public void onClick( DialogInterface dialog, int clicked, boolean selected )
	{
		//Log.i( "ME", _options[ clicked ] + " selected: " + selected );
		
	}
}
public class DialogButtonClickHandler implements DialogInterface.OnClickListener
{
	public void onClick( DialogInterface dialog, int clicked )
	{
		switch( clicked )
		{
			case DialogInterface.BUTTON_POSITIVE:
			{
				for(int i=0;i<tmpcourses.length;i++ )
				{
					if(selections[i]==true)
					{
						selectedc.add(tmpcourses[i]);
						selectedcid.add(tempcoursesid[i]);
					}
				}
				removeDialog(0);
				final ListView courselist=(ListView) findViewById(R.id.list);
       			courselist.getLayoutParams().height=115;
       	        courselist.requestLayout();
       	        courselist.setEnabled(true);
       	        courselist.setClickable(true);
       	        courselist.setAdapter(new ArrayAdapter<String>(appc,R.layout.row,R.id.weekofday,selectedc));
    	        courselist.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE);
    	        if(!selectedc.isEmpty())
    	        	{
    	        	debug.setText("Selected Courses:");
    	        	Button doneb=(Button)findViewById(R.id.button);
    	        	doneb.setVisibility(Button.VISIBLE);
    	        	doneb.requestLayout();
    	        	}
    	       
    	        courselist.setOnItemClickListener(new OnItemClickListener() {

    	       	public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3)

    	        	{
    	        		SparseBooleanArray a = courselist.getCheckedItemPositions();

    	        		for(int i = 0; i < a.size() ; i++)

    	        		{

    	        		if (a.valueAt(i))
    	        			{
    	        				selectedc.remove(i);
    	        				selectedcid.remove(i);
    	        				courselist.setAdapter(new ArrayAdapter<String>(appc,R.layout.row,R.id.weekofday,selectedc));
    	            	        courselist.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE);
    	            	        courselist.requestLayout();
    	            	        if(selectedc.isEmpty())
    	            	        {
    	            	        	debug.setText("");
    	            	        	Button doneb=(Button)findViewById(R.id.button);
    	            	        	doneb.setVisibility(Button.INVISIBLE);
    	            	        	doneb.requestLayout();
    	            	        	ListView courselist=(ListView) findViewById(R.id.list);
    	                   			courselist.getLayoutParams().height=120;
    	                   	        courselist.requestLayout();
    	            	        }
    	        			}
    	        		}


    	        	}});
				break;
			}
		}
	}
}
public void addCourse(View view)
{
	Intent myIntent = new Intent(start.this, SelectedCourseHandler.class);
	Bundle bundle=new Bundle();
	bundle.putStringArrayList("courses", selectedc);
	bundle.putStringArrayList("coursesid", selectedcid);
	myIntent.putExtras(bundle) ;
	start.this.startActivityForResult(myIntent,0);
	

}

}