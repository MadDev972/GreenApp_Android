package se.green.combitech;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import android.R.array;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.view.LayoutInflater;

public class Quizzes_List extends Activity 
{	
	public static final String KEY_ROWID = "_id";
    public static final String KEY_CATEGORY = "Category";
    public static final String KEY_QUESTIONS = "Questions";
    public static final String KEY_OPTIONS1 = "Options1";
    public static final String KEY_OPTIONS2 = "Options2";
    public static final String KEY_OPTIONS3 = "Options3";
    public static final String KEY_OPTIONS4 = "Options4";
    public static final String KEY_ANSWERS = "Answers";
    public static final String KEY_COMMENTS = "Comments";
   
    public static String ANSWER = "", COMMENT = "";
    
    public String[] CATEGORY= new String[]{"Home and Garden"};
    
    public String[] from = new String[]
		              {
		    				KEY_ROWID,
		    				KEY_CATEGORY,
		    				KEY_QUESTIONS,
		    			    KEY_OPTIONS1,
		    			    KEY_OPTIONS2,
		    			    KEY_OPTIONS3,
		    			    KEY_OPTIONS4,
		    			    KEY_ANSWERS,
		    			    KEY_COMMENTS
		    		   };
				
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    		super.onCreate(savedInstanceState);
	        
    		setContentView(R.layout.quizzes_list);
	        
    		final Display display = getWindowManager().getDefaultDisplay(); 
    		final int width = display.getWidth();
    		final int height = display.getHeight();
    		
	        Intent thisIntent = getIntent();
	        
			Spinner spinner_resources = (Spinner)findViewById(R.id.quizzeslist_spinner1);
			
			final TextView Q = (TextView)findViewById(R.id.quizzeslist_textView1);
        	final TextView A1 = (TextView)findViewById(R.id.quizzeslist_textView2);
        	final TextView A2 = (TextView)findViewById(R.id.quizzeslist_textView3);
        	final TextView A3 = (TextView)findViewById(R.id.quizzeslist_textView4);
        	final TextView A4 = (TextView)findViewById(R.id.quizzeslist_textView5);
        	
			ArrayAdapter<?> adapter_resources = ArrayAdapter.createFromResource
			(
					this, 
					R.array.quizzes_categories, 
					android.R.layout.simple_spinner_item
			);
			adapter_resources.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner_resources.setAdapter(adapter_resources);
			
			
			final DataBaseHelper mDbHelper = new DataBaseHelper(this);
			
        	try 
        	{
        		mDbHelper.createDataBase();
        	} 
        	catch (IOException ioe) 
        	{
        		throw new Error("Unable to create database");
        	}
        				 
        	try 
        	{
        		mDbHelper.openDataBase();
        	}
        	catch(SQLException sqle)
        	{
        		throw sqle;
        	}
        		        	
        	final Cursor c  = mDbHelper.fetchAll("Quizzes", from); 
        	
        	startManagingCursor(c);
        			    	        	
			spinner_resources.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
			{
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
				{
					CATEGORY[0]  = (String) parent.getItemAtPosition(pos);  
					
					int i=0; 
		        	
		        	int s = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY).getCount();
		        	
		        	String[][] Questions = new String[s][7];
		        	
		        	c.moveToFirst();

		        	do
		        	{
						if(CATEGORY[0].equals(c.getString(c.getColumnIndex("Category"))))
						{
						
							Questions[i][0]=c.getString(c.getColumnIndex("Questions"));
							Questions[i][1]=c.getString(c.getColumnIndex("Options1"));
							Questions[i][2]=c.getString(c.getColumnIndex("Options2"));
							Questions[i][3]=c.getString(c.getColumnIndex("Options3"));
							Questions[i][4]=c.getString(c.getColumnIndex("Options4"));
							Questions[i][5]=c.getString(c.getColumnIndex("Answers"));
							Questions[i][6]=c.getString(c.getColumnIndex("Comments"));
							
		    	        	i++;
						}
		        	}while(c.moveToNext());
		        	
		        	Random r = new Random();
		        	final int j = r.nextInt(Questions.length);
		        	
		        	Q.setText(Questions[j][0]);
		        	A1.setText(Questions[j][1]);
		        	A2.setText(Questions[j][2]);
		        	A3.setText(Questions[j][3]);
		        	A4.setText(Questions[j][4]);
		        	ANSWER=Questions[j][5];
					COMMENT=Questions[j][6];
		
        		}
        					
        		public void onNothingSelected(AdapterView<?> parent)
    	        {
        			CATEGORY[0]  = "Home and Garden";
                	
        			int i=0; 
                	
                	int s = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY).getCount();
                	
