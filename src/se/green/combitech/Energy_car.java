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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Energy_car extends Activity 
{	
	protected static String s = "0";
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.energycarbon_car);
	        
	        Intent thisIntent = getIntent();
	        
	        final Display display = getWindowManager().getDefaultDisplay(); 
    		final int width = display.getWidth();
    		final int height = display.getHeight();
	        
	        final Spinner spinner_mileage1 = (Spinner)findViewById(R.id.energycarboncar_mileagespinner1);
	        final Spinner spinner_efficiency1 = (Spinner)findViewById(R.id.energycarboncar_efficiencyspinner1);
	        final Spinner spinner_mileage2 = (Spinner)findViewById(R.id.energycarboncar_mileagespinner2);
	        final Spinner spinner_efficiency2 = (Spinner)findViewById(R.id.energycarboncar_efficiencyspinner2);
	        final Spinner spinner_gas1 = (Spinner)findViewById(R.id.energycarboncar_gasspinner1);
	        final Spinner spinner_gas2 = (Spinner)findViewById(R.id.energycarboncar_gasspinner2);
	        
	        ArrayAdapter<?> adapter_mileage = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarboncar_mileage, 
					android.R.layout.simple_spinner_item
			);
			adapter_mileage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_mileage1.setAdapter(adapter_mileage);
			spinner_mileage2.setAdapter(adapter_mileage);

			spinner_mileage1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
			{
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
					if(pos==0)
					{
						ArrayAdapter<?> adapter_efficiency = ArrayAdapter.createFromResource
	        			(
	        					parent.getContext(), 
	        					R.array.energycarboncar_efficiencykm, 
	        					android.R.layout.simple_spinner_item
	        			);
	        			adapter_efficiency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        			spinner_efficiency1.setAdapter(adapter_efficiency);
					}
					else
					{
						ArrayAdapter<?> adapter_efficiency = ArrayAdapter.createFromResource
	        			(
	        					parent.getContext(), 
	        					R.array.energycarboncar_efficiencymiles, 
	        					android.R.layout.simple_spinner_item
	        			);
	        			adapter_efficiency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        			spinner_efficiency1.setAdapter(adapter_efficiency);
					}	
				}
				
        		public void onNothingSelected(AdapterView<?> parent)
    	        {
        			ArrayAdapter<?> adapter_efficiency = ArrayAdapter.createFromResource
        			(
        					parent.getContext(), 
        					R.array.energycarboncar_efficiencykm, 
        					android.R.layout.simple_spinner_item
        			);
        			adapter_efficiency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        			spinner_efficiency1.setAdapter(adapter_efficiency);
    	        }
			});

			
			spinner_mileage2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
			{
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
					if(pos==0)
					{
						ArrayAdapter<?> adapter_efficiency = ArrayAdapter.createFromResource
	        			(
	        					parent.getContext(), 
	        					R.array.energycarboncar_efficiencykm, 
	        					android.R.layout.simple_spinner_item
	        			);
	        			adapter_efficiency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        			spinner_efficiency2.setAdapter(adapter_efficiency);
					}
					else
					{
						ArrayAdapter<?> adapter_efficiency = ArrayAdapter.createFromResource
	        			(
	        					parent.getContext(), 
	        					R.array.energycarboncar_efficiencymiles, 
	        					android.R.layout.simple_spinner_item
	        			);
	        			adapter_efficiency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        			spinner_efficiency2.setAdapter(adapter_efficiency);
					}	
				}
				
        		public void onNothingSelected(AdapterView<?> parent)
    	        {
        			ArrayAdapter<?> adapter_efficiency = ArrayAdapter.createFromResource
        			(
        					parent.getContext(), 
        					R.array.energycarboncar_efficiencykm, 
        					android.R.layout.simple_spinner_item
        			);
        			adapter_efficiency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        			spinner_efficiency2.setAdapter(adapter_efficiency);
    	        }
			});
			
			ArrayAdapter<?> adapter_gas = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarboncar_gas, 
					android.R.layout.simple_spinner_item
			);
			adapter_gas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner_gas1.setAdapter(adapter_gas);
			spinner_gas2.setAdapter(adapter_gas);
			
			Button energycar_button1 = (Button)findViewById(R.id.energycarboncar_button1);
			energycar_button1.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				EditText e;
	        				TextView f,g;
	        				float coeff1 = 0, coeff2 = 0, mileage = 0;
	        				String unit_s1,unit_s2, unit_s3;
	        				
	        				e = (EditText)findViewById(R.id.energycarboncar_editmileage1);
	        				s = e.getText().toString();
	                        float mileage1 = Float.parseFloat(s);
	        				unit_s1 = (String)spinner_mileage1.getSelectedItem();
	        				
	        				e = (EditText)findViewById(R.id.energycarboncar_editefficiency1);
	        				s = e.getText().toString();
	                        float efficiency1 = Float.parseFloat(s);
	        				unit_s2 = (String)spinner_efficiency1.getSelectedItem();
	        				
	        				unit_s3 = (String)spinner_gas1.getSelectedItem();
	        				
	                        if(unit_s2.equals("L/100"))
                        	{
                        		mileage1=(efficiency1*mileage1)/100;
                        	}
	                        else if(unit_s2.equals("g/km(UK)") || unit_s2.equals("mpg(UK)"))
                        	{
                        		mileage1=(float) (efficiency1*mileage1*4.546);
                        	}
                        	else 
                        	{
                        		mileage1=(float) (efficiency1*mileage1*3.785);
                        	}

	                        if(unit_s3.equals("petrol"))
                        	{
                        		coeff1=(float) 2.7329;
                        	}
                        	else if(unit_s3.equals("diesel"))
    	        			{
    	        				coeff1=(float) 3.1787;
    	        			}
    	        			else if(unit_s3.equals("LPG"))
    	        			{
    	        				coeff1=(float) 1.6786;
    	        			}
    	        			else if(unit_s3.equals("LNG/CNG"))
    	        			{
    	        				coeff1=(float) 0.00261;
    	        			}
    	        			else if(unit_s3.equals("Biodiesel"))
    	        			{
    	        				coeff1=(float) 2.493;
    	        			}
    	        			else if(unit_s3.equals("Bioethanol"))
    	        			{
    	        				coeff1=(float) 1.5236;
    	        			}
    	        			else if(unit_s3.equals("Biomethane"))
    	        			{
    	        				coeff1=(float) 0.002;
    	        			}
	        				mileage1=coeff1*mileage1;
	        				
	        				//Motorbikes
	        				e = (EditText)findViewById(R.id.energycarboncar_editmileage2);
	        				s = e.getText().toString();
	                        float mileage2 = Float.parseFloat(s);
	        				unit_s1 = (String)spinner_mileage2.getSelectedItem();
	        				
	        				e = (EditText)findViewById(R.id.energycarboncar_editefficiency2);
	        				s = e.getText().toString();
	                        float efficiency2 = Float.parseFloat(s);
	        				unit_s2 = (String)spinner_efficiency2.getSelectedItem();
	        				
	        				unit_s3 = (String)spinner_gas2.getSelectedItem();
	        				
	                        if(unit_s1.equals("km"))
	                        {
	                        	if(unit_s2.equals("L/100"))
	                        	{
	                        		mileage2=(efficiency2*mileage2)/100;
	                        	}
	                        	else if(unit_s2.equals("g/km(UK)"))
	                        	{
	                        		mileage2=(float) (efficiency2*mileage2*4.546);
	                        	}
	                        	else 
	                        	{
	                        		mileage2=(float) (efficiency2*mileage1*3.785);
	                        	}
	                        }
	                        else 
	                        {	
	                        	if(unit_s2.equals("mpg(UK)"))
	                        	{
	                        		mileage2=(float) ((mileage2/efficiency2)*4.546);
	                        	}
	                        	else if(unit_s2.equals("mpg(US)"))
	                        	{
	                        		mileage2=(float) (efficiency2*mileage2*3.785);
	                        	}	 
	                        }
	                        
	                        if(unit_s3.equals("petrol"))
                        	{
                        		coeff2=(float) 2.7329;
                        	}
                        	else if(unit_s3.equals("diesel"))
    	        			{
    	        				coeff2=(float) 3.1787;
    	        			}
    	        			else if(unit_s3.equals("LPG"))
    	        			{
    	        				coeff2=(float) 1.6786;
    	        			}
    	        			else
    	        			{
    	        				coeff2=(float) 0;//donnee manquante CNG
    	        			}	
	        				mileage2=coeff2*mileage2;
	        				
	        				mileage = mileage1 + mileage2;
	        				
	        				s = (new Float(mileage)).toString();
		                       
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
			
			Button energycar_button2 = (Button)findViewById(R.id.energycarboncar_button2);
			energycar_button2.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				Intent intent = new Intent(Energy_car.this,Energy_bus.class);
	                        intent.putExtra("house", getIntent().getStringExtra("house"));
	                        intent.putStringArrayListExtra("flights", getIntent().getStringArrayListExtra("flights"));
            				intent.putExtra("carbike", s);
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
    			Intent intent = new Intent(Energy_car.this,DisplayCategories.class);
				startActivity(intent);
    			return true;
    	}
		return false;
    }
}
