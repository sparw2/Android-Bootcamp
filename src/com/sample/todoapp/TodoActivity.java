package com.sample.todoapp;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;


public class TodoActivity extends ActionBarActivity implements
	EditFragment.OnItemEditedListener{
	
	private List<Item> items;
	private ItemAdapter listAdapter;
	private ListView listView;
	private EditText newItem; 
	
	
	//private static final int REQUEST_CODE=20;
	public static final int RESULT_OK = 200; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        
        setContentView(R.layout.activity_todo);
        listView = (ListView)findViewById(R.id.lvItems);
        newItem = (EditText)findViewById(R.id.etNewItem);


        readItems();
        listAdapter = new ItemAdapter(this, items);
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
				
				EditFragment fragment = EditFragment.newInstance(listAdapter.getItem(position).name, position);
				ft.attach(fragment);
				fragment.show(ft, "");
			}
    		
		});
    }
    
  /*  
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
    */
    
    private void readItems(){
    	items = new Select().from(Item.class).execute();
    }
    
    private void writeItems(){
    	try {
    		ActiveAndroid.beginTransaction();
    	   	
    		// remove all previous data
    		new Delete().from(Item.class).where("1 == 1").execute();
    		
    		for (Item item : items)
    			item.save();
        	
    		ActiveAndroid.setTransactionSuccessful();
    	} finally {
   	        ActiveAndroid.endTransaction();
    	}

    }
    
    public void addTodoItem(View v){
    	listAdapter.add(new Item(newItem.getText().toString()));
    	newItem.setText("");
    	writeItems();
    }


	@Override
	public void onItemEdited(String item, int position) {
		 items.set(position, new Item(item));
		 listAdapter.notifyDataSetChanged();
		 writeItems();
	}
    
}
