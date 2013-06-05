package se.green.combitech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DisplayCategories extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        Intent thisIntent = getIntent();
        
        
        Button categories_button1 = (Button)findViewById(R.id.categories_food);
        categories_button1.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(DisplayCategories.this,Food.class);
        				startActivity(intent);
        			}
        		}
        ); 
        
        
        Button categories_button2 = (Button)findViewById(R.id.categories_energy);
        categories_button2.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(DisplayCategories.this,Energy.class);
        				startActivity(intent);
        			}
        		}
        );
        
        
        Button categories_button3 = (Button)findViewById(R.id.categories_quizzes);
        categories_button3.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(DisplayCategories.this,Quizzes.class);
        				startActivity(intent);
        			}
        		}
        );
        
        
        Button categories_button4 = (Button)findViewById(R.id.categories_resources);
        categories_button4.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(DisplayCategories.this,Resources.class);
        				startActivity(intent);
        			}
        		}
        ); 
        
        
        Button categories_button5 = (Button)findViewById(R.id.categories_combitech);
        categories_button5.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(DisplayCategories.this,Combitech.class);
        				startActivity(intent);
        			}
        		}
        ); 
    }
    
}