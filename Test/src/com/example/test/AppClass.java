package com.example.test;

import android.app.Application;

import com.sandeepsingh91.db.SQLiteQuery;

public class AppClass extends Application {

	@Override
	public void onCreate() {

		super.onCreate();

		SQLiteQuery.getInstance(getBaseContext(), getApplicationInfo().dataDir);
	}

	public SQLiteQuery getSQLiteQuery() {

		return SQLiteQuery.getInstance(getBaseContext(), getApplicationInfo().dataDir);

	}

}
