package se.green.combitech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Resources_List extends Activity 
{	
	public static final String KEY_ROWID = "_id";
    public static final String KEY_ISBN = "ISBNnumber";
    public static final String KEY_AUTHOR = "Author";
    public static final String KEY_TYPE = "Type";
    public static final String KEY_SUMMARY = "Summary";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_SCNDARYTITLE = "Scndarytitle";
    public static final String KEY_YEAR = "Year";
    
    String[] RESOURCETYPE = new String[]{"ResourceType"};
    String Resource;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        setContentView(R.layout.resources_list);
	        Intent thisIntent = getIntent();
	        
	        
			Spinner spinner_resources = (Spinner)findViewById(R.id.resourceslist_spinner1);
			final Spinner spinner_type = (Spinner)findViewById(R.id.resourceslist_spinner2);
			
			ArrayAdapter<?> adapter_resources = ArrayAdapter.createFromResource
			(
					this, 
					R.array.resources, 
					android.R.layout.simple_spinner_item
			);
			adapter_resources.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_resources.setAdapter(adapter_resources);
			
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
			
			
			spinner_resources.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
			{
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
					Resource  = (String) parent.getItemAtPosition(pos);
					if(Resource.equals("Books"))
					{	
						ArrayAdapter<?> adapter =  ArrayAdapter.createFromResource
						(
							    parent.getContext(), 
								R.array.resources_types_books, 
								android.R.layout.simple_spinner_item);
								adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
						);
						spinner_type.setAdapter(adapter);
					}
					else
					{
						ArrayAdapter<?> adapter =  ArrayAdapter.createFromResource
						(
								parent.getContext(), 
								R.array.resources_types_movies, 
								android.R.layout.simple_spinner_item);
								adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
						);
								
						spinner_type.setAdapter(adapter);
					}
					
					
        			spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
        			{
        				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
        				{
        					RESOURCETYPE[0] = (String) parent.getItemAtPosition(pos);
        					
        					if(Resource.equals("Books"))
    	        			{	
    	        				
    	        				String[] from = new String[]
    	        			                      {
    	        			    						KEY_ROWID,
    	        			    						KEY_TYPE,
    	        			    						KEY_ISBN,
    	        			    						KEY_YEAR,
    	        			    						KEY_AUTHOR,
    	        			    						KEY_TITLE,
    	        			    						KEY_SCNDARYTITLE,
    	        			    						KEY_SUMMARY
    	        			    	                };

    	        				
    	        				Cursor c = mDbHelper.fetchAll("Books", from);
    	        				
    	        				startManagingCursor(c);
    	        				
    	        				final List<Map<String,String>> Book_parents = new ArrayList<Map<String, String>>();
    	        		        final List<List<Map<String, String>>> Book_children = new ArrayList<List<Map<String, String>>>();
    	        	        	
    	        	            final int[]IDs = new int[mDbHelper.fetchListResourceType("Books", from, 1, RESOURCETYPE).getCount()];
    	        	        	
    	          	        	int j = 0;
    	          	        	
    	        	        	c.moveToFirst();
    	        	        	
    	        	        	do
    	        	        	{
    	        	        		if(RESOURCETYPE[0].equals(c.getString(c.getColumnIndex("Type"))))
    	        	        		{	    
    	        	        			IDs[j]= c.getPosition()+1;
    	        	        			Map<String, String> m1 = new HashMap<String, String>();
    	        	        			Book_parents.add(m1);
    	        	        			m1.put("ISBNnumber",c.getString(c.getColumnIndex("ISBNnumber")));
    	        	        			m1.put("title", c.getString(c.getColumnIndex("Title")));
    	        	        			m1.put("scndarytitle", c.getString(c.getColumnIndex("Scndarytitle")));
    	        	        			m1.put("author", c.getString(c.getColumnIndex("Author")));
    	        	        			m1.put("year", c.getString(c.getColumnIndex("Year")));
    	        	        			
    	        	        			List<Map<String,String>> secList = new ArrayList<Map<String, String>>();
    	        	        			Map<String, String> m2 = new HashMap<String, String>();
    	        	        			secList.add(m2);
    	        	        			m2.put("summary",c.getString(c.getColumnIndex("Summary")));
    	        	        			
    	        	        			Book_children.add(secList);
    	        	        			
    	        	        			j++;
    	        	        		}
    	        	        		
    	        	        	}while(c.moveToNext());
    	        	        		
    	        				class MyELVSimpleAdapter extends SimpleExpandableListAdapter
    	        				{
    	        					
    	        					private MyELVSimpleAdapter()
    	        					{
    	        						super
    	        						(
    	        	        	    			getBaseContext(),
    	        	        	    			Book_parents,
    	        	        	    			R.layout.booklist_parent_row,
    	        	        	    			new String[]{"ISBNnumber","year","author","title","scndarytitle"},
    	        	        	    			new int[]{R.id.bookslist_ISBNnumber,R.id.bookslist_year,R.id.bookslist_author,R.id.bookslist_title,R.id.bookslist_scndarytitle},
    	        	        	    			Book_children,
    	        	        	    			R.layout.resourceslist_child_row,
    	        	        	    			new String[]{"summary"},
    	        	        	    			new int[]{R.id.resourceslist_summary}
    	        	        	    	 );
    	        					}
    	        					@Override
    	        					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent)
    	        					{
    	        						
    	        						final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    	        				        String imgName = "book" + IDs[groupPosition];
    	        						ImageView Image = (ImageView)v.findViewById(R.id.resourceslist_imageView1);
    	        						Drawable drawable = getResources().getDrawable(getResources().getIdentifier("se.green.combitech:drawable/"+imgName,null,null));
    	        						Image.setImageDrawable(drawable);
    	        						
    	        						return v;
    	        					}
    	        				}

    	        	    		ExpandableListView explv = (ExpandableListView)findViewById(R.id.resourceslist_explv);
    	        	    		
    	        	    		explv.setAdapter(new MyELVSimpleAdapter());
    	        				
    	        			}
    	        			
    	        			else
    	        			{
    	        				String[] from = new String[]
    	     	        			                      {
    	     	        			    						KEY_ROWID,
    	     	        			    						KEY_TYPE,
    	     	        			    						KEY_YEAR,
    	     	        			    						KEY_TITLE,
    	     	        			    						KEY_SUMMARY
    	     	        			    	                };

    	     	        				
    	     	        				Cursor c = mDbHelper.fetchAll("Movies", from);
    	     	        				
    	     	        				startManagingCursor(c);
    	     	        				
    	     	        				final List<Map<String,String>> Movie_parents = new ArrayList<Map<String, String>>();
    	     	        		        final List<List<Map<String, String>>> Movie_children = new ArrayList<List<Map<String, String>>>();
    	     	        	        	
    	     	        	            final int[]IDs = new int[mDbHelper.fetchListResourceType("Movies", from, 1, RESOURCETYPE).getCount()];
    	     	        	        	
    	     	          	        	int j = 0;
    	     	          	        	
    	     	        	        	c.moveToFirst();
    	     	        	        	
    	     	        	        	do
    	     	        	        	{
    	     	        	        		if(RESOURCETYPE[0].equals(c.getString(c.getColumnIndex("Type"))))
    	     	        	        		{	    
    	     	        	        			IDs[j]= c.getPosition()+1;
    	     	        	        			Map<String, String> m1 = new HashMap<String, String>();
    	     	        	        			Movie_parents.add(m1);
    	     	        	        			m1.put("title", c.getString(c.getColumnIndex("Title")));
    	     	        	        			m1.put("year", c.getString(c.getColumnIndex("Year")));
    	     	        	        			
    	     	        	        			List<Map<String,String>> secList = new ArrayList<Map<String, String>>();
    	     	        	        			Map<String, String> m2 = new HashMap<String, String>();
    	     	        	        			secList.add(m2);
    	     	        	        			m2.put("summary",c.getString(c.getColumnIndex("Summary")));
    	     	        	        			
    	     	        	        			Movie_children.add(secList);
    	     	        	        			
    	     	        	        			j++;
    	     	        	        		}
    	     	        	        		
    	     	        	        	}while(c.moveToNext());
    	     	        	        		
    	     	        				class MyELVSimpleAdapter extends SimpleExpandableListAdapter
    	     	        				{
    	     	        					
    	     	        					private MyELVSimpleAdapter()
    	     	        					{
    	     	        						super
    	     	        						(
    	     	        	        	    			getBaseContext(),
    	     	        	        	    			Movie_parents,
    	     	        	        	    			R.layout.movielist_parent_row,
    	     	        	        	    			new String[]{"year","title"},
    	     	        	        	    			new int[]{R.id.movieslist_year,R.id.movieslist_title},
    	     	        	        	    			Movie_children,
    	     	        	        	    			R.layout.resourceslist_child_row,
    	     	        	        	    			new String[]{"summary"},
    	     	        	        	    			new int[]{R.id.resourceslist_summary}
    	     	        	        	    	 );
    	     	        					}
    	     	        					@Override
    	     	        					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent)
    	     	        					{
    	     	        						
    	     	        						final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    	     	        						
    	     	        						String imgName = "movie" + IDs[groupPosition];
    	     	        						ImageView Image = (ImageView)v.findViewById(R.id.resourceslist_imageView1);
    	     	        						Drawable drawable = getResources().getDrawable(getResources().getIdentifier("se.green.combitech:drawable/"+imgName,null,null));
    	     	        						Image.setImageDrawable(drawable);
    	     	        						
    	     	        						return v;
    	     	        					}
    	     	        				}

    	     	        	    		ExpandableListView explv = (ExpandableListView)findViewById(R.id.resourceslist_explv);
    	     	        	    		
    	     	        	    		explv.setAdapter(new MyELVSimpleAdapter());
    	     	        				
    	        			}
        				}
        					
        				public void onNothingSelected(AdapterView<?> parent)
    	        		{
        					if(Resource.equals("Books"))
    	    	        	{	
    	    	        		RESOURCETYPE[0] = "Eco-Education";
    	    	        		
    	    	        		String[] from = new String[]
    	     	        			                      {
    	     	        			    						KEY_ROWID,
    	     	        			    						KEY_TYPE,
    	     	        			    						KEY_ISBN,
    	     	        			    						KEY_YEAR,
    	     	        			    						KEY_AUTHOR,
    	     	        			    						KEY_TITLE,
    	     	        			    						KEY_SCNDARYTITLE,
    	     	        			    						KEY_SUMMARY
    	     	        			    	                };

    	     	        				
    	     	        				Cursor c = mDbHelper.fetchAll("Books", from);
    	     	        				
    	     	        				startManagingCursor(c);
    	     	        				
    	     	        				final List<Map<String,String>> Book_parents = new ArrayList<Map<String, String>>();
    	     	        		        final List<List<Map<String, String>>> Book_children = new ArrayList<List<Map<String, String>>>();
    	     	        	        	
    	     	        	            final int[]IDs = new int[mDbHelper.fetchListResourceType("Books", from, 1, RESOURCETYPE).getCount()];
    	     	        	        	
    	     	          	        	int j = 0;
    	     	          	        	
    	     	        	        	c.moveToFirst();
    	     	        	        	
    	     	        	        	do
    	     	        	        	{
    	     	        	        		if(RESOURCETYPE[0].equals(c.getString(c.getColumnIndex("Type"))))
    	     	        	        		{	    
    	     	        	        			IDs[j]= c.getPosition()+1;
    	     	        	        			Map<String, String> m1 = new HashMap<String, String>();
    	     	        	        			Book_parents.add(m1);
    	     	        	        			m1.put("ISBNnumber",c.getString(c.getColumnIndex("ISBNnumber")));
    	     	        	        			m1.put("title", c.getString(c.getColumnIndex("Title")));
    	     	        	        			m1.put("scndarytitle", c.getString(c.getColumnIndex("Scndarytitle")));
    	     	        	        			m1.put("author", c.getString(c.getColumnIndex("Author")));
    	     	        	        			m1.put("year", c.getString(c.getColumnIndex("Year")));
    	     	        	        			
    	     	        	        			List<Map<String,String>> secList = new ArrayList<Map<String, String>>();
    	     	        	        			Map<String, String> m2 = new HashMap<String, String>();
    	     	        	        			secList.add(m2);
    	     	        	        			m2.put("summary",c.getString(c.getColumnIndex("Summary")));
    	     	        	        			
    	     	        	        			Book_children.add(secList);
    	     	        	        			
    	     	        	        			j++;
    	     	        	        		}
    	     	        	        		
    	     	        	        	}while(c.moveToNext());
    	     	        	        		
    	     	        				class MyELVSimpleAdapter extends SimpleExpandableListAdapter
    	     	        				{
    	     	        					
    	     	        					private MyELVSimpleAdapter()
    	     	        					{
    	     	        						super
    	     	        						(
    	     	        	        	    			getBaseContext(),
    	     	        	        	    			Book_parents,
    	     	        	        	    			R.layout.booklist_parent_row,
    	     	        	        	    			new String[]{"ISBNnumber","year","author","title","scndarytitle"},
    	     	        	        	    			new int[]{R.id.bookslist_ISBNnumber,R.id.bookslist_year,R.id.bookslist_author,R.id.bookslist_title,R.id.bookslist_scndarytitle},
    	     	        	        	    			Book_children,
    	     	        	        	    			R.layout.resourceslist_child_row,
    	     	        	        	    			new String[]{"summary"},
    	     	        	        	    			new int[]{R.id.resourceslist_summary}
    	     	        	        	    	 );
    	     	        					}
    	     	        					@Override
    	     	        					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent)
    	     	        					{
    	     	        						
    	     	        						final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    	     	        						
    	     	        						String imgName = "book" + IDs[groupPosition];
    	     	        						ImageView Image = (ImageView)v.findViewById(R.id.resourceslist_imageView1);
    	     	        						Drawable drawable = getResources().getDrawable(getResources().getIdentifier("se.green.combitech:drawable/"+imgName,null,null));
    	     	        						Image.setImageDrawable(drawable);
    	     	        						
    	     	        						return v;
    	     	        					}
    	     	        				}

    	     	        	    		ExpandableListView explv = (ExpandableListView)findViewById(R.id.resourceslist_explv);
    	     	        	    		
    	     	        	    		explv.setAdapter(new MyELVSimpleAdapter());
    	     	        				
    	    	        	}
    	    	        			
    	    	        	else
    	    	        	{
    	    	        		RESOURCETYPE[0] = "Documentary";
    	    	        		
    	    	        		String[] from = new String[]
     	     	        			                      {
     	     	        			    						KEY_ROWID,
     	     	        			    						KEY_TYPE,
     	     	        			    						KEY_YEAR,
     	     	        			    						KEY_TITLE,
     	     	        			    						KEY_SUMMARY
     	     	        			    	                };

     	     	        				
     	     	        				Cursor c = mDbHelper.fetchAll("Movies", from);
     	     	        				
     	     	        				startManagingCursor(c);
     	     	        				
     	     	        				final List<Map<String,String>> Movie_parents = new ArrayList<Map<String, String>>();
     	     	        		        final List<List<Map<String, String>>> Movie_children = new ArrayList<List<Map<String, String>>>();
     	     	        	        	
     	     	        	            final int[]IDs = new int[mDbHelper.fetchListResourceType("Movies", from, 1, RESOURCETYPE).getCount()];
     	     	        	        	
     	     	          	        	int j = 0;
     	     	          	        	
     	     	        	        	c.moveToFirst();
     	     	        	        	
     	     	        	        	do
     	     	        	        	{
     	     	        	        		if(RESOURCETYPE[0].equals(c.getString(c.getColumnIndex("Type"))))
     	     	        	        		{	    
     	     	        	        			IDs[j]= c.getPosition()+1;
     	     	        	        			Map<String, String> m1 = new HashMap<String, String>();
     	     	        	        			Movie_parents.add(m1);
     	     	        	        			m1.put("title", c.getString(c.getColumnIndex("Title")));
     	     	        	        			m1.put("year", c.getString(c.getColumnIndex("Year")));
     	     	        	        			
     	     	        	        			List<Map<String,String>> secList = new ArrayList<Map<String, String>>();
     	     	        	        			Map<String, String> m2 = new HashMap<String, String>();
     	     	        	        			secList.add(m2);
     	     	        	        			m2.put("summary",c.getString(c.getColumnIndex("Summary")));
     	     	        	        			
     	     	        	        			Movie_children.add(secList);
     	     	        	        			
     	     	        	        			j++;
     	     	        	        		}
     	     	        	        		
     	     	        	        	}while(c.moveToNext());
     	     	        	        		
     	     	        				class MyELVSimpleAdapter extends SimpleExpandableListAdapter
     	     	        				{
     	     	        					
     	     	        					private MyELVSimpleAdapter()
     	     	        					{
     	     	        						super
     	     	        						(
     	     	        	        	    			getBaseContext(),
     	     	        	        	    			Movie_parents,
     	     	        	        	    			R.layout.movielist_parent_row,
     	     	        	        	    			new String[]{"year","title"},
     	     	        	        	    			new int[]{R.id.movieslist_year,R.id.movieslist_title},
     	     	        	        	    			Movie_children,
     	     	        	        	    			R.layout.resourceslist_child_row,
     	     	        	        	    			new String[]{"summary"},
     	     	        	        	    			new int[]{R.id.resourceslist_summary}
     	     	        	        	    	 );
     	     	        					}
     	     	        					@Override
     	     	        					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent)
     	     	        					{
     	     	        						
     	     	        						final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
     	     	        						
     	     	        						String imgName = "movie" + IDs[groupPosition];
     	     	        						ImageView Image = (ImageView)v.findViewById(R.id.resourceslist_imageView1);
     	     	        						Drawable drawable = getResources().getDrawable(getResources().getIdentifier("se.green.combitech:drawable/"+imgName,null,null));
     	     	        						Image.setImageDrawable(drawable);
     	     	        						
     	     	        						return v;
     	     	        					}
     	     	        				}

     	     	        	    		ExpandableListView explv = (ExpandableListView)findViewById(R.id.resourceslist_explv);
     	     	        	    		
     	     	        	    		explv.setAdapter(new MyELVSimpleAdapter());
    	    	        		
    	    	        		
    	    	        	}
    	        		} 	        	
        			});
				}
				public void onNothingSelected(AdapterView<?> parent)
        		{
					Resource = "Books";
					
					ArrayAdapter<?> adapter =  ArrayAdapter.createFromResource
					(
							parent.getContext(), 
							R.array.resources_types_books, 
							android.R.layout.simple_spinner_item);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
					);
					
					spinner_type.setAdapter(adapter);

					spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
					{
						public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
						{
							String[] from = new String[]
    	        			                      {
    	        			    						KEY_ROWID,
    	        			    						KEY_TYPE,
    	        			    						KEY_ISBN,
    	        			    						KEY_YEAR,
    	        			    						KEY_AUTHOR,
    	        			    						KEY_TITLE,
    	        			    						KEY_SCNDARYTITLE,
    	        			    						KEY_SUMMARY
    	        			    	                };

    	        				
    	        				Cursor c = mDbHelper.fetchAll("Books", from);
    	        				
    	        				startManagingCursor(c);
    	        				
    	        				final List<Map<String,String>> Book_parents = new ArrayList<Map<String, String>>();
    	        		        final List<List<Map<String, String>>> Book_children = new ArrayList<List<Map<String, String>>>();
    	        	        	
    	        	            final int[]IDs = new int[mDbHelper.fetchListResourceType("Books", from, 1, RESOURCETYPE).getCount()];
    	        	        	
    	          	        	int j = 0;
    	          	        	
    	        	        	c.moveToFirst();
    	        	        	
    	        	        	do
    	        	        	{
    	        	        		if(RESOURCETYPE[0].equals(c.getString(c.getColumnIndex("Type"))))
    	        	        		{	    
    	        	        			IDs[j]= c.getPosition()+1;
    	        	        			Map<String, String> m1 = new HashMap<String, String>();
    	        	        			Book_parents.add(m1);
    	        	        			m1.put("ISBNnumber",c.getString(c.getColumnIndex("ISBNnumber")));
    	        	        			m1.put("title", c.getString(c.getColumnIndex("Title")));
    	        	        			m1.put("scndarytitle", c.getString(c.getColumnIndex("Scndarytitle")));
    	        	        			m1.put("author", c.getString(c.getColumnIndex("Author")));
    	        	        			m1.put("year", c.getString(c.getColumnIndex("Year")));
    	        	        			
    	        	        			List<Map<String,String>> secList = new ArrayList<Map<String, String>>();
    	        	        			Map<String, String> m2 = new HashMap<String, String>();
    	        	        			secList.add(m2);
    	        	        			m2.put("summary",c.getString(c.getColumnIndex("Summary")));
    	        	        			
    	        	        			Book_children.add(secList);
    	        	        			
    	        	        			j++;
    	        	        		}
    	        	        		
    	        	        	}while(c.moveToNext());
    	        	        		
    	        				class MyELVSimpleAdapter extends SimpleExpandableListAdapter
    	        				{
    	        					
    	        					private MyELVSimpleAdapter()
    	        					{
    	        						super
    	        						(
    	        	        	    			getBaseContext(),
    	        	        	    			Book_parents,
    	        	        	    			R.layout.booklist_parent_row,
    	        	        	    			new String[]{"ISBNnumber","year","author","title","scndarytitle"},
    	        	        	    			new int[]{R.id.bookslist_ISBNnumber,R.id.bookslist_year,R.id.bookslist_author,R.id.bookslist_title,R.id.bookslist_scndarytitle},
    	        	        	    			Book_children,
    	        	        	    			R.layout.resourceslist_child_row,
    	        	        	    			new String[]{"summary"},
    	        	        	    			new int[]{R.id.resourceslist_summary}
    	        	        	    	 );
    	        					}
    	        					@Override
    	        					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent)
    	        					{
    	        						
    	        						final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    	        						
    	        						String imgName = "book" + IDs[groupPosition];
    	        						ImageView Image = (ImageView)v.findViewById(R.id.resourceslist_imageView1);
    	        						Drawable drawable = getResources().getDrawable(getResources().getIdentifier("se.green.combitech:drawable/"+imgName,null,null));
    	        						Image.setImageDrawable(drawable);
    	        						
    	        						return v;
    	        					}
    	        				}

    	        	    		ExpandableListView explv = (ExpandableListView)findViewById(R.id.resourceslist_explv);
    	        	    		
    	        	    		explv.setAdapter(new MyELVSimpleAdapter());

						}

						public void onNothingSelected(AdapterView<?> parent)
						{
							RESOURCETYPE[0] = "Eco-Education";
							
							String[] from = new String[]
		    	        			                      {
		    	        			    						KEY_ROWID,
		    	        			    						KEY_TYPE,
		    	        			    						KEY_ISBN,
		    	        			    						KEY_YEAR,
		    	        			    						KEY_AUTHOR,
		    	        			    						KEY_TITLE,
		    	        			    						KEY_SCNDARYTITLE,
		    	        			    						KEY_SUMMARY
		    	        			    	                };

		    	        				
		    	        				Cursor c = mDbHelper.fetchAll("Books", from);
		    	        				
		    	        				startManagingCursor(c);
		    	        				
		    	        				final List<Map<String,String>> Book_parents = new ArrayList<Map<String, String>>();
		    	        		        final List<List<Map<String, String>>> Book_children = new ArrayList<List<Map<String, String>>>();
		    	        	        	
		    	        	            final int[]IDs = new int[mDbHelper.fetchListResourceType("Books", from, 1, RESOURCETYPE).getCount()];
		    	        	        	
		    	          	        	int j = 0;
		    	          	        	
		    	        	        	c.moveToFirst();
		    	        	        	
		    	        	        	do
		    	        	        	{
		    	        	        		if(RESOURCETYPE[0].equals(c.getString(c.getColumnIndex("Type"))))
		    	        	        		{	    
		    	        	        			IDs[j]= c.getPosition()+1;
		    	        	        			Map<String, String> m1 = new HashMap<String, String>();
		    	        	        			Book_parents.add(m1);
		    	        	        			m1.put("ISBNnumber",c.getString(c.getColumnIndex("ISBNnumber")));
		    	        	        			m1.put("title", c.getString(c.getColumnIndex("Title")));
		    	        	        			m1.put("scndarytitle", c.getString(c.getColumnIndex("Scndarytitle")));
		    	        	        			m1.put("author", c.getString(c.getColumnIndex("Author")));
		    	        	        			m1.put("year", c.getString(c.getColumnIndex("Year")));
		    	        	        			
		    	        	        			List<Map<String,String>> secList = new ArrayList<Map<String, String>>();
		    	        	        			Map<String, String> m2 = new HashMap<String, String>();
		    	        	        			secList.add(m2);
		    	        	        			m2.put("summary",c.getString(c.getColumnIndex("Summary")));
		    	        	        			
		    	        	        			Book_children.add(secList);
		    	        	        			
		    	        	        			j++;
		    	        	        		}
		    	        	        		
		    	        	        	}while(c.moveToNext());
		    	        	        		
		    	        				class MyELVSimpleAdapter extends SimpleExpandableListAdapter
		    	        				{
		    	        					
		    	        					private MyELVSimpleAdapter()
		    	        					{
		    	        						super
		    	        						(
		    	        	        	    			getBaseContext(),
		    	        	        	    			Book_parents,
		    	        	        	    			R.layout.booklist_parent_row,
		    	        	        	    			new String[]{"ISBNnumber","year","author","title","scndarytitle"},
		    	        	        	    			new int[]{R.id.bookslist_ISBNnumber,R.id.bookslist_year,R.id.bookslist_author,R.id.bookslist_title,R.id.bookslist_scndarytitle},
		    	        	        	    			Book_children,
		    	        	        	    			R.layout.resourceslist_child_row,
		    	        	        	    			new String[]{"summary"},
		    	        	        	    			new int[]{R.id.resourceslist_summary}
		    	        	        	    	 );
		    	        					}
		    	        					@Override
		    	        					public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent)
		    	        					{
		    	        						
		    	        						final View v = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		    	        						
		    	        						String imgName = "book" + IDs[groupPosition];
		    	        						ImageView Image = (ImageView)v.findViewById(R.id.resourceslist_imageView1);
		    	        						Drawable drawable = getResources().getDrawable(getResources().getIdentifier("se.green.combitech:drawable/"+imgName,null,null));
		    	        						Image.setImageDrawable(drawable);
		    	        						
		    	        						return v;
		    	        					}
		    	        				}

		    	        	    		ExpandableListView explv = (ExpandableListView)findViewById(R.id.resourceslist_explv);
		    	        	    		
		    	        	    		explv.setAdapter(new MyELVSimpleAdapter());
							
						}
					});
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
    			text.setText("The sources for the information in this section are www.greenbooks.co.uk, www.grist.org and www.imdb.com");

    			Toast toast = new Toast(getApplicationContext());
    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    			toast.setDuration(Toast.LENGTH_LONG);
    			toast.setView(layout);
    			toast.show();
    			return true;
    			
    		case R.id.return_categories:
    			Intent intent = new Intent(Resources_List.this,DisplayCategories.class);
				startActivity(intent);
    	}
		return false;
    }
 }