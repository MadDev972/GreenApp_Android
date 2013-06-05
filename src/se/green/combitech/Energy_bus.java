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

public class Energy_bus extends Activity 
{	
	protected static String s = "0";	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.energycarbon_bus);
	        
	        Intent thisIntent = getIntent();
	        
	        final Display display = getWindowManager().getDefaultDisplay(); 
    		final int width = display.getWidth();
    		final int height = display.getHeight();
	        
	        final Spinner spinner_bus = (Spinner)findViewById(R.id.energycarbonbus_busspinner);
	        final Spinner spinner_localtrain = (Spinner)findViewById(R.id.energycarbonbus_localtrainspinner);
	        final Spinner spinner_train = (Spinner)findViewById(R.id.energycarbonbus_trainspinner);
	        final Spinner spinner_tram = (Spinner)findViewById(R.id.energycarbonbus_tramspinner);
	        final Spinner spinner_subway = (Spinner)findViewById(R.id.energycarbonbus_subwayspinner);
	        final Spinner spinner_taxi = (Spinner)findViewById(R.id.energycarbonbus_taxispinner);
	        
	        ArrayAdapter<?> adapter_mileage = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarboncar_mileage, 
					android.R.layout.simple_spinner_item
			);
			adapter_mileage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner_bus.setAdapter(adapter_mileage);
			spinner_localtrain.setAdapter(adapter_mileage);
			spinner_train.setAdapter(adapter_mileage);
			spinner_tram.setAdapter(adapter_mileage);
			spinner_subway.setAdapter(adapter_mileage);
			spinner_taxi.setAdapter(adapter_mileage);

			
			Button energybus_button1 = (Button)findViewById(R.id.energycarbonbus_button1);
			energybus_button1.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				TextView f,g;
	        				EditText e;
	        				float coeff = 0, distance = 0;
	        				String p,unit_s;
	        				
	        				e = (EditText)findViewById(R.id.energycarbonbus_busedit);
	        				s = e.getText().toString();
	                        float bus = Float.parseFloat(s);
	        				unit_s = (String)spinner_bus.getSelectedItem();
	        				
	                        if(unit_s.equals("km"))
	                        {
	                        	coeff=(float) 0.16084;
	                        }
	                        else 
	                        {	
	                        	coeff=(float) (0.16084*1.609344); 
	                        }
	                        
	                        bus=coeff*bus;
	                        
	                        
	                        e = (EditText)findViewById(R.id.energycarbonbus_localtrainedit);
	        				s = e.getText().toString();
	                        float localtrain = Float.parseFloat(s);
	        				unit_s = (String)spinner_localtrain.getSelectedItem();
	        				
	                        if(unit_s.equals("km"))
	                        {
	                        	coeff=(float) 0.16084;
	                        }
	                        else 
	                        {	
	                        	coeff=(float) (0.16084*1.609344); 
	                        }
	                        
	                        localtrain=coeff*localtrain;
	                        
	                        
	                        e = (EditText)findViewById(R.id.energycarbonbus_trainedit);
	        				s = e.getText().toString();
	                        float train = Float.parseFloat(s);
	        				unit_s = (String)spinner_train.getSelectedItem();
	        				
	                        if(unit_s.equals("km"))
	                        {
	                        	coeff=(float) 0.06510;
	                        }
	                        else 
	                        {	
	                        	coeff=(float) (0.06510*1.609344); 
	                        }
	                        
	                        train=coeff*train;
	                        
	                        
	                        e = (EditText)findViewById(R.id.energycarbonbus_tramedit);
	        				s = e.getText().toString();
	                        float tram = Float.parseFloat(s);
	        				unit_s = (String)spinner_tram.getSelectedItem();
	        				
	                        if(unit_s.equals("km"))
	                        {
	                        	coeff=(float) 0.08761;
	                        }
	                        else 
	                        {	
	                        	coeff=(float) (0.08761*1.609344); 
	                        }
	                        
	                        tram=coeff*tram;
	                        
	                        
	                        e = (EditText)findViewById(R.id.energycarbonbus_subwayedit);
	        				s = e.getText().toString();
	                        float subway = Float.parseFloat(s);
	        				unit_s = (String)spinner_subway.getSelectedItem();
	        				
	                        if(unit_s.equals("km"))
	                        {
	                        	coeff=(float) 0.08457;
	                        }
	                        else 
	                        {	
	                        	coeff=(float) (0.08457*1.609344); 
	                        }
	                        
	                        subway=coeff*subway;
	                        
	                        
	                        e = (EditText)findViewById(R.id.energycarbonbus_taxiedit);
	        				s = e.getText().toString();
	                        float taxi = Float.parseFloat(s);
	        				unit_s = (String)spinner_taxi.getSelectedItem();
	        				
	                        if(unit_s.equals("km"))
	                        {
	                        	coeff=(float) 0.18274;
	                        }
	                        else 
	                        {	
	                        	coeff=(float) (0.18274*1.609344); 
	                        }
	                        
	                        taxi=coeff*taxi;
	                        
	                        distance = bus + localtrain + tram + train + subway + taxi;
	                        
	                        s = (new Float(distance)).toString();
		                    
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
			
			Button energybus_button2 = (Button)findViewById(R.id.energycarbonbus_button2);
			energybus_button2.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				Intent intent = new Intent(Energy_bus.this,Energy_secondary.class);
	                        intent.putExtra("house", getIntent().getStringExtra("house"));
	                        intent.putStringArrayListExtra("flights", getIntent().getStringArrayListExtra("flights"));
	                        intent.putExtra("carbike", getIntent().getStringExtra("carbike"));
            				intent.putExtra("bus", s);
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
    			Intent intent = new Intent(Energy_bus.this,DisplayCategories.class);
				startActivity(intent);
    			return true;
    	}
		return false;
    }
}
