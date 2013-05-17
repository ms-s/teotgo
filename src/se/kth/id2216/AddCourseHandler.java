package se.kth.id2216;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.ListIterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.thoughtworks.xstream.XStream;

import se.kth.id2216.CalEvent;
import se.kth.id2216.Schema;
import se.kth.id2216.Course;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AddCourseHandler extends Activity{
	ArrayList<String> selectedcid = new ArrayList<String>();
	ArrayList<String> selectedc = new ArrayList<String>();	
	ArrayList<String> newnames = new ArrayList<String>();
	XStream xstream;
	ListIterator<CalEvent> evIt2;
	ArrayList<String> reminders = new ArrayList<String>();
	ProgressDialog dialog;
	Context appc;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 Bundle extras = getIntent().getExtras() ;
		    if( extras != null ){
		    	selectedc=extras.getStringArrayList("courses");
		    	selectedcid=extras.getStringArrayList("coursesid");
		    	
		    	newnames=extras.getStringArrayList("newnames");
		    	reminders=extras.getStringArrayList("reminders");
		        }
		xstreamInit();  
		dialog = ProgressDialog.show(this, "Patience Is Appreciated", "Adding events ", true);
		
		TextView temp=(TextView)findViewById(R.id.finisht);
		appc=this;
		Thread childt=new Thread() {
			public void run() {
				try{
					
					for(int i=0;i<selectedcid.size();i++)
					{
						try
				    	{		
				    		DefaultHttpClient client = new DefaultHttpClient();
				    		String url = "http://www.toxicgold.com/school/scrapexml.php?v=2&count=1&obj0=" + selectedcid.get(i);
				    		HttpGet get = new HttpGet( url );
				            HttpResponse response = client.execute(get);
				            if (  response != null && response.getEntity() != null)
				            {
				            	InputStream in = response.getEntity().getContent();
					            if ( in != null)
					            {
					            	Schema sch = (Schema) xstream.fromXML( in );
					            	evIt2 = sch.getEvents().listIterator();
					            	
					            }
					            else Log.d(INPUT_METHOD_SERVICE, "in = null: searchxml");
					        }
				    	}
				    	catch(Exception e)
				    	{
				    		Log.e("lkjhbjbgh Exception", e.toString() );
				    	}
						ContentResolver contentResolver = appc.getContentResolver();
						final Cursor cursor = contentResolver.query(Uri.parse("content://calendar/calendars"),(new String[] { "_id", "displayName", "selected" }), null, null, null);
						HashSet<String> calendarIds = new HashSet<String>();
						
						while (cursor.moveToNext()) {
							final String _id = cursor.getString(0);
							final String displayName = cursor.getString(1);
							final Boolean selected = !cursor.getString(2).equals("0");
							calendarIds.add(_id);
						}
						for (String id : calendarIds) {
							Uri.Builder builder = Uri.parse("content://calendar/instances/when").buildUpon();
							
							while(evIt2.hasNext()){
								ContentValues event = new ContentValues();
								event.put("calendar_id", id);
								if(newnames.get(i).length()==0)
										event.put("title",selectedc.get(i));
								else
									event.put("title", newnames.get(i));
								event.put("description",evIt2.next().getMoment());
								event.put("eventLocation", evIt2.previous().getRoom());
								event.put("allDay", 0);
								String start=""+evIt2.next().getStart();
								event.put("dtstart",start);
								String end=""+evIt2.previous().getEnd();
								event.put("dtend", end);
								event.put( "hasAlarm", 1 );
								Uri eventsUri = Uri.parse("content://calendar/events");
								Uri url = appc.getContentResolver().insert(eventsUri, event);	
								evIt2.next();
								if(reminders.get(i).contains("Min"))
								{
									String eventid = url.getPathSegments().get(url.getPathSegments().size()-1);
									ContentValues remider = new ContentValues();
									remider.put("event_id",eventid);
									String tempo=reminders.get(i);
									tempo=tempo.substring(0,tempo.indexOf(" "));
									int remint=Integer.parseInt(tempo);
									remider.put("minutes",10);
									Uri reminderUri = Uri.parse("content://calendar/reminders");
									Uri rurl=appc.getContentResolver().insert(reminderUri, remider);
								}
							}
						}
					}
				}
					catch(Exception e)
					{Log.e("hihuiuj Exception", e.toString() );}
					dialog.dismiss();
			}
		};
		childt.start();
		setContentView(R.layout.finish);
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
}
