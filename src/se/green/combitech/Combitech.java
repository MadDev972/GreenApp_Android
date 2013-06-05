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


public class Combitech extends Activity 
{
	WebView mWebView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combitech);
        
        
        TextView Combitech_text1 = (TextView)findViewById(R.id.Combitech_text1);
        Combitech_text1.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(Combitech.this,Combitech_competences.class);
        				startActivity(intent);
        			}
        		}
        );
        
        TextView Combitech_text2 = (TextView)findViewById(R.id.Combitech_text2);
        Combitech_text2.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(Combitech.this,Combitech_offers.class);
        				startActivity(intent);
        			}
        		}
        );
        
        TextView Combitech_text3 = (TextView)findViewById(R.id.Combitech_text3);
        Combitech_text3.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(Combitech.this,Combitech_references.class);
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
		text.setText("The source for the information in this section is www.combitech.se");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
    	return true;
    }
}