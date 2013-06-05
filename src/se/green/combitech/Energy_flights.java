package se.green.combitech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.lang.Math;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Energy_flights extends Activity 
{	
		
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "Name";
	public static final String KEY_CITY = "City";
	public static final String KEY_COUNTRY = "Country";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";
    //protected static String s = null;
    protected static ArrayList<String> s = new ArrayList<String>();
    protected static int i = 0;
    
    public String[] from = new String[]
		              {
		    				KEY_ROWID,
		    				KEY_NAME,
		    			    KEY_CITY,
		    			    KEY_COUNTRY,
		    			    KEY_LATITUDE,
		    			    KEY_LONGITUDE
		    		   };
			
    public String[] from2 = new String[]{KEY_COUNTRY};
			
	public int[] to = new int[] {R.id.name};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.energycarbon_flights);
	        
	        Intent thisIntent = getIntent();
	        
	        final Display display = getWindowManager().getDefaultDisplay(); 
    		final int width = display.getWidth();
    		final int height = display.getHeight();
	        
	        s.add("0");
	        
	        final Spinner spinner_class = (Spinner)findViewById(R.id.energycarbonflights_spinner4);
	        
			ArrayAdapter<?> adapter_class = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonflights_class, 
					android.R.layout.simple_spinner_item
			);
			adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner_class.setAdapter(adapter_class);
			
			final DataBaseHelper mDbHelper = new DataBaseHelper(Energy_flights.this.getBaseContext());
			
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
        			
        	final Cursor c  = mDbHelper.fetchAll("Airports", from); 
        	
        	startManagingCursor(c);  	
			
        	final Button energyflights_frombutton = (Button)findViewById(R.id.energycarbonflights_frombutton);
        	energyflights_frombutton.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			@SuppressWarnings("unchecked")
						public void onClick(View v)
	        			{
	        				ArrayList destinations = new ArrayList();
	        	        	final ArrayList airports = new ArrayList();
	        	        	
	        	        		
	        	        	c.moveToFirst();
	        	        		
	        	        	do
	        	        	{
	        	        		  
	        	        			String country = c.getString(c.getColumnIndex("Country"));
	        	        			HashMap<String, String> m1 = new HashMap<String, String>();
	        	        			m1.put("name",country);
	        	        			
	        	        			destinations.add(m1);
	        	        			
	        	        	}while(c.moveToNext());
	        	        		
	        	        	LinkedHashSet<HashMap<String, String>> hs1 = new LinkedHashSet<HashMap<String, String>>();
    	        			hs1.addAll(destinations);
    	        			destinations.clear();
    	        			destinations.addAll(hs1);
    	        			
    	        			for(int i=0;i<destinations.size();i++)
    	        			{
    	        				ArrayList secList = new ArrayList();	
    	        			
    	        				String a = destinations.get(i).toString();
    	        				a=a.substring(6,a.length()-1);
    	        				
    	        				c.moveToFirst();
    	        				
    	        				do
    	        	        	{
    	        					if(a.equals(c.getString(c.getColumnIndex("Country"))))
    	        					{
    	        						String name = c.getString(c.getColumnIndex("Name"));
    	        						
    	    	        	        	HashMap<String, String> m2 = new HashMap<String, String>();
    	    	        	        	
    	        	        			m2.put("name",name);
    	        	        			
    	        	        			secList.add(m2);
    	        					}
    	        	        	}while(c.moveToNext());
    	        				
    	        				airports.add(secList);
    	        			}
	        	        	
	        				LayoutInflater inflater = (LayoutInflater)Energy_flights.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                		View layout = inflater.inflate
	                			(
	                					R.layout.energyflight_expandable, null
	                			);
	                			
	                		final PopupWindow pw = new PopupWindow(layout, (width/3)*2, height/2, true);
	                			                		
	                		pw.showAtLocation(layout, Gravity.CENTER, 0, 10);
	                		
	                		SimpleExpandableListAdapter expladapter = new SimpleExpandableListAdapter
	        	        												(
	        	        													Energy_flights.this.getBaseContext(),
	        	        													destinations,
	        	        													R.layout.energyflight_row,
	        	        													new String[]{"name"},
	        	        													new int[]{R.id.name},
	        	        													airports,
	        	        													R.layout.energyflight_row,
	        	        													new String[]{"name"},
	        	        													new int[]{R.id.name}
	        	        											     );
	                		
	        				ExpandableListView explv = (ExpandableListView)layout.findViewById(R.id.energycarbonflight_destinations);
	        				
	        				explv.setAdapter(expladapter);
	        				
	        				explv.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
	        				{
	        					public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
	        					{
	        						if(groupPosition==0 && childPosition==0)
	        						{
	        							
	        						}
	        						
	        						;
							        						
	        						ArrayList interm = (ArrayList) airports.get(groupPosition);
	        						String buttontxt = interm.get(childPosition).toString();
	        						buttontxt=buttontxt.substring(6, buttontxt.length()-1);
	        						
	        						energyflights_frombutton.setText(buttontxt);
	        						
	        						pw.dismiss();
	        						
	        						return false;
	        					}
	        				});
	        			}	
	        		}
	        );
        	
        	final Button energyflights_viabutton = (Button)findViewById(R.id.energycarbonflights_viabutton);
        	energyflights_viabutton.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			@SuppressWarnings("unchecked")
						public void onClick(View v)
	        			{
	        				ArrayList destinations = new ArrayList();
	        	        	final ArrayList airports = new ArrayList();
	        	        	
	        	        		
	        	        	c.moveToFirst();
	        	        		
	        	        	do
	        	        	{
	        	        		  
	        	        			String country = c.getString(c.getColumnIndex("Country"));
	        	        			HashMap<String, String> m1 = new HashMap<String, String>();
	        	        			m1.put("name",country);
	        	        			
	        	        			destinations.add(m1);
	        	        			
	        	        	}while(c.moveToNext());
	        	        		
	        	        	LinkedHashSet<HashMap<String, String>> hs1 = new LinkedHashSet<HashMap<String, String>>();
    	        			hs1.addAll(destinations);
    	        			destinations.clear();
    	        			destinations.addAll(hs1);
    	        			
    	        			for(int i=0;i<destinations.size();i++)
    	        			{
    	        				ArrayList secList = new ArrayList();	
    	        			
    	        				String a = destinations.get(i).toString();
    	        				a=a.substring(6,a.length()-1);
    	        				
    	        				c.moveToFirst();
    	        				
    	        				do
    	        	        	{
    	        					if(a.equals(c.getString(c.getColumnIndex("Country"))))
    	        					{
    	        						String name = c.getString(c.getColumnIndex("Name"));
    	        						
    	    	        	        	HashMap<String, String> m2 = new HashMap<String, String>();
    	    	        	        	
    	        	        			m2.put("name",name);
    	        	        			
    	        	        			secList.add(m2);
    	        					}
    	        	        	}while(c.moveToNext());
    	        				
    	        				airports.add(secList);
    	        			}
	        	        	
	        				LayoutInflater inflater = (LayoutInflater)Energy_flights.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                		View layout = inflater.inflate
	                			(
	                					R.layout.energyflight_expandable, null
	                			);
	                			
	                		final PopupWindow pw = new PopupWindow(layout, (width/3)*2, height/2, true);
	                			                		
	                		pw.showAtLocation(layout, Gravity.CENTER, 0, 10);
	                		
	                		SimpleExpandableListAdapter expladapter = new SimpleExpandableListAdapter
	        	        												(
	        	        													Energy_flights.this.getBaseContext(),
	        	        													destinations,
	        	        													R.layout.energyflight_row,
	        	        													new String[]{"name"},
	        	        													new int[]{R.id.name},
	        	        													airports,
	        	        													R.layout.energyflight_row,
	        	        													new String[]{"name"},
	        	        													new int[]{R.id.name}
	        	        											     );
	                		
	        				ExpandableListView explv = (ExpandableListView)layout.findViewById(R.id.energycarbonflight_destinations);
	        				
	        				explv.setAdapter(expladapter);
	        				
	        				explv.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
	        				{
	        					public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
	        					{
	        						if(groupPosition==0 && childPosition==0)
	        						{
	        							
	        						}
	        						
	        						;
							        						
	        						ArrayList interm = (ArrayList) airports.get(groupPosition);
	        						String buttontxt = interm.get(childPosition).toString();
	        						buttontxt=buttontxt.substring(6, buttontxt.length()-1);
	        						
	        						energyflights_viabutton.setText(buttontxt);
	        						
	        						pw.dismiss();
	        						
	        						return false;
	        					}
	        				});
	        			}	
	        		}
	        );
        	
        	final Button energyflights_tobutton = (Button)findViewById(R.id.energycarbonflights_tobutton);
        	energyflights_tobutton.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			@SuppressWarnings("unchecked")
						public void onClick(View v)
	        			{
	        				ArrayList destinations = new ArrayList();
	        	        	final ArrayList airports = new ArrayList();
	        	        	
	        	        		
	        	        	c.moveToFirst();
	        	        		
	        	        	do
	        	        	{
	        	        		  
	        	        			String country = c.getString(c.getColumnIndex("Country"));
	        	        			HashMap<String, String> m1 = new HashMap<String, String>();
	        	        			m1.put("name",country);
	        	        			
	        	        			destinations.add(m1);
	        	        			
	        	        	}while(c.moveToNext());
	        	        		
	        	        	LinkedHashSet<HashMap<String, String>> hs1 = new LinkedHashSet<HashMap<String, String>>();
    	        			hs1.addAll(destinations);
    	        			destinations.clear();
    	        			destinations.addAll(hs1);
    	        			
    	        			for(int i=0;i<destinations.size();i++)
    	        			{
    	        				ArrayList secList = new ArrayList();	
    	        			
    	        				String a = destinations.get(i).toString();
    	        				a=a.substring(6,a.length()-1);
    	        				
    	        				c.moveToFirst();
    	        				
    	        				do
    	        	        	{
    	        					if(a.equals(c.getString(c.getColumnIndex("Country"))))
    	        					{
    	        						String name = c.getString(c.getColumnIndex("Name"));
    	        						
    	    	        	        	HashMap<String, String> m2 = new HashMap<String, String>();
    	    	        	        	
    	        	        			m2.put("name",name);
    	        	        			
    	        	        			secList.add(m2);
    	        					}
    	        	        	}while(c.moveToNext());
    	        				
    	        				airports.add(secList);
    	        			}
	        	        	
	        				LayoutInflater inflater = (LayoutInflater)Energy_flights.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                		View layout = inflater.inflate
	                			(
	                					R.layout.energyflight_expandable, null
	                			);
	                			
	                		final PopupWindow pw = new PopupWindow(layout, (width/3)*2, height/2, true);
	                			                		
	                		pw.showAtLocation(layout, Gravity.CENTER, 0, 10);
	                		
	                		SimpleExpandableListAdapter expladapter = new SimpleExpandableListAdapter
	        	        												(
	        	        													Energy_flights.this.getBaseContext(),
	        	        													destinations,
	        	        													R.layout.energyflight_row,
	        	        													new String[]{"name"},
	        	        													new int[]{R.id.name},
	        	        													airports,
	        	        													R.layout.energyflight_row,
	        	        													new String[]{"name"},
	        	        													new int[]{R.id.name}
	        	        											     );
	                		
	        				ExpandableListView explv = (ExpandableListView)layout.findViewById(R.id.energycarbonflight_destinations);
	        				
	        				explv.setAdapter(expladapter);
	        				
	        				explv.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
	        				{
	        					public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
	        					{
	        						if(groupPosition==0 && childPosition==0)
	        						{
	        							
	        						}
	        						
	        						;
							        						
	        						ArrayList interm = (ArrayList) airports.get(groupPosition);
	        						String buttontxt = interm.get(childPosition).toString();
	        						buttontxt=buttontxt.substring(6, buttontxt.length()-1);
	        						
	        						energyflights_tobutton.setText(buttontxt);
	        						
	        						pw.dismiss();
	        						
	        						return false;
	        					}
	        				});
	        			}	
	        		}
	        );
        	
        	
        	Button energyflights_button1 = (Button)findViewById(R.id.energycarbonflights_button1);
			energyflights_button1.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				TextView f,g;
	        				String from  = null, to = null, via = null, classe = null,
	        						Latitude_from = null, Latitude_to = null, Latitude_via = null,
	        						 Longitude_from = null, Longitude_to = null, Longitude_via = null,
	        						 	DirectionLat_from = null, DirectionLat_to = null, DirectionLat_via = null,
	        						 		DirectionLong_from = null, DirectionLong_to = null, DirectionLong_via = null;
	        				float distance1 = 0, distance2 = 0, distance, coeff, flight;
	        				RadioButton rb1;
	        				
	        				from = energyflights_frombutton.getText().toString();
	        				via = energyflights_viabutton.getText().toString();
	        				to = energyflights_tobutton.getText().toString();
	        			
	        				if(from.equals(getResources().getText(R.string.energycarbonflights_button)) || to.equals(getResources().getText(R.string.energycarbonflights_button)))
	        				{
	        					LayoutInflater inflater = getLayoutInflater();
	        	    			View layout = inflater.inflate(R.layout.energyflight_row,
	        	    			                               (ViewGroup) findViewById(R.id.name));

	        	    			TextView text = (TextView) layout.findViewById(R.id.name);
	        	    			text.setText("You must select departure and arrival airports");

	        	    			Toast toast = new Toast(getApplicationContext());
	        	    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	        	    			toast.setDuration(Toast.LENGTH_LONG);
	        	    			toast.setView(layout);
	        	    			toast.show();
	        				}
	        				else
	        				{
	        					c.moveToFirst();
	        					do
	        					{
	        						if(from.equals(c.getString(c.getColumnIndex("Name"))))
	        						{
	        							Latitude_from = c.getString(c.getColumnIndex("Latitude"));
	        							Longitude_from = c.getString(c.getColumnIndex("Longitude"));
	        						}
	        					
	        						if(to.equals(c.getString(c.getColumnIndex("Name"))))
	        						{
	        							Latitude_to = c.getString(c.getColumnIndex("Latitude"));
	        							Longitude_to = c.getString(c.getColumnIndex("Longitude"));
	        						}
	        					
	        						if(via.equals("Select an airport (Optional)") == false)
	        						{
	        							if(via.equals(c.getString(c.getColumnIndex("Name"))))
	        							{
	        								Latitude_via = c.getString(c.getColumnIndex("Latitude"));
	        								Longitude_via = c.getString(c.getColumnIndex("Longitude"));
	        							}
	        						}
	        					}while(c.moveToNext());
	        				
	        					DirectionLat_from = Latitude_from.substring(Latitude_from.length()-1);
	        					DirectionLong_from = Longitude_from.substring(Longitude_from.length()-1);
	        					double latitude_secondes = Double.parseDouble(Latitude_from.substring(Latitude_from.length()-4,Latitude_from.length()-2));
	        					double latitude_minutes = Double.parseDouble(Latitude_from.substring(Latitude_from.length()-7,Latitude_from.length()-5));
	        					double latitude_degrees = Double.parseDouble(Latitude_from.substring(0,Latitude_from.length()-8));
	        					double longitude_secondes = Double.parseDouble(Longitude_from.substring(Longitude_from.length()-4,Latitude_from.length()-2));
	        					double longitude_minutes = Double.parseDouble(Longitude_from.substring(Longitude_from.length()-7,Latitude_from.length()-5));
	        					double longitude_degrees = Double.parseDouble(Longitude_from.substring(0,Longitude_from.length()-8));
	        					double LATITUDE_from = latitude_degrees + (latitude_minutes * (1/60)) + (latitude_secondes * (1/3600));
	        					double LONGITUDE_from = longitude_degrees + (longitude_minutes * (1/60)) + (longitude_secondes * (1/3600));
	        					if(DirectionLat_from.equals("W") || DirectionLat_from.equals("S"))
	        					{
	        						LATITUDE_from = -LATITUDE_from;
	        					}
	        					if(DirectionLong_from.equals("W") || DirectionLong_from.equals("S"))
	        					{
	        						LONGITUDE_from = -LONGITUDE_from;
	        					}
	        				
	        				
	        					DirectionLat_to = Latitude_to.substring(Latitude_to.length()-1);
	        					DirectionLong_to = Longitude_to.substring(Longitude_to.length()-1);
	        					latitude_secondes = Double.parseDouble(Latitude_to.substring(Latitude_to.length()-4,Latitude_to.length()-2));
	        					latitude_minutes = Double.parseDouble(Latitude_to.substring(Latitude_to.length()-7,Latitude_to.length()-5));
	        					latitude_degrees = Double.parseDouble(Latitude_to.substring(0,Latitude_to.length()-8));
	        					longitude_secondes = Double.parseDouble(Longitude_to.substring(Longitude_to.length()-4,Latitude_to.length()-2));
	        					longitude_minutes = Double.parseDouble(Longitude_to.substring(Longitude_to.length()-7,Latitude_to.length()-5));
	        					longitude_degrees = Double.parseDouble(Longitude_to.substring(0,Longitude_to.length()-8));
	        					double LATITUDE_to = latitude_degrees + (latitude_minutes * (1/60)) + (latitude_secondes * (1/3600));
	        					double LONGITUDE_to = longitude_degrees + (longitude_minutes * (1/60)) + (longitude_secondes * (1/3600));
	        					if(DirectionLat_to.equals("W") || DirectionLat_to.equals("S"))
	        					{
	        						LATITUDE_to = -LATITUDE_to;
	        					}
	        					if(DirectionLong_to.equals("W") || DirectionLong_to.equals("S"))
	        					{
	        						LONGITUDE_to = -LONGITUDE_to;
	        					}

	        					if(via.equals(getResources().getText(R.string.energycarbonflights_viabutton)) || via.equals("None"))
	        					{	
	        						double dLat = (LATITUDE_to-LATITUDE_from)*(Math.PI/180);
	        						double Lat1 = LATITUDE_from*(Math.PI/180);
	        						double Lat2 = LATITUDE_to*(Math.PI/180);
	        						double dLong = (LONGITUDE_to-LONGITUDE_from)*(Math.PI/180);
	        				
	        						double a = Math.sin(dLat/2)*Math.sin(dLat/2)
	        									+ Math.sin(dLong/2)*Math.sin(dLong/2)
	        									* Math.cos(Lat1)*Math.cos(Lat2);
	        				
	        						distance1 = (float) ((float) 6371*2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
	        					}
	        					else
	        					{	DirectionLat_via = Latitude_via.substring(Latitude_via.length()-1);
	        						DirectionLong_via = Longitude_via.substring(Longitude_via.length()-1);
	        						latitude_secondes = Double.parseDouble(Latitude_via.substring(Latitude_via.length()-4,Latitude_via.length()-2));
	        						latitude_minutes = Double.parseDouble(Latitude_via.substring(Latitude_via.length()-7,Latitude_via.length()-5));
	        						latitude_degrees = Double.parseDouble(Latitude_via.substring(0,Latitude_via.length()-8));
	        						longitude_secondes = Double.parseDouble(Longitude_via.substring(Longitude_via.length()-4,Latitude_via.length()-2));
	        						longitude_minutes = Double.parseDouble(Longitude_via.substring(Longitude_via.length()-7,Latitude_via.length()-5));
	        						longitude_degrees = Double.parseDouble(Longitude_via.substring(0,Longitude_via.length()-8));
	        						double LATITUDE_via = latitude_degrees + (latitude_minutes * (1/60)) + (latitude_secondes * (1/3600));
	        						double LONGITUDE_via = longitude_degrees + (longitude_minutes * (1/60)) + (longitude_secondes * (1/3600));
	        						if(DirectionLat_via.equals("W") || DirectionLat_via.equals("S"))
	        						{
	        							LATITUDE_via = -LATITUDE_via;
	        						}
	        						if(DirectionLong_via.equals("W") || DirectionLong_via.equals("S"))
	        						{
	        							LONGITUDE_via = -LONGITUDE_via;
	        						}
	        					
	        				
	        						double dLat1 = (LATITUDE_via-LATITUDE_from)*(Math.PI/180);
	        						double dLat2 = (LATITUDE_to-LATITUDE_via)*(Math.PI/180);
	        						double Lat1 = LATITUDE_from*(Math.PI/180);
	        						double Lat2 = LATITUDE_via*(Math.PI/180);
	        						double Lat3 = LATITUDE_to*(Math.PI/180);
	        						double dLong1 = (LONGITUDE_via-LONGITUDE_from)*(Math.PI/180);
	        						double dLong2 = (LONGITUDE_to-LONGITUDE_via)*(Math.PI/180);
	        				
	        						double a1 = Math.sin(dLat1/2)*Math.sin(dLat1/2)
	        									+ Math.sin(dLong1/2)*Math.sin(dLong1/2)
	        									* Math.cos(Lat1)*Math.cos(Lat2);
	        				
	        						double a2 = Math.sin(dLat2/2)*Math.sin(dLat2/2)
	        									+ Math.sin(dLong2/2)*Math.sin(dLong2/2)
	        									* Math.cos(Lat2)*Math.cos(Lat3);
	        				
	        						distance1 = (float) ((float) 6371*2*Math.atan2(Math.sqrt(a1), Math.sqrt(1-a1)));
	        						distance2 = (float) ((float) 6371*2*Math.atan2(Math.sqrt(a2), Math.sqrt(1-a2)));
	        					}      			
	        					
	        					distance=distance1+distance2;
	        				
	        					classe = (String) spinner_class.getSelectedItem();
	        				
	        					if(classe.equals("Economy class"))
	        					{
	        						if(distance<=463)
	        						{
	        							coeff=(float) 0.20515;
	        						}
	        						else if(distance<=1108)
	        						{
	        							coeff = (float) 0.11054;
	        						}
	        						else
	        						{
	        							coeff = (float) 0.09881;
	        						}
	        					}
	        					else if(classe.equals("Premium Economy class"))
	        					{
	        						if(distance<=463)
	        						{
	        							coeff=(float) 0.20515;
	        						}
	        						else if(distance<=1108)
	        						{
	        							coeff = (float) 0.11600;
	        						}
	        						else
	        						{
	        							coeff = (float) 0.15809;
	        						}
	        					}
	        					else if(classe.equals("Business class"))
	        					{
	        						if(distance<=463)
	        						{
	        							coeff=(float) 0.20515;
	        						}
	        						else if(distance<=1108)
	        						{
	        							coeff = (float) 0.16581;
	        						}
	        						else
	        						{
	        							coeff = (float) 0.28654;
	        						}
	        					}
	        					else 
	        					{
	        						if(distance<=463)
	        						{
	        							coeff=(float) 0.20515;
	        						}
	        						else if(distance<=1108)
	        						{
	        							coeff = (float) 0.16581;
	        						}
	        						else
	        						{
	        							coeff = (float) 0.39523;
	        						}
	        					}
	        				
	        					EditText e = (EditText)findViewById(R.id.energycarbonflights_editText1);
	        					
	        					String a = e.getText().toString();
	        					
	        					float trips = Float.parseFloat(a);
	                        
	        					distance = trips*distance;
	                        
	        					rb1 = (RadioButton)findViewById(R.id.energycarbonflights_radioButton1);
	        					        				
	        					if(rb1.isChecked())
	        					{
	        						distance = distance*2;
	        					}
	        					
	        					flight=distance*coeff;
	        				
	        					s.add(i, new Float(flight).toString());
	        					
	        	    			LayoutInflater inflater = getLayoutInflater();
	        	    			View layout = inflater.inflate(R.layout.energyflight_row,
	        	    			                               (ViewGroup) findViewById(R.id.name));

	        	    			TextView text = (TextView) layout.findViewById(R.id.name);
	        	    			text.setText("The carbon footprint of this flight is: " + s.get(i) + " kg of CO2");

	        	    			Toast toast = new Toast(getApplicationContext());
	        	    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	        	    			toast.setDuration(Toast.LENGTH_LONG);
	        	    			toast.setView(layout);
	        	    			toast.show();
	        						
	        				}
	        			}
	        		}
	        ); 
			
			
			Button energyflights_button2 = (Button)findViewById(R.id.energycarbonflights_button2);
			energyflights_button2.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				energyflights_frombutton.setText(R.string.energycarbonflights_button);
	        				energyflights_viabutton.setText(R.string.energycarbonflights_viabutton);
	        				energyflights_tobutton.setText(R.string.energycarbonflights_button);
	        				
	        				EditText trips = (EditText) findViewById(R.id.energycarbonflights_editText1);
	        				trips.setText("1");
	        				i++;
	        			}
	        		}
	        );
			
			
			Button energyflights_button3 = (Button)findViewById(R.id.energycarbonflights_button3);
			energyflights_button3.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				Intent intent = new Intent(Energy_flights.this,Energy_car.class);
	                        intent.putExtra("house", getIntent().getStringExtra("house"));
	                        intent.putStringArrayListExtra("flights", s);
	                        startActivity(intent);
	        			}
	        		}
	        ); 
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
    		
    		case R.id.return_categories	:
    			Intent intent = new Intent(Energy_flights.this,DisplayCategories.class);
				startActivity(intent);
    			return true;
    	}
		return false;
    }
 }
