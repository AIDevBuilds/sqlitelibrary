package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sandeepsingh91.callback.Callback;
import com.sandeepsingh91.db.SQLiteQuer;
import com.sandeepsingh91.db.SQLiteQuery;
import com.sandeepsingh91.db.WhereMap;

public class MainActivity extends Activity implements Callback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void onSelect(View v) {

		((AppClass) getApplication()).getSQLiteQuery().init().select().execute(Test.class, this);

		// SQLiteQuer query = new SQLiteQuer(this);
		//
		// Test t = new Test();
		// t.setId(10);
		// t.setUserName("first");
		//
		// query.addData(t);

	}

	public void onInsert(View v) {

		((AppClass) getApplication()).getSQLiteQuery().init()
				.insert(new String[] { "name", "id" }, new String[] { "andy", "20" }).executeInsert(Test.class, this);

	}

	public void onUpdate(View v) {

		Test t = new Test();
		t.setId(100);
		t.setUserName("first");

		WhereMap map = new WhereMap();

		map.put("id", "20", "=");

		((AppClass) getApplication()).getSQLiteQuery().init().executeUpdate(t, map, this);

	}

	public void onDelete(View v) {

		// ((AppClass) getApplication()).getSQLiteQuery().init()
		// .where(new String[] { "id" }, new String[] { "20" }, new String[] {
		// "=" })
		// .executeDelete(Test.class, this);

	}

	@Override
	public void onSuccess(ArrayList arrayList) {
		System.err.println("MainActivity.onSuccess()");

		for (Object object : arrayList) {

			System.err.println("id :" + ((Test) object).getId());
			System.err.println("username : " + ((Test) object).getUserName());

		}

	}
}