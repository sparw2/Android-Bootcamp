package com.sample.todoapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditFragment extends DialogFragment {

	private EditText itemView;
	private int pos=0;
	private String itemValue;
	
	private OnItemEditedListener listener;
	
	public static EditFragment newInstance(String editItem, int itemPosition) {
		EditFragment editfragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString("editItem", editItem);
        args.putInt("itemPosition", itemPosition);
        editfragment.setArguments(args);
        return editfragment;
	}
	
	
	public interface OnItemEditedListener {
	   public void onItemEdited(String item, int position);
	}
	
	@Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	      if (activity instanceof OnItemEditedListener) {
	        listener = (OnItemEditedListener) activity;
	      } else {
	        throw new ClassCastException(activity.toString()
	            + " must implement EditFragment.OnItemEditedListener");
	      }
	 }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_edit, container);
		itemView =  (EditText)view.findViewById(R.id.edItem);
     
		// Get back arguments
        pos = getArguments().getInt("itemPosition", 0);	
        itemValue = getArguments().getString("editItem", "");	
	
		itemView.setText(itemValue);
		itemView.setSelection(itemView.getText().length());

		getDialog().setTitle(R.string.edit_item_label);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		itemView.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		
        Button button = (Button)view.findViewById(R.id.btSaveItem);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	listener.onItemEdited(itemView.getText().toString(), pos);
            	getDialog().cancel();
            }
        });   

        
        
		return view;
    }
}
