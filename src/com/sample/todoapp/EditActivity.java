package com.sample.todoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends ActionBarActivity {

	private EditText itemView;
	private int pos=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		String itemValue = getIntent().getStringExtra("editItem");
		pos = getIntent().getIntExtra("itemPosition",0);
		
		itemView =  (EditText)findViewById(R.id.edItem);
		
		itemView.setText(itemValue);
		itemView.setSelection(itemView.getText().length());
	}
	
	public void onSaveItem(View view){
		
		getIntent().putExtra("editItem",itemView.getText().toString());
		getIntent().putExtra("itemPosition",pos);
		
		setResult(TodoActivity.RESULT_OK,getIntent());
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
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
}
