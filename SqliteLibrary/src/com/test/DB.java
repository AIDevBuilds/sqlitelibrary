package com.test;

import java.io.IOException;

import com.sandeepsingh91.Lo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {

	private static DB s_DB;

	private static DataBaseHelper s_DataBaseHelper;

	/**
	 * Enum for Database Tables
	 * 
	 * @author android
	 *
	 */
	private enum Tables {

		Albums, Artists, Genres, Songs, Folders;
	}

	/**
	 * Private constructor to avoid creation of object
	 */
	private DB() {

	}

	/**
	 * Return the object of {@link DB} as a Singleton Object
	 * 
	 * @param context
	 *            use getBaseContext()
	 * @return
	 */
	public static DB getSingleInstance(Context i_context) {

		if (s_DB == null) {

			synchronized (DB.class) {

				if (s_DB == null) {

					s_DataBaseHelper = new DataBaseHelper(i_context);

					s_DB = new DB();

					try {
						s_DataBaseHelper.createDataBase();

					} catch (IOException ioe) {

						throw new Error("Unable to create database");

					}
				}
			}
		}

		return s_DB;
	}

	public void searchAlbums(String i_searchString) {

		SQLiteDatabase sqld = s_DataBaseHelper.getWritableDatabase();

		sqld.beginTransaction();

		String query = "Select * from Test";

		Cursor cursor = sqld.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				Lo.e("values : " + cursor.getString(cursor.getColumnIndex("name")));
			} while (cursor.moveToNext());
		}

		sqld.setTransactionSuccessful();
		sqld.endTransaction();
		sqld.close();

	}

}
