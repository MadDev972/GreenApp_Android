package se.green.combitech;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;


public class GreenActivity extends Activity 
{
	public static final String KEY_ROWID = "_id";
    public static final String KEY_CATEGORY = "Category";
    public static final String KEY_QUESTIONS = "Questions";
    public static final String KEY_OPTIONS1 = "Options1";
    public static final String KEY_OPTIONS2 = "Options2";
    public static final String KEY_OPTIONS3 = "Options3";
    public static final String KEY_OPTIONS4 = "Options4";
    public static final String KEY_ANSWERS = "Answers";
    public static final String KEY_COMMENTS = "Comments";
    
    public String[] from = new String[]
                		              {
                		    				KEY_ROWID,
                		    				KEY_CATEGORY,
                		    				KEY_QUESTIONS,
                		    			    KEY_OPTIONS1,
                		    			    KEY_OPTIONS2,
                		    			    KEY_OPTIONS3,
                		    			    KEY_OPTIONS4,
                		    			    KEY_ANSWERS,
                		    			    KEY_COMMENTS
                		    		   };
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TextView Tip1 = (TextView)findViewById(R.id.main_random_tip1);
        final TextView Tip2 = (TextView)findViewById(R.id.main_random_tip2);
        
        DataBaseHelper mDbHelper = new DataBaseHelper(this);
		
    	try 
    	{
    		mDbHelper.createDataBase();
    	} 
    	catch (IOException ioe) 
    	{
    		throw new Error("Unable to create database");
    	}
    				 
    	try 
    	{
    		mDbHelper.openDataBase();
    	}
    	catch(SQLException sqle)
    	{
    		throw sqle;
    	}
    		        	
    	final Cursor c  = mDbHelper.fetchAll("Quizzes", from); 
    	
    	startManagingCursor(c);
    	
    	c.moveToPosition(new Random().nextInt(c.getCount()));
    	Tip1.setText(c.getString(c.getColumnIndex("Comments")));
    	
    	c.moveToPosition(new Random().nextInt(c.getCount()));
    	Tip2.setText(c.getString(c.getColumnIndex("Comments")));
    	
    	
    	Button main_button1 = (Button) findViewById(R.id.main_more_tips);
        main_button1.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				c.moveToPosition(new Random().nextInt(c.getCount()));
        		    	Tip1.setText(c.getString(c.getColumnIndex("Comments")));
        		    	
        		    	c.moveToPosition(new Random().nextInt(c.getCount()));
        		    	Tip2.setText(c.getString(c.getColumnIndex("Comments")));
        			}
        		}
        );  
        
    	
    	Button main_button2 = (Button) findViewById(R.id.main_pick_cat);
        main_button2.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(GreenActivity.this,DisplayCategories.class);
        				startActivity(intent);
        			}
        		}
        );  
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.energyflight_row,
		                               (ViewGroup) findViewById(R.id.name));

		TextView text = (TextView) layout.findViewById(R.id.name);
		text.setText("The source for the information in this section is http://environment.nationalgeographic.com");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
    	return true;
    }
}