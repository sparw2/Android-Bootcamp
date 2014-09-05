package com.sample.todoapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {

	public ItemAdapter(Context context, List<Item> items) {
	       super(context, R.layout.item, items);
	}
	
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
	       Item item = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
	       }
	       // Lookup view for data population
	       TextView itemView = (TextView) convertView.findViewById(R.id.item);
	       
	       // Populate the data into the template view using the data object
	       itemView.setText(item.name);
	   	       
	       // Return the completed view to render on screen
	       return convertView;
	   }
}
