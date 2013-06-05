package se.green.combitech;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Energy_results extends Activity 
{	
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.energycarbon_results);
	        
	        Intent thisIntent = getIntent();
	        
	        TextView g;
	        float H=0,F=0,C=0,B=0,S=0;
	        
	        String house = getIntent().getStringExtra("house");
	        H = Float.parseFloat(house);
	        g = (TextView)findViewById(R.id.energycarbonresult_houseresult);
            g.setText(house + " kg of CO2");
	        
	        ArrayList<String> Flights = getIntent().getStringArrayListExtra("flights");
            for(int j=0;j<Flights.size();j++)
            {
            	F = F + Float.parseFloat(Flights.get(j).toString());
            }
            String flights = Float.toString(F);
	        g = (TextView)findViewById(R.id.energycarbonresult_flightsresult);
            g.setText(flights + " kg of CO2");
            
	        String carbike = getIntent().getStringExtra("carbike");
	        C = Float.parseFloat(carbike);
	        g = (TextView)findViewById(R.id.energycarbonresult_carresult);
            g.setText(carbike + " kg of CO2");
            
	        String bus = getIntent().getStringExtra("bus");
	        B = Float.parseFloat(bus);
	        g = (TextView)findViewById(R.id.energycarbonresult_publicresult);
            g.setText(bus + " kg of CO2");
            
	        String secondary = getIntent().getStringExtra("secondary");
	       S = Float.parseFloat(secondary);
	        g = (TextView)findViewById(R.id.energycarbonresult_secondresult);
            g.setText(secondary + " tonnes of CO2");
	 
            g = (TextView)findViewById(R.id.energycarbonresult_totalnb);
            H = H + F + C + B + S;
            house = Float.toString(H);
            g.setText(house + " tonnes of CO2");
            
            Button energyresults_button1 = (Button)findViewById(R.id.energycarbonresult_button1);
            energyresults_button1.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
						public void onClick(View v)
	        			{
							Intent intent = new Intent(Energy_results.this,Energy_carbon.class);
	                        startActivity(intent);
	        			}
	        		}
    		);
            
            Button energyresults_button2 = (Button)findViewById(R.id.energycarbonresult_button2);
            energyresults_button2.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
						public void onClick(View v)
	        			{
							Intent intent = new Intent(Energy_results.this,Energy_reduce.class);
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
    			Intent intent = new Intent(Energy_results.this,DisplayCategories.class);
				startActivity(intent);
    			return true;
    	}
		return false;
    }
}
