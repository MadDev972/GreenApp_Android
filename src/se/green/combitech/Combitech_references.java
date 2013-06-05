package se.green.combitech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleExpandableListAdapter;
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


public class Combitech_references extends Activity 
{
	WebView mWebView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combitech_references);      
                
        String [] parents = {
        						(String) getResources().getText(R.string.Combitech_refpar1),
        						(String) getResources().getText(R.string.Combitech_refpar2),	
        						(String) getResources().getText(R.string.Combitech_refpar3),
        						(String) getResources().getText(R.string.Combitech_refpar4),
        						(String) getResources().getText(R.string.Combitech_refpar5),
        						(String) getResources().getText(R.string.Combitech_refpar6),
        						(String) getResources().getText(R.string.Combitech_refpar7),	
        						(String) getResources().getText(R.string.Combitech_refpar8),
        						(String) getResources().getText(R.string.Combitech_refpar9),
        						(String) getResources().getText(R.string.Combitech_refpar10),
        						(String) getResources().getText(R.string.Combitech_refpar11),
        						(String) getResources().getText(R.string.Combitech_refpar12)
        					};
        
        String[] child = {
        					(String) getResources().getText(R.string.Combitech_refchild1),
        					(String) getResources().getText(R.string.Combitech_refchild2),
        					(String) getResources().getText(R.string.Combitech_refchild3),
        					(String) getResources().getText(R.string.Combitech_refchild4),
        					(String) getResources().getText(R.string.Combitech_refchild5),
        					(String) getResources().getText(R.string.Combitech_refchild6),
        					(String) getResources().getText(R.string.Combitech_refchild7),
        					(String) getResources().getText(R.string.Combitech_refchild8),
        					(String) getResources().getText(R.string.Combitech_refchild9),
        					(String) getResources().getText(R.string.Combitech_refchild10),
        					(String) getResources().getText(R.string.Combitech_refchild11),
        					(String) getResources().getText(R.string.Combitech_refchild12)
        				 };
        
        List<Map<String,String>> Parent = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> Child = new ArrayList<List<Map<String, String>>>();
        
        for(int i=0;i<12;i++)
        {
        	Map<String,String>curGroupMap = new HashMap<String,String>();
        	Parent.add(curGroupMap);
        	curGroupMap.put("name", parents[i]);
        	
        	List<Map<String,String>> children = new ArrayList<Map<String, String>>();
        	Map<String, String> curChildMap = new HashMap<String,String>();
    		children.add(curChildMap);
    		curChildMap.put("name", child[i]);
        	Child.add(children);
        }
        

        SimpleExpandableListAdapter expladapter = new SimpleExpandableListAdapter
		(
			this,
			Parent,
			R.layout.energyflight_row,
			new String[]{"name"},
			new int[]{R.id.name},
			Child,
			R.layout.energyflight_row,
			new String[]{"name"},
			new int[]{R.id.name}
	     );

		ExpandableListView explv = (ExpandableListView)findViewById(R.id.Combitech_refexplv);
		
		explv.setAdapter(expladapter);
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
    			Intent intent = new Intent(Combitech_references.this,DisplayCategories.class);
				startActivity(intent);
    	}
		return false;
    }
    
}