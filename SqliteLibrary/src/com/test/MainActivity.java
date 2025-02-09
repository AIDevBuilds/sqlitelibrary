package com.test;

import static com.sandeepsingh91.DBCodes.EQUALS;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sandeepsingh91.WMap;
import com.sandeepsingh91.callback.Callback;
import com.sandeepsingh91.db.Database;
import com.sandeepsingh91.sqlitelibrary.R;

public class MainActivity extends Activity implements Callback {

	Database query;
	private DB m_ESDDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		query = Database.getSingleInstance(getBaseContext());

		m_ESDDatabase = DB.getSingleInstance(getBaseContext());

	}

	public void onSelect(View v) {

		// query.init().select().execute(Test.class, this);

		m_ESDDatabase.searchAlbums("");

		// SQLiteQuer query = new SQLiteQuer(this);
		//
		// Test t = new Test();
		// t.setId(10);
		// t.setUserName("first");
		//
		// query.addData(t);

	}

	public void onInsert(View v) {

		query.init().insert(new String[] { "name", "id" }, new String[] { "andy", "20" })
				.executeInsert(Test.class, this);

	}

	public void onUpdate(View v) {

		Test t = new Test();
		t.setId(100);
		t.setUserName("first");

		WMap map = new WMap();

		map.put("id", "20", EQUALS);

		query.init().executeUpdate(t, map, this);

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