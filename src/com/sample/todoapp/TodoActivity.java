package com.sample.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class TodoActivity extends ActionBarActivity implements
	EditFragment.OnItemEditedListener{
	
	private List<String> items;
	private ArrayAdapter<String> listAdapter;
	private ListView listView;
	private EditText newItem; 
	
	private static final int REQUEST_CODE=20;
	public static final int RESULT_OK = 200; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        listView = (ListView)findViewById(R.id.lvItems);
        newItem = (EditText)findViewById(R.id.etNewItem);


        readItems();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(listAdapter);
        
        listViewListener();
        editViewListener();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    private void listViewListener(){
    	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				listAdapter.remove(items.get(position));
				writeItems();
				return true;
			}
    		
		});
    }
    
    private void editViewListener(){
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				/*
				Intent editIntent = new Intent(TodoActivity.this, EditActivity.class);
				editIntent.putExtra("editItem", listAdapter.getItem(position));
				editIntent.putExtra("itemPosition", position);
				
				startActivityForResult(editIntent, REQUEST_CODE);
				*/
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				
				EditFragment fragment = EditFragment.newInstance(listAdapter.getItem(position), position);
				ft.attach(fragment);
				fragment.show(ft, "");
			}
    		
		});
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   if (requestCode== REQUEST_CODE && resultCode == RESULT_OK){
		   String itemValue = data.getStringExtra("editItem");
		   int pos = data.getIntExtra("itemPosition",0);
		   items.set(pos, itemValue);
		   listAdapter.notifyDataSetChanged();
		   writeItems();
	   }
	   
    }
    
    
    private void readItems(){
    	File itemDir = getFilesDir();
    	File itemStorage = new File(itemDir, "todo.txt");
    	try{
    		items = FileUtils.readLines(itemStorage);
    	}catch(IOException e){
    		items = new ArrayList<>();
    	}
    }
    
    private void writeItems(){
    	File itemDir = getFilesDir();
    	File itemStorage = new File(itemDir, "todo.txt");
    	try{
    		FileUtils.writeLines(itemStorage, items);
    	}catch(IOException e){
    		e.printStackTrace();
    	}	
    }
    
    public void addTodoItem(View v){
    	listAdapter.add(newItem.getText().toString());
    	newItem.setText("");
    	writeItems();
    }


	@Override
	public void onItemEdited(String item, int position) {
		 items.set(position, item);
		 listAdapter.notifyDataSetChanged();
		 writeItems();
	}
    
}
