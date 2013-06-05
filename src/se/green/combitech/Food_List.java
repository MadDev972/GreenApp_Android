package se.green.combitech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class Food_List extends Activity 
{	
	public static final String KEY_ROWID = "_id";
    public static final String KEY_COUNTRY = "Country";
    public static final String KEY_CITY = "City";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_SUBTYPE = "Subtype";
    public static final String KEY_NAME = "Name";
    public static final String KEY_ADRESS = "Adress";
    public static final String KEY_TELEPHONE = "Telephone";
    public static final String KEY_COMMENTS = "Comments";
    public static final String KEY_WEBSITE = "website";
    public static final String KEY_PICTURE1 = "picture1";


    public static final String[] from = new String[]
	                           {
									KEY_ROWID,
									KEY_COUNTRY,
									KEY_CITY,
									KEY_TYPE,
									KEY_SUBTYPE,
									KEY_NAME,
									KEY_ADRESS,
									KEY_TELEPHONE,
									KEY_COMMENTS,
									KEY_WEBSITE,
									KEY_PICTURE1
	                           };
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        setContentView(R.layout.food_list);
	        Intent thisIntent = getIntent();
	        
	        final ExpandableListView Food_explv = (ExpandableListView)findViewById(R.id.foodlist_explv);

	        
	        final DataBaseHelper mDbHelper = new DataBaseHelper(this);
			
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
        			
        	final Cursor c  = mDbHelper.fetchAll("Food", from); 
        	
        	startManagingCursor(c);  	
	        
        	ArrayList Country = new ArrayList();
        	
        	c.moveToFirst();
    		
        	do
        	{
        		  
        			String country = c.getString(c.getColumnIndex("Country"));
        			Country.add(country);
        			
        	}while(c.moveToNext());
        		
        	LinkedHashSet<HashMap<String, String>> hs1 = new LinkedHashSet<HashMap<String, String>>();
			hs1.addAll(Country);
			Country.clear();
			Country.addAll(hs1);
        	
			final Spinner spinner_country = (Spinner)findViewById(R.id.foodlist_spinner1);
			final Spinner spinner_city = (Spinner)findViewById(R.id.foodlist_spinner2);
			
			ArrayAdapter<String> adapter_country = new ArrayAdapter
			(
					this, 
					android.R.layout.simple_spinner_item,
					Country
			);
			adapter_country.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_country.setAdapter(adapter_country);
			
					
			spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
			{
				
				public void onItemSelected(AdapterView<?> parent, View view, int pos0, long id)
				{
				
					final String COUNTRY = (String) parent.getItemAtPosition(pos0);
					
					ArrayList City = new ArrayList();
		        	
		        	c.moveToFirst();
		    		
		        	do
		        	{
		        		  if(c.getString(c.getColumnIndex("Country")).equals((String) parent.getItemAtPosition(pos0)))
		        		  {	
		        			  String city = c.getString(c.getColumnIndex("City"));
		        			  City.add(city);
		        		  }
		        			
		        	}while(c.moveToNext());
		        	
		        	LinkedHashSet<HashMap<String, String>> hs2 = new LinkedHashSet<HashMap<String, String>>();
					hs2.addAll(City);
					City.clear();
					City.addAll(hs2);
		        	
					ArrayAdapter<String> adapter_city = new ArrayAdapter
					(
							getBaseContext(), 
							android.R.layout.simple_spinner_item,
							City
					);
					adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner_city.setAdapter(adapter_city);
					
					spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
					{
						public void onItemSelected(AdapterView<?> parent, View view, final int pos, long id)
						{
							String [] RESOURCETYPE = new String[]{COUNTRY,(String) spinner_city.getItemAtPosition(pos)};
							
							final List<Map<String,String>> Food_parents = new ArrayList<Map<String, String>>();
							final List<List<Map<String, String>>> Food_children = new ArrayList<List<Map<String, String>>>();
    	        	    	       
	        	            final int[]IDs = new int[mDbHelper.fetchListTwoVariable("Food", from, RESOURCETYPE).getCount()];

							class MyELVSimpleAdapter extends SimpleExpandableListAdapter
	        				{
	        					
	        					private MyELVSimpleAdapter()
	        					{
	        						super
	        						(
	        								getBaseContext(),
	    									Food_parents,
	    									R.layout.foodlist_row_parent,
	    									new String[]{"Name","Subtype","Adress"},
	    									new int[]{R.id.foodlist_parent_name,R.id.foodlist_parent_subtype,R.id.foodlist_parent_adress},
	    									Food_children,
	    									R.layout.foodlist_row_child,
	    									new String[]{"Telephone","website","Comments"},
	    									new int[]{R.id.foodlist_child_telephone,R.id.foodlist_child_website,R.id.foodlist_child_comments}
	        	        	    	 );
	        					}
	        					
	        					@Override
	        					public View getGroupView(int groupPosition, boolean isExpanded, View convertView,ViewGroup parent)
	        					{
	        						View v = super.getGroupView(groupPosition, isExpanded, convertView, parent);
	        						c.moveToPosition(IDs[groupPosition]);
	        						String picture1 = c.getString(c.getColumnIndex(KEY_PICTURE1));
	        						ImageView Image1 = (ImageView)v.findViewById(R.id.flrowparent_imageView1);
	        						Drawable drawable1 = getResources().getDrawable(getResources().getIdentifier("se.green.combitech:drawable/"+picture1,null,null));
	        						Image1.setImageDrawable(drawable1);

	        						return v;
	        					}
	        					
	        					@Override
	        					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent)
	        					{
	        						
	        						final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
	        							        						
	        						final TextView link = (TextView)v.findViewById(R.id.foodlist_child_website);
	    	                		link.setOnClickListener
	    	                        (
	    	                        		new View.OnClickListener()
	    	                        		{
	    	                        			public void onClick(View v)
	    	                        			{
	    	                        				startActivity(new Intent("android.intent.action.VIEW",Uri.parse(link.getText().toString())));
	    	                        			}
	    	                        		}
	    	                        );  
	        						
	        						return v;
	        					}	        					
	        				}
							
							int j = 0;
							
							c.moveToFirst();
    	        	
							do
							{
								
								if(c.getString(c.getColumnIndex("Country")).equals(COUNTRY) && c.getString(c.getColumnIndex("City")).equals((String) spinner_city.getItemAtPosition(pos)))
								{	    
									IDs[j]= c.getPosition();
									Map<String, String> m1 = new HashMap<String, String>();
									Food_parents.add(m1);
									m1.put("Name",c.getString(c.getColumnIndex("Name")));
									m1.put("Subtype", c.getString(c.getColumnIndex("Subtype")));
									m1.put("Adress", c.getString(c.getColumnIndex("Adress")));
    	        			
									List<Map<String,String>> secList = new ArrayList<Map<String, String>>();
									Map<String, String> m2 = new HashMap<String, String>();
									secList.add(m2);
									m2.put("Telephone",c.getString(c.getColumnIndex("Telephone")));
									m2.put("website",c.getString(c.getColumnIndex("website")));
									m2.put("Comments",c.getString(c.getColumnIndex("Comments")));
    	        			
									Food_children.add(secList);
									
									j++;
    	        			
								}
    	        		
							}while(c.moveToNext());

							Food_explv.setAdapter(new MyELVSimpleAdapter());
							
							
						}
						@Override
						public void onNothingSelected(AdapterView<?> arg0) 
						{
							// TODO Auto-generated method stub
							
						}
					});
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) 
				{
					
				}
			});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.layout.menuf, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	
    		case R.id.legend:
    			LayoutInflater inflater1 = getLayoutInflater();
    			View layout1 = inflater1.inflate(R.layout.legend,
    			                               (ViewGroup) findViewById(R.id.legend_linearLayout));

    			Toast toast1 = new Toast(getApplicationContext());
    			toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    			toast1.setDuration(Toast.LENGTH_LONG);
    			toast1.setView(layout1);
    			toast1.show();
    			return true;
    			
    		case R.id.sources:
    			LayoutInflater inflater = getLayoutInflater();
    			View layout = inflater.inflate(R.layout.energyflight_row,
    			                               (ViewGroup) findViewById(R.id.name));

    			TextView text = (TextView) layout.findViewById(R.id.name);
    			text.setText("The source for the information in this section is www.happycow.net");

    			Toast toast = new Toast(getApplicationContext());
    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    			toast.setDuration(Toast.LENGTH_LONG);
    			toast.setView(layout);
    			toast.show();
    			return true;
    			
    		case R.id.return_categories:
    			Intent intent = new Intent(Food_List.this,DisplayCategories.class);
				startActivity(intent);
    	}
		return false;
    }
    
}