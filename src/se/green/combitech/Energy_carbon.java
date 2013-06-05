package se.green.combitech;

import java.io.IOException;

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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class Energy_carbon extends Activity 
{	
	protected static String s = "0";

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.energycarbon_house);
	        
	        Intent thisIntent = getIntent();
	        
	        final Display display = getWindowManager().getDefaultDisplay(); 
    		final int width = display.getWidth();
    		final int height = display.getHeight();
    		
	        final Spinner spinner_people = (Spinner)findViewById(R.id.energycarbonhouse_nbspinner);
	        final Spinner spinner_electricity = (Spinner)findViewById(R.id.energycarbonhouse_electricityspinner);
	        final Spinner spinner_naturalgas = (Spinner)findViewById(R.id.energycarbonhouse_naturalgasspinner);
	        final Spinner spinner_heatingoil = (Spinner)findViewById(R.id.energycarbonhouse_heatingoilspinner);
	        final Spinner spinner_coal = (Spinner)findViewById(R.id.energycarbonhouse_coalspinner);
	        final Spinner spinner_lpg = (Spinner)findViewById(R.id.energycarbonhouse_lpgspinner);
	        final Spinner spinner_pellets = (Spinner)findViewById(R.id.energycarbonhouse_pelletsspinner);

			ArrayAdapter<?> adapter_people = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonhouse_people, 
					android.R.layout.simple_spinner_item
			);
			adapter_people.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_electricity = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonhouse_electricity, 
					android.R.layout.simple_spinner_item
			);
			adapter_electricity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_naturalgas = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonhouse_naturalgas, 
					android.R.layout.simple_spinner_item
			);
			adapter_naturalgas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_heatingoil = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonhouse_heatingoil, 
					android.R.layout.simple_spinner_item
			);
			adapter_heatingoil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_coal = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonhouse_coal, 
					android.R.layout.simple_spinner_item
			);
			adapter_coal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_lpg = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonhouse_lpg, 
					android.R.layout.simple_spinner_item
			);
			adapter_lpg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_pellets = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonhouse_pellets, 
					android.R.layout.simple_spinner_item
			);
			adapter_pellets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
			spinner_people.setAdapter(adapter_people);
			spinner_electricity.setAdapter(adapter_electricity);
			spinner_naturalgas.setAdapter(adapter_naturalgas);
			spinner_heatingoil.setAdapter(adapter_heatingoil);
			spinner_coal.setAdapter(adapter_coal);
			spinner_lpg.setAdapter(adapter_lpg);
			spinner_pellets.setAdapter(adapter_pellets);
						
			Button energycarbon_button1 = (Button)findViewById(R.id.energycarbonhouse_calculate);
			energycarbon_button1.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				EditText e;
	        				TextView f,g;
	        				float unit = 0, people = 0, house = 0;
	        				String p,unit_s;
	        				
	        				p = (String)spinner_people.getSelectedItem();
	        				people = Integer.parseInt(p);
	        				
	        				e = (EditText)findViewById(R.id.energycarbonhouse_electricitynb);
	        				s = e.getText().toString();
	                        float electricity = Float.parseFloat(s);
	                        unit_s = (String)spinner_electricity.getSelectedItem();
	                        if(unit_s.equals("kWh"))
	                        {
	                        	unit=(float) 0.61707;
	                        }
	                        electricity=unit*electricity;
	                        
	                        e = (EditText)findViewById(R.id.energycarbonhouse_naturalgasnb);
	        				s = e.getText().toString();
	                        float naturalgas = Float.parseFloat(s);
	                        unit_s = (String)spinner_naturalgas.getSelectedItem();
	                        if(unit_s.equals("kWh"))
	                        {
	                        	unit=(float) 0.22554;
	                        }
	                        else if(unit_s.equals("therms"))
	                        {
	                        	unit=(float) 6.61004;
	                        }
	                        else if(unit_s.equals("cubic meters"))
	                        {
	                        	unit = (float) 2.224;
	                        }
	                        naturalgas=unit*naturalgas;
	                        
	                        e = (EditText)findViewById(R.id.energycarbonhouse_heatingoilnb);
	        				s = e.getText().toString();
	        				float heatingoil = Float.parseFloat(s);
	                        unit_s = (String)spinner_heatingoil.getSelectedItem();
	                        if(unit_s.equals("kWh"))
	                        {
	                        	unit=(float) 0.30786;
	                        }
	                        else if(unit_s.equals("litres"))
	                        {
	                        	unit=(float) 3.0121;
	                        }
	                        else if(unit_s.equals("tonnes"))
	                        {
	                        	unit=(float) 3750.1;
	                        }
	                        else if(unit_s.equals("US gallons"))
	                        {
	                        	unit=(float) 11.4008;
	                        }
	                        else if(unit_s.equals("UK gallons"))
	                        {
	                        	unit=(float) 13.6930;
	                        }
	                        heatingoil=unit*heatingoil;
	                        
	                        e = (EditText)findViewById(R.id.energycarbonhouse_coalnb);
	        				s = e.getText().toString();
	        				float coal = Float.parseFloat(s);
	                        unit_s = (String)spinner_coal.getSelectedItem();
	                        if(unit_s.equals("kWh"))
	                        {
	                        	unit=(float) 0.41342;
	                        }
	                        else if(unit_s.equals("tonnes"))
	                        {
	                        	unit=(float) 3327.5;
	                        }
	                        coal=unit*coal;
	                        
	                        e = (EditText)findViewById(R.id.energycarbonhouse_lpgnb);
	        				s = e.getText().toString();
	        				float lpg = Float.parseFloat(s);
	                        unit_s = (String)spinner_lpg.getSelectedItem();
	                        if(unit_s.equals("kWh"))
	                        {
	                        	unit=(float) 0.25907;
	                        }
	                        else if(unit_s.equals("therms"))
	                        {
	                        	unit=(float) 7.59255;
	                        }
	                        else if(unit_s.equals("litres"))
	                        {
	                        	unit=(float) 1.6786;
	                        }
	                        lpg=unit*lpg;
	                        
	                        e = (EditText)findViewById(R.id.energycarbonhouse_pelletsnb);
	        				s = e.getText().toString();
	        				float pellets = Float.parseFloat(s);
	                        unit_s = (String)spinner_pellets.getSelectedItem();
	                        if(unit_s.equals("tonnes"))
	                        {
	                        	unit=(float) 183.93;
	                        }
	                        else if(unit_s.equals("kWh of fuel"))
	                        {
	                        	unit=(float) 0.04;
	                        }
	                        pellets=unit*pellets;
	                        
	                        house = (electricity + naturalgas + heatingoil + coal 
	                        	+ lpg + pellets)/people;
	                        
	                        s = (new Float(house)).toString();
	                       
	                        LayoutInflater inflater = getLayoutInflater();
        	    			View layout = inflater.inflate(R.layout.energyflight_row,
        	    			                               (ViewGroup) findViewById(R.id.name));

        	    			TextView text = (TextView) layout.findViewById(R.id.name);
        	    			text.setText("Your total household carbon footprint is:" + s + " kg of CO2");

        	    			Toast toast = new Toast(getApplicationContext());
        	    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        	    			toast.setDuration(Toast.LENGTH_LONG);
        	    			toast.setView(layout);
        	    			toast.show();
        	    				                      
	        			}
	        		}
	        ); 

			Button energycarbon_button2 = (Button)findViewById(R.id.energycarbonhouse_next);
			energycarbon_button2.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
            				Intent intent = new Intent(Energy_carbon.this,Energy_flights.class);
	                        intent.putExtra("house", s);
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
    			Intent intent = new Intent(Energy_carbon.this,DisplayCategories.class);
				startActivity(intent);
    			return true;
    	}
		return false;
    }
}