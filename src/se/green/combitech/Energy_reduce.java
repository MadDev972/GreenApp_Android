package se.green.combitech;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Energy_reduce extends Activity 
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
   
    
    public String[] CATEGORY= new String[]{KEY_CATEGORY};
    
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
			
    public String[] from2 = new String[]{KEY_COMMENTS};
			
	public int[] to = new int[] {R.id.energyreduce_tips};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.energy_reduce);
	        
	        Intent thisIntent = getIntent();
	        
			Spinner spinner_resources = (Spinner)findViewById(R.id.energyreduce_spinner1);
			
			ArrayAdapter<?> adapter_resources = ArrayAdapter.createFromResource
			(
					this, 
					R.array.quizzes_categories, 
					android.R.layout.simple_spinner_item
			);
			adapter_resources.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner_resources.setAdapter(adapter_resources);
			
			spinner_resources.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
			{
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
					CATEGORY[0]  = (String) parent.getItemAtPosition(pos);
        									
        			ListView lv = (ListView)findViewById(R.id.energyreduce_listview1);
    	        					
    	        	DataBaseHelper mDbHelper = new DataBaseHelper(parent.getContext());
    	        					
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
    	        			
    	        	Cursor c  = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY); 
    	        			    	
    	        	c.moveToFirst();
    	        					
    	        	startManagingCursor(c);
    	        			    	
    	        	SimpleCursorAdapter dataSource = new SimpleCursorAdapter(parent.getContext(),R.layout.energyreduce_row, c, from2, to);
    	        			    	
    	        	lv.setAdapter(dataSource);
    	        	
        		}
        					
        		public void onNothingSelected(AdapterView<?> parent)
    	        {
    	        	CATEGORY[0] = "Home and Garden";
    	        	
    	        	ListView lv = (ListView)findViewById(R.id.energyreduce_listview1);
    	        					
    	       		DataBaseHelper mDbHelper = new DataBaseHelper(parent.getContext());
    	        				
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
    	        			    	
    	        	Cursor c  = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY); 
    	    	        			    
    	    	    c.moveToFirst();
        	        					
    	        	startManagingCursor(c);
    	        	        			    	
    	        	SimpleCursorAdapter dataSource = new SimpleCursorAdapter(parent.getContext(),R.layout.energyreduce_row, c, from2, to);
    	        	        			    	
    	        	lv.setAdapter(dataSource);
    	    	
    	        }
    	    	        			
    	    });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.layout.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    		case R.id.sources:
    			LayoutInflater inflater = getLayoutInflater();
    			View layout = inflater.inflate(R.layout.energyflight_row,
    			                               (ViewGroup) findViewById(R.id.name));

    			TextView text = (TextView) layout.findViewById(R.id.name);
    			text.setText("The sources for the information in this section are the 2010 Guidelines to Defra / DECC's GHG Conversion Factors for Company Reporting and www.carbonfootprint.com");

    			Toast toast = new Toast(getApplicationContext());
    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    			toast.setDuration(Toast.LENGTH_LONG);
    			toast.setView(layout);
    			toast.show();
    			return true;
    			
    		case R.id.return_categories:
    			Intent intent = new Intent(Energy_reduce.this,DisplayCategories.class);
				startActivity(intent);
    	}
		return false;
    }
    
 }