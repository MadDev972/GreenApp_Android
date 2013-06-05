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

public class Quizzes extends Activity 
{
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizzes);
        Intent thisIntent = getIntent();
        
        
        Button quizzes_button1 = (Button) findViewById(R.id.quizzes_list);
        
        quizzes_button1.setOnClickListener
        (
        		new View.OnClickListener()
        		{
        			public void onClick(View v)
        			{
        				Intent intent = new Intent(Quizzes.this,Quizzes_List.class);
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
		text.setText("The source for the information in this section is http://environment.nationalgeographic.com");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
    	return true;
    }
   
}