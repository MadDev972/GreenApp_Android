package se.green.combitech;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;


public class Combitech_offers extends Activity 
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combitech_offers);
        
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
    			text.setText("The source for the information in this section is www.combitech.se");

    			Toast toast = new Toast(getApplicationContext());
    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    			toast.setDuration(Toast.LENGTH_LONG);
    			toast.setView(layout);
    			toast.show();
    			return true;
    			
    		case R.id.return_categories:
    			Intent intent = new Intent(Combitech_offers.this,DisplayCategories.class);
				startActivity(intent);
    	}
		return false;
    }
    
  }