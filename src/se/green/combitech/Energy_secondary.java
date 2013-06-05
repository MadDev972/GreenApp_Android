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

public class Energy_secondary extends Activity 
{	
	protected static String s = "0";	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.energycarbon_secondary);
	        
	        Intent thisIntent = getIntent();
	        
	        final Display display = getWindowManager().getDefaultDisplay(); 
    		final int width = display.getWidth();
    		final int height = display.getHeight();
	        
	        final Spinner spinner_food = (Spinner)findViewById(R.id.energycarbonsecond_foodspinner);
	        final Spinner spinner_organic = (Spinner)findViewById(R.id.energycarbonsecond_organicspinner);
	        final Spinner spinner_season = (Spinner)findViewById(R.id.energycarbonsecond_seasonspinner);
	        final Spinner spinner_imported = (Spinner)findViewById(R.id.energycarbonsecond_importedspinner);
	        final Spinner spinner_fashion = (Spinner)findViewById(R.id.energycarbonsecond_fashionspinner);
	        final Spinner spinner_packaging = (Spinner)findViewById(R.id.energycarbonsecond_packagingspinner);
	        final Spinner spinner_furniture = (Spinner)findViewById(R.id.energycarbonsecond_furniturespinner);
	       final Spinner spinner_recycling = (Spinner)findViewById(R.id.energycarbonsecond_recyclingspinner);
	        final Spinner spinner_recreation = (Spinner)findViewById(R.id.energycarbonsecond_recreationspinner);
	        final Spinner spinner_car = (Spinner)findViewById(R.id.energycarbonsecond_carspinner);
	        final Spinner spinner_finance = (Spinner)findViewById(R.id.energycarbonsecond_financespinner);
	        
	        
	        ArrayAdapter<?> adapter_food = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_food, 
					android.R.layout.simple_spinner_item
			);
			adapter_food.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_organic = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_organic, 
					android.R.layout.simple_spinner_item
			);
			adapter_organic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_season = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_season, 
					android.R.layout.simple_spinner_item
			);
			adapter_season.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_imported = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_imported, 
					android.R.layout.simple_spinner_item
			);
			adapter_imported.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_fashion = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_fashion, 
					android.R.layout.simple_spinner_item
			);
			adapter_fashion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_packaging = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_packaging, 
					android.R.layout.simple_spinner_item
			);
			adapter_packaging.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_furniture = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_furniture, 
					android.R.layout.simple_spinner_item
			);
			adapter_furniture.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_recycling = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_recycling, 
					android.R.layout.simple_spinner_item
			);
			adapter_recycling.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_recreation = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_recreation, 
					android.R.layout.simple_spinner_item
			);
			adapter_recreation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_car = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_car, 
					android.R.layout.simple_spinner_item
			);
			adapter_car.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			ArrayAdapter<?> adapter_finance = ArrayAdapter.createFromResource
			(
					this, 
					R.array.energycarbonsecond_finance, 
					android.R.layout.simple_spinner_item
			);
			adapter_finance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner_food.setAdapter(adapter_food);
			spinner_organic.setAdapter(adapter_organic);
			spinner_season.setAdapter(adapter_season);
			spinner_imported.setAdapter(adapter_imported);
			spinner_fashion.setAdapter(adapter_fashion);
			spinner_packaging.setAdapter(adapter_packaging);
			spinner_furniture.setAdapter(adapter_furniture);
			spinner_recycling.setAdapter(adapter_recycling);
			spinner_recreation.setAdapter(adapter_recreation);
			spinner_car.setAdapter(adapter_car);
			spinner_finance.setAdapter(adapter_finance);
			
			Button energysecond_button1 = (Button)findViewById(R.id.energycarbonsecond_button1);
			energysecond_button1.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{
	        				TextView f,g;
	        				EditText e;
	        				float coeff = 0;
	        			    Long un,deux,trois,quatre,cinq,six,sept,huit,neuf,dix,onze;
	        				
	        				un = spinner_food.getSelectedItemId();
	        				deux = spinner_organic.getSelectedItemId();
	        				trois = spinner_season.getSelectedItemId();
	        				quatre = spinner_imported.getSelectedItemId();
	        				cinq = spinner_fashion.getSelectedItemId();
	        				six = spinner_packaging.getSelectedItemId();
	        				sept = spinner_furniture.getSelectedItemId();
	        				huit = spinner_recycling.getSelectedItemId();
	        				neuf = spinner_recreation.getSelectedItemId();
	        				dix = spinner_car.getSelectedItemId();
	        				onze = spinner_finance.getSelectedItemId();
	        				
	                        if(un == 0)
	                        {
	                        	coeff=(float)0.0 ;
	                        }
	                        else if(un == 1)
	                        {	
	                        	coeff=(float)(0.14/12) ; 
	                        }
	                        else if (un == 2)
	                        {
	                        	coeff = (float)(0.28/12);
	                        }
	                        else if(un == 3)
	                        {	
	                        	coeff=(float)(0.42/12) ; 
	                        }
	                        else if (un == 4)
	                        {
	                        	coeff = (float)(0.56/12);
	                        }
	                        else 
	                        {	
	                        	coeff=(float)(0.7/12); 
	                        }
	                        
	                        if(deux == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0);
	                        }
	                        else if(deux == 1)
	                        {	
	                        	coeff=(float)(coeff + (0.02/12)) ; 
	                        }
	                        else 
	                        {
	                        	coeff = (float) (coeff + (0.03/12));
	                        }
	                        
	                        if(trois == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0) ;
	                        }
	                        else if(trois == 1)
	                        {	
	                        	coeff=(float)(coeff + (0.03/12)) ; 
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + (0.04/12));
	                        }
	                        
	                        if(quatre == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0);
	                        }
	                        else if(quatre == 1)
	                        {	
	                        	coeff=(float)(coeff + (0.02/12)); 
	                        }
	                        else if(quatre == 2)
	                        {
	                        	coeff = (float)(coeff + (0.04/12));
	                        }
	                        else if(quatre == 3)
	                        {	
	                        	coeff=(float)(coeff + (0.07/12)); 
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + (0.09/12));
	                        }
	                        
	                        if(cinq == 0)
	                        {
	                        	coeff=(float)(coeff + (0.02/12));
	                        }
	                        else if(cinq == 1)
	                        {	
	                        	coeff=(float)(coeff + (0.01/12)); 
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + 0.0);
	                        }
	                        
	                        if(six == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0) ;
	                        }
	                        else if(six == 1)
	                        {	
	                        	coeff=(float)(coeff + (0.03/12)); 
	                        }
	                        else if(six == 2)
	                        {
	                        	coeff = (float)(coeff + (0.06/12));
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + (0.11/12));
	                        }
	                        
	                        if(sept == 0)
	                        {
	                        	coeff=(float)(coeff + (0.04/12));
	                        }
	                        else if(sept == 1)
	                        {	
	                        	coeff=(float)(coeff + (0.02/12)); 
	                        }
	                        else if(sept == 2)
	                        {
	                        	coeff = (float)(coeff + (0.01/12));
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + 0.0);
	                        }
	                        
	                        if(huit == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0);
	                        }
	                        else if(huit == 1)
	                        {	
	                        	coeff=(float)(coeff + (0.03/12)); 
	                        }
	                        else if(huit == 2)
	                        {
	                        	coeff = (float)(coeff + (0.06/12));
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + (0.11/12));
	                        }
	                        
	                        if(neuf == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0);
	                        }
	                        else if(neuf == 1)
	                        {	
	                        	coeff=(float)(coeff + (1.00/12)); 
	                        }
	                        else if(neuf == 2)
	                        {
	                        	coeff = (float)(coeff + (1.50/12));
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + (2.50/12));
	                        }
	                        
	                        if(dix == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0);
	                        }
	                        else if(dix == 1)
	                        {	
	                        	coeff=(float)(coeff + (1.00/12)); 
	                        }
	                        else if(dix == 2)
	                        {
	                        	coeff = (float)(coeff +(2.00/12));
	                        }
	                        else if(dix == 3)
	                        {
	                        	coeff = (float)(coeff + (3.00/12));
	                        }
	                        else 
	                        {
	                        	coeff = (float)(coeff + (4.00/12));
	                        }
	                        
	                        if(onze == 0)
	                        {
	                        	coeff=(float)(coeff + 0.0);
	                        }
	                        else 
	                        {	
	                        	coeff=(float)(coeff + (0.40/12)); 
	                        }
	                        
	                        e = (EditText)findViewById(R.id.energycarbonsecond_editText);
	        				s = e.getText().toString();
	                        float duree = Float.parseFloat(s);
	                        coeff = coeff*duree;
	                        
	                        s = (new Float(coeff)).toString();
		                    
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
			
			Button energysecond_button2 = (Button)findViewById(R.id.energycarbonsecond_button2);
			energysecond_button2.setOnClickListener
	        (
	        		new View.OnClickListener()
	        		{
	        			public void onClick(View v)
	        			{

            				
            				Intent intent = new Intent(Energy_secondary.this,Energy_results.class);
	                        intent.putExtra("house", getIntent().getStringExtra("house"));
	                        intent.putStringArrayListExtra("flights", getIntent().getStringArrayListExtra("flights"));
	                        intent.putExtra("carbike", getIntent().getStringExtra("carbike"));
            				intent.putExtra("bus", getIntent().getStringExtra("bus"));
            				intent.putExtra("secondary",s);
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
    			Intent intent = new Intent(Energy_secondary.this,DisplayCategories.class);
				startActivity(intent);
    			return true;
    	}
		return false;
    }
}
