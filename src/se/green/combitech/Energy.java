package se.green.combitech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Energy extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energy);
        Intent thisIntent = getIntent();
        
        
        Button energy_button1 = (Button) findViewById(R.id.energy_carbon);
        
        energy_button1.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(Energy.this,Energy_carbon.class);
        				startActivity(intent);
        			}
        		}
        );  
        
        
        Button energy_button2 = (Button) findViewById(R.id.energy_reduce);
        
        energy_button2.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(Energy.this,Energy_reduce.class);
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
		text.setText("The sources for the information in this section are the 2010 Guidelines to Defra / DECC's GHG Conversion Factors for Company Reporting and www.carbonfootprint.com");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
    	return true;
    }
}