                	String[][] Questions = new String[s][7];
                	
                	c.moveToFirst();
                	
                	while(c.moveToNext())
                	{
        				if(CATEGORY[0].equals(c.getString(c.getColumnIndex("Category"))))
        				{
        				
        					Questions[i][0]=c.getString(c.getColumnIndex("Questions"));
        					Questions[i][1]=c.getString(c.getColumnIndex("Options1"));
        					Questions[i][2]=c.getString(c.getColumnIndex("Options2"));
        					Questions[i][3]=c.getString(c.getColumnIndex("Options3"));
        					Questions[i][4]=c.getString(c.getColumnIndex("Options4"));
        					Questions[i][5]=c.getString(c.getColumnIndex("Answers"));
        					Questions[i][6]=c.getString(c.getColumnIndex("Comments"));
        					
            	        	i++;
        				}
                	}
                	
                	Random r = new Random();
                	final int j = r.nextInt(Questions.length);
                	
                	Q.setText(Questions[j][0]);
                	A1.setText(Questions[j][1]);
                	A2.setText(Questions[j][2]);
                	A3.setText(Questions[j][3]);
                	A4.setText(Questions[j][4]);
		        	ANSWER=Questions[j][5];
		            COMMENT=Questions[j][6];
        	   }	        			
    	    });
			
			
			A1.setOnClickListener
			(
				new View.OnClickListener()
	        	{
					public void onClick(View v)
	        		{
						LayoutInflater inflater = (LayoutInflater)Quizzes_List.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	
						View layout = inflater.inflate
            			(
            					R.layout.quizzes_popup, null
            			);
            			
						TextView APP1 = (TextView)layout.findViewById(R.id.quizzes_popup_textView1);
						TextView APP2 = (TextView)layout.findViewById(R.id.quizzes_popup_textView2);
						TextView APP3 = (TextView)layout.findViewById(R.id.quizzes_popup_textView3);
						TextView APP4 = (TextView)layout.findViewById(R.id.quizzes_popup_textView4);
						
						APP1.setText(A1.getText().toString()+"?");
						
						if(A1.getText().toString().equals(ANSWER))
						{
							APP2.setText("Correct !");
						}
						else
						{
							APP2.setText("Wrong..");
						}
						
						APP3.setText("The correct answer is:"+ANSWER);
						
						APP4.setText(COMMENT);
						
						final PopupWindow pw = new PopupWindow(layout,(width/3)*2, height/2, true);
            			
						pw.showAtLocation(layout, Gravity.CENTER, 0, 10);
						
						Button Close = (Button)layout.findViewById(R.id.quizzes_popup_button);
						Close.setOnClickListener
						(
							new View.OnClickListener()
				        	{
								public void onClick(View v)
				        		{
									pw.dismiss();
									
									int i=0; 
						        	
						        	int s = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY).getCount();
						        	
						        	String[][] Questions = new String[s][7];
				                	
				                	c.moveToFirst();
				                	
				                	do
				                	{
				        				if(CATEGORY[0].equals(c.getString(c.getColumnIndex("Category"))))
				        				{
				        				
				        					Questions[i][0]=c.getString(c.getColumnIndex("Questions"));
				        					Questions[i][1]=c.getString(c.getColumnIndex("Options1"));
				        					Questions[i][2]=c.getString(c.getColumnIndex("Options2"));
				        					Questions[i][3]=c.getString(c.getColumnIndex("Options3"));
				        					Questions[i][4]=c.getString(c.getColumnIndex("Options4"));
				        					Questions[i][5]=c.getString(c.getColumnIndex("Answers"));
				        					Questions[i][6]=c.getString(c.getColumnIndex("Comments"));
				        					
				            	        	i++;
				        				}
				                	}while(c.moveToNext());
						        	
						        	Random r = new Random();
									int j = r.nextInt(Questions.length);
						        	
									Q.setText(Questions[j][0]);
									A1.setText(Questions[j][1]);
									A2.setText(Questions[j][2]);
									A3.setText(Questions[j][3]);
									A4.setText(Questions[j][4]);
									ANSWER=Questions[j][5];
									COMMENT=Questions[j][6];
						        }
				        	}
						);
		        	}
	        	}
			);
			
	
			A2.setOnClickListener
			(
				new View.OnClickListener()
	        	{
					public void onClick(View v)
	        		{
						LayoutInflater inflater = (LayoutInflater)Quizzes_List.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	
						View layout = inflater.inflate
            			(
            					R.layout.quizzes_popup, null
            			);
            			
						TextView APP1 = (TextView)layout.findViewById(R.id.quizzes_popup_textView1);
						TextView APP2 = (TextView)layout.findViewById(R.id.quizzes_popup_textView2);
						TextView APP3 = (TextView)layout.findViewById(R.id.quizzes_popup_textView3);
						TextView APP4 = (TextView)layout.findViewById(R.id.quizzes_popup_textView4);
						
						APP1.setText(A2.getText().toString()+"?");
						
						if(A2.getText().toString().equals(ANSWER))
						{
							APP2.setText("Correct !");
						}
						else
						{
							APP2.setText("Wrong..");
						}
						
						APP3.setText("The correct answer is:"+ANSWER);
						
						APP4.setText(COMMENT);
						
						final PopupWindow pw = new PopupWindow(layout,(width/3)*2, height/2, true);
            			
						pw.showAtLocation(layout, Gravity.CENTER, 0, 10);
						
						Button Close = (Button)layout.findViewById(R.id.quizzes_popup_button);
						Close.setOnClickListener
						(
							new View.OnClickListener()
				        	{
								public void onClick(View v)
				        		{
									pw.dismiss();
									
									int i=0; 
						        	
						        	int s = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY).getCount();
						        	
						        	String[][] Questions = new String[s][7];
				                	
				                	c.moveToFirst();
				                	
				                	do
				                	{
				        				if(CATEGORY[0].equals(c.getString(c.getColumnIndex("Category"))))
				        				{
				        				
				        					Questions[i][0]=c.getString(c.getColumnIndex("Questions"));
				        					Questions[i][1]=c.getString(c.getColumnIndex("Options1"));
				        					Questions[i][2]=c.getString(c.getColumnIndex("Options2"));
				        					Questions[i][3]=c.getString(c.getColumnIndex("Options3"));
				        					Questions[i][4]=c.getString(c.getColumnIndex("Options4"));
				        					Questions[i][5]=c.getString(c.getColumnIndex("Answers"));
				        					Questions[i][6]=c.getString(c.getColumnIndex("Comments"));
				        					
				            	        	i++;
				        				}
				                	}while(c.moveToNext());
						        	
						        	Random r = new Random();
									int j = r.nextInt(Questions.length);
						        	
									Q.setText(Questions[j][0]);
									A1.setText(Questions[j][1]);
									A2.setText(Questions[j][2]);
									A3.setText(Questions[j][3]);
									A4.setText(Questions[j][4]);
									ANSWER=Questions[j][5];
									COMMENT=Questions[j][6];
						        }
				        	}
						);
		        	}
	        	}
			);
			

			A3.setOnClickListener
			(
				new View.OnClickListener()
		       	{
					public void onClick(View v)
		       		{

						if(A3.getText().toString().isEmpty()==false)
						{	
							LayoutInflater inflater = (LayoutInflater)Quizzes_List.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        	
							View layout = inflater.inflate
	            			(
	            					R.layout.quizzes_popup, null
	            			);
	            			
							TextView APP1 = (TextView)layout.findViewById(R.id.quizzes_popup_textView1);
							TextView APP2 = (TextView)layout.findViewById(R.id.quizzes_popup_textView2);
							TextView APP3 = (TextView)layout.findViewById(R.id.quizzes_popup_textView3);
							TextView APP4 = (TextView)layout.findViewById(R.id.quizzes_popup_textView4);
							
							APP1.setText(A3.getText().toString()+"?");
							
							if(A3.getText().toString().equals(ANSWER))
							{
								APP2.setText("Correct !");
							}
							else
							{
								APP2.setText("Wrong..");
							}
							
							APP3.setText("The correct answer is:"+ANSWER);
							
							APP4.setText(COMMENT);
							
							final PopupWindow pw = new PopupWindow(layout,(width/3)*2, height/2, true);
	            			
							pw.showAtLocation(layout, Gravity.CENTER, 0, 10);
							
							Button Close = (Button)layout.findViewById(R.id.quizzes_popup_button);
							Close.setOnClickListener
							(
								new View.OnClickListener()
					        	{
									public void onClick(View v)
					        		{
										pw.dismiss();
										
										int i=0; 
							        	
							        	int s = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY).getCount();
							        	
							        	String[][] Questions = new String[s][7];
					                	
					                	c.moveToFirst();
					                	
					                	do
					                	{
					        				if(CATEGORY[0].equals(c.getString(c.getColumnIndex("Category"))))
					        				{
					        				
					        					Questions[i][0]=c.getString(c.getColumnIndex("Questions"));
					        					Questions[i][1]=c.getString(c.getColumnIndex("Options1"));
					        					Questions[i][2]=c.getString(c.getColumnIndex("Options2"));
					        					Questions[i][3]=c.getString(c.getColumnIndex("Options3"));
					        					Questions[i][4]=c.getString(c.getColumnIndex("Options4"));
					        					Questions[i][5]=c.getString(c.getColumnIndex("Answers"));
					        					Questions[i][6]=c.getString(c.getColumnIndex("Comments"));
					        					
					            	        	i++;
					        				}
						                }while(c.moveToNext());
							        	
							        	Random r = new Random();
										int j = r.nextInt(Questions.length);
							        	
										Q.setText(Questions[j][0]);
										A1.setText(Questions[j][1]);
										A2.setText(Questions[j][2]);
										A3.setText(Questions[j][3]);
										A4.setText(Questions[j][4]);
										ANSWER=Questions[j][5];
										COMMENT=Questions[j][6];
							        }
					        	}
							);
			        	}
		        	}
		       	}
			);
			
			A4.setOnClickListener
			(
				new View.OnClickListener()
		       	{
					public void onClick(View v)
		      		{
						if(! A4.getText().toString().isEmpty())
						{
							LayoutInflater inflater = (LayoutInflater)Quizzes_List.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        	
							View layout = inflater.inflate
	            			(
	            					R.layout.quizzes_popup, null
	            			);
	            			
							TextView APP1 = (TextView)layout.findViewById(R.id.quizzes_popup_textView1);
							TextView APP2 = (TextView)layout.findViewById(R.id.quizzes_popup_textView2);
							TextView APP3 = (TextView)layout.findViewById(R.id.quizzes_popup_textView3);
							TextView APP4 = (TextView)layout.findViewById(R.id.quizzes_popup_textView4);
							
							APP1.setText(A4.getText().toString()+"?");
							
							if(A4.getText().toString().equals(ANSWER))
							{
								APP2.setText("Correct !");
							}
							else
							{
								APP2.setText("Wrong..");
							}
							
							APP3.setText("The correct answer is:"+ANSWER);
							
							APP4.setText(COMMENT);
							
							final PopupWindow pw = new PopupWindow(layout,(width/3)*2, height/2, true);
	            			
							pw.showAtLocation(layout, Gravity.CENTER, 0, 10);
							
							Button Close = (Button)layout.findViewById(R.id.quizzes_popup_button);
							Close.setOnClickListener
							(
								new View.OnClickListener()
					        	{
									public void onClick(View v)
					        		{
										pw.dismiss();
										
										int i=0; 
							        	
							        	int s = mDbHelper.fetchListResourceType("Quizzes", from, 1, CATEGORY).getCount();
							        	
							        	String[][] Questions = new String[s][7];
					                	
					                	c.moveToFirst();
					                	
					                	do
					                	{
					        				if(CATEGORY[0].equals(c.getString(c.getColumnIndex("Category"))))
					        				{
					        				
					        					Questions[i][0]=c.getString(c.getColumnIndex("Questions"));
					        					Questions[i][1]=c.getString(c.getColumnIndex("Options1"));
					        					Questions[i][2]=c.getString(c.getColumnIndex("Options2"));
					        					Questions[i][3]=c.getString(c.getColumnIndex("Options3"));
					        					Questions[i][4]=c.getString(c.getColumnIndex("Options4"));
					        					Questions[i][5]=c.getString(c.getColumnIndex("Answers"));
					        					Questions[i][6]=c.getString(c.getColumnIndex("Comments"));
					        					
					            	        	i++;
					        				}
					                	}while(c.moveToNext());
							        	
							        	Random r = new Random();
										int j = r.nextInt(Questions.length);
							        	
										Q.setText(Questions[j][0]);
										A1.setText(Questions[j][1]);
										A2.setText(Questions[j][2]);
										A3.setText(Questions[j][3]);
										A4.setText(Questions[j][4]);
										ANSWER=Questions[j][5];
										COMMENT=Questions[j][6];
							        }
					        	}
							);
			        	}
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
    			text.setText("The source for the information in this section is http://environment.nationalgeographic.com");

    			Toast toast = new Toast(getApplicationContext());
    			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    			toast.setDuration(Toast.LENGTH_LONG);
    			toast.setView(layout);
    			toast.show();
    			return true;
    			
    		case R.id.return_categories:
    			Intent intent = new Intent(Quizzes_List.this,DisplayCategories.class);
				startActivity(intent);
    	}
		return false;
    }
}