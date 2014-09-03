package com.sample.todoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Items")
public class Item extends Model {

	@Column(name = "Name")
	public String name;

	public Item() {
		super();
	}

	public Item(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
